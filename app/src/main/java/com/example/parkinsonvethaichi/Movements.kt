package com.example.parkinsonvethaichi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Movements : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movements)
        var actionBar=supportActionBar
        actionBar?.title="Tai Chi Hareketleri"
    }
}