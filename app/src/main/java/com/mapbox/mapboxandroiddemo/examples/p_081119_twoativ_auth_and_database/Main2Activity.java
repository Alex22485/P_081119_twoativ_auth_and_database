package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Collections;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {


    Button flight_1;
    Button flight_2;
    Button flight_3;
    Button flight_4;
    Button btn_sign_out;
    Button three_window;
    TextView setnumber;
    TextView settext;
    TextView word;
    Button choisData;
    int year;
    int month;
    int dayOfmonth;
    Calendar calendar;
    DatePickerDialog datePickerDialog;

    private static final int RC_SIGN_IN = 101;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //ADD number flight
        flight_1 =(Button)findViewById(R.id.flight_1);
        flight_2 =(Button)findViewById(R.id.flight_2);
        flight_3 =(Button)findViewById(R.id.flight_3);
        flight_4 =(Button)findViewById(R.id.flight_4);
        setnumber = findViewById(R.id.setnumber);
        word=findViewById(R.id.word);




       three_window = findViewById(R.id.three_window);
       three_window.setOnClickListener(this);


        //ADD number flight
        flight_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setnumber.setText("1");
            }
        });
        flight_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setnumber.setText("2");
            }
        });
        flight_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setnumber.setText("3");
            }
        });
        flight_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setnumber.setText("4");
            }
        });










//Add Calendar

        choisData=findViewById(R.id.choisData);
        settext=findViewById(R.id.settext);

        choisData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar=Calendar.getInstance();
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH);
                dayOfmonth=calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog=new DatePickerDialog(Main2Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                settext.setText(day + "." + (month + 1) + "." + year);
                            }
                        }, year,month,dayOfmonth);
                datePickerDialog.show();
            }
        });



        doPhoneLogin();

    }





    private void doPhoneLogin() {
        Intent intent = AuthUI.getInstance().createSignInIntentBuilder()
                .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                .setAvailableProviders(Collections.singletonList(
                        new AuthUI.IdpConfig.PhoneBuilder().build()))
                .setLogo(R.mipmap.ic_launcher)
                .build();

        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        btn_sign_out =findViewById(R.id.btn_sign_out);
        three_window =findViewById(R.id.three_window);




        if (requestCode == RC_SIGN_IN) {
            IdpResponse idpResponse = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(

                );
                showAlertDialog(user);
                btn_sign_out.setEnabled(true);
                choisData.setEnabled(true);

                flight_1.setEnabled(true);
                flight_2.setEnabled(true);
                flight_3.setEnabled(true);
                flight_4.setEnabled(true);
                three_window.setEnabled(true);

                String user_id = user.getPhoneNumber();
                DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(user_id);
                current_user_db.setValue(true);


























            } else {
                /**
                 *   Sign in failed. If response is null the user canceled the
                 *   sign-in flow using the back button. Otherwise check
                 *   response.getError().getErrorCode() and handle the error.
                 */
                Toast.makeText(getBaseContext(), "Ошибка Авторизации", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void showAlertDialog(FirebaseUser user) {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(
                Main2Activity.this);

        // Set Title
        mAlertDialog.setTitle("Авторизация успешна");

        // Set Message
        mAlertDialog.setMessage(" Номер телефона " + user.getPhoneNumber());

        mAlertDialog.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        mAlertDialog.create();

        // Showing Alert Message
        mAlertDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.three_window:
                Intent intent = new Intent(this,Main4Activity.class);
                startActivity(intent);
                // TODO Call second activity
                break;
            default:
                break;
        }
    }
}




