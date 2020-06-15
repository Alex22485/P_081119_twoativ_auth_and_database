package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainUserNewOne2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user_new_one2);
    }

    public void mainUserNewOne3(View view){
        Intent mainUserNewOne3=new Intent(this,MainUserNewOne3.class);
        startActivity(mainUserNewOne3);
    }
}
