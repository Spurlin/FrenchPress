package com.example.j_spu.frenchpress;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

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

        View rootView = inflater.inflate(R.layout.tab_home, container, false);
        super.onCreate(savedInstanceState);

        // set up the view with the retrieved data
//        setView(rootView, container);

        Button quick_brew_btn = rootView.findViewById(R.id.btn_brew);

        quick_brew_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "You want a quick brew!", Toast.LENGTH_SHORT).show();
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
}
