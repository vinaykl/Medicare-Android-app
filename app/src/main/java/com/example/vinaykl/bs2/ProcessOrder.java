package com.example.vinaykl.bs2;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class ProcessOrder extends Service {

    SQLiteDatabase db;
    Bundle b5;
    boolean found;
    public ProcessOrder() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {
        super.onCreate();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {

        String pid ;
        ArrayList<String> prescribedDrug = new ArrayList<>();
        ArrayList<Integer> prescribedQuantiy = new ArrayList<>();
        found = false;


        b5 = intent.getExtras();
        pid = b5.getString("Pid");
        prescribedDrug = b5.getStringArrayList("PrescribedDrug");
        prescribedQuantiy = b5.getIntegerArrayList("PrescribedQuantity");

        try
        {
            Context context = getApplicationContext();
            String s = String.valueOf(context.getDatabasePath("Anoop.db"));
            db = getApplicationContext().openOrCreateDatabase(s, MODE_APPEND,null);
            db.beginTransaction();

            Cursor res =  db.rawQuery("select * from " + "CVSStore" + "", null);
            res.moveToFirst();
            String sName = null;
            ArrayList<String> sNames = new ArrayList<>();
            while( res.isAfterLast() == false)
            {
                sName = res.getString(res.getColumnIndex("StoreName"));
                sNames.add(sName);
                res.moveToNext();
            }



            for(int j =0;j<sNames.size(); j++)
            {
                sName = sNames.get(j);
                Cursor res1 =  db.rawQuery( "select * from " + sName + "", null );
                res1.moveToFirst();
                HashMap<String,Integer> storeDetails = new HashMap<>();
                HashMap<String,Integer> drugPrice = new HashMap<>();

                while( res1.isAfterLast() == false)
                {
                    String drugsCVS;
                    int quantiyCVS;
                    int price;

                    price = res1.getInt(res1.getColumnIndex("Price"));
                    drugsCVS = res1.getString(res1.getColumnIndex("DrugName"));
                    quantiyCVS = res1.getInt(res1.getColumnIndex("Quantity"));

                    drugPrice.put(drugsCVS,price);
                    storeDetails.put(drugsCVS,quantiyCVS);
                    res1.moveToNext();
                }


                int flag=0;
                for(int i=0;i< prescribedDrug.size();i++)
                {

                    if(storeDetails.containsKey(prescribedDrug.get(i)) && found == false)
                    {

                        if(storeDetails.get(prescribedDrug.get(i)) < prescribedQuantiy.get(i) )
                        {
                            break;
                        }
                        else
                        {
                            flag++;
                            if(flag == prescribedDrug.size())
                            {
                                System.out.println("Placed order at" + sName);
                                Toast.makeText(this,"Placed order at" + sName,Toast.LENGTH_SHORT).show();
                                found = true;
                                //UPDATE STORE DATA

                                ContentValues cv1 = new ContentValues();
                                String col = "Quantity";


                                //These Fields should be your String values of actual column names
                                for(int r=0;r<prescribedDrug.size();r++)
                                {
                                    int newQuantity;
                                    newQuantity = storeDetails.get(prescribedDrug.get(r)) - prescribedQuantiy.get(r);
                                    int price1 = drugPrice.get(prescribedDrug.get(r));
                                    cv1.put(col, newQuantity);
                                    db.update(sName, cv1, "Price = " + price1, null);
                                }
                                break;
                            }
                        }
                    }
                    else
                    {
                        break;
                    }

                }

            }
            if(found == false){
                System.out.println("OUT OF STOCK");
                Toast.makeText(this,"OUT OF STOCK",Toast.LENGTH_SHORT).show();
            }


            db.setTransactionSuccessful();
        }
        catch (SQLiteException e)
        {

        }
        finally
        {
            db.endTransaction();
        }

        return START_STICKY;
    }



}