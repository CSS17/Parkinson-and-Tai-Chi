package com.example.parkinsonvethaichi

import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_movements.*
import kotlinx.android.synthetic.main.custom_controller.*


class Movements : AppCompatActivity() {
        var isFullScreen=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movements)
        var actionBar = supportActionBar
        actionBar?.title = "Tai Chi Movements"

        val storageRef = Firebase.storage.reference
        val videoRef = storageRef.child("videos/video.mp4")
        val simpleExoplayer=SimpleExoPlayer.Builder(this).
        setSeekBackIncrementMs(5000).
        setSeekForwardIncrementMs(5000).build()

        fullscreen.setOnClickListener {

            if (!isFullScreen){
                fullscreen.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.fullscreen_exit))
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
            }
            else{
                fullscreen.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.fullscreen_active))
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
            }

        })

        videoRef.downloadUrl.addOnSuccessListener { uri ->
            val videoUrl = uri.toString()
            // Video URL'sini kullanarak videoyu açmak için gerekli işlemleri yapın
            val videoUri = Uri.parse(videoUrl)
            val mediaItem=MediaItem.fromUri(videoUri)
            simpleExoplayer.setMediaItem(mediaItem)
            simpleExoplayer.prepare()
            simpleExoplayer.play()

            //videoView.setVideoURI(videoUri)
            //videoView.start()
        }.addOnFailureListener { exception ->
            // Hata durumunda işlem yapın
        }






    }



    override fun onBackPressed() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
