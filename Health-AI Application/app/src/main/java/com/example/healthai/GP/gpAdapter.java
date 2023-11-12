package com.example.healthai.GP;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthai.R;

import java.util.List;

public class gpAdapter extends RecyclerView.Adapter<gpAdapter.ViewHolder>{

    Context context;
    List<gpDetails> gpList;
    RecyclerViewInterface recyclerViewInterface;

    public gpAdapter(Context context, List<gpDetails> gpList, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.gpList = gpList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public gpAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_layout,parent,false);
        return new ViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull gpAdapter.ViewHolder holder, int position) {
        holder.gp_nameView.setText(gpList.get(position).getName());
        holder.gp_sexView.setText(gpList.get(position).getSex());
        holder.gp_mobile.setText(gpList.get(position).getMobile());
    }

    @Override
    public int getItemCount() {
        return gpList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView gp_nameView, gp_sexView, gp_mobile;
        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            gp_nameView = itemView.findViewById(R.id.gp_name);
            gp_sexView = itemView.findViewById(R.id.gp_sex);
            gp_mobile = itemView.findViewById(R.id.gp_mobile);

            itemView.setOnClickListener(view -> {
                if(recyclerViewInterface != null){
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        recyclerViewInterface.onItemClick(pos);
                    }
                }
            });
        }
    }
}
