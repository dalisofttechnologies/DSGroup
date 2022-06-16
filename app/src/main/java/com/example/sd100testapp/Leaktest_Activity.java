package com.example.sd100testapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Leaktest_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button navigate10;
    private String date;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;

    TextView MCID, SKUID;
    String Spinner2 = "", Spinner3 = "", LeakTestShift = "";
    Switch LeakTestTopSeal, LeakTestSideSeal, LeakTestDelamination, LeakTestWrinkle, LeakTestOffRegistration, LeakTestLining;
    EditText LeakTestDate, LeakTestTime, LeakTestTemp, LeakTestRh, LeakTestStripWt, LeakTestNoEmpty, LeakTestSampleSize, LeakTestNoLeak, LeakTestNoOfStrips, LeakTestStripWt1, LeakTestStripWt2, LeakTestStripWt3, LeakTestStripWt4, LeakTestBatchNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaktest);

        navigate10 = findViewById(R.id.leaktestnextbtn);
        LeakTestDate = findViewById(R.id.leaktestdate);
        LeakTestTime = findViewById(R.id.leaktesttime);
        LeakTestTemp = findViewById(R.id.leaktesttemp);
        LeakTestRh = findViewById(R.id.leaktestrh);
        LeakTestNoEmpty = findViewById(R.id.leaktestnoempty);
        LeakTestSampleSize = findViewById(R.id.leaktestsamplesize);
        LeakTestNoLeak = findViewById(R.id.leaktestnoofleakage);
        LeakTestTopSeal = findViewById(R.id.leaktesttopseal);
        LeakTestSideSeal = findViewById(R.id.leaktestsideseal);
        LeakTestDelamination = findViewById(R.id.leaktestdelamination);
        LeakTestWrinkle = findViewById(R.id.leaktestwrinkle);
        LeakTestOffRegistration = findViewById(R.id.leaktestoffregistration);
        LeakTestLining = findViewById(R.id.leaktestlining);
        LeakTestStripWt1 = findViewById(R.id.leakteststripwt1);
        LeakTestStripWt2 = findViewById(R.id.leakteststripwt2);
        LeakTestStripWt3 = findViewById(R.id.leakteststripwt3);
        LeakTestStripWt4 = findViewById(R.id.leakteststripwt4);
        SKUID = findViewById(R.id.leaktestskuid);
        MCID = findViewById(R.id.leaktestmcid);


        String machineidvalue = com.example.sd100testapp.DataHolder.getInstance().getData2();

        String timeStamp = DatabaseCall.getData().FetchData("Select * from GetProdDate", 1);
        String productCode = DatabaseCall.getData().FetchData("Select * from BatchExecution WHERE Status = 1 AND CONVERT(date, ProdDate) = '" + timeStamp + "' AND MachineSLN = '" + machineidvalue + "'", 2);
        String stockType = DatabaseCall.getData().FetchData("Select * from BatchExecution WHERE Status = 1 AND CONVERT(date, ProdDate) = '" + timeStamp + "' AND MachineSLN = '" + machineidvalue + "'", 3);
        String productVariant = "";
        SKUID.setText(productVariant);
        if (productCode.length() == 0 || stockType.length() == 0) {
            Toast.makeText(Leaktest_Activity.this, "Running Machine Not Planned", Toast.LENGTH_LONG).show();
        } else {
            productVariant = DatabaseCall.getData().FetchData("Select * from ProductVariant WHERE ProductCode ='" + productCode + "' AND ProductStockType ='" + stockType + "'", 6);
            SKUID.setText(productVariant);
        }
        MCID.setText("");
        LeakTestNoLeak.setTransformationMethod(null);

        navigate10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String LeakTestDateValue = LeakTestDate.getText().toString();
                String LeakTestTimeValue = LeakTestTime.getText().toString();
                String LeakTestTempValue = LeakTestTemp.getText().toString();
                String LeakTestRhValue = LeakTestRh.getText().toString();
                String LeakTestNoEmptyValue = LeakTestNoEmpty.getText().toString();
                String LeakTestSampleSizeValue = LeakTestSampleSize.getText().toString();
                String LeakTestNoLeakValue = LeakTestNoLeak.getText().toString();
                String LeakTestStripWt1Value = LeakTestStripWt1.getText().toString();
                String LeakTestStripWt2Value = LeakTestStripWt2.getText().toString();
                String LeakTestStripWt3Value = LeakTestStripWt3.getText().toString();
                String LeakTestStripWt4Value = LeakTestStripWt4.getText().toString();
                if (LeakTestTempValue.matches("") || LeakTestRhValue.matches("") || LeakTestNoEmptyValue.matches("") || LeakTestSampleSizeValue.matches("") || LeakTestNoLeakValue.matches("")) {
                    Toast.makeText(getApplicationContext(), "Please fill all entries", Toast.LENGTH_SHORT).show();
                    return;
                }
                Boolean LeakTestTopSealValue = LeakTestTopSeal.isChecked();
                Boolean LeakTestSideSealValue = LeakTestSideSeal.isChecked();
                Boolean LeakTestDelaminationValue = LeakTestDelamination.isChecked();
                Boolean LeakTestWrinkleValue = LeakTestWrinkle.isChecked();
                Boolean LeakTestOffRegistrationValue = LeakTestOffRegistration.isChecked();
                Boolean LeakTestLiningValue = LeakTestLining.isChecked();

                Intent intent = new Intent(Leaktest_Activity.this, LeakTestSummary_Activity.class);
                intent.putExtra("LeakTestDate", LeakTestDateValue);
                intent.putExtra("LeakTestTime", LeakTestTimeValue);
                intent.putExtra("LeakTestTemp", LeakTestTempValue);
                intent.putExtra("LeakTestRh", LeakTestRhValue);
                intent.putExtra("LeakTestNoEmpty", LeakTestNoEmptyValue);
                intent.putExtra("LeakTestSampleSize", LeakTestSampleSizeValue);
                intent.putExtra("LeakTestNoLeak", LeakTestNoLeakValue);
                intent.putExtra("LeakTestStripWt1", LeakTestStripWt1Value);
                intent.putExtra("LeakTestStripWt2", LeakTestStripWt2Value);
                intent.putExtra("LeakTestStripWt3", LeakTestStripWt3Value);
                intent.putExtra("LeakTestStripWt4", LeakTestStripWt4Value);
                intent.putExtra("LeakTestTopSeal", LeakTestTopSealValue);
                intent.putExtra("LeakTestSideSeal", LeakTestSideSealValue);
                intent.putExtra("LeakTestDelamination", LeakTestDelaminationValue);
                intent.putExtra("LeakTestWrinkle", LeakTestWrinkleValue);
                intent.putExtra("LeakTestOffRegistration", LeakTestOffRegistrationValue);
                intent.putExtra("LeakTestLining", LeakTestLiningValue);
                intent.putExtra("Spinner2", Spinner2);
                intent.putExtra("LeakTestShift", LeakTestShift);
                intent.putExtra("ProductCode", productCode);
                intent.putExtra("ProductStockType", stockType);

                startActivity(intent);
            }
        });

        //DropdownData
        String[] arr2 = DatabaseCall.getData().FetchArray("Select * from ProductVariant", 6);
        String[] arr = new String[60];
        for (int i = 0; i < arr2.length; i++) {
            arr[i] = arr2[i];
        }
        for (int i = arr2.length; i < 60; i++) {
            arr[i] = "";
        }
        //dropdown
        Spinner dropdown = findViewById(R.id.spinner1);
        ArrayAdapter myAdap = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, arr);
        dropdown.setAdapter(myAdap);

        if (productVariant != null) {
            int spinnerPosition = myAdap.getPosition(productVariant);
            dropdown.setSelection(spinnerPosition);
        }
        dropdown.setOnItemSelectedListener(this);

        //Machine ID Spinner
        Spinner dropdown2 = findViewById(R.id.spinner2);
        String[] items2 = new String[]{"150324162", "160724593", "160724594", "160724595", "160724596", "160724597"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        dropdown2.setAdapter(adapter2);
        dropdown2.setOnItemSelectedListener(this);


        //set Date Automatically
        DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        String inputText = DatabaseCall.getData().FetchData("Select * from GetProdDate", 1);
        Date date = null;
        try {
            date = inputFormat.parse(inputText);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputText = outputFormat.format(date);
        LeakTestDate.setText(outputText);

        //set Time Automatically
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        LeakTestTime.setText(currentTime);

        //shift dropdown
        Spinner dropdown4 = findViewById(R.id.leaktestshift);
        String[] items4 = new String[]{"A", "B", "C", "D", "N", "G"};
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items4);
        dropdown4.setAdapter(adapter4);
        dropdown4.setOnItemSelectedListener(this);

    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        if (parent.getId() == R.id.leaktestshift) {
            this.LeakTestShift = parent.getItemAtPosition(pos).toString();
        } else if (parent.getId() == R.id.spinner2) {
            this.Spinner2 = parent.getItemAtPosition(pos).toString();
        } else if (parent.getId() == R.id.spinner1) {
            String text = parent.getItemAtPosition(pos).toString();
            Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}