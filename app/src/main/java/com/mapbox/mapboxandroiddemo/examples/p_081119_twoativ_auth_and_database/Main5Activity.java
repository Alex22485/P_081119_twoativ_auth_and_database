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

    TextView proba;

    String UserToken;
    String  keyReg;

    FirebaseDatabase database02;
    DatabaseReference ref02;

      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main5 );
        proba = findViewById( R.id.proba );
          Log.d(TAG, "onCreate");

          //получение токена
          FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(Main5Activity.this,new OnSuccessListener<InstanceIdResult>() {
              @Override
              public void onSuccess(InstanceIdResult instanceIdResult) {
                  UserToken = instanceIdResult.getToken();

                  //задержка запроса
                  Handler handler1 = new Handler();
                  handler1.postDelayed(new Runnable() {
                      @Override
                      public void run() {
                          //временно для проверки
                          Toast.makeText( Main5Activity.this, "Токен считан  "+UserToken, Toast.LENGTH_SHORT ).show();
                          Log.d(TAG, "токен"+UserToken);

                          CheckRegistration();
                      }
                  },10);
              }
          });

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

    //проверка регистрации
    public void CheckRegistration(){

        keyReg="";

        //задержка запроса
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {

                // Проверка регистрации токена
                checkHaveToken();

            }
        },600);


        //чтение в БД с правилом для любых пользователей
        database02 = FirebaseDatabase.getInstance();
        ref02 = database02.getReference("Check")
                .child("CheckUsers")
                .child("Token")
//                .child("Internet")
//                .child("Work");
                .child(UserToken);
        ref02.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                // Чтение
                keyReg=dataSnapshot.getValue(String.class);

                //временно для проверки
                Toast.makeText( Main5Activity.this, "Токен зарегестрирован?  "+keyReg, Toast.LENGTH_SHORT ).show();

                // с этой записью makeText появляется только один раз!!!!! ХОРОШО
                ref02.removeEventListener(this);





            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    //проверка зарегестрированного токена
    public void checkHaveToken(){
        // переход на Активити InternetNot
        if (keyReg==null){

            //переход к авторизации по телефону от firebase
            Intent AuthList = new Intent(this,Main2Activity.class);
            startActivity(AuthList);
        }
        else if(keyReg.isEmpty()) {

            //Если keyReg пусто, значит пропал интернет и переходим на  InternetNot
            Intent aaa = new Intent(this,InternetNot.class);
            startActivity(aaa);
            Toast.makeText( Main5Activity.this, "опять нет интернета  "+keyReg, Toast.LENGTH_SHORT ).show();
        }
        else if (keyReg.equals("Hello")){
            goMainList();}

    }

    //Переход в главное меню заказов
    public void goMainList(){
        Intent mainList = new Intent(this,Choose_direction.class);
        startActivity(mainList);
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
    protected void onStart (){
        super.onStart();
        Log.d(TAG, "onStart");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "onPause");


    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "onResume");

    }
    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG, "onRestart");
    }





}




