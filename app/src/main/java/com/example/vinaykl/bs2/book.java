package com.example.vinaykl.bs2;

import android.app.AlarmManager;
import android.app.PendingIntent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class book extends AppCompatActivity {

    SQLiteDatabase db,db1;
    public Bundle b2;
    String dateField;
    String Pid;
    String DocId;
    String dbName="vkl.db";
    private static String TableName = null;
    final ArrayList<String> availibility = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {

            db1 = getApplicationContext().openOrCreateDatabase(dbName, getApplicationContext().MODE_APPEND, null);
            if (db1 == null) {
                Toast.makeText(getApplicationContext(), "Erron in DB0", Toast.LENGTH_SHORT).show();
                return;
            }
            db1.execSQL("CREATE TABLE IF NOT EXISTS  APPOINTMENTS (PID TEXT, DID TEXT,RATE TEXT,AMOUNT TEXT);");
        } catch (SQLException E) {
            Toast.makeText(this, E.getMessage(), Toast.LENGTH_LONG).show();
        } catch (Exception E) {
            Toast.makeText(getApplicationContext(), E.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            //db.endTransaction();
        }

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DocId =getIntent().getExtras().getString("Doc");
        Pid = getIntent().getExtras().getString("Patient");
        db1.execSQL("INSERT INTO APPOINTMENTS (PID,DID,RATE,AMOUNT) VALUES('"+Pid+"','"+DocId+"','NR','NOT PAID');");
        //Toast.makeText(this,"Inserted into Appointments",Toast.LENGTH_SHORT).show();


        // vkl Code for accessing records for the doctor

        records_update_accessibility();
        record_update_insert();
        // vkl end


        //TextView textView = (TextView) findViewById(R.id.book);
        //textView.setText(DocId + "  " + Patient);

        //Intent intent = getIntent();
        //b2 = intent.getExtras();
        TableName = DocId+"_Availability";

        updateDB();




    }

    // vkl code to give acces to doctors about the patients
    public void records_update_accessibility(){
        try{

            Context context = getApplicationContext();
            String s = String.valueOf(context.getDatabasePath("vkl.db"));
            db = getApplicationContext().openOrCreateDatabase(s,MODE_APPEND,null);
            String tname = DocId+"_access";
            db.beginTransaction();
            try {
                String query = "create table " + tname + " ("
                        + " patient TEXT ); ";
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

        }

    }

    public void record_update_insert(){
        String Dtable =DocId+"_access"; ;
        db.execSQL("INSERT INTO " + Dtable + " (patient) VALUES('" + Pid + "');");

    }
    public void send_email(String body){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"vinay.k.lakshman@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
        i.putExtra(Intent.EXTRA_TEXT   , "Hello from Virtual Health Care, \n\n You have a new Appointment at " + body +"\n\n Thanks, \n Virtual Health Care \n" );
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(book.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateDB()
    {

        String id = "10302016";
        Context context = getApplicationContext();
        String s = String.valueOf(context.getDatabasePath("vkl.db"));
        db = getApplicationContext().openOrCreateDatabase(s,MODE_APPEND ,null);

        //db = helper.getReadableDatabase();
        System.out.println(TableName + " ** ");
        Cursor res =  db.rawQuery( "select * from " + TableName , null );
        //+ " where DateAvailable= " + id+ ""
        res.moveToFirst();

        while( res.isAfterLast() == false)
        {
            String[] status = new String[24];
            String temp;
            int flag=0;
            //pull date
            dateField = res.getString(res.getColumnIndex("DateAvailable"));
            System.out.println("pulling data from query ** **");
            for(int i=0;i<status.length ;i++)
            {
                String var = "Status_";
                var = var + String.valueOf(i);

                status[i] = res.getString(res.getColumnIndex(var));
                if(status[i].equals("Available"))
                    flag++;
            }






            System.out.println("BEFORE ** ** ");
            for(int i=0;i<status.length ;i++)
            {
                //push to array
                System.out.print(status[i] + " ** ");
            }


            int j=0;
//CHECKING FOR FROM TO TO TIME
            int start =0,end =0;
            for(int i=0;i<status.length ;i++)
            {
                //push to array
                if(status[i].equals("Available"))
                {
                    start = i;
                    while(status[i].equals("Available"))
                        i++;
                    end =i;
                    int counter = start;
                    for(int k=0;k<end-start;k++)
                    {

                        availibility.add(dateField + "|" + String.valueOf(counter) + ":00 to|" + String.valueOf(counter +1) + ":00");
                        counter++;
                    }

                }
            }


//DATE FROM TO TIME


            res.moveToNext();
        }//END OF WHILE


        System.out.println(availibility);

        System.out.println();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, availibility);
        ListView listView = (ListView) findViewById(R.id.DisplayAvailability);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String temp = availibility.get(position);
                //UPDATE DATABASE
                String[] date;
                db1.execSQL("INSERT INTO APPOINTMENTS (PID,DID,RATE) VALUES('"+Pid+"','"+DocId+"','NR');");
                //Toast.makeText(getApplication(), "Inserted"+ Pid + DocId, Toast.LENGTH_SHORT).show();
                date = temp.split("[\\|\\s]+");
                //System.out.println("suceess" + date[0] + " " + date[1] + " " + date[2]);
                String[] timeslot = date[1].split("[\\:\\s]+");
                System.out.println("update this" + timeslot[0]);
                System.out.println("******" + temp + "******");
                //String Pid = "PName";
                //CALLING BOOK SLOT
                bookSlot(timeslot[0], Pid, temp);

            }
        });





    }

    public void bookSlot(String timeslot,String Pid, String notification)
    {

        try
        {
            Context context = getApplicationContext();
            String s = String.valueOf(context.getDatabasePath("vkl.db"));
            db = getApplicationContext().openOrCreateDatabase(s, MODE_APPEND,null);

            ContentValues cv = new ContentValues();

            String col = "Status_" + timeslot;
            String date = dateField;
            cv.put(col, Pid);


            //These Fields should be your String values of actual column names
            db.update(TableName, cv, "DateAvailable = " + date, null);
            Toast.makeText(this,"Appointment Booked",Toast.LENGTH_SHORT).show();


            String[] notificationdate;
            notificationdate = notification.split("[\\|\\s]+");
            String splitnotificationdate = notificationdate[0];

            String hourof = notificationdate[1];
            String houroffinal[] = hourof.split("[\\:\\s]+");
            hourof = houroffinal[0];
            int hourcalc = Integer.parseInt(hourof);
            String ampm;

            if(hourcalc <13)
                ampm = "am";
            else
            {
                hourcalc = hourcalc - 12;
                ampm = "pm";
            }

            Calendar calendar = Calendar.getInstance();

            calendar.set(Calendar.MONTH, Integer.parseInt(splitnotificationdate.substring(0, 2))-1);
            calendar.set(Calendar.YEAR, Integer.parseInt(splitnotificationdate.substring(4, 8)));
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(splitnotificationdate.substring(2, 4)));

            //NOTIFICATION BEFORE 1 HOUR
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hourof)-1);
            calendar.set(Calendar.MINUTE, 10);
            calendar.set(Calendar.SECOND, 0);
            if(ampm.equals("pm"))
                calendar.set(Calendar.AM_PM, Calendar.PM);
            else
                calendar.set(Calendar.AM_PM, Calendar.AM);
