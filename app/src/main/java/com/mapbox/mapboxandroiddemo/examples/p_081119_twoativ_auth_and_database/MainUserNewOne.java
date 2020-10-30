package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainUserNewOne extends AppCompatActivity {

    private static final String TAG ="MainUserNewOne" ;

    TextView ttg,lozung,lozung1;
    int timeNew;
    int sizeNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user_new_one);

        ttg=findViewById(R.id.ttg);
        lozung=findViewById(R.id.lozung);
        lozung1=findViewById(R.id.lozung1);

        sizeNew=0;
        timeNew=1;
        timeX();

    }

    public void timeX(){
        if (timeNew<31){

            Handler Date = new Handler();
            Date.postDelayed(new Runnable() {
                @Override
                public void run() {
                    lozung.setTextSize(sizeNew);

                    sizeNew=sizeNew+1;
                    timeNew=timeNew+1;
                    Log.d(TAG, timeNew+"");
                    timeX();
                }
            },timeNew+1);
        }
        else {
            sizeNew=0;
            timeNew=1;
            timeY();
        }
    }

    public void timeY(){
        if (timeNew<31){

            Handler Date = new Handler();
            Date.postDelayed(new Runnable() {
                @Override
                public void run() {
                    lozung1.setTextSize(sizeNew);

                    sizeNew=sizeNew+1;
                    timeNew=timeNew+1;
                    Log.d(TAG, timeNew+"");
                    timeY();
                }
            },timeNew+1);
        }
        else {
            sizeNew=0;
            timeNew=1;
            timeZ();
        }
    }

    public void timeZ(){
        if (timeNew<31){

            Handler Date = new Handler();
            Date.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ttg.setTextSize(sizeNew);

                    sizeNew=sizeNew+1;
                    timeNew=timeNew+1;
                    Log.d(TAG, timeNew+"");
                    timeZ();
                }
            },timeNew+1);
        }
        else {
            Toast.makeText(MainUserNewOne.this,"УРА....",Toast.LENGTH_LONG).show();
        }
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
