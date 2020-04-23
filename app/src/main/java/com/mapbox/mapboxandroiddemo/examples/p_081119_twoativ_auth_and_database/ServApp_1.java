package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;



public class ServApp_1 extends AppCompatActivity {

    TextView Дата;
    TextView Рейс;
    TextView Направление;
    TextView Маршрут;

    TextView driverNew1;
    TextView driverNew2;
    TextView driverNew3;
    TextView driverNew4;

    TextView onePoint;
    TextView twoPoint;
    TextView treePoint;
    TextView fourPoint;

    TextView oneMen;
    TextView twoMen;
    TextView treeMen;
    TextView fourMen;

    TextView oneTimeStop;
    TextView twoTimeStop;
    TextView treeTimeStop;
    TextView fourTimeStop;

    TextView oneTimeSend;
    TextView twoTimeSend;
    TextView treeTimeSend;
    TextView fourTimeSend;

    TextView oneTimeAccepted;
    TextView twoTimeAccepted;
    TextView treeTimeAccepted;
    TextView fourTimeAccepted;

    TextView oneNameDriver;
    TextView twoNameDriver;
    TextView treeNameDriver;
    TextView fourNameDriver;

    TextView oneTimedelete;
    TextView twoTimedelete;
    TextView treeTimedelete;
    TextView fourTimedelete;

    Button choiseD;
    Button choiseF;
    Button choiseN;
    Button read;

    Button BtnOneStop;
    Button BtnTwoStop;
    Button BtnTreeStop;
    Button BtnFourStop;

    Button DelBtnOneStop;
    Button DelBtnTwoStop;
    Button DelBtnTreeStop;
    Button DelBtnFourStop;

    Button BtnOneDriver;
    Button BtnTwoDriver;
    Button BtnTreeDriver;
    Button BtnFourDriver;

    String dateTime;

    //Calendar
    Calendar calendar;
    int year;
    int month;
    int dayOfmonth;
    DatePickerDialog datePickerDialog;

    //Номер рейса
    String[] listFlights = {"1","2","3"};
    //Номер Направления
    String[] listMap = {"Красноярск-Аэропорт","Аэропорт-Красноярск"};
    String[] listMap1 = {"КрасТэц-Аэропорт","Щорса-Аэропорт","Северный-Аэропорт","Ветлужанка-Аэропорт"};
    String[] listMap2 = {"Аэропорт-КрасТэц","Аэропорт-Щорса","Аэропорт-Северный","Аэропорт-Ветлужанка"};
    String[] pointOneMap = {"ДК КрасТЭЦ","Аэрокосмическая академия","Торговый центр","Предмостная пл"};
    String[] pointTwoMap = {"Кинотеатр Металлург","Автобусный пер","Пикра","Мебельная фабрика"};

    // 20.03.2020 Для выбора водителя
    ArrayList<String> driver=new ArrayList<String>(  );
    String[] array={};
    String  key;


    //20.03.2020 для отправки заявки в БД Водителя
    ServApp_2 servApp_2;

