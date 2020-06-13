package com.bnb.binh.dembuocchan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bnb.binh.dembuocchan.R;
import com.bnb.binh.dembuocchan.models.HIstory;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.Viewholder> {
    private Context context;
    private List<HIstory> historyList;

    public HistoryAdapter(Context context, List<HIstory> historyList) {
        this.context = context;
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        HIstory hIstory = historyList.get(position);
        holder.name.setText(hIstory.getNameHis());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView name;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameHistory);
        }
    }
}
