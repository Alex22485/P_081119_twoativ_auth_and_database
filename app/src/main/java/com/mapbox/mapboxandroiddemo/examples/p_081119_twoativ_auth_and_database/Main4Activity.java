package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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




public class Main4Activity extends AppCompatActivity {

    FirebaseAuth mAuth;
    String userID;
    String userI;


    Button btnInsert,btnStatus;
    TextView Flight;
    FirebaseDatabase database;
    DatabaseReference ref;

    FirebaseDatabase ddd;
    DatabaseReference ggg;

    FirebaseDatabase nextdatabase;
    DatabaseReference nextref;
    DatabaseReference nextref2;
    DatabaseReference nextref3;

    User user;

    //Новая ветка в базе Пользователи
    UserTwo userTwo;

    String Phone;

    /*TextView proba;
   ПРОБА
   Button btnout;
   TextView Calendout;
   TextView Flightout;*/


    // ADD Calendar
    Button choisData;
    TextView Calend;
    int year;
    int month;
    int dayOfmonth;
    Calendar calendar;
    DatePickerDialog datePickerDialog;


    // Выбрать номер рейса Старый вариант
    /*Button fly_1;
    Button fly_2;
    Button fly_3;
    Button fly_4;*/

    //Выбрать номер рейса Новый вариант
    String[] listFlights = {"1","2","3","4"};
    String newToken;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        // Получить Токен!!!! Работает с показом на экане
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(Main4Activity.this,new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                newToken = instanceIdResult.getToken();
                Log.d("TAG", newToken);
                // Показ на экране Toast.makeText(Main4Activity.this, newToken, Toast.LENGTH_SHORT).show();
            }
        });










      /* btnout=findViewById(R.id.btnout);
     Calendout=findViewById(R.id.Calendout);
     Flightout=findViewById(R.id.Flightout);
       proba=findViewById(R.id.proba);
        String ada=( valueOf( Calendout ) );*/

        Flight = findViewById(R.id.Flight);
        btnStatus=findViewById( R.id.btnStatus );




        // Выбрать номер рейса Старый вариант
        /*fly_1 =(Button)findViewById(R.id.fly_1);
        fly_2 =(Button)findViewById(R.id.fly_2);
        fly_3 =(Button)findViewById(R.id.fly_3);
        fly_4 =(Button)findViewById(R.id.fly_4);*/


// ADD Calendar
        choisData=(Button)findViewById(R.id.choisData);
        Calend=findViewById(R.id.Calend);
        btnInsert = findViewById(R.id.btnInsert);

        user = new User();
        userTwo=new UserTwo(  );










