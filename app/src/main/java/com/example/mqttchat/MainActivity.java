package com.example.mqttchat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.mqttchat.model.MessageModel;
import com.example.mqttchat.recycleradapter.ConversationRecyclerAdapter;
import com.example.mqttchat.viewmodel.MainViewModel;
import com.example.mqttchat.viewmodel.MainViewModelFactory;

public class MainActivity extends AppCompatActivity {

    private ConversationRecyclerAdapter adapter;
    private MainViewModel mainViewModel;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainViewModelFactory mainViewModelFactory = new MainViewModelFactory(getApplication());
        ViewModelProvider provider = new ViewModelProvider(this, mainViewModelFactory);
        mainViewModel = provider.get(MainViewModel.class);

        mainViewModel.subscribe().observe(this, this::messageArrived);

        setupLayout();
    }

    private void setupLayout() {
        recyclerView = findViewById(R.id.conversation_recycler_view);
        adapter = new ConversationRecyclerAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        EditText inputMessage = findViewById(R.id.text_input_message);
        Button buttonSendMessage = findViewById(R.id.button_submit_message);

        buttonSendMessage.setOnClickListener(view -> {
            mainViewModel.sendMessage(inputMessage.getText().toString());
            inputMessage.setText("");
        });
    }

    public void messageArrived(MessageModel message) {
        adapter.addMessage(message);
        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
    }
}