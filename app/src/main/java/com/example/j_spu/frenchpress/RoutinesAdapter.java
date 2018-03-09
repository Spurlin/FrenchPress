package com.example.j_spu.frenchpress;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.MenuPopupWindow;
import android.text.Layout;
import android.transition.Transition;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by j_spu on 2/21/2018.
 */

public class RoutinesAdapter extends ArrayAdapter<Routines> {

    private static final String LOG_TAG = RoutinesAdapter.class.getSimpleName();
    private ConstraintLayout mainView;
    private android.support.v4.app.FragmentManager fragmentManager;

    public RoutinesAdapter(Activity context, List<Routines> routines, View newMainView, android.support.v4.app.FragmentManager originalFragManager) {
        super(context, 0, routines);
        mainView = (ConstraintLayout) newMainView;
        fragmentManager = originalFragManager;
    }

    public View getView(int position, final View convertView, final ViewGroup parent) {

        // check if the existing view is being reused, otherwise inflate the view
        View routinesView = convertView;
        if(routinesView == null) {
            routinesView = LayoutInflater.from(getContext()).inflate(
                    R.layout.routine_item, parent, false
            );
        }

        // get the {@link routine} object located at this position in the list
        final Routines currentRoutine = getItem(position);

        // start a transaction that will be used any time the layout needs to be updated
        final android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();

        //TODO: CREATE VIEW FOR A ROUTINE AND FILL WITH INFORMATION
        // find the TextView in the routine_item.xml layout with the ID routineName
        TextView routineName = (TextView) routinesView.findViewById(R.id.routineName);
        routineName.setText(currentRoutine.getName());

        // find the TextView in the routine_item.xml layout with the ID time
        TextView routineTime = (TextView) routinesView.findViewById(R.id.time);
        routineTime.setText(currentRoutine.getTime());

        // find the TextView in the routine_item.xml layout with the ID coffeeName
        TextView routineCoffee = (TextView) routinesView.findViewById(R.id.coffeeName);
        routineCoffee.setText(currentRoutine.getCoffee().getName());

        final CardView currentCard = (CardView) routinesView.findViewById(R.id.routine_card);
        currentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                final ListView routine_list_layout = (ListView) parent.findViewById(R.id.routine_list);
                final View popupView = inflater.from(getContext()).inflate(R.layout.edit_routine, null);

                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                popupView.getForeground().setAlpha(0);

                final TextView mTitle = (TextView) popupView.findViewById(R.id.edit_header_txt);
                mTitle.setText("Edit Routine");

                final List<ToggleButton> dayButtons = Utilities.setSelectedDays(popupView, currentRoutine.getDays());

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

                if ( currentRoutine.getTime().contains("AM") ||
                        (currentRoutine.getTime().contains("PM") &
                                Integer.parseInt(currentRoutine.getTime()
                                        .substring(0, currentRoutine.getTime().indexOf(":"))) == 12)) {
                    timePicker.setHour(Integer.parseInt(currentRoutine.getTime()
                            .substring(0, currentRoutine.getTime().indexOf(":"))));
                } else {
                    timePicker.setHour((Integer.parseInt(currentRoutine.getTime()
                            .substring(0, currentRoutine.getTime().indexOf(":"))) + 12));
                }

                timePicker.setMinute(Integer.parseInt(currentRoutine.getTime()
                        .substring(currentRoutine.getTime().indexOf(":") + 1)
                        .replace(" AM", "")
                        .replace(" PM", "")));

                Log.e(LOG_TAG, "--ERROR: " + Math.floor(getContext().getResources().getDisplayMetrics().density));

                popupWindow.setOutsideTouchable(false);
                popupWindow.showAtLocation(routine_list_layout, Gravity.CENTER, 0, 0);

                mainView.getForeground().setAlpha(220);

                final Spinner coffee_spinner = (Spinner) popupView.findViewById(R.id.coffee_spinner);
                final ArrayAdapter<String> coffeeAdapter = new ArrayAdapter<String>(getContext(),
                        R.layout.spinner_item, Utilities.convertCoffeesToStringArray(MainActivity.mainUser.getCoffees()));
                coffeeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_items);

