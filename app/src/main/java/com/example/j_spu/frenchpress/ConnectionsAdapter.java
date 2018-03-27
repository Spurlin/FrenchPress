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

import java.util.List;

/**
 * Created by j_spu on 3/26/2018.
 */

public class ConnectionsAdapter extends ArrayAdapter<Machines> {

    private static final String LOG_TAG = ConnectionsAdapter.class.getSimpleName();

    public ConnectionsAdapter(Activity context, List<Machines> machines) {
        super(context, 0, machines);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // checks if the existing view is being reused, otherwise inflate the view
        View machineView = convertView;
        if (machineView == null) {
            machineView = LayoutInflater.from(getContext()).inflate(
                    R.layout.machine_item, parent, false );
        }

        final Machines currentMachine = getItem(position);

        TextView machineText = machineView.findViewById(R.id.machine_text);
        machineText.setText(currentMachine.getName());

        return machineView;
    }
}
