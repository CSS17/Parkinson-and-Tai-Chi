package com.example.parkinsonvethaichi


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()

        val constraints = Constraints.Builder()
            .setRequiresCharging(false)
            .setRequiresBatteryNotLow(true)
            .build()

        val myWorkRequest : PeriodicWorkRequest = PeriodicWorkRequestBuilder<RefreshDataBase>(15,TimeUnit.MINUTES)
            .setInitialDelay(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "myWork",
            ExistingPeriodicWorkPolicy.KEEP,
            myWorkRequest
        )

    }


    fun Movements(view: View) {
        intent = Intent(applicationContext, MovementPassAnimation::class.java)
        startActivity(intent)
    }
    fun ParkinsonTaiChi(view: View) {
        intent = Intent(applicationContext, TaiChiParkinsonPassAnimation::class.java)
        startActivity(intent)
    }

    fun Medicine(view: View) {
        intent = Intent(applicationContext, MedicinePassAnimation::class.java)
        startActivity(intent)
    }

    fun Statistics(view: View) {
        intent = Intent(applicationContext, StatiscticPassAnimation::class.java)
        startActivity(intent)
    }


}