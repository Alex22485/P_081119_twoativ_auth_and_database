package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

    private static final String TAG ="ServApp_1";

    String dataREF;
    String MapREF;
    String TextTime;
    String TextRoad;

    TextView Data;
    TextView TimeFly;
    TextView Map;
    TextView Road;

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

    Button sendToDriver1;
    Button sendToDriver2;
    Button sendToDriver3;
    Button sendToDriver4;

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
    String[] listMap2 = {"Аэропорт-КрасТэц","Аэропорт-Щорса","Аэропорт-Северный","Аэропорт-Ветлужанка","Аэропорт-Сосновоборск","Аэропорт-Ачинск","Аэропорт-Канск","Аэропорт-Северо-Енисейск"};
    String[] pointOneMap = {"ДК КрасТЭЦ","Аэрокосмическая академия","Торговый центр","Предмостная пл"};
    String[] pointTwoMap = {"Кинотеатр Металлург","Автобусный пер","Пикра","Мебельная фабрика"};
    String[] pointTreeMap = {"1xxx","2xxx","3xxx","4xxx"};
    String[] pointFourMap = {"5xxx","6xxx","7xxx","8xxx"};

    // 20.03.2020 Для выбора водителя
    ArrayList<String> driver=new ArrayList<String>(  );
    String[] array={};
    String  key;

    String Dr1PhRef;
    String Dr2PhRef;
    String Dr3PhRef;
    String Dr4PhRef;


    // НЕ Используется 20.03.2020 для отправки заявки в БД Водителя
    //ServApp_2 servApp_2;

    FirebaseDatabase database01;
    FirebaseDatabase databaseGetPhDR1;
    FirebaseDatabase databaseGetPhDR2;
    FirebaseDatabase databaseGetPhDR3;
    FirebaseDatabase databaseGetPhDR4;

    DatabaseReference ref01;
    DatabaseReference ref02;
    DatabaseReference refGetPhDR1;
    DatabaseReference refGetPhDR2;
    DatabaseReference refGetPhDR3;
    DatabaseReference refGetPhDR4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_serv_app_1 );



        Data = findViewById( R.id. Data );
        TimeFly = findViewById( R.id. TimeFly );
        Map = findViewById( R.id. Map );
        Road = findViewById( R.id. Road );

        read = findViewById( R.id. read );

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

        sendToDriver1= findViewById( R.id. sendToDriver1 );
        sendToDriver2= findViewById( R.id. sendToDriver2 );
        sendToDriver3= findViewById( R.id. sendToDriver3 );
        sendToDriver4= findViewById( R.id. sendToDriver4 );

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

        Intent nex=getIntent();
        dataREF=nex.getStringExtra("dataREF");
        MapREF=nex.getStringExtra("MapREF");
        TextTime=nex.getStringExtra("TextTime");
        TextRoad=nex.getStringExtra("TextRoad");

        Data.setText(dataREF);
        Map.setText(MapREF);
        TimeFly.setText(TextTime);
        Road.setText(TextRoad);




        // запись парковка Р3 если TextRoad совпадает с любым словом из массива listMap2
        for (int i=0; i<listMap2.length;i++){
            if (TextRoad.equals(listMap2[i])){
                onePoint.setText("Парковка Р3");
                Log.d(TAG, "TextRoad: "+TextRoad+" равно "+listMap2[i]);
            }
            Log.d(TAG, "TextRoad: "+TextRoad+" не равно "+listMap2[i]);
        }



        if(TextRoad.equals("КрасТэц-Аэропорт")){
            onePoint.setText( pointOneMap[0] );
            twoPoint.setText( pointOneMap[1] );
            treePoint.setText( pointOneMap[2] );
            fourPoint.setText( pointOneMap[3] );
        }
        if(TextRoad.equals("Щорса-Аэропорт")){
            onePoint.setText( pointTwoMap[0] );
            twoPoint.setText( pointTwoMap[1] );
            treePoint.setText( pointTwoMap[2] );
            fourPoint.setText( pointTwoMap[3] );
        }
        if(TextRoad.equals("Северный-Аэропорт")){
            onePoint.setText( pointTreeMap[0] );
            twoPoint.setText( pointTreeMap[1] );
            treePoint.setText( pointTreeMap[2] );
            fourPoint.setText( pointTreeMap[3] );
        }
        if(TextRoad.equals("Ветлужанка-Аэропорт")){
            onePoint.setText( pointFourMap[0] );
            twoPoint.setText( pointFourMap[1] );
            treePoint.setText( pointFourMap[2] );
            fourPoint.setText( pointFourMap[3] );
        }


        //30 03 2020 Получить все ключи объекта по его значению "Водила" записать их в ArrayList и преобразовать в строковый массив array
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child( "Водители" ).child("DriverForOder");
        ref.orderByValue().equalTo( "Водила" ).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap: dataSnapshot.getChildren()){

                    //Преобразовываем ArrayList в обычный массив чтобы вставить его в AlertDialog
                    key = snap.getKey(); //получить все ключи значения
                    driver.add( key );
                    array = driver.toArray(new String[driver.size()]);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

        // Прослушивание текста для Видимости кнопок 1 точки
       oneMen.addTextChangedListener( loginTextWather1 );
       oneTimeStop.addTextChangedListener( loginTextWather1 );
//       driverNew1.addTextChangedListener( loginTextWather1 );
        Log.d(TAG, "oneMen: "+oneMen.getText().toString());
        Log.d(TAG, "oneTimeStop: "+oneTimeStop.getText().toString());

        // Прослушивание текста для Видимости кнопок 2 точки
       twoMen.addTextChangedListener( loginTextWather2 );
       twoTimeStop.addTextChangedListener( loginTextWather2 );
//       driverNew2.addTextChangedListener( loginTextWather2 );

        // Прослушивание текста для Видимости кнопок 3 точки
       treeMen.addTextChangedListener( loginTextWather3 );
       treeTimeStop.addTextChangedListener( loginTextWather3 );
//       driverNew3.addTextChangedListener( loginTextWather3 );
        // Прослушивание текста для Видимости кнопок 4 точки
        fourMen.addTextChangedListener( loginTextWather4 );
        fourTimeStop.addTextChangedListener( loginTextWather4 );
//        driverNew4.addTextChangedListener( loginTextWather4 );
    }

    // Невидимость кнопки Стоп Заказ, Отмена Стоп, Driver,Send Oder 1 точки
    TextWatcher loginTextWather1=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String oneMenInput =oneMen.getText().toString().trim();
            String oneTimeStopInput =oneTimeStop.getText().toString().trim();

            // кнопка Стоп Заказ активна если есть число человек заявке и нет записи "Остановлена"
            BtnOneStop.setEnabled(!oneMenInput.isEmpty()&& oneTimeStopInput.isEmpty());
            // кнопка Отмена Стоп Заказ и  кнопка Выбора водителя Driver  активна если есть запись "Остановлена"
            DelBtnOneStop.setEnabled(!oneTimeStopInput.isEmpty());
            BtnOneDriver.setEnabled(!oneTimeStopInput.isEmpty());

        }
        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    // Невидимость кнопки Стоп Заказ , Отмена Стоп, Driver,Send Oder 2 точки
    TextWatcher loginTextWather2=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String twoMenInput =twoMen.getText().toString().trim();
            String twoTimeStopInput =twoTimeStop.getText().toString().trim();
