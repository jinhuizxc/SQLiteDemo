package com.example.jinhui.sqlitedemo;

/**
 * Email: 1004260403@qq.com
 * Created by jinhui on 2018/11/20.
 */
public class Person {

    private String name;
    private String phone;
    private int id;

    public Person(String name, String phone, int id) {
        super();
        this.name = name;
        this.phone = phone;
        this.id = id;
    }


    public Person(){

    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
