package com.example.p2pchat.ui;

import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.p2pchat.MainActivity;
import com.example.p2pchat.Networking.Network;
import com.example.p2pchat.R;

import static android.content.Context.WIFI_SERVICE;
import static java.lang.Thread.sleep;

public class HomeFragment extends Fragment {

    private Button connectButton, disconnectButton, chatButton;
    private TextView serverPort, clientIP, clientPort, warningText;
    public static TextView status;

    private Network network;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        network = new Network();

        connectButton = root.findViewById(R.id.home_connect_button);
        disconnectButton = root.findViewById(R.id.home_disconnect_button);
        disconnectButton = root.findViewById(R.id.home_disconnect_button);
        chatButton = root.findViewById(R.id.home_chat_button);
        warningText = root.findViewById(R.id.home_no_wifi_warning);
        serverPort = root.findViewById(R.id.home_server_port);
        clientIP = root.findViewById(R.id.home_client_ip);
        clientPort = root.findViewById(R.id.home_client_port);
        status = root.findViewById(R.id.home_status_text);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        WifiManager wm = (WifiManager) MainActivity.context.getSystemService(WIFI_SERVICE);
                        String ip = null;
                        if (wm != null) {
                            ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
                            if(ip.equals("0.0.0.0")){
                                warningText.setText(MainActivity.NO_WIFI_WARNING_TEXT);
                            }
                            else {
                                warningText.setText("Your IP address is " + ip);
                            }
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        serverPort.setText(String.valueOf(MainActivity.networkObjects.getServerPortNumber()));
        clientIP.setText(MainActivity.networkObjects.getClientIPAddress());
        clientPort.setText(String.valueOf(MainActivity.networkObjects.getClientPortNumber()));

        if(MainActivity.networkObjects.isConnected()){
            status.setText("Connected");
        }
        else {
            status.setText("Not connected");
        }

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                network.onConnectClicked();
            }
        });

        disconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.networkObjects.isConnected())
                {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                MainActivity.networkObjects.getSendReceive().write(MainActivity.DISCONNECTED.getBytes());
                                sleep(1000);
                                network.onDisconnectClicked();

                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                else {
                    new Handler(Looper.getMainLooper()).post(new Runnable(){
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.context, "You haven't connected yet.", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_home_to_nav_chat);
            }
        });

        return root;
    }
}