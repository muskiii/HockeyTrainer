package com.dev.fabianos.hockeyt.states;

/**
 * Created by jmfabiano on 28/12/2016.
 */

public class MyContext {
    private MyState myState;

    public MyContext() {
        myState = new UntouchState();
    }

    public void setState(MyState myState) {
        this.myState = myState;
    }

    public MyState getState() {
        return myState;
    }
}
