package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;



public class ServApp_1 extends AppCompatActivity {

    TextView Дата;
    TextView Рейс;
    TextView Направление;
    TextView Маршрут;
    TextView onePoint;
    TextView oneMen;
    TextView twoPoint;
    TextView twoMen;
    TextView treePoint;
    TextView treeMen;
    TextView fourPoint;
    TextView fourMen;

    Button choiseD;
    Button choiseF;
    Button choiseN;
    Button read;
    Button BtnOne;
    Button BtnTwo;
    Button BtnTree;
    Button BtnFour;

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

    FirebaseDatabase database01;
    DatabaseReference ref01;
    DatabaseReference dbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_serv_app_1 );

        Дата = findViewById( R.id. Дата );
        Рейс = findViewById( R.id. Рейс );
        Направление = findViewById( R.id. Направление );
        Маршрут = findViewById( R.id. Маршрут );
        onePoint = findViewById( R.id. onePoint );
        oneMen = findViewById( R.id. oneMen );
        twoPoint = findViewById( R.id. twoPoint );
        twoMen = findViewById( R.id. twoMen );
        treePoint = findViewById( R.id. treePoint );
        treeMen = findViewById( R.id. treeMen );
        fourPoint = findViewById( R.id. fourPoint );
        fourMen = findViewById( R.id. fourMen );
        choiseD = findViewById( R.id. choiseD );
        choiseF = findViewById( R.id. choiseF );
        choiseN = findViewById( R.id. choiseN );
        read = findViewById( R.id. read );
        BtnOne = findViewById( R.id. BtnOne );
        BtnTwo = findViewById( R.id. BtnTwo );
        BtnTree = findViewById( R.id. BtnTree );
        BtnFour = findViewById( R.id. BtnFour );



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
                    }
                    }, year,month,dayOfmonth);
                datePickerDialog.show();
            }
        } );
    }
// определениие точек сбора в зависимости от выбранного маршрута
    public void getPoint(){

        String map=Маршрут.getText().toString();
        String map1=listMap1[0];
        String map2=listMap2[0];

        String map3=listMap1[1];
        String map4=listMap2[1];
// если маршрут Краснояск-Аэропорт или Аэропорт-Красноярск то устанавливаем точки сбора из массива pointOneMap
        if (map==map1||map==map2){
            onePoint.setText( pointOneMap[0] );
            twoPoint.setText( pointOneMap[1] );
            treePoint.setText( pointOneMap[2] );
            fourPoint.setText( pointOneMap[3] );
        }else if(map==map3||map==map4){
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

                            //запускаем выполнение метода
                            getPoint();
                        }
                    }
            );
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        }

        public void reada (View view){
        // считывание количества человек из БД по первой точке сбора
            Query aaa1= FirebaseDatabase.getInstance().getReference("ЗаявкиServerApp")
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
                    // чтобы отображалось прибавляем к числу пустую строчку ""
                    oneMen.setText(data+"" );
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
            Query aaa2= FirebaseDatabase.getInstance().getReference("ЗаявкиServerApp")
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
                    // чтобы отображалось прибавляем к числу пустую строчку ""
                    twoMen.setText(data+"" );
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
            Query aaa3= FirebaseDatabase.getInstance().getReference("ЗаявкиServerApp")
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
                    // чтобы отображалось прибавляем к числу пустую строчку ""
                    treeMen.setText(data+"" );
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
            Query aaa4= FirebaseDatabase.getInstance().getReference("ЗаявкиServerApp")
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
                    // чтобы отображалось прибавляем к числу пустую строчку ""
                    fourMen.setText(data+"" );
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




            //030320 Запись токена в БД ЗАЯВКИ
            /*database01 = FirebaseDatabase.getInstance();
            ref01 = database01.getReference( "ЗаявкиServerApp" )
                    .child( Дата.getText().toString() )
                    .child( Направление.getText().toString() )
                    .child( Рейс.getText().toString()  )
                    .child( Маршрут.getText().toString()  )
                    .child(onePoint.getText().toString());

            dbRef = database01.getReference( "ЗаявкиServerApp" )
                    .child( Дата.getText().toString() )
                    .child( Направление.getText().toString() )
                    .child( Рейс.getText().toString()  )
                    .child( Маршрут.getText().toString()  )
                    .child(onePoint.getText().toString());
            dbRef.addValueEventListener(changeListener);
            String title = dataSnapshot.child("message1").child("title").getValue(String.class);*/
            /*ref01.addValueEventListener( new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ref01.child( onePoint.getText().toString() ).getV( "Человек");
                    // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД "вкладка "newToken
                    ref01.removeEventListener( this );

                    //Видимость кнопки Проверить статус
                    btnStatus.setEnabled( true );
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            } );*/


            /*Query oneP= FirebaseDatabase.getInstance().getReference("ЗаявкиServerApp")
                    .child(Дата.getText().toString() )
                    .child(Направление.getText().toString()  )
                    .child(Рейс.getText().toString()  )
                    .child(Маршрут.getText().toString()   )
                    .orderByChild("Аэрокосмическая академия" );
            oneP.addChildEventListener( new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    int dataone=dataSnapshot.child( "Человек" ).getValue(Integer.class);
                    oneMen.setText( dataone+"" );

                    Log.d("TAG", dataone+" ");
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
            } );*/

            /*Query twoP= FirebaseDatabase.getInstance().getReference("ЗаявкиServerApp")
                    .child(Дата.getText().toString() )
                    .child(Направление.getText().toString()  )
                    .child(Рейс.getText().toString()  )
                    .child(Маршрут.getText().toString()   )
                    .orderByChild(twoPoint.getText().toString() );
            twoP.addChildEventListener( new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    int datatwo=dataSnapshot.child( "Человек" ).getValue(Integer.class);
                    twoMen.setText( datatwo+"" );

                    Log.d("TAG", datatwo+" ");
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
            } );*/

            /*DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("ЗаявкиServerApp")
                    .child(Дата.getText().toString())
                    .child( Направление.getText().toString() )
                    .child( Рейс.getText().toString() );
                    //.child( Маршрут.getText().toString() );
            DatabaseReference usersdRef = rootRef.child( Маршрут.getText().toString() );
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        //String data = ds.child("Человек").getValue(String.class);
                        int flight = ds.child("Человек").getValue(Integer.class);


                        Log.d("TAG", flight+" ");

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };usersdRef.addListenerForSingleValueEvent(valueEventListener);*/
        }
}


