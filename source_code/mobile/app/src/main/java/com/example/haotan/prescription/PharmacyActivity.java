package com.example.haotan.prescription;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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

public class PharmacyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Select a Pharmacy");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy);
        final ListView lv = (ListView)findViewById(R.id.lvPharmacy);
                final ArrayList<HashMap<String,String>> myListData = new ArrayList<HashMap<String,String>>();
        final List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        final Map<String, String> postParam= new HashMap<String, String>();
        SharedPreferences share = getSharedPreferences(String.valueOf(R.string.user_account_filename), MODE_PRIVATE);
        postParam.put("token", share.getString("token", null));
        postParam.put("username", share.getString("username", null));
        postParam.put("prescription_id", getIntent().getStringExtra("prescriptionid"));
        CustomRequest jsObjRequest = new CustomRequest
                (Request.Method.POST, getString(R.string.url_getpharmacylist), postParam, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("111:" + response.toString());
                        try {
                            if(response.getString("SUCCESS").equals(String.valueOf("true")) == true){
                                JSONArray array = response.getJSONArray("PHARMACY_LIST");
                                for(int j = 0; j < array.length(); j++){
                                    JSONObject lan = array.getJSONObject(j);
                                    Map<String, Object> map = new HashMap<String, Object>();
                                    map.put("pharmacyid", String.valueOf(lan.getInt("PHARMACY_ID")));
                                    map.put("pharmacyname", lan.getString("PHARMACY_NAME"));
                                    map.put("pharmacyaddress", lan.getString("ADDRESS"));
                                    list.add(j,map);
                                    System.out.println(list.toString());
                                }

                                lv.setAdapter(new PharmacylistAdapter(getBaseContext(), list));
                                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        System.out.println("position" + position);
                                        Intent intent = new Intent(getApplicationContext(),OrderDetailActivity.class);
                                        intent.putExtra("Pharmacy",String.valueOf(list.get(position).get("pharmacyname")));
                                        intent.putExtra("prescriptionid",String.valueOf(getIntent().getStringExtra("prescriptionid")));
                                        intent.putExtra("pharmacyid", String.valueOf(list.get(position).get("pharmacyid")));
                                        intent.putExtra("pickdate", getIntent().getStringExtra("pickdate").toString());
                                        intent.putExtra("flag",String.valueOf(3));
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
        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);

    }
}
