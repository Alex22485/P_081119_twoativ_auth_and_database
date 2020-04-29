package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.sip.SipSession;
import android.os.Bundle;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.Collections;

public class DriversApp_0 extends AppCompatActivity {

    private static final int RC_SIGN_IN = 101;
    FirebaseDatabase database01;
    DatabaseReference ref01;
    String UserToken;


    //Проверка интернета
    String  key;

    // Выпадающий массив для марок авто
    private static final String[] CARS= new String[]{"Abarth","Acura","Alfa Romeo","Allard","Alpina","Alpine","Alvis","AMC","Ariel","Armstrong Siddeley","Ascari","Aston Martin","Audi","Austin","Austin-Healey","Autobianchi","Auverland","Avanti","Beijing","Bentley","Berkeley","Bitter","Bizzarrini","BMW","Brilliance","Bristol","Bugatti","Buick","BYD","Cadillac","Caterham","Changan","Checker","Chery","Chevrolet","Chrysler","Citroen","Dacia","Daewoo","DAF","Daihatsu","Daimler","Datsun","De Tomaso","DKW","Dodge","Dongfeng","Donkervoort","Eagle","Fairthorpe","FAW","Ferrari","Fiat","Ford","GAC","GAZ","Geely","Genesis","Ginetta","GMC","Great Wall","Haval","Holden","Honda","Hudson","Humber","Hummer","Hyundai","Infiniti","Innocenti","Isuzu","Italdesign","Iveco","Jaguar","Jeep","Jensen","Kia","Lada","Lamborghini","Lancia","Land Rover","Lexus","Lifan","Lincoln","Lotec","Lotus","Mahindra","Marcos","Marussia","Maserati","Matra-Simca","Maybach","Mazda","MCC","McLaren","Mercedes","Mercedes-Benz","Mercury","MG","Mini","Mitsubishi","Monteverdi","Moretti","Morgan","Morris","Nissan","Noble","NSU","Oldsmobile","Opel","Packard","Panoz","Peugeot","Pininfarina","Plymouth","Pontiac","Porsche","Proton","Ravon","Reliant","Renault","Riley","Rolls-Royce","Rover","Saab","Samsung","Saturn","Scion","Seat","Simca","Singer","Skoda","Smart","Spyker","Ssang Yong","SsangYong","Steyr","Studebaker","Subaru","Sunbeam","Suzuki","Talbot","Tata","Tatra","Tesla","Toyota","Trabant","Triumph","TVR","UAZ","Vauxhall","VAZ","Vector","Venturi","Volkswagen","Volvo","Wartburg","Westfield","Willys-Overland","Xedos","Zagato","Zastava","ZAZ","ZIL"};

    // Выпадающий массив для цветовых оттенков машины
    private static final String[] CARSCOLOR= new String[]{"баклажановый","бежевый","белый","желтый","зеленый","коричневый","красный","оранжевый","розовый","серый ","синий","фиолетовый","черный"};

    FirebaseAuth mAuth;
    String driverPhone;
    String driverId;
    String tokenID;
    String driverToken;


    FirebaseDatabase databaseCheckInternet1;
    FirebaseDatabase databaseCheckInternet2;

    DatabaseReference ref03;
    DatabaseReference ref04;

    TextView checkWord;
    TextView TextHello1;
    TextView TextHello2;

    EditText editDriverName;
    EditText editCar;
    EditText editNumberCar;
    EditText editColorCar;
    EditText editQuantiatyOfPackages;

    Button saveEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivers_app_0);


        checkWord=findViewById(R.id.checkWord);
        TextHello1=findViewById(R.id.TextHello1);
        TextHello2=findViewById(R.id.TextHello2);

        editDriverName=findViewById(R.id.editDriverName);
        editCar=findViewById(R.id.editCar);
        editNumberCar=findViewById(R.id.editNumberCar);
        editColorCar=findViewById(R.id.editColorCar);
        editQuantiatyOfPackages=findViewById(R.id.editQuantiatyOfPackages);

        saveEdit=findViewById(R.id.saveEdit);


        // Прослушивание текста для Видимости кнопки Сохранить данные
        editCar.addTextChangedListener( loginTextWather1 );
        editNumberCar.addTextChangedListener( loginTextWather1 );
        editColorCar.addTextChangedListener( loginTextWather1 );
        editDriverName.addTextChangedListener( loginTextWather1 );
        editQuantiatyOfPackages.addTextChangedListener( loginTextWather1 );




        doPhoneLogin();
    }

    private void doPhoneLogin() {
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

                //получение данных
                getIdTokenDriver();
                showAlertDialog(user);

                //29.04.20 запись регистрации в БД Chesh-CheskUser

                visibleYes();

            }
            else {
                Toast.makeText(getBaseContext(), "Ошибка Авторизации", Toast.LENGTH_LONG).show();
                visibleNo();
            }
        }
    }
    // Всплывающая информация
    public void showAlertDialog(FirebaseUser user) {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(DriversApp_0.this);

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

    public void writeMyToken(){

        //получение токена
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(DriversApp_0.this,new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                UserToken = instanceIdResult.getToken();
            }
        });

        //030320 Запись токена для проверки Разрешения на запись заявки в БД ЗАЯВКИ...-...-...-"CheckStopOder"...
        database01 = FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Check")
                .child("CheckDrivers")
                .child("Token");
        ref01.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            ref01.child(UserToken).setValue("Hello");
                                            // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД БД ЗАЯВКИ...-...-...-"CheckStopOder"...
                                            ref01.removeEventListener(this);
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    }
        );
    }


