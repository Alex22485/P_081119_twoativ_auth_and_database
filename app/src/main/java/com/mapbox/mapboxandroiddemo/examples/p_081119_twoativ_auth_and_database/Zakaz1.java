package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Calendar;

public class Zakaz1 extends AppCompatActivity {

    private static final String TAG ="Zakaz1" ;



    Button button1;
    TextView button2,button3,button4;
    TextView Right1,Right2,Right3,Right4,Right5;
    TextView No1,No2,No3,No4,No5;
    TextView Exclamation1,Exclamation2,Exclamation3,Exclamation4;
    TextView OderRight;

    // выбранный маршрут
    String RefMap;
    // выбранная точка сбора
    String RefPoint;

    // выбранный рейс самолета (город)
    String RefplaneCity;

    String [] listCityTaxi= {"Красноярск","Сосновоборск","Ачинск","Канск","Лесосибирск"};
    String refCityTaxi,refFromInCity;
    String [] listCityFromIn= {};
    String [] planeCity={"Игарка","Северо-Енисейск","Новосибирск","Иркутск","Омск","Екатеринбург","Нет нужного города"};

    // для заголовка Alert при выборе рейса самолета
    String [] refOne = {"Щорса-Аэропорт","КрасТэц-Аэропорт","Северный-Аэропорт","ЖД вокзал-Аэропорт","Ветлужанка-Аэропорт","Сосновоборск->Аэропорт",
            "Ачинск->Аэропорт","Канск->Аэропорт","Лесосибирск->Аэропорт"};
    String [] refTwo = {"Аэропорт-Щорса","Аэропорт-КрасТэц","Аэропорт-Северный","Аэропорт-ЖД вокзал","Аэропорт-Ветлужанка","Аэропорт->Сосновоборск",
            "Аэропорт->Ачинск","Аэропорт->Канск","Аэропорт->Лесосибирск"};
    String AlertFrom="В какой город вы летите из Красноярска?";
    String AlertIn="Из какого города вы летите в Красноярск?";
    String RefAlertTitle;

    // for title of Calendar
    String titleCalendarFromAirport="Дата вылета из Красноярска";
    String titleCalendarInAirport="Дата прилета в Красноярск";
    String ReftitleCalendar;

    // for title of time
    String titleTimeFromAirport="Время вылета из Красноярска";
    String titleTimeInAirport="Время прилета в Красноярск";
    String ReftitleTime;


    // показ календаря
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfmonth;

    //выбранная дата
    String Calend;

    String time;

