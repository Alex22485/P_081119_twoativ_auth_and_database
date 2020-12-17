package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class Zakaz3finish extends AppCompatActivity {

    //финишный лист заказа с кнопкой зарегистрировать заказ
    // реализован метод времени точки сбора как  время вылета -(время дороги +время до начала регистрации) только для целых чисел
    // реализована блокировка спящего режима экрана getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


    private static final String TAG ="Zakaz3finish" ;

    // токен
    String newToken;
    // формируется ok после ввода телефона при авторизации
    String authOK="";
    // Экспорт из Zakaz1 реф слово чтобы определить какой маршрут "в Аэропорт" или "из Аэропорта" (для правильного отображения активити)
    String RefInFromAirport="";
    // Экспорт из Zakaz1 стоимость проезда
    String fare;
    // для регистрации заявки
    String timeOut,proverka;
    FirebaseDatabase database01;
    DatabaseReference ref01;
    // визуализация процесса
    TextView tVProgressB;
    ProgressBar progressB;
    // значения экспорта из других активити
    String phoneNew,Calend,RefplaneCity,time,RefMap,RefPoint,timeOfPoint,dateOfPoint;
    // наполнение активити
    TextView Calend1,RefMap1,textTime,RefplaneCity1,time1,dateFly;
    TextView text2,text3,text4,TextDateFly,text5,text6,text7;

    // Сообщение кнопки времени точки сбора (Присваивается в зависимости от типап Маршрута Чартер, В Аэропорт Из  Аэропорта)
    String stringExplanation="";
    // пояснение при Чартерных рейсах из Играки
    String forIgarkaCharter="Вылетая из Игарки, сообщите водителю об этом. Только так он узнает время вашего прилета. (Функция доступна после регистрации заявки).";
    // пояснение при рейсах В Аэропорт
    String inAirport="Время рассчитано к началу регистрации на рейс (т.е. за 2 часа до вылета плюс время дороги до Аэропорта).";
    // пояснение при рейсах из Аэропорта
    String fromAirport="Водитель будет ждать вас в Аэропорту. Пожалуйста, перед вылетом в Красноярск сообщите ему об этом. (Функция доступна после регистрации заявки).";
    // сообщение при нажатии на кнопку изменить заказ
    String textMassageChaingeOder="Данные заказа будут стёрты, продолжить? ";

    // кнопки подробней, Зарегестрировать заявку, изменить условия заявки, детали заказа, карта
    Button BtnRefTime,btnOder,button9,detailsOder,RefPoint1;
    // для регистрации заявки
    String tOBeforReg;
    String proverkaBeforRegistraion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakaz3finish);

        Calend1=findViewById(R.id.Calend1);
        RefMap1=findViewById(R.id.RefMap1);
        textTime=findViewById(R.id.textTime);
        BtnRefTime=findViewById(R.id.BtnRefTime);
        RefplaneCity1=findViewById(R.id.RefplaneCity1);
        time1=findViewById(R.id.time1);
        dateFly=findViewById(R.id.dateFly);
        text2=findViewById(R.id.text2);
        text3=findViewById(R.id.text3);
        text4=findViewById(R.id.text4);
        TextDateFly=findViewById(R.id.TextDateFly);
        text5=findViewById(R.id.text5);
        text6=findViewById(R.id.text6);
        text7=findViewById(R.id.text7);
        btnOder=findViewById(R.id.btnOder);
        button9=findViewById(R.id.button9);
        detailsOder=findViewById(R.id.detailsOder);
        RefPoint1=findViewById(R.id.RefPoint1);
        tVProgressB=findViewById(R.id.tVProgressB);
        progressB=findViewById(R.id.progressB);

        // получить токен
        FirebaseMessaging. getInstance (). getToken ()
                . addOnCompleteListener ( new OnCompleteListener< String >() {
                    @Override
                    public void onComplete ( @NonNull Task< String > task ) {
                        if (! task . isSuccessful ()) {
                            // ошибка получения токена (по идее никогда не должна вылезти, но на всякий случай оставил)
                            // уведомление приложение будет перезапущено
                            AlertMistake();
                        }
                        else {
                            // запись токена
                            newToken = task . getResult ();
                            Log . d ( TAG , "токен получен"+newToken );
                        }
                    }
                });
        // скрыть всю визуализацию активити
        visibilityNo();
        // скрыть  визуализацию детали
        visibilityDetailseNo();
        // скрыть визуализацию процесса
        visibilityProcessNo();

        // получение экспорта из листа авторизации
        Intent ProbaToZakaz3finish= getIntent();
        authOK= "K"+ ProbaToZakaz3finish.getStringExtra("authOk");
        Log.d(TAG, "authOk: "+authOK);


        // автоматическая регистрация заявки (после успешной авторизации) при переходе из Proba.class
        if(authOK.equals("KOk")){
            // данные экспорта из Proba
            // телефон
            phoneNew=ProbaToZakaz3finish.getStringExtra("phoneNew");
            // дата поездки
            Calend=ProbaToZakaz3finish.getStringExtra("Calend");
            // рейс самолета
            RefplaneCity=ProbaToZakaz3finish.getStringExtra("RefplaneCity");
            // маршрут такси
            RefMap=ProbaToZakaz3finish.getStringExtra("RefMap");
            // пункт сбора
            RefPoint=ProbaToZakaz3finish.getStringExtra("RefPoint");
            // время вылета/прилета/номер рейса для чартера
            time=ProbaToZakaz3finish.getStringExtra("time");
            // Время точки сбора (для рейсов из Аэропорта)
            timeOfPoint=ProbaToZakaz3finish.getStringExtra("timeOfPoint");
            // дата точки сбора (для рейсов из Аэропорта)
            dateOfPoint=ProbaToZakaz3finish.getStringExtra("dateOfPoint");
            // реф слово чтобы определить какой маршрут "в Аэропорт" или "из Аэропорта" (для правильного отображения активити)
            RefInFromAirport=ProbaToZakaz3finish.getStringExtra("RefInFromAirport");
            // стоимость проезда
            fare=ProbaToZakaz3finish.getStringExtra("fare");


            Log.d(TAG, "автоматическая регистрация после авторизации: ");

            Log.d(TAG, "экспорт из Proba телефон: "+phoneNew);
            Log.d(TAG, "экспорт из Proba дата вылета-прилета: "+Calend);
            Log.d(TAG, "экспорт из Proba дата точки сбора: "+dateOfPoint);
            Log.d(TAG, "экспорт из Proba время вылета-прилета: "+time);
            Log.d(TAG, "экспорт из Proba время точки сбора: "+timeOfPoint);
            Log.d(TAG, "экспорт из Proba самолет: "+RefplaneCity);
            Log.d(TAG, "экспорт из Proba маршрут: "+RefMap);
            Log.d(TAG, "экспорт из Proba точка сбора: "+RefPoint);
            Log.d(TAG, "экспорт из Proba тип маршрута В(ИЗ) Аэропорта: "+RefInFromAirport);
            Log.d(TAG, "экспорт из Proba стоимость проезда: "+fare);

            // показать визуализацию процесса автоматической регистрации заявки после авторизации
            visibilityProcessYes();

            //задержка 4 секунды чтобы успел записаться NO в БД Заявки из другого кода
            Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // проверка интернета перед автоматической регистрации ранее сформированной заявки
                    btnInsert ();
                }
            },4000);
        }
        // переход из Zakaz1 получение данных экспорта
        else{
            // получение данных из Zakaz1
            Intent Zakaz1ToZakaz3finish = getIntent();
            // secretNumber
            phoneNew=""+Zakaz1ToZakaz3finish.getStringExtra("phoneNew");
            // дата полета
            Calend=""+Zakaz1ToZakaz3finish.getStringExtra("Calend");
            // рейс самолета
            RefplaneCity=""+Zakaz1ToZakaz3finish.getStringExtra("RefplaneCity");
            // время вылета/прилета/номер рейса для чартера
            time=""+Zakaz1ToZakaz3finish.getStringExtra("time");
            // маршрут
            RefMap=""+Zakaz1ToZakaz3finish.getStringExtra("RefMap");
            // точка сбора
            RefPoint=""+Zakaz1ToZakaz3finish.getStringExtra("RefPoint");
            // Время точки сбора (для рейсов из Аэропорта)
            timeOfPoint=Zakaz1ToZakaz3finish.getStringExtra("timeOfPoint");
            // дата точки сбора (для рейсов из Аэропорта)
            dateOfPoint=Zakaz1ToZakaz3finish.getStringExtra("dateOfPoint");
            // реф слово чтобы определить какой маршрут "в Аэропорт" или "из Аэропорта" (для правильного отображения активити)
            RefInFromAirport=Zakaz1ToZakaz3finish.getStringExtra("RefInFromAirport");
            // стоимость проезда
            fare=Zakaz1ToZakaz3finish.getStringExtra("fare");

            Log.d(TAG, "экспорт из Zakaz1 телефон: "+phoneNew);
            Log.d(TAG, "экспорт из Zakaz1 дата вылета-прилета: "+Calend);
            Log.d(TAG, "экспорт из Zakaz1 дата точки сбора: "+dateOfPoint);
            Log.d(TAG, "экспорт из Zakaz1 время вылета-прилета: "+time);
            Log.d(TAG, "экспорт из Zakaz1 время точки сбора: "+timeOfPoint);
            Log.d(TAG, "экспорт из Zakaz1 самолет: "+RefplaneCity);
            Log.d(TAG, "экспорт из Zakaz1 маршрут: "+RefMap);
            Log.d(TAG, "экспорт из Zakaz1 точка сбора: "+RefPoint);
            Log.d(TAG, "экспорт из Zakaz1 тип маршрута В(ИЗ) Аэропорта: "+RefInFromAirport);
            Log.d(TAG, "экспорт из Zakaz1 стоимость проезда: "+fare);

            // отображение заявки на экране
            VisualOderShow();
        }
    }
