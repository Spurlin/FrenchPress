package com.example.j_spu.frenchpress;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
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

/**
 * Created by j_spu on 2/13/2018.
 */

public class TabHome extends Fragment implements Serializable {

    private PopupWindow popupWindow;
    private DrawerLayout mainLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.tab_home, container, false);
        super.onCreate(savedInstanceState);

        // set up the view with the retrieved data
//        setView(rootView, container);

        Button quick_brew_btn = rootView.findViewById(R.id.btn_brew);

        quick_brew_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "You want a quick brew!", Toast.LENGTH_SHORT).show();

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
//                lowerTempBtn.setOnClickListener(tempOnClickListener(Integer.valueOf((String) lowerTempBtn.getTag()), tempText));
                final ImageButton raiseTempBtn = (ImageButton) popupView.findViewById(R.id.raise_temp_btn);
//                raiseTempBtn.setOnClickListener(tempOnClickListener(Integer.valueOf((String) raiseTempBtn.getTag()), tempText));

                // Views for the brew time
                final TextView timeText = (TextView) popupView.findViewById(R.id.brew_time);
                final ImageButton lowerTimeBtn = (ImageButton) popupView.findViewById(R.id.lower_brew_time_btn);
//                lowerTimeBtn.setOnClickListener(timeOnClickListener(Integer.valueOf((String) lowerTimeBtn.getTag()), timeText));
                final ImageButton raiseTimeBtn = (ImageButton) popupView.findViewById(R.id.raise_brew_time_btn);
//                raiseTimeBtn.setOnClickListener(timeOnClickListener(Integer.valueOf((String) raiseTimeBtn.getTag()), timeText));

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
                            coffeeText.setText(currentProgress + " scoops");
                        } else {
                            coffeeText.setText(currentProgress + " scoop");
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
                        Toast.makeText(getContext(), "Brew Sent", Toast.LENGTH_SHORT).show();
                        popupWindow.dismiss();
                    }
                });

                // User Clicks cancel
                TextView cancelText = (TextView) popupView.findViewById(R.id.cancel_text);
                cancelText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
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

    // this method creates the proper view based on the users
    // data that has been stored in the users class.
    // Sets up for all routines the user currently has
    public void setView(View rootView, ViewGroup container) {
        // TODO: CREATE CARDS REAL TIME
    }

    public View.OnClickListener tempOnClickListener(int tag, final TextView text) {

        final int mTag = tag;
        final String fullTemp = text.getText().toString();
        final String temp = fullTemp.substring(0, fullTemp.indexOf(' ') - 1);
        final int tempInt = Integer.valueOf(temp);

        View.OnClickListener mOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTag > 0) {
                    text.setText(fullTemp.replace(temp, String.valueOf(tempInt + 5)));
                } else {
                    if (tempInt > 5) {
                        text.setText(fullTemp.replace(temp, String.valueOf(tempInt - 5)));
                    } else { Toast.makeText(getContext(), "Can't go lower than 5", Toast.LENGTH_SHORT).show(); }
                }
            }
        };

        return mOnClick;
    }

    public View.OnClickListener timeOnClickListener(int tag, final TextView text) {

        //TODO: FIX ERROR
        final int mTag = tag;
        final String fullTime = text.getText().toString();
        final String hour = fullTime.substring(0, fullTime.indexOf(':') - 1);
        final String min = fullTime.substring(fullTime.indexOf(':') + 1, fullTime.indexOf(' ') - 1);
        final int hourInt = Integer.valueOf(hour);
        final int minInt = Integer.valueOf(min);

        View.OnClickListener mOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (mTag > 0) {
                        if ( minInt == 0) {
                            text.setText(fullTime.replace(min, String.valueOf(minInt + 30)));
                        } else {
                            fullTime.replace(hour, String.valueOf(hourInt + 1));
                                text.setText(fullTime.replace(min, "00"));
                        }
                } else {
                    if (hourInt > 0 & minInt > 0) {
                        if ( minInt == 0) {
                            fullTime.replace(hour, String.valueOf(hourInt - 1));
                            text.setText(fullTime.replace(min, String.valueOf(minInt + 30)));
                        } else {
                            text.setText(fullTime.replace(min, "00"));
                        }
                    } else { Toast.makeText(getContext(), "Can't go lower than 0", Toast.LENGTH_SHORT).show(); }
                }
            }
        };

        return mOnClick;
    }
}
