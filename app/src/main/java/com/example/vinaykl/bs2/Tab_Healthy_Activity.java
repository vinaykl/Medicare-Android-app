package com.example.vinaykl.bs2;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class Tab_Healthy_Activity extends TabActivity {

    TabHost tabhost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tipsanddiet);

        TabHost tabHost=getTabHost();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("First Tab");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Second Tab");

        tab1.setIndicator("Healthy Advice");
        tab1.setContent(new Intent(this,Healthy_Tips.class));

        tab2.setIndicator("Diet Chat");
        tab2.setContent(new Intent(this,Diet.class));

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);

    }
}
