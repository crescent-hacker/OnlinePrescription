package dao.admin;

import java.util.List;

import domainModel.doctor.DoctorBO;
import domainModel.pharmacy.Pharmacy;

public interface AdminDAO {
	//Doctor
	int getDoctorCount(String wherePart, String statusPart);
	
	List<DoctorBO> getDoctorList(String wherePart, String statusPart, String orderByPart, String rownumPart);
	
	DoctorBO getDoctorById(int id);
	
	boolean approveRejectDoctorRequeust(int doctorId, String action);
	
	//Pharmacy
	int getPharmacyCount(String wherePart, String statusPart);
	
	List<Pharmacy> getPharmacyList(String wherePart, String statusPart, String orderByPart, String rownumPart);
	
	Pharmacy getPharmacyById(int id);
	
	boolean approveRejectPharmacyRequeust(int pharmId, String action);
}
