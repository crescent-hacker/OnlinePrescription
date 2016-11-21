package service.system;

import domainModel.doctor.DoctorBO;
import domainModel.patient.Patient;
import domainModel.pharmacy.Pharmacy;
import domainModel.system.User;

import java.util.Map;

public interface ILoginService {
    int getLoginTimes(String username);

    boolean updateLoginTimes(String username, String source);

    User getUser(String username, boolean isQueryPwd);

    boolean verifyToken(String username, String token) throws Exception;

    Map<String,Object> mobileLogin(String username, String password, String digest) throws Exception;

    Map<String, Object> registerDoctor(DoctorBO doctor);

    Map<String, Object> registerPatient(Patient patient);

    Map<String, Object> registerPharmacy(Pharmacy pharmacy);
}
