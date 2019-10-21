package io.ionic.starter;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

public class SensorService extends Service {
    public int counter=0;
    Context context;
    String CHANEL_ID = "1";
    SensorRestarterBroadcastReceiver sensorBroadcast;
    private Socket mSocket;

    public SensorService(Context applicationContext) {
        super();
        Log.i("Service", "abriu");
    }

    public SensorService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        try {
            mSocket = IO.socket("http://10.20.18.83:3000/");
            mSocket.connect();
            Log.i("SocketIO", "Socket conectado");

        } catch (URISyntaxException e) {
            Log.i("SocketIo", "Socket não conectado");
        }
        startTimer();
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("EXIT sensor service", "serviço destruido");
        Intent broadcastIntent = new Intent(getApplicationContext(), SensorRestarterBroadcastReceiver.class);
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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void registraNotificacao(){

        mSocket.emit("join", "Marinho");
        Log.i("SocketIo", "enviado");


        /*NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANEL_ID)
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
        notificationManager.notify(0, notificationBuilder.build());*/
    }


    /*private Emitter.Listener onNewMessage = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    String message;
                    try {
                        username = data.getString("username");
                        message = data.getString("message");

                        System.out.println(username);
                        System.out.println(message);
                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };*/
}