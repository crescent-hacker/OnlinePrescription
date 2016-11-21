package com.example.haotan.prescription;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    int tab_position;
    BottomNavigationBar bottomNavigationBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        //read file and get user account information
//        SharedPreferences share = getSharedPreferences(String.valueOf(R.string.user_account_filename), MODE_PRIVATE);
//        if(share.getString("username", null) == null){
//            startActivity(new Intent(this, LoginActivity.class));
//        }

        //set bottom navigation bar
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.ic_home_black_24dp, "Home"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_format_list_bulleted_black_24dp, "Order enabled prescription"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_format_list_bulleted_black_24dp, "Processing Order"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_format_list_bulleted_black_24dp, "Completed Order"))
                .initialise();

        //set click listener of bottom navigation bar
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener(){
            @Override
            public void onTabSelected(int position) {
                if(position == 0 && tab_position != position){
                    setTitle("Prescription List");
                    MainFragment fragment = new MainFragment();
                    fragment.setListview_height(findViewById(R.id.activity_main).getHeight() - findViewById(R.id.bottom_navigation_bar).getHeight());
                    getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, fragment).commit();
                }
                else if(position == 1 && tab_position != position){
                    setTitle("Order enabled prescription");
                    OrderListFragment fragment = new OrderListFragment();
                    fragment.setFlag(0);
                    getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, fragment).commit();
                }
                else if(position == 2 && tab_position != position){
                    setTitle("Processing Order");
                    OrderListFragment fragment = new OrderListFragment();
                    fragment.setFlag(1);
                    getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, fragment).commit();
                }
                else if(position == 3 && tab_position != position){
                    setTitle("Completed Order");
                    OrderListFragment fragment = new OrderListFragment();
                    fragment.setFlag(2);
                    getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, fragment).commit();
                }
                tab_position = position;
            }
            @Override
            public void onTabUnselected(int position) {
            }
            @Override
            public void onTabReselected(int position) {
            }
        });

        if(savedInstanceState == null){
            setTitle("Prescription List");
            MainFragment fragment = new MainFragment();
            //there is a problem regarding get view height in onCreate function
            SharedPreferences share = getSharedPreferences(String.valueOf(R.string.user_account_filename), MODE_PRIVATE);
            fragment.setListview_height((int) share.getFloat("formHeight", -1));
            getSupportFragmentManager().beginTransaction().add(R.id.activity_main, fragment).commit();
        }
    }

    @Override
    //action bar
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    //action bar click listener
    public boolean onOptionsItemSelected(final MenuItem item) {
        if(item.getItemId() == R.id.action_logout){
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you want log out?")
                    .setCancelable(false)
                    .setPositiveButton("YES",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            //log out action
                            SharedPreferences share = getSharedPreferences(String.valueOf(R.string.user_account_filename), MODE_PRIVATE);
                            SharedPreferences.Editor editor = share.edit();
                            editor.remove("username");
                            editor.remove("password");
                            editor.remove("token");
                            editor.commit();
                            Intent intent = new Intent(builder.getContext(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }).setNegativeButton("Cancel", null);
            builder.create().show();
        }
        else if(item.getItemId() == R.id.action_profile){
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            intent.putExtra("flag",1);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_username);
        SharedPreferences share = getSharedPreferences(String.valueOf(R.string.user_account_filename), MODE_PRIVATE);
        item.setTitle(share.getString("username", null));
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        if(getIntent().getIntExtra("tab_position", -1) == 2)
            bottomNavigationBar.selectTab(2);
        super.onResume();
    }


}
