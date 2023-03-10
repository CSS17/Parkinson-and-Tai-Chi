package com.example.parkinsonvethaichi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer

class StatiscticPassAnimation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statisctic_pass_animation)
        supportActionBar?.hide()
        val timer = object: CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                val intent= Intent(applicationContext,Movements::class.java)
                startActivity(intent)
            }
        }
        timer.start()
    }
    }