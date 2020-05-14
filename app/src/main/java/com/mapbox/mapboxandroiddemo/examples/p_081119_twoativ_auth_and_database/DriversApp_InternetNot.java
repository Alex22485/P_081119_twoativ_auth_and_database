package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DriversApp_InternetNot extends AppCompatActivity {

    //Используется для DriversApp_Start и для DriversApp_0

    TextView TextUpdate;
    TextView TextMessage1;
    TextView TextMessage2;
    Button BtnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivers_app__internet_not);

        TextUpdate=findViewById(R.id.TextUpdate);
        TextMessage1=findViewById(R.id.TextMessage1);
        TextMessage1.setText("Кажется, что-то пошло не так!!!");
        TextMessage2=findViewById(R.id.TextMessage2);
        BtnUpdate=findViewById(R.id.BtnUpdate);

        TextMessage1.setVisibility(View.VISIBLE);
        TextMessage2.setVisibility(View.VISIBLE);
        BtnUpdate.setVisibility(View.VISIBLE);
    }

    // кнопка Back сворачивает приложение
    @Override
    public void onBackPressed(){
        this.moveTaskToBack(true);
    }

    // Кнопка обновить
    public void Update(View view){
        Intent ddd=new Intent(this,DriversApp_Start.class);
        startActivity(ddd);
    }
}
