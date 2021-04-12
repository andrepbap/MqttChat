package com.example.mqttchat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mqttchat.client.MyMqttClient;
import com.example.mqttchat.model.MessageModel;
import com.example.mqttchat.recycleradapter.ConversationRecyclerAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;

public class MainActivity extends AppCompatActivity implements IMqttMessageListener {

    MyMqttClient myMqttClient;
    List<MessageModel> messageModelList;
    ConversationRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageModelList = new ArrayList<>();
        myMqttClient = new MyMqttClient(getApplicationContext());
        setupLayout();
    }

    private void setupLayout() {
        messageModelList = Arrays.asList(
//                new MessageModel("xablau", "123456"),
//                new MessageModel("xablau", "123456"),
//                new MessageModel("xablau", "123456")
        );

        RecyclerView recyclerView = findViewById(R.id.conversation_recycler_view);
        adapter = new ConversationRecyclerAdapter(messageModelList);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        TextInputEditText inputMessage = findViewById(R.id.text_input_message);
        Button buttonSendMessage = findViewById(R.id.button_submit_message);

        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = inputMessage.getText().toString();
                if(message != null && !message.isEmpty()) {
                    String json = new Gson().toJson(new MessageModel(message, "19:00"));
                    myMqttClient.sendMessage(json);
                } else {
                    Toast.makeText(getApplication(), "Por favor digite uma mensagem!", Toast.LENGTH_LONG).show();
                }
            }
        });

        myMqttClient.connectToMqtt(new IMqttActionListener(){

            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                myMqttClient.subscribeToMqttChannel(MainActivity.this);
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

            }
        });
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        new Thread(
                () -> {
                    try {
                        messageModelList = new ArrayList<>();
                        MessageModel messageModel = new Gson().fromJson(new String(message.getPayload()), MessageModel.class);
                        messageModelList.add(messageModel);
                        adapter.notifyDataSetChanged();
                    }catch (Exception e) {
                        System.out.println(e);
                    }
                }
        ).run();

    }
}