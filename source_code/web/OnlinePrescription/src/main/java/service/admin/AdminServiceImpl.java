package service.admin;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import dao.admin.AdminDAO;
import domainModel.demo.Person;
import domainModel.doctor.DoctorBO;
import domainModel.pharmacy.Pharmacy;

public class AdminServiceImpl implements IAdminService {
	
	@Autowired
	AdminDAO adminDAO;
	
	/**
	 * Count for doctors with filter
	 */
	@Override
	public int getDoctorCount(String wherePart, String statusPart) {
		int count = adminDAO.getDoctorCount(wherePart, statusPart);
		return count;
	}
	
	/**
	 * Doctor datatable service
	 */
	@Override
	public JSONObject getDoctorJsonData(HttpServletRequest request) {
		JSONObject obj = new JSONObject();;
		
		String[] columnSearch = {"d.first_name", "d.last_name","d.hospital","d.department","date_format(d.registration_date,'%d-%m-%Y')"};
		
	    String[] columnSort = {"d.first_name", "d.last_name","d.hospital","d.department","d.registration_date"};
	    
		String wherePart="", orderByPart="", rowNumPart="";
		
		//for datatable internal
		String startStr = request.getParameter("iDisplayStart");
		int start = -1;
		if(startStr != null)
			start = Integer.parseInt(startStr);
		
		int noOfRecords = Integer.parseInt(request.getParameter("iDisplayLength"));
		
		
		if(startStr != null && noOfRecords != -1)
		{
			rowNumPart = "where rownum between "+(start + 1)+" and "+(start+noOfRecords);
		}
		
		// sorting part
		
		if(request.getParameter("iSortCol_0") != null)
		{
			orderByPart = " ORDER BY ";
			
			//System.out.println("no of sortable columns are "+request.getParameter("iSortingCols"));
			for(int i = 0;i< Integer.parseInt(request.getParameter("iSortingCols"));i++)
			{
				
				if(request.getParameter("bSortable_"+Integer.parseInt(request.getParameter("iSortCol_"+i))).equals("true") )
				{
					orderByPart+= columnSort[Integer.parseInt(request.getParameter("iSortCol_"+i))]+" "+request.getParameter("sSortDir_"+i)+" ";
                }
	        }
		}
		
		//search part
		
		if(!request.getParameter("sSearch").equals(""))
		{
			wherePart += " and ( ";
			for(int i = 0;i<columnSearch.length;i++)
			{
				wherePart+=" lower("+columnSearch[i]+") LIKE '%"+request.getParameter("sSearch").toLowerCase()+"%' OR ";
			}
			wherePart = wherePart.substring(0,wherePart.length()-3);
			wherePart+=") ";
		}
		
		String status = request.getParameter("status");
		String statusPart = "";
		if(!status.equals("all")){
			//get doctors by status
			statusPart = " and d.status = '"+status+"'";
		}
		
		//total records
		int echo = Integer.parseInt(request.getParameter("sEcho"));
		int totalRecords = 0;
		totalRecords = adminDAO.getDoctorCount("", statusPart);
		obj.put("sEcho",new Integer(echo));
		obj.put("iTotalRecords",new Integer(totalRecords));
		
		JSONArray parentObj = new JSONArray();
		JSONArray childList = null;
				
		//display data
		List<DoctorBO> list = adminDAO.getDoctorList(wherePart, statusPart, orderByPart, rowNumPart);
		for(int i=0; i<list.size(); i++){
			childList = new JSONArray();
			childList.put(list.get(i).getFirstName());
			childList.put(list.get(i).getLastName());
			childList.put(list.get(i).getHospital());
			childList.put(list.get(i).getDepartment());
			childList.put(list.get(i).getRegistrationDate());
			if(list.get(i).getStatus().equalsIgnoreCase("pending")){
				childList.put("<a href='viewDoctor.page?id="+list.get(i).getDocId()+"'>Approve/Reject</a>");
			}else{
				childList.put("<a href='viewDoctor.page?id="+list.get(i).getDocId()+"'>View</a>");
			}
			
			parentObj.put(childList);
		}
		
		obj.put("aaData", parentObj);
		
		//filtered records
		int totalFilteredRecords = 0;
		if(!wherePart.equals("") ){
			totalFilteredRecords = adminDAO.getDoctorCount(wherePart, statusPart);
		}else{
			totalFilteredRecords = totalRecords;
		}
		obj.put("iTotalDisplayRecords",new Integer(totalFilteredRecords));
		
		return obj;
	}
	
