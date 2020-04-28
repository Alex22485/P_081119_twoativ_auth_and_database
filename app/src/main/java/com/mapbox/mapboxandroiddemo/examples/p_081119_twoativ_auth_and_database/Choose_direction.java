package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Choose_direction extends AppCompatActivity {

    Button inAirport,inCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_choose_direction );

        inAirport = findViewById(R.id.inAirport);
        inCity = findViewById(R.id.inCity);

    }

    public void inCity(View view){
        Intent nextListInAir_choise_routes = new Intent( this,InAir_choise_routes.class );

        nextListInAir_choise_routes.putExtra( "Маршрут", "Аэропорт-Красноярск" );
        nextListInAir_choise_routes.putExtra( "oneMap", "Аэропорт-КрасТэц" );
        nextListInAir_choise_routes.putExtra( "twoMap", "Аэропорт-Щорса" );
        nextListInAir_choise_routes.putExtra( "treeMap", "Аэропорт-Северный" );
        nextListInAir_choise_routes.putExtra( "fourMap", "Аэропорт-Ветлужанка" );
        startActivity( nextListInAir_choise_routes);
    }

    public void inAirport(View view){
        Intent nextListInAir_choise_routes = new Intent( this,InAir_choise_routes.class );

        nextListInAir_choise_routes.putExtra( "Маршрут", "Красноярск-Аэропорт" );
        nextListInAir_choise_routes.putExtra( "oneMap", "КрасТэц-Аэропорт" );
        nextListInAir_choise_routes.putExtra( "twoMap", "Щорса-Аэропорт" );
        nextListInAir_choise_routes.putExtra( "treeMap", "Северный-Аэропорт" );
        nextListInAir_choise_routes.putExtra( "fourMap", "Ветлужанка-Аэропорт" );


        startActivity( nextListInAir_choise_routes);
    }

    // кнопка Back сворачивает приложение
    @Override
    public void onBackPressed(){
        this.moveTaskToBack(true);
    }

}
