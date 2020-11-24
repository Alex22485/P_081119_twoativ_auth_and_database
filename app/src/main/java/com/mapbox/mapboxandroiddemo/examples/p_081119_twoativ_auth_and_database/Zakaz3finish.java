package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class Zakaz3finish extends AppCompatActivity {

    //финишный лист заказа с кнопкой зарегистрировать заказ

    private static final String TAG ="Zakaz3finish" ;

    String newToken;
    // формируется ok после ввода телефона при авторизации
    String authOK="";

    String phoneNew,Calend,RefplaneCity,time,RefMap,RefPoint;
    TextView Calend1,RefMap1,RefPoint1,RefplaneCity1,time1,NumberCharter;
    TextView text0,text1,text2,text3,text4,text5,text6,text7;
    Button btnTime,btnOder,button9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakaz3finish);

        // get TOKEN new with 13/11/2020
        FirebaseMessaging. getInstance (). getToken ()
                . addOnCompleteListener ( new OnCompleteListener< String >() {
                    @Override
                    public void onComplete ( @NonNull Task< String > task ) {
                        if (! task . isSuccessful ()) {
                            Log . w ( TAG , "Fetching FCM registration token failed" , task . getException ());
                            return ;
                        }

                        // Get new FCM registration token
                        newToken = task . getResult ();

                        // Log and toast
                        Log . d ( TAG , newToken );
                        //Toast. makeText ( Zakaz3finish . this , newToken , Toast . LENGTH_SHORT ). show ();
                    }
                });

        Calend1=findViewById(R.id.Calend1);
        RefMap1=findViewById(R.id.RefMap1);
        RefPoint1=findViewById(R.id.RefPoint1);
        RefplaneCity1=findViewById(R.id.RefplaneCity1);
        time1=findViewById(R.id.time1);
        text0=findViewById(R.id.text0);
        text1=findViewById(R.id.text1);
        text2=findViewById(R.id.text2);
        text3=findViewById(R.id.text3);
        NumberCharter=findViewById(R.id.NumberCharter);
        text4=findViewById(R.id.text4);
        text5=findViewById(R.id.text5);
        text6=findViewById(R.id.text6);
        text7=findViewById(R.id.text7);
        btnTime=findViewById(R.id.btnTime);
        btnOder=findViewById(R.id.btnOder);
        button9=findViewById(R.id.button9);

        // from Main2Activity- auth if auth is OK (phone is Right)
        Intent Main2AcivityToZakaz3finish= getIntent();
        authOK= "K"+ Main2AcivityToZakaz3finish.getStringExtra("authOk");
        Log.d(TAG, "authOk: "+authOK);

        if(authOK.equals("KOk")){
            //TextProgress.setVisibility(View.VISIBLE);
            Log.d(TAG, "автоматическая регистрация после авторизации: ");

//            refCity=Main3Activity.getStringExtra("refCity");
//            toOrFrom=Main3Activity.getStringExtra("toOrFrom");
//            MapTop=Main3Activity.getStringExtra("MapTop");
//            Calend.setText(Main3Activity.getStringExtra("Calend"));
//            CalendTime.setText(Main3Activity.getStringExtra("CalendTime"));
//            Flight.setText(Main3Activity.getStringExtra("Flight"));
//            time.setText(Main3Activity.getStringExtra("time"));
//            TVchoiseMap=Main3Activity.getStringExtra("TVchoiseMap");
//            phoneNew=Main3Activity.getStringExtra("phoneNew");

            // телефон
            phoneNew=Main2AcivityToZakaz3finish.getStringExtra("phoneNew");
            // дата поездки
            Calend=Main2AcivityToZakaz3finish.getStringExtra("Calend");
            // рейс самолета
            RefplaneCity=Main2AcivityToZakaz3finish.getStringExtra("RefplaneCity");
            // маршрут такси
            RefMap=Main2AcivityToZakaz3finish.getStringExtra("RefMap");
            // пункт сбора
            RefPoint=Main2AcivityToZakaz3finish.getStringExtra("TVchoiseMap");
            // время вылета/прилета/номер рейса для чартера
            time=Main2AcivityToZakaz3finish.getStringExtra("Calend");

            // автоматическая регистрация ранее сформированной заявки после авторизации
            //задержка чтобы успел записаться NO в БД Заявки из другого кода
            Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast. makeText ( Zakaz3finish . this , "готовность для регистрации заявки" , Toast . LENGTH_SHORT ). show ();
                    //автоматическая регистрация ранее сформированной заявки
                    //btnInsertd ();
                }
            },4000);
        }

        // получение данных из Zakaz1
        Intent Zakaz1ToZakaz3finish = getIntent();
        // secretNumber
        phoneNew=""+Zakaz1ToZakaz3finish.getStringExtra("phoneNew");
        // дата полета
        Calend=""+Zakaz1ToZakaz3finish.getStringExtra("Calend");
        // рейс самолета
        RefplaneCity=""+Zakaz1ToZakaz3finish.getStringExtra("RefplaneCity");
        // время вылета/прилета/номер рейса для чартера
        time=""+Zakaz1ToZakaz3finish.getStringExtra("time");
        // маршрут
        RefMap=""+Zakaz1ToZakaz3finish.getStringExtra("RefMap");
        // точка сбора
        RefPoint=""+Zakaz1ToZakaz3finish.getStringExtra("RefPoint");

        if (time.equals("1 рейс")||time.equals("2 рейс")||time.equals("3 рейс")){
            text3.setText("Чартер ");
            NumberCharter.setText(time);
            btnTime.setVisibility(View.VISIBLE);
            time1.setVisibility(View.INVISIBLE);
        }
        else{
            time1.setText(time);
            btnTime.setVisibility(View.INVISIBLE);
        }
        Calend1.setText(Calend);
        RefMap1.setText(RefMap);
        RefPoint1.setText(RefPoint);
        RefplaneCity1.setText(RefplaneCity);

        Log.d(TAG, "phoneNew:"+phoneNew);
        Log.d(TAG, "Calend:"+Calend);
        Log.d(TAG, "RefplaneCity:"+RefplaneCity);
        Log.d(TAG, "time:"+time);
        Log.d(TAG, "RefMap:"+RefMap);
        Log.d(TAG, "RefPoint:"+RefPoint);
    }
    public void btnOder(View view) {
        if (phoneNew.equals("null")) {

            AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz3finish.this);
            mAlertDialog.setMessage("Для продолжения необходимо" +
                    " авторизироваться." +
                    " Вам придет SMS c кодом подтверждения")
                    .setPositiveButton("Принять", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            goListRegistration();
                        }
                    })
                    .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            mAlertDialog.create();
            mAlertDialog.show();
        }
        else {
            //btnInsertd();
        }
        }
    public void goListRegistration(){
        Intent Zakaz3finishToMain2AcivityTo =new Intent(this, Proba.class);
        // телефон
        Zakaz3finishToMain2AcivityTo.putExtra("phoneNew",phoneNew);
        // дата поездки
        Zakaz3finishToMain2AcivityTo.putExtra("Calend",Calend);
        // рейс самолета
        Zakaz3finishToMain2AcivityTo.putExtra("RefplaneCity",RefplaneCity);
        // маршрут такси
        Zakaz3finishToMain2AcivityTo.putExtra("RefMap",RefMap);
        // пункт сбора
        Zakaz3finishToMain2AcivityTo.putExtra("RefPoint",RefPoint);
        // время вылета/прилета/номер рейса для чартера
        Zakaz3finishToMain2AcivityTo.putExtra("time",time);
        startActivity(Zakaz3finishToMain2AcivityTo);
    }
}