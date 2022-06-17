package com.example.sd100testapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class IndividualPouchWeight_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button navigate9;


    String[] pouchEntry;
    String[] pouchHeading;
    com.example.sd100testapp.PouchAdapter pouchAdapter;
    RecyclerView recyclerView;
    ArrayList<PouchModal> pouchModalArrayList;
    ArrayList<String> Arr = new ArrayList<String>();


    TextView MCID, SKUID;
    String IPWShift = "";
    ImageView addPouch, deletePouch, stripAdd, stripMinus;
    EditText IPWDate, IPWTime, IPWRollNo, IPWStripWt1, IPWStripWt2, IPWStripWt3, IPWStripWt4, IPWStripCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_pouch_weight);

        //Creating recycler view
        recyclerView = findViewById(R.id.pouchRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(false);

        pouchModalArrayList = new ArrayList<PouchModal>();

        pouchAdapter = new com.example.sd100testapp.PouchAdapter(this, pouchModalArrayList);
        recyclerView.setAdapter(pouchAdapter);

        //Initialize variables
        IPWDate = findViewById(R.id.IPWDate);
        IPWTime = findViewById(R.id.IPWTime);
        IPWStripWt1 = findViewById(R.id.IPWStripWt1);
        IPWStripWt2 = findViewById(R.id.IPWStripWt2);
        IPWStripWt3 = findViewById(R.id.IPWStripWt3);
        IPWStripWt4 = findViewById(R.id.IPWStripWt4);
        navigate9 = findViewById(R.id.Individualnextbtn);
        IPWRollNo = findViewById(R.id.IPWRollNo);
        String timeStamp = DatabaseCall.getData().FetchData("Select * from GetProdDate", 1);
        String machineidvalue = com.example.sd100testapp.DataHolder.getInstance().getData2();
        IPWRollNo.setText(com.example.sd100testapp.DatabaseCall.getData().FetchData("Select top (1)* from BatchExecution WHERE  MachineSLN = '\" + machineidvalue + \"' order by Timestamp desc", 8));
        MCID = findViewById(R.id.IPWmcid);

        MCID.setText(machineidvalue);
        SKUID = findViewById(R.id.IPWskuid);


//        String productCode = com.example.sd100testapp.DatabaseCall.getData().FetchData("Select * from BatchExecution WHERE Status = 1 AND CONVERT(date, ProdDate) = '" + timeStamp + "' AND MachineSLN = '" + machineidvalue + "'", 2);
//        String stockType = com.example.sd100testapp.DatabaseCall.getData().FetchData("Select * from BatchExecution WHERE Status = 1 AND CONVERT(date, ProdDate) = '" + timeStamp + "' AND MachineSLN = '" + machineidvalue + "'", 3);
        String Product = DatabaseCall.getData().FetchData("select Product from ProductionPlan where Status =1  and ProdDate=dbo.getProdDateFunction(getdate()) and MachineSLN='" + machineidvalue + "'", 1);

        String productVariant = "";
        SKUID.setText(productVariant);
        if (Product.length() == 0) {
            Toast.makeText(IndividualPouchWeight_Activity.this, "Running Machine Not Planned", Toast.LENGTH_LONG).show();
        } else {
            productVariant = com.example.sd100testapp.DatabaseCall.getData().FetchData("select Description from ProductVariant where( ProductCode+'-'+ProductStockType)= '" + Product + "'", 1);
            SKUID.setText(productVariant);
        }
        //add delete button recyclerview
        addPouch = findViewById(R.id.addPouch);
        deletePouch = findViewById(R.id.deletePouch);
        stripAdd = findViewById(R.id.ipwstripadd);
        stripMinus = findViewById(R.id.ipwstripminus);
        IPWStripCount = findViewById(R.id.ipwstripcount);

        stripAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newValue = Integer.parseInt(String.valueOf(IPWStripCount.getText())) + 1;
//             Log.e("Here", String.valueOf(newValue));newValue
                IPWStripCount.setText(String.valueOf(newValue));
            }
        });
        stripMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newValue = Integer.parseInt(String.valueOf(IPWStripCount.getText())) - 1;
                IPWStripCount.setText(String.valueOf(newValue));
            }
        });

        addPouch.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if (pouchModalArrayList.size() < 100) {
                    int q = pouchModalArrayList.size() + 1;
                    PouchModal pouchModalChange = new PouchModal("Pouch wt " + q, "");
                    Arr.add("ead");
                    pouchModalArrayList.add(pouchModalChange);
                    pouchAdapter.notifyItemInserted(pouchModalArrayList.size());
                }
            }
        });

        deletePouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pouchModalArrayList.size() > 1) {
                    pouchModalArrayList.remove(pouchModalArrayList.size() - 1);
                    pouchAdapter.notifyItemRemoved(pouchModalArrayList.size());
                }
            }
        });


        //Logic to create recyclerview with two box
        Integer b = 30;
        String[] a = new String[b];
        String[] c = new String[b];
        for (int i = 0; i < b; i++) {
            int j = i + 1;
            a[i] = "Pouch wt " + j;
            c[i] = "";
        }
        pouchHeading = a;
        pouchEntry = c;
        getData2();


        navigate9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String IPWDateValue = IPWDate.getText().toString();
                String IPWTimeValue = IPWTime.getText().toString();
                String IPWRollNoValue = IPWRollNo.getText().toString();
                String IPWStripWtValue1 = IPWStripWt1.getText().toString();
                String IPWStripWtValue2 = IPWStripWt2.getText().toString();
                String IPWStripWtValue3 = IPWStripWt3.getText().toString();
                String IPWStripWtValue4 = IPWStripWt4.getText().toString();
                String IPWStripCountValue = IPWStripCount.getText().toString();

                if (IPWRollNoValue.matches("") || IPWStripWtValue1.matches("")) {
                    Toast.makeText(getApplicationContext(), "Please fill all entries", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(IndividualPouchWeight_Activity.this, com.example.sd100testapp.IndividualPouchWeightSummary_Activity.class);

                intent.putExtra("IPWDate", IPWDateValue);
                intent.putExtra("IPWTime", IPWTimeValue);
                intent.putExtra("IPWRollNo", IPWRollNoValue);
                intent.putExtra("IPWStripWt1", IPWStripWtValue1);
                intent.putExtra("IPWStripWt2", IPWStripWtValue2);
                intent.putExtra("IPWStripWt3", IPWStripWtValue3);
                intent.putExtra("IPWStripWt4", IPWStripWtValue4);
                intent.putExtra("IPWShift", IPWShift);
                intent.putExtra("IPWStripCount", IPWStripCountValue);
                intent.putExtra("ProductCode", "productCode");
                intent.putExtra("ProductStockType", "stockType");
                intent.putExtra("Product", Product);
                intent.putExtra("SKUID", SKUID.getText());

                //get arraylist size
                int sizeArrayList = Integer.parseInt(String.valueOf(pouchModalArrayList.size()));
                //clear arraylist
                pouchModalArrayList.clear();

                //put all edittext recycleview data in arraylist
                for (int i = 0; i < Integer.parseInt(String.valueOf(sizeArrayList)); i++) {
                    String finalEntry = ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.pouchWtEntry)).getText().toString();
//                    Log.e("Here", finalEntry);

                    PouchModal pouchModal = new PouchModal("Pouch wt " + i, finalEntry);
                    pouchModalArrayList.add(pouchModal);
                }
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST", (Serializable) pouchModalArrayList);
                intent.putExtra("BUNDLE", args);
                startActivity(intent);
            }
        });

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
        IPWDate.setText(outputText);

        //set Time Automatically
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        IPWTime.setText(currentTime);


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
        Spinner dropdown = findViewById(R.id.IPWspinner);
        ArrayAdapter myAdap = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, arr);
        dropdown.setAdapter(myAdap);

        if (productVariant != null) {
            int spinnerPosition = myAdap.getPosition(productVariant);
            dropdown.setSelection(spinnerPosition);
        }
        dropdown.setOnItemSelectedListener(this);

        //dropdown
        Spinner dropdown2 = findViewById(R.id.IPWShift);
        String[] items2 = new String[]{"A", "B", "C", "D", "N", "G"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        dropdown2.setAdapter(adapter2);
        dropdown2.setOnItemSelectedListener(this);
    }

    private void getData2() {

        for (int i = 0; i < pouchHeading.length; i++) {
            PouchModal pouchModal = new PouchModal(pouchHeading[i], pouchEntry[i]);
            pouchModalArrayList.add(pouchModal);
        }
        pouchAdapter.notifyDataSetChanged();
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        if (parent.getId() == R.id.IPWShift) {
            this.IPWShift = parent.getItemAtPosition(pos).toString();
        } else if (parent.getId() == R.id.IPWspinner) {
            String text = parent.getItemAtPosition(pos).toString();
            Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}