package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Main3Activity extends AppCompatActivity {

    private static final String TAG ="Main3Activity";


    String toOrFrom;
    String refCity;

    String tOBeforReg;
    String proverkaBeforRegistraion;

    LinearLayout TextRegistration;
    LinearLayout TextRegistrationFromCity;
    LinearLayout TextProgress;
    LinearLayout MistakeRegistration;
    LinearLayout TextData;
    LinearLayout TextNumberFlight;
    LinearLayout TextTime;


    Button btnInsert;
    Button btnInsertTime;
    Button btn_number_Flight;
    Button choisData;

    FirebaseAuth mAuth;
    FirebaseDatabase database01;
    DatabaseReference ref01;

    TextView Calend;
    TextView CalendTime;
    TextView Flight;
    TextView time;
    TextView TextMarshryt;
    TextView TextMarshrytTime;
    TextView TextSbor;
    TextView TextSborTime;

    int year;
    int month;
    int dayOfmonth;

    int hourOfDay;
    int minute;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    String[] listFlights = {"1","2","3"};
    String newToken;
    String TVchoiseMap;
    String TVchoise_pointMap;
    String MapTop;
    String userPhone;
    String timeOut;
    String proverka;

    String registrationREF;
    String reg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Intent MainUserNewOne= getIntent();
        registrationREF =MainUserNewOne.getStringExtra( "registration" );
        reg="k"+registrationREF;
        Log.d(TAG, "registration:"+registrationREF);
        Log.d(TAG, "registration1:"+reg);

        // putExtra from InAir_choise_routes
        Intent nextList = getIntent();
        TVchoiseMap = nextList.getStringExtra( "TVchoiseMap" );
        TVchoise_pointMap = nextList.getStringExtra( "TVchoise_pointMap" );
        MapTop = nextList.getStringExtra( "mapTop" );

        toOrFrom =nextList.getStringExtra("toOrFrom");
        refCity= nextList.getStringExtra("refCity");

        Log.d(TAG, "toOrFrom: "+toOrFrom);
        Log.d(TAG, "refCity: "+refCity);

        TextData=findViewById(R.id.TextData);
        TextNumberFlight=findViewById(R.id.TextNumberFlight);
        TextTime=findViewById(R.id.TextTime);

        TextRegistration= findViewById(R.id.TextRegistration);
        TextRegistrationFromCity= findViewById(R.id.TextRegistrationFromCity);

        TextProgress= findViewById(R.id.TextProgress);
        MistakeRegistration= findViewById(R.id.MistakeRegistration);

        Flight = findViewById(R.id.Flight);
        time = findViewById(R.id.time);

        btn_number_Flight=findViewById( R.id.btn_number_Flight );

        TextMarshryt=findViewById( R.id.TextMarshryt );
        TextMarshrytTime=findViewById( R.id.TextMarshrytTime );

        TextSbor=findViewById( R.id.TextSbor );
        TextSborTime=findViewById( R.id.TextSborTime );

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser ghg = mAuth.getCurrentUser();
        userPhone = ghg.getPhoneNumber();

        // Получить Токен!!!!
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(Main3Activity.this,new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                newToken = instanceIdResult.getToken();
            }
        }
        );
        // ADD Calendar
        choisData=findViewById(R.id.choisData);
        Calend=findViewById(R.id.Calend);
        CalendTime=findViewById(R.id.CalendTime);

        //btnInsert = findViewById(R.id.btnInsert);

        choisData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar=Calendar.getInstance();
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH);
                dayOfmonth=calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog=new DatePickerDialog(Main3Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Calend.setText(day + " " + (month + 1) + " " + year);
                        CalendTime.setText(day + " " + (month + 1) + " " + year);
                        if(toOrFrom.equals("В Красноярск")){
                            TextData.setVisibility(View.GONE);
                            TextNumberFlight.setVisibility(View.VISIBLE);
                            showNumberFlight();
                        }
                        if(toOrFrom.equals("Из Красноярска")){
                            TextData.setVisibility(View.GONE);
                            TextTime.setVisibility(View.VISIBLE);
                            showTime();
                        }
                    }
                    },
                        year,month,dayOfmonth);
                datePickerDialog.show();
            }
        }
        );

