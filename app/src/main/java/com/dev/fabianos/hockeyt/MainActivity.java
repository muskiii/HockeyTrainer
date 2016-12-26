package com.dev.fabianos.hockeyt;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.RelativeLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends Activity implements Chronometer.OnChronometerTickListener{

    RelativeLayout rl;
    Chronometer mChronometer;
    Button start, stop, restart;
    private long mLastStopTime;
    private boolean running = false;
    private boolean stoped = true;

    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rl = (RelativeLayout) findViewById(R.id.rl);

        start = (Button) findViewById(R.id.btnStartResume);
        restart = (Button) findViewById(R.id.btnRestart);

        mChronometer = new Chronometer (MainActivity.this);
        mChronometer.setOnChronometerTickListener(this);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                ((int) LayoutParams.WRAP_CONTENT, (int) LayoutParams.WRAP_CONTENT);

        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.setMargins(0, 980, 0, 0);
        mChronometer.setLayoutParams(params);
        mChronometer.setTextSize((float) 50);

        rl.addView(mChronometer);
        restart.setEnabled(false);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if ( running == false ) {
                    mChronometer.setBase(SystemClock.elapsedRealtime());
                    mChronometer.start();
                    running = true;
                    stoped = false;
                    restart.setEnabled(true);
                }
                else if( running == true && stoped == true){
                    long intervalOnPause = (SystemClock.elapsedRealtime() - mLastStopTime);
                    mChronometer.setBase( mChronometer.getBase() + intervalOnPause );
                    mChronometer.start();
                    stoped = false;
                }else if (running == true && stoped == false){
                    mChronometer.stop();
                    mLastStopTime = SystemClock.elapsedRealtime();
                    stoped = true;
                }
            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mChronometer.setBase(SystemClock.elapsedRealtime());
                mLastStopTime=0;
                mChronometer.start();
                running = true;
                stoped = false;
            }
        });
    }

    @Override
    public void onChronometerTick(Chronometer chronometer) {
//        if ("00:10".equals(chronometer.getText())){
//            rl.setBackgroundColor(50);
        Date date = null;
        try {
            date = sdf.parse((String) chronometer.getText());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long seconds = date.getTime() / 1000;
        System.out.println(seconds);
//        }
    }
}