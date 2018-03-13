package com.example.j_spu.frenchpress;

import java.util.List;

/**
 * Created by j_spu on 2/7/2018.
 */

public class Users {

    private String mName;
    private int deviceCount;
    private List<Routines> mRoutines;
    private List<Coffees> mCoffees;
    private List<Settings> mSettings;

    public Users(String newName, List<Settings> settings) {
        mName = newName;
        mSettings = settings;
    }

    public String getName() { return mName; }

    public int getDeviceCount() { return deviceCount; }

    public List<Routines> getRoutines() { return mRoutines; }

    public List<Coffees> getCoffees() { return mCoffees; }

    public void updateRoutines(List<Routines> updatedRoutines) {
        mRoutines = updatedRoutines;
    }

    public void updateCoffees(List<Coffees> updatedCoffees) {
        mCoffees = updatedCoffees;
    }

    public List<Settings> getSettings() { return mSettings; }

    public void replaceRoutine(Routines updatedRoutine, Routines oldRoutine) {
        mRoutines.remove(oldRoutine);
        mRoutines.add(updatedRoutine);
    }

    public Coffees getCoffeeByName(String name) {
        for(Coffees targetCoffee : mCoffees) {
            if (targetCoffee.getName().equals(name)) {
                return targetCoffee;
            }
        }
        return null;
    }

}
