package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.health.UidHealthStats;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.UidVerifier;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Main4Activity extends AppCompatActivity {

    Button btnInsert;
    TextView Flight;
    FirebaseDatabase database;
    DatabaseReference ref;
    User user;
    String Bnbn;

    //ПРОБА
    Button btnout;
    TextView Calendout;
    TextView Flightout;


    // ADD Calendar
    Button choisData;
    TextView Calend;
    int year;
    int month;
    int dayOfmonth;
    Calendar calendar;
    DatePickerDialog datePickerDialog;


    // Chois Data and number Flight
    Button fly_1;
    Button fly_2;
    Button fly_3;
    Button fly_4;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);





        btnout=findViewById(R.id.btnout);
        Calendout=findViewById(R.id.Calendout);
        Flightout=findViewById(R.id.Flightout);




        Flight = findViewById(R.id.Flight);

        fly_1 =(Button)findViewById(R.id.fly_1);
        fly_2 =(Button)findViewById(R.id.fly_2);
        fly_3 =(Button)findViewById(R.id.fly_3);
        fly_4 =(Button)findViewById(R.id.fly_4);


// ADD Calendar
        choisData=(Button)findViewById(R.id.choisData);
        Calend=findViewById(R.id.Calend);




        btnInsert = findViewById(R.id.btnInsert);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Пользователь");
        user = new User();



        fly_1.setOnClickListener(new View.OnClickListener() {
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
        });



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
                                Calend.setText(day + "." + (month + 1) + "." + year);
                            }
                        }, year,month,dayOfmonth);
                datePickerDialog.show();
            }
        });

// добавить телефон пользователя в базу
        FirebaseUser phone = FirebaseAuth.getInstance().getCurrentUser();
        String Phone=phone.getPhoneNumber();
        Bnbn=Phone;



    }

    private void  getValues(){

        user.setPhone(Bnbn);
        user.setРейс(Flight.getText().toString());
        user.setДата(Calend.getText().toString());


    };

    public void btnInsert (View view){

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getValues();

                FirebaseUser mmm = FirebaseAuth.getInstance().getCurrentUser();

                // база данных во главе ID пользователя далее дата и номер рейса
                String user_id = mmm.getUid();

                // база данных во главе телефон далее дата и номер рейса
                //String user_id = mmm.getPhoneNumber();

                ref.child(user_id).setValue(user);
                Toast.makeText(Main4Activity.this,"Data Insert....",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
    public void btnout (View view){





        DatabaseReference data_one=FirebaseDatabase.getInstance().getReference("Пользователь");

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
        });*/

    }
}

