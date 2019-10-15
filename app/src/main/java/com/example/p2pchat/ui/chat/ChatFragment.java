package com.example.p2pchat.ui.chat;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p2pchat.MainActivity;
import com.example.p2pchat.Networking.Messages;
import com.example.p2pchat.Networking.NetworkObjects;
import com.example.p2pchat.Networking.SendReceive;
import com.example.p2pchat.R;

import java.net.Socket;
import java.util.ArrayList;

public class ChatFragment extends Fragment {

    static RecyclerView mMessageRecyclerView;
    private RecyclerView.Adapter mMessageAdapter;
    private RecyclerView.LayoutManager mlayoutManager;
    private Button sendButton;
    private EditText myMessage;
    public static ArrayList<Messages> messageArray;

    private ChatViewModel chatViewModel;

    Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        chatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);

        View root = inflater.inflate(R.layout.fragment_chat, container, false);
        context = container.getContext();

        messageArray = new ArrayList<>();

        mMessageRecyclerView = root.findViewById(R.id.recylerview_message_list);
        mMessageRecyclerView.setHasFixedSize(true);

        mlayoutManager = new LinearLayoutManager(context);
        ((LinearLayoutManager) mlayoutManager).setStackFromEnd(true);
        mMessageRecyclerView.setLayoutManager(mlayoutManager);

        mMessageAdapter = new Adapter(messageArray);
        mMessageRecyclerView.setAdapter(mMessageAdapter);

        sendButton = root.findViewById(R.id.button_chatbox_send);
        myMessage = root.findViewById(R.id.edit_text_chatbox);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Messages message;
                message = new Messages(myMessage.getText().toString(), "sent");
                messageArray.add(message);

                Adapter mMessageAdapter = new Adapter(messageArray);
                mMessageRecyclerView.setAdapter(mMessageAdapter);

                MainActivity.networkObjects.getSendReceive().write(myMessage.getText().toString().getBytes());
            }
        });

        new Thread() {
            @Override
            public void run() {
                int size = messageArray.size();
                while (true){
                    try {
                        if(size<messageArray.size()){
                            Adapter mMessageAdapter = new Adapter(messageArray);
                            mMessageRecyclerView.setAdapter(mMessageAdapter);
                            size=messageArray.size();
                        }
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        return root;
    }
}