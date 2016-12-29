package com.dev.fabianos.hockeyt;

import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;

/**
 * Created by jmfabiano on 28/12/2016.
 */

public class StopedState implements MyState {
    @Override
    public void doAction(MyContext myContext, Chronometer mChronometer) {
        System.out.println("------------------------Stoped state-----------------------");
        mChronometer.stop();
        myContext.setState(this);
    }
}
