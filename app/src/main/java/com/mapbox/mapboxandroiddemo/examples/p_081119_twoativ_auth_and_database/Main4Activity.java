package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

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


        // ПРОБА
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
    }

    private void  getValues(){
        user.setРейс(Flight.getText().toString());
        user.setДата(Calend.getText().toString());

    };

    public void btnInsert (View view){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getValues();

                FirebaseUser mmm = FirebaseAuth.getInstance().getCurrentUser();
                String user_id = mmm.getPhoneNumber();

                ref.child(user_id).setValue(user);
                Toast.makeText(Main4Activity.this,"Data Insert....",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    public void btnout (View view){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                FirebaseUser bbb = FirebaseAuth.getInstance().getCurrentUser();
                String user_id = bbb.getPhoneNumber();
                //String vvv=FirebaseDatabase.getInstance().getReference().child("дата").equals()
               Flightout.setText(user_id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

