package com.example.j_spu.frenchpress;

import java.util.List;

/**
 * Created by j_spu on 2/7/2018.
 */

public class Users {

    private String name;
    private int deviceCount;
    private List<Routines> routines;
    private List<Coffees> coffees;

    public String getName() { return name; }

    public int getDeviceCount() { return deviceCount; }

    public List<Routines> getRoutines() { return routines; }

    public List<Coffees> getCoffees() { return coffees; }


    /**
     * adds a created routine to the users saved routines
     * iff the routine didn't already exist
     * @param newRoutine
     * @return if the routine was added or not
     */
    public boolean addRoutine( Routines newRoutine ) {

        if (validateRoutine(newRoutine)) {
            routines.add(newRoutine);
            return true;
        } else {
            return false;
        }
    }

    /**
     * adds a created coffee recipe to the users saved coffees
     * iff the coffee didn't already exist
     * @param newCoffee
     * @return if the coffee was added or not
     */
    public boolean addCoffee( Coffees newCoffee ) {

        if (validateCoffee(newCoffee)) {
            coffees.add(newCoffee);
            return true;
        } else {
            return false;
        }
    }

    public void removeRoutine( Routines deleteRoutine ) { routines.remove(deleteRoutine); }

    public void removeCoffee( Coffees deleteCoffee ) { coffees.remove(deleteCoffee); }

    /**
     * checks every routine the user already has saved to
     * validate that the new routine isn't a duplicate routine
     * @param newRoutine
     * @return if the new routine can be added
     */
    private boolean validateRoutine( Routines newRoutine ) {

        boolean isValid = true;

        //TODO ADD VALIDATION STEP

        // checks every routine for the user to validate
        // that the day and time combination is not already
        // reserved for another routine
        for ( Routines userRoutine : routines) {
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
    private boolean validateCoffee( Coffees newCoffee ) {

        boolean isValid = true;

        //TODO ADD VALIDATION STEP

        for ( Coffees userCoffee : coffees) {
            if ( userCoffee.getName().equals(newCoffee.getName()) ||
                    ( userCoffee.getTemp() == newCoffee.getTemp() &
                    userCoffee.getStrength() == newCoffee.getStrength() ) ) {
                return false;
            }
        }

        return isValid;
    }
}
