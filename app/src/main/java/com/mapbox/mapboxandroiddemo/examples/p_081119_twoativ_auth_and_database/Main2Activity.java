package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.Collections;

public class Main2Activity extends AppCompatActivity {

    private static final String TAG ="Main2activity" ;


    FirebaseDatabase database01;
    DatabaseReference ref01;
    String UserToken;
    String phoneUser;

    TextView TextHello1;
    Button GoMainActivity;

    LinearLayout Layout1;
    LinearLayout Layout2;

    FirebaseAuth mAuth;

    //Проверка интернета
    String keyReg;
    String checkInternet;
    String writeData;

    private static final int RC_SIGN_IN = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextHello1=findViewById(R.id.TextHello1);
        GoMainActivity=findViewById(R.id.GoMainActivity);
        Layout1=findViewById(R.id.Layout1);
        Layout2=findViewById(R.id.Layout2);

        doPhoneLogin();
    }
    private void doPhoneLogin() {
//        Intent intent = AuthUI.getInstance().createSignInIntentBuilder()
        Intent intent = AuthUI.getInstance().createSignInIntentBuilder()
                .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                .setAvailableProviders(Collections.singletonList(
                        new AuthUI.IdpConfig.PhoneBuilder().build()))
                .setLogo(R.mipmap.ic_launcher)
                .build();

        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {
            IdpResponse idpResponse = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                showAlertDialog(user);

                //29.04.20 получить токен и телефон
                getMyToken();

                Layout1.setVisibility(View.VISIBLE);
                Layout2.setVisibility(View.VISIBLE);

            }
            else {
                TextHello1.setText("Авторизация не выполнена ");
                TextHello1.setTextColor(getResources().getColor( R.color.colorNew ));
                TextHello1.setVisibility(View.VISIBLE);

                GoMainActivity.setVisibility(View.VISIBLE);
                GoMainActivity.setVisibility(View.VISIBLE);

                //Toast.makeText(getBaseContext(), "Ошибка Авторизации", Toast.LENGTH_LONG).show();
            }
        }
    }
    // Всплывающая информация
    public void showAlertDialog(FirebaseUser user) {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(
                Main2Activity.this);

        // Set Title
        mAlertDialog.setTitle("Авторизация успешна");

        // Set Message
        mAlertDialog
                .setMessage(" Номер телефона " + user.getPhoneNumber())
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        mAlertDialog.create();
        // Showing Alert Message
        mAlertDialog.show();
    }


    public void GoMainActivity(View view){
        Intent GoMainActivity= new Intent(this,Main2Activity.class);
        startActivity(GoMainActivity);
    }



    public void getMyToken(){

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser ghg = mAuth.getCurrentUser();
        phoneUser=ghg.getPhoneNumber();

        //получение токена
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(Main2Activity.this,new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                UserToken = instanceIdResult.getToken();

            }
        });
    }

//    public void writePrivateData(){
//        //030320 Запись токена для проверки Разрешения на запись заявки в БД ЗАЯВКИ...-...-...-"CheckStopOder"...
//        database01 = FirebaseDatabase.getInstance();
//        ref01 = database01.getReference("Check")
//                .child("CheckUsers")
//                .child("Token");
//        ref01.addValueEventListener(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                            ref01.child(UserToken).setValue("Hello");
//                                            // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД БД ЗАЯВКИ...-...-...-"CheckStopOder"...
//                                            ref01.removeEventListener(this);
//                                        }
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//                                        }
//                                    }
//        );
//    }

    //Кнопка пропустить c в БД персональные данные
    public void GoMainOder(View view){

        keyReg="";
        checkInternet="";
        writeData="";

        //ТАЙМ-АУТ ЗАПРОСА ИНТЕРНЕТА
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {

                // Завершен ТАЙМ-АУТ ЗАПРОСА ИНТЕРНЕТА при записи данных в БД
                checkInternet="Out";
                Log.d(TAG, "Записан checkInternet=Out");/*специально пусто*/
                inetNotWhenGoCheckRegistration();
            }
        },15000);


        //030320 Запись токена для проверки Разрешения на запись заявки в БД ЗАЯВКИ...-...-...-"CheckStopOder"...
        database01 = FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Пользователи")
                .child("Personal")
                .child(phoneUser)
                .child("Private");
        ref01.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            ref01.child("token").setValue(UserToken);
                                            // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД БД ЗАЯВКИ...-...-...-"CheckStopOder"...
                                            ref01.removeEventListener(this);

                                            writeData="Yes";
                                            getMainList();
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    }
        );





//        Intent GoMainOder = new Intent( this,Choose_direction.class );
//        startActivity( GoMainOder);
    }

    public void inetNotWhenGoCheckRegistration(){
        if(writeData.equals("Yes")){
            Log.d(TAG, "таймер остановлен");/*специально пусто*/}

        else {
            //пропал интернет во время проверки наличия регистрации
            Log.d(TAG, "Интернета пропал при записи данных");
            Intent aaa = new Intent(this,InternetNot.class);
            startActivity(aaa);
        }

    }

    public void getMainList(){
        Log.d(TAG, "вход в проверку getMainList");/*специально пусто*/

        if(checkInternet.equals("Out")){
            Log.d(TAG, "getMainList остановлен");/*специально пусто*/

            //return нужен чтобы при возобноблении интернета автоматически не переходило на лист с заявками
            return;
        }
        Intent Choose_direction=new Intent(this,Choose_direction.class);
        startActivity(Choose_direction);
    }

    // кнопка Back сворачивает приложение
    @Override
    public void onBackPressed(){
        this.moveTaskToBack(true);
    }





    }
