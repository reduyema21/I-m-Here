package com.example.healthcareapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;

// Broadcast Receiver class
public class ReminderReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int medId = intent.getIntExtra("medId", -1);

        // Create Notification Channel (for Android 8+)
        createNotificationChannel(context);

        // Build Notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "MED_CHANNEL")
                .setSmallIcon(R.drawable.ic_pill)
                .setContentTitle("Medication Reminder")
                .setContentText("Time to take your medicine")
                .setAutoCancel(true);

        // Add Taken Action
        Intent takenIntent = new Intent(context, ActionReceiver.class);
        takenIntent.putExtra("action", "taken");
        takenIntent.putExtra("medId", medId);
        PendingIntent takenPending = PendingIntent.getBroadcast(context, medId, takenIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        builder.addAction(R.drawable.ic_check, "Taken", takenPending);

        // Add Snooze Action
        Intent snoozeIntent = new Intent(context, ActionReceiver.class);
        snoozeIntent.putExtra("action", "snooze");
        snoozeIntent.putExtra("medId", medId);
        PendingIntent snoozePending = PendingIntent.getBroadcast(context, medId, snoozeIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        builder.addAction(R.drawable.ic_snooze, "Snooze", snoozePending);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(medId, builder.build());
    }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("MED_CHANNEL",
                    "Medication Reminders", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Medicine reminder notifications");
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
