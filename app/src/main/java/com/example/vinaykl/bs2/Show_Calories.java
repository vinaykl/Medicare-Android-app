package com.example.vinaykl.bs2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Show_Calories extends AppCompatActivity {

    private ListView lv2;
    String patientname;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__calories);


        Context context = getApplicationContext();
        String s = String.valueOf(context.getDatabasePath("vkl.db"));
        db = getApplicationContext().openOrCreateDatabase(s,MODE_APPEND,null);

        // vkl code for fit data
        ArrayList<String> calories = new ArrayList<String>();

        patientname = getIntent().getExtras().getString("pname");
        String table = patientname+"_fit";

        System.out.println(" Trying to read from database "+ table);

        //db = SQLiteDatabase.openOrCreateDatabase(Environment.getExternalStorageDirectory() + "/databaseFolder/myDB", null);
        Cursor cursor1 = db.rawQuery("SELECT * FROM "+ table ,null);
        cursor1.moveToFirst();
        System.out.println("reading from database");
        while( cursor1.isAfterLast() == false){
            System.out.println("get record");
            String start = cursor1.getString(cursor1.getColumnIndex("start"));
            String end = cursor1.getString(cursor1.getColumnIndex("end"));
            String Calories1 = cursor1.getString(cursor1.getColumnIndex("calories"));
            String result = start + " to "+ end + " :- " + Calories1;
            System.out.println(result);
            calories.add(result);
            cursor1.moveToNext();

        }


        lv2 = (ListView) findViewById(R.id.listviewfit);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                calories );

        lv2.setAdapter(arrayAdapter1);
    }


}
