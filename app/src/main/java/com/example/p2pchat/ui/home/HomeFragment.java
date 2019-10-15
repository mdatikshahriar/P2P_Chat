package com.example.p2pchat.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.p2pchat.MainActivity;
import com.example.p2pchat.Networking.Network;
import com.example.p2pchat.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Button startServerButton, connectClientButton, chatButton;
    private TextView serverIP, serverPort, clientIP, clientPort;

    private Network network;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        network = new Network();

        startServerButton = root.findViewById(R.id.home_start_server_button);
        connectClientButton = root.findViewById(R.id.home_connect_client_button);
        chatButton = root.findViewById(R.id.home_chat_button);
        serverIP = root.findViewById(R.id.home_server_ip);
        serverPort = root.findViewById(R.id.home_server_port);
        clientIP = root.findViewById(R.id.home_client_ip);
        clientPort = root.findViewById(R.id.home_server_port);

        serverIP.setText(MainActivity.networkObjects.getServerIPAddress());
        serverPort.setText(String.valueOf(MainActivity.networkObjects.getServerPortNumber()));
        clientIP.setText(MainActivity.networkObjects.getClientIPAddress());
        clientPort.setText(String.valueOf(MainActivity.networkObjects.getClientPortNumber()));

        startServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                network.onStartServerClicked();
            }
        });

        connectClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                network.onConnectClicked();
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