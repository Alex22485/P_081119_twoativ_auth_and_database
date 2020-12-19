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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Calendar;

public class Zakaz1 extends AppCompatActivity {

    // КОД ПРОТЕСТИРОВАН 19.12.2020

    // Первый лист заказа
    // 1.есть ловушка AlertZakaz2 неперехода на Zakaz2 (лист выбора маршрута) при несварачивании приложения
    // 2. есть ловушка AlertZakaz3finish неперехода на Zakaz3Finish (итоговый лист заказа)
    // 3.метод DateTimePointOfMap вычисляет время точки сбора и дату заказа для маршрутов До АЭропорта для разных городов
    // (время дороги и время до начала регистрации забивается в timeRoad и timeRegistration )
    // при необходимости(если заказ выпадает на первый день месяца и года) время сбора и дата заявки пересчитывается на предыдущие сутки месяц и год

    private static final String TAG ="Zakaz1";

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

    // реф слово для экспорта в Zaka3Finish (чтобы определить какой маршрут в Аэропорт или из Аэропорта)
    // по умолчанию ставим "из Аэропорта", есть будет наоборот слово перепишется на "в Аэропорт"
    String RefInFromAirport="из Аэропорта";

    // для Чартерных рейсов из Игарки
    String knowOrNotTime="Вы знаете время прилета в Красноярск?";
    String [] knowOrNotTimeChoise= {"1. Да","2. Нет, я лечу на чартере"};
    String knowOrNotTimeRef="";
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
    // Стоимость проезда Красноярск, Сосновоборск, Ачинск, Канск, Лесосибирск
    String [] RefFare = {"300","300","300","300","300","500","700","1500","2000"};
    // Выбранная стоимость проезда
    String fare="";

// ДЛЯ ВЫБОРА ДАТЫ И ВРЕМЕНИ ТОЧКИ СБОРА МАРШРУТОВ в АЭРОПОРТ

    // время дороги до аэропорта ( часы соответствуют массиву refOne выше по тексту)
    //c Красноярска 60 минут
    //c Сосновоборска 60 минут
    //c Ачинска 120 минут (165 км)
    // c Канска 240 минут (265 км)
    // c Лесосибирск 300 минут (312 км)
    Integer [] timeRoad ={60,60,60,60,60,60,120,240,300};
    // Время начала регистрации (за сколько часов до вылета)
    Integer [] timeRegistration ={120,120,120,120,120,120,120,120,120,};
    // год выбранный календарем
    Integer yearReferens;
    // месяц выбранный календарем
    Integer mounthReferens;
    // день выбранный календарем
    Integer dayReferens;
    // час выбранный календарем
    Integer hourReferens;
    // минуты выбранный календарем
    Integer minuteReferens;
    // дата точки сбора
    String dateOfPoint;
    // время точки сбора
    String timeOfPoint;
    // cоответствие месяца и количество дней в предыдущем месяце
    Integer [] monthChesk ={1,2,3,4,5,6,7,8,9,10,11,12};
    // количество дней предыдущего месяца
    Integer [] monthCheskDay ={31,31,28,31,30,31,30,31,31,30,31,30};

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

    //выбранная дата вылета-прилета (из календаря)
    String Calend;
    //выбранное вылета-прилета из (календаря)
    String time;

    // Итоговое значение часа вылета-прилета в виде двух символов
    String hourRef;
    // Итоговое значение минут вылета-прилета в виде двух символов
    String minuteRef;
    // Итоговое значение дня вылета-прилета в виде двух символов
    String dayRef;
    // Итоговое значение месяца вылета-прилета в виде двух символов
    String monthRef;

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
        Intent Zakaz4ToZakaz1=getIntent();
        phoneNewFromMain6=""+Zakaz4ToZakaz1.getStringExtra("regFromMain6");
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
        //если был то присваиваем phoneNew (лист с Заставкой)
        if (!regFromZaka3finish.equals("null")){
            phoneNew=regFromZaka3finish;
            Log.d(TAG, "phoneNewAfterSTOPODER:"+phoneNew);
        }

