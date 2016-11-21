package com.example.haotan.prescription;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLogTags;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDetailActivity extends AppCompatActivity {
    private String pharmacyname;
    private String pharmacyaddress;
    private String pharmacyid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        setTitle("Order Details");
        Button btnSelectPharmacy = (Button)findViewById(R.id.btnSelectPharmacy);
        final Button btnPlaceOrder = (Button)findViewById(R.id.btnPlaceOrder);
        Button btnCheckPres = (Button)findViewById(R.id.btnCheckPres);
        Button btnSetPickdate = (Button)findViewById(R.id.btnPickdate);

        final TextView tvPresid = (TextView)findViewById(R.id.tvPresid);
        final TextView tvSelePhar = (TextView)findViewById(R.id.tvSelePhar);
        TextView tvPrice = (TextView)findViewById(R.id.tvPrice);
        TextView tvDate = (TextView)findViewById(R.id.tvDateofpres2);
        final TextView tvPickdate = (TextView)findViewById(R.id.tvPickdate);

        TextView tv10 = (TextView)findViewById(R.id.textView10);
        TextView tv16 = (TextView)findViewById(R.id.textView16);
        TextView tv21 = (TextView)findViewById(R.id.textView21);
        final int flag = Integer.valueOf(getIntent().getStringExtra("flag"));
        if(flag == 1 || flag == 2){
            btnSelectPharmacy.setText("Map");
            btnPlaceOrder.setVisibility(View.INVISIBLE);
            btnSetPickdate.setVisibility(View.INVISIBLE);
            displayDecriptionDetail(flag);
        }
        else if(flag == 0){//has not selected a pharmacy yet in Untreated order list
            btnPlaceOrder.setEnabled(false);
            tvPrice.setVisibility(View.INVISIBLE);
            tvDate.setVisibility(View.INVISIBLE);
            tv16.setVisibility(View.INVISIBLE);
            tv21.setVisibility(View.INVISIBLE);
            displayDecriptionDetail(flag);
        }
        else if(flag == 3){//has selected pharmacy in Untreated order list
            tvPrice.setVisibility(View.INVISIBLE);
            tvDate.setVisibility(View.INVISIBLE);
            tv16.setVisibility(View.INVISIBLE);
            tv21.setVisibility(View.INVISIBLE);
            tvSelePhar.setText(getIntent().getStringExtra("Pharmacy"));
            tvPresid.setText(getIntent().getStringExtra("prescriptionid"));
            if(getIntent().getStringExtra("pickdate").toString().equals("")){
                btnPlaceOrder.setEnabled(false);
            }
            else{
                tvPickdate.setText(getIntent().getStringExtra("pickdate"));
            }

        }

        btnSelectPharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == 0 || flag == 3){
                    Intent intent = new Intent(getBaseContext(), PharmacyActivity.class);
                    intent.putExtra("prescriptionid",getIntent().getStringExtra("prescriptionid").toString());
                    intent.putExtra("pickdate", tvPickdate.getText().toString());
                    startActivity(intent);
                }
                else if(flag == 1 || flag == 2){
                    Intent intent = new Intent(v.getContext(), PharmacymapsActivity.class);
                    intent.putExtra("pharmacyid", pharmacyid);
                    intent.putExtra("pharmacyname", pharmacyname);
                    intent.putExtra("pharmacyaddress", pharmacyaddress);
                    v.getContext().startActivity(intent);
                }

            }
        });

        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Map<String, String> postParam= new HashMap<String, String>();
                SharedPreferences share = getSharedPreferences(String.valueOf(R.string.user_account_filename), MODE_PRIVATE);
                postParam.put("token", share.getString("token", null));
                postParam.put("username", share.getString("username", null));
                postParam.put("pharmacy_id", getIntent().getStringExtra("pharmacyid"));
                postParam.put("prescription_id", getIntent().getStringExtra("prescriptionid"));
                String datetime = tvPickdate.getText().toString();
                postParam.put("pick_time", datetime);
                System.out.println("pick_time" + datetime);
                CustomRequest jsObjRequest = new CustomRequest
                        (Request.Method.POST, getString(R.string.url_order), postParam, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                System.out.println("111:" + response.toString());
                                try {
                                    if(response.getString("SUCCESS").equals(String.valueOf("true")) == true){
                                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                                        intent.putExtra("tab_position", 2);
                                        startActivity(intent);
                                        Toast.makeText(getApplicationContext(),"Order Placed",Toast.LENGTH_LONG).show();
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
                MySingleton.getInstance(v.getContext()).addToRequestQueue(jsObjRequest);
            }
        });

        btnCheckPres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PrescriptionDetailActivity.class);
                intent.putExtra("presno", tvPresid.getText());
                startActivity(intent);
            }
        });

        btnSetPickdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {
                        TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(final TimePicker view, int hourOfDay, int minute) {
                                tvPickdate.setText(String.valueOf(year) + "-" + String.valueOf(month + 1) + "-" + String.valueOf(dayOfMonth) + " " + String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
                                if(flag == 3)
                                    btnPlaceOrder.setEnabled(true);

                            }
                        }, 0, 0, true);
                        timePickerDialog.show();
                    }
                }, 2016, 10, 10);
                datePickerDialog.show();
            }
        });
    }

    private void displayDecriptionDetail(int flag) {
        final TextView tvPresid = (TextView)findViewById(R.id.tvPresid);
        final TextView tvSelePhar = (TextView)findViewById(R.id.tvSelePhar);
        final TextView tvPrice = (TextView)findViewById(R.id.tvPrice);
        final TextView tvDate = (TextView)findViewById(R.id.tvDateofpres2);
        final TextView tvPickdate = (TextView)findViewById(R.id.tvPickdate);
        tvPresid.setText(getIntent().getStringExtra("prescriptionid"));
        if(flag == 1 || flag == 2){
            SharedPreferences share = getSharedPreferences(String.valueOf(R.string.user_account_filename), MODE_PRIVATE);
                final Map<String, String> postParam= new HashMap<String, String>();
                postParam.put("token", share.getString("token", null));
                postParam.put("username", share.getString("username", null));
                postParam.put("order_id", getIntent().getStringExtra("orderid"));
                CustomRequest jsObjRequest = new CustomRequest
                        (Request.Method.POST, getString(R.string.url_getorderdetail), postParam, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                System.out.println("111:" + response.toString());
                                try {
                                    if(response.getString("SUCCESS").equals(String.valueOf("true")) == true){
                                        tvPresid.setText(String.valueOf(response.getInt("PRESCRIPTION_ID")));
                                        pharmacyname = response.getString("PHARMACY_NAME");
                                        pharmacyaddress = response.getString("ADDRESS");
                                        pharmacyid = String.valueOf(response.getInt("PHARMACY_ID"));
                                        tvSelePhar.setText(response.getString("PHARMACY_NAME") + "("  + response.get("ADDRESS") + ")");
                                        tvPrice.setText(String.valueOf(response.getDouble("TOTAL_PRICE")));
                                        tvDate.setText(response.getString("ORDER_TIME"));
                                        tvPickdate.setText(response.getString("PICK_TIME"));
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
}
