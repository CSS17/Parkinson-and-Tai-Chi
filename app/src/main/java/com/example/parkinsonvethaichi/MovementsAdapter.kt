package com.example.parkinsonvethaichi

import android.app.Activity
import android.content.Intent
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.parkinsonvethaichi.databinding.MovementRecyclerRowBinding
import com.google.android.exoplayer2.ExoPlayer
import java.util.Timer
import java.util.logging.Handler

class MovementsAdapter(private val MovementList: ArrayList<MovementsModel>, private val exoPlayer: ExoPlayer,private var elapsedSeconds:Long,private var elapsedTimeInMillis:Long,private val movements: Movements,private var flag:Boolean) : RecyclerView.Adapter<MovementsAdapter.MovementHolder>(){
    private val handler = android.os.Handler(Looper.getMainLooper())
    val arrayList = ArrayList<String>() // GÃ¶nderilecek ArrayList



    class MovementHolder(val binding : MovementRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovementHolder {
        val binding = MovementRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return MovementHolder(binding)
    }

    override fun getItemCount(): Int {
        return MovementList.size
    }


    override fun onBindViewHolder(holder: MovementHolder, position: Int) {
        arrayList.add("Hareket 1")
        arrayList.add("Hareket 2")
        arrayList.add("Hareket 3")
        arrayList.add("Hareket 4")
        arrayList.add("Hareket 5")
        arrayList.add("Hareket 6")
        arrayList.add("Hareket 7")
        arrayList.add("Hareket 8")
        arrayList.add("Hareket 9")
        arrayList.add("Hareket 10")
        arrayList.add("Hareket 11")
        arrayList.add("Hareket 12")
        arrayList.add("Hareket 13")
        arrayList.add("Hareket 14")
        arrayList.add("Hareket 15")
        arrayList.add("Hareket 16")
        arrayList.add("Hareket 17")
        arrayList.add("Hareket 18")
        arrayList.add("Hareket 19")
        arrayList.add("Hareket 20")
        arrayList.add("Hareket 21")
        arrayList.add("Hareket 22")
        arrayList.add("Hareket 23")
        arrayList.add("Hareket 24")



        holder.binding.recyclerText.text = MovementList.get(position).Movements_name

        holder.itemView.setOnClickListener {
            exoPlayer.playWhenReady = false

            movements.stopTimer()
            movements.flag_true()
            flag = true
            val intent = Intent(holder.itemView.context, MovementsVideo::class.java)

            intent.putStringArrayListExtra("arrayListKey", arrayList)
            intent.putExtra("intKey", position + 1)
            intent.putExtra("time", elapsedSeconds)
            intent.putExtra("milis", elapsedTimeInMillis)
            intent.putExtra("flag",flag)

            holder.itemView.context.startActivity(intent)



        }
    }


    fun updateElapsedTime(elapsedSeconds: Long,elapsedTimeInMillis:Long) {
        handler.post {
            this.elapsedSeconds = elapsedSeconds
            this.elapsedTimeInMillis=elapsedTimeInMillis

        }
    }


}
