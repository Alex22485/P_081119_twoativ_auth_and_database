package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Website1 extends AppCompatActivity {
    private static final String TAG ="Website1" ;


    FirebaseDatabase database01;
    DatabaseReference ref01;
    String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website1);

        //isNetworkAvailable();
        cheskInternet();

//        if(!isNetworkAvailable()){
//            //Create an alertdialog
//            AlertDialog.Builder Checkbuilder=new  AlertDialog.Builder(Website1.this);
//            Checkbuilder.setIcon(R.drawable.ic_action_name);
//            Checkbuilder.setTitle("Error!");
//            Checkbuilder.setMessage("Check Your Internet Connection.");
//            //Builder Retry Button
//
//            Checkbuilder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int id) {
//                    //Restart The Activity
//                    Intent intent = getIntent();
//                    finish();
//                    startActivity(intent);
//
//                }
//            });
//
//            Checkbuilder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    finish();
//                }
//            }) ;
//
//            AlertDialog alert=Checkbuilder.create();
//            alert.show();
//
//        }
//
//
//
//        else {
//            if (isNetworkAvailable()){
//
//                Thread tr=new Thread(){
//                    public  void  run(){
//                        try {
//                            sleep(1000);
//                        }
//                        catch (Exception e){
//                            e.printStackTrace();
//                        }
//                        finally {
//                            startActivity(new Intent(Website1.this,Website.class));
//                            finish();
//                        }
//                    }
//                };
//                tr.start();
//
//            }
//        }



    }



//    private boolean isNetworkAvailable(){
//        ConnectivityManager connectivityManager=(ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo=connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo !=null;
//    }

    public boolean isNetworkAvailable(){

        //чтение из БД с правилом для любых пользователей
        database01 = FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Check")
                .child("Internet")
                .child("Work");
        ref01.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                // Чтение
                key=dataSnapshot.getValue(String.class);

                //временно для проверки
                Toast.makeText( Website1.this, "Интернет есть?  "+key, Toast.LENGTH_SHORT ).show();

                // с этой записью makeText появляется только один раз!!!!! ХОРОШО, блин не всегда :(((
                ref01.removeEventListener(this);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText( Website1.this, "Интернет есть?  "+key, Toast.LENGTH_SHORT ).show();
            }
        });

        return true;
    }

    public void cheskInternet(){
        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    Log.d(TAG, "connected");
                    Toast.makeText( Website1.this, "Интернет есть  ", Toast.LENGTH_SHORT ).show();
                } else {
                    Log.d(TAG, "not connected");
                    Toast.makeText( Website1.this, "Интернета нет  ", Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Listener was cancelled");
            }
        });
}
}
