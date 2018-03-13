package com.example.j_spu.frenchpress;

/**
 * Created by j_spu on 2/7/2018.
 */

public class Coffees {

    private String mName;
    private int mTemp;
    private String mBrewTime;
    private int mCupsOfWater;
    private int mScoopsOfCoffee;

    Coffees() {}

    Coffees( String newName, int newTemp, String newTime, int newCupsOfWater, int newScoopsOfCoffee ) {
        mName = newName;
        mTemp = newTemp;
        mBrewTime = newTime;
        mCupsOfWater = newCupsOfWater;
        mScoopsOfCoffee = newScoopsOfCoffee;
    }

    public String getName() { return mName; }

    public void setName(String name) { mName = name; }

    public int getTemp() { return mTemp; }

    public void setTemp(int temp) { mTemp = temp; }

    public String getBrewTime() { return mBrewTime; }

    public void setBrewTime(String brewTime) { mBrewTime = brewTime; }

    public int getCupsOfWater() { return mCupsOfWater; }

    public void setCupsOfWater(int cupsOfWater) { mCupsOfWater = cupsOfWater; }

    public int getScoopsOfCoffee() { return mScoopsOfCoffee; }

    public void setScoopsOfCoffee(int scoopsOfCoffee) { mScoopsOfCoffee = scoopsOfCoffee; }
}
