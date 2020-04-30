package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DriversApp_1 extends AppCompatActivity {

    TextView DataOrder;
    TextView TimeOrder;
    String MapOrder;
    TextView RoutepOrder;
    TextView StopOrder1;
    TextView MenpOrder1;
    TextView StopOrder2;
    TextView MenpOrder2;
    TextView StopOrder3;
    TextView MenpOrder3;
    TextView StopOrder4;
    TextView MenpOrder4;

    TextView TextStopOder1;
    TextView TextStopOder2;
    TextView TextStopOder3;
    TextView TextStopOder4;

    TextView TextMenpOrder1;
    TextView TextMenpOrder2;
    TextView TextMenpOrder3;
    TextView TextMenpOrder4;
    TextView TextRoutepOrder;
    //TextView TextMapOrder;

    TextView TextDataOrder;
    TextView TextTimeOrder;

    Button BtnYes;
    Button BtnNo;
    Button timeExpl;
    Button MapSee;

    FirebaseDatabase database01;
    DatabaseReference ref01;
    FirebaseDatabase database02;
    DatabaseReference ref02;
    FirebaseDatabase database03;
    DatabaseReference ref03;
    FirebaseDatabase database04;
    DatabaseReference ref04;

    String dateTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivers_app_1);

        TextDataOrder=findViewById(R.id.TextDataOrder);
        TextTimeOrder=findViewById(R.id.TextTimeOrder);

        DataOrder=findViewById(R.id.DataOrder);
        TimeOrder=findViewById(R.id.TimeOrder);
        //MapOrder=findViewById(R.id.MapOrder);
        RoutepOrder=findViewById(R.id.RoutepOrder);
        StopOrder1=findViewById(R.id.StopOrder1);
        MenpOrder1=findViewById(R.id.MenpOrder1);
        StopOrder2=findViewById(R.id.StopOrder2);
        MenpOrder2=findViewById(R.id.MenpOrder2);
        StopOrder3=findViewById(R.id.StopOrder3);
        MenpOrder3=findViewById(R.id.MenpOrder3);
        StopOrder4=findViewById(R.id.StopOrder4);
        MenpOrder4=findViewById(R.id.MenpOrder4);

        TextStopOder1=findViewById(R.id.TextStopOder1);
        TextStopOder2=findViewById(R.id.TextStopOder2);
        TextStopOder3=findViewById(R.id.TextStopOder3);
        TextStopOder4=findViewById(R.id.TextStopOder4);

        TextMenpOrder1=findViewById(R.id.TextMenpOrder1);
        TextMenpOrder2=findViewById(R.id.TextMenpOrder2);
        TextMenpOrder3=findViewById(R.id.TextMenpOrder3);
        TextMenpOrder4=findViewById(R.id.TextMenpOrder4);
        TextRoutepOrder=findViewById(R.id.TextRoutepOrder);
        //TextMapOrder=findViewById(R.id.TextMapOrder);




        BtnYes=findViewById(R.id.BtnYes);
        BtnNo=findViewById(R.id.BtnNo);
        timeExpl=findViewById(R.id.timeExpl);
        MapSee=findViewById(R.id.MapSee);


    }

    // Проверить заказ нужно изменить путь child( "123Lexus" ) для любого водителя чтобы работал Н-р id
    public void checkOder (View view){
        Query aaa= FirebaseDatabase.getInstance().getReference("Drivers").child( "123Lexus" )
                .orderByChild( "Заявки" );
        aaa.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String time=dataSnapshot.child( "рейс" ).getValue(String.class);
                String date=dataSnapshot.child( "дата" ).getValue(String.class);
                MapOrder=dataSnapshot.child( "направление" ).getValue(String.class);
                String маршрут=dataSnapshot.child( "маршрут" ).getValue(String.class);
                String point1=dataSnapshot.child( "точкаСбора1" ).getValue(String.class);
                String point1Men=dataSnapshot.child( "точкаСбора1Чел" ).getValue(String.class);
                String point2=dataSnapshot.child( "точкаСбора2" ).getValue(String.class);
                String point2Men=dataSnapshot.child( "точкаСбора2Чел" ).getValue(String.class);
                String point3=dataSnapshot.child( "точкаСбора3" ).getValue(String.class);
                String point3Men=dataSnapshot.child( "точкаСбора3Чел" ).getValue(String.class);
                String point4=dataSnapshot.child( "точкаСбора4" ).getValue(String.class);
                String point4Men=dataSnapshot.child( "точкаСбора4Чел" ).getValue(String.class);

                DataOrder.setText(date);
                TimeOrder.setText(time);
                //MapOrder.setText(направление);
                RoutepOrder.setText(маршрут);
                StopOrder1.setText(point1);
                MenpOrder1.setText(point1Men);
                StopOrder2.setText(point2);
                MenpOrder2.setText(point2Men);
                StopOrder3.setText(point3);
                MenpOrder3.setText(point3Men);
                StopOrder4.setText(point4);
                MenpOrder4.setText(point4Men);

                visability ();
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
        });
    }

    public void visability (){

        String onePoint=StopOrder1.getText().toString();
        String twoPoint=StopOrder2.getText().toString();
        String treePoint=StopOrder3.getText().toString();
        String fourPoint=StopOrder4.getText().toString();
        Log.d("TAG", "вторая точка" + twoPoint);


        if(!onePoint.isEmpty()){
            StopOrder1.setVisibility(View.VISIBLE);
            StopOrder1.setTextColor(getResources().getColor( R.color.colorNew ));
            TextStopOder1.setVisibility(View.VISIBLE);
            TextMenpOrder1.setVisibility(View.VISIBLE);
            MenpOrder1.setVisibility(View.VISIBLE);
            MenpOrder1.setTextColor(getResources().getColor( R.color.colorNew ));

            //Видимость общего текста
            visabileGeneralText();

    }
        if (!twoPoint.isEmpty()){
            StopOrder2.setVisibility(View.VISIBLE);
            StopOrder2.setTextColor(getResources().getColor( R.color.colorNew ));
            TextStopOder2.setVisibility(View.VISIBLE);
            TextMenpOrder2.setVisibility(View.VISIBLE);
            MenpOrder2.setVisibility(View.VISIBLE);
            MenpOrder2.setTextColor(getResources().getColor( R.color.colorNew ));

            //Видимость общего текста
            visabileGeneralText();
        }
        if (!treePoint.isEmpty()){
            StopOrder3.setVisibility(View.VISIBLE);
            StopOrder3.setTextColor(getResources().getColor( R.color.colorNew ));
            TextStopOder3.setVisibility(View.VISIBLE);
            TextMenpOrder3.setVisibility(View.VISIBLE);
            MenpOrder3.setVisibility(View.VISIBLE);
            MenpOrder3.setTextColor(getResources().getColor( R.color.colorNew ));

            //Видимость общего текста
            visabileGeneralText();
        }
        if (!fourPoint.isEmpty()){
            StopOrder4.setVisibility(View.VISIBLE);
            StopOrder4.setTextColor(getResources().getColor( R.color.colorNew ));
            TextStopOder4.setVisibility(View.VISIBLE);
            TextMenpOrder4.setVisibility(View.VISIBLE);
            MenpOrder4.setVisibility(View.VISIBLE);
            MenpOrder4.setTextColor(getResources().getColor( R.color.colorNew ));

            //Видимость общего текста
            visabileGeneralText();
        }

    }
    //Видимость общего текста
    public void visabileGeneralText(){

        BtnYes.setVisibility(View.VISIBLE);
        BtnNo.setVisibility(View.VISIBLE);
        MapSee.setVisibility(View.VISIBLE);

        DataOrder.setVisibility(View.VISIBLE);
        DataOrder.setTextColor(getResources().getColor( R.color.colorNew ));

//        MapOrder.setVisibility(View.VISIBLE);
//        MapOrder.setTextColor(getResources().getColor( R.color.colorNew ));

        RoutepOrder.setVisibility(View.VISIBLE);
        RoutepOrder.setTextColor(getResources().getColor( R.color.colorNew ));

        TextDataOrder.setVisibility(View.VISIBLE);
        TextTimeOrder.setVisibility(View.VISIBLE);
        TextRoutepOrder.setVisibility(View.VISIBLE);
//        TextMapOrder.setVisibility(View.VISIBLE);

        TimeOrder.setVisibility(View.VISIBLE);
        TimeOrder.setTextColor(getResources().getColor( R.color.colorNew ));

        timeExpl.setVisibility(View.VISIBLE);

    }

    //Принять заявку 26 03 2020 c записью в БД в нужную ветку заявки
    // нужно изменить путь child( "123Lexus" ) для любого водителя чтобы работал Н-р id
    public void BtnYes (View view){

        String a =  StopOrder1.getText().toString();
        String b =  StopOrder2.getText().toString();
        String c =  StopOrder3.getText().toString();
        String d =  StopOrder4.getText().toString();
        // если строка пусатя то запись в БД не будет
        String abcdRef =  "";

        if (!a.isEmpty()){

            //Получить текущую дату
            getTimNow();

            //260320 Запись в БД Заявки--...--Users-Заявки водителя принявшего заявку
            database01 = FirebaseDatabase.getInstance();
            ref01 = database01.getReference("Заявки")
                    .child(MapOrder)
                    .child(DataOrder.getText().toString())
                    .child(TimeOrder.getText().toString())
                    .child(RoutepOrder.getText().toString())
                    .child(StopOrder1.getText().toString())
                    .child("Заявка");
            ref01.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ref01.child("123Lexus").setValue(dateTime);
                    ref01.removeEventListener(this);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        // запись пустой точки номер два ПРОБА
        if (!b.isEmpty()){

            //Получить текущую дату
            getTimNow();

            database02 = FirebaseDatabase.getInstance();
            ref02 = database02.getReference("Заявки")
                    .child(MapOrder)
                    .child(DataOrder.getText().toString())
                    .child(TimeOrder.getText().toString())
                    .child(RoutepOrder.getText().toString())
                    .child(StopOrder2.getText().toString())
                    .child("Заявка");
            ref02.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ref02.child("123Lexus").setValue(dateTime);
                    ref02.removeEventListener(this);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        // запись пустой точки номер три ПРОБА
        if (!c.isEmpty()){

            //Получить текущую дату
            getTimNow();

            database03 = FirebaseDatabase.getInstance();
            ref03 = database03.getReference("Заявки")
                    .child(MapOrder)
                    .child(DataOrder.getText().toString())
                    .child(TimeOrder.getText().toString())
                    .child(RoutepOrder.getText().toString())
                    .child(StopOrder3.getText().toString())
                    .child("Заявка");
            ref03.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ref03.child("123Lexus").setValue(dateTime);
                    ref03.removeEventListener(this);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        // запись пустой точки номер четыре ПРОБА
        if (!d.isEmpty()){
            //Получить текущую дату
            getTimNow();

            database04 = FirebaseDatabase.getInstance();
            ref04 = database04.getReference("Заявки")
                    .child(MapOrder)
                    .child(DataOrder.getText().toString())
                    .child(TimeOrder.getText().toString())
                    .child(RoutepOrder.getText().toString())
                    .child(StopOrder4.getText().toString())
                    .child("Заявка");
            ref04.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ref04.child("123Lexus").setValue(dateTime);
                    ref04.removeEventListener(this);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    public void  getTimNow(){
        //ПОЛУЧЕНИЕ ТЕКУЩЕГО ВРЕМЕНИ
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a dd-MM ");
        dateTime= simpleDateFormat.format(calendar.getTime());

    }

    // кнопка Back сворачивает приложение
    @Override
    public void onBackPressed(){
        this.moveTaskToBack(true);
    }


}