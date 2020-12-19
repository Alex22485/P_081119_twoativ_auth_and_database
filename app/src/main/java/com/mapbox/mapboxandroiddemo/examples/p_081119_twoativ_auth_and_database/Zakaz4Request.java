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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Zakaz4Request extends AppCompatActivity {

    // КОД ПРОТЕСТИРОВАН 19.12.2020

    // Зарегистрированый заказ
    // реализована блокировка и разблокировка спящего режима экрана getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


    private static final String TAG ="Zakaz4Request" ;
    // наполнение активити
    TextView Calend1,textCarIsFine,RefMap1,textTime,RefplaneCity1,time1,dateFly;
    TextView text2,text3,text4,TextDateFly,text5,text6,text7,tVProgressB;
    TextView CarCondition;
    TextView messageForDriver,TextQRcode;
    ImageView QRcode;
    // кнопки карта, подробней,детали заказа, я вылетел, я приземлился
    Button BtnRefTime,RefPoint1,detailsOder,BtnIfly,BtnIland,cancelOder;
    ProgressBar progressBB,progressB;

    // Сообщение кнопки времени точки сбора (Присваивается в зависимости от типап Маршрута Чартер, В Аэропорт Из  Аэропорта)
    String stringExplanation="";
    // пояснение при Чартерных рейсах из Играки
    String forIgarkaCharter="Вылетая из Игарки, сообщите водителю об этом. Только так он узнает время вашего прилета. (Функция станет доступна, когда водитель будет найден).";
    // пояснение при рейсах В Аэропорт
    String inAirport="Время рассчитано к началу регистрации на рейс (т.е. за 2 часа до вылета плюс время дороги до Аэропорта).";
    // пояснение при рейсах из Аэропорта
    String fromAirport="Водитель будет ждать вас в Аэропорту. Пожалуйста, перед вылетом в Красноярск сообщите ему об этом. (Функция станет доступна, когда водитель будет найден).";
    // сообщение при нажатии на кнопку изменить заказ

    //Экспорт СС номера из MainActivity начальная страница заставки
    String phoneNew1="";
    //Экспорт СС номера из Zakaz3Finish после регистрации заявки
    String phoneNew2="";
    //Номер телефона
    String phoneNew="";
    // реф слова для метода считывания заявки
    String timeOut="";
    String proverka="";
// Данные заявки считанные с БД
    // Дата точки сбор*дата вылета(прилета)
    String dateOfPointANDCalend="";
    // Cамолет
    String RefplaneCity="";
    // Время точки сбор*время вылета(прилета)
    String timeOfPointANDtime="";
    // Маршрут*стоимость
    String RefMapANDfare="";
    // Точка сбора
    String RefPoint="";
    // Найденный Автомобиль
    String сarDrive="";
    // Количество человек в заявке (пока что не используется но в БД есть)
    Integer peopleOder;

// Для идентификации даннх из БД
    // Дата точки сбора
    String dateOfPoint="";
    // Дата вылета-прилета
    String Calend="";
    // Время точки сбора
    String timeOfPoint="";
    // Время вылета-прилета
    String time="";
    // Маршрут
    String RefMap="";
    // Стоимость
    String fare="";

// Для отмены заявки
    // заголовок Alert причины отмены
    String TitleWhyDellOder="Укажите причину отмены";
    // Причина отмены
    String[] CancelOderWhy ={"Самолет отменён","Передумал", };

    // реф слова для процесса удаления заявки
    String timeOutBeforDel="";
    String proverkaBeforDel="";
    // для удаления из БД заявки
    FirebaseDatabase ggg;
    DatabaseReference mmm;
    // реф слова для проверки после удаления заявки
    String timeOutDel;
    String proverkaDel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakaz4_request);

        Calend1=findViewById(R.id.Calend1);
        textCarIsFine=findViewById(R.id.textCarIsFine);
        CarCondition=findViewById(R.id.CarCondition);
        progressBB=findViewById(R.id.progressBB);
        RefMap1=findViewById(R.id.RefMap1);
        RefPoint1=findViewById(R.id.RefPoint1);
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
        detailsOder=findViewById(R.id.detailsOder);
        BtnIfly=findViewById(R.id.BtnIfly);
        BtnIland=findViewById(R.id.BtnIland);
        messageForDriver=findViewById(R.id.messageForDriver);
        QRcode=findViewById(R.id.QRcode);
        TextQRcode=findViewById(R.id.TextQRcode);
        tVProgressB=findViewById(R.id.tVProgressB);
        progressB=findViewById(R.id.progressB);
        cancelOder=findViewById(R.id.cancelOder);

