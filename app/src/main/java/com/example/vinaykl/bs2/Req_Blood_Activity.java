package com.example.vinaykl.bs2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Req_Blood_Activity extends AppCompatActivity {



    Button req;
    Intent intent;
    String name1 = null, age1 = null, addrreq = null, sex1 = null, blood = null;
    EditText name, addr, age, bg;
    String DBName = null;
    //StringBuilder sb;
    RadioGroup rg;
    SQLiteDatabase db;
    static final String TABLE_NAME = "Patient";
    static final String DATABASE_NAME = "vkl.db";
    private DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_req__blood_);

        req = (Button) findViewById(R.id.BtnReq);
        name = (EditText) findViewById(R.id.editName);
        addr = (EditText) findViewById(R.id.editAddr);
        bg = (EditText) findViewById(R.id.editBlood);
        age = (EditText) findViewById(R.id.editAge);
        rg = (RadioGroup) findViewById(R.id.radioSex);
        try {
            dbHelper = new DataBaseHelper(this, DATABASE_NAME, null,1);
            db = dbHelper.getWritableDatabase();

        }catch (Exception e){
            e.printStackTrace();
        }



        req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name1 = name.getText().toString();
                blood = bg.getText().toString();
                age1 = age.getText().toString();
                addrreq = addr.getText().toString();
                sex1 = ((RadioButton) findViewById(rg.getCheckedRadioButtonId())).getText().toString();
                String content = "Urgent Blood Required:\n Reciepent:" + name1 + "\n " +
                        "Blood Group:" + blood + "\nAge:" + age1 + "\nSex:" + sex1 + "\nAddress:" + addrreq;
                //Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT).show();
                SendSMS(blood,content);


            }
        });


    }
    public void SendSMS(String blood,String content){

        String num="";
        StringBuilder builder = new StringBuilder();
        String delim = "";

        Cursor cursor=db.query("Patient", null, " Pblood=?", new String[]{blood}, null, null, null);
        //Cursor cursor=db.rawQuery("SELECT Dpassword FROM  LOGIN   where Dname=?", null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            Toast.makeText(this,"No one has the Blood grp, Plz contact the nearest blood bank",Toast.LENGTH_SHORT).show();
        }else {

            cursor.moveToFirst();
            try {
                while (cursor.isAfterLast() == false) {

                    num = cursor.getString(cursor.getColumnIndex("Pphone"));
                    //Toast.makeText(this,num,Toast.LENGTH_SHORT).show();
                    builder.append(delim).append(num.toString());
                    delim=";";
                    cursor.moveToNext();
                }
            }catch (Exception e){
                e.printStackTrace();
                cursor.close();
            }

            //SmsManager smsManager = SmsManager.getDefault();
            num=builder.toString();
            //Toast.makeText(this,num,Toast.LENGTH_SHORT).show();
            //smsManager.sendTextMessage(num, null, content, null, null);

            Intent i = new Intent(android.content.Intent.ACTION_VIEW);
            i.putExtra("address", num);
            // here i can send message to emulator 5556,5558,5560
            // you can change in real device
            i.putExtra("sms_body", content);
            i.setType("vnd.android-dir/mms-sms");
            startActivity(i);
            Toast.makeText(getApplicationContext(), "Click Send", Toast.LENGTH_SHORT).show();

        }


    }

}