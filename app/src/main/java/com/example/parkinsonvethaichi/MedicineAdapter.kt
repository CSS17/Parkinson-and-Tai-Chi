package com.example.parkinsonvethaichi

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView

class MedicineAdapter:RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>() {

        private var onClickItem: ((MedicineModel) ->Unit)? = null
        private var onClickDeleteItem: ((MedicineModel) -> Unit)? = null
        private var mdcList: ArrayList<MedicineModel> =ArrayList()



    fun addItems(items: ArrayList<MedicineModel>){
        this.mdcList = items
        notifyDataSetChanged()
    }
    fun setOnClickItem(callback:(MedicineModel) -> Unit){
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback:(MedicineModel) -> Unit){
        this.onClickDeleteItem = callback
    }

    class MedicineViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val medicine_name:TextView=itemView.findViewById(R.id.medicine_name)
        val medicine_time:TextView=itemView.findViewById(R.id.medicine_time)
        val myButton: ImageButton = itemView.findViewById(R.id.delete)

        fun bindView(mdc:MedicineModel){
            medicine_name.text = mdc.medicine_name
            medicine_time.text = mdc.medicine_hour + ":" + mdc.medicine_minute
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MedicineViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.medicines,parent,false)

    )

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        val mdc=mdcList[position]
        holder.bindView(mdc)
        holder.itemView.setOnClickListener{ onClickItem?.invoke(mdc)}
        holder.myButton.setOnClickListener{ onClickDeleteItem?.invoke(mdc)}

    }



    override fun getItemCount(): Int {
       return mdcList.size
    }
}