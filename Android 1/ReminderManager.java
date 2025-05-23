package com.example.healthcareapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import java.util.Calendar;

public class ReminderManager {
    private final Context context;
    private final AlarmManager alarmManager;

    public ReminderManager(Context context) {
        this.context = context;
        this.alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void setReminder(int medId, String time) {
        try {
            // Parse time string (expected format: "HH:mm")
            String[] parts = time.split(":");
            int hour = Integer.parseInt(parts[0]);
            int minute = Integer.parseInt(parts[1]);

            // Set up calendar instance
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);

            // If the time has already passed today, schedule for tomorrow
            if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
                calendar.add(Calendar.DAY_OF_YEAR, 1);
            }

            // Create pending intent
            Intent intent = new Intent(context, ReminderReceiver.class);
            intent.putExtra("medId", medId);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    context,
                    medId,  // Unique request code
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT |
                            (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ?
                                    PendingIntent.FLAG_MUTABLE : PendingIntent.FLAG_IMMUTABLE)
            );

            // Set alarm based on Android version
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExactAndAllowWhileIdle(
                            AlarmManager.RTC_WAKEUP,
                            calendar.getTimeInMillis(),
                            pendingIntent
                    );
                } else {
                    // Handle permission not granted
                    requestExactAlarmPermission();
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(),
                        pendingIntent
                );
            } else {
                alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(),
                        pendingIntent
                );
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private void requestExactAlarmPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Intent permissionIntent = new Intent(android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
            permissionIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(permissionIntent);
        }
    }
}