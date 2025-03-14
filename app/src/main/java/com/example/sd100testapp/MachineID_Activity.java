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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
        String timeStamp2 = DatabaseCall.getData().FetchData("Select * from GetProdDate", 1);

        //Getting previous date also now
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String outputText = "";
        Date date = null;

        try {
            date = inputFormat.parse(timeStamp2);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DATE, -1);
            Date currentDatePlusOne = c.getTime();
            outputText = inputFormat.format(currentDatePlusOne);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        //Get the data from Sql Server to be filled in the ArrayList
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();


            if (connect != null) {
                String timeStamp = DatabaseCall.getData().FetchData("Select * from GetProdDate", 1);
                String query = "SELECT DISTINCT MachineSLN FROM BatchExecution WHERE ProdDate = '" + timeStamp + "' OR ProdDate = '" + outputText + "'";
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