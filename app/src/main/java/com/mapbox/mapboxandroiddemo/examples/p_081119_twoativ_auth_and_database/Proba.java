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
    private static final String TAG ="Proba";
    // ввод телефона и OTP кода
    EditText edtPhone, edtCode;
    TextView tVAuth,tVPhone,tVProgressB,tVCode;
    // кнопки получить и отправить OTP код
    Button getCode,sendCode,tryAgain;
    // визуализация процесса
    ProgressBar progressB;
    FirebaseAuth mAuth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    String code;
    ConstraintLayout constraintLayout;

    // для экспорта из Zakaz3finish
    String Calend, RefplaneCity, RefMap, RefPoint, time;
    // токен
    String newToken;
    //для шифрования
    String IneternetYES,phoneNew;
    FirebaseDatabase databaseSecret;
    DatabaseReference refSecret;
    FirebaseDatabase database01;
    DatabaseReference ref01;
    String keyReg;
    String checkInternet;
    String writeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proba);

        mAuth=FirebaseAuth.getInstance();
        constraintLayout=findViewById(R.id.constraintLayout);
        tVAuth=findViewById(R.id.tVAuth);
        tVPhone=findViewById(R.id.tVPhone);
        tVProgressB=findViewById(R.id.tVProgressB);
        progressB=findViewById(R.id.progressB);
        tVCode=findViewById(R.id.tVCode);
        edtPhone=findViewById(R.id.edtPhone);
        edtCode=findViewById(R.id.edtCode);
        getCode=findViewById(R.id.getCode);
        sendCode=findViewById(R.id.sendCode);
        tryAgain=findViewById(R.id.tryAgain);

        //данные из main3Activity
        Intent Zakaz3finishToMain2AcivityTo=getIntent();

        // дата поездки
        Calend=Zakaz3finishToMain2AcivityTo.getStringExtra("Calend");
        // рейс самолета
        RefplaneCity=Zakaz3finishToMain2AcivityTo.getStringExtra("RefplaneCity");
        // маршрут такси
        RefMap=Zakaz3finishToMain2AcivityTo.getStringExtra("RefMap");
        // пункт сбора
        RefPoint=Zakaz3finishToMain2AcivityTo.getStringExtra("RefPoint");
        // время вылета/прилета/номер рейса для чартера
        time=Zakaz3finishToMain2AcivityTo.getStringExtra("time");

        // get TOKEN new with 13/11/2020
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
                        Toast. makeText ( Proba . this , newToken , Toast . LENGTH_SHORT ). show ();
                    }
                });

        // видимость при старте страницы
        StyleStart();
        //callback
        callbacks= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                //ошибка связи прверьте интернет
                AlertNotInternet();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                Toast.makeText(Proba.this,"ожидайте смс",Toast.LENGTH_LONG).show();
                // code это полученный токен
                code = s;
                // видимость ввод проверочный код
                StylSentOPT();

            }
        };
}
// кнопка получить код доступа
public void getCode (View view){
    // проверка пустоты поля
    if (!edtPhone.getText().toString().isEmpty()){
        // проверка чила цифр =10
        if(edtPhone.getText().toString().length()==10){

            // видимость процесс регистрации
            StyleProgressAuth();

            PhoneAuthOptions options =
                    PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber("+7"+edtPhone.getText().toString())       // Phone number to verify
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
                            //запуск criptography
                            StartCripto();
                        }
                        else {
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
        mAlertDialog.setMessage("Введите код подтверждения");
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }
    // Добро пожаловать
    public void Alert3(){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Proba.this);
        mAlertDialog.setMessage("Добро пожаловать! Нажмите, ОК, для продолжения оформления заявки");
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }
    //Ошибка регистрации
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
    //ошибка связи прверьте интернет
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
    // видимость при старте страницы
    public void StyleStart(){
        tVPhone.setVisibility(View.VISIBLE);
        edtPhone.setVisibility(View.VISIBLE);
        getCode.setVisibility(View.VISIBLE);
        tVProgressB.setVisibility(View.INVISIBLE);
        progressB.setVisibility(View.INVISIBLE);
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
    public void StyleStartCripto(){
        tVAuth.setText("Авторизация успешна");
        tVCode.setVisibility(View.INVISIBLE);
        edtCode.setVisibility(View.INVISIBLE);
        sendCode.setVisibility(View.INVISIBLE);
        tVProgressB.setText("Построение базы данных");
        tVProgressB.setVisibility(View.VISIBLE);
        progressB.setVisibility(View.VISIBLE);
    }
    // старт криптографии
    public void StartCripto(){
        Log.d(TAG, "Старт шифрования");
        IneternetYES="";

        //проверка интернета
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Завершен ТАЙМ-АУТ проверка интернета
                IneternetYES="Out";
                // проверка интернета при записи в БД
                IneternetYesNo();
            }
        },40000);

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
        phoneNew="";
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
    // проверка интернета при записи в БД
    public void IneternetYesNo(){
        if (!phoneNew.isEmpty()){
            Log.d(TAG, "время проверки интернета вышло, но номер СС получен");
        }
        else{
            Log.d(TAG, "Время проверки вышло, not internet");
            showAlertDialog4();
        }
    }
    // проверка интернета при чтении СС кода из БД
    public void checkInternetYesNo(){
        if(IneternetYES.equals("Out")){
            Log.d(TAG, "СС номер получен, но время проверки интернета вышло");
        }
        else if(!phoneNew.isEmpty()){
            Log.d(TAG, "CC получен, старт регистрации заявки");
            // запись в личную комнату токена для дальнейшего формирования данных о пользователе
            WritePrivatTokentoBD();
        }
    }
    public void showAlertDialog4(){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Proba.this);
        mAlertDialog.setMessage("Низкая скорость передачи данных, проверьте интернет");
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        tVAuth.setText("Ошибка базы данных");
                        // видимость кнопки попробовать еще
                        tryAgain.setVisibility(View.VISIBLE);
                        // перепривязка текста
                        ConstraintSet set=new ConstraintSet();
                        // считываем параметры constraintLayout
                        set.clone(constraintLayout);
                        // очистка привязки верхней, хотя это делать не нужно и так работает
                        //set.clear(R.id.tVCode,ConstraintSet.TOP);
                        // привязываем верхушку tVCode  к нижней границе tVAuth,  можно сделать отступ от tVAuth указав значение, н-р 20
                        set.connect(R.id.tryAgain,ConstraintSet.TOP,R.id.tVAuth,ConstraintSet.BOTTOM,50);
                        //приминение нужных параметров
                        set.applyTo(constraintLayout);
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }
    // запись в личную комнату токена для дальнейшего формирования данных о пользователе
    public void WritePrivatTokentoBD(){
        keyReg="";
        checkInternet="";
        writeData="";
        //ТАЙМ-АУТ ЗАПРОСА ИНТЕРНЕТА
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Завершен ТАЙМ-АУТ ЗАПРОСА ИНТЕРНЕТА при записи данных в БД
                checkInternet="Out";
                Log.d(TAG, "Записан checkInternet=Out");
                // проверка интернета пр записи токена в БД
                inetNotWhenGoCheckRegistration();
            }
            },15000);
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
                                            //переход на лист Zakaz3Finish для регистрации ЗАЯВКИ
                                            getMainList();
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    }
        );
    }
    // проверка интернета пр записи токена в БД
    public void inetNotWhenGoCheckRegistration(){
        if(writeData.equals("Yes")){
            Log.d(TAG, "таймер остановлен");
        }
        else {
            //пропал интернет во время проверки наличия регистрации
            Log.d(TAG, "Интернета пропал при записи данных");
            Intent aaa = new Intent(this,InternetNot.class);
            startActivity(aaa);
        }
    }

    public void getMainList(){
        Log.d(TAG, "вход В МЕТОД getMainList");
        if(checkInternet.equals("Out")){
            Log.d(TAG, "getMainList остановлен");
            //return нужен чтобы при возобноблении интернета автоматически не переходило на лист с заявками
            return;
        }

        Intent Main2AcivityToZakaz3finish=new Intent(this,Zakaz3finish.class);

        //регистрация завершена успешно передаем Ok в main3Activity в лист регистрации заявки
        Main2AcivityToZakaz3finish.putExtra("authOk","Ok");
        //параметры заявки полученные из Zakaz3finish возвращаем обратно в Zakaz3finish
        // телефон
        Main2AcivityToZakaz3finish.putExtra("phoneNew",phoneNew);
        // дата поездки
        Main2AcivityToZakaz3finish.putExtra("Calend",Calend);
        // рейс самолета
        Main2AcivityToZakaz3finish.putExtra("RefplaneCity",RefplaneCity);
        // маршрут такси
        Main2AcivityToZakaz3finish.putExtra("RefMap",RefMap);
        // пункт сбора
        Main2AcivityToZakaz3finish.putExtra("RefPoint",RefPoint);
        // время вылета/прилета/номер рейса для чартера
        Main2AcivityToZakaz3finish.putExtra("time",time);


        startActivity(Main2AcivityToZakaz3finish);
    }
}