// МЕТОДЫ
    // отображение заявки на экране
    public void VisualOderShow(){
        // показать всю визуализацию активити
        visibilityYes();

        // Если Рейс Чартер из Игарки
        if (time.equals("1 рейс")||time.equals("2 рейс")||time.equals("3 рейс")){
            // дата заказа
            Calend1.setText("Заказ на "+Calend);
            // маршрут
            RefMap1.setText(RefMap);
            // пункт сбора
            RefPoint1.setText(RefPoint);
            // кнопка время сбора
            BtnRefTime.setText("по факту прилета");
            // кнопка время сбора (Сообщение ПОЯСНЕНИЕ)
            stringExplanation=forIgarkaCharter;
            // стоимость поездки
            text6.setText(fare);
            // номер чартера
            text3.setText("Чартер "+time);
            // самолет
            RefplaneCity1.setText(RefplaneCity);
            // Текст дата вылета из Игарки
            TextDateFly.setText("Дата вылета из Игарки:");
            // дата вылета
            dateFly.setText(Calend);
        }
        else{
            // если рейс в Аэропорт
            if (RefInFromAirport.equals("в Аэропорт")){
                // дата заказа
                Calend1.setText("Заказ на "+dateOfPoint);
                // маршрут
                RefMap1.setText(RefMap);
                // пункт сбора
                RefPoint1.setText(RefPoint);
                // кнопка время сбора
                BtnRefTime.setText(timeOfPoint+" подробней");
                // кнопка время сбора (Сообщение ПОЯСНЕНИЕ)
                stringExplanation=inAirport;
                // стоимость поездки
                text6.setText(fare);
                // самолет
                RefplaneCity1.setText(RefplaneCity);
                // дата вылета
                dateFly.setText(Calend);
                // время вылета вылета
                time1.setText(time);

            }
            // если рейс из Аэропорта
            else if (RefInFromAirport.equals("из Аэропорта")) {
                // дата заказа
                Calend1.setText("Заказ на "+dateOfPoint);
                // маршрут
                RefMap1.setText(RefMap);
                // пункт сбора
                RefPoint1.setText(RefPoint);
                // кнопка время сбора
                BtnRefTime.setText(timeOfPoint+" подробней");
                // кнопка время сбора (Сообщение ПОЯСНЕНИЕ)
                stringExplanation="В "+time+" "+fromAirport;
                // стоимость поездки
                text6.setText(fare);
                // самолет
                RefplaneCity1.setText(RefplaneCity);
                // Текст дата прилета
                TextDateFly.setText("Дата прилета:");
                // дата прилета
                dateFly.setText(Calend);
                // время прилета
                text4.setText("Время прилета");
                // время прилета
                time1.setText(time);
            }
        }
    }
    // проверка интернета перед регистрацией заявки
    public void btnInsert(){
        Log.d(TAG, "Старт проверка интернета ЧЕРЕЗ YesNO");
        // реф значения
        tOBeforReg="";
        proverkaBeforRegistraion="";
        //ТАЙМ-АУТ проверка интернета
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // реф значение время проверка интернета закончилось
                tOBeforReg="Out";
                // время проверки интернета вышло
                intNotBeforRegistraion();
            }
        },20000);
        // чтение YES-No
        //Важно в БД с читаемым объектом не должно быть параллельных линий :)
        // только тогда считывает значения с первого раза без null
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        Query query = rootRef.child("Пользователи")
                .child("Personal")
                .child(phoneNew)
                .child("Proverka")
                .orderByChild("Oder");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ddd : dataSnapshot.getChildren()) {
                    String yesNo=ddd.child("Заявка").getValue(String.class);
                    proverkaBeforRegistraion=yesNo;
                    Log.d(TAG, "инетрнет есть, заявка есть?"+yesNo);
                    // проверка YEsNo в заявке
                    YesNoBeforeReg();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);
    }
    // время проверки интернета вышло
    public void intNotBeforRegistraion(){
        if (!proverkaBeforRegistraion.isEmpty()){
            Log.d(TAG, "время проверки интернета вышло, но значение YES-No считано");
        }
        else{
            Log.d(TAG, "Время проверки вышло, not internet");
            showAlertDialog4();
        }
    }
    // Start регистрации заявки
    public void YesNoBeforeReg(){
        if(tOBeforReg.equals("Out")){
            Log.d(TAG, "Время проверки интернета вышло, но интернет есть");
        }
        // Start регистрации заявки
        else if (proverkaBeforRegistraion.equals("No")){
            Log.d(TAG, "Интернет есть, старт регистрации");
            // Регистрация заявки
            startRegistration();
        }
        // найдена старая заявка (по идее такое не должно быть но на вчякий случай оставил)
        else if (proverkaBeforRegistraion.equals("Yes")){
            Log.d(TAG, "Интернет есть, НО есть старая заявка");
            showAlertDialog5();
        }
    }
    // Регистрация заявки
    public void startRegistration(){
        Log.d(TAG, "Старт Регистрации заявки");
        timeOut="";
        proverka="";
        //ТАЙМ-АУТ ЗАПРОСА ИНТЕРНЕТА
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // ТАЙМ-АУТ ИНТЕРНЕТА при регистрации заявки
                timeOut="Out";
                internetNot();
            }
        },15000);
        // Запись для проверки Разрешения на запись заявки в БД
        database01 = FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Пользователи")
                .child("Personal")
                .child(phoneNew)
                .child("Заявки")
                .child(dateOfPoint+"*"+Calend) // дата точки сбор*дата вылета(прилета)
                .child(RefplaneCity)
                .child(timeOfPoint+"*"+time) // время точки сбор*время вылета(прилета)
                .child(RefMap+"*"+fare) // маршрут*стоимость
                .child(RefPoint)
                .child("CheckStopOder")
                .child(phoneNew);
        ref01.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ref01.child(newToken).setValue(phoneNew);
                //проверка разрешена или нет запись заявки в БД
                Qwery();
                // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД ЗАЯВКИ...-...-...-"CheckStopOder"...
                ref01.removeEventListener(this);
                Log.d(TAG, "запись отправлена в БД для проверки Разрешения/Запрета");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        }
        );
    }
    //проверка разрешена или нет запись заявки в БД
    public void Qwery(){
        Log.d(TAG, "Start поиск слова Разрешения/Запрет на запись");
        // проверяем какое слово написано в объекте РазрешениеНаЗапись.
        // Если Разрешено то запись заявки оформляется, если нет то заявка отклонена (процесс записи и отклонения выполнен в nod js function OderCheck)
        final Query aaa1= FirebaseDatabase.getInstance().getReference("Пользователи")
                .child("Personal")
                .child(phoneNew)
                .child("Заявки")
                .child(dateOfPoint+"*"+Calend) // дата точки сбор*дата вылета(прилета)
                .child(RefplaneCity)
                .child(timeOfPoint+"*"+time) // время точки сбор*время вылета(прилета)
                .child(RefMap+"*"+fare) // маршрут*стоимость
                .child(RefPoint)
                .child("Разрешение")
                .child(phoneNew)
                .orderByChild("Разрешение");
        aaa1.addChildEventListener( new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String data=dataSnapshot.child( "РазрешениеНаЗапись" ).getValue(String.class);
                Log.d(TAG, "РазрешениеНаЗапись"+data);
                // реф значение
                proverka=data;
                // проверка считанного слова Разрешение или Запрет на запись заявки в БД
                checkWordProverka();
                //Останавливаем прослушивание, чтобы в приложении у другого пользователя не появлялась информация когда другой пользоваьель регистрирует заявку
                aaa1.removeEventListener(this);
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
    // проверка считанного слова Разрешение или Запрет на запись заявки в БД
    public void checkWordProverka(){
        if(timeOut.equals("Out")){
            Log.d(TAG, "Разрешение/Запретс получен но время проверки интернета вышло");
        }
        else if(proverka.equals("Разрешено")){
            Log.d(TAG, "Разрешено");
            // убрать визуализацию процесса
            visibilityProcessNo();
            showAlertDialog1();
        }
        else if (proverka.equals("Запрещено")){
            Log.d(TAG, "Запрещено");
            // убрать визуализацию процесса
            visibilityProcessNo();
            showAlertDialog();
        }
    }

//КНОПКИ
    // кнопка "время сбора нажми на меня" (подробности формирования времени сбора)
    public void BtnRefTime(View view) {
        showAlertDialog6();
    }
    // кнопка зарегистрировать заявку
    public void btnOder(View view) {
        if (phoneNew.equals("null")) {
            AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz3finish.this);
            mAlertDialog.setMessage("Для продолжения необходимо" +
                    " авторизироваться." +
                    " Вам придет SMS c кодом подтверждения")
                    .setPositiveButton("Принять", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // переход на лист авторизации
                            goListRegistration();
                        }
                    })
                    .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            mAlertDialog.create();
            mAlertDialog.show();
        }
        else {
            // блокировка спящего режима экрана
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            // отмена блокировки спящего режима экрана
            //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

            // проверка интернета перед регистрацией заявки
            btnInsert();
            // отображение видимости
            visibilityNo();
            visibilityDetailseNo();
            visibilityProcessYes();
        }
    }
    // кнопка детали поездки
    public void detailsOder(View view){
        String d=detailsOder.getText().toString();
        Log.d(TAG, "d: "+d);

        // показать детали
        if(detailsOder.getText().toString().equals("детали заказа")) {
            visibilityDetailseYes();
            detailsOder.setText("скрыть");
            Log.d(TAG, "показать: "+detailsOder.getText().toString());
        }
        // скрыть детали
        else {
            visibilityDetailseNo();
            detailsOder.setText("детали заказа");
            Log.d(TAG, "скрыть: "+detailsOder.getText().toString());
        }
    }
    // кнопка изменить условия заказа
    public void  button9(View view){
        //Alert вы действительно хотите изменить уловия заявки
        AlertChangeOder();
    }

