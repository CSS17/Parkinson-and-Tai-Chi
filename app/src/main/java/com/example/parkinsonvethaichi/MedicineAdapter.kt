package com.example.parkinsonvethaichi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView

class MedicineAdapter(private val medicine_list:ArrayList<MedicineModel>)
    :RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>()
{
    class MedicineViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val medicine_name:TextView=itemView.findViewById(R.id.medicine_name)
        val medicine_time:TextView=itemView.findViewById(R.id.medicine_time)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.medicines,parent,false)
        return MedicineViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        val medicine=medicine_list[position]
        holder.medicine_name.text=medicine.medicine_name
        holder.medicine_time.text=medicine.medicine_hour+":"+medicine.medicine_minute
    }

    override fun getItemCount(): Int {
       return medicine_list.size
    }
}