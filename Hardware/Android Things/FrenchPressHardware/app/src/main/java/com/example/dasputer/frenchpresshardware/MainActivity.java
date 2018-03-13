package com.example.dasputer.frenchpresshardware;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.things.pio.UartDevice;
import com.google.android.things.pio.PeripheralManager;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class MainActivity extends Activity {

    private static final String TAG = "UART";
    private PeripheralManager mPeripheralManagerService;
    private UartDevice mArduino;
    private byte[] mData = new byte[2];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //making buttons and textviews to development purposes
        Button tempBtn = findViewById(R.id.tempBtn);
        final TextView tempTV = findViewById(R.id.tempTV);

        Button groundsBtn = findViewById(R.id.groundsBtn);
        final TextView groundsTV = findViewById(R.id.groundsTV);

        Button servoBtn = findViewById(R.id.servoBtn);
        //conversateWithArduino();

        //used to manage the Arduino
        mPeripheralManagerService = PeripheralManager.getInstance();
        try {
            mArduino = mPeripheralManagerService.openUartDevice("UART0");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //set baudrate to 9600, or whatever allowed value as set on Arduino
        try {
            mArduino.setBaudrate(9600);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //setting the onClick to send a message to the Arduino and get back the temp in F
        tempBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //message to be sent in this case a 1
                mData[0] = (byte) (1);
                try {
                    //send the message
                    mArduino.write(mData, mData.length);
                    //wait a second for the response
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //read the response back, convert to string, log, output to screen
                    byte[] aData = new byte[4];
                    mArduino.read(aData, aData.length);
                    String s = new String(aData);
                    Log.i(TAG, s);
                    tempTV.setText(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        //setting the onClick to send a message to the Arduino and get back the light value
        groundsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //message to be sent in this case a 2
                mData[0] = (byte) (2);
                try {
                    //send the message
                    mArduino.write(mData, mData.length);
                    //wait a second for the response
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //read the response back, convert to string, log, output to screen
                    byte[] aData = new byte[4];
                    mArduino.read(aData, aData.length);
                    String s = new String(aData);
                    Log.i(TAG, s);
                    groundsTV.setText(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        //setting the onClick to send a message to the Arduino to move the servo (dispense grounds)
        servoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //message to be sent in this case a 3
                mData[0] = (byte) (3);
                try {
                    //send the message no need to wait on a response
                    mArduino.write(mData, mData.length);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //this function is used to do regular polling at 1 second intervals not needed at the moment
   /* public void conversateWithArduino() {
        mPeripheralManagerService = PeripheralManager.getInstance();
        try {
            mArduino = mPeripheralManagerService.openUartDevice("UART0");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //set baudrate to 9600, or whatever allowed value as set on Arduino
        try {
            mArduino.setBaudrate(9600);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //submit repeatable operation of sending data
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable() {

        public void run() {
            //trigger sending data
            mData[0] = (byte) (2);
            //mData[1] = (byte) (1);

            //write data to UART device
            try {
                mArduino.write(mData, mData.length);
                *//*try {
                    Log.i(TAG, "Going to sleep");
                    TimeUnit.SECONDS.sleep(1);
                    Log.i(TAG, "I'm back baby");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*//*
                byte[] aData = new byte[4];
                mArduino.read(aData, aData.length);
                String s = new String(aData);
                Log.i(TAG, s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }, 0, 1, TimeUnit.SECONDS);
}*/

}
