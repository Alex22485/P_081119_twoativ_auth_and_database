package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Driver2 extends AppCompatActivity {

    private static final String TAG ="Driver2" ;

    // Для экспорта данных
    String phoneNew; // из листа Driver1
    String phoneNewDriverAuth; // из листа DriverAuth
    String UserToken; // токен из Driver1
    String UserTokenFromDriverAuth; // токен из DriverAuth

    // Заполнение Активити
    EditText editDriverName;
    EditText editCar;
    EditText editNumberCar;
    EditText editColorCar;
    EditText editQuantiatyOfPackages;
    Button saveEdit;
    ProgressBar progressBar2;

    // Выпадающий массив для марок авто
    private static final String[] CARS= new String[]{"Abarth","Acura","Alfa Romeo","Allard","Alpina","Alpine","Alvis","AMC","Ariel","Armstrong Siddeley","Ascari","Aston Martin","Audi","Austin","Austin-Healey","Autobianchi","Auverland","Avanti","Beijing","Bentley","Berkeley","Bitter","Bizzarrini","BMW","Brilliance","Bristol","Bugatti","Buick","BYD","Cadillac","Caterham","Changan","Checker","Chery","Chevrolet","Chrysler","Citroen","Dacia","Daewoo","DAF","Daihatsu","Daimler","Datsun","De Tomaso","DKW","Dodge","Dongfeng","Donkervoort","Eagle","Fairthorpe","FAW","Ferrari","Fiat","Ford","GAC","GAZ","Geely","Genesis","Ginetta","GMC","Great Wall","Haval","Holden","Honda","Hudson","Humber","Hummer","Hyundai","Infiniti","Innocenti","Isuzu","Italdesign","Iveco","Jaguar","Jeep","Jensen","Kia","Lada","Lamborghini","Lancia","Land Rover","Lexus","Lifan","Lincoln","Lotec","Lotus","Mahindra","Marcos","Marussia","Maserati","Matra-Simca","Maybach","Mazda","MCC","McLaren","Mercedes","Mercedes-Benz","Mercury","MG","Mini","Mitsubishi","Monteverdi","Moretti","Morgan","Morris","Nissan","Noble","NSU","Oldsmobile","Opel","Packard","Panoz","Peugeot","Pininfarina","Plymouth","Pontiac","Porsche","Proton","Ravon","Reliant","Renault","Riley","Rolls-Royce","Rover","Saab","Samsung","Saturn","Scion","Seat","Simca","Singer","Skoda","Smart","Spyker","Ssang Yong","SsangYong","Steyr","Studebaker","Subaru","Sunbeam","Suzuki","Talbot","Tata","Tatra","Tesla","Toyota","Trabant","Triumph","TVR","UAZ","Vauxhall","VAZ","Vector","Venturi","Volkswagen","Volvo","Wartburg","Westfield","Willys-Overland","Xedos","Zagato","Zastava","ZAZ","ZIL"};
    // Выпадающий массив для цветовых оттенков машины
    private static final String[] CARSCOLOR= new String[]{"баклажановый","бежевый","белый","желтый","зеленый","коричневый","красный","оранжевый","розовый","серый ","синий","фиолетовый","черный"};

    // Для регистрации данных
    String keyReg;
    String checkInternet;
    String writeData;
    FirebaseDatabase databaseCheckInternet1;
    DatabaseReference ref03;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver2);


        editDriverName=findViewById(R.id.editDriverName);
        editCar=findViewById(R.id.editCar);
        editNumberCar=findViewById(R.id.editNumberCar);
        editColorCar=findViewById(R.id.editColorCar);
        editQuantiatyOfPackages=findViewById(R.id.editQuantiatyOfPackages);
        progressBar2=findViewById(R.id.progressBar2);
        saveEdit=findViewById(R.id.saveEdit);

        // Убрать видимость прогресса
        progressBar2.setVisibility(View.INVISIBLE);

        // Прослушивание текста для Видимости кнопки Сохранить данные
        editCar.addTextChangedListener( loginTextWather1 );
        editNumberCar.addTextChangedListener( loginTextWather1 );
        editColorCar.addTextChangedListener( loginTextWather1 );
        editDriverName.addTextChangedListener( loginTextWather1 );
        editQuantiatyOfPackages.addTextChangedListener( loginTextWather1 );

        // Определение выпадающего массива
        arrayMassives();

        //проверка был ли переход из Driver1 если данные о пользователе еще не заполнялись
        Intent Driver2= getIntent();
        phoneNew=Driver2.getStringExtra("phoneNew");
        UserToken=Driver2.getStringExtra("UserToken");
        Log.d(TAG, "phoneNew:"+phoneNew);

        // проверка был ли переход из DriverAuth сразу после авторизации
        Intent GoDriver2=getIntent();
        phoneNewDriverAuth=""+GoDriver2.getStringExtra("phoneNewDriverAuth");
        UserTokenFromDriverAuth=""+GoDriver2.getStringExtra("UserToken");
        Log.d(TAG, "phoneNewDriverAuth:"+phoneNewDriverAuth);

        //если был то пишем phoneNew=phoneNewDriverAuth
        if (!phoneNewDriverAuth.equals("null")){
            phoneNew=phoneNewDriverAuth;
            UserToken=UserTokenFromDriverAuth;
            Log.d(TAG, "phoneNewNEW:"+phoneNew);
            Log.d(TAG, "tokenNewNEW:"+UserToken);
        }
    }

