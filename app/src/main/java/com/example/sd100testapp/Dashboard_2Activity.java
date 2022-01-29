package com.example.sd100testapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class Dashboard_2Activity extends AppCompatActivity {
    LinearLayout navigate3;
    LinearLayout navigate4;
    LinearLayout navigate5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard2);

        navigate3 = findViewById(R.id.stripactivitybtn);
        navigate4 = findViewById(R.id.icboxactivitybtn);
        navigate5 = findViewById(R.id.mcboxactivitybtn);

        String EmailValue = getIntent().getStringExtra("EmailValue");
        String MachineIDValue = getIntent().getStringExtra("MachineIdValue");

        navigate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard_2Activity.this, com.example.sd100testapp.Strip_Activity.class);
                intent.putExtra("EmailValue",EmailValue);
                intent.putExtra("MachineIDValue",MachineIDValue);
                startActivity(intent);
            }
        });

        navigate4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard_2Activity.this,ICBox_Activity.class);
                intent.putExtra("EmailValue",EmailValue);
                intent.putExtra("MachineIDValue",MachineIDValue);
                startActivity(intent);
            }
        });

        navigate5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard_2Activity.this,MCBox_Activity.class);
                intent.putExtra("EmailValue",EmailValue);
                intent.putExtra("MachineIDValue",MachineIDValue);
                startActivity(intent);
            }
        });
    }
}