package service.doctor;

import domainModel.doctor.Prescription;

import java.util.List;
import java.util.Map;

/**
 * Author: Difan Chen
 * Date: 25/10/2016
 * Student No: 4901496
 */
public interface IDoctorService {
    List<Map<String, Object>> getPatientList();

    boolean approvePatient(int patientId);

    boolean rejectPatient(int integer);

    List<Map<String, Object>> searchMedicine(String name);

    List<Map<String, Object>> getDrugs();

    List<Map<String, Object>> getPatients();

    Map<String,Object> submitPrescription(Prescription prescription, long userId);

    Map<String,Object> prescriptionHistory(long userId);
}
