package com.example.vinaykl.bs2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import  java.util.Calendar;

public class Doctor_View_Appointments extends AppCompatActivity
{
    Bundle b1;
    String TableName;
    String temp;

    SQLiteDatabase db;
    String today_date;
    Map <String, Integer> appointments = new HashMap<String, Integer>();
    ArrayList<String> Pid_List = new ArrayList<String>();
    int hour;
    LoginDataBase_adapter Patient_loginDataBase_Adapter;
    ListView listView;
    //Button b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__view__appointments);
        /**Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);

         FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
         fab.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        .setAction("Action", null).show();
        }
        });**/

        System.out.println("hi malli");
        Intent intent = getIntent();
        b1 = intent.getExtras();
        TableName = b1.getString("TableName");
        Patient_loginDataBase_Adapter = new LoginDataBase_adapter(this);
        Patient_loginDataBase_Adapter = Patient_loginDataBase_Adapter.open();


        try {
            Context context = getApplicationContext();
            String s = String.valueOf(context.getDatabasePath("vkl.db"));
            db = getApplicationContext().openOrCreateDatabase(s, MODE_APPEND, null);
            System.out.println("hi malli1");
            DateFormat df = new SimpleDateFormat("MMddyyyy");
            Date today = new Date();

            /*int day=today.getDay();
            int month = today.getMonth();
            int year = today.getYear();
            System.out.println(day+""+month+""+year);*/

            today_date = df.format(today);
            //today_date=Integer.toString(month)+Integer.toString(day)+Integer.toString(year);
            System.out.println("date" + today_date);


            Cursor res = db.rawQuery("select * from " + TableName + " where DateAvailable =" + today_date , null);
            System.out.println("count of appo "+res.getCount());
            if (res.getCount() < 1) // UserName Not Exist
            {
                System.out.println("hi inside if");
                res.close();
                Toast.makeText(Doctor_View_Appointments.this, "No appointments today", Toast.LENGTH_LONG).show();

            } else {
                System.out.println("hi inside else");
                res.moveToFirst();
                String[] status = new String[24];
                for (int i = 0; i < status.length; i++) {
                    System.out.println("hi malli2");
                    String var = "Status_";
                    var = var + String.valueOf(i);
                    status[i] = res.getString(res.getColumnIndex(var));

                }
                System.out.println("hi malli3");
                for (int i = 0; i < status.length; i++) {
                    if (status[i].equals("Available") || status[i].equals("Not Available")) {
                        continue;
                    } else {
                        appointments.put(status[i], i);
                        Pid_List.add(status[i]);

                    }
                }
                final ArrayAdapter adapter = new ArrayAdapter<String>(this,
                        R.layout.activity_listview, Pid_List);
                listView = (ListView) findViewById(R.id.DisplayAvailability);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                        temp = Pid_List.get(position);
                        Calendar now = Calendar.getInstance();
                        hour = now.get(Calendar.HOUR_OF_DAY);
                        if (hour == appointments.get(temp))
                        {
                            String data=(String)parent.getItemAtPosition(position);
                            String prisc = "priscribe "+data;
                            String phone_no = Patient_loginDataBase_Adapter.getPhone_Number(temp);
                            System.out.println("ph : "+phone_no);
                            //added by anoop
                            System.out.println("hihi");
                            Intent chatORvideo = new Intent(Doctor_View_Appointments.this,ChatOrVideo.class);
                            Bundle bcv = new Bundle();
                            bcv.putString("phone_no",phone_no);
                            Toast.makeText(getApplicationContext(),phone_no,Toast.LENGTH_SHORT).show();
                            chatORvideo.putExtras(bcv);
                            startActivity(chatORvideo);

                            //till here added by anoop

                            //dirctchat(phone_no);
                            Pid_List.remove(position);
                            // listView.notifyDataSetChanged();
                            adapter.notifyDataSetChanged();
                            android.support.design.widget.CoordinatorLayout lnr = (android.support.design.widget.CoordinatorLayout) findViewById(R.id.but);



                            Button b2 = new Button(Doctor_View_Appointments.this);
                            b2.setText(prisc);

                            lnr.addView(b2);
                            b2.setOnClickListener(new View.OnClickListener() {

                                public void onClick(View v) {
                                    updating(TableName, today_date, temp, hour);
                                    Intent intentSignUP = new Intent(getApplicationContext(), Priscription_Submission.class);
                                    //intentSignUP.putExtra("Doc", doctorName);
                                    startActivity(intentSignUP);

                                }

                            });
                            //b2.setVisibility(View.GONE);


                            //Toast.makeText(Doctor_View_Appointments.this, "still time is there", Toast.LENGTH_LONG).show();

                        }
                        else
                        {
                            Toast.makeText(Doctor_View_Appointments.this, "still time is there", Toast.LENGTH_LONG).show();
                        }
                        //updating(TableName, today_date, temp, hour);

                    }
                });



            }
        }

        catch(SQLiteException e)
        {

        }
        finally
        {

        }



    }
    public  void updating(String TableName,String todaydate,String Pname,int status_string )
    {
        Context context = getApplicationContext();
        String s = String.valueOf(context.getDatabasePath("vkl.db"));
        db = getApplicationContext().openOrCreateDatabase(s,MODE_APPEND,null);
        String Not_Available="Not Available";
        System.out.println("**** Trying to insert" + TableName + "*****");
        //String query1 = "INSERT INTO " + TableName +" where DateAvailable="+todaydate+" and "+"Status_" + status_string+"="+Pname;
        String query1 = "UPDATE " + TableName +" SET "+"Status_" + status_string+ " = "+"'"+"Not Available"+"'"+" WHERE DateAvailable="+todaydate+" AND "+"Status_" + status_string+" = "+"'"+Pname+"'";
        System.out.println("query : "+query1);
        db.execSQL(query1);
    }
    /*
    public void dirctchat(String phone_no)
    {
        Uri mUri = Uri.parse("smsto:+1"+phone_no);
        Intent mIntent = new Intent(Intent.ACTION_SENDTO, mUri);
        mIntent.setPackage("com.whatsapp");
        mIntent.putExtra("sms_body", "The text goes here");
        mIntent.putExtra("chat",true);
        startActivity(mIntent);
    }*/

}
