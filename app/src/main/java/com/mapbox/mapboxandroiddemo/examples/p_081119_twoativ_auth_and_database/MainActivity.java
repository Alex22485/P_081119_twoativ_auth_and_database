package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

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

    public class MainActivity extends AppCompatActivity {

    private static final String TAG ="MainActivity" ;

    String phoneNew;

    String keyReg;
    String UserToken;

    String registration;
    String checkregistrationTimeOut;
    String checkregistrationTimeOut2;

    FirebaseDatabase database01;
    FirebaseDatabase database02;
    DatabaseReference ref01;
    DatabaseReference ref02;

    FirebaseDatabase databaseSecret;
    DatabaseReference refSecret;

    FirebaseAuth mAuth;
    String userPhone;
    String proverka;

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
                Log.d(TAG, "токен: "+UserToken);

            }
        });
    }

    @Override
    protected void onStart (){
        super.onStart();
        Log.d(TAG, "onStart");

        //СТАРТ Проверка интернета+регистрации
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                CheckRegistration();
            }
        },700);

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

    //проверка регистрации
    public void CheckRegistration(){

        Log.d(TAG, "запрос регистрации начат");

        keyReg="";
        registration="";
        checkregistrationTimeOut="";

        //таймер ЗАПРОСА ИНТЕРНЕТА-1
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {

                // Завершен таймер ЗАПРОСА ИНТЕРНЕТА-1
                checkregistrationTimeOut="Out";
                inetNotWhenGoCheckRegistration();
            }
        },30000);

        //чтение из БД с правилом для любых пользователей
        database02 = FirebaseDatabase.getInstance();
        ref02 = database02.getReference("Check")
                .child("CheckUsers")
                .child("Token")
                .child(UserToken);
        ref02.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                keyReg=dataSnapshot.getValue(String.class);
                registration=""+keyReg; /* так как может получить null*/
                Log.d(TAG, "keyReg"+keyReg);
                Log.d(TAG, "registration"+keyReg);

                // с этой записью makeText появляется только один раз!!!!! ХОРОШО
                ref02.removeEventListener(this);

                //Проверка токена
                checkHaveToken();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

        //Проверка интернета во время проверки регистрации
        public void inetNotWhenGoCheckRegistration (){

            if(registration.equals("Hello")||keyReg==null){
                Log.d(TAG, "таймер запроса интернета-1 остановлен");/*специально пусто*/}

            else {
                //пропал интернет во время проверки наличия регистрации
                Log.d(TAG, "Интернета пропал при проверке регистрации");
                Intent aaa = new Intent(this,InternetNot.class);
                startActivity(aaa);
            }
        }

    //Проверка токена
    public void checkHaveToken(){

        if (checkregistrationTimeOut.equals("Out")){
            Log.d(TAG, "данные регистрации считаны, но таймер интернета-1 вышел");
        }

        else{
        if (keyReg==null){

            //15/06/20 Добавлено
            Log.d(TAG, "Переход на первый лист");
            Intent MainUserNewOne = new Intent(this,MainUserNewOne.class);
            startActivity(MainUserNewOne);
        }
        else if (keyReg.equals("Hello")){
            // 25.08.2020 УБРАЛ
            //полуаем phone пользователя
//            mAuth= FirebaseAuth.getInstance(  );
//            FirebaseUser user=mAuth.getCurrentUser();
//            userPhone = user.getPhoneNumber();
//            Log.d(TAG, "получен телефон"+userPhone);

            //Старт Проверка наличия заявок
            Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    // 25.08.2020 УБРАЛ
//                    //start шифрования
//                   cryptography();

                    // 25.08.2020 Поиск наличия заявок
                    checkOder();

                }
            },500);
        }
    }
    }

        // поиск старых заявок
        public void checkOder(){
            checkregistrationTimeOut2="";
            proverka="";

            //таймер ЗАПРОСА ИНТЕРНЕТА-2
            Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {

                    // Завершен таймер ЗАПРОСА ИНТЕРНЕТА-2
                    checkregistrationTimeOut2="Out";
                    inetNotWhenGoCheckRegistration2();
                }
            },30000);

            Log.d(TAG, "Чтение Yes/No из БД");
            //Чтение Yes/No из БД
            database01 = FirebaseDatabase.getInstance();
            ref01 = database01.getReference("Пользователи")
                    .child("Personal")
                    .child(UserToken)
                    .child("Proverka")
                    .child("Oder")
                    .child("Заявка");
            ref01.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    proverka=""+dataSnapshot.getValue(String.class);
                    Log.d(TAG, "Наличие заявок Yes/No"+proverka);

                    // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД БД ЗАЯВКИ...-...-...-"CheckStopOder"...
                    ref01.removeEventListener(this);

                    Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            checkWordProverka();
                        }
                    },200);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }

        //Проверка интернета во время поиска наличия заявок
        public void inetNotWhenGoCheckRegistration2(){

            if(proverka.equals("Yes")||proverka.equals("No")){
                Log.d(TAG, "таймер запроса интернета-2 остановлен");}

            else {
                //пропал интернет во время проверки наличия регистрации
                Log.d(TAG, "Интернет пропал при поиске наличия заявок");
                Intent aaa = new Intent(this,InternetNot.class);
                startActivity(aaa);
            }
        }

        public void checkWordProverka(){

            if (checkregistrationTimeOut2.equals("Out")){
                Log.d(TAG, "данные Yes/No считаны, но таймер интернета-2 вышел");
            }

        if(proverka.equals("Yes")){
            Log.d(TAG, "переход на лист заявок");
            Intent MainTOMain6= new Intent(this,Main6Activity.class);
            MainTOMain6.putExtra("phoneNew",phoneNew);
            startActivity(MainTOMain6);
        }
        if(proverka.equals("No")){
            Log.d(TAG, "Заявок нет");
            Intent MainActivityToMainUserNewOne3= new Intent(this,MainUserNewOne3.class);
            // регистрация есть заявок нет отправляем Hello
            MainActivityToMainUserNewOne3.putExtra("registration",registration);
            startActivity(MainActivityToMainUserNewOne3);
        }
    }


    // Блокировка кнопки Back!!!! :)))
    @Override
    public void onBackPressed(){
    }
}
