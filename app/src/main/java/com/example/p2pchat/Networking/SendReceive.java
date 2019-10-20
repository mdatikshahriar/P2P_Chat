package com.example.p2pchat.Networking;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.p2pchat.MainActivity;
import com.example.p2pchat.ui.chat.ChatFragment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SendReceive extends Thread{
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private FileInputStream fileInputStream;
    private FileOutputStream fileOutputStream;
    private BufferedInputStream bufferedInputStream;
    private BufferedOutputStream bufferedOutputStream;

    public SendReceive(Socket socket)
    {
        this.socket = socket;

        try {
            inputStream=socket.getInputStream();
            outputStream=socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            final String error = "Error: "+e;
            Log.d("tag", error);
        }
    }

    @Override
    public void run() {
        byte[] buffer=new byte[1048576];
        int bytes;

        while (socket!=null)
        {
            try {
                bytes=inputStream.read(buffer);
                if(bytes>0)
                {
                    MainActivity.handler.obtainMessage(MainActivity.MESSAGE_READ,bytes,-1,buffer).sendToTarget();
                }
            } catch (IOException e) {
                e.printStackTrace();
                final String error = "Error: "+e;
                Log.d("tag", error);
            }
        }
    }

    public void write(final byte[] bytes) {
        new Thread(new Runnable(){
            @Override
            public void run() {
                Log.d("sender", "Sending message");
                try {
                    outputStream.write(bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                    final String error = "Error: "+e;
                    Log.d("tag", error);
                }
            }
        }).start();
    }

    public void sendFile(final String filePath){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("sender", "sending file");
                try {
                    File file = new File(filePath);
                    fileInputStream = new FileInputStream(file);

                    byte[] buffer=new byte[(int)file.length()];
                    int bytes = buffer.length;

                    bufferedInputStream = new BufferedInputStream(fileInputStream);

                    bufferedInputStream.read(buffer, 0, bytes);

                    outputStream.write(buffer, 0, bytes);

                    Log.d("sender", "file sent");

                    outputStream.flush();
                } catch (IOException e){
                    e.printStackTrace();
                    final String error = "Error: "+e;
                    Log.d("tag", error);
                }
            }
        }).start();
    }

    public void writeFile(final String fileName){
        new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] buffer=new byte[1048576];
                int bytes = buffer.length, bytesRead;

                try {
                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
                    fileOutputStream = new FileOutputStream(file);
                    bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

                    bytesRead = inputStream.read(buffer, 0, bytes);

                    bufferedOutputStream.write(buffer, 0, bytesRead);

                    Log.d("file", String.valueOf(bytesRead) + buffer.toString());
                    bufferedOutputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    final String error = "Error: "+e;
                    Log.d("error", error);
                }
            }
        }).start();
    }
}