//ALERT DIALOGS
    // ошибка загрузки данных приложение будет перезапущено (по идее не должно никогда появиться но на всякий случай оставил)
    public void AlertMistake(){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz3finish.this);
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setMessage("ошибка загрузки данных, приложение будет перезапущено")
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // перезапуск приложения
                        reStartApp();
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }
    // Всплывающая информация "Заявка отклонена!!!"
    public void showAlertDialog() {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz3finish.this);
        mAlertDialog.setTitle("Заявка отклонена :(");
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setMessage("На Пункте сбора"+" "+RefPoint+" "+"все места заняты. Попробуйте выбрать, другие пункты сбора данного маршрута")
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onBackList();
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }
    // Всплывающая информация "Заявка оформлена!!!"
    public void showAlertDialog1() {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz3finish.this);
        mAlertDialog.setTitle("Спасибо, заявка оформлена!!!");
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setMessage("Ищем автомобиль..."+"Вы получите уведомление о результате поиска")
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //переход в окно статуса
                        onStatusList();
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }
    // Нет интернета перед регистрацией заявки (при считывании YesNO)
    public void showAlertDialog4(){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(
                Zakaz3finish.this);
        mAlertDialog.setTitle("Ошибка регистрации");
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setMessage("проверьте настройки интернета")
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // убрать визуализацию процесса
                        visibilityProcessNo();
                        // отображение заявки на экране
                        VisualOderShow();
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }
    // Найдена ваша старая заявка (по идее не должно никогда появиться но на всякий случай оставил)
    public void showAlertDialog5(){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(
                Zakaz3finish.this);
        // Set Title
        mAlertDialog.setTitle("Найдена ваша старая заявка");
        mAlertDialog.setCancelable(false);
        // Set Message
        mAlertDialog
                .setMessage("отмените старую заявку")
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Переход на лист Статуса
                        onStatusList();
                    }
                });
        mAlertDialog.create();
        // Showing Alert Message
        mAlertDialog.show();
    }
    // подробности как формируется время сбора
    public void showAlertDialog6() {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz3finish.this);
        mAlertDialog.setCancelable(false);
        mAlertDialog
                // Message в зависимости от типа Маршрута (Чартер, В Аэропорт, из Аэропорта)
                .setMessage(stringExplanation)
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }
    // Нет интернета при получении  РАЗРЕШЕНО-ЗАПРЕЩЕНО из БД при регистрации заявки
    public void showAlertDialog7(){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(
                Zakaz3finish.this);
        mAlertDialog.setTitle("Проверьте настройки интернета");
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setMessage("Приложение будет перезапущено.")
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // перезапуск приложения
                        reStartApp();
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }
    //Alert вы действительно хотите изменить уловия заявки
    public void AlertChangeOder(){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz3finish.this);
        mAlertDialog.setMessage(textMassageChaingeOder);
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // переход на лист Zakaz1 для изменения заявки
                        onBackList();
                    }
                });
        mAlertDialog
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }

