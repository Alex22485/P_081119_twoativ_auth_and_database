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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Main6Activity extends AppCompatActivity {

    private static final String TAG ="Main6Activity" ;

    LinearLayout TextOder;
    LinearLayout TextProcess;
    LinearLayout TimeInDetailed;
    LinearLayout TextAboutTimeSerchDriver;
    LinearLayout QRcode;
    LinearLayout TextDetailed;

    String timeOut;
    String timeOutDel;
    String proverka;
    String proverkaDel;
    String phoneNew;
    String phoneNew1;
    String phoneNew2;

    String timeOutBeforDel;
    String proverkaBeforDel;


    FirebaseDatabase ggg;
    DatabaseReference mmm;

    Button cancelOder;
    Button detailsTrip;
    Button BtnPushTime;
    Button BtnDetailed;
    Button BtnShortly;

    String[] CancelOderWhy ={"Самолет отменён","Передумал", };


    TextView TextMain;
    TextView NamePlain;
    TextView flight_number_Out1;
    TextView flight_number_Out;
    TextView flight_number_Out2;
    TextView Textflight_number_Out2;
    TextView road_number_out;
    TextView road_name_out;
    TextView number;
    TextView searchCar;
    TextView TextNumberFromIgarkaCh;
    TextView NumberFromIgarkaCh;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main6 );


        Log.d(TAG, "onCreate");/*специально пусто*/

        TextMain = findViewById( R.id.TextMain );
        NamePlain = findViewById( R.id.NamePlain );
        progressBar = findViewById( R.id.progressBar );
        TimeInDetailed=findViewById(R.id.TimeInDetailed);
        TextAboutTimeSerchDriver=findViewById(R.id.TextAboutTimeSerchDriver);
        QRcode=findViewById(R.id.QRcode);
        TextDetailed=findViewById(R.id.TextDetailed);

        TextOder = findViewById( R.id.TextOder );
        TextProcess = findViewById( R.id.TextProcess );

        number = findViewById( R.id.number );
        flight_number_Out1=findViewById( R.id.flight_number_Out1 );
        flight_number_Out=findViewById( R.id.flight_number_Out );
        Textflight_number_Out2=findViewById( R.id.Textflight_number_Out2 );
        flight_number_Out2=findViewById( R.id.flight_number_Out2 );
        road_number_out=findViewById( R.id.road_number_out );
        road_name_out=findViewById( R.id.road_name_out );


        searchCar=findViewById( R.id.searchCar );
        cancelOder=findViewById( R.id.cancelOder );
        detailsTrip=findViewById( R.id.detailsTrip );
        BtnPushTime=findViewById( R.id.BtnPushTime );
        BtnDetailed=findViewById( R.id.BtnDetailed );
        BtnShortly=findViewById( R.id.BtnShortly );

        TextNumberFromIgarkaCh=findViewById( R.id.TextNumberFromIgarkaCh );
        NumberFromIgarkaCh=findViewById( R.id.NumberFromIgarkaCh );

        // Видимость всего текста "ПОДРОБНО"
        TextDetailed.setVisibility(View.INVISIBLE);

        //Видимость время сбора в "ПОДРОБНО"
        BtnPushTime.setVisibility(View.GONE);
        flight_number_Out.setVisibility(View.GONE);

        //Видимость номера рейса самолета в "ПОДРОБНО"
        TextNumberFromIgarkaCh.setVisibility(View.GONE);
        NumberFromIgarkaCh.setVisibility(View.GONE);

        // видимость время вылета самолета в "ПОДРОБНО"
        Textflight_number_Out2.setVisibility(View.GONE);
        flight_number_Out2.setVisibility(View.GONE);

        //видимость текста  30-180минут в "ПОДРОБНО"
        TextAboutTimeSerchDriver.setVisibility(View.GONE);

        // Видимость текста покажите QRкод в "Подробно"
        QRcode.setVisibility(View.GONE);

        // Видимость кнопки "Скрыть"
        BtnShortly.setVisibility(View.GONE);

        //Экспорт СС номера из MainActivity
        Intent MainTOMain6=getIntent();
        phoneNew1=""+MainTOMain6.getStringExtra("phoneNew");
        Log.d(TAG, "phoneNew1: "+phoneNew1);

        //если значение не равно "null"
        if (!phoneNew1.equals("null")){
            phoneNew=phoneNew1;
            Log.d(TAG, "phoneNew: "+phoneNew);
        }

        //Экспорт СС номера из Main3Activity
        Intent Main3ActivTOMain6=getIntent();
        phoneNew2=""+Main3ActivTOMain6.getStringExtra("phoneRef");
        Log.d(TAG, "phoneNew2: "+phoneNew2);


        //если значение не равно "null"
        if (!phoneNew2.equals("null")){
            phoneNew=phoneNew2;
            Log.d(TAG, "phoneNew: "+phoneNew);
        }



        TextProcess.setVisibility(View.VISIBLE);
        number.setText("получение данных...");

        //Старт Проверка интернета+статус заявок
        // С выдержкой времени чтобы заявка успела записаться в БД
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {

                readOder();
                Log.d(TAG, "Считывание Заявки");/*специально пусто*/
            }
            //сделано специально чтобы текст заявки успел записаться через nodJS
        },4000);
    }

    // Видимость текста "ПОДРОБНО"
    public void BtnDetailed (View view){
        TextDetailed.setVisibility(View.VISIBLE);
        BtnDetailed.setVisibility(View.GONE);
        BtnShortly.setVisibility(View.VISIBLE);
    }

    // Видимость текста "ПОДРОБНО"
    public void BtnShortly (View view){
        TextDetailed.setVisibility(View.INVISIBLE);
        BtnDetailed.setVisibility(View.VISIBLE);
        BtnShortly.setVisibility(View.GONE);
    }

    @Override
    protected void onStart (){
        super.onStart();
        Log.d(TAG, "onStart");
        }

    @Override
    protected void onStop (){
        super.onStop();
        Log.d(TAG, "onStop");
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

    public void readOder(){

        timeOut="";
        proverka="";

        //ТАЙМ-АУТ ЗАПРОСА ИНТЕРНЕТА
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {

                // Завершен ТАЙМ-АУТ ЗАПРОСА ИНТЕРНЕТА-2 при проверке регистрации
                timeOut="Out";
                internetNot();
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

                    String token=ddd.child("token").getValue(String.class);
                    Integer peopleOder=ddd.child("Человек_в_Заявке").getValue(Integer.class);
                    String data= (String) ddd.child("дата").getValue();
                    String roar_number= (String) ddd.child("маршрут_номер").getValue();
                    String road_name= (String) ddd.child("маршрут_точкаСбора").getValue();
                    String map= (String) ddd.child("направление").getValue();
                    String flidht_number= (String) ddd.child("рейс_самолета").getValue();
                    String сarDrive= (String) ddd.child("Автомобиль").getValue();

                    proverka=data;
                    Log.d(TAG, "Получаем статус"+data+map+roar_number+road_name+flidht_number+сarDrive+token);

                    road_number_out.setText( roar_number );
                    road_name_out.setText( road_name );
                    flight_number_Out1.setText( "в "+flidht_number );
                    flight_number_Out.setText( flidht_number );
                    flight_number_Out2.setText( flidht_number );
                    NumberFromIgarkaCh.setText( flidht_number );

                    if (map.equals("ИгаркаЧ")){
                        if(roar_number.equals("КрасТэц-Аэропорт")||
                                roar_number.equals("Щорса-Аэропорт")||
                                roar_number.equals("Ветлужанка-Аэропорт")||
                                roar_number.equals("Северный-Аэропорт")||
                                roar_number.equals("Канск-Аэропорт")||
                                roar_number.equals("Ачинск-Аэропорт")||
                                roar_number.equals("Сосновоборск-Аэропорт")){
                            NamePlain.setText(" Красноярск-Игарка(чартер)");
                            //Видимость Время вылета самолета в ""ПОДРОБНО"
                            Textflight_number_Out2.setVisibility(View.VISIBLE);
                            flight_number_Out2.setVisibility(View.VISIBLE);
                        }

                        if(roar_number.equals("Аэропорт-КрасТэц")||
                                roar_number.equals("Аэропорт-Щорса")||
                                roar_number.equals("Аэропорт-Ветлужанка")||
                                roar_number.equals("Аэропорт-Северный")||
                                roar_number.equals("Аэропорт-Канск")||
                                roar_number.equals("Аэропорт-Ачинск")||
                                roar_number.equals("Аэропорт-Сосновоборск")){
                            NamePlain.setText(" Игарка-Красноярск(чартер)");
                            //Видимость номера рейса самолета в "ПОДРОБНО"
                            NumberFromIgarkaCh.setVisibility(View.VISIBLE);
                            TextNumberFromIgarkaCh.setVisibility(View.VISIBLE);
                        }
                    }

                    if (сarDrive==null){
                        //видимость строки в "ПОДРОБНО"
                        TimeInDetailed.setVisibility(View.VISIBLE);

                        //видимость текста  30-180минут в "ПОДРОБНО"
                        TextAboutTimeSerchDriver.setVisibility(View.VISIBLE);

                        // видимость времени в "ПОДРОБНО"
                        if(roar_number.equals("КрасТэц-Аэропорт")||roar_number.equals("Щорса-Аэропорт")||roar_number.equals("Ветлужанка-Аэропорт")||roar_number.equals("Северный-Аэропорт")){
                            flight_number_Out.setVisibility(View.VISIBLE);
                        }
                        // // видимость кнопки ?Нажми в "ПОДРОБНО"
                        if(roar_number.equals("Аэропорт-КрасТэц")||roar_number.equals("Аэропорт-Щорса")||roar_number.equals("Аэропорт-Ветлужанка")||roar_number.equals("Аэропорт-Северный")){
                            BtnPushTime.setVisibility(View.VISIBLE);
                        }
                        Log.d(TAG, "Автомобиль не найден");/*специально пусто*/
                    }
                    else {
                        // Видимость текста покажите QRкод в "Подробно"
                        QRcode.setVisibility(View.GONE);

                        if(roar_number.equals("КрасТэц-Аэропорт")||roar_number.equals("Щорса-Аэропорт")||roar_number.equals("Ветлужанка-Аэропорт")||roar_number.equals("Северный-Аэропорт")){
                            flight_number_Out1.setVisibility(View.VISIBLE);
                        }

                        searchCar.setText("к Вам подъедет "+сarDrive);
                    }
                    // Проверяем закончилось ли время опроса time-out
                    checkWordProverka();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    public void internetNot(){
        if (!proverka.isEmpty()){
            Log.d(TAG, "время вышло, но статус получен");
        }
          else{
            Log.d(TAG, "Время поиска статуса вышло not internet");
            Intent Main6ActivityNotInternet  = new Intent(this,Main6ActivityNotInternet.class);
            startActivity(Main6ActivityNotInternet);
        }
    }

    public void checkWordProverka(){

        if (timeOut.equals("Out")){
            Log.d(TAG, "статус получен но время вышло");
        }
        else if(!proverka.isEmpty()){
            Log.d(TAG, "Заявка есть");

            TextProcess.setVisibility(View.GONE);
            TextOder.setVisibility(View.VISIBLE);
            TextMain.setText("Заказ на "+proverka);

        }
    }

// Отмена заявки
    public void cancelOder (View view){
        AlertDialog.Builder builder=new AlertDialog.Builder( Main6Activity.this );
        builder.setTitle( "Укажите причину отмены");
        //builder.setCancelable( false );
        builder.setItems(CancelOderWhy, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {


                        Log.d(TAG, "проверка интернета перед удалением");

                        TextOder.setVisibility(View.GONE);
                        TextProcess.setVisibility(View.VISIBLE);
                        number.setText( "процесс отмены заявки..." );

                        //проверка интернета перед удалением
                        CheskInt();
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

    public void CheskInt(){

        timeOutBeforDel="";
        proverkaBeforDel="";

        //ТАЙМ-АУТ проверка интернета
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {

                // Завершен ТАЙМ-АУТ проверка интернета
                timeOutBeforDel="Out";
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

                    // проверка YES NO
                    StartDellOder();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);

    }

    public void internetNotBeforDel(){

        if (!proverkaBeforDel.isEmpty()){
            Log.d(TAG, "время проверки интернета вышло, но интернет есть");
        }
        else{
            Log.d(TAG, "Время удаления вышло not internet");
            Toast.makeText(Main6Activity.this,"слабый сигнал, проверьте интернет....",Toast.LENGTH_LONG).show();

            TextMain.setText( "ошибка отмены заявки" );
            TextMain.setTextColor(getResources().getColor( R.color.colorMistakeDell));
            TextProcess.setVisibility(View.GONE);
            TextOder.setVisibility(View.VISIBLE);
        }
    }

    public void StartDellOder(){
        if (timeOutBeforDel.equals("Out")){
            Log.d(TAG, "интернет есть, но время проверки вышло");
        }
        else if (proverkaBeforDel.equals("Yes")){
            Log.d(TAG, "старт удаления");
            // удаления заявки
            DeleteOder();
        }
        else if (proverkaBeforDel.equals("No")){
            // этот пункт вообще не должен появляться но на всякий случай сделал защиту
            Log.d(TAG, "Ошибка в опросе");

            AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(
                    Main6Activity.this);
            // Set Title
            mAlertDialog.setTitle("Ошибка сервера");
            mAlertDialog.setCancelable(false);
            // Set Message
            mAlertDialog
                    .setMessage("")
                    .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            mistakeServer();
                        }
                    });
            mAlertDialog.create();
            // Showing Alert Message
            mAlertDialog.show();

        }

    }

    //этот пункт вообще не должен работать но на всякий случай сделал защиту
    public void mistakeServer (){
        Intent ddd=new Intent(this,MainActivity.class);
        startActivity(ddd);
    }

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

                //проверка удален или нет
                checkDellOderWithTime();
                // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ
                mmm.removeEventListener(this);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    //задержка на считывание YesNo
    public void checkDellOderWithTime(){
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {

                checkDellOder();
                Log.d(TAG, "проверка удаления");
            }
        }, 3000);
    }

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
                internetNotDel();
            }
        },15000);
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

                    // Проверяем YesNo
                    checkYesNo();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        query1.addListenerForSingleValueEvent(valueEventListener);

    }

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
            Toast.makeText(Main6Activity.this,"Заявка Отменена....",Toast.LENGTH_LONG).show();
            Log.d(TAG, "Заявка удалена");

            Intent Main6ToMain3  = new Intent(this,MainUserNewOne3.class);
            // отправляем Hello в для считывания в Main3Activity это вместо Hello которое берется из MainActivity(заставка)
            Main6ToMain3.putExtra("regFromMain6","Hello");
            startActivity(Main6ToMain3);
        }
        }

    public void internetNotDel(){
        if (!proverkaDel.isEmpty()){
            Log.d(TAG, "время удаления вышло, но опрос получен");
        }
        else{
            Log.d(TAG, "Время удаления вышло not internet");
            Intent Main6ActivityNotInternetAfterDellOder  = new Intent(this,Main6ActivityNotInternetAfterDellOder.class);
            startActivity(Main6ActivityNotInternetAfterDellOder);
        }
    }

    // кнопка Back сворачивает приложение
    @Override
    public void onBackPressed(){
        this.moveTaskToBack(true);
    }
}

//// Кнопка обновить информацию перезапуск активити
//public void btnStatus(View view) {
//        //перезапуск активити
//        restartActivity();
//}
//
//    public void restartActivity(){
//        Intent mIntent = getIntent();
//        finish();
//        startActivity(mIntent);
//    }