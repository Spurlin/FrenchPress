package com.example.j_spu.frenchpress;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo;
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by j_spu on 3/26/2018.
 */

public class Connections extends Fragment implements Serializable {

    private static final String LOG_TAG = Connections.class.getSimpleName();
    private android.support.v4.app.FragmentManager fragmentManager;
    private ConnectionsAdapter mAdapter;
    private final EndpointDiscoveryCallback mEndpointDiscoveryCallback =
            new EndpointDiscoveryCallback() {
                @Override
                public void onEndpointFound(
                        String endpointId, DiscoveredEndpointInfo discoveredEndpointInfo) {
                    // An endpoint was found!
                    Toast.makeText(getContext(), "An endpoint was found!", Toast.LENGTH_SHORT).show();
                    MainActivity.mainUser.addMachine(new Machines(endpointId));
                }

                @Override
                public void onEndpointLost(String endpointId) {
                    // A previously discovered endpoint has gone away.
                    Toast.makeText(getContext(), "An endpoint was lost!", Toast.LENGTH_SHORT).show();
                }
            };

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.connection, container, false);
        super.onCreate(savedInstanceState);
        fragmentManager = getFragmentManager();
        // start a transaction that will be used any time the layout needs to be updated
        final android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();

        ImageView mBackButton = (ImageView) rootView.findViewById(R.id.back_btn);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction.replace(R.id.container, new TabSettings()).commit();
            }
        });

        final TextView searchText = (TextView) rootView.findViewById(R.id.txt_search);
        searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!searchText.getText().toString().equalsIgnoreCase("search")) {
                    Utilities.stopDiscovery(getContext());
                    searchText.setText("SEARCH");
                    return;
                }

                searchText.setText("STOP");
                String SERVICE_ID = LOG_TAG;
                Utilities.startDiscovery(SERVICE_ID, getContext(), mEndpointDiscoveryCallback);


            }
        });

        //TODO: CONNECTIONS CODE
        if (searchText.getText().toString().equalsIgnoreCase("stop")) {

            // Find a reference to the {@link ListView} in the layout
            ListView machineListView = (ListView) rootView.findViewById(R.id.machines_list);
            mAdapter = new ConnectionsAdapter(getActivity(), MainActivity.mainUser.getMachines());
            machineListView.setAdapter(mAdapter);
            transaction.replace(R.id.container, new Connections()).commit();
        }

        return rootView;
    }
}
