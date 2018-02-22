package com.example.j_spu.frenchpress;

import android.provider.AlarmClock;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by j_spu on 2/7/2018.
 */

public class Routines {

    private String mName;
    private List<Days> mDays;
    //TODO TIME VARIABLE
    private String mTime;
    private Coffees mCoffee;

    Routines( List<Days> newDays, String newTime, Coffees newCoffee) {
        mDays = newDays;
        mTime = newTime;
        mCoffee = newCoffee;
        mName = createName();
    }

    public List<Days> getDays() { return mDays; }

    public String getTime() { return mTime; }

    public Coffees getCoffee() { return mCoffee; }

    public String getName() { return mName; }

    private String createName() {
        StringBuilder buildName = new StringBuilder();

        for ( Days currentDay : mDays ) {
            buildName.append(currentDay.toString() + ", ");
        }
        mName = buildName.toString();
        mName = mName.substring(0, mName.lastIndexOf(','));
        return mName;
    }

    public void updateTime(String newTime) {
        mTime = newTime;
    }

    public void updateDays(List<Days> newDays) {
        mDays = newDays;
    }

    public void updateCoffee(Coffees newCofee) {
        mCoffee = newCofee;
    }
}
