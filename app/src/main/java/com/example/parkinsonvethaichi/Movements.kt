package com.example.parkinsonvethaichi

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AlertDialog
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
import java.util.*
import kotlin.collections.ArrayList
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
    private lateinit var fulltime:ArrayList<StatisticsModel>
    private var elapsedSeconds:Long=0
    private var elapsedTimeInMillis: Long = 0
    private var isVideoPlaying = false
    private var flag=false
    val calendar = Calendar.getInstance()
    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    private var dialogFlag=false
    private lateinit var connectivityManager: ConnectivityManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movements)
        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (!isConnected()) {
            showNoConnectionDialog()
        }
        else{
            Log.d("TUŞ","ONCREATE")
            sqLiteHelper = SQLiteHelper(this)
            fulltime=sqLiteHelper.getAllStats()
            //val stats = sqLiteHelper.getAllStats()
            var elapsedTimeInSeconds = 0

            elapsedSeconds= intent.getLongExtra("time",0)
            elapsedTimeInMillis=intent.getLongExtra("milis",0)
            flag=intent.getBooleanExtra("flag",false)
            Log.d("ALİHAN","GELEN DEĞER:"+elapsedSeconds)

            //Log.d("VENUSS",stats.get(0).day_of_week)

            var actionBar = supportActionBar
            actionBar?.title = "Tai Chi Movements"
            recyclerView = findViewById(R.id.MovementList)
            recyclerView.layoutManager = LinearLayoutManager(this)
            MovementArrayList = ArrayList<MovementsModel>()



            // Timer'ı durdurmak için bir süre bekleyelim
            timer?.cancel()

            for (i in 1..24) {
                val movement = MovementsModel(i.toString() + "."+" Egzersiz")
                MovementArrayList.add(movement)
            }
            Log.d("Saturn",MovementArrayList.get(10).Movements_name)


            Log.d("Saaaaturn",MovementArrayList.get(10).Movements_name)

            val storageRef = Firebase.storage.reference
            val videoRef = storageRef.child("videos/video.mp4")
            simpleExoplayer=SimpleExoPlayer.Builder(this).
            setSeekBackIncrementMs(5000).
            setSeekForwardIncrementMs(5000).build()
            val myView: View = findViewById(R.id.player)
            MovementsAdapter = MovementsAdapter(MovementArrayList,simpleExoplayer,elapsedSeconds,elapsedTimeInMillis,this,flag)
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
                        timer?.cancel()
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



            exo_play.setOnClickListener {
                simpleExoplayer.playWhenReady = true
                timer = timer(period = 100) {
                    elapsedTimeInMillis += 100
                    elapsedSeconds = elapsedTimeInMillis / 1000
                    MovementsAdapter.updateElapsedTime(elapsedSeconds,elapsedTimeInMillis)
                    Log.d("ALİHAN", "Geçen süre Çalışmaya devam: $elapsedSeconds saniye")
                }


            }

            exo_pause.setOnClickListener {
                simpleExoplayer.playWhenReady = false
                timer?.cancel()
                MovementsAdapter.updateElapsedTime(elapsedSeconds,elapsedTimeInMillis)

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
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        builder.setCancelable(false)
        builder.show()
    }

    private fun openWifiSettings() {
        val intent = Intent(android.provider.Settings.ACTION_WIFI_SETTINGS)
        startActivity(intent)
        finish()
    }



    private fun addTime(day_name:String,day:Int){
        var time= elapsedSeconds.toInt()+fulltime.get(day).spend_time
        sqLiteHelper.instertTime(StatisticsModel(day_name,time))
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




    fun stopTimer() {
        timer?.cancel()
        timer = null
    }

    override fun onBackPressed() {
        if (!isFullScreen){
            stopTimer()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            simpleExoplayer?.stop()
            simpleExoplayer?.release()
            finish()
            getDay()

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
    fun flag_true() {
        flag=true
    }

    override fun onStop() {
        super.onStop()
        if(!dialogFlag){
            simpleExoplayer.playWhenReady=false
            if(!flag){
                Log.d("TUŞ","Şimdi ekleme yapacağım")
            }
            else{
                Log.d("TUŞ","Şimdi ekleme YAPAMAM")
            }
            Log.d("TUŞ","ONSTOP")
        }

    }

    override fun onPause() {
        super.onPause()
        if(!dialogFlag){
            simpleExoplayer.playWhenReady=false
            Log.d("TUŞ","ONPAUSE")
            if(!flag){
                Log.d("TUŞ","Şimdi ekleme yapacağım")
                getDay()
            }
            else{
                Log.d("TUŞ","Şimdi ekleme YAPAMAM")
            }
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        if(!dialogFlag){
            simpleExoplayer.playWhenReady=false
            if(!flag){
                Log.d("TUŞ","Şimdi ekleme yapacağım")
            }
            else{
                Log.d("TUŞ","Şimdi ekleme YAPAMAM")
            }
            Log.d("TUŞ","ONDESTROY")
        }

    }
    override fun onResume() {
        super.onResume()
        Log.d("TUŞ","ONRESUME")

    }

    override fun onStart() {
        super.onStart()
        Log.d("TUŞ","ONSTART")
    }


}
