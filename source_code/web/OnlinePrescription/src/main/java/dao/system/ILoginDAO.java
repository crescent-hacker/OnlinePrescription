package dao.system;

import domainModel.demo.Person;
import domainModel.doctor.DoctorBO;
import domainModel.patient.Patient;
import domainModel.pharmacy.Pharmacy;
import domainModel.system.User;

import java.util.List;
import java.util.Map;

public interface ILoginDAO {
    int getLoginTimes(String username);

    boolean updateLoginTimes(String username, String source);

    User getUser(String username, boolean isQueryPwd);

   Map<String,Object> registerPatient(Patient patient);

   Map<String,Object> registerPharmacy(Pharmacy pharmacy);

    Map<String,Object> registerDoctor(DoctorBO doctor);
}