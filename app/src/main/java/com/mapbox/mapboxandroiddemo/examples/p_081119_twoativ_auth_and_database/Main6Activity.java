package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main6Activity extends AppCompatActivity {

    FirebaseDatabase ggg;
    DatabaseReference mmm;

    List<Integer> num=new ArrayList<Integer>(  );
    Integer[] ar={};
    Button btnStatus,cancelOder,detailsTrip;
    String userID;
    String userI;

    String token;

    String[] CancelOderWhy ={"Самолет отменён","Передумал", };

    FirebaseAuth mAuth;

    TextView Calend_Out,flight_number_Out,Map,road_number_out,road_name_out;
    TextView number,information2,people,people2,process,process1,process2,process3,searchCar;
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
        //information=findViewById( R.id.information );
        information2=findViewById( R.id.information2 );
        people=findViewById( R.id.people );
        people2=findViewById( R.id.people2 );
        process=findViewById( R.id.process );
        process1=findViewById( R.id.process1 );
        process2=findViewById( R.id.process2 );
        process3=findViewById( R.id.process3 );
        searchCar=findViewById( R.id.searchCar );
        cancelOder=findViewById( R.id.cancelOder );
        detailsTrip=findViewById( R.id.detailsTrip );

        num=new ArrayList<Integer>( Arrays.asList( ar ) );

        mAuth= FirebaseAuth.getInstance(  );
        FirebaseUser user=mAuth.getCurrentUser();

        //полуаем номер телефона пользователя
        userID=user.getPhoneNumber();
        userI=user.getUid();
        //полуаем номер ID пользователя
        //userID=user.getUid();
        //Log.d("TAG", userID);
// прослушивание listwiew
        /*listwiew=findViewById( R.id.listwiew );
        basa=new ArrayList<String>( Arrays.asList( array ) );
        ad= new ArrayAdapter<>( this,android.R.layout.simple_list_item_1,basa );
        listwiew.setAdapter( ad );*/

        // Disable Button "Отменить заявку" if Text is Empty
        Calend_Out.addTextChangedListener( loginTextWather );
        Map.addTextChangedListener( loginTextWather );
        road_number_out.addTextChangedListener( loginTextWather );
        road_name_out.addTextChangedListener( loginTextWather );
        flight_number_Out.addTextChangedListener( loginTextWather );
    }
// Блокировка кнопки Back!!!! (Иначе в БД Будет Задвоение! :)))
    @Override
    public void onBackPressed(){
    }
