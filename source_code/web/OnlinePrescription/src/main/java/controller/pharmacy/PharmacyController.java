package controller.pharmacy;

import domainModel.pharmacy.Detail;
import domainModel.pharmacy.Order;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import service.pharmacy.PharmacyService;

import javax.servlet.http.HttpServletRequest;

import java.util.*;

/**
 * Controller for pharmacy client
 */
@Controller
public class PharmacyController {
    @Autowired
    PharmacyService pharService;

    private static final Logger logger = Logger.getLogger(PharmacyController.class);
    String phar_id = "1";

    @RequestMapping(value = "/pharmacyIndex.page")
    public String pharmacyIndex(ModelMap model) {
        phar_id = pharService.getUserId();
        if(phar_id.equals("-1")){
            return "domain/pharmacy/pharmacyRegister";
        } else {
            model.put("neworder", pharService.getNewOrderCount());
            return "domain/pharmacy/pharmacyIndex";
        }
    }
    @RequestMapping(value = "/checkOrders.page")
    public String checkOrders(ModelMap model) {

        return "domain/pharmacy/pharmacyOrders";
    }
    @RequestMapping(value = "/checkList.page")
    public String checkList(ModelMap model) {

        return "domain/pharmacy/pharmacyList";
    }
    @RequestMapping(value = "/checkHistory.page")
    public String checkHistory(ModelMap model) {

        return "domain/pharmacy/pharmacyHistory";
    }
    @RequestMapping(value="/getAllCurrentOrdersJsonData.ajax")
    @ResponseBody
    public String getAllCurrentOrdersJsonData(HttpServletRequest request){
        logger.info("demo data table called........");
        JSONObject obj = pharService.getAllCurrentOrdersJsonData(request);
        return obj.toString();
    }
    @RequestMapping(value="/getAllDrugsJsonData.ajax")
    @ResponseBody
    public String getAllDrugsJsonData(HttpServletRequest request){
        logger.info("demo data table called........");
        JSONObject obj = pharService.getAllDrugsJsonData(request);
        return obj.toString();
    }

    @RequestMapping(value="/getAllHistoryOrdersJsonData.ajax")
    @ResponseBody
    public String getAllHistoryOrdersJsonData(HttpServletRequest request){
        logger.info("demo data table called........");
        JSONObject obj = pharService.getAllHistoryOrdersJsonData(request);
        return obj.toString();
    }





    @RequestMapping(value = "/Order.page", method = RequestMethod.GET)
    public String Order(@RequestParam("orderId") String order_id, ModelMap model) {
        Order test = pharService.getOneOrder(order_id);
        model.put("hidden1", "hidden");
        model.put("hidden2", "hidden");
        model.put("orderid", test);
        model.put("onum", order_id);
        model.put("onum1", order_id);
        model.put("pname", test.getPat_name());
        model.put("pphone", test.getPat_phone());
        model.put("pmedic", test.getPat_medicnum());
        model.put("dtitle", test.getDoc_title()+" ");
        model.put("dname", test.getDoc_name());
        model.put("dphone", test.getDoc_phone());
        model.put("dhospital", test.getDoc_hospital());
        model.put("dpart", test.getDepartment());
        Map<Integer,String> statusList = new LinkedHashMap<Integer, String>();
        statusList.put(0,"Received");
        statusList.put(1,"Processing");
        statusList.put(2,"Available");
        statusList.put(3,"Finish");
        statusList.put(4,"Hold");
        statusList.put(5,"Cancel");
        model.put("statusList", statusList);
        // TODO get patient details.
        // TODO get doctor details.

        return "domain/pharmacy/Order";
    }

