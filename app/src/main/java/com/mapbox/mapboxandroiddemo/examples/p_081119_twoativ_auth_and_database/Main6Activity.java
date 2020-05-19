package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;


public class Main6Activity extends AppCompatActivity {

    private static final String TAG ="Main6Activity" ;

    String timeOut;
    String proverka;

    FirebaseDatabase database01;
    DatabaseReference ref01;

    FirebaseDatabase ggg;
    DatabaseReference mmm;

    Button cancelOder;
    Button detailsTrip;
    Button BtnNewOrder;
    String userPhone;

    String[] CancelOderWhy ={"Самолет отменён","Передумал", };

    String token;

    FirebaseAuth mAuth;

    TextView Calend_Out;
    TextView flight_number_Out;
    TextView Map;
    TextView road_number_out;
    TextView road_name_out;
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

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main6 );

        Log.d(TAG, "onCreate");/*специально пусто*/

        progressBar = findViewById( R.id.progressBar );

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
        BtnNewOrder=findViewById( R.id.BtnNewOrder );


        TextFlight=findViewById( R.id.TextFlight );
        TextData=findViewById( R.id.TextData );
        TextMap=findViewById( R.id.TextMap );
        TextPoint=findViewById( R.id.TextPoint );


        //полуаем phone пользователя
        mAuth= FirebaseAuth.getInstance(  );
        FirebaseUser user=mAuth.getCurrentUser();
        userPhone = user.getPhoneNumber();
        Log.d(TAG, "получен телефон"+userPhone);/*специально пусто*/

        // Получить Токен!!!!
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(Main6Activity.this,new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                token = instanceIdResult.getToken();
                Log.d(TAG, "получен токен: "+token);
            }
        });

        number.setText("Поиск Заявок...");
        progressBar.setVisibility(View.VISIBLE);

        //Старт Проверка интернета+статус заявок
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                readYesNoEmpty();
                Log.d(TAG, "Считывание Yes/No/empty");/*специально пусто*/
            }
        },2000);


    }

    @Override
    protected void onStart (){
        super.onStart();
        Log.d(TAG, "onStart");
        }

    @Override
    protected void onStop (){
        super.onStop();
        Log.d(TAG, "onStop");
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

    public void readYesNoEmpty(){

        timeOut="";
        proverka="";


        //ТАЙМ-АУТ ЗАПРОСА ИНТЕРНЕТА
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {

                // Завершен ТАЙМ-АУТ ЗАПРОСА ИНТЕРНЕТА-2 при проверке регистрации
                timeOut="Out";
                internetNot();
            }
        },15000);

        Log.d(TAG, "Чтение Yes/No из БД");
        //Чтение Yes/No из БД
        database01 = FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Пользователи")
                .child("Personal")
                .child(userPhone)
                .child("Proverka")
                .child("Заявка");
        ref01.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                proverka=dataSnapshot.getValue(String.class);
                Log.d(TAG, "запрос регистрации получен"+proverka);

                // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД БД ЗАЯВКИ...-...-...-"CheckStopOder"...
                ref01.removeEventListener(this);
                checkWordProverka();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void internetNot(){
        if (proverka.endsWith("Yes")){
            Log.d(TAG, "proverka Yes");
        }
        else if (proverka.endsWith("No")){
            Log.d(TAG, "proverka No");
        }
        else {
            Log.d(TAG, "not internet");
            Intent Main6ActivityNotInternet  = new Intent(this,Main6ActivityNotInternet.class);
            startActivity(Main6ActivityNotInternet);
        }
    }

    public void checkWordProverka(){

        if (timeOut.equals("Out")){
            Log.d(TAG, "time out");
        }
        else if(proverka.equals("Yes")){
            Log.d(TAG, "Заявка есть");
            readOder();
        }
        else if(proverka.equals("No")){
            Log.d(TAG, "Заявок нет");
            setNotText();
        }
        else{
            Log.d(TAG, "в БД null");
        }
    }

    public void readOder(){

        Query aaa1= FirebaseDatabase.getInstance().getReference("Пользователи")
                .child("Personal")
                .child( userPhone )
                .orderByChild("Status");
        aaa1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String data=dataSnapshot.child( "дата" ).getValue(String.class);
                String map=dataSnapshot.child( "направление" ).getValue(String.class);
                String roar_number=dataSnapshot.child( "маршрут_номер" ).getValue(String.class);
                String road_name=dataSnapshot.child( "маршрут_точкаСбора" ).getValue(String.class);
                String flidht_number=dataSnapshot.child( "рейс_самолета" ).getValue(String.class);
                Integer peopleOder=dataSnapshot.child("Человек_в_Заявке").getValue(Integer.class);
                String сarDrive=dataSnapshot.child("Автомобиль").getValue(String.class);

                Calend_Out.setText( data );
                Map.setText( map );
                road_number_out.setText( roar_number );
                road_name_out.setText( road_name );
                flight_number_Out.setText( flidht_number );
                people.setText(""+peopleOder);

                number.setText( "заявка оформлена" );
                progressBar.setVisibility(View.INVISIBLE);

                if (сarDrive==null){
                    Log.d(TAG, "Автомобиль не найден");/*специально пусто*/
                }
                else {
                    searchCar.setText("Найден автомобиль "+сarDrive);
                }
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
        });
    }

    public void setNotText(){
        Calend_Out.setText("");
        Map.setText("");
        road_number_out.setText("");
        road_name_out.setText("");
        flight_number_Out.setText("");
        people.setText("");
        number.setText( "заявка не оформлена" );
        progressBar.setVisibility(View.INVISIBLE);


        //делаем текст НЕ видимым
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
        //кнопка Отменить заявку
        cancelOder.setVisibility(View.INVISIBLE);

        BtnNewOrder.setVisibility(View.VISIBLE);

    }

// кнопка Back сворачивает приложение
  @Override
   public void onBackPressed(){
     this.moveTaskToBack(true);
    }

    public void backMainList(View view){
        Intent Choose_direction =new Intent(this,Choose_direction.class);
        startActivity(Choose_direction);
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

                Toast.makeText(Main6Activity.this,"Заявка Отменена....",Toast.LENGTH_LONG).show();
                setNotText();

//                //28 02 2020  задержка на удаление из БД, нужна для правильного подсчета человек в БД ЗАявкиServerApp
//                Handler handler = new Handler();
//                handler.postDelayed( new Runnable() {
//                    @Override
//                    public void run() {
//                        mmm = ggg.getReference("Пользователи")
//                                .child("Personal")
//                                .child(userPhone);
//                        mmm.child( "Status" ).removeValue();
//
//                        //CheckDelOder();
//
//                        Toast.makeText(Main6Activity.this,"Заявка Отменена....",Toast.LENGTH_LONG).show();
//                        setNotText();
//
//                    }
//                },1000
//                );
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

//    public void backMainList (View view){
//
//        Intent qwe= new Intent(this,Choose_direction.class);
//        startActivity(qwe);
//    }
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