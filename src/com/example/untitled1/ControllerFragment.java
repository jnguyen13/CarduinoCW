package com.example.untitled1;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by student on 4/3/14.
 * Used to control what the buttons do.
 */
public class ControllerFragment extends Fragment {

    public static final String PREFERENCE_KEY_IP_ADDRESS = "preference_key_ip_address";
    public static final String PREFERENCE_PORT_NUMBER = "preference_port_number";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        UDPSender.setIpAddress(pref.getString(PREFERENCE_KEY_IP_ADDRESS, "127.0.0.0"));
        UDPSender.setPort(Integer.parseInt(pref.getString(PREFERENCE_PORT_NUMBER, "2390")));

        View viewTreeRoot = inflater.inflate(R.layout.fragment_controller, container, false);

        Button powerButton = (Button) viewTreeRoot.findViewById(R.id.power_button);
        powerButton.setActivated(false);
        powerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View powerButton) {
                if(UDPSender.isRunning()) {
                    UDPSender.setRunningState(false);
                }
                else {
                    UDPSender.setRunningState(true);
                    UDPSender.beginUdpLoop();
                    UDPSender.authenticate = true;
                    UDPSender.authenticate = false;
                }
            }
        });

        Button upButton = (Button) viewTreeRoot.findViewById(R.id.up_button);
        upButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch ( event.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        UDPSender.stop = false;  //quit stopping
                        UDPSender.forward = true; //go
                        break;
                    case MotionEvent.ACTION_UP:
                        UDPSender.forward = false; //stop going
                        UDPSender.stop = true; // stop
                        break;
                }
                return true;
            }
        });

        Button downButton = (Button) viewTreeRoot.findViewById(R.id.down_button);
        downButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch ( event.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        UDPSender.stop = false;  //quit stopping
                        UDPSender.reverse = true; //go
                        break;
                    case MotionEvent.ACTION_UP:
                        UDPSender.reverse = false; //stop going
                        UDPSender.stop = true; // stop
                        break;
                }
                return true;
            }
        });

        Button leftButton = (Button) viewTreeRoot.findViewById(R.id.left_button);
        leftButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch ( event.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        UDPSender.realign = false;  //quit stopping
                        UDPSender.left = true; //go
                        break;
                    case MotionEvent.ACTION_UP:
                        UDPSender.left = false; //stop going
                        UDPSender.realign = true; // stop
                        break;
                }
                return true;
            }
        });

        Button rightButton = (Button) viewTreeRoot.findViewById(R.id.right_button);
        rightButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch ( event.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        UDPSender.realign = false;  //quit stopping
                        UDPSender.right = true; //go
                        break;
                    case MotionEvent.ACTION_UP:
                        UDPSender.right = false; //stop going
                        UDPSender.realign = true; // stop
                        break;
                }
                return true;
            }
        });

        return viewTreeRoot;
    }
}