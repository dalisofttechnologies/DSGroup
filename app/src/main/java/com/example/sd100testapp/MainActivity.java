package com.example.sd100testapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    Connection connect;
    String ConnectionResult = "";

    Button navigate;
    EditText email, password;

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Declare variables
        navigate = (Button) findViewById(R.id.icnextbtn);
        email = findViewById(R.id.mainEmail);
        password = findViewById(R.id.mainPassword);

        //click on login button
        navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailvalue = email.getText().toString();
                String passwordvalue = password.getText().toString();
                if (emailvalue.matches("") && passwordvalue.matches("")) {
                    Toast.makeText(getApplicationContext(), "Enter a username and password", Toast.LENGTH_SHORT).show();
                    return;
                } else if (emailvalue.matches("")) {
                    Toast.makeText(getApplicationContext(), "Enter a username", Toast.LENGTH_SHORT).show();
                    return;
                } else if (passwordvalue.matches("")) {
                    Toast.makeText(getApplicationContext(), "Enter a password", Toast.LENGTH_SHORT).show();
                    return;
                }
                com.example.sd100testapp.DataHolder.getInstance().setData(emailvalue);

                // Check if user exists in database
                try {

                    ConnectionHelper connectionHelper = new ConnectionHelper();
                    connect = connectionHelper.connectionclass();


                    if (connect != null) {

                        String query = "Select * from [User] where Email='"
                                + emailvalue + "' and Password='" + passwordvalue + "'";

                        Statement st = connect.createStatement();
                        ResultSet rs = st.executeQuery(query);

                        if (rs.next()) {
                            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(MainActivity.this, MachineID_Activity.class);
                            startActivity(intent);
                        } else {
                            try {

                                String query2 = "Select * from [User] where Email='"
                                        + emailvalue + "'";

                                Statement st2 = connect.createStatement();
                                ResultSet rs2 = st2.executeQuery(query2);

                                if (rs2.next()) {
                                    Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "Incorrect Credentials", Toast.LENGTH_SHORT).show();
                                }
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                    } else {
                        ConnectionResult = "Check Connection";
                        Toast.makeText(getApplicationContext(), "Check Network Connection", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    Log.e("Here",ex.toString());
                }
            }
        });
    }
}