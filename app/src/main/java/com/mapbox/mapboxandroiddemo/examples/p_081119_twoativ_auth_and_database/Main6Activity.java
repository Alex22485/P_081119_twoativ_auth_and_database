package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;


public class Main6Activity extends AppCompatActivity {

    private static final String TAG ="Main6Activity" ;

    FirebaseDatabase database02;
    DatabaseReference ref02;

    String checkregistrationTimeOut;

    FirebaseDatabase ggg;
    DatabaseReference mmm;

    Button cancelOder;
    Button detailsTrip;
    String userPhone;

    String[] CancelOderWhy ={"Самолет отменён","Передумал", };

    String data;
    String map;
    String roar_number;
    String road_name;
    String flidht_number;
    Integer peopleOder;
    String сarDrive;
    String token;

    String data1;
    String map1;
    String roar_number1;
    String road_name1;
    String flidht_number1;
    String peopleOder1;
    String сarDrive1;
    String token1;

    FirebaseAuth mAuth;

    TextView Calend_Out,flight_number_Out,Map,road_number_out,road_name_out;
    TextView number;
    TextView information2;
    TextView people;
    TextView people2;
    TextView process;
    TextView process1;
    TextView process2;
    TextView process3;
    TextView searchCar;
    TextView TextData;
    TextView TextFlight;
    TextView TextMap;
    TextView TextPoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main6 );

        Log.d(TAG, "onCreate");/*специально пусто*/

        number = findViewById( R.id.number );
        Calend_Out=findViewById( R.id.Calend_Out );
        flight_number_Out=findViewById( R.id.flight_number_Out );
        Map=findViewById( R.id.Map );
        road_number_out=findViewById( R.id.road_number_out );
        road_name_out=findViewById( R.id.road_name_out );
        information2=findViewById( R.id.information2 );
        people=findViewById( R.id.people );
        people2=findViewById( R.id.people2 );
        process=findViewById( R.id.process );
        process1=findViewById( R.id.process1 );
        process2=findViewById( R.id.process2 );
        process3=findViewById( R.id.process3 );
        searchCar=findViewById( R.id.searchCar );
        cancelOder=findViewById( R.id.cancelOder );
        detailsTrip=findViewById( R.id.detailsTrip );

        TextFlight=findViewById( R.id.TextFlight );
        TextData=findViewById( R.id.TextData );
        TextMap=findViewById( R.id.TextMap );
        TextPoint=findViewById( R.id.TextPoint );

        data1="";

        //полуаем phone пользователя
        mAuth= FirebaseAuth.getInstance(  );
        FirebaseUser user=mAuth.getCurrentUser();
        userPhone = user.getPhoneNumber();
        Log.d(TAG, "получен телефон"+userPhone);/*специально пусто*/

        number.setText("Поиск Заявок...");

//        //Старт Проверка интернета+статус заявок
//        Handler handler1 = new Handler();
//        handler1.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                getStatus();
//                Log.d(TAG, "Считывание СТАТУСА");/*специально пусто*/
//            }
//        },700);
    }

    @Override
    protected void onStart (){
        super.onStart();
        Log.d(TAG, "onStart");

        //Старт Проверка интернета+статус заявок
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                getStatus();
                Log.d(TAG, "Считывание СТАТУСА");/*специально пусто*/
            }
        },700);

    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "onPause");
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "onResume");
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG, "onRestart");
    }
    @Override
    protected void onStop (){
        super.onStop();
        Log.d(TAG, "onStop");

                //Intent mIntent = getIntent();
                finish();
                //startActivity(mIntent);
    }



    public void getStatus(){

        //registration="";
        checkregistrationTimeOut="";

        //ТАЙМ-АУТ ЗАПРОСА ИНТЕРНЕТА
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {

                // Завершен ТАЙМ-АУТ ЗАПРОСА ИНТЕРНЕТА
                checkregistrationTimeOut="Out";
                inetNotWhenGoCheckRegistration();
            }
        },15000);


        final Query aaa= FirebaseDatabase.getInstance().getReference("Пользователи")
                .child("Personal")
                .child( userPhone )
                .orderByChild("Status");
        aaa.addChildEventListener( new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                 data=dataSnapshot.child( "дата" ).getValue(String.class);
                 map=dataSnapshot.child( "направление" ).getValue(String.class);
                 roar_number=dataSnapshot.child( "маршрут_номер" ).getValue(String.class);
                 road_name=dataSnapshot.child( "маршрут_точкаСбора" ).getValue(String.class);
                 flidht_number=dataSnapshot.child( "рейс_самолета" ).getValue(String.class);
                 token=dataSnapshot.child( "token" ).getValue(String.class);
                 peopleOder=dataSnapshot.child("Человек_в_Заявке").getValue(Integer.class);
                 сarDrive=dataSnapshot.child("Автомобиль").getValue(String.class);

                 //ТАЙМ-АУТ чтобы данные успели считаться
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        writeString();
                    }
                },100);

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

    public void writeString(){
        data1=""+data;
        map1=""+map;
        roar_number1=""+roar_number;
        road_name1=""+road_name;
        flidht_number1=""+flidht_number;
        token1=""+token;
        peopleOder1=""+peopleOder;
        сarDrive1=""+сarDrive;

        CheckNullStatus();

                Log.d(TAG, "дата"+data1);/*специально пусто*/
                Log.d(TAG, "направление"+map1);/*специально пусто*/
                Log.d(TAG, "маршрут"+roar_number1);/*специально пусто*/
                Log.d(TAG, "точка сбора"+road_name1);/*специально пусто*/
                Log.d(TAG, "рейс"+flidht_number1);/*специально пусто*/
                Log.d(TAG, "токен"+token1);/*специально пусто*/
                Log.d(TAG, "кол-во человек"+peopleOder1);/*специально пусто*/
                Log.d(TAG, "автомобиль"+сarDrive1);/*специально пусто*/

    }

    //Проверка интернета во время проверки регистрации
    public void inetNotWhenGoCheckRegistration (){

            if(!data1.isEmpty()){
            Log.d(TAG, "таймер остановлен");/*специально пусто*/}

        else {
            //пропал интернет при считывании Статуса
            Log.d(TAG, "Интернета пропал при считывании статуса");
            Intent aaa = new Intent(this,Main6ActivityNotInternet.class);
            startActivity(aaa);
        }
    }



    public void CheckNullStatus(){

        Log.d(TAG, "Метод CheckNullStatus ");/*специально пусто*/

        if (checkregistrationTimeOut.equals("Out")){
            Log.d(TAG, "проверка интернета время вышло");/*специально пусто*/
        }
        else{
            if(data1.equals("null")){
            number.setText( "заявка НЕ оформлена" );
            number.setTextColor(getResources().getColor( R.color.colorNew ));

            Log.d(TAG, "Заявок нет");/*специально пусто*/
                }
                else if(!data1.isEmpty()) {
                    Log.d(TAG, "Заявка оформлена");/*специально пусто*/

                    number.setText( "заявка оформлена" );
                    Calend_Out.setText( data1 );
                    Map.setText( map1 );
                    road_number_out.setText( roar_number1 );
                    road_name_out.setText( road_name1 );
                    flight_number_Out.setText( flidht_number1 );
                    people.setText(""+peopleOder1);

            //делаем текст видимым
            TextFlight.setVisibility(View.VISIBLE);
            TextData.setVisibility(View.VISIBLE);
            TextMap.setVisibility(View.VISIBLE);
            TextPoint.setVisibility(View.VISIBLE);
            searchCar.setVisibility(View.VISIBLE);
            information2.setVisibility(View.VISIBLE);
            people2.setVisibility(View.VISIBLE);
            //стрелочки
            process.setVisibility(View.VISIBLE);
            process1.setVisibility(View.VISIBLE);
            process2.setVisibility(View.VISIBLE);
            process3.setVisibility(View.VISIBLE);
            //кнопка Отменить заявку
            cancelOder.setVisibility(View.VISIBLE);

            if (сarDrive1.equals("null")){
                Log.d(TAG, "Автомобиль не найден");/*специально пусто*/
            }
            else {
                searchCar.setText("Найден автомобиль"+сarDrive1);
            }
        }
        }
        }
