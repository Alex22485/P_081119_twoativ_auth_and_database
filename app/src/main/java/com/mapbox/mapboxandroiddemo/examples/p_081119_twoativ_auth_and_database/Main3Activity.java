package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.os.Handler;
import android.os.storage.StorageManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
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

import java.util.Calendar;

public class Main3Activity extends AppCompatActivity {



    FirebaseAuth mAuth;
    Button btnInsert,btnStatus,btn_number_Flight;
    TextView Flight;
    FirebaseDatabase database01;
    DatabaseReference ref01;
    User user;

    //Новая ветка в базе Пользователи
    UserTwo userTwo;

    // ADD Calendar
    Button choisData;
    TextView Calend;
    int year;
    int month;
    int dayOfmonth;
    Calendar calendar;
    DatePickerDialog datePickerDialog;

    //Выбрать номер рейса Новый вариант
    String[] listFlights = {"1","2","3","4"};
    String newToken;

    //1.1Put Extra form InAir_choise_routes
    String TVchoiseMap;
    String TVchoise_pointMap;
    String MapTop;
    String userPhone;
    String userid;

    // Для запрета заиси заявок от servApp (типо прием заявок окончен маршрут сформирован)
    String stopOder;
    TextView StopFromServerApp;
    TextView StopRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // Получить Токен!!!! Работает с показом на экане
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(Main3Activity.this,new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                newToken = instanceIdResult.getToken();
                //Log.d("TAG", newToken);
                // Показ на экране Toast.makeText(Main4Activity.this, newToken, Toast.LENGTH_SHORT).show();
            }
        });


        Flight = findViewById(R.id.Flight);
        btnStatus=findViewById( R.id.btnStatus );
        btn_number_Flight=findViewById( R.id.btn_number_Flight );
        StopFromServerApp=findViewById( R.id.StopFromServerApp );
        StopRef=findViewById( R.id.StopRef );
// ADD Calendar
        choisData=(Button)findViewById(R.id.choisData);
        Calend=findViewById(R.id.Calend);
        btnInsert = findViewById(R.id.btnInsert);

        user = new User();
        userTwo=new UserTwo(  );

        choisData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar=Calendar.getInstance();
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH);
                dayOfmonth=calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog=new DatePickerDialog(Main3Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Calend.setText(day + " " + (month + 1) + " " + year);
                    }
                    }, year,month,dayOfmonth);
                datePickerDialog.show();
            };
        }
        );

// Disable Button if Text is Empty
        Calend.addTextChangedListener( loginTextWather );
        Flight.addTextChangedListener( loginTextWather );

    }
    private TextWatcher loginTextWather = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            String calendInput =Calend.getText().toString().trim();
            String flightInput =Flight.getText().toString().trim();

            btn_number_Flight.setEnabled(!calendInput.isEmpty());
            btnInsert.setEnabled(!calendInput.isEmpty()&& !flightInput.isEmpty() );

        }
        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    //Выбрать номер рейса Новый вариант
    public void btn_number_Flight (View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder( Main3Activity.this );
        builder.setTitle( "Выберите Номер рейса" );
        builder.setCancelable( false );
        builder.setItems( listFlights, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                Flight.setText( listFlights[which] );
                StopRef.setText("");
                StopFromServerApp.setText("");
            }
        }
        );
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    // C 05032020 не используется вся работа идет в node js
    /*private void  getValues(){ // C 05032020 не используется
        user.setЧисло( 1 );
        // Запись во вторую ветку БД Пользователи
        userTwo.setДата(Calend.getText().toString());
        userTwo.setНаправление(MapTop);
        userTwo.setМаршрут_номер(TVchoiseMap);
        userTwo.setМаршрут_точкаСбора(TVchoise_pointMap);
        userTwo.setРейс_самолета(Flight.getText().toString());
        userTwo.setToken( newToken );
    }*/

    public void btnInsert (View view) {


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser ghg = mAuth.getCurrentUser();

        //полуачем номер телефона пользователя
        userPhone = ghg.getPhoneNumber();
        userid = ghg.getUid();

        Intent nextList = getIntent();
        TVchoiseMap = nextList.getStringExtra( "TVchoiseMap" );
        TVchoise_pointMap = nextList.getStringExtra( "TVchoise_pointMap" );
        MapTop = nextList.getStringExtra( "mapTop" );

        //030320 Запись токена для проверки Разрешения на запись заявки в БД ЗАЯВКИ...-...-...-"CheckStopOder"...
        database01 = FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Заявки")
                .child(MapTop)
                .child(Calend.getText().toString())
                .child(Flight.getText().toString())
                .child(TVchoiseMap)
                .child(TVchoise_pointMap)
                .child("CheckStopOder")
                .child(userid);
        ref01.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ref01.child(newToken).setValue(userid);

                // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД БД ЗАЯВКИ...-...-...-"CheckStopOder"...
                ref01.removeEventListener(this);

                //запускаем метод
                Qwery();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        }
        );
    }

    // Всплывающая информация "Заявка отклонена!!!"
    public void showAlertDialog() {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(
                Main3Activity.this);
        // Set Title
        mAlertDialog.setTitle("Заявка отклонена :(");
        // Set Message
        mAlertDialog
                .setMessage("Маршрут"+" "+TVchoiseMap+"."+" "+"Точка сбора"+" "+TVchoise_pointMap+" "+"уже сформирована. Проверьте другие точки сбора данного маршрута")
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        mAlertDialog.create();
        // Showing Alert Message
        mAlertDialog.show();
    }

    // Всплывающая информация "Заявка принята!!!"
    public void showAlertDialog2() {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(
                Main3Activity.this);
        // Set Title
        mAlertDialog.setTitle("Спасибо, заявка принята!!!");
        // Set Message
        mAlertDialog
                .setMessage("Ищем автомобиль..."+" "+"Вы получите уведомление о результате поиска")
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        mAlertDialog.create();
        // Showing Alert Message
        mAlertDialog.show();
    }

    public void Qwery(){
        // проверяем какое слово написано в объекте РазрешениеНаЗапись. Если Разрешено то запись заявки оформляется, если нет то заявка отклонена (процесс записи и отклонения выполнен в nod js function OderCheck)
         final Query aaa1= FirebaseDatabase.getInstance().getReference("Заявки")
                .child(MapTop)
                .child(Calend.getText().toString())
                .child(Flight.getText().toString())
                .child(TVchoiseMap)
                .child(TVchoise_pointMap)
                .child("Разрешение")
                .child(userid)
                .orderByChild("Разрешение");
        aaa1.addChildEventListener( new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String data=dataSnapshot.child( "РазрешениеНаЗапись" ).getValue(String.class);
                Log.d("TAG", "РазрешениеНаЗапись" + data);
                Toast.makeText( Main3Activity.this, "РазрешениеНаЗапись"+data, Toast.LENGTH_SHORT ).show();

                if(data.equals("Разрешено")){
                    showAlertDialog2();
                    StopFromServerApp.setText("Заявка Принята");
                    btnStatus.setEnabled(true);

                }
                else if (data.equals("Запрещено")){
                    showAlertDialog();
                    StopFromServerApp.setText("Заявка отклонена");
                }
                //Останавливаем прослушивание, чтобы не в приложении у другого пользователя не появлялась информация когда другой пользоваьель регистрирует заявку
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

    public void btnStatus(View view){
        Intent zxz = new Intent( this,Main6Activity.class );
        startActivity( zxz);
    }

    // Блокировка кнопки Back!!!! :)))
//    @Override
//    public void onBackPressed(){
//    }
}