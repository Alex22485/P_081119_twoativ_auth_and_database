package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.concurrent.TimeUnit;


public class Proba extends AppCompatActivity {

    // КОД ПРОТЕСТИРОВАН 19.12.2020

    // 1. Авторизация по номеру телефона
    // а.если в момент отправки номера телефона свернуть приложение процесс Auth может (почти всегда, но бывает и нормально) не завершится
    // поэтому добавил время сессии 100 секунд по истечении которых уведомление "Приложение будт закрыто"
    // б.если процес Auth успешен время сессии остановится когда появится Toast "ожидайте смс"
    // в.если пропал интернет при отправке номера телефона появляется "Ошибка связи, проверьте интернет"
    // и при повтнорной отправке номера телефона(при рабочем интернете) свернув приложение и открыв снова (при неудачной auth) уведомление "время сессии вышло" все равно появится но только один раз(блокировка однократности)

    // 2. после успешной Auth запускается Cripto (js)
    // a. c таймером сворачивания, который обнуляется (с 5000 до 10) при запуске длительного метода StartCripto(). т.к есть  всегда первый-нормальный переход в onStop при Web переходе
    // б. реализована ловушка неперехода в Zakaz3Finish (при сворачивании приложения до 5 секунд ) через Alert5() (он принудительно разрешает переход на др активити после сварачивания)


    private static final String TAG ="Proba";
    // ввод телефона и OTP кода
    EditText edtPhone, edtCode;
    TextView tVAuth,tVPhone,tVProgressB,tVCode;
    // кнопки получить и отправить OTP код
    Button getCode,sendCode,tryAgain;
    // визуализация процесса отправки телефона
    ProgressBar progressB;
    // визуализация процесса отправки кода смс
    ProgressBar progressB2;
    FirebaseAuth mAuth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    String code;
    ConstraintLayout constraintLayout;

    // для экспорта из Zakaz3finish
    String Calend, RefplaneCity, RefMap, RefPoint, time,timeOfPoint,dateOfPoint;
    // Экспорт из Zakaz3finish реф слово чтобы определить какой маршрут "в Аэропорт" или "из Аэропорта" (для правильного отображения активити Zakaz3finish)
    String RefInFromAirport="";
    // Экспорт из Zakaz1 стоимость проезда
    String fare;
    // токен
    String newToken;
    //для шифрования
    String IneternetYES="";
    String phoneNew="";
    FirebaseDatabase databaseSecret;
    DatabaseReference refSecret;
    FirebaseDatabase database01;
    DatabaseReference ref01;
    //для
    String checkInternet="";
    String writeData="";


    // для остановки метода времени сессии timeSessionEnd() если смс с кодом уже отправлено
    String refStopTimeSession="";
    // реф слово чтобы Toast "ожидайте смс" в onCodeSent не появилось при истечении времени сессии
    String refSmsNoShow="";
    // для проверки однократности появления уведомления "время сессии вышло"
    Integer refShowOnlyOne=1;
    // ref слово для выбора какой использовать код в кнопке "Попробовать еще раз" при пропаже интернета в процессе криптографии и записи токена в личную комнату
    String refBtnTryAgain="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proba);

        // блокировка спящего режима экрана
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // отмена блокировки спящего режима экрана
        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mAuth=FirebaseAuth.getInstance();
        constraintLayout=findViewById(R.id.constraintLayout);
        tVAuth=findViewById(R.id.tVAuth);
        tVPhone=findViewById(R.id.tVPhone);
        tVProgressB=findViewById(R.id.tVProgressB);
        progressB=findViewById(R.id.progressB);
        progressB2=findViewById(R.id.progressB2);
        tVCode=findViewById(R.id.tVCode);
        edtPhone=findViewById(R.id.edtPhone);
        edtCode=findViewById(R.id.edtCode);
        getCode=findViewById(R.id.getCode);
        sendCode=findViewById(R.id.sendCode);
        tryAgain=findViewById(R.id.tryAgain);

        //данные из Zakaz3finish
        Intent Zakaz3finishToProba=getIntent();

