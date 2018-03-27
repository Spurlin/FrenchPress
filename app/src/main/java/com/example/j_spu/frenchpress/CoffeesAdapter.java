package com.example.j_spu.frenchpress;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 *  Created by j_spu on 2/21/2018.
 *
 *  Following copyright is for use of SwipeRevealLayout:
 *
 *  The MIT License (MIT)
 *
 *  Copyright (c) 2016 Chau Thai
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 */

public class CoffeesAdapter extends ArrayAdapter<Coffees> {

    private static final String LOG_TAG = CoffeesAdapter.class.getSimpleName();
    private InputMethodManager mgr;
    private ConstraintLayout mainView;
    private android.support.v4.app.FragmentManager fragmentManager;

    public CoffeesAdapter(Activity context, List<Coffees> coffees, View newMainView, android.support.v4.app.FragmentManager originalFragManager) { super(context, 0, coffees);
        mainView = (ConstraintLayout) newMainView;
        fragmentManager = originalFragManager;
    }

    public View getView(int position, View convertView, final ViewGroup parent) {

        // checks if the existing view is being reused, otherwise inflate the view
        View coffeeView = convertView;
        if (coffeeView == null) {
            coffeeView = LayoutInflater.from(getContext()).inflate(
                    R.layout.coffee_item, parent, false );
        }

        final Coffees currentCoffee = getItem(position);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        // start a transaction that will be used any time the layout needs to be updated
        final android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();

        //TODO: CREATE COFFEE ITEMS AND FILL WITH INFORMATION
        // find the TextView in the coffee_item.xml layout with the ID coffeeName
        TextView coffeeName = (TextView) coffeeView.findViewById(R.id.coffeeName);
        coffeeName.setText(currentCoffee.getName());

        // find the TextView in the coffee_item.xml layout with the ID temp
        final TextView coffeeTemp = (TextView) coffeeView.findViewById(R.id.temp);
        coffeeTemp.setText(currentCoffee.getTemp() + "\u00B0 F");

        // find the TextView in the coffee_item.xml layout with the ID brewTime
        final TextView coffeeBrewTime = (TextView) coffeeView.findViewById(R.id.brewTime);
        coffeeBrewTime.setText(currentCoffee.getBrewTime() + " min");

        // find the TextView in the coffee_item.xml layout with the ID cupsWater
        TextView coffeeCupsOfWater = (TextView) coffeeView.findViewById(R.id.cupsWater);
        coffeeCupsOfWater.setText(currentCoffee.getCupsOfWater() + " cups");

        // find the TextView in the coffee_item.xml layout with the ID scoopsCoffee
        TextView coffeeScoops = (TextView) coffeeView.findViewById(R.id.scoopsCoffee);
        coffeeScoops.setText(currentCoffee.getScoopsOfCoffee() + " tbsp");

        // set what happenes when the user clicks the given coffee card
        CardView coffeeCardView = (CardView) coffeeView.findViewById(R.id.coffee_card);
        coffeeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final ListView coffee_list_view = (ListView) parent.findViewById(R.id.coffee_list);
                final View popupView = inflater.from(getContext()).inflate(R.layout.edit_coffee, null);

                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                popupWindow.showAtLocation(coffee_list_view, Gravity.CENTER, 0, 0);

                popupView.getForeground().setAlpha(0);

                final Coffees tempCoffee = new Coffees();

                final EditText mCoffeeName = (EditText) popupView.findViewById(R.id.coffee_name);
                mCoffeeName.setText(currentCoffee.getName());
                mCoffeeName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            if (!TextUtils.isEmpty(mCoffeeName.getText().toString())) {
                                hideSoftKeyboard(popupView);
                            } else { Toast.makeText(getContext(), "Please enter a name.", Toast.LENGTH_SHORT).show(); }
                        }
                        return true;
                    }
                });


                // Views for the brew temp
                final TextView tempText = (TextView) popupView.findViewById(R.id.brew_temp);
                tempText.setText(currentCoffee.getTemp() + "\u00B0 F");
                final ImageButton lowerTempBtn = (ImageButton) popupView.findViewById(R.id.lower_temp_btn);
                lowerTempBtn.setOnTouchListener(new View.OnTouchListener() {

                    private Handler motionHandler;
                    private int initDelay = 250;
                    private int delay = 100;

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                if (motionHandler != null) { return true; }
                                motionHandler = new Handler();
                                motionHandler.postDelayed(mAction, initDelay);
//                                initDelay+= 100;
                                break;

                            case MotionEvent.ACTION_UP:
                                if (motionHandler == null) {return true; }
                                motionHandler.removeCallbacks(mAction);
                                motionHandler = null;
                                break;
                        }

                        return false;
                    }

                    Runnable mAction = new Runnable() {

                        @Override
                        public void run() {

                            if (Integer.parseInt(tempText.getText().toString().replace("\u00B0 F", "")) == 50) {
                                Toast.makeText(getContext(), "Can't go lower than 50\u00B0 F", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            tempText.setText(Integer.parseInt(tempText.getText().toString().replace("\u00B0 F", "")) - 5 + "\u00B0 F");

                            motionHandler.postDelayed(this, delay);
                        }
                    };
                });
                lowerTempBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Integer.parseInt(tempText.getText().toString().replace("\u00B0 F", "")) == 50) {
                            Toast.makeText(getContext(), "Can't go lower than 50\u00B0 F", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        tempText.setText(Integer.parseInt(tempText.getText().toString().replace("\u00B0 F", "")) - 5 + "\u00B0 F");
                    }
                });

                final ImageButton raiseTempBtn = (ImageButton) popupView.findViewById(R.id.raise_temp_btn);
                raiseTempBtn.setOnTouchListener(new View.OnTouchListener() {

                    private Handler motionHandler;
                    private int initDelay = 250;
                    private int delay = 100;

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                if (motionHandler != null) { return true; }
                                motionHandler = new Handler();
                                motionHandler.postDelayed(mAction, initDelay);
//                                initDelay+= 100;
                                break;

                            case MotionEvent.ACTION_UP:
                                if (motionHandler == null) {return true; }
                                motionHandler.removeCallbacks(mAction);
                                motionHandler = null;
                                break;
                        }

                        return false;
                    }

                    Runnable mAction = new Runnable() {

                        @Override
                        public void run() {

                            if (Integer.parseInt(tempText.getText().toString().replace("\u00B0 F", "")) == 185) {
                                Toast.makeText(getContext(), "Can't go higher than 185\u00B0 F", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            tempText.setText(Integer.parseInt(tempText.getText().toString().replace("\u00B0 F", "")) + 5 + "\u00B0 F");

                            motionHandler.postDelayed(this, delay);
                        }
                    };
                });
                raiseTempBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Integer.parseInt(tempText.getText().toString().replace("\u00B0 F", "")) == 185) {
                            Toast.makeText(getContext(), "Can't go higher than 185\u00B0 F", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        tempText.setText(Integer.parseInt(tempText.getText().toString().replace("\u00B0 F", "")) + 5 + "\u00B0 F");
                    }
                });

                // Views for the brew time
                final TextView timeText = (TextView) popupView.findViewById(R.id.brew_time);
                timeText.setText(currentCoffee.getBrewTime() + " min");
                final ImageButton lowerTimeBtn = (ImageButton) popupView.findViewById(R.id.lower_brew_time_btn);
                lowerTimeBtn.setOnTouchListener(new View.OnTouchListener() {

                    private Handler motionHandler;
                    private int initDelay = 250;
                    private int delay = 100;

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                if (motionHandler != null) { return true; }
                                motionHandler = new Handler();
                                motionHandler.postDelayed(mAction, initDelay);
//                                initDelay+= 100;
                                break;

                            case MotionEvent.ACTION_UP:
                                if (motionHandler == null) {return true; }
                                motionHandler.removeCallbacks(mAction);
                                motionHandler = null;
                                break;
                        }

                        return false;
                    }

                    Runnable mAction = new Runnable() {

                        @Override
                        public void run() {

                            if (Integer.parseInt(timeText.getText().toString().substring(0, timeText.getText().toString().indexOf(":")).replace(":", "")) == 0) {
                                if (Integer.parseInt(timeText.getText().toString().substring(timeText.getText().toString().indexOf(":") + 1). replace(" min", "")) == 0) {
                                    Toast.makeText(getContext(), "Can't go that low", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                timeText.setText("0:00 min");
                            } else {
                                if (Integer.parseInt(timeText.getText().toString().substring(timeText.getText().toString().indexOf(":") + 1). replace(" min", "")) == 0) {
                                    timeText.setText((Integer.parseInt(timeText.getText().toString().substring(0, timeText.getText().toString().indexOf(":")).replace(":", "")) - 1)
                                            + timeText.getText().toString().substring(timeText.getText().toString().indexOf(":")).replace(":00", ":30"));
                                } else {
                                    timeText.setText(timeText.getText().toString(). replace(":30", ":00"));
                                }
                            }

                            motionHandler.postDelayed(this, delay);
                        }
                    };
                });
                lowerTimeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Integer.parseInt(timeText.getText().toString().substring(0, timeText.getText().toString().indexOf(":")).replace(":", "")) == 0) {
                            if (Integer.parseInt(timeText.getText().toString().substring(timeText.getText().toString().indexOf(":") + 1). replace(" min", "")) == 0) {
                                Toast.makeText(getContext(), "Can't go that low", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            timeText.setText("0:00 min");
                        } else {
                            if (Integer.parseInt(timeText.getText().toString().substring(timeText.getText().toString().indexOf(":") + 1). replace(" min", "")) == 0) {
                                timeText.setText((Integer.parseInt(timeText.getText().toString().substring(0, timeText.getText().toString().indexOf(":")).replace(":", "")) - 1)
                                        + timeText.getText().toString().substring(timeText.getText().toString().indexOf(":")).replace(":00", ":30"));
                            } else {
                                timeText.setText(timeText.getText().toString(). replace(":30", ":00"));
                            }
                        }
                    }
                });

                final ImageButton raiseTimeBtn = (ImageButton) popupView.findViewById(R.id.raise_brew_time_btn);
                raiseTimeBtn.setOnTouchListener(new View.OnTouchListener() {

                    private Handler motionHandler;
                    private int initDelay = 250;
                    private int delay = 100;

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                if (motionHandler != null) { return true; }
                                motionHandler = new Handler();
                                motionHandler.postDelayed(mAction, initDelay);
//                                initDelay+= 100;
                                break;

                            case MotionEvent.ACTION_UP:
                                if (motionHandler == null) {return true; }
                                motionHandler.removeCallbacks(mAction);
                                motionHandler = null;
                                break;
                        }

                        return false;
                    }

                    Runnable mAction = new Runnable() {

                        @Override
                        public void run() {

                            if (Integer.parseInt(timeText.getText().toString().replace(" min", "")
                                    .substring(timeText.getText().toString()
                                            .replace(" min", "").indexOf(":") + 1).replace(" min", "")) == 30) {

                                timeText.setText(
                                        (Integer.parseInt(timeText.getText().toString().replace(" min", "")
                                                .substring(0, timeText.getText().toString()
                                                        .replace(" min", "").indexOf(":")).replace(":", "")) + 1)
                                                + ":00 min");
                            } else {
                                timeText.setText(
                                        Integer.parseInt(timeText.getText().toString().replace(" min", "")
                                                .substring(0, timeText.getText().toString()
                                                        .replace(" min", "").indexOf(":")).replace(":", ""))
                                                + ":30 min");
                            }

                            motionHandler.postDelayed(this, delay);
                        }
                    };
                });
                raiseTimeBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (Integer.parseInt(timeText.getText().toString().replace(" min", "")
                                .substring(timeText.getText().toString()
                                        .replace(" min", "").indexOf(":") + 1).replace(" min", "")) == 30) {

                            timeText.setText(
                                    (Integer.parseInt(timeText.getText().toString().replace(" min", "")
                                            .substring(0, timeText.getText().toString()
                                                    .replace(" min", "").indexOf(":")).replace(":", "")) + 1)
                                            + ":00 min");
                        } else {
                            timeText.setText(
                                    Integer.parseInt(timeText.getText().toString().replace(" min", "")
                                            .substring(0, timeText.getText().toString()
                                                    .replace(" min", "").indexOf(":")).replace(":", ""))
                                            + ":30 min");
                        }
                    }
                });


                final SeekBar mWaterSeekBar = (SeekBar) popupView.findViewById(R.id.water_seek_bar);
                final SeekBar mCoffeeSeekBar = (SeekBar) popupView.findViewById(R.id.coffee_seek_bar);
                final TextView waterText = (TextView) popupView.findViewById(R.id.create_brew_water);
                final TextView coffeeText = (TextView) popupView.findViewById(R.id.create_brew_coffee);

                waterText.setText( (currentCoffee.getCupsOfWater() == 1 ?
                        currentCoffee.getCupsOfWater() + " cup" :
                        currentCoffee.getCupsOfWater() + " cups") );
                mWaterSeekBar.setProgress(currentCoffee.getCupsOfWater());
                mWaterSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                        String currentProgress = String.valueOf(progress);

