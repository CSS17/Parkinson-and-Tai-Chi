package com.example.parkinsonvethaichi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.size

class Medicine : AppCompatActivity() {

    val hours = arrayOf("00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23")
    val munites= arrayOf("00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicine)
        var actionBar=supportActionBar
        actionBar?.title="İlaç Saatleri Ayarla"

        val hourspinner = findViewById<Spinner>(R.id.saatsec)

        val hourarrayAdapter= ArrayAdapter<String>(this,R.layout.spinner_item,hours)
        hourspinner.adapter = hourarrayAdapter

        hourspinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

    }

}