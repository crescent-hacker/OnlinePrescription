package service.system;

import dao.system.ILoginDAO;
import domainModel.doctor.DoctorBO;
import domainModel.patient.Patient;
import domainModel.pharmacy.Pharmacy;
import domainModel.system.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.CryptoUtil;
import util.HmacSHA256Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements ILoginService {
    @Autowired
    ILoginDAO loginDAO;

    @Override
    public int getLoginTimes(String username) {
        return loginDAO.getLoginTimes(username);
    }

    @Override
    public boolean updateLoginTimes(String username, String source) {
        return loginDAO.updateLoginTimes(username, source);
    }

    @Override
    public User getUser(String username, boolean isQueryPwd) {
        return loginDAO.getUser(username, isQueryPwd);
    }

    @Override
    public boolean verifyToken(String username, String token) throws Exception {
        int loginTimes = loginDAO.getLoginTimes(username);
        //recompute the token
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String dateStr = df.format(new Date());
        String hashText = CryptoUtil.MD5(username + CryptoConstant.SALT + dateStr + loginTimes * loginTimes);
        String descryptedHashText = CryptoUtil.decryptByAES(CryptoUtil.keyGen(CryptoConstant.SYMMETRIC_KEY), token);
        boolean result = hashText.equals(descryptedHashText);
        return result;
    }

    @Override
    public Map<String, Object> mobileLogin(String username, String password, String digest) throws Exception {
        //0.init return map
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put(JsonConstant.SUCCESS, false);

        //1.verify digest
        String computedDigest = HmacSHA256Utils.digest(password, username + password);
        if (!computedDigest.equals(digest)) {
            retMap.put(JsonConstant.SUCCESS, false);
            retMap.put(JsonConstant.ERR_MSG, "Digest validation error");
            return retMap;
        }

        //2.get user info
        User user = loginDAO.getUser(username, true);
        if (user == null) {
            retMap.put(JsonConstant.SUCCESS, false);
            retMap.put(JsonConstant.ERR_MSG, "User not found");
            return retMap;
        }

        //3.check if user is patient
        if (!user.getUserType().equals(UserType.PATIENT)) {
            retMap.put(JsonConstant.SUCCESS, false);
            retMap.put(JsonConstant.ERR_MSG, "User type error");
            return retMap;
        }

        //4.verify password
        if (!password.equals(user.getPassword())) {
            retMap.put(JsonConstant.SUCCESS, false);
            retMap.put(JsonConstant.ERR_MSG, "Username or password error");
            return retMap;
        }

        //5.login verify success
        //5.1.update login times
        this.updateLoginTimes(username, ClientSource.MOBILE);
        //5.2 get login times
        int loginTimes = this.getLoginTimes(username);
        //5.3.compute token
        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String plainText = username + CryptoConstant.SALT + dateStr + loginTimes * loginTimes;
        String hashText = CryptoUtil.MD5(plainText);
        String token = CryptoUtil.encryptByAES(CryptoUtil.keyGen(CryptoConstant.SYMMETRIC_KEY), hashText);
        retMap.put("TOKEN", token);

        //return verify success
        retMap.put(JsonConstant.SUCCESS, true);
        retMap.put("USER_NAME", username);
        retMap.put("REGISTER_DATE", user.getRegDate());
        retMap.put("EMAIL_ADDRESS", user.getEmailAddr());
        retMap.put("USER_ID", user.getUserId());
        retMap.put("USER_STATUS", user.getStatus());

        return retMap;
    }

    @Override
    public Map<String, Object> registerDoctor(DoctorBO doctor) {
        return loginDAO.registerDoctor(doctor);
    }

    @Override
    public Map<String, Object> registerPatient(Patient patient) {
        return loginDAO.registerPatient(patient);
    }

    @Override
    public Map<String, Object> registerPharmacy(Pharmacy pharmacy) {
        return loginDAO.registerPharmacy(pharmacy);
    }
}