//Убираем все видимости
        // Скрыть заказ без Деталей
        VisibleOderNo();
        // Скрыть Статус Автомобиля
        VisibleSearchCarNo();
        //Скрыть детали заказа
        VisibleDetailesNo();
        //Скрыть Сообщение водителю Я Вылетел-Приземлился
        VisibleMessageDriverNo();
        //Скрыть QR code
        VisibleQRcodeNo();
//Показать Прогресс загрузки
        VisibleLoadingYes();

        //Экспорт СС номера из MainActivity начальная страница заставки
        Intent MainTOZakaz4=getIntent();
        phoneNew1=""+MainTOZakaz4.getStringExtra("phoneNew");
        Log.d(TAG, "phoneNew1: "+phoneNew1);

        //если значение не равно "null"
        if (!phoneNew1.equals("null")){
            phoneNew=phoneNew1;
            Log.d(TAG, "phoneNew: "+phoneNew);
        }

        //Экспорт СС номера из Zakaz3Finish после регистрации заявки или если нашлась старая заявка
        Intent Zakaz3FinishTOMain6=getIntent();
        phoneNew2=""+Zakaz3FinishTOMain6.getStringExtra("phoneRef");
        Log.d(TAG, "phoneNew2: "+phoneNew2);
        //если значение не равно "null"
        if (!phoneNew2.equals("null")){
            phoneNew=phoneNew2;
            Log.d(TAG, "phoneNew: "+phoneNew);
        }

        //Старт считывание заявки из БД
        // С выдержкой времени чтобы заявка успела записаться в БД (в nodJS)
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Метод считывания заявки
                readOder();
                Log.d(TAG, "Start ");

                // блокировка спящего режима экрана
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                // отмена блокировки спящего режима экрана
                //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

            }
        },4000);
    }
