package com.example.vinaykl.bs2;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class CVSStore extends AppCompatActivity {

    SQLiteDatabase db;
    public String storeName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cvsstore);
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



        Button create = (Button) findViewById(R.id.CreateStore);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try{

                    EditText store_Name = (EditText) findViewById(R.id.CVSStoreName);
                    storeName = store_Name.getText().toString();
                    Context context = getApplicationContext();
                    String s = String.valueOf(context.getDatabasePath("Anoop.db"));

                    db = getApplicationContext().openOrCreateDatabase(s,MODE_APPEND,null);
                    db.beginTransaction();
                    try {


                        String query3 = "create table if not exists " + "CVSSTORE " + " ("
                                + "StoreName TEXT);";
                        //perform your database operations here ...

                        db.execSQL(query3);
                        System.out.println("TABLE CREATED CVS STORE");

                    }
                    catch (SQLiteException e)
                    {

                        System.out.println("********SOME THING WRONG WHILE CREATING******");
                    }
                    finally {
                        // db.endTransaction();
                    }


                    try {


                        String query4 = "create table if not exists " + storeName + " ("
                                + "Price REAL,"
                                + "DrugName TEXT,"
                                + "Quantity INTEGER);";
                        //perform your database operations here ...

                        db.execSQL(query4);
                        System.out.println("TABLE CREATED " + storeName);
                        Toast.makeText(CVSStore.this,"TABLE CREATED " + storeName,Toast.LENGTH_SHORT).show();
                        db.setTransactionSuccessful(); //commit your changes
                    }
                    catch (SQLiteException e)
                    {

                        System.out.println("********SOME THING WRONG WHILE CREATING******");
                    }
                    finally {
                        db.endTransaction();
                    }



                }catch (SQLException e)
                {
                    System.out.println("********SOME THING WRONG OUTER WHILE OPENING******");
                    //Toast.makeText(CVSStore.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }


                try
                {
                    Context context = getApplicationContext();
                    String s = String.valueOf(context.getDatabasePath("Anoop.db"));
                    db = getApplicationContext().openOrCreateDatabase(s,MODE_APPEND,null);
                    db.beginTransaction();

                    String query5 = "INSERT INTO " + "CVSSTORE" +
                            " (StoreName) "
                            + "VALUES ('"
                            + storeName + "');";
                    db.execSQL(query5);


                    String[] drugname = new String[5];
                    int rand1 = 0;

                    //DELETE THIS

                    int[] price = new int[5];
                    if(storeName.equals("store1"))
                    {
                        rand1 = 5;
                        drugname[0] = "Crocin"; drugname[1] = "Sinerest"; drugname[2] = "Cetrezin";
                        drugname[3] = "Digein"; drugname[4] = "Dolo650";
                        price[0] = 45; price[1] = 60; price[2] = 28; price[3] = 80;price[4] = 25;
                    }

                    else
                    {
                        drugname[0] = "Crocin"; drugname[1] = "Sinerest"; drugname[2] = "Cetrezin";
                        drugname[3] = "Digein"; drugname[4] = "Dolo650";
                        price[0] = 45; price[1] = 60; price[2] = 28; price[3] = 80;price[4] = 25;
                        rand1 = 10;
                    }





                    for(int i=0;i< drugname.length; i++)
                    {
                        String query2 = "INSERT INTO " + storeName +
                                " (Price,DrugName,Quantity) "
                                + "VALUES ('"
                                + price[i]   + "','" +drugname[i]+  "','" + rand1  +  "');";
                        db.execSQL(query2);
                    }


                    Cursor res1 =  db.rawQuery( "select * from " + storeName + "", null );
                    res1.moveToFirst();
                    while( res1.isAfterLast() == false)
                    {
                        String drugsCVS;
                        int quantiyCVS;
                        int price1;
                        price1 = res1.getInt(res1.getColumnIndex("Price"));
                        drugsCVS = res1.getString(res1.getColumnIndex("DrugName"));
                        quantiyCVS = res1.getInt(res1.getColumnIndex("Quantity"));
                        res1.moveToNext();
                    }



                    db.setTransactionSuccessful();
                }
                catch (SQLiteException e)
                {
                    System.out.println("SOMETHING WRONG WHILE INSERTING");
                }
                finally
                {
                    db.endTransaction();
                }

            }
        });


    }

}
