package com.example.j_spu.frenchpress;

/**
 * Created by j_spu on 2/21/2018.
 */

public class Settings {

    private String mName;
    private boolean mState;

    public Settings(String settingName, Boolean settingState) {
        mName = settingName;
        mState = settingState;
    }

    public String getName() { return mName; }

    public boolean getState() { return mState; }

    public void toggleState() { mState = !mState; }
}
