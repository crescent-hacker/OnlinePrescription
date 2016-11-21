package service.admin;


import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import domainModel.doctor.DoctorBO;
import domainModel.pharmacy.Pharmacy;

public interface IAdminService {
	
	//For Doctor request
	int getDoctorCount(String wherePart, String statusPart);
	
	JSONObject getDoctorJsonData(HttpServletRequest request);
	
	DoctorBO getDoctorById(int id);
	
	boolean approveRejectDoctorRequest(int doctorId, String action);
	
	public String downloadDoctorReport(HttpServletRequest request, String status);
	
	//For Pharmacy Request
	int getPharmacyCount(String wherePart, String statusPart);
	
	public JSONObject getPharmacyJsonData(HttpServletRequest request);
	
	Pharmacy getPharmacyById(int id);
	
	boolean approveRejectPharmacyRequest(int pharmId, String action);
	
	public String downloadPharmacyReport(HttpServletRequest request, String status);
}