        // Экспорт данных с листа Zakaz2 Проверка был ли переход сюда с листа Zakaz2
        Intent backZakaz2ToZakaz1=getIntent();
        RefMap=""+backZakaz2ToZakaz1.getStringExtra("RefMap");
        RefPoint=""+backZakaz2ToZakaz1.getStringExtra("RefPoint");
        RefBackFromZakaz2=""+backZakaz2ToZakaz1.getStringExtra("RefBackFromZakaz2");
        // Если переход был нажатием кнопки НАЗАД Zakaz2
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
        // переход из Zakaz2 был c выбором маршрута
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
                    //Выбор рейса самолета (+ выбор заголовка для Alert )
                    setPlain();
                    // показать кнопку изменить условия заказа
                    button5.setVisibility(View.VISIBLE);
                }
            },700);
        }
        // переход из Zakaz2  Не был. START при первом открытии Activity
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
    }

 // ALERT DIALOGS
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
                        // недоступность кнопки выбора города
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
        listCityFromIn= new String[]{refCityTaxi + "->Аэропорт", "Аэропорт->" + refCityTaxi};
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz1.this);
        mAlertDialog.setTitle("Куда поедем?");
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setItems(listCityFromIn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        refFromInCity=listCityFromIn[which];
                        button1.setEnabled(false);
                        // задержка для дизайна+переход на Zakaz2
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
                                // проверка самолет из Игарки?
                                CheskPlainFromIgarka();
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
    // Alert выбор даты
    public void AlertCalendar(){
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
                        // данные для выбора даты точки сбора (только для маршрутов в АЭРОПОРТ)
                        yearReferens=year;
                        mounthReferens=month+1;
                        dayReferens=day;
                        // преобразования дня в два символа (Н-р 6->06)
                        if (day<10){
                            dayRef="0"+day;
                        }
                        else {
                            dayRef=""+day;
                        }
                        // преобразования месяца в два символа (Н-р 3->03)
                        if (month+1<10){
                            // +1 т.к. почему-то календарь заведомо занижает выбранный месяц на один
                            monthRef="0"+(month + 1);
                            Log.d(TAG, "ПРОВЕРКА1:"+monthRef);
                        }
                        else {
                            monthRef=""+(month + 1);
                            Log.d(TAG, "ПРОВЕРКА2:"+monthRef);
                        }
                        // итоговая дата вылета-прилета в виде слова
                        Calend=dayRef + " " + monthRef + " " + year;
                        // недоступность кнопки дата,время
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
        // Индивидуальный заголовок в календаре
        datePickerDialog.setTitle(ReftitleCalendar);
        datePickerDialog.setCancelable(false);
        datePickerDialog.show();
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
                        // данные для выбора времени точки сбора (только для маршрутов в АЭРОПОРТ)
                        hourReferens=hourOfDay;
                        minuteReferens=minute;

                        // преобразования часа в два символа (Н-р 6->06)
                        if (hourOfDay<10){
                            hourRef="0"+hourOfDay;
                        }
                        else {
                            hourRef=""+hourOfDay;
                        }

                        if (minute<10){
                            minuteRef="0"+minute;
                        }
                        if (minute>=10){
                            minuteRef=""+minute;
                        }
                        // итоговое время вылета в виде слова
                        time=hourRef+":"+minuteRef;
                        button4.setEnabled(false);
                        button5.setVisibility(View.INVISIBLE);

                        // приравниваем время+даты точки сбора с временем вылета
                        // Если маршруты ИЗ АЭРОПОРТА то это время поменяется в методе DateTimePointOfMap();
                        timeOfPoint=time;
                        dateOfPoint=Calend;

                        Log.d(TAG, "дата точки сбора+прилета из Аэропорта:"+Calend);
                        Log.d(TAG, "время точки сбора+прилета из Аэропорта:"+time);

                        // Запуск метода вычисления времени точки сбора и даты (только для маршрутов в АЭРОПОРТ)
                        DateTimePointOfMap();

                    }
                },hourOfDay,minute,true);
        timePickerDialog.setTitle(ReftitleTime);
        timePickerDialog.setCancelable(false);
        timePickerDialog.show();
    }
    // Вопрос знаешь ли время прибытия самолета или у тебя Чартер (Для Игарки)
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
                            //выбрать дату прилета из Игарки
                            AlertCalendar();
                        }
                        // если НЕ знаю время прилета,
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
    // Выбор номера чартера ИЗ Игарки
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
                                //выбора даты вылета из Игарки ТОЛЬКО ДЛЯ ЧАРТЕРА
                                AlertDataFromCharter();
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
    // Выбор даты чартера ИЗ Игарки
    public void AlertDataFromCharter(){
        Exline4();
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
                        // данные для выбора даты точки сбора (только для маршрутов в АЭРОПОРТ)
                        yearReferens=year;
                        mounthReferens=month+1;
                        dayReferens=day;
                        // преобразования дня в два символа (Н-р 6->06)
                        if (day<10){
                            dayRef="0"+day;
                        }
                        else {
                            dayRef=""+day;
                        }
                        // преобразования месяца в два символа (Н-р 3->03)
                        if (month+1<10){
                            // +1 т.к. почему-то календарь заведомо занижает выбранный месяц на один
                            monthRef="0"+(month + 1);
                            Log.d(TAG, "ПРОВЕРКА1:"+monthRef);
                        }
                        else {
                            monthRef=""+(month + 1);
                            Log.d(TAG, "ПРОВЕРКА2:"+monthRef);
                        }
                        // итоговая дата вылета-прилета в виде слова
                        Calend=dayRef + " " + monthRef + " " + year;
                        // недоступность кнопки дата,время
                        button4.setEnabled(false);
                        // скрыть кнопку изменить условия заказа
                        button5.setVisibility(View.INVISIBLE);

                        // приравниваем время+даты точки сбора с временем вылета оно будет совпадать с датой прилет и временем прилета
                        // чтобы не было null
                        timeOfPoint=time;
                        dateOfPoint=Calend;

                        // Расчет Cтоимости проезда
                        Choisfare();
                    }
                },year,month,dayOfmonth);
        // Индивидуальный заголовок в календаре Только для Чартера ИЗ Игарки
        datePickerDialog.setTitle("Дата вылета из Игарки");
        datePickerDialog.setCancelable(false);
        datePickerDialog.show();
    }
    // Alert ловушка неперехода на Zakaz2
    public void AlertZakaz2(){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz1.this);
        mAlertDialog.setMessage("Нажмите ОК, для продолжения.");
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Переход в Zaka2 для выбора маршрута
                        Zakaz1ToZakaz2();
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }
    // Alert ловушка неперехода на Zakaz3finish
    public void AlertZakaz3finish(){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz1.this);
        mAlertDialog.setMessage("Нажмите ОК, для продолжения.");
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Переход в Zaka3finish на итоговый лист заказа
                        Zakaz3finish();
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }

