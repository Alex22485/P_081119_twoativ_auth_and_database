package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Main4Activity extends AppCompatActivity {

    private EditText settext;
    private Button knopka;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        mAuth = FirebaseAuth.getInstance();

        /*firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(Main4Activity.this, DriverMapActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };*/

        settext=findViewById(R.id.settext);
        knopka=findViewById(R.id.knopka);
        knopka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String rrr=settext.getText().toString();
                String user_id = mAuth.getCurrentUser().getUid();
                DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(user_id).child("name");
                current_user_db.setValue(rrr);
               /* mAuth.createUserWithEmailAndPassword(rrr,rrr).addOnCompleteListener(Main4Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(Main4Activity.this, "sign up error", Toast.LENGTH_SHORT).show();
                        }else{
                            String user_id = mAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(user_id).child("name");
                            current_user_db.setValue(rrr);
                        }
                    }
                });*/

            }
        });

       /* knopka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = settext.getText().toString();

                mAuth.signInWithEmailAndPassword(email,email).addOnCompleteListener(Main4Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(Main4Activity.this, "sign in error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });*/
    }


    /*@Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }
    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }*/
}











       /* settext=findViewById(R.id.settext);
        knopka=findViewById(R.id.knopka);

        knopka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rrr=settext.getText().toString();


               /*FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseDatabase database=FirebaseDatabase.getInstance();
               String user_id=user.getPhoneNumber();
                DatabaseReference myRef=database.getInstance().getReference().child("Users").child("Customers").child("Flights");
                myRef.setValue(rrr);*/







        /*Person person1=new Person();
        String app=person1.sett();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        String user_id=user.getPhoneNumber();
        DatabaseReference myRef=database.getInstance().getReference().child("Users").child("Customers").child(user_id).child("Flights");
        myRef.setValue(app);*/





        /*set=findViewById(R.id.set);
        set.setText(R.string.data);


        String text= (String) getText(R.string.vois);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String user_id=user.getPhoneNumber();
        DatabaseReference myRef=database.getInstance().getReference().child("Users").child("Customers").child(user_id).child("Flights");
        myRef.setValue(text);
    }
    /*class Person{

        String text= (String) getText(R.string.data);
        String sett(){

            String years=text;
            return years;
        }
    }*/