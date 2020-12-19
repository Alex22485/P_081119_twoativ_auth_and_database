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
import android.widget.Toast;

public class Zakaz2 extends AppCompatActivity {

    // КОД ПРОТЕСТИРОВАН 19.12.2020

    //Лист заказа 2, выбор маршрута и пункта сбора

    private static final String TAG ="Zakaz2" ;
    TextView textView;
    Button button1,button2,button3,button4,button5;
    Button map1,map2,map3,map4,map5;
    // данные из Zakaz1
    String refFromInCity;
    String phoneNew;

    String[] array1 ={"Щорса-Аэропорт","КрасТэц-Аэропорт","Северный-Аэропорт","ЖД вокзал-Аэропорт","Ветлужанка-Аэропорт"};
    String[] array2 ={"Аэропорт-Щорса","Аэропорт-КрасТэц","Аэропорт-Северный","Аэропорт-ЖД вокзал","Аэропорт-Ветлужанка"};

    // пункты сбора Красноярск
    String[] KrasnojarskOneMap={"Кинотеатр Металлург","Автобусный пер","Пикра","Мебельная фабрика"};
    String[] KrasnojarskTwoMap = {"ДК КрасТЭЦ","Аэрокосмическая академия","Торговый центр","Предмостная пл"};
    String[] KrasnojarskTreeMap = {"XXX","xxx","xxx","xxx"};
    String[] KrasnojarskFourMap = {"YYY","yyy","yyy","yyy"};
    String[] KrasnojarskFiveMap = {"uuu","uuu","Тuuu","uuu"};

    // пункты сбора других городов
    String[] AnyCity={"Подтвердить?"};

    // пункт сбора из Аэропорта
    String[] FromAirport={"Парковка Р2"};

    String[] RefList1;
    String[] RefList2;
    String[] RefList3;
    String[] RefList4;
    String[] RefList5;

    // выбранный маршрут
    String RefMap;
    // выбранная точка сбора
    String RefPoint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakaz2);

        textView=findViewById(R.id.textView);
        button1=findViewById(R.id.button1);
        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);
        button4=findViewById(R.id.button4);
        button5=findViewById(R.id.button5);
        map1=findViewById(R.id.map1);
        map2=findViewById(R.id.map2);
        map3=findViewById(R.id.map3);
        map4=findViewById(R.id.map4);
        map5=findViewById(R.id.map5);

        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);
        button4.setVisibility(View.INVISIBLE);
        button5.setVisibility(View.INVISIBLE);
        map1.setVisibility(View.INVISIBLE);
        map2.setVisibility(View.INVISIBLE);
        map3.setVisibility(View.INVISIBLE);
        map4.setVisibility(View.INVISIBLE);
        map5.setVisibility(View.INVISIBLE);

        // полученине данных из Zakaz1
        Intent Zakaz1ToZakaz2=getIntent();
        refFromInCity= Zakaz1ToZakaz2.getStringExtra("refFromInCity");
        phoneNew=Zakaz1ToZakaz2.getStringExtra("phoneNew");
        visalList();
        Log.d(TAG, "Данные из Zakaz1 refFromInCity:"+refFromInCity);
        Log.d(TAG, "Данные из Zakaz1 phoneNew:"+phoneNew);
    }

