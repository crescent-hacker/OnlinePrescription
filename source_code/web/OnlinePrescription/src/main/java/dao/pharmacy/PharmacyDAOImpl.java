package dao.pharmacy;

import domainModel.pharmacy.Detail;
import domainModel.pharmacy.Order;
import domainModel.pharmacy.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fang on 2/10/2016.
 */
public class PharmacyDAOImpl implements PharmacyDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;



    @Override
    public List<Order> getAllCurrentOrdersData(String wherePart, String orderByPart, String rowNumPart) {
        //System.out.println("demo data dao called........");
        List<Order> list = new ArrayList<Order>();

        String sql2="select * from ( "
                + "select o.id, o.order_id, o.prescription_id, o.order_time, o.status,  @rownum:=@rownum+1 as rownum " +
                "from orders o , (SELECT @rownum:=0) r where o.status in (0,1,2,4) "+
                wherePart+" "+orderByPart+ " ) x "+rowNumPart;
        try{
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql2);

            Order order;
            while(rs.next()){
                order = new Order();
                order.setId(rs.getString("id"));
                order.setOrder_id(rs.getString("order_id"));
                //order.setDoc_id(rs.getString("doc_id"));
                order.setPat_id(rs.getString("prescription_id"));
                //order.setPhar_id(rs.getString("phar_id"));
                order.setOrder_time(rs.getString("order_time"));
                order.setStatus(rs.getString("status"));
                list.add(order);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }



    @Override
    public int getCurrentOrdersCount(String wherePart){
        //System.out.println("demo data count dao called........");
        int count=0;
        String sql= "select count(*) totalCount from orders o where status in (0,1,2,4)"+wherePart;
        try{
            count = jdbcTemplate.queryForInt(sql);
        }catch(Exception e){
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int getStocksCount(String wherePart, String phar_id) {
        //System.out.println("demo data count dao called........");
        int count=0;
        String sql= "select count(*) totalCount from stocks s,drugs d where s.phar_id=" + phar_id + " "+wherePart;
        try{
            count = jdbcTemplate.queryForInt(sql);
        }catch(Exception e){
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public List<Stock> getAllStocksData(String wherePart, String orderByPart, String rowNumPart, String phar_id) {
        //System.out.println("demo data dao called........");
        List<Stock> list = new ArrayList<Stock>();

        String sql2="select * from ( "
                + "select d.id, d.name,d.producer,s.stock, @rownum:=@rownum+1 as rownum from stocks s left join drugs" +
                " d " +
                "on " +
                "s.drug_id = d.id, (SELECT @rownum:=0) r where s.phar_id =" + phar_id + " "+
                wherePart+" "+orderByPart+ " ) x "+rowNumPart;
        try{
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql2);

            Stock stock;
            while(rs.next()){
                stock = new Stock();
                stock.setId(rs.getString("id"));
                stock.setName(rs.getString("name"));
                stock.setProducer(rs.getString("producer"));
                stock.setStock(rs.getString("stock"));

                list.add(stock);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public int getDetailsCount(String order_id) {
        int count=0;
        String sql= "select count(*) totalCount from orders o where o.order_id=" + order_id;
        try{
            count = jdbcTemplate.queryForInt(sql);
        }catch(Exception e){
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public List<Detail> getOrderDetailData(String order_id) {
        List<Detail> list = new ArrayList<Detail>();

        String sql2="select d.name,p.dose,p.description from drugs d left join prescription_drugs p  on p.drug_id=d" +
                ".id left join orders o  on p.prescription_id=o.prescription_id where o.order_id ="+order_id;
        try{
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql2);

            Detail detail;
            while(rs.next()){
                detail = new Detail();
                detail.setDrug_name(rs.getString("name"));
                detail.setValue(rs.getString("dose"));
                detail.setStatus(rs.getString("description"));
                list.add(detail);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Order getOneOrder(String order_id) {
        Order tmp = null;
        String sql2="select p2.first_name,p2.last_name,p2.medicare_no,p2.phone_no, d.* from orders o " +
                "left join prescription_history p1 on o.prescription_id=p1.prescription_id " +
                "left join patient_info p2 on p1.patient_id=p2.patient_id " +
                "left join doctor_info d on p1.doctor_id = d.doctor_id" +
                "    where o.order_id = "+order_id;
        try{
            tmp =  (Order)jdbcTemplate.queryForObject(sql2, new OrderMapper());
            tmp.setOrder_id(order_id);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return tmp;
    }

    @Override
    public int updateOrderStatus(String order_id, String status) {
        String sql2= "UPDATE orders SET status="+ status +" WHERE order_id = " + order_id;
        int i = 0;
        try{
            i= jdbcTemplate.update(sql2);

        }catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public int getHistoryOrdersCount(String wherePart) {
        //System.out.println("demo data count dao called........");
        int count=0;
        String sql= "select count(*) totalCount from orders o,drugs d where status in (3,5)"+wherePart;
        try{
            count = jdbcTemplate.queryForInt(sql);
        }catch(Exception e){
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public List<Order> getAllHistoryOrdersData(String wherePart, String orderByPart, String rowNumPart) {
        //System.out.println("demo data dao called........");
        List<Order> list = new ArrayList<Order>();

        String sql2="select * from ( "
                + "select o.id, o.order_id, o.prescription_id, o.order_time, o.status,  @rownum:=@rownum+1 as rownum " +
                "from orders o , (SELECT @rownum:=0) r where o.status in (3,5) "+
                wherePart+" "+orderByPart+ " ) x "+rowNumPart;
        try{
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql2);

            Order order;
            while(rs.next()){
                order = new Order();
                order.setId(rs.getString("id"));
                order.setOrder_id(rs.getString("order_id"));
                //order.setDoc_id(rs.getString("doc_id"));
                order.setPat_id(rs.getString("prescription_id"));
                //order.setPhar_id(rs.getString("phar_id"));
                order.setOrder_time(rs.getString("order_time"));
                order.setStatus(rs.getString("status"));
                list.add(order);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public int getNewOrderCount() {
        int count=0;
        String sql= "select count(*) totalCount from orders o where status = 0";
        try{
            count = jdbcTemplate.queryForInt(sql);
        }catch(Exception e){
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public Detail getOneDetail(String drug_id) {
        Detail tmp = null;
        String sql2="select d.*,s.stock from drugs d left join stocks s on d.id = s.drug_id where d.id = "+drug_id;
        try{
            tmp =  (Detail)jdbcTemplate.queryForObject(sql2, new DetailMapper());
        }catch (Exception e) {
            e.printStackTrace();
        }
        return tmp;
    }

    @Override
    public int updateDrugStore(String drug_id, String value) {
        String sql2= "UPDATE stocks SET stock="+ value +" WHERE drug_id = " + drug_id;
        int i = 0;
        try{
            i= jdbcTemplate.update(sql2);

        }catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public int getDrugsCount(String s) {
        int count=0;
        String sql= "select count(*) totalCount from drugs d";
        try{
            count = jdbcTemplate.queryForInt(sql);
        }catch(Exception e){
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public List<Detail> getAllDrugsData(String wherePart, String orderByPart, String rowNumPart) {
        List<Detail> list = new ArrayList<Detail>();

        String sql2="select * from ( "
                + "select d.*,  @rownum:=@rownum+1 as rownum " +
                "from drugs d , (SELECT @rownum:=0) r where 1=1 "+
                wherePart+" "+orderByPart+ " ) x "+rowNumPart;
        try{
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql2);

            Detail detail;
            while(rs.next()){
                detail = new Detail();
                detail.setDrug_id(rs.getString("id"));
                detail.setDrug_name(rs.getString("name"));
                detail.setDrug_serial(rs.getString("serial"));
                detail.setDrug_producer(rs.getString("producer"));
                detail.setDrug_package(rs.getString("package"));
                detail.setDrug_price(rs.getString("price"));
                list.add(detail);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public class OrderMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            Order order = new Order();
            order.setPat_name(rs.getString(1)+' '+rs.getString(2));
            order.setPat_medicnum(rs.getString(3));
            order.setPat_phone(rs.getString(4));
            order.setDoc_id(rs.getString(5));
            order.setDoc_title(rs.getString(6));
            order.setDoc_name(rs.getString(7)+' '+rs.getString(8));
            order.setDoc_phone(rs.getString(9));
            order.setDoc_hospital(rs.getString(11));
            order.setDepartment(rs.getString(12));
            return order;
        }
    }

    public class DetailMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            Detail detail = new Detail();
            detail.setDrug_id(rs.getString(1));
            detail.setDrug_name(rs.getString(2));
            detail.setDrug_serial(rs.getString(3));
            detail.setDrug_producer(rs.getString(4));
            detail.setDrug_package(rs.getString(5));
            detail.setDrug_price(rs.getString(6));
            detail.setValue(rs.getString(8));
            return detail;
        }
    }
}
