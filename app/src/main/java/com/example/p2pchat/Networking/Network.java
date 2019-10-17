package com.example.p2pchat.Networking;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.p2pchat.MainActivity;

public class Network {

    private ServerClass serverClass;
    private ClientClass clientClass;

    public Network() {
    }

    public void onStartServerClicked(){
        if(MainActivity.networkObjects.isConnected()){
            new Handler(Looper.getMainLooper()).post(new Runnable(){
                @Override
                public void run() {
                    Toast.makeText(MainActivity.context, "Connection already established.", Toast.LENGTH_LONG).show();
                }
            });
        }
        else{
            serverClass = new ServerClass(MainActivity.networkObjects.getServerPortNumber());
            serverClass.start();
        }
    }

    public void onConnectClicked(){
        if(MainActivity.networkObjects.isConnected()){
            new Handler(Looper.getMainLooper()).post(new Runnable(){
                @Override
                public void run() {
                    Toast.makeText(MainActivity.context, "Connection already established.", Toast.LENGTH_LONG).show();
                }
            });
        }
        else if(MainActivity.networkObjects.getServerIPAddress().equals(MainActivity.networkObjects.getClientIPAddress())){
            new Handler(Looper.getMainLooper()).post(new Runnable(){
                @Override
                public void run() {
                    Toast.makeText(MainActivity.context, "Can't connect with Thyself", Toast.LENGTH_LONG).show();
                }
            });
        }
        else{
            clientClass = new ClientClass(MainActivity.networkObjects.getClientIPAddress(), MainActivity.networkObjects.getClientPortNumber());
            clientClass.start();
        }
    }
}
