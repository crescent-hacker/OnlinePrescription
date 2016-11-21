package com.example.haotan.prescription;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.haotan.prescription.MainActivity.*;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;

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

import static android.content.Context.MODE_PRIVATE;


public class MainFragment extends Fragment {
    int listview_height;

    public int getListview_height() {
        return listview_height;
    }

    public void setListview_height(int listview_height) {
        this.listview_height = listview_height;
    }

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            View root = inflater.inflate(R.layout.fragment_main, container, false);
        if(getActivity() != null) {
            final ListView lv = (ListView) root.findViewById(R.id.lv);
            lv.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, listview_height));
            final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            SharedPreferences share = getActivity().getSharedPreferences(String.valueOf(R.string.user_account_filename), MODE_PRIVATE);
            final Map<String, String> postParam = new HashMap<String, String>();

            postParam.put("token", share.getString("token", null));
            postParam.put("username", share.getString("username", null));
            System.out.println(postParam.toString());
            CustomRequest jsObjRequest = new CustomRequest
                    (Request.Method.POST, getString(R.string.url_getprescriptionlist), postParam, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println("111:" + response.toString());
                            try {
                                if (response.getString("SUCCESS").equals(String.valueOf("true")) == true) {
                                    JSONArray array = response.getJSONArray("PRESCRIPTION_LIST");
                                    for (int j = 0; j < array.length(); j++) {
                                        JSONObject lan = array.getJSONObject(j);
                                        Map<String, Object> map = new HashMap<String, Object>();
                                        map.put("presno", lan.getInt("PRESCRIPTION_ID"));
                                        map.put("presdate", lan.getString("PRESCRIPTION_DATE"));
                                        int status = lan.getInt("STATUS");
                                        if (status == 0)
                                            map.put("presstatus", Color.YELLOW);
                                        else if (status == 1)
                                            map.put("presstatus", Color.RED);
                                        else if (status == 2)
                                            map.put("presstatus", Color.GREEN);
                                        map.put("presbriefdescribtion", lan.getString("BRIEF_DESC"));
                                        list.add(j, map);
                                    }
                                    System.out.println("list" + list);
                                    if(getActivity() != null){
                                        lv.setAdapter(new PrescriptionListAdapter(getActivity().getBaseContext(), list));
                                    }

                                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            Intent intent = new Intent(getContext(), PrescriptionDetailActivity.class);
                                            intent.putExtra("presno", String.valueOf(list.get(position).get("presno")));
                                            startActivity(intent);
                                        }
                                    });
                                } else {

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


            System.out.println(list);
        }
        return root;
    }
}
