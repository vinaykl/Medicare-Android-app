package com.example.vinaykl.bs2;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class PlaceOrder extends AppCompatActivity {

    public Bundle b2;
    SQLiteDatabase db;
    String Pid;
    String dbName="vkl.db";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        try {

            db = getApplicationContext().openOrCreateDatabase(dbName, getApplicationContext().MODE_APPEND, null);
            if (db == null) {
                Toast.makeText(getApplicationContext(), "Erron in DB0", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (SQLException E) {
            Toast.makeText(this, E.getMessage(), Toast.LENGTH_LONG).show();
        } catch (Exception E) {
            Toast.makeText(getApplicationContext(), E.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            //db.endTransaction();
        }

        //GET PATIENT NAME/ID FROM START PAGE
        Intent intent = getIntent();
        b2 = intent.getExtras();
        Pid = b2.getString("pname");//USE THIS TO GET PRESCRIPTION
        System.out.println(" pid : "+Pid );
        System.out.println("select * from " + "PRESCRIPTION" + " where PATIENTNAME = '" + Pid+ "'");

        // CREATE A SELECT STATEMENT ACCESSING PRESCRIPTION DATA
        Cursor res =  db.rawQuery( "select * from " + "PRESCRIPTION" + " where PATIENTNAME = '" + Pid+ "'", null );
        res.moveToFirst();
        String prescription1 = res.getString(res.getColumnIndex("TABLET"));

        //DELETE THIS LATER
        //String prescription1 = "Dolo650|8;Sinerest|8;Cetrezin|4";

        //USED TO PARSE DRUGS AND QUANTITY AFTER QUERY FROM DATABASE
        String[] drug_quantity;
        //drug_quantity = prescription1.split("[\\;\\s]+");
        drug_quantity = prescription1.split("[\\,\\s]+");

        final ArrayList<String> prescribedDrug = new ArrayList<>();
        final ArrayList<Integer> prescribedQuantiy = new ArrayList<>();
        for(String st:drug_quantity)
        {
            //String[] drugs = null;
            //drugs = s.split("[\\|\\s]+");

            //prescribedDrug.add(drugs[0]);
            //prescribedQuantiy.add(Integer.parseInt(drugs[1]));
            prescribedDrug.add(st);
            prescribedQuantiy.add(8);
        }

        //TO DISPLAY IN LISTVIEW
        ArrayList<String> displayDrugs = new ArrayList<>();
        for(int i=0;i<prescribedDrug.size();i++)
        {
            String temp = prescribedDrug.get(i) + "      " + prescribedQuantiy.get(i);
            displayDrugs.add(temp);
        }

        //LISTVIEW
        final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, displayDrugs);
        ListView listView = (ListView) findViewById(R.id.DisplayDrugs);
        listView.setAdapter(adapter);


        Button placeOrder = (Button) findViewById(R.id.placeOrder);
        placeOrder.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent startSenseService = new Intent(PlaceOrder.this, ProcessOrder.class);
                Bundle b4 = new Bundle();
                b4.putString("Pid", Pid);
                b4.putStringArrayList("PrescribedDrug", prescribedDrug);
                b4.putIntegerArrayList("PrescribedQuantity",prescribedQuantiy);
                startSenseService.putExtras(b4);
                startService(startSenseService);



            }
        });

        //CALL A SERVICE




    }

}
