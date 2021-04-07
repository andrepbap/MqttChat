package com.example.mqttchat.recycleradapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mqttchat.R;
import com.example.mqttchat.model.MessageModel;

import java.util.List;

public class ConversationRecyclerAdapter extends RecyclerView.Adapter<ConversationRecyclerAdapter.MessageViewHolder> {

    private final List<MessageModel> messageList;

    public ConversationRecyclerAdapter(List<MessageModel> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_row, parent, false);

        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        holder.setupView(messageList.get(position));
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {

        private final TextView messageTextView;
        private final TextView timeTextView;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            messageTextView = itemView.findViewById(R.id.message_label);
            timeTextView = itemView.findViewById(R.id.time_label);
        }

        public void setupView(MessageModel messageModel) {
            messageTextView.setText(messageModel.getMessage());
            timeTextView.setText(timeTextView.getText());
        }
    }

}
