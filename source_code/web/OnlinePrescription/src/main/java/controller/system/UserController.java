package controller.system;

import domainModel.doctor.DoctorBO;
import domainModel.patient.Patient;
import domainModel.pharmacy.Pharmacy;
import domainModel.system.JsonConstant;
import domainModel.system.SessionAttrKey;
import domainModel.system.User;
import domainModel.system.UserType;
import exception.BusinessException;
import exception.OPErrorCode;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.system.ILoginService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ILoginService loginService;

    private static final Logger logger = Logger.getLogger(controller.system.UserController.class);

    @RequestMapping("/redirect.page")
    public String redirectToSubsystem(HttpServletRequest request) throws Exception {
        Subject currentUser = SecurityUtils.getSubject();
        User user = (User) currentUser.getSession().getAttribute(SessionAttrKey.USER);
        logger.info("testing!!!!!!!!!!!!!~" + user.getUserName());
        return "redirect:" + UserType.getRedirectLink(user.getUserType());

    }

    @RequestMapping(value = "/businessException.ajax", method = RequestMethod.POST)
    @ResponseBody
    public String businessException(HttpServletRequest request) {
        throw new BusinessException(OPErrorCode.NULL_OBJ);
    }

    @RequestMapping(value = "/otherException.ajax", method = RequestMethod.POST)
    @ResponseBody
    public String otherException(HttpServletRequest request) throws Exception {
        throw new Exception();
    }

    /**
     * username and pwd verification
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/checkLogin.ajax", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> checkLogin(String username, String password, String code) {
        Map<String, Object> result = new HashMap<String, Object>();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject currentUser = SecurityUtils.getSubject();
        //verify captcha code
        Object isCheckCode = request.getSession().getAttribute(SessionAttrKey.IS_CHECK_CODE);

        if (isCheckCode != null && (Boolean) isCheckCode) {
            String verifyCode = (String) currentUser.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
            if (verifyCode == null || !verifyCode.equals(code)) {
                throw new BusinessException(OPErrorCode.CAPTCHA_VERIFY_FAILURE);
            }
        }

        try {
            //verify login info
            currentUser.logout();

            if (!currentUser.isAuthenticated()) {
                //use shiro for verification
                token.setRememberMe(true);
                currentUser.login(token);//verify role and rights
                User user = loginService.getUser(username, false);
                currentUser.getSession().setAttribute(SessionAttrKey.USER, user);
            }
            User user = (User) currentUser.getSession().getAttribute(SessionAttrKey.USER);
            //redirect_link
            String redirectUrl = "";
            if ("approved".equals(user.getStatus())) {
                redirectUrl = UserType.getRedirectLink(user.getUserType());
            } else {
                redirectUrl = "userNotApproved.page";
            }
            result.put(JsonConstant.REDIRECT_URL, redirectUrl);
            result.put(JsonConstant.SUCCESS, true);
            return result;
        } catch (Exception ex) {
            currentUser.getSession().setAttribute(SessionAttrKey.IS_CHECK_CODE, true);
            throw new BusinessException(OPErrorCode.LOGIN_VERIFY_FAILURE);
        }

    }

    /**
     * log out
     */
    @RequestMapping(value = "/logout.ajax", method = RequestMethod.GET)
    public String logout() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(JsonConstant.SUCCESS, true);
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        try {
            return "redirect:/login.page";
        } catch (Exception ex) {
            throw new BusinessException(OPErrorCode.LOGIN_VERIFY_FAILURE);
        }
    }

    @RequestMapping(value = "/login.page")
    public String loginPage(ModelMap model) {
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute(SessionAttrKey.USER);
        String userName = "";
        if (user != null) {
            userName = user.getUserName();
        }
        model.put("userName", userName);
        return "domain/system/loginPage";
    }

    @RequestMapping(value = "/mobile/login.ajax", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> mobileLogin(String username, String password, String digest) throws Exception {
        logger.info("User " + username + " trying to login with " + password + " and digest is " + digest);

        Map<String, Object> map = loginService.mobileLogin(username, password, digest);
        String mapJson = new ObjectMapper().writeValueAsString(map);
        logger.info("Response result:\n" + mapJson);
        return map;
    }

    @RequestMapping(value = "/mobile/tokenVerify.ajax", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> mobileTokenVerify(String username, String token) throws Exception {
        Map<String, Object> retMap = new HashMap<String, Object>();
        boolean result = loginService.verifyToken(username, token);
        retMap.put(JsonConstant.SUCCESS, result);
        return retMap;
    }

    @RequestMapping(value = "/userNotApproved.page", method = RequestMethod.GET)
    public String userNotApproved(ModelMap modelMap) throws Exception {
        Subject currentUser = SecurityUtils.getSubject();
        User user = (User) currentUser.getSession().getAttribute(SessionAttrKey.USER);
        modelMap.put("USER_STATUS", user.getStatus());
        return "domain/system/userNotApproved";
    }

    @RequestMapping(value = "/register.page", method = RequestMethod.GET)
    public String registerPage() throws Exception {
        return "domain/system/register";
    }

    @RequestMapping(value = "/register.ajax", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> registerSubmit(@RequestParam Map<String, Object> params) throws Exception {
        Map<String, Object> retMap = null;
        //generate obj
        UserType userType = UserType.getUserTypeByName((String) params.get("user_type"));
        if (userType == UserType.PATIENT) {
            Patient patient = new Patient(params);
            retMap = loginService.registerPatient(patient);
        }
        if (userType == UserType.DOCTOR) {
            DoctorBO doctor = new DoctorBO(params);
            retMap = loginService.registerDoctor(doctor);
        }
        if (userType == UserType.PHARMACY) {
            Pharmacy pharmacy = new Pharmacy(params);
            retMap = loginService.registerPharmacy(pharmacy);
        }
        //check result
        if ((Boolean) retMap.get(JsonConstant.SUCCESS))
            retMap.put(JsonConstant.REDIRECT_URL, "login.page");

        return retMap;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String landing() throws Exception {
        return "domain/system/landingPage";
    }

}