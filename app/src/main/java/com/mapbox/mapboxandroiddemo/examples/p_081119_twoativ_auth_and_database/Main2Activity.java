package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Collections;

public class Main2Activity extends AppCompatActivity {


    Button btn_sign_out;
    Button three_window;
    //Button inAirport,inCity;
    Button start_order;
    private static final int RC_SIGN_IN = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        three_window = (Button) findViewById(R.id.three_window);
        //three_window.setOnClickListener(this);
        //inAirport = (Button) findViewById(R.id.inAirport);
        //inCity = (Button) findViewById(R.id.inCity);

        start_order = (Button) findViewById(R.id.start_order);

        doPhoneLogin();
    }
    private void doPhoneLogin() {
        Intent intent = AuthUI.getInstance().createSignInIntentBuilder()
                .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                .setAvailableProviders(Collections.singletonList(
                        new AuthUI.IdpConfig.PhoneBuilder().build()))
                .setLogo(R.mipmap.ic_launcher)
                .build();

        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        btn_sign_out =(Button)findViewById(R.id.btn_sign_out);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse idpResponse = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                showAlertDialog(user);
                btn_sign_out.setEnabled(true);
                three_window.setEnabled(true);

                start_order.setEnabled( true );
                //inAirport.setEnabled(true);
                //inCity.setEnabled(true);
            }
            else {
                Toast.makeText(getBaseContext(), "Ошибка Авторизации", Toast.LENGTH_LONG).show();
            }
        }
    }
    // Всплывающая информация
    public void showAlertDialog(FirebaseUser user) {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(
                Main2Activity.this);

        // Set Title
        mAlertDialog.setTitle("Авторизация успешна");

        // Set Message
        mAlertDialog
                .setMessage(" Номер телефона " + user.getPhoneNumber())
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        mAlertDialog.create();
        // Showing Alert Message
        mAlertDialog.show();
    }

    public void  start_order (View view){
        Intent List_Choose_direction =new Intent( this,Choose_direction.class );
        startActivity( List_Choose_direction );
    }

    /*@Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.three_window:
                Intent intent = new Intent(this,Main4Activity.class);
                startActivity(intent);
                // TODO Call second activity
                break;
            default:
                break;
        }
    }*/
        /*public void inCity(View view){
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
        nextListInAir_choise_routes.putExtra( "fourMap", "Ветлужанка-Аэропорт" );
        nextListInAir_choise_routes.putExtra( "oneMap", "КрасТэц-Аэропорт" );
        nextListInAir_choise_routes.putExtra( "twoMap", "Щорса-Аэропорт" );
        nextListInAir_choise_routes.putExtra( "treeMap", "Северный-Аэропорт" );
        nextListInAir_choise_routes.putExtra( "fourMap", "Ветлужанка-Аэропорт" );


        startActivity( nextListInAir_choise_routes);
    }*/
    }
