package com.example.vinaykl.bs2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

/**
 * Created by memal on 11/19/2016.
 */

public class LoginDataBase_adapter
{
    static final String DATABASE_NAME = "vkl.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    // TODO: Create public field for each column in your table.
    // SQL Statement to create a new database.
    //static final String DATABASE_CREATE = "create table "+"LOGIN"+
    //     "( " +"DID"+" integer primary key autoincrement,"+ "USERNAME  text,PASSWORD text); ";
    static final String DATABASE_CREATE_D = "create table "+"Doctor"+
            "( " +"Did"+" integer primary key autoincrement," +
            "Dname TEXT," +
            "Dage INTEGER," +
            "Dsex text," +
            "Dspecilization TEXT," +
            "Dnum INTEGER," +
            "Dphone TEXT," +
            "Demail TEXT," +
            "Dpassword TEXT,"+
            "Drating INTEGER); ";

    static final String DATABASE_CREATE_P = "create table "+"Patient"+
            "( " +"Pid"+" integer primary key autoincrement," +
            "Pname TEXT," +
            "Page INTEGER," +
            "Psex text," +
            "Pblood TEXT," +
            "Ppreconditions TEXT," +
            "Pphone TEXT," +
            "Pgaurdianemail TEXT,"+
            "Pemail TEXT," +
            "Ppassword TEXT); ";
    static final String DATABASE_CREATE_PRISC = "create table "+"PRESCRIPTION"+
            "( " +"PRIS_ID"+" integer primary key autoincrement,"+
            "DOCTORNAME  text,PATIENTNAME text," +
            "PID TEXT,"+
            "DID TEXT,"+
            "PATIENTAGE text,TABLET text," +
            "FEES TEXT ,"+
            "FOREIGN KEY(DID) REFERENCES LOGINDOC(Did)" +
            "FOREIGN KEY(PID) REFERENCES LOGINPAT(Pid)); ";

    // Variable to hold the database instance
    public SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private DataBaseHelper dbHelper;
    public LoginDataBase_adapter(Context _context)
    {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public LoginDataBase_adapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        db.close();
    }

    public  SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }

    public void insertEntry_doctor(String userName,String password,int age,String Dsex,String Dspecilization,int Dnum,
                           String Dphone,String Demail,int Drating)
    {
        ContentValues newValues = new ContentValues();
        //newValues.put("USERNAME", userName);
        //newValues.put("PASSWORD",password);
        newValues.put("Dname", userName);
        newValues.put("Dage", age);
        newValues.put("Dsex",Dsex);
        newValues.put("Dspecilization",Dspecilization);
        newValues.put("Dnum", Dnum);


        newValues.put("Dphone", Dphone);
        newValues.put("Demail",Demail);

        newValues.put("Dpassword",password);
        newValues.put("Drating",Drating);




        // Insert the row into your table
        db.insert("Doctor", null, newValues);
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }

    public void insertEntry_patient(String userName,String password,int age,String Psex,String Pblood,String Pprecondition
            ,String Pphone,String Pgaurdianemail,String Pemail)
    {
        ContentValues newValues = new ContentValues();
        //newValues.put("USERNAME", userName);
        //newValues.put("PASSWORD",password);
        newValues.put("Pname", userName);
        newValues.put("Page", age);
        newValues.put("Psex",Psex);
        newValues.put("Pblood",Pblood);
        newValues.put("Ppreconditions", Pprecondition);


        newValues.put("Pphone", Pphone);
        newValues.put("Pgaurdianemail", Pgaurdianemail);
        newValues.put("Pemail",Pemail);

        newValues.put("Ppassword",password);




        // Insert the row into your table
        db.insert("Patient", null, newValues);
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }

    public String getSinlgeEntry_patient(String pemail)
    {
        Cursor cursor=db.query("Patient", null, " Pemail=?", new String[]{pemail}, null, null, null);
        //Cursor cursor=db.rawQuery("SELECT Dpassword FROM  LOGIN   where Dname=?", null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password= cursor.getString(cursor.getColumnIndex("Ppassword"));
        cursor.close();
        return password;
    }
    public String subcription_pat_check(String patientname)
    {
        ;
        Cursor cursor=db.query("Patient", null, " Pname=?", new String[]{patientname}, null, null, null);
        //Cursor cursor=db.rawQuery("SELECT Dpassword FROM  LOGIN   where Dname=?", null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String res= cursor.getString(cursor.getColumnIndex("Pid"));
        cursor.close();

        return res;

    }

    public String getPatientName(String pemail)
    {
        Cursor cursor=db.query("Patient", null, " Pemail=?", new String[]{pemail}, null, null, null);
        //Cursor cursor=db.rawQuery("SELECT Dpassword FROM  LOGIN   where Dname=?", null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String name= cursor.getString(cursor.getColumnIndex("Pname"));
        cursor.close();
        return name;
    }

    public String getPhone_Number(String pid)
    {
        Cursor cursor=db.query("Patient", null, " Pname=?", new String[]{pid}, null, null, null);
        //Cursor cursor=db.rawQuery("SELECT Dpassword FROM  LOGIN   where Dname=?", null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String phone_no= cursor.getString(cursor.getColumnIndex("Pphone"));
        cursor.close();
        return phone_no;
    }

    public String getSinlgeEntry_doctor(String demail)
    {
        Cursor cursor=db.query("Doctor", null, " Demail=?", new String[]{demail}, null, null, null);
        //Cursor cursor=db.rawQuery("SELECT Dpassword FROM  LOGIN   where Dname=?", null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password= cursor.getString(cursor.getColumnIndex("Dpassword"));
        cursor.close();
        return password;
    }
    public String subcription_doc_check(String docname)
    {

        Cursor cursor=db.query("Doctor", null, " Dname=?", new String[]{docname}, null, null, null);
        //Cursor cursor=db.rawQuery("SELECT Dpassword FROM  LOGIN   where Dname=?", null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String res= cursor.getString(cursor.getColumnIndex("Did"));
        cursor.close();

        return res;

    }

    public String getDoctorName(String Demail)
    {
        Cursor cursor=db.query("Doctor", null, " Demail=?", new String[]{Demail}, null, null, null);
        //Cursor cursor=db.rawQuery("SELECT Dpassword FROM  LOGIN   where Dname=?", null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String res= cursor.getString(cursor.getColumnIndex("Dname"));
        cursor.close();

        return res;
    }
    public void insert_prisc_Entry(String resdoc,String respat,String docname,String patientname,String patientage,String tabletlist,String fees)
    {
        System.out.println("Fees "+ fees);
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("doctorname", docname);
        newValues.put("patientname",patientname);
        newValues.put("patientage",patientage);
        newValues.put("tablet",tabletlist);
        newValues.put("PID",respat);
        newValues.put("DID",resdoc);
        newValues.put("FEES",fees);

        // Insert the row into your table
        db.insert("PRESCRIPTION", null, newValues);
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }



}
