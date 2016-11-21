package service.pharmacy;

import dao.pharmacy.PharmacyDAO;
import domainModel.pharmacy.Detail;
import domainModel.pharmacy.Stock;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import domainModel.pharmacy.Order;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Fang on 2/10/2016.
 */
public class PharmacyServiceImpl implements PharmacyService {
    @Autowired
    PharmacyDAO pharmacyDAO;
    @Override
    public String getUserId() {
    //TODO
        return "1";
    }

    @Override
    public Order getOneOrder(String order_id) {
        Order tmp = pharmacyDAO.getOneOrder(order_id);
        return tmp;
    }
    @Override
    public Detail getOneDetail(String drug_id) {
        Detail tmp = pharmacyDAO.getOneDetail(drug_id);
        return tmp;
    }

    @Override
    public JSONObject getAllCurrentOrdersJsonData(HttpServletRequest request) {
        //System.out.println("demo data service called........");
        JSONObject obj=new JSONObject();

        String[] columnSearch = {"o.id", "o.order_id", "o.prescription_id", "o.order_time", "o.status"};
        String[] columnSort = {"o.id", "o.order_id", "o.prescription_id", "o.order_time", "o.status"};

        String wherePart="", orderByPart="", rowNumPart="";

        //for datatable internal
        String startStr = request.getParameter("iDisplayStart");
        int start = -1;
        if(startStr != null)
            start = Integer.parseInt(startStr);
        System.out.println(request.getParameter("iDisplayLength"));
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

        //total records
        int echo = Integer.parseInt(request.getParameter("sEcho"));
        int totalRecords = 0;
        totalRecords = pharmacyDAO.getCurrentOrdersCount("");
        obj.put("sEcho",new Integer(echo));
        obj.put("iTotalRecords",new Integer(totalRecords));

        JSONArray parentObj = new JSONArray();
        JSONArray childList = null;

        //display data
        List<Order> list = pharmacyDAO.getAllCurrentOrdersData(wherePart, orderByPart, rowNumPart);
        for(int i=0; i<list.size(); i++){
            childList = new JSONArray();
            childList.put(list.get(i).getId());
            childList.put(list.get(i).getOrder_id());
            //childList.put(list.get(i).getDoc_id());
            //childList.put(list.get(i).getPat_id());
            childList.put(list.get(i).getOrder_time());
            childList.put(list.get(i).getStatus());
            parentObj.put(childList);
        }

        obj.put("aaData", parentObj);

        //filtered records
        int totalFilteredRecords = 0;
        if(!wherePart.equals("") ){
            totalFilteredRecords = pharmacyDAO.getCurrentOrdersCount(wherePart);
        }else{
            totalFilteredRecords = totalRecords;
        }
        obj.put("iTotalDisplayRecords",new Integer(totalFilteredRecords));

        return obj;
    }

    @Override
    public JSONObject getAllHistoryOrdersJsonData(HttpServletRequest request) {
        //System.out.println("demo data service called........");
        JSONObject obj=new JSONObject();


        String[] columnSearch = {"o.id", "o.order_id", "o.prescription_id", "o.order_time", "o.status"};

        String[] columnSort = {"o.id", "o.order_id", "o.prescription_id", "o.order_time", "o.status"};

        String wherePart="", orderByPart="", rowNumPart="";

        //for datatable internal
        String startStr = request.getParameter("iDisplayStart");
        int start = -1;
        if(startStr != null)
            start = Integer.parseInt(startStr);
        System.out.println(request.getParameter("iDisplayLength"));
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

        //total records
        int echo = Integer.parseInt(request.getParameter("sEcho"));
        int totalRecords = 0;
        totalRecords = pharmacyDAO.getHistoryOrdersCount("");
        obj.put("sEcho",new Integer(echo));
        obj.put("iTotalRecords",new Integer(totalRecords));

        JSONArray parentObj = new JSONArray();
        JSONArray childList = null;

        //display data
        List<Order> list = pharmacyDAO.getAllHistoryOrdersData(wherePart, orderByPart, rowNumPart);
        for(int i=0; i<list.size(); i++){
            childList = new JSONArray();
            childList.put(list.get(i).getId());
            childList.put(list.get(i).getOrder_id());
            //childList.put(list.get(i).getDoc_id());
            //childList.put(list.get(i).getPat_id());
            childList.put(list.get(i).getOrder_time());
            childList.put(list.get(i).getStatus());
            parentObj.put(childList);
        }

        obj.put("aaData", parentObj);

        //filtered records
        int totalFilteredRecords = 0;
        if(!wherePart.equals("") ){
            totalFilteredRecords = pharmacyDAO.getHistoryOrdersCount(wherePart);
        }else{
            totalFilteredRecords = totalRecords;
        }
        obj.put("iTotalDisplayRecords",new Integer(totalFilteredRecords));

        return obj;
    }