//МЕТОДЫ
    //Выбор заголовков для Alert dialogs (1.В-из города, 2. В Календаре, 3. При выборе времени)
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
    // проверка самолет из Игарки?.
    public void CheskPlainFromIgarka(){
        Handler ha = new Handler();
        ha.postDelayed(new Runnable() {
            @Override
            public void run() {
                Exline4();
            }
        },300);

        // если из Игарки то сначала спрашиваем Чартер или нет
        if(RefplaneCity.equals("Игарка")&& RefAlertTitle.equals(AlertIn)){
            // вызов Alert знаю/ не знаю время прилета
            showKnowTimeorNot();
        }
        //если маршрут не из Игарки запускантся выбор даты
        else{
            // выбор даты Вылета-Прилета
            AlertCalendar();
        }
    }
    // Метод вычисления время и дата точки сбора только для маршрутов в АЭРОПОРТ
    public void DateTimePointOfMap(){
        //ОПРЕДЕЛЯЕМ выбранный рейс в АЭРОПОРТ или из АЭРОПОРТА
        int i=refOne.length;
        int x;
        for ( x=0; x<i; x++){
            // КОД работает, только если выбранный рейс в АЭРОПОРТ ( для расчета ВРЕМЕНИ и ДАТЫ ТОЧКИ СБОРА)
            if (RefMap.equals(refOne[x])){
                // реф слово для экспорта в Zaka3Finish (чтобы определить какой маршрут в Аэропорт или из Аэропорта)
                RefInFromAirport="в Аэропорт";

// ОПРЕДЕЛЕНИЕ ВРЕМЕНИ ТОЧКИ СБОРА

               // вычисление времени затрат в минутах
                int sum=timeRoad[x]+timeRegistration[x];
                // вычисление времени вылета в минутах
                int timePlane = hourReferens*60+minuteReferens;


                //если время вылета меньше времени затрат МЕНЯЕМ ВРЕМЯ и ДАТУ
                if (timePlane<sum){
                    // время точки сбора в минутах
                    int timePoint=24*60-(sum-timePlane);

                    // перевод в часы+минуты
                    int h = timePoint/60;
                    int m =timePoint%60;

                    // итоговый час точки сбора
                    if(h<10){
                        timeOfPoint ="0"+h;
                    }
                    if(h>=10){
                        timeOfPoint =""+h;
                    }
                    // итоговое общее время точки сбора
                    if(m<10){
                        timeOfPoint =timeOfPoint+":"+"0"+m;
                    }
                    if(m>=10){
                        timeOfPoint =timeOfPoint+":"+m;
                    }

                    Log.d(TAG, "ВРЕМЯ ТОЧКИ СБОРА"+timeOfPoint);

// ИЗМЕНЕНИЕ ДАТЫ ТОЧКИ СБОРА

                    // если день вылета равен 1 то день точки сбора принимаем из предыдущего месяца
                    if(dayReferens==1){

                        // если год високосный
                        if (yearReferens==2020||yearReferens==2024||yearReferens==2028||yearReferens==2032||yearReferens==2036||yearReferens==2040){
                            // проверяем март ли это
                            if(mounthReferens==3){
                                // устанавливаем день точки сбора
                                dayReferens=29;
                            }
                            // проверяем какой тогда выбран месяц
                            else {
                                int y=monthChesk.length;
                                int n;
                                for ( n=0; n<y; n++){
                                    // сравниваем массив с выбранным месяцем и устанавливаем день точки сбора
                                    if (mounthReferens.equals(monthChesk[n])){
                                        // устанавливаем день точки сбора
                                        dayReferens=monthCheskDay[n];
                                    }
                                }
                            }
                        }
                        // если год НЕ високосный
                        else {
                            int y=monthChesk.length;
                            int n;
                            for ( n=0; n<y; n++){
                                // сравниваем массив с выбранным месяцем и устанавливаем день точки сбора
                                if (mounthReferens.equals(monthChesk[n])){
                                    // устанавливаем день точки сбора
                                    dayReferens=monthCheskDay[n];
                                }
                            }
                        }

                        // уменьшаем год точки сбора если месяц январь
                        if (mounthReferens==1){
                            yearReferens=yearReferens-1;
                        }
                        //уменьшаем месяц точки сбора если выбранный =1 (январь)
                        if(mounthReferens==1){
                            mounthReferens=12;
                        }
                        else {
                            mounthReferens=mounthReferens-1;
                        }

                        // ИТОГОВАЯ ДАТА точки сбора
                        if (mounthReferens<10){
                            // дата точки сбора (строка)
                            dateOfPoint=dayReferens+" "+"0"+mounthReferens+" "+yearReferens;
                        }
                        if (mounthReferens>=10){
                            // дата точки сбора (строка)
                            dateOfPoint=dayReferens+" "+mounthReferens+" "+yearReferens;
                        }

                        Log.d(TAG, "ДАТА ТОЧКИ СБОРА"+dateOfPoint);
                        //Итоговая дата

                    }
                    // если день вылета больше 1
                    else {
                        // день точки сбора (число)
                        dayReferens=dayReferens-1;

                        // дата точки сбора (строка)
                        if(dayReferens<10){
                            if(mounthReferens<10){
                                dateOfPoint="0"+dayReferens+" "+"0"+mounthReferens+" "+yearReferens;
                                Log.d(TAG, "ДАТА ТОЧКИ СБОРА"+dateOfPoint);
                            }
                            if(mounthReferens>=10){
                                dateOfPoint="0"+dayReferens+" "+mounthReferens+" "+yearReferens;
                                Log.d(TAG, "ДАТА ТОЧКИ СБОРА"+dateOfPoint);
                            }
                        }
                        if(dayReferens>=10){
                            if(mounthReferens<10){
                                dateOfPoint=dayReferens+" "+"0"+mounthReferens+" "+yearReferens;
                                Log.d(TAG, "ДАТА ТОЧКИ СБОРА"+dateOfPoint);
                            }
                            if(mounthReferens>=10){
                                dateOfPoint=dayReferens+" "+mounthReferens+" "+yearReferens;
                                Log.d(TAG, "ДАТА ТОЧКИ СБОРА"+dateOfPoint);
                            }
                        }
                    }
                }
                //если время вылета больше или рано времени затрат
                else {
                    // время точки сбора в минутах
                    int timePoint = timePlane-sum;

                    // перевод в часы+минуты
                    int h = timePoint/60;
                    int m =timePoint%60;

                    // итоговый час точки сбора
                    if(h<10){
                        timeOfPoint ="0"+h;
                    }
                    if(h>=10){
                        timeOfPoint =""+h;
                    }
                    // итоговое общее время точки сбора
                    if(m<10){
                        timeOfPoint =timeOfPoint+":"+"0"+m;
                    }
                    if(m>=10){
                        timeOfPoint =timeOfPoint+":"+m;
                    }

                    Log.d(TAG, "ВРЕМЯ ТОЧКИ СБОРА в Аэропорт"+timeOfPoint);

                    // дата точки сбора ПРИ этом не меняется и равна дате вылета
                }
            }
        }
        // определение стоимости проезда
        Choisfare();
    }
    // Cтоимости проезда
    public void Choisfare(){
        //ОПРЕДЕЛЯЕМ выбранный рейс в АЭРОПОРТ или из АЭРОПОРТА
        int i=refOne.length;
        int x;
        for ( x=0; x<i; x++){
            // если рейс в Аэропорт
            if (RefMap.equals(refOne[x])) {
                // стоимость поездки равна
                fare=RefFare[x];
            }
            // если рейс в Аэропорт
            else if (RefMap.equals(refTwo[x])){
                // стоимость поездки равна
                fare=RefFare[x];
            }
        }
        // переход на лист Zaka3Finish
        // выдержка с запасом чтобы метод успел завершиться
        Handler han = new Handler();
        han.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Последние присваивания слов для экспорта time-время самолета или номер рейса, RefplaneCity-рейс самолет
                finishChesk();
                Rline4();
                Rline5();
            }
        },300);
    }
    // Последние присваивания слов для экспорта time-время самолета или номер рейса, RefplaneCity-рейс самолет
    public void finishChesk(){
        // если рейс самолета чартер из Игарки то присваиваем time номер рейса чартера 1,2,3
        if (haveACharter.equals("haveACharter")){
            time=numberCharterRef;
            // приравниваем время+даты точки сбора с временем вылета оно будет совпадать с датой прилет и временем прилета
            // чтобы не было null
            timeOfPoint=time;
            dateOfPoint=Calend;
        }

        // присваиваем полное название рейса самолета Н-р Игарка красноярск или Красноярск-Новосибирск
        if (RefAlertTitle.equals(AlertIn)){
            RefplaneCity=RefplaneCity+"-"+"Красноярск";
        }
        if (RefAlertTitle.equals(AlertFrom)){
            RefplaneCity="Красноярск"+"-"+RefplaneCity;
        }
        //переход на итоговый лист регистрации заявки Zakaz3finish()
        Zakaz3finish();
    }

