package com.example.lab1.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab1.Model.ThanhPho;
import com.example.lab1.R;

import java.util.ArrayList;

public class ThanhPhoAdapter extends RecyclerView.Adapter<ThanhPhoAdapter.viewHolder> {
    private final Context context;
    private final ArrayList<ThanhPho> list;

    public ThanhPhoAdapter(Context context, ArrayList<ThanhPho> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_ds, null);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.txtTenTP.setText("Thành phố: " + list.get(position).getTenThanhPho());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView txtTenTP;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenTP = itemView.findViewById(R.id.txtTenTP);

        }
    }
}
