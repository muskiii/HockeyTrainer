package com.dev.fabianos.hockeyt;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
    Chronometer mChronometer;
    LinearLayout ll;

    static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mChronometer = (Chronometer) findViewById(R.id.chronometer);
        Log.i(TAG, "starting the app");

        final Button btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(mStartListener);


        final Button buttonStop = (Button) findViewById(R.id.btnStop);
        buttonStop.setOnClickListener(mStopListener);

        final Button buttonReset = (Button) findViewById(R.id.btnRestart);
        buttonReset.setOnClickListener(mResetListener);


//        final TextView txtTitulo = (TextView) findViewById(R.id.txtTitulo);
//
//        btnStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i(TAG, "---------------Clicking----------------");
////                Timer timer = new Timer();
////                TimerTask onOffTimer = new CustomTimerTask(MainActivity.this);
////                timer.scheduleAtFixedRate(onOffTimer, 0, 1000);
//            }
//        });
    }

    View.OnClickListener mStartListener = new View.OnClickListener() {
        public void onClick(View v) {
            mChronometer.start();
            Log.i(TAG, "START");

        }
    };

    View.OnClickListener mStopListener = new View.OnClickListener() {
        public void onClick(View v) {
            mChronometer.stop();
            Log.i(TAG, "STOP");
        }
    };

    View.OnClickListener mResetListener = new View.OnClickListener() {
        public void onClick(View v) {
            mChronometer.setBase(SystemClock.elapsedRealtime());
            Log.i(TAG, "RESET");
        }
    };




//    public class CustomTimerTask extends TimerTask {
//
//        private Handler mHandler = new Handler();
//        public int a = 0;
//        final TextView txtTitulo = (TextView) findViewById(R.id.txtTitulo);
//
//        public CustomTimerTask(MainActivity mainActivity) {
//        }
//
//        @Override
//        public void run() {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    mHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            txtTitulo.setText(String.valueOf(a));
//                            a++;
//                        }
//                    });
//                }
//            }).start();
//        }
//    }
}
