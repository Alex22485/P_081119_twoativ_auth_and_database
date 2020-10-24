package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
//import android.app.DialogFragment;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;
import java.util.Calendar;
import androidx.fragment.app.DialogFragment;

public class Proba extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proba);
        // Get the widgets reference from XML layout

        Button btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize a new date picker dialog fragment
                DialogFragment dFragment = new DatePickerFragment();

                // Show the date picker dialog fragment
                dFragment.show(getSupportFragmentManager(), "Date Picker");
            }
        });
    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener{

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                    AlertDialog.THEME_HOLO_LIGHT,this,year,month,day);

            dpd.setTitle("выберите дату"); // Uncomment this line to activate it

            // Return the DatePickerDialog
            return  dpd;

            // Create a TextView programmatically.
            //TextView tv = new TextView(getActivity());

            // Create a TextView programmatically
//            LayoutParams lp = new RelativeLayout.LayoutParams(
//                    LayoutParams.WRAP_CONTENT, // Width of TextView
//                    LayoutParams.WRAP_CONTENT); // Height of TextView
//            tv.setLayoutParams(lp);
//            tv.setPadding(10, 10, 10, 10);
//            tv.setGravity(Gravity.CENTER);
//            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
//            tv.setText("This is a custom title.");
//            tv.setTextColor(Color.parseColor("#ff0000"));
//            tv.setBackgroundColor(Color.parseColor("#FFD2DAA7"));

            // Set the newly created TextView as a custom tile of DatePickerDialog
            //dpd.setCustomTitle(tv);

            // Or you can simply set a tile for DatePickerDialog
            /*
                setTitle(CharSequence title)
                    Set the title text for this dialog's window.
            */

        }

        public void onDateSet(DatePicker view, int year, int month, int day){
            // Do something with the chosen date
        }
    }
}