package com.example.sd100testapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;

public class Overview_Activity extends AppCompatActivity {
    Button exit;
    Button navigate12;
    Button Daily, Monthly;
    Connection connect;
    String ConnectionResult = "";
    Button LoadingBtn;
    Button Creatingbtn;
    Button UpdatingBtn;
    Button Deletingbtn;

    TextView stripinspecteddone, stripcheckeddone, stripcheckedpending, stripapproveddone, stripapprovedpending, icinspecteddone, iccheckeddone, iccheckedpending, icapproveddone, icapprovedpending, mcinspecteddone, mccheckeddone, mccheckedpending, mcapproveddone, mcapprovedpending, leaktestinspecteddone, leaktestcheckeddone, leaktestcheckedpending, leaktestapproveddone, leaktestapprovedpending, ipwinspecteddone, ipwcheckeddone, ipwcheckedpending, ipwapproveddone, ipwapprovedpending;

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        exit = findViewById(R.id.exitbtn);
        Daily = findViewById(R.id.overviewDaily);
        Monthly = findViewById(R.id.overviewMonthly);
        navigate12 = findViewById(R.id.logoutbtn);
        stripinspecteddone = findViewById(R.id.stripinspecteddone);
        stripcheckeddone = findViewById(R.id.stripcheckeddone);
        stripcheckedpending = findViewById(R.id.stripcheckedpending);
        icinspecteddone = findViewById(R.id.icinspecteddone);
        iccheckeddone = findViewById(R.id.iccheckeddone);
        iccheckedpending = findViewById(R.id.iccheckedpending);
        mcinspecteddone = findViewById(R.id.mcinspecteddone);
        mccheckeddone = findViewById(R.id.mccheckeddone);
        mccheckedpending = findViewById(R.id.mccheckedpending);
        leaktestinspecteddone = findViewById(R.id.leaktestinspecteddone);
        leaktestcheckeddone = findViewById(R.id.leaktestcheckeddone);
        leaktestcheckedpending = findViewById(R.id.leaktestcheckedpending);
        ipwinspecteddone = findViewById(R.id.ipwinspecteddone);
        ipwcheckeddone = findViewById(R.id.ipwcheckeddone);
        ipwcheckedpending = findViewById(R.id.ipwcheckedpending);
        String timeStamp = DatabaseCall.getData().FetchData("Select * from GetProdDate", 1);

//        LoadingBtn = findViewById(R.id.loadbtn);
//        Creatingbtn = findViewById(R.id.createbtn);
//        UpdatingBtn = findViewById(R.id.updatebtn);
//        Deletingbtn = findViewById(R.id.deletebtn);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Overview_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        navigate12.setOnClickListener(view -> {
            Intent intent = new Intent(Overview_Activity.this, MachineID_Activity.class);
            startActivity(intent);
        });

        Daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Integer StripInspected = com.example.sd100testapp.DatabaseCall.getData().FetchCountData("Select Count(*) AS T from QAStrip Where ProdDate = '"+timeStamp+"' AND DATALENGTH(StripInspected) > 0", 1);
                stripinspecteddone.setText(StripInspected.toString());
                Integer StripChecked = com.example.sd100testapp.DatabaseCall.getData().FetchCountData("Select Count(*) AS T from QAStrip Where ProdDate = '"+timeStamp+"' AND DATALENGTH(StripChecked) > 0", 1);
                stripcheckeddone.setText(StripChecked.toString());
                Integer StripPending = StripInspected - StripChecked;
                stripcheckedpending.setText(StripPending.toString());

                Integer IcInspected = com.example.sd100testapp.DatabaseCall.getData().FetchCountData("Select Count(*) AS T from QAIC Where ProdDate = '"+timeStamp+"' AND DATALENGTH(ICInspected) > 0", 1);
                icinspecteddone.setText(IcInspected.toString());
                Integer IcChecked = com.example.sd100testapp.DatabaseCall.getData().FetchCountData("Select Count(*) AS T from QAIC Where ProdDate = '"+timeStamp+"' AND DATALENGTH(ICChecked) > 0", 1);
                iccheckeddone.setText(IcChecked.toString());
                Integer IcPending = IcInspected - IcChecked;
                iccheckedpending.setText(IcPending.toString());

                Integer MCInspected = com.example.sd100testapp.DatabaseCall.getData().FetchCountData("Select Count(*) AS T from QAMC Where ProdDate = '"+timeStamp+"' AND DATALENGTH(MCInspected) > 0", 1);
                mcinspecteddone.setText(MCInspected.toString());
                Integer MCChecked = com.example.sd100testapp.DatabaseCall.getData().FetchCountData("Select Count(*) AS T from QAMC Where ProdDate = '"+timeStamp+"' AND DATALENGTH(MCChecked) > 0", 1);
                mccheckeddone.setText(MCChecked.toString());
                Integer MCPending = MCInspected - MCChecked;
                mccheckedpending.setText(MCPending.toString());

                Integer LeakTestInspected = com.example.sd100testapp.DatabaseCall.getData().FetchCountData("Select Count(*) AS T from QALeakTest Where ProdDate = '"+timeStamp+"' AND DATALENGTH(LeakTestInspected) > 0", 1);
                leaktestinspecteddone.setText(LeakTestInspected.toString());
                Integer LeakTestChecked = com.example.sd100testapp.DatabaseCall.getData().FetchCountData("Select Count(*) AS T from QALeakTest Where ProdDate = '"+timeStamp+"' AND DATALENGTH(LeakTestChecked) > 0", 1);
                leaktestcheckeddone.setText(LeakTestChecked.toString());
                Integer LeakTestPending = LeakTestInspected - LeakTestChecked;
                leaktestcheckedpending.setText(LeakTestPending.toString());

                Integer IPWInspected = com.example.sd100testapp.DatabaseCall.getData().FetchCountData("Select Count(*) AS T from QAIPW Where ProdDate = '"+timeStamp+"' AND DATALENGTH(IPWInspected) > 0", 1);
                ipwinspecteddone.setText(IPWInspected.toString());
                Integer IPWChecked = com.example.sd100testapp.DatabaseCall.getData().FetchCountData("Select Count(*) AS T from QAIPW Where ProdDate = '"+timeStamp+"' AND DATALENGTH(IPWChecked) > 0", 1);
                ipwcheckeddone.setText(IPWChecked.toString());
                Integer IPWPending = IPWInspected - IPWChecked;
                ipwcheckedpending.setText(IPWPending.toString());

            }
        });

        Monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer StripInspected = com.example.sd100testapp.DatabaseCall.getData().FetchCountData("Select Count(*) AS T from QAStrip Where MONTH(ProdDate) = Month('"+timeStamp+"') AND DATALENGTH(StripInspected) > 0", 1);
                stripinspecteddone.setText(StripInspected.toString());
                Integer StripChecked = com.example.sd100testapp.DatabaseCall.getData().FetchCountData("Select Count(*) AS T from QAStrip Where MONTH(ProdDate) = Month('"+timeStamp+"') AND DATALENGTH(StripChecked) > 0", 1);
                stripcheckeddone.setText(StripChecked.toString());
                Integer StripPending = StripInspected - StripChecked;
                stripcheckedpending.setText(StripPending.toString());
                Log.e("Here", String.valueOf(StripPending));

                Integer IcInspected = com.example.sd100testapp.DatabaseCall.getData().FetchCountData("Select Count(*) AS T from QAIC Where MONTH(ProdDate) = Month('"+timeStamp+"') AND DATALENGTH(ICInspected) > 0", 1);
                icinspecteddone.setText(IcInspected.toString());
                Integer IcChecked = com.example.sd100testapp.DatabaseCall.getData().FetchCountData("Select Count(*) AS T from QAIC Where MONTH(ProdDate) = Month('"+timeStamp+"') AND DATALENGTH(ICChecked) > 0", 1);
                iccheckeddone.setText(IcChecked.toString());
                Integer IcPending = IcInspected - IcChecked;
                iccheckedpending.setText(IcPending.toString());

                Integer MCInspected = com.example.sd100testapp.DatabaseCall.getData().FetchCountData("Select Count(*) AS T from QAMC Where MONTH(ProdDate) = Month('"+timeStamp+"') AND DATALENGTH(MCInspected) > 0", 1);
                mcinspecteddone.setText(MCInspected.toString());
                Integer MCChecked = com.example.sd100testapp.DatabaseCall.getData().FetchCountData("Select Count(*) AS T from QAMC Where MONTH(ProdDate) = Month('"+timeStamp+"') AND DATALENGTH(MCChecked) > 0", 1);
                mccheckeddone.setText(MCChecked.toString());
                Integer MCPending = MCInspected - MCChecked;
                mccheckedpending.setText(MCPending.toString());

                Integer LeakTestInspected = com.example.sd100testapp.DatabaseCall.getData().FetchCountData("Select Count(*) AS T from QALeakTest Where MONTH(ProdDate) = Month('"+timeStamp+"') AND DATALENGTH(LeakTestInspected) > 0", 1);
                leaktestinspecteddone.setText(LeakTestInspected.toString());
                Integer LeakTestChecked = com.example.sd100testapp.DatabaseCall.getData().FetchCountData("Select Count(*) AS T from QALeakTest Where MONTH(ProdDate) = Month('"+timeStamp+"') AND DATALENGTH(LeakTestChecked) > 0", 1);
                leaktestcheckeddone.setText(LeakTestChecked.toString());
                Integer LeakTestPending = LeakTestInspected - LeakTestChecked;
                leaktestcheckedpending.setText(LeakTestPending.toString());

                Integer IPWInspected = com.example.sd100testapp.DatabaseCall.getData().FetchCountData("Select Count(*) AS T from QAIPW Where MONTH(ProdDate) = Month('"+timeStamp+"') AND DATALENGTH(IPWInspected) > 0", 1);
                ipwinspecteddone.setText(IPWInspected.toString());
                Integer IPWChecked = com.example.sd100testapp.DatabaseCall.getData().FetchCountData("Select Count(*) AS T from QAIPW Where MONTH(ProdDate) = Month('"+timeStamp+"') AND DATALENGTH(IPWChecked) > 0", 1);
                ipwcheckeddone.setText(IPWChecked.toString());
                Integer IPWPending = IPWInspected - IPWChecked;
                ipwcheckedpending.setText(IPWPending.toString());

            }
        });

        Integer StripInspected = com.example.sd100testapp.DatabaseCall.getData().FetchCountData("Select Count(*) AS T from QAStrip Where ProdDate = '"+timeStamp+"' AND DATALENGTH(StripInspected) > 0", 1);
        stripinspecteddone.setText(StripInspected.toString());
        Integer StripChecked = com.example.sd100testapp.DatabaseCall.getData().FetchCountData("Select Count(*) AS T from QAStrip Where ProdDate = '"+timeStamp+"' AND DATALENGTH(StripChecked) > 0", 1);
        stripcheckeddone.setText(StripChecked.toString());
        Integer StripPending = StripInspected - StripChecked;
        stripcheckedpending.setText(StripPending.toString());

        Integer IcInspected = com.example.sd100testapp.DatabaseCall.getData().FetchCountData("Select Count(*) AS T from QAIC Where ProdDate = '"+timeStamp+"' AND DATALENGTH(ICInspected) > 0", 1);
        icinspecteddone.setText(IcInspected.toString());
        Integer IcChecked = com.example.sd100testapp.DatabaseCall.getData().FetchCountData("Select Count(*) AS T from QAIC Where ProdDate = '"+timeStamp+"' AND DATALENGTH(ICChecked) > 0", 1);
        iccheckeddone.setText(IcChecked.toString());
        Integer IcPending = IcInspected - IcChecked;
        iccheckedpending.setText(IcPending.toString());

        Integer MCInspected = com.example.sd100testapp.DatabaseCall.getData().FetchCountData("Select Count(*) AS T from QAMC Where ProdDate = '"+timeStamp+"' AND DATALENGTH(MCInspected) > 0", 1);
        mcinspecteddone.setText(MCInspected.toString());
        Integer MCChecked = com.example.sd100testapp.DatabaseCall.getData().FetchCountData("Select Count(*) AS T from QAMC Where ProdDate = '"+timeStamp+"' AND DATALENGTH(MCChecked) > 0", 1);
        mccheckeddone.setText(MCChecked.toString());
        Integer MCPending = MCInspected - MCChecked;
        mccheckedpending.setText(MCPending.toString());

        Integer LeakTestInspected = com.example.sd100testapp.DatabaseCall.getData().FetchCountData("Select Count(*) AS T from QALeakTest Where ProdDate = '"+timeStamp+"' AND DATALENGTH(LeakTestInspected) > 0", 1);
        leaktestinspecteddone.setText(LeakTestInspected.toString());
        Integer LeakTestChecked = com.example.sd100testapp.DatabaseCall.getData().FetchCountData("Select Count(*) AS T from QALeakTest Where ProdDate = '"+timeStamp+"' AND DATALENGTH(LeakTestChecked) > 0", 1);
        leaktestcheckeddone.setText(LeakTestChecked.toString());
        Integer LeakTestPending = LeakTestInspected - LeakTestChecked;
        leaktestcheckedpending.setText(LeakTestPending.toString());

        Integer IPWInspected = com.example.sd100testapp.DatabaseCall.getData().FetchCountData("Select Count(*) AS T from QAIPW Where ProdDate = '"+timeStamp+"' AND DATALENGTH(IPWInspected) > 0", 1);
        ipwinspecteddone.setText(IPWInspected.toString());
        Integer IPWChecked = com.example.sd100testapp.DatabaseCall.getData().FetchCountData("Select Count(*) AS T from QAIPW Where ProdDate = '"+timeStamp+"' AND DATALENGTH(IPWChecked) > 0", 1);
        ipwcheckeddone.setText(IPWChecked.toString());
        Integer IPWPending = IPWInspected - IPWChecked;
        ipwcheckedpending.setText(IPWPending.toString());
    }

}