//            String  driverNew2Input = driverNew2.getText().toString().trim();
            // кнопка Стоп Заказ активна если есть число человек заявке и нет записи "Остановлна"
            BtnTwoStop.setEnabled(!twoMenInput.isEmpty()&& twoTimeStopInput.isEmpty());
            // кнопка Отмена Стоп Заказ и  кнопка Выбора водителя Driver  активна если есть запись "Остановлна"
            DelBtnTwoStop.setEnabled(!twoTimeStopInput.isEmpty());
            BtnTwoDriver.setEnabled(!twoTimeStopInput.isEmpty());
            // кнопка Send Oder  активна если есть запись водителя в строке
//            sendToDriver2.setEnabled(!driverNew2Input.isEmpty());
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    // Невидимость кнопки Стоп Заказ , Отмена Стоп, Driver,Send Oder 3 точки
    TextWatcher loginTextWather3=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String treeMenInput =treeMen.getText().toString().trim();
            String treeTimeStopInput =treeTimeStop.getText().toString().trim();
//            String  driverNew3Input = driverNew3.getText().toString().trim();
            // кнопка Стоп Заказ активна если есть число человек заявке и нет записи "Остановлна"
            BtnTreeStop.setEnabled(!treeMenInput.isEmpty()&& treeTimeStopInput.isEmpty());
            // кнопка Отмена Стоп Заказ и  кнопка Выбора водителя Driver  активна если есть запись "Остановлна"
            DelBtnTreeStop.setEnabled(!treeTimeStopInput.isEmpty());
            BtnTreeDriver.setEnabled(!treeTimeStopInput.isEmpty());
            // кнопка Send Oder  активна если есть запись водителя в строке
