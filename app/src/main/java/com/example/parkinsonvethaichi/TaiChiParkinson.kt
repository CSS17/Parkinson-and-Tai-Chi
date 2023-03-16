package com.example.parkinsonvethaichi

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.effect.Effect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import java.io.*


class TaiChiParkinson : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tai_chi_parkinson)
        var actionBar = supportActionBar
        actionBar?.title = "Tai Chi ve Parkinson"

        var parkinsoncount=0
        var parkinsonreasonscount=0
        var symptomscount=0
        var effectscount=0
        var treatmentcount=0
        var taichicount=0
        var taichieffectcount=0


        val ParkinsonInfo= findViewById<TextView>(R.id.ParkinsonInfo)
        val ParkinsonReasonsInfo = findViewById<TextView>(R.id.ParkinsonReasonsInfo)
        val SymptomsInfo = findViewById<TextView>(R.id.SymptomsInfo)
        val EffectsInfo= findViewById<TextView>(R.id.EffectsInfo)
        val TreatmentInfo=findViewById<TextView>(R.id.TreatmentInfo)
        val TaiChiInfo=findViewById<TextView>(R.id.TaiChiInfo)
        val TaiChiEffectInfo=findViewById<TextView>(R.id.TaiChiEffectInfo)




        val ParkinsonTitle = findViewById<TextView>(R.id.ParkinsonTitle)

        ParkinsonTitle.setOnClickListener {
            // Handle the click event here

            if(parkinsoncount%2==0){
                ParkinsonInfo.visibility=View.VISIBLE
                ParkinsonTitle.setBackgroundColor(255)
                parkinsoncount+=1
            }
            else{
                ParkinsonTitle.setBackgroundResource(R.drawable.frame)
                ParkinsonInfo.visibility=View.GONE
                parkinsoncount+=1
            }


        }

        val ParkisonReasonsTitle= findViewById<TextView>(R.id.ParkinsonReasonsTitle)

        ParkisonReasonsTitle.setOnClickListener {

            if (parkinsonreasonscount % 2 == 0) {
                ParkisonReasonsTitle.setBackgroundColor(255)
                ParkinsonReasonsInfo.visibility = View.VISIBLE
                parkinsonreasonscount += 1
            } else {
                ParkisonReasonsTitle.setBackgroundResource(R.drawable.frame)
                ParkinsonReasonsInfo.visibility = View.GONE
                parkinsonreasonscount += 1


            }
        }
            val SymptomsTitle = findViewById<TextView>(R.id.SymptomsTitle)
            SymptomsTitle.setOnClickListener {

                if (symptomscount % 2 == 0) {
                    SymptomsTitle.setBackgroundColor(255)
                    SymptomsInfo.visibility = View.VISIBLE
                    symptomscount += 1
                } else {
                    SymptomsTitle.setBackgroundResource(R.drawable.frame)
                    SymptomsInfo.visibility = View.GONE
                    symptomscount += 1


                }

            }

        val EffectsTitle = findViewById<TextView>(R.id.EffectsTitle)

        EffectsTitle.setOnClickListener {

            if (effectscount % 2 == 0) {
                EffectsTitle.setBackgroundColor(255)
                EffectsInfo.visibility = View.VISIBLE
                effectscount += 1
            } else {
                EffectsTitle.setBackgroundResource(R.drawable.frame)
                EffectsInfo.visibility = View.GONE
                effectscount += 1


            }

        }

        val TreatmentTitle = findViewById<TextView>(R.id.TreatmentTitle)

        TreatmentTitle.setOnClickListener {

            if (treatmentcount % 2 == 0) {
                TreatmentTitle.setBackgroundColor(255)
                TreatmentInfo.visibility = View.VISIBLE
                treatmentcount += 1
            } else {
                TreatmentTitle.setBackgroundResource(R.drawable.frame)
                TreatmentInfo.visibility = View.GONE
                treatmentcount += 1


            }

        }

        val TaiChiTitle = findViewById<TextView>(R.id.TaiChiTitle)

        TaiChiTitle.setOnClickListener {

            if (taichicount % 2 == 0) {
                TaiChiTitle.setBackgroundColor(255)
                TaiChiInfo.visibility = View.VISIBLE
                taichicount += 1
            } else {
                TaiChiTitle.setBackgroundResource(R.drawable.frame)
                TaiChiInfo.visibility = View.GONE
                taichicount += 1


            }

        }

        val TaiChiEffectTitle = findViewById<TextView>(R.id.TaiChiEffectTitle)

        TaiChiEffectTitle.setOnClickListener {

            if (taichieffectcount % 2 == 0) {
                TaiChiEffectTitle.setBackgroundColor(255)
                TaiChiEffectInfo.visibility = View.VISIBLE
                taichieffectcount += 1
            } else {
                TaiChiEffectTitle.setBackgroundResource(R.drawable.frame)
                TaiChiEffectInfo.visibility = View.GONE
                taichieffectcount += 1


            }

        }



        }

    override fun onBackPressed() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }




    }
