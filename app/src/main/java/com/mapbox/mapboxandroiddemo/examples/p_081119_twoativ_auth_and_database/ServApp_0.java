package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ServApp_0 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serv_app_0);

    }

    public void ServApp1_window (View view){
        Intent ServApp1_window=new Intent( this,ServApp_1.class );
        startActivity(ServApp1_window  );
    }

    public void MyStatusOder(View view){
        Intent MyStatusOder = new Intent(this,Main6Activity.class);
        startActivity(MyStatusOder);
    }
    public void DriversApp_1(View view){
        Intent DriversApp_1=new Intent( this,DriversApp_1.class);
        startActivity(DriversApp_1);
    }
}
