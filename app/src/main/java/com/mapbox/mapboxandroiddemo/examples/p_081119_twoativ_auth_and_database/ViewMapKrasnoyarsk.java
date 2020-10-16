package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ViewMapKrasnoyarsk extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_map_krasnoyarsk);
    }
    public void goBack(View view){
        Intent goBack = new Intent (this,MainUserNewOne2.class);
        startActivity(goBack);
    }

    public void goToOder (View view){
        Intent goToOder = new Intent(this,ViewZakazOne.class);
        startActivity(goToOder);
    }
}