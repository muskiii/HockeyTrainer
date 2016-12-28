package com.dev.fabianos.hockeyt;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.RelativeLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements Chronometer.OnChronometerTickListener, ActivityCompat.OnRequestPermissionsResultCallback {

    public final int WAKE_LOCK = 0;
    protected PowerManager.WakeLock mWakeLock;
    RelativeLayout rl;
    Chronometer mChronometer;
    Button start, restart;
    SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
    private long mLastStopTime;
    private boolean running = false;
    private boolean stoped = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WAKE_LOCK}, WAKE_LOCK);
        } else {
            //TODO
            final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
            this.mWakeLock.acquire();
        }





        rl = (RelativeLayout) findViewById(R.id.rl);

        start = (Button) findViewById(R.id.btnStartResume);
        restart = (Button) findViewById(R.id.btnRestart);

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

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (!running) {
                    mChronometer.setBase(SystemClock.elapsedRealtime());
                    mChronometer.start();
                    running = true;
                    stoped = false;
                    restart.setEnabled(true);
                } else if (running && stoped) {
                    long intervalOnPause = (SystemClock.elapsedRealtime() - mLastStopTime);
                    mChronometer.setBase(mChronometer.getBase() + intervalOnPause);
                    mChronometer.start();
                    stoped = false;
                } else if (running && !stoped) {
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
                mLastStopTime = 0;
                mChronometer.start();
                running = true;
                stoped = false;
            }
        });
    }

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
                    rl.setBackgroundColor(0xFF000000);
                    break;
            }
        }
    }
    @Override
    public void onDestroy() {
        this.mWakeLock.release();
        super.onDestroy();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case WAKE_LOCK:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //TODO
                    final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
                    this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
                    this.mWakeLock.acquire();
                }
                break;
            default:
                break;
        }
    }
}