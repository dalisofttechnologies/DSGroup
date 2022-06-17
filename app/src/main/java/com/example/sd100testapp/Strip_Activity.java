package com.example.sd100testapp;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class Strip_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Connection connect;
    String ConnectionResult = "";

    int k = 0;
    String[] arr2;
    Spinner dropdown;
    Button navigate14;
    ArrayAdapter myAdap;
    String stripshiftvalue = "";
    EditText BatchNo, CountPerStrip, NetWtPerPouch, WtRangeMin, WtRangeStd, WtRangeMax, StripWt;
    TextView BatchNoHeading, CountPerStripHeading, NetWtPerPouchHeading, MCID, SKUID;
    Switch MfgAddress, PouchSize, Cutting, Perforation, Delamination, Wrinkle, SilverLumps, Offregistration, Lining, PouchTear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strip);

        navigate14 = findViewById(R.id.stripnextbtn);
        MfgAddress = findViewById(R.id.stripaddress);
        BatchNo = findViewById(R.id.stripbatchno);
        CountPerStrip = findViewById(R.id.stripcountperstrip);
        NetWtPerPouch = findViewById(R.id.stripnetwtperpouch);
        BatchNoHeading = findViewById(R.id.stripbatchnoheading);
        CountPerStripHeading = findViewById(R.id.stripcountperstripheading);
        NetWtPerPouchHeading = findViewById(R.id.stripnetwtperpouchheading);
        SilverLumps = findViewById(R.id.stripsilverlump);
        PouchSize = findViewById(R.id.strippouchsize);
        Cutting = findViewById(R.id.stripcutting);
        Perforation = findViewById(R.id.stripperforation);
        Delamination = findViewById(R.id.stripdelamination);
        Offregistration = findViewById(R.id.stripOffregistration);
        Lining = findViewById(R.id.stripLining);
        Wrinkle = findViewById(R.id.stripwrinkle);
        PouchTear = findViewById(R.id.strippouchtear);
        StripWt = findViewById(R.id.stripstripwt);
        SKUID = findViewById(R.id.stripskuid);
        WtRangeMin = findViewById(R.id.stripmin);
        WtRangeStd = findViewById(R.id.stripstd);
        WtRangeMax = findViewById(R.id.stripmax);
        MCID = findViewById(R.id.stripmcid);

        String machineidvalue = com.example.sd100testapp.DataHolder.getInstance().getData2();
        MCID.setText(machineidvalue);
        String timeStamp = DatabaseCall.getData().FetchData("Select * from GetProdDate", 1);
