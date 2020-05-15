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

    String keyReg;
    String registration;
    String checkregistrationTimeOut;

    String UserToken;

    FirebaseDatabase database02;
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

        //СТАРТ Проверка интернета+регистрации
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                CheckRegistration();
            }
        },700);
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
        },15000);

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


    //Проверка интернета во время проверки регистрации
    public void inetNotWhenGoCheckRegistration (){

        if(registration.equals("Hello")||keyReg==null){
            Log.d(TAG, "таймер-2 остановлен");/*специально пусто*/}

        else {
            //пропал интернет во время проверки наличия регистрации
            Log.d(TAG, "Интернета пропал при проверке регистрации");
            Intent aaa = new Intent(this,DriversApp_InternetNot.class);
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
                //Переход в главное меню заказов
                Intent mainList = new Intent(this,DriversApp_1.class);
                startActivity(mainList);
            }
        }
    }

    // Блокировка кнопки Back!!!! :)))
    @Override
    public void onBackPressed(){
    }
}

