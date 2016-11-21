package com.example.haotan.prescription;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by Hao Tan on 10/21/2016.
 */

public class PrescriptionListAdapter extends BaseAdapter {
    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public PrescriptionListAdapter(Context context, List<Map<String, Object>> data){
        this.context = context;
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public final class PrescriptionList{
        public TextView presno;
        public TextView presdate;
        public View presstatus;
        public TextView presbriefdescription;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        PrescriptionList prescriptionList = null;
        if(convertView == null){
            prescriptionList = new PrescriptionList();
            convertView = layoutInflater.inflate(R.layout.prescriptionlist, null);
            prescriptionList.presno = (TextView) convertView.findViewById(R.id.tvPresid2);
            prescriptionList.presdate = (TextView) convertView.findViewById(R.id.tvPresdate);
            prescriptionList.presstatus = (View) convertView.findViewById(R.id.ivImg);
            prescriptionList.presbriefdescription = (TextView)convertView.findViewById(R.id.tvBriefDescription);
            convertView.setTag(prescriptionList);
        }
        else{
            prescriptionList = (PrescriptionList)convertView.getTag();
        }
        prescriptionList.presno.setText(Integer.toString((Integer) data.get(position).get("presno")));
        prescriptionList.presdate.setText((String)data.get(position).get("presdate"));
        prescriptionList.presstatus.setBackgroundColor((int)data.get(position).get("presstatus"));
        prescriptionList.presbriefdescription.setText((String)data.get(position).get("presbriefdescribtion"));

        return convertView;
    }
}
