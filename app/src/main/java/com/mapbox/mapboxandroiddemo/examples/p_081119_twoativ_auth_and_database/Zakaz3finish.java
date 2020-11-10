package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class Zakaz3finish extends AppCompatActivity {

    private static final String TAG ="Zakaz3finish" ;

    String newToken;
    // формируется ok после ввода телефона при авторизации
    String authOK="";

    //добавлено 10.11.20 из Main3Activity
    String tOBeforReg;
    String proverkaBeforRegistraion;
    String timeOut;
    String proverka;
    FirebaseDatabase database01;
    DatabaseReference ref01;

    String phoneNew,Calend,RefplaneCity,time,RefMap,RefPoint;
    TextView Calend1,RefMap1,RefPoint1,RefplaneCity1,time1,NumberCharter;
    TextView text0,text1,text2,text3,text4,text5,text6,text7;
    Button btnTime,btnOder,button9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakaz3finish);

        Calend1=findViewById(R.id.Calend1);
        RefMap1=findViewById(R.id.RefMap1);
        RefPoint1=findViewById(R.id.RefPoint1);
        RefplaneCity1=findViewById(R.id.RefplaneCity1);
        time1=findViewById(R.id.time1);
        text0=findViewById(R.id.text0);
        text1=findViewById(R.id.text1);
        text2=findViewById(R.id.text2);
        text3=findViewById(R.id.text3);
        NumberCharter=findViewById(R.id.NumberCharter);
        text4=findViewById(R.id.text4);
        text5=findViewById(R.id.text5);
        text6=findViewById(R.id.text6);
        text7=findViewById(R.id.text7);
        btnTime=findViewById(R.id.btnTime);
        btnOder=findViewById(R.id.btnOder);
        button9=findViewById(R.id.button9);

        // Получить Токен!!!!
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(Zakaz3finish.this,new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        newToken = instanceIdResult.getToken();
                        Log.d(TAG, "newToken: "+newToken);
                    }
                }
        );
        // экспорт из листа регисттрации после успешного ввода телефона поучаем OK
        Intent Main3Activity= getIntent();
        authOK= "K"+ Main3Activity.getStringExtra("authOk");
        Log.d(TAG, "authOk: "+authOK);

        if(authOK.equals("KOk")){
            //TextProgress.setVisibility(View.VISIBLE);
            Log.d(TAG, "автоматическая регистрация после авторизации: ");

            // изменено 10.11.20
//            refCity=Main3Activity.getStringExtra("refCity");
//            toOrFrom=Main3Activity.getStringExtra("toOrFrom");
//            MapTop=Main3Activity.getStringExtra("MapTop");
//            Calend.setText(Main3Activity.getStringExtra("Calend"));
//            CalendTime.setText(Main3Activity.getStringExtra("CalendTime"));
//            Flight.setText(Main3Activity.getStringExtra("Flight"));
//            time.setText(Main3Activity.getStringExtra("time"));
//            TVchoiseMap=Main3Activity.getStringExtra("TVchoiseMap");
//            phoneNew=Main3Activity.getStringExtra("phoneNew");

            // телефон
            phoneNew=Main3Activity.getStringExtra("phoneNew");
            // дата поездки
            Calend=Main3Activity.getStringExtra("Calend");
            // рейс самолета
            RefplaneCity=Main3Activity.getStringExtra("RefplaneCity");
            // маршрут такси
            RefMap=Main3Activity.getStringExtra("RefMap");
            // пункт сбора
            RefPoint=Main3Activity.getStringExtra("RefPoint");
            // время вылета/прилета/номер рейса для чартера
            time=Main3Activity.getStringExtra("time");

            // автоматическая регистрация ранее сформированной заявки после авторизации
            //задержка чтобы успел записаться NO в БД Заявки из другого кода
            Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    btnInsertd ();
                }
            },4000);
        }


        // получение данных из Zakaz1
        Intent Zakaz1ToZakaz3finish = getIntent();
        // secretNumber
        phoneNew=""+Zakaz1ToZakaz3finish.getStringExtra("phoneNew");
        // дата полета
        Calend=""+Zakaz1ToZakaz3finish.getStringExtra("Calend");
        // рейс самолета
        RefplaneCity=""+Zakaz1ToZakaz3finish.getStringExtra("RefplaneCity");
        // время вылета/прилета/номер рейса для чартера
        time=""+Zakaz1ToZakaz3finish.getStringExtra("time");
        // маршрут
        RefMap=""+Zakaz1ToZakaz3finish.getStringExtra("RefMap");
        // точка сбора
        RefPoint=""+Zakaz1ToZakaz3finish.getStringExtra("RefPoint");

        if (time.equals("1 рейс")||time.equals("2 рейс")||time.equals("3 рейс")){
            text3.setText("Чартер ");
            NumberCharter.setText(time);
            btnTime.setVisibility(View.VISIBLE);
            time1.setVisibility(View.INVISIBLE);
        }
        else{
            time1.setText(time);
            btnTime.setVisibility(View.INVISIBLE);
        }

        Calend1.setText(Calend);
        RefMap1.setText(RefMap);
        RefPoint1.setText(RefPoint);
        RefplaneCity1.setText(RefplaneCity);

        Log.d(TAG, "phoneNew:"+phoneNew);
        Log.d(TAG, "Calend:"+Calend);
        Log.d(TAG, "RefplaneCity:"+RefplaneCity);
        Log.d(TAG, "time:"+time);
        Log.d(TAG, "RefMap:"+RefMap);
        Log.d(TAG, "RefPoint:"+RefPoint);
    }
    // Кнопка зарегистрировать заявку
    public void btnOder(View view) {
        if (phoneNew.equals("null")) {

            AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz3finish.this);
            mAlertDialog.setMessage("Для продолжения необходимо" +
                    " авторизироваться." +
                    " Вам придет SMS c кодом подтверждения")
                    .setPositiveButton("Принять", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            goListRegistration();
                        }
                    })
                    .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            mAlertDialog.create();
            mAlertDialog.show();
        }
        else {
            //запуск ПРОЦЕССА регистрации заявки
                btnInsertd();
            }
        }

    //запуск ПРОЦЕССА регистрации заявки
    public void btnInsertd(){
        //TextProgress.setVisibility(View.VISIBLE);
        Log.d(TAG, "Старт Проверка интернета YesNO");


        tOBeforReg="";
        proverkaBeforRegistraion="";

        //ТАЙМ-АУТ проверка интернета
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {

                // Завершен ТАЙМ-АУТ проверка интернета
                tOBeforReg="Out";
                intNotBeforRegistraion();
            }
        },20000);

        //Важно в БД с читаемым объектом не должно быть параллельных линий :)
        // только тогда считывает значения с первого раза без null
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        Query query = rootRef.child("Пользователи")
                .child("Personal")
                .child(phoneNew)
                .child("Proverka")
                .orderByChild("Oder");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ddd : dataSnapshot.getChildren()) {

                    String yesNo=ddd.child("Заявка").getValue(String.class);

                    proverkaBeforRegistraion=yesNo;
                    Log.d(TAG, "инетрнет есть, заявка есть?"+yesNo);

                    // проверка YEsNo
                    YesNoBeforeReg();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    // добавлено 10/11/20 из Main3Activity
    public void YesNoBeforeReg(){
        if(tOBeforReg.equals("Out")){
            Log.d(TAG, "Время проверки интернета вышло, но интернет есть");
        }
        else if (proverkaBeforRegistraion.equals("No")){
            Log.d(TAG, "Интернет есть, старт регистрации");
            startRegistration();

        }
        else if (proverkaBeforRegistraion.equals("Yes")){
            Log.d(TAG, "Интернет есть, НО есть старая заявка");
            showAlertDialog5();
        }
    }


    // добавлено 10/11/20 из Main3Activity
    // !!!!!Переделка на private Room 30.05.20
    public void startRegistration(){
        Log.d(TAG, "Старт Регистрации");

        timeOut="";
        proverka="";

        //ТАЙМ-АУТ ЗАПРОСА ИНТЕРНЕТА
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Завершен ТАЙМ-АУТ ЗАПРОСА ИНТЕРНЕТА-2 при проверке регистрации
                timeOut="Out";
                internetNot();
            }
        },15000);

            //030320 Запись токена для проверки Разрешения на запись заявки в БД
            database01 = FirebaseDatabase.getInstance();
            ref01 = database01.getReference("Пользователи")
                    .child("Personal")
                    .child(phoneNew)
                    .child("Заявки")
                    .child(Calend)
                    .child(RefplaneCity)
                    .child(time)
                    .child(RefMap)
                    .child(RefPoint)
                    .child("CheckStopOder")
                    .child(phoneNew);
            ref01.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                ref01.child(newToken).setValue(phoneNew);
                                                //запускаем метод
                                                Qwery();
                                                // ОСТАНАВЛИВАЕМ ПРОСЛУШИВАНИЕ БД БД ЗАЯВКИ...-...-...-"CheckStopOder"...
                                                ref01.removeEventListener(this);
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                            }
                                        }
            );
    }



    // добавлено 10/11/20 из Main3Activity
    public void intNotBeforRegistraion(){
        if (!proverkaBeforRegistraion.isEmpty()){
            Log.d(TAG, "время проверки интернета вышло, но интернет есть");
        }
        else{
            Log.d(TAG, "Время проверки вышло, not internet");
            showAlertDialog4();
        }
    }

    // переход на лист АВТОРИЗАЦИИ
    public void goListRegistration(){
        Intent main3Activity =new Intent(this, Main2Activity.class);
//        main3Activity.putExtra("refCity",refCity);
//        main3Activity.putExtra("toOrFrom",toOrFrom);
//        main3Activity.putExtra("MapTop",MapTop);
//        main3Activity.putExtra("Calend",Calend.getText().toString());
//        main3Activity.putExtra("CalendTime",CalendTime.getText().toString());
//        main3Activity.putExtra("Flight",Flight.getText().toString());
//        main3Activity.putExtra("time",time.getText().toString());
//        main3Activity.putExtra("TVchoiseMap",TVchoiseMap);
//        main3Activity.putExtra("TVchoise_pointMap",TVchoise_pointMap);
        // secretNumber
        main3Activity.putExtra("phoneNew",phoneNew);
        // дата полета
        main3Activity.putExtra("Calend",Calend);
        // рейс самолета
        main3Activity.putExtra("RefplaneCity",RefplaneCity);
        // время вылета/прилета/номер рейса для чартера
        main3Activity.putExtra("time",time);
        // маршрут
        main3Activity.putExtra("RefMap",RefMap);
        // точка сбора
        main3Activity.putExtra("RefPoint",RefPoint);

        startActivity(main3Activity);

    }

    // добавлено 10/11/20 из Main3Activity
    // Всплывающая информация "Заявка отклонена!!!"
    public void showAlertDialog() {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz3finish.this);
        // Set Title
        mAlertDialog.setTitle("Заявка отклонена :(");
        mAlertDialog.setCancelable(false);
        // Set Message
        mAlertDialog
                .setMessage("Точка сбора"+" "+RefPoint+" "+"уже сформирована. Проверьте другие, ближайшие к вам точки сбора данного маршрута")
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onBackList();
                    }
                });
        mAlertDialog.create();
        // Showing Alert Message
        mAlertDialog.show();
    }
    // Всплывающая информация "Заявка оформлена!!!"
    public void showAlertDialog1() {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Zakaz3finish.this);
        // Set Title
        mAlertDialog.setTitle("Спасибо, заявка оформлена!!!");
        mAlertDialog.setCancelable(false);
        // Set Message
        mAlertDialog
                .setMessage("Ищем автомобиль..."+" "+"Вы получите уведомление о результате поиска")
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //переход в окно статуса лист после получения 6
                        onStatusList();
                    }
                });
        mAlertDialog.create();
        // Showing Alert Message
        mAlertDialog.show();
    }
    // Всплывающая информация "Повтор!!!"
    public void showAlertDialog3() {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(
                Zakaz3finish.this);
        // Set Title
        mAlertDialog.setTitle("!!!");
        mAlertDialog.setCancelable(false);
        // Set Message
        mAlertDialog
                .setMessage("По этому направлению вы уже зарегистрированы ранее")
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //переход в окно статуса лист 6
                        onStatusList();
                    }
                });
        mAlertDialog.create();
        // Showing Alert Message
        mAlertDialog.show();
    }

    public void showAlertDialog4(){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(
                Zakaz3finish.this);
        // Set Title
        mAlertDialog.setTitle("Ошибка регистрации");
        mAlertDialog.setCancelable(false);
        // Set Message
        mAlertDialog
                .setMessage("проверьте настройки интернета")
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //изменено 10.11.20
//                        MistakeRegistration.setVisibility(View.VISIBLE);
//                        TextProgress.setVisibility(View.GONE);
//                        TextRegistration.setVisibility(View.VISIBLE);
                        //добавлено 10.11.20
                        Toast.makeText(Zakaz3finish.this,"ОШИБКА РЕГИСТРАЦИИ НЕТ ИНТЕРНЕТА....",Toast.LENGTH_LONG).show();
                    }
                });
        mAlertDialog.create();
        // Showing Alert Message
        mAlertDialog.show();
    }
    public void showAlertDialog5(){
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(
                Zakaz3finish.this);
        // Set Title
        mAlertDialog.setTitle("Найдена ваша старая заявка");
        mAlertDialog.setCancelable(false);
        // Set Message
        mAlertDialog
                .setMessage("отмените старую заявку")
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //Переход на лист Статуса
                        onStatusList();

                    }
                });
        mAlertDialog.create();
        // Showing Alert Message
        mAlertDialog.show();
    }

    // Переход на лист Статуса
    public void onStatusList() {
        //Переход на лист Статуса
        Intent Main3ActivTOMain6 = new Intent( this,Main6Activity.class );
        Main3ActivTOMain6.putExtra("phoneRef",phoneNew);
        startActivity(Main3ActivTOMain6);
    }
    // Переход на лист выбора точки сбора
    public void onBackList() {
        //Переходд на лист регистрации заявки
        Intent Main3ToMainUserNewOne3 = new Intent( this,MainUserNewOne3.class );
        // отправляем phoneNew в Main3Activity
        Main3ToMainUserNewOne3.putExtra("regFromMain3",phoneNew);
        startActivity( Main3ToMainUserNewOne3);
    }

    public void internetNot(){
        if (!proverka.isEmpty()){
            Log.d(TAG, "время вышло но Разрешение/Запрет получен из БД");
        }
        else{
            Log.d(TAG, "Время поиска статуса вышло not internet");
            Intent Main6ActivityNotInternet  = new Intent(this,Main6ActivityNotInternet.class);
            startActivity(Main6ActivityNotInternet);
        }
    }

    public void Qwery(){
        Log.d(TAG, "старт запроса Разрешения/Запрет");
            // проверяем какое слово написано в объекте РазрешениеНаЗапись.
            // Если Разрешено то запись заявки оформляется, если нет то заявка отклонена (процесс записи и отклонения выполнен в nod js function OderCheck)
            final Query aaa1= FirebaseDatabase.getInstance().getReference("Пользователи")
                    .child("Personal")
                    .child(phoneNew)
                    .child("Заявки")
                    .child(Calend)
                    .child(RefplaneCity)
                    .child(time)
                    .child(RefMap)
                    .child(RefPoint)
                    .child("Разрешение")
                    .child(phoneNew)
                    .orderByChild("Разрешение");
            aaa1.addChildEventListener( new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    String data=dataSnapshot.child( "РазрешениеНаЗапись" ).getValue(String.class);
                    Log.d(TAG, "РазрешениеНаЗапись"+data);/*специально пусто*/

                    proverka=data;

                    // Проверяем закончилось ли время опроса time-out
                    checkWordProverka();

                    //Останавливаем прослушивание, чтобы в приложении у другого пользователя не появлялась информация когда другой пользоваьель регистрирует заявку
                    aaa1.removeEventListener(this);
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
            } );
    }
    public void checkWordProverka(){
        if(timeOut.equals("Out")){
            Log.d(TAG, "Разрешение/Запретс получен но время вышло");
        }
        else if(proverka.equals("Разрешено")){
            Log.d(TAG, "Разрешено");
            //TextProgress.setVisibility(View.GONE);
            showAlertDialog1();
        }
        else if (proverka.equals("Запрещено")){
            Log.d(TAG, "Запрещено");
            //TextProgress.setVisibility(View.GONE);
            showAlertDialog();
        }
        else if (proverka.equals("Повтор")){
            Log.d(TAG, "Повтор");
            //TextProgress.setVisibility(View.GONE);
            showAlertDialog3();
        }
    }


}