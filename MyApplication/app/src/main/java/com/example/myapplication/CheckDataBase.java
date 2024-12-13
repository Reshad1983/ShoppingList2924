package com.example.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CheckDataBase extends Service {
    SharedPreferences myPre;
    public static final String CHANNEL_ID = "Items to buy";
    public static final String FIRST_DATE = "starting_control";

    public CheckDataBase() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myPre = getSharedPreferences(FIRST_DATE, MODE_PRIVATE);
        startForeground(1, showNotification("Message"));
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NotificationChannel channel;
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        channel = new NotificationChannel(CHANNEL_ID, "Needs to buy", NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("Must buy items");
        channel.enableVibration(true);
        notificationManager.createNotificationChannel(channel);
        DatabaseHelper dbh = new DatabaseHelper(this);
        Handler handler = new Handler();
        Runnable runnable_task = new Runnable()
        {
            @Override
            public void run() {
                List<NameStatusPair> items;
                try{
                    items = dbh.getItemsSortedByUsage();
                } catch (ParseException e){
                    throw new RuntimeException(e);
                }
                StringBuilder message = prior_list( dbh, items);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String today_date = sdf.format(new Date());
                String first_time_to_insert = myPre.getString(FIRST_DATE, "");
           if(!today_date.equals(first_time_to_insert)){
              SharedPreferences.Editor editor = myPre.edit();
              editor.putString(FIRST_DATE, today_date);
              editor.apply();
               sdf = new SimpleDateFormat("E");
               today_date = sdf.format(new Date());
                if(today_date.equals("Fri")){
                  for (NameStatusPair item : items) {
                      String date_string = item.get_date();
                      if (date_string != null) {
                          Date date;
                          try {
                              date = sdf.parse(date_string);
                          } catch (ParseException e) {
                              date = new Date();
                          }
                          int interval = Integer.parseInt(item.get_interval());
                          Calendar c = Calendar.getInstance();
                          c.setTime(date); // Using today's date
                          c.add(Calendar.DATE, interval); // Adding 5 days
                          sdf = new SimpleDateFormat("yyyy-MM-dd");
                          String buy_date_to_now = sdf.format(c.getTime());
                          today_date = sdf.format(new Date());
                          if (today_date.compareTo(buy_date_to_now) > 0) {
                              dbh.update_status(1, item.getName());
                              Log.d("Handlers", "Time to buy");
                              message.append("\n").append(item.getName());
                          }
                      }
                  }
                }
              }
                    updateNotification(message.toString());
                    Log.d("Handlers", "Message updated.");
                handler.postDelayed(this, 7200000);
            }
        };
        handler.post(runnable_task);
        return Service.START_NOT_STICKY;
    }
    private StringBuilder prior_list(DatabaseHelper dbh, List<NameStatusPair> items) {
        StringBuilder message = new StringBuilder();
        for(NameStatusPair item : items)
        {
            if(item.getPrio().equals("1"))
            {
                message.append("\n").append(item.getName());
            }
        }
        return message;
    }
    private Notification showNotification(String content) {
        Intent intent = new Intent(this, NewMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(
                new NotificationChannel(CHANNEL_ID, "Forground notification"
                        , NotificationManager.IMPORTANCE_HIGH));

        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Some items to buy")
                .setSmallIcon(R.drawable.ic_add)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();
    }

    private void updateNotification(String method_message) {

        Notification notification = showNotification(method_message);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, notification);
    }
}