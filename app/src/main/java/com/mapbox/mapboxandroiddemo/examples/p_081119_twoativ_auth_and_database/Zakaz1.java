package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Calendar;

public class Zakaz1 extends AppCompatActivity {

    // Первый лист заказа
    // есть таймер сворачивания для перехода в Zakaz2 выбор маршрута
    // есть таймер сворачивания для перехода в Zakaz3Finish итоговый лист заказа
    // есть время сессии, запускается при переходе в Zakaz2 выбор маршрута
    // есть время сессии, запускается при переходе в Zakaz3Finish итоговый лист заказа

    private static final String TAG ="Zakaz1" ;

    String phoneNew;
    String phoneNewFromMain6;
    String regFromZaka3finish;

    Button button1,button3,button4,button5;
    TextView button2;
    TextView Right1,Right2,Right3,Right4,Right5;
    TextView No1,No2,No3,No4,No5;
    TextView Exclamation1,Exclamation2,Exclamation3,Exclamation4;
    TextView OderRight;
    // прогресс бар появляется в момент запуска времени сессии при переходе на лист Zakaz1
    ProgressBar progressBar;

    // выбранный маршрут
    String RefMap;
    // выбранная точка сбора
    String RefPoint;

    // Реф значение был ли переход из Zakaz2
    String RefBackFromZakaz2;

    // выбранный рейс самолета (город)
    String RefplaneCity;

    // для Чартерных рейсов из Игарки
    String knowOrNotTime="Вы знаете время прилета в Красноярск?";
    String [] knowOrNotTimeChoise= {"1. Да","2. Нет, я лечу на чартере"};
    String knowOrNotTimeRef;
    String choiseNumberCharter="Ваш порядковый номер самолета чартера из Игарки";
    String [] numberCharter = {"1 рейс","2 рейс","3 рейс"};
    String numberCharterRef;
    String haveACharter="";

    String [] listCityTaxi= {"Красноярск","Сосновоборск","Ачинск","Канск","Лесосибирск","Нет нужного города"};
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
    // Итоговое значение часа вылета в виде слова
    String hourRef;

