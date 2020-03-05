package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

    FirebaseDatabase proba;
    DatabaseReference proba2;

    FirebaseAuth mAuth;
    String userID;
    String userI;



    Button btnInsert,btnStatus,btn_number_Flight;
    TextView Flight;
    FirebaseDatabase database;
    FirebaseDatabase database01;
    FirebaseDatabase database02;
    DatabaseReference ref;
    DatabaseReference ref01;
    DatabaseReference ref02;

    FirebaseDatabase ddd;
    FirebaseDatabase ddd01;
    DatabaseReference ggg;
    DatabaseReference ggg01;

    FirebaseDatabase nextdatabase;
    DatabaseReference nextref;

    FirebaseDatabase new2;
    DatabaseReference new23;




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

    String data;
    String map;
    String roar_number;
    String flidht_number;
    String TVchoise_pointMap2;
    String token;

    String userPhone;
    String userid;


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
            }
        }
        );
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void  getValues(){

        // с 23.02.2020  используется Запись во первую ветку БД Заявки
        //user.setPhone(userID);
        //user.setUserI(userI);
        //user.setРейс(Flight.getText().toString());
        //user.setДата(Calend.getText().toString());
        user.setЧисло( 1 );
        //user.setToken( newToken );

        // Запись во вторую ветку БД Пользователи
        userTwo.setДата(Calend.getText().toString());
        userTwo.setНаправление(MapTop);
        userTwo.setМаршрут_номер(TVchoiseMap);
        userTwo.setМаршрут_точкаСбора(TVchoise_pointMap);
        userTwo.setРейс_самолета(Flight.getText().toString());
        userTwo.setToken( newToken );
    }

    public void btnInsert (View view){

        mAuth= FirebaseAuth.getInstance(  );
        FirebaseUser ghg=mAuth.getCurrentUser();

        //полуаем номер телефона пользователя
        userPhone=ghg.getPhoneNumber();
        userid=ghg.getUid();

        Intent nextList = getIntent();
        TVchoiseMap = nextList.getStringExtra( "TVchoiseMap" );
        TVchoise_pointMap = nextList.getStringExtra( "TVchoise_pointMap" );
        MapTop = nextList.getStringExtra( "mapTop" );

        //030320 Запись токена в БД ЗАЯВКИ
        database01 = FirebaseDatabase.getInstance();
        ref01 = database01.getReference( "Заявки" )
                .child( MapTop )
                .child( Calend.getText().toString() )
                .child( Flight.getText().toString() )
                .child( TVchoiseMap )
                .child( TVchoise_pointMap )
                .child( "notificationTokens" );
        ref01.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ref01.child( newToken ).setValue( userid );
                // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД "вкладка "newToken
                ref01.removeEventListener( this );
                Toast.makeText( Main3Activity.this, "Заявка принята....", Toast.LENGTH_LONG ).show();
                //Видимость кнопки Проверить статус
                btnStatus.setEnabled( true );
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
    @Override
    public void onBackPressed(){
    }
}