// ВИДИМОСТИ
    //Показать заказ (без Деталей)
    public void VisibleOderYes(){
        //Дата заказа
        Calend1.setVisibility(View.VISIBLE);
        //Маршрут заказа
        RefMap1.setVisibility(View.VISIBLE);
        //Текст пункт сбора
        text2.setVisibility(View.VISIBLE);
        // Кнопка-карта пункт сбора
        RefPoint1.setVisibility(View.VISIBLE);
        // Текст Ваше время сбора
        textTime.setVisibility(View.VISIBLE);
        // Кнопка-пояснение время пункта сбора
        BtnRefTime.setVisibility(View.VISIBLE);
        // Текст стоимость поездки
        text5.setVisibility(View.VISIBLE);
        // Цена поездки
        text6.setVisibility(View.VISIBLE);
        // Текст р./человек
        text7.setVisibility(View.VISIBLE);
        // Кнопка детали заказа
        detailsOder.setVisibility(View.VISIBLE);
        // Кнопка отменить заказ
        cancelOder.setVisibility(View.VISIBLE);
    }
    // Скрыть заказ без Деталей
    public void VisibleOderNo(){
        //Дата заказа
        Calend1.setVisibility(View.INVISIBLE);
        //Маршрут заказа
        RefMap1.setVisibility(View.INVISIBLE);
        //Текст пункт сбора
        text2.setVisibility(View.INVISIBLE);
        // Кнопка-карта пункт сбора
        RefPoint1.setVisibility(View.INVISIBLE);
        // Текст Ваше время сбора
        textTime.setVisibility(View.INVISIBLE);
        // Кнопка-пояснение время пункта сбора
        BtnRefTime.setVisibility(View.INVISIBLE);
        // Текст стоимость поездки
        text5.setVisibility(View.INVISIBLE);
        // Цена поездки
        text6.setVisibility(View.INVISIBLE);
        // Текст р./человек
        text7.setVisibility(View.INVISIBLE);
        // Кнопка детали заказа
        detailsOder.setVisibility(View.INVISIBLE);
        // Кнопка отменить заказ
        cancelOder.setVisibility(View.INVISIBLE);
    }

    //Показать Статус Автомобиля
    public void VisibleSearchCarYes(){
        //если автомобиль не найден
        if(сarDrive.equals("null")){
            //Текст к вам подъедет автомобиль
            textCarIsFine.setVisibility(View.INVISIBLE);
            //Текст
            CarCondition.setText("Идет поиск автомобиля");
            CarCondition.setVisibility(View.VISIBLE);
            // прогресс
            progressBB.setVisibility(View.VISIBLE);
        }
        else {
            //Текст к вам подъедет автомобиль
            textCarIsFine.setVisibility(View.VISIBLE);
            //Текст
            CarCondition.setText("Mazda"+"цвет серый"+"гос номер"+"777");
            CarCondition.setVisibility(View.VISIBLE);
            // прогресс
            progressBB.setVisibility(View.INVISIBLE);
            // показать сообщения для водителя
            VisibleMessageDriverYes();
            //Показать QR code
            VisibleQRcodeYes();
        }

    }
    // Скрыть Статус Автомобиля
    public void VisibleSearchCarNo(){
        //Текст к вам подъедет автомобиль
        textCarIsFine.setVisibility(View.INVISIBLE);
        //Текст
        CarCondition.setVisibility(View.INVISIBLE);
        // прогресс
        progressBB.setVisibility(View.INVISIBLE);
    }

    //Показать детали заказа В АЭРОПОРТ
    public void VisibleDetailesInAirYes(){
        // Текст самолет
        text3.setVisibility(View.VISIBLE);
        // Самолет
        RefplaneCity1.setVisibility(View.VISIBLE);
        //Текст дата вылета
        TextDateFly.setVisibility(View.VISIBLE);
        // Дата вылета
        dateFly.setVisibility(View.VISIBLE);
        // Текст время вылета
        text4.setText("Время вылета");
        text4.setVisibility(View.VISIBLE);
        // Время вылета
        time1.setVisibility(View.VISIBLE);

    }
    //Показать детали заказа ИЗ АЭРОПОРТА
    public void VisibleDetailesFromAirYes(){
        // Текст самолет
        text3.setVisibility(View.VISIBLE);
        // Самолет
        RefplaneCity1.setVisibility(View.VISIBLE);
        //Текст дата вылета
        TextDateFly.setVisibility(View.VISIBLE);
        // Дата вылета
        dateFly.setVisibility(View.VISIBLE);
        // Текст время вылета
        text4.setText("Время прилета");
        text4.setVisibility(View.VISIBLE);
        // Время вылета
        time1.setVisibility(View.VISIBLE);

    }
    //Показать детали заказа ЧАРТЕР ИЗ ИГАРКИ
    public void VisibleDetailesCharterYes(){
        // Текст самолет
        text3.setVisibility(View.VISIBLE);
        // Самолет
        RefplaneCity1.setVisibility(View.VISIBLE);
        //Текст дата вылета
        TextDateFly.setVisibility(View.VISIBLE);
        // Дата вылета
        dateFly.setVisibility(View.VISIBLE);
        // Текст время вылета
        text4.setVisibility(View.INVISIBLE);
        // Время вылета
        time1.setVisibility(View.INVISIBLE);
    }

    //Скрыть детали заказа
    public void VisibleDetailesNo(){
        // Текст самолет
        text3.setVisibility(View.INVISIBLE);
        // Самолет
        RefplaneCity1.setVisibility(View.INVISIBLE);
        //Текст дата вылета
        TextDateFly.setVisibility(View.INVISIBLE);
        // Дата вылета
        dateFly.setVisibility(View.INVISIBLE);
        // Текст время вылета
        text4.setVisibility(View.INVISIBLE);
        // Время вылета
        time1.setVisibility(View.INVISIBLE);
    }

    //Показать Сообщение водителю Я Вылетел-Приземлился
    public void VisibleMessageDriverYes(){
        //Текст сообщение водителю
        messageForDriver.setVisibility(View.VISIBLE);
        //Кнопка Я вылетел
        BtnIfly.setVisibility(View.VISIBLE);
        //Кнопка Я приземлился
        BtnIland.setVisibility(View.VISIBLE);

    }
    //Скрыть Сообщение водителю Я Вылетел-Приземлился
    public void VisibleMessageDriverNo(){
        //Текст сообщение водителю
        messageForDriver.setVisibility(View.INVISIBLE);
        //Кнопка Я вылетел
        BtnIfly.setVisibility(View.INVISIBLE);
        //Кнопка Я приземлился
        BtnIland.setVisibility(View.INVISIBLE);
    }

    //Показать QR code
    public void VisibleQRcodeYes(){
        //Текст Покажите QRcode Водителю
        TextQRcode.setVisibility(View.VISIBLE);
        //QRcode
        QRcode.setVisibility(View.VISIBLE);
    }
    //Скрыть QR code
    public void VisibleQRcodeNo(){
        //Текст Покажите QRcode Водителю
        TextQRcode.setVisibility(View.INVISIBLE);
        //QRcode
        QRcode.setVisibility(View.INVISIBLE);

    }

    //Показать Прогресс загрузки
    public void VisibleLoadingYes(){
        //Текст получение данных
        tVProgressB.setText("получение данных");
        tVProgressB.setVisibility(View.VISIBLE);
        //прогресс бар
        progressB.setVisibility(View.VISIBLE);
    }
    //Скрыть Прогресс загрузки
    public void VisibleLoadingNo(){
        //Текст получение данных
        tVProgressB.setVisibility(View.INVISIBLE);
        //прогресс бар
        progressB.setVisibility(View.INVISIBLE);
    }

    //Показать Прогресс удаление заказа
    public void VisibleRemoveYes(){
        //Текст получение данных
        tVProgressB.setText("удаление заказа");
        tVProgressB.setVisibility(View.VISIBLE);
        //прогресс бар
        progressB.setVisibility(View.VISIBLE);
    }
    //Скрыть Прогресс удаление заказа
    public void VisibleRemoveNo(){
        //Текст получение данных
        tVProgressB.setVisibility(View.INVISIBLE);
        //прогресс бар
        progressB.setVisibility(View.INVISIBLE);
    }

