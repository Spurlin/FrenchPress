package com.example.j_spu.frenchpress;

/**
 * Created by j_spu on 2/7/2018.
 */

public class Coffees {

    private String name;
    private int temp;
    private Enum<Strength> mStrengthEnum;
//    private Enum<GrindSize> mGrindSizeEnum;

    Coffees( String newName, int newTemp, Enum<Strength> newStength,
             Enum<GrindSize> newGrindSize ) {

        name = newName;
        temp = newTemp;
        mStrengthEnum = newStength;
//        mGrindSizeEnum = newGrindSize;
    }

    public String getName() { return name; }

    public int getTemp() { return temp; }

    public Enum<Strength> getStrength() { return mStrengthEnum; }

//    public Enum<GrindSize> getGrindSize() { return mGrindSizeEnum; }

}
