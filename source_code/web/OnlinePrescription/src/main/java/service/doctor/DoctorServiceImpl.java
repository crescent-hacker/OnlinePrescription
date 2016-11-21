package service.doctor;

import dao.doctor.IDoctorDAO;
import domainModel.doctor.Prescription;
import domainModel.system.JsonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Difan Chen
 * Date: 25/10/2016
 * Student No: 4901496
 */
@Service
public class DoctorServiceImpl implements IDoctorService {
    @Autowired
    IDoctorDAO doctorDAO;

    @Override
    public List<Map<String, Object>> getPatientList() {
        return doctorDAO.getPatientList();
    }

    @Override
    public boolean approvePatient(int patientId) {
        return doctorDAO.approvePatient(patientId);
    }

    @Override
    public boolean rejectPatient (int patientId) { return doctorDAO.rejectPatient(patientId);}

    @Override
    public List<Map<String,Object>> searchMedicine(String name) { return doctorDAO.searchMedicine(name);}

    @Override
    public List<Map<String, Object>> getDrugs() {
        return doctorDAO.getDrugs();
    }

    @Override
    public List<Map<String, Object>> getPatients() {
        return doctorDAO.getPatients();
    }

    @Override
    public Map<String, Object> submitPrescription(Prescription prescription, long userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        try{
            map = doctorDAO.submitPrescription(prescription,userId);
        }catch (Exception e){
            e.printStackTrace();
            map.put(JsonConstant.SUCCESS,false);
            map.put(JsonConstant.ERR_MSG,"System error.");
        }
        return map;
    }

    @Override
    public Map<String, Object> prescriptionHistory(long userId) {
        return doctorDAO.prescriptionHistory(userId);
    }
}
