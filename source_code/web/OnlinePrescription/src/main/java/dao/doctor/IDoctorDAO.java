package dao.doctor;

import domainModel.doctor.Prescription;

import java.util.List;
import java.util.Map;

/**
 * Author: Difan Chen
 * Date: 25/10/2016
 * Student No: 4901496
 */
public interface IDoctorDAO {
    List<Map<String, Object>> getPatientList();

    boolean approvePatient(int patientId);

    boolean rejectPatient(int patientId);

    ///********************************************************////
    // Custom Intervention: Hrudai Pendli ////////////////////////

    List<Map<String, Object>> searchMedicine(String name);

    List<Map<String, Object>> getDrugs();

    List<Map<String, Object>> getPatients();

    Map<String, Object> submitPrescription(Prescription prescription, long userId) throws Exception;

    Map<String,Object> prescriptionHistory(long userId);
}
