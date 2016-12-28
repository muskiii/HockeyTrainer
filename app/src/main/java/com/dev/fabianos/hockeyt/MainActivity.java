package com.dev.fabianos.hockeyt;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.RelativeLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements Chronometer.OnChronometerTickListener, ActivityCompat.OnRequestPermissionsResultCallback {

    RelativeLayout rl;
    Chronometer mChronometer;
    Button start, restart;
    private long mLastStopTime;
    private boolean running = false;
    private boolean stoped = true;
    SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
    //    ---LOGGING---
    private static final String TAG = "MyActivity";

    MyContext myContext = new MyContext();
    UntouchState untouchState = new UntouchState();
    StartResumeState startResumeState = new StartResumeState();
    StopedState stopedState = new StopedState();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ---KEEP SCREEN ON---
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

//        ---Views---
        rl = (RelativeLayout) findViewById(R.id.rl);
        start = (Button) findViewById(R.id.btnStartResume);
        restart = (Button) findViewById(R.id.btnRestart);

//        ---DYNAMICALYL ADDED CHRONOMETER---
        mChronometer = new Chronometer(MainActivity.this);
        mChronometer.setOnChronometerTickListener(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                ((int) LayoutParams.WRAP_CONTENT, (int) LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.setMargins(0, 500, 0, 0);
        mChronometer.setLayoutParams(params);
        mChronometer.setTextSize((float) 50);

        restart.setEnabled(false);
        rl.addView(mChronometer);


//        --START ON CLICK---
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myContext.getState().getClass() == UntouchState.class) {
                    untouchState.doAction(myContext, mChronometer);
                    restart.setEnabled(true);
                } else if (myContext.getState().getClass() == StopedState.class) {
                    long intervalOnPause = (SystemClock.elapsedRealtime() - mLastStopTime);
                    mChronometer.setBase(mChronometer.getBase() + intervalOnPause);
                    startResumeState.doAction(myContext, mChronometer);
                } else if (myContext.getState().getClass() == StartResumeState.class) {
                    mLastStopTime = SystemClock.elapsedRealtime();
                    stopedState.doAction(myContext, mChronometer);
                }
            }
        });

//        --RESTART ON CLICK---
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLastStopTime = 0;
                untouchState.doAction(myContext, mChronometer);
            }
        });
    }

    //    ---TO DO WHILE TICKING---
    @Override
    public void onChronometerTick(Chronometer chronometer) {
        Random r = new Random();
        int high = 5;
        Date date = null;
        try {
            date = sdf.parse((String) chronometer.getText());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long seconds = date.getTime() / 1000;
        if (seconds % 2 == 0) {
            switch (r.nextInt(high)) {
                case 0:
                    rl.setBackgroundColor(0xFFff0000);
                    break;
                case 1:
                    rl.setBackgroundColor(0xFFffff00);
                    break;
                case 2:
                    rl.setBackgroundColor(0xFF00ff00);
                    break;
                case 3:
                    rl.setBackgroundColor(0xFF0040ff);
                    break;
                case 4:
                    rl.setBackgroundColor(0xFFFFFFFF);
                    break;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


//    ------------------------------Example on managing permission---------------------------------
// <uses-permission android:name="android.permission.WAKE_LOCK"></uses-permission>
//    private static final int REQUEST_WAKE_LOCK = 0;
//    protected PowerManager.WakeLock mWakeLock;
//    if (ContextCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WAKE_LOCK}, REQUEST_WAKE_LOCK);
//        } else {
//            final PowerManager pm = (PowerManager) getSystemService(MyContext.POWER_SERVICE);
//            this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
//            this.mWakeLock.acquire();
//        }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//
//        if (requestCode == REQUEST_WAKE_LOCK) {
//            // Received permission result for WAKE_LOCK permission.
//            Log.i(TAG, "Received response for WAKE_LOCK permission request.");
//
//            // Check if the only required permission has been granted
//            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // WAKE_LOCK permission has been granted
//            } else {
//                Log.i(TAG, "WAKE_LOCK permission was NOT granted.");
//            }
//        } else {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//    }
//
//    public interface MyState {
//        void doAction(MyContext myContext, View view);
//    }
//    public class MyContext {
//        private MyState myState;
//
//        public MyContext() {
//            myState = new UntouchState();
//        }
//
//        public void setState(MyState myState) {
//            this.myState = myState;
//        }
//
//        public MyState getState() {
//            return myState;
//        }
//    }
//    public class UntouchState implements MyState {
//        @Override
//        public void doAction(MyContext myContext, View view) {
//                System.out.println("------------------------Start state-----------------------");
//                mChronometer.setBase(SystemClock.elapsedRealtime());
//                mChronometer.start();
//                myContext.setState(this);
//        }
//    }
//    public class StartResumeState implements MyState {
//
//        @Override
//        public void doAction(MyContext myContext, View view) {
//            if (myContext.getState().getClass() == UntouchState.class) {
//                System.out.println("------------------------StartResumeState state-----------------------");
//                mChronometer.setBase(SystemClock.elapsedRealtime());
//                mChronometer.start();
//                restart.setEnabled(true);
//                myContext.setState(this);
//            }else{
//                System.out.println("------------------------StartResumeState state-----------------------");
//                long intervalOnPause = (SystemClock.elapsedRealtime() - mLastStopTime);
//                mChronometer.setBase(mChronometer.getBase() + intervalOnPause);
//                mChronometer.start();
//                myContext.setState(this);
//            }
//        }
//    }
//    public class StopedState implements MyState {
//
//        @Override
//        public void doAction(MyContext myContext, View view) {
//            System.out.println("------------------------Stoped state-----------------------");
//            mChronometer.stop();
//            mLastStopTime = SystemClock.elapsedRealtime();
//            myContext.setState(this);
//        }
//    }
}