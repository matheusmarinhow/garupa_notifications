package io.ionic.starter;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;

public class SensorService extends Service {
    public int counter=0;
    Context context;
    String CHANEL_ID = "1";
    SensorRestarterBroadcastReceiver sensorBroadcast;

    public SensorService(Context applicationContext) {
        super();
        Log.i("Service", "abriu");
    }

    public SensorService() {
    }

    public Context getContext() {
        return context;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("EXIT sensor service", "serviço destruido");

        System.out.println(this);
        Intent broadcastIntent = new Intent(getContext(), sensorBroadcast.getClass());

        Log.i("huebr", "enviando broadcast...");
        sendBroadcast(broadcastIntent);
        stoptimertask();
    }

    private Timer timer;
    private TimerTask timerTask;
    public void startTimer() {
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, 1000, 1000 * 60); //
    }


    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                Log.i("timer " , ""+ (counter++));
                registraNotificacao();
            }
        };
    }


    public void stoptimertask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void registraNotificacao(){
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANEL_ID)
                .setContentTitle("Test titulo")
                .setContentText("Test copor")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setSound(Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.sisira))
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(false);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }
}