package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private static final String TAG ="MainActivity" ;

    // токен
    String UserToken;
    // реф Srting проверка интернета при первом чистом запуске приложения
    String firstLoadNotInternt="";
    // реф Srting проверка интернета при 1-ом считываении из БД
    String checkregistrationTimeOut="";
    // реф Srting полученное из БД (cripto)
    String keyReg="";
    // реф Srting проверка интернета при 2-ом считываении из БД
    String checkregistrationTimeOut2="";
    // реф Srting проверка заявок Yes/No
    String proverka="";
    // реф Integer ловушка от многократных переходов на InternetNot activity (такое могло быть при сворачивании приложения и снова открытии, только если код помещен в OnStart)
    Integer onStopref;
    // реф Integer ловушка от многократных переходов на первую активити activity (такое могло быть при сворачивании приложенияи снова открытии, только если код помещен в OnStart)
    Integer onStopref1;
    // время выдержки времени для исключения неперехода на др активити при сварачивании,
    // есть порог 5 секунд меньше которых переход на др активити не сработает при сварачивании)
    // поэтому при сварачивании, в OnStop увеличиваем таймер еще на 5 секунд
    Integer b,c,d,e,f,g,i,h;

    FirebaseDatabase database01;
    FirebaseDatabase database02;
    DatabaseReference ref01;
    DatabaseReference ref02;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");

         //взято призвольное мальнькое время
         c=10;
         e=10;
         f=10;
         i=10;

        // ловушки от многократных переходов на др активити для кода в  OnStart
        //onStopref=1;
        //onStopref1=1;
        // получение токена
        // токен будет если есть интернет
        // токен будет если есть нет интернета но это 2,3,4... вход в приложение (без удаления настроек приложения APP)
        // токен НЕ будет если НЕТ интернета только при Самом первом запуске приложения
        FirebaseMessaging. getInstance (). getToken ()
                .addOnCompleteListener ( new OnCompleteListener< String >() {
                    @Override
                    public void onComplete ( @NonNull Task< String > task ) {
                        if (! task . isSuccessful ()) {
                            // ошибка подключения нет интернета при самом первом запуске приложения
                            //нет интернета при запуске приложения
                            Handler handler1 = new Handler();
                            handler1.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // таймер-сворачивания
                                    timePlus();
                                }
                            },2000);
                            //return ;
                        }
                        else{
                            // получаем токен
                            UserToken = task . getResult ();
                            Toast. makeText ( MainActivity . this , "токен получен"+UserToken , Toast . LENGTH_SHORT ). show ();
                            Log.d(TAG, "UserToken"+UserToken);
                            //задержка чтобы успеть прочитать заставку
                            Handler handler1 = new Handler();
                            handler1.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d(TAG, "CheckRegistration");
                                    CheckRegistration();
                                }
                            },2000);
                        }
                    }
                });
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
    @Override
    protected void onStop (){
        super.onStop();
        Log.d(TAG, "onStop");
        // таймер-сворачивания для перехода inetNotWhenGoCheckRegistration()
        c=5000;
        // таймер-сворачивания для перехода inetNotWhenGoCheckRegistration2()
        e=5000;
        // таймер-сворачивания для перехода на лист заявок GoMain6Activity();
        f=5000;
        // таймер-сворачивания для перехода на лист формирования заявок GOMainUserNewOne3()
        i=5000;
        Log.d(TAG, "onStop c="+c);
        Log.d(TAG, "onStop e="+e);
        Log.d(TAG, "onStop f="+f);
        Log.d(TAG, "onStop i="+i);
    }
    //проверка регистрации
    public void CheckRegistration(){
        Log.d(TAG, "запрос регистрации начат");

        //таймер ЗАПРОСА ИНТЕРНЕТА-1
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Завершен таймер ЗАПРОСА ИНТЕРНЕТА-1
                checkregistrationTimeOut="Out";
                // метод увеличения времени для удачного перехода на др активити при сварачивании
                // если сворачивания не было то время почти не увеличивается
                timePlus();
            }
        },35000);
        //чтение из БД данных об авторизации с "правилом для любых пользователей"
        database02 = FirebaseDatabase.getInstance();
        ref02 = database02.getReference("Check")
                .child("CheckUsers")
                .child("Token")
                .child(UserToken);
        ref02.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // "" обязательно иначе ошибка
                keyReg=""+dataSnapshot.getValue(String.class);
                Log.d(TAG, "keyReg"+keyReg);
                // с этой записью makeText появляется только один раз!!!!!
                ref02.removeEventListener(this);
                //Проверка авторизации по полученным данным
                checkHaveToken();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
        //1. Проверка интернета во время считывания токена
        //2.  Проверка интернета при проверке авторизации авторизации
        public void inetNotWhenGoCheckRegistration (){
        if (!keyReg.isEmpty()){
            Log.d(TAG, "таймер проверки интернета вышел, но данные считаны");
        }
        else {
            Log.d(TAG, "onStopref ДО"+onStopref);
            // ловушка для двойных переходов в следующее активити при сваращивании приложения в процессе считывания данных
            if (onStopref==1){
                //пропал интернет во время проверки наличия регистрации
                Log.d(TAG, "Интернета пропал при проверке регистрации");
                Intent aaa = new Intent(this,InternetNot.class);
                startActivity(aaa);
                // ловушка для двойных переходов в следующее активити при сварачивании приложения в процессе считывания данных для кода в OnStart
                onStopref=onStopref+1;
                Log.d(TAG, "onStopref ПОСЛЕ"+onStopref);
            }
        }
    }
    //Проверка авторизации по полученным данным
    public void checkHaveToken(){
        if (checkregistrationTimeOut.equals("Out")){
            Log.d(TAG, "данные регистрации считаны, но таймер интернета-1 вышел");
        }
        else{
            // null если авторизации не было
            if (keyReg.equals("null")){
                Log.d(TAG, "onStopref1 ДО"+onStopref1);
                // ловушка от многократных переходов при сворачивании приложения
                if (onStopref1==1){
                    Log.d(TAG, "Переход на первый лист");
                    Intent MainUserNewOne = new Intent(this,MainUserNewOne.class);
                    startActivity(MainUserNewOne);
                    // ловушка от многократных переходов при сворачивании приложения
                    // работает только если код помещен в OnStart
                    onStopref1++;
                    Log.d(TAG, "onStopref1 ПОСЛЕ"+onStopref1);
                }
            }
            // авторизация была
            else if (!keyReg.isEmpty()){
                // проверка наличия заявок
                checkOder();
            }
        }
    }
    // проверка наличия заявок
    public void checkOder(){
        //таймер ЗАПРОСА ИНТЕРНЕТА-2
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkregistrationTimeOut2="Out";
                timePlus2();
            }
            },35000);
        Log.d(TAG, "Чтение Yes/No из БД");
        //проверка наличия заявок
        database01 = FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Пользователи")
                .child("Personal")
                .child(keyReg)
                .child("Proverka")
                .child("Oder")
                .child("Заявка");
        ref01.addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // проверка Yes/No старых заявок
                proverka=dataSnapshot.getValue(String.class);
                Log.d(TAG, "Старая заявка Yes/No"+proverka);
                // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД БД ЗАЯВКИ...-...-...-"CheckStopOder"...
                ref01.removeEventListener(this);
                // выбор на какую страницу перейти из этой
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // проверка наличия заявок
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
            Log.d(TAG, "ДО onStopref"+onStopref);
            //ловушка
            if (onStopref==1){
                //пропал интернет во время проверки наличия заявок
                Log.d(TAG, "Интернет пропал при поиске наличия заявок");
                Intent aaa = new Intent(this,InternetNot.class);
                startActivity(aaa);
                // ловушка для двойных переходов в следующее активити при сварачивании приложения в процессе считывания данных
                onStopref=onStopref+1;
                Log.d(TAG, "После onStopref"+onStopref);
            }
        }
    }
    // проверка наличия заявок
    public void checkWordProverka(){
        // при пропаже интернета и переходе на другое активити "УПС проверьте связь"
        // находясь в новой активити если появится интернет все равно считаются данные String proverka и
        // запустится данный метод но дальше ничего не пойдет именно из за этой контрукции НИЖЕ
        if (checkregistrationTimeOut2.equals("Out")){
            Log.d(TAG, "данные Yes/No считаны, но таймер интернета-2 вышел");
        }
        else {
            Log.d(TAG, "ДО onStopref1"+onStopref1);
            // ловушка от многократных переходов при сворачивании приложения
            if (onStopref1==1){
                if(proverka.equals("Yes")){
                    Log.d(TAG, "переход лист заявок");
                    //таймер-сворачивания
                    timePlus3();
                    // ловушка от многократных переходов при сворачивании приложения
                    //onStopref1=onStopref1+1;
                        Log.d(TAG, "После"+onStopref1);
                    }
                    if(proverka.equals("No")){
                        Log.d(TAG, "Заявок нет");
                        //таймер-сворачивания
                        timePlus4();
                        // ловушка от многократных переходов при сворачивании приложения
                        //onStopref1=onStopref1+1;
                        Log.d(TAG, "После"+onStopref1);
                    }
            }
        }
    }

    // ТАЙМЕРЫ СВАРАЧИВАНИЯ

    // 1.когда токен не считался при первом чистом запуске приложения
    // 2. когда нет интерента при повторных запусков приложения)
    public void timePlus(){
        b=c+10;
        Log.d(TAG, "timePlus b="+b);
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // нет интернета
                inetNotWhenGoCheckRegistration();
            }
        },b);
    }
    // 1.когда токен не считался при первом чистом запуске приложения
    // 2. когда нет интерента при повторных запусков приложения)
    public void timePlus2(){
        d=e+10;
        Log.d(TAG, "timePlus2 d="+d);
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // нет интернета
                inetNotWhenGoCheckRegistration2();
            }
        },d);
    }
    // метод прибавляет задержку времени таймера
    // при сворачивании приложения для перехода на лист заявок Main6Activity
    public void timePlus3(){
        g=f+10;
        Log.d(TAG, "timePlus3 g="+g);
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // переход на лист заявок
                GoMain6Activity();
            }
        },g);
    }
    // метод прибавляет задержку времени таймера
    // при сворачивании приложения для перехода на лист формирования заявок  GOMainUserNewOne3()
    public void timePlus4(){
        h=i+10;
        Log.d(TAG, "timePlus4 i="+i);
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // переход на лист формирования заявок
                GOMainUserNewOne3();
            }
        },h);
    }
    // ПЕРЕХОДЫ НА ДР АКТИВИТИ

    // переход на лист заявок
    public void GoMain6Activity(){
        Intent MainTOMain6= new Intent(this,Main6Activity.class);
        MainTOMain6.putExtra("phoneNew",keyReg);
        startActivity(MainTOMain6);
    }
    // переход на лист формирования заявок
    public void GOMainUserNewOne3(){
        Intent MainActivityToMainUserNewOne3= new Intent(this,MainUserNewOne3.class);
        // регистрация есть заявок нет отправляем Hello
        MainActivityToMainUserNewOne3.putExtra("phoneNew",keyReg);
        startActivity(MainActivityToMainUserNewOne3);
    }
    // Блокировка кнопки Back!!!! :)))
    @Override
    public void onBackPressed(){
    }
}
