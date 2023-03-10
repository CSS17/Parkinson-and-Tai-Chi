package com.example.parkinsonvethaichi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TaiChiParkinson : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tai_chi_parkinson)
        var actionBar=supportActionBar
        actionBar?.title="Tai Chi ve Parkinson"
    }
}