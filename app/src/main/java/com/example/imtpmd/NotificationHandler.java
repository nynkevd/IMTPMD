package com.example.imtpmd;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class NotificationHandler extends Worker {
    public NotificationHandler(@NonNull Context context, @NonNull WorkerParameters workerParameters){
        super(context, workerParameters);
    }

    public static void scheduleReminder(Long duration, Data data, String tag){
        OneTimeWorkRequest notificationWork = new OneTimeWorkRequest.Builder(NotificationHandler.class)
                .setInitialDelay(duration, TimeUnit.MILLISECONDS).addTag(tag)
                .setInputData(data).build();

        WorkManager instance = WorkManager.getInstance();
        instance.enqueue(notificationWork);
    }

    public static void cancelReminder(String tag) {
        WorkManager instance = WorkManager.getInstance();
        Log.d("notification", "Cancelled all notifications for " + tag);
        instance.cancelAllWorkByTag(tag); //ook cancelAllWork mogelijk, tag zou eventueel misschien dagdeel kunnen zijn? Geen idee wat mogelijk is maar daar moeten we zo even naar kijken
    }

    @NonNull
    @Override
    public ListenableWorker.Result doWork() {
        // --------------------------------- TOEVOEGEN hoeft denk niet??? -------------------------------------
        String title = getInputData().getString("title");
        String text = getInputData().getString("text");
        int id = (int) getInputData().getLong("id", 0);

        sendNotification(title, text, id);

        return Result.success();
    }

    private void sendNotification(String title, String text, int id) {
        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("id", id);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_HIGH);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "default")
                .setContentTitle(title)
                .setContentText(text)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_action_pil)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        Objects.requireNonNull(notificationManager).notify(id, notification.build());
    }
}
