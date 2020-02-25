package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class User {

    private String Phone;
    //private String Дата;
    //private String Рейс;
    private Integer Число;
    private String Token;
    private String UserI;

    public User(){

    }

    public User(String phone, Integer число, String token, String userI) {
        Phone = phone;
        Число = число;
        Token = token;
        UserI = userI;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public Integer getЧисло() {
        return Число;
    }

    public void setЧисло(Integer число) {
        Число = число;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getUserI() {
        return UserI;
    }

    public void setUserI(String userI) {
        UserI = userI;
    }

    /*public User(String phone, String дата, String рейс, Integer число, String token) {
        Phone = phone;
        Дата = дата;
        Рейс = рейс;
        Число = число;
        Token = token;
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

    public Integer getЧисло() {
        return Число;
    }

    public void setЧисло(Integer число) {
        Число = число;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }*/
}









