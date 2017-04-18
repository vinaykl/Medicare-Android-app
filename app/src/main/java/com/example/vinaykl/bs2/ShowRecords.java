package com.example.vinaykl.bs2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ShowRecords extends AppCompatActivity {

    SQLiteDatabase db;
    ArrayList<String> plist = new ArrayList<String>();
    public Bundle b;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_records);

        String doctype = getIntent().getExtras().getString("Doc");
        String tablename = getIntent().getExtras().getString("Name");
        System.out.println(tablename+ " "+ doctype);

        // vkl testing

        Context context = getApplicationContext();
        String s = String.valueOf(context.getDatabasePath("vkl.db"));

        // Read the database

        db = getApplicationContext().openOrCreateDatabase(s,MODE_APPEND,null);
        String Dtable = tablename;
        //db = SQLiteDatabase.openOrCreateDatabase(Environment.getExternalStorageDirectory() + "/databaseFolder/myDB", null);
        Cursor cursor = db.rawQuery("SELECT * FROM "+ Dtable ,null);
        cursor.moveToFirst();
        while( cursor.isAfterLast() == false){
            String patientname = cursor.getString(cursor.getColumnIndex("patient"));
            System.out.println(patientname);

            plist.add(cursor.getString(cursor.getColumnIndex("patient")));

            cursor.moveToNext();

        }
        // Now display in the listview
        lv = (ListView) findViewById(R.id.listviewres);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                plist );

        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // Get the selected value
                String var = plist.get(position);
                // get the name of the patient

                // Start the booking page, pass the doc ID
                Intent PatienDetails = new Intent(ShowRecords.this, ShowDetails.class);
                Bundle b = new Bundle();
                System.out.println("Sending "+ var);
                b.putString("Patient",var);
                PatienDetails.putExtras(b);
                startActivity(PatienDetails);




            }
        });








        // vkl testing done
    }
}
