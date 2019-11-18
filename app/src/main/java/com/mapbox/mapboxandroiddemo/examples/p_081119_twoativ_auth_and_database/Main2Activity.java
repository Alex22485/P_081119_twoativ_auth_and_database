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

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {


    Button btn_sign_out;
    Button three_window;
    private static final int RC_SIGN_IN = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        three_window = (Button) findViewById(R.id.three_window);
        three_window.setOnClickListener(this);


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
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(

                );
                showAlertDialog(user);
                btn_sign_out.setEnabled(true);




            } else {
                /**
                 *   Sign in failed. If response is null the user canceled the
                 *   sign-in flow using the back button. Otherwise check
                 *   response.getError().getErrorCode() and handle the error.
                 */
                Toast.makeText(getBaseContext(), "Ошибка Автоирзации", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void showAlertDialog(FirebaseUser user) {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(
                Main2Activity.this);

        // Set Title
        mAlertDialog.setTitle("Авторизация успешна");

        // Set Message
        mAlertDialog.setMessage(" Номер телефона " + user.getPhoneNumber());

        mAlertDialog.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        mAlertDialog.create();

        // Showing Alert Message
        mAlertDialog.show();
    }

    @Override
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
    }
}