package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class Main6ActivityNotInternet extends AppCompatActivity {
    private static final String TAG ="Main6ActivityNotInt" ;

    // переход из
    // 1. Main3Activity при второй проверке интернета
    // 2. Main6Activity при считывании статуса заявки и потере интернета



    Button BtnUpdate;
    LinearLayout TextNotInternet;
    LinearLayout TextProccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6_not_internet);

        BtnUpdate=findViewById(R.id.BtnUpdate);
        TextNotInternet=findViewById(R.id.TextNotInternet);
        TextProccess=findViewById(R.id.TextProccess);
        TextNotInternet.setVisibility(View.VISIBLE);

    }

    // кнопка Back сворачивает приложение
    @Override
    public void onBackPressed(){
        this.moveTaskToBack(true);
    }

    // Кнопка обновить
    public void Update(View view){
        Intent ddd=new Intent(this,MainActivity.class);
        startActivity(ddd);
    }

}
