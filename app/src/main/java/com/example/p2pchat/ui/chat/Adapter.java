package com.example.p2pchat.ui.chat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p2pchat.MainActivity;
import com.example.p2pchat.Networking.Messages;
import com.example.p2pchat.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Messages> messageArray;

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    public Adapter(ArrayList<Messages> messageArray) {
        this.messageArray = messageArray;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        Log.d("tag", "Adapter created.");

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_received, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    @Override
    public int getItemViewType(int position) {
        Log.d("tag", "Adapter -> getItemViewType.");

        Messages message = messageArray.get(position);

        if (message.getType().equals("received")) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_SENT;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d("tag", "Adapter -> onBindViewHolder.");

        Messages message = messageArray.get(position);

        if(message.getType().equals("received")){
            ReceivedMessageHolder holder2 = (ReceivedMessageHolder) holder;
            TextView messageText = holder2.messageText;
            TextView timeText = holder2.timeText;
            TextView nameText = holder2.nameText;
            messageText.setText(message.getMessage());
            Log.d("tag", message.getMessage());
            String time = "12:00";
            timeText.setText(time);
            nameText.setText(MainActivity.networkObjects.getClientIPAddress());
        }
        else{
            SentMessageHolder holder1 = (SentMessageHolder) holder;
            TextView messageText = holder1.messageText;
            TextView timeText = holder1.timeText;
            messageText.setText(message.getMessage());
            Log.d("tag", message.getMessage());
            String time = "12:00";
            timeText.setText(time);
        }
    }

    @Override
    public int getItemCount() {
        Log.d("tag", "Adapter -> getItemCount.");

        return messageArray.size();
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {

        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.sent_text_message_body);
            timeText = itemView.findViewById(R.id.sent_text_message_time);
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.received_text_message_body);
            timeText = itemView.findViewById(R.id.received_text_message_time);
            nameText = itemView.findViewById(R.id.received_text_message_name);
        }
    }
}
