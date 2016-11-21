package com.example.haotan.prescription;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Profile");
        int flag = getIntent().getIntExtra("flag",-1);//set whether the client should get information from serve,0 represents no, 1 represents yes

        Button btnReturn = (Button)findViewById(R.id.btnReturn);
        Button btnEdit = (Button)findViewById(R.id.btnEditprofile);
        final TextView  tvusername = (TextView)findViewById(R.id.tvUsername);

        final TextView  tvfirstname = (TextView)findViewById(R.id.tvFirstname);

        final TextView  tvlastname = (TextView)findViewById(R.id.tvLastname);

        final TextView  tvdateofbirth = (TextView)findViewById(R.id.tvDateofbirth);

        final TextView tvmedno = (TextView)findViewById(R.id.tvMedicarenum);

        final TextView tvphoneno = (TextView)findViewById(R.id.tvPhonenum);

        final TextView tvemail = (TextView)findViewById(R.id.tvEmail);

        SharedPreferences share = getSharedPreferences(String.valueOf(R.string.user_account_filename), MODE_PRIVATE);
        final Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("token", share.getString("token", null));
        postParam.put("username", share.getString("username", null));
        CustomRequest jsObjRequest = new CustomRequest
                (Request.Method.POST, getString(R.string.url_viewprofile), postParam, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("111:" + response.toString());
                        try {
                            if(response.getString("SUCCESS").equals(String.valueOf("true")) == true){
                                tvusername.setText(response.getString("USER_NAME"));
                                tvfirstname.setText(response.getString("FIRST_NAME"));
                                tvlastname.setText(response.getString("LAST_NAME"));
                                tvdateofbirth.setText(response.getString("DATE_OF_BIRTH"));
                                tvmedno.setText(String.valueOf(response.getInt("MEDICARE_NO")));
                                tvphoneno.setText(response.getString("PHONE_NO"));
                                tvemail.setText(response.getString("EMAIL_ADDR"));
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


        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                final String username = tvusername.getText().toString();
                final String firstname = tvfirstname.getText().toString();
                final String lastname = tvlastname.getText().toString();
                final String dateofbirth = tvdateofbirth.getText().toString();
                final String medno = tvmedno.getText().toString();
                final String phoneno = tvphoneno.getText().toString();
                final String email = tvemail.getText().toString();
                intent.putExtra("username",username);
                intent.putExtra("firstname",firstname);
                intent.putExtra("lastname",lastname);
                intent.putExtra("dateofbirth",dateofbirth);
                intent.putExtra("medno",medno);
                intent.putExtra("phoneno",phoneno);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
    }
}
