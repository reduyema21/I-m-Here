package com.example.healthcareapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ActionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getStringExtra("action");
        int medId = intent.getIntExtra("medId", -1);
        DatabaseHelper dbHelper = new DatabaseHelper(context);

        switch (action) {
            case "taken":
                dbHelper.updateDoseStatus(medId);
                dbHelper.addDoseLog(medId, System.currentTimeMillis(), "Taken");
                break;

            case "snooze":
                // Reschedule for 10 minutes later
                new ReminderManager(context).setReminder(medId,
                        String.valueOf(System.currentTimeMillis() + 10 * 60 * 1000));
                break;
        }
    }
}