	/**
	 * Doctor details by Id
	 */
	@Override
	public DoctorBO getDoctorById(int id) {
		
		DoctorBO doc = adminDAO.getDoctorById(id);
		//dummy data
		/*doc.setTitle("Dr.");
		doc.setFirstName("Kunal");
		doc.setLastName("Kanere");
		doc.setDocId(1);
		doc.setHospital("Sydney Hospital");
		doc.setDepartment("Department of Cardiology");
		doc.setRegistrationDate("15-09-2016 11:20:00");
		doc.setDob("16-09-1987");
		doc.setPhone("+61-412563789");*/
		return doc;
	}
	
	/**
	 * Doctor Approve/Reject action
	 */
	@Override
	public boolean approveRejectDoctorRequest(int doctorId, String action) {
		boolean response = adminDAO.approveRejectDoctorRequeust(doctorId, action);
		return response;
	}
	
	/**
	 * Pharmacy Data-table service
	 */
	@Override
	public JSONObject getPharmacyJsonData(HttpServletRequest request) {
		JSONObject obj = new JSONObject();
		
		String[] columnSearch = {"name","street","postcode","suburb","phone","date_format(registration_date,'%d-%m-%Y')"};
		
	    String[] columnSort = {"name","street","postcode","suburb","phone","registration_date"};
	    
		String wherePart="", orderByPart="", rowNumPart="";
		
		//for datatable internal
		String startStr = request.getParameter("iDisplayStart");
		int start = -1;
		if(startStr != null)
			start = Integer.parseInt(startStr);
		
		int noOfRecords = Integer.parseInt(request.getParameter("iDisplayLength"));
		
		
		if(startStr != null && noOfRecords != -1)
		{
			rowNumPart = "where rownum between "+(start + 1)+" and "+(start+noOfRecords);
		}
		
		// sorting part
		
		if(request.getParameter("iSortCol_0") != null)
		{
			orderByPart = " ORDER BY ";
			
			//System.out.println("no of sortable columns are "+request.getParameter("iSortingCols"));
			for(int i = 0;i< Integer.parseInt(request.getParameter("iSortingCols"));i++)
			{
				
				if(request.getParameter("bSortable_"+Integer.parseInt(request.getParameter("iSortCol_"+i))).equals("true") )
				{
					orderByPart+= columnSort[Integer.parseInt(request.getParameter("iSortCol_"+i))]+" "+request.getParameter("sSortDir_"+i)+" ";
                }
	        }
		}
		
		//search part
		
		if(!request.getParameter("sSearch").equals(""))
		{
			wherePart += " and ( ";
			for(int i = 0;i<columnSearch.length;i++)
			{
				wherePart+=" lower("+columnSearch[i]+") LIKE '%"+request.getParameter("sSearch").toLowerCase()+"%' OR ";
			}
			wherePart = wherePart.substring(0,wherePart.length()-3);
			wherePart+=") ";
		}
		
		String status = request.getParameter("status");
		String statusPart = "";
		if(!status.equals("all")){
			//get doctors by status
			statusPart = " and status = '"+status+"'";
		}
		
		//total records
		int echo = Integer.parseInt(request.getParameter("sEcho"));
		int totalRecords = 0;
		totalRecords = adminDAO.getPharmacyCount("", statusPart);
		obj.put("sEcho",new Integer(echo));
		obj.put("iTotalRecords",new Integer(totalRecords));
		
		JSONArray parentObj = new JSONArray();
		JSONArray childList = null;
				
		//display data
		List<Pharmacy> list = adminDAO.getPharmacyList(wherePart, statusPart, orderByPart, rowNumPart);
		for(int i=0; i<list.size(); i++){
			childList = new JSONArray();
			childList.put(list.get(i).getName());
			childList.put(list.get(i).getStreet());
			childList.put(list.get(i).getPostcode());
			childList.put(list.get(i).getSuburb());
			childList.put(list.get(i).getPhone());
			childList.put(list.get(i).getRegistrationDate());
			if(list.get(i).getStatus().equalsIgnoreCase("pending")){
				childList.put("<a href='viewPharmacy.page?id="+list.get(i).getId()+"'>Approve/Reject</a>");
			}else{
				childList.put("<a href='viewPharmacy.page?id="+list.get(i).getId()+"'>View</a>");
			}
			
			parentObj.put(childList);
		}
		
		obj.put("aaData", parentObj);
		
		//filtered records
		int totalFilteredRecords = 0;
		if(!wherePart.equals("") ){
			totalFilteredRecords = adminDAO.getPharmacyCount(wherePart, statusPart);
		}else{
			totalFilteredRecords = totalRecords;
		}
		obj.put("iTotalDisplayRecords",new Integer(totalFilteredRecords));
		
		return obj;
	}
	
