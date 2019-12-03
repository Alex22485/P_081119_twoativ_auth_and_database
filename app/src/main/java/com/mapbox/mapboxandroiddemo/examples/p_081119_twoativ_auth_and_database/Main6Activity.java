package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main6Activity extends AppCompatActivity {




    Button btnStatus;
    String userID;
    FirebaseAuth mAuth;
    ListView listwiew;

    List<String> basa=new ArrayList<String>(  );
    ArrayAdapter ad;
    String[] array={};

    TextView oder_Ok;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main6 );

        oder_Ok=findViewById( R.id.oder_Ok );
        btnStatus = findViewById( R.id.btnStatus );
        mAuth= FirebaseAuth.getInstance(  );
        FirebaseUser user=mAuth.getCurrentUser();

        //полуаем номер телефона пользователя
        userID=user.getPhoneNumber();

        //полуаем номер ID пользователя
        //userID=user.getUid();


        Log.d("TAG", userID);


        listwiew=findViewById( R.id.listwiew );
        basa=new ArrayList<String>( Arrays.asList( array ) );
        ad= new ArrayAdapter<>( this,android.R.layout.simple_list_item_1,basa );
        listwiew.setAdapter( ad );

    }


public void btnStatus(View view){

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Пользователи");
    rootRef.orderByChild( userID ).addChildEventListener( new ChildEventListener() {
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
    } );

    oder_Ok.setText( "Спасибо, Ваш заказ принят!" );






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