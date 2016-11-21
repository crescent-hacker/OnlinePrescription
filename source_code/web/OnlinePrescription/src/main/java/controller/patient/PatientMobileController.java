package controller.patient;

import domainModel.system.JsonConstant;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.patient.IPatientService;
import service.system.ILoginService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/mobile")
public class PatientMobileController {
    @Autowired
    ILoginService loginService;
    @Autowired
    IPatientService patientService;

    private static final Logger logger = Logger.getLogger(controller.system.UserController.class);


    @RequestMapping(value = "/getPrescriptionList.ajax")
    @ResponseBody
    public Map<String, Object> getPrescriptionList(String username, String token) throws Exception {
        Map<String, Object> retMap = new HashMap<String, Object>();
        logger.info("getPrescriptionList|"+username+"|"+token);
        //check token
        if (!loginService.verifyToken(username, token)) {
            retMap.put(JsonConstant.SUCCESS, false);
            retMap.put(JsonConstant.ERR_MSG, "Token invalid.");
            logger.info(new ObjectMapper().writeValueAsString(retMap));
            return retMap;
        }
        //retrieve prescription list
        List<Map<String, Object>> prescriptionList = patientService.getPrescriptionList(username);
        if(prescriptionList==null){
            retMap.put(JsonConstant.SUCCESS, false);
            return retMap;
        }
        retMap.put("PRESCRIPTION_LIST", prescriptionList);
        retMap.put(JsonConstant.SUCCESS, true);
        logger.info(new ObjectMapper().writeValueAsString(retMap));
        return retMap;
    }

    @RequestMapping(value = "/getPrescriptionDetail.ajax")
    @ResponseBody
    public Map<String, Object> getPrescriptionDetail(String username, String token, String prescription_id) throws Exception{
        //check token
        if (!loginService.verifyToken(username, token)) {
            Map<String, Object> retMap = new HashMap<String, Object>();
            retMap.put(JsonConstant.SUCCESS, false);
            retMap.put(JsonConstant.ERR_MSG, "Token invalid.");
            return retMap;
        }
        //retrieve prescription detail by id
        int prescriptionId = Integer.valueOf(prescription_id);
        Map<String, Object> retMap = patientService.getPrescriptionDetail(username,prescriptionId);
        if(retMap == null){
            retMap=new HashMap<String, Object>();
            retMap.put(JsonConstant.SUCCESS,false);
            return retMap;
        }
        retMap.put(JsonConstant.SUCCESS,true);

        return retMap;
    }

    @RequestMapping(value = "/getPharmacyList.ajax")
    @ResponseBody
    public Map<String, Object> getPharmacyList(String username, String token) throws Exception{
        Map<String, Object> retMap = new HashMap<String, Object>();
        //check token
        if (!loginService.verifyToken(username, token)) {
            retMap.put(JsonConstant.SUCCESS, false);
            retMap.put(JsonConstant.ERR_MSG, "Token invalid.");
            return retMap;
        }
        //retrieve pharmacy list
        List<Map<String,Object>> pharmacyList = patientService.getPharmacyList();
        if(pharmacyList == null){
            retMap.put(JsonConstant.SUCCESS,false);
            return retMap;
        }
        retMap.put("PHARMACY_LIST",pharmacyList);
        retMap.put(JsonConstant.SUCCESS,true);

        return retMap;
    }

    @RequestMapping(value = "/getUntreatedOrderList.ajax")
    @ResponseBody
    public Map<String, Object> getUntreatedOrderList(String username, String token) throws Exception{
        Map<String, Object> retMap = new HashMap<String, Object>();
        //check token
        if (!loginService.verifyToken(username, token)) {
            retMap.put(JsonConstant.SUCCESS, false);
            retMap.put(JsonConstant.ERR_MSG, "Token invalid.");
            return retMap;
        }
        //retrieve pharmacy list
        List<Map<String,Object>> uOrderList = patientService.getUntreatedOrderList(username);
        if(uOrderList == null){
            retMap.put(JsonConstant.SUCCESS,false);
            return retMap;
        }
        retMap.put("UNTREATED_ORDER_LIST",uOrderList);
        retMap.put(JsonConstant.SUCCESS,true);

        return retMap;
    }

