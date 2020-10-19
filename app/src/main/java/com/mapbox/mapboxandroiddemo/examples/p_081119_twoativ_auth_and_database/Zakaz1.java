package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

public class Zakaz1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakaz1);

        toG();

    }

public void toGo(View view){
        FragmentZakaz1 ft=new FragmentZakaz1();
    FragmentTransaction fr= getSupportFragmentManager().beginTransaction();
    fr.replace(R.id.container,ft);
    fr.commit();
}

    public void toG(){
        FragmentZakaz1 ft=new FragmentZakaz1();
        FragmentTransaction fr= getSupportFragmentManager().beginTransaction();
        fr.replace(R.id.container,ft);
        fr.commit();
    }

}