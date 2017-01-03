package com.dev.fabianos.hockeyt.states;

import android.widget.Chronometer;

/**
 * Created by jmfabiano on 28/12/2016.
 */

public interface MyState {
    void doAction(MyContext myContext, Chronometer mChronometer);
}