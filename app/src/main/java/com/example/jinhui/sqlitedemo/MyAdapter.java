package com.example.jinhui.sqlitedemo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Email: 1004260403@qq.com
 * Created by jinhui on 2018/11/20.
 */
public class MyAdapter extends BaseAdapter {

    ArrayList<Person> data;
    LayoutInflater inflater;

    public MyAdapter(Context context, ArrayList<Person> data ) {
        this.data = data;
        this.inflater = LayoutInflater.from(context);
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

    long allTime;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        long last = System.currentTimeMillis();

        ViewHolder holder = null;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.contact_item, null);

            holder = new ViewHolder();

            TextView tv_id = (TextView) convertView.findViewById(R.id.tv_id);
            TextView tvName = (TextView) convertView.findViewById(R.id.textView1);
            TextView tvPhone = (TextView) convertView.findViewById(R.id.textView2);

            holder.tv_id = tv_id;
            holder.tvName = tvName;
            holder.tvPhone = tvPhone;

            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        //设置数据
        Person person = data.get(position);
        holder.tv_id.setText(person.getId() + "");
        holder.tvName.setText(person.getName());
        holder.tvPhone.setText(person.getPhone());

        long current = System.currentTimeMillis();

        allTime += (current-last);
        Log.e("Test", "allTime = " + allTime);

        return convertView;
    }

    class ViewHolder{
        TextView tv_id;
        TextView tvName;
        TextView tvPhone;
    }

}