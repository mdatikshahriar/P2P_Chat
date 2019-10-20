package com.example.p2pchat.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p2pchat.MainActivity;
import com.example.p2pchat.Networking.Messages;
import com.example.p2pchat.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChatFragment extends Fragment {

    public static RecyclerView mMessageRecyclerView;
    private RecyclerView.Adapter mMessageAdapter;
    private RecyclerView.LayoutManager mlayoutManager;
    private Button sendButton, attachButton;
    private EditText myMessage;
    private String myName;

    public static final int REQUEST_FILE_GET = 728;

    Context context;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_chat, container, false);
        context = container.getContext();
        myName = MainActivity.networkObjects.getMyName();

        mMessageRecyclerView = root.findViewById(R.id.recylerview_message_list);
        mMessageRecyclerView.setHasFixedSize(true);

        mlayoutManager = new LinearLayoutManager(context);
        ((LinearLayoutManager) mlayoutManager).setStackFromEnd(true);
        mMessageRecyclerView.setLayoutManager(mlayoutManager);

        mMessageAdapter = new Adapter(MainActivity.messageArray);
        mMessageRecyclerView.setAdapter(mMessageAdapter);

        sendButton = root.findViewById(R.id.button_chatbox_send);
        attachButton = root.findViewById(R.id.button_chatbox_attach);
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
                        MainActivity.messageArray.add(message);

                        Adapter mMessageAdapter = new Adapter(MainActivity.messageArray);
                        mMessageRecyclerView.setAdapter(mMessageAdapter);

                        String tosent = "message" + "~" + myName + "~" + currentTime + "~" + messageString;
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

        attachButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(true){
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.setType("*/*");
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    // Only the system receives the ACTION_OPEN_DOCUMENT, so no need to test.
                    startActivityForResult(intent, REQUEST_FILE_GET);
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
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_FILE_GET) {
            Uri fileUri = data.getData();

            String[] split = fileUri.getPath().split(":");
            String filePath = "/storage/emulated/0/" + split[1];

            Log.d("tag", filePath);

            Cursor returnCursor = MainActivity.context.getContentResolver().query(fileUri, null, null, null, null);
            int fileName = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            returnCursor.moveToFirst();

            Log.d("tag", returnCursor.getString(fileName));

            String currentTime = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date());
            Messages message = new Messages(returnCursor.getString(fileName), currentTime, "sent");

            MainActivity.messageArray.add(message);

            Adapter mMessageAdapter = new Adapter(MainActivity.messageArray);
            mMessageRecyclerView.setAdapter(mMessageAdapter);

            String tosent = "file" + "~" + myName + "~" + currentTime + "~" + returnCursor.getString(fileName);
            MainActivity.networkObjects.getSendReceive().write(tosent.getBytes());
//
//            MainActivity.networkObjects.getSendReceive().sendFile(filePath);
        }
    }
}