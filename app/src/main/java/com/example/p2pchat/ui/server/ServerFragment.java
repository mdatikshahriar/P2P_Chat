package com.example.p2pchat.ui.server;

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
import com.example.p2pchat.Networking.NetworkObjects;
import com.example.p2pchat.R;

public class ServerFragment extends Fragment {

    private ServerViewModel serverViewModel;
    private EditText serverPortNumber;
    private Button startServerButton, serverUpdateButton;

    private Network network;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        serverViewModel =
                ViewModelProviders.of(this).get(ServerViewModel.class);
        View root = inflater.inflate(R.layout.fragment_server, container, false);

        network = new Network();

        serverPortNumber = root.findViewById(R.id.server_port_edit_text);
        startServerButton = root.findViewById(R.id.server_start_server_button);
        serverUpdateButton = root.findViewById(R.id.server_update_button);

        serverPortNumber.setText(String.valueOf(MainActivity.networkObjects.getClientPortNumber()));

        serverUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.networkObjects.setServerPortNumber(Integer.parseInt(serverPortNumber.getText().toString()));
                serverPortNumber.setText(serverPortNumber.getText());
            }
        });

        startServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                network.onStartServerClicked();
            }
        });

        return root;
    }
}