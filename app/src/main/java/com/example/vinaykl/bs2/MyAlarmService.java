package com.example.vinaykl.bs2;



        import android.app.Service;
        import android.content.Context;
        import android.content.Intent;
        import android.content.res.Resources;
        import android.graphics.BitmapFactory;
        import android.media.RingtoneManager;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.IBinder;
        import android.app.Notification;
        import android.app.NotificationManager;
        import android.app.PendingIntent;
        import android.app.Service;
        import android.content.Intent;
        import android.os.IBinder;
        import android.support.v4.app.NotificationCompat;
        import android.util.Log;

public class MyAlarmService extends Service {

    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    public MyAlarmService() {
    }



    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();


    }

    @SuppressWarnings("static-access")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.abc)
                        .setContentTitle("Attention")
                        .setContentText("Time For your appointment");

        Intent notificationIntent = new Intent(this, MyAlarmService.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
        return START_STICKY;
    }






}
