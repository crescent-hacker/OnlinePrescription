package controller.patient;

import domainModel.system.SessionAttrKey;
import domainModel.system.User;
import domainModel.system.UserType;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class PatientWebController {
    private static final Logger logger = Logger.getLogger(controller.system.UserController.class);


    @RequestMapping(value = "/patientIndex.page", method = RequestMethod.GET)
    public String patientIndex(ModelMap modelMap) throws Exception {
        Subject currentUser = SecurityUtils.getSubject();
        User user = (User) currentUser.getSession().getAttribute(SessionAttrKey.USER);
        modelMap.put("USER_STATUS", user.getStatus());
        return "domain/patient/patientIndex";
    }

    @RequestMapping(value = "/downloadApk.ajax")
    public void downloadApk(HttpServletRequest request, HttpServletResponse response) {
        String dataDirectory = request.
                getServletContext().getRealPath("/jsp/apk");
        File file = new File(dataDirectory, "OnlinePrescription.apk");
        if (file.exists()) {
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition",
                    "attachment; filename=OnlinePrescription.apk");
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            // if using Java 7, use try-with-resources
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (IOException ex) {
                // do something,
                // probably forward to an Error page
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
    }
}
