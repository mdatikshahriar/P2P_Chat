package com.example.p2pchat.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.p2pchat.MainActivity;
import com.example.p2pchat.Networking.Network;
import com.example.p2pchat.R;

public class ClientFragment extends Fragment {

    private EditText clientIPAddress, clientPortNumber;
    private Button connectButton, clientUpdateButton;

    private Network network;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_client, container, false);

        network = new Network();

        clientIPAddress = root.findViewById(R.id.client_ip_edit_text);
        clientPortNumber = root.findViewById(R.id.client_port_edit_text);
        connectButton = root.findViewById(R.id.client_connect_button);
        clientUpdateButton = root.findViewById(R.id.client_update_button);

        clientIPAddress.setText(MainActivity.networkObjects.getClientIPAddress());
        clientPortNumber.setText(String.valueOf(MainActivity.networkObjects.getClientPortNumber()));

        clientUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.networkObjects.isConnected()){
                    new Handler(Looper.getMainLooper()).post(new Runnable(){
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.context, "You are already connected. Please disconnect first.", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else{
                    MainActivity.networkObjects.setClientIPAddress(clientIPAddress.getText().toString());
                    MainActivity.networkObjects.setClientPortNumber(Integer.parseInt(clientPortNumber.getText().toString()));

                    SharedPreferences.Editor editor = MainActivity.sharedpreferences.edit();
                    editor.putString(MainActivity.PREV_CLIENT_IP_KEY, clientIPAddress.getText().toString());
                    editor.putInt(MainActivity.PREV_CLIENT_PORT_KEY, Integer.parseInt(clientPortNumber.getText().toString()));
                    editor.apply();

                    clientIPAddress.setText("");
                    clientPortNumber.setText("");
                }
            }
        });

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                network.onConnectClicked();
            }
        });

        return root;
    }
}