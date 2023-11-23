package com.example.healthai.docBotActivity;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthai.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.collection.LLRBNode;

import java.util.List;

public class docBotAdapter extends RecyclerView.Adapter<docBotAdapter.ViewHolder> {
    private ItemClickListener mClickListener;
    private LayoutInflater mInflater;
    private List<Message> messageList;
    private docBotActivity context;

    docBotAdapter(docBotActivity context, List<Message> messageList){
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.messageList = messageList;
    }


    Message getItem(int id){
        return messageList.get(id);
    }

    @Override
    public docBotAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.docbot_recycler_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Binds the data
        String type = messageList.get(position).getType();
        String message = messageList.get(position).getMessage();
        holder.tvMessage.setText(message);
        if (type == "user")
            holder.crdMessage.setCardBackgroundColor(Color.GRAY);

    }

    @Override
    public int getItemCount(){
        return messageList.size();
    }

    // Stores the views off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private CardView crdMessage;
        private TextView tvMessage;

        ViewHolder(@NonNull View itemView){
            super(itemView);
            crdMessage = itemView.findViewById(R.id.cardMessage);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view){
            if (mClickListener != null)
                mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public interface ItemClickListener{
        void onItemClick(View view, int position);
    }
}
