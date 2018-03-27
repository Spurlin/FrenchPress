package com.example.j_spu.frenchpress;

import android.content.Context;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

/**
 * Created by j_spu on 2/7/2018.
 */

public class TabCoffees extends Fragment implements Serializable {

    private static final String LOG_TAG = TabCoffees.class.getSimpleName();
    private CoffeesAdapter mAdapter;
    private LinearLayout mEmptyStateLinearLayout;
    private InputMethodManager mgr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.tab_coffees, container, false);
        super.onCreate(savedInstanceState);

        // Find a reference to the {@link ListView} in the layout
        ListView coffeesListView = (ListView) rootView.findViewById(R.id.coffee_list);

        final ConstraintLayout view = container.getRootView().findViewById(R.id.main_layout);

        mAdapter = new CoffeesAdapter(getActivity(), MainActivity.mainUser.getCoffees(), view, getFragmentManager());

        mEmptyStateLinearLayout = (LinearLayout) rootView.findViewById(R.id.empty_view_layout);

        coffeesListView.setAdapter(mAdapter);
        coffeesListView.setEmptyView(mEmptyStateLinearLayout);

        View endOfListView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.end_of_list, null, false);

        // displays the empty view if there wasn't any coffees
        if (!mAdapter.isEmpty()) {
            mEmptyStateLinearLayout.setVisibility(View.GONE);
            coffeesListView.setVisibility(View.VISIBLE);
            coffeesListView.addFooterView(endOfListView);

            TextView counterText = (TextView) endOfListView.findViewById(R.id.counter_text);
            counterText.setText(counterText.getText().toString()
                    .replace("[number]", String.valueOf(mAdapter.getCount()))
                    .replace("[item]", (mAdapter.getCount() > 1 ? "Coffees" : "Coffee")));
        } else {
            mEmptyStateLinearLayout.setVisibility(View.VISIBLE);
            coffeesListView.setVisibility(View.GONE);
        }


        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();

        // start a transaction that will be used any time the layout needs to be updated
        final android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();

        FloatingActionButton mAddCoffee = (FloatingActionButton) rootView.findViewById(R.id.add_coffee_FAB);
        mAddCoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final FrameLayout homeLayout = (FrameLayout) rootView.findViewById(R.id.coffees_mainL);
                final View popupView = inflater.from(getContext()).inflate(R.layout.create_coffee, null);

                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                popupWindow.showAtLocation(homeLayout, Gravity.CENTER, 0, 0);

                final Coffees tempCoffee = new Coffees();

                final EditText mCoffeeName = (EditText) popupView.findViewById(R.id.coffee_name);
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

                        if ( !Utilities.validateNewCoffee(tempCoffee, MainActivity.mainUser.getCoffees()) ) {
                            Toast.makeText(getContext(), "Coffee already exists.", Toast.LENGTH_SHORT).show();
                            return;
                        } else if ( TextUtils.isEmpty(tempCoffee.getName())) {
                            Toast.makeText(getContext(), "Please enter a name.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Utilities.addCoffee(tempCoffee, MainActivity.mainUser.getCoffees());
                        Toast.makeText(getContext(), mCoffeeName.getText().toString() + " Coffee Created", Toast.LENGTH_SHORT).show();
                        transaction.replace(R.id.container, new TabCoffees()).commit();
                        popupWindow.dismiss();
                    }
                });

                // User Clicks delete
                TextView cancelText = (TextView) popupView.findViewById(R.id.delete_text);
                cancelText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
            }
        });

//        Button quick_brew_btn = getActivity().findViewById(R.id.btn_brew);
//        quick_brew_btn.setVisibility(View.INVISIBLE);

        return rootView;
    }

    private void hideSoftKeyboard(View view) {
        mgr = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(
                view.getWindowToken(), 0 );
    }

}
