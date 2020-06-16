package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainUserNewOne3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user_new_one3);
    }
    public void IgarkaCharter(View view){
        Intent mainUserNewOne4 = new Intent(this,MainUserNewOne4.class);
        mainUserNewOne4.putExtra( "refCity", "ИгаркаЧ" );
        startActivity(mainUserNewOne4);
    }
    public void Igarka(View view){
        Intent mainUserNewOne4 = new Intent(this,MainUserNewOne4.class);
        mainUserNewOne4.putExtra( "refCity", "Игарка" );
        startActivity(mainUserNewOne4);
    }
    public void Turyxansk(View view){
        Intent mainUserNewOne4 = new Intent(this,MainUserNewOne4.class);
        mainUserNewOne4.putExtra( "refCity", "Туруханск" );
        startActivity(mainUserNewOne4);
    }
    public void SeveroEniseisk(View view){
        Intent mainUserNewOne4 = new Intent(this,MainUserNewOne4.class);
        mainUserNewOne4.putExtra( "refCity", "Северо-Енисейск" );
        startActivity(mainUserNewOne4);
    }
}