// Вызов Личного статуса заказа вкладка пользователи "Пользователи"
public void btnStatus(View view){


        //ВАЖНО УБРАТЬ КОМЕНТЫ!!! очистка массива для обновления количества пользователей по заявке
   num.clear();

    Query aaa= FirebaseDatabase.getInstance().getReference("Пользователи").child( userI )
            .orderByChild( "Status" );
    aaa.addChildEventListener( new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            String data=dataSnapshot.child( "дата" ).getValue(String.class);
            String map=dataSnapshot.child( "направление" ).getValue(String.class);
            String roar_number=dataSnapshot.child( "маршрут_номер" ).getValue(String.class);
            String road_name=dataSnapshot.child( "маршрут_точкаСбора" ).getValue(String.class);
            String flidht_number=dataSnapshot.child( "рейс_самолета" ).getValue(String.class);
            token=dataSnapshot.child( "token" ).getValue(String.class);
            Integer peopleOder=dataSnapshot.child("Человек_в_Заявке").getValue(Integer.class);
            String сarDrive=dataSnapshot.child("Автомобиль").getValue(String.class);
            Log.d("TAG", ""+сarDrive);

            Calend_Out.setText( data );
            Map.setText( map );
            road_number_out.setText( roar_number );
            road_name_out.setText( road_name );
            flight_number_Out.setText( flidht_number );
            people.setText(""+peopleOder);

            if (сarDrive == null){}
            else {
                searchCar.setText("Найден автомобиль"+сarDrive);
            }

//c 31/03/20 Не испльзуется. но код рабочий Получение текущего количества людей зарегистрированных на данный маршрут
            /*DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Заявки")
                    .child( Map.getText().toString() )
                    .child( Calend_Out.getText().toString() )
                    .child(flight_number_Out.getText().toString())
                    .child( road_number_out.getText().toString() )
                    .child( road_name_out.getText().toString() );

            DatabaseReference usersdRef = rootRef.child( "Users" );
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        int xxx=0;
                        int flight = ds.child("Человек").getValue(Integer.class);
                        int a=xxx+flight;
                        num.add( a );
                        int sum=0;

                        for (int counter=0;counter<num.size();counter++){
                            sum+= num.get(counter);
                        }
                        people.setText(""+sum);

                        if(sum>=5){
                            //information.setText( "маршрут сформирован V" );
                            //information.setTextColor( getResources().getColor( R.color.colorRed ) );
                            searchCar.setText("поиск автомобиля...");
                            process2.setText( "|" );
                            process3.setText( "V" );
                    }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

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

        }

    // Disable Button "Отменить заявку" if Text is Empty
    private final TextWatcher loginTextWather = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            String calendInput = Calend_Out.getText().toString().trim();
            String flightInput = Map.getText().toString().trim();
            String road_number_ou = road_number_out.getText().toString().trim();
            String road_name_ou = road_name_out.getText().toString().trim();
            String flight_number_Ou = flight_number_Out.getText().toString().trim();

            cancelOder.setEnabled(!calendInput.isEmpty() && !flightInput.isEmpty()
                    && !road_number_ou.isEmpty() && !road_name_ou.isEmpty() && !flight_number_Ou.isEmpty());



            //если база данных НЕ Пуста (а именно параметры loginTextWather НЕ пусты!!! ) то вступают в силу эти изменения
            number.setText( " заявка оформлена" );
            number.setTextColor(getResources().getColor( R.color.colorRed ));
            //information.setText( "формирование маршрута..." );
            process.setText( "|" );
            process1.setText( "V" );
            information2.setText( "зарегистрировано" );
            people2.setText( "человек(а)" );
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
// Отмена текущей заявки нажимаем (УДАЛЕНИЕ ИЗ БД)
    public void cancelOder (View view){

        AlertDialog.Builder builder=new AlertDialog.Builder( Main6Activity.this );
        builder.setTitle( "Укажите причину отмены");
        //builder.setCancelable( false );

        builder.setItems( CancelOderWhy, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                ggg = FirebaseDatabase.getInstance();
                mmm = ggg.getReference("Заявки")
                        .child(Map.getText().toString() )
                        .child( Calend_Out.getText().toString() )
                        //.child(road_number_out.getText().toString())
                        .child(flight_number_Out.getText().toString()  )
                        .child(road_number_out.getText().toString())
                        .child(road_name_out.getText().toString())
                        .child("notificationTokens");
                mmm.child( token ).removeValue();

                //28 02 2020  задержка на удаление из БД, нужна для правильного подсчета человек в БД ЗАявкиServerApp
                Handler handler = new Handler();
                handler.postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        mmm = ggg.getReference("Заявки")
                                .child(Map.getText().toString() )
                                .child( Calend_Out.getText().toString() )
                                //.child(road_number_out.getText().toString())
                                .child(flight_number_Out.getText().toString()  )
                                .child(road_number_out.getText().toString())
                                .child(road_name_out.getText().toString())
                                .child("Users");
                        mmm.child( userI ).removeValue();

                        mmm = ggg.getReference("Пользователи")
                                .child(userI);
                        mmm.child( "Status" ).removeValue();
                        Toast.makeText(Main6Activity.this,"Заявка Отменена....",Toast.LENGTH_LONG).show();

                        Calend_Out.setText( "" );
                        Map.setText( "" );
                        road_number_out.setText( "" );
                        road_name_out.setText( "" );
                        flight_number_Out.setText( "" );
                        number.setText( " заявка НЕ оформлена" );
                        number.setTextColor(getResources().getColor( R.color.colorNew ));

                        //information.setText( "" );
                        searchCar.setText("");
                        process2.setText( "" );
                        process3.setText( "" );
                        people.setText("");
                        process.setText( "" );
                        process1.setText( "" );
                        btnStatus.setEnabled(false);
                    }
                },1000
                );

            }
        }
        );

        builder.setNeutralButton("Назад", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void backMainList (View view){
        Intent qwe= new Intent(this,Choose_direction.class);
        startActivity(qwe);
    }
}