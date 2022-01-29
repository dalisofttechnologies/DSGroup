package com.example.sd100testapp;

import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseCall {

    private static final DatabaseCall instance = new DatabaseCall();

    public static DatabaseCall getData() {
        return instance;
    }

    Connection connect;
    String ConnectionResult = "";
    String Data;
    String DataCount;
    ArrayList<String> ar2 = new ArrayList<String>();

    public String[] FetchArray(String Query, int index) {
        ArrayList<String> ar = new ArrayList<String>();
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();

            if (connect != null) {
                String query = Query;
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    int i = 0;
                    ar.add(rs.getString(index));
                    i++;
                }
            } else {
                ConnectionResult = "Check Connection";
            }
        } catch (Exception ex) {
            Log.e("Here", ex.toString());

        }
        int size = ar.size();
        String arr[] = new String[size];
        for (int i = 0; i < size; i++) {
            arr[i] = ar.get(i);
        }
        return arr;
    }

    public ArrayList<String> FetchArray2(String Query, int index) {
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();

            if (connect != null) {
                String query = Query;
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    int i = 0;
                    ar2.add(rs.getString(index));
                    i++;
                }
            } else {
                ConnectionResult = "Check Connection";
            }
        } catch (Exception ex) {
            Log.e("Here", ex.toString());

        }
        return ar2;
    }

    public String FetchData(String Query, int index) {
        String Data2 = "";
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();

            if (connect != null) {
                String query = Query;
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    Data2 = rs.getString(index);
                }
            } else {

                ConnectionResult = "Check Connection";
            }
        } catch (Exception ex) {
            Log.e("Here", ex.toString());
        }
        Log.e("Here",Data2);
        return Data2;
    }

    public Integer FetchCountData(String Query, int index) {
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();

            if (connect != null) {
                String query = Query;
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    DataCount = rs.getString(index);
                }
            } else {

                ConnectionResult = "Check Connection";
            }
        } catch (Exception ex) {
            Log.e("Here", ex.toString());
        }
        return Integer.valueOf(DataCount);
    }
}
