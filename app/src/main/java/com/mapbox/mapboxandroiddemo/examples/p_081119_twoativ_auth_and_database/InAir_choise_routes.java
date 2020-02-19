package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InAir_choise_routes extends AppCompatActivity {

    TextView TVchoiseMap,TVchoise_pointMap;
    Button oneChoose,twoChoose,threeChoose,fourChoose;

    Button nextList;

    //Выбрать выбрать пункт Сбора Маршрута №1 КрасТэц-Аэропорт
    String[] pointOneMap = {"ДК КрасТЭЦ","Аэрокосмическая академия","Торговый центр","Предмостная пл."};

    //Выбрать выбрать пункт Сбора Маршрута №2 Щорса-Аэропорт
    String[] pointTwoMap={"Кинотеатр Металлург","Автобусный пер.","Пикра","Мебельная фабрика"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_in_air_choise_routes );

        TVchoiseMap = findViewById(R.id.TVchoiseMap);
        TVchoise_pointMap = findViewById(R.id.TVchoise_pointMap);
        oneChoose = findViewById(R.id.oneChoose);
        twoChoose = findViewById(R.id.twoChoose);
        threeChoose = findViewById(R.id.threeChoose);
        fourChoose = findViewById(R.id.fourChoose);

        // 1.1 Disable Button if Text is Empty
        nextList=findViewById(R.id.nextList);
        TVchoiseMap.addTextChangedListener( loginTextWather );
        TVchoise_pointMap.addTextChangedListener( loginTextWather );

    }

    //Выбрать выбрать пункт Сбора Маршрута №1 КрасТэц-Аэропорт
public  void oneChoose(View view) {
    AlertDialog.Builder builder=new AlertDialog.Builder( InAir_choise_routes.this );
    builder.setTitle( R.string.Первый5маршрут);
    builder.setCancelable( true );
    builder.setItems( pointOneMap, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int which) {

            TVchoiseMap.setText( R.string.Первый5маршрут );
            TVchoise_pointMap.setText(pointOneMap[which]);

        }
    } );
    AlertDialog dialog = builder.create();
    dialog.show();
}

    //Выбрать выбрать пункт Сбора Маршрута №2 Щорса-Аэропорт
    public  void twoChoose(View view) {
        AlertDialog.Builder builder=new AlertDialog.Builder( InAir_choise_routes.this );
        builder.setTitle( R.string.Второй5маршрут);
        builder.setCancelable( true );
        builder.setItems( pointTwoMap, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                TVchoiseMap.setText( R.string.Второй5маршрут );
                TVchoise_pointMap.setText(pointTwoMap[which]);
            }
        } );
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    // 1.2 Disable Button if Text is Empty
    private TextWatcher loginTextWather = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            String TVchoiseMapInput =TVchoiseMap.getText().toString().trim();
            String TVchoise_pointMapInput =TVchoise_pointMap.getText().toString().trim();

            nextList.setEnabled(!TVchoiseMapInput.isEmpty()&& !TVchoise_pointMapInput.isEmpty() );

        }
        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public void nextList(View view){
        Intent nextList = new Intent( this,Main3Activity.class );
        startActivity( nextList);
    }
}
