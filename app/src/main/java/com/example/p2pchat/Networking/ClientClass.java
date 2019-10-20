package com.example.p2pchat.Networking;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.p2pchat.MainActivity;
import com.example.p2pchat.ui.HomeFragment;

import java.io.IOException;
import java.net.Socket;

public class ClientClass extends Thread{
    private Socket socket;
    private String ip;

    private int port;

    private SendReceive sendReceive;

    public ClientClass(String ip, int port)
    {
        this.ip = ip;
        this.port = port;
        MainActivity.networkObjects.setClientIPAddress(ip);
        MainActivity.networkObjects.setClientPortNumber(port);
    }

    @Override
    public void run() {
        try {
            socket=new Socket(MainActivity.networkObjects.getClientIPAddress(), MainActivity.networkObjects.getClientPortNumber());
            new Handler(Looper.getMainLooper()).post(new Runnable(){
                @Override
                public void run() {
                    Toast.makeText(MainActivity.context, "Successfully connected", Toast.LENGTH_LONG).show();
                    HomeFragment.status.setText("Connected");
                }
            });
            Log.d("tag", "Successfully connected");
            MainActivity.networkObjects.setConnected(true);
            sendReceive=new SendReceive(socket);
            sendReceive.start();
            MainActivity.networkObjects.setSendReceive(sendReceive);
        } catch (IOException e) {
            e.printStackTrace();
            final String error = "Error: "+e;
            new Handler(Looper.getMainLooper()).post(new Runnable(){
                @Override
                public void run() {
                    Toast.makeText(MainActivity.context, error, Toast.LENGTH_LONG).show();
                }
            });
            Log.d("tag", error);
        }
    }
}
