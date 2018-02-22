package com.example.j_spu.frenchpress;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements Serializable {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private TabLayout tabLayout;
    private PopupWindow popupWindow;
    private Context mContext;
    LinearLayout mLinearLayout;
    private TextView mTextView;
    public static Users mainUser;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get intent
        Intent intent = getIntent();

        ConstraintLayout mainView = (ConstraintLayout) findViewById(R.id.main_layout);
        mainView.getForeground().setAlpha(0);

        mContext = getApplicationContext();

        // HARD CODED SETTINGS TO TEST LAYOUT
        List<Settings> testSettings = new ArrayList<>();
        testSettings.add(new Settings("Setting 1", true));
        testSettings.add(new Settings("Setting 2", false));
        testSettings.add(new Settings("Setting 3", true));
        testSettings.add(new Settings("Setting 4", false));
        testSettings.add(new Settings("Setting 5", true));
        testSettings.add(new Settings("Setting 6", false));
        testSettings.add(new Settings("Setting 7", true));
        testSettings.add(new Settings("Setting 8", false));
        testSettings.add(new Settings("Setting 9", true));
        testSettings.add(new Settings("Setting 10", false));

        // HARD CODED USER TO TEST LAYOUT AND PROGRAM LOGIC
        mainUser = new Users("John", testSettings);

        // HARD CODED DAYS TO TEST LOGIC OF ROUTINES
        // MONDAY, WEDNESDAY, FRIDAY
        List<Days> arrayDays = new ArrayList<>();
        arrayDays.add(Days.Monday);
        arrayDays.add(Days.Wednesday);
        arrayDays.add(Days.Friday);

        // TUESDAY, THURSDAY
        List<Days> arrayDays2 = new ArrayList<>();
        arrayDays2.add(Days.Tuesday);
        arrayDays2.add(Days.Thursday);

        // MONDAY
        List<Days> monday = new ArrayList<>();
        monday.add(Days.Monday);

        // HARD CODED COFFEE RECIPES TO TEST LAYOUT AND LOGIC
        // OF ADDING DUPLICATE COFFEES
        List<Coffees> myCoffees = new ArrayList<>();
        myCoffees = Utilities.addCoffee(new Coffees("Morning Joe", 90, Strength.Strong, "2",2, 1), myCoffees);
        mainUser.updateCoffees(myCoffees);
        myCoffees = Utilities.addCoffee(new Coffees("Morning Joe", 90, Strength.Strong, "2", 2, 1), myCoffees);
        mainUser.updateCoffees(myCoffees);
        myCoffees = Utilities.addCoffee(new Coffees("The Best", 100, Strength.Strong, "4", 1, 1), myCoffees);
        mainUser.updateCoffees(myCoffees);
        myCoffees = Utilities.addCoffee(new Coffees("Starbucks Copy-Cat", 110, Strength.Strong, "4", 2, 2), myCoffees);
        mainUser.updateCoffees(myCoffees);
        myCoffees = Utilities.addCoffee(new Coffees("I am", 100, Strength.Strong, "1", 2, 2), myCoffees);
        mainUser.updateCoffees(myCoffees);
        myCoffees = Utilities.addCoffee(new Coffees("running out", 80, Strength.Strong, "3", 3, 3), myCoffees);
        mainUser.updateCoffees(myCoffees);
        myCoffees = Utilities.addCoffee(new Coffees("Of Names", 90, Strength.Strong, "2", 1, 2), myCoffees);
        mainUser.updateCoffees(myCoffees);

        // HARD CODDED ROUTINES TO TEST LAYOUT AND LOGIC
        // OF ADDING ROUTINES THAT WOULD CONFLICT EACH OTHER
        // (i.e. A ROUTINE WILL GO OFF AT THE SAME TIME ON THE
        // SAME DAY)
        List<Routines> myRoutines = new ArrayList<>();
        myRoutines = Utilities.addRoutine(new Routines(arrayDays, "7:30 AM", myCoffees.get(0)), myRoutines);
        mainUser.updateRoutines(myRoutines);

        // DUPLICATE
        myRoutines = Utilities.addRoutine(new Routines(arrayDays, "7:30 AM", myCoffees.get(0)), myRoutines); // CAUGHT: error check
        mainUser.updateRoutines(myRoutines);

        myRoutines = Utilities.addRoutine(new Routines(arrayDays, "9:30 AM", myCoffees.get(1)), myRoutines);
        mainUser.updateRoutines(myRoutines);

        myRoutines = Utilities.addRoutine(new Routines(arrayDays2, "8:30 AM", myCoffees.get(1)), myRoutines);
        mainUser.updateRoutines(myRoutines);

        // DUPLICATE
        myRoutines = Utilities.addRoutine(new Routines(monday, "7:30 AM", myCoffees.get(1)), myRoutines); // CAUGHT: error check
        mainUser.updateRoutines(myRoutines);

        myRoutines = Utilities.addRoutine(new Routines(monday, "8:30 AM", myCoffees.get(2)), myRoutines);
        mainUser.updateRoutines(myRoutines);

        // DUPLICATE
        myRoutines = Utilities.addRoutine(new Routines(monday, "9:30 AM", myCoffees.get(1)), myRoutines); // CAUGHT: error check
        mainUser.updateRoutines(myRoutines);

        myRoutines = Utilities.addRoutine(new Routines(monday, "12:30 PM", myCoffees.get(0)), myRoutines);
        mainUser.updateRoutines(myRoutines);

        myRoutines = Utilities.addRoutine(new Routines(monday, "5:30 PM", myCoffees.get(0)), myRoutines);
        mainUser.updateRoutines(myRoutines);
        // END OF HARD CODED TEST CASES

        // set up the bottom navigation
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // start a transaction with the home tab first
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        transaction.replace(R.id.container, new TabHome()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);


            switch (item.getItemId()) {
                case R.id.navigation_Home:
                    transaction.replace(R.id.container, new TabHome()).commit();
                    return true;
                case R.id.navigation_Routines:
                    transaction.replace(R.id.container, new TabRoutines()).commit();
                    return true;
                case R.id.navigation_Coffees:
                    transaction.replace(R.id.container, new TabCoffees()).commit();
                    return true;
                case R.id.navigation_Settings:
                    transaction.replace(R.id.container, new TabSettings()).commit();
                    return true;
            }
            return false;
        }
    };

    // override the back button action to not do anything
    @Override
    public void onBackPressed() {
    }

}