// МЕТОДЫ
    // Видимость кнопки СОХРАНИТЬ
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
    // Определение выпадающего массива
    public void arrayMassives(){
        // Выпадающий массив для марок авто
        AutoCompleteTextView editText=findViewById(R.id.editCar);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, CARS);
        editText.setAdapter(adapter);

        // Выпадающий массив для цвета авто
        AutoCompleteTextView editText1=findViewById(R.id.editColorCar);
        ArrayAdapter<String> adapter1=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, CARSCOLOR);
        editText1.setAdapter(adapter1);
    }
    //Запись данных в БД
    public void writePrivetData(){
        keyReg="";
        checkInternet="";
        writeData="";

        //ТАЙМ-АУТ ЗАПРОСА ИНТЕРНЕТА
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Завершен ТАЙМ-АУТ ЗАПРОСА ИНТЕРНЕТА-2 при проверке регистрации
                checkInternet="Out";
                Log.d(TAG, "Записан checkInternet=Out");
                //  Проверка времени интернета
                inetNotWhenGoCheckRegistration();
            }
        },15000);

        databaseCheckInternet1 = FirebaseDatabase.getInstance();
        ref03 = databaseCheckInternet1.getReference("Водители")
                .child("Personal")
                .child(phoneNew)
                .child("Private");
        ref03.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // первая строка нужна для правильного добавления/удаления данных в БД через nod js
                ref03.child("NCP").setValue(editNumberCar.getText().toString()+editCar.getText().toString()+editQuantiatyOfPackages.getText().toString()+"M");
                ref03.child("name").setValue(editDriverName.getText().toString());
                ref03.child("phone").setValue(phoneNew);
                ref03.child("token").setValue(UserToken);
                ref03.child("car").setValue(editCar.getText().toString());
                ref03.child("carNumber").setValue(editNumberCar.getText().toString());
                ref03.child("carColor").setValue(editColorCar.getText().toString());
                ref03.child("carPlases").setValue(editQuantiatyOfPackages.getText().toString());

                // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД
                ref03.removeEventListener(this);
                writeData="Yes";

                // Запись в БД YES
                wtiteYes();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    //  Проверка времени интернета
    public void inetNotWhenGoCheckRegistration(){
        if(writeData.equals("Yes")){
            Log.d(TAG, "таймер-1 остановлен");
        }
        else {
            //пропал интернет во время проверки наличия регистрации
            Log.d(TAG, "Интернета пропал при записи данных");
            // Alert нет Интернета при записи данных
            showAlertDialog4();
            // Убрать видимость прогресса
            progressBar2.setVisibility(View.INVISIBLE);
        }
    }
    // Запись в БД YES
    public void wtiteYes(){
        databaseCheckInternet1 = FirebaseDatabase.getInstance();
        ref03 = databaseCheckInternet1.getReference("Водители")
                .child("Personal")
                .child(phoneNew)
                .child("Proverka")
                .child("Oder");
        ref03.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // первая строка нужна для правильного добавления/удаления данных в БД через nod js
                ref03.child("Proverka").setValue("Yes");

                // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД
                ref03.removeEventListener(this);
                writeData="Yes";

                // Переход на лист заказа
                getMainList();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    // Проверка Перехода в лист заказов
    public void getMainList(){
        Log.d(TAG, "вход в проверку getMainList");/*специально пусто*/
        if(checkInternet.equals("Out")){
            Log.d(TAG, "getMainList остановлен");
        }
        else {
            // Переход на лист заказа
            GoDriversApp_1();
        }
    }

// КНОПКИ
    // Кнопка сохранить
    public void saveEdit(View view){
        // Убрать видимость прогресса
        progressBar2.setVisibility(View.VISIBLE);
        //однократное нажатие кнопки сохранить
        saveEdit.setVisibility(View.INVISIBLE);
        //Запись данных в БД
        writePrivetData();
    }

//ALERT
    // Alert нет Интернета при записи данных
    public void showAlertDialog4(){
    AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Driver2.this);
    mAlertDialog.setTitle("Слабый сигнал интернета!");
    mAlertDialog.setMessage("Нажмите ОК, чтобы поробовать еще раз.");
    mAlertDialog.setCancelable(false);
    mAlertDialog
            .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //Запись данных в БД Повторно
                    writePrivetData();
                    progressBar2.setVisibility(View.VISIBLE);
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
    // Alert ловушка неперехода на DriversApp_1
    public void AlertDriversApp_1(){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Driver2.this);
        mAlertDialog.setMessage("Нажмите ОК, для продолжения.");
        mAlertDialog.setCancelable(false);
        mAlertDialog
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Переход в Zaka2 для выбора маршрута
                        GoDriversApp_1();
                    }
                });
        mAlertDialog.create();
        mAlertDialog.show();
    }

// ПЕРЕХОДЫ
    public void GoDriversApp_1(){
        Intent GoDriversApp_1=new Intent(this,DriversApp_1.class);
        GoDriversApp_1.putExtra("phoneNew",phoneNew);
        startActivity(GoDriversApp_1);

        // Alert ловушка неперехода на DriversApp_1
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Alert ловушка неперехода на DriversApp_1
                AlertDriversApp_1();
            }
        },1000);
    }
}