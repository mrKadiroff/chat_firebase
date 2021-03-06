package com.example.shoxgram

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {

    private val TAG = "MyFirebaseMessagingServ"
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")


            var builder = NotificationCompat.Builder(this,"1")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("New message")
                .setContentText(remoteMessage.data["body"])
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)


            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = getString(R.string.app_name)
                val descriptionText = getString(R.string.app_name)
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel("1", name, importance).apply {
                    description = descriptionText
                }
                notificationManager.createNotificationChannel(channel)
            }
            notificationManager.notify(1,builder.build())
//            Log.d(TAG, "Message Notification Body: ${it.body}")




//            if (/* Check if data needs to be processed by long running job */ true) {
//                // For long-running tasks (10 seconds or more) use WorkManager.
//                scheduleJob()
//            } else {
//                // Handle message within 10 seconds
//                handleNow()
//            }

        }

        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
//            var builder = NotificationCompat.Builder(this,"1")
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle(it.title)
//                .setContentText(it.body)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setAutoCancel(true)
//
//
//            val notificationManager: NotificationManager =
//                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                val name = getString(R.string.app_name)
//                val descriptionText = getString(R.string.app_name)
//                val importance = NotificationManager.IMPORTANCE_DEFAULT
//                val channel = NotificationChannel("1", name, importance).apply {
//                    description = descriptionText
//                }
//                notificationManager.createNotificationChannel(channel)
//            }
//            notificationManager.notify(1,builder.build())
//            Log.d(TAG, "Message Notification Body: ${it.body}")

        }
    }
}