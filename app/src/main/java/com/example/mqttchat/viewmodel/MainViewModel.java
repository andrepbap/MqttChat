package com.example.mqttchat.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mqttchat.MainActivity;
import com.example.mqttchat.client.MyMqttClient;
import com.example.mqttchat.model.MessageModel;
import com.google.gson.Gson;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MainViewModel extends AndroidViewModel implements IMqttMessageListener {

    private final MutableLiveData<MessageModel> liveData;
    private final MyMqttClient myMqttClient;

    public MainViewModel(@NonNull Application application) {
        super(application);
        liveData = new MutableLiveData<>();
        myMqttClient = new MyMqttClient(application);
    }

    public LiveData<MessageModel> subscribe() {
        myMqttClient.connectToMqtt(new IMqttActionListener(){

            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                myMqttClient.subscribeToMqttChannel(MainViewModel.this);
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

            }
        });

        return liveData;
    }

    public void sendMessage(String message) {
        if(message != null && !message.isEmpty()) {
            String json = new Gson().toJson(new MessageModel(message, "19:00"));
            myMqttClient.sendMessage(json);
        } else {
            Toast.makeText(getApplication(), "Por favor digite uma mensagem!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        MessageModel messageModel = new Gson().fromJson(new String(message.getPayload()), MessageModel.class);
        liveData.postValue(messageModel);
    }
}
