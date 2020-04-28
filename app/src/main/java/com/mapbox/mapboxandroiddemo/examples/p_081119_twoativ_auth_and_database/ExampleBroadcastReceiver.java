package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

// не используется
//Класс проверки интернета для MainActivity. выдает соответствующую всплывающую информацию при запуске окна MainActivity. но если Wi-fi подклчючен без интернета, то показывает что есть интернет, что не правильно

public class ExampleBroadcastReceiver extends BroadcastReceiver {


        @Override
    public void onReceive(final Context context, Intent intent) {

    }


}


