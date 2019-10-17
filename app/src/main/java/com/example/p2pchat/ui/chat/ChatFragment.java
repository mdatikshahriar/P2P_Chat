package com.example.p2pchat.ui.chat;

import android.content.Context;
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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p2pchat.MainActivity;
import com.example.p2pchat.Networking.Messages;
import com.example.p2pchat.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChatFragment extends Fragment {

    public static RecyclerView mMessageRecyclerView;
    private RecyclerView.Adapter mMessageAdapter;
    private RecyclerView.LayoutManager mlayoutManager;
    private Button sendButton;
    private EditText myMessage;
    public static ArrayList<Messages> messageArray;
    private String myName;

    private ChatViewModel chatViewModel;

    Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        chatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);

        View root = inflater.inflate(R.layout.fragment_chat, container, false);
        context = container.getContext();
        myName = MainActivity.networkObjects.getMyName();

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
                String currentTime = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date());
                String messageString = myMessage.getText().toString();

                if(!messageString.isEmpty()){
                    if(MainActivity.networkObjects.isConnected()){
                        message = new Messages(messageString, currentTime, "sent");
                        messageArray.add(message);

                        Adapter mMessageAdapter = new Adapter(messageArray);
                        mMessageRecyclerView.setAdapter(mMessageAdapter);

                        String tosent = myName + "~" + currentTime + "~" + messageString;
                        MainActivity.networkObjects.getSendReceive().write(tosent.getBytes());

                        myMessage.setText("");
                    }
                    else {
                        new Handler(Looper.getMainLooper()).post(new Runnable(){
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.context, "Please establish a connection first.", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }
        });

        return root;
    }
}