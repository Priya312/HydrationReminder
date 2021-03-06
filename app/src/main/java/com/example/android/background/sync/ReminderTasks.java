package com.example.android.background.sync;

import android.content.Context;

import com.example.android.background.utilities.NotificationUtils;
import com.example.android.background.utilities.PreferenceUtilities;

public class ReminderTasks {

    public static  String  ACTION_INCREMENT_WATER_COUNT = "increment_water_count";
    public static final String ACTION_CHARGING_REMINDER = "charging_reminder";
    public static final String ACTION_DISMISS_NOTIFICATION = "dismiss_notification";


    public static void executeTask(Context context, String action){
            if (ACTION_INCREMENT_WATER_COUNT.equals(action))
                incrementWaterCount(context);
            else if (ACTION_DISMISS_NOTIFICATION.equals(action))
                NotificationUtils.clearAllNotifications(context);
            else if (ACTION_CHARGING_REMINDER.equals(action))
                issueChargingReminder(context);
        }

    private static void issueChargingReminder(Context context) {
        PreferenceUtilities.incrementChargingReminderCount(context);
        NotificationUtils.remindUserBecauseCharging(context);
    }

    private static void incrementWaterCount(Context context){
    PreferenceUtilities.incrementWaterCount(context);
        NotificationUtils.clearAllNotifications(context);
    }
}