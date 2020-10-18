package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class FragmentZakaz1 extends Fragment {

    TextView text2;
    String [] listCityTaxi= {"Красноярск","Сосновоборск","Ачинск","Канск","Лесосибирск"};
    String refCityTaxi;
    String refInFromCity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       //return inflater.inflate(R.layout.fragment_zakaz1, container, false);

        View view = inflater.inflate(R.layout.fragment_zakaz1,
                container, false);
        Button button1 = (Button) view.findViewById(R.id.button1);
        text2= (TextView) view.findViewById(R.id.text1);
        button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                text2.setText("rrhrthrth");
            }
        });

        // выпадающий список городов
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(view.getContext());
        // Set Title
        mAlertDialog.setTitle("Выбери город");

        // Set Message
        mAlertDialog
                .setItems(listCityTaxi, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        refCityTaxi=listCityTaxi[which];

                        InFromCity();
                    }
                });

        mAlertDialog.create();
        // Showing Alert Message
        mAlertDialog.show();

        return view;
    }

    public void InFromCity(){

        final String [] InFromCity={refCityTaxi+"->Аэропорт","Аэропорт->"+refCityTaxi};

        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(getView().getContext());
        // Set Title
        mAlertDialog.setTitle("Куда поедем");

        // Set Message
        mAlertDialog
                .setItems(InFromCity, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      refInFromCity=InFromCity[which];

                    }
                });

        mAlertDialog.create();
        // Showing Alert Message
        mAlertDialog.show();

    };
}
