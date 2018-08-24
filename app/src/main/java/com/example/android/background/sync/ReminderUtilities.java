package com.example.android.background.sync;


import android.content.Context;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

public class ReminderUtilities  {

    private static final int REMINDER_INTERVAL_MINUTES = 15;
    private static final int REMINDER_INTERVAL_SECONDS = (int)
            (java.util.concurrent.TimeUnit.MINUTES.toSeconds(REMINDER_INTERVAL_MINUTES));
    private static final int SYNC_FLEXTIME_SECONDS = REMINDER_INTERVAL_MINUTES;
    private static final String REMINDER_JOB_TAG = "hydration_reminder_tag";
    private static boolean sInitialized;

    synchronized public static void scheduleChargingReminder(@NonNull Context context){
        if (sInitialized) return;
        com.firebase.jobdispatcher.Driver driver= new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);
        Job constrainReminderJob = dispatcher.newJobBuilder()
                .setService(WaterReminderFirebaseJobService.class)
                .setTag(REMINDER_JOB_TAG)
                .setConstraints(Constraint.DEVICE_CHARGING)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(REMINDER_INTERVAL_SECONDS, REMINDER_INTERVAL_SECONDS +SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build();
        dispatcher.schedule(constrainReminderJob);
        sInitialized = true;
    }

}
