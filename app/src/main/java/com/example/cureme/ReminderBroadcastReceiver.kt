package com.example.cureme

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.app.NotificationChannel
import android.os.Build
import android.app.NotificationManager
import androidx.core.app.NotificationCompat


class ReminderBroadcastReceiver : BroadcastReceiver() {
    @SuppressLint("LongLogTag")
    override fun onReceive(context: Context, intent: Intent) {
        // Create a notification channel for Android Oreo and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "reminder_channel"
            val channelName = "Reminder Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance)
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Create the notification
        val notificationBuilder = NotificationCompat.Builder(context, "reminder_channel")
            .setContentTitle("Reminder")
            .setContentText("It's time to take your medication!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Display the notification
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notificationBuilder.build())

        // Handle the broadcast receiver logic here
        Log.d("ReminderBroadcastReceiver", "Received reminder broadcast")

        // You can access the extras from the intent if needed
        val extras = intent.extras
        if (extras != null) {
            // Extract any extra data from the intent
            val med = extras.getString("med")
            val time = extras.getString("time")
            val everyday = extras.getBoolean("everyday")
            val s = extras.getBoolean("s")
            val m = extras.getBoolean("m")
            val t = extras.getBoolean("t")
            val w = extras.getBoolean("w")
            val th = extras.getBoolean("th")
            val f = extras.getBoolean("f")
            val s2 = extras.getBoolean("s2")

            // Log the extracted data
            Log.d("ReminderBroadcastReceiver", "Medicine Name: $med")
            Log.d("ReminderBroadcastReceiver", "Set Time: $time")
            Log.d("ReminderBroadcastReceiver", "Everyday: $everyday")
            Log.d("ReminderBroadcastReceiver", "S: $s")
            Log.d("ReminderBroadcastReceiver", "M: $m")
            Log.d("ReminderBroadcastReceiver", "T: $t")
            Log.d("ReminderBroadcastReceiver", "W: $w")
            Log.d("ReminderBroadcastReceiver", "Th: $th")
            Log.d("ReminderBroadcastReceiver", "F: $f")
            Log.d("ReminderBroadcastReceiver", "S: $s2")
        }
    }
}