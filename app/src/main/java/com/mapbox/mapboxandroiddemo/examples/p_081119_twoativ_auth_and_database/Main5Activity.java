package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class Main5Activity extends AppCompatActivity {

    //ADD ListView
    ListView lv2;
    List<String> basa=new ArrayList<String>(  );
    ArrayAdapter ad;
    TextView Calend;


    // ADD Calendar
    Button choisData,choisMap,choisFlight,btnInsert;
    int year;
    int month;
    int dayOfmonth;
    Calendar calendar;
    DatePickerDialog datePickerDialog;

    String[] array={};

    // Вставляем данные маршрута и номера рейса
    TextView pushMap,pushflight;
    String[] listItems1 = {"Маршрут 1","Маршрут 2","Маршрут 3","Маршрут 4"};
    String[] listItems2 = {"Рейс номер 1","Рейс номер 2","Рейс номер 3","Рейс номер 4"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main5 );
        //Добавил
        lv2=findViewById( R.id.lv2 );
        basa=new ArrayList<String>( Arrays.asList( array ) );
        ad= new ArrayAdapter<>( this,android.R.layout.simple_list_item_1,basa );
        lv2.setAdapter( ad );
        Calend=findViewById(R.id.Calend);


        choisData=findViewById(R.id.choisData);
        choisMap=findViewById(R.id.choisMap);
        choisFlight=findViewById(R.id.choisFlight);
        btnInsert=findViewById( R.id.btnInsert );


// Вставляем данные маршрута и номера рейса
        pushMap=findViewById( R.id.pushMap );
        pushflight=findViewById( R.id.pushflight );


// Календарь
        choisData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar= Calendar.getInstance();
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH);
                dayOfmonth=calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog=new DatePickerDialog(Main5Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                Calend.setText(day + " " + (month + 1) + " " + year);
                            }
                        }, year,month,dayOfmonth);
                datePickerDialog.show();
            }
        });

        Calend.addTextChangedListener( loginTextWather );
        pushMap.addTextChangedListener( loginTextWather );
        pushflight.addTextChangedListener( loginTextWather );



        // извлечение из базы данных Пользователи ПРОБА
       /* DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Заявки")
                .child("Аэропорт-Красноярск")
                .child( Calend.getText().toString() )
                .child( pushMap.getText().toString() );
        DatabaseReference usersdRef = rootRef.child( pushflight.getText().toString() );
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String data = ds.child("дата").getValue(String.class);
                    String flight = ds.child("рейс").getValue(String.class);
                    String phone  = ds.child("phone").getValue(String.class);

                    Log.d("TAG", data+"  "+"Рейс номер"+""+flight+"  "+phone);
                    basa.add("Рейс №"+flight+"  "+"Дата"+"  "+data+"  "+phone );
                    Collections.sort(basa);
                    ad.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };usersdRef.addListenerForSingleValueEvent(valueEventListener);*/



    }
    // Disable Button it Text is Empty
    private TextWatcher loginTextWather= new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            String choisDataInput = Calend.getText().toString().trim();
            String choisMapInput = pushMap.getText().toString().trim();
            String choisFlightInput = pushflight.getText().toString().trim();

            btnInsert.setEnabled(!choisDataInput.isEmpty()&& !choisMapInput.isEmpty()&& !choisFlightInput.isEmpty());
        }
        @Override
        public void afterTextChanged(Editable editable) {
        }
    };


    // переход на другой лист
    public void next_list2(View view){
        Intent n = new Intent(this,Main3Activity.class);
        startActivity(n);
    }


    // AlertDialog Вставляем данные номера Маршрута и Номер рейса
    public void showDialog_6 (View view){

        AlertDialog.Builder builder=new AlertDialog.Builder( Main5Activity.this );
        builder.setTitle( "Выберите Маршрут");
        builder.setCancelable( false );
        builder.setItems( listItems1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                pushMap.setText(listItems1[which]);

            }
        } );
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void showDialog_7 (View view){

        AlertDialog.Builder builder=new AlertDialog.Builder( Main5Activity.this );
        builder.setTitle( "Выберите Номер рейса");
        builder.setCancelable( false );
        builder.setItems( listItems2, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                pushflight.setText(listItems2[which]);

            }
        } );
        AlertDialog dialog = builder.create();
        dialog.show();
    }



    // извлечение из базы данных
    public void btnInsert (View view){
        
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Заявки")
                .child("Аэропорт-Красноярск")
                .child( Calend.getText().toString() )
                .child( pushMap.getText().toString() );
        DatabaseReference usersdRef = rootRef.child( pushflight.getText().toString() );
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String data = ds.child("дата").getValue(String.class);
                    String flight = ds.child("рейс").getValue(String.class);
                    String phone  = ds.child("phone").getValue(String.class);

                    Log.d("TAG", data+"  "+"Рейс номер"+""+flight+"  "+phone);
                    basa.add("Рейс №"+flight+"  "+"Дата"+"  "+data+"  "+phone );
                    Collections.sort(basa);
                    ad.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };usersdRef.addListenerForSingleValueEvent(valueEventListener);
    }
}