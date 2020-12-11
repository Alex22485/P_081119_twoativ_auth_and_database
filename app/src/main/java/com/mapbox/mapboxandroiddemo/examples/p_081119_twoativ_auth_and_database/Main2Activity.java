package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class Main2Activity extends AppCompatActivity {

    // c 10.2020 не использется

    // Лист авторизации по телефону
    private static final String TAG ="Main2activity" ;

    // для экспорта из Zakaz3finish
    String phoneNew, Calend, RefplaneCity, RefMap, RefPoint, time;

    LinearLayout TextProgress;


    FirebaseDatabase database01;
    DatabaseReference ref01;

    FirebaseDatabase databaseSecret;
    DatabaseReference refSecret;

    String UserToken;
    String phoneUser;
    String IneternetYES;

    TextView TextHello1;
    Button GoMainActivity;

    LinearLayout Layout1;
    LinearLayout Layout2;
    LinearLayout AuthNot;

    FirebaseAuth mAuth;

    //Проверка интернета
    String keyReg;
    String checkInternet;
    String writeData;

    String refCity;
    String toOrFrom;
    String MapTop;
    String CalendTime;
    String Flight;
    String TVchoiseMap;
    String TVchoise_pointMap;

    Integer phoneNumber;


    private static final int RC_SIGN_IN = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //данные из main3Activity
        Intent main3Activity=getIntent();

        // телефон
        phoneNew=main3Activity.getStringExtra("phoneNew");
        // дата поездки
        Calend=main3Activity.getStringExtra("Calend");
        // рейс самолета
        RefplaneCity=main3Activity.getStringExtra("RefplaneCity");
        // маршрут такси
        RefMap=main3Activity.getStringExtra("RefMap");
        // пункт сбора
        RefPoint=main3Activity.getStringExtra("RefPoint");
        // время вылета/прилета/номер рейса для чартера
        time=main3Activity.getStringExtra("time");


        // скрыто 13.11.2020
//        refCity=main3Activity.getStringExtra("refCity");
//        toOrFrom=main3Activity.getStringExtra("toOrFrom");
//        MapTop=main3Activity.getStringExtra("MapTop");
//        Log.d(TAG, "MapTop: "+MapTop);
//        Calend=main3Activity.getStringExtra("Calend");
//        CalendTime=main3Activity.getStringExtra("CalendTime");
//        Flight=main3Activity.getStringExtra("Flight");
//        time=main3Activity.getStringExtra("time");
//        TVchoiseMap=main3Activity.getStringExtra("TVchoiseMap");
//        TVchoise_pointMap=main3Activity.getStringExtra("TVchoise_pointMap");

        TextHello1=findViewById(R.id.TextHello1);
        //GoMainActivity=findViewById(R.id.GoMainActivity);
        Layout1=findViewById(R.id.Layout1);
        Layout2=findViewById(R.id.Layout2);
        AuthNot=findViewById(R.id. AuthNot);
        TextProgress=findViewById(R.id. TextProgress);

        doPhoneLogin();
    }
    private void doPhoneLogin() {
//        Intent intent = AuthUI.getInstance().createSignInIntentBuilder()
//                .setIsSmartLockEnabled(!BuildConfig.DEBUG)
//                .setAvailableProviders(Collections.singletonList(
//                        new AuthUI.IdpConfig.PhoneBuilder().build()))
//                .setLogo(R.mipmap.ic_launcher)
//                .build();
//
//        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {
            IdpResponse idpResponse = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                showAlertDialog(user);

                //29.04.20 получить токен и телефон
                getMyToken();

                Layout1.setVisibility(View.VISIBLE);
                Layout2.setVisibility(View.VISIBLE);

            }
            else {
                AuthNot.setVisibility(View.VISIBLE);
            }
        }
    }
    // Всплывающая информация
    public void showAlertDialog(FirebaseUser user) {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Main2Activity.this);
        // Set Title
        mAlertDialog.setTitle("Авторизация успешна");

        // Set Message
        mAlertDialog
                .setMessage(" Номер телефона " + user.getPhoneNumber())
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        mAlertDialog.create();
        // Showing Alert Message
        mAlertDialog.show();
    }

// Auth is NOT кнопка перезапуска приложения
    public void GoMainActivity(View view){
        Intent GoMainActivity= new Intent(this,MainActivity.class);
        startActivity(GoMainActivity);
    }



    public void getMyToken(){

        //получение теелфона
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser ghg = mAuth.getCurrentUser();
        phoneUser=ghg.getPhoneNumber();

//        //получение токена
//        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(Main2Activity.this,new OnSuccessListener<InstanceIdResult>() {
//            @Override
//            public void onSuccess(InstanceIdResult instanceIdResult) {
//                UserToken = instanceIdResult.getToken();
//
//            }
//        });
    }

