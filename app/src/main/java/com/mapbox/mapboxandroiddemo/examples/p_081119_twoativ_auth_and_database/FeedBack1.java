package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class FeedBack1 extends AppCompatActivity {

    // Обратная связь (нет нужного рейса/маршрута)

    EditText Ed1,Ed2,Ed3,Ed4;
    CheckBox checkBox;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back1);
        Ed1=findViewById(R.id.Ed1);
        Ed2=findViewById(R.id.Ed2);
        Ed3=findViewById(R.id.Ed3);
        Ed4=findViewById(R.id.Ed4);
        checkBox=findViewById(R.id.checkBox);
        button=findViewById(R.id.button);
        Ed4.setVisibility(View.INVISIBLE);
    }
    // нажатие checkBox
    public void checkBox(View view){
        if(checkBox.isChecked()){
            Ed4.setVisibility(View.VISIBLE);
        }
        else {
            Ed4.setVisibility(View.INVISIBLE);
        }
    }
    // кнопка отправить запрос
    public void button(View view){
        // если нужен ответ на запрос
        if (checkBox.isChecked()){
            // проверка пусты ли четыре строки
            if (!Ed1.getText().toString().isEmpty()&&!Ed2.getText().toString().isEmpty()&&!Ed3.getText().toString().isEmpty()&&!Ed4.getText().toString().isEmpty()){
                // отправка запроса
                sendDataToFB();
                return;
            }
            Alert();
            return;
        }
        //если не нужен ответ на запрос
        // проверка пусты ли три строки
        if (!Ed1.getText().toString().isEmpty()&&!Ed2.getText().toString().isEmpty()&&!Ed3.getText().toString().isEmpty()){
            // отправка запроса
            sendDataToFB();
            return;
        }
        Alert();
    }
    // отправка запроса в БД о желаемом рейсе-маршруте
    public void sendDataToFB(){
        Toast.makeText(FeedBack1.this,"сообщение отправлено....",Toast.LENGTH_LONG).show();
    }
    // Alert запоолните все поля
    public void Alert(){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(FeedBack1.this);
        mAlertDialog.setMessage("Пожалуйста, заполните все поля");
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }
    // Блокировка кнопки Back!!!! :)))
    @Override
    public void onBackPressed(){
    }
}