package com.example.j_spu.frenchpress;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by j_spu on 2/13/2018.
 */

public class TabHome extends Fragment implements Serializable {

    private PopupWindow popupWindow;
    private DrawerLayout mainLayout;
    private int tempInt;
    private com.github.lzyzsd.circleprogress.ArcProgress waterLevel;
    private com.github.lzyzsd.circleprogress.ArcProgress coffeeLevel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.tab_home, container, false);
        super.onCreate(savedInstanceState);

        // The progress bars that show the water and coffee levels
        waterLevel = rootView.findViewById(R.id.water_level_progress);
        coffeeLevel = rootView.findViewById(R.id.coffee_level_progress);

        // set up the view with the retrieved data
//        setView(rootView, container);

        // set up the next routine field in the header
        TextView mNextRoutine = (TextView) rootView.findViewById(R.id.next_routine);

        SimpleDateFormat simpleDateFormatDay = new SimpleDateFormat("dd/MM/yyyy h:mm a");
//        SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("dd/MM/yyyy h:mm a");

        //TODO: UPDATE THE TIME SHOWN FOR NEXT ROUTINE
//        Date currentTime = Calendar.getInstance().getTime();
//        Date alarmDate = currentTime;
//
//        try {
//            alarmDate = simpleDateFormatDay.parse("08/03/2018 6:00 AM");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        mNextRoutine.setText(currentTime.compareTo(alarmDate));

        Button quick_brew_btn = rootView.findViewById(R.id.btn_brew);

        quick_brew_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final FrameLayout homeLayout = (FrameLayout) rootView.findViewById(R.id.home_mainL);
                final View popupView = inflater.from(getContext()).inflate(R.layout.quick_brew, null);

                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                popupWindow.showAtLocation(homeLayout, Gravity.CENTER, 0, 0);
                final SeekBar mWaterSeekBar = (SeekBar) popupView.findViewById(R.id.water_seek_bar);
                final SeekBar mCoffeeSeekBar = (SeekBar) popupView.findViewById(R.id.coffee_seek_bar);
//                final TextView floatingText = (TextView) popupView.findViewById(R.id.float_text);
                final TextView waterText = (TextView) popupView.findViewById(R.id.quick_brew_water);
                final TextView coffeeText = (TextView) popupView.findViewById(R.id.quick_brew_coffee);

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



                // User clicks brew
                TextView brewText = (TextView) popupView.findViewById(R.id.brew_text);
                brewText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Brew Sent:\n"
                                + "Water: " + mWaterSeekBar.getProgress()
                                + "\nCoffee: " + mCoffeeSeekBar.getProgress()
                                + "\nTemp: " + tempText.getText().toString().replace(" degrees", "")
                                + "\nTime: " + timeText.getText().toString(), Toast.LENGTH_SHORT).show();
                        popupWindow.dismiss();
                    }
                });

                // User Clicks cancel
                TextView cancelText = (TextView) popupView.findViewById(R.id.cancel_text);
                cancelText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

            }
        });

        CardView coffeeInfo = rootView.findViewById(R.id.coffee_levels_card);
        coffeeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Coffee Info", Toast.LENGTH_SHORT).show();
            }
        });
        CardView waterInfo = rootView.findViewById(R.id.water_levels_card);
        waterInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Water Info", Toast.LENGTH_SHORT).show();
            }
        });
        CardView machineInfo = rootView.findViewById(R.id.machine_status_card);
        machineInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Machine Info", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}
