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

    public void ServAppList(View view){
        Intent ServAppList= new Intent(this,ServApp_0.class);
        startActivity(ServAppList);
    }

    public void ProbaTime(View view){
        Intent ProbaTime= new Intent(this,ProbaTime.class);
        startActivity(ProbaTime);
    }

    public void Website1(View view){
        Intent Website1= new Intent(this,Website1.class);
        startActivity(Website1);
    }
    public void probaRegDriver(View view){
        Intent probaRegDriver= new Intent(this,DriversApp_0.class);
        startActivity(probaRegDriver);
    }
    public void prParsing(View view){
        Intent ParsingCursApp= new Intent(this,ParsingCursApp.class);
        startActivity(ParsingCursApp);
    }
}
