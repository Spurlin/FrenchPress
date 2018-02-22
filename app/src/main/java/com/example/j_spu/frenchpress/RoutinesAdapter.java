package com.example.j_spu.frenchpress;

import android.app.Activity;
import android.app.TimePickerDialog;
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
    private Coffees updatedCoffee;

    public RoutinesAdapter(Activity context, List<Routines> routines, View newMainView) {
        super(context, 0, routines);
        mainView = (ConstraintLayout) newMainView;
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

        CardView currentCard = (CardView) routinesView.findViewById(R.id.routine_card);
        currentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "You clicked the " + currentRoutine.getName() +
                " at " + currentRoutine.getTime() + " routine.", Toast.LENGTH_SHORT).show();

                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                final ListView routine_list_layout = (ListView) parent.findViewById(R.id.routine_list);
                final View popupView = inflater.from(getContext()).inflate(R.layout.edit_routine, null);

                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                if (Build.VERSION.SDK_INT >= 21) {
                    popupWindow.setElevation(5.0f);
                }

                popupWindow.setOutsideTouchable(false);
                popupWindow.showAtLocation(routine_list_layout, Gravity.CENTER, 0, 0);

                mainView.getForeground().setAlpha(220);

                TextView headerText = (TextView) popupView.findViewById(R.id.edit_header_txt);
                headerText.setText("Edit Routine");

                final TimePicker timePicker = (TimePicker) popupView.findViewById(R.id.routine_timePicker);

                final Spinner coffee_spinner = (Spinner) popupView.findViewById(R.id.coffee_spinner);
                final ArrayAdapter<String> coffeeAdapter = new ArrayAdapter<String>(getContext(),
                        R.layout.spinner_item, Utilities.convertCoffeesToStringArray(MainActivity.mainUser.getCoffees()));
                coffeeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_items);

                coffee_spinner.setAdapter(coffeeAdapter);


                // User clicks save
                TextView saveText = (TextView) popupView.findViewById(R.id.save_text);
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
                        Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                        mainView.getForeground().setAlpha(0);
                    }
                });

                TextView deleteText = (TextView) popupView.findViewById(R.id.delete_text);
                deleteText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                        mainView.getForeground().setAlpha(0);
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

//                TextView
            }
        });

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
