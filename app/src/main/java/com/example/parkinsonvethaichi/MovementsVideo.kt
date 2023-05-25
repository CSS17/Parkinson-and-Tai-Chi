package com.example.parkinsonvethaichi

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
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
import kotlinx.android.synthetic.main.custom_controller.*
import java.util.*
import kotlin.concurrent.timer

class MovementsVideo : AppCompatActivity() {

    var isFullScreen=false
    private lateinit var exoPlayer: ExoPlayer
    private var time:Long=0
    private var milis:Long=0
    private var timer = Timer()
    private var flag = false
    private lateinit var fulltime:ArrayList<StatisticsModel>
    private lateinit var sqLiteHelper : SQLiteHelper
    val calendar = Calendar.getInstance()
    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    private lateinit var connectivityManager: ConnectivityManager
    private var dialogFlag=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movements_video)
        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (!isConnected()) {
            showNoConnectionDialog()
        }
        else{
            var actionBar = supportActionBar
            actionBar?.title = "Tai Chi Movements"
            sqLiteHelper = SQLiteHelper(this)
            fulltime=sqLiteHelper.getAllStats()
            val storageRef = Firebase.storage.reference
            val videoList = intent.getStringArrayListExtra("arrayListKey")
            var num_video = intent.getIntExtra("intKey", 0)
            time= intent.getLongExtra("time",0)
            milis=intent.getLongExtra("milis",0)
            flag = intent.getBooleanExtra("flag",false)
            Log.d("BEBEK",flag.toString())
            Log.d("SATURN",num_video.toString())
            var videoRef = storageRef.child("videos/video"+num_video+".mp4")
            exoPlayer= SimpleExoPlayer.Builder(this).
            setSeekBackIncrementMs(5000).
            setSeekForwardIncrementMs(5000).build()
            val myView: View = findViewById(R.id.player2)
            movementtitle.text=videoList?.get(num_video-1)

            button2.setOnClickListener(){

                if(num_video-1 !=0){
                    timer.cancel()
                    num_video=num_video-1
                    movementtitle.text=videoList?.get(num_video-1)
                    videoRef = storageRef.child("videos/video"+num_video+".mp4")
                    videoRef.downloadUrl.addOnSuccessListener { uri ->
                        val videoUrl = uri.toString()
                        // Video URL'sini kullanarak videoyu açmak için gerekli işlemleri yapın
                        val videoUri = Uri.parse(videoUrl)
                        val mediaItem= MediaItem.fromUri(videoUri)
                        exoPlayer.setMediaItem(mediaItem)

                        exoPlayer.playWhenReady = false
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
                    timer.cancel()
                    num_video=num_video+1
                    movementtitle.text=videoList?.get(num_video-1)
                    videoRef = storageRef.child("videos/video"+num_video+".mp4")
                    videoRef.downloadUrl.addOnSuccessListener { uri ->
                        val videoUrl = uri.toString()
                        // Video URL'sini kullanarak videoyu açmak için gerekli işlemleri yapın
                        val videoUri = Uri.parse(videoUrl)
                        val mediaItem= MediaItem.fromUri(videoUri)
                        exoPlayer.setMediaItem(mediaItem)

                        exoPlayer.playWhenReady = false
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
                    else if (playbackState == Player.STATE_ENDED) {
                        timer.cancel()
                        exoPlayer.playWhenReady = false
                        exoPlayer.seekTo(0)
                    }
                }

            })

            exo_play.setOnClickListener {
                exoPlayer.playWhenReady = true
                timer = timer(period = 100) {
                    milis += 100
                    time = milis / 1000
                    Log.d("ALİHAN", "Geçen süre BUUUUUUUUUUUUUUU : $time saniye")
                }
            }

            exo_pause.setOnClickListener {
                exoPlayer.playWhenReady = false
                timer?.cancel()
            }

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

    }

    private fun isConnected(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            return networkInfo.isConnected
        }
    }
    private fun showNoConnectionDialog() {
        dialogFlag=true
        val builder = AlertDialog.Builder(this)
        builder.setTitle("İnternet Bağlantısı Yok")
        builder.setMessage("İnternet bağlantınız kapalı. Bağlantıyı açmak ister misiniz?")
        builder.setPositiveButton("Aç") { dialog, which ->
            openWifiSettings()
        }
        builder.setNegativeButton("İptal") { dialog, which ->
            finish()
        }
        builder.setCancelable(false)
        builder.show()
    }
    private fun openWifiSettings() {
        val intent = Intent(android.provider.Settings.ACTION_WIFI_SETTINGS)
        startActivity(intent)
        finish()
    }


    override fun onBackPressed() {
        if(!isFullScreen){
            flag=false
            timer.cancel()
            exoPlayer?.stop()
            exoPlayer?.release()
            val intent = Intent(this, Movements::class.java)
            Log.d("GONYA",time.toString())
            intent.putExtra("time",time)
            intent.putExtra("milis",milis)
            intent.putExtra("flag",flag)
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

    private fun addTime(day_name:String,day:Int){
        var Time= time.toInt()+fulltime.get(day).spend_time
        sqLiteHelper.instertTime(StatisticsModel(day_name,Time))
    }


    private fun getDay(){
        when (dayOfWeek) {

            Calendar.MONDAY -> {
                println("Bugün Pazartesi")
                addTime("Pazartesi",0)
            }
            Calendar.TUESDAY -> {
                println("Bugün Salı")
                addTime("Salı",1)
            }
            Calendar.WEDNESDAY -> {
                println("Bugün Çarşamba")
                addTime("Çarşamba",2)
            }
            Calendar.THURSDAY -> {
                println("Bugün Perşembe")
                addTime("Perşembe",3)
            }
            Calendar.FRIDAY -> {
                println("Bugün Cuma")
                addTime("Cuma",4)
            }
            Calendar.SATURDAY -> {
                println("Bugün Cumartesi")
                addTime("Cumartesi",5)
            }
            Calendar.SUNDAY -> {
                println("Bugün Pazar")
                addTime("Pazar",6)
            }

        }


    }

    override fun onPause() {
        super.onPause()
        if(!dialogFlag){
            exoPlayer.playWhenReady=false
            if(flag){
                getDay()
            }
            Log.d("TUŞ","ONPAUSE")
        }

    }





}