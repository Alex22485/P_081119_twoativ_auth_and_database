package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main6Activity extends AppCompatActivity {

    List<Integer> num=new ArrayList<Integer>(  );
    Integer[] ar={};
     Button btnStatus;
    String userID;
    String userI;

    FirebaseAuth mAuth;
    // создание ListView
    /*ListView listwiew;
    List<String> basa=new ArrayList<String>(  );
    ArrayAdapter ad;
    String[] array={};*/
    TextView Calend_Out,flight_number_Out,Map,road_number_out,road_name_out;
    TextView number,information,information2,people,people2,process,process1,process2,process3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main6 );

        btnStatus = findViewById( R.id.btnStatus );
        number = findViewById( R.id.number );
        Calend_Out=findViewById( R.id.Calend_Out );
        flight_number_Out=findViewById( R.id.flight_number_Out );
        Map=findViewById( R.id.Map );
        road_number_out=findViewById( R.id.road_number_out );
        road_name_out=findViewById( R.id.road_name_out );
        information=findViewById( R.id.information );
        information2=findViewById( R.id.information2 );
        people=findViewById( R.id.people );
        people2=findViewById( R.id.people2 );
        process=findViewById( R.id.process );
        process1=findViewById( R.id.process1 );
        process2=findViewById( R.id.process2 );
        process3=findViewById( R.id.process3 );

        num=new ArrayList<Integer>( Arrays.asList( ar ) );

        mAuth= FirebaseAuth.getInstance(  );
        FirebaseUser user=mAuth.getCurrentUser();

        //полуаем номер телефона пользователя
        userID=user.getPhoneNumber();
        userI=user.getUid();
        //полуаем номер ID пользователя
        //userID=user.getUid();
        Log.d("TAG", userID);
// прослушивание listwiew
        /*listwiew=findViewById( R.id.listwiew );
        basa=new ArrayList<String>( Arrays.asList( array ) );
        ad= new ArrayAdapter<>( this,android.R.layout.simple_list_item_1,basa );
        listwiew.setAdapter( ad );*/
    }
// Вызов Личного статуса заказа вкладка пользователи "Пользователи"
public void btnStatus(View view){
        //очистка массива для обновления количества пользователей по заявке
    num.clear();

    Query aaa= FirebaseDatabase.getInstance().getReference("Пользователи").child( userID )
            .orderByChild( userI );
    aaa.addChildEventListener( new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            String data=dataSnapshot.child( "дата" ).getValue(String.class);
            String map=dataSnapshot.child( "направление" ).getValue(String.class);
            String roar_number=dataSnapshot.child( "маршрут_номер" ).getValue(String.class);
            String road_name=dataSnapshot.child( "маршрут_название" ).getValue(String.class);
            String flidht_number=dataSnapshot.child( "рейс_самолета" ).getValue(String.class);

            Calend_Out.setText( data );
            Map.setText( map );
            road_number_out.setText( roar_number );
            road_name_out.setText( road_name );
            flight_number_Out.setText( flidht_number );

// Получение текущего количества людей зарегистрированных на данный маршрут
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Заявки")
                    .child( Map.getText().toString() )
                    .child( Calend_Out.getText().toString() )
                    .child( road_number_out.getText().toString() );

            DatabaseReference usersdRef = rootRef.child( flight_number_Out.getText().toString() );
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        int xxx=0;
                        int flight = ds.child("число").getValue(Integer.class);
                        int a=xxx+flight;
                        num.add( a );
                        int sum=0;

                        for (int counter=0;counter<num.size();counter++){
                            sum+= num.get(counter);
                        }
                        people.setText(" "+sum);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };;usersdRef.addListenerForSingleValueEvent(valueEventListener);
// ПРОБА
            /*DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Заявки")
                    .child("Аэропорт-Красноярск")
                    .child( data )
                    .child( roar_number );
            DatabaseReference usersdRef = rootRef.child( flidht_number );
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {


                        String flight = ds.child("рейс").getValue(String.class);
                        number.setText(flight);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };usersdRef.addListenerForSingleValueEvent(valueEventListener);*/
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
    number.setText( " заявка оформлена   V" );
    information.setText( "идет формирование маршрута:" );
    process.setText( "|" );
    process1.setText( "|" );
    information2.setText( "уже зарегистрировано" );
    people2.setText( "человек(а)" );
// рабочий код возвращает правда всех юзеров
    //DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Пользователи");
    /*rootRef.orderByChild( userID ).addChildEventListener( new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            String data=dataSnapshot.child( "дата" ).getValue(String.class);
            String map=dataSnapshot.child( "направление" ).getValue(String.class);
            String roar_number=dataSnapshot.child( "маршрут_номер" ).getValue(String.class);
            String road_name=dataSnapshot.child( "маршрут_название" ).getValue(String.class);
            String flidht_number=dataSnapshot.child( "рейс_самолета" ).getValue(String.class);


            basa.add( "Дата:"+" "+data+"  "+map+" "+roar_number+":"+" "+road_name+" "+"Рейс самолета №"+" "+flidht_number );
            ad.notifyDataSetChanged();


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
// ВАЖНЫЙ ПРИМЕР!!! извлечение конкретных данных из CHILD
   /* DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Заявки")
            .child("Аэропорт-Красноярск")
            .child( "8 12 2019" )
            .child( "Маршрут 1")
            .child( "Рейс номер 1" );
    rootRef.orderByChild( userID ).addChildEventListener( new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


            String data=dataSnapshot.child( "дата" ).getValue(String.class);
            String phone=dataSnapshot.child( "phone" ).getValue(String.class);
            basa.add( data+""+phone );
            ad.notifyDataSetChanged();



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
    // ВАЖНЫЙ ПРИМЕР!!! извлечение ВСЕХ данных из всех CHILDs КОНКРЕТНОГО ПОЛЬЗОВАТЕЛЯ
   /* DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Заявки")
            .child("Аэропорт-Красноярск")
            .child( "8 12 2019" )
            .child( "Маршрут 1")
            .child( "Рейс номер 1" )
            .child( userID );
    // слово "photo" может быть вообще любым даже не совпадать с названием child. В listView получается каждое значение child в новой строке!!!!
    rootRef.orderByChild( "photo" ).addChildEventListener( new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


            String data=dataSnapshot.getValue(String.class);

            basa.add( data );
            ad.notifyDataSetChanged();



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
   /* Старье не работает !!!!!DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Заявки")
            .child("Аэропорт-Красноярск")
            .child( "8 12 2019" )
            .child( "Маршрут 1")
            ;
    DatabaseReference usersdRef = rootRef.child( "Рейс номер 1" );
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                String data = ds.child("дата").getValue(String.class);
                String flight = ds.child("рейс").getValue(String.class);
                String phone  = ds.child("phone").getValue(String.class);

                Log.d("TAG", data+"  "+"Рейс номер"+""+flight+"  "+phone);
                basa.add("Рейс №"+flight+"  "+"Дата"+"  "+data+"  "+phone );
                Collections.sort(basa);
                ad.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    };usersdRef.addListenerForSingleValueEvent(valueEventListener);*/
        }
}