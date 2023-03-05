package com.example.parkinsonvethaichi


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()

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


}