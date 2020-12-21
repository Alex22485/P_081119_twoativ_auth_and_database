package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

    Integer data=0;

    // Для ЭКСПОРТА
    String dateOfPointCalend; //Дата сбора*дата вылета(прилета)
    String RefplaneCity; //Самолет
    String timeofPoint; //время сбора*время вылета(прилета)
    String RefStringMap; //Маршрут*стоимость
    String RefMap; //Маршрут
    String fare;  // Стоимость

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

//Определение точек сбора
    // для маршрутов в Аэропорт
    String[] listMap1 = {"Щорса-Аэропорт","КрасТэц-Аэропорт","Северный-Аэропорт","ЖД вокзал-Аэропорт","Ветлужанка-Аэропорт","Сосновоборск->Аэропорт","Ачинск->Аэропорт","Канск->Аэропорт","Лесосибирск->Аэропорт"};
    // пункты сбора Красноярск
    String[] KrasnojarskOneMap={"Кинотеатр Металлург","Автобусный пер","Пикра","Мебельная фабрика"};
    String[] KrasnojarskTwoMap = {"ДК КрасТЭЦ","Аэрокосмическая академия","Торговый центр","Предмостная пл"};
    String[] KrasnojarskTreeMap = {"XXX","xxx","xxx","xxx"};
    String[] KrasnojarskFourMap = {"YYY","yyy","yyy","yyy"};
    String[] KrasnojarskFiveMap = {"uuu","uuu","Тuuu","uuu"};
    // пункты сбора Другие города
    String[] pointSosnovoborsk={"Автовокзал Сосновоборск"}; // Сосновоборск
    String[] pointAchinsk={"Автовокзал Ачинск"}; // Ачинск
    String[] pointKansk={"Автовокзал Канск"}; // Канск
    String[] pointLesosobirsk={"Автовокзал Лесосибирск"};// Лесосибирск

    // для маршрутов из Аэропорта
    String[] listMap2 = {"Аэропорт-Щорса","Аэропорт-КрасТэц","Аэропорт-Северный","Аэропорт-ЖД вокзал","Аэропорт-Ветлужанка","Аэропорт->Сосновоборск","Аэропорт->Ачинск","Аэропорт->Канск","Аэропорт->Лесосибирск"};
    // точка сбора в Аэропорт
    String pointInAirport="Парковка Р2";

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



        Data=findViewById(R.id.Data);
        TimeFly=findViewById(R.id.TimeFly);
        Map=findViewById(R.id.Map);
        Road=findViewById(R.id.Road);

        read=findViewById(R.id.read);

        onePoint=findViewById(R.id.onePoint);
        twoPoint=findViewById(R.id.twoPoint);
        treePoint=findViewById(R.id.treePoint);
        fourPoint=findViewById(R.id.fourPoint);

        oneMen=findViewById(R.id.oneMen);
        twoMen=findViewById(R.id.twoMen);
        treeMen=findViewById(R.id.treeMen);
        fourMen=findViewById(R.id.fourMen);

        BtnOneStop=findViewById(R.id.BtnOneStop);
        BtnTwoStop=findViewById(R.id.BtnTwoStop);
        BtnTreeStop=findViewById(R.id.BtnTreeStop);
        BtnFourStop=findViewById(R.id.BtnFourStop);

        DelBtnOneStop=findViewById(R.id.DelBtnOneStop);
        DelBtnTwoStop=findViewById(R.id.DelBtnTwoStop);
        DelBtnTreeStop=findViewById(R.id.DelBtnTreeStop);
        DelBtnFourStop=findViewById(R.id.DelBtnFourStop);

        BtnOneDriver= findViewById(R.id.BtnOneDriver);
        BtnTwoDriver= findViewById(R.id.BtnTwoDriver);
        BtnTreeDriver= findViewById(R.id.BtnTreeDriver);
        BtnFourDriver= findViewById(R.id.BtnFourDriver);

        sendToDriver1=findViewById(R.id.sendToDriver1);
        sendToDriver2=findViewById(R.id.sendToDriver2);
        sendToDriver3=findViewById(R.id.sendToDriver3);
        sendToDriver4=findViewById(R.id.sendToDriver4);

        oneTimeStop=findViewById(R.id.oneTimeStop);
        twoTimeStop=findViewById(R.id.twoTimeStop);
        treeTimeStop=findViewById(R.id.treeTimeStop);
        fourTimeStop=findViewById(R.id.fourTimeStop);

        oneTimeSend=findViewById(R.id.oneTimeSend);
        twoTimeSend=findViewById(R.id.twoTimeSend);
        treeTimeSend=findViewById(R.id.treeTimeSend);
        fourTimeSend=findViewById(R.id.fourTimeSend);


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
        dateOfPointCalend=nex.getStringExtra("dateOfPointCalend"); //Дата сбора*дата вылета(прилета)
        RefplaneCity=nex.getStringExtra("RefplaneCity"); //Самолет
        timeofPoint=nex.getStringExtra("timeofPoint"); //время сбора*время вылета(прилета)
        RefStringMap=nex.getStringExtra("RefStringMap"); //Маршрут*стоимость
        RefMap=nex.getStringExtra("RefMap"); //Маршрут
        fare=nex.getStringExtra("fare"); //Стоимость

        Log.d(TAG, " Дата сбора*дата вылета(прилета) "+dateOfPointCalend);
        Log.d(TAG, " Самолет "+RefplaneCity);
        Log.d(TAG, " время сбора*время вылета(прилета) "+timeofPoint);
        Log.d(TAG, " Маршрут*стоимость "+RefStringMap);
        Log.d(TAG, " Маршрут "+RefMap);
        Log.d(TAG, " стоимость "+fare);

        Data.setText(dateOfPointCalend);
        Map.setText(RefplaneCity);
        TimeFly.setText(timeofPoint);
        Road.setText(RefStringMap);

