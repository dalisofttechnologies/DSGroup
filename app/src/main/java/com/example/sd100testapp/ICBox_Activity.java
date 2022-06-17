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

public class ICBox_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Connection connect;
    String ConnectionResult = "";

    int k = 0;
    Button navigate8;
    Spinner dropdown;
    String icshiftvalue = "";
    TextView MCID, SKUID;
    EditText iccountperstrip, icwtperfilledbox, icbatchno, icmrp, icwtperemptybox, icmin, icstd, icmax;
    Switch icmfgaddress, ictaping, iccutting, icwrinkle, icbubble, iccraking, ictrimming, iccreasing, ictextmatter, icpacking;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icbox);

        navigate8 = findViewById(R.id.icnextbtn);
        icwtperemptybox = findViewById(R.id.icwtperemptybox);
        iccountperstrip = findViewById(R.id.iccountperstrip);
        icwtperfilledbox = findViewById(R.id.icwtperfilledbox);
        icbatchno = findViewById(R.id.icbatchno);
        icmrp = findViewById(R.id.icmrp);
        icmfgaddress = findViewById(R.id.icmfgaddress);
        ictaping = findViewById(R.id.ictaping);
        ictextmatter = findViewById(R.id.ictextmatter);
        icpacking = findViewById(R.id.icpacking);
        SKUID = findViewById(R.id.icskuid);
        MCID = findViewById(R.id.icmcid);
        icmin = findViewById(R.id.icmin);
        icstd = findViewById(R.id.icstd);
        icmax = findViewById(R.id.icmax);

        String machineidvalue = com.example.sd100testapp.DataHolder.getInstance().getData2();
        MCID.setText(machineidvalue);
        String timeStamp = DatabaseCall.getData().FetchData("Select * from GetProdDate", 1);
        icbatchno.setText(com.example.sd100testapp.DatabaseCall.getData().FetchData("Select top (1)* from BatchExecution WHERE  MachineSLN = '" + machineidvalue + "' and order by Timestamp desc", 11));
        Log.e("Here", icbatchno.toString());
