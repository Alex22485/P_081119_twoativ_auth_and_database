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
import android.view.WindowManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class Driver1 extends AppCompatActivity {

    // главная страница заставка
    // Alert перезапуск или закрытие приложения при пропаже интернета:
    // 1. при считывании токена(в самый первый запуск приложения)
    // 2. при считывании данных авторизации
    // 3. при при проверке YES-NO
    // ЛОАВУШКА не Перехода
    // 1. на лист без авторизации
    // 2. на лист формирования заявок
    // 3. на лист статуса заявки
    // применен таймер сварачивания для корректного перехода на любую другую активити при свернутом приложении
    // реализовано вермя сессии
    // блокировка спящего режима экрана

    private static final String TAG ="Driver1" ;

    // токен
    String UserToken;
    // реф Srting проверка интернета при 1-ом считываении из БД
    String checkregistrationTimeOut="";
    // реф Srting полученное из БД (cripto)
    String keyReg="";
    // реф Srting проверка интернета при 2-ом считываении из БД
    String checkregistrationTimeOut2="";
    // реф Srting проверка заявок Yes/No
    String proverka="";

    FirebaseDatabase database01;
    FirebaseDatabase database02;
    DatabaseReference ref01;
    DatabaseReference ref02;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver1);
        Log.d(TAG, "onCreate");

        // блокировка спящего режима экрана
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // отмена блокировки спящего режима экрана
        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

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
                                    // 1. не загрузился токен (такое будет если при первом запуске нет интернета)
                                    inetNotWhenGoCheckRegistration ();
                                }
                            },2000);
                        }
                        else{
                            // получаем токен
                            UserToken = task . getResult ();
                            //Toast. makeText ( MainActivity . this , "токен получен"+UserToken , Toast . LENGTH_SHORT ). show ();
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
    }

    //проверка авторизации
    public void CheckRegistration(){
        Log.d(TAG, "Start запрос авторизации ");

        //таймер ЗАПРОСА ИНТЕРНЕТА-1
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Завершен таймер ЗАПРОСА ИНТЕРНЕТА-1
                checkregistrationTimeOut="Out";
                //  проверка интернета при считывании данных авторизации из БД
                inetNotWhenGoCheckRegistration ();
            }
        },35000);
        //чтение из БД данных об авторизации с "правилом для любых пользователей"
        database02 = FirebaseDatabase.getInstance();
        ref02 = database02.getReference("Check")
                .child("CheckDrivers")
                .child("Token")
                .child(UserToken);
        ref02.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // "" обязательно иначе ошибка
                keyReg=""+dataSnapshot.getValue(String.class);
                Log.d(TAG, "keyReg"+keyReg);
                // с этой записью keyReg появляется только один раз!!!!!
                ref02.removeEventListener(this);
                //Проверка авторизации по полученным данным
                checkHaveToken();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    //Проверка авторизации по полученным данным
    public void checkHaveToken(){
        if (checkregistrationTimeOut.equals("Out")){
            Log.d(TAG, "данные для авторизации считаны, но таймер интернета-1 вышел");
        }
        else{
            // null если авторизации не было
            if (keyReg.equals("null")){
                // Переход на лист АВТОРИЗАЦИИ
                GoDriverAuth();
            }
            // авторизация была
            else if (!keyReg.isEmpty()){
                // Проверка Записи личных данных
                checkOder();
                          }
        }
    }
    // Проверка Записи личных данных
    public void checkOder(){
        //таймер ЗАПРОСА ИНТЕРНЕТА-2
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkregistrationTimeOut2="Out";
                // когда нет интерента при проверке наличия заявок)
                inetNotWhenGoCheckRegistration2();
            }
        },35000);
        Log.d(TAG, "Чтение Yes/No из БД");
        //проверка наличия заявок
        database01 = FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Водители")
                .child("Personal")
                .child(keyReg)
                .child("Proverka")
                .child("Oder")
                .child("Proverka");
        ref01.addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // проверка Yes/No старых заявок
                proverka=""+dataSnapshot.getValue(String.class);
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
    // // Проверка Записи личных данных продолжение
    public void checkWordProverka(){
        // при пропаже интернета и переходе на другое активити "УПС проверьте связь"
        // находясь в новой активити если появится интернет все равно считаются данные String proverka и
        // запустится данный метод но дальше ничего не пойдет именно из-за checkregistrationTimeOut2=OUT
        if (checkregistrationTimeOut2.equals("Out")){
            Log.d(TAG, "данные Yes/No считаны, но таймер интернета-2 вышел");
        }
        else {
            // Данные записаны
            if(proverka.equals("Yes")){
                // ПЕРЕХОД на ЛИСТ ЗАЯВОК
                GoOder();
            }
            // Заявки нет
            if(proverka.equals("null")){
                // переход на лист заполнения данных о пользователе
                GoWritePrivateDate();
            }
        }
    }

    // ALERT DIALOG
    // Alert нет Интернета Перезапуск активити или закрыть приложение
    public void showAlertDialog4(){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Driver1.this);
        mAlertDialog.setTitle("Слабый сигнал интернета!");
        mAlertDialog.setMessage("Нажмите ОК, чтобы поробовать еще раз.");
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Перезапуск активити
                        Intent mIntent=getIntent();
                        finish();
                        startActivity(mIntent);
                    }
                });
        mAlertDialog
                .setNegativeButton("Закрыть приложение", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // закрытие приложения реальное
                        finishAffinity();
                        System.exit(0);
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }
    // Alert ловушка неперехода на на лист DriverAuth
    public void AlertGoDriverAuth(){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Driver1.this);
        mAlertDialog.setMessage("Нажмите ОК, для продолжения.");
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // переход на лист без авторизации MainUserNewOne
                        GoDriverAuth();
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }
    // Alert ловушка неперехода на лист Заявок GoOder
    public void AlertGoOder() {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Driver1.this);
        mAlertDialog.setMessage("Нажмите ОК, для продолжения.");
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // переход на лист без авторизации Zakaz1
                        GoOder();
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }
    // Alert ловушка неперехода на Zakaz1
    public void AlertDriver2() {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Driver1.this);
        mAlertDialog.setMessage("Нажмите ОК, для продолжения.");
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // переход на лист без авторизации Driver2
                        GoWritePrivateDate();
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }


