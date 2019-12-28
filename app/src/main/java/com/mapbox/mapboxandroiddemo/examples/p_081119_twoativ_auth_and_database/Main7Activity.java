package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main7Activity extends AppCompatActivity {

    // НЕ ИСПОЛЬЗОВАТЬ

    String SENT_SMS="SENT SMS";
    String DELIVER_SMS="DELIVER_SMS";

    EditText EditText,PhoneN;
    Button SendBtbn;

    Intent sent_intenet=new Intent(SENT_SMS);
    Intent deliver_intent= new Intent(DELIVER_SMS);
    PendingIntent sent_pi,deliver_pi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        EditText = findViewById(R.id.EditText);
        PhoneN = findViewById(R.id.PhoneN);
        SendBtbn = findViewById(R.id.SendBtbn);

        sent_pi=PendingIntent.getBroadcast(Main7Activity.this,0,sent_intenet,0);
        deliver_pi=PendingIntent.getBroadcast(Main7Activity.this,0,deliver_intent,0);


        SendBtbn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(PhoneN.getText().toString(),null,EditText.getText().toString(),sent_pi,deliver_pi);




                /*Intent intent =new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("smsto:"+PhoneN.getText().toString()));
                intent.putExtra("sms_body",EditText.getText().toString());
                startActivity(intent);*/
            }
        });

    }

    /*@Override
    protected void onPostResume() {
        super.onPostResume();
        registerReceiver(sentReciver,new IntentFilter(SENT_SMS));
        registerReceiver(deliverReciver,new IntentFilter(DELIVER_SMS));

    }*/

  @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(sentReciver,new IntentFilter(SENT_SMS));
        registerReceiver(deliverReciver,new IntentFilter(DELIVER_SMS));
    }

    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(sentReciver);
        unregisterReceiver(deliverReciver);
    }

    BroadcastReceiver sentReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            switch (getResultCode()){
                case Activity.RESULT_OK:
                    Toast.makeText(context,"Sented",Toast.LENGTH_LONG ).show();
                    break;
                default:
                    Toast.makeText(context,"Error sent",Toast.LENGTH_LONG ).show();
                    break;
            }

        }
    };
    BroadcastReceiver deliverReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            switch (getResultCode()){
                case Activity.RESULT_OK:
                    Toast.makeText(context,"Delivered",Toast.LENGTH_LONG ).show();
                    break;
                default:
                    Toast.makeText(context,"Error Deliver",Toast.LENGTH_LONG ).show();
                    break;
            }

        }
    };
}
