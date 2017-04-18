package com.example.vinaykl.bs2;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Payment_Submit extends AppCompatActivity {

    String dbName="vkl.db";
    SQLiteDatabase db;
    LoginDataBase_adapter Patient_loginDataBase_Adapter;
    Button Pay_and_Rate;
    TextView textView,doc_name,textView6;

    String val,val1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_payment__submission);
        Bundle bundle = getIntent().getExtras();
        val = bundle.getString("DEMAIL");
        val1 = bundle.getString("PEMAIL");
        Patient_loginDataBase_Adapter = new LoginDataBase_adapter(this);
        Patient_loginDataBase_Adapter = Patient_loginDataBase_Adapter.open();
        String name = Patient_loginDataBase_Adapter.getPatientName(val1);
        String docname = Patient_loginDataBase_Adapter.getDoctorName(val);
        doc_name=(TextView)findViewById(R.id.editDoctorName);
        doc_name.setText("DOCTOR NAME : "+docname);
        System.out.println(name);
        textView=(TextView)findViewById(R.id.textView);
        textView6=(TextView)findViewById(R.id.textView6);
        //String amount = get_doctor_amount(name);


        Pay_and_Rate = (Button)findViewById(R.id.buttonAuthorize);
        try {

            db = getApplicationContext().openOrCreateDatabase(dbName, getApplicationContext().MODE_APPEND, null);
            if (db == null) {
                Toast.makeText(getApplicationContext(), "Erron in DB0", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (SQLException E) {
            Toast.makeText(this, E.getMessage(), Toast.LENGTH_LONG).show();
        } catch (Exception E) {
            Toast.makeText(getApplicationContext(), E.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            //db.endTransaction();
        }

        String d="null";

        //String query="SELECT FEES FROM PRESCRIPTION WHERE PATIENTNAME = '"+patient_name+"'";
        Cursor c = db.rawQuery("SELECT * FROM PRESCRIPTION WHERE PATIENTNAME = '"+name+"'",null);
        System.out.println(name);
        c.moveToFirst();
        //while(c.moveToNext())
        while(c.isAfterLast() == false)
        {
            d = c.getString(c.getColumnIndex("FEES"));
            System.out.println("string "+d);
            c.moveToNext();
        }
        c.close();
        textView.setText("AMOUNT TO BE PAID : $"+d);



        Pay_and_Rate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {



                //Toast.makeText(getApplicationContext(),"Average Rating = "+tot, Toast.LENGTH_SHORT).show();


                insert_ratedr(val,val1);

                Intent intent = new Intent(getApplicationContext(),DoctorRating_Activity.class);
                Bundle bundle = getIntent().getExtras();
                intent.putExtra("DEMAIL", val);
                String val = bundle.getString("PEMAIL");
                intent.putExtra("PEMAIL", val1);
                startActivity(intent);


            }

        });


    }

    public void insert_ratedr(String dname,String pname){
        try {

            db.execSQL("UPDATE APPOINTMENTS SET AMOUNT = 'PAID' WHERE DID = '"+ dname+"'"+"AND PID = '"+pname+"'");
            //Toast.makeText(this,"Inserted",Toast.LENGTH_SHORT).show();
            Cursor c = db.rawQuery("Select Drating from Doctor Where Demail = '"+dname+"'",null);
            while(c.moveToNext()) {
                String d = c.getString(c.getColumnIndex("Drating"));
                System.out.println("Doctor:" + d);
            }




        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public String get_doctor_amount(String patient_name)
    {
        String d="";
        //String query="SELECT FEES FROM PRESCRIPTION WHERE PATIENTNAME = '"+patient_name+"'";
        Cursor c = db.rawQuery("SELECT FEES FROM PRESCRIPTION WHERE PATIENTNAME = '"+patient_name+"'",null);
        c.moveToFirst();
        while(c.moveToNext())
        {
            d = c.getString(c.getColumnIndex("FEES"));

        }
        c.close();
        return d;

    }

}