    @Override
    public int getNewOrderCount() {
        int i = pharmacyDAO.getNewOrderCount();
        return i;
    }

    @Override
    public int updateDrugStore(String drug_id, String value) {
        int i = pharmacyDAO.updateDrugStore(drug_id, value);
        return i;
    }

    @Override
    public JSONObject getAllDrugsJsonData(HttpServletRequest request) {
        JSONObject obj=new JSONObject();


        String[] columnSearch = {"d.name", "d.serial","d.producer"};

        String[] columnSort = {"d.name", "d.serial","d.producer"};

        String wherePart="", orderByPart="", rowNumPart="";

        //for datatable internal
        String startStr = request.getParameter("iDisplayStart");
        int start = -1;
        if(startStr != null)
            start = Integer.parseInt(startStr);
        System.out.println(request.getParameter("iDisplayLength"));
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

        //total records
        int echo = Integer.parseInt(request.getParameter("sEcho"));
        int totalRecords = 0;
        totalRecords = pharmacyDAO.getDrugsCount("");
        obj.put("sEcho",new Integer(echo));
        obj.put("iTotalRecords",new Integer(totalRecords));

        JSONArray parentObj = new JSONArray();
        JSONArray childList = null;

        //display data
        List<Detail> list = pharmacyDAO.getAllDrugsData(wherePart, orderByPart, rowNumPart);
        for(int i=0; i<list.size(); i++){
            childList = new JSONArray();
            childList.put(list.get(i).getDrug_id());
            childList.put(list.get(i).getDrug_name());
            childList.put(list.get(i).getDrug_serial());
            //childList.put(list.get(i).getPat_id());
            childList.put(list.get(i).getDrug_producer());
            childList.put(list.get(i).getDrug_package());
            childList.put(list.get(i).getDrug_price());
            parentObj.put(childList);
        }

        obj.put("aaData", parentObj);

        //filtered records
        int totalFilteredRecords = 0;
        if(!wherePart.equals("") ){
            totalFilteredRecords = pharmacyDAO.getDrugsCount(wherePart);
        }else{
            totalFilteredRecords = totalRecords;
        }
        obj.put("iTotalDisplayRecords",new Integer(totalFilteredRecords));

        return obj;
    }


    @Override
    public JSONObject getAllStocksJsonData(HttpServletRequest request, String phar_id) {
        JSONObject obj=new JSONObject();


        String[] columnSearch = {"d.name", "d.producer","s.stock"};

        String[] columnSort = {"d.name", "d.producer","s.stock"};

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

        //total records
        int echo = Integer.parseInt(request.getParameter("sEcho"));
        int totalRecords = 0;
        totalRecords = pharmacyDAO.getStocksCount("",phar_id);
        obj.put("sEcho",new Integer(echo));
        obj.put("iTotalRecords",new Integer(totalRecords));

        JSONArray parentObj = new JSONArray();
        JSONArray childList = null;

        //display data
        List<Stock> list = pharmacyDAO.getAllStocksData(wherePart, orderByPart, rowNumPart, phar_id);
        for(int i=0; i<list.size(); i++){
            childList = new JSONArray();
            childList.put(list.get(i).getId());
            childList.put(list.get(i).getName());
            childList.put(list.get(i).getProducer());
            childList.put(list.get(i).getStock());
            parentObj.put(childList);
        }

        obj.put("aaData", parentObj);

        //filtered records
        int totalFilteredRecords = 0;
        if(!wherePart.equals("") ){
            totalFilteredRecords = pharmacyDAO.getStocksCount("", wherePart);
        }else{
            totalFilteredRecords = totalRecords;
        }
        obj.put("iTotalDisplayRecords",new Integer(totalFilteredRecords));

        return obj;
    }

    @Override
    public JSONObject getOrderDetailJsonData(HttpServletRequest request, String order_id) {
        JSONObject obj=new JSONObject();

        //total records
        int echo = Integer.parseInt(request.getParameter("sEcho"));
        int totalRecords = 0;
        totalRecords = pharmacyDAO.getDetailsCount(order_id);
        obj.put("sEcho",new Integer(echo));
        obj.put("iTotalRecords",new Integer(totalRecords));

        JSONArray parentObj = new JSONArray();
        JSONArray childList = null;

        //display data
        List<Detail> list = pharmacyDAO.getOrderDetailData(order_id);
        for(int i=0; i<list.size(); i++){
            childList = new JSONArray();
            childList.put(i+1);

            childList.put(list.get(i).getDrug_name());
            childList.put(list.get(i).getValue());
            childList.put(list.get(i).getStatus());
            parentObj.put(childList);
        }

        obj.put("aaData", parentObj);

        obj.put("iTotalDisplayRecords",new Integer(totalRecords));

        return obj;
    }

    @Override
    public int updateOrderStatus(String order_id, String status) {
        int i = pharmacyDAO.updateOrderStatus(order_id, status);

        return i;
    }
}
