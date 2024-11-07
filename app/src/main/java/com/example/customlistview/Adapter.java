package com.example.customlistview;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {

    public void setData(ArrayList<Contact> data){
        this.data = data;
    }

    private ArrayList<Contact> data;
    private ArrayList<Contact> databackup;
    private Activity context;
    private LayoutInflater inflater;

    public Adapter(ArrayList<Contact> data, Activity activity) {
        this.data = data;
        this.context = activity;
        this.inflater = (LayoutInflater) activity.getSystemService(context.LAYOUT_INFLATER_SERVICE);
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
        return data.get(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = view;
        if(v==null) {
            v = inflater.inflate(R.layout.item_contact, null);
        }
        ImageView imgprofile = v.findViewById(R.id.Images);
        TextView tvname = v.findViewById(R.id.txtName);
        tvname.setText(data.get(position).getName());
        TextView tvphone = v.findViewById(R.id.txtPhone);
        tvphone.setText(data.get(position).getPhone());
        return v;
    }
}