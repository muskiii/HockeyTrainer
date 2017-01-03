package com.dev.fabianos.hockeyt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.RelativeLayout;

import com.dev.fabianos.hockeyt.states.MyContext;
import com.dev.fabianos.hockeyt.states.StartResumeState;
import com.dev.fabianos.hockeyt.states.StopedState;
import com.dev.fabianos.hockeyt.states.UntouchState;

import java.text.SimpleDateFormat;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements Chronometer.OnChronometerTickListener, ActivityCompat.OnRequestPermissionsResultCallback {
    //    LOGGING
    private static final String TAG = "gameActivity";

    RelativeLayout rl;
    Chronometer mChronometer;
    Button start, stop;
    private long mLastStopTime;
    MyContext myContext = new MyContext();
    UntouchState untouchState = new UntouchState();
    StartResumeState startResumeState = new StartResumeState();
    StopedState stopedState = new StopedState();
    Random random = new Random();
    int upperBound ;
    int lastRandom ;
    Handler h = new Handler();
    float secondsDelay;
    long millisecondsDelay;


    Runnable runnable = new Runnable() {
        public void run() {
            switch (generateRandom(lastRandom)) {
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
            h.postDelayed(this, millisecondsDelay);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

//        Get intent
        Intent intent = getIntent();
        secondsDelay = intent.getFloatExtra("com.dev.fabianos.hockeyt.SPINNER_VALUE", 1.0f);
        millisecondsDelay = (long) (secondsDelay * 1000);
        upperBound = intent.getIntExtra("com.dev.fabianos.hockeyt.SPINNER2_VALUE",0);

//        KEEP SCREEN ON
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

//        Views
        rl = (RelativeLayout) findViewById(R.id.activity_game);
        start = (Button) findViewById(R.id.btnStartResume);
        stop = (Button) findViewById(R.id.btnStop);

//        DYNAMICALYL ADDED CHRONOMETER
        mChronometer = new Chronometer(GameActivity.this);
        mChronometer.setOnChronometerTickListener(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                ((int) LayoutParams.WRAP_CONTENT, (int) LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.setMargins(0, 500, 0, 0);
        mChronometer.setLayoutParams(params);
        mChronometer.setTextSize((float) 50);

        stop.setEnabled(false);
        rl.addView(mChronometer);


//        START ON CLICK
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myContext.getState().getClass() == UntouchState.class) {
                    untouchState.doAction(myContext, mChronometer);
                    stop.setEnabled(true);
                    h.postDelayed(runnable, millisecondsDelay);
                } else if (myContext.getState().getClass() == StopedState.class) {
                    long intervalOnPause = (SystemClock.elapsedRealtime() - mLastStopTime);
                    mChronometer.setBase(mChronometer.getBase() + intervalOnPause);
                    startResumeState.doAction(myContext, mChronometer);

                    h.postDelayed(runnable, millisecondsDelay);

                }
            }
        });
//        STOP ON CLICK
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLastStopTime = SystemClock.elapsedRealtime();
                stopedState.doAction(myContext, mChronometer);
                h.removeCallbacks(runnable);
            }
        });
    }




    int generateRandom(int lastRandom) {
        int result;
        do {
            result = random.nextInt(upperBound);

        } while (result == lastRandom);
        this.lastRandom = result;
        return result;
    }
    //    ---TO DO WHILE TICKING---
    @Override
    public void onChronometerTick(Chronometer chronometer) {

//        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");


//        h.postDelayed(runnable , millisecondsDelay);
//        final Random r = new Random();
//        Date date = null;
//        try {
//            date = sdf.parse((String) chronometer.getText());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        long seconds = date.getTime() / 1000;
//
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}

