package dao.patient;

import domainModel.system.UserStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Transactional(rollbackForClassName = {"RuntimeException", "Exception"})
public class PatientDAOImpl implements IPatientDAO {
    private static final Logger logger = Logger.getLogger(controller.system.UserController.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<Map<String, Object>> getPrescriptionList(String username) {
        String sql = "SELECT T1.REPEAT_TIME,T1.ORDERED_REPEAT,T1.PRESCRIPTION_ID,T1.PRESCRIPTION_DATE,T1.STATUS " +
                "FROM PRESCRIPTION T1,PRESCRIPTION_HISTORY T2,USER T3,PATIENT_INFO T4 " +
                "WHERE T1.PRESCRIPTION_ID = T2.PRESCRIPTION_ID " +
                "AND T2.PATIENT_ID = T4.PATIENT_ID " +
                "AND T3.USER_ID = T4.USER_ID " +
                "AND T3.USER_NAME = ? " +
                "ORDER BY T1.PRESCRIPTION_DATE DESC";
        List<Map<String, Object>> list = null;
        try {
            list = jdbcTemplate.queryForList(sql, new Object[]{username});
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            return list;
        }
        for (int i = 0; i < list.size(); i++) {
            String desc = "Rest repeat: " + ((Integer) list.get(i).get("REPEAT_TIME") - (Integer) list.get(i).get("ORDERED_REPEAT"));
            list.get(i).put("BRIEF_DESC", desc);
            list.get(i).put("REPEAT", list.get(i).get("REPEAT_TIME"));
            list.get(i).remove("REPEAT_TIME");
        }
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Map<String, Object> getPrescriptionDetail(String userName, int prescriptionId) {

        //query detail of prescription
        String pSql = "SELECT T1.DESCRIPTION,T1.DURATION,T1.PRESCRIPTION_ID,T1.PRESCRIPTION_DATE,T1.ORDERED_REPEAT," +
                "T1.REPEAT_TIME,T1.STATUS,T5.FIRST_NAME,T5.LAST_NAME,T5.HOSPITAL AS HOSPITAL_NAME," +
                "T1.TOTAL_PRICE,T1.LATEST_REPEAT_TIME " +
                "FROM PRESCRIPTION T1,PRESCRIPTION_HISTORY T2,USER T3,PATIENT_INFO T4,DOCTOR_INFO T5 " +
                "WHERE T1.PRESCRIPTION_ID = T2.PRESCRIPTION_ID " +
                "AND T2.PATIENT_ID = T4.PATIENT_ID " +
                "AND T3.USER_ID = T4.USER_ID " +
                "AND T5.DOCTOR_ID = T2.DOCTOR_ID " +
                "AND T3.USER_NAME = ? " +
                "AND T1.PRESCRIPTION_ID = ?";

        Map<String, Object> prescriptionMap = null;
        try {
            prescriptionMap = jdbcTemplate.queryForMap(pSql, new Object[]{userName, prescriptionId});
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            return prescriptionMap;
        }
        String doctorName = prescriptionMap.get("FIRST_NAME") + " " + prescriptionMap.get("LAST_NAME");
        prescriptionMap.put("DOCTOR_NAME", doctorName);
        prescriptionMap.put("REPEAT", prescriptionMap.get("REPEAT_TIME"));
        prescriptionMap.remove("REPEAT_TIME");

        //query the corresponding drugs
        String dSql = "SELECT T3.NAME AS DRUG_NAME,T3.PRODUCER AS DRUG_MANUFACTORY,T2.DOSE,T3.PRICE " +
                "FROM PRESCRIPTION T1,PRESCRIPTION_DRUGS T2,DRUGS T3 " +
                "WHERE T1.PRESCRIPTION_ID = T2.PRESCRIPTION_ID " +
                "AND T2.DRUG_ID = T3.ID " +
                "AND T1.PRESCRIPTION_ID = ? ";

        List<Map<String, Object>> drugList = jdbcTemplate.queryForList(dSql, new Object[]{prescriptionId});

        prescriptionMap.put("DRUG_LIST", drugList);

        return prescriptionMap;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<Map<String, Object>> getPharmacyList() {
        String sql = "SELECT ID AS PHARMACY_ID,NAME AS PHARMACY_NAME,STREET,SUBURB,POSTCODE FROM PHARMACIST_INFO WHERE STATUS = ?";
        List<Map<String, Object>> phList = null;
        try {
            phList = jdbcTemplate.queryForList(sql,new Object[]{UserStatus.APPROVED});
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            return phList;
        }
        for (Map<String, Object> map : phList) {
            String address = map.get("STREET") + "," + map.get("SUBURB") + "," + map.get("POSTCODE");
            map.put("ADDRESS", address);
        }
        return phList;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<Map<String, Object>> getUntreatedOrderList(String username) throws ParseException {
        String sql = "SELECT T1.LATEST_REPEAT_TIME,T1.DURATION,T1.PRESCRIPTION_ID,T1.PRESCRIPTION_DATE,T5.FIRST_NAME,T5.LAST_NAME,T5.HOSPITAL " +
                "FROM PRESCRIPTION T1,PRESCRIPTION_HISTORY T2,USER T3,PATIENT_INFO T4,DOCTOR_INFO T5 " +
                "WHERE T1.PRESCRIPTION_ID = T2.PRESCRIPTION_ID " +
                "AND T2.PATIENT_ID = T4.PATIENT_ID " +
                "AND T3.USER_ID = T4.USER_ID " +
                "AND T5.DOCTOR_ID = T2.DOCTOR_ID " +
                "AND T1.STATUS <> 1 " +
                "AND T3.USER_NAME = ? " +
                "ORDER BY T1.PRESCRIPTION_DATE DESC";
        List<Map<String, Object>> list = null;
        try {
            list = jdbcTemplate.queryForList(sql, new Object[]{username});
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            return list;
        }

        for (Map<String, Object> map : list) {
            String doctorName = map.get("FIRST_NAME") + " " + map.get("LAST_NAME");
            map.put("DOCTOR_NAME", doctorName);
        }
        long today = new Date().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = list.size() - 1; i >= 0; i--) {
            long lRepeatTime = sdf.parse((String) list.get(i).get("LATEST_REPEAT_TIME")).getTime();
            long diffDays = (today - lRepeatTime) / (1000 * 60 * 60 * 24);
            long duration = (Integer) list.get(i).get("DURATION");
            if (diffDays <= duration) {
                list.remove(i);
            }
        }
        return list;
    }

    @Override
    public boolean order(String username, String prescription_id, String pharmacy_id, String pick_time) throws Exception {
        //wrong params
        if (prescription_id == null || pharmacy_id == null || pick_time == null)
            return false;
        //query the prescription
        String querySql = "SELECT * FROM PRESCRIPTION WHERE PRESCRIPTION_ID = ?";
        Map<String, Object> prescription = jdbcTemplate.queryForMap(querySql, new Object[]{prescription_id});
        int repeat = (Integer) prescription.get("REPEAT_TIME");
        int orderedRepeat = (Integer) prescription.get("ORDERED_REPEAT");
        int status = (Integer) prescription.get("STATUS");
        //handle updating prescription
        int uStatus = status;
        int uOrderedRepeat = orderedRepeat;
        //close prescription
        if (repeat - orderedRepeat == 0 || status == 1) {
            return false;
        }
        //have not passed the duration yet
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long today = new Date().getTime();
        long lRepeatDate = sdf.parse((String) prescription.get("LATEST_REPEAT_TIME")).getTime();
        long diffDays = (today - lRepeatDate) / (1000 * 60 * 60 * 24);
        long duration = (Integer) prescription.get("DURATION");
        if (diffDays <= duration) {
            return false;
        }
        //update fields
        if (repeat - orderedRepeat == 1) {
            uStatus = 1;
        }
        if (repeat - orderedRepeat > 1) {
            uStatus = 0;
        }
        uOrderedRepeat++;

        SimpleDateFormat sdf_1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf_2 = new SimpleDateFormat("YYYYMMddHHmmss");
        Date now = new Date();
        String orderId = sdf_2.format(now);
        String orderTime = sdf_1.format(now);
        String lRepeatTime = sdf.format(now);

        //update prescription status
        String updateSql = "UPDATE PRESCRIPTION SET STATUS = ?,ORDERED_REPEAT = ?,LATEST_REPEAT_TIME = ? WHERE PRESCRIPTION_ID = ?";
        int rows = jdbcTemplate.update(updateSql, new Object[]{uStatus, uOrderedRepeat, lRepeatTime, prescription_id});
        if (rows <= 0) {
            return false;
        }

        //insert order
        String insertSql = "INSERT INTO ORDERS(ORDER_ID,PRESCRIPTION_ID,ORDER_TIME,PICK_TIME,STATUS,PHAR_ID) VALUES(?,?,?,?,?,?)";
        rows = jdbcTemplate.update(insertSql, new Object[]{orderId, prescription_id, orderTime, pick_time, 0, pharmacy_id});

        return rows > 0;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<Map<String, Object>> getOrderList(String username, String order_status_filter) {
        String sql = "SELECT T1.ORDER_ID,T1.ORDER_TIME,T5.TOTAL_PRICE " +
                "FROM ORDERS T1,PRESCRIPTION_HISTORY T2,PATIENT_INFO T3,USER T4,PRESCRIPTION T5 " +
                "WHERE T1.PRESCRIPTION_ID = T2.PRESCRIPTION_ID " +
                "AND T1.PRESCRIPTION_ID = T5.PRESCRIPTION_ID " +
                "AND T2.PATIENT_ID = T3.PATIENT_ID " +
                "AND T3.USER_ID = T4.USER_ID " +
                "AND T4.USER_NAME = ? " +
                "AND T1.STATUS = ? " +
                "ORDER BY T1.ORDER_TIME,T5.TOTAL_PRICE DESC";
        List<Map<String, Object>> list = null;
        try {
            list = jdbcTemplate.queryForList(sql, new Object[]{username, order_status_filter});
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            return list;
        }

        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date orderTime = new Date(((Timestamp) map.get("ORDER_TIME")).getTime());
            String orderTimeStr = sdf.format(orderTime);
            map.put("ORDER_TIME", orderTimeStr);
        }

        return list;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Map<String, Object> getOrderDetail(String username, String order_id) {
        String sql = "SELECT T1.PHAR_ID AS PHARMACY_ID,T1.PICK_TIME,T1.PRESCRIPTION_ID,T2.NAME AS PHARMACY_NAME,T2.SUBURB,T2.STREET,T2.POSTCODE,T3.TOTAL_PRICE,T1.ORDER_TIME " +
                "FROM ORDERS T1,PHARMACIST_INFO T2,PRESCRIPTION T3,PATIENT_INFO T4,USER T5,PRESCRIPTION_HISTORY T6 " +
                "WHERE T1.PRESCRIPTION_ID = T3.PRESCRIPTION_ID " +
                "AND T1.PHAR_ID = T2.ID " +
                "AND T4.PATIENT_ID = T6.PATIENT_ID " +
                "AND T6.PRESCRIPTION_ID = T1.PRESCRIPTION_ID " +
                "AND T5.USER_ID = T4.USER_ID " +
                "AND T1.ORDER_ID = ? " +
                "AND T5.USER_NAME = ?";
        Map<String, Object> retMap = null;
        try {
            retMap = jdbcTemplate.queryForMap(sql, new Object[]{order_id, username});
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            return retMap;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date orderTime = new Date(((Timestamp) retMap.get("ORDER_TIME")).getTime());
        String orderTimeStr = sdf.format(orderTime);
        retMap.put("ORDER_TIME", orderTimeStr);
        String address = retMap.get("STREET") + "," + retMap.get("SUBURB") + "," + retMap.get("POSTCODE");
        retMap.put("ADDRESS", address);
        return retMap;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Map<String, Object> viewProfile(String username) {
        String sql = "SELECT T1.USER_NAME,T2.FIRST_NAME,T2.LAST_NAME,T2.DATE_OF_BIRTH," +
                "T2.MEDICARE_NO,T2.PHONE_NO,T1.EMAIL_ADDR,T2.STATUS " +
                "FROM USER T1,PATIENT_INFO T2 " +
                "WHERE T1.USER_ID = T2.USER_ID " +
                "AND T1.USER_NAME = ?";
        Map<String, Object> retMap = null;
        try {
            retMap = jdbcTemplate.queryForMap(sql, new Object[]{username});
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            return retMap;
        }
        return retMap;
    }

    @Override
    public boolean editProfile(String username, String phone_no, String email) {
        String sql = "UPDATE USER T1,PATIENT_INFO T2 " +
                "SET T1.EMAIL_ADDR = ?,T2.PHONE_NO = ? " +
                "WHERE T1.USER_ID = T2.USER_ID " +
                "AND T1.USER_NAME = ?";
        int effectRows = jdbcTemplate.update(sql, new Object[]{email, phone_no, username});
        return effectRows > 0;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Map<String, Object> getPharmacyDetail(int id) {
        String sql = "SELECT ID,NAME,STREET,POSTCODE,SUBURB,PHONE,STATUS,LONGITUDE,LATITUDE,REGISTRATION_DATE" +
                " FROM PHARMACIST_INFO WHERE ID = ? AND STATUS = ?";
        Map<String, Object> retMap = null;
        try {
            retMap = jdbcTemplate.queryForMap(sql, new Object[]{id, UserStatus.APPROVED});
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            return retMap;
        }
        Timestamp t = (Timestamp) retMap.get("REGISTRATION_DATE");
        Date date = new Date(t.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String regDateStr = sdf.format(date);
        retMap.put("REGISTRATION_DATE", regDateStr);
        return retMap;
    }

}
