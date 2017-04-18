package com.example.vinaykl.bs2;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class DoctorRating_Activity extends AppCompatActivity {

    Button rate1;
    RatingBar r1,r2,r3,r4,r5;
    String dbName="vkl.db";
    String val,val1;
    SQLiteDatabase db;
    LoginDataBase_adapter dataBase_adapter = new LoginDataBase_adapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_rating_);

        Bundle bundle = getIntent().getExtras();
        val = bundle.getString("DEMAIL");
        val1 = bundle.getString("PEMAIL");

        rate1 = (Button)this.findViewById(R.id.SubmitRate);
        r1=(RatingBar)findViewById(R.id.rb1);
        r2=(RatingBar)findViewById(R.id.rb2);
        r3=(RatingBar)findViewById(R.id.rb3);
        r4=(RatingBar)findViewById(R.id.rb4);
        r5=(RatingBar)findViewById(R.id.rb5);
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

        rate1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Float rv1= (Float) r1.getRating();
                Float rv2= (Float) r2.getRating();
                Float rv3= (Float) r3.getRating();
                Float rv4= (Float) r4.getRating();
                Float rv5= (Float) r5.getRating();
                int tot=(int)(rv1+rv2+rv3+rv4+rv5)/5;
                Toast.makeText(getApplicationContext(),"Average Rating = "+tot, Toast.LENGTH_SHORT).show();
                Bundle bundle = getIntent().getExtras();
                String val = bundle.getString("DEMAIL");
                String val1 = bundle.getString("PEMAIL");
                insert_ratedr(val,val1,tot);

                Intent intent = new Intent(getApplicationContext(),Patient_Welcome.class);
                //Bundle bundle = getIntent().getExtras();
                intent.putExtra("DEMAIL", val);
                //String val = bundle.getString("PEMAIL");
                intent.putExtra("PEMAIL", val1);
                startActivity(intent);

            }

        });




    }

    public void insert_ratedr(String dname,String pname, int rating){
        try {
            String query = "UPDATE Doctor SET Drating = '"+rating+"' WHERE Demail ='"+dname+"';";
            //"INSERT INTO Doctor (Drating) VALUES (" + rating + ") WHERE Demail ='" + dname+"'";

            db.execSQL(query);
            db.execSQL("UPDATE APPOINTMENTS SET RATE = 'R' WHERE DID = '"+ dname+"'"+"AND PID = '"+pname+"'");
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
}
