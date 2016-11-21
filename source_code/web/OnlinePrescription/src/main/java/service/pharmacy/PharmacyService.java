package service.pharmacy;

import domainModel.pharmacy.Detail;
import domainModel.pharmacy.Order;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Fang on 2/10/2016.
 */
public interface PharmacyService {
    String getUserId();

    Order getOneOrder(String order_id);

    JSONObject getAllStocksJsonData(HttpServletRequest request, String phar_id);

    JSONObject getOrderDetailJsonData(HttpServletRequest request, String order_id);

    int updateOrderStatus(String order_id, String status);

    Detail getOneDetail(String drug_id);

    JSONObject getAllCurrentOrdersJsonData(HttpServletRequest request);

    JSONObject getAllHistoryOrdersJsonData(HttpServletRequest request);

    int getNewOrderCount();


    int updateDrugStore(String drug_id, String value);

    JSONObject getAllDrugsJsonData(HttpServletRequest request);
}
