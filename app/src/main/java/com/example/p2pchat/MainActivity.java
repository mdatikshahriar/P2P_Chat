package com.example.p2pchat;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import com.example.p2pchat.Networking.Messages;
import com.example.p2pchat.Networking.NetworkObjects;
import com.example.p2pchat.ui.chat.Adapter;
import com.example.p2pchat.ui.chat.ChatFragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.format.Formatter;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.p2pchat.ui.settings.SettingsFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public static TextView headerName, headerIP;
    public static NetworkObjects networkObjects;
    public static Context context;
    public static Handler handler;
    public static final int MESSAGE_READ=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = getApplicationContext();

        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ip = null;
        if (wm != null) {
            ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        }

        networkObjects = new NetworkObjects(ip,"192.168.0.100", 6666, 6666);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_server, R.id.nav_client,
                R.id.nav_chat, R.id.nav_settings, R.id.nav_about)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View header = navigationView.inflateHeaderView(R.layout.nav_header_main);

        headerName = header.findViewById(R.id.nav_header_name);
        headerIP = header.findViewById(R.id.nav_header_ip);
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

                        String[] receivedArray = received.split("~",3);
                        String senderName = receivedArray[0];
                        String time = receivedArray[1];
                        String message = receivedArray[2];

                        networkObjects.setSenderName(senderName);

                        Messages messageArray = new Messages(message, time, "received");
                        ChatFragment.messageArray.add(messageArray);

                        new Handler(Looper.getMainLooper()).post(new Runnable(){
                            @Override
                            public void run() {
                                Adapter mMessageAdapter = new Adapter(ChatFragment.messageArray);
                                ChatFragment.mMessageRecyclerView.setAdapter(mMessageAdapter); }
                        });

                        Log.d("receiver", "Received message is: " + message);
                        break;
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
}
