package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Choose_direction extends AppCompatActivity {

    private static final String TAG ="Choose_direction";
    Button inAirport,inCity;

    String timeOut;
    String proverka;
    String userPhone;
    Button MyStatus;

    FirebaseDatabase database01;
    DatabaseReference ref01;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_choose_direction );

        inAirport = findViewById(R.id.inAirport);
        inCity = findViewById(R.id.inCity);
        MyStatus = findViewById(R.id.MyStatus);

        //полуаем phone пользователя
        mAuth= FirebaseAuth.getInstance(  );
        FirebaseUser user=mAuth.getCurrentUser();
        userPhone = user.getPhoneNumber();
        Log.d(TAG, "получен телефон"+userPhone);/*специально пусто*/




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
                readYesNoEmpty();
                Log.d(TAG, "Считывание Yes/No/empty");/*специально пусто*/
            }
        },2000);
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
                //ref01.removeEventListener(this);

                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        checkWordProverka();
                    }
                },1000);





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
            Intent Choose_directionNotInternet  = new Intent(this,Choose_directionNotInternet.class);
            startActivity(Choose_directionNotInternet);
        }
    }

    public void checkWordProverka(){

        if (timeOut.equals("Out")){
            Log.d(TAG, "time out");
        }
        else if(proverka.equals("Yes")){
            Log.d(TAG, "Заявка есть");
            MyStatus.setText("посмотрите не закрытую заявку");
            MyStatus.setVisibility(View.VISIBLE);
            MyStatus.setEnabled(true);
        }
        else if(proverka.equals("No")){
            Log.d(TAG, "Заявок нет");
            MyStatus.setText("актуальных заявок нет");
            MyStatus.setVisibility(View.VISIBLE);
            MyStatus.setEnabled(false);
        }
        else{
            Log.d(TAG, "в БД null");
        }
    }











    public void inCity(View view){
        Intent nextListInAir_choise_routes = new Intent( this,InAir_choise_routes.class );

        nextListInAir_choise_routes.putExtra( "Маршрут", "Аэропорт-Красноярск" );
        nextListInAir_choise_routes.putExtra( "oneMap", "Аэропорт-КрасТэц" );
        nextListInAir_choise_routes.putExtra( "twoMap", "Аэропорт-Щорса" );
        nextListInAir_choise_routes.putExtra( "treeMap", "Аэропорт-Северный" );
        nextListInAir_choise_routes.putExtra( "fourMap", "Аэропорт-Ветлужанка" );
        startActivity( nextListInAir_choise_routes);
    }

    public void inAirport(View view){
        Intent nextListInAir_choise_routes = new Intent( this,InAir_choise_routes.class );

        nextListInAir_choise_routes.putExtra( "Маршрут", "Красноярск-Аэропорт" );
        nextListInAir_choise_routes.putExtra( "oneMap", "КрасТэц-Аэропорт" );
        nextListInAir_choise_routes.putExtra( "twoMap", "Щорса-Аэропорт" );
        nextListInAir_choise_routes.putExtra( "treeMap", "Северный-Аэропорт" );
        nextListInAir_choise_routes.putExtra( "fourMap", "Ветлужанка-Аэропорт" );
        startActivity( nextListInAir_choise_routes);
    }

    public void MyStatus(View view){
        Intent MyStatus= new Intent(this,Main6Activity.class);
        startActivity(MyStatus);
    }



    // кнопка Back сворачивает приложение
    @Override
    public void onBackPressed(){
        this.moveTaskToBack(true);
    }

    @Override
    protected void onStop (){
        super.onStop();
       Log.d(TAG, "onStop");

    }

}
