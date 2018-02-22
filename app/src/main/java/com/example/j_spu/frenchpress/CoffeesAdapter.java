package com.example.j_spu.frenchpress;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by j_spu on 2/21/2018.
 */

public class CoffeesAdapter extends ArrayAdapter<Coffees> {

    private static final String LOG_TAG = CoffeesAdapter.class.getSimpleName();

    public CoffeesAdapter(Activity context, List<Coffees> coffees) { super(context, 0, coffees); }

    public View getView(int position, View convertView, ViewGroup parent) {

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
            }
        });


        return coffeeView;
    }
}
