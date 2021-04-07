package com.example.mqttchat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mqttchat.client.MyMqttClient;
import com.example.mqttchat.model.MessageModel;
import com.example.mqttchat.recycleradapter.ConversationRecyclerAdapter;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupLayout();

        new MyMqttClient(getApplicationContext());
    }

    private void setupLayout() {
        List<MessageModel> messageModelList = Arrays.asList(
                new MessageModel("xablau", "123456"),
                new MessageModel("xablau", "123456"),
                new MessageModel("xablau", "123456")
        );

        RecyclerView recyclerView = findViewById(R.id.conversation_recycler_view);
        recyclerView.setAdapter(new ConversationRecyclerAdapter(messageModelList));
        recyclerView.setHasFixedSize(true);
    }
}