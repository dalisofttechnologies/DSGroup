package com.example.sd100testapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class Dashboard_1Activity extends AppCompatActivity {
    LinearLayout navigate2;
    LinearLayout navigate6;
    LinearLayout navigate7;
    LinearLayout navigate15;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard1);
        navigate2 = findViewById(R.id.dashboard2navbtn);
        navigate6 = findViewById(R.id.leaktestactivitybtn);
        navigate7 = findViewById(R.id.Individualpouchwtactivitybtn);
//        navigate15 = findViewById(R.id.GSMactivitybtn);

        navigate2.setOnClickListener(view -> {
            Intent intent = new Intent(Dashboard_1Activity.this, com.example.sd100testapp.Dashboard_2Activity.class);
            startActivity(intent);
        });

        navigate6.setOnClickListener(view -> {
            Intent intent = new Intent(Dashboard_1Activity.this, com.example.sd100testapp.Leaktest_Activity.class);
            startActivity(intent);
        });

        navigate7.setOnClickListener(view -> {
            Intent intent = new Intent(Dashboard_1Activity.this,IndividualPouchWeight_Activity.class);
            startActivity(intent);
        });

//        navigate15.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Dashboard_1Activity.this, GSM_Activity.class);
//                startActivity(intent);
//            }
//        });
    }
}