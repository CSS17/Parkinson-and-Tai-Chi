package com.example.parkinsonvethaichi

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_tai_chi_parkinson.*
import java.io.*


class TaiChiParkinson : AppCompatActivity() {






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tai_chi_parkinson)
        // Metin butonuna tıklanınca
        ParkinsonTitle.setOnClickListener {
            // Metin butonu tıklandığında görünürlük özelliğini değiştireceğiz.
            // Eğer görünürse gizleyeceğiz, gizliyse görünür yapacağız.
            if (ParkinsonInfo.visibility == View.VISIBLE) {
                ParkinsonInfo.visibility = View.GONE
            } else {
                ParkinsonInfo.visibility = View.VISIBLE
            }
        }

         ParkinsonReasonsTitle.setOnClickListener {
             // Metin butonu tıklandığında görünürlük özelliğini değiştireceğiz.
             // Eğer görünürse gizleyeceğiz, gizliyse görünür yapacağız.
             if (ParkinsonReasonsInfo.visibility == View.VISIBLE) {
                 ParkinsonReasonsInfo.visibility = View.GONE
             } else {
                 ParkinsonReasonsInfo.visibility = View.VISIBLE
             }
         }
        SymptomsTitle.setOnClickListener {
            if (SymptomsInfo.visibility == View.VISIBLE) {
                SymptomsInfo.visibility = View.GONE
            } else {
                SymptomsInfo.visibility = View.VISIBLE
            }

        }

        EffectsTitle.setOnClickListener {
            if (EffectsInfo.visibility == View.VISIBLE) {
                EffectsInfo.visibility = View.GONE
            } else {
                EffectsInfo.visibility = View.VISIBLE
            }

        }
        TreatmentTitle.setOnClickListener {
            if (TreatmentInfo.visibility == View.VISIBLE) {
                TreatmentInfo.visibility = View.GONE
            } else {
                TreatmentInfo.visibility = View.VISIBLE
            }
        }
        TaiChiTitle.setOnClickListener {
            if (TaiChiInfo.visibility == View.VISIBLE) {
                TaiChiInfo.visibility = View.GONE
            } else {
                TaiChiInfo.visibility = View.VISIBLE
            }
        }

        TaiChiEffectTitle.setOnClickListener {
            if (TaiChiEffectInfo.visibility == View.VISIBLE) {
                TaiChiEffectInfo.visibility = View.GONE
            } else {
                TaiChiEffectInfo.visibility = View.VISIBLE
            }
        }

    }

    }
