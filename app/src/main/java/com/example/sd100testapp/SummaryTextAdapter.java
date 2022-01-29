package com.example.sd100testapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SummaryTextAdapter extends RecyclerView.Adapter<SummaryTextAdapter.MyViewHolder> {

    Context context;
    ArrayList<SummaryTextModal> SummaryTextModalList;

    public SummaryTextAdapter(Context context, ArrayList<SummaryTextModal> summaryTextModalList) {
        this.context = context;
        SummaryTextModalList = summaryTextModalList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.striplistsum_item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        SummaryTextModal summaryTextModal = SummaryTextModalList.get(position);
        holder.rvHeading.setText(summaryTextModal.heading);
        holder.rvContent.setText(summaryTextModal.content);


    }

    @Override
    public int getItemCount() {
        return SummaryTextModalList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView rvHeading;
        EditText rvContent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rvHeading = itemView.findViewById(R.id.pouchWtNumSum);
            rvContent = itemView.findViewById(R.id.pouchWtEntrySum);
            rvContent.setEnabled(false);
        }
    }
}
