package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Calendar;

public class Zakaz1 extends AppCompatActivity {

    //private static final String TAG ="Zakaz1" ;

    Button button1,button3,button4;
    TextView button2;
    TextView Right1,Right2,Right3,Right4,Right5;
    TextView No1,No2,No3,No4,No5;
    TextView Exclamation1,Exclamation2,Exclamation3,Exclamation4;
    TextView OderRight;

    // выбранный маршрут
    String RefMap;
    // выбранная точка сбора
    String RefPoint;

    // Реф значение был ли переход из Zakaz2
    String RefBackFromZakaz2;

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
        AllLineNoShow();


        // Проверка был ли переход сюда с листа Zakaz2
        Intent backZakaz2ToZakaz1=getIntent();
        RefMap=""+backZakaz2ToZakaz1.getStringExtra("RefMap");
        RefPoint=""+backZakaz2ToZakaz1.getStringExtra("RefPoint");
        RefBackFromZakaz2=""+backZakaz2ToZakaz1.getStringExtra("RefBackFromZakaz2");

        // переход был нажатием кнопки НАЗАД
        if(RefBackFromZakaz2.equals("backNoFromZakaz2")){
          Nline1();
          Nline2();
          Nline3();
          Nline4();
          Nline5();

          Handler if1 = new Handler();
          if1.postDelayed(new Runnable() {
              @Override
              public void run() {
                  Exline1();
                  Exline2();
                  button1.setEnabled(false);
              }
              },400);

          Handler if2 = new Handler();
          if2.postDelayed(new Runnable() {
              @Override
              public void run() {
                  AlertChoiceCity();
              }
              },700);
        }

