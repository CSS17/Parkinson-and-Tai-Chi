package com.example.parkinsonvethaichi

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_tai_chi_parkinson.*
import java.io.*


class TaiChiParkinson : AppCompatActivity() {

    private lateinit var mediaPlayer1: MediaPlayer
    private lateinit var mediaPlayer2: MediaPlayer
    private lateinit var mediaPlayer3: MediaPlayer
    private lateinit var mediaPlayer4: MediaPlayer
    private lateinit var mediaPlayer5: MediaPlayer
    private lateinit var mediaPlayer6: MediaPlayer
    private lateinit var mediaPlayer7: MediaPlayer



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tai_chi_parkinson)

        mediaPlayer1 = MediaPlayer.create(this, R.raw.record)
        mediaPlayer2 = MediaPlayer.create(this, R.raw.record2)
        mediaPlayer3 = MediaPlayer.create(this, R.raw.record3)
        mediaPlayer4 = MediaPlayer.create(this, R.raw.record4)
        mediaPlayer5 = MediaPlayer.create(this, R.raw.record5)
        mediaPlayer6 = MediaPlayer.create(this, R.raw.record6)
        mediaPlayer7 = MediaPlayer.create(this, R.raw.record7)

        val mediaPlayers= arrayOf(mediaPlayer1,mediaPlayer2,mediaPlayer3,mediaPlayer4,mediaPlayer5,mediaPlayer6,mediaPlayer7)

        fun stopMedia(){
            for(i in mediaPlayers){
                if(i.isPlaying){
                    i.stop()
                    i.prepare()
                }
            }
        }



        fun playMusic(mediaPlayer:MediaPlayer,voiceButton: ImageButton,view:View){
            if(mediaPlayer.isPlaying){
                mediaPlayer.stop()
                mediaPlayer.prepare()
                voiceButton.setImageResource(R.drawable.volume_base)
            }
            else{
                mediaPlayer.start()
                voiceButton.setImageResource(R.drawable.volume)
            }
            mediaPlayer.setOnCompletionListener {
                voiceButton.setImageResource(R.drawable.volume_base)
            }
        }


        fun voiceClick(view: View){
            //stopMedia()
            when (view.id) {
                R.id.ses_butonu_1 -> playMusic(mediaPlayer1,ses_butonu_1, view)
                R.id.ses_butonu_2 -> playMusic(mediaPlayer2,ses_butonu_2, view)
                R.id.ses_butonu_3 -> playMusic(mediaPlayer3,ses_butonu_3, view)
                R.id.ses_butonu_4 -> playMusic(mediaPlayer4,ses_butonu_4, view)
                R.id.ses_butonu_5 -> playMusic(mediaPlayer5,ses_butonu_5, view)
                R.id.ses_butonu_6 -> playMusic(mediaPlayer6,ses_butonu_6, view)
                R.id.ses_butonu_7 -> playMusic(mediaPlayer7,ses_butonu_7, view)
            }

        }
        ses_butonu_1.setOnClickListener(::voiceClick)
        ses_butonu_2.setOnClickListener(::voiceClick)
        ses_butonu_3.setOnClickListener(::voiceClick)
        ses_butonu_4.setOnClickListener(::voiceClick)
        ses_butonu_5.setOnClickListener(::voiceClick)
        ses_butonu_6.setOnClickListener(::voiceClick)
        ses_butonu_7.setOnClickListener(::voiceClick)




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
    override fun onDestroy() {
        mediaPlayer1.release()
        mediaPlayer2.release()
        mediaPlayer3.release()
        mediaPlayer4.release()
        mediaPlayer5.release()
        mediaPlayer6.release()
        mediaPlayer7.release()
        super.onDestroy()
    }

}