package com.example.vinaykl.bs2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Diet extends AppCompatActivity {

    Button send;
    Button ratedr;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);
        //send=(Button)this.findViewById(R.id.send);
        //ratedr=(Button)this.findViewById(R.id.rateDR);

       /* if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("+14804109749", null, "Nagarchith is GOD", null, null);
                Toast.makeText(getApplicationContext(),"Msg sent", Toast.LENGTH_SHORT).show();
                //sendSMS();

                intent = new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(intent);
            }
        });
        ratedr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent = new Intent(getApplicationContext(),Rating_bar.class);
                startActivity(intent);

            }
        });*/


    }

    protected void sendSMS(){
        Log.i("Send SMS", "");

        Uri uri = Uri.parse("smsto:" + "4804109749");
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO,uri);

        //smsIntent.setData(Uri.parse("smsto:"));
        //smsIntent.setType("vnd.android-dir/mms-sms");
        //smsIntent.putExtra("address"  , new String ("4804109749"));
        smsIntent.putExtra("sms_body"  , "Test ");
        Toast.makeText(this,"SMS sent.", Toast.LENGTH_SHORT).show();

        try {
            startActivity(smsIntent);
            finish();
            Log.i("Finished sending SMS...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this,
                    "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }

}
