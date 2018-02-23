package com.example.j_spu.frenchpress;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

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

        View rootView = inflater.inflate(R.layout.tab_coffees, container, false);
        super.onCreate(savedInstanceState);

        // Find a reference to the {@link ListView} in the layout
        ListView coffeesListView = (ListView) rootView.findViewById(R.id.coffee_list);

        mAdapter = new CoffeesAdapter(getActivity(), MainActivity.mainUser.getCoffees());

        coffeesListView.setAdapter(mAdapter);

//        Button quick_brew_btn = getActivity().findViewById(R.id.btn_brew);
//        quick_brew_btn.setVisibility(View.INVISIBLE);

        return rootView;
    }
}
