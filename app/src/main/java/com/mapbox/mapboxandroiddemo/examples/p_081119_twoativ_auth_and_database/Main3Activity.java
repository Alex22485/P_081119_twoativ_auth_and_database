package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main3Activity extends AppCompatActivity implements ValueEventListener {

    private EditText HeadingInput;
    private TextView HeadingText;
    private RadioButton RbRED,RbBlue;
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference mRootReference = firebaseDatabase.getReference();
    private DatabaseReference mHeadingReference=mRootReference.child("heading");
    private DatabaseReference mFontColorReference=mRootReference.child("fontcolor");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        HeadingText =(TextView) findViewById(R.id.headingText);
        HeadingInput =(EditText) findViewById(R.id.headingInput);
        RbRED=(RadioButton)findViewById(R.id.rbRed);
        RbBlue=(RadioButton)findViewById(R.id.rbBlue);

    }

    public void submitHeading(View view){

        String heading =HeadingInput.getText().toString();
        mHeadingReference.setValue(heading);
        HeadingInput.setText("");


    }

    public void onRadioButtonClicked(View view)
    {

        switch (view.getId())

        {
            case R.id.rbRed:mFontColorReference.setValue("red");
            break;

            case R.id.rbBlue:mFontColorReference.setValue("Blue");
                break;



        }

    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        if (dataSnapshot.getValue(String.class)!=null)
        {
            String key =dataSnapshot.getKey();
            if (key.equals("heading"))
            {
                String heading=dataSnapshot.getValue(String.class);
                HeadingText.setText(heading);

            }
            else if(key.equals("fontcollor"))
        {
            String color=dataSnapshot.getValue(String.class);
            if(color.equals("red"))

            {
                HeadingText.setTextColor(ContextCompat.getColor(this, R.color.colorRed));
                RbRED.setChecked(true);
            }
                    else if (color.equals("blue"))
            {
                HeadingText.setTextColor(ContextCompat.getColor(this, R.color.colorBlue));
                RbBlue.setChecked(true);

            }

                }

            }
        }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
    @Override
    protected void onStart() {
        super.onStart();
        mHeadingReference.addValueEventListener(this);
        mFontColorReference.addValueEventListener(this);
    }
}