        // дата поездки
        Calend=Zakaz3finishToProba.getStringExtra("Calend");
        // рейс самолета
        RefplaneCity=Zakaz3finishToProba.getStringExtra("RefplaneCity");
        // маршрут такси
        RefMap=Zakaz3finishToProba.getStringExtra("RefMap");
        // пункт сбора
        RefPoint=Zakaz3finishToProba.getStringExtra("RefPoint");
        // время вылета/прилета/номер рейса для чартера
        time=Zakaz3finishToProba.getStringExtra("time");
        // Время точки сбора (для рейсов из Аэропорта)
        timeOfPoint=Zakaz3finishToProba.getStringExtra("timeOfPoint");
        // дата точки сбора (для рейсов из Аэропорта)
        dateOfPoint=Zakaz3finishToProba.getStringExtra("dateOfPoint");
        // Экспорт из Zakaz3finish реф слово чтобы определить какой маршрут "в Аэропорт" или "из Аэропорта" (для правильного отображения активити Zakaz3finish)
        RefInFromAirport=Zakaz3finishToProba.getStringExtra("RefInFromAirport");
        // стоимость проезда
        fare=Zakaz3finishToProba.getStringExtra("fare");

        Log.d(TAG, "экспорт из Zakaz3Finish дата вылета-прилета: "+Calend);
        Log.d(TAG, "экспорт из Zakaz3Finish дата точки сбора: "+dateOfPoint);
        Log.d(TAG, "экспорт из Zakaz3Finish время вылета-прилета: "+time);
        Log.d(TAG, "экспорт из Zakaz3Finish время точки сбора: "+timeOfPoint);
        Log.d(TAG, "экспорт из Zakaz3Finish самолет: "+RefplaneCity);
        Log.d(TAG, "экспорт из Zakaz3Finish маршрут: "+RefMap);
        Log.d(TAG, "экспорт из Zakaz3Finish точка сбора: "+RefPoint);
        Log.d(TAG, "экспорт из Zakaz3Finish тип Маршрута В(из) Аэропорта: "+RefInFromAirport);
        Log.d(TAG, "экспорт из Zakaz3Finish стоимость проезда: "+fare);

        // получить токен для
        FirebaseMessaging. getInstance (). getToken ()
                . addOnCompleteListener ( new OnCompleteListener< String >() {
                    @Override
                    public void onComplete ( @NonNull Task< String > task ) {
                        if (! task . isSuccessful ()) {
                            Log. w ( TAG , "Fetching FCM registration token failed" , task . getException ());
                            return ;
                        }
                        // Get new FCM registration token
                        newToken = task . getResult ();
                        // Log and toast
                        Log . d ( TAG , ""+newToken );
                        //Toast. makeText ( Proba . this , newToken , Toast . LENGTH_SHORT ). show ();
                    }
                });

        // видимость при старте страницы
        StyleStart();

        //callback для Auth
        callbacks= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            }
            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                //ошибка связи прверьте интернет
                AlertNotInternet();
                // для остановки метода времени сессии timeSessionEnd() если интернет исчез
                refStopTimeSession="StopTimeSession";
            }
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                // если проверочное слово не равно "Yes" значит время сессии (время ошибки при сворачивании) еще не вышло и код работает дальше
                if (!refSmsNoShow.equals("Yes")){

                    Toast.makeText(Proba.this,"ожидайте sms c кодом подтверждения",Toast.LENGTH_LONG).show();

                    // code это полученный токен
                    code = s;
                    // видимость ввод проверочный код
                    StylSentOPT();

                    // для остановки метода времени сессии timeSessionEnd() если смс с кодом уже отправлено
                    refStopTimeSession="StopTimeSession";
                }
            }
        };
    }
    @Override
    protected void onStop (){
        super.onStop();
        Log.d(TAG, "onStop");
    }

