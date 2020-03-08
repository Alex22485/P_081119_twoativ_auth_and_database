package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
// с 5.03.2020 не используется заявки формируются через Node js
public class User {

    private String Phone;

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


}









