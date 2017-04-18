package com.example.vinaykl.bs2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        System.out.println("IT is getting till here receiver");
        Intent service1 = new Intent(context, MyAlarmService.class);
        context.startService(service1);

    }
}

