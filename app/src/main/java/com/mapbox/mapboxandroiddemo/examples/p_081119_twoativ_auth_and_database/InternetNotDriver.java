package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InternetNotDriver extends AppCompatActivity {

    String key;
    TextView TextUpdate;
    TextView TextMessage1;
    TextView TextMessage2;
    Button BtnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_not_driver);

        TextUpdate=findViewById(R.id.TextUpdate);
        TextMessage1=findViewById(R.id.TextMessage1);
        TextMessage2=findViewById(R.id.TextMessage2);
        BtnUpdate=findViewById(R.id.BtnUpdate);

        TextMessage1.setVisibility(View.VISIBLE);
        TextMessage2.setVisibility(View.VISIBLE);
        BtnUpdate.setVisibility(View.VISIBLE);


    }

    // кнопка Back сворачивает приложение
    @Override
    public void onBackPressed(){
        this.moveTaskToBack(true);
    }


    // Кнопка обновить
    public void Update(View view){

        key="";

        TextMessage1.setVisibility(View.INVISIBLE);
        TextMessage2.setVisibility(View.INVISIBLE);
        BtnUpdate.setVisibility(View.INVISIBLE);

        TextUpdate.setVisibility(View.VISIBLE);
        //задержка запроса из БД для полного завершения функций из nod js
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                check();
            }
        },5000);

        //ПРОВЕРКА ИНТЕРНЕТА читаем данные из БД
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child( "КонтрТочка" )
                .child( "Проверка" )
                .child( "Загрузка" )
                .child( "Успешна" );
        ref.orderByValue().equalTo( "ДА" ).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap: dataSnapshot.getChildren()){
                    key = snap.getKey(); //получить все ключи значения
                    Toast.makeText( InternetNotDriver.this, "Eсть интернет "+key, Toast.LENGTH_SHORT ).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }

    public void check(){
        if (key.isEmpty()){
            TextMessage1.setVisibility(View.VISIBLE);
            TextMessage2.setVisibility(View.VISIBLE);
            BtnUpdate.setVisibility(View.VISIBLE);

            TextUpdate.setVisibility(View.INVISIBLE);
        }else {


            AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(InternetNotDriver.this);
            // Set Title
            mAlertDialog.setTitle("!!!");
            mAlertDialog.setCancelable(false);
            // Set Message
            mAlertDialog
                    .setMessage("Соединение восстановлено")
                    .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //возвращаемся в главное меню
                            backMainMenu();
                        }
                    });
            mAlertDialog.create();
            // Showing Alert Message
            mAlertDialog.show();



        }
    }

    public void backMainMenu (){
        Intent backMainMenu =new Intent(this,DriversApp_0.class);
        startActivity(backMainMenu);
    }

}
