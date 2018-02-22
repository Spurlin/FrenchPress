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

/**
 * Created by j_spu on 2/7/2018.
 */

public class TabSettings extends Fragment implements Serializable {

    private static final String LOG_TAG = TabSettings.class.getSimpleName();
    private SettingsAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab_settings, container, false);
        super.onCreate(savedInstanceState);

        // Find a reference to the {@link ListView} in the layout
        ListView settingListView = (ListView) rootView.findViewById(R.id.setting_list);

        mAdapter = new SettingsAdapter(getActivity(), MainActivity.mainUser.getSettings());

        settingListView.setAdapter(mAdapter);

        Button quick_brew_btn = getActivity().findViewById(R.id.btn_brew);
        quick_brew_btn.setVisibility(View.INVISIBLE);

        return rootView;
    }

    // this method creates the proper view based on the users
    // data that has been stored in the users class.
    // Sets up for all routines the user currently has
    public void setView(View rootView, ViewGroup container) {
        // TODO: CREATE CARDS REAL TIME
    }
}
