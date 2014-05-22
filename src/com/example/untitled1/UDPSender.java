package com.example.untitled1;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.*;

/**
 * Created by student on 4/10/14.
 */
public class UDPSender {

    public static int port = 0;
    public static String ipAddress;

    public static boolean forward = false;
    public static boolean left = false;
    public static boolean right = false;
    public static boolean reverse = false;
    public static boolean stop = false;
    public static boolean running = false;
    public static boolean realign = false;
    public static boolean authenticate = false;


    public static String getIpAddress() {
        return ipAddress;
    }

    public static void setIpAddress(String ipAddress) {
        UDPSender.ipAddress = ipAddress;
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        UDPSender.port = port;
    }

    public static boolean isRunning() {
        return running;
    }

    public static void setRunningState(boolean running) {
        UDPSender.running = running;
    }

    public static void beginUdpLoop() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                running = true;

                while(running) //Depending on button, send packet
                {
                    if(forward)
                        logAndSendPacket("forward",ipAddress,port);
                    else if(reverse)
                        logAndSendPacket("reverse",ipAddress,port);
                    if(left)
                        logAndSendPacket("left",ipAddress,port);
                    else if(right)
                        logAndSendPacket("right",ipAddress,port);
                    else if(stop)
                        logAndSendPacket("stop",ipAddress,port);
                    else if(realign)
                        logAndSendPacket("realign",ipAddress,port);
                    else if(authenticate)
                        logAndSendPacket("authenticate",ipAddress,port);
                }
                return null;
            }
        }.execute();
    }

    public static void logAndSendPacket(String packetContents, String ipAddress, int port) { //Send those packets to Arduino

        try {
            //Communication Socket
            DatagramSocket socket = new DatagramSocket(port);

            //Resolve IP Address
            InetAddress address = InetAddress.getByName(ipAddress);

            //Package Message
            int messageLength = packetContents.length();
            byte[] byteMessage = packetContents.getBytes();

            //Create Packet
            DatagramPacket packet = new DatagramPacket(byteMessage, messageLength, port);

            //Send Packet
            socket.send(packet);

            Log.d("UDP", String.format("Send packet %s on port %d to address to address %s",packet.toString(), port, ipAddress));
        }
        catch (IOException e) {
             Log.e("UDP", "IOException occured during packet seed", e);
        }
    }
}
