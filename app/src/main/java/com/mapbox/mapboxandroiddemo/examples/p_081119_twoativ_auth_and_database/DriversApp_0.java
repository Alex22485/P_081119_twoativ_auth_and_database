package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DriversApp_0 extends AppCompatActivity {

    // Выпадающий массив для марок авто
    private static final String[] CARS= new String[]{"Abarth","Acura","Alfa Romeo","Allard","Alpina","Alpine","Alvis","AMC","Ariel","Armstrong Siddeley","Ascari","Aston Martin","Audi","Austin","Austin-Healey","Autobianchi","Auverland","Avanti","Beijing","Bentley","Berkeley","Bitter","Bizzarrini","BMW","Brilliance","Bristol","Bugatti","Buick","BYD","Cadillac","Caterham","Changan","Checker","Chery","Chevrolet","Chrysler","Citroen","Dacia","Daewoo","DAF","Daihatsu","Daimler","Datsun","De Tomaso","DKW","Dodge","Dongfeng","Donkervoort","Eagle","Fairthorpe","FAW","Ferrari","Fiat","Ford","GAC","GAZ","Geely","Genesis","Ginetta","GMC","Great Wall","Haval","Holden","Honda","Hudson","Humber","Hummer","Hyundai","Infiniti","Innocenti","Isuzu","Italdesign","Iveco","Jaguar","Jeep","Jensen","Kia","Lada","Lamborghini","Lancia","Land Rover","Lexus","Lifan","Lincoln","Lotec","Lotus","Mahindra","Marcos","Marussia","Maserati","Matra-Simca","Maybach","Mazda","MCC","McLaren","Mercedes","Mercedes-Benz","Mercury","MG","Mini","Mitsubishi","Monteverdi","Moretti","Morgan","Morris","Nissan","Noble","NSU","Oldsmobile","Opel","Packard","Panoz","Peugeot","Pininfarina","Plymouth","Pontiac","Porsche","Proton","Ravon","Reliant","Renault","Riley","Rolls-Royce","Rover","Saab","Samsung","Saturn","Scion","Seat","Simca","Singer","Skoda","Smart","Spyker","Ssang Yong","SsangYong","Steyr","Studebaker","Subaru","Sunbeam","Suzuki","Talbot","Tata","Tatra","Tesla","Toyota","Trabant","Triumph","TVR","UAZ","Vauxhall","VAZ","Vector","Venturi","Volkswagen","Volvo","Wartburg","Westfield","Willys-Overland","Xedos","Zagato","Zastava","ZAZ","ZIL"};

    // Выпадающий массив для цветовых оттенков машины
    private static final String[] CARSCOLOR= new String[]{"баклажановый","бежевый","белый","желтый","зеленый","коричневый","красный","оранжевый","розовый","серый ","синий","фиолетовый","черный"};

    FirebaseAuth mAuth;
    String driverPhone;
    String driverId;
    String phoneID;
    //String searchID;

    FirebaseDatabase database01;
    DatabaseReference ref01;
    DatabaseReference ref02;

    TextView checkWord;
    TextView TextHello1;
    TextView TextHello2;

    EditText editDriverName;
    EditText editCar;
    EditText editNumberCar;
    EditText editColorCar;

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



        //полуачем номер телефона и ID водителя
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser ghg = mAuth.getCurrentUser();
        driverPhone = ghg.getPhoneNumber();
        driverId = ghg.getUid();

        //запись в БД Водители-ID-driverPhone-driverId для проверки зарегистрирован ли водитель ранее или нет
        database01 = FirebaseDatabase.getInstance();
        ref01 = database01.getReference("Водители")
                .child("ID")
                .child("ID");
        ref01.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ref01.child(driverPhone).setValue(driverId);

                // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД
                ref01.removeEventListener(this);

                chesk();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    public void chesk(){

        Query aaa= FirebaseDatabase.getInstance().getReference("Водители").child("ID")
                .orderByChild( "ID" );
        aaa.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                phoneID=dataSnapshot.child(driverPhone).getValue(String.class);

                //чтение марки автомобиля по ID водителя
                String searchID=dataSnapshot.child(driverId).getValue(String.class);
                checkWord.setText(searchID);

                //Проверка есть ли уже регистрация!!!
                visible();
                }
                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                }
                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                }
                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
        });
    }

    public void visible(){
        //ааа это название марки и три цифры гос номера
        String aaa=checkWord.getText().toString();
        if (!aaa.isEmpty()){

            TextHello1.setText("Здравствуй, ");
            TextHello1.setTextColor(getResources().getColor( R.color.colorNew ));
            TextHello1.setVisibility(View.VISIBLE);

            TextHello2.setText(aaa);
            TextHello2.setTextColor(getResources().getColor( R.color.colorNew ));
            TextHello2.setVisibility(View.VISIBLE);

            //Удаление временной записи
            dellTemporaryRecord();



        }
        else {
            TextHello1.setText("Пожалуйста, ");
            TextHello1.setTextColor(getResources().getColor( R.color.colorNew ));
            TextHello1.setVisibility(View.VISIBLE);

            TextHello2.setText("зарегистируйтесь!");
            TextHello2.setTextColor(getResources().getColor( R.color.colorNew ));
            TextHello2.setVisibility(View.VISIBLE);

            //Удаление временной записи
            dellTemporaryRecord();

            editCar.setVisibility(View.VISIBLE);
            editNumberCar.setVisibility(View.VISIBLE);
            editColorCar.setVisibility(View.VISIBLE);
            editDriverName.setVisibility(View.VISIBLE);

            // Выпадающий массив для марок авто
            AutoCompleteTextView editText=findViewById(R.id.editCar);
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, CARS);
            editText.setAdapter(adapter);

            // Выпадающий массив для цвета авто
            AutoCompleteTextView editText1=findViewById(R.id.editColorCar);
            ArrayAdapter<String> adapter1=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, CARSCOLOR);
            editText1.setAdapter(adapter1);

        }

    }
    //Удаление временной записи
    public void dellTemporaryRecord(){
        ref02 = database01.getReference("Водители")
                .child("ID")
                .child("ID");
        ref02.child(driverPhone).removeValue();
    }
}
