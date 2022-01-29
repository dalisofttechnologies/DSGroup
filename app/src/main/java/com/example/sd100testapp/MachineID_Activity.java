package com.example.sd100testapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MachineID_Activity extends AppCompatActivity {

    Connection connect;
    String ConnectionResult = "";

    Integer i;
    Spinner spinner;
    TextView textView;
    Button machineNext;

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_id);

        //assign variable
        spinner = findViewById(R.id.machinespinner);
        textView = findViewById(R.id.machinetextview);
        machineNext = findViewById(R.id.machinenext);

        //Initialize ArrayList
        ArrayList<String> numberList = new ArrayList<>();
        ArrayList<String> numberList2 = new ArrayList<>();


        //Add number is ArrayList
        numberList.add("Select Machine SLN");

        //Get the data from Sql Server to be filled in the ArrayList
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();

            if (connect != null) {
                String query = "Select * from MachineID";
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);

                while (rs.next()) {
                    numberList.add(rs.getString(1));
                }
            } else {
                ConnectionResult = "Check Connection";
            }
        } catch (Exception ex) {
        }


        //Set adapter
        spinner.setAdapter(new ArrayAdapter<>(MachineID_Activity.this, android.R.layout.simple_spinner_dropdown_item, numberList));

        String EmailValue = getIntent().getStringExtra("emailvalue");

        machineNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MachineID_Activity.this, Dashboard_1Activity.class);
                startActivity(intent);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (i == 0) {
                    machineNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getApplicationContext(), "Please Select Machine ID", Toast.LENGTH_SHORT).show();
                        }
                    });
                    //Set empty value on Textview
                    textView.setText("");
                } else {
                    String sNumber = parent.getItemAtPosition(i).toString();
                    //Set selected value on TextView
                    textView.setText(sNumber);
                    machineNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            com.example.sd100testapp.DataHolder.getInstance().setData2(sNumber);
                            Intent intent = new Intent(MachineID_Activity.this, Dashboard_1Activity.class);
                            intent.putExtra("MachineIDValue", sNumber);
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    ;
}