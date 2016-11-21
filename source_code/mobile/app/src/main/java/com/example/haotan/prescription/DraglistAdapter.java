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
 * Created by Hao Tan on 10/19/2016.
 */

public class DraglistAdapter extends BaseAdapter
{
    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public DraglistAdapter(Context context, List<Map<String, Object>> data){
        this.context = context;
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public final class DragList{
        public TextView dragname;
        public TextView dragprod;
        public TextView dragprice;
        public TextView dragdoes;
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
        DragList dragList = null;
        if(convertView == null){
            dragList = new DragList();
            convertView = layoutInflater.inflate(R.layout.draglist, null);
            dragList.dragname = (TextView)convertView.findViewById(R.id.tvDragname);
            dragList.dragprod = (TextView)convertView.findViewById(R.id.tvDragprod);
            dragList.dragprice = (TextView)convertView.findViewById(R.id.tvDragprice);
            dragList.dragdoes = (TextView)convertView.findViewById(R.id.tvDragdose);
            convertView.setTag(dragList);
        }
        else{
            dragList = (DragList)convertView.getTag();
        }
        dragList.dragname.setText((String)data.get(position).get("dragname"));
        dragList.dragprod.setText((String)data.get(position).get("dragprod"));
        dragList.dragprice.setText((String)data.get(position).get("dragprice"));
        dragList.dragdoes.setText((String)data.get(position).get("dragdoes"));

        return convertView;
    }
}
