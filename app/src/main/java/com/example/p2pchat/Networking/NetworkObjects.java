package com.example.p2pchat.Networking;

import java.net.Socket;

public class NetworkObjects {

    private String serverIPAddress ,clientIPAddress, myName, senderName;
    private int serverPortNumber, clientPortNumber;
    private boolean isConnected, isWifiOpen;

    private Socket socket = null;
    private SendReceive sendReceive = null;

    public NetworkObjects(String serverIPAddress, String clientIPAddress, int serverPortNumber, int clientPortNumber, String myName) {
        this.serverIPAddress = serverIPAddress;
        this.clientIPAddress = clientIPAddress;
        this.serverPortNumber = serverPortNumber;
        this.clientPortNumber = clientPortNumber;
        this.myName = myName;
        this.senderName = "";
        this.isConnected = false;
        this.isWifiOpen = true;
    }

    public String getClientIPAddress() {
        return clientIPAddress;
    }

    public void setClientIPAddress(String clientIPAddress) {
        this.clientIPAddress = clientIPAddress;
    }

    public int getServerPortNumber() {
        return serverPortNumber;
    }

    public void setServerPortNumber(int serverPortNumber) {
        this.serverPortNumber = serverPortNumber;
    }

    public int getClientPortNumber() {
        return clientPortNumber;
    }

    public void setClientPortNumber(int clientPortNumber) {
        this.clientPortNumber = clientPortNumber;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public SendReceive getSendReceive() {
        return sendReceive;
    }

    public void setSendReceive(SendReceive sendReceive) {
        this.sendReceive = sendReceive;
    }

    public String getServerIPAddress() {
        return serverIPAddress;
    }

    public void setServerIPAddress(String serverIPAddress) {
        this.serverIPAddress = serverIPAddress;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public boolean isWifiOpen() {
        return isWifiOpen;
    }

    public void setWifiOpen(boolean wifiOpen) {
        isWifiOpen = wifiOpen;
    }
}
