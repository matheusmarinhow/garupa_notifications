package io.ionic.starter;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class SensorRestarterBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(SensorRestarterBroadcastReceiver.class.getSimpleName(), "Reiniciando serviço");
        //aqui cria novamente o serviço
        context.startService(new Intent(context, SensorService.class));

        //Aqui cria a notificação
        /*NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, "chanelid")
                .setContentTitle("Test titulo 2")
                .setContentText("Test copor 2")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setSound(Uri.parse("android.resource://" + context.getApplicationContext().getPackageName() + "/" + R.raw.sisira))
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(false);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());*/
    }
}