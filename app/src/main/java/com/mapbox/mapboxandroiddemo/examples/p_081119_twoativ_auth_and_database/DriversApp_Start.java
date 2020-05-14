package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class DriversApp_Start extends AppCompatActivity {

    private static final String TAG ="DriversApp_Start" ;
    String key;
    String keyReg;
    String UserToken;

    String internet;
    String internetTimeOut;

    String registration;
    String checkregistrationTimeOut;

    FirebaseDatabase database01;
    FirebaseDatabase database02;
    DatabaseReference ref01;
    DatabaseReference ref02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivers_app__start);

        // Получить Токен!!!!
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(DriversApp_Start.this,new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                UserToken = instanceIdResult.getToken();
            }
        });
    }
    @Override
    protected void onStart (){
        super.onStart();
//СТАРТ Проверка интернета
        cheskInternet();
    }

    //Проверка интернета
    public void cheskInternet(){

        key="";
        internet="";
        internetTimeOut="";

        //ТАЙМ-АУТ ЗАПРОСА ИНТЕРНЕТА-1
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {

                // Завершен ТАЙМ-АУТ ЗАПРОСА ИНТЕРНЕТА-1
                inetNot();
                internetTimeOut="Out";
            }
        },7000);


        //чтение из БД с правилом для любых пользователей
        database01 = FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Check")
                .child("Internet")
                .child("Work");
        ref01.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                key=dataSnapshot.getValue(String.class);
                internet=key;
                Log.d(TAG, "интернет 1 есть");

                //Проверка time-Out
                timeOutInternet();


                // с этой записью makeText появляется только один раз!!!!! ХОРОШО, блин не всегда :(((
                ref01.removeEventListener(this);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void  inetNot(){
        if (internet.equals("Yes")){
            Log.d(TAG, "таймер-1 остановлен");/*специально пусто*/}
        else {
            Log.d(TAG, "Интернета нет при первом запросе");/*специально пусто*/
            Intent aaa = new Intent(this,DriversApp_InternetNot_forDriverApp_0.class);
            startActivity(aaa);
        }
    }

    //Проверка time-Out
    public void timeOutInternet (){
        if(internetTimeOut.equals("Out")){
            Log.d(TAG, "проверка интернета1 время вышло");/*специально пусто*/}

        else {
            // Проверка регистрации
            CheckRegistration();
        }
    }

    //проверка регистрации
    public void CheckRegistration(){

        keyReg="";
        registration="";
        checkregistrationTimeOut="";

        //ТАЙМ-АУТ ЗАПРОСА ИНТЕРНЕТА-2
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {

                // Завершен ТАЙМ-АУТ ЗАПРОСА ИНТЕРНЕТА-2 при проверке регистрации
                checkregistrationTimeOut="Out";
                inetNotWhenGoCheckRegistration();
            }
        },7000);

        Log.d(TAG, "запрос регистрации начат");
        //чтение из БД с правилом для любых пользователей
        database02 = FirebaseDatabase.getInstance();
        ref02 = database02.getReference("Check")
                .child("CheckDrivers")
                .child("Token")
                .child(UserToken);
        ref02.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                keyReg=dataSnapshot.getValue(String.class);
                registration=""+keyReg; /* так как может получить null*/
                Log.d(TAG, "запрос регистрации получен");

                // с этой записью makeText появляется только один раз!!!!! ХОРОШО
                ref02.removeEventListener(this);

                //Проверка токена
                checkHaveToken();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }



//    //проверка регистрации
//    public void CheckRegistration(){
//
//        keyReg="";
//
//        //задержка запроса
//        Handler handler1 = new Handler();
//        handler1.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // Проверка регистрации токена
//                checkHaveToken();
//            }
//        },600);
//
//
//        //чтение из БД с правилом для любых пользователей
//        database02 = FirebaseDatabase.getInstance();
//        ref02 = database02.getReference("Check")
//                .child("CheckDrivers")
//                .child("Token")
//                .child(UserToken);
//        ref02.addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                // Чтение
//                keyReg=dataSnapshot.getValue(String.class);
//                ref02.removeEventListener(this);
//
//                //Toast.makeText( DriversApp_Start.this, "Регистрация есть"+keyReg, Toast.LENGTH_SHORT ).show();
//
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
//
//    }

    //Проверка интернета во время проверки регистрации
    public void inetNotWhenGoCheckRegistration (){

        if(registration.equals("Hello")||keyReg==null){
            Log.d(TAG, "таймер-2 остановлен");/*специально пусто*/}

        else {
            //пропал интернет во время проверки наличия регистрации
            Log.d(TAG, "Интернета пропал при проверке регистрации");
            Intent aaa = new Intent(this,DriversApp_InternetNot_forDriverApp_0.class);
            startActivity(aaa);
        }
    }

    //Проверка токена
    public void checkHaveToken(){

        if (checkregistrationTimeOut.equals("Out")){
            Log.d(TAG, "проверка интернета2 время вышло");/*специально пусто*/
        }

        else{
            if (keyReg==null){

                //переход к авторизации по телефону от firebase
                Intent AuthList = new Intent(this,DriversApp_0.class);
                startActivity(AuthList);
            }
            else if (keyReg.equals("Hello")){
                goMainList();}
        }
    }

//    //проверка зарегестрированного токена
//    public void checkHaveToken(){
//        // переход на Активити InternetNot
//        if (keyReg==null){
//
//            //переход к авторизации по телефону от firebase
//            Intent AuthList = new Intent(this,DriversApp_0.class);
//            startActivity(AuthList);
////            Toast.makeText( DriversApp_Start.this, "Привет  "+key, Toast.LENGTH_SHORT ).show();
//        }
//        else if(keyReg.isEmpty()) {
//
//            //Если keyReg пусто, значит пропал интернет и переходим на  InternetNot
//            Intent aaa = new Intent(this,DriversApp_InternetNot.class);
//            startActivity(aaa);
//        }
//        else if (keyReg.equals("Hello")){
//            goMainList();}
//    }

    //Переход в главное меню заказов
    public void goMainList(){
        Intent mainList = new Intent(this,DriversApp_1.class);
        startActivity(mainList);
    }

//    //Переход в главное меню заказов
//    public void goMainList(){
//        Intent mainList = new Intent(this,DriversApp_1.class);
//        startActivity(mainList);
//    }

    // Блокировка кнопки Back!!!! :)))
    @Override
    public void onBackPressed(){
    }
}

