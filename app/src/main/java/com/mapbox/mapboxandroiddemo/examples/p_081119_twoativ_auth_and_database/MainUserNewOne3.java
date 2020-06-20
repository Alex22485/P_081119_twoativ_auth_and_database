package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainUserNewOne3 extends AppCompatActivity {

    private static final String TAG ="MainUserNewOne3" ;

    String registration;
    String regFromMain6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user_new_one3);

        //транзит в Main3Activity
        Intent MainActivityToMainUserNewOne3= getIntent();
        registration=MainActivityToMainUserNewOne3.getStringExtra("registration");

        // проверка был ли переход на эту страницу после отмены заявки
        Intent Main6ToMain3=getIntent();
        regFromMain6=""+Main6ToMain3.getStringExtra("regFromMain6");
        Log.d(TAG, "regFromMain6:"+regFromMain6);

        //если был то пишем Hello вместо такого же слова которое бралось из Mainactivity (лист с Заставкой)
        if (regFromMain6.equals("Hello")){
            registration="Hello";
            Log.d(TAG, "registrationNEW:"+regFromMain6);
        }

    }
    public void IgarkaCharter(View view){
        Intent mainUserNewOne4 = new Intent(this,MainUserNewOne4.class);
        mainUserNewOne4.putExtra( "refCity", "ИгаркаЧ" );

        //транзит в Main3Activity
        mainUserNewOne4.putExtra("registration",registration);
        startActivity(mainUserNewOne4);
    }
    public void Igarka(View view){
        Intent mainUserNewOne4 = new Intent(this,MainUserNewOne4.class);
        mainUserNewOne4.putExtra( "refCity", "Игарка" );

        //транзит в Main3Activity
        mainUserNewOne4.putExtra("registration",registration);
        startActivity(mainUserNewOne4);
    }
    public void Turyxansk(View view){
        Intent mainUserNewOne4 = new Intent(this,MainUserNewOne4.class);
        mainUserNewOne4.putExtra( "refCity", "Туруханск" );

        //транзит в Main3Activity
        mainUserNewOne4.putExtra("registration",registration);
        startActivity(mainUserNewOne4);
    }
    public void SeveroEniseisk(View view){
        Intent mainUserNewOne4 = new Intent(this,MainUserNewOne4.class);
        mainUserNewOne4.putExtra( "refCity", "Северо-Енисейск" );

        //транзит в Main3Activity
        mainUserNewOne4.putExtra("registration",registration);
        startActivity(mainUserNewOne4);
    }
    
    // Блокировка кнопки Back!!!! :)))
    @Override
    public void onBackPressed(){
    }
}
