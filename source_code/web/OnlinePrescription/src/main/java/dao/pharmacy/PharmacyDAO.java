package dao.pharmacy;


import domainModel.pharmacy.Order;
import domainModel.pharmacy.Stock;
import domainModel.pharmacy.Detail;

import java.util.List;

/**
 * Created by Fang on 2/10/2016.
 */
public interface PharmacyDAO {

    List<Order> getAllCurrentOrdersData(String wherePart, String orderByPart, String rowNumPart);

    int getCurrentOrdersCount(String wherePart);

    int getStocksCount(String s1, String s);

    List<Stock> getAllStocksData(String wherePart, String orderByPart, String rowNumPart, String phar_id);

    int getDetailsCount(String order_id);

    List<Detail> getOrderDetailData(String order_id);

    Order getOneOrder(String order_id);

    int updateOrderStatus(String order_id, String status);

    int getHistoryOrdersCount(String wherePart);

    List<Order> getAllHistoryOrdersData(String wherePart, String orderByPart, String rowNumPart);

    int getNewOrderCount();

    Detail getOneDetail(String drug_id);

    int updateDrugStore(String drug_id, String value);

    int getDrugsCount(String s);

    List<Detail> getAllDrugsData(String wherePart, String orderByPart, String rowNumPart);
}