    FirebaseDatabase database01;
    DatabaseReference ref01;
    DatabaseReference ref02;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_serv_app_1 );

        Дата = findViewById( R.id. Дата );
        Рейс = findViewById( R.id. Рейс );
        Направление = findViewById( R.id. Направление );
        Маршрут = findViewById( R.id. Маршрут );

        choiseD = findViewById( R.id. choiseD );
        choiseF = findViewById( R.id. choiseF );
        choiseN = findViewById( R.id. choiseN );
        read = findViewById( R.id. read );

        driverNew1 = findViewById( R.id. driverNew1 );
        driverNew2 = findViewById( R.id. driverNew2 );
        driverNew3 = findViewById( R.id. driverNew3 );
        driverNew4 = findViewById( R.id. driverNew4 );

        onePoint = findViewById( R.id. onePoint );
        twoPoint = findViewById( R.id. twoPoint );
        treePoint = findViewById( R.id. treePoint );
        fourPoint = findViewById( R.id. fourPoint );

        oneMen = findViewById( R.id. oneMen );
        twoMen = findViewById( R.id. twoMen );
        treeMen = findViewById( R.id. treeMen );
        fourMen = findViewById( R.id. fourMen );





        BtnOneStop = findViewById( R.id. BtnOneStop );
        BtnTwoStop = findViewById( R.id. BtnTwoStop );
        BtnTreeStop = findViewById( R.id. BtnTreeStop );
        BtnFourStop = findViewById( R.id. BtnFourStop );

        DelBtnOneStop= findViewById( R.id. DelBtnOneStop );
        DelBtnTwoStop= findViewById( R.id. DelBtnTwoStop );
        DelBtnTreeStop= findViewById( R.id. DelBtnTreeStop );
        DelBtnFourStop= findViewById( R.id. DelBtnFourStop );

        BtnOneDriver= findViewById( R.id. BtnOneDriver );
        BtnTwoDriver= findViewById( R.id. BtnTwoDriver );
        BtnTreeDriver= findViewById( R.id. BtnTreeDriver );
        BtnFourDriver= findViewById( R.id. BtnFourDriver );

        oneTimeStop = findViewById( R.id. oneTimeStop );
        twoTimeStop = findViewById( R.id. twoTimeStop );
        treeTimeStop = findViewById( R.id. treeTimeStop );
        fourTimeStop = findViewById( R.id. fourTimeStop );

        oneTimeSend = findViewById( R.id. oneTimeSend );
        twoTimeSend = findViewById( R.id. twoTimeSend );
        treeTimeSend = findViewById( R.id. treeTimeSend );
        fourTimeSend = findViewById( R.id. fourTimeSend );

        oneTimeAccepted=findViewById(R.id.oneTimeAccepted);
        twoTimeAccepted=findViewById(R.id.twoTimeAccepted);
        treeTimeAccepted=findViewById(R.id.treeTimeAccepted);
        fourTimeAccepted=findViewById(R.id.fourTimeAccepted);

        oneNameDriver=findViewById(R.id.oneNameDriver);
        twoNameDriver=findViewById(R.id.twoNameDriver);
        treeNameDriver=findViewById(R.id.treeNameDriver);
        fourNameDriver=findViewById(R.id.fourNameDriver);

        oneTimedelete=findViewById(R.id.oneTimedelete);
        twoTimedelete=findViewById(R.id.twoTimedelete);
        treeTimedelete=findViewById(R.id.treeTimedelete);
        fourTimedelete=findViewById(R.id.fourTimedelete);

        //20.03.2020 для отправки заявки в БД Водителя
        servApp_2= new ServApp_2();



        choiseD.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar=Calendar.getInstance();
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH);
                dayOfmonth=calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog=new DatePickerDialog(ServApp_1.this,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Дата.setText(day + " " + (month + 1) + " " + year);
                        Дата.setTextColor(getResources().getColor( R.color.colorNew));
                    }
                    }, year,month,dayOfmonth);
                datePickerDialog.show();
            }
        } );

        //30 03 2020 Получить все ключи объекта по его значению "Водила" записать их в ArrayList и преобразовать в строковый массив array
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child( "Proba" );
        ref.orderByValue().equalTo( "Водила" ).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap: dataSnapshot.getChildren()){

                    //Преобразовываем ArrayList в обычный массив чтобы вставить его в AlertDialog
                    key = snap.getKey(); //получить все ключи значения
                    driver.add( key );
                    array = driver.toArray(new String[driver.size()]);

                    //String value = snap.getValue(String.class); //получить все значения, так побаловаться
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

        // Невидимость кнопки Стоп Заказ и Отмена Стоп 1 точки
       oneMen.addTextChangedListener( loginTextWather1 );
       oneTimeStop.addTextChangedListener( loginTextWather1 );

       // Невидимость кнопки Стоп Заказ и Отмена Стоп 2 точки
       twoMen.addTextChangedListener( loginTextWather2 );
       twoTimeStop.addTextChangedListener( loginTextWather2 );

        // Невидимость кнопки Стоп Заказ и Отмена Стоп 3 точки
       treeMen.addTextChangedListener( loginTextWather3 );
       treeTimeStop.addTextChangedListener( loginTextWather3 );

        // Невидимость кнопки Стоп Заказ и Отмена Стоп 4 точки
        fourMen.addTextChangedListener( loginTextWather4 );
        fourTimeStop.addTextChangedListener( loginTextWather4 );

    }

    // Невидимость кнопки Стоп Заказ 1 точки
    TextWatcher loginTextWather1=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String oneMenInput =oneMen.getText().toString().trim();
            String oneTimeStopInput =oneTimeStop.getText().toString().trim();
            // кнопка Стоп Заказ активна если есть число человек заявке и нет записи "Остановлна"
            BtnOneStop.setEnabled(!oneMenInput.isEmpty()&& oneTimeStopInput.isEmpty());
            // кнопка Отмена Стоп Заказ и  кнопка Выбора водителя Driver  активна если есть запись "Остановлна"
            DelBtnOneStop.setEnabled(!oneTimeStopInput.isEmpty());
            BtnOneDriver.setEnabled(!oneTimeStopInput.isEmpty());
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    // Невидимость кнопки Стоп Заказ 2 точки
    TextWatcher loginTextWather2=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String twoMenInput =twoMen.getText().toString().trim();
            String twoTimeStopInput =twoTimeStop.getText().toString().trim();
            // кнопка Стоп Заказ активна если есть число человек заявке и нет записи "Остановлна"
            BtnTwoStop.setEnabled(!twoMenInput.isEmpty()&& twoTimeStopInput.isEmpty());
            // кнопка Отмена Стоп Заказ и  кнопка Выбора водителя Driver  активна если есть запись "Остановлна"
            DelBtnTwoStop.setEnabled(!twoTimeStopInput.isEmpty());
            BtnTwoDriver.setEnabled(!twoTimeStopInput.isEmpty());
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    // Невидимость кнопки Стоп Заказ 3 точки
    TextWatcher loginTextWather3=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String treeMenInput =treeMen.getText().toString().trim();
            String treeTimeStopInput =treeTimeStop.getText().toString().trim();
            // кнопка Стоп Заказ активна если есть число человек заявке и нет записи "Остановлна"
            BtnTreeStop.setEnabled(!treeMenInput.isEmpty()&& treeTimeStopInput.isEmpty());
            // кнопка Отмена Стоп Заказ и  кнопка Выбора водителя Driver  активна если есть запись "Остановлна"
            DelBtnTreeStop.setEnabled(!treeTimeStopInput.isEmpty());
            BtnTreeDriver.setEnabled(!treeTimeStopInput.isEmpty());
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    // Невидимость кнопки Стоп Заказ 3 точки
    TextWatcher loginTextWather4=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String fourMenInput =fourMen.getText().toString().trim();
            String fourTimeStopInput =fourTimeStop.getText().toString().trim();
            // кнопка Стоп Заказ активна если есть число человек заявке и нет записи "Остановлна"
            BtnFourStop.setEnabled(!fourMenInput.isEmpty()&& fourTimeStopInput.isEmpty());
            // кнопка Отмена Стоп Заказ и  кнопка Выбора водителя Driver  активна если есть запись "Остановлна"
            DelBtnFourStop.setEnabled(!fourTimeStopInput.isEmpty());
            BtnFourDriver.setEnabled(!fourTimeStopInput.isEmpty());
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

