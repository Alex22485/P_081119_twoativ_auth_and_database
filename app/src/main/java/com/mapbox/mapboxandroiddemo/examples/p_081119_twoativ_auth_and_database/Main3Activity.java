package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import java.util.Calendar;


public class Main3Activity extends AppCompatActivity {

    FirebaseAuth mAuth;
    Button btnInsert;
    Button btn_number_Flight;
    TextView Flight;
    FirebaseDatabase database01;
    DatabaseReference ref01;
    // ADD Calendar
    Button choisData;
    TextView Calend;

    TextView TextProcess;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Flight = findViewById(R.id.Flight);
        btn_number_Flight=findViewById( R.id.btn_number_Flight );
        TextProcess=findViewById( R.id.TextProcess );

        // Получить Токен!!!!
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(Main3Activity.this,new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                newToken = instanceIdResult.getToken();
            }
        });

        //Проверка интернета
//        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//
//        if (networkInfo != null && networkInfo.isConnected()) {
//            // fetch data
//        } else {
//            new AlertDialog.Builder(this)
//                    .setTitle("Connection Failure")
//                    .setMessage("Please Connect to the Internet")
//                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                        }
//                    })
//                    .setIcon(android.R.drawable.ic_dialog_alert)
//                    .show();
//        }


// ADD Calendar
        choisData=(Button)findViewById(R.id.choisData);
        Calend=findViewById(R.id.Calend);
        btnInsert = findViewById(R.id.btnInsert);
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

    // Disable Button if Text is Empty
    TextWatcher loginTextWather = new TextWatcher() {
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

    //Выбрать номер рейса
    public void btn_number_Flight (View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder( Main3Activity.this );
        builder.setTitle( "Выберите Номер рейса" );
        builder.setCancelable( false );
        builder.setItems( listFlights, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                Flight.setText( listFlights[which] );
            }
        }
        );
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // кнопка регистрация
    public void btnInsert (View view) {

        //проверка есть ли интернет
        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            btnInsert.setVisibility(View.INVISIBLE);
            TextProcess.setText("процесс регистрации...");
            mAuth = FirebaseAuth.getInstance();
            FirebaseUser ghg = mAuth.getCurrentUser();
            //полуачем номер телефона пользователя
            userPhone = ghg.getPhoneNumber();
            userid = ghg.getUid();
            //Экспорт данных из др активити
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


        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Ошибка!!!")
                    .setMessage("Пожалуйста, проверьте соединение с сетью")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            TextProcess.setText("ошибка загрузки данных");
                            btnInsert.setVisibility(View.VISIBLE);
                            //запуск метода выдачи ошибки если через 5 секунд не придет ответ от БД
                            //getInternetCheck();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }



    }

    public void   getInternetCheck(){
        //задержка запроса из БД для полного завершения функций из nod js
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                String a=TextProcess.getText().toString();
                String b="";
                if(a.equals(b)){}
                else{
                TextProcess.setText("Ошибка регистрации...");
                showAlertDialog4();}
                btnInsert.setVisibility(View.VISIBLE);
            }
        },5000);


    }

    // Всплывающая информация "Заявка отклонена!!!"
    public void showAlertDialog() {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Main3Activity.this);
        // Set Title
        mAlertDialog.setTitle("Заявка отклонена :(");
        mAlertDialog.setCancelable(false);
        // Set Message
        mAlertDialog
                .setMessage("Точка сбора"+" "+TVchoise_pointMap+" "+"уже сформирована. Проверьте другие, ближайшие к вам точки сбора данного маршрута")
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onBackList();
                    }
                });
        mAlertDialog.create();
        // Showing Alert Message
        mAlertDialog.show();
    }
    // Всплывающая информация "Заявка оформлена!!!"
    public void showAlertDialog1() {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Main3Activity.this);
        // Set Title
        mAlertDialog.setTitle("Спасибо, заявка оформлена!!!");
        mAlertDialog.setCancelable(false);
        // Set Message
        mAlertDialog
                .setMessage("Ищем автомобиль..."+" "+"Вы получите уведомление о результате поиска")
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //переход в окно статуса лист 6
                        onStatusList();
                    }
                });
        mAlertDialog.create();
        // Showing Alert Message
        mAlertDialog.show();
    }
    // Всплывающая информация "Повтор!!!"
    public void showAlertDialog3() {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(
                Main3Activity.this);
        // Set Title
        mAlertDialog.setTitle("!!!");
        mAlertDialog.setCancelable(false);
        // Set Message
        mAlertDialog
                .setMessage("По этому направлению вы уже зарегистрированы ранее")
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //переход в окно статуса лист 6
                        onStatusList();
                    }
                });
        mAlertDialog.create();
        // Showing Alert Message
        mAlertDialog.show();
    }

    // Всплывающая информация "ошибка регистрации Нет Интернета!!!"
    public void showAlertDialog4() {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(
                Main3Activity.this);
        // Set Title
        mAlertDialog.setTitle("Ошибка");
        mAlertDialog.setCancelable(false);
        // Set Message
        mAlertDialog
                .setMessage("Проверьте соединение интернета")
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //задержка
                        Handler handler1 = new Handler();
                        handler1.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                onBackList();
                            }
                        },10);

                    }
                });
        mAlertDialog.create();
        // Showing Alert Message
        mAlertDialog.show();
    }

    // Переход на лист Статуса
    public void onStatusList() {
        //Переход на лист Статуса
        Intent zxz = new Intent( this,Main6Activity.class );
        startActivity(zxz);
    }

    // Переход на лист выбора точки сбора
    public void onBackList() {
        //Переход на лист Статуса
        Intent Choose_direction = new Intent( this,Choose_direction.class );
        startActivity( Choose_direction);
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
                //Toast.makeText( Main3Activity.this, "РазрешениеНаЗапись"+data, Toast.LENGTH_SHORT ).show();

                if(data.equals("Разрешено")){
                    TextProcess.setText("");
                    showAlertDialog1();
                }
                else if (data.equals("Запрещено")){
                    TextProcess.setText("");
                    showAlertDialog();
                }
                else if (data.equals("Повтор")){
                    TextProcess.setText("");
                    showAlertDialog3();
                }
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

    public void btnStatus(View view){
        Intent zxz = new Intent( this,Main6Activity.class );
        startActivity( zxz);
    }

//    public void checkConnection()
//    {
//        ConnectivityManager connectivityManager=(ConnectivityManager)
//                this.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo wifi=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//        NetworkInfo  network=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//        if (wifi.isConnected())
//        {
//            //Internet available
//        }
//        else if(network.isConnected())
//        {
//            //Internet available
//        }
//        else
//        {
//            //Internet is not available
//        }
//    }

    // Блокировка кнопки Back!!!! :)))
//    @Override
//    public void onBackPressed(){
//    }
}