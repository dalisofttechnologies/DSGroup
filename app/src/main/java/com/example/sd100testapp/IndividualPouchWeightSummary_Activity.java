package com.example.sd100testapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class IndividualPouchWeightSummary_Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<SummaryTextModal> summaryTextModalArrayListSum;
    com.example.sd100testapp.SummaryTextAdapter summaryTextAdapter;

    Connection connect;
    String ConnectionResult = "";

    TextView IPWShiftValue, MCIDSUM, SKUIDSUM;
    Button IndividualnextbtnSum;
    EditText IPWDateValue, IPWTimeValue, IPWRollNoValue, IPWStripWtValue1, IPWStripWtValue2, IPWStripWtValue3, IPWStripWtValue4, IPWInspectedBy, IPWCheckedBy, IPWApprovedBy, IPWStripCountValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_pouch_weight_summary);


        recyclerView = findViewById(R.id.pouchRecyclerViewSum);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        summaryTextModalArrayListSum = new ArrayList<SummaryTextModal>();

        summaryTextAdapter = new com.example.sd100testapp.SummaryTextAdapter(this, summaryTextModalArrayListSum);
        recyclerView.setAdapter(summaryTextAdapter);


        IndividualnextbtnSum = findViewById(R.id.IndividualnextbtnSum);

        IPWDateValue = findViewById(R.id.IPWDateSum);
        IPWShiftValue = findViewById(R.id.IPWShiftSum);
        IPWTimeValue = findViewById(R.id.IPWTimeSum);
        IPWRollNoValue = findViewById(R.id.IPWRollNoSum);
        IPWStripWtValue1 = findViewById(R.id.IPWStripWtSum1);
        IPWStripWtValue2 = findViewById(R.id.IPWStripWtSum2);
        IPWStripWtValue3 = findViewById(R.id.IPWStripWtSum3);
        IPWStripWtValue4 = findViewById(R.id.IPWStripWtSum4);
        IPWInspectedBy = findViewById(R.id.ipwinspectedsum);
        IPWCheckedBy = findViewById(R.id.ipwcheckedsum);
        IPWStripCountValue = findViewById(R.id.IPWStripcountsum);
        MCIDSUM = findViewById(R.id.IPWmcidsum);
        SKUIDSUM = findViewById(R.id.IPWskuidsum);
        MCIDSUM.setText(com.example.sd100testapp.DataHolder.getInstance().getData2());
//        String productCode = com.example.sd100testapp.DatabaseCall.getData().FetchData("Select * from BatchExecution WHERE Status = 1", 2);
//        String stockType = com.example.sd100testapp.DatabaseCall.getData().FetchData("Select * from BatchExecution WHERE Status = 1", 3);
//        String productVariant = com.example.sd100testapp.DatabaseCall.getData().FetchData("Select * from ProductVariant WHERE ProductCode ='" + productCode + "' AND ProductStockType ='" + stockType + "'", 6);
        String SKUID = getIntent().getStringExtra("SKUID");
        SKUIDSUM.setText(SKUID);

        String IPWDate = getIntent().getStringExtra("IPWDate");
        String IPWShift = getIntent().getStringExtra("IPWShift");
        String IPWTime = getIntent().getStringExtra("IPWTime");
        String IPWRollNo = getIntent().getStringExtra("IPWRollNo");
        String IPWStripWt1 = getIntent().getStringExtra("IPWStripWt1");
        String IPWStripWt2 = getIntent().getStringExtra("IPWStripWt2");
        String IPWStripWt3 = getIntent().getStringExtra("IPWStripWt3");
        String IPWStripWt4 = getIntent().getStringExtra("IPWStripWt4");
        String MachineIDValue = com.example.sd100testapp.DataHolder.getInstance().getData2();
        String IPWStripCount = getIntent().getStringExtra("IPWStripCount");
        String ProductCode = getIntent().getStringExtra("ProductCode");
        String ProductStockType = getIntent().getStringExtra("ProductStockType");
        String Product = getIntent().getStringExtra("Product");

        Bundle args = getIntent().getBundleExtra("BUNDLE");
        ArrayList<Object> object = (ArrayList<Object>) args.getSerializable("ARRAYLIST");
