package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main4Activity extends AppCompatActivity {

    EditText Name,Email,Phone,Username,Password;
    Button btnInsert;
    FirebaseDatabase database;
    DatabaseReference ref;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);



        Name = findViewById(R.id.Name);
        Phone = findViewById(R.id.Phone);
        Email = findViewById(R.id.Email);
        Username = findViewById(R.id.Username);
        Password = findViewById(R.id.Password);
        btnInsert = findViewById(R.id.btnInsert);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Пользователь");
        user = new User();
    }

    private void  getValues(){

        user.setName(Name.getText().toString());
        user.setEmail(Email.getText().toString());
        user.setPhone(Phone.getText().toString());
        user.setUserName(Username.getText().toString());
        user.setPassword(Password.getText().toString());
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
}
