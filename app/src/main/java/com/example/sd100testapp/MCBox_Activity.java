package com.example.sd100testapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MCBox_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Connection connect;
    String ConnectionResult = "";

    int k = 0;
    Button navigate11;
    TextView MCID, SKUID;
    String mcshiftvalue = "";
    EditText MCBatchNo, MCICPerMC, MCNetWt, MCGrossWt, WtRangeMin, WtRangeStd, WtRangeMax;
    Switch MCMfgAddress, MCCutting, MCPerforation, MCWrinkle, MCBubble, MCCracking, MCTrimming, MCCreasing, MCTaping, MCTextMatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcbox);

        navigate11 = findViewById(R.id.mcboxnextbtn);
        MCBatchNo = findViewById(R.id.mcbatchno);
        MCICPerMC = findViewById(R.id.mcicboxcount);
        MCNetWt = findViewById(R.id.mcnetwt);
        MCGrossWt = findViewById(R.id.mcgrosswt);
        MCMfgAddress = findViewById(R.id.mcmfgaddress);
        MCWrinkle = findViewById(R.id.mcwrinkle);
        MCBubble = findViewById(R.id.mcbubble);
        MCCracking = findViewById(R.id.mccracking);
        MCCreasing = findViewById(R.id.mccreasing);
        MCTaping = findViewById(R.id.mctaping);
        MCTextMatter = findViewById(R.id.mctextmatter);
        WtRangeMin = findViewById(R.id.mcmin);
        WtRangeStd = findViewById(R.id.mcstd);
        WtRangeMax = findViewById(R.id.mcmax);
        MCID = findViewById(R.id.mcmcid);
        SKUID = findViewById(R.id.mcskuid);
        String machineidvalue = com.example.sd100testapp.DataHolder.getInstance().getData2();
        MCID.setText(machineidvalue);



        //Select TOP(1)* from BatchExecution WHERE ProdDate ='2022-03-04' and MachineSLN='160724595' order by Timestamp desc
        String timeStamp = DatabaseCall.getData().FetchData("Select * from GetProdDate", 1);
