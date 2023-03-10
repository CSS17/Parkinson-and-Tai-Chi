package com.example.parkinsonvethaichi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Statistic : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistic)
        var actionBar=supportActionBar
        actionBar?.title="Günlük İstatistikler"
    }
}