    @RequestMapping(value = "/order.ajax")
    @ResponseBody
    public Map<String, Object> order(String username, String token, String prescription_id,String pharmacy_id,String pick_time) throws Exception{
        Map<String, Object> retMap = new HashMap<String, Object>();
        //check token
        if (!loginService.verifyToken(username, token)) {
            retMap.put(JsonConstant.SUCCESS, false);
            retMap.put(JsonConstant.ERR_MSG, "Token invalid.");
            return retMap;
        }
        //order
        boolean result = patientService.order(username,prescription_id,pharmacy_id,pick_time);
        retMap.put(JsonConstant.SUCCESS,result);

        return retMap;
    }

    @RequestMapping(value = "/getOrderList.ajax")
    @ResponseBody
    public Map<String, Object> getOrderList(String username, String token, String order_status_filter) throws Exception{
        Map<String, Object> retMap = new HashMap<String, Object>();
        //check token
        if (!loginService.verifyToken(username, token)) {
            retMap.put(JsonConstant.SUCCESS, false);
            retMap.put(JsonConstant.ERR_MSG, "Token invalid.");
            return retMap;
        }
        //order list
        List<Map<String,Object>> list = patientService.getOrderList(username,order_status_filter);
        if(list == null){
            retMap.put(JsonConstant.SUCCESS,false);
            return retMap;
        }
        retMap.put("ORDER_LIST",list);
        retMap.put(JsonConstant.SUCCESS,true);

        return retMap;
    }

    @RequestMapping(value = "/getOrderDetail.ajax")
    @ResponseBody
    public Map<String, Object> getOrderDetail(String username, String token, String order_id) throws Exception{
        Map<String, Object> retMap = new HashMap<String, Object>();
        //check token
        if (!loginService.verifyToken(username, token)) {
            retMap.put(JsonConstant.SUCCESS, false);
            retMap.put(JsonConstant.ERR_MSG, "Token invalid.");
            return retMap;
        }

        //order detail
        retMap = patientService.getOrderDetail(username,order_id);
        if(retMap == null){
            retMap=new HashMap<String, Object>();
            retMap.put(JsonConstant.SUCCESS,false);
        }
        retMap.put(JsonConstant.SUCCESS,true);
        return retMap;
    }

    @RequestMapping(value = "/viewProfile.ajax")
    @ResponseBody
    public Map<String, Object> viewProfile(String username, String token) throws Exception{
        Map<String, Object> retMap = new HashMap<String, Object>();
        //check token
        if (!loginService.verifyToken(username, token)) {
            retMap.put(JsonConstant.SUCCESS, false);
            retMap.put(JsonConstant.ERR_MSG, "Token invalid.");
            return retMap;
        }
        //order detail
        retMap = patientService.viewProfile(username);
        if(retMap == null){
            retMap=new HashMap<String, Object>();
            retMap.put(JsonConstant.SUCCESS,false);
        }
        retMap.put(JsonConstant.SUCCESS,true);
        return retMap;
    }

    @RequestMapping(value = "/editProfile.ajax")
    @ResponseBody
    public Map<String, Object> editProfile(String username, String token,String phone_no,String email) throws Exception{
        Map<String, Object> retMap = new HashMap<String, Object>();
        //check token
        if (!loginService.verifyToken(username, token)) {
            retMap.put(JsonConstant.SUCCESS, false);
            retMap.put(JsonConstant.ERR_MSG, "Token invalid.");
            return retMap;
        }
        //order detail
        boolean result = patientService.editProfile(username,phone_no,email);
        retMap.put(JsonConstant.SUCCESS,result);
        return retMap;
    }

    @RequestMapping(value = "/getPharmacyDetail.ajax")
    @ResponseBody
    public Map<String, Object> getPharmacyDetail(String username, String token,String pharmacy_id) throws Exception{
        //check token
        if (!loginService.verifyToken(username, token)) {
            Map<String, Object> retMap = new HashMap<String, Object>();
            retMap.put(JsonConstant.SUCCESS, false);
            retMap.put(JsonConstant.ERR_MSG, "Token invalid.");
            return retMap;
        }
        //phamacy detail
        int id = Integer.valueOf(pharmacy_id);
        Map<String,Object> retMap = patientService.getPharmacyDetail(id);
        if(retMap == null){
            retMap=new HashMap<String, Object>();
            retMap.put(JsonConstant.SUCCESS,false);
        }
        retMap.put(JsonConstant.SUCCESS,true);
        return retMap;
    }


















}