//        String productCode = com.example.sd100testapp.DatabaseCall.getData().FetchData("Select * from BatchExecution WHERE Status = 1 AND CONVERT(date, ProdDate) = '" + timeStamp + "'AND MachineSLN = '"+ machineidvalue +"'", 2);
//        String stockType = com.example.sd100testapp.DatabaseCall.getData().FetchData("Select * from BatchExecution WHERE Status = 1 AND CONVERT(date, ProdDate) = '" + timeStamp + "'AND MachineSLN = '"+ machineidvalue +"'", 3);
        String Product = DatabaseCall.getData().FetchData("select Product from ProductionPlan where Status =1  and ProdDate=dbo.getProdDateFunction(getdate()) and MachineSLN='"+machineidvalue+"'", 1);

        String productVariant = "";
        SKUID.setText(productVariant);
        if (Product.length() == 0) {
            Toast.makeText(MCBox_Activity.this, "Running Machine Not Planned", Toast.LENGTH_LONG).show();
        } else {
            productVariant = com.example.sd100testapp.DatabaseCall.getData().FetchData("select Description from ProductVariant where( ProductCode+'-'+ProductStockType)= '"+Product+"'", 1);
            SKUID.setText(productVariant);
        }
        MCBatchNo.setText(com.example.sd100testapp.DatabaseCall.getData().FetchData("Select top (1)* from BatchExecution WHERE  MachineSLN = '" + machineidvalue + "' order by Timestamp desc", 11));
        WtRangeMin.setText(com.example.sd100testapp.DatabaseCall.getData().FetchData("Select TOP 1 * from QAMC where MachineSLN = '" + machineidvalue + "'  ORDER BY Stamp DESC", 7));
        WtRangeStd.setText(com.example.sd100testapp.DatabaseCall.getData().FetchData("Select TOP 1 * from QAMC where MachineSLN = '"+machineidvalue+"'  ORDER BY Stamp DESC", 8));
        WtRangeMax.setText(com.example.sd100testapp.DatabaseCall.getData().FetchData("Select TOP 1 * from QAMC where MachineSLN = '"+machineidvalue+"'  ORDER BY Stamp DESC", 9));

        navigate11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String MCBatchNoValue = MCBatchNo.getText().toString();
                String MCICPerMCValue = MCICPerMC.getText().toString();
                String MCNetWtValue = MCNetWt.getText().toString();
                String MCGrossWtValue = MCGrossWt.getText().toString();
                String WtRangeMinValue = WtRangeMin.getText().toString();
                String WtRangeStdValue = WtRangeStd.getText().toString();
                String WtRangeMaxValue = WtRangeMax.getText().toString();

                boolean MCMfgAddressValue = MCMfgAddress.isChecked();
                boolean MCWrinkleValue = MCWrinkle.isChecked();
                boolean MCBubbleValue = MCBubble.isChecked();
                boolean MCCrackingValue = MCCracking.isChecked();
                boolean MCCreasingValue = MCCreasing.isChecked();
                boolean MCTapingValue = MCTaping.isChecked();
                boolean MCTextMatterValue = MCTextMatter.isChecked();

                if (MCBatchNoValue.matches("") || MCICPerMCValue.matches("") || MCNetWtValue.matches("") || MCGrossWtValue.matches("")) {
                    Toast.makeText(getApplicationContext(), "Please fill all entries", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (k == 0) {
                    if (Float.parseFloat(MCGrossWtValue) <= (Float.parseFloat(WtRangeMinValue) - 0.1) || Float.parseFloat(MCGrossWtValue) >= (Float.parseFloat(WtRangeMaxValue) + 0.1)) {
                        MCGrossWt.setError("Weight out of Range!");
                            k++;
                        return;
                    }
                }

                Intent intent = new Intent(MCBox_Activity.this, com.example.sd100testapp.MCSummary_Activity.class);
                intent.putExtra("MCBatchNo", MCBatchNoValue);
                intent.putExtra("MCICPerMC", MCICPerMCValue);
                intent.putExtra("MCNetWt", MCNetWtValue);
                intent.putExtra("MCGrossWt", MCGrossWtValue);
                intent.putExtra("MCMfgAddress", MCMfgAddressValue);
                intent.putExtra("MCWrinkle", MCWrinkleValue);
                intent.putExtra("MCBubble", MCBubbleValue);
                intent.putExtra("MCCracking", MCCrackingValue);
                intent.putExtra("MCCreasing", MCCreasingValue);
                intent.putExtra("MCTaping", MCTapingValue);
                intent.putExtra("MCTextMatter", MCTextMatterValue);
                intent.putExtra("WtRangeMin", WtRangeMinValue);
                intent.putExtra("WtRangeStd", WtRangeStdValue);
                intent.putExtra("WtRangeMax", WtRangeMaxValue);
                intent.putExtra("mcshiftvalue", mcshiftvalue);
                intent.putExtra("ProductCode", "productCode");
                intent.putExtra("ProductStockType", "stockType");
                intent.putExtra("Product", Product);
                intent.putExtra("SKUID", SKUID.getText());
                startActivity(intent);
            }
        });

        //DropdownData
        String[] arr2 = com.example.sd100testapp.DatabaseCall.getData().FetchArray("Select * from ProductVariant", 6);
        String[] arr = new String[60];
        for (int i = 0; i < arr2.length; i++) {
            arr[i] = arr2[i];
        }
        for (int i = arr2.length; i < 60; i++) {
            arr[i] = "";
        }
        //dropdown
        Spinner dropdown = findViewById(R.id.mcboxspinner);
        ArrayAdapter myAdap = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, arr);
        dropdown.setAdapter(myAdap);

        if (productVariant != null) {
            int spinnerPosition = myAdap.getPosition(productVariant);
            dropdown.setSelection(spinnerPosition);
        }
        dropdown.setOnItemSelectedListener(this);

        Spinner dropdown2 = findViewById(R.id.mcshift);
        String[] items3 = new String[]{"A", "B", "C", "D", "N", "G"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items3);
        dropdown2.setAdapter(adapter3);
        dropdown2.setOnItemSelectedListener(this);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        if (parent.getId() == R.id.mcshift) {
            this.mcshiftvalue = parent.getItemAtPosition(pos).toString();
        } else if (parent.getId() == R.id.mcboxspinner) {
            String text = parent.getItemAtPosition(pos).toString();
            Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();

            ArrayList<String> NewArr = new ArrayList<String>();
            ArrayList<String> NewArrNetwtdeclaration = new ArrayList<String>();
            try {
                ConnectionHelper connectionHelper = new ConnectionHelper();
                connect = connectionHelper.connectionclass();

                if (connect != null) {
                    String query = "Select * from ProductVariant";
                    Statement st = connect.createStatement();
                    ResultSet rs = st.executeQuery(query);
                    while (rs.next()) {
                        NewArr.add(rs.getString(12));
                    }
                } else {
                    ConnectionResult = "Check Connection";
                }
            } catch (Exception ex) {
                Log.e("Here", ex.toString());

            }
            if(pos==NewArr.size()){
                NewArr.add("");
            }
            MCICPerMC.setText(NewArr.get(pos));

            try {
                ConnectionHelper connectionHelper = new ConnectionHelper();
                connect = connectionHelper.connectionclass();

                if (connect != null) {
                    String query = "Select * from ProductVariant";
                    Statement st = connect.createStatement();
                    ResultSet rs = st.executeQuery(query);
                    while (rs.next()) {
                        NewArrNetwtdeclaration.add(rs.getString(14));
                    }
                } else {
                    ConnectionResult = "Check Connection";
                }
            } catch (Exception ex) {
                Log.e("Here", ex.toString());

            }
            if(pos==NewArrNetwtdeclaration.size()){
                NewArrNetwtdeclaration.add("");
            }
            MCNetWt.setText(NewArrNetwtdeclaration.get(pos));
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}