// ВИЗУАЛИЗАЦИЯ
    public void visalList(){
        if(refFromInCity.equals("Красноярск->Аэропорт")){

            button1.setVisibility(View.VISIBLE);
            button2.setVisibility(View.VISIBLE);
            button3.setVisibility(View.VISIBLE);
            button4.setVisibility(View.VISIBLE);
            button5.setVisibility(View.VISIBLE);
            map1.setVisibility(View.VISIBLE);
            map2.setVisibility(View.VISIBLE);
            map3.setVisibility(View.VISIBLE);
            map4.setVisibility(View.VISIBLE);
            map5.setVisibility(View.VISIBLE);

            button1.setText(array1[0]);
            button2.setText(array1[1]);
            button3.setText(array1[2]);
            button4.setText(array1[3]);
            button5.setText(array1[4]);

            RefList1=KrasnojarskOneMap;
            RefList2=KrasnojarskTwoMap;
            RefList3=KrasnojarskTreeMap;
            RefList4=KrasnojarskFourMap;
            RefList5=KrasnojarskFiveMap;
    }

        if(refFromInCity.equals("Аэропорт->Красноярск")){
            button1.setVisibility(View.VISIBLE);
            button2.setVisibility(View.VISIBLE);
            button3.setVisibility(View.VISIBLE);
            button4.setVisibility(View.VISIBLE);
            button5.setVisibility(View.VISIBLE);
            map1.setVisibility(View.VISIBLE);
            map2.setVisibility(View.VISIBLE);
            map3.setVisibility(View.VISIBLE);
            map4.setVisibility(View.VISIBLE);
            map5.setVisibility(View.VISIBLE);

            button1.setText(array2[0]);
            button2.setText(array2[1]);
            button3.setText(array2[2]);
            button4.setText(array2[3]);
            button5.setText(array2[4]);

            RefList1=FromAirport;
            RefList2=FromAirport;
            RefList3=FromAirport;
            RefList4=FromAirport;
            RefList5=FromAirport;
        }

        if(refFromInCity.equals("Сосновоборск->Аэропорт")){
            button1.setVisibility(View.VISIBLE);
            map1.setVisibility(View.VISIBLE);
            RefList1=AnyCity;
            button1.setText(refFromInCity);
        }

        if(refFromInCity.equals("Аэропорт->Сосновоборск")){
            button1.setVisibility(View.VISIBLE);
            map1.setVisibility(View.VISIBLE);
            button1.setText(refFromInCity);
            RefList1=FromAirport;
        }

        if(refFromInCity.equals("Ачинск->Аэропорт")){
            button1.setVisibility(View.VISIBLE);
            map1.setVisibility(View.VISIBLE);
            button1.setText(refFromInCity);
            RefList1=AnyCity;
        }

        if(refFromInCity.equals("Аэропорт->Ачинск")){
            button1.setVisibility(View.VISIBLE);
            map1.setVisibility(View.VISIBLE);
            button1.setText(refFromInCity);
            RefList1=FromAirport;
        }

        if(refFromInCity.equals("Канск->Аэропорт")){
            button1.setVisibility(View.VISIBLE);
            map1.setVisibility(View.VISIBLE);
            button1.setText(refFromInCity);
            RefList1=AnyCity;
        }

        if(refFromInCity.equals("Аэропорт->Канск")){
            button1.setVisibility(View.VISIBLE);
            map1.setVisibility(View.VISIBLE);
            button1.setText(refFromInCity);
            RefList1=FromAirport;
        }

        if(refFromInCity.equals("Лесосибирск->Аэропорт")){
            button1.setVisibility(View.VISIBLE);
            map1.setVisibility(View.VISIBLE);
            button1.setText(refFromInCity);
            RefList1=AnyCity;
        }

        if(refFromInCity.equals("Аэропорт->Лесосибирск")){
            button1.setVisibility(View.VISIBLE);
            map1.setVisibility(View.VISIBLE);
            button1.setText(refFromInCity);
            RefList1=FromAirport;
        }
    }
//КНОПКИ
    // Выбор точки сбора
    public void button1 (View view){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz2.this);
        mAlertDialog.setTitle("Пункт сбора");
        mAlertDialog
                .setItems(RefList1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        RefPoint=RefList1[which];
                        RefMap=button1.getText().toString();
                        // идем обратно в Zakaz1
                        backZakaz2ToZakaz1();
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }
    public void button2 (View view){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz2.this);
        mAlertDialog.setTitle("Пункт сбора");
        mAlertDialog
                .setItems(RefList2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        RefPoint=RefList2[which];
                        RefMap=button2.getText().toString();
                        // идем обратно в Zakaz1
                        backZakaz2ToZakaz1();
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }
    public void button3 (View view){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz2.this);
        mAlertDialog.setTitle("Пункт сбора");
        mAlertDialog
                .setItems(RefList3, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RefPoint=RefList3[which];
                        RefMap=button3.getText().toString();
                        // идем обратно в Zakaz1
                        backZakaz2ToZakaz1();
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }
    public void button4 (View view){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz2.this);
        mAlertDialog.setTitle("Пункт сбора");
        mAlertDialog
                .setItems(RefList4, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RefPoint=RefList4[which];
                        RefMap=button4.getText().toString();
                        // идем обратно в Zakaz1
                        backZakaz2ToZakaz1();
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }
    public void button5 (View view){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz2.this);
        mAlertDialog.setTitle("Пункт сбора");
        mAlertDialog
                .setItems(RefList5, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RefPoint=RefList5[which];
                        RefMap=button5.getText().toString();
                        // идем обратно в Zakaz1
                        backZakaz2ToZakaz1();
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }
    // Кнопка Отмене обратно в Zakaz1
    public void back(View view){
        // передаем null в Zakaz1
        Intent backZakaz2ToZakaz1= new Intent(this,Zakaz1.class);
        backZakaz2ToZakaz1.putExtra("RefBackFromZakaz2","backNoFromZakaz2");
        backZakaz2ToZakaz1.putExtra("phoneNew",phoneNew);
        startActivity(backZakaz2ToZakaz1);
    }

// ПЕРЕХОДЫ
    // Переход обратно в Zakaz1 после выбора пункта сбора
    public void backZakaz2ToZakaz1(){
        Intent backZakaz2ToZakaz1= new Intent(this,Zakaz1.class);
        // передаем Маршрут и пункт сбора в Zakaz1
        backZakaz2ToZakaz1.putExtra("RefMap",RefMap);
        backZakaz2ToZakaz1.putExtra("RefPoint",RefPoint);
        backZakaz2ToZakaz1.putExtra("RefBackFromZakaz2","backYesFromZakaz2");
        backZakaz2ToZakaz1.putExtra("phoneNew",phoneNew);
        startActivity(backZakaz2ToZakaz1);
    }


    // Блокировка кнопки Back!!!! :)))
    @Override
    public void onBackPressed(){
    }
}