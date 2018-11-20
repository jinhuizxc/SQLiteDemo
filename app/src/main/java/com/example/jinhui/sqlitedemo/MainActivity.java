package com.example.jinhui.sqlitedemo;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * 数据库的基本操作，日后有需要持续更新！
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener, View.OnClickListener {

    private static final String TAG = "MainActivity";
    ArrayList<Person> data = new ArrayList<Person>();
    Button btAdd;

    DBHelper helper;
    MyAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btAdd = findViewById(R.id.bt_add);
        listView = findViewById(R.id.listView);

        btAdd.setOnClickListener(this);

        helper = new DBHelper(getApplicationContext());

        adapter = new MyAdapter(this, data);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(this);


        // 查询数据库 --> 保存data中
        // 刷新ListView
        queryAndRefresh();

    }

    /**
     * 查询数据库 --> 保存data中 刷新ListView
     */
    private void queryAndRefresh() {

        Cursor cursor = helper.query();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
//            Log.e(TAG, "queryAndRefresh: " + id);
            String name = cursor.getString(cursor
                    .getColumnIndex(DBHelper.FIELD_NAME));
            String phone = cursor.getString(cursor
                    .getColumnIndex(DBHelper.FIELD_PHONE));

            data.add(new Person(name, phone, id));
        }
        // 刷新适配器。通知系统调用适配器中的方法，更新视图
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("更新");
        View viewDialog = LayoutInflater.from(this).inflate(R.layout.dialog, null);
        final EditText etName = (EditText) viewDialog.findViewById(R.id.editText1);
        final EditText etPhone = (EditText) viewDialog.findViewById(R.id.editText2);

        etName.setText(data.get(position).getName());
        etPhone.setText(data.get(position).getPhone());
        builder.setView(viewDialog);
        //增加了删除与更新数据操作----------添加项有哪些好好体会下
        builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                String id = String.valueOf(data.get(position).getId());

                //将记录从数据库中删除
                helper.delete(id);
                //更新视图
                data.remove(position);
                adapter.notifyDataSetChanged();

            }
        });

        builder.setNeutralButton("更新", new DialogInterface.OnClickListener() {

            private Person person;
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //更新数据库
                String name = etName.getText().toString();
                String phone = etPhone.getText().toString();

//                Cursor cursor = helper.query();
//                int cursorId = 0;
//                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
//                    cursorId = cursor.getInt(cursor.getColumnIndex("id"));
//                    Log.e(TAG, "queryAndRefresh 更新 : " + cursorId);
//                }
                String args = String.valueOf(data.get(position).getId());
                Log.e(TAG, "更新 onClick: " + position + "--" + args);
                ContentValues values = new ContentValues();
                values.put("name", String.valueOf(name));
                values.put("phone", String.valueOf(phone));
                // 修改/更新
                helper.update(values, args);
                //更新视图
                person = new Person();
                person.setId(data.get(position).getId());
                person.setName(name);
                person.setPhone(phone);
                data.set(position, person);
                adapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("取消", null);
        builder.create();
        builder.show();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.close();
    }

    @Override
    public void onClick(View v) {
        // 添加记录
        // 1.用户输入的
        // 2.添加到数据库中
        // 3.更新视图
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("添加记录");
        View view = LayoutInflater.from(this).inflate(R.layout.dialog, null);
        final EditText etName = (EditText) view.findViewById(R.id.editText1);
        final EditText etPhone = (EditText) view.findViewById(R.id.editText2);
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = etName.getText().toString();
                String phone = etPhone.getText().toString();

                //添加到数据库
                ContentValues values = new ContentValues();
                values.put(DBHelper.FIELD_NAME, name);
                values.put(DBHelper.FIELD_PHONE, phone);
                helper.insert(values);

                Log.e("Test", "添加一条记录");

                //查询新增记录的id
                Cursor cursor = helper.query();
                cursor.moveToLast();
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                //更新视图
                data.add(new Person(name, phone, id));
                adapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("取消", null);
        builder.create();
        builder.show();
    }
}