//        String productCode = com.example.sd100testapp.DatabaseCall.getData().FetchData("Select * from BatchExecution WHERE Status = 1 AND CONVERT(date, ProdDate) = '" + timeStamp + "' AND MachineSLN = '" + machineidvalue + "'", 2);
//        String stockType = com.example.sd100testapp.DatabaseCall.getData().FetchData("Select * from BatchExecution WHERE Status = 1 AND CONVERT(date, ProdDate) = '" + timeStamp + "' AND MachineSLN = '" + machineidvalue + "'", 3);
        String Product = DatabaseCall.getData().FetchData("select Product from ProductionPlan where Status =1  and ProdDate=dbo.getProdDateFunction(getdate()) and MachineSLN='"+machineidvalue+"'", 1);
        String productVariant = "";
        SKUID.setText(productVariant);
        if (Product.length() == 0) {
            Toast.makeText(Strip_Activity.this, "Running Machine Not Planned", Toast.LENGTH_LONG).show();
            SharedPreferences pref = getApplication().getSharedPreferences("MyPref", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("Abnormality", String.valueOf(1)); // Storing string
            editor.apply();
        } else {
            productVariant = com.example.sd100testapp.DatabaseCall.getData().FetchData("select Description from ProductVariant where( ProductCode+'-'+ProductStockType)= '"+Product+"'", 1);
            SKUID.setText(productVariant);
        }

        //   Select top (1)* from BatchExecution WHERE ProdDate = CAST('2022-03-04' as date) AND MachineSLN = '160724596' order by Timestamp desc
        BatchNo.setText(com.example.sd100testapp.DatabaseCall.getData().FetchData("Select top (1)* from BatchExecution WHERE AND MachineSLN = '" + machineidvalue + "' order by Timestamp desc", 8));
        WtRangeMin.setText(com.example.sd100testapp.DatabaseCall.getData().FetchData("Select TOP 1 * from QAStrip Where MachineSLN = '" + machineidvalue + "' ORDER BY Stamp DESC", 6));
        WtRangeStd.setText(com.example.sd100testapp.DatabaseCall.getData().FetchData("Select TOP 1 * from QAStrip Where MachineSLN = '" + machineidvalue + "' ORDER BY Stamp DESC", 7));
        WtRangeMax.setText(com.example.sd100testapp.DatabaseCall.getData().FetchData("Select TOP 1 * from QAStrip Where MachineSLN = '" + machineidvalue + "' ORDER BY Stamp DESC", 8));


        navigate14.setOnClickListener(view -> {

            String BatchNoValue = BatchNo.getText().toString();
            String CountPerStripValue = CountPerStrip.getText().toString();
            String NetWtPerPouchValue = NetWtPerPouch.getText().toString();
            String StripWtValue = StripWt.getText().toString();
            String WtRangeMinValue = WtRangeMin.getText().toString();
            String WtRangeStdValue = WtRangeStd.getText().toString();
            String WtRangeMaxValue = WtRangeMax.getText().toString();
            String EmailValue = getIntent().getStringExtra("EmailValue");
            String MachineIDValue = getIntent().getStringExtra("MachineIdValue");

            boolean SilverLumpsValue = SilverLumps.isChecked();
            boolean MfgAddressValue = MfgAddress.isChecked();
            boolean PouchSizeValue = PouchSize.isChecked();
            boolean CuttingValue = Cutting.isChecked();
            boolean PerforationValue = Perforation.isChecked();
            boolean OffregistrationValue = Offregistration.isChecked();
            boolean LiningValue = Lining.isChecked();
            boolean DelaminationValue = Delamination.isChecked();
            boolean WrinkleValue = Wrinkle.isChecked();
            boolean PouchTearValue = PouchTear.isChecked();

            if (BatchNoValue.matches("") || CountPerStripValue.matches("") || NetWtPerPouchValue.matches("") || StripWtValue.matches("") || WtRangeMinValue.matches("") || WtRangeMaxValue.matches("")) {
                Toast.makeText(getApplicationContext(), "Please fill all entries", Toast.LENGTH_SHORT).show();
                return;
            }

            if (k == 0) {
                if (Float.parseFloat(StripWtValue) <= (Float.parseFloat(WtRangeMinValue) - 0.1) || Float.parseFloat(StripWtValue) >= (Float.parseFloat(WtRangeMaxValue) + 0.1)) {
                    StripWt.setError("Weight out of Range!");
                    k++;
                    return;
                }
            }

            Intent intent = new Intent(Strip_Activity.this, com.example.sd100testapp.Summary_Activity.class);
            intent.putExtra("MfgAddress", MfgAddressValue);
            intent.putExtra("PouchSize", PouchSizeValue);
            intent.putExtra("Cutting", CuttingValue);
            intent.putExtra("Perforation", PerforationValue);
            intent.putExtra("Delamination", DelaminationValue);
            intent.putExtra("PouchTear", PouchTearValue);
            intent.putExtra("Wrinkle", WrinkleValue);
            intent.putExtra("Offregistration", OffregistrationValue);
            intent.putExtra("Lining", LiningValue);
            intent.putExtra("BatchNo", BatchNoValue);
            intent.putExtra("CountPerStrip", CountPerStripValue);
            intent.putExtra("NetWtPerPouch", NetWtPerPouchValue);
            intent.putExtra("StripWt", StripWtValue);
            intent.putExtra("SilverLumps", SilverLumpsValue);
            intent.putExtra("EmailValue", EmailValue);
            intent.putExtra("MachineIDValue", MachineIDValue);
            intent.putExtra("WtRangeMinValue", WtRangeMinValue);
            intent.putExtra("WtRangeStdValue", WtRangeStdValue);
            intent.putExtra("WtRangeMaxValue", WtRangeMaxValue);
            intent.putExtra("StripShiftValue", stripshiftvalue);
            intent.putExtra("ProductCode", "productCode");
            intent.putExtra("ProductStockType", "stockType");
            intent.putExtra("Product",Product);
            intent.putExtra("SKUID",SKUID.getText());
            startActivity(intent);

        });

        //DropdownData
        arr2 = com.example.sd100testapp.DatabaseCall.getData().FetchArray("Select * from ProductVariant", 6);
        //dropdown
        dropdown = findViewById(R.id.stripspinner1);
        myAdap = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, arr2);
        dropdown.setAdapter(myAdap);
        Log.e("Here", String.valueOf(dropdown));

        if (productVariant != null) {
            int spinnerPosition = myAdap.getPosition(productVariant);
            dropdown.setSelection(spinnerPosition);
        }
        dropdown.setOnItemSelectedListener(this);

        Spinner dropdown2 = findViewById(R.id.stripshift);
        String[] items3 = new String[]{"A", "B", "C", "D", "N", "G"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items3);
        dropdown2.setAdapter(adapter3);
        dropdown2.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        if (parent.getId() == R.id.stripshift) {
            this.stripshiftvalue = parent.getItemAtPosition(pos).toString();
        } else if (parent.getId() == R.id.stripspinner1) {
            ArrayList<String> NewArr = new ArrayList<String>();
            ArrayList<String> NewArrNetwtperpouch = new ArrayList<String>();

            try {
                ConnectionHelper connectionHelper = new ConnectionHelper();
                connect = connectionHelper.connectionclass();

                if (connect != null) {
                    String query = "Select * from ProductVariant";
                    Statement st = connect.createStatement();
                    ResultSet rs = st.executeQuery(query);
                    while (rs.next()) {
                        NewArr.add(rs.getString(10));
                    }
                } else {
                    ConnectionResult = "Check Connection";
                }
            } catch (Exception ex) {
                Log.e("Here", ex.toString());

            }
            String[] countOfStrip = DatabaseCall.getData().FetchArray("Select * from ProductVariant", 11);
            if (countOfStrip[pos] != null) {
                float count = Float.parseFloat(NewArr.get(pos)) / Float.parseFloat(countOfStrip[pos]);
                CountPerStrip.setText(String.valueOf((int) count));

            }

            try {
                ConnectionHelper connectionHelper = new ConnectionHelper();
                connect = connectionHelper.connectionclass();

                if (connect != null) {
                    String query = "Select * from ProductVariant";
                    Statement st = connect.createStatement();
                    ResultSet rs = st.executeQuery(query);
                    while (rs.next()) {
                        NewArrNetwtperpouch.add(rs.getString(8));
                    }
                } else {
                    ConnectionResult = "Check Connection";
                }
            } catch (Exception ex) {
                Log.e("Here", ex.toString());

            }
            NetWtPerPouch.setText(NewArrNetwtperpouch.get(pos));

            String text = parent.getItemAtPosition(pos).toString();
            Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


}