// ПЕРЕХОДЫ НА ДР АКТИВИТИ
    // 1. перезагрузка приложения из-за ошибки получения токена
    // 2. перезагрузка приложения Нет интернета при получении  РАЗРЕШЕНО-ЗАПРЕЩЕНО из БД при регистрации заявки
    public void reStartApp(){
        Intent GoMainActivity= new Intent(this,MainActivity.class);
        startActivity(GoMainActivity);
    }
    // переход на лист авторизации
    public void goListRegistration(){
        Intent Zakaz3finishToProba =new Intent(this, Proba.class);

        // дата поездки
        Zakaz3finishToProba.putExtra("Calend",Calend);
        // рейс самолета
        Zakaz3finishToProba.putExtra("RefplaneCity",RefplaneCity);
        // маршрут такси
        Zakaz3finishToProba.putExtra("RefMap",RefMap);
        // пункт сбора
        Zakaz3finishToProba.putExtra("RefPoint",RefPoint);
        // время вылета/прилета/номер рейса для чартера
        Zakaz3finishToProba.putExtra("time",time);
        // Время точки сбора (для рейсов из Аэропорта)
        Zakaz3finishToProba.putExtra("timeOfPoint", timeOfPoint);
        // дата точки сбора (для рейсов из Аэропорта)
        Zakaz3finishToProba.putExtra("dateOfPoint", dateOfPoint);
        // реф слово чтобы определить какой маршрут "в Аэропорт" или "из Аэропорта" (для правильного отображения активити)
        Zakaz3finishToProba.putExtra("RefInFromAirport", RefInFromAirport);
        // стоимость проезда
        Zakaz3finishToProba.putExtra("fare", fare);

        startActivity(Zakaz3finishToProba);
    }
    //Нет интернета при регистрации заявки
    public void internetNot(){
        if (!proverka.isEmpty()){
            Log.d(TAG, "время вышло но Разрешение/Запрет получен из БД");
        }
        else{
            Log.d(TAG, "Время поиска Разрешение/Запрет для записи заявки вышло not internet");
            // Нет интернета при получении  РАЗРЕШЕНО-ЗАПРЕЩЕНО из БД при регистрации заявки
            showAlertDialog7(); // ПРОВЕРИТЬ ОБЯЗАТЕЛЬНО
        }
    }
    // Переход на лист оформления заявки т.к. выбранный пункт сбора уже сформирован либо
    // для изменения заявки (по кнопке изменить Условия заказа)
    public void onBackList() {
        //Переход на лист оформления заявки
        Intent Zakaz3FinishToZakaz1 = new Intent( this,Zakaz1.class );
        // отправляем phoneNew в Zakaz1
        Zakaz3FinishToZakaz1.putExtra("regFromMain3",phoneNew);
        startActivity( Zakaz3FinishToZakaz1);
    }
    // Переход на лист Статуса
    public void onStatusList() {
        //Переход на лист Статуса
        Intent Zakaz3FinishTOMain6 = new Intent( this,Zakaz4Request.class );
        Zakaz3FinishTOMain6.putExtra("phoneRef",phoneNew);
        startActivity(Zakaz3FinishTOMain6);
    }

