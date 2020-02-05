package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;


import android.util.Log;


import com.google.firebase.messaging.FirebaseMessagingService;

public class GettingDeviceTokenService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d("NEW_TOKEN",s);
    }

}
