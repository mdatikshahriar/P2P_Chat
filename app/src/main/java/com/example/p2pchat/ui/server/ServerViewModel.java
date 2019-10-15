package com.example.p2pchat.ui.server;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ServerViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ServerViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}