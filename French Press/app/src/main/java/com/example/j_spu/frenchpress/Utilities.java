package com.example.j_spu.frenchpress;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.ToggleButton;

import java.util.ArrayList;
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

        if (!validateNewRoutine(newRoutine, userRoutines)) {
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

        if (!validateNewCoffee(newCoffee, userCoffees)) {
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
    public static boolean validateNewRoutine( Routines newRoutine, List<Routines> userRoutines) {

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
     * checks every routine the user already has saved, and the original state
     * of the routine to validate that the updated routine isn't a duplicate routine
     * @param updatedRoutine
     * @param originalRoutine
     * @return if the new routine can be added
     */
    public static boolean validateEditRoutine( Routines updatedRoutine, Routines originalRoutine, List<Routines> userRoutines) {

        //TODO ADD VALIDATION STEP

        // checks every routine for the user to validate
        // that the day and time combination is not already
        // reserved for another routine
        for ( Routines userRoutine : userRoutines) {
            for ( Days routineDays : userRoutine.getDays() ) {
                if (!routinesEqual(updatedRoutine, originalRoutine) &
                        (updatedRoutine.getDays().contains(routineDays) &
                        userRoutine.getTime().equals(updatedRoutine.getTime()))) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * checks every coffee the user already has saved to
     * validate that the new coffee isn't a duplicate coffee
     * @param newCoffee
     * @return if the new coffee can be added
     */
    public static boolean validateNewCoffee(Coffees newCoffee, List<Coffees> userCoffees) {

        //TODO ADD VALIDATION STEP

        for ( Coffees userCoffee : userCoffees) {
            if (userCoffee.getName().equals(newCoffee.getName()) ||
                    ( coffeesEqual(userCoffee, newCoffee)) ) {
                Log.i(LOG_TAG, "I returned false\n\n");
                return false;
            }
        }

        Log.i(LOG_TAG, "I returned true\n\n");
        return true;
    }

    /**
     * checks every coffee the user already has saved to
     * validate that the new coffee isn't a duplicate coffee
     * @param newCoffee
     * @return if the new coffee can be added
     */
    public static boolean validateEditCoffee(Coffees newCoffee, List<Coffees> userCoffees, Coffees originalCoffee) {

        boolean isValid = true;

        //TODO ADD VALIDATION STEP

        for ( Coffees userCoffee : userCoffees) {
            if ( (userCoffee.getName().equals(newCoffee.getName()) & !newCoffee.getName().equals(originalCoffee.getName()))  ||
                    coffeesEqual(userCoffee, newCoffee) & !coffeesEqual(originalCoffee, newCoffee)) {
                return false;
            }

            Log.i(LOG_TAG, "{" + String.valueOf(userCoffee.getTemp()) + "}, {" + newCoffee.getTemp() + "}, {" + originalCoffee.getTemp() + "}"
            + "\n" + "{" + userCoffee.getBrewTime() + "}, {" + newCoffee.getBrewTime() + "}, {" + originalCoffee.getBrewTime() + "}"
            + "\n" + "{" + userCoffee.getScoopsOfCoffee() + "}, {" + newCoffee.getScoopsOfCoffee() + "}, {" + originalCoffee.getScoopsOfCoffee() + "}"
            + "\n" + "{" + userCoffee.getCupsOfWater() + "}, {" + newCoffee.getCupsOfWater() + "}, {" + originalCoffee.getCupsOfWater() + "}");
        }

        return isValid;
    }

    /**
     * checks if every attribute (except for the name)
     * for two coffees are the same
     * @param coffee1
     * @param coffee2
     * @return True if the two coffess have the same attributes, false otherwise
     */
    private static boolean coffeesEqual(Coffees coffee1, Coffees coffee2) {
        if (    coffee1.getTemp() == coffee2.getTemp() &
                coffee1.getBrewTime().equals(coffee2.getBrewTime()) &
                coffee1.getCupsOfWater() == coffee2.getCupsOfWater() &
                coffee1.getScoopsOfCoffee() == coffee2.getScoopsOfCoffee() ) {
            return true;
        }
        return false;
    }

    /**
     * checks if every attribute (except for the coffee)
     * for two routines are the same
     * @param routine1
     * @param routine2
     * @return True if the two coffess have the same attributes, false otherwise
     */
    private static boolean routinesEqual(Routines routine1, Routines routine2) {

        for (Days day : routine2.getDays() ) {
            if ( routine1.getDays().contains(day) &
                    routine1.getTime().equals(routine2.getTime())) {
                return true;
            }
        }
        return false;
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

        String minStr = String.valueOf(min);
        if (minStr.length() == 1) {
            minStr = "0" + minStr;
        }

        if (hour > 12 && hour != 0) {
            hour -= 12;
            formattedTime.append(hour + ":" + minStr + " PM");
        } else if (hour == 12) {
            formattedTime.append(hour + ":" + minStr + " PM");
        } else if (hour == 0) {
            hour += 12;
            formattedTime.append(hour + ":" + minStr + " AM");
        } else {
            formattedTime.append(hour + ":" + minStr + " AM");
        }

        return formattedTime.toString();
    }

    /**
     * This is passed a coffee that is getting deleted and checks if
     * it was being used by a routine. If a routine is found that used
     * the deleted coffee, a defaulted coffee configuration is stored
     * @param deletedCoffee is the deleted coffee
     * @param routinesList is the list of routines for the user
     */
    public static void defaultRoutinesUsedByCoffee(Coffees deletedCoffee, List<Routines> routinesList) {

        for (Routines routine : routinesList ) {
            if ( routine.getCoffee().equals(deletedCoffee) ) {
                routine.updateCoffee(new Coffees("Default", 90, "2:00", 1,1));
            }
        }
    }

    /**
     * this is passed either the edit routine or create routine view
     * and returns the List<Days> that the user selected
     * @param rootView
     * @return
     */
    public static List<Days> getSelectedDays(View rootView) {
        List<Days> newDays = new ArrayList<>();

        List<ToggleButton> dayButtons = new ArrayList<>();
        dayButtons.add((ToggleButton) rootView.findViewById(R.id.day_sunday));
        dayButtons.add((ToggleButton) rootView.findViewById(R.id.day_monday));
        dayButtons.add((ToggleButton) rootView.findViewById(R.id.day_tuesday));
        dayButtons.add((ToggleButton) rootView.findViewById(R.id.day_wednesday));
        dayButtons.add((ToggleButton) rootView.findViewById(R.id.day_thursday));
        dayButtons.add((ToggleButton) rootView.findViewById(R.id.day_friday));
        dayButtons.add((ToggleButton) rootView.findViewById(R.id.day_saturday));

        for ( int i = 0; i < dayButtons.size(); i++) {
            if (dayButtons.get(i).isChecked()) {
                switch (i) {
                    case 0:
                        newDays.add(Days.Sunday);
                        break;
                    case 1:
                        newDays.add(Days.Monday);
                        break;
                    case 2:
                        newDays.add(Days.Tuesday);
                        break;
                    case 3:
                        newDays.add(Days.Wednesday);
                        break;
                    case 4:
                        newDays.add(Days.Thursday);
                        break;
                    case 5:
                        newDays.add(Days.Friday);
                        break;
                    case 6:
                        newDays.add(Days.Saturday);
                        break;
                }
            }
        }

        return newDays;
    }

    /**
     * this is passed either the edit routine or create routine view
     * and the List<Days> that the routine has saved and checks the
     * appropriate buttons in the view
     * @param rootView
     * @param days
     * @return dayButtons
     */
    public static List<ToggleButton> setSelectedDays(View rootView, List<Days> days) {

        List<ToggleButton> dayButtons = new ArrayList<>();
        dayButtons.add((ToggleButton) rootView.findViewById(R.id.day_sunday));
        dayButtons.add((ToggleButton) rootView.findViewById(R.id.day_monday));
        dayButtons.add((ToggleButton) rootView.findViewById(R.id.day_tuesday));
        dayButtons.add((ToggleButton) rootView.findViewById(R.id.day_wednesday));
        dayButtons.add((ToggleButton) rootView.findViewById(R.id.day_thursday));
        dayButtons.add((ToggleButton) rootView.findViewById(R.id.day_friday));
        dayButtons.add((ToggleButton) rootView.findViewById(R.id.day_saturday));

        for ( Days day: days) {
                switch (day) {
                    case Sunday:
                        dayButtons.get(0).setChecked(true);
                        break;
                    case Monday:
                        dayButtons.get(1).setChecked(true);
                        break;
                    case Tuesday:
                        dayButtons.get(2).setChecked(true);
                        break;
                    case Wednesday:
                        dayButtons.get(3).setChecked(true);
                        break;
                    case Thursday:
                        dayButtons.get(4).setChecked(true);
                        break;
                    case Friday:
                        dayButtons.get(5).setChecked(true);
                        break;
                    case Saturday:
                        dayButtons.get(6).setChecked(true);
                        break;
                }
            }

        return dayButtons;
    }
}
