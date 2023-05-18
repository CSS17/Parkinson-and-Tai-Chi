package com.example.parkinsonvethaichi

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.parkinsonvethaichi.databinding.MovementRecyclerRowBinding

class MovementsAdapter(private val MovementList: ArrayList<MovementsModel>) : RecyclerView.Adapter<MovementsAdapter.MovementHolder>(){


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
        holder.binding.recyclerText.text = MovementList.get(position).Movements_name
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context,MovementsVideo::class.java)
            holder.itemView.context.startActivity(intent)
        }
    }


}