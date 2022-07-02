package com.example.sd100testapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import net.sourceforge.jtds.jdbc.DateTime;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MCSummary_Activity extends AppCompatActivity {
    Connection connect;
    String ConnectionResult = "";

    Button MCSubmit;
    TextView MCShiftValue, MCIDSUM, SKUIDSUM;
    EditText MCBatchNoValue, MCICPerMCValue, MCNetWtValue, MCGrossWtValue, MCInspectedBy, MCCheckedBy, WtRangeMinValue,WtRangeStdValue,WtRangeMaxValue;
    Switch MCMfgAddressValue, MCCuttingValue, MCPerforationValue, MCWrinkleValue, MCBubbleValue, MCCrackingValue, MCTrimmingValue, MCCreasingValue, MCTapingValue, MCTextMatterValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcsummary);

        String MCBatchNo = getIntent().getStringExtra("MCBatchNo");
        String MCICPerMC = getIntent().getStringExtra("MCICPerMC");
        String MCNetWt = getIntent().getStringExtra("MCNetWt");
        String MCGrossWt = getIntent().getStringExtra("MCGrossWt");
        String MCShift = getIntent().getStringExtra("mcshiftvalue");
        String WtRangeMin = getIntent().getStringExtra("WtRangeMin");
        String WtRangeStd = getIntent().getStringExtra("WtRangeStd");
        String WtRangeMax = getIntent().getStringExtra("WtRangeMax");
        String ProductCode = getIntent().getStringExtra("ProductCode");
        String ProductStockType = getIntent().getStringExtra("ProductStockType");
        String Product = getIntent().getStringExtra("Product");
        String SKUID = getIntent().getStringExtra("SKUID");
        Boolean MCMfgAddress = getIntent().getBooleanExtra("MCMfgAddress", false);
        Boolean MCCutting = getIntent().getBooleanExtra("MCCutting", false);
        Boolean MCPerforation = getIntent().getBooleanExtra("MCPerforation", false);
        Boolean MCWrinkle = getIntent().getBooleanExtra("MCWrinkle", false);
        Boolean MCBubble = getIntent().getBooleanExtra("MCBubble", false);
        Boolean MCCracking = getIntent().getBooleanExtra("MCCracking", false);
        Boolean MCCreasing = getIntent().getBooleanExtra("MCCreasing", false);
        Boolean MCTaping = getIntent().getBooleanExtra("MCTaping", false);
        Boolean MCTextMatter = getIntent().getBooleanExtra("MCTextMatter", false);

        MCSubmit = findViewById(R.id.mcboxnextbtnsum);
        MCBatchNoValue = findViewById(R.id.mcboxbatchno);
        MCICPerMCValue = findViewById(R.id.mcboxicboxcount);
        MCNetWtValue = findViewById(R.id.mcboxnetwt);
        MCGrossWtValue = findViewById(R.id.mcboxgrosswt);
        MCShiftValue = findViewById(R.id.mcboxshift);
        MCMfgAddressValue = findViewById(R.id.mcboxmfgaddress);
        MCWrinkleValue = findViewById(R.id.mcboxwrinkle);
        MCBubbleValue = findViewById(R.id.mcboxbubble);
        MCCrackingValue = findViewById(R.id.mcboxcracking);
        MCCreasingValue = findViewById(R.id.mcboxcreasing);
        MCTapingValue = findViewById(R.id.mcboxtaping);
        MCTextMatterValue = findViewById(R.id.mcboxtextmatter);
        MCInspectedBy = findViewById(R.id.mcinspectedsum);
        MCCheckedBy = findViewById(R.id.mccheckedsum);
        WtRangeMinValue = findViewById(R.id.mcboxminsum);
        WtRangeStdValue = findViewById(R.id.mcboxstdsum);
        WtRangeMaxValue = findViewById(R.id.mcboxmaxsum);
        SKUIDSUM = findViewById(R.id.mcboxskuid);
        MCIDSUM = findViewById(R.id.mcboxmcid);
        MCIDSUM.setText(com.example.sd100testapp.DataHolder.getInstance().getData2());
//        String productCode = com.example.sd100testapp.DatabaseCall.getData().FetchData("Select * from BatchExecution WHERE Status = 1", 2);
//        String stockType = com.example.sd100testapp.DatabaseCall.getData().FetchData("Select * from BatchExecution WHERE Status = 1", 3);
//        String productVariant = com.example.sd100testapp.DatabaseCall.getData().FetchData("Select * from ProductVariant WHERE ProductCode ='" + productCode + "' AND ProductStockType ='" + stockType + "'", 6);
        SKUIDSUM.setText(SKUID);

        MCBatchNoValue.setText(MCBatchNo);
        MCICPerMCValue.setText(MCICPerMC);
        MCNetWtValue.setText(MCNetWt);
        MCGrossWtValue.setText(MCGrossWt);
        MCShiftValue.setText(MCShift);
        WtRangeMinValue.setText(WtRangeMin);
        WtRangeStdValue.setText(WtRangeStd);
        WtRangeMaxValue.setText(WtRangeMax);
        MCMfgAddressValue.setChecked(MCMfgAddress);
        MCWrinkleValue.setChecked(MCWrinkle);
        MCBubbleValue.setChecked(MCBubble);
        MCCrackingValue.setChecked(MCCracking);
        MCCreasingValue.setChecked(MCCreasing);
        MCTapingValue.setChecked(MCTaping);
        MCTextMatterValue.setChecked(MCTextMatter);
        MCInspectedBy.setText(com.example.sd100testapp.DataHolder.getInstance().getData());
        String MachineIdValue = com.example.sd100testapp.DataHolder.getInstance().getData2();

        MCSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //set Time Automatically
                String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

                //IcBox Data insert in SQL server
                try {
                    ConnectionHelper connectionHelper = new ConnectionHelper();
                    connect = connectionHelper.connectionclass();
                    String timeStamp = DatabaseCall.getData().FetchData("Select * from GetProdDate",1);
                    if (connect != null) {
                        Log.e("Here", "here");
                        String sqlinsert = "Insert into QAMC values ('" + timeStamp + "','" + MCBatchNoValue.getText().toString() + "','" + MCICPerMCValue.getText().toString() + "','" + MCNetWtValue.getText().toString() + "','" + MCGrossWtValue.getText().toString() + "','" + MCShiftValue.getText().toString() +"','" + WtRangeMinValue.getText().toString() +"','" + WtRangeStdValue.getText().toString() +"','" + WtRangeMaxValue.getText().toString() + "','" + MCMfgAddress + "','" + MCWrinkle + "','" + MCBubble + "','" + MCCracking +  "','" + MCCreasing + "','" + MCTaping + "','" + MCTextMatter + "','" + MCInspectedBy.getText().toString() + "','" + MCCheckedBy.getText().toString() +  "','" + MachineIdValue +"','" + Product +"','" + Product +"','" + currentTime + "')";
                        Statement st = connect.createStatement();
                        ResultSet rs = st.executeQuery(sqlinsert);
                        Log.e("Here", "Success");
                    } else {
                        ConnectionResult = "Check Connection";
                    }
                } catch (Exception ex) {
                    Log.e("Here", ex.toString());
                }

                Intent intent = new Intent(MCSummary_Activity.this, com.example.sd100testapp.Overview_Activity.class);
                startActivity(intent);

            }
        });

    }
}

