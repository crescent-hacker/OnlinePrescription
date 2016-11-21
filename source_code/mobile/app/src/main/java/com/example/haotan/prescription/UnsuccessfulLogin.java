package com.example.haotan.prescription;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UnsuccessfulLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unsuccessful_login);
        setTitle("Log in error");
        String errormsg = getIntent().getStringExtra("errormsg");
        TextView tvErrormsg = (TextView)findViewById(R.id.tvUnsuccessfulLoginMsg);
        if(errormsg.equals("pending"))
            tvErrormsg.setText("Your account is not activated.");
        else if(errormsg.equals("rejected"))
            tvErrormsg.setText("Your account is rejected by admin.");

        Button btnReturn = (Button)findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