// ВИЗУАЛИЗАЦИИ АКТИВИТИ
    // убрать всю визуализацию активити
    public void visibilityNo(){
        Calend1.setVisibility(View.GONE);
        RefMap1.setVisibility(View.GONE);
        RefPoint1.setVisibility(View.GONE);
        textTime.setVisibility(View.GONE);
        BtnRefTime.setVisibility(View.GONE);
        text2.setVisibility(View.GONE);
        text5.setVisibility(View.GONE);
        text6.setVisibility(View.GONE);
        text7.setVisibility(View.GONE);
        btnOder.setVisibility(View.GONE);
        button9.setVisibility(View.GONE);
        detailsOder.setVisibility(View.GONE);
            }
    //показать визулизацию активити
    public void visibilityYes(){
        Calend1.setVisibility(View.VISIBLE);
        RefMap1.setVisibility(View.VISIBLE);
        RefPoint1.setVisibility(View.VISIBLE);
        textTime.setVisibility(View.VISIBLE);
        BtnRefTime.setVisibility(View.VISIBLE);
        text2.setVisibility(View.VISIBLE);
        text5.setVisibility(View.VISIBLE);
        text6.setVisibility(View.VISIBLE);
        text7.setVisibility(View.VISIBLE);
        detailsOder.setText("детали заказа");
        btnOder.setVisibility(View.VISIBLE);
        button9.setVisibility(View.VISIBLE);
        detailsOder.setVisibility(View.VISIBLE);
    }
    // убрать визуализацию Детали заказа
    public void visibilityDetailseNo(){
        text3.setVisibility(View.GONE);
        RefplaneCity1.setVisibility(View.GONE);
        TextDateFly.setVisibility(View.GONE);
        dateFly.setVisibility(View.GONE);
        text4.setVisibility(View.GONE);
        time1.setVisibility(View.GONE);
    }
    // показать визуализацию Детали заказа
    public void visibilityDetailseYes(){
        text3.setVisibility(View.VISIBLE);
        RefplaneCity1.setVisibility(View.VISIBLE);
        TextDateFly.setVisibility(View.VISIBLE);
        dateFly.setVisibility(View.VISIBLE);
        // если рейсы Чартер из Игарки
        if (time.equals("1 рейс")||time.equals("2 рейс")||time.equals("3 рейс")){
            // убрать видимость ТЕКСТ время вылета-прилета
            text4.setVisibility(View.INVISIBLE);
            // убрать видимость время вылета-прилета
            time1.setVisibility(View.INVISIBLE);
        }
        else {
            // показать видимость ТЕКСТ время вылета-прилета
            text4.setVisibility(View.VISIBLE);
            // показать видимость время вылета-прилета
            time1.setVisibility(View.VISIBLE);
        }
    }
    // убрать визуализацию процесса
    public void visibilityProcessNo(){
        tVProgressB.setVisibility(View.GONE);
        progressB.setVisibility(View.GONE);
    }
    // показать визуализацию процесса
    public void visibilityProcessYes(){
        tVProgressB.setVisibility(View.VISIBLE);
        progressB.setVisibility(View.VISIBLE);
    }

// Блокировка кнопки Back!!!! :)))
    @Override
    public void onBackPressed(){
    }

}