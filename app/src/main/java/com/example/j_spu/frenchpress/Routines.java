package com.example.j_spu.frenchpress;

import android.provider.AlarmClock;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by j_spu on 2/7/2018.
 */

public class Routines {

    private final String LOG_TAG = Routines.class.getSimpleName();
    private String mName;
    private List<Days> mDays;
    //TODO TIME VARIABLE
    private String mTime;
    private Coffees mCoffee;
    private boolean state;

    Routines(){ state = true; }

    Routines( List<Days> newDays, String newTime, Coffees newCoffee) {
        mDays = newDays;
        mTime = newTime;
        mCoffee = newCoffee;
        generateName();
        state = true;
    }

    public List<Days> getDays() { return mDays; }

    public String getTime() { return mTime; }

    public Coffees getCoffee() { return mCoffee; }

    public String getName() { return mName; }

    public boolean getState() { return state; }

    public void generateName() {

        List<Days> weekDays = new ArrayList<>();
        weekDays.add(Days.Monday);
        weekDays.add(Days.Tuesday);
        weekDays.add(Days.Wednesday);
        weekDays.add(Days.Thursday);
        weekDays.add(Days.Friday);

        List<Days> weekendDays = new ArrayList<>();
        weekendDays.add(Days.Saturday);
        weekendDays.add(Days.Sunday);

        if (mDays.size() == 7) {
            mName = "Every Day";
            return;
        } else if (!(mDays.contains(Days.Sunday)) &
                    !(mDays.contains(Days.Saturday)) &
                        mDays.containsAll(weekDays)) {
            mName = "Week Days";
            return;
        }

        StringBuilder buildName = new StringBuilder();
        String tempName;

        for ( Days currentDay : mDays ) {
            buildName.append(currentDay.toString() + ", ");
        }
        tempName = buildName.toString();
        tempName = tempName.substring(0, tempName.lastIndexOf(','));

        mName = tempName;
    }

    public void updateTime(String newTime) {
        mTime = newTime;
    }

    public void updateDays(List<Days> newDays) {
        mDays = newDays;
    }

    public void updateCoffee(Coffees newCoffee) {
        mCoffee = newCoffee;
    }

    public void updateState() { state = !state; }
}
