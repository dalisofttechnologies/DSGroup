package com.example.sd100testapp;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionHelper {
    Connection con;
    String uname, pass, ip, port, database;

    @SuppressLint("NewApi")

    public Connection connectionclass() {
        //Plant Connection
//        ip = "172.168.1.200";
//        database = "DS";
//        port = "1433";
//        uname = "admin";
//        pass = "ds@123";
        //Local Connection
        ip = "192.168.29.3";
        database = "DS12";
        port = "1433";
        uname = "Shubh";
        pass = "123456";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";" + "databasename=" + database + ";user=" + uname + ";password=" + pass + ";";
            connection = DriverManager.getConnection(ConnectionURL);

        } catch (Exception ex) {
            Log.e("Error", ex.getMessage());
        }

        return connection;
    }
}