// ВИЗУАЛИЗАЦИИ
    // видимость при старте страницы
    public void StyleStart(){
        tVPhone.setVisibility(View.VISIBLE);
        edtPhone.setVisibility(View.VISIBLE);
        getCode.setVisibility(View.VISIBLE);
        tVProgressB.setVisibility(View.INVISIBLE);
        progressB.setVisibility(View.INVISIBLE);
        progressB2.setVisibility(View.INVISIBLE);
        tVCode.setVisibility(View.INVISIBLE);
        edtCode.setVisibility(View.INVISIBLE);
        sendCode.setVisibility(View.INVISIBLE);
        tryAgain.setVisibility(View.INVISIBLE);
    }
    // видимость процесс регистрации
    public void StyleProgressAuth(){
        tVPhone.setVisibility(View.INVISIBLE);
        edtPhone.setVisibility(View.INVISIBLE);
        getCode.setVisibility(View.INVISIBLE);
        tVProgressB.setVisibility(View.VISIBLE);
        progressB.setVisibility(View.VISIBLE);
    }
    // видимость ввод проверочный код
    public void StylSentOPT(){
        //ТАЙМ-АУТ исчезновения StileProgress
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                tVProgressB.setVisibility(View.INVISIBLE);
                progressB.setVisibility(View.INVISIBLE);
                tVCode.setVisibility(View.VISIBLE);
                edtCode.setVisibility(View.VISIBLE);
                sendCode.setVisibility(View.VISIBLE);

                // перепривязка текста
                ConstraintSet set=new ConstraintSet();
                // считываем параметры constraintLayout
                set.clone(constraintLayout);
                // очистка привязки верхней, хотя это делать не нужно и так работает
                //set.clear(R.id.tVCode,ConstraintSet.TOP);
                // привязываем верхушку tVCode  к нижней границе tVAuth,  можно сделать отступ от tVAuth указав значение, н-р 20
                set.connect(R.id.tVCode,ConstraintSet.TOP,R.id.tVAuth,ConstraintSet.BOTTOM,50);
                //приминение нужных параметров
                set.applyTo(constraintLayout);
            }
        },5000);
    }
    // видимость процесса криптографии
    public void StyleStartCripto(){
        tVAuth.setText("Авторизация успешна");
        tVCode.setVisibility(View.INVISIBLE);
        edtCode.setVisibility(View.INVISIBLE);
        sendCode.setVisibility(View.INVISIBLE);
        tryAgain.setVisibility(View.INVISIBLE);
        tVProgressB.setText("Построение базы данных");
        tVProgressB.setVisibility(View.VISIBLE);
        progressB.setVisibility(View.VISIBLE);
        progressB2.setVisibility(View.INVISIBLE);
    }