//    public void visible(){
//        //ааа это название марки и три цифры гос номера
//        String aaa=checkWord.getText().toString();
//        if (!aaa.isEmpty()){
//
//            TextHello1.setText("Здравствуй, ");
//            TextHello1.setTextColor(getResources().getColor( R.color.colorNew ));
//            TextHello1.setVisibility(View.VISIBLE);
//
//            TextHello2.setText(aaa);
//            TextHello2.setTextColor(getResources().getColor( R.color.colorNew ));
//            TextHello2.setVisibility(View.VISIBLE);
//        }
//        else {
//            TextHello1.setText("Пожалуйста, ");
//            TextHello1.setTextColor(getResources().getColor( R.color.colorNew ));
//            TextHello1.setVisibility(View.VISIBLE);
//
//            TextHello2.setText("зарегистируйтесь!");
//            TextHello2.setTextColor(getResources().getColor( R.color.colorNew ));
//            TextHello2.setVisibility(View.VISIBLE);
//        }
//    }

    public void visibleYes(){
        TextHello1.setText("Пожалуйста, ");
        TextHello1.setTextColor(getResources().getColor( R.color.colorNew ));
        TextHello1.setVisibility(View.VISIBLE);

        TextHello2.setText("заполните данные!");
        TextHello2.setTextColor(getResources().getColor( R.color.colorNew ));
        TextHello2.setVisibility(View.VISIBLE);
        registration();

    }

    public void visibleNo(){
        TextHello1.setText("Авторизация ");
        TextHello1.setTextColor(getResources().getColor( R.color.colorNew ));
        TextHello1.setVisibility(View.VISIBLE);

        TextHello2.setText("не выполнена!");
        TextHello2.setTextColor(getResources().getColor( R.color.colorNew ));
        TextHello2.setVisibility(View.VISIBLE);

    };



    //процедура регистрации
    public void registration(){

        editCar.setVisibility(View.VISIBLE);
        editNumberCar.setVisibility(View.VISIBLE);
        editColorCar.setVisibility(View.VISIBLE);
        editDriverName.setVisibility(View.VISIBLE);
        editQuantiatyOfPackages.setVisibility(View.VISIBLE);
        saveEdit.setVisibility(View.VISIBLE);



        // Выпадающий массив для марок авто
        AutoCompleteTextView editText=findViewById(R.id.editCar);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, CARS);
        editText.setAdapter(adapter);

        // Выпадающий массив для цвета авто
        AutoCompleteTextView editText1=findViewById(R.id.editColorCar);
        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, CARSCOLOR);
        editText1.setAdapter(adapter1);

    }

    //полуачем номер ID водителя
    public void getIdTokenDriver(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser ghg = mAuth.getCurrentUser();
        driverId = ghg.getUid();

        //получение токена
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(DriversApp_0.this,new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                UserToken = instanceIdResult.getToken();
            }
        });

    }



    //регистрация в БД
    public void saveEdit(View view){



        writeMyToken();


        databaseCheckInternet1 = FirebaseDatabase.getInstance();
        ref03 = databaseCheckInternet1.getReference("Водители")
                .child("Personal")
                .child(driverId)
                .child("Private");
        ref03.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ref03.child("name").setValue(editDriverName.getText().toString());
                ref03.child("phone").setValue(driverPhone);
                ref03.child("token").setValue(driverToken);
                ref03.child("car").setValue(editCar.getText().toString());
                ref03.child("carNumber").setValue(editNumberCar.getText().toString());
                ref03.child("carColor").setValue(editColorCar.getText().toString());
                ref03.child("carPlases").setValue(editQuantiatyOfPackages.getText().toString());

                // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД
                ref03.removeEventListener(this);
                Toast.makeText( DriversApp_0.this, "Сохранить удалось ", Toast.LENGTH_SHORT ).show();

                //запись в ID
                //writeIDID();
                //DriversApp_0.this.finish();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void writeIDID(){

        databaseCheckInternet2 = FirebaseDatabase.getInstance();
        ref04=databaseCheckInternet2.getReference("Водители")
                .child("ID")
                .child("ID");
        ref04.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ref04.child(driverId).setValue(editCar.getText().toString());
                // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД
                ref04.removeEventListener(this);
                Toast.makeText( DriversApp_0.this, "ID зарегистрирован "+editDriverName.getText().toString(), Toast.LENGTH_SHORT ).show();
                //DriversApp_0.this.finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    TextWatcher loginTextWather1=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String editCarInput =editCar.getText().toString().trim();
            String editNumberCarInput = editNumberCar.getText().toString().trim();
            String editColorCarInput =editColorCar.getText().toString().trim();
            String editDriverNameInput =editDriverName.getText().toString().trim();
            String editQuantiatyOfPackagesInput =editQuantiatyOfPackages.getText().toString().trim();

            // Видимость кнопки Сохранить
            saveEdit.setEnabled(!editCarInput.isEmpty()&&!editNumberCarInput.isEmpty()&&!editColorCarInput.isEmpty()
                    &&!editDriverNameInput.isEmpty()&&!editQuantiatyOfPackagesInput.isEmpty());
        }
        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    // кнопка Back сворачивает приложение
    @Override
    public void onBackPressed(){
        this.moveTaskToBack(true);
    }
}
