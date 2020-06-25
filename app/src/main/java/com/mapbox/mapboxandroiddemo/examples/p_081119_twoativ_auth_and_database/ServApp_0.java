package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class ServApp_0 extends AppCompatActivity {

    private static final String TAG ="ServApp_0";


    Button choisData;
    Calendar calendar;
    DatePickerDialog datePickerDialog;

    TextView dataREF;
    TextView MapREF;

    String Calend;
    String Map;
    String[] array={};
    String[] array1={};
    String  key;
    String  key1;
    String findMap;
    String findMap1;

    int year;
    int month;
    int dayOfmonth;

    ArrayList<String> driver=new ArrayList<>(  );
    ArrayList<String> driver1=new ArrayList<>(  );


    ///Выбрать пункт Сбора Маршрута №1 КрасТэц-Аэропорт-КрасТэц
    String[] pointOneMap = {"ИгаркаЧ","Игарка","Туруханск","Северо-Енисейск"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serv_app_0);

        dataREF=findViewById(R.id.dataREF);
        MapREF=findViewById(R.id.MapREF);
        choisData=findViewById(R.id.choisData);

        // выбрать дату в календаре
        choisData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar= Calendar.getInstance();
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH);
                dayOfmonth=calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog=new DatePickerDialog(ServApp_0.this,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        dataREF.setText(day + " " + (month + 1) + " " + year);
                        Log.d(TAG, "дата: "+dataREF);
                    }
                    },year,month,dayOfmonth);
                datePickerDialog.show();
            }
        }
        );
    }

    // выбрать направлениие
public void BtnMap(View view){

    AlertDialog.Builder builder = new AlertDialog.Builder(ServApp_0.this);
    builder.setTitle("Направление самолета");
    //builder.setCancelable(true);
    builder.setItems(pointOneMap, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int which) {
            MapREF.setText(pointOneMap[which]);
            //findZrefTime();
        }
    });
    AlertDialog dialog = builder.create();
    dialog.show();
}

// получить данные время-рейс
public void findTime(View view){
        // обнуляем массив,  т.к. без этого в выпадающем списке дублируются данные при повторном считывании
        driver.clear();


    //30 03 2020 Получить все ключи объекта по его значению "Водила" записать их в ArrayList и преобразовать в строковый массив array
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child( "Заявки" ).child("zRef").child(dataREF.getText().toString()).child(MapREF.getText().toString()).child("time");
    ref.orderByValue().equalTo( "время" ).addListenerForSingleValueEvent( new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot snap: dataSnapshot.getChildren()){

                //Преобразовываем ArrayList в обычный массив чтобы вставить его в AlertDialog
                key = snap.getKey(); //получить все ключи значения
                driver.add( key );
                array = driver.toArray(new String[driver.size()]);

                Log.d(TAG, "key: "+key);
                Log.d(TAG, "driver: "+driver);
                Log.d(TAG, "array: "+array);

                Toast.makeText(ServApp_0.this,"Время-Рейс СЧИТАН",Toast.LENGTH_LONG).show();
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    } );
}

public void showTimeFlight (View view){
    AlertDialog.Builder builder = new AlertDialog.Builder( ServApp_0.this );
    builder.setTitle( "Найдено Время/рейс" );
    // Отображает Водителей загруженных из БД
    builder.setItems( array, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {

                    findMap=array[which];
                    //BtnOneDriver.setText(array[which]);
                }
            }
    );
    AlertDialog dialog = builder.create();
    dialog.show();
}

public void showFindZrefTime(){
    AlertDialog.Builder builder = new AlertDialog.Builder( ServApp_0.this );
    builder.setTitle( "Найдено Время/рейс" );
    //builder.setCancelable(true);
    // Отображает Водителей загруженных из БД
    builder.setItems( array, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {

                    findMap=array[which];
                    //Запускаем метод считывание телефона выбранного водителя
                    findZrefMap();
                    Log.d(TAG, "Выбрано время-рейс: "+findMap);


                }
            }
    );
    AlertDialog dialog = builder.create();
    dialog.show();
}

public void findZrefMap(){

    driver1.clear();

    //30 03 2020 Получить все ключи объекта по его значению "направление" записать их в ArrayList и преобразовать в строковый массив array
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child( "Заявки" ).child("zRef").child(dataREF.getText().toString()).child(MapREF.getText().toString()).child(findMap);
    ref.orderByValue().equalTo( "направление" ).addListenerForSingleValueEvent( new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot snap: dataSnapshot.getChildren()){

                //key1="";

                //Преобразовываем ArrayList в обычный массив чтобы вставить его в AlertDialog
                key1 = snap.getKey(); //получить все ключи значения
                driver1.add( key1 );
                array1 = driver1.toArray(new String[driver1.size()]);

                Log.d(TAG, "key1: "+key1);
                Log.d(TAG, "driver1: "+driver1);
                Log.d(TAG, "array1: "+array1);

                showFindZrefMap();
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    } );
}

public void showFindZrefMap(){

    AlertDialog.Builder builder = new AlertDialog.Builder( ServApp_0.this );
    builder.setTitle( "Найдено направление" );
    builder.setCancelable(true);
    // Отображает Водителей загруженных из БД
    builder.setItems( array1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {

                    findMap1=array1[which];
                    Log.d(TAG, "выбрано направление: "+findMap1);


                }
            }
    );
    AlertDialog dialog = builder.create();
    dialog.show();
}

public void nex (View view){
        Intent nex = new Intent(this,ServApp_1.class);
        startActivity(nex);
}

}
