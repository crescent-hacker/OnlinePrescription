package controller.doctor;

import domainModel.doctor.Prescription;
import domainModel.system.JsonConstant;
import domainModel.system.SessionAttrKey;
import domainModel.system.User;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.doctor.IDoctorService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for doctor client
 */
@Controller
public class DoctorController {

    @Autowired
    IDoctorService doctorService;

    private static final Logger logger = Logger.getLogger(DoctorController.class);

    @RequestMapping(value = "/doctorIndex.page")
    public String doctorIndex(ModelMap model) {

        String greeting = "Index is being accessed.";
        model.addAttribute("message", greeting);

        logger.debug(greeting);

        return "domain/doctor/doctorIndex";
    }

    @RequestMapping(value = "/referpatient.page")
    public String referPatient(ModelMap model) {

        String greeting = "Index is being accessed.";
        model.addAttribute("message", greeting);

        logger.debug(greeting);

        return "domain/doctor/referPatient";
    }

    @RequestMapping(value = "/prescribemedicines.page")
    public String prescribeMedicines(ModelMap model) {

        String greeting = "Index is being accessed.";
        model.addAttribute("message", greeting);

        logger.debug(greeting);

        return "domain/doctor/prescribeMedicines";
    }

    @RequestMapping(value = "/prescribemedicines.ajax")
    @ResponseBody
    public List<Map<String,Object>> drugSearch (String name) {
        System.out.println("search criteria is: "+  name);
        List<Map<String,Object>> resultList = doctorService.searchMedicine(name);
        System.out.println("search result is: "+  resultList);
        return  resultList;

    }

    @RequestMapping(value = "/patienthistory.page")
    public String patientHistory (ModelMap model) {

        String greeting = "Index is being accessed.";
        model.addAttribute("message", greeting);

        logger.debug(greeting);

        return "domain/doctor/patientHistory";
    }

    @RequestMapping(value = "/patientapproval.page")
    public String patientApprovalPage (ModelMap model) {
        return "domain/doctor/patientApproval";
    }

    @RequestMapping(value = "/viewpatients.ajax")
    @ResponseBody
    public List<Map<String,Object>> viewPatients () {
        return doctorService.getPatientList();
    }

    @RequestMapping(value = "/approvePatient.ajax")
    @ResponseBody
    public Map<String,Object> approvePatient (String patient_id) {
        System.out.println("patient id from approval: "+  patient_id);
        boolean result =  doctorService.approvePatient(Integer.valueOf(patient_id));
        Map<String,Object> retMap = new HashMap<String, Object>();
        retMap.put(JsonConstant.SUCCESS,result);
        return retMap;
    }

    @RequestMapping(value = "/rejectPatient.ajax")
    @ResponseBody
    public Map<String,Object> rejectPatient (String patient_id) {
        System.out.println("patient id from reject: "+  patient_id);
        boolean result =  doctorService.rejectPatient(Integer.valueOf(patient_id));
        Map<String,Object> retMap = new HashMap<String, Object>();
        retMap.put(JsonConstant.SUCCESS,result);
        return retMap;
    }

    @RequestMapping(value = "/getDrugs.ajax")
    @ResponseBody
    public Map<String,Object> getDrugs () {
        Map<String,Object> retMap = new HashMap<String, Object>();
        List<Map<String, Object>> list =  doctorService.getDrugs();
        if(list!=null){
            retMap.put(JsonConstant.SUCCESS,true);
            retMap.put("DRUG_LIST",list);
        }else{
            retMap.put(JsonConstant.SUCCESS,false);
            retMap.put(JsonConstant.ERR_MSG,"No drugs retrieved.");
        }
        return retMap;
    }

    @RequestMapping(value = "/getPatients.ajax")
    @ResponseBody
    public Map<String,Object> getPatients () {
        Map<String,Object> retMap = new HashMap<String, Object>();
        List<Map<String, Object>> list =  doctorService.getPatients();
        if(list!=null){
            retMap.put(JsonConstant.SUCCESS,true);
            retMap.put("PATIENT_LIST",list);
        }else{
            retMap.put(JsonConstant.SUCCESS,false);
            retMap.put(JsonConstant.ERR_MSG,"No patients retrieved.");
        }
        return retMap;
    }

    @RequestMapping(value = "/submitPrescription.ajax")
    @ResponseBody
    public Map<String,Object> submitPrescription (@RequestParam Map<String,Object> params) {
        Prescription prescription = new Prescription(params);
        User user = (User)SecurityUtils.getSubject().getSession().getAttribute(SessionAttrKey.USER);
        Map<String, Object> retMap =  doctorService.submitPrescription(prescription,user.getUserId());
        if((Boolean)retMap.get(JsonConstant.SUCCESS))
            retMap.put(JsonConstant.REDIRECT_URL,"patienthistory.page");
        return retMap;
    }

    @RequestMapping(value = "/prescriptionHistory.ajax")
    @ResponseBody
    public Map<String,Object> prescriptionHistory() {
        User user = (User)SecurityUtils.getSubject().getSession().getAttribute(SessionAttrKey.USER);
        Map<String, Object> retMap =  doctorService.prescriptionHistory(user.getUserId());
        return retMap;
    }


}