//            sendToDriver3.setEnabled(!driverNew3Input.isEmpty());
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    // Невидимость кнопки Стоп Заказ , Отмена Стоп, Driver,Send Oder 4 точки
    TextWatcher loginTextWather4=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String fourMenInput =fourMen.getText().toString().trim();
            String fourTimeStopInput =fourTimeStop.getText().toString().trim();
//            String  driverNew4Input = driverNew4.getText().toString().trim();
            // кнопка Стоп Заказ активна если есть число человек заявке и нет записи "Остановлна"
            BtnFourStop.setEnabled(!fourMenInput.isEmpty()&& fourTimeStopInput.isEmpty());
            // кнопка Отмена Стоп Заказ и  кнопка Выбора водителя Driver  активна если есть запись "Остановлна"
            DelBtnFourStop.setEnabled(!fourTimeStopInput.isEmpty());
            BtnFourDriver.setEnabled(!fourTimeStopInput.isEmpty());
            // кнопка Send Oder  активна если есть запись водителя в строке
//            sendToDriver4.setEnabled(!driverNew4Input.isEmpty());
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

// определениие точек сбора в зависимости от выбранного маршрута
    public void getPoint(){

        String map=Map.getText().toString();
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
                Road.setText( listFlights[which] );
                Road.setTextColor(getResources().getColor( R.color.colorNew));
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
                        Map.setText( listMap[which] );
                        Map.setTextColor(getResources().getColor( R.color.colorNew));
                    }
                }
        );
        AlertDialog dialog = builder.create();
        dialog.show();
    }
