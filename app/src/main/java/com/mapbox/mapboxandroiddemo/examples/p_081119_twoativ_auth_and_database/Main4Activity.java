package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Main4Activity extends AppCompatActivity {

    TextView set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);


        set=findViewById(R.id.set);
        set.setText(R.string.data);


        String text= (String) getText(R.string.vois);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String user_id=user.getPhoneNumber();
        DatabaseReference myRef=database.getInstance().getReference().child("Users").child("Customers").child(user_id).child("Flights");
        myRef.setValue(text);
    }
}
