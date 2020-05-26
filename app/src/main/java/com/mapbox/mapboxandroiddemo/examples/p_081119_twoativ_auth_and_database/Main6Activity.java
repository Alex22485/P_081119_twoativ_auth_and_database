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


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class Main6Activity extends AppCompatActivity {

    private static final String TAG ="Main6Activity" ;

    LinearLayout TextOder;

    String timeOut;
    String timeOutDel;
    String proverka;
    String proverkaDel;

    String timeOutBeforDel;
    String proverkaBeforDel;


    FirebaseDatabase ggg;
    DatabaseReference mmm;

    Button cancelOder;
    Button detailsTrip;
    String userPhone;

    String[] CancelOderWhy ={"Самолет отменён","Передумал", };

    String token;

    FirebaseAuth mAuth;

    TextView Calend_Out;
    TextView flight_number_Out;
    TextView Map;
    TextView road_number_out;
    TextView road_name_out;
    TextView number;
    TextView people;
    TextView searchCar;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main6 );

        Log.d(TAG, "onCreate");/*специально пусто*/

        progressBar = findViewById( R.id.progressBar );
        TextOder = findViewById( R.id.TextOder );

        number = findViewById( R.id.number );
        Calend_Out=findViewById( R.id.Calend_Out );
        flight_number_Out=findViewById( R.id.flight_number_Out );
        Map=findViewById( R.id.Map );
        road_number_out=findViewById( R.id.road_number_out );
        road_name_out=findViewById( R.id.road_name_out );

        people=findViewById( R.id.people );

        searchCar=findViewById( R.id.searchCar );
        cancelOder=findViewById( R.id.cancelOder );
        detailsTrip=findViewById( R.id.detailsTrip );


        //полуаем phone пользователя
        mAuth= FirebaseAuth.getInstance(  );
        FirebaseUser user=mAuth.getCurrentUser();
        userPhone = user.getPhoneNumber();
        Log.d(TAG, "получен телефон"+userPhone);/*специально пусто*/

        // Получить Токен!!!!
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(Main6Activity.this,new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                token = instanceIdResult.getToken();
                Log.d(TAG, "получен токен: "+token);
            }
        });

        number.setText("Поиск Заявок...");
        progressBar.setVisibility(View.VISIBLE);

        //Старт Проверка интернета+статус заявок
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {

                readOder();
                Log.d(TAG, "Считывание Заявки");/*специально пусто*/
            }
        },500);


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
                .child(userPhone)
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

                    Calend_Out.setText( data );
                    Map.setText( map );
                    road_number_out.setText( roar_number );
                    road_name_out.setText( road_name );
                    flight_number_Out.setText( flidht_number );
                    people.setText(""+peopleOder);

                    if (сarDrive==null){
                        Log.d(TAG, "Автомобиль не найден");/*специально пусто*/
                    }
                    else {
                        searchCar.setText("Найден автомобиль "+сarDrive);
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

            number.setText( "заявка оформлена" );
            progressBar.setVisibility(View.INVISIBLE);
            TextOder.setVisibility(View.VISIBLE);

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

                        number.setText( "процесс удаления заявки" );
                        progressBar.setVisibility(View.VISIBLE);
                        TextOder.setVisibility(View.INVISIBLE);

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
                .child(userPhone)
                .child("Proverka")
                .orderByChild("Oder");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ddd : dataSnapshot.getChildren()) {

                    String yesNo=ddd.child("Заявка").getValue(String.class);

                    proverkaBeforDel=yesNo;
                    Log.d(TAG, "инетрнет есть, заявка есть?"+yesNo);

                    // начало удаления заявки
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
            Toast.makeText(Main6Activity.this,"not internet....",Toast.LENGTH_LONG).show();
//            Intent Main6ActivityNotInternetAfterDellOder  = new Intent(this,Main6ActivityNotInternetAfterDellOder.class);
//            startActivity(Main6ActivityNotInternetAfterDellOder);
        }
    }

    public void StartDellOder(){
        if (timeOutBeforDel.equals("Out")){
            Log.d(TAG, "интернет есть, но время проверки вышло");
        }
        else if (proverkaBeforDel.equals("Yes")){
            Log.d(TAG, "старт удаления");
            //цикл удаления заявки
            DeleteOder();
        }
        else if (proverkaBeforDel.equals("No")){
            //Toast.makeText(Main6Activity.this,"Заявка Отменена....",Toast.LENGTH_LONG).show();
            Log.d(TAG, "Ошибка в опросе");
            //setNotText();
            //progressBar.setVisibility(View.INVISIBLE);

            //Intent Choose_direction  = new Intent(this,Choose_direction.class);
            //startActivity(Choose_direction);

        }

    }

    public void DeleteOder(){
        ggg = FirebaseDatabase.getInstance();
        mmm = ggg.getReference("Заявки")
                .child(Map.getText().toString())
                .child(Calend_Out.getText().toString())
                .child(flight_number_Out.getText().toString())
                .child(road_number_out.getText().toString())
                .child(road_name_out.getText().toString())
                .child("notificationTokens");
        mmm.child(token).removeValue();
        Log.d(TAG, "запрос удаления отправлен в БД");

        //проверка удален или нет
        checkDellOderWithTime();
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
        }, 2500);
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
                .child(userPhone)
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

            Intent Choose_direction  = new Intent(this,Choose_direction.class);
            startActivity(Choose_direction);

        }

        }

    public void internetNotDel(){
        if (!proverkaDel.isEmpty()){
            Log.d(TAG, "время удаления вышло, но опрос получен");
        }
//        else if (proverka.isEmpty()){
        else{
            Log.d(TAG, "Время удаления вышло not internet");
            Toast.makeText(Main6Activity.this,"Время вышло not internet....",Toast.LENGTH_LONG).show();
//            Intent Main6ActivityNotInternetAfterDellOder  = new Intent(this,Main6ActivityNotInternetAfterDellOder.class);
//            startActivity(Main6ActivityNotInternetAfterDellOder);
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