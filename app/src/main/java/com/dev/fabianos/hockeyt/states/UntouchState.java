package com.dev.fabianos.hockeyt.states;

import android.os.SystemClock;
import android.widget.Chronometer;

/**
 * Created by jmfabiano on 28/12/2016.
 */

public class UntouchState implements MyState {
    @Override
    public void doAction(MyContext myContext, Chronometer mChronometer) {
        System.out.println("------------------------Start state-----------------------");
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();
        myContext.setState(new StartResumeState());
    }
}
