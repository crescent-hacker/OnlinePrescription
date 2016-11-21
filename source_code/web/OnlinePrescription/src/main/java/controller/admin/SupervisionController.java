package controller.admin;

import java.io.PrintWriter;
import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import domainModel.doctor.DoctorBO;
import domainModel.pharmacy.Pharmacy;
import service.admin.AdminServiceImpl;
import service.admin.IAdminService;

/**
 * Controller for supervision client
 */
@Controller
public class SupervisionController {

    @Autowired
    IAdminService adminService;

	//private AdminServiceImpl adminService = new AdminServiceImpl();
    private static final Logger logger = Logger.getLogger(SupervisionController.class);
    
    /**
     * To redirect to view list of doctor requests in table format
     * @param model
     * @return
     */
    @RequestMapping(value = "/viewDoctorRequest.page")
    public String viewDoctorRequest(ModelMap model) {

        String greeting = "Doctor Requests";
        model.addAttribute("message", greeting);

        logger.debug(greeting);

        return "domain/admin/doctorRequestDatatable";
    }
    
    /**
     * Returns doctor request data-table data in JSON format
     * @param request
     * @return
     */
    @RequestMapping(value="/getDoctorRequest.ajax")
    @ResponseBody
    public String getDoctorRequest(HttpServletRequest request){
    	logger.info("doctor request data table called........");
    	JSONObject obj=null;
    	try{
    		obj = adminService.getDoctorJsonData(request);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
		
        return obj.toString();
	}

    /**
     * Redirects user to view details of a doctor by Id
     * @param model
     * @param doctorId
     * @return
     */
    @RequestMapping(value = "/viewDoctor.page")
    public String viewDoctor(ModelMap model, @RequestParam("id") int doctorId) {

    	//int doctorId = Integer.parseInt(request.getParameter("id"));
        //System.out.println("doc id = "+doctorId);
    	DoctorBO doctorBO = adminService.getDoctorById(doctorId);
        
        model.addAttribute("doctorBo",doctorBO);
        if(doctorBO.getStatus().equalsIgnoreCase("pending"))
        	model.addAttribute("actionRequired", "yes");
        else
        	model.addAttribute("actionRequired", "no");
        return "domain/admin/viewDoctor";
    }
    /**
     * Ajax call to perform action of Approve/Reject for a doctor application
     * @param request
     * @param doctorId
     * @param action
     * @return
     */
    @RequestMapping(value="/approveRejectDoctorRequest.ajax")
    @ResponseBody
    public String approveRejectDoctorRequest(HttpServletRequest request, 
    		@RequestParam("doctorId") int doctorId, @RequestParam("action") String action){
    	logger.info("approve reject called........");
    	String message = "";
    	try{
    		if(adminService.approveRejectDoctorRequest(doctorId, action))
    			message = "success";
    		else
    			message = "fail";
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
		
        return message;
	}
    
    /**
     *  To redirect to view list of pharmacy requests in table format
     * @param model
     * @return
     */
    @RequestMapping(value = "/viewPharmacyRequest.page")
    public String viewPharmacyRequest(ModelMap model) {

        String greeting = "Pharmacy Requests";
        model.addAttribute("message", greeting);

        logger.debug(greeting);

        return "domain/admin/pharmacyRequestDatatable";
    }
    
    /**
     * Returns pharmacy request data-table data in JSON format
     * @param request
     * @return
     */
    @RequestMapping(value="/getPharmacyRequest.ajax")
    @ResponseBody
    public String getPharmacyRequest(HttpServletRequest request){
    	logger.info("pharmacy request data table called........");
    	JSONObject obj=null;
    	try{
    		obj = adminService.getPharmacyJsonData(request);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
		
        return obj.toString();
	}
    
    /**
     * View details of a pharmacy by id
     * @param model
     * @param pharmId
     * @return
     */
    @RequestMapping(value = "/viewPharmacy.page")
    public String viewPharmacy(ModelMap model, @RequestParam("id") int pharmId) {

    	//int doctorId = Integer.parseInt(request.getParameter("id"));
      //  System.out.println("pharm id = "+pharmId);
        Pharmacy pharm = adminService.getPharmacyById(pharmId);
        
        model.addAttribute("pharm",pharm);
        if(pharm.getStatus().equalsIgnoreCase("pending"))
        	model.addAttribute("actionRequired", "yes");
        else
        	model.addAttribute("actionRequired", "no");
        return "domain/admin/viewPharmacy";
    }
    
    /**
     * Ajax call to perform action of Approve/Reject for a doctor application
     * @param request
     * @param pharmId
     * @param action
     * @return
     */
    @RequestMapping(value="/approveRejectPharmacyRequest.ajax")
    @ResponseBody
    public String approveRejectPharmacyRequest(HttpServletRequest request, 
    		@RequestParam("pharmId") int pharmId, @RequestParam("action") String action){
    	logger.info("approve reject pharm called........");
    	String message = "";
    	try{
    		if(adminService.approveRejectPharmacyRequest(pharmId, action))
    			message = "success";
    		else
    			message = "fail";
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
		
        return message;
	}
    
    //Dashboard
    /**
     * Redirect user to dashboard view
     * @param model
     * @return
     */
    @RequestMapping(value = "/viewDashboard.page")
    public String viewDashboard(ModelMap model) {
    	DecimalFormat df = new DecimalFormat("#.##");
    	//Chart data for Doctor
    	float docAppCount = adminService.getDoctorCount("", " and status = 'approved'");
    	float docPenCount = adminService.getDoctorCount("", "and status = 'pending'");
    	float docRejCount = adminService.getDoctorCount("", "and status = 'rejected'");
    	float docTot = docAppCount+docPenCount+docRejCount;
    	//System.out.println(a+"-"+p+"-"+r+"-"+(a/(a+p+r)));
    	try{
    		float aa = (docAppCount/docTot)*100;
    		float pp = (docPenCount/docTot)*100;
    		float rr = (docRejCount/docTot)*100;
    		model.addAttribute("docApp", df.format(aa));
        	model.addAttribute("docPen", df.format(pp));
        	model.addAttribute("docRej", df.format(rr));
        //	System.out.println(aa+"-"+pp+"-"+rr);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	//Chart data for Pharmacy
    	float pharmAppCount = adminService.getPharmacyCount("", " and status = 'approved'");
    	float pharmPenCount = adminService.getPharmacyCount("", "and status = 'pending'");
    	float pharmRejCount = adminService.getPharmacyCount("", "and status = 'rejected'");
    	float pharmTot = pharmAppCount+pharmPenCount+pharmRejCount;
    	//System.out.println(a+"-"+p+"-"+r+"-"+(a/(a+p+r)));
    	try{
    		float aa = (pharmAppCount/pharmTot)*100;
    		float pp = (pharmPenCount/pharmTot)*100;
    		float rr = (pharmRejCount/pharmTot)*100;
    		
    		model.addAttribute("pharmApp", df.format(aa));
        	model.addAttribute("pharmPen", df.format(pp));
        	model.addAttribute("pharmRej", df.format(rr));
        //	System.out.println(aa+"-"+pp+"-"+rr);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return "domain/admin/dashboard";
    }
    
    /**
     * Download Doctor Request details in excel format
     * @param request
     * @param response
     * @param status
     */
    @RequestMapping(value="/downloadDoctorReport.ajax")
   	public void downloadDoctorReport(HttpServletRequest request , HttpServletResponse response,
   			@RequestParam("status") String status){
       	logger.info("download doctor excel........");
   		
   		try{
   			String output = adminService.downloadDoctorReport(request, status);
   			PrintWriter outData = response.getWriter();
			response.setContentType("application/vnd.ms-excel");		
			response.setHeader("Expires:", "0");
			response.setHeader("Content-Disposition", "attachment; filename=Doctor_Report.xls");
			outData.println(output);
			outData.flush();
			outData.close();
   		}catch (Exception e) {
   			e.printStackTrace();
   		}
   	}
    
    /**
     * Download Pharmacy Request details in excel format
     * @param request
     * @param response
     * @param status
     */
    @RequestMapping(value="/downloadPharmacyReport.ajax")
   	public void downloadPharmacyReport(HttpServletRequest request , HttpServletResponse response,
   			@RequestParam("status") String status){
       	logger.info("download pharmacy excel........");
   		
   		try{
   			String output = adminService.downloadPharmacyReport(request, status);
   			PrintWriter outData = response.getWriter();
			response.setContentType("application/vnd.ms-excel");		
			response.setHeader("Expires:", "0");
			response.setHeader("Content-Disposition", "attachment; filename=Pharmacy_Report.xls");
			outData.println(output);
			outData.flush();
			outData.close();
   		}catch (Exception e) {
   			e.printStackTrace();
   		}
   	}
}
