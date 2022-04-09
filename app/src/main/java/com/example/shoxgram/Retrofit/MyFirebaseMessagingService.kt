package com.example.shoxgram.Retrofit

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.shoxgram.HolderActivity
import com.example.shoxgram.MainActivity
import com.example.shoxgram.R
import com.example.shoxgram.fragments.ProfileFragment
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.app.PendingIntent
import android.app.NotificationManager











//class MyFirebaseMessagingService:FirebaseMessagingService() {
//
//    private val TAG = "MyFirebaseMessagingServ"
//    @SuppressLint("WrongConstant")
//    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        Log.d(TAG, "From: ${remoteMessage.from}")
//
//        // Check if message contains a data payload.
//        if (remoteMessage.data.isNotEmpty()) {
//            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
//
//
//            val myIntent = Intent(this, MainActivity::class.java)
//            val contentIntent = PendingIntent.getActivity(
//                this,
//                System.currentTimeMillis().toInt(),
//                myIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//            )
//
//
//
//
//            var builder = NotificationCompat.Builder(this,"1")
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle("New message")
//                .setContentText(remoteMessage.data["body"])
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setContentIntent(contentIntent)
//                .setAutoCancel(true)
//
//
//
//
//
//
//
//
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
//
//
//
//
//
//
//
//        }
//
//        remoteMessage.notification?.let {
//
//
////            val intent = Intent(this,MainActivity::class.java)
////            val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
////
////
////
////
////            Log.d(TAG, "Message Notification Body: ${it.body}")
////            var builder = NotificationCompat.Builder(this,"1")
////                .setSmallIcon(R.mipmap.ic_launcher)
////                .setContentTitle(it.title)
////                .setContentText(it.body)
////                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
////                .setDefaults(NotificationCompat.DEFAULT_ALL)
////                .setContentIntent(pendingIntent)
////                .setAutoCancel(true)
////
////
////            val notificationManager: NotificationManager =
////                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
////
////            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
////                val name = getString(R.string.app_name)
////                val descriptionText = getString(R.string.app_name)
////                val importance = NotificationManager.IMPORTANCE_DEFAULT
////                val channel = NotificationChannel("1", name, importance).apply {
////                    description = descriptionText
////                }
////                notificationManager.createNotificationChannel(channel)
////            }
////            notificationManager.notify(1,builder.build())
////            Log.d(TAG, "Message Notification Body: ${it.body}")
////
//        }
//    }
//}