// определениие точек сбора в зависимости от выбранного маршрута
    public void getPoint(){

        String map=Маршрут.getText().toString();
        String map1=listMap1[0];
        String map2=listMap2[0];

        String map3=listMap1[1];
        String map4=listMap2[1];
// если маршрут Краснояск-Аэропорт или Аэропорт-Красноярск то устанавливаем точки сбора из массива pointOneMap
        if (map.equals(map1) || map.equals(map2)){
            onePoint.setText( pointOneMap[0] );
            twoPoint.setText( pointOneMap[1] );
            treePoint.setText( pointOneMap[2] );
            fourPoint.setText( pointOneMap[3] );
        }else if(map.equals(map3)||map.equals(map4)){
            onePoint.setText( pointTwoMap[0] );
            twoPoint.setText( pointTwoMap[1] );
            treePoint.setText( pointTwoMap[2] );
            fourPoint.setText( pointTwoMap[3] );
        }
    }

    //Выбрать номер рейса
    public void choiseF (View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder( ServApp_1.this );
        builder.setTitle( "Выберите Номер рейса" );
        builder.setCancelable( false );
        builder.setItems( listFlights, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                Рейс.setText( listFlights[which] );
                Рейс.setTextColor(getResources().getColor( R.color.colorNew));
            }
        }
        );
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //Выбрать направление
    public void choiseN (View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder( ServApp_1.this );
        builder.setTitle( "Выберите Направление" );
        builder.setCancelable( false );
        builder.setItems( listMap, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Направление.setText( listMap[which] );
                        Направление.setTextColor(getResources().getColor( R.color.colorNew));
                    }
                }
        );
        AlertDialog dialog = builder.create();
        dialog.show();
    }