// КНОПКИ
    // кнопка "время сбора нажми на меня" (подробности формирования времени сбора)
    public void BtnRefTime(View view) {
    showAlertDialog6();
    }
    // Детали заявки
    public void detailsOder(View view){
        // показать детали
        if(detailsOder.getText().toString().equals("детали заказа")) {
            // если Чартер из Игарки
            if (time.equals("1 рейс")||time.equals("2 рейс")||time.equals("3 рейс")){
                VisibleDetailesCharterYes();
            }
            else {
                // Если маршрут в Аэропорт
                if(!timeOfPoint.equals(time)){
                    VisibleDetailesInAirYes();
                }
                else {
                    // Если маршрут из Аэропорта
                    VisibleDetailesFromAirYes();
                }
            }
            detailsOder.setText("скрыть");
        }
        // скрыть детали
        else {
            VisibleDetailesNo();
            detailsOder.setText("детали заказа");
            Log.d(TAG, "3detailsOder: "+detailsOder.getText().toString());
        }
    }
    // Отменить заказ
    public void cancelOder(View view){
        // Alert укажите причину отмены заявки
        AlertWhyDell();

    }

//МЕТОДЫ
    // Метод считывания заявки
    public void readOder(){
        // реф слова для метода считывания заявки
        timeOut="";
        proverka="";

        //ТАЙМ-АУТ ЗАПРОСА ИНТЕРНЕТА
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                timeOut="Out";
                // время проверки интернета вышло
                cheskTimeInternet();
            }
        },20000);

        //Важно в БД с читаемым объектом не должно быть параллельных линий :)
        // только тогда считывает значения с первого раза без null
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        Query query = rootRef.child("Пользователи")
                .child("Personal")
                .child(phoneNew)
                .child("Status")
                .orderByChild("Status");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ddd : dataSnapshot.getChildren()) {

                    // Дата точки сбор*дата вылета(прилета)
                    dateOfPointANDCalend= (String) ddd.child("дата").getValue();
                    // Cамолет
                    RefplaneCity=""+ddd.child("направление").getValue();
                    // Время точки сбор*время вылета(прилета)
                    timeOfPointANDtime=""+ddd.child("рейс_самолета").getValue();
                    // Маршрут*стоимость
                    RefMapANDfare=""+ddd.child("маршрут_номер").getValue();
                    // Точка сбора
                    RefPoint=""+ddd.child("маршрут_точкаСбора").getValue();
                    // Найденный Автомобиль
                    сarDrive=""+ddd.child("Автомобиль").getValue();
                    // Человек в заявке (пока что не используется но в БД есть)
                    Integer peopleOder=ddd.child("Человек_в_Заявке").getValue(Integer.class);

                    Log.d(TAG, "Дата точки сбор*дата вылета(прилета)"+dateOfPointANDCalend);
                    Log.d(TAG, "Cамолет"+RefplaneCity);
                    Log.d(TAG, "Время точки сбор*время вылета(прилета)"+timeOfPointANDtime);
                    Log.d(TAG, "Маршрут*стоимость"+RefMapANDfare);
                    Log.d(TAG, "Точка сбора"+RefPoint);
                    Log.d(TAG, "Найденный Автомобиль"+сarDrive);
                    Log.d(TAG, "Человек в заявке"+peopleOder);

                    //данные считаны реф значение становится не равно нулю
                    proverka=dateOfPointANDCalend;

                    // проверка вышло ли время проверки интернета
                    checkWordProverka();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);
    }
    // время проверки интернета вышло
    public void cheskTimeInternet(){
        if (!proverka.isEmpty()){
            Log.d(TAG, "время проверки интернета вышло, но данные считаны");
        }
        else{
            Log.d(TAG, "нет интернета при поиске заявок");
            // Alert нет интернента
            AlertNotInternet();
        }
    }
    // проверка вышло ли время проверки интернета
    public void checkWordProverka(){
        if (timeOut.equals("Out")){
            Log.d(TAG, "статус получен но время вышло");
        }
        else if(!proverka.isEmpty()){
            Log.d(TAG, "Заявка считана");
            // запуск метода заполнения активити
            StartShowActivity();
        }
    }
    // запуск метода заполнения активити
    public void StartShowActivity(){
        // обнуляем данные т.к. при повторном выполнении метода (когда пропал интернет) они удваиваются
        dateOfPoint="";
        Calend="";
        timeOfPoint="";
        time="";
        RefMap="";
        fare="";
// ИДЕНТИФИКАЦИЯ ДАННЫХ
    // Выделяем дату точки сбора и дату вылета-прилета
        Log.d(TAG, " старт FOR Data");
        // определяем длину слова
        int i =dateOfPointANDCalend.length();
        for (int a=0;a<i;a++){
            // Находим элемент * в слове
            String b=""+dateOfPointANDCalend.charAt(a);
            if(b.equals("*")){
                // составляем слово до *
                for (int x=0;x<a;x++){
                    // дата точки сбора
                    dateOfPoint=dateOfPoint+dateOfPointANDCalend.charAt(x);
                }
                Log.d(TAG, " дата точки сбора "+dateOfPoint);

                // составляем слово после *
                // дата вылета
                for (int z=a+1;z<dateOfPointANDCalend.length();z++){
                    // дата вылета-прилета
                    Calend=Calend+dateOfPointANDCalend.charAt(z);
                }
                Log.d(TAG, " дата вылета-прилета "+Calend);
            }
        }
    // Выделяем время точки сбора и время вылета-прилета
        Log.d(TAG, " старт FOR time");
        // определяем длину слова
        int q =timeOfPointANDtime.length();
        for (int w=0;w<q;w++){
            // Находим элемент * в слове
            String k=""+timeOfPointANDtime.charAt(w);
            if(k.equals("*")){
                // составляем слово до *
                for (int l=0;l<w;l++){
                    // составляем слово после *
                    // время точки сбора
                    timeOfPoint=timeOfPoint+timeOfPointANDtime.charAt(l);
                }
                Log.d(TAG, " время точки сбора "+timeOfPoint);

                // время вылета-прилета
                for (int z=w+1;z<timeOfPointANDtime.length();z++){
                    // дата вылета-прилета
                    time=time+timeOfPointANDtime.charAt(z);
                }
                Log.d(TAG, " время вылета-прилета "+time);
            }
        }
    // Выделяем маршрут и стоимость поездки
        Log.d(TAG, " старт FOR Map");
        // определяем длину слова
        int m =RefMapANDfare.length();
        for (int n=0;n<m;n++){
            // Находим элемент * в слове
            String b=""+RefMapANDfare.charAt(n);
            if(b.equals("*")){
                // составляем слово до *
                for (int x=0;x<n;x++){
                    // Маршрут
                    RefMap=RefMap+RefMapANDfare.charAt(x);
                }
                Log.d(TAG, " Маршрут "+RefMap);

                // составляем слово после *
                // стоимость проезда
                for (int z=n+1;z<RefMapANDfare.length();z++){
                    // дата вылета-прилета
                    fare=fare+RefMapANDfare.charAt(z);
                }
                Log.d(TAG, " стоимость проезда "+fare);
            }
        }

// ЗАПОЛНЕНИЕ АКТИВИТИ
        // Если чартер из Игарки Чартер
        if (time.equals("1 рейс")||time.equals("2 рейс")||time.equals("3 рейс")){
            Log.d(TAG, "Рейс из Игарки Чартер");
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
            // если рейс в Аэропорт (определяем по неравенству времени точки сбора и времени вылета)
            if (!timeOfPoint.equals(time)){
                Log.d(TAG, "Рейс в Аэропорт");
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
                // Текст дата вылета из Игарки
                TextDateFly.setText("Дата вылета:");
                // дата вылета
                dateFly.setText(Calend);
                // время вылета вылета
                time1.setText(time);
            }
            // если рейс из Аэропорта
            else {
                Log.d(TAG, "Рейс из Аэропорта");
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
        //Показать активити без деталей
        VisibleOderYes();
        //Показать Статус Автомобиля
        VisibleSearchCarYes();
        //Скрыть Прогресс загрузки
       VisibleLoadingNo();

       // отмена блокировки спящего режима экрана
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

// МЕТОДЫ УДАЛЕНИЯ ЗАЯВКИ
    //проверка интернета перед удалением (считывание YES-NO)
    public void CheskInternetbeforDellOder(){
        // реф слова для процесса удаления заявки
        timeOutBeforDel="";
        proverkaBeforDel="";

        //ТАЙМ-АУТ проверка интернета
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Завершен ТАЙМ-АУТ проверка интернета
                timeOutBeforDel="Out";
                // время проверки интернета вышло
                internetNotBeforDel();
            }
        },20000);

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

                    proverkaBeforDel=yesNo;
                    Log.d(TAG, "инетрнет есть, заявка есть?"+yesNo);

                    // проверка YES NO перед удалением
                    StartDellOder();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);
    }
    // время проверки интернета вышло при Удалении заявки
    public void internetNotBeforDel(){
        if (!proverkaBeforDel.isEmpty()){
            Log.d(TAG, "время проверки интернета вышло, но интернет есть");
        }
        else{
            Log.d(TAG, "Время проверки вышло, not internet");
            // Alert нет Интернета при Удалении заявки
            showAlertDialog4();
        }
    }
    // проверка YES NO перед удалением
    public void StartDellOder(){
        if (timeOutBeforDel.equals("Out")){
            Log.d(TAG, "интернет есть, но время проверки вышло");
        }
        else if (proverkaBeforDel.equals("No")){
            // этот пункт вообще не должен появляться но на всякий случай сделал защиту
            Log.d(TAG, "Ошибка сервера");
            //Alert ошибка сервера
            AlertMistakeServer();
        }
        else if (proverkaBeforDel.equals("null")){
            // этот пункт вообще не должен появляться но на всякий случай сделал защиту
            Log.d(TAG, "Ошибка сервера");
            //Alert ошибка сервера
            AlertMistakeServer();
        }
        else if (proverkaBeforDel.equals("Yes")){
            Log.d(TAG, "старт удаления");
            // Старт удаления заявки
            DeleteOder();
        }
    }
    // Старт удаления
    public void DeleteOder(){
        ggg = FirebaseDatabase.getInstance();
        mmm = ggg.getReference("Пользователи")
                .child("Personal")
                .child(phoneNew)
                .child("Status")
                .child("Status");
        mmm.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "запрос удаления отправлен в БД");
                mmm.child("Dell").setValue("Oder");

                //задержка на проверку YES-NO из БД после удаления
                checkDellOderWithTime();
                // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ
                mmm.removeEventListener(this);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    //задержка на проверку YES-NO из БД после удаления
    public void checkDellOderWithTime(){
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // проверка удалилась заявка или нет
                checkDellOder();
                Log.d(TAG, "проверка удаления");
            }
        }, 3000);
    }
    // проверка удалилась заявка или нет
    public void checkDellOder(){
        timeOutDel="";
        proverkaDel="";

        //ТАЙМ-АУТ ЗАПРОСА YesNo
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {

                // Завершен ТАЙМ-АУТ ЗАПРОСА YesNo
                timeOutDel="Out";
                // пропал интернет при считывании YES-NO  после удаления заявки
                internetNotDel();
            }
        },55000); // такое большое время чтобы точно завершился циклический процесс удаления

        //Важно в БД с читаемым объектом не должно быть параллельных линий :)
        // только тогда считывает значения с первого раза без null
        DatabaseReference rootRef1 = FirebaseDatabase.getInstance().getReference();
        Query query1 = rootRef1.child("Пользователи")
                .child("Personal")
                .child(phoneNew)
                .child("Proverka")
                .orderByChild("Oder");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ddd : dataSnapshot.getChildren()) {

                    String YesNo=ddd.child("Заявка").getValue(String.class);

                    proverkaDel=YesNo;
                    Log.d(TAG, "Получаем статус"+YesNo);

                    // Проверка YesNo после удаления
                    checkYesNo();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        query1.addListenerForSingleValueEvent(valueEventListener);
    }
    // пропал интернет при считывании YES-NO  после удаления заявки
    public void internetNotDel(){
        if (!proverkaDel.isEmpty()){
            Log.d(TAG, "время удаления вышло, но опрос получен");
        }
        else{
            Log.d(TAG, "Время считывании YES-NO после удаления вышло нет интернета");
            //Alert ошибка сервера приложение будет перезагружено при считывании YES_NO после удалении
            AlertMistakeServer();
        }
    }
    // Проверка YesNo после удаления
    public void checkYesNo(){
        if (timeOutDel.equals("Out")) {
            Log.d(TAG, "Время удаления вышло но запрос YesNo получен");
        }
        else if (proverkaDel.equals("Yes")){
            Log.d(TAG, "удаление не завершено повторяем цикл проверки");
            //цикл еще раз проверка удаления на YesNo
            checkDellOderWithTime();
        }
        else if (proverkaDel.equals("No")){
            Toast.makeText(Zakaz4Request.this,"Заявка Отменена....",Toast.LENGTH_LONG).show();
            Log.d(TAG, "Заявка удалена");

            // переход в лист заказов после удаления заявки
            GoZaka1();
        }
    }

