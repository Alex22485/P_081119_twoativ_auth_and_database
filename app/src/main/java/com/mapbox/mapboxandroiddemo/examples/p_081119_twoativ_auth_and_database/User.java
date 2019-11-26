package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

public class User {

    private String Phone;
    private String Дата;
    private String Рейс;



    public User() {


    }

    public User(String phone, String дата, String рейс) {
        Phone = phone;
        Дата = дата;
        Рейс = рейс;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getДата() {
        return Дата;
    }

    public void setДата(String дата) {
        Дата = дата;
    }

    public String getРейс() {
        return Рейс;
    }

    public void setРейс(String рейс) {
        Рейс = рейс;
    }
}








