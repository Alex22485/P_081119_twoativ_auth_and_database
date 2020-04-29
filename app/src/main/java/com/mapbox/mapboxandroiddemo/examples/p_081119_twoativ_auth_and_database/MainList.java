package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
    }

    public void IamDriver(View view){
        Intent IamDriver= new Intent(this,DriversApp_Start.class);
        startActivity(IamDriver);
    }

    public void IamUser(View view){
        Intent IamUser= new Intent(this,MainActivity.class);
        startActivity(IamUser);
    }
}
