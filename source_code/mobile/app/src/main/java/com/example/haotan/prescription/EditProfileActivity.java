package com.example.haotan.prescription;

import android.app.DatePickerDialog;
import android.content.Intent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        setTitle("Edit Profile");
        final TextView tvusername = (TextView)findViewById(R.id.tvUsername);
        tvusername.setText(getIntent().getStringExtra("username"));
        final TextView tvfirstname = (TextView)findViewById(R.id.tvFirstname);
        tvfirstname.setText(getIntent().getStringExtra("firstname"));
        final TextView tvlastname = (TextView)findViewById(R.id.tvLastname);
        tvlastname.setText(getIntent().getStringExtra("lastname"));
        final TextView tvdateofbirth = (TextView)findViewById(R.id.tvDateofbirth);
        tvdateofbirth.setText(getIntent().getStringExtra("dateofbirth"));
        final TextView tvmedno = (TextView)findViewById(R.id.tvMedino);
        tvmedno.setText(getIntent().getStringExtra("medno"));
        EditText etphoneno = (EditText)findViewById(R.id.etPhonenum);
        etphoneno.setText(getIntent().getStringExtra("phoneno"));
        EditText etemail = (EditText)findViewById(R.id.etEmail);
        etemail.setText(getIntent().getStringExtra("email"));

        Button btnEditconfirm = (Button)findViewById(R.id.btnEditConfirm);
        btnEditconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                SharedPreferences share = getSharedPreferences(String.valueOf(R.string.user_account_filename), MODE_PRIVATE);
                final Map<String, String> postParam= new HashMap<String, String>();
                postParam.put("token", share.getString("token", null));
                postParam.put("username", share.getString("username", null));
                String phoneno = ((EditText) findViewById(R.id.etPhonenum)).getText().toString();
                String email = ((EditText) findViewById(R.id.etEmail)).getText().toString();
                postParam.put("phone_no", phoneno);
                postParam.put("email", email);
                CustomRequest jsObjRequest = new CustomRequest
                        (Request.Method.POST, getString(R.string.url_editprofile), postParam, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                System.out.println("111:" + response.toString());
                                try {
                                    if(response.getString("SUCCESS").equals(String.valueOf("true")) == true){
                                        Toast.makeText(v.getContext(), "Edit success", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(v.getContext(), ProfileActivity.class);
                                        intent.putExtra("flag",1);
                                        startActivity(intent);
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

        Button btnCancel = (Button)findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProfileActivity.class);
                intent.putExtra("flag",0);
                startActivity(intent);
            }
        });
    }
}
