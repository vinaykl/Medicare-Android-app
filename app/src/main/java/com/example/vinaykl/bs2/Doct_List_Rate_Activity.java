package com.example.vinaykl.bs2;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class Doct_List_Rate_Activity extends AppCompatActivity  {

    final ArrayList<String> Doctor = new ArrayList<>();
    String d = null;
    String dbName="vkl.db";
    SQLiteDatabase db1;
    String val;
    ListView listView;
    LoginDataBase_adapter Patient_loginDataBase_Adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doct__list__rate_);
        Patient_loginDataBase_Adapter = new LoginDataBase_adapter(this);
        Patient_loginDataBase_Adapter = Patient_loginDataBase_Adapter.open();
        Bundle bundle = getIntent().getExtras();
        val = bundle.getString("PEMAIL");
        String name = Patient_loginDataBase_Adapter.getPatientName(val);
        listView =(ListView)findViewById(R.id.DisplayAvailability);
        try {

            db1 = getApplicationContext().openOrCreateDatabase(dbName, getApplicationContext().MODE_APPEND, null);
            if (db1 == null) {
                Toast.makeText(getApplicationContext(), "Erron in DB0", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException E) {
            Toast.makeText(this, E.getMessage(), Toast.LENGTH_LONG).show();
        } catch (Exception E) {
            Toast.makeText(getApplicationContext(), E.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            //db.endTransaction();
        }

            Cursor c = db1.rawQuery("SELECT * FROM APPOINTMENTS WHERE PID = '"+ name +"' AND RATE = 'NR'",null);
            if(c.getCount()<1){
                Toast.makeText(getApplicationContext(),"No Doctors",Toast.LENGTH_SHORT).show();
            }
            c.moveToFirst();
            while(c.moveToNext()) {
                d = c.getString(c.getColumnIndex("DID"));
                System.out.println("Doctor:" + d);
                Doctor.add(d);
            }
        final ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, Doctor);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),DoctorRating_Activity.class);
                String data=(String)parent.getItemAtPosition(position);
                Bundle bundle = getIntent().getExtras();
                intent.putExtra("DEMAIL", data);
                String val = bundle.getString("PEMAIL");
                intent.putExtra("PEMAIL", val);
                startActivity(intent);



            }
        });

    }






}
