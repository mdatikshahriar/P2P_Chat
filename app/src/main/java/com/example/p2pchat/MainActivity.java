package com.example.p2pchat;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import com.example.p2pchat.Networking.Messages;
import com.example.p2pchat.Networking.Network;
import com.example.p2pchat.Networking.NetworkObjects;
import com.example.p2pchat.ui.HomeFragment;
import com.example.p2pchat.ui.chat.Adapter;
import com.example.p2pchat.ui.chat.ChatFragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.format.Formatter;
import android.util.Log;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    public static TextView headerName, headerIP;

    public static ArrayList<Messages> messageArray;

    public static NetworkObjects networkObjects;
    public static Network network;
    public static Context context;
    public static Handler handler;
    public static SharedPreferences sharedpreferences;


    public static final int MESSAGE_READ=1;
    public static final String NO_WIFI_WARNING_TEXT = "No Wifi available. Connect to Wifi first...";
    public static final String MyPREFERENCES = "MySharedPreferences" ;
    public static final String NAME_KEY = "nameKey";
    public static final String SAVED_NAME_DEFAULT_VALUE = "Name not set yet";
    public static final String MY_PORT_KEY = "myKey";
    public static final int SAVED_MY_PORT_DEFAULT_VALUE = 6666;
    public static final String PREV_CLIENT_IP_KEY = "prevClientIPKey";
    public static final String SAVED_PREV_CLIENT_IP_DEFAULT_VALUE = "192.168.0.100";
    public static final String PREV_CLIENT_PORT_KEY = "prevClientPortKey";
    public static final int SAVED_PREV_CLIENT_PORT_DEFAULT_VALUE = 6666;

    public static final String DISCONNECTED = "disconnected";

    private String myName, prevClientIP;
    private int myPort, prevClientPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = getApplicationContext();

        //Checking SharedPreferences for previously saved data. If available then load it.
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        myName = sharedpreferences.getString(NAME_KEY, SAVED_NAME_DEFAULT_VALUE);
        myPort = sharedpreferences.getInt(MY_PORT_KEY, SAVED_MY_PORT_DEFAULT_VALUE);
        prevClientIP = sharedpreferences.getString(PREV_CLIENT_IP_KEY, SAVED_PREV_CLIENT_IP_DEFAULT_VALUE);
        prevClientPort = sharedpreferences.getInt(PREV_CLIENT_PORT_KEY, SAVED_PREV_CLIENT_PORT_DEFAULT_VALUE);

        //Checking IP address.
        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ip = null;
        if (wm != null) {
            ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        }

        networkObjects = new NetworkObjects(ip, prevClientIP, myPort, prevClientPort, myName);
        network = new Network();
        messageArray = new ArrayList<>();

        network.onStartServer();

        if (ip.equals("0.0.0.0") || ip.equals(null)){
            networkObjects.setServerIPAddress("Not Available");
            networkObjects.setWifiOpen(false);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_client,
                R.id.nav_chat, R.id.nav_settings, R.id.nav_about)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View header = navigationView.inflateHeaderView(R.layout.nav_header_main);

        headerName = header.findViewById(R.id.nav_header_name);
        headerIP = header.findViewById(R.id.nav_header_ip);
        headerName.setText(myName);
        headerIP.setText(ip);

        handler = new Handler(new Handler.Callback() {

            @Override
            public boolean handleMessage(Message msg) {
                Log.d("tag", "Handler started");
                switch (msg.what)
                {
                    case MESSAGE_READ:
                        byte[] readBuff= (byte[]) msg.obj;
                        String received = new String(readBuff, 0, msg.arg1);

                        if(received.equals(DISCONNECTED)){
                            network.onDisconnectClicked();
                            Toast.makeText(context,"Connection disconnected", Toast.LENGTH_LONG).show();
                        }
                        else {
                            String[] receivedArray = received.split("~",4);
                            Log.d("tag", received);
                            String type = receivedArray[0];
                            String senderName = receivedArray[1];
                            String time = receivedArray[2];
                            String message = receivedArray[3];

                            networkObjects.setSenderName(senderName);

                            Messages messages = new Messages(message, time, "received");
                            messageArray.add(messages);

                            new Handler(Looper.getMainLooper()).post(new Runnable(){
                                @Override
                                public void run() {
                                    Adapter mMessageAdapter = new Adapter(messageArray);
                                    ChatFragment.mMessageRecyclerView.setAdapter(mMessageAdapter); }
                            });

                            Log.d("receiver", "Received message is: " + message);
                            break;
                        }
                }
                return true;
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (networkObjects.getSocket() != null) {
            try {
                network.onDisconnectClicked();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