//Log.e("There", String.valueOf(object));

        getData(object);

        IPWDateValue.setText(IPWDate);
        IPWShiftValue.setText(IPWShift);
        IPWTimeValue.setText(IPWTime);
        IPWRollNoValue.setText(IPWRollNo);
        IPWStripWtValue1.setText(IPWStripWt1);
        IPWStripWtValue2.setText(IPWStripWt2);
        IPWStripWtValue3.setText(IPWStripWt3);
        IPWStripWtValue4.setText(IPWStripWt4);
        IPWStripCountValue.setText(IPWStripCount);
        IPWInspectedBy.setText(com.example.sd100testapp.DataHolder.getInstance().getData());

        //making string of data that was input through intent of the pouches
        //initialising variables
        String test = "";
        String test2 = "','";
        String test3 = "%s";
        String test4 = test2 + test3;
        String testFirst = "%s";
        String testLast = test2 + "%s";
        //'" + IPWInspectedBy.getText().toString() + "'
        //adding variables to a final string
        for (int i = 0; i < 100; i++) {
            if (i == 0) {
                String temptest = String.format(testFirst, String.valueOf(object.get(i)));
                test = "'" + test + temptest;
            } else if (i < object.size()) {
                String temptest = String.format(test4, String.valueOf(object.get(i)));
                test = test + temptest;
            } else if (i == object.size() && object.size() > 99 && object.get(99) != null) {
                String temptest = String.format(testLast, String.valueOf(object.get(i)));
                test = test + temptest;
            } else if (i == 99) {
                String temptest2 = String.format(testLast, "");
                test = test + temptest2;
            } else {
                String temptest = String.format(test4, "");
                test = test + temptest;
            }
        }

        Log.e("Here", test);//','1','1','1','1
        String finalTest = test + "'";

        //converting date back to GetProdDate style
        DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        DateFormat inputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);

        String inputText = IPWDateValue.getText().toString();

        Date date = null;
        try {
            date = inputFormat.parse(inputText);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputText = outputFormat.format(date);
        IPWDateValue.setText(outputText);

        //click button to send data to server and move to overview screen
        IndividualnextbtnSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //StipQuality Data insert in SQL server

                try {
                    ConnectionHelper connectionHelper = new ConnectionHelper();
                    connect = connectionHelper.connectionclass();
                    if (connect != null) {
                        String datetime =  IPWDateValue.getText().toString()+" " + IPWTimeValue.getText().toString();
                        Log.e("Here",datetime);
                        String sqlinsert = "Insert into QAIPW values ('" + IPWDateValue.getText().toString() + "','" + datetime + "','" + IPWRollNoValue.getText().toString() + "','" + IPWStripCountValue.getText().toString() + "','" + IPWStripWtValue1.getText().toString() + "','" + IPWStripWtValue2.getText().toString() + "','" + IPWStripWtValue3.getText().toString() + "','" + IPWStripWtValue4.getText().toString() + "'," + finalTest + ",'" + IPWShiftValue.getText().toString() + "','" + IPWInspectedBy.getText().toString() + "','" + IPWCheckedBy.getText().toString() + "','" + MachineIDValue + "','" + Product + "','" + Product + "')";
                        Statement st = connect.createStatement();
                        ResultSet rs = st.executeQuery(sqlinsert);

                    } else {
                        ConnectionResult = "Check Connection";
                    }
                } catch (Exception ex) {
                    Log.e("Here", ex.toString());
                }

                Intent intent = new Intent(IndividualPouchWeightSummary_Activity.this, Overview_Activity.class);
                startActivity(intent);
            }

        });

    }

    private void getData(ArrayList<Object> object) {
        for (int i = 0; i < object.size(); i++) {
            int k = i + 1;
            SummaryTextModal summaryTextModal = new SummaryTextModal("Pouch Wt" + k, String.valueOf(object.get(i)));
            Log.e("There", String.valueOf(summaryTextModal));
            summaryTextModalArrayListSum.add(summaryTextModal);
        }
        summaryTextAdapter.notifyDataSetChanged();
    }
}