//         //запись парковка Р3 если TextRoad совпадает с любым словом из массива listMap2
//        for (int i=0; i<listMap2.length;i++){
//            if (TextRoad.equals(listMap2[i])){
//                onePoint.setText("Парковка Р3");
//                Log.d(TAG, "TextRoad: "+TextRoad+" равно "+listMap2[i]);
//            }
//            Log.d(TAG, "TextRoad: "+TextRoad+" не равно "+listMap2[i]);
//        }

        // Интересный цикл "for each" перебирает весь массив listMap2 и каждую позицию сравнивает с TextRoad.
        // присваиваем строке i поочередно каждую позицию из массива listMap2
        // более современный цикл "for each" чем просто цикл "for" указанный в коментах выше. хотя оба работают одинаково

//Точка сбора для рейсов из АЭРОПОРТА (Парковка Р2)
        for (String i:listMap2){
            if (RefMap.equals(i)){
                onePoint.setText(pointInAirport);
            }
        }
// Точки сбора для рейсов в АЭРОПОРТ
        for (int x=0; x<listMap1.length;x++){
            if (RefMap.equals(listMap1[x])){
                // для Щорса-Аэропорт
                if (x==0){
                    onePoint.setText(KrasnojarskOneMap[0]);
                    twoPoint.setText(KrasnojarskOneMap[1]);
                    treePoint.setText(KrasnojarskOneMap[2]);
                    fourPoint.setText(KrasnojarskOneMap[3]);
                }
                // для КрасТэц-Аэропорт
                if (x==1){
                    onePoint.setText(KrasnojarskTwoMap[0]);
                    twoPoint.setText(KrasnojarskTwoMap[1]);
                    treePoint.setText(KrasnojarskTwoMap[2]);
                    fourPoint.setText(KrasnojarskTwoMap[3]);
                }
                // для Северный-Аэропорт
                if (x==2){
                    onePoint.setText(KrasnojarskTreeMap[0]);
                    twoPoint.setText(KrasnojarskTreeMap[1]);
                    treePoint.setText(KrasnojarskTreeMap[2]);
                    fourPoint.setText(KrasnojarskTreeMap[3]);
                }
                // для ЖД вокзал-Аэропорт
                if (x==3){
                    onePoint.setText(KrasnojarskFourMap[0]);
                    twoPoint.setText(KrasnojarskFourMap[1]);
                    treePoint.setText(KrasnojarskFourMap[2]);
                    fourPoint.setText(KrasnojarskFourMap[3]);
                }
                // для Ветлужанка-Аэропорт
                if (x==4){
                    onePoint.setText(KrasnojarskFiveMap[0]);
                    twoPoint.setText(KrasnojarskFiveMap[1]);
                    treePoint.setText(KrasnojarskFiveMap[2]);
                    fourPoint.setText(KrasnojarskFiveMap[3]);
                }
                // для Лесосибирска
                if (x==5){
                    onePoint.setText(pointSosnovoborsk[0]);
                }
                // для Ачинска
                if (x==6){
                    onePoint.setText(pointAchinsk[0]);
                }
                // для Канск
                if (x==7){
                    onePoint.setText(pointKansk[0]);
                }
                // для Лесосибирска
                if (x==8){
                    onePoint.setText(pointLesosobirsk[0]);
                }
            }
        }

        //30 03 2020 Получить все ключи объекта по его значению "Водила" записать их в ArrayList и преобразовать в строковый массив array
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child( "Водители" )
                .child("DriverForOder");
        ref.orderByValue()
                .equalTo( "Водила" ).addListenerForSingleValueEvent( new ValueEventListener() {
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

// ПРОСЛУШИВАНИЕ ТЕКСТА
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

//НЕВИДИМОСТЬ КНОПОК
    // 1 ТОЧКА Невидимость кнопки Стоп Заказ, Отмена Стоп, Driver,Send Oder
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

    // 2 ТОЧКА Невидимость кнопки Стоп Заказ , Отмена Стоп, Driver,Send Oder
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

    // 3 ТОЧКА Невидимость кнопки Стоп Заказ , Отмена Стоп, Driver,Send Oder
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

    // 4 ТОЧКА Невидимость кнопки Стоп Заказ , Отмена Стоп, Driver,Send Oder 4 точки
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

// КНОПКИ

// чтение данных заявки
    public void reada (View view){
        //очищаем точки со старыми данными
        BtnOne();
        BtnTwo();
        BtnTree();
        BtnFour();

        // считывание количества человек из БД по первой точке сбора
            final Query aaa1= FirebaseDatabase.getInstance().getReference("Заявки")
                    .child(dateOfPointCalend)
                    .child(RefplaneCity)
                    .child(timeofPoint)
                    .child(RefStringMap)
                    .child( onePoint.getText().toString() )
                    .child("Человек")
                    .orderByChild( "Человек" );
            aaa1.addChildEventListener( new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    //в БД стоит число поэтому ставим ""
                    String fff=""+dataSnapshot.child( "Человек" ).getValue(Integer.class);
                    String StopOder1=dataSnapshot.child("Остановлена").getValue(String.class);
                    String oneTimeSend1=dataSnapshot.child("Отправлена").getValue(String.class);
                    String oneNameDriver1=dataSnapshot.child("Водитель").getValue(String.class);
                    String oneTimeAccepted1=dataSnapshot.child("Принята").getValue(String.class);

                    oneMen.setText(fff);
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
            final Query aaa2= FirebaseDatabase.getInstance().getReference("Заявки")
                    .child(dateOfPointCalend)
                    .child(RefplaneCity)
                    .child(timeofPoint)
                    .child(RefStringMap)
                    .child( twoPoint.getText().toString() )
                    .child("Человек")
                    .orderByChild( "Человек" );
            aaa2.addChildEventListener( new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    //в БД стоит число поэтому ставим ""
                    String fff=""+dataSnapshot.child( "Человек" ).getValue(Integer.class);
                    String StopOder2=dataSnapshot.child("Остановлена").getValue(String.class);
                    String twoTimeSend2=dataSnapshot.child("Отправлена").getValue(String.class);
                    String oneNameDriver2=dataSnapshot.child("Водитель").getValue(String.class);
                    String twoTimeAccepted2=dataSnapshot.child("Принята").getValue(String.class);
                    Log.d("TAG", "вторая точка" + data);

                    // чтобы отображалось прибавляем к числу пустую строчку ""
                    twoMen.setText(fff);
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
            final Query aaa3= FirebaseDatabase.getInstance().getReference("Заявки")
                    .child(dateOfPointCalend)
                    .child(RefplaneCity)
                    .child(timeofPoint)
                    .child(RefStringMap)
                    .child( treePoint.getText().toString() )
                    .child("Человек")
                    .orderByChild( "Человек" );
            aaa3.addChildEventListener( new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    //в БД стоит число поэтому ставим ""
                    String fff=""+dataSnapshot.child( "Человек" ).getValue(Integer.class);
                    String StopOder3=dataSnapshot.child("Остановлена").getValue(String.class);
                    String treeTimeSend3=dataSnapshot.child("Отправлена").getValue(String.class);
                    String oneNameDriver3=dataSnapshot.child("Водитель").getValue(String.class);
                    String treeTimeAccepted3=dataSnapshot.child("Принята").getValue(String.class);
                    Log.d("TAG", "третья точка" + StopOder3);
                    // чтобы отображалось прибавляем к числу пустую строчку ""
                    treeMen.setText(fff);
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
            final Query aaa4= FirebaseDatabase.getInstance().getReference("Заявки")
                    .child(dateOfPointCalend)
                    .child(RefplaneCity)
                    .child(timeofPoint)
                    .child(RefStringMap)
                    .child( fourPoint.getText().toString() )
                    .child("Человек")
                    .orderByChild( "Человек" );
            aaa4.addChildEventListener( new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    //в БД стоит число поэтому ставим ""
                    String fff=""+dataSnapshot.child( "Человек" ).getValue(Integer.class);
                    String StopOder4=dataSnapshot.child("Остановлена").getValue(String.class);
                    String fourTimeSend4=dataSnapshot.child("Отправлена").getValue(String.class);
                    String oneNameDriver4=dataSnapshot.child("Водитель").getValue(String.class);
                    String fourTimeAccepted4=dataSnapshot.child("Принята").getValue(String.class);
                    // чтобы отображалось прибавляем к числу пустую строчку ""
                    fourMen.setText(fff);
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

// КНОПКИ ПЕРВАЯ ТОЧКА
    // STOPODER 1 точка
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
    // ОТМЕНА STOPODER 1 точка
    public void DelBtnOneStop(View view){

        DelBtnOneStop.setEnabled(false);
        sendToDriver1.setEnabled(false);
        BtnOneDriver.setTextColor(getResources().getColor( R.color.colorBlack));
        BtnOneDriver.setText("Driver");
        BtnOneDriver.setEnabled(false);

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
    // // ВЫБОР ВОДИТЕЛЯ 1 точка
    public void choise_Driver1 (View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder( ServApp_1.this );
        builder.setTitle( "Выберите Водителя" );
        // Отображает Водителей загруженных из БД
        builder.setItems( array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
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
    // Отправить заявку водителю по 1 точки
    public void sendToDriver1(View view){
        sendToDriver1.setEnabled(false);

        database01=FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Водители")
                .child("Personal")
                .child(Dr1PhRef)
                .child("Заявка");
        ref01.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ref01.child("направление").setValue(RefplaneCity); //Самолет
                ref01.child("рейс").setValue(timeofPoint); //время сбора*время вылета(прилета)
                ref01.child("маршрут").setValue(RefStringMap); //Маршрут*стоимость
                ref01.child("точкаСбора1").setValue(onePoint.getText().toString());
                ref01.child("точкаСбора1Чел").setValue(oneMen.getText().toString());
                // искусственная задержка.
                // записывается в последнюю очередь т.к. по факту этой записи запускается функция уведомления водителя
                // ее поставил в последнюю очередь чтобы вся заявка успела записаться в БД
                ref01.child("дата").setValue(dateOfPointCalend); //Дата сбора*дата вылета(прилета)

                ref01.removeEventListener( this );
                //Отправляем время "Заказ отправлен" в БД ServerApp по 1-ой точке
                sendTimeToServerApp1Point();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

// КНОПКИ ВТОРАЯ ТОЧКА
    // STOPODER 2 точка
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
    // ОТМЕНА STOPODER 2 точка
    public void DelBtnTwoStop(View view){

        DelBtnTwoStop.setEnabled(false);
        sendToDriver2.setEnabled(false);
        BtnTwoDriver.setTextColor(getResources().getColor( R.color.colorBlack));
        BtnTwoDriver.setText("Driver");
        BtnTwoDriver.setEnabled(false);

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
    // ВЫБОР ВОДИТЕЛЯ 2 точка
    public void choise_Driver2 (View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder( ServApp_1.this );
        builder.setTitle( "Выберите Водителя" );
        // Отображает Водителей загруженных из БД
        builder.setItems( array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
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
    // Отправить заявку водителю по второй точке
    public void sendToDriver2(View view){
        sendToDriver2.setEnabled(false);

        database01=FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Водители").child("Personal").child(Dr2PhRef).child("Заявка");
        ref01.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ref01.child("направление").setValue(RefplaneCity); //Самолет
                ref01.child("рейс").setValue(timeofPoint); //время сбора*время вылета(прилета)
                ref01.child("маршрут").setValue(RefStringMap); //Маршрут*стоимость
                ref01.child("точкаСбора2").setValue(twoPoint.getText().toString());
                ref01.child("точкаСбора2Чел").setValue(twoMen.getText().toString());
                // искусственная задержка.
                // записывается в последнюю очередь т.к. по факту этой записи запускается функция уведомления водителя
                // ее поставил в последнюю очередь чтобы вся заявка успела записаться в БД
                ref01.child("дата").setValue(dateOfPointCalend); //Дата сбора*дата вылета(прилета)

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

// КНОПКИ ТРЕТЬЯ ТОЧКА
    // STOPODER 3 точка
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
    // ОТМЕНА STOPODER 3 точка
    public void DelBtnTreeStop(View view){

        DelBtnTreeStop.setEnabled(false);
        sendToDriver3.setEnabled(false);
        BtnTreeDriver.setTextColor(getResources().getColor( R.color.colorBlack));
        BtnTreeDriver.setText("Driver");
        BtnTreeDriver.setEnabled(false);


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
    // ВЫБОР ВОДИТЕЛЯ 3 точка
    public void choise_Driver3 (View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder( ServApp_1.this );
        builder.setTitle( "Выберите Водителя" );
        // Отображает Водителей загруженных из БД
        builder.setItems( array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
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
    // Отправить заявку водителю по третьей точке
    public void sendToDriver3(View view){

        sendToDriver3.setEnabled(false);

        database01=FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Водители")
                .child("Personal")
                .child(Dr3PhRef)
                .child("Заявка");
        ref01.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ref01.child("направление").setValue(RefplaneCity); //Самолет
                ref01.child("рейс").setValue(timeofPoint); //время сбора*время вылета(прилета)
                ref01.child("маршрут").setValue(RefStringMap); //Маршрут*стоимость
                ref01.child("точкаСбора3").setValue(treePoint.getText().toString());
                ref01.child("точкаСбора3Чел").setValue(treeMen.getText().toString());
                // искусственная задержка.
                // записывается в последнюю очередь т.к. по факту этой записи запускается функция уведомления водителя
                // ее поставил в последнюю очередь чтобы вся заявка успела записаться в БД
                ref01.child("дата").setValue(dateOfPointCalend); //Дата сбора*дата вылета(прилета)

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

// КНОПКИ ЧЕТВЕРТАЯ ТОЧКА
    // STOPODER 4 точка
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
    // ОТМЕНА STOPODER 4 точка
    public void DelBtnFourStop(View view){

        DelBtnFourStop.setEnabled(false);
        sendToDriver4.setEnabled(false);
        BtnFourDriver.setTextColor(getResources().getColor( R.color.colorBlack));
        BtnFourDriver.setText("Driver");
        BtnFourDriver.setEnabled(false);

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
    // ВЫБОР ВОДИТЕЛЯ 4 точка
    public void choise_Driver4 (View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder( ServApp_1.this );
        builder.setTitle( "Выберите Водителя" );
        // Отображает Водителей загруженных из БД
        builder.setItems( array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
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
    // Отправить заявку водителю по четвертой точке
    public void sendToDriver4(View view){

        sendToDriver4.setEnabled(false);

        database01=FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Водители")
                .child("Personal")
                .child(Dr4PhRef)
                .child("Заявка");
        ref01.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ref01.child("направление").setValue(RefplaneCity); //Самолет
                ref01.child("рейс").setValue(timeofPoint); //время сбора*время вылета(прилета)
                ref01.child("маршрут").setValue(RefStringMap); //Маршрут*стоимость
                ref01.child("точкаСбора4").setValue(fourPoint.getText().toString());
                ref01.child("точкаСбора4Чел").setValue(fourMen.getText().toString());
                // искусственная задержка.
                // записывается в последнюю очередь т.к. по факту этой записи запускается функция уведомления водителя
                // ее поставил в последнюю очередь чтобы вся заявка успела записаться в БД
                ref01.child("дата").setValue(dateOfPointCalend); //Дата сбора*дата вылета(прилета)

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

// МЕТОДЫ
// Очистка данных перед считыванием
    // По 1 ТОЧКЕ
    public void BtnOne () {
            oneMen.setText("");
            oneTimeStop.setText("");
            oneTimeSend.setText("");
            oneTimeAccepted.setText("");
            oneNameDriver.setText("");
            oneTimedelete.setText("");


        }
    // По 2 ТОЧКЕ
    public void BtnTwo () {
        twoMen.setText("");
        twoTimeStop.setText("");
        twoTimeSend.setText("");
        twoTimeAccepted.setText("");
        twoNameDriver.setText("");
        twoTimedelete.setText("");

    }
    // По 3 ТОЧКЕ
    public void BtnTree () {
        treeMen.setText("");
        treeTimeStop.setText("");
        treeTimeSend.setText("");
        treeTimeAccepted.setText("");
        treeNameDriver.setText("");
        treeTimedelete.setText("");

    }
    // По 4 ТОЧКЕ
    public void BtnFour () {
        fourMen.setText("");
        fourTimeStop.setText("");
        fourTimeSend.setText("");
        fourTimeAccepted.setText("");
        fourNameDriver.setText("");
        fourTimedelete.setText("");

    }

// Считывание водителя (телефона)
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

//Отправляем время "Заказ отправлен" в БД ServerApp по 1 точки
    public void sendTimeToServerApp1Point(){
        // получение текущего времени
        getTimNow();

        //080420 Запись Отправлена заявка водителю заявки в БД ServApp...-...-...-"Отправлена"...
        ref02 = database01.getReference("Заявки")
                .child(Data.getText().toString())
                .child(Map.getText().toString())
                .child(TimeFly.getText().toString())
                .child(Road.getText().toString())
                .child(onePoint.getText().toString())
                .child("Человек")
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
    //Отправляем время "Заказ отправлен" в БД ServerApp по 2-ой точке
    public void sendTimeToServerApp2Point(){

        // получение текущего времени
        getTimNow();

        //080420 Запись Отправлена заявка водителю заявки в БД ServApp...-...-...-"Отправлена"...
        ref02 = database01.getReference("Заявки")
                .child(Data.getText().toString())
                .child(Map.getText().toString())
                .child(TimeFly.getText().toString())
                .child(Road.getText().toString())
                .child(twoPoint.getText().toString())
                .child("Человек")
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
    //Отправляем время "Заказ отправлен" в БД ServerApp по 3-ой точке
    public void sendTimeToServerApp3Point(){
        // получение текущего времени
        getTimNow();

        //080420 Запись Отправлена заявка водителю заявки в БД ServApp...-...-...-"Отправлена"...
        ref02 = database01.getReference("Заявки")
                .child(Data.getText().toString())
                .child(Map.getText().toString())
                .child(TimeFly.getText().toString())
                .child(Road.getText().toString())
                .child(treePoint.getText().toString())
                .child("Человек")
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
    //Отправляем время "Заказ отправлен" в БД ServerApp по 4-ой точке
    public void sendTimeToServerApp4Point(){
        // получение текущего времени
        getTimNow();

        //080420 Запись Отправлена заявка водителю заявки в БД ServApp...-...-...-"Отправлена"...
        ref02 = database01.getReference("Заявки")
                .child(Data.getText().toString())
                .child(Map.getText().toString())
                .child(TimeFly.getText().toString())
                .child(Road.getText().toString())
                .child(fourPoint.getText().toString())
                .child("Человек")
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

    //ПОЛУЧЕНИЕ ТЕКУЩЕГО ВРЕМЕНИ
    public void  getTimNow(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a dd-MM ");
        dateTime= simpleDateFormat.format(calendar.getTime());
    }
}


