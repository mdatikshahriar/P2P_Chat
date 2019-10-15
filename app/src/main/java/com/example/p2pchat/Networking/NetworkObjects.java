package com.example.p2pchat.Networking;

import java.net.Socket;

public class NetworkObjects {

    private String serverIPAddress ,clientIPAddress;
    private int serverPortNumber, clientPortNumber;

    private Socket serverSocket = null, clientSocket = null;
    private SendReceive sendReceive = null;

    public NetworkObjects(String serverIPAddress, String clientIPAddress, int serverPortNumber, int clientPortNumber) {
        this.serverIPAddress = serverIPAddress;
        this.clientIPAddress = clientIPAddress;
        this.serverPortNumber = serverPortNumber;
        this.clientPortNumber = clientPortNumber;
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

    public Socket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(Socket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
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
}