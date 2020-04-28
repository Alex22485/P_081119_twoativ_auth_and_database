package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main5Activity extends AppCompatActivity {
    private static final String TAG ="Main5Activity" ;

    // 28 02 2020 Лист Сформировать заявку Водителю и отправить ему уведомление о новом Заказе

    //List<String> basa=new ArrayList<String>(  );//можно так 1*
    ArrayList<String>driver=new ArrayList<String>(  );
    String[] array={};
    String  key;
    TextView proba;

    FirebaseAuth mAuth;
    String driverPhone;
    String driverId;
    String driverToken;


    FirebaseDatabase database01;
    DatabaseReference ref01;

      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main5 );
        proba = findViewById( R.id.proba );
          Log.d(TAG, "onCreate");


////28 02 2020 Получить все ключи объекта по его значению "Водила" записать их в ArrayList и преобразовать в строковый массив array
//          DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child( "Proba" );
//          ref.orderByValue().equalTo( "Водила" ).addListenerForSingleValueEvent( new ValueEventListener() {
//              @Override
//              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                  for (DataSnapshot snap: dataSnapshot.getChildren()){
//
//                      //Преобразовываем ArrayList в обычный массив чтобы вставить его в AlertDialog
//                      key = snap.getKey(); //получить все ключи значения
//                      driver.add( key );
//                      array = driver.toArray(new String[driver.size()]);
//
//                      //String value = snap.getValue(String.class); //получить все значения, так побаловаться
//                  }
//              }
//              @Override
//              public void onCancelled(@NonNull DatabaseError databaseError) {
//
//              }
//          } );
        }

    @Override
    protected void onStart (){
        super.onStart();
        Log.d(TAG, "onStart");
        cheskInternet();

    }

    //Выберите Водителя
    public void btn_number_Flight (View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder( Main5Activity.this );
        builder.setTitle( "Выберите Водителя" );
        // Отображает Водителей загруженных из БД
        builder.setItems( array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        proba.setText( array[which] );
                    }
                }
        );
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy");

        // 1.1не выдает всплывающее сообщение в другом активити когда нет интернета. Работает в паре с finish() в onPause
        database01.goOffline();


    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "onPause");

        // 1.2не выдает всплывающее сообщение в другом активити когда нет интернета. Работает в паре с  database01.goOffline() в onDestroy
        finish();
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "onResume");
        //Toast.makeText( DriversApp_0.this, "token"+driverToken, Toast.LENGTH_SHORT ).show();
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG, "onRestart");
    }



    //Проверка интернета
    public void cheskInternet(){

        key="";

        //задержка запроса
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                check();
            }
        },5000);

        //чтение в БД с правилом для любых пользователей
        database01 = FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Check")
                .child("Internet")
                .child("Work");
        ref01.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                // Чтение
                key=dataSnapshot.getValue(String.class);
                Toast.makeText( Main5Activity.this, "Интернет есть?  "+key, Toast.LENGTH_SHORT ).show();

                // с этой записью makeText появляется только один раз!!!!! ХОРОШО
                ref01.removeEventListener(this);

                proba();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void check(){
        if (key.isEmpty()){
            getActivityError();
        }
    }

    public void getActivityError(){
        Intent aaa = new Intent(this,InternetNot.class);
        startActivity(aaa);
    }

    public void proba(){
        Intent aaa = new Intent(this,ServApp_1.class);
        startActivity(aaa);

    }

    //работает в паре с классом ExampleBroadcastReceiver для проверки интернета
    @Override
    protected void onStop (){
        super.onStop();
        Log.d(TAG, "onStop");
        //finish();
//        unregisterReceiver(exampleBroadcastReceiver);
    }

}