//        LoadingBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                TextView tx1 = findViewById(R.id.loadtext1);
//                TextView tx2 = findViewById(R.id.loadtext2);
//
//                try {
//                    ConnectionHelper connectionHelper = new ConnectionHelper();
//                    connect = connectionHelper.connectionclass();
//                    if (connect != null) {
//                        String query = "Select * from Table_1";
//                        Statement st = connect.createStatement();
//                        ResultSet rs = st.executeQuery(query);
//
//                        while (rs.next()) {
//                            tx1.setText(rs.getString(1));
//                            tx2.setText(rs.getString(2));
//                        }
//                    } else {
//                        ConnectionResult = "Check Connection";
//                    }
//                } catch (Exception ex) {
//                }
//            }
//
//        });
//
//        Creatingbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                TextView tx1 = findViewById(R.id.itemid);
//                TextView tx2 = findViewById(R.id.itemname);
//                TextView tx3 = findViewById(R.id.itemdesign);
//                TextView tx4 = findViewById(R.id.itemcolor);
//
//                try {
//                    ConnectionHelper connectionHelper = new ConnectionHelper();
//                    connect = connectionHelper.connectionclass();
//                    if (connect != null) {
//                        String sqlinsert = "Insert into Table_1 values ('" + tx1.getText().toString() + "','" + tx2.getText().toString() + "','" + tx3.getText().toString() + "','" + tx4.getText().toString() + "')";
//                        Statement st = connect.createStatement();
//                        ResultSet rs = st.executeQuery(sqlinsert);
//
//                    } else {
//                        ConnectionResult = "Check Connection";
//                    }
//                } catch (Exception ex) {
//                }
//            }
//
//        });
//
//        UpdatingBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                TextView tx1 = findViewById(R.id.itemid);
//                TextView tx2 = findViewById(R.id.itemname);
//                TextView tx3 = findViewById(R.id.itemdesign);
//                TextView tx4 = findViewById(R.id.itemcolor);
//
//                try {
//                    ConnectionHelper connectionHelper = new ConnectionHelper();
//                    connect = connectionHelper.connectionclass();
//                    if (connect != null) {
//                        String sqlupdate = "update Table_1 set Item_Name ='" + tx2.getText().toString() + "', Design ='" + tx3.getText().toString() + "', Color ='" + tx4.getText().toString() + "' where Product_ID ='" + tx1.getText().toString() + "'";
//                        Statement st = connect.createStatement();
//                        ResultSet rs = st.executeQuery(sqlupdate);
//
//                    } else {
//                        ConnectionResult = "Check Connection";
//                    }
//                } catch (Exception ex) {
//                }
//            }
//
//        });
//
//        Deletingbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                TextView tx1 = findViewById(R.id.itemid);
//                TextView tx2 = findViewById(R.id.itemname);
//                TextView tx3 = findViewById(R.id.itemdesign);
//                TextView tx4 = findViewById(R.id.itemcolor);
//
//                try {
//                    ConnectionHelper connectionHelper = new ConnectionHelper();
//                    connect = connectionHelper.connectionclass();
//                    if (connect != null) {
//                        String sqldelete = "delete Table_1  where Product_ID ='" + tx1.getText().toString() + "'";
//                        Statement st = connect.createStatement();
//                        ResultSet rs = st.executeQuery(sqldelete);
//
//                    } else {
//                        ConnectionResult = "Check Connection";
//                    }
//                } catch (Exception ex) {
//                }
//            }
//
//        });
