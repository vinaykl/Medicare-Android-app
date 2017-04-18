package com.example.vinaykl.bs2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;

public class Results extends AppCompatActivity {
    SQLiteDatabase db;
    ArrayList<String> docname = new ArrayList<String>();
    ArrayList<String> doc = new ArrayList<String>();
    ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> map = new HashMap<String, String>();
    public Bundle b;
    String doctype;
    String time;
    String location;
    String patientname;
    private ListView lv;

    /* ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("Value1", arrayList1.get(0));
        map.put("Value2", arrayList2.get(0));
        mylist.add(map);*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
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

        doctype = getIntent().getExtras().getString("Doc");
        time = getIntent().getExtras().getString("Time");
        location =getIntent().getExtras().getString("location");
        patientname = getIntent().getExtras().getString("Patient");

        System.out.println("variable passed is " +doctype);

        //TextView textView = (TextView) findViewById(R.id.results);
        //textView.setText(doctype+" "+time+" "+location);

        Context context = getApplicationContext();
        String s = String.valueOf(context.getDatabasePath("vkl.db"));

        // Read the database

        db = getApplicationContext().openOrCreateDatabase(s,MODE_APPEND,null);
        String Dtable = "Doctor";
        //db = SQLiteDatabase.openOrCreateDatabase(Environment.getExternalStorageDirectory() + "/databaseFolder/myDB", null);
        Cursor cursor = db.rawQuery("SELECT * FROM "+ Dtable ,null);
        //Cursor c = db.rawQuery("SELECT Drating FROM Doctor where ",null);
        cursor.moveToFirst();
        System.out.println("reading from database");
        while( cursor.isAfterLast() == false){
            String Dspecilization = cursor.getString(cursor.getColumnIndex("Dspecilization"));
            System.out.println(" specilaztion is "+ Dspecilization);

            if( doctype.equals(Dspecilization)){
                System.out.println(" adding doctor " +cursor.getString(cursor.getColumnIndex("Dname")));

                String name = cursor.getString(cursor.getColumnIndex("Dname"));
                Cursor c = db.rawQuery("SELECT Drating FROM Doctor where Dname = '"+name+"'",null);
                c.moveToFirst();
                String name1 = name+"\t\t\t\t\t\t\t\t"+c.getString(c.getColumnIndex("Drating"))+"/5";
                doc.add(name);
                //Toast.makeText(this,c.getString(c.getColumnIndex("Drating")),Toast.LENGTH_SHORT).show();
                docname.add(name1);
                //docrating.add(c.getString(c.getColumnIndex("Drating")));
                //map.put("Value 1",cursor.getString(cursor.getColumnIndex("Dspecilization")));
                //map.put("Value 2",c.getString(c.getColumnIndex("Drating")));
            }
            cursor.moveToNext();

        }
        // Now display in the listview
        lv = (ListView) findViewById(R.id.ListView);
        //mylist.add(map);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                docname );


        //lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple‌​_list_item_1 , mylist));

        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // Get the selected value
                String var = doc.get(position);
                // get the ID from the Doctor name

                // Start the booking page, pass the doc ID
                Intent BooksPage = new Intent(Results.this, book.class);
                Bundle b = new Bundle();
                b.putString("Doc", var);
                b.putString("Patient",patientname);
                BooksPage.putExtras(b);
                startActivity(BooksPage);



            }
        });

    }

}
