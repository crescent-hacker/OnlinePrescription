package service.patient;

import dao.patient.IPatientDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PatientServiceImpl implements IPatientService {
    @Autowired
    IPatientDAO patientDAO;

    @Override
    public List<Map<String, Object>> getPrescriptionList(String username) {
        return patientDAO.getPrescriptionList(username);
    }

    @Override
    public Map<String, Object> getPrescriptionDetail(String username, int prescriptionId) {
        return patientDAO.getPrescriptionDetail(username, prescriptionId);
    }

    @Override
    public List<Map<String, Object>> getPharmacyList() {
        return patientDAO.getPharmacyList();
    }

    @Override
    public List<Map<String, Object>> getUntreatedOrderList(String username) throws Exception {
        return patientDAO.getUntreatedOrderList(username);
    }

    @Override
    public boolean order(String username, String prescription_id, String pharmacy_id, String pick_time) throws Exception {
        return patientDAO.order(username, prescription_id, pharmacy_id, pick_time);
    }

    @Override
    public List<Map<String, Object>> getOrderList(String username, String order_status_filter) {
        return patientDAO.getOrderList(username, order_status_filter);
    }

    @Override
    public Map<String, Object> getOrderDetail(String username, String order_id) {
        return patientDAO.getOrderDetail(username, order_id);
    }

    @Override
    public Map<String, Object> viewProfile(String username) {
        return patientDAO.viewProfile(username);
    }

    @Override
    public boolean editProfile(String username, String phone_no, String email) {
        return patientDAO.editProfile(username, phone_no, email);
    }

    @Override
    public Map<String, Object> getPharmacyDetail(int id) {
        return patientDAO.getPharmacyDetail(id);
    }
}