// выбор выпадающего меню в зависимости от того что указано в строке Направление "Красноярск-Аэропорт" или "Аэропорт-Красноярск"
    public void choiseM (View view){
        String one=Map.getText().toString();
        String ref="Красноярск-Аэропорт";
        if (one.equals(ref)){
                AlertDialog.Builder builder = new AlertDialog.Builder( ServApp_1.this );
                builder.setTitle( "Выберите Маршрут" );
                builder.setCancelable( false );
                builder.setItems( listMap1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                Map.setText( listMap1[which] );
                                Map.setTextColor(getResources().getColor( R.color.colorNew));
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
                            Map.setText( listMap2[which] );
                            Map.setTextColor(getResources().getColor( R.color.colorNew));
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

            Log.d(TAG, "Data: "+Data.getText().toString());
            Log.d(TAG, "Map: "+Map.getText().toString());
            Log.d(TAG, "TimeFly: "+TimeFly.getText().toString());
            Log.d(TAG, "Road: "+Road.getText().toString());
            Log.d(TAG, "onePoint: "+onePoint.getText().toString());


        // считывание количества человек из БД по первой точке сбора
            final Query aaa1= FirebaseDatabase.getInstance().getReference("ЗаявкиServerApp")
                    .child( Data.getText().toString() )
                    .child( Map.getText().toString() )
                    .child( TimeFly.getText().toString() )
                    .child( Road.getText().toString() )
                    .child( onePoint.getText().toString() )
                    .orderByChild( "Человек" );
            aaa1.addChildEventListener( new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    //в БД стоит число поэтому считываем число
                    int data=dataSnapshot.child( "Человек" ).getValue(Integer.class);
                    String StopOder1=dataSnapshot.child("Остановлена").getValue(String.class);
                    String oneTimeSend1=dataSnapshot.child("Отправлена").getValue(String.class);
                    String oneNameDriver1=dataSnapshot.child("Водитель").getValue(String.class);
                    String oneTimeAccepted1=dataSnapshot.child("Принята").getValue(String.class);

                    //Log.d("TAG", "первая точка добавлена" + data);

                    // чтобы отображалось число прибавляем к числу пустую строчку ""
                    oneMen.setText(data+"" );
                    oneTimeStop.setText(StopOder1);
                    oneTimeSend.setText(oneTimeSend1);
                    oneNameDriver.setText(oneNameDriver1);
                    oneTimeAccepted.setText(oneTimeAccepted1);
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
                    .child( Data.getText().toString() )
                    .child( Map.getText().toString() )
                    .child( TimeFly.getText().toString() )
                    .child( Road.getText().toString() )
                    .child( twoPoint.getText().toString() )
                    .orderByChild( "Человек" );
            aaa2.addChildEventListener( new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    //в БД стоит число поэтому считываем число
                    int data=dataSnapshot.child( "Человек" ).getValue(Integer.class);
                    String StopOder2=dataSnapshot.child("Остановлена").getValue(String.class);
                    String twoTimeSend2=dataSnapshot.child("Отправлена").getValue(String.class);
                    String oneNameDriver2=dataSnapshot.child("Водитель").getValue(String.class);
                    String twoTimeAccepted2=dataSnapshot.child("Принята").getValue(String.class);
                    Log.d("TAG", "вторая точка" + data);

                    // чтобы отображалось прибавляем к числу пустую строчку ""
                    twoMen.setText(data+"" );
                    twoTimeStop.setText(StopOder2);
                    twoTimeSend.setText(twoTimeSend2);
                    twoNameDriver.setText(oneNameDriver2);
                    twoTimeAccepted.setText(twoTimeAccepted2);
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
                    .child( Data.getText().toString() )
                    .child( Map.getText().toString() )
                    .child( TimeFly.getText().toString() )
                    .child( Road.getText().toString() )
                    .child( treePoint.getText().toString() )
                    .orderByChild( "Человек" );
            aaa3.addChildEventListener( new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    //в БД стоит число поэтому считываем число
                    int data=dataSnapshot.child( "Человек" ).getValue(Integer.class);
                    String StopOder3=dataSnapshot.child("Остановлена").getValue(String.class);
                    String treeTimeSend3=dataSnapshot.child("Отправлена").getValue(String.class);
                    String oneNameDriver3=dataSnapshot.child("Водитель").getValue(String.class);
                    String treeTimeAccepted3=dataSnapshot.child("Принята").getValue(String.class);
                    Log.d("TAG", "третья точка" + StopOder3);
                    // чтобы отображалось прибавляем к числу пустую строчку ""
                    treeMen.setText(data+"" );
                    treeTimeStop.setText(StopOder3);
                    treeTimeSend.setText(treeTimeSend3);
                    treeNameDriver.setText(oneNameDriver3);
                    treeTimeAccepted.setText(treeTimeAccepted3);
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
                    .child( Data.getText().toString() )
                    .child( Map.getText().toString() )
                    .child( TimeFly.getText().toString() )
                    .child( Road.getText().toString() )
                    .child( fourPoint.getText().toString() )
                    .orderByChild( "Человек" );
            aaa4.addChildEventListener( new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    //в БД стоит число поэтому считываем число
                    int data=dataSnapshot.child( "Человек" ).getValue(Integer.class);
                    String StopOder4=dataSnapshot.child("Остановлена").getValue(String.class);
                    String fourTimeSend4=dataSnapshot.child("Отправлена").getValue(String.class);
                    String oneNameDriver4=dataSnapshot.child("Водитель").getValue(String.class);
                    String fourTimeAccepted4=dataSnapshot.child("Принята").getValue(String.class);
                    // чтобы отображалось прибавляем к числу пустую строчку ""
                    fourMen.setText(data+"" );
                    fourTimeStop.setText(StopOder4);
                    fourTimeSend.setText(fourTimeSend4);
                    fourNameDriver.setText(oneNameDriver4);
                    fourTimeAccepted.setText(fourTimeAccepted4);
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
            oneNameDriver.setText("");
            oneTimedelete.setText("");


        }
    // 20.03.2020 Очищаем данные по второй точке перед считыванием
    public void BtnTwo () {
        twoMen.setText("");
        twoTimeStop.setText("");
        twoTimeSend.setText("");
        twoTimeAccepted.setText("");
        twoNameDriver.setText("");
        twoTimedelete.setText("");

    }
    // 20.03.2020 Очищаем данные по третьей точке перед считыванием
    public void BtnTree () {
        treeMen.setText("");
        treeTimeStop.setText("");
        treeTimeSend.setText("");
        treeTimeAccepted.setText("");
        treeNameDriver.setText("");
        treeTimedelete.setText("");

    }
    // 20.03.2020 Очищаем данные по четвертой точке перед считыванием
    public void BtnFour () {
        fourMen.setText("");
        fourTimeStop.setText("");
        fourTimeSend.setText("");
        fourTimeAccepted.setText("");
        fourNameDriver.setText("");
        fourTimedelete.setText("");

    }

// Остановить запись заказов 1 точки StopOder
    public void BtnOneStop(View view){

        BtnOneStop.setEnabled(false);
        // получение текущего времени
        getTimNow();

        //080420 Запись Запрета Заявки заявки в БД ЗАЯВКИ...-...-...-"StopOder:Stop"...
        database01 = FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Заявки")
                .child( Data.getText().toString() )
                .child( Map.getText().toString() )
                .child( TimeFly.getText().toString() )
                .child( Road.getText().toString() )
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
    // ОТМЕНА StopOder записи заказов 1 точки
    public void DelBtnOneStop(View view){

        DelBtnOneStop.setEnabled(false);

        //Удаление Запрета Заявки заявки в БД ЗАЯВКИ...-...-...-"StopOder:Stop"...
        database01 = FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Заявки")
                .child( Data.getText().toString() )
                .child( Map.getText().toString() )
                .child( TimeFly.getText().toString() )
                .child( Road.getText().toString() )
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


    // Остановить запись заказов 2 точки StopOder
    public void BtnTwoStop(View view){

        BtnTwoStop.setEnabled(false);
        // получение текущего времени
        getTimNow();

        //080420 Запись Запрета Заявки заявки в БД ЗАЯВКИ...-...-...-"StopOder:Stop"...
        database01 = FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Заявки")
                .child( Data.getText().toString() )
                .child( Map.getText().toString() )
                .child( TimeFly.getText().toString() )
                .child( Road.getText().toString() )
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

    // ОТМЕНА StopOder записи заказов 2 точки
    public void DelBtnTwoStop(View view){

        DelBtnTwoStop.setEnabled(false);

        //Удаление Запрета Заявки заявки в БД ЗАЯВКИ...-...-...-"StopOder:Stop"...
        database01 = FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Заявки")
                .child( Data.getText().toString() )
                .child( Map.getText().toString() )
                .child( TimeFly.getText().toString() )
                .child( Road.getText().toString() )
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

    // Остановить запись заказов 3 точки StopOder
    public void BtnTreeStop(View view){

        BtnTreeStop.setEnabled(false);

        // получение текущего времени
        getTimNow();

        //080420 Запись Запрета Заявки заявки в БД ЗАЯВКИ...-...-...-"StopOder:Stop"...
        database01 = FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Заявки")
                .child( Data.getText().toString() )
                .child( Map.getText().toString() )
                .child( TimeFly.getText().toString() )
                .child( Road.getText().toString() )
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

    // ОТМЕНА StopOder записи заказов 3 точки
    public void DelBtnTreeStop(View view){

        DelBtnTreeStop.setEnabled(false);
        //Удаление Запрета Заявки заявки в БД ЗАЯВКИ...-...-...-"StopOder:Stop"...
        database01 = FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Заявки")
                .child( Data.getText().toString() )
                .child( Map.getText().toString() )
                .child( TimeFly.getText().toString() )
                .child( Road.getText().toString() )
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

    // Остановить запись заказов 4 точки StopOder
    public void BtnFourStop(View view){

        BtnFourStop.setEnabled(false);

        // получение текущего времени
        getTimNow();

        //080420 Запись Запрета Заявки заявки в БД ЗАЯВКИ...-...-...-"StopOder:Stop"...
        database01 = FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Заявки")
                .child( Data.getText().toString() )
                .child( Map.getText().toString() )
                .child( TimeFly.getText().toString() )
                .child( Road.getText().toString() )
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

    // ОТМЕНА StopOder записи заказов 3 точки
    public void DelBtnFourStop(View view){

        DelBtnFourStop.setEnabled(false);

        //Удаление Запрета Заявки заявки в БД ЗАЯВКИ...-...-...-"StopOder:Stop"...
        database01 = FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Заявки")
                .child( Data.getText().toString() )
                .child( Map.getText().toString() )
                .child( TimeFly.getText().toString() )
                .child( Road.getText().toString() )
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
//                        driverNew1.setText( array[which] );
//                        driverNew1.setTextColor(getResources().getColor( R.color.colorBlue));
                        BtnOneDriver.setText(array[which]);
                        BtnOneDriver.setTextColor(getResources().getColor( R.color.colorBlue));


                        //Запускаем метод считывание телефона выбранного водителя
                        getWriteDriver1Point();


                    }
                }
        );
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //метод считывание телефона выбранного водителя 1 точки
    public void getWriteDriver1Point(){

        //чтение из БД с правилом для любых пользователей
        databaseGetPhDR1 = FirebaseDatabase.getInstance();
        refGetPhDR1 = databaseGetPhDR1.getReference("Водители")
                .child("DriverPhone")
                .child(BtnOneDriver.getText().toString());
        refGetPhDR1.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Чтение результата из БД
                Dr1PhRef=dataSnapshot.getValue(String.class);
                refGetPhDR1.removeEventListener(this);

                //Активность кнопки отправить заявку
                sendToDriver1.setEnabled(true);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }



    // Выбрать Водителя по 2 точке
    public void choise_Driver2 (View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder( ServApp_1.this );
        builder.setTitle( "Выберите Водителя" );
        // Отображает Водителей загруженных из БД
        builder.setItems( array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
//                        driverNew2.setText( array[which] );
//                        driverNew2.setTextColor(getResources().getColor( R.color.colorBlue));
                        BtnTwoDriver.setText(array[which]);
                        BtnTwoDriver.setTextColor(getResources().getColor( R.color.colorBlue));

                        //Запускаем метод считывание телефона выбранного водителя
                        getWriteDriver2Point();

                    }
                }
        );
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //метод считывание телефона выбранного водителя 2 точки
    public void getWriteDriver2Point(){

        //чтение из БД с правилом для любых пользователей
        databaseGetPhDR2 = FirebaseDatabase.getInstance();
        refGetPhDR2 = databaseGetPhDR2.getReference("Водители")
                .child("DriverPhone")
                .child(BtnTwoDriver.getText().toString());
        refGetPhDR2.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Чтение результата из БД
                Dr2PhRef=dataSnapshot.getValue(String.class);
                refGetPhDR2.removeEventListener(this);

                //Активность кнопки отправить заявку
                sendToDriver2.setEnabled(true);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    // Выбрать Водителя по 3 точке
    public void choise_Driver3 (View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder( ServApp_1.this );
        builder.setTitle( "Выберите Водителя" );
        // Отображает Водителей загруженных из БД
        builder.setItems( array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
//                        driverNew3.setText( array[which] );
//                        driverNew3.setTextColor(getResources().getColor( R.color.colorBlue));
                        BtnTreeDriver.setText(array[which]);
                        BtnTreeDriver.setTextColor(getResources().getColor( R.color.colorBlue));

                        //Запускаем метод считывание телефона выбранного водителя
                        getWriteDriver3Point();

                    }
                }
        );
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //метод считывание телефона выбранного водителя 3 точки
    public void getWriteDriver3Point(){

        //чтение из БД с правилом для любых пользователей
        databaseGetPhDR3 = FirebaseDatabase.getInstance();
        refGetPhDR3 = databaseGetPhDR3.getReference("Водители")
                .child("DriverPhone")
                .child(BtnTreeDriver.getText().toString());
        refGetPhDR3.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Чтение результата из БД
                Dr3PhRef=dataSnapshot.getValue(String.class);
                refGetPhDR3.removeEventListener(this);

                //Активность кнопки отправить заявку
                sendToDriver3.setEnabled(true);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    // Выбрать Водителя по 4 точке
    public void choise_Driver4 (View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder( ServApp_1.this );
        builder.setTitle( "Выберите Водителя" );
        // Отображает Водителей загруженных из БД
        builder.setItems( array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
//                        driverNew4.setText( array[which] );
//                        driverNew4.setTextColor(getResources().getColor( R.color.colorBlue));
                        BtnFourDriver.setText(array[which]);
                        BtnFourDriver.setTextColor(getResources().getColor( R.color.colorBlue));

                        //Запускаем метод считывание телефона выбранного водителя
                        getWriteDriver4Point();
                    }
                }
        );
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //метод считывание телефона выбранного водителя 4 точки
    public void getWriteDriver4Point(){

        //чтение из БД с правилом для любых пользователей
        databaseGetPhDR4 = FirebaseDatabase.getInstance();
        refGetPhDR4 = databaseGetPhDR4.getReference("Водители")
                .child("DriverPhone")
                .child(BtnFourDriver.getText().toString());
        refGetPhDR4.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Чтение результата из БД
                Dr4PhRef=dataSnapshot.getValue(String.class);
                refGetPhDR4.removeEventListener(this);

                //Активность кнопки отправить заявку
                sendToDriver4.setEnabled(true);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }






    // Отправить заявку водителю по первой точке
    public void sendToDriver1(View view){

        database01=FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Водители").child("Personal").child(Dr1PhRef).child("Заявка");
        ref01.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //getServApp_2();
                //ref01.child("Заявка").setValue(servApp_2);
                ref01.child("дата").setValue(Data.getText().toString());
                ref01.child("рейс").setValue(TimeFly.getText().toString());
                ref01.child("направление").setValue(Map.getText().toString());
                ref01.child("маршрут").setValue(Road.getText().toString());
                ref01.child("точкаСбора1").setValue(onePoint.getText().toString());
                ref01.child("точкаСбора1Чел").setValue(oneMen.getText().toString());
                ref01.removeEventListener( this );

                //Отправляем время "Заказ отправлен" в БД ServerApp по 1-ой точке
                sendTimeToServerApp1Point();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    //Отправляем время "Заказ отправлен" в БД ServerApp по 1-ой точке
    public void sendTimeToServerApp1Point(){
        // получение текущего времени
        getTimNow();

        //080420 Запись Отправлена заявка водителю заявки в БД ServApp...-...-...-"Отправлена"...
        ref02 = database01.getReference("ЗаявкиServerApp")
                .child(Data.getText().toString())
                .child(Map.getText().toString())
                .child(TimeFly.getText().toString())
                .child(Road.getText().toString())
                .child(onePoint.getText().toString())
                .child("Человек");
        ref02.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            ref02.child("Отправлена").setValue(dateTime);
                                            ref02.child("Водитель").setValue(BtnOneDriver.getText().toString());

                                            // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД БД ЗАЯВКИ...-...-...-"CheckStopOder"...
                                            ref02.removeEventListener(this);

                                            Toast.makeText(ServApp_1.this,"Заявка отправлена....",Toast.LENGTH_LONG).show();
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
        ref01 = database01.getReference("Водители").child("Personal").child(Dr2PhRef).child("Заявка");
        ref01.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ref01.child("дата").setValue(Data.getText().toString());
                ref01.child("рейс").setValue(TimeFly.getText().toString());
                ref01.child("направление").setValue(Map.getText().toString());
                ref01.child("маршрут").setValue(Road.getText().toString());
                ref01.child("точкаСбора2").setValue(twoPoint.getText().toString());
                ref01.child("точкаСбора2Чел").setValue(twoMen.getText().toString());

                // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД "вкладка "Пользователи"
                ref01.removeEventListener( this );

                //Отправляем время "Заказ отправлен" в БД ServerApp по 1-ой точке
                sendTimeToServerApp2Point();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    //Отправляем время "Заказ отправлен" в БД ServerApp по 2-ой точке
    public void sendTimeToServerApp2Point(){

        // получение текущего времени
        getTimNow();

        //080420 Запись Отправлена заявка водителю заявки в БД ServApp...-...-...-"Отправлена"...
        ref02 = database01.getReference("ЗаявкиServerApp")
                .child(Data.getText().toString())
                .child(Map.getText().toString())
                .child(TimeFly.getText().toString())
                .child(Road.getText().toString())
                .child(twoPoint.getText().toString())
                .child("Человек");
        ref02.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            ref02.child("Отправлена").setValue(dateTime);
                                            ref02.child("Водитель").setValue(BtnTwoDriver.getText().toString());

                                            // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД БД ЗАЯВКИ...-...-...-"CheckStopOder"...
                                            ref02.removeEventListener(this);

                                            Toast.makeText(ServApp_1.this,"Заявка отправлена....",Toast.LENGTH_LONG).show();
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
        ref01 = database01.getReference("Водители").child("Personal").child(Dr3PhRef).child("Заявка");
        ref01.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ref01.child("дата").setValue(Data.getText().toString());
                ref01.child("рейс").setValue(TimeFly.getText().toString());
                ref01.child("направление").setValue(Map.getText().toString());
                ref01.child("маршрут").setValue(Road.getText().toString());
                ref01.child("точкаСбора3").setValue(treePoint.getText().toString());
                ref01.child("точкаСбора3Чел").setValue(treeMen.getText().toString());

                // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД "вкладка "Пользователи"
                ref01.removeEventListener( this );

                //Отправляем время "Заказ отправлен" в БД ServerApp по 1-ой точке
                sendTimeToServerApp3Point();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    //Отправляем время "Заказ отправлен" в БД ServerApp по 3-ой точке
    public void sendTimeToServerApp3Point(){
        // получение текущего времени
        getTimNow();

        //080420 Запись Отправлена заявка водителю заявки в БД ServApp...-...-...-"Отправлена"...
        ref02 = database01.getReference("ЗаявкиServerApp")
                .child(Data.getText().toString())
                .child(Map.getText().toString())
                .child(TimeFly.getText().toString())
                .child(Road.getText().toString())
                .child(treePoint.getText().toString())
                .child("Человек");
        ref02.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            ref02.child("Отправлена").setValue(dateTime);
                                            ref02.child("Водитель").setValue(BtnTreeDriver.getText().toString());

                                            // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД БД ЗАЯВКИ...-...-...-"CheckStopOder"...
                                            ref02.removeEventListener(this);

                                            Toast.makeText(ServApp_1.this,"Заявка отправлена....",Toast.LENGTH_LONG).show();
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
        ref01 = database01.getReference("Водители").child("Personal").child(Dr4PhRef).child("Заявка");
        ref01.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ref01.child("дата").setValue(Data.getText().toString());
                ref01.child("рейс").setValue(TimeFly.getText().toString());
                ref01.child("направление").setValue(Map.getText().toString());
                ref01.child("маршрут").setValue(Road.getText().toString());
                ref01.child("точкаСбора4").setValue(fourPoint.getText().toString());
                ref01.child("точкаСбора4Чел").setValue(fourMen.getText().toString());

                // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД "вкладка "Пользователи"
                ref01.removeEventListener( this );

                //Отправляем время "Заказ отправлен" в БД ServerApp по 1-ой точке
                sendTimeToServerApp4Point();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }

    //Отправляем время "Заказ отправлен" в БД ServerApp по 4-ой точке
    public void sendTimeToServerApp4Point(){
        // получение текущего времени
        getTimNow();

        //080420 Запись Отправлена заявка водителю заявки в БД ServApp...-...-...-"Отправлена"...
        ref02 = database01.getReference("ЗаявкиServerApp")
                .child(Data.getText().toString())
                .child(Map.getText().toString())
                .child(TimeFly.getText().toString())
                .child(Road.getText().toString())
                .child(fourPoint.getText().toString())
                .child("Человек");
        ref02.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            ref02.child("Отправлена").setValue(dateTime);
                                            ref02.child("Водитель").setValue(BtnFourDriver.getText().toString());

                                            // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД БД ЗАЯВКИ...-...-...-"CheckStopOder"...
                                            ref02.removeEventListener(this);

                                            Toast.makeText(ServApp_1.this,"Заявка отправлена....",Toast.LENGTH_LONG).show();
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    }
        );
    }
    }


