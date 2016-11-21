package com.example.haotan.prescription;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrescriptionDetailActivity extends AppCompatActivity {

    private ListView listView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_detail);
        setTitle("Prescription Detail");
        listView = (ListView)findViewById(R.id.lvDragList);
        List<Map<String, Object>> list = getData(getIntent().getStringExtra("presno"));
        listView.setAdapter(new DraglistAdapter(this, list));
    }

    public List<Map<String,Object>> getData(String presno) {
        final List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        final Map<String, String> postParam= new HashMap<String, String>();
        SharedPreferences share = getSharedPreferences(String.valueOf(R.string.user_account_filename), MODE_PRIVATE);
        postParam.put("token", share.getString("token", null));
        postParam.put("username", share.getString("username", null));
        postParam.put("prescription_id", presno);
        CustomRequest jsObjRequest = new CustomRequest
                (Request.Method.POST, getString(R.string.url_getprescriptiondetail), postParam, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("111:" + response.toString());
                        try {
                            if(response.getString("SUCCESS").equals(String.valueOf("true")) == true){
                                TextView tvPresno = (TextView)findViewById(R.id.tvPresno);
                                TextView tvDateofpres = (TextView)findViewById(R.id.tvDateofpres);
                                TextView tvRepeattime = (TextView)findViewById(R.id.tvRepeattime);
                                TextView tvRepeatedtims = (TextView)findViewById(R.id.tvRepeatedtims);
                                TextView tvStatus = (TextView)findViewById(R.id.tvStatus);
                                TextView tvDoctor = (TextView)findViewById(R.id.tvDoctor);
                                TextView tvHospital = (TextView)findViewById(R.id.tvHospital);
                                TextView tvTotalPrice = (TextView)findViewById(R.id.tvTotalPrice);
                                TextView tvDuration = (TextView)findViewById(R.id.tvDuration);
                                TextView tvDescription = (TextView)findViewById(R.id.tvDescription);
                                tvPresno.setText(String.valueOf(response.getInt("PRESCRIPTION_ID")));
                                tvDateofpres.setText(response.getString("PRESCRIPTION_DATE"));
                                tvRepeattime.setText(String.valueOf(response.getInt("REPEAT")));
                                tvRepeatedtims.setText(response.getString("ORDERED_REPEAT"));
                                if(response.getInt("STATUS") == 0)
                                    tvStatus.setText("Repeat available");
                                else  if(response.getInt("STATUS") == 1)
                                    tvStatus.setText("Closed");
                                else if(response.getInt("STATUS") == 2)
                                    tvStatus.setText("Not ordered yet");
                                tvDoctor.setText(response.getString("DOCTOR_NAME"));
                                tvHospital.setText(response.getString("HOSPITAL_NAME"));
                                tvTotalPrice.setText(String.valueOf(response.getDouble("TOTAL_PRICE")));
                                tvDuration.setText(String.valueOf(response.getInt("DURATION")));
                                tvDescription.setText(response.getString("DESCRIPTION"));
                                JSONArray array = response.getJSONArray("DRUG_LIST");
                                for(int i = 0; i < array.length(); i++){
                                    JSONObject lan = array.getJSONObject(i);
                                    Map<String, Object> map = new HashMap<String, Object>();
                                    map.put("dragname", lan.getString("DRUG_NAME"));
                                    map.put("dragprod", lan.getString("DRUG_MANUFACTORY"));
                                    map.put("dragprice", "$" + lan.getDouble("PRICE"));
                                    map.put("dragdoes", String.valueOf(lan.getInt("DOSE")));
                                    list.add(i,map);
                                }
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
        return list;
    }
}

