package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class Main6ActivityNotInternet extends AppCompatActivity {
    private static final String TAG ="Main6ActivityNotInt" ;



    Button BtnUpdate;
    LinearLayout TextNotInternet;
    LinearLayout TextProccess;

    String UserToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6_not_internet);

        BtnUpdate=findViewById(R.id.BtnUpdate);

        TextNotInternet=findViewById(R.id.TextNotInternet);
        TextProccess=findViewById(R.id.TextProccess);

        TextNotInternet.setVisibility(View.VISIBLE);

        // Получить Токен!!!!
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(Main6ActivityNotInternet.this,new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                UserToken = instanceIdResult.getToken();
                Log.d(TAG, "токен: "+UserToken);
            }
        });


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


//    public void checkElseInternet(){
//
//        keyReg="";
//        registration="";
//        checkregistrationTimeOut="";
//
//        //ТАЙМ-АУТ ЗАПРОСА ИНТЕРНЕТА-2
//        Handler handler1 = new Handler();
//        handler1.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                // Завершен ТАЙМ-АУТ ЗАПРОСА ИНТЕРНЕТА-2 при проверке регистрации
//                checkregistrationTimeOut="Out";
//                inetNotWhenGoCheckRegistration();
//            }
//        },15000);
//
//        Log.d(TAG, "запрос регистрации начат");
//        //чтение из БД с правилом для любых пользователей
//        database02 = FirebaseDatabase.getInstance();
//        ref02 = database02.getReference("Check")
//                .child("CheckUsers")
//                .child("Token")
//                .child(UserToken);
//        ref02.addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                keyReg=dataSnapshot.getValue(String.class);
//                registration=""+keyReg;
//                Log.d(TAG, "запрос регистрации получен"+keyReg);
//
//
//                // с этой записью makeText появляется только один раз!!!!! ХОРОШО
//                ref02.removeEventListener(this);
//
//                //переход на главную страницу
//                MainListStart();
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
//}
//
//    //Проверка интернета
//    public void inetNotWhenGoCheckRegistration (){
//
//        if(keyReg.equals("Hello")){
//            Log.d(TAG, "таймер остановлен но запром из БД получен");/*специально пусто*/}
//
//        else {
//            //пропал интернет во время проверки наличия регистрации
//            Log.d(TAG, "Интернета пропал при проверке регистрации");
//            TextNotInternet.setVisibility(View.VISIBLE);
//            TextProccess.setVisibility(View.INVISIBLE);
//        }
//    }
//
//    public void MainListStart(){
//        if(checkregistrationTimeOut.equals("Out")){
//            Log.d(TAG, "Интернета появился но время запроса вышло");
//        }
//        else if (keyReg.equals("Hello")){
//            Log.d(TAG, "Интернета появился переход на гл заставку через 6 секунд");
//
//            Toast.makeText(Main6ActivityNotInternet.this,"Связь восстановлена....",Toast.LENGTH_LONG).show();
//            //задержка на переход 6 секунд чтобы успела удалиться старая заявка  и появиться ref word "No"
//            Handler handler1 = new Handler();
//            handler1.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                    MainListStartNow();
//
//                }
//            },6000);
//        }
//    }
//
//    public void MainListStartNow(){
//        Intent ddd=new Intent(this,MainActivity.class);
//        startActivity(ddd);
//    }
//
//

}
