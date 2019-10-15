package com.example.p2pchat.Networking;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.example.p2pchat.MainActivity;
import com.example.p2pchat.ui.chat.Adapter;
import com.example.p2pchat.ui.chat.ChatFragment;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Base64;

public class SendReceive extends Thread{
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public static final int MESSAGE_READ=1;


    public SendReceive(Socket skt)
    {
        socket=skt;
        try {
            inputStream=socket.getInputStream();
            outputStream=socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        Log.d("tag", "Send receive started");

        byte[] buffer=new byte[1024];
        int bytes;

        Log.d("tag", "Handler passed");

        while (socket!=null)
        {
            Log.d("tag", "Receiving");

            try {
                bytes=inputStream.read(buffer);
                if(bytes>0)
                {
                    String msg = new String(buffer, "UTF-8");
                    Messages message = new Messages(msg, "received");
                    ChatFragment.messageArray.add(message);
                    Log.d("tag", "Inside Receiving. Message is: " + msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void write(final byte[] bytes) {
        new Thread(new Runnable(){
            @Override
            public void run() {
                Log.d("tag", "Sending");
                try {
                    outputStream.write(bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}