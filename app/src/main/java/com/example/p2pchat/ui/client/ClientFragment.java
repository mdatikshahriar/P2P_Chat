package com.example.p2pchat.ui.client;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.p2pchat.MainActivity;
import com.example.p2pchat.Networking.Network;
import com.example.p2pchat.R;

public class ClientFragment extends Fragment {

    private ClientViewModel clientViewModel;
    private EditText clientIPAddress, clientPortNumber;
    private Button connectButton, clientUpdateButton;

    private Network network;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        clientViewModel =
                ViewModelProviders.of(this).get(ClientViewModel.class);
        View root = inflater.inflate(R.layout.fragment_client, container, false);

        network = new Network();

        clientIPAddress = root.findViewById(R.id.client_ip_edit_text);
        clientPortNumber = root.findViewById(R.id.client_port_edit_text);
        connectButton = root.findViewById(R.id.client_connect_client_button);
        clientUpdateButton = root.findViewById(R.id.client_update_button);

        clientIPAddress.setText(MainActivity.networkObjects.getClientIPAddress());
        clientPortNumber.setText(String.valueOf(MainActivity.networkObjects.getClientPortNumber()));

        clientUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.networkObjects.setClientIPAddress(clientIPAddress.getText().toString());
                MainActivity.networkObjects.setClientPortNumber(Integer.parseInt(clientPortNumber.getText().toString()));

                clientIPAddress.setText("");
                clientPortNumber.setText("");
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