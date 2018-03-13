package com.example.j_spu.frenchpress;

import android.content.Context;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by j_spu on 2/7/2018.
 */

public class TabRoutines extends Fragment implements Serializable {

    private static final String LOG_TAG = TabRoutines.class.getSimpleName();
    private RoutinesAdapter mAdapter;
    private LinearLayout mEmptyStateLinearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.tab_routines, container, false);
        super.onCreate(savedInstanceState);

        // Find a reference to the {@link ListView} in the layout
        ListView routinesListView = (ListView) rootView.findViewById(R.id.routine_list);
        FrameLayout routinesMainLayout = (FrameLayout) rootView.findViewById(R.id.routines_mainL);
        routinesMainLayout.getForeground().setAlpha(0);

        final ConstraintLayout view = container.getRootView().findViewById(R.id.main_layout);

        mAdapter = new RoutinesAdapter(getActivity(), MainActivity.mainUser.getRoutines(), view, getFragmentManager());

        mEmptyStateLinearLayout = (LinearLayout) rootView.findViewById(R.id.empty_view_layout);

        routinesListView.setAdapter(mAdapter);
        routinesListView.setEmptyView(mEmptyStateLinearLayout);

        View endOfListView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.end_of_list, null, false);

        // displays the empty view if there wasn't any routines
        if (!mAdapter.isEmpty()) {
            mEmptyStateLinearLayout.setVisibility(View.GONE);
            routinesListView.setVisibility(View.VISIBLE);
            routinesListView.addFooterView(endOfListView);

            TextView counterText = (TextView) endOfListView.findViewById(R.id.counter_text);
            counterText.setText(counterText.getText().toString()
                    .replace("[number]", String.valueOf(mAdapter.getCount()))
                    .replace("[item]", (mAdapter.getCount() > 1 ? "Routines" : "Routine")));
        } else {
            mEmptyStateLinearLayout.setVisibility(View.VISIBLE);
            routinesListView.setVisibility(View.GONE);
        }

        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();

        // start a transaction that will be used any time the layout needs to be updated
        final android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();

        FloatingActionButton mAddRoutine = (FloatingActionButton) rootView.findViewById(R.id.add_routine_FAB);
        mAddRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MainActivity.mainUser.getCoffees().isEmpty()) {
                    Toast.makeText(getContext(), R.string.no_Coffees_Routine_Error, Toast.LENGTH_SHORT).show();
                    return;
                }

                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                final ListView routine_list_layout = (ListView) rootView.findViewById(R.id.routine_list);
                final View popupView = inflater.from(getContext()).inflate(R.layout.edit_routine, null);

                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                popupView.getForeground().setAlpha(0);

                final TextView mTitle = (TextView) popupView.findViewById(R.id.edit_header_txt);
                mTitle.setText("Create Routine");

                if (Build.VERSION.SDK_INT >= 21) {
                    popupWindow.setElevation(5.0f);
                }

                TimePicker timePicker;
                TimePicker timePickerOther;

                // if the screen can't fit the analog clock
                if ( (getContext().getResources().getDisplayMetrics().density <=
                        3) ) {
                    timePicker = (TimePicker) popupView.findViewById(R.id.routine_timePickerSpinner);
                    timePickerOther = (TimePicker) popupView.findViewById(R.id.routine_timePicker);
                    timePicker.setVisibility(View.VISIBLE);
                    timePickerOther.setVisibility(View.GONE);
                } else {
                    timePicker = (TimePicker) popupView.findViewById(R.id.routine_timePicker);
                    timePickerOther = (TimePicker) popupView.findViewById(R.id.routine_timePickerSpinner);
                    timePicker.setVisibility(View.VISIBLE);
                    timePickerOther.setVisibility(View.GONE);
                }

                final TimePicker mTimePicker = timePicker;

                Log.e(LOG_TAG, "--ERROR: " + Math.floor(getContext().getResources().getDisplayMetrics().density));

                popupWindow.setOutsideTouchable(false);
                popupWindow.showAtLocation(routine_list_layout, Gravity.CENTER, 0, 0);

                view.getForeground().setAlpha(220);

                final Spinner coffee_spinner = (Spinner) popupView.findViewById(R.id.coffee_spinner);
                final ArrayAdapter<String> coffeeAdapter = new ArrayAdapter<String>(getContext(),
                        R.layout.spinner_item, Utilities.convertCoffeesToStringArray(MainActivity.mainUser.getCoffees()));
                coffeeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_items);

                if (!coffeeAdapter.isEmpty()) {
                    coffee_spinner.setAdapter(coffeeAdapter);
                    coffee_spinner.setEnabled(true);
                } else { coffee_spinner.setEnabled(false); }


                // User clicks save
                TextView saveText = (TextView) popupView.findViewById(R.id.save_text);
                saveText.setText("Create");
                saveText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO: FIX TIME PICKER
                        Routines newRoutine = new Routines();

                        int hour = mTimePicker.getHour();
                        int min = mTimePicker.getMinute();
//
                        String formatedTime = Utilities.formatTime(hour, min);
//
                        List<Days> updatedDays = Utilities.getSelectedDays(popupView);
//
//                        // update the routine
                        newRoutine.updateTime(formatedTime);
                        newRoutine.updateCoffee(MainActivity.mainUser.getCoffeeByName(coffee_spinner.getSelectedItem().toString()));
                        newRoutine.updateDays(updatedDays);
                        if (!updatedDays.isEmpty()) { newRoutine.generateName(); }
//
//                        // check if the created routine is valid
                        if (!Utilities.validateNewRoutine(newRoutine, MainActivity.mainUser.getRoutines())) {

                            Toast.makeText(getContext(), R.string.duplicate_Routine_Error, Toast.LENGTH_SHORT).show();
                            return;
                        } else if ( updatedDays.isEmpty() ){
                            Toast.makeText(getContext(), R.string.no_Days_Routine_Error, Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Utilities.addRoutine(newRoutine, MainActivity.mainUser.getRoutines());
                        Toast.makeText(getContext(), "Routine created.", Toast.LENGTH_SHORT).show();
                        transaction.replace(R.id.container, new TabRoutines()).commit();
                        popupWindow.dismiss();
                        view.getForeground().setAlpha(0);

                    }
                });

                TextView deleteText = (TextView) popupView.findViewById(R.id.delete_text);
                deleteText.setText("Cancel");
                deleteText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        view.getForeground().setAlpha(0);
                    }
                });

                // handles when the pop up window is closed via touch outside of window or
                // via back button
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        view.getForeground().setAlpha(0);
                        popupWindow.dismiss();
                    }
                });
            }
        });

//        Button quick_brew_btn = getActivity().findViewById(R.id.btn_brew);
//        quick_brew_btn.setVisibility(View.GONE);

        return rootView;
    }
}
