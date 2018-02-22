package com.example.j_spu.frenchpress;

import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ListView;

import java.util.List;

/**
 * Created by j_spu on 2/21/2018.
 */

public class Utilities {

    private static final String LOG_TAG = Utilities.class.getSimpleName();

    private Utilities(){}

    /**
     * adds a created routine to the users saved routines
     * iff the routine didn't already exist
     * @param newRoutine
     * @return if the routine was added or not
     */
    public static List<Routines> addRoutine(Routines newRoutine, List<Routines> userRoutines) {

        if (!validateRoutine(newRoutine, userRoutines)) {
            Log.e(LOG_TAG, "--ROUTINE ALREADY EXISTS--");
            return userRoutines;
        }
        userRoutines.add(newRoutine);
        return userRoutines;
    }

    /**
     * adds a created coffee recipe to the users saved coffees
     * iff the coffee didn't already exist
     * @param newCoffee
     * @return if the coffee was added or not
     */
    public static List<Coffees> addCoffee( Coffees newCoffee, List<Coffees> userCoffees ) {

        if (!validateCoffee(newCoffee, userCoffees)) {
            Log.e(LOG_TAG, "--COFFEE ALREADY EXISTS--");
            return userCoffees;
        }
        userCoffees.add(newCoffee);
        return userCoffees;
    }

    public static List<Routines> removeRoutine( Routines deleteRoutine, List<Routines> userRoutines ) {
        userRoutines.remove(deleteRoutine);
        return userRoutines;
    }

    public static List<Coffees> removeCoffee( Coffees deleteCoffee, List<Coffees> userCoffees ) {
        userCoffees.remove(deleteCoffee);
        return userCoffees;
    }

    /**
     * checks every routine the user already has saved to
     * validate that the new routine isn't a duplicate routine
     * @param newRoutine
     * @return if the new routine can be added
     */
    public static boolean validateRoutine( Routines newRoutine, List<Routines> userRoutines) {

        boolean isValid = true;

        //TODO ADD VALIDATION STEP

        // checks every routine for the user to validate
        // that the day and time combination is not already
        // reserved for another routine
        for ( Routines userRoutine : userRoutines) {
            for ( Days routineDays : userRoutine.getDays() )
                if (  newRoutine.getDays().contains(routineDays) &
                        userRoutine.getTime().equals(newRoutine.getTime())) {
                    return false;
                }
        }

        return isValid;
    }

    /**
     * checks every coffee the user already has saved to
     * validate that the new coffee isn't a duplicate coffee
     * @param newCoffee
     * @return if the new coffee can be added
     */
    private static boolean validateCoffee(Coffees newCoffee, List<Coffees> userCoffees) {

        boolean isValid = true;

        //TODO ADD VALIDATION STEP

        for ( Coffees userCoffee : userCoffees) {
            if ( userCoffee.getName().equals(newCoffee.getName()) ||
                    ( userCoffee.getTemp() == newCoffee.getTemp() &
                            userCoffee.getBrewTime().equals(newCoffee.getBrewTime()) &
                    userCoffee.getCupsOfWater() == newCoffee.getCupsOfWater() &
                    userCoffee.getScoopsOfCoffee() == newCoffee.getScoopsOfCoffee()) ) {
                return false;
            }
        }

        return isValid;
    }

    public static String[] convertCoffeesToStringArray(List<Coffees> coffeeList) {

        String[] coffeeStringArray = new String[coffeeList.size()];

        for (int i = 0; i < coffeeList.size(); i++) {
            coffeeStringArray[i] = coffeeList.get(i).getName();
        }
        return coffeeStringArray;
    }

    public static String formatTime(int hour, int min ) {
        StringBuilder formattedTime = new StringBuilder();

        if (hour > 12 && hour != 0) {
            hour -= 12;
            formattedTime.append(hour + ":" + min + " PM");
        } else if (hour == 12) {
            formattedTime.append(hour + ":" + min + " PM");
        } else if (hour == 0) {
            hour += 12;
            formattedTime.append(hour + ":" + min + " AM");
        } else {
            formattedTime.append(hour + ":" + min + " AM");
        }

        return formattedTime.toString();
    }
}
