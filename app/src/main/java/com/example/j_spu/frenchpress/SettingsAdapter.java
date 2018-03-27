package com.example.j_spu.frenchpress;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.List;

/**
 * Created by j_spu on 2/21/2018.
 */

public class SettingsAdapter extends ArrayAdapter<Settings> {

    private static final String LOG_TAG = SettingsAdapter.class.getSimpleName();
    private android.support.v4.app.FragmentManager fragmentManager;

    public SettingsAdapter(Activity context, List<Settings> settings, android.support.v4.app.FragmentManager originalFragManager) {
        super(context, 0, settings);
        fragmentManager = originalFragManager;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // checks if the existing view is being reused, otherwise inflate the view
        View settingsView = convertView;
        if (settingsView == null) {
            settingsView = LayoutInflater.from(getContext()).inflate(
                    R.layout.setting_item, parent, false );
        }

        final Settings currentSetting = getItem(position);

        // find the ToggleButton in the setting_item.xml layout with the ID setting_btn
        final Switch settingBtn = settingsView.findViewById(R.id.setting_btn);

        if(currentSetting.getName().equalsIgnoreCase("connect to a machine")) {
            //TODO: FILL LAYOUT AND FILL WITH INFORMATION
            // find the TextView in the setting_item.xml layout with the ID setting_txt
            TextView settingTxt = settingsView.findViewById(R.id.setting_txt);
            settingTxt.setText(currentSetting.getName());

            LinearLayout mSettingsLayout = settingsView.findViewById(R.id.settings_main_layout);
            mSettingsLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // start a transaction that will be used any time the layout needs to be updated
                    final android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.container, new Connections(), "connections").commit();
                }
            });

            settingBtn.setVisibility(View.GONE);
        } else {
            settingBtn.setChecked(currentSetting.getState());
            settingBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentSetting.toggleState();
                    settingBtn.setChecked(currentSetting.getState());
                    Toast.makeText(getContext(), "You turned " +
                            ( currentSetting.getState() ? "on " : "off ")
                            + currentSetting.getName(), Toast.LENGTH_SHORT).show();
                }
            });
        }


        return settingsView;
    }
}
