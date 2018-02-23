package com.example.j_spu.frenchpress;

import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by j_spu on 2/7/2018.
 */

public class TabRoutines extends Fragment implements Serializable {

    private static final String LOG_TAG = TabRoutines.class.getSimpleName();
    private RoutinesAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab_routines, container, false);
        super.onCreate(savedInstanceState);

        // Find a reference to the {@link ListView} in the layout
        ListView routinesListView = (ListView) rootView.findViewById(R.id.routine_list);
        FrameLayout routinesMainLayout = (FrameLayout) rootView.findViewById(R.id.routines_mainL);
        routinesMainLayout.getForeground().setAlpha(0);

        ConstraintLayout view = container.getRootView().findViewById(R.id.main_layout);



        mAdapter = new RoutinesAdapter(getActivity(), MainActivity.mainUser.getRoutines(), view);

        routinesListView.setAdapter(mAdapter);

//        Button quick_brew_btn = getActivity().findViewById(R.id.btn_brew);
//        quick_brew_btn.setVisibility(View.GONE);

        return rootView;
    }
}
