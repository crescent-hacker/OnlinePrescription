package com.example.haotan.prescription;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class OrderListFragment extends Fragment {
    int flag;
    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public OrderListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    final String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry","WebOS","Ubuntu","Windows7","Max OS X","Android","IPhone","WindowsMobile","Blackberry","WebOS","Ubuntu","Windows7","Max OS X"};
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_order_list, container, false);
        final ListView lv = (ListView)root.findViewById(R.id.lvOrderList);
//        lv.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 2016));
        final List<Map<String, Object>> list = getData(flag, lv);
//        lv.setAdapter(new OrderlistAdapter(getContext(), list));
        //flag == 3 means the customer has selected a pharmacy and will place the order.
        SharedPreferences share = getActivity().getSharedPreferences(String.valueOf(R.string.user_account_filename), MODE_PRIVATE);
        int height = (int)share.getFloat("formHeight", -1);
        System.out.println(height);
//        container.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height));

        //refresh gesture action
        final SwipeRefreshLayout mySwipeRefreshLayout = (SwipeRefreshLayout)root.findViewById(R.id.swiperefresh);
        mySwipeRefreshLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.

                        final List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
                        SharedPreferences share = getActivity().getSharedPreferences(String.valueOf(R.string.user_account_filename), MODE_PRIVATE);
                        if(flag == 0){
                            final Map<String, String> postParam= new HashMap<String, String>();
                            postParam.put("token", share.getString("token", null));
                            postParam.put("username", share.getString("username", null));
                            CustomRequest jsObjRequest = new CustomRequest
                                    (Request.Method.POST, getString(R.string.url_getuntreatedorderlist), postParam, new Response.Listener<JSONObject>() {

                                        @Override
                                        public void onResponse(JSONObject response) {
                                            System.out.println("111:" + response.toString());
                                            try {
                                                if(response.getString("SUCCESS").equals(String.valueOf("true")) == true){
                                                    JSONArray array = response.getJSONArray("UNTREATED_ORDER_LIST");
                                                    for(int j = 0; j < array.length(); j++){
                                                        JSONObject lan = array.getJSONObject(j);
                                                        Map<String, Object> map = new HashMap<String, Object>();
                                                        map.put("presno", String.valueOf(lan.getInt("PRESCRIPTION_ID")));
                                                        map.put("presdate", lan.getString("PRESCRIPTION_DATE"));
                                                        map.put("presdoctor", lan.getString("DOCTOR_NAME") + "--" + lan.getString("HOSPITAL"));
                                                        list.add(j,map);
                                                    }
                                                    lv.setAdapter(new UntreatedOrderlistAdapter(getContext(), list));
                                                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                        @Override
                                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                            Intent intent = new Intent(getContext(),OrderDetailActivity.class);
                                                            intent.putExtra("prescriptionid",String.valueOf(list.get(position).get("presno")));
                                                            if(flag == 1 || flag ==2)
                                                                intent.putExtra("orderid", String.valueOf(list.get(position).get("orderid")));
                                                            intent.putExtra("flag",String.valueOf(flag));
                                                            startActivity(intent);
                                                        }
                                                    });
                                                    Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();
                                                    mySwipeRefreshLayout.setRefreshing(false);

                                                }
                                                else {

                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {

                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                            // TODO Auto-generated method stub
                                            System.out.println("ERROR");
                                        }
                                    });
                            jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                            MySingleton.getInstance(getContext()).addToRequestQueue(jsObjRequest);
                        }
                        else if(flag == 1 || flag == 2){
                            final Map<String, String> postParam= new HashMap<String, String>();
                            postParam.put("token", share.getString("token", null));
                            postParam.put("username", share.getString("username", null));
                            if(flag == 1)
                                postParam.put("order_status_filter", String.valueOf(0));
                            else if(flag == 2)
                                postParam.put("order_status_filter", String.valueOf(1));
                            CustomRequest jsObjRequest = new CustomRequest
                                    (Request.Method.POST, getString(R.string.url_getorderlist), postParam, new Response.Listener<JSONObject>() {

                                        @Override
                                        public void onResponse(JSONObject response) {
                                            System.out.println("111:" + response.toString());
                                            try {
                                                if(response.getString("SUCCESS").equals(String.valueOf("true")) == true){
                                                    JSONArray array = response.getJSONArray("ORDER_LIST");
                                                    for(int j = 0; j < array.length(); j++){
                                                        JSONObject lan = array.getJSONObject(j);
                                                        Map<String, Object> map = new HashMap<String, Object>();
                                                        map.put("orderid", lan.getString("ORDER_ID"));
                                                        map.put("orderdate", lan.getString("ORDER_TIME"));
                                                        map.put("orderprice", String.valueOf(lan.getDouble("TOTAL_PRICE")));
                                                        list.add(j,map);
                                                    }
                                                    lv.setAdapter(new OrderlistAdapter(getContext(), list));
                                                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                        @Override
                                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                            Intent intent = new Intent(getContext(),OrderDetailActivity.class);
                                                            intent.putExtra("prescriptionid",String.valueOf(list.get(position).get("presno")));
                                                            if(flag == 1 || flag ==2)
                                                                intent.putExtra("orderid", String.valueOf(list.get(position).get("orderid")));
                                                            intent.putExtra("flag",String.valueOf(flag));
                                                            startActivity(intent);
                                                        }
                                                    });
                                                    mySwipeRefreshLayout.setRefreshing(false);
                                                    Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();
                                                }
                                                else {

                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {

                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                            // TODO Auto-generated method stub
                                            System.out.println("ERROR");
                                        }
                                    });
                            jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                            MySingleton.getInstance(getContext()).addToRequestQueue(jsObjRequest);
                        }
                    }
                }
        );
        return root;
    }

    private List<Map<String,Object>> getData(final int flag, final ListView lv) {
        final List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        SharedPreferences share = getActivity().getSharedPreferences(String.valueOf(R.string.user_account_filename), MODE_PRIVATE);
        if(flag == 0){
            final Map<String, String> postParam= new HashMap<String, String>();
            postParam.put("token", share.getString("token", null));
            postParam.put("username", share.getString("username", null));
            CustomRequest jsObjRequest = new CustomRequest
                    (Request.Method.POST, getString(R.string.url_getuntreatedorderlist), postParam, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println("111:" + response.toString());
                            try {
                                if(response.getString("SUCCESS").equals(String.valueOf("true")) == true){
                                    JSONArray array = response.getJSONArray("UNTREATED_ORDER_LIST");
                                    for(int j = 0; j < array.length(); j++){
                                        JSONObject lan = array.getJSONObject(j);
                                        Map<String, Object> map = new HashMap<String, Object>();
                                        map.put("presno", String.valueOf(lan.getInt("PRESCRIPTION_ID")));
                                        map.put("presdate", lan.getString("PRESCRIPTION_DATE"));
                                        map.put("presdoctor", lan.getString("DOCTOR_NAME") + "--" + lan.getString("HOSPITAL"));
                                        list.add(j,map);
                                    }
                                    if(getActivity() != null)
                                        lv.setAdapter(new UntreatedOrderlistAdapter(getActivity().getBaseContext(), list));
                                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            Intent intent = new Intent(getContext(),OrderDetailActivity.class);
                                            intent.putExtra("prescriptionid",String.valueOf(list.get(position).get("presno")));
                                            if(flag == 1 || flag ==2)
                                                intent.putExtra("orderid", String.valueOf(list.get(position).get("orderid")));
                                            intent.putExtra("flag",String.valueOf(flag));
                                            startActivity(intent);
                                        }
                                    });
                                }
                                else {

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {

                            // TODO Auto-generated method stub
                            System.out.println("ERROR");
                        }
                    });
            jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getInstance(getContext()).addToRequestQueue(jsObjRequest);
        }
        else if(flag == 1 || flag == 2){
            final Map<String, String> postParam= new HashMap<String, String>();
            postParam.put("token", share.getString("token", null));
            postParam.put("username", share.getString("username", null));
            if(flag == 1)
                postParam.put("order_status_filter", String.valueOf(0));
            else if(flag == 2)
                postParam.put("order_status_filter", String.valueOf(1));
            CustomRequest jsObjRequest = new CustomRequest
                    (Request.Method.POST, getString(R.string.url_getorderlist), postParam, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println("111:" + response.toString());
                            try {
                                if(response.getString("SUCCESS").equals(String.valueOf("true")) == true){
                                    JSONArray array = response.getJSONArray("ORDER_LIST");
                                    for(int j = 0; j < array.length(); j++){
                                        JSONObject lan = array.getJSONObject(j);
                                        Map<String, Object> map = new HashMap<String, Object>();
                                        map.put("orderid", lan.getString("ORDER_ID"));
                                        map.put("orderdate", lan.getString("ORDER_TIME"));
                                        map.put("orderprice", String.valueOf(lan.getDouble("TOTAL_PRICE")));
                                        list.add(j,map);
                                    }
                                    if(getActivity() != null)
                                        lv.setAdapter(new OrderlistAdapter(getActivity().getBaseContext(), list));
                                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            Intent intent = new Intent(getContext(),OrderDetailActivity.class);
                                            intent.putExtra("prescriptionid",String.valueOf(list.get(position).get("presno")));
                                            if(flag == 1 || flag ==2)
                                                intent.putExtra("orderid", String.valueOf(list.get(position).get("orderid")));
                                            intent.putExtra("flag",String.valueOf(flag));
                                            startActivity(intent);
                                        }
                                    });
                                }
                                else {

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {

                            // TODO Auto-generated method stub
                            System.out.println("ERROR");
                        }
                    });
            jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getInstance(getContext()).addToRequestQueue(jsObjRequest);
        }
        return list;
    }

    @Override
    public void onResume() {
        System.out.println("fragment onResume");
        super.onResume();
    }
}
