package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InternetNot extends AppCompatActivity {

    //Используется для MainActivity

    String key;
    TextView TextUpdate;
    TextView TextMessage1;
    TextView TextMessage2;
    Button BtnUpdate;

    FirebaseDatabase database01;
    DatabaseReference ref01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_not);

        TextUpdate=findViewById(R.id.TextUpdate);
        TextMessage1=findViewById(R.id.TextMessage1);
        TextMessage1.setText("Кажется, что-то пошло не так!!!");
        TextMessage2=findViewById(R.id.TextMessage2);
        BtnUpdate=findViewById(R.id.BtnUpdate);

        TextMessage1.setVisibility(View.VISIBLE);
        TextMessage2.setVisibility(View.VISIBLE);
        BtnUpdate.setVisibility(View.VISIBLE);


    }

    // Кнопка обновить
    public void Update(View view){

        Intent ddd=new Intent(this,MainActivity.class);
        startActivity(ddd);
    }

    // Блокировка кнопки Back!!!! :)))
    @Override
    public void onBackPressed(){
    }
//    public void check(){
//        if (key.isEmpty()){
//            TextMessage1.setVisibility(View.VISIBLE);
//            TextMessage1.setText("упс... cоединение не успешно");
//            TextMessage2.setVisibility(View.VISIBLE);
//            BtnUpdate.setVisibility(View.VISIBLE);
//
//            TextUpdate.setVisibility(View.INVISIBLE);
//        }else {
//
//
//            AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(InternetNot.this);
//            // Set Title
//            mAlertDialog.setTitle("!!!");
//            mAlertDialog.setCancelable(false);
//            // Set Message
//            mAlertDialog
//                    .setMessage("cоединение восстановлено!")
//                    .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            //возвращаемся в главное меню (меню после заставки)
//                            backMainMenu();
//                        }
//                    });
//            mAlertDialog.create();
//            // Showing Alert Message
//            mAlertDialog.show();
//        }
//    }
//
//    public void backMainMenu (){
//        Intent backMainMenu =new Intent(this,Choose_direction.class);
//        startActivity(backMainMenu);
//    }

}
