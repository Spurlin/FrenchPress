package com.example.j_spu.frenchpress;

import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by j_spu on 2/7/2018.
 */

public class TabRoutines extends Fragment implements Serializable {

    private static final String LOG_TAG = TabRoutines.class.getSimpleName();
    private RoutinesAdapter mAdapter;

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

        mAdapter = new RoutinesAdapter(getActivity(), MainActivity.mainUser.getRoutines(), view);

        routinesListView.setAdapter(mAdapter);

        FloatingActionButton mAddRoutine = (FloatingActionButton) rootView.findViewById(R.id.add_routine_FAB);
        mAddRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                final ListView routine_list_layout = (ListView) rootView.findViewById(R.id.routine_list);
                final View popupView = inflater.from(getContext()).inflate(R.layout.edit_routine, null);

                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                final TimePicker timePicker = (TimePicker) popupView.findViewById(R.id.routine_timePicker);
                final TimePicker timePickerSmallScreen = (TimePicker) popupView.findViewById(R.id.routine_timePickerSpinner);
                final TextView mTitle = (TextView) popupView.findViewById(R.id.edit_header_txt);
                mTitle.setText("Create Routine");

                if (Build.VERSION.SDK_INT >= 21) {
                    popupWindow.setElevation(5.0f);
                }

                if ( (getContext().getResources().getDisplayMetrics().density <=
                        3) ) {
                    timePicker.setVisibility(View.GONE);
                    timePickerSmallScreen.setVisibility(View.VISIBLE);
                }

                Log.e(LOG_TAG, "--ERROR: " + Math.floor(getContext().getResources().getDisplayMetrics().density));

                popupWindow.setOutsideTouchable(false);
                popupWindow.showAtLocation(routine_list_layout, Gravity.CENTER, 0, 0);

                view.getForeground().setAlpha(220);

                final Spinner coffee_spinner = (Spinner) popupView.findViewById(R.id.coffee_spinner);
                final ArrayAdapter<String> coffeeAdapter = new ArrayAdapter<String>(getContext(),
                        R.layout.spinner_item, Utilities.convertCoffeesToStringArray(MainActivity.mainUser.getCoffees()));
                coffeeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_items);

                coffee_spinner.setAdapter(coffeeAdapter);


                // User clicks save
                TextView saveText = (TextView) popupView.findViewById(R.id.save_text);
                saveText.setText("Create");
                saveText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO: FIX TIME PICKER
//                        Routines updatedRoutine = currentRoutine;
//                        int hour = timePicker.getHour();
//                        int min = timePicker.getMinute();
//
//                        String formatedTime = Utilities.formatTime(hour, min);
//
//                        coffee_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                            @Override
//                            public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
//                                updatedCoffee = MainActivity.mainUser.getCoffeeByName(adapter.getItemAtPosition(position).toString());
//                            }
//
//                            @Override
//                            public void onNothingSelected(AdapterView<?> parent) {
//                            }
//                        });
//
//                        List<Days> updatedDays = getSelectedDays(popupView);
//
//                        // update the routine
//                        updatedRoutine.updateTime(formatedTime);
//                        updatedRoutine.updateCoffee(updatedCoffee);
//                        updatedRoutine.updateDays(updatedDays);
//                        updatedRoutine.getName();
//
//                        // check if the updated routine is valid
//                        if (Utilities.validateRoutine(updatedRoutine, MainActivity.mainUser.getRoutines())) {
//                            MainActivity.mainUser.replaceRoutine(updatedRoutine, currentRoutine);
//                            popupWindow.dismiss();
//                            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
//                            mainView.getForeground().setAlpha(0);
//                        } else { Toast.makeText(getContext(), "Routine already exists.", Toast.LENGTH_SHORT).show(); }
                        popupWindow.dismiss();
                        Toast.makeText(getContext(), "Created", Toast.LENGTH_SHORT).show();
                        view.getForeground().setAlpha(0);
                    }
                });

                TextView deleteText = (TextView) popupView.findViewById(R.id.delete_text);
                deleteText.setText("Cancel");
                deleteText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
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
