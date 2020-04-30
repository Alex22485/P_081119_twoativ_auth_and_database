package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;


import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

    public class MainActivity extends AppCompatActivity {

    private static final String TAG ="MainActivity" ;

    String  key;
    String keyReg;
    String UserToken;

    FirebaseDatabase database01;
    FirebaseDatabase database02;
    DatabaseReference ref01;
    DatabaseReference ref02;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");

        // Получить Токен!!!!
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(MainActivity.this,new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                UserToken = instanceIdResult.getToken();
                Log.d(TAG, "токен"+UserToken);
            }
        });
    }

    @Override
    protected void onStart (){
        super.onStart();
        Log.d(TAG, "onStart");
//Проверка интернета
        cheskInternet();
// Проверка интернета
        //cheskInternet();

//        key="";
//
//        //задержка запроса
//        Handler handler1 = new Handler();
//        handler1.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//               //check();
//            }
//        },5000);
//
//
//        //запись-чтение в БД с правилом для любых пользователей
//        database01 = FirebaseDatabase.getInstance();
//        ref01 = database01.getReference("rooms")
//                .child("5555")
//                .child("videoID");
//        ref01.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                // Запись
//                ref01.setValue("2222");
//                Toast.makeText( MainActivity.this, "Запись для ВСЕХ ", Toast.LENGTH_SHORT ).show();
//
//                // Чтение
//                String s=dataSnapshot.getValue(String.class);
//                Toast.makeText( MainActivity.this, "Чтение для ВСЕХ "+s, Toast.LENGTH_SHORT ).show();
//
//                // с этой записью makeText появляется только один раз!!!!! ХОРОШО
//                ref01.removeEventListener(this);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
//
//        //запись-чтение в БД с правилом только когда зарегестирован
//        database02 = FirebaseDatabase.getInstance();
//        ref02 = database02.getReference("rooms")
//                .child("5555")
//                .child("time");
//        //.child("time");
//        ref02.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                // Запись
//                ref02.setValue("2222");
//                Toast.makeText( MainActivity.this, "Запись только для зарегестрированных ", Toast.LENGTH_SHORT ).show();
//                // Чтение
//                String s=dataSnapshot.getValue(String.class);
//                Toast.makeText( MainActivity.this, "Чтение только для зарегестрированных "+s, Toast.LENGTH_SHORT ).show();
//
//                // Запись работает c правилом. с этой записью makeText появляется только один раз!!!!! ХОРОШО
//                ref02.removeEventListener(this);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });

//        //ПРОВЕРКА ИНТЕРНЕТА читаем данные из БД
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child( "КонтрТочка" )
//                .child( "Проверка" )
//                .child( "Загрузка" )
//                .child( "Успешна" );
//        ref.orderByValue().equalTo( "ДА" ).addListenerForSingleValueEvent( new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snap: dataSnapshot.getChildren()){
//                    key = snap.getKey(); //получить все ключи значения
//                    Toast.makeText( MainActivity.this, "Главное Автивити "+key, Toast.LENGTH_SHORT ).show();
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        } );
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy");

        // 1.1не выдает всплывающее сообщение в другом активити когда нет интернета. Работает в паре с finish() в onPause
        //отключает прослушивание(выполнение запроса из базы данных), при переходе в спящий режим или переходе в другую активити, конкретно когда нет интернета
      //database01.goOffline();

    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "onPause");
        // 1.2не выдает всплывающее сообщение в другом активити когда нет интернета. Работает в паре с  database01.goOffline() в onDestroy
        //finish();
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "onResume");


        // 1.3 Работает в паре с finish() в onPause
        //включает прослушивание(выполнение запроса из базы данных), при переходе в спящий режим или переходе в другую активити, конкретно когда нет интернета
        //database01.goOnline();

    }
    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG, "onRestart");
    }
    @Override
    protected void onStop (){
        super.onStop();
        Log.d(TAG, "onStop");
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
        },4000);


        //чтение из БД с правилом для любых пользователей
        database01 = FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Check")
                .child("Internet")
                .child("Work");
        ref01.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                // Чтение
                key=dataSnapshot.getValue(String.class);

                //временно для проверки
                Toast.makeText( MainActivity.this, "Интернет есть?  "+key, Toast.LENGTH_SHORT ).show();

                // с этой записью makeText появляется только один раз!!!!! ХОРОШО, блин не всегда :(((
                ref01.removeEventListener(this);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void check(){
        // переход на Активити InternetNot
        if (key.isEmpty()){
            Intent aaa = new Intent(this,InternetNot.class);
            startActivity(aaa);
        }
        else if(key.equals("Yes")) {

            //проверка регистрации
            CheckRegistration();
        }
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


        //чтение из БД с правилом для любых пользователей
        database02 = FirebaseDatabase.getInstance();
        ref02 = database02.getReference("Check")
                .child("CheckUsers")
                .child("Token")
                .child(UserToken);
        ref02.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                // Чтение
                keyReg=dataSnapshot.getValue(String.class);

                //временно для проверки
                Toast.makeText( MainActivity.this, "Мой Токен найден?  "+keyReg, Toast.LENGTH_SHORT ).show();

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
            Toast.makeText( MainActivity.this, "опять нет интернета  "+keyReg, Toast.LENGTH_SHORT ).show();
        }
        else if (keyReg.equals("Hello")){
            goMainList();}
    }

    //Переход в главное меню заказов
    public void goMainList(){
        Intent mainList = new Intent(this,Choose_direction.class);
        startActivity(mainList);
    }

    // Блокировка кнопки Back!!!! :)))
    @Override
    public void onBackPressed(){
    }
}
