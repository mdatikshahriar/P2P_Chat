package com.example.p2pchat.Networking;

import android.util.Log;
import android.widget.Toast;

import com.example.p2pchat.MainActivity;

import java.io.IOException;
import java.net.Socket;

public class ClientClass extends Thread{
    Socket socket;

    String ip;
    int port;

    SendReceive sendReceive;

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
            MainActivity.networkObjects.setClientSocket(socket);
            Log.d("tag", "Client is connected to server");
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
