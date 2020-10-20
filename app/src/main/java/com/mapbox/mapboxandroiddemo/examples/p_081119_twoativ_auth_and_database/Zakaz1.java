package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Zakaz1 extends AppCompatActivity {

    private static final String TAG ="Zakaz1" ;

    Button button1,button2,button3,button4;
    TextView Right1,Right2,Right3,Right4,Right5;
    TextView No1,No2,No3,No4,No5;
    TextView OderRight;

    String [] listCityTaxi= {"Красноярск","Сосновоборск","Ачинск","Канск","Лесосибирск"};
    String refCityTaxi,refFromInCity;
    String [] listCityFromIn= {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakaz1);

        button1=findViewById(R.id.button1);
        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);
        button4=findViewById(R.id.button4);
        Right1=findViewById(R.id.Right1);
        Right2=findViewById(R.id.Right2);
        Right3=findViewById(R.id.Right3);
        Right4=findViewById(R.id.Right4);
        Right5=findViewById(R.id.Right5);
        No1=findViewById(R.id.No1);
        No2=findViewById(R.id.No2);
        No3=findViewById(R.id.No3);
        No4=findViewById(R.id.No4);
        No5=findViewById(R.id.No5);
        OderRight=findViewById(R.id.OderRight);

        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);
        button4.setVisibility(View.INVISIBLE);
        Right1.setVisibility(View.INVISIBLE);
        Right2.setVisibility(View.INVISIBLE);
        Right3.setVisibility(View.INVISIBLE);
        Right4.setVisibility(View.INVISIBLE);
        Right5.setVisibility(View.INVISIBLE);
        No2.setVisibility(View.INVISIBLE);
        No3.setVisibility(View.INVISIBLE);
        No4.setVisibility(View.INVISIBLE);
        No5.setVisibility(View.INVISIBLE);
        OderRight.setVisibility(View.INVISIBLE);

        dinamicView();

    }

    // кнопка button1
    public void button1(View view){
        AlertChoisCity();
    }
    // кнопка button2
    public void button2(View view){

    }
    // кнопка button3
    public void button3(View view){

    }
    // кнопка button4
    public void button4(View view){

    }


    public void dinamicView(){

        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                button2.setVisibility(View.VISIBLE);
                No2.setVisibility(View.VISIBLE);
            }
        },300);

        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                button3.setVisibility(View.VISIBLE);
                No3.setVisibility(View.VISIBLE);
            }
        },600);

        Handler handler3 = new Handler();
        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                button4.setVisibility(View.VISIBLE);
                No4.setVisibility(View.VISIBLE);
            }
        },900);

        Handler handler4 = new Handler();
        handler4.postDelayed(new Runnable() {
            @Override
            public void run() {
                OderRight.setVisibility(View.VISIBLE);
                No5.setVisibility(View.VISIBLE);

                //выбор города
                AlertChoisCity();
                button1.setEnabled(true);
            }
        },1200);

    }
    public void AlertChoisCity(){


        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz1.this);
        mAlertDialog.setTitle("Выбери город");
        mAlertDialog
                .setItems(listCityTaxi, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        refCityTaxi=listCityTaxi[which];

                        //выбор в/из города
                        AlertFromInCity();
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }

    public void AlertFromInCity(){

        listCityFromIn= new String[]{refCityTaxi + "->Аэропорт","Аэропорт->"+refCityTaxi};

        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz1.this);
        mAlertDialog.setTitle("Выбери город");
        mAlertDialog
                .setItems(listCityFromIn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        refFromInCity=listCityFromIn[which];

                        No1.setVisibility(View.INVISIBLE);
                        Right1.setVisibility(View.VISIBLE);
                        button2.setEnabled(true);

                        //выбор маршрута
                        Zakaz1ToZakaz2();
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }

    public void Zakaz1ToZakaz2(){
        Intent Zakaz1ToZakaz2=new Intent(this,Zakaz2.class);
        Zakaz1ToZakaz2.putExtra("refFromInCity",refFromInCity);
        startActivity(Zakaz1ToZakaz2);



    }

}