// МЕТОДЫ
    // Проверка
    // 1. не загрузился токен (такое будет если при первом запуске нет интернета)
    // 2. если пропал интернет при считывании данных авторизации из БД
    public void inetNotWhenGoCheckRegistration (){
        if (!keyReg.isEmpty()){
            Log.d(TAG, "таймер проверки интернета вышел, но данные авторизации считаны");
        }
        else {
            // Alert нет Интернета Перезапуск активити или закрыть приложение
            showAlertDialog4();
        }
    }
    // Проверка интернета при поиске наличия заявок
    public void inetNotWhenGoCheckRegistration2(){
        if(proverka.equals("Yes")||proverka.equals("No")){
            Log.d(TAG, "таймер запроса интернета-2 остановлен");}
        else {
            //пропал интернет во время проверки наличия заявок
            Log.d(TAG, "Интернет пропал при поиске наличия заявок");
            // Alert нет Интернета Перезапуск активити или закрыть приложение
            showAlertDialog4();
        }
    }

// ПЕРЕХОДЫ НА ДР АКТИВИТИ
    // НА ЛИСТ АВТОРИЗАЦИИ
    public void GoDriverAuth(){
        Log.d(TAG, "Переход на лист без авторизации");
        Intent DriverAuth = new Intent(this,DriverAuth.class);
        startActivity(DriverAuth);

        // Alert ловушка неперехода на MainUserNewOne
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Alert ловушка неперехода на лист DriverAuth
                AlertGoDriverAuth();
            }
        },1000);
    }
    // НА ЛИСТ ЗАПОЛНЕНИЯ ДАННЫХ о пользователе
    public void GoWritePrivateDate(){
        Intent Driver2= new Intent(this,Driver2.class);
        Driver2.putExtra("phoneNew",keyReg);
        Driver2.putExtra("UserToken",UserToken);
        startActivity(Driver2);

        // Alert ловушка неперехода на Driver2
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Alert ловушка неперехода на Driver2
                AlertDriver2();
            }
        },1000);
    }
    // НА ЛИСТ ЗАЯВОК GoOder
    public void GoOder(){
        Intent GoDriversApp_1= new Intent(this,DriversApp_1.class);
        GoDriversApp_1.putExtra("phoneNew",keyReg);
        startActivity(GoDriversApp_1);

        // Alert ловушка неперехода на DriversApp_1
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Alert ловушка неперехода на DriversApp_1
                AlertGoOder();
            }
        },1000);
    }

    // Блокировка кнопки Back!!!! :)))
    @Override
    public void onBackPressed(){
    }
}