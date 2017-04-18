package com.example.vinaykl.bs2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ChatOrVideo extends AppCompatActivity
{

    Bundle b;
    String phone_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_or_video);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Intent intent = this.getIntent();
        b = intent.getExtras();
        phone_no = b.getString("phone_no");
        Toast.makeText(getApplicationContext(),phone_no,Toast.LENGTH_SHORT).show();
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        Button goToChat = (Button) findViewById(R.id.Chat);

        goToChat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dirctchat(phone_no);
            }
        });


        Button goToVideo = (Button) findViewById(R.id.videoCall);

        goToVideo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dirctchat(phone_no);
            }
        });

    }

    public void dirctchat(String phone_no)
    {
        Uri mUri = Uri.parse("smsto:+1"+phone_no);
        Intent mIntent = new Intent(Intent.ACTION_SENDTO, mUri);
        mIntent.setPackage("com.whatsapp");
        mIntent.putExtra("sms_body", "The text goes here");
        mIntent.putExtra("chat",true);
        startActivity(mIntent);
    }



}
