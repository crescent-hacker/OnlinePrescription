package com.example.haotan.prescription;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hao Tan on 10/28/2016.
 */

public class PharmacylistAdapter extends BaseAdapter {
    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public PharmacylistAdapter(Context context, List<Map<String, Object>> data){
        this.context = context;
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public final class Pharmacylist{
        public TextView pharmacyname;
        public TextView pharmacyaddr;
        public Button pharmacymap;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        PharmacylistAdapter.Pharmacylist pharmacylist  = null;
        if(convertView == null){
            pharmacylist = new PharmacylistAdapter.Pharmacylist();
            convertView = layoutInflater.inflate(R.layout.pharmacylist, null);
            pharmacylist.pharmacyname = (TextView) convertView.findViewById(R.id.tvPharmacyname);
            pharmacylist.pharmacyaddr = (TextView) convertView.findViewById(R.id.tvPharmacyAddr);
            pharmacylist.pharmacymap = (Button) convertView.findViewById(R.id.btnPharmacymap);
            convertView.setTag(pharmacylist);
        }
        else{
            pharmacylist = (PharmacylistAdapter.Pharmacylist)convertView.getTag();
        }
        pharmacylist.pharmacyname.setText((String)data.get(position).get("pharmacyname"));
        pharmacylist.pharmacyaddr.setText((String)data.get(position).get("pharmacyaddress"));
        pharmacylist.pharmacymap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent intent = new Intent(v.getContext(), PharmacymapsActivity.class);
                intent.putExtra("pharmacyid", (String)data.get(position).get("pharmacyid"));
                intent.putExtra("pharmacyname", (String)data.get(position).get("pharmacyname"));
                intent.putExtra("pharmacyaddress", (String)data.get(position).get("pharmacyaddress"));
                v.getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}
