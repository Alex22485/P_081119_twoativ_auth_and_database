package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class Server1 extends AppCompatActivity {

    private static final String TAG ="Server1";

    ProgressBar progressBar;
    Button DataleIsFind; // Кнопка Найдены Самолеты
    Button MapIsFind;   // Кнопка Найдены Маршруты

    // показ календаря
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfmonth;
    Integer yearReferens;   // год выбранный календарем
    Integer mounthReferens; // месяц выбранный календарем
    Integer dayReferens;    // день выбранный календарем
    String dayRef;  // Итоговое значение дня в виде двух символов
    String monthRef;    // Итоговое значение месяца в виде двух символов
    String Calend;  //выбранная дата (из календаря)

    // данные для поиска Выбранной даты в БД
    String[] arrayDate={};
    ArrayList<String> date=new ArrayList<>(  );  // массив для даты-самолет-время (БД папка Date)

    // данные для поиска Маршрута (по выбанной дате*самотелет*время в БД)
    String[] arrayMap={};
    ArrayList<String> Map=new ArrayList<>(  );  // массив для поиска маршрута (БД папка Map)

    // реф значение из БД (дата точки сбора*дата вылета(прилета)№Самолет@время точки сбора*время вылета(прилета))
    String RefStringData;

    // Для идентификации(выделение слов) считанных из БД (ДАТА-САМОЛЕТ-ВРЕМЯ)
    String dateOfPointCalend; // дата точки сбора*дата вылета(прилета)
    String RefplaneCity;    // Самолет
    String timeofPoint;     // Время точки сбора*время вылета(прилета)
    Integer oneRefIneger;         //Позиция  Первого разделяющего знака №

    // Для идентификации(выделение слов) считанных из БД (Маршрут-Стоимость)
    String RefStringMap;
    String RefMap; //Маршрут
    String fare;    //стоимость

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server1);

        progressBar=findViewById(R.id.progressBar);
        DataleIsFind=findViewById(R.id.DataleIsFind);
        MapIsFind=findViewById(R.id.MapIsFind);
        // Скрыть Видимость процесса
        progressBar.setVisibility(View.INVISIBLE);
    }
// КНОПКИ
    // Кнопка выбрать Дату
    public void ChoisDate(View view){

        // Убрать доступность кнопки Найдена дата
        DataleIsFind.setEnabled(false);
        // Убрать доступность кнопки Маршруты выбранного самолета
        MapIsFind.setEnabled(false);

        calendar= Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        dayOfmonth=calendar.get(Calendar.DAY_OF_MONTH);

        //выбирая разные параметры style календарь отображается по разному
        //datePickerDialog=new DatePickerDialog(this,android.R.style.Theme_Light_NoTitleBar,
        // .....Zakaz1.this,AlertDialog.BUTTON_POSITIVE дает написать заголовок в календаре! УРА

        datePickerDialog=new DatePickerDialog(Server1.this, AlertDialog.BUTTON_POSITIVE,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        // данные для выбора даты точки сбора (только для маршрутов в АЭРОПОРТ)
                        yearReferens=year;
                        mounthReferens=month+1;
                        dayReferens=day;
                        // преобразования дня в два символа (Н-р 6->06)
                        if (day<10){
                            dayRef="0"+day;
                        }
                        else {
                            dayRef=""+day;
                        }
                        // преобразования месяца в два символа (Н-р 3->03)
                        if (month+1<10){
                            // +1 т.к. почему-то календарь заведомо занижает выбранный месяц на один
                            monthRef="0"+(month + 1);
                        }
                        else {
                            monthRef=""+(month + 1);
                        }
                        // итоговая дата вылета-прилета в виде слова
                        Calend=dayRef + " " + monthRef + " " + year;
                        Log.d(TAG, "ВЫБРАННАЯ ДАТА КАЛЕНДАРЯ: "+Calend);

                        // ПОИСК ВЫБРАННОЙ ДАТЫ В БД
                        findDate();
                    }
                },year,month,dayOfmonth);
        // Индивидуальный заголовок в календаре
        datePickerDialog.setTitle("Выберите дату");
        datePickerDialog.setCancelable(false);
        datePickerDialog.show();
    }
    // Кнопка Найденные самолеты
    public void DataleIsFind(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder( Server1.this );
        builder.setTitle( "Дата: "+Calend );
        builder.setItems( arrayDate, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        // Записываем выбранный самолет в String
                        RefStringData=arrayDate[which];
                        //Метод идентификации (выделения из реф RefString слова данных ДАТА-САМОЛЕТ-ВРЕМЯ)
                        identifyData();

                    }
                }
        );
        AlertDialog dialog = builder.create();
        dialog.show();


    }
    // Кнопка Найденные маршруты
    public void MapIsFind(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder( Server1.this );
        builder.setTitle( "Маршруты Самолета" );
        builder.setItems( arrayMap, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        // Записываем выбранный маршрут в String
                        RefStringMap=arrayMap[which];
                        //Метод идентификации (выделения из реф RefStringMap слова данных Маршрут-стоимость)
                        //identifyMap();
                        // переход на другой лист с заказом
                        GoServerApp_1();

                    }
                }
        );
        AlertDialog dialog = builder.create();
        dialog.show();


    }

