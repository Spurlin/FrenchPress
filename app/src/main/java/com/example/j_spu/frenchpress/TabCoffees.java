package com.example.j_spu.frenchpress;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.tab_coffees, container, false);
        super.onCreate(savedInstanceState);

        // Find a reference to the {@link ListView} in the layout
        ListView coffeesListView = (ListView) rootView.findViewById(R.id.coffee_list);

        mAdapter = new CoffeesAdapter(getActivity(), MainActivity.mainUser.getCoffees());

        coffeesListView.setAdapter(mAdapter);

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

                // User clicks save
                TextView brewText = (TextView) popupView.findViewById(R.id.save_text);
                brewText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Coffee Created", Toast.LENGTH_SHORT).show();
                        popupWindow.dismiss();
                    }
                });

                // User Clicks delete
                TextView cancelText = (TextView) popupView.findViewById(R.id.delete_text);
                cancelText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                        popupWindow.dismiss();
                    }
                });
            }
        });

//        Button quick_brew_btn = getActivity().findViewById(R.id.btn_brew);
//        quick_brew_btn.setVisibility(View.INVISIBLE);

        return rootView;
    }
}
