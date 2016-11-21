package dao.system;

import domainModel.doctor.DoctorBO;
import domainModel.patient.Patient;
import domainModel.pharmacy.Pharmacy;
import domainModel.system.JsonConstant;
import domainModel.system.User;
import domainModel.system.UserStatus;
import domainModel.system.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Transactional(rollbackForClassName = {"RuntimeException", "Exception"})
public class LoginDAOImpl implements ILoginDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * get login times
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public int getLoginTimes(String username) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(new Date());
        //perform query
        String sql = "SELECT LOGIN_TIMES FROM USER_LOGIN_LOG WHERE USER_NAME = ? AND LOGIN_DATE = ?";
        Object[] params = new Object[]{username, dateString};
        int loginTimes = jdbcTemplate.queryForObject(sql, params, Integer.class);
        return loginTimes;
    }

    /**
     * update login times
     */
    @Override
    public boolean updateLoginTimes(String username, String source) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(new Date());
        //perform update
        String updateSql = "UPDATE USER_LOGIN_LOG SET LOGIN_TIMES = LOGIN_TIMES + 1 WHERE USER_NAME = ? AND LOGIN_DATE = ?";
        Object[] params = new Object[]{username, dateString};
        int effectRows = jdbcTemplate.update(updateSql, params);
        //not exits, then insert one for today
        if (effectRows == 0) {
            String insertSql = "INSERT INTO USER_LOGIN_LOG(USER_NAME,LOGIN_TIMES,LOGIN_DATE,SOURCE) VALUES (?,1,?,?)";
            params = new Object[]{username, dateString, source};
            effectRows += jdbcTemplate.update(insertSql, params);
        }
        return effectRows > 0;
    }

    /**
     * get password by username for login check
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public User getUser(String username, boolean isQueryPwd) {
        //do query
        String sql = "SELECT T1.*,T2.TYPE_NAME " +
                "FROM USER T1,USER_TYPE T2 " +
                "WHERE USER_NAME = ? AND T1.TYPE_ID = T2.TYPE_ID";
        Object[] params = new Object[]{username};
        //set db fields into POJO
        Map<String, Object> userMap = null;
        try {
            userMap = jdbcTemplate.queryForMap(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (userMap != null && userMap.get("USER_NAME") != null) {
            User user = new User();
            user.setUserId((Long) userMap.get("USER_ID"));
            user.setUserName((String) userMap.get("USER_NAME"));
            user.setUserType(UserType.getUserTypeByName((String) userMap.get("TYPE_NAME")));
            user.setEmailAddr((String) userMap.get("EMAIL_ADDR"));
            user.setRegDate((String) userMap.get("REG_DATE"));
            if (isQueryPwd)
                user.setPassword((String) userMap.get("USER_PWD"));
            //query for status
            String roleTableName = "";
            if (user.getUserType() == UserType.DOCTOR)
                roleTableName = "DOCTOR_INFO";
            if (user.getUserType() == UserType.PHARMACY)
                roleTableName = "PHARMACIST_INFO";
            if (user.getUserType() == UserType.PATIENT)
                roleTableName = "PATIENT_INFO";
            if (!"".equals(roleTableName)) {
                String sSql = "SELECT STATUS FROM " + roleTableName + " WHERE USER_ID = ?";
                String status = (String) jdbcTemplate.queryForMap(sSql, new Object[]{user.getUserId()}).get("STATUS");
                user.setStatus(status);
            } else {
                user.setStatus("approved");
            }
            return user;
        } else {
            return null;
        }
    }

    @Override
    public Map<String, Object> registerPatient(Patient patient) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        //check if user existed in DB
        String isUserExistSql = "SELECT COUNT(1) FROM USER WHERE USER_NAME = ?";
        boolean isUserExist = (jdbcTemplate.queryForObject(isUserExistSql, new Object[]{patient.getUserName()}, Integer.class) > 0);
        if (isUserExist) {
            retMap.put(JsonConstant.SUCCESS, false);
            retMap.put(JsonConstant.ERR_MSG, "Username is existed.");
            return retMap;
        }
        //check if email existed in DB
        String isMailExistSql = "SELECT COUNT(1) FROM USER WHERE EMAIL_ADDR = ?";
        boolean isMailExist = (jdbcTemplate.queryForObject(isMailExistSql, new Object[]{patient.getEmail()}, Integer.class) > 0);
        if (isMailExist) {
            retMap.put(JsonConstant.SUCCESS, false);
            retMap.put(JsonConstant.ERR_MSG, "Email is existed.");
            return retMap;
        }
        //insert user
        String insertSql = "INSERT INTO USER(USER_NAME,TYPE_ID,USER_PWD,EMAIL_ADDR,REG_DATE) VALUES(?,?,?,?,?)";
        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Object params[] = new Object[]{patient.getUserName(), 3, patient.getPassword(), patient.getEmail(), dateStr};
        int rows = jdbcTemplate.update(insertSql, params);
        if (rows <= 0) {
            retMap.put(JsonConstant.SUCCESS, false);
            retMap.put(JsonConstant.ERR_MSG, "User insertion error.");
            return retMap;
        }
        //insert patient
        String getIdSql = "SELECT USER_ID FROM USER WHERE USER_NAME = ?";
        int userId = jdbcTemplate.queryForObject(getIdSql, new Object[]{patient.getUserName()}, Integer.class);
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(patient.getDateOfBirth());
        } catch (Exception e) {
            retMap.put(JsonConstant.SUCCESS, false);
            retMap.put(JsonConstant.ERR_MSG, "Birth date error.");
            return retMap;
        }
        String insertPatient = "INSERT INTO PATIENT_INFO(FIRST_NAME,LAST_NAME,DATE_OF_BIRTH,MEDICARE_NO,PHONE_NO,STATUS,USER_ID) VALUES(?,?,?,?,?,?,?)";
        params = new Object[]{patient.getFirstName(), patient.getLastName(), date, patient.getMedicareNo(), patient.getPhoneNo(), UserStatus.PENDING, userId};
        rows = jdbcTemplate.update(insertPatient, params);

        retMap.put(JsonConstant.SUCCESS, rows > 0);
        return retMap;
    }

    @Override
    public Map<String, Object> registerDoctor(DoctorBO doctor) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        //check if user existed in DB
        String isUserExistSql = "SELECT COUNT(1) FROM USER WHERE USER_NAME = ?";
        boolean isUserExist = (jdbcTemplate.queryForObject(isUserExistSql, new Object[]{doctor.getUserName()}, Integer.class) > 0);
        if (isUserExist) {
            retMap.put(JsonConstant.SUCCESS, false);
            retMap.put(JsonConstant.ERR_MSG, "Username is existed.");
            return retMap;
        }
        //check if email existed in DB
        String isMailExistSql = "SELECT COUNT(1) FROM USER WHERE EMAIL_ADDR = ?";
        boolean isMailExist = (jdbcTemplate.queryForObject(isMailExistSql, new Object[]{doctor.getEmail()}, Integer.class) > 0);
        if (isMailExist) {
            retMap.put(JsonConstant.SUCCESS, false);
            retMap.put(JsonConstant.ERR_MSG, "Email is existed.");
            return retMap;
        }
        //insert user
        String insertSql = "INSERT INTO USER(USER_NAME,TYPE_ID,USER_PWD,EMAIL_ADDR,REG_DATE) VALUES(?,?,?,?,?)";
        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Object params[] = new Object[]{doctor.getUserName(), 2, doctor.getPassword(), doctor.getEmail(), dateStr};
        int rows = jdbcTemplate.update(insertSql, params);
        if (rows <= 0) {
            retMap.put(JsonConstant.SUCCESS, false);
            retMap.put(JsonConstant.ERR_MSG, "User insertion error.");
            return retMap;
        }
        //insert doctor
        String getIdSql = "SELECT USER_ID FROM USER WHERE USER_NAME = ?";
        int userId = jdbcTemplate.queryForObject(getIdSql, new Object[]{doctor.getUserName()}, Integer.class);
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(doctor.getDob());
        } catch (Exception e) {
            retMap.put(JsonConstant.SUCCESS, false);
            retMap.put(JsonConstant.ERR_MSG, "Birth date error.");
            return retMap;
        }
        String insertDoctor = "INSERT INTO DOCTOR_INFO(FIRST_NAME,LAST_NAME,DATE_OF_BIRTH,LICENSE_NO,PHONE_NUM,STATUS,USER_ID,HOSPITAL,DEPARTMENT,TITLE,REGISTRATION_DATE)" +
                " VALUES(?,?,?,?,?,?,?,?,?,?,NOW())";
        params = new Object[]{doctor.getFirstName(), doctor.getLastName(), date, doctor.getLicense(), doctor.getPhone(), UserStatus.PENDING, userId, doctor.getHospital(), doctor.getDepartment(), doctor.getTitle()};
        rows = jdbcTemplate.update(insertDoctor, params);

        retMap.put(JsonConstant.SUCCESS, rows > 0);
        return retMap;
    }

    @Override
    public Map<String, Object> registerPharmacy(Pharmacy pharmacy) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        //check if user existed in DB
        String isUserExistSql = "SELECT COUNT(1) FROM USER WHERE USER_NAME = ?";
        boolean isUserExist = (jdbcTemplate.queryForObject(isUserExistSql, new Object[]{pharmacy.getUserName()}, Integer.class) > 0);
        if (isUserExist) {
            retMap.put(JsonConstant.SUCCESS, false);
            retMap.put(JsonConstant.ERR_MSG, "Username is existed.");
            return retMap;
        }
        //check if email existed in DB
        String isMailExistSql = "SELECT COUNT(1) FROM USER WHERE EMAIL_ADDR = ?";
        boolean isMailExist = (jdbcTemplate.queryForObject(isMailExistSql, new Object[]{pharmacy.getEmail()}, Integer.class) > 0);
        if (isMailExist) {
            retMap.put(JsonConstant.SUCCESS, false);
            retMap.put(JsonConstant.ERR_MSG, "Email is existed.");
            return retMap;
        }
        //insert user
        String insertSql = "INSERT INTO USER(USER_NAME,TYPE_ID,USER_PWD,EMAIL_ADDR,REG_DATE) VALUES(?,?,?,?,?)";
        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Object params[] = new Object[]{pharmacy.getUserName(), 4, pharmacy.getPassword(), pharmacy.getEmail(), dateStr};
        int rows = jdbcTemplate.update(insertSql, params);
        if (rows <= 0) {
            retMap.put(JsonConstant.SUCCESS, false);
            retMap.put(JsonConstant.ERR_MSG, "User insertion error.");
            return retMap;
        }
        //insert doctor
        String getIdSql = "SELECT USER_ID FROM USER WHERE USER_NAME = ?";
        int userId = jdbcTemplate.queryForObject(getIdSql, new Object[]{pharmacy.getUserName()}, Integer.class);

        String insertPharmacy = "INSERT INTO PHARMACIST_INFO(NAME,STREET,POSTCODE,SUBURB,PHONE,STATUS,USER_ID,LONGITUDE,LATITUDE,REGISTRATION_DATE)" +
                " VALUES(?,?,?,?,?,?,?,?,?,NOW())";
        params = new Object[]{pharmacy.getName(), pharmacy.getStreet(), pharmacy.getPostcode(), pharmacy.getSuburb(), pharmacy.getPhone(),
                UserStatus.PENDING, userId, Double.valueOf(pharmacy.getLongitude()), Double.valueOf(pharmacy.getLatitude())};
        rows = jdbcTemplate.update(insertPharmacy, params);

        retMap.put(JsonConstant.SUCCESS, rows > 0);
        return retMap;
    }

}
