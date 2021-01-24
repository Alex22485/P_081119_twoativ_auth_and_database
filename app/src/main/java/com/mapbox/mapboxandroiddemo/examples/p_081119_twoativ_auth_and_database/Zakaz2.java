package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Zakaz2 extends AppCompatActivity {

    // КОД ПРОТЕСТИРОВАН 19.12.2020

    //Лист заказа 2, выбор маршрута и пункта сбора

    private static final String TAG ="Zakaz2" ;
    TextView textView;
    Button button1,button2,button3,button4,button5;
    // данные из Zakaz1
    String refFromInCity;
    String phoneNew;

    String [] refMap={"Красноярск->Аэропорт","Аэропорт->Красноярск",
            "Сосновоборск->Аэропорт","Аэропорт->Сосновоборск",
            "Ачинск->Аэропорт","Аэропорт->Ачинск",
            "Канск->Аэропорт","Аэропорт->Канск",
            "Лесосибирск->Аэропорт","Аэропорт->Лесосибирск"};

    // Для выбора какую карту открыть для первой кнопки
    String [] arrayOneButton={"Щорса-Аэропорт","Аэропорт-Щорса","Cосновоборск-Аэропорт","Аэропорт-Cосновоборск",
            "Ачинск-Аэропорт","Аэропорт-Ачинск","Канск-Аэропорт","Аэропорт-Канск","Лесосибирск-Аэропорт","Аэропорт-Лесосибирск"};


    String[] array1 ={"Щорса-Аэропорт","КрасТэц-Аэропорт","Северный-Аэропорт","ЖД вокзал-Аэропорт","Ветлужанка-Аэропорт"};
    String[] array2 ={"Аэропорт-Щорса","Аэропорт-КрасТэц","Аэропорт-Северный","Аэропорт-ЖД вокзал","Аэропорт-Ветлужанка"};
    String [] proba ={"Map1_Shorsa_Air","","","","",};
    String [] proba2 ={"Map1_Shorsa_Air","","","","",};

    // пункты сбора Красноярск
    String[] KrasnojarskOneMap={"Кинотеатр Металлург","Автобусный пер","Пикра","Мебельная фабрика"};
    String[] KrasnojarskTwoMap = {"Гостиннинца Кедр","Аэрокосмическая академия","Торговый центр","Предмостная пл"};
    String[] KrasnojarskTreeMap = {"XXX","xxx","xxx","xxx"};
    String[] KrasnojarskFourMap = {"YYY","yyy","yyy","yyy"};
    String[] KrasnojarskFiveMap = {"uuu","uuu","Тuuu","uuu"};

    // пункты сбора других городов
    // Сосновоборск
    String[] pointSosnovoborsk={"Автовокзал Сосновоборск"};
    // Ачинск
    String[] pointAchinsk={"Автовокзал Ачинск"};
    // Канск
    String[] pointKansk={"Автовокзал Канск"};
    // Лесосибирск
    String[] pointLesosobirsk={"Автовокзал Лесосибирск"};

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

        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);
        button4.setVisibility(View.INVISIBLE);
        button5.setVisibility(View.INVISIBLE);


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
        for (int i=0;i<refMap.length;i++){
            if (refFromInCity.equals(refMap[i])){
                Log.d(TAG, "refMap: "+refMap[i]);
                if (i==0){
                    // Визуализация маршрутов Красноярск->Аэропорт
                    getMetod0();
                }
                if (i==1){
                    // Визуализация маршрутов Аэропорт->Красноярск
                    getMetod1();
                }
                if (i==2){
                    // Визуализация маршрутов Сосновоборск->Аэропорт
                    getMetod2();
                }
                if (i==3){
                    // Визуализация маршрутов Аэропорт->Сосновоборск
                    getMetod3();
                }
                if (i==4){
                    // Визуализация маршрутов Ачинск->Аэропорт
                    getMetod4();
                }
                if (i==5){
                    // Визуализация маршрутов Аэропорт->Ачинск
                    getMetod5();
                }
                if (i==6){
                    // Визуализация маршрутов Канск->Аэропорт
                    getMetod6();
                }
                if (i==7){
                    // Визуализация маршрутов Аэропорт->Канск
                    getMetod7();
                }
                if (i==8){
                    // Визуализация маршрутов Лесосибирск->Аэропорт
                    getMetod8();
                }
                if (i==9){
                    // Визуализация маршрутов Аэропорт->Канск
                    getMetod9();
                }
            }
        }
    }
    // Красноярск->Аэропорт
    public void getMetod0(){
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        button4.setVisibility(View.VISIBLE);
        button5.setVisibility(View.VISIBLE);

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
    //Аэропорт->Красноярск
    public void getMetod1(){
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        button4.setVisibility(View.VISIBLE);
        button5.setVisibility(View.VISIBLE);

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
    //Сосновоборск->Аэропорт
    public void getMetod2(){
        button1.setVisibility(View.VISIBLE);
        RefList1=pointSosnovoborsk;
        button1.setText(refFromInCity);
    }
    //Аэропорт->Сосновоборск
    public void getMetod3(){
        button1.setVisibility(View.VISIBLE);
        button1.setText(refFromInCity);
        RefList1=FromAirport;
    }
    //Ачинск->Аэропорт
    public void getMetod4(){
        button1.setVisibility(View.VISIBLE);
        button1.setText(refFromInCity);
        RefList1=pointAchinsk;
    }
    //Аэропорт->Ачинск
    public void getMetod5(){
        button1.setVisibility(View.VISIBLE);
        button1.setText(refFromInCity);
        RefList1=FromAirport;
    }
    //Канск->Аэропорт
    public void getMetod6(){
        button1.setVisibility(View.VISIBLE);
        button1.setText(refFromInCity);
        RefList1=pointKansk;
    }
    //Аэропорт->Канск
    public void getMetod7(){
        button1.setVisibility(View.VISIBLE);
        button1.setText(refFromInCity);
        RefList1=FromAirport;
    }
    //Лесосибирск->Аэропорт
    public void getMetod8(){
        button1.setVisibility(View.VISIBLE);
        button1.setText(refFromInCity);
        RefList1=pointLesosobirsk;
    }
    //Аэропорт->Лесосибирск
    public void getMetod9(){
        button1.setVisibility(View.VISIBLE);
        button1.setText(refFromInCity);
        RefList1=FromAirport;
    }
//КНОПКИ
    // Выбор точки сбора
    public void button1 (View view){
        // выбранный маршрут
        RefMap=button1.getText().toString();
        // Открытие карты
        buttonOpenMap();

//        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz2.this);
//        mAlertDialog.setTitle("Пункт сбора");
//        mAlertDialog
//                .setItems(RefList1, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        RefPoint=RefList1[which];
//                        RefMap=button1.getText().toString();
//                        // идем обратно в Zakaz1
//                        backZakaz2ToZakaz1();
//                    }
//                });
//        mAlertDialog.create();
//        mAlertDialog.show();
    }
    public void button2 (View view){
        // выбранный маршрут
        RefMap=button2.getText().toString();
        // Открытие карты
        buttonOpenMap();
//        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz2.this);
//        mAlertDialog.setTitle("Пункт сбора");
//        mAlertDialog
//                .setItems(RefList2, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        RefPoint=RefList2[which];
//                        RefMap=button2.getText().toString();
//                        // идем обратно в Zakaz1
//                        backZakaz2ToZakaz1();
//                    }
//                });
//        mAlertDialog.create();
//        mAlertDialog.show();
    }
    public void button3 (View view){
        // выбранный маршрут
        RefMap=button3.getText().toString();
        // Открытие карты
        buttonOpenMap();

//        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz2.this);
//        mAlertDialog.setTitle("Пункт сбора");
//        mAlertDialog
//                .setItems(RefList3, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        RefPoint=RefList3[which];
//                        RefMap=button3.getText().toString();
//                        // идем обратно в Zakaz1
//                        backZakaz2ToZakaz1();
//                    }
//                });
//        mAlertDialog.create();
//        mAlertDialog.show();
    }
    public void button4 (View view){
        // выбранный маршрут
        RefMap=button4.getText().toString();
        // Открытие карты
        buttonOpenMap();

//        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz2.this);
//        mAlertDialog.setTitle("Пункт сбора");
//        mAlertDialog
//                .setItems(RefList4, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        RefPoint=RefList4[which];
//                        RefMap=button4.getText().toString();
//                        // идем обратно в Zakaz1
//                        backZakaz2ToZakaz1();
//                    }
//                });
//        mAlertDialog.create();
//        mAlertDialog.show();
    }
    public void button5 (View view){
        // выбранный маршрут
        RefMap=button1.getText().toString();
        // Открытие карты
        buttonOpenMap();

//        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz2.this);
//        mAlertDialog.setTitle("Пункт сбора");
//        mAlertDialog
//                .setItems(RefList5, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        RefPoint=RefList5[which];
//                        RefMap=button5.getText().toString();
//                        // идем обратно в Zakaz1
//                        backZakaz2ToZakaz1();
//                    }
//                });
//        mAlertDialog.create();
//        mAlertDialog.show();
    }
    // Кнопка Отмене обратно в Zakaz1
    public void back(View view){
        // передаем null в Zakaz1
        Intent backZakaz2ToZakaz1= new Intent(this,Zakaz1.class);
        backZakaz2ToZakaz1.putExtra("RefBackFromZakaz2","BackNoFromZakaz2");
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
    // Открытие карты по первой кнопке
    public void buttonOpenMap(){
        Intent buttonOpenMap = new Intent(this,Zakaz2ShowMapBtn1.class);
        // передаем в карту
        buttonOpenMap.putExtra("RefMap",RefMap); // название выбранного маршрута
        buttonOpenMap.putExtra("RefBackFromZakaz2","BackYesFromZakaz2"); // подтверждение выбора (а не просто возврат по кнопке НАЗАД)
        buttonOpenMap.putExtra("phoneNew",phoneNew); // реф phone

        startActivity(buttonOpenMap);
    }
    // Открытие АЭРОПОРТ-ЩОРСА-АЭРОПОРТ
    public void Map1(View view) {

        Log.d(TAG, "1button1:"+button1.getText().toString());

        for (String s : array1) {
            // Если маршрут В АЭРОПОРТ
            if (button1.getText().toString().equals(s)) {
                Log.d(TAG, "3button1:"+button1.getText().toString());
                
                Intent Map1_Shorsa_Air = new Intent(this, Map1_Shorsa_Air.class);
                startActivity(Map1_Shorsa_Air);
                return;
            }
            // Если маршрут ИЗ АЭРОПОРТА
            else {
                Log.d(TAG, "4button1:"+button1.getText().toString());
                // Открываем карту ИЗ АЭРОПОРТА
                Intent Map1 = new Intent(this, Map1.class);
                startActivity(Map1);
                return;
            }
        }
    }
    // Открытие АЭРОПОРТ-КРАСТЭЦ
    public void Map2(View view){
        Intent Map1 =new Intent(this,Map2_Air_KrasTeth.class);
        startActivity(Map1);
    }


    // Блокировка кнопки Back!!!! :)))
    @Override
    public void onBackPressed(){
    }
}