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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Summary_Activity extends AppCompatActivity {
    Connection connect;
    String ConnectionResult = "";

    Button navigate13;
    TextView MCIDSUM, SKUIDSUM,ShiftValue;
    EditText BatchNoValue, CountPerStripValue, NetWtPerPouchValue, StripInspectedSum, StripCheckedSum, WtRangeMinValue, WtRangeStdValue, WtRangeMaxValue, StripWtValue ;
    Switch MfgAddressValue, PouchSizeValue, CuttingValue, PerforationValue, DelaminationValue, WrinkleValue, SilverLumpsValue, OffregistrationValue, LiningValue, PouchTearValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        //get intent from strip
        String BatchNo = getIntent().getStringExtra("BatchNo");
        String StripWt = getIntent().getStringExtra("StripWt");
        String CountPerStrip = getIntent().getStringExtra("CountPerStrip");
        String NetWtPerPouch = getIntent().getStringExtra("NetWtPerPouch");
        String WtRangeMin = getIntent().getStringExtra("WtRangeMinValue");
        String WtRangeStd = getIntent().getStringExtra("WtRangeStdValue");
        String WtRangeMax = getIntent().getStringExtra("WtRangeMaxValue");
        String Shift = getIntent().getStringExtra("StripShiftValue");
        String MachineIDValue = DataHolder.getInstance().getData2();
        String ProductCode = getIntent().getStringExtra("ProductCode");
        String ProductStockType = getIntent().getStringExtra("ProductStockType");
        Boolean SilverLumps = getIntent().getBooleanExtra("SilverLumps", false);
        Boolean MfgAddress = getIntent().getBooleanExtra("MfgAddress", false);
        Boolean PouchSize = getIntent().getBooleanExtra("PouchSize", false);
        Boolean Cutting = getIntent().getBooleanExtra("Cutting", false);
        Boolean Perforation = getIntent().getBooleanExtra("Perforation", false);
        Boolean Delamination = getIntent().getBooleanExtra("Delamination", false);
        Boolean Wrinkle = getIntent().getBooleanExtra("Wrinkle", false);
        Boolean PouchTear = getIntent().getBooleanExtra("PouchTear", false);
        Boolean Offregistration = getIntent().getBooleanExtra("Offregistration", false);
        Boolean Lining = getIntent().getBooleanExtra("Lining", false);


        MfgAddressValue = findViewById(R.id.stripaddresssum);
        BatchNoValue = findViewById(R.id.stripbatchnosum);
        OffregistrationValue = findViewById(R.id.stripOffregistrationsum);
        CountPerStripValue = findViewById(R.id.stripcountperstripsum);
        NetWtPerPouchValue = findViewById(R.id.stripnetwtperpouchsum);
        SilverLumpsValue = findViewById(R.id.stripsilverlumpsum);
        PouchSizeValue = findViewById(R.id.strippouchsizesum);
        CuttingValue = findViewById(R.id.stripcuttingsum);
        PerforationValue = findViewById(R.id.stripperforationsum);
        DelaminationValue = findViewById(R.id.stripdelaminationsum);
        WrinkleValue = findViewById(R.id.stripwrinklesum);
        PouchTearValue = findViewById(R.id.strippouchtearsum);
        StripWtValue = findViewById(R.id.stripstripwtsum);
        LiningValue = findViewById(R.id.stripLiningsum);
        StripInspectedSum = findViewById(R.id.stripinspectedsum);
        StripCheckedSum = findViewById(R.id.stripcheckedsum);
        MCIDSUM = findViewById(R.id.stripmcidsum);
        SKUIDSUM = findViewById(R.id.stripskuidsum);
        WtRangeMinValue = findViewById(R.id.stripminsum);
        WtRangeStdValue = findViewById(R.id.stripstdsum);
        WtRangeMaxValue = findViewById(R.id.stripmaxsum);
        ShiftValue = findViewById(R.id.stripshiftsum);


        BatchNoValue.setText(BatchNo);
        CountPerStripValue.setText(CountPerStrip);
        NetWtPerPouchValue.setText(NetWtPerPouch);
        WtRangeMinValue.setText(WtRangeMin);
        WtRangeStdValue.setText(WtRangeStd);
        WtRangeMaxValue.setText(WtRangeMax);
        StripWtValue.setText(StripWt);
        SilverLumpsValue.setChecked(SilverLumps);
        MfgAddressValue.setChecked(MfgAddress);
        PouchSizeValue.setChecked(PouchSize);
        CuttingValue.setChecked(Cutting);
        PerforationValue.setChecked(Perforation);
        DelaminationValue.setChecked(Delamination);
        WrinkleValue.setChecked(Wrinkle);
        PouchTearValue.setChecked(PouchTear);
        OffregistrationValue.setChecked(Offregistration);
        LiningValue.setChecked(Lining);
        ShiftValue.setText(Shift);
        StripInspectedSum.setText(DataHolder.getInstance().getData());
        MCIDSUM.setText(DataHolder.getInstance().getData2());
        String productCode = DatabaseCall.getData().FetchData("Select * from BatchExecution WHERE Status = 1", 2);
        String stockType = DatabaseCall.getData().FetchData("Select * from BatchExecution WHERE Status = 1", 3);
        String productVariant = DatabaseCall.getData().FetchData("Select * from ProductVariant WHERE ProductCode ='" + productCode + "' AND ProductStockType ='" + stockType + "'", 6);
        SKUIDSUM.setText(productVariant);
        navigate13 = findViewById(R.id.stripsummarynextbtn);

        navigate13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Summary_Activity.this, Overview_Activity.class);
                startActivity(intent);

                //StipQuality Data insert in SQL server

                try {
                    ConnectionHelper connectionHelper = new ConnectionHelper();
                    connect = connectionHelper.connectionclass();
                    String timeStamp = DatabaseCall.getData().FetchData("Select * from GetProdDate",1);
                    Log.e("here",timeStamp);
                    if (connect != null) {
                        String sqlinsert = "Insert into QAStrip values ('" + timeStamp + "','" + BatchNoValue.getText().toString() + "','" + CountPerStripValue.getText().toString() + "','" + NetWtPerPouchValue.getText().toString() + "','" + StripWtValue.getText().toString() + "','" + WtRangeMinValue.getText().toString() + "','" + WtRangeStdValue.getText().toString() + "','" + WtRangeMaxValue.getText().toString() + "','" + ShiftValue.getText().toString() +"','" + SilverLumps + "','" + MfgAddress + "','" + PouchSize + "','" + Cutting + "','" + Perforation + "','" + Delamination + "','" + Offregistration + "','" + Lining + "','" + Wrinkle + "','" + PouchTear + "','" + StripInspectedSum.getText().toString() + "','" + StripCheckedSum.getText().toString() + "','" + MachineIDValue +"','" + ProductCode +"','" + ProductStockType + "')";
                        Statement st = connect.createStatement();
                        ResultSet rs = st.executeQuery(sqlinsert);

                    } else {
                        ConnectionResult = "Check Connection";
                    }
                } catch (Exception ex) {
                    Log.e("Here", ex.toString());
                }

            }
        });
    }
}