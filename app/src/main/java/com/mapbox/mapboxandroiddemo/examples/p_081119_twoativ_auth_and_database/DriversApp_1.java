package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class DriversApp_1 extends AppCompatActivity {

    TextView DataOrder;
    TextView TimeOrder;
    TextView MapOrder;
    TextView RoutepOrder;
    TextView StopOrder1;
    TextView MenpOrder1;
    TextView StopOrder2;
    TextView MenpOrder2;
    TextView StopOrder3;
    TextView MenpOrder3;
    TextView StopOrder4;
    TextView MenpOrder4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivers_app_1);

        DataOrder=findViewById(R.id.DataOrder);
        TimeOrder=findViewById(R.id.TimeOrder);
        MapOrder=findViewById(R.id.MapOrder);
        RoutepOrder=findViewById(R.id.RoutepOrder);
        StopOrder1=findViewById(R.id.StopOrder1);
        MenpOrder1=findViewById(R.id.MenpOrder1);
        StopOrder2=findViewById(R.id.StopOrder2);
        MenpOrder2=findViewById(R.id.MenpOrder2);
        StopOrder3=findViewById(R.id.StopOrder3);
        MenpOrder3=findViewById(R.id.MenpOrder3);
        StopOrder4=findViewById(R.id.StopOrder4);
        MenpOrder4=findViewById(R.id.MenpOrder4);

    }
    public void checkOder (View view){
        Query aaa= FirebaseDatabase.getInstance().getReference("Drivers").child( "123Lexus" )
                .orderByChild( "Заявки" );
        aaa.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String time=dataSnapshot.child( "время" ).getValue(String.class);
                String date=dataSnapshot.child( "дата" ).getValue(String.class);
                String направление=dataSnapshot.child( "направление" ).getValue(String.class);
                String маршрут=dataSnapshot.child( "маршрут" ).getValue(String.class);
                String point1=dataSnapshot.child( "точкаСбора1" ).getValue(String.class);
                String point1Men=dataSnapshot.child( "точкаСбора1Чел" ).getValue(String.class);
                String point2=dataSnapshot.child( "точкаСбора2" ).getValue(String.class);
                String point2Men=dataSnapshot.child( "точкаСбора2Чел" ).getValue(String.class);
                String point3=dataSnapshot.child( "точкаСбора3" ).getValue(String.class);
                String point3Men=dataSnapshot.child( "точкаСбора3Чел" ).getValue(String.class);
                String point4=dataSnapshot.child( "точкаСбора4" ).getValue(String.class);
                String point4Men=dataSnapshot.child( "точкаСбора4Чел" ).getValue(String.class);




                DataOrder.setText(date);
                TimeOrder.setText(time);
                MapOrder.setText(направление);
                RoutepOrder.setText(маршрут);
                StopOrder1.setText(point1);
                MenpOrder1.setText(point1Men);
                StopOrder2.setText(point2);
                MenpOrder2.setText(point2Men);
                StopOrder3.setText(point3);
                MenpOrder3.setText(point3Men);
                StopOrder4.setText(point4);
                MenpOrder4.setText(point4Men);





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
        });
    }
}