// МЕТОДЫ
    // старт криптографии
    public void StartCripto(){
        Log.d(TAG, "Старт шифрования");

        //проверка интернета
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Завершен ТАЙМ-АУТ проверка интернета
                IneternetYES="Out";
                // проверка интернета старте криптографии
                IneternetYesNo();
            }
        },50000);

        //запись phone to БД secret
        databaseSecret = FirebaseDatabase.getInstance();
        refSecret = databaseSecret.getReference("Пользователи")
                .child("Cipher")
                .child(edtPhone.getText().toString());
        refSecret.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                refSecret.child("phone").setValue(edtPhone.getText().toString());
                Log.d(TAG, "Телефон для шифрования записан");
                //получаем СС номер из БД
                QwerySecret();
                // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД БД ЗАЯВКИ...-...-...-"CheckStopOder"...
                refSecret.removeEventListener(this);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        }
        );
    }
    //получаем СС номер из БД
    public void QwerySecret(){
        Log.d(TAG, "Получаем секретный номер");
        final Query secret= FirebaseDatabase.getInstance().getReference("Пользователи")
                .child("Cipher")
                .child(edtPhone.getText().toString())
                .child("secretNumber")
                .orderByChild("number");
        secret.addChildEventListener( new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String number=dataSnapshot.child( "numberCrypt" ).getValue(String.class);
                Log.d(TAG, "Секретный номер"+number);
                phoneNew=number;
                // Проверяем закончилось ли время опроса интернета
                checkInternetYesNo();
                //Останавливаем прослушивание, чтобы в приложении у другого пользователя не появлялась информация когда другой пользоваьель регистрирует заявку
                secret.removeEventListener(this);
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
    // проверка интернета при старте криптографии
    public void IneternetYesNo(){
        if (!phoneNew.isEmpty()){
            Log.d(TAG, "время проверки интернета вышло, но номер СС получен");
        }
        else{
            Log.d(TAG, "Время проверки вышло, not internet при старте криптографии");
            // нет интернета при работе криптографии
            showAlertDialog4();
        }
    }
    // проверка интернета при чтении СС кода из БД
    public void checkInternetYesNo(){
        if(IneternetYES.equals("Out")){
            Log.d(TAG, "СС номер получен, но время проверки интернета вышло");
        }
        else if(!phoneNew.isEmpty()){
            Log.d(TAG, "CC получен, запись в личную комнату данных о пользователе");
            // запись в личную комнату токена для дальнейшего формирования данных о пользователе
            WritePrivatTokentoBD();
        }
    }
    // запись в личную комнату токена для дальнейшего формирования данных о пользователе
    public void WritePrivatTokentoBD(){
        Log.d(TAG, "Start записи токена");

        //ТАЙМ-АУТ ЗАПРОСА ИНТЕРНЕТА
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Завершен ТАЙМ-АУТ ЗАПРОСА ИНТЕРНЕТА при записи в личную комнату токена для дальнейшего формирования данных о пользователе
                checkInternet="Out";
                Log.d(TAG, "Записан checkInternet=Out");
                // проверка интернета при записи в личную комнату токена для дальнейшего формирования данных о пользователе
                inetNotWhenGoCheckRegistration();
            }
            },35000);
        //030320 Запись токена в БД
        database01 = FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Пользователи")
                .child("Personal")
                .child(phoneNew)
                .child("Private");
        ref01.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ref01.child("token").setValue(newToken);
                // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД БД ЗАЯВКИ...-...-...-"CheckStopOder"...
                ref01.removeEventListener(this);
                writeData="Yes";
                //  проверка не Вышло ли время интернета для перехода в Zakaz3Finish
                getMainList();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        }
        );
    }
    //  проверка не Вышло ли время интернета для перехода в Zakaz3Finish
    public void getMainList(){
        Log.d(TAG, "вход В МЕТОД getMainList");
        if(checkInternet.equals("Out")){
            Log.d(TAG, "getMainList остановлен");
            //return нужен чтобы при возобноблении интернета автоматически не переходило на лист с заявками
            return;
        }
        // переход на Zakaz3finish для регистрации заявки
        GoToZakaz3Finish();

    }
    // проверка интернета при записи токена в БД
    public void inetNotWhenGoCheckRegistration(){
        if(writeData.equals("Yes")){
            Log.d(TAG, "таймер остановлен");
        }
        else {
            //Интернет пропал при записи данных пользователя в личную комнату
            showAlertDialog4();
            // ref слово для выбора кода в кнопке "Попробовать еще раз"
            refBtnTryAgain="twoCode";
            Log.d(TAG, "Интернета пропал при записи данных пользователя в личную комнату");
        }
    }
    // ВРЕМЯ СЕССИИ AUTH при переходе на Web страницу при сварачивании
    public void startTimeSession(){
        Log.d(TAG, "Time Auth Session Start");
        Handler timeSession = new Handler();
        timeSession.postDelayed(new Runnable() {
            @Override
            public void run() {
                // время сессии истекло
                timeSessionEnd();
            }
        },100000);
    }