        // переход был c выбором маршрута
        if (RefBackFromZakaz2.equals("backYesFromZakaz2")){
            Rline1();
            Nline2();
            Nline3();
            Nline4();
            Nline5();

            Handler handler0 = new Handler();
            handler0.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Rline2();
                    //button2.setTextColor(getResources().getColor(R.color.Zakaz1RightOder));
                    }
                    },400);

            // задержка Alert рейс самолета
            Handler setPlain = new Handler();
            setPlain.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Выбор заголовка для Alert (рейс самолета)
                    setPlain();
                }
            },700);
        }

        // переход  Не был
        if (RefBackFromZakaz2.equals("null")){

            Nline1();
            // динамик дизайн при первом открытии Activity
            dinamicView();
        }
    }

    // видимости линии 1-5
    public void Rline1(){
        button1.setVisibility(View.VISIBLE);
        button1.setEnabled(false);
        Right1.setVisibility(View.VISIBLE);
        No1.setVisibility(View.INVISIBLE);
        Exclamation1.setVisibility(View.INVISIBLE);
    }
    public void Nline1(){
        button1.setVisibility(View.VISIBLE);
        button1.setEnabled(false);
        Right1.setVisibility(View.INVISIBLE);
        No1.setVisibility(View.VISIBLE);
        Exclamation1.setVisibility(View.INVISIBLE);
    }
    public void Exline1(){
        button1.setVisibility(View.VISIBLE);
        button1.setEnabled(true);
        Right1.setVisibility(View.INVISIBLE);
        No1.setVisibility(View.INVISIBLE);
        Exclamation1.setVisibility(View.VISIBLE);
    }
    public void Rline2(){
        button2.setVisibility(View.VISIBLE);
        button2.setEnabled(false);
        Right2.setVisibility(View.VISIBLE);
        No2.setVisibility(View.INVISIBLE);
        Exclamation2.setVisibility(View.INVISIBLE);
    }
    public void Nline2(){
        button2.setVisibility(View.VISIBLE);
        Right2.setVisibility(View.INVISIBLE);
        No2.setVisibility(View.VISIBLE);
        Exclamation2.setVisibility(View.INVISIBLE);
    }
    public void Exline2(){
        button2.setVisibility(View.VISIBLE);
        Right2.setVisibility(View.INVISIBLE);
        No2.setVisibility(View.INVISIBLE);
        Exclamation2.setVisibility(View.VISIBLE);
    }
    public void Rline3(){
        button3.setVisibility(View.VISIBLE);
        button3.setEnabled(false);
        Right3.setVisibility(View.VISIBLE);
        No3.setVisibility(View.INVISIBLE);
        Exclamation3.setVisibility(View.INVISIBLE);
    }
    public void Nline3(){
        button3.setVisibility(View.VISIBLE);
        button3.setEnabled(false);
        Right3.setVisibility(View.INVISIBLE);
        No3.setVisibility(View.VISIBLE);
        Exclamation3.setVisibility(View.INVISIBLE);
    }
    public void Exline3(){
        button3.setVisibility(View.VISIBLE);
        button3.setEnabled(true);
        Right3.setVisibility(View.INVISIBLE);
        No3.setVisibility(View.INVISIBLE);
        Exclamation3.setVisibility(View.VISIBLE);
    }
    public void Rline4(){
        button4.setVisibility(View.VISIBLE);
        button4.setEnabled(false);
        Right4.setVisibility(View.VISIBLE);
        No4.setVisibility(View.INVISIBLE);
        Exclamation4.setVisibility(View.INVISIBLE);
    }
    public void Nline4(){
        button4.setVisibility(View.VISIBLE);
        button4.setEnabled(false);
        Right4.setVisibility(View.INVISIBLE);
        No4.setVisibility(View.VISIBLE);
        Exclamation4.setVisibility(View.INVISIBLE);
    }
    public void Exline4(){
        button4.setVisibility(View.VISIBLE);
        button4.setEnabled(true);
        Right4.setVisibility(View.INVISIBLE);
        No4.setVisibility(View.INVISIBLE);
        Exclamation4.setVisibility(View.VISIBLE);
    }
    public void Rline5(){
        OderRight.setVisibility(View.VISIBLE);
        OderRight.setEnabled(true);
        Right5.setVisibility(View.VISIBLE);
        No5.setVisibility(View.INVISIBLE);
    }
    public void Nline5(){
        OderRight.setVisibility(View.VISIBLE);
        Right5.setVisibility(View.INVISIBLE);
        No5.setVisibility(View.VISIBLE);
    }
    public void AllLineNoShow(){
        button1.setVisibility(View.INVISIBLE);
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
    }

    // кнопка button1
    public void button1(View view){
        AlertChoiceCity();
    }
    // кнопка button3
    public void button3(View view){
        showAlert();
    }
    // кнопка button4
    public void button4(View view){
        setData();
    }

    // динамик дизайн при входе в Activity
    public void dinamicView(){

        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                Nline2();
            }
        },300);

        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                Nline3();
            }
        },600);

        Handler handler3 = new Handler();
        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                Nline4();
            }
        },900);

        Handler handler4 = new Handler();
        handler4.postDelayed(new Runnable() {
            @Override
            public void run() {
                Nline5();
                //выбор города
                AlertChoiceCity();
            }
        },1200);
    }
    // Выбор города
    public void AlertChoiceCity(){

        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz1.this);
        mAlertDialog.setTitle("Выберите город");
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setItems(listCityTaxi, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        refCityTaxi=listCityTaxi[which];
                        button1.setEnabled(false);

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

                        Handler handlerNeg1 = new Handler();
                        handlerNeg1.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Exline1();
                            }
                            },400);
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }
    // Выбор в/из города
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
                        button1.setEnabled(false);
                        // задержка для дизайна
                        timeOut1();
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
                                Exline1();
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
                Rline1();
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

    //Выбор заголовка для Alert (рейс самолета)
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
                        Rline3();
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
                        Handler handlerNeg1 = new Handler();
                        handlerNeg1.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Exline3();
                            }
                            },300);
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }
    // вызов календаря
    public void setData(){
        Handler ha = new Handler();
        ha.postDelayed(new Runnable() {
            @Override
            public void run() {
                Exline4();
            }
        },300);

        calendar= Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        dayOfmonth=calendar.get(Calendar.DAY_OF_MONTH);

        //выбирая разные параметры style календарь отображается по разному
        //datePickerDialog=new DatePickerDialog(this,android.R.style.Theme_Light_NoTitleBar,
        // .....Zakaz1.this,AlertDialog.BUTTON_POSITIVE дает написать заголовок в календаре! УРА

        datePickerDialog=new DatePickerDialog(Zakaz1.this,AlertDialog.BUTTON_POSITIVE,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        Calend=day + " " + (month + 1) + " " + year;
                        button4.setEnabled(false);
                        Handler handlerNeg1 = new Handler();
                        handlerNeg1.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // выбрать время
                                setTime();
                            }
                        },300);
                    }
                },year,month,dayOfmonth);
        datePickerDialog.setTitle(ReftitleCalendar);
        datePickerDialog.setCancelable(false);
        datePickerDialog.show();
        }
    // вызов времени
    public void setTime(){
        Handler ha = new Handler();
        ha.postDelayed(new Runnable() {
            @Override
            public void run() {
                Exline4();
            }
        },300);
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
                            button4.setEnabled(false);
                            Handler han = new Handler();
                            han.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Rline4();
                                    finishOder();
                                }
                            },300);
                        }
                        if (minute>=10){
                            time=(hourOfDay+":"+minute);
                            button4.setEnabled(false);
                            Handler han = new Handler();
                            han.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Rline4();
                                    finishOder();
                                }
                            },300);
                        }
                    }
                },hourOfDay,minute,true);
        timePickerDialog.setTitle(ReftitleTime);
        timePickerDialog.show();
    }
    // Oder is Finish
    public void finishOder(){
        Handler handler5 = new Handler();
        handler5.postDelayed(new Runnable() {
            @Override
            public void run() {
                Rline5();
            }
        },300);
    }
    // Блокировка кнопки Back!!!! :)))
    @Override
    public void onBackPressed(){
    }

}