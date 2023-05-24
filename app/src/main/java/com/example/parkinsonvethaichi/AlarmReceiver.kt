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
    private var NOTIFICATION_ID = 1


    override fun onReceive(context: Context, intent: Intent) {
        // Bildirim gösterme kodunu buraya ekleyin
        val medicineName = intent.getStringExtra("medicine_name")
        val medicineId = intent.getIntExtra("MEDICINE_ID", 0)
        showNotification(context, "İlaç Bildirimi", "İlacını almayı unutma: $medicineName",medicineId)
    }

    private fun showNotification(context: Context, title: String, message: String,medicineId: Int) {
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
        NOTIFICATION_ID = medicineId
        // Bildirimi göster
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }
}


