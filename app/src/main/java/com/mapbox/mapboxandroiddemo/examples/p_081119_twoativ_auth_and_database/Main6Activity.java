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


public class Main6Activity extends AppCompatActivity {

    FirebaseDatabase ggg;
    DatabaseReference mmm;

    Button btnStatus;
    Button cancelOder;
    Button detailsTrip;
    String userI;
    String token;
    String[] CancelOderWhy ={"Самолет отменён","Передумал", };

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

        btnStatus = findViewById( R.id.btnStatus );
        number = findViewById( R.id.number );
        Calend_Out=findViewById( R.id.Calend_Out );
        flight_number_Out=findViewById( R.id.flight_number_Out );
        Map=findViewById( R.id.Map );
        road_number_out=findViewById( R.id.road_number_out );
        road_name_out=findViewById( R.id.road_name_out );
        //information=findViewById( R.id.information );
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

        mAuth= FirebaseAuth.getInstance(  );
        FirebaseUser user=mAuth.getCurrentUser();
        //полуаем id пользователя
        userI=user.getUid();

        // Disable Button "Отменить заявку" if Text is Empty
//        Calend_Out.addTextChangedListener( loginTextWather );
//        Map.addTextChangedListener( loginTextWather );
//        road_number_out.addTextChangedListener( loginTextWather );
//        road_name_out.addTextChangedListener( loginTextWather );
//        flight_number_Out.addTextChangedListener( loginTextWather );

        // Проверка есть ли интернет
        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            number.setText("Поиск Заявок...");

            //задержка запроса из БД для полного завершения функций из nod js
            Handler handler0 = new Handler();
            handler0.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getStatus();

                }
            },3000);

            //задержка запроса из БД для полного завершения функций из nod js
            Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getNull();
                }
            },10000);

        }
        else {
            new AlertDialog.Builder(this)
                    .setTitle("Ошибка!!!")
                    .setMessage("Пожалуйста, проверьте соединение с сетью")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            number.setText("ошибка загрузки данных");
                            btnStatus.setVisibility(View.VISIBLE);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

    }

    public void getNull(){
        String numberChesk=number.getText().toString();
        String Chrsk="заявка оформлена";

        if(numberChesk.equals(Chrsk)){
        }
        else {number.setText( "заявка НЕ оформлена" );
            number.setTextColor(getResources().getColor( R.color.colorNew ));}
    }

    public void getStatus(){
        Query aaa= FirebaseDatabase.getInstance().getReference("Пользователи").child( userI )
                .orderByChild( "Status" );
        aaa.addChildEventListener( new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String data=dataSnapshot.child( "дата" ).getValue(String.class);
                String map=dataSnapshot.child( "направление" ).getValue(String.class);
                String roar_number=dataSnapshot.child( "маршрут_номер" ).getValue(String.class);
                String road_name=dataSnapshot.child( "маршрут_точкаСбора" ).getValue(String.class);
                String flidht_number=dataSnapshot.child( "рейс_самолета" ).getValue(String.class);
                token=dataSnapshot.child( "token" ).getValue(String.class);
                Integer peopleOder=dataSnapshot.child("Человек_в_Заявке").getValue(Integer.class);
                String сarDrive=dataSnapshot.child("Автомобиль").getValue(String.class);
                Log.d("TAG", ""+сarDrive);

                Calend_Out.setText( data );
                Map.setText( map );
                road_number_out.setText( roar_number );
                road_name_out.setText( road_name );
                flight_number_Out.setText( flidht_number );
                people.setText(""+peopleOder);
                number.setText( "заявка оформлена" );

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
                //кнопка Обновить, Отменить заявку
                btnStatus.setVisibility(View.VISIBLE);
                cancelOder.setVisibility(View.VISIBLE);


//                //Останавливаем прослушивание, чтобы обновилась информация (т.е. старая заявка не отображалась)
//                aaa.removeEventListener(this);

                if (сarDrive == null){}
                else {
                    searchCar.setText("Найден автомобиль"+сarDrive);
                }
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
// кнопка Back сворачивает приложение
  @Override
   public void onBackPressed(){
     this.moveTaskToBack(true);
    }


// Кнопка обновить информацию перезапуск активити
public void btnStatus(View view) {
        //перезапуск активити
        restartActivity();
}

    public void restartActivity(){
        Intent mIntent = getIntent();
        finish();
        startActivity(mIntent);
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
                mmm.child( token ).removeValue();

                //28 02 2020  задержка на удаление из БД, нужна для правильного подсчета человек в БД ЗАявкиServerApp
                Handler handler = new Handler();
                handler.postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        mmm = ggg.getReference("Заявки")
                                .child(Map.getText().toString() )
                                .child( Calend_Out.getText().toString() )
                                //.child(road_number_out.getText().toString())
                                .child(flight_number_Out.getText().toString()  )
                                .child(road_number_out.getText().toString())
                                .child(road_name_out.getText().toString())
                                .child("Users");
                        mmm.child( userI ).removeValue();

                        mmm = ggg.getReference("Пользователи")
                                .child(userI);
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
                        //кнопка Обновить, Отменить заявку
                        btnStatus.setVisibility(View.INVISIBLE);
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
    //временная кнопка переход на лист регистрации
    public void backMain2List (View view){
        Intent backMain2List= new Intent(this,Main2Activity.class);
        startActivity(backMain2List);
    }
}