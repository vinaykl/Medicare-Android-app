package com.example.vinaykl.bs2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowDetails extends AppCompatActivity {

    String patientname;
    SQLiteDatabase db;

    String age;
    String Sex;
    String BloodGroup;
    String Conditions;
    private ListView lv;
    Button calories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        patientname = getIntent().getExtras().getString("Patient");


        Context context = getApplicationContext();
        String s = String.valueOf(context.getDatabasePath("vkl.db"));
        db = getApplicationContext().openOrCreateDatabase(s,MODE_APPEND,null);
        String Ptable = "Patient";
        //db = SQLiteDatabase.openOrCreateDatabase(Environment.getExternalStorageDirectory() + "/databaseFolder/myDB", null);
        Cursor cursor = db.rawQuery("SELECT * FROM "+ Ptable ,null);
        cursor.moveToFirst();
        System.out.println("reading from database");
        while( cursor.isAfterLast() == false){
            String pname = cursor.getString(cursor.getColumnIndex("Pname"));
            System.out.println(" Name is "+ pname);
            System.out.println(" comapred with "+ patientname);
            if( patientname.equals(pname)){
                age = cursor.getString(cursor.getColumnIndex("Page"));
                Sex = cursor.getString(cursor.getColumnIndex("Psex"));
                BloodGroup = cursor.getString(cursor.getColumnIndex("Pblood"));
                Conditions = cursor.getString(cursor.getColumnIndex("Ppreconditions"));
            }
            cursor.moveToNext();

        }

        TextView textView1 = (TextView) findViewById(R.id.pName);
        textView1.setText(patientname);
        TextView textView2 = (TextView) findViewById(R.id.pAge);
        textView2.setText(age);
        TextView textView3 = (TextView) findViewById(R.id.pSex);
        textView3.setText(Sex);
        TextView textView4 = (TextView) findViewById(R.id.pBloodGroup);
        textView4.setText(BloodGroup);

        ArrayList<String> ConditionsList = new ArrayList<String>();
        String[] splited = Conditions.split("[\\|\\s]+");
        for ( int i =0 ; i< splited.length ; i++){
            ConditionsList.add(splited[i]);
        }

        lv = (ListView) findViewById(R.id.ListView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                ConditionsList );

        lv.setAdapter(arrayAdapter);


        calories = (Button) findViewById(R.id.cal);
        calories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ResultsActivity = new Intent(ShowDetails.this, Show_Calories.class);
                Bundle b = new Bundle();

                b.putString("pname",patientname);
                ResultsActivity.putExtras(b);
                startActivity(ResultsActivity);
                //finish();
            }
        });




    }
}
