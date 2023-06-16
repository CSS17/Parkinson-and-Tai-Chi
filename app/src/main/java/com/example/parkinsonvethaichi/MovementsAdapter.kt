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
    val arrayList = ArrayList<String>()



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
        arrayList.add("Egzersiz 1")
        arrayList.add("Egzersiz 2")
        arrayList.add("Egzersiz 3")
        arrayList.add("Egzersiz 4")
        arrayList.add("Egzersiz 5")
        arrayList.add("Egzersiz 6")
        arrayList.add("Egzersiz 7")
        arrayList.add("Egzersiz 8")
        arrayList.add("Egzersiz 9")
        arrayList.add("Egzersiz 10")
        arrayList.add("Egzersiz 11")
        arrayList.add("Egzersiz 12")
        arrayList.add("Egzersiz 13")
        arrayList.add("Egzersiz 14")
        arrayList.add("Egzersiz 15")
        arrayList.add("Egzersiz 16")
        arrayList.add("Egzersiz 17")
        arrayList.add("Egzersiz 18")
        arrayList.add("Egzersiz 19")
        arrayList.add("Egzersiz 20")
        arrayList.add("Egzersiz 21")
        arrayList.add("Egzersiz 22")
        arrayList.add("Egzersiz 23")
        arrayList.add("Egzersiz 24")



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