// ВИЗУАЛИЗАЦИИ
    // Дизайн при входе в Activity
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
    // Визуализация при вызове календаря вызов времени
    public void choiseSetTime(){
        Handler ha = new Handler();
        ha.postDelayed(new Runnable() {
            @Override
            public void run() {
                Exline4();
            }
        },300);
            showTimeCalendar();
    }
    // Видимости линии 1-5
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
    // задержка для дизайна+ переход на Zakaz2 выбор маршрута
    public void timeOut1(){
        Handler handler5 = new Handler();
        handler5.postDelayed(new Runnable() {
            @Override
            public void run() {
                Rline1();
            }
        },400);

        // переход на Zakaz2 выбор маршрута
        Handler handler6 = new Handler();
        handler6.postDelayed(new Runnable() {
            @Override
            public void run() {
                // выбор маршрута
                Zakaz1ToZakaz2();
            }
        },500);
    }

//КНОПКИ
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
        // проверка самолет из Игарки?
        CheskPlainFromIgarka();
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

// ПЕРЕХОДЫ НА ДРУГИЕ АКТИВИТИ
    // Переход в Zaka2 для выбора маршрута
    public void Zakaz1ToZakaz2(){
    Intent Zakaz1ToZakaz2=new Intent(this,Zakaz2.class);
    Zakaz1ToZakaz2.putExtra("refFromInCity",refFromInCity);
    //отправляем phoneNew в Zakaz2
    Zakaz1ToZakaz2.putExtra("phoneNew",phoneNew);
    Log.d(TAG, "Cтарт переход в Zakaz2 phoneNew:"+phoneNew);
    startActivity(Zakaz1ToZakaz2);

        // Alert ловушка неперехода на Zakaz2
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Alert ловушка неперехода на Zakaz2
                AlertZakaz2();
            }
            },1000);
    }
    // Переход на Форму обратной связи (если нет нужного города)
    public void FeedBack1(){
        Intent FeedBack1= new Intent(this,FeedBack1.class);
        startActivity(FeedBack1);
    }
    // Переход на Zakaz3finish
    public void Zakaz3finish() {
        // передаем данные для регистрации заявки
        Intent Zakaz1ToZakaz3finish = new Intent (this,Zakaz3finish.class);
        // secretNumber
        Zakaz1ToZakaz3finish.putExtra("phoneNew", phoneNew);
        // дата полета
        Zakaz1ToZakaz3finish.putExtra("Calend", Calend);
        // дата точки сбора (для рейсов из Аэропорта)
        Zakaz1ToZakaz3finish.putExtra("dateOfPoint", dateOfPoint);
        // рейс самолета
        Zakaz1ToZakaz3finish.putExtra("RefplaneCity", RefplaneCity);
        // время вылета/прилета/номер рейса для чартера
        Zakaz1ToZakaz3finish.putExtra("time", time);
        // маршрут
        Zakaz1ToZakaz3finish.putExtra("RefMap", RefMap);
        // точка сбора
        Zakaz1ToZakaz3finish.putExtra("RefPoint", RefPoint);
        // Время точки сбора (для рейсов из Аэропорта)
        Zakaz1ToZakaz3finish.putExtra("timeOfPoint", timeOfPoint);
        // реф слово для экспорта в Zaka3Finish (чтобы определить какой маршрут "в Аэропорт" или "из Аэропорта")
        Zakaz1ToZakaz3finish.putExtra("RefInFromAirport", RefInFromAirport);
        // стоимость проезда
        Zakaz1ToZakaz3finish.putExtra("fare", fare);
        startActivity(Zakaz1ToZakaz3finish);

        // Alert ловушка неперехода на Zakaz2
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Alert ловушка неперехода на Zakaz3finish
                AlertZakaz3finish();
            }
        },1000);

        Log.d(TAG, "итог телефон:"+phoneNew);
        Log.d(TAG, "итог дата вылета-прилета:"+Calend);
        Log.d(TAG, "итог дата точки сбора:"+dateOfPoint);
        Log.d(TAG, "итог время вылета-прилета:"+time);
        Log.d(TAG, "итог время точки сбора:"+timeOfPoint);
        Log.d(TAG, "итог самолет:"+RefplaneCity);
        Log.d(TAG, "итог маршрут:"+RefMap);
        Log.d(TAG, "итог точка сбора:"+RefPoint);
        Log.d(TAG, "итог тип маршрута В(из) Аэропорта:"+RefInFromAirport);
        Log.d(TAG, "итог стоимость проезда:"+fare);
    }

    // Блокировка кнопки Back!!!! :)))
    @Override
    public void onBackPressed(){
    }

}