//Выбрать номер рейса старый вариант
      /*  fly_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Flight.setText("1");
            }
        });
        fly_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Flight.setText("2");
            }
        });
        fly_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Flight.setText("3");
            }
        });
        fly_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Flight.setText("4");
            }
        });*/



        choisData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                calendar=Calendar.getInstance();
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH);
                dayOfmonth=calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog=new DatePickerDialog(Main4Activity.this,
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



// добавить телефон пользователя в базу
        /*FirebaseUser phone = FirebaseAuth.getInstance().getCurrentUser();
        String addphone=phone.getPhoneNumber();
        Phone=addphone;*/

        /*String addada=Calendout.getText().toString();
        ada=addada;*/

       /* if (Calend.getText().length() == 0){
            btnInsert.setEnabled( true );
        }*/
        /*Calend.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {
                btnInsert.setEnabled(Calend.length() > 0);
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            } @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        }); */
      /* if (Calend.getText().toString().equals( null )) {
        btnInsert.setEnabled( false );
        }
        else {btnInsert.setEnabled( true );
        }*/

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

            btnInsert.setEnabled(!calendInput.isEmpty()&& !flightInput.isEmpty() );
        }
        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    //Выбрать номер рейса Новый вариант
    public void btn_number_Flight (View view){



        AlertDialog.Builder builder=new AlertDialog.Builder( Main4Activity.this );
        builder.setTitle( "Выбирите Номер рейса");
        builder.setCancelable( false );
        builder.setItems( listFlights, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                Flight.setText(listFlights[which]);

            }
        } );
        AlertDialog dialog = builder.create();
        dialog.show();

         /*mAuth= FirebaseAuth.getInstance(  );
        FirebaseUser ghg=mAuth.getCurrentUser();

        //полуаем номер телефона пользователя
        userID=ghg.getPhoneNumber();
        userI=ghg.getUid();

        Query aaa=FirebaseDatabase.getInstance().getReference("Пользователи").child( userID ).child("Status")
                .orderByChild( userI );
        aaa.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String data=dataSnapshot.child( "дата" ).getValue(String.class);
                String map=dataSnapshot.child( "направление" ).getValue(String.class);
                String roar_number=dataSnapshot.child( "маршрут_номер" ).getValue(String.class);
                String flidht_number=dataSnapshot.child( "рейс_самолета" ).getValue(String.class);

                ddd = FirebaseDatabase.getInstance();
                ggg = ddd.getReference("Заявки")
                        .child(map)
                        .child(data)
                        .child(roar_number)
                        .child(flidht_number);
                //ggg.child( userI ).removeValue();
                // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД "вкладка "Заявки"
                ref.removeEventListener( this );

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
        });*/


    }



    private void  getValues(){

        user.setPhone(userID);
        user.setРейс(Flight.getText().toString());
        user.setДата(Calend.getText().toString());
        user.setЧисло( 1 );
        user.setToken( newToken );



        // Запись во вторую ветку БД Пользователи

        userTwo.setДата(Calend.getText().toString());
        userTwo.setНаправление("Аэропорт-Красноярск");
        userTwo.setМаршрут_номер("Маршрут 1");
        userTwo.setМаршрут_название("Аэропорт-КрасТэц");
        userTwo.setРейс_самолета(Flight.getText().toString());
        userTwo.setПоездки("число8");
        userTwo.setToken( newToken );
    }

    public void btnInsert (View view){





        mAuth= FirebaseAuth.getInstance(  );
        FirebaseUser ghg=mAuth.getCurrentUser();

        //полуаем номер телефона пользователя
        userID=ghg.getPhoneNumber();
        userI=ghg.getUid();

        Query aaa=FirebaseDatabase.getInstance().getReference("Пользователи").child( userID ).child("Status")
                .orderByChild( userI );
        aaa.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String data=dataSnapshot.child( "дата" ).getValue(String.class);
                String map=dataSnapshot.child( "направление" ).getValue(String.class);
                String roar_number=dataSnapshot.child( "маршрут_номер" ).getValue(String.class);
                String flidht_number=dataSnapshot.child( "рейс_самолета" ).getValue(String.class);

                ddd = FirebaseDatabase.getInstance();
                ggg = ddd.getReference("Заявки")
                        .child(map)
                        .child(data)
                        //.child(roar_number)
                        .child(flidht_number)
                        .child(roar_number)
                        .child("Users");

                // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД "вкладка "Заявки"
                ggg.removeEventListener( this );
                

            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                /*Удаление старых заявок, чтобы не было запараллеливания*/ ggg.child( userI ).removeValue();
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
        });


       /* mAuth= FirebaseAuth.getInstance(  );
        FirebaseUser ghg=mAuth.getCurrentUser();

        //полуаем номер телефона пользователя
        userID=ghg.getPhoneNumber();
        userI=ghg.getUid();

        Query aaa=FirebaseDatabase.getInstance().getReference("Пользователи").child( userID ).child("Status")
                .orderByChild( userI );
        aaa.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String data=dataSnapshot.child( "дата" ).getValue(String.class);
                String map=dataSnapshot.child( "направление" ).getValue(String.class);
                String roar_number=dataSnapshot.child( "маршрут_номер" ).getValue(String.class);
                String flidht_number=dataSnapshot.child( "рейс_самолета" ).getValue(String.class);

                ddd = FirebaseDatabase.getInstance();
                ggg = ddd.getReference("Заявки")
                        .child(map)
                        .child(data)
                        .child(roar_number)
                        .child(flidht_number);
                ggg.child( userI ).removeValue();

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
        });*/



        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Заявки")
                .child("Аэропорт-Красноярск" )
                .child( Calend.getText().toString() )
                //.child("Маршрут 1")
                .child(Flight.getText().toString()  )
                .child("Маршрут 1")
                .child("Users")
                .child(userI);
        ref.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getValues();

                FirebaseUser mmm = FirebaseAuth.getInstance().getCurrentUser();
                // база данных во главе ID пользователя далее дата и номер рейса
                String user_id = mmm.getUid();
                // база данных во главе телефон далее дата и номер рейса
                //String user_id = mmm.getPhoneNumber();

                //ref.child( user_id ).setValue( user );
                ref.child( userI ).setValue( user_id );
                Toast.makeText( Main4Activity.this, "Заявка принята....", Toast.LENGTH_LONG ).show();
                //Видимость кнопки Проверить статус
                btnStatus.setEnabled( true );


                // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД "вкладка "Заявки"
                ref.removeEventListener( this );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }


        } );

        FirebaseUser mmm = FirebaseAuth.getInstance().getCurrentUser();

        // база данных во главе ID пользователя далее дата и номер рейса
        String user_i = mmm.getPhoneNumber();

        //Новая ветка в базе Пользователи
        nextdatabase = FirebaseDatabase.getInstance();
        nextref = nextdatabase.getReference("Пользователи").child(user_i);
        nextref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getValues();

                //вызов
                FirebaseUser ccc = FirebaseAuth.getInstance().getCurrentUser();
                String nextuser_id = ccc.getUid();
                nextref.child("Status").child(nextuser_id).setValue(userTwo);
                //Toast.makeText(Main4Activity.this,"Заявка принята....",Toast.LENGTH_LONG).show();

                // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД "вкладка "Пользователи"
                nextref.removeEventListener( this );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        //Добавить вкладку История Поездок

        nextref = nextdatabase.getReference("Пользователи").child(user_i);
        nextref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nextref.child("History").setValue("Исторя поездок");

                // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД "вкладка "History
                nextref.removeEventListener( this );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //06.02.20 Проба новая структура для работы уведомления Nodjs
        FirebaseUser kkk = FirebaseAuth.getInstance().getCurrentUser();
        // база данных во главе ID пользователя далее дата и номер рейса
        String user_id = kkk.getUid();
        nextref2 = nextdatabase.getReference("Пользователи").child(user_id).child("notificationTokens");
        nextref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nextref2.child(newToken).setValue("true");

                // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД "вкладка "History
                nextref2.removeEventListener( this );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void btnStatus(View view){
        Intent zxz = new Intent( this,Main6Activity.class );
        startActivity( zxz);
    }

    // Блокировка кнопки Back!!!! :)))
    @Override
    public void onBackPressed(){
    }






   /* public void btnout (View view){
        final ListView lvMain=(ListView)findViewById(R.id.lv);
        // Проба ArrayList
        DatabaseReference rootRef=FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersdRef = rootRef.child("Пользователь");
       /* ValueEventListener eventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = ds.child("рейс").getValue(String.class);
                    Log.d("TAG", name);
                 // array.add(name);
                }
              // ArrayAdapter<String> adapter = new ArrayAdapter(Main4Activity.this, android.R.layout.simple_list_item_1, array);
               ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(Main4Activity.this,R.array.day_of_weeks,android.R.layout.simple_list_item_1);
                lvMain.setAdapter(adapter);
               // mListView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        usersdRef .addListenerForSingleValueEvent(eventListener);
        //Проба №3  orderByChild("дата").equalTo("22.11.2019")
     /*   DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Пользователь");
        Query queries=ref.orderByChild("дата").equalTo("22.11.2019");
        queries.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot nnn:dataSnapshot.getChildren()){
                    Flightout.setText(nnn.child("дата").getValue()+"   "+nnn.child("phone").getValue()+"   "+nnn.child("рейс").getValue());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });*/


    //ЕЩЕ одна ПРОБА
        /*FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("Пользователь");
        myRef.orderByChild("рейс").equalTo("3").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    Flightout.setText(childDataSnapshot.getKey()+"   "+childDataSnapshot.child("дата").getValue()+"   "+childDataSnapshot.child("phone").getValue());
                   // Log.d(TAG, "PARENT: "+ childDataSnapshot.getKey());
                   // Log.d(TAG,""+ childDataSnapshot.child("name").getValue());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });*/


