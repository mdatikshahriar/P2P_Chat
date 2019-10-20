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
import com.example.p2pchat.R;

public class SettingsFragment extends Fragment {

    private EditText nameEditText, serverPortEditText;
    private Button nameUpdateButton, serverPortUpdateButton;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        nameEditText = root.findViewById(R.id.settings_name_editText);
        serverPortEditText = root.findViewById(R.id.settings_server_port_editText);
        nameUpdateButton = root.findViewById(R.id.settings_name_update_button);
        serverPortUpdateButton = root.findViewById(R.id.settings_server_port_update_button);

        String prevName = MainActivity.networkObjects.getMyName();
        final int prevPort = MainActivity.networkObjects.getServerPortNumber();

        if(!prevName.equals(MainActivity.SAVED_NAME_DEFAULT_VALUE)){
            nameEditText.setText(prevName);
        }

        serverPortEditText.setText(String.valueOf(MainActivity.networkObjects.getServerPortNumber()));

        nameUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();

                if(!name.isEmpty()){
                    MainActivity.networkObjects.setMyName(name);
                    MainActivity.headerName.setText(name);
                    nameEditText.setText("");

                    SharedPreferences.Editor editor = MainActivity.sharedpreferences.edit();
                    editor.putString(MainActivity.NAME_KEY, name);
                    editor.apply();
                }
            }
        });

        serverPortUpdateButton.setOnClickListener(new View.OnClickListener() {
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
                    String newPort = serverPortEditText.getText().toString();

                    if (!newPort.equals(String.valueOf(prevPort))){
                        MainActivity.networkObjects.setServerPortNumber(Integer.parseInt(newPort));
                        serverPortEditText.setText("");
                        MainActivity.network.onStartServer();

                        SharedPreferences.Editor editor = MainActivity.sharedpreferences.edit();
                        editor.putInt(MainActivity.MY_PORT_KEY, Integer.parseInt(newPort));
                        editor.apply();
                    }
                }

            }
        });

        return root;
    }
}