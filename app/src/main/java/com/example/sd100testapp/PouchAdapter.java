package com.example.sd100testapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PouchAdapter extends RecyclerView.Adapter<PouchAdapter.MyViewHolder> {

    Context context;
    ArrayList<PouchModal> PouchModalArrayList;

    public PouchAdapter(Context context, ArrayList<PouchModal> pouchModalArrayList) {
        this.context = context;
        PouchModalArrayList = pouchModalArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.striplist_item, parent, false);
        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        PouchModal pouchModal = PouchModalArrayList.get(position);
        holder.tvHeading.setText(pouchModal.heading);
        holder.etEntry.setText(pouchModal.entry);
    }

    @Override
    public int getItemCount() {
        return PouchModalArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvHeading;
        EditText etEntry;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHeading = itemView.findViewById(R.id.pouchWtNum);
            etEntry = itemView.findViewById(R.id.pouchWtEntry);
        }
    }
}