//НОВАЯ ПРОБА

       /* DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Пользователь");
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ref.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data=dataSnapshot.child("дата").getValue().toString();
                String flight=dataSnapshot.child("рейс").getValue().toString();
                String phone=dataSnapshot.child("phone").getValue().toString();
                Flightout.setText(phone+" "+"Дата полета "+data+" "+"Рейс номер "+flight);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
       /* DatabaseReference data_one=FirebaseDatabase.getInstance().getReference("Пользователь");
        DatabaseReference data_two=FirebaseDatabase.getInstance().getReference().child("дата");
        //Проба
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("Пользователь");
        ValueEventListener valueEventListener= new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot mjm:dataSnapshot.getChildren()){
                    //String as =calendout.child("дата").getValue(String.class);
                    //  Calendout.setText(as);
                    // Проба
                    User uInfo = mjm.getValue(User.class);
                    String ppp = uInfo.getДата();
                    String ddd = uInfo.getPhone();
                    String fff= uInfo.getРейс();
                    Calendout.setText(ddd+"  "+ppp+"  "+"рейс№"+"  "+fff);
                    Flightout.setText(uid);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        //Проба
        usersRef.addListenerForSingleValueEvent(valueEventListener);
//Рабочие коды
       /* data_one.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot calendout:dataSnapshot.getChildren()){
                    String as =calendout.child("дата").getValue(String.class);
                    Calendout.setText(as);
                }
                for (DataSnapshot flightout:dataSnapshot.getChildren()){
                    String ad =flightout.child("рейс").getValue(String.class);
                    Flightout.setText(ad);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        })*/
    ;


       /* ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //FirebaseUser bbb = FirebaseAuth.getInstance().getCurrentUser();
               // FirebaseDatabase ass =FirebaseDatabase.getInstance();
             //  String user_id = bbb.getPhoneNumber();
                //String vvv=FirebaseDatabase.getInstance().getReference().child("Пользователь").child("дата").getKey();
             //  Flightout.setText(vvv);
                ;
               // Flightout.setText(user_id);
                DatabaseReference dfd = FirebaseDatabase.getInstance().getReference("Пользователь").child("дата");
                dfd.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String xzx=dataSnapshot.getValue(String.class);
                        Flightout.setText(xzx);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }*/
}