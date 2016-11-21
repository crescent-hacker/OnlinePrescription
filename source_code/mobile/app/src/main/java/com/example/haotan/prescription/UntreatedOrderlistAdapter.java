package com.example.haotan.prescription;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by Hao Tan on 10/21/2016.
 */

public class UntreatedOrderlistAdapter extends BaseAdapter {

    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public UntreatedOrderlistAdapter(Context context, List<Map<String, Object>> data){
        this.context = context;
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public final class UntreatedOrderlist{
        TextView presid;
        TextView presdate;
        TextView presdoctor;
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
        UntreatedOrderlist untreatedOrderlist = null;
        if(convertView == null){
            untreatedOrderlist = new UntreatedOrderlist();
            convertView = layoutInflater.inflate(R.layout.untreatedorderlist, null);
            untreatedOrderlist.presid = (TextView)convertView.findViewById(R.id.tvPresid3);
            untreatedOrderlist.presdate = (TextView)convertView.findViewById(R.id.tvPresdate2);
            untreatedOrderlist.presdoctor = (TextView)convertView.findViewById(R.id.tvDoctorname);
            convertView.setTag(untreatedOrderlist);
        }
        else{
            untreatedOrderlist = (UntreatedOrderlist)convertView.getTag();
        }
        untreatedOrderlist.presid.setText((String)data.get(position).get("presno"));
        untreatedOrderlist.presdate.setText((String)data.get(position).get("presdate"));
        untreatedOrderlist.presdoctor.setText((String)data.get(position).get("presdoctor"));

        return convertView;
    }
}