// МЕТОДЫ
    // ПОИСК ВЫБРАННОЙ ДАТЫ В БД
    public void findDate(){
        // Показать Видимость процесса
        progressBar.setVisibility(View.VISIBLE);

        // обнуляем массив,  т.к. без этого в выпадающем списке дублируются данные при повторном считывании
        date.clear();
        // код ниже зачемто выл раньше активный я его убрал пока что
        //array = driver.toArray(new String[driver.size()]);

        // Получить все ключи объекта по его значению "Date" записать их в ArrayList и преобразовать в строковый массив array
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child("Заявки")
                .child("Date")
                .child(Calend);
        ref.orderByValue().equalTo("Date").addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap: dataSnapshot.getChildren()){

                    //Преобразовываем ArrayList в обычный массив чтобы вставить его в AlertDialog
                    //key = snap.getKey(); //получить все ключи значения
                    date.add(snap.getKey() ); //получить все ключи значения и поместить их в date
                    arrayDate = date.toArray(new String[0]);

                    //код ниже тоже рабочий но дает предупреждение от AndroidStudio
                    //array = driver.toArray(new String[driver.size()]);

                    Log.d(TAG, "date: "+date);

                    // Показать кнопку Найдена дата
                    DataleIsFind.setEnabled(true);
                    // Убрать Видимость процесса
                    progressBar.setVisibility(View.INVISIBLE);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        } );
    }
    //Метод идентификации (выделения из реф RefString слова данных ДАТА-САМОЛЕТ-ВРЕМЯ)
    public void identifyData(){
        // Обнуляем реф данные (для защиты от повторного выполнения метода)
        dateOfPointCalend=""; // дата точки сбора*дата вылета(прилета)
        RefplaneCity="";    // Самолет
        timeofPoint="";     // Время точки сбора*время вылета(прилета)

        // определяем длину слова
        int i =RefStringData.length();
        for (int a=0;a<i;a++){
            // Находим элемент № в слове
            String b=""+RefStringData.charAt(a);
            Log.d(TAG, " элемент в реф слове RefStringData "+b);
            if(b.equals("№")){
                // составляем слово до №
                for (int x=0;x<a;x++){
                    // дата точки сбора*дата вылета(Прилета)
                    dateOfPointCalend=dateOfPointCalend+RefStringData.charAt(x);
                }

                //Позиция  Первого разделяющего знака №
                oneRefIneger=a;
                int s=oneRefIneger+1;
                Log.d(TAG, " Позиция  Первого разделяющего знака № "+oneRefIneger);
                Log.d(TAG, " Позиция  ПОСЛЕ разделяющего знака № "+s);
            }
            // Находим элемент @ в слове
            if(b.equals("@")){
                // составляем слово от № до "@ (Рейс Самолета)
                for(int x=oneRefIneger+1;x<a;x++){
                    // Рейс Самолета
                    RefplaneCity=RefplaneCity+RefStringData.charAt(x);
                }
                // составляем слово после "@ (время точки сбора*время вылета(прилета))
                for(int z=a+1;z<RefStringData.length();z++){
                    // время точки сбора*время вылета(вылета)
                    timeofPoint=timeofPoint+RefStringData.charAt(z);
                }
            }
        }
        Log.d(TAG, " дата точки сбора*дата вылета(прилета) "+dateOfPointCalend);
        Log.d(TAG, " Самолет "+RefplaneCity);
        Log.d(TAG, " время точки сбора*время вылета(прилета) "+timeofPoint);

        // Метод поиска Маршрута (папка MAP в БД)
        findMap();
    }
    // Метод поиска Маршрута (папка MAP в БД)
    public void findMap(){
        // Показать Видимость процесса
        progressBar.setVisibility(View.VISIBLE);

        // обнуляем массив,  т.к. без этого в выпадающем списке дублируются данные при повторном считывании
        Map.clear();
        // код ниже зачемто выл раньше активный я его убрал пока что
        //array = driver.toArray(new String[driver.size()]);

        // Получить все ключи объекта по его значению "Date" записать их в ArrayList и преобразовать в строковый массив array
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child("Заявки")
                .child(dateOfPointCalend)
                .child(RefplaneCity)
                .child(timeofPoint)
                .child("Map");
        ref.orderByValue().equalTo("Map").addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap: dataSnapshot.getChildren()){

                    //Преобразовываем ArrayList в обычный массив чтобы вставить его в AlertDialog
                    //key = snap.getKey(); //получить все ключи значения
                    Map.add(snap.getKey() ); //получить все ключи значения и поместить их в Map
                    arrayMap = Map.toArray(new String[0]);

                    //код ниже тоже рабочий но дает предупреждение от AndroidStudio
                    //array = driver.toArray(new String[driver.size()]);

                    Log.d(TAG, "Map: "+Map);

                    // Показать кнопку Найдена дата
                    MapIsFind.setEnabled(true);
                    // Убрать Видимость процесса
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        } );
    }
    //Метод идентификации (выделения из реф RefStringMap слова данных Маршрут-стоимость)
    public void identifyMap(){
        // обнуляем реф данные
        RefMap=""; //Маршрут
        fare="";    //стоимость
        // определяем длину слова
        int i =RefStringMap.length();
        for (int a=0;a<i;a++){
            // Находим элемент * в слове
            String b=""+RefStringMap.charAt(a);
            Log.d(TAG, " элемент в реф слове RefStringMap "+b);
            if(b.equals("*")){
                // составляем слово до *
                for (int x=0;x<a;x++){
                    //Маршрут
                    RefMap=RefMap+RefStringMap.charAt(x);
                }

                // составляем слово после *
                for (int x=a+1;x<RefStringMap.length();x++){
                    //Стоимость
                    fare=fare+RefStringMap.charAt(x);
                }
            }
        }
        Log.d(TAG, " Маршрут "+RefMap);
        Log.d(TAG, " стоимость "+fare);

        // переход на лист заявок
        GoServerApp_1();
    }

// ПЕРЕХОДЫ
    // переход на лист заявок
    public void GoServerApp_1(){
        Intent nex = new Intent(this,ServApp_1.class);
        nex.putExtra("dateOfPointCalend",dateOfPointCalend);
        nex.putExtra("RefplaneCity",RefplaneCity);
        nex.putExtra("timeofPoint",timeofPoint);
        nex.putExtra("RefMap",RefStringMap);
        startActivity(nex);
    }

}