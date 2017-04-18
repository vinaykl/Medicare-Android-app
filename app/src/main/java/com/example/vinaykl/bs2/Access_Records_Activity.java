package com.example.vinaykl.bs2;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class Access_Records_Activity extends Activity {

    SearchView sv;
    static final String DATABASE_NAME = "vkl.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    static final String TABLE_NAME = "Patient";
    public SQLiteDatabase db;
    private DataBaseHelper dbHelper;
    TextView name,pre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access__records_);
        sv=(SearchView)findViewById(R.id.SearchDep);
        name = (TextView) findViewById(R.id.editname1);
        pre = (TextView) findViewById(R.id.editpre);
        try{
            dbHelper = new DataBaseHelper(this, DATABASE_NAME, null, DATABASE_VERSION);
            db = dbHelper.getWritableDatabase();

        }catch (Exception e){
            e.printStackTrace();
        }

        sv.setOnQueryTextFocusChangeListener(new SearchView.OnFocusChangeListener()
        {

            @Override
            public void onFocusChange(View arg0, boolean arg1)
            {
               // Toast.makeText(getApplicationContext(), "Got the focus : "+String.valueOf(arg1),Toast.LENGTH_SHORT).show();

            }
        });

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String arg0)
            {
                //Toast.makeText(getApplicationContext(), arg0,Toast.LENGTH_LONG).show();
                Bundle bundle = getIntent().getExtras();
                String val = bundle.getString("PEMAIL");
                //Toast.makeText(getApplicationContext(), val,Toast.LENGTH_LONG).show();

                Cursor cursor=db.query("Patient", null, " Pgaurdianemail=?", new String[]{val}, null, null, null);
                //Cursor cursor=db.rawQuery("SELECT Dpassword FROM  LOGIN   where Dname=?", null);
                if(cursor.getCount()<1) // UserName Not Exist
                {
                    cursor.close();
                    Toast.makeText(getApplicationContext(),"You dont have access to that record", Toast.LENGTH_LONG).show();


                }
                else {
                    cursor.moveToFirst();
                    String gaurd = cursor.getString(cursor.getColumnIndex("Pemail"));
                    //Toast.makeText(getApplicationContext(), gaurd,Toast.LENGTH_LONG).show();
                    if (gaurd.equalsIgnoreCase(arg0)==true) {
                        Toast.makeText(getApplicationContext(), "Access granted", Toast.LENGTH_LONG).show();
                        name.setText(cursor.getString(cursor.getColumnIndex("Pname")));
                        pre.setText(cursor.getString(cursor.getColumnIndex("Ppreconditions")));
                       // Toast.makeText(getApplicationContext(),""+cursor.getString(cursor.getColumnIndex("Pname"))+" ", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "You dont have access to the record", Toast.LENGTH_LONG).show();
                    }
                    cursor.close();
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String arg0)
            {
                //Toast.makeText(getApplicationContext(),arg0,Toast.LENGTH_LONG).show();
                return false;
            }
        });

    }
}