// выбор выпадающего меню в зависимости от того что указано в строке Направление "Красноярск-Аэропорт" или "Аэропорт-Красноярск"
    public void choiseM (View view){
        String one=Направление.getText().toString();
        String ref="Красноярск-Аэропорт";
        if (one==ref){
                AlertDialog.Builder builder = new AlertDialog.Builder( ServApp_1.this );
                builder.setTitle( "Выберите Маршрут" );
                builder.setCancelable( false );
                builder.setItems( listMap1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                Маршрут.setText( listMap1[which] );
                                Маршрут.setTextColor(getResources().getColor( R.color.colorNew));
                                getPoint();
                            }
                        }
                );
                AlertDialog dialog = builder.create();
                dialog.show();
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder( ServApp_1.this );
            builder.setTitle( "Выберите Маршрут" );
            builder.setCancelable( false );
            builder.setItems( listMap2, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            Маршрут.setText( listMap2[which] );
                            Маршрут.setTextColor(getResources().getColor( R.color.colorNew));
                            onePoint.setText( "Парковка Р3" );

                            //запускаем выполнение метода
                            //getPoint();
                        }
                    }
            );
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        }

        public void reada (View view){

        //перед считыванием очищаем точки со старыми данными
            BtnOne();
            BtnTwo();
            BtnTree();
            BtnFour();


        // считывание количества человек из БД по первой точке сбора
            final Query aaa1= FirebaseDatabase.getInstance().getReference("ЗаявкиServerApp")
                    .child( Дата.getText().toString() )
                    .child( Направление.getText().toString() )
                    .child( Рейс.getText().toString() )
                    .child( Маршрут.getText().toString() )
                    .child( onePoint.getText().toString() )
                    .orderByChild( "Человек" );
            aaa1.addChildEventListener( new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    //в БД стоит число поэтому считываем число
                    int data=dataSnapshot.child( "Человек" ).getValue(Integer.class);
                    String StopOder1=dataSnapshot.child("Остановлена").getValue(String.class);
                    String oneTimeSend1=dataSnapshot.child("Отправлена").getValue(String.class);
                    Log.d("TAG", "первая точка добавлена" + data);
                    // чтобы отображалось прибавляем к числу пустую строчку ""
                    oneMen.setText(data+"" );
                    oneTimeStop.setText(StopOder1);
                    oneTimeSend.setText(oneTimeSend1);
                    Toast.makeText( ServApp_1.this, "точка 1 считана", Toast.LENGTH_SHORT ).show();

                    //Останавливаем прослушивание, чтобы обновилась информация (т.е. старая заявка не отображалась)
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
            // считывание количества человек из БД по второй точке сбора
            final Query aaa2= FirebaseDatabase.getInstance().getReference("ЗаявкиServerApp")
                    .child( Дата.getText().toString() )
                    .child( Направление.getText().toString() )
                    .child( Рейс.getText().toString() )
                    .child( Маршрут.getText().toString() )
                    .child( twoPoint.getText().toString() )
                    .orderByChild( "Человек" );
            aaa2.addChildEventListener( new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    //в БД стоит число поэтому считываем число
                    int data=dataSnapshot.child( "Человек" ).getValue(Integer.class);
                    String StopOder2=dataSnapshot.child("Остановлена").getValue(String.class);
                    String twoTimeSend2=dataSnapshot.child("Отправлена").getValue(String.class);
                    Log.d("TAG", "вторая точка" + data);
                    // чтобы отображалось прибавляем к числу пустую строчку ""
                    twoMen.setText(data+"" );
                    twoTimeStop.setText(StopOder2);
                    twoTimeSend.setText(twoTimeSend2);
                    Toast.makeText( ServApp_1.this, "точка 2 считана", Toast.LENGTH_SHORT ).show();

                    //Останавливаем прослушивание, чтобы обновилась информация (т.е. старая заявка не отображалась)
                    aaa2.removeEventListener(this);
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

            // считывание количества человек из БД по третьей точке сбора
            final Query aaa3= FirebaseDatabase.getInstance().getReference("ЗаявкиServerApp")
                    .child( Дата.getText().toString() )
                    .child( Направление.getText().toString() )
                    .child( Рейс.getText().toString() )
                    .child( Маршрут.getText().toString() )
                    .child( treePoint.getText().toString() )
                    .orderByChild( "Человек" );
            aaa3.addChildEventListener( new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    //в БД стоит число поэтому считываем число
                    int data=dataSnapshot.child( "Человек" ).getValue(Integer.class);
                    String StopOder3=dataSnapshot.child("Остановлена").getValue(String.class);
                    String treeTimeSend3=dataSnapshot.child("Отправлена").getValue(String.class);
                    Log.d("TAG", "третья точка" + StopOder3);
                    // чтобы отображалось прибавляем к числу пустую строчку ""
                    treeMen.setText(data+"" );
                    treeTimeStop.setText(StopOder3);
                    treeTimeSend.setText(treeTimeSend3);
                    Toast.makeText( ServApp_1.this, "точка 3 считана", Toast.LENGTH_SHORT ).show();

                    //Останавливаем прослушивание, чтобы обновилась информация (т.е. старая заявка не отображалась)
                    aaa3.removeEventListener(this);
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
            // считывание количества человек из БД по четвертой точке сбора
            final Query aaa4= FirebaseDatabase.getInstance().getReference("ЗаявкиServerApp")
                    .child( Дата.getText().toString() )
                    .child( Направление.getText().toString() )
                    .child( Рейс.getText().toString() )
                    .child( Маршрут.getText().toString() )
                    .child( fourPoint.getText().toString() )
                    .orderByChild( "Человек" );
            aaa4.addChildEventListener( new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    //в БД стоит число поэтому считываем число
                    int data=dataSnapshot.child( "Человек" ).getValue(Integer.class);
                    String StopOder4=dataSnapshot.child("Остановлена").getValue(String.class);
                    String fourTimeSend4=dataSnapshot.child("Отправлена").getValue(String.class);
                    // чтобы отображалось прибавляем к числу пустую строчку ""
                    fourMen.setText(data+"" );
                    fourTimeStop.setText(StopOder4);
                    fourTimeSend.setText(fourTimeSend4);
                    Toast.makeText( ServApp_1.this, "точка 4 считана", Toast.LENGTH_SHORT ).show();

                    //Останавливаем прослушивание, чтобы обновилась информация (т.е. старая заявка не отображалась)
                    aaa4.removeEventListener(this);
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
        // 20.03.2020 Очищаем данные по первой точке перед считыванием
        public void BtnOne () {
            oneMen.setText("");
            oneTimeStop.setText("");
            oneTimeSend.setText("");
            oneTimeAccepted.setText("");
            oneTimedelete.setText("");


        }
    // 20.03.2020 Очищаем данные по второй точке перед считыванием
    public void BtnTwo () {
        twoMen.setText("");
        twoTimeSend.setText("");
        twoTimeAccepted.setText("");
        twoNameDriver.setText("");
        twoTimedelete.setText("");

    }
    // 20.03.2020 Очищаем данные по третьей точке перед считыванием
    public void BtnTree () {
        treeMen.setText("");
        treeTimeSend.setText("");
        treeTimeAccepted.setText("");
        treeNameDriver.setText("");
        treeTimedelete.setText("");

    }
    // 20.03.2020 Очищаем данные по четвертой точке перед считыванием
    public void BtnFour () {
        fourMen.setText("");
        fourTimeSend.setText("");
        fourTimeAccepted.setText("");
        fourNameDriver.setText("");
        fourTimedelete.setText("");

    }


    public void BtnOneStop(View view){

        BtnOneStop.setEnabled(false);
        // получение текущего времени
        getTimNow();

        //080420 Запись Запрета Заявки заявки в БД ЗАЯВКИ...-...-...-"StopOder:Stop"...
        database01 = FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Заявки")
                .child(Направление.getText().toString())
                .child(Дата.getText().toString())
                .child(Рейс.getText().toString())
                .child(Маршрут.getText().toString())
                .child(onePoint.getText().toString());
        ref01.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            ref01.child("StopOder").setValue(dateTime);

                                            // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД БД ЗАЯВКИ...-...-...-"CheckStopOder"...
                                            ref01.removeEventListener(this);
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    }
        );

    }

    public void DelBtnOneStop(View view){

        DelBtnOneStop.setEnabled(false);

        //Удаление Запрета Заявки заявки в БД ЗАЯВКИ...-...-...-"StopOder:Stop"...
        database01 = FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Заявки")
                .child(Направление.getText().toString())
                .child(Дата.getText().toString())
                .child(Рейс.getText().toString())
                .child(Маршрут.getText().toString())
                .child(onePoint.getText().toString());
        ref01.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            ref01.child("StopOder").setValue(null);
                                            // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД БД ЗАЯВКИ...-...-...-"CheckStopOder"...
                                            ref01.removeEventListener(this);
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    }
        );
    }



    public void BtnTwoStop(View view){

        BtnTwoStop.setEnabled(false);
        // получение текущего времени
        getTimNow();

        //080420 Запись Запрета Заявки заявки в БД ЗАЯВКИ...-...-...-"StopOder:Stop"...
        database01 = FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Заявки")
                .child(Направление.getText().toString())
                .child(Дата.getText().toString())
                .child(Рейс.getText().toString())
                .child(Маршрут.getText().toString())
                .child(twoPoint.getText().toString());
        ref01.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            ref01.child("StopOder").setValue(dateTime);
                                            // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД БД ЗАЯВКИ...-...-...-"CheckStopOder"...
                                            ref01.removeEventListener(this);
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    }
        );

    }

    public void DelBtnTwoStop(View view){

        DelBtnTwoStop.setEnabled(false);

        //Удаление Запрета Заявки заявки в БД ЗАЯВКИ...-...-...-"StopOder:Stop"...
        database01 = FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Заявки")
                .child(Направление.getText().toString())
                .child(Дата.getText().toString())
                .child(Рейс.getText().toString())
                .child(Маршрут.getText().toString())
                .child(twoPoint.getText().toString());
        ref01.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            ref01.child("StopOder").setValue(null);
                                            // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД БД ЗАЯВКИ...-...-...-"CheckStopOder"...
                                            ref01.removeEventListener(this);
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    }
        );

    }

    public void BtnTreeStop(View view){

        BtnTreeStop.setEnabled(false);

        // получение текущего времени
        getTimNow();

        //080420 Запись Запрета Заявки заявки в БД ЗАЯВКИ...-...-...-"StopOder:Stop"...
        database01 = FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Заявки")
                .child(Направление.getText().toString())
                .child(Дата.getText().toString())
                .child(Рейс.getText().toString())
                .child(Маршрут.getText().toString())
                .child(treePoint.getText().toString());
        ref01.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            ref01.child("StopOder").setValue(dateTime);

                                            // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД БД ЗАЯВКИ...-...-...-"CheckStopOder"...
                                            ref01.removeEventListener(this);
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    }
        );

    }

    public void DelBtnTreeStop(View view){

        DelBtnTreeStop.setEnabled(false);
        //Удаление Запрета Заявки заявки в БД ЗАЯВКИ...-...-...-"StopOder:Stop"...
        database01 = FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Заявки")
                .child(Направление.getText().toString())
                .child(Дата.getText().toString())
                .child(Рейс.getText().toString())
                .child(Маршрут.getText().toString())
                .child(treePoint.getText().toString());
        ref01.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            ref01.child("StopOder").setValue(null);

                                            // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД БД ЗАЯВКИ...-...-...-"CheckStopOder"...
                                            ref01.removeEventListener(this);
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    }
        );

    }

    public void BtnFourStop(View view){

        BtnFourStop.setEnabled(false);

        // получение текущего времени
        getTimNow();

        //080420 Запись Запрета Заявки заявки в БД ЗАЯВКИ...-...-...-"StopOder:Stop"...
        database01 = FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Заявки")
                .child(Направление.getText().toString())
                .child(Дата.getText().toString())
                .child(Рейс.getText().toString())
                .child(Маршрут.getText().toString())
                .child(fourPoint.getText().toString());
        ref01.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            ref01.child("StopOder").setValue(dateTime);

                                            // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД БД ЗАЯВКИ...-...-...-"CheckStopOder"...
                                            ref01.removeEventListener(this);
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    }
                                    );

    }

    public void DelBtnFourStop(View view){

        DelBtnFourStop.setEnabled(false);

        //Удаление Запрета Заявки заявки в БД ЗАЯВКИ...-...-...-"StopOder:Stop"...
        database01 = FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Заявки")
                .child(Направление.getText().toString())
                .child(Дата.getText().toString())
                .child(Рейс.getText().toString())
                .child(Маршрут.getText().toString())
                .child(fourPoint.getText().toString());
        ref01.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            ref01.child("StopOder").setValue(null);

                                            // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД БД ЗАЯВКИ...-...-...-"CheckStopOder"...
                                            ref01.removeEventListener(this);
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    }
        );

    }


    public void  getTimNow(){
        //ПОЛУЧЕНИЕ ТЕКУЩЕГО ВРЕМЕНИ
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a dd-MM ");
        dateTime= simpleDateFormat.format(calendar.getTime());

    }

    // Выбрать Водителя по 1 точке
    public void choise_Driver1 (View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder( ServApp_1.this );
        builder.setTitle( "Выберите Водителя" );
        // Отображает Водителей загруженных из БД
        builder.setItems( array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        driverNew1.setText( array[which] );
                        driverNew1.setTextColor(getResources().getColor( R.color.colorBlue));
                    }
                }
        );
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    // Выбрать Водителя по 2 точке
    public void choise_Driver2 (View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder( ServApp_1.this );
        builder.setTitle( "Выберите Водителя" );
        // Отображает Водителей загруженных из БД
        builder.setItems( array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        driverNew2.setText( array[which] );
                        driverNew2.setTextColor(getResources().getColor( R.color.colorBlue));
                    }
                }
        );
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    // Выбрать Водителя по 2 точке
    public void choise_Driver3 (View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder( ServApp_1.this );
        builder.setTitle( "Выберите Водителя" );
        // Отображает Водителей загруженных из БД
        builder.setItems( array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        driverNew3.setText( array[which] );
                        driverNew3.setTextColor(getResources().getColor( R.color.colorBlue));
                    }
                }
        );
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    // Выбрать Водителя по 2 точке
    public void choise_Driver4 (View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder( ServApp_1.this );
        builder.setTitle( "Выберите Водителя" );
        // Отображает Водителей загруженных из БД
        builder.setItems( array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        driverNew4.setText( array[which] );
                        driverNew4.setTextColor(getResources().getColor( R.color.colorBlue));
                    }
                }
        );
        AlertDialog dialog = builder.create();
        dialog.show();
    }







    // Отправить заявку водителю по первой точке
    public void sendToDriver1(View view){

        database01=FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Drivers").child(driverNew1.getText().toString()).child("Заявка");
        ref01.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //getServApp_2();
                //ref01.child("Заявка").setValue(servApp_2);
                ref01.child("дата").setValue(Дата.getText().toString());
                ref01.child("рейс").setValue(Рейс.getText().toString());
                ref01.child("направление").setValue(Направление.getText().toString());
                ref01.child("маршрут").setValue(Маршрут.getText().toString());
                ref01.child("точкаСбора1").setValue(onePoint.getText().toString());
                ref01.child("точкаСбора1Чел").setValue(oneMen.getText().toString());
                Toast.makeText(ServApp_1.this,"Заявка отправлена....",Toast.LENGTH_LONG).show();
                // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД "вкладка "Пользователи"
                ref01.removeEventListener( this );
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        // получение текущего времени
        getTimNow();

        //080420 Запись Отправлена заявка водителю заявки в БД ServApp...-...-...-"Отправлена"...
        ref02 = database01.getReference("ЗаявкиServerApp")
                .child(Дата.getText().toString())
                .child(Направление.getText().toString())
                .child(Рейс.getText().toString())
                .child(Маршрут.getText().toString())
                .child(onePoint.getText().toString())
                .child("Человек");
        ref02.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            ref02.child("Отправлена").setValue(dateTime);

                                            // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД БД ЗАЯВКИ...-...-...-"CheckStopOder"...
                                            ref02.removeEventListener(this);
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    }
        );
    }

    // Отправить заявку водителю по второй точке
    public void sendToDriver2(View view){

        database01=FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Drivers").child(driverNew2.getText().toString()).child("Заявка");
        ref01.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //getServApp_2();
                //ref01.child("Заявка").setValue(servApp_2);
                ref01.child("дата").setValue(Дата.getText().toString());
                ref01.child("рейс").setValue(Рейс.getText().toString());
                ref01.child("направление").setValue(Направление.getText().toString());
                ref01.child("маршрут").setValue(Маршрут.getText().toString());
                ref01.child("точкаСбора2").setValue(twoPoint.getText().toString());
                ref01.child("точкаСбора2Чел").setValue(twoMen.getText().toString());
                Toast.makeText(ServApp_1.this,"Заявка отправлена....",Toast.LENGTH_LONG).show();
                // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД "вкладка "Пользователи"
                ref01.removeEventListener( this );
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        // получение текущего времени
        getTimNow();

        //080420 Запись Отправлена заявка водителю заявки в БД ServApp...-...-...-"Отправлена"...
        ref02 = database01.getReference("ЗаявкиServerApp")
                .child(Дата.getText().toString())
                .child(Направление.getText().toString())
                .child(Рейс.getText().toString())
                .child(Маршрут.getText().toString())
                .child(twoPoint.getText().toString())
                .child("Человек");
        ref02.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            ref02.child("Отправлена").setValue(dateTime);

                                            // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД БД ЗАЯВКИ...-...-...-"CheckStopOder"...
                                            ref02.removeEventListener(this);
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    }
        );

    }

    // Отправить заявку водителю по третьей точке
    public void sendToDriver3(View view){

        database01=FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Drivers").child(driverNew3.getText().toString()).child("Заявка");
        ref01.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ref01.child("дата").setValue(Дата.getText().toString());
                ref01.child("рейс").setValue(Рейс.getText().toString());
                ref01.child("направление").setValue(Направление.getText().toString());
                ref01.child("маршрут").setValue(Маршрут.getText().toString());
                ref01.child("точкаСбора3").setValue(treePoint.getText().toString());
                ref01.child("точкаСбора3Чел").setValue(treeMen.getText().toString());
                Toast.makeText(ServApp_1.this,"Заявка отправлена....",Toast.LENGTH_LONG).show();
                // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД "вкладка "Пользователи"
                ref01.removeEventListener( this );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        // получение текущего времени
        getTimNow();

        //080420 Запись Отправлена заявка водителю заявки в БД ServApp...-...-...-"Отправлена"...
        ref02 = database01.getReference("ЗаявкиServerApp")
                .child(Дата.getText().toString())
                .child(Направление.getText().toString())
                .child(Рейс.getText().toString())
                .child(Маршрут.getText().toString())
                .child(treePoint.getText().toString())
                .child("Человек");
        ref02.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            ref02.child("Отправлена").setValue(dateTime);

                                            // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД БД ЗАЯВКИ...-...-...-"CheckStopOder"...
                                            ref02.removeEventListener(this);
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    }
        );
    }

    // Отправить заявку водителю по четвертой точке
    public void sendToDriver4(View view){

        database01=FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Drivers").child(driverNew4.getText().toString()).child("Заявка");
        ref01.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ref01.child("дата").setValue(Дата.getText().toString());
                ref01.child("рейс").setValue(Рейс.getText().toString());
                ref01.child("направление").setValue(Направление.getText().toString());
                ref01.child("маршрут").setValue(Маршрут.getText().toString());
                ref01.child("точкаСбора4").setValue(fourPoint.getText().toString());
                ref01.child("точкаСбора4Чел").setValue(fourMen.getText().toString());
                Toast.makeText(ServApp_1.this,"Заявка отправлена....",Toast.LENGTH_LONG).show();
                // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД "вкладка "Пользователи"
                ref01.removeEventListener( this );
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        // получение текущего времени
        getTimNow();

        //080420 Запись Отправлена заявка водителю заявки в БД ServApp...-...-...-"Отправлена"...
        ref02 = database01.getReference("ЗаявкиServerApp")
                .child(Дата.getText().toString())
                .child(Направление.getText().toString())
                .child(Рейс.getText().toString())
                .child(Маршрут.getText().toString())
                .child(fourPoint.getText().toString())
                .child("Человек");
        ref02.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            ref02.child("Отправлена").setValue(dateTime);

                                            // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД БД ЗАЯВКИ...-...-...-"CheckStopOder"...
                                            ref02.removeEventListener(this);
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    }
        );
    }

    private void  getServApp_2(){

        servApp_2.setДата(Дата.getText().toString());
        servApp_2.setРейс(Рейс.getText().toString());
        servApp_2.setНаправление(Направление.getText().toString());
        servApp_2.setМаршрут(Маршрут.getText().toString());
        servApp_2.setТочкаСбора1(onePoint.getText().toString());
        servApp_2.setТочкаСбора1Чел(oneMen.getText().toString());
        servApp_2.setТочкаСбора2(twoPoint.getText().toString());
        servApp_2.setТочкаСбора2Чел(twoMen.getText().toString());
        servApp_2.setТочкаСбора3(treePoint.getText().toString());
        servApp_2.setТочкаСбора3Чел(treeMen.getText().toString());
        servApp_2.setТочкаСбора4(fourPoint.getText().toString());
        servApp_2.setТочкаСбора4Чел(fourMen.getText().toString());

    }


}


