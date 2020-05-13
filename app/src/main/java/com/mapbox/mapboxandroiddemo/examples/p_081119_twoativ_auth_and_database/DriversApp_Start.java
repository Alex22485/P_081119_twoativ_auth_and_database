package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class DriversApp_Start extends AppCompatActivity {

    String  key;
    String keyReg;
    String UserToken;

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
                //Toast.makeText( DriversApp_Start.this, "токен"+UserToken, Toast.LENGTH_SHORT ).show();
            }
        });
    }
    @Override
    protected void onStart (){
        super.onStart();
//Проверка интернета
        cheskInternet();
    }
    //Проверка интернета
    public void cheskInternet(){

        key="";

        //задержка запроса
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                check();
            }
        },10000);


        //чтение из БД с правилом для любых пользователей
        database01 = FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Check")
                .child("Internet")
                .child("Work");
        ref01.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Чтение результата из БД
                key=dataSnapshot.getValue(String.class);
                ref01.removeEventListener(this);

//                //задержка запроса
//                Handler handler1 = new Handler();
//                handler1.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        check();
//                    }
//                },1000);

                Toast.makeText( DriversApp_Start.this, "Интернет есть"+key, Toast.LENGTH_SHORT ).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void check(){
        // переход на Активити InternetNot
        if (key.isEmpty()){
            Intent aaa = new Intent(this,DriversApp_InternetNot.class);
            startActivity(aaa);
        }
        else if(key.equals("Yes")) {

            //проверка регистрации
            CheckRegistration();
            //Toast.makeText( DriversApp_Start.this, "Проверка регистрации", Toast.LENGTH_SHORT ).show();
        }
    }

    //проверка регистрации
    public void CheckRegistration(){

        keyReg="";

        //задержка запроса
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Проверка регистрации токена
                checkHaveToken();
            }
        },600);


        //чтение из БД с правилом для любых пользователей
        database02 = FirebaseDatabase.getInstance();
        ref02 = database02.getReference("Check")
                .child("CheckDrivers")
                .child("Token")
                .child(UserToken);
        ref02.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Чтение
                keyReg=dataSnapshot.getValue(String.class);
                ref02.removeEventListener(this);

                //Toast.makeText( DriversApp_Start.this, "Регистрация есть"+keyReg, Toast.LENGTH_SHORT ).show();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    //проверка зарегестрированного токена
    public void checkHaveToken(){
        // переход на Активити InternetNot
        if (keyReg==null){

            //переход к авторизации по телефону от firebase
            Intent AuthList = new Intent(this,DriversApp_0.class);
            startActivity(AuthList);
//            Toast.makeText( DriversApp_Start.this, "Привет  "+key, Toast.LENGTH_SHORT ).show();
        }
        else if(keyReg.isEmpty()) {

            //Если keyReg пусто, значит пропал интернет и переходим на  InternetNot
            Intent aaa = new Intent(this,DriversApp_InternetNot.class);
            startActivity(aaa);
        }
        else if (keyReg.equals("Hello")){
            goMainList();}
    }

    //Переход в главное меню заказов
    public void goMainList(){
        Intent mainList = new Intent(this,DriversApp_1.class);
        startActivity(mainList);
    }

    // Блокировка кнопки Back!!!! :)))
    @Override
    public void onBackPressed(){
    }
}