/*
            calendar.set(Calendar.MONTH, 10);
            calendar.set(Calendar.YEAR, 2016);
            calendar.set(Calendar.DAY_OF_MONTH, 20);

            calendar.set(Calendar.HOUR_OF_DAY, 3);
            calendar.set(Calendar.MINUTE, 51);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.AM_PM, Calendar.PM);*/

            long timeinmill = calendar.getTimeInMillis();

            Intent myIntent = new Intent(book.this, MyReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(book.this, 0, myIntent,0);

            AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC, timeinmill, pendingIntent);

            String Doctormail = splitnotificationdate.substring(0, 2) + "/" +splitnotificationdate.substring(2, 4) + "/" +  splitnotificationdate.substring(4, 8) + " at " + hourof +":00" + " with patient:- "+ Pid;
            send_email(Doctormail);

            //Context context1 = getApplicationContext();
            //String s1 = String.valueOf(context1.getDatabasePath("Anoop.db"));
            //db = getApplicationContext().openOrCreateDatabase(s1,MODE_APPEND ,null);

            //db = helper.getReadableDatabase();
            //System.out.println(TableName + " ** ");
            Cursor res =  db.rawQuery( "select * from " + TableName + " where DateAvailable= " + date+ "", null );
            //+ " where DateAvailable= " + id+ ""
            res.moveToFirst();
            String[] status = new String[24];
            for(int i=0;i<status.length ;i++)
            {
                String var = "Status_";
                var = var + String.valueOf(i);

                status[i] = res.getString(res.getColumnIndex(var));

            }
            System.out.println();
            System.out.println("AFTER **");
            System.out.println(status.length);
            for(int i=0;i<status.length ;i++)
            {
                System.out.print(status[i] + " ** ** ");
            }



        }
        catch (SQLiteException e)
        {

        }
        finally
        {

        }
    }
}
