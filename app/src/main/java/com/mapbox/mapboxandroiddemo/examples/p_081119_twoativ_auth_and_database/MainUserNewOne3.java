package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainUserNewOne3 extends AppCompatActivity {

    private static final String TAG ="MainUserNewOne3" ;

    String phoneNew;
    String phoneNewFromMain6;
    String regFromMain3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user_new_one3);

        //транзит из MainActivity
        Intent MainActivityToMainUserNewOne3= getIntent();
        phoneNew=MainActivityToMainUserNewOne3.getStringExtra("phoneNew");
        Log.d(TAG, "phoneNew:"+phoneNew);

        // проверка был ли переход на эту страницу после отмены заявки
        Intent Main6ToMain3=getIntent();
        phoneNewFromMain6=""+Main6ToMain3.getStringExtra("regFromMain6");
        Log.d(TAG, "phoneNewFromMain6:"+phoneNewFromMain6);

        //если был то пишем phoneNew=phoneNewFromMain6
        if (!phoneNewFromMain6.equals("null")){
            phoneNew=phoneNewFromMain6;
            Log.d(TAG, "phoneNewNEW:"+phoneNew);
        }

        // проверка был ли переход на эту страницу из Main3Activity после запрета регистрации на уже сформировавийся маршрут (STOPODER)
        Intent Main3ToMainUserNewOne3=getIntent();
        regFromMain3=""+Main3ToMainUserNewOne3.getStringExtra("regFromMain3");
        Log.d(TAG, "regFromMain3:"+regFromMain3);

        //если был то присваеваем phoneNew (лист с Заставкой)
        if (!regFromMain3.equals("null")){
            phoneNew=regFromMain3;
            Log.d(TAG, "phoneNewAfterSTOPODER:"+phoneNew);
        }

    }
    public void IgarkaCharter(View view){
        Intent mainUserNewOne3TomainUserNewOne4 = new Intent(this,MainUserNewOne4.class);
        mainUserNewOne3TomainUserNewOne4.putExtra( "refCity", "ИгаркаЧ" );

        //транзит в Main3Activity
        mainUserNewOne3TomainUserNewOne4.putExtra("phoneNew",phoneNew);
        startActivity(mainUserNewOne3TomainUserNewOne4);
        Log.d(TAG, "IgarkaCharter:"+phoneNew);
    }
    public void Igarka(View view){
        Intent mainUserNewOne3TomainUserNewOne4 = new Intent(this,MainUserNewOne4.class);
        mainUserNewOne3TomainUserNewOne4.putExtra( "refCity", "Игарка" );

        //транзит в Main3Activity
        mainUserNewOne3TomainUserNewOne4.putExtra("phoneNew",phoneNew);
        startActivity(mainUserNewOne3TomainUserNewOne4);
        Log.d(TAG, "Igarka:"+phoneNew);
    }
    public void Turyxansk(View view){
        Intent mainUserNewOne3TomainUserNewOne4 = new Intent(this,MainUserNewOne4.class);
        mainUserNewOne3TomainUserNewOne4.putExtra( "refCity", "Туруханск" );

        //транзит в Main3Activity
        mainUserNewOne3TomainUserNewOne4.putExtra("phoneNew",phoneNew);
        startActivity(mainUserNewOne3TomainUserNewOne4);
        Log.d(TAG, "Turyxansk:"+phoneNew);
    }
    public void SeveroEniseisk(View view){
        Intent mainUserNewOne3TomainUserNewOne4 = new Intent(this,MainUserNewOne4.class);
        mainUserNewOne3TomainUserNewOne4.putExtra( "refCity", "Северо-Енисейск" );

        //транзит в Main3Activity
        mainUserNewOne3TomainUserNewOne4.putExtra("phoneNew",phoneNew);
        startActivity(mainUserNewOne3TomainUserNewOne4);
        Log.d(TAG, "SeveroEniseisk:"+phoneNew);
    }
    
    // Блокировка кнопки Back!!!! :)))
    @Override
    public void onBackPressed(){
    }
}