                if (!coffeeAdapter.isEmpty()) {
                    coffee_spinner.setAdapter(coffeeAdapter);
                    coffee_spinner.setEnabled(true);
                    coffee_spinner.setSelection(coffeeAdapter.getPosition(currentRoutine.getCoffee().getName()));
                } else { coffee_spinner.setEnabled(false); }


                // User clicks save
                TextView saveText = (TextView) popupView.findViewById(R.id.save_text);
                saveText.setText("Save");
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

                        if (coffee_spinner.isEnabled() != false) {
                            newRoutine.updateCoffee(MainActivity.mainUser.getCoffeeByName(coffee_spinner.getSelectedItem().toString()));
                        } else { newRoutine.updateCoffee(currentRoutine.getCoffee()); }

                        newRoutine.updateDays(updatedDays);
                        if (!updatedDays.isEmpty()) { newRoutine.generateName(); }
//
//                        // check if the edited routine is valid
                        if (!Utilities.validateEditRoutine(newRoutine, currentRoutine, MainActivity.mainUser.getRoutines())) {

                            Toast.makeText(getContext(), R.string.duplicate_Routine_Error, Toast.LENGTH_SHORT).show();
                            return;
                        } else if ( updatedDays.isEmpty() ){
                            Toast.makeText(getContext(), R.string.no_Days_Routine_Error, Toast.LENGTH_SHORT).show();
                            return;
                        }

                        currentRoutine.updateTime(newRoutine.getTime());
                        currentRoutine.updateCoffee(newRoutine.getCoffee());
                        currentRoutine.updateDays(newRoutine.getDays());
                        currentRoutine.generateName();

                        Toast.makeText(getContext(), "Routine Saved.", Toast.LENGTH_SHORT).show();
                        transaction.replace(R.id.container, new TabRoutines()).commit();
                        popupWindow.dismiss();
                        mainView.getForeground().setAlpha(0);

                    }
                });

                TextView deleteText = (TextView) popupView.findViewById(R.id.delete_text);
                deleteText.setText("Delete");
                deleteText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        final ListView coffee_list_view = (ListView) parent.findViewById(R.id.routine_list);
                        final View newPopupView = inflater.from(getContext()).inflate(R.layout.delete_confirmation, null);

                        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                        boolean focusable = true;
                        final PopupWindow newPopupWindow = new PopupWindow(newPopupView, width, height, focusable);
                        newPopupWindow.showAtLocation(coffee_list_view, Gravity.CENTER, 0, 0);

                        TextView deleteMessage = (TextView) newPopupView.findViewById(R.id.delete_message);
                        deleteMessage.setText(deleteMessage.getText().toString().replace("[item]", " routine"));

                        popupView.getForeground().setAlpha(220);

                        TextView deleteAction = (TextView) newPopupView.findViewById(R.id.delete_action);
                        deleteAction.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                                Utilities.removeRoutine(currentRoutine, MainActivity.mainUser.getRoutines());

                                transaction.replace(R.id.container, new TabRoutines()).commit();
                                newPopupWindow.dismiss();
                                popupWindow.dismiss();
                            }
                        });

                        TextView cancelAction = (TextView) newPopupView.findViewById(R.id.cancel_action);
                        cancelAction.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                newPopupWindow.dismiss();
                                popupView.getForeground().setAlpha(0);
                            }
                        });

                        // handles when the pop up window is closed via touch outside of window or
                        // via back button
                        newPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                popupView.getForeground().setAlpha(0);
                                newPopupWindow.dismiss();
                            }
                        });
                    }
                });

                // handles when the pop up window is closed via touch outside of window or
                // via back button
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        mainView.getForeground().setAlpha(0);
                        popupWindow.dismiss();
                    }
                });
            }
        });

        if (getCount() == position + 1) {
            // check if the existing view is being reused, otherwise inflate the view
            View endOfListView = convertView;
            if(endOfListView == null) {
                endOfListView = LayoutInflater.from(getContext()).inflate(
                        R.layout.end_of_list, parent, false
                );
            }
        }

        return routinesView;
    }

    // used to get the days that were selected from the edit_routine.xml layout
    private static List<Days> getSelectedDays(View rootView) {
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

}
