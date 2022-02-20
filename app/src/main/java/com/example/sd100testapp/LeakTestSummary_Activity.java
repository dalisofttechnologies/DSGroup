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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LeakTestSummary_Activity extends AppCompatActivity {

    Button Leaktestsubmit;
    Connection connect;
    String ConnectionResult = "";
    TextView SKUID, MCID;
    EditText LeakTestDateValue, LeakTestShiftValue, LeakTestTimeValue, LeakTestTempValue, LeakTestRhValue, LeakTestStripWtValue, LeakTestNoEmptyValue, LeakTestSampleSizeValue, LeakTestNoLeakValue, LeakTestTopSealRValue, LeakTestSideSealRValue;
    EditText Spinner2Value, Spinner3Value, LeakTestInspectedBy, LeakTestCheckedBy, LeakTestNoOfStripsValue, LeakTestStripWt1Value, LeakTestStripWt2Value, LeakTestStripWt3Value, LeakTestStripWt4Value, LeakTestBatchNoValue;
    Switch LeakTestTopSealValue, LeakTestSideSealValue, LeakTestDelaminationValue, LeakTestWrinkleValue, LeakTestOffRegistrationValue, LeakTestLiningValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak_test_summary);

        Leaktestsubmit = findViewById(R.id.leaktestnextbtnsum);
        LeakTestBatchNoValue = findViewById(R.id.leaktestbatchnosum);
        LeakTestDateValue = findViewById(R.id.leaktestdatesum);
        LeakTestShiftValue = findViewById(R.id.leaktestshiftsum);
        LeakTestTimeValue = findViewById(R.id.leaktesttimesum);
        LeakTestTempValue = findViewById(R.id.leaktesttempsum);
        LeakTestRhValue = findViewById(R.id.leaktestrhsum);
        LeakTestNoEmptyValue = findViewById(R.id.leaktestnoemptysum);
        LeakTestSampleSizeValue = findViewById(R.id.leaktestsamplesizesum);
        LeakTestNoLeakValue = findViewById(R.id.leaktestnoofleakagesum);
        LeakTestTopSealValue = findViewById(R.id.leaktesttopsealsum);
        LeakTestSideSealValue = findViewById(R.id.leaktestsidesealsum);
        LeakTestDelaminationValue = findViewById(R.id.leaktestdelaminationsum);
        LeakTestWrinkleValue = findViewById(R.id.leaktestwrinklesum);
        LeakTestOffRegistrationValue = findViewById(R.id.leaktestoffregistrationsum);
        LeakTestLiningValue = findViewById(R.id.leaktesliningsum);
        Spinner2Value = findViewById(R.id.spinner2sum);
        LeakTestInspectedBy = findViewById(R.id.leaktestinspectedsum);
        LeakTestCheckedBy = findViewById(R.id.leaktestcheckedsum);
        LeakTestStripWt1Value = findViewById(R.id.leakteststripwt1sum);
        LeakTestStripWt2Value = findViewById(R.id.leakteststripwt2sum);
        LeakTestStripWt3Value = findViewById(R.id.leakteststripwt3sum);
        LeakTestStripWt4Value = findViewById(R.id.leakteststripwt4sum);
        MCID = findViewById(R.id.leaktestmcidsum);
        String machineidvalue = com.example.sd100testapp.DataHolder.getInstance().getData2();
        MCID.setText("");
        SKUID = findViewById(R.id.leaktestskuidsum);
        String productCode = com.example.sd100testapp.DatabaseCall.getData().FetchData("Select * from BatchExecution WHERE Status = 1", 2);
        String stockType = com.example.sd100testapp.DatabaseCall.getData().FetchData("Select * from BatchExecution WHERE Status = 1", 3);
        String productVariant = com.example.sd100testapp.DatabaseCall.getData().FetchData("Select * from ProductVariant WHERE ProductCode ='" + productCode + "' AND ProductStockType ='" + stockType + "'", 6);
        SKUID.setText(productVariant);

        String LeakTestBatchNo = getIntent().getStringExtra("LeakTestBatchNo");
        String LeakTestDate = getIntent().getStringExtra("LeakTestDate");
        String LeakTestShift = getIntent().getStringExtra("LeakTestShift");
        String LeakTestTime = getIntent().getStringExtra("LeakTestTime");
        String LeakTestTemp = getIntent().getStringExtra("LeakTestTemp");
        String LeakTestRh = getIntent().getStringExtra("LeakTestRh");
        String LeakTestStripWt = getIntent().getStringExtra("LeakTestStripWt");
        String LeakTestNoEmpty = getIntent().getStringExtra("LeakTestNoEmpty");
        String LeakTestSampleSize = getIntent().getStringExtra("LeakTestSampleSize");
        String LeakTestNoLeak = getIntent().getStringExtra("LeakTestNoLeak");
        String LeakTestNoOfStrips = getIntent().getStringExtra("LeakTestNoOfStrips");
        String LeakTestStripWt1 = getIntent().getStringExtra("LeakTestStripWt1");
        String LeakTestStripWt2 = getIntent().getStringExtra("LeakTestStripWt2");
        String LeakTestStripWt3 = getIntent().getStringExtra("LeakTestStripWt3");
        String LeakTestStripWt4 = getIntent().getStringExtra("LeakTestStripWt4");
        Boolean LeakTestTopSeal = getIntent().getBooleanExtra("LeakTestTopSeal", false);
        Boolean LeakTestSideSeal = getIntent().getBooleanExtra("LeakTestSideSeal", false);
        Boolean LeakTestDelamination = getIntent().getBooleanExtra("LeakTestDelamination", false);
        Boolean LeakTestWrinkle = getIntent().getBooleanExtra("LeakTestWrinkle", false);
        Boolean LeakTestOffRegistration = getIntent().getBooleanExtra("LeakTestOffRegistration", false);
        Boolean LeakTestLining = getIntent().getBooleanExtra("LeakTestLining", false);
        String Spinner2 = getIntent().getStringExtra("Spinner2");
        String ProductCode = getIntent().getStringExtra("ProductCode");
        String ProductStockType = getIntent().getStringExtra("ProductStockType");


        LeakTestBatchNoValue.setText(LeakTestBatchNo);
        LeakTestDateValue.setText(LeakTestDate);
        LeakTestShiftValue.setText(LeakTestShift);
        LeakTestTimeValue.setText(LeakTestTime);
        LeakTestTempValue.setText(LeakTestTemp);
        LeakTestRhValue.setText(LeakTestRh);
        LeakTestNoEmptyValue.setText(LeakTestNoEmpty);
        LeakTestSampleSizeValue.setText(LeakTestSampleSize);
        LeakTestNoLeakValue.setText(LeakTestNoLeak);
        LeakTestStripWt1Value.setText(LeakTestStripWt1);
        LeakTestStripWt2Value.setText(LeakTestStripWt2);
        LeakTestStripWt3Value.setText(LeakTestStripWt3);
        LeakTestStripWt4Value.setText(LeakTestStripWt4);
        LeakTestTopSealValue.setChecked(LeakTestTopSeal);
        LeakTestSideSealValue.setChecked(LeakTestSideSeal);
        LeakTestDelaminationValue.setChecked(LeakTestDelamination);
        LeakTestWrinkleValue.setChecked(LeakTestWrinkle);
        LeakTestOffRegistrationValue.setChecked(LeakTestOffRegistration);
        LeakTestLiningValue.setChecked(LeakTestLining);
        Spinner2Value.setText(Spinner2);
        LeakTestInspectedBy.setText(com.example.sd100testapp.DataHolder.getInstance().getData());

        //converting Date back to GetProdDate style
        DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        DateFormat inputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);

        String inputText = LeakTestDateValue.getText().toString();
        Date date = null;
        try {
            date = inputFormat.parse(inputText);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputText = outputFormat.format(date);
        LeakTestDateValue.setText(outputText);


        Leaktestsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LeakTestSummary_Activity.this, com.example.sd100testapp.Overview_Activity.class);
                startActivity(intent);
                //StipQuality Data insert in SQL server

                try {
                    com.example.sd100testapp.ConnectionHelper connectionHelper = new com.example.sd100testapp.ConnectionHelper();
                    connect = connectionHelper.connectionclass();
                    if (connect != null) {
                        String datetime = LeakTestDateValue.getText().toString() + " " + LeakTestTimeValue.getText().toString();
                        String sqlinsert = "Insert into QALeakTest values ('" + LeakTestDateValue.getText().toString() + "','" + datetime + "','" + LeakTestTempValue.getText().toString() + "','" + LeakTestRhValue.getText().toString() + "','" + LeakTestStripWt1Value.getText().toString() + "','" + LeakTestStripWt2Value.getText().toString() + "','" + LeakTestStripWt3Value.getText().toString() + "','" + LeakTestStripWt4Value.getText().toString() + "','" + LeakTestNoEmptyValue.getText().toString() + "','" + LeakTestSampleSizeValue.getText().toString() + "','" + LeakTestNoLeakValue.getText().toString() + "','" + LeakTestShiftValue.getText().toString() + "','" + Spinner2 + "','" + LeakTestTopSeal + "','" + LeakTestSideSeal + "','" + LeakTestDelamination + "','" + LeakTestOffRegistration + "','" + LeakTestLining + "','" + LeakTestWrinkle + "','" + LeakTestInspectedBy.getText().toString() + "','" + LeakTestCheckedBy.getText().toString() +"','" + ProductCode +"','" + ProductStockType + "')";
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