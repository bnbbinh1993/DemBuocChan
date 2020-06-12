package com.bnb.binh.dembuocchan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bnb.binh.dembuocchan.R;
import com.bnb.binh.dembuocchan.models.LichTrinh;

import java.util.List;

public class homeAdapter extends RecyclerView.Adapter<homeAdapter.ViewHolder> {

    private Context context;
    private List<LichTrinh> lichTrinhList;

    public homeAdapter(Context context, List<LichTrinh> lichTrinhList) {
        this.context = context;
        this.lichTrinhList = lichTrinhList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lich_trinh,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LichTrinh lichTrinh = lichTrinhList.get(position);
        holder.nameLichTrinh.setText(lichTrinh.getName());

    }

    @Override
    public int getItemCount() {
        return lichTrinhList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameLichTrinh;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameLichTrinh = itemView.findViewById(R.id.nameLichTrinh);
        }
    }
}
