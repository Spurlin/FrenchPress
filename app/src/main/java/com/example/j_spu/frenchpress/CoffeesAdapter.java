package com.example.j_spu.frenchpress;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by j_spu on 2/21/2018.
 */

public class CoffeesAdapter extends ArrayAdapter<Coffees> {

    private static final String LOG_TAG = CoffeesAdapter.class.getSimpleName();

    public CoffeesAdapter(Activity context, List<Coffees> coffees) { super(context, 0, coffees); }

    public View getView(int position, View convertView, final ViewGroup parent) {

        // checks if the existing view is being reused, otherwise inflate the view
        View coffeeView = convertView;
        if (coffeeView == null) {
            coffeeView = LayoutInflater.from(getContext()).inflate(
                    R.layout.coffee_item, parent, false );
        }

        final Coffees currentCoffee = getItem(position);

        //TODO: CREATE COFFE ITEMS AND FILL WITH INFORMATION
        // find the TextView in the coffee_item.xml layout with the ID coffeeName
        TextView coffeeName = (TextView) coffeeView.findViewById(R.id.coffeeName);
        coffeeName.setText(currentCoffee.getName());

        // find the TextView in the coffee_item.xml layout with the ID temp
        TextView coffeeTemp = (TextView) coffeeView.findViewById(R.id.temp);
        coffeeTemp.setText(currentCoffee.getTemp() + " degrees");

        // find the TextView in the coffee_item.xml layout with the ID brewTime
        TextView coffeeBrewTime = (TextView) coffeeView.findViewById(R.id.brewTime);
        coffeeBrewTime.setText(currentCoffee.getBrewTime() + " minutes");

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
                Toast.makeText(getContext(), "You clicked on the " + currentCoffee.getName()
                + " coffee.", Toast.LENGTH_SHORT).show();

                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final ListView coffee_list_view = (ListView) parent.findViewById(R.id.coffee_list);
                final View popupView = inflater.from(getContext()).inflate(R.layout.edit_coffee, null);

                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                popupWindow.showAtLocation(coffee_list_view, Gravity.CENTER, 0, 0);

                final EditText mCoffeeName = (EditText) popupView.findViewById(R.id.coffee_name);
                mCoffeeName.setText(currentCoffee.getName());
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
                        currentCoffee.getScoopsOfCoffee() + " scoop" :
                        currentCoffee.getScoopsOfCoffee() + " scoops") );
                mCoffeeSeekBar.setProgress(currentCoffee.getScoopsOfCoffee());
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
                        Toast.makeText(getContext(), "Coffee Saved", Toast.LENGTH_SHORT).show();
                        popupWindow.dismiss();
                    }
                });

                // User Clicks delete
                TextView cancelText = (TextView) popupView.findViewById(R.id.delete_text);
                cancelText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                        popupWindow.dismiss();
                    }
                });
            }
        });


        return coffeeView;
    }
}
