package com.example.p2pchat.ui.settings;

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
import com.example.p2pchat.R;

public class SettingsFragment extends Fragment {

    private EditText nameEditText;
    private Button nameUpdateButton;

    private SettingsViewModel settingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                ViewModelProviders.of(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        nameEditText = root.findViewById(R.id.settings_name_editText);
        nameUpdateButton = root.findViewById(R.id.settings_name_update_button);

        nameEditText.setText(MainActivity.networkObjects.getMyName());

        nameUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();

                if(!name.isEmpty()){
                    MainActivity.networkObjects.setMyName(name);
                    MainActivity.headerName.setText(name);
                    nameEditText.setText("");
                }
            }
        });

        return root;
    }
}