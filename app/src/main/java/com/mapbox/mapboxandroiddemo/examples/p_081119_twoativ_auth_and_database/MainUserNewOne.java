package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainUserNewOne extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user_new_one);
    }
    public void mainUserNewOne2(View view){
        Intent mainUserNewOne2=new Intent(this,MainUserNewOne2.class);
        startActivity(mainUserNewOne2);
    }

     //Блокировка кнопки Back!!!! :)))
    @Override
    public void onBackPressed(){
    }
}
