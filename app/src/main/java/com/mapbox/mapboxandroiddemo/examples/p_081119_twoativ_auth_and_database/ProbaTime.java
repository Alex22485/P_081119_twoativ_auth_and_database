package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class ProbaTime extends AppCompatActivity {
    private static final String TAG ="ProbaTime" ;

    private CheckBox mSingleShotCheckBox;
    private Button mStartButton, mCancelButton;
    private TextView mCounterTextView;

    private Timer mTimer;
    private MyTimerTask mMyTimerTask;

    TextView mtv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proba_time);
        mSingleShotCheckBox = (CheckBox) findViewById(R.id.checkBoxSingleShot);
        mStartButton = (Button) findViewById(R.id.buttonStart);
        mCancelButton = (Button) findViewById(R.id.buttonCancel);
        mCounterTextView = (TextView) findViewById(R.id.textViewCounter);

        //проверка факта подключения к интернету или сети без интернета
        mtv=findViewById(R.id.mtv);
        ConnectivityManager connectivityManager=
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(((Network)connectivityManager.getActiveNetwork())!=null)
                mtv.setText("true");
            else
                mtv.setText("fasle");
        }

        boolean a;
        a=checkInternetConnection();
//        checkInternetConnection();
        Toast.makeText( ProbaTime.this, "Подключение"+a, Toast.LENGTH_SHORT ).show();




        mStartButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


//                InterruptTimerTask interruptTimerTask =
//                        new InterruptTimerTask(Thread.currentThread());
//                timer.schedule(interruptTimerTask, 5000);
//                try {
//
//                    //задержка запроса
//                    Handler handler1 = new Handler();
//                    handler1.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            getProba();
//                        }
//                    },10000);
//
//                    // put here the portion of code that may take more than "waitTimeout"
//                }
//                finally {
//                    timer.cancel();
//                    Toast.makeText( ProbaTime.this, "Время вышло ", Toast.LENGTH_SHORT ).show();
//                }


                if (mTimer != null) {
                    mTimer.cancel();
                }

                // re-schedule timer here
                // otherwise, IllegalStateException of
                // "TimerTask is scheduled already"
                // will be thrown
                mTimer = new Timer();
                mMyTimerTask = new MyTimerTask();

                if (mSingleShotCheckBox.isChecked()) {
                    // singleshot delay 1000 ms
                    mTimer.schedule(mMyTimerTask, 1000);


                } else {
                    // delay 1000ms, repeat in 5000ms
                    mTimer.schedule(mMyTimerTask, 1000, 5000);
                }
            }
        });

        mCancelButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mTimer != null) {
                    mTimer.cancel();
                    mTimer = null;
                }
            }
        });
    }

    public boolean checkInternetConnection()
    {
        //boolean stat = false;
        boolean stat = true;
        Socket sock = new Socket();
        InetSocketAddress address = new InetSocketAddress("https://p081119twoativauthanddatabase.firebaseio.com", 404);
//
//        try
//        {
//            sock.connect(address, 3000);
//            if(sock.isConnected()) stat = true;
//        }
//        catch(Exception e)
//        {
//            stat = false;
//        }
//        finally
//        {
//            try
//            {
//                sock.close();
//            }
//            catch(Exception e){}
//        }

        return stat;
    }

//    class InterruptTimerTaskAddDel extends TimerTask {
//
//        private Thread theTread;
//        private long timeout;
//
//        public InterruptTimerTaskAddDel(Thread theTread,long i_timeout) {
//            this.theTread = theTread;
//            timeout=i_timeout;
//        }
//
//        @Override
//        public void run() {
//            try {
//
//                //задержка запроса
//                Handler handler1 = new Handler();
//                handler1.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        getProba();
//                    }
//                },500);
//
//                Thread.currentThread().sleep(timeout);
//
//            } catch (InterruptedException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace(System.err);
//            }
//            theTread.interrupt();
//        }
//
//    }
//
//
//    protected class InterruptTimerTask extends TimerTask {
//
//        private Thread theTread;
//
//        public InterruptTimerTask(Thread theTread) {
//            this.theTread = theTread;
//        }
//
//        @Override
//        public void run() {
//            theTread.interrupt();
//        }
//
//    }
//
//    public void getProba(){
//        Toast.makeText( ProbaTime.this, "Чтение успешно ", Toast.LENGTH_SHORT ).show();
//    }

    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                    "dd:MMMM:yyyy HH:mm:ss a", Locale.getDefault());
            final String strDate = simpleDateFormat.format(calendar.getTime());

            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    mCounterTextView.setText(strDate);
                }
            });
        }
    }
}
