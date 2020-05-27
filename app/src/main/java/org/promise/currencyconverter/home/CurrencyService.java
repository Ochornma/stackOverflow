package org.promise.currencyconverter.home;


import android.app.job.JobParameters;
import android.app.job.JobService;

import android.util.Log;


import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavDeepLinkBuilder;

import org.promise.currencyconverter.CurrencyRepository;
import org.promise.currencyconverter.R;

import static org.promise.currencyconverter.App.CHANNEL_1_ID;

public class CurrencyService extends JobService {
    private boolean jobCancelled;
    NotificationManagerCompat notificationManager;

    @Override
    public boolean onStartJob(JobParameters params) {
        if (!jobCancelled){
            Log.i("started", "started");
            notificationManager = NotificationManagerCompat.from(this);
            new CurrencyRepository( this).getCurrency();
            notification();
        }
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i("started1", "started1");
        jobCancelled = true;
        return true;
    }

    private void notification(){
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, CHANNEL_1_ID);
        notification.setSmallIcon(R.drawable.dollar)
                .setContentTitle("Promise")
                .setContentText("Ochornma")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(
                        new NavDeepLinkBuilder(this)
                                .setGraph(R.navigation.navigation)
                                .setDestination(R.id.homeFragment)
                                .createPendingIntent()
                );
        notificationManager.notify(1, notification.build());
    }
}
