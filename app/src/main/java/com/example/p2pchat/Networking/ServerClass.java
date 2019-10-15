package com.example.p2pchat.Networking;

import android.util.Log;
import android.widget.Toast;

import com.example.p2pchat.MainActivity;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerClass extends Thread{

    private Socket socket;
    private ServerSocket serverSocket;

    private int port;

    SendReceive sendReceive;

    public ServerClass(int port) {
        this.port = port;
        MainActivity.networkObjects.setServerPortNumber(port);
    }

    @Override
    public void run() {
        try {
            serverSocket=new ServerSocket(port);
            Log.d("tag", "Waiting for client...");
            socket=serverSocket.accept();
            MainActivity.networkObjects.setServerSocket(socket);
            Log.d("tag", "Connection established from server.");
            sendReceive=new SendReceive(socket);
            sendReceive.start();
            MainActivity.networkObjects.setSendReceive(sendReceive);
        } catch (IOException e) {
            e.printStackTrace();
            String error = "Error: "+e;
            Log.d("tag", error);
        }
    }
}
