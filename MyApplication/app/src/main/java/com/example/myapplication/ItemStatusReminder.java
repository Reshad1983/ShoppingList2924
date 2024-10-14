package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;



public class ItemStatusReminder extends Service {

    @Override

    // execution of service will start
    // on calling this method
    public int onStartCommand(Intent intent, int flags, int startId) {
        // of the program
        return START_STICKY;
    }

    @Override

    // execution of the service will
    // stop on calling this method
    public void onDestroy() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
