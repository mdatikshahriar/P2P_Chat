package com.example.p2pchat.Networking;

import com.example.p2pchat.MainActivity;

public class Network {

    ServerClass serverClass;
    ClientClass clientClass;

    public Network() {
    }

    public void onStartServerClicked(){
        serverClass = new ServerClass(MainActivity.networkObjects.getServerPortNumber());
        serverClass.start();
    }

    public void onConnectClicked(){
        clientClass = new ClientClass(MainActivity.networkObjects.getClientIPAddress(), MainActivity.networkObjects.getClientPortNumber());
        clientClass.start();
    }
}