// КНОПКИ
    // кнопка получить код доступа
    public void getCode (View view){
    // проверка пустоты поля
    if (!edtPhone.getText().toString().isEmpty()){
        // проверка чила цифр =10
        if(edtPhone.getText().toString().length()==10){
            // обнуляем реф слово остановки времени сессии ()
            refStopTimeSession="";
            // запуск времени сессии на случай если приложение не перейдет на Web проверку при сворачивании приложения
            startTimeSession();
            // видимость процесс регистрации
            StyleProgressAuth();
            PhoneAuthOptions options =
                    PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber("+7"+edtPhone.getText().toString())
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .setActivity(this)
                            .setCallbacks(callbacks)
                            .build();
            PhoneAuthProvider.verifyPhoneNumber(options);
        }
        else {
            // введите корректный номер телефона
            Alert0();
        }
    }
    else {
        // введите номер телефона
        Alert1();
    }
}
    // кнопка отправить код
    public void sendCode (View view){
        // проверка заполненного поля кода
        if (!edtCode.getText().toString().isEmpty()){
            //блокировка кнопки от повторных нажатий
            sendCode.setEnabled(false);
            // показать процесс отправки кода смс
            progressB2.setVisibility(View.VISIBLE);
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(code,edtCode.getText().toString());
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                               @Override
                                               public void onComplete(@NonNull Task<AuthResult> task) {
                                                   if(task.isSuccessful()){
                                                       //Стиль при процессе криптографии
                                                       StyleStartCripto();
                                                       // Добро пожаловать
                                                       Alert3();
                                                       // старт криптографии
                                                       StartCripto();
                                                   }
                                                   else {
                                                       // убрать процесс отправки кода смс
                                                       progressB2.setVisibility(View.INVISIBLE);
                                                       //Ошибка регистрации
                                                       Alert4();
                                                       //разблокировка кнопки от повторных нажатий
                                                       sendCode.setEnabled(true);
                                                   }
                                               }
                                           }
                    )
                    .addOnFailureListener(new OnFailureListener() {
                                              @Override
                                              public void onFailure(@NonNull Exception e) {
                                                  //Toast.makeText(Proba.this,"Неверный код подтверждения",Toast.LENGTH_SHORT).show();
                                              }
                                          }
                    );
        }
        else {
            // введите код подтверждения
            Alert2();
        }
    }
    // кнопка попробовать еще раз после пропажи интернета при старте криптографии
    public void tryAgain(View view){
        // выбор работы кода
        // если не равен "twoCode" значит интернет пропал при старте криптографии
        // запускаем код повторной криптографии
        if(!refBtnTryAgain.equals("twoCode")){
            // обнуляем реф слова для повторной криптографии
            IneternetYES="";
            phoneNew="";
            //Стиль при процессе криптографии
            StyleStartCripto();
            // повторный старт криптографии
            StartCripto();
            Log.d(TAG, "повторная криптографии");
        }
        // Интернет пропал при записи данных пользователя в личную комнату
        // запускаем код повторной записи токена в личную комнату
        else {
            // обнуляем реф слова для повторной записи токена
            checkInternet="";
            writeData="";
            // обнуляем ref слово для выбора кода в кнопке "Попробовать еще раз"
            //refBtnTryAgain="";
            //Стиль при процессе криптографии+записи токена в личную комнату
            StyleStartCripto();
            // повторный старт записи данных пользователя в личную комнату
            WritePrivatTokentoBD();
            Log.d(TAG, "повторная запись токена данных пользователя в личную комнату");
        }

    }

//ALERT DIALOG
    // Введите корректный номер телефона
    public void Alert0(){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Proba.this);
        mAlertDialog.setMessage("Введите корректный номер телефона");
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }
    // Введите номер телефона
    public void Alert1(){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Proba.this);
        mAlertDialog.setMessage("Введите номер телефона");
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }
    // Введите код подтверждения
    public void Alert2(){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Proba.this);
        mAlertDialog.setMessage("Введите SMS код");
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }
    // Авторизация успешна, нажмите ОК
    public void Alert3(){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Proba.this);
        mAlertDialog.setMessage("Авторизация успешна, нажмите ОК!");
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }
    //Неверный код подтверждения или слабый сигнал интернета
    public void Alert4(){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Proba.this);
        mAlertDialog.setMessage("Неверный код подтверждения или слабый сигнал интернета");
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setPositiveButton("Попробовать снова", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }
    //ошибка связи проверьте интернет выдает callback
    public void AlertNotInternet(){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Proba.this);
        mAlertDialog.setMessage("Ошибка связи, проверьте интернет");
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        StyleStart();
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }
    // время сессии auth истекло
    public void timeSessionEnd(){
        Log.d(TAG, " первое значение refShowOnlyOne "+refShowOnlyOne);
        // остановка времени сессии если код отправлен или потерян интернет
        if (!refStopTimeSession.equals("StopTimeSession")){
            Log.d(TAG, " второе значение refShowOnlyOne "+refShowOnlyOne);
            // проверка однократности появления такого уведомления
            if (refShowOnlyOne==1){
                // увеличиваем число на 1 чтобы уведомление повторно не показывалось (однократность уведомления)
                refShowOnlyOne=refShowOnlyOne+1;
                Log.d(TAG, "время сессии истекло");
                Log.d(TAG, " третье значение refShowOnlyOne "+refShowOnlyOne);

                // реф слово чтобы Toast "ожидайте смс" в onCodeSent не появилось при истечении времени сессии
                refSmsNoShow="Yes";

                AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Proba.this);
                mAlertDialog.setCancelable(false);
                mAlertDialog
                        .setMessage("Время сессии истекло, приложение будет закрыто")
                        .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                // закрытие приложения реальное
                                closeApp();
                            }
                        });
                mAlertDialog.create();
                // Showing Alert Message
                mAlertDialog.show();
            }
        }
        else {Log.d(TAG, "время сессии auth остановлено");}

    }
    //ловушка от неперехода работает если приложение не перешло на Zakaz3Finish для дальнейшей регистрации заявки
    public void Alert5(){
        Log.d(TAG, "!!!!! ловушка не перехода");
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Proba.this);
        mAlertDialog.setMessage("Нажмите OK для продолжения");
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // ПОВТОРНЫЙ переход на Zakaz3finish для регистрации заявки
                        GoToZakaz3Finish();
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }
    // 1. Интернет пропал при работе криптографии
    // 2. Интернет пропал при записи токена пользователя в личную комнату
    public void showAlertDialog4(){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Proba.this);
        mAlertDialog.setMessage("Низкая скорость передачи данных, проверьте интернет");
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        tVAuth.setText("Ошибка базы данных");
                        // видимость кнопки "попробовать еще"
                        tryAgain.setVisibility(View.VISIBLE);
                        // убрать видимость"Построение базы данных"+прогресс бар
                        tVProgressB.setVisibility(View.INVISIBLE);
                        progressB.setVisibility(View.INVISIBLE);
                        // перепривязка текста
                        ConstraintSet set=new ConstraintSet();
                        // считываем параметры constraintLayout
                        set.clone(constraintLayout);
                        // очистка привязки верхней, хотя это делать не нужно и так работает
                        //set.clear(R.id.tVCode,ConstraintSet.TOP);
                        // привязываем верхушку tryAgain  к нижней границе tVAuth,  можно сделать отступ от tVAuth указав значение, н-р 20
                        set.connect(R.id.tryAgain,ConstraintSet.TOP,R.id.tVAuth,ConstraintSet.BOTTOM,50);
                        //приминение нужных параметров
                        set.applyTo(constraintLayout);
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }

// ПЕРЕХОДЫ на Другие Активити
    // переход на Zakaz3finish для регистрации заявки
    public void GoToZakaz3Finish() {

        Intent ProbaToZakaz3finish=new Intent(this,Zakaz3finish.class);
        //регистрация завершена успешно передаем Ok в Zakaz3finish в лист регистрации заявки
        ProbaToZakaz3finish.putExtra("authOk","Ok");
        //параметры заявки полученные из Zakaz3finish возвращаем обратно в Zakaz3finish
        // телефон
        ProbaToZakaz3finish.putExtra("phoneNew",phoneNew);
        // дата поездки
        ProbaToZakaz3finish.putExtra("Calend",Calend);
        // рейс самолета
        ProbaToZakaz3finish.putExtra("RefplaneCity",RefplaneCity);
        // маршрут такси
        ProbaToZakaz3finish.putExtra("RefMap",RefMap);
        // пункт сбора
        ProbaToZakaz3finish.putExtra("RefPoint",RefPoint);
        // время вылета/прилета/номер рейса для чартера
        ProbaToZakaz3finish.putExtra("time",time);
        // Время точки сбора (для рейсов из Аэропорта)
        ProbaToZakaz3finish.putExtra("timeOfPoint",timeOfPoint);
        // дата точки сбора (для рейсов из Аэропорта)
        ProbaToZakaz3finish.putExtra("dateOfPoint",dateOfPoint);
        // реф слово чтобы определить какой маршрут "в Аэропорт" или "из Аэропорта" (для правильного отображения активити Zakaz3finish)
        ProbaToZakaz3finish.putExtra("RefInFromAirport",RefInFromAirport);
        // стоимость проезда
        ProbaToZakaz3finish.putExtra("fare",fare);
        startActivity(ProbaToZakaz3finish);

        // ловушка не перехода в Zakaz3finish
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                //уведомление нажмите Ок для регистрации заявки
                Alert5();
            }
        },1000);
    }
    // закрытие приложения при окончании времени сессии auth (т.е. не удачной авторизации)
    public void closeApp(){
        // закрытие приложения реальное
        finishAffinity();
        System.exit(0);
    }

    // ВРЕМЯ СЕССИИ КРИПТО  AUTH
    // Блокировка кнопки Back!!!! :)))
    @Override
    public void onBackPressed(){
    }
}
