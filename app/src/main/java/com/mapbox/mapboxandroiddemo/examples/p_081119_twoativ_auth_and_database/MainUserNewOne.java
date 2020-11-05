package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.SurfaceControl;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainUserNewOne extends AppCompatActivity {

    // есть примеры плавного перемещения текста три примера
    // пример плавного увеличения текста

    private static final String TAG ="MainUserNewOne" ;

    int dinamic1Size,dinamic2Size,dinamic3Size,dinamic4Size,dinamic5Size,dinamic6Size;
    int dinamicLittleTxt1, dinamicLittleTxt2;

    double goubleRef1;
    double goubleRef2;
    double goubleRef3;

    TextView tV1,tV2,tV3,tV4,tV5,tV6,tV7,tV8, point1, point2, point3,tV9;
    Button button6;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user_new_one);

        tV1=findViewById(R.id.tV1);
        tV2=findViewById(R.id.tV2);
        tV3=findViewById(R.id.tV3);
        tV4=findViewById(R.id.tV4);
        tV5=findViewById(R.id.tV5);
        tV6=findViewById(R.id.tV6);
        tV7=findViewById(R.id.tV7);
        tV8=findViewById(R.id.tV8);
        point1=findViewById(R.id.point1);
        point2=findViewById(R.id.point2);
        point3=findViewById(R.id.point3);


        tV9=findViewById(R.id.tV9);
        button6=findViewById(R.id.button6);
        constraintLayout=findViewById(R.id.cl);

        dinamic1Size=0;
        dinamic2Size=0;
        dinamic3Size=0;
        dinamic4Size=0;
        dinamic5Size=0;
        dinamic6Size=0;

        tV1.setTextSize(0);
        tV2.setTextSize(0);
        tV3.setTextSize(0);
        tV4.setTextSize(0);
        tV5.setTextSize(0);
        tV6.setTextSize(0);
        tV7.setTextSize(0);
        tV8.setTextSize(0);
        tV9.setTextSize(0);

        Handler Date = new Handler();
        Date.postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                // Появление фразы "Летаешь через Аэропорт г.Красноярск"
                dinamic1Size();
            }
        },500);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    // Появление фразы "Летаешь через Аэропорт г.Красноярск"
    public void dinamic1Size(){
        if (dinamic1Size<26){

            Handler Date = new Handler();
            Date.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tV1.setTextSize(dinamic1Size);
                    tV2.setTextSize(dinamic1Size);

                    dinamic1Size=dinamic1Size+1;
                    dinamic1Size();
                }
            },50);
        }
        else {
            // Появление "?"
            dinamic2Size();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    // Появление "?"
    public void dinamic2Size(){
        if (dinamic2Size<26){

            Handler Date = new Handler();
            Date.postDelayed(new Runnable() {
                @Override
                public void run() {
            tV3.setTextSize(dinamic2Size);
            dinamic2Size=dinamic2Size+1;
            dinamic2Size();
        }
    },50);
}
        else {
            //Toast.makeText(MainUserNewOne.this,"УРА....",Toast.LENGTH_LONG).show();
            // начальное значение verticalBias
            goubleRef1=0.5;
            //Смещение вверх фразы "Летаешь через Аэропорт г.Красноярск?"
            dinamicBias1();

            Handler Date = new Handler();
            Date.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //уменьшение размера фразы "Летаешь через Аэропорт г.Красноярск?"
                    dinamicLittleTxt1=25;
                    dinamicLittleTxt1();
                }
            },200);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    //Смещение вверх фразы "Летаешь через Аэропорт г.Красноярск?"
    public void dinamicBias1(){
        if(goubleRef1>0.01){
            Handler Date = new Handler();
            Date.postDelayed(new Runnable() {
                @Override
                public void run() {

                    ConstraintLayout.LayoutParams params= (ConstraintLayout.LayoutParams) tV1.getLayoutParams();
                    params.verticalBias= (float) goubleRef1;
                    tV1.setLayoutParams(params);
                    goubleRef1=goubleRef1-0.005;
                    dinamicBias1();
                }
            },5);
        }
        else {
            // Появление фразы "Посмотри, может эти маршрутные такси удобны для тебя"
            dinamic3Size();
        }
        //1 ПРИМЕР
        //источник урок 183 STARTANDROID
        //плавное перемещение текста, через ConstraintSet+библиотека TransitionManager
        // реально работает если у TextView есть папраметр bias vertical!!!
//        ConstraintSet set=new ConstraintSet();

        // считываем параметры constraintLayout
//        set.clone(constraintLayout);
        // установка нужного параметра смещения
//        set.setVerticalBias(R.id.tV1,0.05f);
//        // library for dinamic show
//        TransitionManager.beginDelayedTransition(constraintLayout);
        //приминение нужных параметров
//        set.applyTo(constraintLayout);

        //2 ПРИМЕР
        // мой код
        //плавное перемещение текста, реально рабоает если у TextView есть папраметр margintTop!!!
//        if(intRef>70){
//            Log.d(TAG, intRef+"");
//            Handler Date = new Handler();
//            Date.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    ConstraintLayout.LayoutParams params= (ConstraintLayout.LayoutParams) tV1.getLayoutParams();
//                    //плавное перемещение текста, реально рабоает ели у TextView есть папраметр margitTop!!!
//                    params.setMargins(params.leftMargin,params.topMargin*intRef/100,params.rightMargin,params.bottomMargin);
//                    tV1.setLayoutParams(params);
//                    intRef=intRef-1;
//                    dinamic3();
//                }
//            },40);
//        }
//        else {
//            Toast.makeText(MainUserNewOne.this,"Круто",Toast.LENGTH_LONG).show();
//        }

        //3 ПРИМЕР ИДЕАЛЬНЫЙ
        //перемещение текста, реально рабоает еcли у TextView есть папраметр verticalBias!!!
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    //Уменьшение размера фразы "Летаешь через Аэропорт г.Красноярск?"
    public void dinamicLittleTxt1(){
        if (dinamicLittleTxt1>18){
            Log.d(TAG, dinamicLittleTxt1+"");
            Handler Date = new Handler();
            Date.postDelayed(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void run() {
                    tV1.setTextSize(dinamicLittleTxt1);
                    tV2.setTextSize(dinamicLittleTxt1);
                    tV3.setTextSize(dinamicLittleTxt1);
                    dinamicLittleTxt1=dinamicLittleTxt1-1;
                    dinamicLittleTxt1();
                }
            },200);
        }
    };

    // Появление фразы "Посмотри, может эти маршрутные такси удобны для тебя"
    public void dinamic3Size(){
        if (dinamic3Size<26){

            Handler Date = new Handler();
            Date.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tV4.setTextSize(dinamic3Size);
                    tV5.setTextSize(dinamic3Size);
                    tV6.setTextSize(dinamic3Size);

                    dinamic3Size=dinamic3Size+1;
                    dinamic3Size();
                }
            },50);
        }
        else {
            goubleRef2=0.5;


            Handler upGoTxt = new Handler();
            upGoTxt.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Смещение фразы "Посмотри, может эти маршрутные такси удобны для тебя"
                    dinamicBias2();
                }
            },1000);


            Handler little2 = new Handler();
            little2.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //уменьшение размера фразы "Посмотри, может эти маршрутные такси удобны для тебя"
                    dinamicLittleTxt2=25;
                    dinamicLittleTxt2();
                }
            },500);


            Handler ttG = new Handler();
            ttG.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // запуск фразы "Зарезервируй место вместе с time to go"
                    dinamic4Size();
                }
            },2500);
        }
    }
    // Смещение фразы "Посмотри, может эти маршрутные такси удобны для тебя"
    public void dinamicBias2(){
        if(goubleRef2>0.09){

            Handler Date = new Handler();
            Date.postDelayed(new Runnable() {
                @Override
                public void run() {

                    ConstraintLayout.LayoutParams params= (ConstraintLayout.LayoutParams) tV4.getLayoutParams();
                    params.verticalBias= (float) goubleRef2;
                    tV4.setLayoutParams(params);
                    goubleRef2=goubleRef2-0.005;
                    dinamicBias2();
                }
            },5);
        }
        else {
//            tV4.setTextSize(20);
//            tV5.setTextSize(20);
//            tV6.setTextSize(20);
//
//            tV4.setText("Посмотри, может эти маршрутные");
//            tV5.setText("такси удобны для тебя");
//            tV6.setText("");
//            ConstraintSet set=new ConstraintSet();
//            // считываем параметры constraintLayout
//            set.clone(constraintLayout);
//            // очищаем именно нижнюю привязку  для tV4
//            set.clear(R.id.tV4,ConstraintSet.BOTTOM);
//            // очищаем именно правую привязку  для tV4, tv5
//            set.clear(R.id.tV4,ConstraintSet.END);
//            set.clear(R.id.tV5,ConstraintSet.END);
//            // очищаем именно правую привязку  для tV5
//            set.clear(R.id.tV5,ConstraintSet.END);
//            // привязываем верхушку TV4  к нижней границе tV2,  можно сделать отступ от tV2 указав значение, н-р 5
//            set.connect(R.id.tV4,ConstraintSet.TOP,R.id.tV2,ConstraintSet.BOTTOM,5);
//            // привязываем левую часть tv4 и tv5 к левой стороне окна consrtainLayouut cl с отступом
//            set.connect(R.id.tV4,ConstraintSet.START,R.id.cl,ConstraintSet.START,5);
//            set.connect(R.id.tV5,ConstraintSet.START,R.id.cl,ConstraintSet.START,5);
//
//            tV1.setTextSize(20);
//            tV2.setTextSize(20);
//            tV3.setTextSize(20);
//            tV4.setTextSize(20);
//            tV5.setTextSize(20);
//            tV6.setTextSize(20);
//
//            tV4.setText("Посмотри, может эти маршрутные");
//            tV5.setText("такси удобны для тебя");
//            tV6.setText("");
//
//
//            //приминение нужных параметров
//            set.applyTo(constraintLayout);
//            // library for dinamic show
//        //TransitionManager.beginDelayedTransition(constraintLayout);
        }
    }

    //уменьшение размера фразы "Посмотри, может эти маршрутные такси удобны для тебя"
    public void dinamicLittleTxt2(){
        if (dinamicLittleTxt2>18){

            Handler Date = new Handler();
            Date.postDelayed(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void run() {
                    tV4.setTextSize(dinamicLittleTxt2);
                    tV5.setTextSize(dinamicLittleTxt2);
                    tV6.setTextSize(dinamicLittleTxt2);
                    dinamicLittleTxt2=dinamicLittleTxt2-1;
                    dinamicLittleTxt2();
                }
            },200);
        }
    }
    // Появление фраз "Зарезервируй место,"
    public void dinamic4Size(){
        if (dinamic4Size<26){

            Handler Date = new Handler();
            Date.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tV7.setTextSize(dinamic4Size);

                    dinamic4Size=dinamic4Size+1;
                    dinamic4Size();
                }
            },50);
        }
        else {
            // Появление фраз "Вместе с"
            dinamic5Size();
        }
    }

    // Появление фраз "Вместе с"
    public void dinamic5Size(){
        if (dinamic5Size<26){

            Handler Date = new Handler();
            Date.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tV8.setTextSize(dinamic5Size);
                    dinamic5Size=dinamic5Size+1;
                    dinamic5Size();
                }
            },50);
        }
        else {
            Handler pointTime1 = new Handler();
            pointTime1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    point1.setVisibility(View.VISIBLE);
                }
            },400);

            Handler pointTime2 = new Handler();
            pointTime2.postDelayed(new Runnable() {
                @Override
                public void run() {
                    point2.setVisibility(View.VISIBLE);
                }
            },800);

            Handler pointTime3 = new Handler();
            pointTime3.postDelayed(new Runnable() {
                @Override
                public void run() {
                    point3.setVisibility(View.VISIBLE);
                }
            },1200);

            Handler upTXT = new Handler();
            upTXT.postDelayed(new Runnable() {
                @Override
                public void run() {
                    goubleRef3=0.5;
                    // Смещение текста "Зарезервируй место, вместе с"
                    dinamicBias3();
                }
            },2400);
        }
    }
    // Смещение текста "Зарезервируй место, вместе с"
    public void dinamicBias3(){
        if(goubleRef3>0.3){
        Handler Date = new Handler();
        Date.postDelayed(new Runnable() {
            @Override
            public void run() {

                ConstraintLayout.LayoutParams params= (ConstraintLayout.LayoutParams) tV7.getLayoutParams();
                params.verticalBias= (float) goubleRef3;
                tV7.setLayoutParams(params);
                goubleRef3=goubleRef3-0.005;
                dinamicBias3();
            }
        },5);
    }
        else {
        ConstraintSet set=new ConstraintSet();
        // считываем параметры constraintLayout
                set.clone(constraintLayout);
                // очищаем именно нижнюю привязку  для tV4
                set.clear(R.id.tV9,ConstraintSet.BOTTOM);
                // привязываем верхушку TV9  к нижней границе tV8,  можно сделать отступ от tV2 указав значение, н-р 15
                set.connect(R.id.tV9,ConstraintSet.TOP,R.id.tV8,ConstraintSet.BOTTOM,25);
                //приминение нужных параметров
                set.applyTo(constraintLayout);
                // library for dinamic show
                //TransitionManager.beginDelayedTransition(constraintLayout);

            // Появление фразы "Time to go"
            dinamic6Size();

        }
    }
    // Появление фразы "Time to go"
    public void dinamic6Size(){

        if (dinamic6Size<40){

            Handler Date = new Handler();
            Date.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tV9.setTextSize(dinamic6Size);


                    dinamic6Size=dinamic6Size+1;
                    dinamic6Size();
                }
            },70);
        }
        else {
            button6.setVisibility(View.VISIBLE);
            //Toast.makeText(MainUserNewOne.this,"",Toast.LENGTH_LONG).show();
        }
    }


    public void mainUserNewOne2(View view){
        Intent mainUserNewOne2=new Intent(this,MainUserNewOne2.class);
        startActivity(mainUserNewOne2);
    }

     //Блокировка кнопки Back!!!! :)))
    @Override
    public void onBackPressed(){
    }
}
