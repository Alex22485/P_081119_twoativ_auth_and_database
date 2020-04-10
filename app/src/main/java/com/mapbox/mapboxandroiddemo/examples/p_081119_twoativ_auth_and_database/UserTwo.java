package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

public class UserTwo {
    //Не используется

    private String Дата;
    private String Направление;
    private String Маршрут_номер;
    private String Маршрут_точкаСбора;
    private String Рейс_самолета;
    private String Token;

    public UserTwo() {
    }

    public UserTwo(String дата, String направление, String маршрут_номер, String маршрут_точкаСбора, String рейс_самолета, String token) {
        Дата = дата;
        Направление = направление;
        Маршрут_номер = маршрут_номер;
        Маршрут_точкаСбора = маршрут_точкаСбора;
        Рейс_самолета = рейс_самолета;
        Token = token;
    }

    public String getДата() {
        return Дата;
    }

    public void setДата(String дата) {
        Дата = дата;
    }

    public String getНаправление() {
        return Направление;
    }

    public void setНаправление(String направление) {
        Направление = направление;
    }

    public String getМаршрут_номер() {
        return Маршрут_номер;
    }

    public void setМаршрут_номер(String маршрут_номер) {
        Маршрут_номер = маршрут_номер;
    }

    public String getМаршрут_точкаСбора() {
        return Маршрут_точкаСбора;
    }

    public void setМаршрут_точкаСбора(String маршрут_точкаСбора) {
        Маршрут_точкаСбора = маршрут_точкаСбора;
    }

    public String getРейс_самолета() {
        return Рейс_самолета;
    }

    public void setРейс_самолета(String рейс_самолета) {
        Рейс_самолета = рейс_самолета;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
}


