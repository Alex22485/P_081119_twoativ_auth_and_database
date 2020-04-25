package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
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

public class DriversApp_0 extends AppCompatActivity {

    FirebaseAuth mAuth;
    String driverPhone;
    String driverId;
    String phoneID;
    //String searchID;

    FirebaseDatabase database01;
    DatabaseReference ref01;

    TextView checkWord;
    TextView TextHello1;
    TextView TextHello2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivers_app_0);

        checkWord=findViewById(R.id.checkWord);
        TextHello1=findViewById(R.id.TextHello1);
        TextHello2=findViewById(R.id.TextHello2);

        //полуачем номер телефона и ID водителя
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser ghg = mAuth.getCurrentUser();
        driverPhone = ghg.getPhoneNumber();
        driverId = ghg.getUid();

        //запись в БД Водители-ID-driverPhone-driverId для проверки зарегистрирован ли водитель ранее или нет
        database01 = FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Водители")
                .child("ID")
                .child("ID");
        ref01.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ref01.child(driverPhone).setValue(driverId);

                // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД
                ref01.removeEventListener(this);

                //задержка
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        chesk();
                    }
                },1000);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    public void chesk(){

        Query aaa= FirebaseDatabase.getInstance().getReference("Водители").child("ID")
                .orderByChild( "ID" );
        aaa.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                phoneID=dataSnapshot.child(driverPhone).getValue(String.class);

                //чтение марки автомобиля по ID водителя
                String searchID=dataSnapshot.child(driverId).getValue(String.class);
                checkWord.setText(searchID);

                //Проверка есть ли уже регистрация
                visible();
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

    public void visible(){

        String aaa=checkWord.getText().toString();
        if (!aaa.isEmpty()){

            TextHello1.setText("Здравствуй ");
            TextHello1.setTextColor(getResources().getColor( R.color.colorNew ));
            TextHello1.setVisibility(View.VISIBLE);

            TextHello2.setText(aaa);
            TextHello2.setTextColor(getResources().getColor( R.color.colorNew ));
            TextHello2.setVisibility(View.VISIBLE);
        }
        else {
            TextHello1.setText("Пожалуйста, ");
            TextHello1.setTextColor(getResources().getColor( R.color.colorNew ));
            TextHello1.setVisibility(View.VISIBLE);

            TextHello2.setText("зарегистируйтесь!");
            TextHello2.setTextColor(getResources().getColor( R.color.colorNew ));
            TextHello2.setVisibility(View.VISIBLE);
        }

    }
}
