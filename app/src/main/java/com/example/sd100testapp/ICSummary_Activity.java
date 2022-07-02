package com.example.sd100testapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ICSummary_Activity extends AppCompatActivity {

    Connection connect;
    String ConnectionResult = "";

    Button icsumbit;
    TextView MCID, SKUID, icshiftvalue;
    EditText iccountperstripvalue, icwtperemptyboxvalue, icwtperfilledboxvalue, icbatchnovalue, icmrpvalue, icinspectedsum, iccheckedsum, WtRangeMinValue, WtRangeStdValue, WtRangeMaxValue;
    Switch icmfgaddressvalue, ictapingvalue, iccuttingvalue, icwrinklevalue, icbubblevalue, iccrakingvalue, ictrimmingvalue, iccreasingvalue, ictextmattervalue, icpackingvalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icsummary);

        //get intent from icbox
        String iccountperstrip = getIntent().getStringExtra("iccountperstrip");
        String icwtperemptybox = getIntent().getStringExtra("icwtperemptybox");
        String icwtperfilledbox = getIntent().getStringExtra("icwtperfilledbox");
        String icbatchno = getIntent().getStringExtra("icbatchno");
        String icmrp = getIntent().getStringExtra("icmrp");
        String WtRangeMin = getIntent().getStringExtra("icmin");
        String WtRangeStd = getIntent().getStringExtra("icstd");
        String WtRangeMax = getIntent().getStringExtra("icmax");
        String Shift = getIntent().getStringExtra("icshiftvalue");
        String ProductCode = getIntent().getStringExtra("productCode");
        String productStockType = getIntent().getStringExtra("productStockType");
        String product = getIntent().getStringExtra("product");
        String SKUIDSUM = getIntent().getStringExtra("SKUID");
        Boolean icmfgaddress = getIntent().getBooleanExtra("icmfgaddress", false);
        Boolean ictaping = getIntent().getBooleanExtra("ictaping", false);
        Boolean ictextmatter = getIntent().getBooleanExtra("ictextmatter", false);
        Boolean icpacking = getIntent().getBooleanExtra("icpacking", false);


        //id attached
        icsumbit = findViewById(R.id.icboxnextbtnsum);
        iccountperstripvalue = findViewById(R.id.icboxcountperstrip);
        icwtperemptyboxvalue = findViewById(R.id.icboxwtperemptybox);
        icwtperfilledboxvalue = findViewById(R.id.icboxwtperfilledbox);
        icbatchnovalue = findViewById(R.id.icboxbatchno);
        icmrpvalue = findViewById(R.id.icboxmrp);
        icmfgaddressvalue = findViewById(R.id.icboxmfgaddress);
        ictapingvalue = findViewById(R.id.icboxtaping);
        ictextmattervalue = findViewById(R.id.icboxtextmatter);
        icpackingvalue = findViewById(R.id.icboxpacking);
        icinspectedsum = findViewById(R.id.icinspectedsum);
        iccheckedsum = findViewById(R.id.iccheckedsum);
        MCID = findViewById(R.id.icboxmcid);
        SKUID = findViewById(R.id.icboxskuid);
        WtRangeMinValue = findViewById(R.id.icboxmin);
        WtRangeStdValue = findViewById(R.id.icboxstd);
        WtRangeMaxValue = findViewById(R.id.icboxmax);
        icshiftvalue = findViewById(R.id.icshiftsum);

        iccountperstripvalue.setText(iccountperstrip);
        icwtperemptyboxvalue.setText(icwtperemptybox);
        icwtperfilledboxvalue.setText(icwtperfilledbox);
        icbatchnovalue.setText(icbatchno);
        icmrpvalue.setText(icmrp);
        icmfgaddressvalue.setChecked(icmfgaddress);
        ictapingvalue.setChecked(ictaping);
        ictextmattervalue.setChecked(ictextmatter);
        icpackingvalue.setChecked(icpacking);
        icinspectedsum.setText(com.example.sd100testapp.DataHolder.getInstance().getData());
        String machineidvalue = com.example.sd100testapp.DataHolder.getInstance().getData2();
        MCID.setText(machineidvalue);
//        String productCode = com.example.sd100testapp.DatabaseCall.getData().FetchData("Select * from BatchExecution WHERE Status = 1", 2);
//        String stockType = com.example.sd100testapp.DatabaseCall.getData().FetchData("Select * from BatchExecution WHERE Status = 1", 3);
//        String productVariant = com.example.sd100testapp.DatabaseCall.getData().FetchData("Select * from ProductVariant WHERE ProductCode ='" + productCode + "' AND ProductStockType ='" + stockType + "'", 6);
        SKUID.setText(SKUIDSUM);
        WtRangeMinValue.setText(WtRangeMin);
        WtRangeStdValue.setText(WtRangeStd);
        WtRangeMaxValue.setText(WtRangeMax);
        icshiftvalue.setText(Shift);


        icsumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());


//                //IcBox Data insert in SQL server
                try {
                    ConnectionHelper connectionHelper = new ConnectionHelper();
                    connect = connectionHelper.connectionclass();
                    String timeStamp = DatabaseCall.getData().FetchData("Select * from GetProdDate",1);
                    if (connect != null) {
                        String sqlinsert = "Insert into QAIC values ('" + timeStamp + "','" + icbatchnovalue.getText().toString() + "','" + iccountperstripvalue.getText().toString() + "','" + icwtperfilledboxvalue.getText().toString() + "','" + icmrpvalue.getText().toString() + "','" + icwtperemptyboxvalue.getText().toString() + "','" + WtRangeMinValue.getText().toString() + "','" + WtRangeStdValue.getText().toString() + "','" + WtRangeMaxValue.getText().toString() + "','" + icshiftvalue.getText().toString() +"','" + icmfgaddress + "','" + ictaping + "','" + ictextmatter + "','" + icpacking + "','" + icinspectedsum.getText().toString() + "','" + iccheckedsum.getText().toString() + "','" + machineidvalue +"','" + product +"','" + product +"','" + currentTime + "')";
                        Statement st = connect.createStatement();
                        ResultSet rs = st.executeQuery(sqlinsert);

                    } else {
                        ConnectionResult = "Check Connection";
                        Toast.makeText(getApplicationContext(), "Check Network Connection", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    Log.e("Here", ex.toString());
                }

                Intent intent = new Intent(ICSummary_Activity.this, com.example.sd100testapp.Overview_Activity.class);
                startActivity(intent);

            }
        });

    }
}