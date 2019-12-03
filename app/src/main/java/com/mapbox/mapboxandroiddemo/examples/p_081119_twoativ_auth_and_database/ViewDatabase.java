package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewDatabase extends AppCompatActivity {

    // Activity Не Используется !!!!!!!

    private static final String TAG="ViewDatabase";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private String userID;
    private ListView mListView;
    

    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout. view_database_layout);

        mListView=findViewById( R.id.listwiew );
        mAuth=FirebaseAuth.getInstance(  );
        mFirebaseDatabase= FirebaseDatabase.getInstance();
        myRef=mFirebaseDatabase.getReference("Заявки")
                .child("Аэропорт-Красноярск")
                .child("8 12 2019")
                .child( "Маршрут 1" )
                .child( "Рейс номер 1" );
        FirebaseUser user=mAuth.getCurrentUser();
        userID=user.getUid();

        /*mAuthStateListener= new FirebaseAuth.AuthStateListener() {
           @Override
           public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

              FirebaseUser user =firebaseAuth.getCurrentUser();
                  if (user!=null){
                    Toast.makeText(getBaseContext(), "Удача Автоирзации"+user.getPhoneNumber(), Toast.LENGTH_LONG).show();
                  }
                  else {Toast.makeText(getBaseContext(), "Выход"+user.getPhoneNumber(), Toast.LENGTH_LONG).show();
                  }
           }
        };*/


        myRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }

    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds: dataSnapshot.getChildren()){
            UserInformation uInfo=new UserInformation();
            uInfo.setPhone( ds.child( userID ).getValue(UserInformation.class).getPhone() );
            ArrayList<String> array   = new ArrayList<>(  );
            array.add(uInfo.getPhone()  );
            ArrayAdapter   adapter=   new ArrayAdapter( this,android.R.layout.simple_list_item_1 );
            mListView.setAdapter( adapter );
            

            
        }
    }
}