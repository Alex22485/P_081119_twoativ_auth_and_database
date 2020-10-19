package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FragmentZakaz2 extends Fragment {

    TextView data;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_zakaz2, container, false);
        data= view.findViewById(R.id.data);

        Bundle bundle =this.getArguments();
        //ddd=bundle.getString("key");
        data.setText(bundle.getString("key"));
        return view;
    }
}