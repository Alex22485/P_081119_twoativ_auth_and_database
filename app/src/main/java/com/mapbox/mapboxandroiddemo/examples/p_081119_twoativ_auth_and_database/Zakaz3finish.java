package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class Zakaz3finish extends AppCompatActivity {

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

        // Получить Токен!!!!
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(Zakaz3finish.this,new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        newToken = instanceIdResult.getToken();
                        Log.d(TAG, "newToken: "+newToken);
                    }
                }
        );
        // экспорт из листа регисттрации после успешного ввода телефона поучаем OK
        Intent Main3Activity= getIntent();
        authOK= "K"+ Main3Activity.getStringExtra("authOk");
        Log.d(TAG, "authOk: "+authOK);

        if(authOK.equals("KOk")){
            TextProgress.setVisibility(View.VISIBLE);
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
            phoneNew=Main3Activity.getStringExtra("phoneNew");
            // дата поездки
            Calend=Main3Activity.getStringExtra("Calend");
            // рейс самолета
            RefplaneCity=Main3Activity.getStringExtra("RefplaneCity");
            // маршрут такси
            RefMap=Main3Activity.getStringExtra("RefMap");
            // пункт сбора
            RefPoint=Main3Activity.getStringExtra("TVchoiseMap");
            // время вылета/прилета/номер рейса для чартера
            time=Main3Activity.getStringExtra("Calend");

            // автоматическая регистрация ранее сформированной заявки после авторизации
            //задержка чтобы успел записаться NO в БД Заявки из другого кода
            Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    btnInsertd ();
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

        else {
                btnInsertd();
            }
        }
    }
    public void goListRegistration(){
        Intent main3Activity =new Intent(this, Zakaz3finish.class);
        main3Activity.putExtra("refCity",refCity);
        main3Activity.putExtra("toOrFrom",toOrFrom);
        main3Activity.putExtra("MapTop",MapTop);
        main3Activity.putExtra("Calend",Calend.getText().toString());
        main3Activity.putExtra("CalendTime",CalendTime.getText().toString());
        main3Activity.putExtra("Flight",Flight.getText().toString());
        main3Activity.putExtra("time",time.getText().toString());
        main3Activity.putExtra("TVchoiseMap",TVchoiseMap);
        main3Activity.putExtra("TVchoise_pointMap",TVchoise_pointMap);
        startActivity(main3Activity);
    }
}