    // время выдержки времени для исключения неперехода на др активити при сварачивании,
    // есть порог 5 секунд меньше которых переход на др активити не сработает при сварачивании)
    // поэтому при сварачивании, в OnStop увеличиваем таймер еще на 5 секунд
    Integer b,c,d,e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakaz1);

        // для таймера сварачивания
        // взято призвольное мальнькое время
        c=10;
        e=10;


        button1=findViewById(R.id.button1);
        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);
        button4=findViewById(R.id.button4);
        button5=findViewById(R.id.button5);
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
        progressBar=findViewById(R.id.progressBar);

        button5.setVisibility(View.INVISIBLE);
        AllLineNoShow();
        // убрать видимость прогресс бара
        progressBar.setVisibility(View.INVISIBLE);

        //транзит из Заставки MainActivity. переход скачком на эту страницу если ранее зарегистрирован и нет новых заявок
        Intent MainActivityToZakaz1= getIntent();
        phoneNew=MainActivityToZakaz1.getStringExtra("phoneNew");
        Log.d(TAG, "phoneNew:"+phoneNew);

        // проверка был ли переход на эту страницу после отмены заявки
        Intent Main6ToZakaz1=getIntent();
        phoneNewFromMain6=""+Main6ToZakaz1.getStringExtra("regFromMain6");
        Log.d(TAG, "phoneNewFromMain6:"+phoneNewFromMain6);

        //если был то пишем phoneNew=phoneNewFromMain6
        if (!phoneNewFromMain6.equals("null")){
            phoneNew=phoneNewFromMain6;
            Log.d(TAG, "phoneNewNEW:"+phoneNew);
        }

        // проверка был ли переход на эту страницу из Main3Activity после запрета регистрации на уже сформировавийся маршрут (STOPODER)
        Intent Zakaz3FinishToZakaz1=getIntent();
        regFromZaka3finish=""+Zakaz3FinishToZakaz1.getStringExtra("regFromMain3");
        Log.d(TAG, "regFromMain3:"+regFromZaka3finish);

        //если был то присваеваем phoneNew (лист с Заставкой)
        if (!regFromZaka3finish.equals("null")){
            phoneNew=regFromZaka3finish;
            Log.d(TAG, "phoneNewAfterSTOPODER:"+phoneNew);
        }


        // Экспорт данных с листа Zakaz2 Проверка был ли переход сюда с листа Zakaz2
        Intent backZakaz2ToZakaz1=getIntent();
        RefMap=""+backZakaz2ToZakaz1.getStringExtra("RefMap");
        RefPoint=""+backZakaz2ToZakaz1.getStringExtra("RefPoint");
        RefBackFromZakaz2=""+backZakaz2ToZakaz1.getStringExtra("RefBackFromZakaz2");


        // переход был нажатием кнопки НАЗАД
        if(RefBackFromZakaz2.equals("backNoFromZakaz2")){
            // экспорт phoneNew из Zakaz2
            phoneNew=""+backZakaz2ToZakaz1.getStringExtra("phoneNew");
            Log.d(TAG, "Переход из Zakaz2 phoneNew:"+phoneNew);

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
            // экспорт phoneNew из Zakaz2
            phoneNew=""+backZakaz2ToZakaz1.getStringExtra("phoneNew");
            Log.d(TAG, "Переход из Zakaz2 phoneNew:"+phoneNew);

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

                    }
                    },400);

            // задержка Alert рейс самолета
            Handler setPlain = new Handler();
            setPlain.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Выбор рейс самолета (+ выбор заголовка для Alert )
                    setPlain();
                    // показать кнопку изменить условия заказа
                    button5.setVisibility(View.VISIBLE);
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
    @Override
    protected void onStop (){
        super.onStop();
        Log.d(TAG, "onStop");
        // таймер-сворачивания для перехода в Zakaz2
        c=5000;
        // таймер-сворачивания для перехода в Zakaz3Finish
        e=5000;
        Log.d(TAG, "onStop c="+c);

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

    // кнопка button1 выбор города
    public void button1(View view){
        AlertChoiceCity();
    }
    // кнопка button3 рейс самолета
    public void button3(View view){
        showAlert();
    }
    // кнопка button4 дата время
    public void button4(View view){
        setData();
    }
    // кнопка button5 изменить условия заказа
    public void button5(View view){
        changeMyOder();
    }
    //изменить условия заказа
    public void changeMyOder(){

        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz1.this);
        mAlertDialog.setTitle("Настройки вашего заказа будут стерты, продолжить?");
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // скрыть кнопку изменить условия заказа
                        button5.setVisibility(View.INVISIBLE);
                        AllLineNoShow();

                        Handler handler1 = new Handler();
                        handler1.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Nline1();
                                dinamicView();
                            }
                        },300);

                    }
                });
        mAlertDialog
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
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
                        if (refCityTaxi.equals("Нет нужного города")){
                            // нет нужного города
                            FeedBack1();
                            return;
                        }

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
        mAlertDialog.setTitle("Куда поедем?");
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
    // задержка для дизайна+ переход на  Zakaz1 выбор маршрута
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
                //таймер сварачивания для перехода на Zakaz2 выбор маршрута
                timePlus();
                // показать видимость прогресс бара
                progressBar.setVisibility(View.VISIBLE);
                // запуск времени сессии на случай если приложение не перейдет на др. активити Zakaz1 при сворачивании приложения
                Log.d(TAG, "Time Session Start");
                Handler timeSession = new Handler();
                timeSession.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // время сессии истекло
                        timeSessionEnd();
                        Log.d(TAG, "время сессии 1 истекло");
                    }
                },20000);
            }
        },600);
    }
    public void Zakaz1ToZakaz2(){

        Intent Zakaz1ToZakaz2=new Intent(this,Zakaz2.class);
        Zakaz1ToZakaz2.putExtra("refFromInCity",refFromInCity);
        //отправляем phoneNew в Zakaz2
        Zakaz1ToZakaz2.putExtra("phoneNew",phoneNew);
        Log.d(TAG, "Cтарт переход в Zakaz2 phoneNew:"+phoneNew);

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

                        if (RefplaneCity.equals("Нет нужного города")){
                            FeedBack1();
                            return;
                        }
                        // убрать кнопку изменить условия заказа
                        button5.setVisibility(View.INVISIBLE);
                        Rline3();
                        // Задержка на появление Календаря
                        Handler Date = new Handler();
                        Date.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // выбрать дату
                                setData();
                                // показать кнопку изменить условия заказа
                                button5.setVisibility(View.VISIBLE);
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
                        // скрыть кнопку изменить условия заказа
                        button5.setVisibility(View.INVISIBLE);

                        Handler handlerNeg1 = new Handler();
                        handlerNeg1.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // выбрать время
                                choiseSetTime();
                                // показать кнопку изменить условия заказа
                                button5.setVisibility(View.VISIBLE);
                            }
                        },300);
                    }
                },year,month,dayOfmonth);
        datePickerDialog.setTitle(ReftitleCalendar);
        datePickerDialog.setCancelable(false);
        datePickerDialog.show();
        }
    // вызов времени
    public void choiseSetTime(){
        Handler ha = new Handler();
        ha.postDelayed(new Runnable() {
            @Override
            public void run() {
                Exline4();
            }
        },300);

        // если человек летит из Игарки показать вопрос знает ли он время прибытия самолета или у него чартер
        if(RefplaneCity.equals("Игарка")&& RefAlertTitle.equals(AlertIn)){
            // вызов Alert знаю/ не знаю время прилета
            showKnowTimeorNot();
        }

        // в других случаях выбрать время прилета
        else {
            //выбрать время прилета
            showTimeCalendar();
        }
    }
    //выбрать время прилета
    public void showTimeCalendar(){
        calendar=Calendar.getInstance();
        hourOfDay=calendar.get(Calendar.HOUR);
        minute=calendar.get(Calendar.MINUTE);
        //SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
        timePickerDialog=new TimePickerDialog(Zakaz1.this,AlertDialog.BUTTON_POSITIVE,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // преобразования часа в два символа (Н-р 6->06)
                        if (hourOfDay<10){
                            hourRef="0"+hourOfDay;
                        }
                        else {
                            hourRef=""+hourOfDay;
                        }

                        if (minute<10){
                            //time=hourOfDay+":"+"0"+minute;
                            time=(hourRef+":"+"0"+minute);
                            button4.setEnabled(false);
                            button5.setVisibility(View.INVISIBLE);
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
                            time=(hourRef+":"+minute);
                            button4.setEnabled(false);
                            button5.setVisibility(View.INVISIBLE);
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

    public void showKnowTimeorNot(){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz1.this);
        mAlertDialog.setTitle(knowOrNotTime);
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setItems(knowOrNotTimeChoise, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        knowOrNotTimeRef=knowOrNotTimeChoise[which];

                        // если знаю время прилета,
                        if (knowOrNotTimeRef.equals("1. Да")){
                            //выбрать время прилета
                            showTimeCalendar();
                        }
                        else {
                            // выбрать номер чартера
                            choisNumberCharter();
                        }
                    }
                });
        mAlertDialog
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Handler handlerNeg1 = new Handler();
                        handlerNeg1.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Exline4();
                            }
                        },300);
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }

    public void choisNumberCharter(){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz1.this);
        mAlertDialog.setTitle(choiseNumberCharter);
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setItems(numberCharter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        numberCharterRef=numberCharter[which];
                        haveACharter="haveACharter";
                        Handler han = new Handler();
                        han.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Rline4();
                                finishOder();
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
                                //обнуляем значение
                                knowOrNotTimeRef="";
                                Exline4();
                            }
                        },300);
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }

    // Заказ готов
    public void finishOder(){
        // запуск времени сессии на случай если приложение не перейдет на др. активити Zakaz3Finish при сворачивании приложения
        Handler timeSession = new Handler();
        timeSession.postDelayed(new Runnable() {
            @Override
            public void run() {
                // время сессии истекло
                timeSessionEnd();
                Log.d(TAG, "время сессии 2 истекло");
            }
        },20000);
        Handler handler5 = new Handler();
        handler5.postDelayed(new Runnable() {
            @Override
            public void run() {
                Rline5();
                //таймер сварачивания  при переходе на Zakaz3finish()
                timePlus2();
                // показать видимость прогресс бара
                progressBar.setVisibility(View.VISIBLE);
            }
        },300);
    }

    public void Zakaz3finish() {

        // если рейс самолета чартер из Игарки то присваиваем time номер рейса чартера 1,2,3
        if (haveACharter.equals("haveACharter")){
            time=numberCharterRef;
        }

        // присваиваем полное название рейса самолета Н-р Игарка красноярск или Красноярск-Новосибирск
        if (RefAlertTitle.equals(AlertIn)){
            RefplaneCity=RefplaneCity+"-"+"Красноярск";
        }
        if (RefAlertTitle.equals(AlertFrom)){
            RefplaneCity="Красноярск"+"-"+RefplaneCity;
        }

        // передаем данные для регистрации заявки
        Intent Zakaz1ToZakaz3finish = new Intent (this,Zakaz3finish.class);
        // secretNumber
        Zakaz1ToZakaz3finish.putExtra("phoneNew", phoneNew);
        // дата полета
        Zakaz1ToZakaz3finish.putExtra("Calend", Calend);
        // рейс самолета
        Zakaz1ToZakaz3finish.putExtra("RefplaneCity", RefplaneCity);
        // время вылета/прилета/номер рейса для чартера
        Zakaz1ToZakaz3finish.putExtra("time", time);
        // маршрут
        Zakaz1ToZakaz3finish.putExtra("RefMap", RefMap);
        // точка сбора
        Zakaz1ToZakaz3finish.putExtra("RefPoint", RefPoint);
        startActivity(Zakaz1ToZakaz3finish);
    }
    // нет нужного города
    public void FeedBack1(){
        Intent FeedBack1= new Intent(this,FeedBack1.class);
        startActivity(FeedBack1);
    }
    // ТАЙМЕР СВАРАЧИВАНИЯ

    // 1.при переходе на лист Zakaz2
    public void timePlus(){
        b=c+10;
        Log.d(TAG, "timePlus b="+b);
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // выбор маршрута
                Zakaz1ToZakaz2();
            }
        },b);
    }
    // 1.при переходе на итоговый лист регистрации заявки Zakaz3finish()
    public void timePlus2(){
        d=e+10;
        Log.d(TAG, "timePlus2 d="+d);
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                //переход на итоговый лист регистрации заявки Zakaz3finish()
                Zakaz3finish();
            }
        },d);
    }

    // время сессии истекло
    public void timeSessionEnd(){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz1.this);
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setMessage("Время сессии истекло, приложение будет перезапущено")
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //перезапуск приложения
                       reStartApp();
                    }
                });
        mAlertDialog.create();
        // Showing Alert Message
        mAlertDialog.show();
    }
    //перезапуск приложения если время сессии истекло
    public void reStartApp(){
        Intent ddd=new Intent(this,MainActivity.class);
        startActivity(ddd);
    }
    // Блокировка кнопки Back!!!! :)))
    @Override
    public void onBackPressed(){
    }

}