//        String productCode = com.example.sd100testapp.DatabaseCall.getData().FetchData("Select * from BatchExecution WHERE Status = 1 AND CONVERT(date, ProdDate) = '" + timeStamp + "' AND MachineSLN = '" + machineidvalue + "'", 2);
//        String stockType = com.example.sd100testapp.DatabaseCall.getData().FetchData("Select * from BatchExecution WHERE Status = 1 AND CONVERT(date, ProdDate) = '" + timeStamp + "' AND MachineSLN = '" + machineidvalue + "'", 3);
        String Product = DatabaseCall.getData().FetchData("select Product from ProductionPlan where Status =1  and ProdDate=dbo.getProdDateFunction(getdate()) and MachineSLN='"+machineidvalue+"'", 1);

        String productVariant = "";
        SKUID.setText(productVariant);
        if (Product.length() == 0) {
            Toast.makeText(ICBox_Activity.this, "Running Machine Not Planned", Toast.LENGTH_LONG).show();
        } else {
            productVariant = com.example.sd100testapp.DatabaseCall.getData().FetchData("select Description from ProductVariant where( ProductCode+'-'+ProductStockType)= '"+Product+"'", 1);
            SKUID.setText(productVariant);
        }
        icmin.setText(com.example.sd100testapp.DatabaseCall.getData().FetchData("Select TOP 1 * from QAIC where MachineSLN = '" + machineidvalue + "' ORDER BY Stamp DESC", 7));
        icstd.setText(com.example.sd100testapp.DatabaseCall.getData().FetchData("Select TOP 1 * from QAIC where MachineSLN = '" + machineidvalue + "' ORDER BY Stamp DESC", 8));
        icmax.setText(com.example.sd100testapp.DatabaseCall.getData().FetchData("Select TOP 1 * from QAIC where MachineSLN = '" + machineidvalue + "' ORDER BY Stamp DESC", 9));


        navigate8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String icountperstripvalue = iccountperstrip.getText().toString();
                String icwtperfilledboxvalue = icwtperfilledbox.getText().toString();
                String icbatchnovalue = icbatchno.getText().toString();
                String icmrpvalue = icmrp.getText().toString();
                String icwtperemptyboxvalue = icwtperemptybox.getText().toString();
                String icminvalue = icmin.getText().toString();
                String icstdvalue = icstd.getText().toString();
                String icmaxvalue = icmax.getText().toString();

                if (icountperstripvalue.matches("") || icwtperfilledboxvalue.matches("") || icbatchnovalue.matches("") || icmrpvalue.matches("") || icwtperemptyboxvalue.matches("")) {
                    Toast.makeText(getApplicationContext(), "Please fill all entries", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (k == 0) {
                    if (Float.parseFloat(icwtperfilledboxvalue) <= (Float.parseFloat(icminvalue) - 0.1) || Float.parseFloat(icwtperfilledboxvalue) >= (Float.parseFloat(icmaxvalue) + 0.1)) {
                        icwtperfilledbox.setError("Weight out of Range!");
                        k++;
                        return;
                    }
                }
                boolean icmfgaddressvalue = icmfgaddress.isChecked();
                boolean ictapingvalue = ictaping.isChecked();
                boolean ictextmattervalue = ictextmatter.isChecked();
                boolean icpackingvalue = icpacking.isChecked();
                Intent intent = new Intent(ICBox_Activity.this, com.example.sd100testapp.ICSummary_Activity.class);
                intent.putExtra("iccountperstrip", icountperstripvalue);
                intent.putExtra("icwtperfilledbox", icwtperfilledboxvalue);
                intent.putExtra("icbatchno", icbatchnovalue);
                intent.putExtra("icmrp", icmrpvalue);
                intent.putExtra("icmfgaddress", icmfgaddressvalue);
                intent.putExtra("ictaping", ictapingvalue);
                intent.putExtra("ictextmatter", ictextmattervalue);
                intent.putExtra("icpacking", icpackingvalue);
                intent.putExtra("icwtperemptybox", icwtperemptyboxvalue);
                intent.putExtra("icmin", icminvalue);
                intent.putExtra("icstd", icstdvalue);
                intent.putExtra("icmax", icmaxvalue);
                intent.putExtra("icshiftvalue", icshiftvalue);
                intent.putExtra("productCode", "productCode");
                intent.putExtra("productStockType", "stockType");
                intent.putExtra("product", Product);
                intent.putExtra("SKUID",SKUID.getText());
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
        dropdown = findViewById(R.id.spinneric);
        ArrayAdapter myAdap = new ArrayAdapter<>(ICBox_Activity.this, android.R.layout.simple_spinner_dropdown_item, arr);
        dropdown.setAdapter(myAdap);

        if (productVariant != null) {
            int spinnerPosition = myAdap.getPosition(productVariant);
            dropdown.setSelection(spinnerPosition);
        }
        dropdown.setOnItemSelectedListener(this);


        Spinner dropdown2 = findViewById(R.id.icshift);
        String[] items3 = new String[]{"A", "B", "C", "D", "N", "G"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items3);
        dropdown2.setAdapter(adapter3);
        dropdown2.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        if (parent.getId() == R.id.icshift) {
            this.icshiftvalue = parent.getItemAtPosition(pos).toString();
        } else if (parent.getId() == R.id.spinneric) {
            ArrayList<String> NewArr = new ArrayList<String>();
            try {
                ConnectionHelper connectionHelper = new ConnectionHelper();
                connect = connectionHelper.connectionclass();
                if (connect != null) {
                    String query = "Select * from ProductVariant";
                    Statement st = connect.createStatement();
                    ResultSet rs = st.executeQuery(query);
                    while (rs.next()) {
                        if (rs.getString(10) == null) {
                            NewArr.add(String.valueOf(0));
                        } else {
                            NewArr.add(rs.getString(10));
                        }
                    }
                } else {
                    ConnectionResult = "Check Connection";
                }
            } catch (Exception ex) {
                Log.e("Here", ex.toString());
            }
            if (pos == NewArr.size()) {
                NewArr.add("");
            }
            iccountperstrip.setText(NewArr.get(pos));
            if (parent.getId() == R.id.spinneric) {
                String text = parent.getItemAtPosition(pos).toString();
                Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}