package com.example.j_spu.frenchpress;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import java.io.Serializable;

/**
 * Created by j_spu on 2/7/2018.
 */

public class TabCoffees extends Fragment implements Serializable {

    private PopupWindow popupWindow;
    private DrawerLayout mainLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab_coffees, container, false);
        super.onCreate(savedInstanceState);

        // set up the view with the retrieved data
        setView(rootView, container);

        return rootView;
    }

    // this method creates the proper view based on the users
    // data that has been stored in the users class.
    // Sets up for all routines the user currently has
    public void setView(View rootView, ViewGroup container) {
        // TODO: CREATE CARDS REAL TIME
    }
}
