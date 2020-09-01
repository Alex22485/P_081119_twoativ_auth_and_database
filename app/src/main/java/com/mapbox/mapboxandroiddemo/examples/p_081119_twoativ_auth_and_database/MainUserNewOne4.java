package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainUserNewOne4 extends AppCompatActivity {

    String refCity;
    String phoneNew;

    private static final String TAG ="MainUserNewOne4" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user_new_one4);

        //для транзита из MainUserOne3
        Intent mainUserNewOne3TomainUserNewOne4 = getIntent();
        refCity =mainUserNewOne3TomainUserNewOne4.getStringExtra( "refCity" );

        //транзит в Main3Activity
        phoneNew=mainUserNewOne3TomainUserNewOne4.getStringExtra( "phoneNew" );
        Log.d(TAG, "refCity: "+refCity);
        Log.d(TAG, "phoneNew: "+phoneNew);


    }

    public void fromKrasnoyarsk(View view){
        Intent MainUserNewOne4ToListInAir_choise_routes = new Intent( this,InAir_choise_routes.class );

        MainUserNewOne4ToListInAir_choise_routes.putExtra( "Маршрут", "Красноярск-Аэропорт" );
        MainUserNewOne4ToListInAir_choise_routes.putExtra( "oneMap", "КрасТэц-Аэропорт" );
        MainUserNewOne4ToListInAir_choise_routes.putExtra( "twoMap", "Щорса-Аэропорт" );
        MainUserNewOne4ToListInAir_choise_routes.putExtra( "treeMap", "Северный-Аэропорт" );
        MainUserNewOne4ToListInAir_choise_routes.putExtra( "fourMap", "Ветлужанка-Аэропорт" );
        MainUserNewOne4ToListInAir_choise_routes.putExtra( "toOrFrom", "Из Красноярска" );

        //транзит в MainUserOne3
        MainUserNewOne4ToListInAir_choise_routes.putExtra( "refCity", refCity );

        //транзит в Main3Activity
        MainUserNewOne4ToListInAir_choise_routes.putExtra("phoneNew",phoneNew);

        startActivity( MainUserNewOne4ToListInAir_choise_routes);
    }

    public void toKrasnoyarsk(View view){
        Intent MainUserNewOne4ToListInAir_choise_routes = new Intent( this,InAir_choise_routes.class );

        MainUserNewOne4ToListInAir_choise_routes.putExtra( "Маршрут", "Аэропорт-Красноярск" );
        MainUserNewOne4ToListInAir_choise_routes.putExtra( "oneMap", "Аэропорт-КрасТэц" );
        MainUserNewOne4ToListInAir_choise_routes.putExtra( "twoMap", "Аэропорт-Щорса" );
        MainUserNewOne4ToListInAir_choise_routes.putExtra( "treeMap", "Аэропорт-Северный" );
        MainUserNewOne4ToListInAir_choise_routes.putExtra( "fourMap", "Аэропорт-Ветлужанка" );
        MainUserNewOne4ToListInAir_choise_routes.putExtra( "toOrFrom", "В Красноярск" );

        //транзит из MainUserOne3
        MainUserNewOne4ToListInAir_choise_routes.putExtra( "refCity", refCity );

        //транзит в Main3Activity
        MainUserNewOne4ToListInAir_choise_routes.putExtra("phoneNew",phoneNew);

        startActivity( MainUserNewOne4ToListInAir_choise_routes);
    }


}