	/**
	 * Pharmacy object by Id
	 */
	@Override
	public Pharmacy getPharmacyById(int id) {
		Pharmacy pharm = adminDAO.getPharmacyById(id);
		return pharm;
	}
	
	/**
	 * Pharmacy action Approve/Reject 
	 */
	@Override
	public boolean approveRejectPharmacyRequest(int pharmId, String action) {
		boolean update = adminDAO.approveRejectPharmacyRequeust(pharmId, action);
		return update;
	}
	
	/**
	 * Count of Pharmacy request by filter
	 */
	@Override
	public int getPharmacyCount(String wherePart, String statusPart) {
		
		return adminDAO.getPharmacyCount(wherePart, statusPart);
	}
	
	/**
	 * Doctor request Report service
	 */
	@Override
	public String downloadDoctorReport(HttpServletRequest request, String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		String statusPart="";
		if(status.equalsIgnoreCase("pending")){
			statusPart=" and d.status='pending' ";
		}else if(status.equalsIgnoreCase("approved")){
			statusPart=" and d.status='approved' ";
		}else if(status.equalsIgnoreCase("rejected")){
			statusPart=" and d.status='rejected' ";
		}
		
		String orderByPart = " order by d.registration_date asc";
		List<DoctorBO> list = adminDAO.getDoctorList("", statusPart, orderByPart, "");
	
		map.put("doctorList", list);
		//map.put("fileName", "PersonExport"+".csv");
		String output="";
		try{
			InputStream isStream = null ;
	        isStream = this.getClass().getClassLoader().getResourceAsStream("../../static/vm/doctorData.vm");
	        Velocity.init();
	        VelocityContext context = new VelocityContext(map);
			StringWriter writer = new StringWriter();
			Velocity.evaluate(context, writer, "", isStream);
			output = writer.getBuffer().toString();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return output;
	}
	
	/**
	 * Pharmacy request report service 
	 */
	@Override
	public String downloadPharmacyReport(HttpServletRequest request,
			String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		String statusPart="";
		if(status.equalsIgnoreCase("pending")){
			statusPart=" and status='pending' ";
		}else if(status.equalsIgnoreCase("approved")){
			statusPart=" and status='approved' ";
		}else if(status.equalsIgnoreCase("rejected")){
			statusPart=" and status='rejected' ";
		}
		
		String orderByPart = " order by registration_date asc";
		List<Pharmacy> list = adminDAO.getPharmacyList("", statusPart, orderByPart, "");
	
		map.put("pharmList", list);
		//map.put("fileName", "PersonExport"+".csv");
		String output="";
		try{
			InputStream isStream = null ;
	        isStream = this.getClass().getClassLoader().getResourceAsStream("../../static/vm/pharmacyData.vm");
	        Velocity.init();
	        VelocityContext context = new VelocityContext(map);
			StringWriter writer = new StringWriter();
			Velocity.evaluate(context, writer, "", isStream);
			output = writer.getBuffer().toString();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return output;
	}

	

}
