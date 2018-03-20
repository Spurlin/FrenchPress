package com.example.dasputer.frenchpresshardware;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.things.pio.UartDevice;
import com.google.android.things.pio.PeripheralManager;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class MainActivity extends Activity {

    private static final String TAG = "UART";
    private PeripheralManager mPeripheralManagerService;
    private UartDevice mArduino;
    private byte[] mData = new byte[2];

    private Double temperture;
    private Double grounds;
    private Double weight;

    Button tempBtn;
    TextView tempTV;
    Button groundsBtn;
    TextView groundsTV;
    Button servoBtn;
    Button weightBtn;
    TextView weightTV;

    private int mInterval = 3000; // 5 seconds by default, can be changed later
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //making buttons and textviews to development purposes
        tempBtn = findViewById(R.id.tempBtn);
        tempTV = findViewById(R.id.tempTV);

        groundsBtn = findViewById(R.id.groundsBtn);
        groundsTV = findViewById(R.id.groundsTV);

        servoBtn = findViewById(R.id.servoBtn);

        weightBtn = findViewById(R.id.weightBtn);
        weightTV = findViewById(R.id.weightTV);

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

        //setting the onClick to send a message to the Arduino and get back the temperture in F
        tempBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                temperture = sensorCheck(1);
                tempTV.setText(temperture.toString());

            }
        });
        //setting the onClick to send a message to the Arduino and get back the light value
        groundsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                grounds = sensorCheck(2);
                groundsTV.setText(grounds.toString());

            }
        });
        //setting the onClick to send a message to the Arduino to move the servo (dispense grounds)
        servoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sensorCheck(3);
            }
        });

        weightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                weight = sensorCheck(4);
                weightTV.setText(weight.toString());

            }
        });

        //setting up a repeating poll every 3 seconds
        mHandler = new Handler();
        startRepeatingTask();



    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
    }
    //polls the sensors every 3 seconds
    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                check(); //this function can change value of mInterval.
            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

    public void check(){
        temperture = sensorCheck(1);
        tempTV.setText(temperture.toString());

        grounds = sensorCheck(2);
        groundsTV.setText(grounds.toString());

        //sensorCheck(3);

        weight = sensorCheck(4);
        weightTV.setText(weight.toString());

        if (temperture > 60 && grounds < 50 && weight > 1)
            sensorCheck(5);
        else
            sensorCheck(6);
    }




    Double sensorCheck(int c){
        //message to be sent in this case a 1
        mData[0] = (byte) (c);
        try {
            //send the message
            mArduino.write(mData, mData.length);
            //wait a second for the response
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //read the response back, convert to string, log, output to screen
            byte[] aData = new byte[10];
            int length = mArduino.read(aData, aData.length);

            String s = new String(aData,0,length);

            Log.i(TAG, s);
            if(!s.isEmpty())
               return Double.parseDouble(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
