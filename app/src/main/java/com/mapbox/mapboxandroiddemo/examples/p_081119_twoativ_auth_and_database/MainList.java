package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainList extends AppCompatActivity {

    private static final String TAG ="MainList" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
    }

    // Alert нет Интернета Перезапуск активити или закрыть приложение
    public void showAlertDialog4(){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(MainList.this);
        mAlertDialog.setTitle("Слабый сигнал интернета!");
        mAlertDialog.setMessage("Нажмите ОК, чтобы поробовать еще раз.");
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent mIntent=getIntent();
                        finish();
                        startActivity(mIntent);
                    }
                });
        mAlertDialog
                .setNegativeButton("Закрыть приложение", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // закрытие приложения реальное
                        finishAffinity();
                        System.exit(0);
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }

    public void IamUser(){
        Intent IamUser= new Intent(this,MainActivity.class);
        startActivity(IamUser);
    }

    public void IamDriver(View view){
        Intent IamDriver= new Intent(this,Driver1.class);
        startActivity(IamDriver);
    }

    public void IamUser(View view){
        Intent IamUser= new Intent(this,MainActivity.class);
        startActivity(IamUser);
    }

    public void ServAppList(View view){
        Intent ServAppList= new Intent(this,Server1.class);
        startActivity(ServAppList);
    }

    public void OPT_Auth(View view){
        Intent OPT_Auth= new Intent(this,Proba.class);
        startActivity(OPT_Auth);
    }

    public void Zakaz1(View view){
        Intent Zakaz1= new Intent(this,Zakaz4Request.class);
        startActivity(Zakaz1);
    }
    public void probaRegDriver(View view){
        Intent probaRegDriver= new Intent(this,DriversApp_0.class);
        startActivity(probaRegDriver);
    }
}
