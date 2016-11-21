package dao.patient;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface IPatientDAO {
    List<Map<String,Object>> getPrescriptionList(String username);

    Map<String,Object> getPrescriptionDetail(String username,int prescriptionId);

    List<Map<String,Object>> getPharmacyList();

    List<Map<String,Object>> getUntreatedOrderList(String username) throws ParseException;

    boolean order(String username, String prescription_id, String pharmacy_id,String pick_time) throws Exception;

    List<Map<String,Object>> getOrderList(String username, String order_status_filter);

    Map<String, Object> getOrderDetail(String username, String order_id);

    Map<String,Object> viewProfile(String username);

    boolean editProfile(String username, String phone_no, String email);

    Map<String,Object> getPharmacyDetail(int id);
}