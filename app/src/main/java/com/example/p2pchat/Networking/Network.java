package com.example.p2pchat.Networking;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.p2pchat.MainActivity;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class Network {

    private ServerClass serverClass;
    private ClientClass clientClass;
    private NetworkObjects networkObjects;

    public Network() {
        this.networkObjects = MainActivity.networkObjects;
    }

    public void onStartServer(){
        if (!networkObjects.isWifiOpen()){
            new Handler(Looper.getMainLooper()).post(new Runnable(){
                @Override
                public void run() {
                    Toast.makeText(MainActivity.context, "Sorry, you are not connected to Wifi.", Toast.LENGTH_LONG).show();
                }
            });
        }
        else if(networkObjects.isConnected()){
            new Handler(Looper.getMainLooper()).post(new Runnable(){
                @Override
                public void run() {
                    Toast.makeText(MainActivity.context, "Connection already established", Toast.LENGTH_LONG).show();
                }
            });
        }
        else{
            serverClass = new ServerClass(MainActivity.networkObjects.getServerPortNumber());
            serverClass.start();
        }
    }

    public void onConnectClicked(){
        if (!networkObjects.isWifiOpen()){
            new Handler(Looper.getMainLooper()).post(new Runnable(){
                @Override
                public void run() {
                    Toast.makeText(MainActivity.context, "Sorry, you are not connected to Wifi.", Toast.LENGTH_LONG).show();
                }
            });
        }
        else if(networkObjects.isConnected()){
            new Handler(Looper.getMainLooper()).post(new Runnable(){
                @Override
                public void run() {
                    Toast.makeText(MainActivity.context, "Connection already established.", Toast.LENGTH_LONG).show();
                }
            });
        }
        else if(networkObjects.getServerIPAddress().equals(MainActivity.networkObjects.getClientIPAddress())){
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

    public void onDisconnectClicked(){
        if(networkObjects.getSocket() == null){
            new Handler(Looper.getMainLooper()).post(new Runnable(){
                @Override
                public void run() {
                    Toast.makeText(MainActivity.context, "No connection found", Toast.LENGTH_LONG).show();
                }
            });
        }
        else {
            try {
                networkObjects.getSocket().close();
                networkObjects.setConnected(false);
                new Handler(Looper.getMainLooper()).post(new Runnable(){
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.context, "Disconnected", Toast.LENGTH_LONG).show();
                    }
                });

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            sleep(1000);
                            onStartServer();
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
            } catch (IOException e) {
                e.printStackTrace();
                final String error = "Error: " + e;
                new Handler(Looper.getMainLooper()).post(new Runnable(){
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.context, error, Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }
}