// ALERT DIALOG
    // подробности как формируется время сбора
    public void showAlertDialog6() {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz4Request.this);
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
    // Alert нет интернента при считывании данных
    public void AlertNotInternet(){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz4Request.this);
        mAlertDialog.setTitle("Слабый сигнал интернета!");
        mAlertDialog.setMessage("Нажмите ОК, чтобы поробовать еще раз.");
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // Метод считывания заявки
                    readOder();
                }
            });
        mAlertDialog
                .setNegativeButton("Закрыть приложение", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // закрытие приложения реальное
                        closeApp();
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
}
    // Alert укажите причину отмены заявки
    public void AlertWhyDell(){
        AlertDialog.Builder builder=new AlertDialog.Builder( Zakaz4Request.this );
        builder.setTitle(TitleWhyDellOder);
        builder.setItems(CancelOderWhy, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        //проверка интернета перед удалением
                        CheskInternetbeforDellOder();

                    //ВИЗУАЛИЗАЦИЯ
                        // Скрыть заказ без Деталей
                        VisibleOderNo();
                        // Скрыть Статус Автомобиля
                        VisibleSearchCarNo();
                        // Скрыть детали заказа
                        VisibleDetailesNo();
                        // Скрыть QRcode
                        VisibleQRcodeNo();
                        //Скрыть Сообщение водителю Я Вылетел-Приземлился
                        VisibleMessageDriverNo();
                        //Показать Прогресс удаления заказа
                        VisibleRemoveYes();

                        // блокировка спящего режима экрана
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                        // отмена блокировки спящего режима экрана
                        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


                    }
                }
        ).setNeutralButton("Назад", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    // Alert нет Интернета при Удалении заявки
    public void showAlertDialog4(){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz4Request.this);
        mAlertDialog.setTitle("Слабый сигнал интернета!");
        mAlertDialog.setMessage("Нажмите ОК, чтобы поробовать еще раз.");
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //проверка интернета перед удалением
                        CheskInternetbeforDellOder();
                    }
                });
        mAlertDialog
                .setNegativeButton("Закрыть приложение", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // закрытие приложения реальное
                        closeApp();
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }
    //Alert ошибка сервера приложение будет перезагружено при получении NO и null при считывании YES_NO перед удалением, либо после удалении
    public void AlertMistakeServer(){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(
                Zakaz4Request.this);
        mAlertDialog.setTitle("Ошибка сервера");
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setMessage("Приложение будет перезапущено")
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // перезагрузка приложение
                        reStartApp();
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }
    // Alert ловушка неперехода на Zakaz1
    public void AlertZakaz2(){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz4Request.this);
        mAlertDialog.setTitle("Заявка удалена");
        mAlertDialog.setMessage("Нажмите ОК, для продолжения.");
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Переход в Zaka1 для выбора маршрута
                        GoZaka1();
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }


// ПЕРЕХОДЫ на ДР Активити
    // закрытие приложения реальное
    public void closeApp(){
    // закрытие приложения реальное
    finishAffinity();
    System.exit(0);
}
    // перезагрузка приложение
    public void reStartApp(){
        Intent GoMainActivity= new Intent(this,MainActivity.class);
        startActivity(GoMainActivity);
    }
    // переход в лист заказов после удаления заявки
    public void GoZaka1(){
        Intent Zakaz4ToZakaz1  = new Intent(this,Zakaz1.class);
        // отправляем phoneNew в Zakaz1
        Zakaz4ToZakaz1.putExtra("regFromMain6",phoneNew);
        startActivity(Zakaz4ToZakaz1);

        // Alert ловушка неперехода на Zakaz1
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Alert ловушка неперехода на Zakaz1
                AlertZakaz2();
            }
        },1000);
    }

}
