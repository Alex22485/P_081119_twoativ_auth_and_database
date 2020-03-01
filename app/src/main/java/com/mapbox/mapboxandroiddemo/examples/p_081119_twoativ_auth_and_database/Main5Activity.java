package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main5Activity extends AppCompatActivity {

    // 28 02 2020 Лист Сформировать заявку Водителю и отправить ему уведомление о новом Заказе

    //List<String> basa=new ArrayList<String>(  );//можно так 1*
    ArrayList<String>driver=new ArrayList<String>(  );
    String[] array={};
    String  key;
    TextView proba;

      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main5 );

        proba = findViewById( R.id.proba );

//28 02 2020 Получить все ключи объекта по его значению "Водила" записать их в ArrayList и преобразовать в строковый массив array
          DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child( "Proba" );
          ref.orderByValue().equalTo( "Водила" ).addListenerForSingleValueEvent( new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  for (DataSnapshot snap: dataSnapshot.getChildren()){

                      //Преобразовываем ArrayList в обычный массив чтобы вставить его в AlertDialog
                      key = snap.getKey(); //получить все ключи значения
                      driver.add( key );
                      array = driver.toArray(new String[driver.size()]);

                      //String value = snap.getValue(String.class); //получить все значения, так побаловаться
                  }
              }
              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {

              }
          } );
        }

    //Выберите Водителя
    public void btn_number_Flight (View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder( Main5Activity.this );
        builder.setTitle( "Выберите Водителя" );
        // Отображает Водителей загруженных из БД
        builder.setItems( array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        proba.setText( array[which] );
                    }
                }
        );
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}




