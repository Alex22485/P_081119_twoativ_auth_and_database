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

    //Выбрать выбрать пункт Сбора Маршрута №1 КрасТэц-Аэропорт-КрасТэц
    String[] pointOneMap = {"ДК КрасТЭЦ","Аэрокосмическая академия","Торговый центр","Предмостная пл."};

    //Выбрать выбрать пункт Сбора Маршрута №2 Щорса-Аэропорт-Щорса
    String[] pointTwoMap={"Кинотеатр Металлург","Автобусный пер.","Пикра","Мебельная фабрика"};


    TextView mapTop,oneMap,twoMap,treeMap,fourMap;

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

        // for Extra
        oneMap= findViewById(R.id.oneMap);
        twoMap= findViewById(R.id.twoMap);
        treeMap= findViewById(R.id.treeMap);
        fourMap= findViewById(R.id.fourMap);
        mapTop= findViewById(R.id.mapTop);

        //Put Extra form Main2Activity Определяет какое значение установть в название маршрута если в Main2Activity нажата В Аэропорт или из Аэропорта
        Intent nextListInAir_choise_routes =getIntent();
        String sMapTop =nextListInAir_choise_routes.getStringExtra( "Маршрут" );
        String sOneMap =nextListInAir_choise_routes.getStringExtra( "oneMap" );
        String sTwoMap =nextListInAir_choise_routes.getStringExtra( "twoMap" );
        String sTreeMap =nextListInAir_choise_routes.getStringExtra( "treeMap" );
        String sFourMap =nextListInAir_choise_routes.getStringExtra( "fourMap" );

        mapTop.setText( sMapTop );
        oneMap.setText( sOneMap );
        twoMap.setText( sTwoMap );
        treeMap.setText( sTreeMap );
        fourMap.setText( sFourMap );



    }

    //Выбрать выбрать пункт Сбора Маршрута КрасТэц-Аэропорт-КрасТэц
public  void oneChoose(View view) {
    AlertDialog.Builder builder=new AlertDialog.Builder( InAir_choise_routes.this );
    builder.setTitle( oneMap.getText().toString());
    builder.setCancelable( true );
    builder.setItems( pointOneMap, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int which) {

            TVchoiseMap.setText( oneMap.getText().toString() );
            TVchoise_pointMap.setText(pointOneMap[which]);

        }
    } );
    AlertDialog dialog = builder.create();
    dialog.show();

}

    //Выбрать выбрать пункт Сбора Маршрута Щорса-Аэропорт-Щорса
    public  void twoChoose(View view) {
        AlertDialog.Builder builder=new AlertDialog.Builder( InAir_choise_routes.this );
        builder.setTitle(twoMap.getText().toString());
        builder.setCancelable( true );
        builder.setItems( pointTwoMap, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                TVchoiseMap.setText( twoMap.getText().toString() );
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
        nextList.putExtra( "TVchoiseMap",TVchoiseMap.getText().toString() );
        nextList.putExtra( "TVchoise_pointMap",TVchoise_pointMap.getText().toString() );
        nextList.putExtra( "mapTop",mapTop.getText().toString() );
        startActivity( nextList);
    }
}
