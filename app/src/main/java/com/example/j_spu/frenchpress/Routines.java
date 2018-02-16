package com.example.j_spu.frenchpress;

import java.util.ArrayList;

/**
 * Created by j_spu on 2/7/2018.
 */

public class Routines {

    private ArrayList<Days> mDays;
    //TODO TIME VARIABLE
    String mTime;
    private Coffees mCoffee;

    Routines( ArrayList<Days> newDays, String newTime, Coffees newCoffee) {
        mDays = newDays;
        mTime = newTime;
        mCoffee = newCoffee;
    }

    public ArrayList<Days> getDays() { return mDays; }

    public String getTime() { return mTime; }

    public Coffees getCoffee() { return mCoffee; }
}
