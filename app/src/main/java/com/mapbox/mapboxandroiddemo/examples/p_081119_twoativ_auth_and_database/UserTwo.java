package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

public class UserTwo {

    private String Дата;
    private String Направление;
    private String Маршрут_номер;
    private String Маршрут_название;
    private String Рейс_самолета;
    private String Поездки;

    public UserTwo() {
    }

    public UserTwo(String дата, String направление, String маршрут_номер, String маршрут_название, String рейс_самолета, String поездки) {
        Дата = дата;
        Направление = направление;
        Маршрут_номер = маршрут_номер;
        Маршрут_название = маршрут_название;
        Рейс_самолета = рейс_самолета;
        Поездки = поездки;
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

    public String getМаршрут_название() {
        return Маршрут_название;
    }

    public void setМаршрут_название(String маршрут_название) {
        Маршрут_название = маршрут_название;
    }

    public String getРейс_самолета() {
        return Рейс_самолета;
    }

    public void setРейс_самолета(String рейс_самолета) {
        Рейс_самолета = рейс_самолета;
    }

    public String getПоездки() {
        return Поездки;
    }

    public void setПоездки(String поездки) {
        Поездки = поездки;
    }
}
