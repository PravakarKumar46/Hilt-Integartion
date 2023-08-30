package com.example.hilt_integartion.framework.mvvm.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.hilt_integartion.R
import com.example.hilt_integartion.framework.mvvm.ui.main.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val CHANNEL_ID = "CHANNEL_ID"
const val CHANNEL_NAME = "CHANNEL_NAME"

class FirebaseMessageService : FirebaseMessagingService() {


    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(" FCM_RefreshedToken", token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
//its failed need to check
//        generateNotification(message.notification?.title, message.notification?.body)


        message.notification.let { notification ->
            pushNotification(notification?.title, notification?.body)
        }

    }

    private fun generateNotification(title: String?, message: String?) {

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        var builder: NotificationCompat.Builder =
            NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                .setSmallIcon(R.drawable.image)
                .setAutoCancel(true)
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(title, message))

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationChannel =
            NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(notificationChannel)

        notificationManager.notify(0, builder.build())
    }

    @SuppressLint("RemoteViewLayout")
    private fun getRemoteView(title: String?, message: String?): RemoteViews {

        val remoteViews = RemoteViews(
            "com.example.hilt_integartion.framework.mvvm.service",
            R.layout.notification
        )
        remoteViews.setTextViewText(R.id.title, title)
        remoteViews.setTextViewText(R.id.discription, message)
        remoteViews.setTextViewText(R.id.app_logo, R.drawable.image.toString())

        return remoteViews
    }

    private fun pushNotification(title: String?, message: String?) {

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
        builder.priority = NotificationManager.IMPORTANCE_HIGH
        val notification = getNotification(builder, title, message)
        notificationManager.notify(1, notification)

    }

    private fun getNotification(
        builder: NotificationCompat.Builder,
        title: String?,
        message: String?
    ): Notification {

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = createPendingIntentGetActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        return builder.setContentTitle("")
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.baseline_circle_notifications_24)
            .setContentTitle(title)
            .setSubText(message)
            .setAutoCancel(true)
            .setChannelId(CHANNEL_ID)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            .build()

    }

    private fun createPendingIntentGetActivity(
        context: Context?,
        id: Int,
        intent: Intent?,
        flag: Int
    ): PendingIntent {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_IMMUTABLE or flag)
        } else {
            PendingIntent.getActivity(context, id, intent, flag)
        }

    }

}