// Disable Button if Text is Empty
//        Calend.addTextChangedListener( loginTextWather );
//        Flight.addTextChangedListener( loginTextWather );

        //Экспорт
        TextMarshryt.setText(TVchoiseMap);
        TextSbor.setText(TVchoise_pointMap);

        TextMarshrytTime.setText(TVchoiseMap);
        TextSborTime.setText(TVchoise_pointMap);

        TextData.setVisibility(View.VISIBLE);
        showCalendar();

    }

    // Disable Button if Text is Empty
//    TextWatcher loginTextWather = new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//        }
//        @Override
//        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            String calendInput =Calend.getText().toString().trim();
//            String flightInput =Flight.getText().toString().trim();
//            btn_number_Flight.setEnabled(!calendInput.isEmpty());
//            btnInsert.setEnabled(!calendInput.isEmpty()&& !flightInput.isEmpty() );
//        }
//        @Override
//        public void afterTextChanged(Editable editable) {
//        }
//    };

    // Показать календарь
    public void showCalendar (){

        Log.d(TAG, "Запуск Календаря: "+"запуск");
        calendar=Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        dayOfmonth=calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog=new DatePickerDialog(Main3Activity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Calend.setText(day + " " + (month + 1) + " " + year);
                        CalendTime.setText(day + " " + (month + 1) + " " + year);
                        if(toOrFrom.equals("В Красноярск")){
                            TextData.setVisibility(View.GONE);
                            TextNumberFlight.setVisibility(View.VISIBLE);
                            showNumberFlight();
                        }
                        if(toOrFrom.equals("Из Красноярска")){
                            TextData.setVisibility(View.GONE);
                            TextTime.setVisibility(View.VISIBLE);
                            showTime();
                        }

                    }
                },
                year,month,dayOfmonth);
        datePickerDialog.show();
    }



    // Показать время
    public void showTime(){
        calendar=Calendar.getInstance();
        hourOfDay=calendar.get(Calendar.HOUR);
        minute=calendar.get(Calendar.MINUTE);
        //SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
        timePickerDialog=new TimePickerDialog(Main3Activity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (minute<10){
                            //time=hourOfDay+":"+"0"+minute;
                            time.setText(hourOfDay+":"+"0"+minute);
                            TextTime.setVisibility(View.GONE);
                            TextRegistrationFromCity.setVisibility(View.VISIBLE);
                        }
                        if (minute>=10){
                            time.setText(hourOfDay+":"+minute);
                            TextTime.setVisibility(View.GONE);
                            TextRegistrationFromCity.setVisibility(View.VISIBLE);
                        }


                        Log.d(TAG, "время: "+time);
                    }
                },hourOfDay,minute,true);
        timePickerDialog.show();
    }

    // Показать время
    public void btn_time (View view){
        calendar=Calendar.getInstance();
        hourOfDay=calendar.get(Calendar.HOUR);
        minute=calendar.get(Calendar.MINUTE);
        //SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
        timePickerDialog=new TimePickerDialog(Main3Activity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (minute<10){
                            //time=hourOfDay+":"+"0"+minute;
                            time.setText(hourOfDay+":"+"0"+minute);
                            TextTime.setVisibility(View.GONE);
                            TextRegistrationFromCity.setVisibility(View.VISIBLE);
                        }
                        if (minute>=10){
                            time.setText(hourOfDay+":"+minute);
                            TextTime.setVisibility(View.GONE);
                            TextRegistrationFromCity.setVisibility(View.VISIBLE);
                        }


                        Log.d(TAG, "время: "+time);
                    }
                },hourOfDay,minute,true);
        timePickerDialog.show();
    }

    // Показать выбор номера рейса
    public void showNumberFlight(){
        AlertDialog.Builder builder = new AlertDialog.Builder( Main3Activity.this );
        builder.setTitle( "порядковый номер рейса самолета из Игарки (чартер)" );
       // builder.setCancelable( false );
        builder.setItems( listFlights, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Flight.setText( listFlights[which] );
                        TextNumberFlight.setVisibility(View.GONE);
                        TextRegistration.setVisibility(View.VISIBLE);
                    }
                }
        );
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    //Выбрать номер рейса
    public void btn_number_Flight (View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder( Main3Activity.this );
        builder.setTitle( "порядковый номер рейса самолета из Игарки (чартер)" );
        //builder.setCancelable( false );
        builder.setItems( listFlights, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                Flight.setText( listFlights[which] );
                TextNumberFlight.setVisibility(View.GONE);
                TextRegistration.setVisibility(View.VISIBLE);
            }
        }
        );
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void btnInsert(View view){
        Log.d(TAG, "registration2:"+reg);

        if (reg.equals("knull")){
            Log.d(TAG, "registration3:"+reg);

            AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Main3Activity.this);
            // Set Title
            //mAlertDialog.setTitle("Спасибо, заявка оформлена!!!");
            //mAlertDialog.setCancelable(false);
            // Set Message
            mAlertDialog.setMessage("Для продолжения необходимо" +
                    " зарегистрироваться по номеру телефона." +
                    " Вам придет SMS c кодом подтверждения")
                    .setPositiveButton("Принять", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            goListRegistration();

                        }
                    })
            .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            mAlertDialog.create();
            mAlertDialog.show();
        }
        if (!reg.equals("knull")){
            btnInsertd();
        }
    }

    public void goListRegistration(){
        Intent main2Activity =new Intent(this, Main2Activity.class);
        startActivity(main2Activity);
    }

    // кнопка регистрация
    public void btnInsertd () {
        Log.d(TAG, "Старт Проверка интернета YesNO");

        MistakeRegistration.setVisibility(View.GONE);
        TextProgress.setVisibility(View.VISIBLE);
        TextRegistration.setVisibility(View.GONE);
        TextRegistrationFromCity.setVisibility(View.GONE);

        tOBeforReg="";
        proverkaBeforRegistraion="";

        //ТАЙМ-АУТ проверка интернета
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {

                // Завершен ТАЙМ-АУТ проверка интернета
                tOBeforReg="Out";
                intNotBeforRegistraion();
            }
        },20000);

        //Важно в БД с читаемым объектом не должно быть параллельных линий :)
        // только тогда считывает значения с первого раза без null
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        Query query = rootRef.child("Пользователи")
                .child("Personal")
                .child(userPhone)
                .child("Proverka")
                .orderByChild("Oder");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ddd : dataSnapshot.getChildren()) {

                    String yesNo=ddd.child("Заявка").getValue(String.class);

                    proverkaBeforRegistraion=yesNo;
                    Log.d(TAG, "инетрнет есть, заявка есть?"+yesNo);

                    // проверка YEsNo
                    YesNoBeforeReg();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);

    }

    public void intNotBeforRegistraion(){
        if (!proverkaBeforRegistraion.isEmpty()){
            Log.d(TAG, "время проверки интернета вышло, но интернет есть");
        }
        else{
            Log.d(TAG, "Время проверки вышло, not internet");
            showAlertDialog4();
        }
    }

    public void YesNoBeforeReg(){
        if(tOBeforReg.equals("Out")){
            Log.d(TAG, "Время проверки интернета вышло, но интернет есть");
        }
        else if (proverkaBeforRegistraion.equals("No")){
            Log.d(TAG, "Интернет есть, старт регистрации");
            startRegistration();

        }
        else if (proverkaBeforRegistraion.equals("Yes")){
            Log.d(TAG, "Интернет есть, НО есть старая заявка");
            showAlertDialog5();
        }
    }

    // !!!!!Переделка на private Room 30.05.20
    public void startRegistration(){
        Log.d(TAG, "Старт Регистрации");

        timeOut="";
        proverka="";

        //ТАЙМ-АУТ ЗАПРОСА ИНТЕРНЕТА
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Завершен ТАЙМ-АУТ ЗАПРОСА ИНТЕРНЕТА-2 при проверке регистрации
                timeOut="Out";
                internetNot();
            }
        },15000);

        if(toOrFrom.equals("В Красноярск")){
            //030320 Запись токена для проверки Разрешения на запись заявки в БД
            database01 = FirebaseDatabase.getInstance();
            ref01 = database01.getReference("Пользователи")
                    .child("Personal")
                    .child(userPhone)
                    .child("Заявки")
                    .child(MapTop)
                    .child(Calend.getText().toString())
                    .child(Flight.getText().toString())
                    .child(TVchoiseMap)
                    .child(TVchoise_pointMap)
                    .child("CheckStopOder")
                    .child(userPhone);
            ref01.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                ref01.child(newToken).setValue(userPhone);
                                                //запускаем метод
                                                Qwery();
                                                // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД БД ЗАЯВКИ...-...-...-"CheckStopOder"...
                                                ref01.removeEventListener(this);
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                            }
                                        }
            );
        }

        if(toOrFrom.equals("Из Красноярска")){
            //030320 Запись токена для проверки Разрешения на запись заявки в БД
            database01 = FirebaseDatabase.getInstance();
            ref01 = database01.getReference("Пользователи")
                    .child("Personal")
                    .child(userPhone)
                    .child("Заявки")
                    .child(MapTop)
                    .child(CalendTime.getText().toString())
                    .child(time.getText().toString())
                    .child(TVchoiseMap)
                    .child(TVchoise_pointMap)
                    .child("CheckStopOder")
                    .child(userPhone);
            ref01.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                ref01.child(newToken).setValue(userPhone);
                                                //запускаем метод
                                                Qwery();
                                                // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД БД ЗАЯВКИ...-...-...-"CheckStopOder"...
                                                ref01.removeEventListener(this);
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                            }
                                        }
            );
        }


    }

    public void Qwery(){
        Log.d(TAG, "старт запроса Разрешения/Запрет");

        if(toOrFrom.equals("В Красноярск")){
            // проверяем какое слово написано в объекте РазрешениеНаЗапись.
            // Если Разрешено то запись заявки оформляется, если нет то заявка отклонена (процесс записи и отклонения выполнен в nod js function OderCheck)
            final Query aaa1= FirebaseDatabase.getInstance().getReference("Пользователи")
                    .child("Personal")
                    .child(userPhone)
                    .child("Заявки")
                    .child(MapTop)
                    .child(Calend.getText().toString())
                    .child(Flight.getText().toString())
                    .child(TVchoiseMap)
                    .child(TVchoise_pointMap)
                    .child("Разрешение")
                    .child(userPhone)
                    .orderByChild("Разрешение");
            aaa1.addChildEventListener( new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    String data=dataSnapshot.child( "РазрешениеНаЗапись" ).getValue(String.class);
                    Log.d(TAG, "РазрешениеНаЗапись"+data);/*специально пусто*/

                    proverka=data;

                    // Проверяем закончилось ли время опроса time-out
                    checkWordProverka();

                    //Останавливаем прослушивание, чтобы в приложении у другого пользователя не появлялась информация когда другой пользоваьель регистрирует заявку
                    aaa1.removeEventListener(this);
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
            } );

        }
        if(toOrFrom.equals("Из Красноярска")){
            // проверяем какое слово написано в объекте РазрешениеНаЗапись.
            // Если Разрешено то запись заявки оформляется, если нет то заявка отклонена (процесс записи и отклонения выполнен в nod js function OderCheck)
            final Query aaa1= FirebaseDatabase.getInstance().getReference("Пользователи")
                    .child("Personal")
                    .child(userPhone)
                    .child("Заявки")
                    .child(MapTop)
                    .child(CalendTime.getText().toString())
                    .child(time.getText().toString())
                    .child(TVchoiseMap)
                    .child(TVchoise_pointMap)
                    .child("Разрешение")
                    .child(userPhone)
                    .orderByChild("Разрешение");
            aaa1.addChildEventListener( new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    String data=dataSnapshot.child( "РазрешениеНаЗапись" ).getValue(String.class);
                    Log.d(TAG, "РазрешениеНаЗапись"+data);/*специально пусто*/

                    proverka=data;

                    // Проверяем закончилось ли время опроса time-out
                    checkWordProverka();

                    //Останавливаем прослушивание, чтобы в приложении у другого пользователя не появлялась информация когда другой пользоваьель регистрирует заявку
                    aaa1.removeEventListener(this);
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
            } );

        }


    }

    public void internetNot(){
        if (!proverka.isEmpty()){
            Log.d(TAG, "время вышло но Разрешение/Запрет получен из БД");
        }
        else{
            Log.d(TAG, "Время поиска статуса вышло not internet");
            Intent Main6ActivityNotInternet  = new Intent(this,Main6ActivityNotInternet.class);
            startActivity(Main6ActivityNotInternet);
        }
    }

    public void checkWordProverka(){
        if(timeOut.equals("Out")){
            Log.d(TAG, "Разрешение/Запретс получен но время вышло");
        }
        else if(proverka.equals("Разрешено")){
            Log.d(TAG, "Разрешено");
            TextProgress.setVisibility(View.GONE);
            showAlertDialog1();
        }
        else if (proverka.equals("Запрещено")){
            Log.d(TAG, "Запрещено");
            TextProgress.setVisibility(View.GONE);
            showAlertDialog();
        }
        else if (proverka.equals("Повтор")){
            Log.d(TAG, "Повтор");
            TextProgress.setVisibility(View.GONE);
            showAlertDialog3();
        }
    }

    // Всплывающая информация "Заявка отклонена!!!"
    public void showAlertDialog() {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Main3Activity.this);
        // Set Title
        mAlertDialog.setTitle("Заявка отклонена :(");
        mAlertDialog.setCancelable(false);
        // Set Message
        mAlertDialog
                .setMessage("Точка сбора"+" "+TVchoise_pointMap+" "+"уже сформирована. Проверьте другие, ближайшие к вам точки сбора данного маршрута")
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onBackList();
                    }
                });
        mAlertDialog.create();
        // Showing Alert Message
        mAlertDialog.show();
    }
    // Всплывающая информация "Заявка оформлена!!!"
    public void showAlertDialog1() {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Main3Activity.this);
        // Set Title
        mAlertDialog.setTitle("Спасибо, заявка оформлена!!!");
        mAlertDialog.setCancelable(false);
        // Set Message
        mAlertDialog
                .setMessage("Ищем автомобиль..."+" "+"Вы получите уведомление о результате поиска")
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //переход в окно статуса лист после получения 6
                        onStatusList();
                    }
                });
        mAlertDialog.create();
        // Showing Alert Message
        mAlertDialog.show();
    }
    // Всплывающая информация "Повтор!!!"
    public void showAlertDialog3() {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(
                Main3Activity.this);
        // Set Title
        mAlertDialog.setTitle("!!!");
        mAlertDialog.setCancelable(false);
        // Set Message
        mAlertDialog
                .setMessage("По этому направлению вы уже зарегистрированы ранее")
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //переход в окно статуса лист 6
                        onStatusList();
                    }
                });
        mAlertDialog.create();
        // Showing Alert Message
        mAlertDialog.show();
    }

    public void showAlertDialog4(){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(
                Main3Activity.this);
        // Set Title
        mAlertDialog.setTitle("Ошибка регистрации");
        mAlertDialog.setCancelable(false);
        // Set Message
        mAlertDialog
                .setMessage("проверьте настройки интернета")
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        MistakeRegistration.setVisibility(View.VISIBLE);
                        TextProgress.setVisibility(View.GONE);
                        TextRegistration.setVisibility(View.VISIBLE);
                    }
                });
        mAlertDialog.create();
        // Showing Alert Message
        mAlertDialog.show();
    }
    public void showAlertDialog5(){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(
                Main3Activity.this);
        // Set Title
        mAlertDialog.setTitle("Ошибка регистрации");
        mAlertDialog.setCancelable(false);
        // Set Message
        mAlertDialog
                .setMessage("сначала отмените старую заявку")
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //Переход на лист Статуса
                        onStatusList();

                    }
                });
        mAlertDialog.create();
        // Showing Alert Message
        mAlertDialog.show();
    }

    // Переход на лист Статуса
    public void onStatusList() {
        //Переход на лист Статуса
        Intent zxz = new Intent( this,Main6Activity.class );
        startActivity(zxz);
    }
    // Переход на лист выбора точки сбора
    public void onBackList() {
        //Переход на лист Статуса
        Intent Choose_direction = new Intent( this,Choose_direction.class );
        startActivity( Choose_direction);
    }

    // Блокировка кнопки Back!!!! :)))
//    @Override
//    public void onBackPressed(){
//    }
}