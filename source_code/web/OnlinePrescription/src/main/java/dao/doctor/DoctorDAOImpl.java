package dao.doctor;

import domainModel.doctor.DoctorBO;
import domainModel.doctor.Prescription;
import domainModel.system.JsonConstant;
import domainModel.system.User;
import domainModel.system.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Difan Chen
 * Date: 25/10/2016
 * Student No: 4901496
 */

@Transactional(rollbackForClassName = {"RuntimeException", "Exception"})
public class DoctorDAOImpl implements IDoctorDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * get pending patients
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<Map<String, Object>> getPatientList() {
        String sql = "SELECT * FROM PATIENT_INFO WHERE STATUS = ?";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, new Object[]{UserStatus.PENDING});
        return result;
    }

    @Override
    public boolean approvePatient(int patientId) {
        String sql = "UPDATE PATIENT_INFO SET STATUS = ? WHERE PATIENT_ID = ? AND STATUS = ?";
        int effectRows = jdbcTemplate.update(sql, new Object[]{UserStatus.APPROVED, patientId, UserStatus.PENDING});
        return effectRows > 0;
    }

    @Override
    public boolean rejectPatient(int patientId) {
        String sql = "UPDATE PATIENT_INFO SET STATUS = ? WHERE PATIENT_ID = ? AND STATUS = ?";
        int effectRows = jdbcTemplate.update(sql, new Object[]{UserStatus.REJECTED, patientId, UserStatus.PENDING});
        return effectRows > 0;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<Map<String, Object>> searchMedicine(String name) {
        String sql = "SELECT NAME FROM DRUGS WHERE NAME LIKE ?";
        List<Map<String, Object>> search_list = null;
        try {
            search_list = jdbcTemplate.queryForList(sql, new Object[]{"%" + name + "%"});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return search_list;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<Map<String, Object>> getDrugs() {
        String sql = "SELECT * FROM DRUGS ";
        List<Map<String, Object>> drugList = null;
        try {
            drugList = jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drugList;
    }

    /**
     * Get approved patients
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<Map<String, Object>> getPatients() {
        String sql = "SELECT * FROM PATIENT_INFO WHERE STATUS = ?";
        List<Map<String, Object>> patientList = null;
        try {
            patientList = jdbcTemplate.queryForList(sql, new Object[]{UserStatus.APPROVED});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return patientList;
    }

    /**
     * submit prescription
     *
     * @param prescription
     * @return
     */
    @Override
    public Map<String, Object> submitPrescription(final Prescription prescription, long userId) throws Exception{
        Map<String, Object> retMap = new HashMap<String, Object>();
        //0.make prescription seq
        String sequence = "P" + (new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

        //1.insert prescription
        String insertPrescriptionSql = "INSERT INTO PRESCRIPTION(PRESCRIPTION_SEQ,PRESCRIPTION_DATE,REPEAT_TIME,ORDERED_REPEAT,STATUS,DESCRIPTION,DURATION,TOTAL_PRICE)" +
                " VALUES(?,CURDATE(),?,?,?,?,?,?)";
        Object[] params = new Object[]{sequence, prescription.getRepeat(), 0, 2, prescription.getDescription(), prescription.getDuration(), prescription.getTotalPrice()};
        int rows = jdbcTemplate.update(insertPrescriptionSql, params);
        if (rows <= 0) {
            retMap.put(JsonConstant.SUCCESS, false);
            retMap.put(JsonConstant.ERR_MSG, "Prescription create error.");
        }
        //2.query prescription id
        String queryPrescriptionId = "SELECT PRESCRIPTION_ID FROM PRESCRIPTION WHERE PRESCRIPTION_SEQ = ?";
        final int prescriptionId = jdbcTemplate.queryForObject(queryPrescriptionId, new Object[]{sequence}, Integer.class);

        //3.prescription_drugs
        String insertPrescriptionDrugsSql = "INSERT INTO PRESCRIPTION_DRUGS(DRUG_ID,PRESCRIPTION_ID,DOSE,DESCRIPTION) VALUES(?,?,?,?)";
        int[] batchRows = jdbcTemplate.batchUpdate(insertPrescriptionDrugsSql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                int drugId = prescription.getDrugsId()[i];
                int drugDose = prescription.getDrugsDose()[i];
                String drugDesc = prescription.getDrugsDesc()[i];
                ps.setInt(1, drugId);
                ps.setInt(2, prescriptionId);
                ps.setInt(3, drugDose);
                ps.setString(4, drugDesc);
            }

            @Override
            public int getBatchSize() {
                return prescription.getDrugsId().length;
            }
        });
        //check if success
        for (int i = 0; i < batchRows.length; i++) {
            rows *= batchRows[i];
        }
        if (rows <= 0) {
            retMap.put(JsonConstant.SUCCESS, false);
            retMap.put(JsonConstant.ERR_MSG, "Prescription and drugs relation create error.");
        }
        //TODO,4.prescription_history
        String queryDoctorIdSql = "SELECT DOCTOR_ID FROM DOCTOR_INFO WHERE USER_ID = ?";
        int doctorId = jdbcTemplate.queryForObject(queryDoctorIdSql, new Object[]{userId}, Integer.class);
        String insertPrescriptionHistorySql = "INSERT INTO PRESCRIPTION_HISTORY(PRESCRIPTION_ID,DOCTOR_ID,PATIENT_ID) VALUES(?,?,?)";
        rows *= jdbcTemplate.update(insertPrescriptionHistorySql, new Object[]{prescriptionId, doctorId, prescription.getPatientId()});
        if (rows <= 0) {
            retMap.put(JsonConstant.SUCCESS, false);
            retMap.put(JsonConstant.ERR_MSG, "Prescription history create error.");
        }
        //success
        retMap.put(JsonConstant.SUCCESS, true);
        return retMap;
    }

    @Override
    public Map<String, Object> prescriptionHistory(long userId) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        String sql = "SELECT T2.PRESCRIPTION_SEQ,T1.FIRST_NAME,T1.LAST_NAME,T2.PRESCRIPTION_DATE,T2.TOTAL_PRICE,DESCRIPTION " +
                "FROM PATIENT_INFO T1,PRESCRIPTION T2,PRESCRIPTION_HISTORY T3,DOCTOR_INFO T4 "+
                "WHERE T1.PATIENT_ID = T3.PATIENT_ID " +
                "AND T2.PRESCRIPTION_ID = T3.PRESCRIPTION_ID " +
                "AND T4.DOCTOR_ID = T3.DOCTOR_ID " +
                "AND T4.USER_ID = ? " +
                "ORDER BY PRESCRIPTION_DATE DESC";
        List<Map<String,Object>> list;
        try {
            list = jdbcTemplate.queryForList(sql, new Object[]{userId});
            retMap.put("P_HISTORY_LIST",list);
            retMap.put(JsonConstant.SUCCESS,true);
        }catch (Exception e){
            e.printStackTrace();
            retMap.put(JsonConstant.SUCCESS,false);
            retMap.put(JsonConstant.ERR_MSG,"History query error.");
        }
        return retMap;
    }
}
