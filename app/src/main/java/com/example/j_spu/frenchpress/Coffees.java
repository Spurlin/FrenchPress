package com.example.j_spu.frenchpress;

/**
 * Created by j_spu on 2/7/2018.
 */

public class Coffees {

    private String mName;
    private int mTemp;
    private Enum<Strength> mStrengthEnum;
    private String mBrewTime;
    private int mCupsOfWater;
    private int mScoopsOfCoffee;

    Coffees( String newName, int newTemp, Enum<Strength> newStength, String newTime, int newCupsOfWater, int newScoopsOfCoffee ) {
        mName = newName;
        mTemp = newTemp;
        mStrengthEnum = newStength;
        mBrewTime = newTime;
        mCupsOfWater = newCupsOfWater;
        mScoopsOfCoffee = newScoopsOfCoffee;
    }

    public String getName() { return mName; }

    public int getTemp() { return mTemp; }

    public Enum<Strength> getStrength() { return mStrengthEnum; }

    public String getBrewTime() { return mBrewTime; }

    public int getCupsOfWater() { return mCupsOfWater; }

    public int getScoopsOfCoffee() { return mScoopsOfCoffee; }
}
