package com.example.vinaykl.bs2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class changepass extends AppCompatActivity {

    String pass1;
    String pass2;
    SQLiteDatabase db;
    String PatientName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepass);

        final EditText password =  (EditText) findViewById(R.id.pass1);
        final EditText password1 =  (EditText) findViewById(R.id.pass2);

        Bundle bundle = getIntent().getExtras();
        PatientName = bundle.getString("pname");



        Button create = (Button) findViewById(R.id.update);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass1 = password.getText().toString();
                pass2 = password1.getText().toString();
                if( !(pass1.equals(pass2))){
                    Toast.makeText(changepass.this," PASSWORDS DO NOT MATCH",Toast.LENGTH_SHORT).show();
                } else{
                    try{
                        Context context = getApplicationContext();
                        String s = String.valueOf(context.getDatabasePath("vkl.db"));
                        db = getApplicationContext().openOrCreateDatabase(s, MODE_APPEND,null);

                        ContentValues cv = new ContentValues();
                        String col = "Ppassword";
                        cv.put(col, pass1);
                        String TableName = "Patient";
                        db.update(TableName, cv, PatientName, null);


                    }
                    catch (SQLiteException e) {

                    }
                    finally {

                    }
                }
            }
        });
    }
}


/*Context context = getApplicationContext();
            String s = String.valueOf(context.getDatabasePath("vkl.db"));
            db = getApplicationContext().openOrCreateDatabase(s, MODE_APPEND,null);

            ContentValues cv = new ContentValues();

            String col = "Status_" + timeslot;
            String date = dateField;
            cv.put(col, Pid);


            //These Fields should be your String values of actual column names
            db.update(TableName, cv, "DateAvailable = " + date, null);

            */