package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

public class MainUserNewOne2 extends AppCompatActivity {

    String[] listCityPlane= {"Игарка","Северо-Енисейск","Новосибирск","Иркутск","Омск","Екатеринбург"};
    String [] listCityTaxi= {"Красноярск","Сосновоборск","Ачинск","Канск","Лесосибирск"};
    String refCityTaxi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user_new_one2);

    }

    // список рейсов самолетов доступных к заказу
    public void listCityFly(View view){

        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(MainUserNewOne2.this);
        // Set Title
        mAlertDialog.setTitle("Рейсы самолетов доступные к заказу");

        // Set Message
        mAlertDialog
                .setItems(listCityPlane, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        mAlertDialog.create();
        // Showing Alert Message
        mAlertDialog.show();

    }

    // список городов доступных маршрутов
    public void listCityRoad(View view){

        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(MainUserNewOne2.this);
        // Set Title
        mAlertDialog.setTitle("Выбери город");

        // Set Message
        mAlertDialog
                .setItems(listCityTaxi, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        refCityTaxi=listCityTaxi[which];
                        goToRefList();
                    }
                });
        mAlertDialog.create();
        // Showing Alert Message
        mAlertDialog.show();

    }

    private void goToRefList() {
        if (refCityTaxi.equals("Красноярск")){
            Intent Krasnoyarsk = new Intent(this,ViewMapKrasnoyarsk.class);
            startActivity(Krasnoyarsk);
        }
        if (refCityTaxi.equals("Сосновоборск")){
            Intent Sosnovobors = new Intent(this,ViewMapSosnovoborsk.class);
            startActivity(Sosnovobors);
        }
        if (refCityTaxi.equals("Ачинск")){
            Intent Achinsk = new Intent(this,ViewMapAchinsk.class);
            startActivity(Achinsk);
        }
        if (refCityTaxi.equals("Канск")){
            Intent Kansk = new Intent(this,ViewMapKansk.class);
            startActivity(Kansk);
        }
        if (refCityTaxi.equals("Лесосибирск")){
            Intent Lesosibirsk = new Intent(this,ViewMapLesosibirsk.class);
            startActivity(Lesosibirsk);
        }
    }
}