//                        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(
//                                RelativeLayout.LayoutParams.WRAP_CONTENT,
//                                RelativeLayout.LayoutParams.WRAP_CONTENT);
//
//                        p.addRule(RelativeLayout.BELOW, seekBar.getId());
//                        Rect thumbRect = mWaterSeekBar.getThumb().getBounds();
//                        p.setMargins(thumbRect.centerX(),0,0,0);
//                        floatingText.setLayoutParams(p);
//                        floatingText.setText(currentProgress);

                        if (progress > 1) {
                            waterText.setText(currentProgress + " cups");
                        } else {
                            waterText.setText(currentProgress + " cup");
                        }

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
//                        floatingText.setVisibility(View.VISIBLE);
//                        final Animation animationFadeIn = AnimationUtils.loadAnimation(getActivity(),
//                                R.anim.floating_text_anim);
//                        floatingText.startAnimation(animationFadeIn);
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
//                        final Animation animationFadeOut = AnimationUtils.loadAnimation(getActivity(),
//                                R.anim.floating_text_fade_out_anim);
//                        floatingText.startAnimation(animationFadeOut);
//                        floatingText.setVisibility(View.INVISIBLE);

                    }
                });

                coffeeText.setText( (currentCoffee.getScoopsOfCoffee() == 1 ?
                        currentCoffee.getScoopsOfCoffee() + " tbsp" :
                        currentCoffee.getScoopsOfCoffee() + "tbsp") );
                mCoffeeSeekBar.setProgress(currentCoffee.getScoopsOfCoffee());
                mCoffeeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        String currentProgress = String.valueOf(progress);

                        if (progress > 1) {
                            coffeeText.setText(currentProgress + " tbsp");
                        } else {
                            coffeeText.setText(currentProgress + " tbsp");
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                // User clicks save
                TextView brewText = (TextView) popupView.findViewById(R.id.save_text);
                brewText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        tempCoffee.setName(mCoffeeName.getText().toString());
                        tempCoffee.setCupsOfWater(mWaterSeekBar.getProgress());
                        tempCoffee.setScoopsOfCoffee(mCoffeeSeekBar.getProgress());
                        tempCoffee.setTemp(Integer.parseInt(tempText.getText().toString().replace("\u00B0 F", "")));
                        tempCoffee.setBrewTime(timeText.getText().toString().replace(" min", ""));

                        if ( !Utilities.validateEditCoffee(tempCoffee, MainActivity.mainUser.getCoffees(), currentCoffee) ) {
                            Toast.makeText(getContext(), "Coffee already exists.", Toast.LENGTH_SHORT).show();
                            return;
                        } else if ( TextUtils.isEmpty(tempCoffee.getName())) {
                            Toast.makeText(getContext(), "Please enter a name.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        currentCoffee.setName(tempCoffee.getName());
                        currentCoffee.setCupsOfWater(tempCoffee.getCupsOfWater());
                        currentCoffee.setScoopsOfCoffee(tempCoffee.getScoopsOfCoffee());
                        currentCoffee.setTemp(tempCoffee.getTemp());
                        currentCoffee.setBrewTime(tempCoffee.getBrewTime());

                        Toast.makeText(getContext(), mCoffeeName.getText().toString() + " Coffee Saved", Toast.LENGTH_SHORT).show();
                        transaction.replace(R.id.container, new TabCoffees()).commit();
                        popupWindow.dismiss();
                    }
                });

                // User Clicks cancel
                TextView cancelText = (TextView) popupView.findViewById(R.id.delete_text);
                cancelText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
            }
        });

        final com.chauthai.swipereveallayout.SwipeRevealLayout swipeLayout = coffeeView.findViewById(R.id.swipe_layout);
        LinearLayout deleteLayout = (LinearLayout) coffeeView.findViewById(R.id.delete_layout);
        deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final ListView coffee_list_view = (ListView) parent.findViewById(R.id.coffee_list);
                final View deletePopupView = inflater.from(getContext()).inflate(R.layout.delete_confirmation, null);

                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true;
                final PopupWindow deletePopupWindow = new PopupWindow(deletePopupView, width, height, focusable);
                deletePopupWindow.showAtLocation(coffee_list_view, Gravity.CENTER, 0, 0);

                TextView deleteMessage = (TextView) deletePopupView.findViewById(R.id.delete_message);
                deleteMessage.setText(deleteMessage.getText().toString().replace("[item]", " coffee"));

                mainView.getForeground().setAlpha(220);

                TextView deleteAction = (TextView) deletePopupView.findViewById(R.id.delete_action);
                deleteAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                        Utilities.removeCoffee(currentCoffee, MainActivity.mainUser.getCoffees());
                        Utilities.defaultRoutinesUsedByCoffee(currentCoffee, MainActivity.mainUser.getRoutines());

                        transaction.replace(R.id.container, new TabCoffees()).commit();
                        deletePopupWindow.dismiss();
                    }
                });

                TextView cancelAction = (TextView) deletePopupView.findViewById(R.id.cancel_action);
                cancelAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deletePopupWindow.dismiss();
                        mainView.getForeground().setAlpha(0);
                    }
                });

                // handles when the pop up window is closed via touch outside of window or
                // via back button
                deletePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        mainView.getForeground().setAlpha(0);
                        deletePopupWindow.dismiss();
                    }
                });
            }
        });


        return coffeeView;
    }

    private void hideSoftKeyboard(View view) {
        mgr = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(
                view.getWindowToken(), 0 );
    }
}
