package com.example.parkinsonvethaichi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Medicine : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicine)
        var actionBar=supportActionBar
        actionBar?.title="İlaç Saatleri Ayarla"
    }
}