    @RequestMapping(value = "/Order.ajax", method = RequestMethod.POST)
    public String sOrder(@ModelAttribute("orderid") Order order,@RequestParam("action") String action,  @RequestParam
            ("orderId") String order_id, ModelMap model) {
        Order test = pharService.getOneOrder(order_id);
        if( action.equals("Cancel") ){
            return "domain/pharmacy/pharmacyOrders";
        }
        else if( action.equals("Save") ){
            int i = pharService.updateOrderStatus(order_id,order.getStatus());
            if(i == 1){
                model.put("hidden1", "hidden");
            } else {
                model.put("hidden2", "hidden");
            }
            model.put("orderid", test);
            model.put("onum", order_id);
            model.put("onum1", order_id);
            model.put("pname", test.getPat_name());
            model.put("pphone", test.getPat_phone());
            model.put("pmedic", test.getPat_medicnum());
            model.put("dtitle", test.getDoc_title()+" ");
            model.put("dname", test.getDoc_name());
            model.put("dphone", test.getDoc_phone());
            model.put("dhospital", test.getDoc_hospital());
            model.put("dpart", test.getDepartment());
            Map<Integer,String> statusList = new LinkedHashMap<Integer, String>();
            statusList.put(0,"Received");
            statusList.put(1,"Processing");
            statusList.put(2,"Available");
            statusList.put(3,"Finish");
            statusList.put(4,"Hold");
            statusList.put(5,"Cancel");
            model.put("statusList", statusList);
        }
        return "domain/pharmacy/Order";
    }

    @RequestMapping(value = "/Medicine.page", method = RequestMethod.GET)
    public String Medicine(@RequestParam("drug_id") String drug_id, ModelMap model) {
        Detail test = pharService.getOneDetail(drug_id);
        model.put("hidden1", "hidden");
        model.put("hidden2", "hidden");
        model.put("mname", test.getDrug_name());
        model.put("drugid", test);
        model.put("dname", test.getDrug_name());
        model.put("dserial", test.getDrug_serial());
        model.put("dproducer", test.getDrug_producer());
        model.put("dpackage", test.getDrug_package());
        model.put("dprice", test.getDrug_price());
        model.put("dvalue1", test.getValue());
        model.put("dvalue2", test.getValue());
        model.put("drug_id", test.getDrug_id());
        // TODO get patient details.
        // TODO get doctor details.

        return "domain/pharmacy/Medicine";
    }
    @RequestMapping(value = "/Medicine.ajax", method = RequestMethod.POST)
    public String sOrder(@ModelAttribute("drugid") Detail drugid,@RequestParam("action") String action,  @RequestParam
            ("drug_id") String drug_id, ModelMap model) {

        if( action.equals("Cancel") ){
            return "domain/pharmacy/pharmacyStocks";
        }
        else if( action.equals("Recruit") ){
            int i = pharService.updateDrugStore(drug_id,drugid.getValue());
            Detail test = pharService.getOneDetail(drug_id);
            if(i == 1){
                model.put("hidden1", "hidden");
            } else {
                model.put("hidden2", "hidden");
            }
            model.put("mname", test.getDrug_name());
            model.put("drugid", test);
            model.put("dname", test.getDrug_name());
            model.put("dserial", test.getDrug_serial());
            model.put("dproducer", test.getDrug_producer());
            model.put("dpackage", test.getDrug_package());
            model.put("dprice", test.getDrug_price());
            model.put("dvalue1", test.getValue());
            model.put("dvalue2", test.getValue());
            model.put("drug_id", test.getDrug_id());
        }
        return "domain/pharmacy/Medicine";
    }

    @RequestMapping(value="/getOrderDetailJsonData.ajax")
    @ResponseBody
    public String getOrderDetailJsonData(HttpServletRequest request){
        logger.info("Test    data table called........"+request.getParameter("onum"));
        JSONObject obj = pharService.getOrderDetailJsonData(request,request.getParameter("onum"));
        return obj.toString();
    }


    @RequestMapping(value = "/checkStocks.page")
    public String checkStocks(ModelMap model) {
        return "domain/pharmacy/pharmacyStocks";
    }
    @RequestMapping(value="/getAllStocksJsonData.ajax")
    @ResponseBody
    public String getAllStocksJsonData(HttpServletRequest request){
        logger.info("demo data table called........");
        JSONObject obj = pharService.getAllStocksJsonData(request,phar_id);
        return obj.toString();
    }




}
