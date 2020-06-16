package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainUserNewOne4 extends AppCompatActivity {

    String refCity;

    private static final String TAG ="MainUserNewOne4" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user_new_one4);

        //для транзита из MainUserOne3
        Intent mainUserNewOne4 = getIntent();
        refCity =mainUserNewOne4.getStringExtra( "refCity" );
        Log.d(TAG, "refCity: "+refCity);


    }

    public void fromKrasnoyarsk(View view){
        Intent nextListInAir_choise_routes = new Intent( this,InAir_choise_routes.class );

        nextListInAir_choise_routes.putExtra( "Маршрут", "Красноярск-Аэропорт" );
        nextListInAir_choise_routes.putExtra( "oneMap", "КрасТэц-Аэропорт" );
        nextListInAir_choise_routes.putExtra( "twoMap", "Щорса-Аэропорт" );
        nextListInAir_choise_routes.putExtra( "treeMap", "Северный-Аэропорт" );
        nextListInAir_choise_routes.putExtra( "fourMap", "Ветлужанка-Аэропорт" );

        nextListInAir_choise_routes.putExtra( "toOrFrom", "Из Красноярска" );

        //транзит из MainUserOne3
        nextListInAir_choise_routes.putExtra( "refCity", refCity );
        startActivity( nextListInAir_choise_routes);
    }

    public void toKrasnoyarsk(View view){
        Intent nextListInAir_choise_routes = new Intent( this,InAir_choise_routes.class );

        nextListInAir_choise_routes.putExtra( "Маршрут", "Аэропорт-Красноярск" );
        nextListInAir_choise_routes.putExtra( "oneMap", "Аэропорт-КрасТэц" );
        nextListInAir_choise_routes.putExtra( "twoMap", "Аэропорт-Щорса" );
        nextListInAir_choise_routes.putExtra( "treeMap", "Аэропорт-Северный" );
        nextListInAir_choise_routes.putExtra( "fourMap", "Аэропорт-Ветлужанка" );

        nextListInAir_choise_routes.putExtra( "toOrFrom", "В Красноярск" );

        //транзит из MainUserOne3
        nextListInAir_choise_routes.putExtra( "refCity", refCity );
        startActivity( nextListInAir_choise_routes);
    }


}
