package com.example.sd100testapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVSummary_Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<com.example.sd100testapp.SummaryTextModal> summaryTextModalList;
    com.example.sd100testapp.SummaryTextAdapter summaryTextAdapter;
    String[] Heading;
    String[] Content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rvsummary);

        String[] strHeadingArr = getIntent().getStringArrayExtra("strHeadingArr");
        String[] strContentArr = getIntent().getStringArrayExtra("strContentArr");

        recyclerView = findViewById(R.id.txtrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        summaryTextModalList = new ArrayList<com.example.sd100testapp.SummaryTextModal>();

        summaryTextAdapter = new com.example.sd100testapp.SummaryTextAdapter(this, summaryTextModalList);
        recyclerView.setAdapter(summaryTextAdapter);

        Heading = strHeadingArr;

        Content = strContentArr;


        getData();

    }

    private  void getData(){

        for (int i=0;i<Heading.length;i++){
            com.example.sd100testapp.SummaryTextModal summaryTextModal = new com.example.sd100testapp.SummaryTextModal(Heading[i],Content[i]);
            summaryTextModalList.add(summaryTextModal);
        }

        summaryTextAdapter.notifyDataSetChanged();
    }
}