// кнопка Back сворачивает приложение
  @Override
   public void onBackPressed(){
     this.moveTaskToBack(true);
    }

// Отмена заявки
    public void cancelOder (View view){
        AlertDialog.Builder builder=new AlertDialog.Builder( Main6Activity.this );
        builder.setTitle( "Укажите причину отмены");
        //builder.setCancelable( false );
        builder.setItems( CancelOderWhy, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                ggg = FirebaseDatabase.getInstance();
                mmm = ggg.getReference("Заявки")
                        .child(Map.getText().toString() )
                        .child( Calend_Out.getText().toString() )
                        //.child(road_number_out.getText().toString())
                        .child(flight_number_Out.getText().toString()  )
                        .child(road_number_out.getText().toString())
                        .child(road_name_out.getText().toString())
                        .child("notificationTokens");
                mmm.child( token1 ).removeValue();

                //28 02 2020  задержка на удаление из БД, нужна для правильного подсчета человек в БД ЗАявкиServerApp
                Handler handler = new Handler();
                handler.postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        mmm = ggg.getReference("Пользователи")
                                .child("Personal")
                                .child(userPhone);
                        mmm.child( "Status" ).removeValue();
                        Toast.makeText(Main6Activity.this,"Заявка Отменена....",Toast.LENGTH_LONG).show();

                        Calend_Out.setText( "" );
                        Map.setText( "" );
                        road_number_out.setText( "" );
                        road_name_out.setText( "" );
                        flight_number_Out.setText( "" );
                        people.setText("");
                        number.setText( "заявка НЕ оформлена" );
                        number.setTextColor(getResources().getColor( R.color.colorNew ));

                        //делаем текст видимым
                        TextFlight.setVisibility(View.INVISIBLE);
                        TextData.setVisibility(View.INVISIBLE);
                        TextMap.setVisibility(View.INVISIBLE);
                        TextPoint.setVisibility(View.INVISIBLE);
                        searchCar.setVisibility(View.INVISIBLE);
                        information2.setVisibility(View.INVISIBLE);
                        people2.setVisibility(View.INVISIBLE);
                        //стрелочки
                        process.setVisibility(View.INVISIBLE);
                        process1.setVisibility(View.INVISIBLE);
                        process2.setVisibility(View.INVISIBLE);
                        process3.setVisibility(View.INVISIBLE);
                        //кнопка  Отменить заявку
                        cancelOder.setVisibility(View.INVISIBLE);
                    }
                },1000
                );
            }
        }
        );

        builder.setNeutralButton("Назад", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void backMainList (View view){
        Intent qwe= new Intent(this,Choose_direction.class);
        startActivity(qwe);
    }
}


//// Кнопка обновить информацию перезапуск активити
//public void btnStatus(View view) {
//        //перезапуск активити
//        restartActivity();
//}
//
//    public void restartActivity(){
//        Intent mIntent = getIntent();
//        finish();
//        startActivity(mIntent);
//    }