    //показ часов
    TimePickerDialog timePickerDialog;
    int hourOfDay;
    int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakaz1);

        button1=findViewById(R.id.button1);
        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);
        button4=findViewById(R.id.button4);
        Right1=findViewById(R.id.Right1);
        Right2=findViewById(R.id.Right2);
        Right3=findViewById(R.id.Right3);
        Right4=findViewById(R.id.Right4);
        Right5=findViewById(R.id.Right5);
        No1=findViewById(R.id.No1);
        No2=findViewById(R.id.No2);
        No3=findViewById(R.id.No3);
        No4=findViewById(R.id.No4);
        No5=findViewById(R.id.No5);
        Exclamation1=findViewById(R.id.Exclamation1);
        Exclamation2=findViewById(R.id.Exclamation2);
        Exclamation3=findViewById(R.id.Exclamation3);
        Exclamation4=findViewById(R.id.Exclamation4);

        OderRight=findViewById(R.id.OderRight);

        // Проверка был ли переход сюда с листа Zakaz2
        Intent backZakaz2ToZakaz1=getIntent();
        RefMap=""+backZakaz2ToZakaz1.getStringExtra("RefMap");
        RefPoint=""+backZakaz2ToZakaz1.getStringExtra("RefPoint");

        // переход был
        if (!RefMap.equals("null")){

            button1.setEnabled(true);
            No1.setVisibility(View.INVISIBLE);
            No2.setVisibility(View.VISIBLE);
            No3.setVisibility(View.VISIBLE);
            No4.setVisibility(View.VISIBLE);
            No5.setVisibility(View.VISIBLE);
            Right1.setVisibility(View.VISIBLE);
            Right2.setVisibility(View.INVISIBLE);
            Right3.setVisibility(View.INVISIBLE);
            Right4.setVisibility(View.INVISIBLE);
            Right5.setVisibility(View.INVISIBLE);
            Exclamation1.setVisibility(View.INVISIBLE);
            Exclamation2.setVisibility(View.INVISIBLE);
            Exclamation3.setVisibility(View.INVISIBLE);
            Exclamation4.setVisibility(View.INVISIBLE);

            //dinamic desajin
            Handler handler0 = new Handler();
            handler0.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Right2.setVisibility(View.VISIBLE);
                    No2.setVisibility(View.INVISIBLE);
                }
            },300);

            // задержка Alert рейс самолета
            Handler setPlain = new Handler();
            setPlain.postDelayed(new Runnable() {
                @Override
                public void run() {

                    //Выбор заголовка для Alert (рейс самолета)
                    setPlain();
                }
            },300);
        }

        // переход  Не был
        else {
        Log.d(TAG, ""+RefMap);

            button2.setVisibility(View.INVISIBLE);
            button3.setVisibility(View.INVISIBLE);
            button4.setVisibility(View.INVISIBLE);
            Right1.setVisibility(View.INVISIBLE);
            Right2.setVisibility(View.INVISIBLE);
            Right3.setVisibility(View.INVISIBLE);
            Right4.setVisibility(View.INVISIBLE);
            Right5.setVisibility(View.INVISIBLE);
            No2.setVisibility(View.INVISIBLE);
            No3.setVisibility(View.INVISIBLE);
            No4.setVisibility(View.INVISIBLE);
            No5.setVisibility(View.INVISIBLE);
            Exclamation1.setVisibility(View.INVISIBLE);
            Exclamation2.setVisibility(View.INVISIBLE);
            Exclamation3.setVisibility(View.INVISIBLE);
            Exclamation4.setVisibility(View.INVISIBLE);
            OderRight.setVisibility(View.INVISIBLE);


        dinamicView();
        }

    }

    // кнопка button1
    public void button1(View view){
        AlertChoiceCity();
    }
    // кнопка button2
    public void button2(View view){
    }
    // кнопка button3
    public void button3(View view){
    }
    // кнопка button4
    public void button4(View view){
    }


    public void dinamicView(){

        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                button2.setVisibility(View.VISIBLE);
                No2.setVisibility(View.VISIBLE);
            }
        },300);

        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                button3.setVisibility(View.VISIBLE);
                No3.setVisibility(View.VISIBLE);
            }
        },600);

        Handler handler3 = new Handler();
        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                button4.setVisibility(View.VISIBLE);
                No4.setVisibility(View.VISIBLE);
            }
        },900);

        Handler handler4 = new Handler();
        handler4.postDelayed(new Runnable() {
            @Override
            public void run() {
                OderRight.setVisibility(View.VISIBLE);
                No5.setVisibility(View.VISIBLE);

                //выбор города
                AlertChoiceCity();
                button1.setEnabled(true);
            }
        },1200);
    }

    public void AlertChoiceCity(){

        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz1.this);
        mAlertDialog.setTitle("Выберите город");
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setItems(listCityTaxi, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        refCityTaxi=listCityTaxi[which];
                        Exclamation1.setVisibility(View.INVISIBLE);

                        // Задержка второго Диалога
                        Handler AlertFromInCity = new Handler();
                        AlertFromInCity.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //выбор в/из города
                                AlertFromInCity();
                            }
                        },300);
                    }
                });
        mAlertDialog
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        No1.setVisibility(View.INVISIBLE);

                        Handler handlerNeg1 = new Handler();
                        handlerNeg1.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Exclamation1.setVisibility(View.VISIBLE);
                            }
                            },400);
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }
    public void AlertFromInCity(){
        listCityFromIn= new String[]{refCityTaxi + "->Аэропорт","Аэропорт->"+refCityTaxi};
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz1.this);
        mAlertDialog.setTitle("Укажите направление");
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setItems(listCityFromIn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        refFromInCity=listCityFromIn[which];
                        Exclamation1.setVisibility(View.INVISIBLE);
                        // задержка для дизайна
                        timeOut1();
                        button1.setEnabled(false);
                    }
                });
        mAlertDialog
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        No1.setVisibility(View.INVISIBLE);
                        // Задержка на появление Воскл1
                        Handler handlerNeg1 = new Handler();
                        handlerNeg1.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Exclamation1.setVisibility(View.VISIBLE);
                            }
                            },300);
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }

    // задержка для дизайна
    public void timeOut1(){

        Handler handler5 = new Handler();
        handler5.postDelayed(new Runnable() {
            @Override
            public void run() {

                No1.setVisibility(View.INVISIBLE);
                Right1.setVisibility(View.VISIBLE);

            }
        },500);

        Handler handler6 = new Handler();
        handler6.postDelayed(new Runnable() {
            @Override
            public void run() {

                // выбор маршрута
                Zakaz1ToZakaz2();
            }
        },900);
    }

    public void Zakaz1ToZakaz2(){

        Intent Zakaz1ToZakaz2=new Intent(this,Zakaz2.class);
        Zakaz1ToZakaz2.putExtra("refFromInCity",refFromInCity);
        startActivity(Zakaz1ToZakaz2);
    }

    public void setPlain(){

        int x=refOne.length;
        int i;
         for ( i=0; i<x; i++){

             if (RefMap.equals(refOne[i])){
                 RefAlertTitle=AlertFrom;
                 ReftitleCalendar=titleCalendarFromAirport;
                 ReftitleTime=titleTimeFromAirport;

                 // Alert рейс самолета
                 showAlert();
             }
         }

        int y=refTwo.length;
        int b;
        for ( b=0; b<y; b++){

            if (RefMap.equals(refTwo[b])){
                RefAlertTitle=AlertIn;
                ReftitleCalendar=titleCalendarInAirport;
                ReftitleTime=titleTimeInAirport;

                // Alert рейс самолета
                showAlert();
            }
        }
    }

    // Alert рейс самолета
    public void showAlert(){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz1.this);
        mAlertDialog.setTitle(RefAlertTitle);
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setItems(planeCity, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        RefplaneCity=planeCity[which];

                        No3.setVisibility(View.INVISIBLE);
                        Right3.setVisibility(View.VISIBLE);

                        // Задержка на появление Календаря
                        Handler Date = new Handler();
                        Date.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // выбрать дату
                                setData();
                            }
                        },300);
                    }
                });
        mAlertDialog
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Right1.setVisibility(View.INVISIBLE);
                        Right2.setVisibility(View.INVISIBLE);
                        Handler handlerNeg1 = new Handler();
                        handlerNeg1.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Exclamation1.setVisibility(View.VISIBLE);
                                Exclamation2.setVisibility(View.VISIBLE);
                            }
                            },300);

                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }

    public void setData(){
        calendar= Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        dayOfmonth=calendar.get(Calendar.DAY_OF_MONTH);

        //выбирая разные параметры style календарь отображается по разному
        //datePickerDialog=new DatePickerDialog(this,android.R.style.Theme_Light_NoTitleBar,

        datePickerDialog=new DatePickerDialog(Zakaz1.this,AlertDialog.BUTTON_POSITIVE,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        Calend=day + " " + (month + 1) + " " + year;
                        // выбрать время
                        setTime();
                    }

                },year,month,dayOfmonth);
        datePickerDialog.setTitle(ReftitleCalendar);
        datePickerDialog.setCancelable(false);
        datePickerDialog.show();
        }

    public void setTime(){
        calendar=Calendar.getInstance();
        hourOfDay=calendar.get(Calendar.HOUR);
        minute=calendar.get(Calendar.MINUTE);
        //SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
        timePickerDialog=new TimePickerDialog(Zakaz1.this,AlertDialog.BUTTON_POSITIVE,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (minute<10){
                            //time=hourOfDay+":"+"0"+minute;
                            time=(hourOfDay+":"+"0"+minute);
                            Right4.setVisibility(View.VISIBLE);
                            No4.setVisibility(View.INVISIBLE);
                            finishOder();
                        }
                        if (minute>=10){
                            time=(hourOfDay+":"+minute);
                            Right4.setVisibility(View.VISIBLE);
                            No4.setVisibility(View.INVISIBLE);
                            finishOder();
                        }
                    }
                },hourOfDay,minute,true);
        timePickerDialog.setTitle(ReftitleTime);
        timePickerDialog.show();
    }
    public void finishOder(){

        button1.setEnabled(false);

        Handler handler5 = new Handler();
        handler5.postDelayed(new Runnable() {
            @Override
            public void run() {

                OderRight.setTextColor(getResources().getColor(R.color.Zakaz1RightOder));
                Right5.setVisibility(View.VISIBLE);
                No5.setVisibility(View.INVISIBLE);


            }
        },500);
    }

    // Блокировка кнопки Back!!!! :)))
    @Override
    public void onBackPressed(){
    }

}