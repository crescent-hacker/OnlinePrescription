package com.example.haotan.prescription;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by Hao Tan on 10/21/2016.
 */

public class OrderlistAdapter extends BaseAdapter {
    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public OrderlistAdapter(Context context, List<Map<String, Object>> data){
        this.context = context;
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public final class OrderList{
        public TextView orderid;
        public TextView orderdate;
        public TextView orderprice;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OrderList orderList = null;
        if(convertView == null){
            orderList = new OrderList();
            convertView = layoutInflater.inflate(R.layout.orderlist, null);
            orderList.orderid = (TextView)convertView.findViewById(R.id.tvOrderid);
            orderList.orderdate = (TextView)convertView.findViewById(R.id.tvOrderdate);
            orderList.orderprice = (TextView)convertView.findViewById(R.id.tvOrderprice);
            convertView.setTag(orderList);
        }
        else{
            orderList = (OrderList)convertView.getTag();
        }
        orderList.orderid.setText((String)data.get(position).get("orderid"));
        orderList.orderdate.setText((String)data.get(position).get("orderdate"));
        orderList.orderprice.setText((String)data.get(position).get("orderprice"));

        return convertView;
    }
}
