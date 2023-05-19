package com.example.parkinsonvethaichi

import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_movements.*
import kotlinx.android.synthetic.main.activity_movements_video.button2
import kotlinx.android.synthetic.main.activity_movements_video.button3
import kotlinx.android.synthetic.main.activity_movements_video.movementtitle

import kotlinx.android.synthetic.main.activity_movements_video.player2
import kotlinx.android.synthetic.main.activity_movements_video.progress_bar2
import kotlinx.android.synthetic.main.custom_controller.fullscreen

class MovementsVideo : AppCompatActivity() {

    var isFullScreen=false
    private lateinit var exoPlayer: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movements_video)
        var actionBar = supportActionBar
        actionBar?.title = "Tai Chi Movements"
        val storageRef = Firebase.storage.reference
        val videoList = intent.getStringArrayListExtra("arrayListKey")
        var num_video = intent.getIntExtra("intKey", 0)
        Log.d("SATURN",num_video.toString())
        var videoRef = storageRef.child("videos/video"+num_video+".mp4")
        exoPlayer= SimpleExoPlayer.Builder(this).
        setSeekBackIncrementMs(5000).
        setSeekForwardIncrementMs(5000).build()
        val myView: View = findViewById(R.id.player2)

        movementtitle.text=videoList?.get(num_video-1)

        button2.setOnClickListener(){

            if(num_video-1 !=0){
                num_video=num_video-1
                movementtitle.text=videoList?.get(num_video-1)
                videoRef = storageRef.child("videos/video"+num_video+".mp4")
                videoRef.downloadUrl.addOnSuccessListener { uri ->
                    val videoUrl = uri.toString()
                    // Video URL'sini kullanarak videoyu açmak için gerekli işlemleri yapın
                    val videoUri = Uri.parse(videoUrl)
                    val mediaItem= MediaItem.fromUri(videoUri)
                    exoPlayer.setMediaItem(mediaItem)

                    exoPlayer.stop()
                    exoPlayer.prepare()
                    //simpleExoplayer.play()

                    //videoView.setVideoURI(videoUri)
                    //videoView.start()
                }.addOnFailureListener { exception ->
                    // Hata durumunda işlem yapın
                }
            }
        }

        button3.setOnClickListener(){
            if(num_video+1 !=25){
                num_video=num_video+1
                movementtitle.text=videoList?.get(num_video-1)
                videoRef = storageRef.child("videos/video"+num_video+".mp4")
                videoRef.downloadUrl.addOnSuccessListener { uri ->
                    val videoUrl = uri.toString()
                    // Video URL'sini kullanarak videoyu açmak için gerekli işlemleri yapın
                    val videoUri = Uri.parse(videoUrl)
                    val mediaItem= MediaItem.fromUri(videoUri)
                    exoPlayer.setMediaItem(mediaItem)

                    exoPlayer.stop()
                    exoPlayer.prepare()
                    //simpleExoplayer.play()

                    //videoView.setVideoURI(videoUri)
                    //videoView.start()
                }.addOnFailureListener { exception ->
                    // Hata durumunda işlem yapın
                }
            }
        }

        fullscreen.setOnClickListener {

            if (!isFullScreen){
                fullscreen.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.fullscreen_exit))
                supportActionBar?.hide()
                val layoutParams = myView.layoutParams as LinearLayout.LayoutParams
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT // Genişliği "match_parent" olarak ayarlar
                layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT // Yüksekliği "match_parent" olarak ayarlar
                myView.layoutParams = layoutParams
                movementtitle.visibility = View.GONE
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
                movementtitle.visibility = View.VISIBLE
                myView.requestLayout()
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            }
            Log.d("SATURN","bu 1 ")
            isFullScreen=!isFullScreen

        }


        player2.player=exoPlayer
        player2.keepScreenOn=true
        exoPlayer.addListener(object: Player.Listener{
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if(playbackState== Player.STATE_BUFFERING){
                    progress_bar2.visibility= View.VISIBLE
                }
                else if(playbackState== Player.STATE_READY){
                    progress_bar2.visibility= View.GONE
                }
            }

        })

        videoRef.downloadUrl.addOnSuccessListener { uri ->
            val videoUrl = uri.toString()
            // Video URL'sini kullanarak videoyu açmak için gerekli işlemleri yapın
            val videoUri = Uri.parse(videoUrl)
            val mediaItem= MediaItem.fromUri(videoUri)
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()

            //simpleExoplayer.play()

            //videoView.setVideoURI(videoUri)
            //videoView.start()
        }.addOnFailureListener { exception ->
            // Hata durumunda işlem yapın
        }
    }













    override fun onBackPressed() {
        if(!isFullScreen){
            exoPlayer?.stop()
            exoPlayer?.release()
            val intent = Intent(this, Movements::class.java)
            startActivity(intent)
            finish()
        }
        else{
            fullscreen.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.fullscreen_active))
            val density = resources.displayMetrics.density
            val heightInDp = 300
            val marginInDp = 10
            val marginPixels = (marginInDp * density + 0.5f).toInt()
            val heightInPixels = (heightInDp * density + 0.5f).toInt()
            val layoutParams = player2.layoutParams as LinearLayout.LayoutParams
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT// Genişlik değerini günceller
            player2.layoutParams.height = heightInPixels // Yükseklik değerini günceller
            layoutParams.setMargins(marginPixels,marginPixels,marginPixels,marginPixels)
            player2.requestLayout()
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            isFullScreen=!isFullScreen
        }

    }
}