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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Zakaz4Request extends AppCompatActivity {

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
    String forIgarkaCharter="Вылетая из Игарки, сообщите водителю об этом. Только так он узнает время вашего прилета (Функция станет доступна, когда водитель будет найден).";
    // пояснение при рейсах В Аэропорт
    String inAirport="Время рассчитано к началу регистрации на рейс (т.е. за 2часа до вылета плюс время дороги до Аэропорта).";
    // пояснение при рейсах из Аэропорта
    String fromAirport="Водитель будет ждать вас в Аэропорту. Пожалуйста, перед вылетом в Красноярск сообщите ему об этом (Функция станет доступна, когда водитель будет найден).";
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
        //Скрыть детали заказа В АЭРОПОРТ
        VisibleDetailesInAirNo();
        //Скрыть Сообщение водителю Я Вылетел-Приземлился
        VisibleMessageDriverNo();
        //Скрыть QR code
        VisibleQRcodeNo();
//Показать Прогресс загрузки
        VisibleLoadingYes();

        //Экспорт СС номера из MainActivity начальная страница заставки
        Intent MainTOMain6=getIntent();
        phoneNew1=""+MainTOMain6.getStringExtra("phoneNew");
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
    //Скрыть детали заказа В АЭРОПОРТ
    public void VisibleDetailesInAirNo(){
        // Текст самолет
        text3.setVisibility(View.INVISIBLE);
        // Самолет
        RefplaneCity1.setVisibility(View.INVISIBLE);
        //Текст дата вылета
        TextDateFly.setVisibility(View.INVISIBLE);
        // Дата вылета
        dateFly.setVisibility(View.INVISIBLE);
        // Текст время вылета
        text4.setText("Время вылета");
        text4.setVisibility(View.INVISIBLE);
        // Время вылета
        time1.setVisibility(View.INVISIBLE);

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
    //Скрыть детали заказа ИЗ АЭРОПОРТА
    public void VisibleDetailesFromAirNo(){
        // Текст самолет
        text3.setVisibility(View.INVISIBLE);
        // Самолет
        RefplaneCity1.setVisibility(View.INVISIBLE);
        //Текст дата вылета
        TextDateFly.setVisibility(View.INVISIBLE);
        // Дата вылета
        dateFly.setVisibility(View.INVISIBLE);
        // Текст время вылета
        text4.setText("Время прилета");
        text4.setVisibility(View.INVISIBLE);
        // Время вылета
        time1.setVisibility(View.INVISIBLE);

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
    //Скрыть детали заказа ЧАРТЕР ИЗ ИГАРКИ
    public void VisibleDetailesCharterNo(){
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
    // кнопка детали заявки
    public void detailsOder(View view){

        Log.d(TAG, "1detailsOder: "+detailsOder.getText().toString());
        // показать детали
        // если Чартер из Игарки
        if(detailsOder.getText().toString().equals("детали заказа")) {
            if (time.equals("1 рейс")||time.equals("2 рейс")||time.equals("3 рейс")){
                VisibleDetailesCharterYes();
                detailsOder.setText("скрыть");
            }
            else {
                // Если маршрут в Аэропорт
                if(!timeOfPoint.equals(time)){
                    VisibleDetailesInAirYes();
                    detailsOder.setText("скрыть");
                }
                else {
                    // Если маршрут из Аэропорта
                    VisibleDetailesFromAirYes();
                    detailsOder.setText("скрыть");
                }
            }
            Log.d(TAG, "2detailsOder: "+detailsOder.getText().toString());
        }
        // скрыть детали
        else {
            VisibleDetailesFromAirNo();
            detailsOder.setText("детали заказа");
            Log.d(TAG, "3detailsOder: "+detailsOder.getText().toString());
        }
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
    // Alert нет интернента
    public void AlertNotInternet(){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz4Request.this);
        mAlertDialog.setTitle("Слабый сигнал интернета!");
        mAlertDialog.setMessage("Нажмите ОК, чтобы поробовать еще раз, либо закрыть приложение");
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


// ПЕРЕХОДЫ на ДР Активити
    // закрытие приложения реальное
    public void closeApp(){
    // закрытие приложения реальное
    finishAffinity();
    System.exit(0);
}

}
