package com.example.vinaykl.bs2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class doctorHome extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public Bundle b;
    SQLiteDatabase db;
    String DocName;
    private static String TableName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);
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

        //TextView textView = (TextView) findViewById(R.id.doct);
        //String doctype = getIntent().getExtras().getString("Doc");
        //textView.setText(doctype);

        Intent intent = getIntent();
        b = intent.getExtras();
        TableName = b.getString("Name");
        DocName = b.getString("Doc");

        try{
            Context context = getApplicationContext();
            String s = String.valueOf(context.getDatabasePath("vkl.db"));

            db = getApplicationContext().openOrCreateDatabase(s,MODE_APPEND,null);
            //db = SQLiteDatabase.openOrCreateDatabase(Environment.getExternalStorageDirectory() + "/databaseFolder/myDB", null);
            db.beginTransaction();
            try {

                System.out.println("TABLE BEING CREATED" + " " + TableName);
                String query = "create table if not exists " + TableName + " ("
                        + " DateAvailable TEXT,"
                        + " Status_0 TEXT,"
                        + " Status_1 TEXT,"
                        + " Status_2 TEXT,"
                        + " Status_3 TEXT,"
                        + " Status_4 TEXT,"
                        + " Status_5 TEXT,"
                        + " Status_6 TEXT,"
                        + " Status_7 TEXT,"
                        + " Status_8 TEXT,"
                        + " Status_9 TEXT,"
                        + " Status_10 TEXT,"
                        + " Status_11 TEXT,"
                        + " Status_12 TEXT,"
                        + " Status_13 TEXT,"
                        + " Status_14 TEXT,"
                        + " Status_15 TEXT,"
                        + " Status_16 TEXT,"
                        + " Status_17 TEXT,"
                        + " Status_18 TEXT,"
                        + " Status_19 TEXT,"
                        + " Status_20 TEXT,"
                        + " Status_21 TEXT,"
                        + " Status_22 TEXT,"
                        + " Status_23 TEXT ); ";
                //perform your database operations here ...

                db.execSQL(query);

                db.setTransactionSuccessful(); //commit your changes
            }
            catch (SQLiteException e) {

                System.out.println("********SOME THING WRONG******");
            }
            finally {
                db.endTransaction();
            }
        }catch (SQLException e){
            System.out.println("********SOME THING WRONG OUTER******");
            Toast.makeText(doctorHome.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        Button add = (Button) findViewById(R.id.AddAvailability);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateDB();

            }
        });
        /*
        Button view = (Button) findViewById(R.id.ViewAvailability);
        add.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String DocId = "vicky";
                // Rest all will remain the same
                Intent intent = new Intent(doctorHome.this, ShowRecords.class);
                Bundle b = new Bundle();
                b.putString("Doc",DocName);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        }); */

    }

    public void updateDB()
    {
        String[] status = new String[24];
        TimePicker startTime;
        startTime = (TimePicker) findViewById(R.id.FromAvailable);
        startTime.setIs24HourView(true);
        int startHour = startTime.getCurrentHour();

        TimePicker endTime;
        endTime = (TimePicker) findViewById(R.id.ToAvailable);
        endTime.setIs24HourView(true);
        int endHour = endTime.getCurrentHour();



        DatePicker date;
        date = (DatePicker) findViewById(R.id.DateAvailable);
        int year = date.getYear();
        int month = date.getMonth();
        month = month+1;
        String m;
        if(month < 10){
            m = "0"+String.valueOf(month);
        }else{
            m = String.valueOf(month);
        }

        int day = date.getDayOfMonth();
        String d;
        if( day < 10){
            d = "0"+ String.valueOf(day);
        }else{
            d = String.valueOf(day);
        }
        String dateField = String.valueOf(m) + String.valueOf(d) + String.valueOf(year);

        System.out.println( "****" + dateField + "****" + startHour + "****" + endHour );

        for(int i=0; i<status.length; i++)
        {
            if(i>=startHour && i<endHour)
                status[i] = "Available";
            else
                status[i] = "Not Available";
        }


        //DELETE LATER
        for(int i=0; i<status.length; i++)
        {
            System.out.print( i + "  " + status[i] + " ; ");
        }
        System.out.println();
        //BETWEEN
        try
        {
            Context context = getApplicationContext();
            String s = String.valueOf(context.getDatabasePath("vkl.db"));
            db = getApplicationContext().openOrCreateDatabase(s,MODE_APPEND,null);
            System.out.println("**** Trying to insert" + TableName + "*****");
            String query1 = "INSERT INTO " + TableName +
                    " (DateAvailable, Status_0, Status_1, Status_2, Status_3, Status_4, Status_5, Status_6, Status_7, Status_8, Status_9, Status_10, Status_11,"
                    + " Status_12, Status_13, Status_14, Status_15, Status_16, Status_17, Status_18, Status_19, Status_20, Status_21, Status_22, Status_23)"
                    + "VALUES ('"
                    + dateField + "','" + status[0] +"','" + status[1] + "','" + status[2] + "','" + status[3] + "','" + status[4] + "','" + status[5]
                    + "','" + status[6] + "','" + status[7] + "','"  + status[8] + "','" + status[9] + "','" + status[10] + "','"  + status[11]
                    + "','" + status[12] + "','" + status[13] + "','" + status[14] + "','" + status[15] + "','" + status[16] + "','" + status[17]
                    + "','" + status[18] +"','" + status[19] + "','" + status[20] + "','" + status[21] +  "','" + status[22] + "','"+ status[23] + "');";
            db.execSQL(query1);
            Toast.makeText(getApplicationContext(),"Availability Added",Toast.LENGTH_SHORT).show();;

            System.out.println("****DONE INSERTING****");
        }
        catch (SQLiteException e)
        {

        }
        finally
        {

        }
    }
}
