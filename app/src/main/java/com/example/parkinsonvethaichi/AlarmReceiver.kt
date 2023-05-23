package com.example.parkinsonvethaichi

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import java.util.Calendar

class AlarmReceiver : BroadcastReceiver() {
    private val CHANNEL_ID = "my_channel_id"
    private val NOTIFICATION_ID = 1

    override fun onReceive(context: Context, intent: Intent) {
        // Bildirim gösterme kodunu buraya ekleyin
        showNotification(context, "İlaç Bildirimi", "İlacını al bebek")
    }

    private fun showNotification(context: Context, title: String, message: String) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Bildirim kanalını kontrol et
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "my_channel_id",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Bildirim oluşturma
        val builder =
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.medicineicon9)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)

        // Bildirimi göster
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }
}


