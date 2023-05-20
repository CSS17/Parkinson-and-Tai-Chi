package com.example.parkinsonvethaichi

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_movements.*
import kotlinx.android.synthetic.main.custom_controller.*
import java.time.Duration
import java.util.Timer
import kotlin.concurrent.timer
import kotlin.math.log


class Movements : AppCompatActivity() {
    private lateinit var sqLiteHelper : SQLiteHelper
    var isFullScreen=false
    private lateinit var MovementsAdapter : MovementsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var MovementArrayList : ArrayList<MovementsModel>
    private lateinit var simpleExoplayer: ExoPlayer
    private var timer: Timer? = null
    private var elapsedSeconds:Long=0
    private var elapsedTimeInMillis: Long = 0
    private var isVideoPlaying = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movements)
        sqLiteHelper = SQLiteHelper(this)
        //val stats = sqLiteHelper.getAllStats()
        var timer = Timer()
        var elapsedTimeInSeconds = 0
        elapsedSeconds= intent.getLongExtra("time",0)
        elapsedTimeInMillis=intent.getLongExtra("milis",0)
        Log.d("ALİHAN","GELEN DEĞER:"+elapsedSeconds)
        //Log.d("VENUSS",stats.get(0).day_of_week)

        var actionBar = supportActionBar
        actionBar?.title = "Tai Chi Movements"
        recyclerView = findViewById(R.id.MovementList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        MovementArrayList = ArrayList<MovementsModel>()



        // Timer'ı durdurmak için bir süre bekleyelim
        timer.cancel()

        for (i in 1..24) {
            val movement = MovementsModel(i.toString() + "."+" Hareket")
            MovementArrayList.add(movement)
        }
        Log.d("Saturn",MovementArrayList.get(10).Movements_name)


        Log.d("Saaaaturn",MovementArrayList.get(10).Movements_name)

        val storageRef = Firebase.storage.reference
        val videoRef = storageRef.child("videos/video1.mp4")
        simpleExoplayer=SimpleExoPlayer.Builder(this).
        setSeekBackIncrementMs(5000).
        setSeekForwardIncrementMs(5000).build()
        val myView: View = findViewById(R.id.player)
        MovementsAdapter = MovementsAdapter(MovementArrayList,simpleExoplayer,elapsedSeconds,elapsedTimeInMillis,timer)
        recyclerView.adapter = MovementsAdapter
        fullscreen.setOnClickListener {

            if (!isFullScreen){
                fullscreen.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.fullscreen_exit))
                supportActionBar?.hide()
                val layoutParams = myView.layoutParams as LinearLayout.LayoutParams
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT // Genişliği "match_parent" olarak ayarlar
                layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT // Yüksekliği "match_parent" olarak ayarlar
                myView.layoutParams = layoutParams
                layoutParams.setMargins(0,0,0,0)
                myView.requestLayout()
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
            }
            else{
                fullscreen.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.fullscreen_active))
                val density = resources.displayMetrics.density
                val heightInDp = 300
                val marginInDp = 10
                val marginPixels = (marginInDp * density + 0.5f).toInt()
                val heightInPixels = (heightInDp * density + 0.5f).toInt()
                val layoutParams = myView.layoutParams as LinearLayout.LayoutParams
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT// Genişlik değerini günceller
                myView.layoutParams.height = heightInPixels // Yükseklik değerini günceller
                layoutParams.setMargins(marginPixels,marginPixels,marginPixels,marginPixels)
                myView.requestLayout()
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            }

            isFullScreen=!isFullScreen

        }


        player.player=simpleExoplayer
        player.keepScreenOn=true

        simpleExoplayer.addListener(object: Player.Listener{
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {

                if(playbackState==Player.STATE_BUFFERING){
                    progress_bar.visibility=View.VISIBLE

                }
                else if(playbackState==Player.STATE_READY){
                    progress_bar.visibility=View.GONE

                }
                else if (playbackState == Player.STATE_ENDED) {
                    timer.cancel()
                    simpleExoplayer.playWhenReady = false
                    simpleExoplayer.seekTo(0)

                }
            }

        })



        videoRef.downloadUrl.addOnSuccessListener { uri ->
            val videoUrl = uri.toString()
            // Video URL'sini kullanarak videoyu açmak için gerekli işlemleri yapın
            val videoUri = Uri.parse(videoUrl)
            val mediaItem=MediaItem.fromUri(videoUri)
            simpleExoplayer.setMediaItem(mediaItem)
            simpleExoplayer.prepare()

            //simpleExoplayer.play()

            //videoView.setVideoURI(videoUri)
            //videoView.start()
        }.addOnFailureListener { exception ->
            // Hata durumunda işlem yapın
        }

        recyclerView.setOnTouchListener { _, event ->
            // Video oynatıcısı çalışıyorsa tıklama olayını engelle
            if (isVideoPlaying) {
                true
            } else {
                // Video oynatıcısı çalışmıyorsa tıklama olayını devam ettir
                false
            }
        }

        exo_play.setOnClickListener {
            simpleExoplayer.playWhenReady = true
            timer = timer(period = 100) {
                elapsedTimeInMillis += 100
                elapsedSeconds = elapsedTimeInMillis / 1000
                Log.d("ALİHAN", "Geçen süre: $elapsedSeconds saniye")
                MovementsAdapter.updateElapsedTime(elapsedSeconds,elapsedTimeInMillis)
            }
        }

        exo_pause.setOnClickListener {
            simpleExoplayer.playWhenReady = false
            timer?.cancel()
            MovementsAdapter.updateElapsedTime(elapsedSeconds,elapsedTimeInMillis)
        }




        MovementsAdapter.notifyDataSetChanged()

    }



    override fun onBackPressed() {
        if (!isFullScreen){
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            simpleExoplayer?.stop()
            simpleExoplayer?.release()
            finish()
        }
        else{
            fullscreen.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.fullscreen_active))
            val density = resources.displayMetrics.density
            val heightInDp = 300
            val marginInDp = 10
            val marginPixels = (marginInDp * density + 0.5f).toInt()
            val heightInPixels = (heightInDp * density + 0.5f).toInt()
            val layoutParams = player.layoutParams as LinearLayout.LayoutParams
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT// Genişlik değerini günceller
            player.layoutParams.height = heightInPixels // Yükseklik değerini günceller
            layoutParams.setMargins(marginPixels,marginPixels,marginPixels,marginPixels)
            player.requestLayout()
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            isFullScreen=!isFullScreen
        }



    }

    override fun onPause() {
        super.onPause()
    }
    override fun onDestroy() {
        super.onDestroy()
    }
}