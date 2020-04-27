package com.everyone.cantalk.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.everyone.cantalk.R
import com.everyone.cantalk.ui.message.MessageActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessaging : FirebaseMessagingService() {
    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

        val sented = p0.data["sented"]
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null && sented.equals(user.uid)) {
            sendNotification(p0)
        }
    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
        val user = remoteMessage.data["user"]
        val icon = remoteMessage.data["icon"]
        val title = remoteMessage.data["title"]
        val body = remoteMessage.data["body"]

        val notification : RemoteMessage.Notification = remoteMessage.notification as RemoteMessage.Notification
        val j = Integer.parseInt(user?.replace(Regex("[\\D]"), "") ?: "")
        val intent = MessageActivity.getIntent(this, user?: "")
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, j, intent, PendingIntent.FLAG_ONE_SHOT)

        val defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder : NotificationCompat.Builder = NotificationCompat.Builder(this)
            .setSmallIcon(if (icon != null) Integer.parseInt(icon) else R.drawable.ic_notifications)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(defaultSound)
            .setVibrate(longArrayOf(100))
            .setContentIntent(pendingIntent)
        val notificationManager : NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        var i = 0
        if (j > 0) i = j

        notificationManager.notify(i, builder.build())
    }
}