// реализация шифрования кнопка пропустить
    public void GoMainOder(View view){
        Log.d(TAG, "Старт шифрования");

        TextProgress.setVisibility(View.VISIBLE);
        Layout1.setVisibility(View.GONE);
        Layout2.setVisibility(View.GONE);

        IneternetYES="";

        //ТАЙМ-АУТ проверка интернета
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Завершен ТАЙМ-АУТ проверка интернета
                IneternetYES="Out";
                IneternetYesNo();
            }
            },40000);


        //050720 реализация шифрования
        //запись phone to БД secret
        databaseSecret = FirebaseDatabase.getInstance();
        refSecret = databaseSecret.getReference("Пользователи")
                .child("Cipher")
                .child(phoneUser);
        refSecret.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                refSecret.child("phone").setValue(phoneUser);

                Log.d(TAG, "Телефон для шифрования записан");
                //получаем СС номер
                QwerySecret();
                // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД БД ЗАЯВКИ...-...-...-"CheckStopOder"...
                refSecret.removeEventListener(this);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        }
        );
    }

    //получаем СС номер
    public void QwerySecret(){
        Log.d(TAG, "Получаем секретный номер");

        phoneNew="";

        final Query secret= FirebaseDatabase.getInstance().getReference("Пользователи")
                .child("Cipher")
                .child(phoneUser)
                .child("secretNumber")
                .orderByChild("number");
        secret.addChildEventListener( new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String number=dataSnapshot.child( "numberCrypt" ).getValue(String.class);
                Log.d(TAG, "Секретный номер"+number);

                phoneNew=number;

                // Проверяем закончилось ли время опроса интернета
                checkInternetYesNo();

                //Останавливаем прослушивание, чтобы в приложении у другого пользователя не появлялась информация когда другой пользоваьель регистрирует заявку
                secret.removeEventListener(this);
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        } );
    }

    public void checkInternetYesNo(){
        if(IneternetYES.equals("Out")){
            Log.d(TAG, "СС номер получен, но время проверки интернета вышло");
        }
        else if(!phoneNew.isEmpty()){
            Log.d(TAG, "CC получен, старт регистрации заявки");
            btnInsertd();
        }
    }

    public void IneternetYesNo(){
        if (!phoneNew.isEmpty()){
            Log.d(TAG, "время проверки интернета вышло, но номер СС получен");
        }
        else{
            Log.d(TAG, "Время проверки вышло, not internet");
            showAlertDialog4();
        }
    }

    //запись  в БД персональные данные
    public void btnInsertd(){

        keyReg="";
        checkInternet="";
        writeData="";

        //ТАЙМ-АУТ ЗАПРОСА ИНТЕРНЕТА
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Завершен ТАЙМ-АУТ ЗАПРОСА ИНТЕРНЕТА при записи данных в БД
                checkInternet="Out";
                Log.d(TAG, "Записан checkInternet=Out");
                inetNotWhenGoCheckRegistration();
            }
        },15000);


        //030320 Запись токена для проверки Разрешения на запись заявки в БД ЗАЯВКИ...-...-...-"CheckStopOder"...
        database01 = FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Пользователи")
                .child("Personal")
                .child(phoneNew)
                .child("Private");
        ref01.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            ref01.child("token").setValue(UserToken);
                                            // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД БД ЗАЯВКИ...-...-...-"CheckStopOder"...
                                            ref01.removeEventListener(this);

                                            writeData="Yes";
                                            getMainList();
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    }
        );
    }

    public void inetNotWhenGoCheckRegistration(){
        if(writeData.equals("Yes")){
            Log.d(TAG, "таймер остановлен");/*специально пусто*/}

        else {
            //пропал интернет во время проверки наличия регистрации
            Log.d(TAG, "Интернета пропал при записи данных");
            Intent aaa = new Intent(this,InternetNot.class);
            startActivity(aaa);
        }
    }

    public void getMainList(){
        Log.d(TAG, "вход в проверку getMainList");

        if(checkInternet.equals("Out")){
            Log.d(TAG, "getMainList остановлен");

            //return нужен чтобы при возобноблении интернета автоматически не переходило на лист с заявками
            return;
        }

        Intent Main3Activity=new Intent(this,Main3Activity.class);

        //регистрация завершена успешно передаем Ok в main3Activity в лист регистрации заявки
        Main3Activity.putExtra("authOk","Ok");

        //параметры заявки полученные из main3Activity возвращаем обратно в main3Activity
        // телефон
        Main3Activity.putExtra("phoneNew",phoneNew);
        // дата поездки
        Main3Activity.putExtra("Calend",Calend);
        // рейс самолета
        Main3Activity.putExtra("RefplaneCity",RefplaneCity);
        // маршрут такси
        Main3Activity.putExtra("RefMap",RefMap);
        // пункт сбора
        Main3Activity.putExtra("RefPoint",RefPoint);
        // время вылета/прилета/номер рейса для чартера
        Main3Activity.putExtra("time",time);

        // скрыто 13.11.2020
//        Main3Activity.putExtra("refCity",refCity);
//        Main3Activity.putExtra("toOrFrom",toOrFrom);
//        Main3Activity.putExtra("MapTop",MapTop);
//        Main3Activity.putExtra("Calend",Calend);
//        Main3Activity.putExtra("CalendTime",CalendTime);
//        Main3Activity.putExtra("Flight",Flight);
//        Main3Activity.putExtra("time",time);
//        Main3Activity.putExtra("TVchoiseMap",TVchoiseMap);
//        Main3Activity.putExtra("TVchoise_pointMap",TVchoise_pointMap);
//        Main3Activity.putExtra("phoneNew",phoneNew);

        startActivity(Main3Activity);
    }

    public void showAlertDialog4(){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(
                Main2Activity.this);
        // Set Title
        mAlertDialog.setTitle("Ошибка регистрации");
        mAlertDialog.setCancelable(false);
        // Set Message
        mAlertDialog
                .setMessage("проверьте настройки интернета")
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        TextProgress.setVisibility(View.GONE);
                        Layout1.setVisibility(View.VISIBLE);
                        Layout2.setVisibility(View.VISIBLE);

                    }
                });
        mAlertDialog.create();
        // Showing Alert Message
        mAlertDialog.show();
    }

    // кнопка Back сворачивает приложение
    @Override
    public void onBackPressed(){
        this.moveTaskToBack(true);
    }

    }
