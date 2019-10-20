package com.example.p2pchat.Networking;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.p2pchat.MainActivity;
import com.example.p2pchat.ui.HomeFragment;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerClass extends Thread{

    private Socket socket;
    private ServerSocket serverSocket;

    private int port;

    private SendReceive sendReceive;

    public ServerClass(int port) {
        this.port = port;
        MainActivity.networkObjects.setServerPortNumber(port);
    }

    @Override
    public void run() {
        try {
            serverSocket=new ServerSocket(port);
            new Handler(Looper.getMainLooper()).post(new Runnable(){
                @Override
                public void run() {
                    Toast.makeText(MainActivity.context, "Waiting for receiver to connect...", Toast.LENGTH_LONG).show();
                }
            });
            Log.d("tag", "Waiting for receiver to connect...");

            socket=serverSocket.accept();
            MainActivity.networkObjects.setSocket(socket);
            new Handler(Looper.getMainLooper()).post(new Runnable(){
                @Override
                public void run() {
                    Toast.makeText(MainActivity.context, "Connection established", Toast.LENGTH_LONG).show();
                    HomeFragment.status.setText("Connected");
                }
            });
            MainActivity.networkObjects.setConnected(true);
            Log.d("tag", "Connection established");

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
