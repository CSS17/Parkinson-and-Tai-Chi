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

class MedicineAdapter(private val medicine_list:ArrayList<MedicineModel>,val context: Context):RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>() {
        var onItemClick: ((MedicineModel)->Unit)?=null
        private lateinit var sqLiteHelper : SQLiteHelper
        private lateinit var recyclerView: RecyclerView
        private lateinit var medicineList: ArrayList<MedicineList>

    class MedicineViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val medicine_name:TextView=itemView.findViewById(R.id.medicine_name)
        val medicine_time:TextView=itemView.findViewById(R.id.medicine_time)
        val myButton: ImageButton = itemView.findViewById(R.id.delete)

        init {
            myButton.setOnClickListener {
                // Handle button click event
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int,): MedicineViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.medicines,parent,false)
        val listview=LayoutInflater.from(parent.context).inflate(R.layout.activity_medicine,parent,false)
        sqLiteHelper = SQLiteHelper(context)
        recyclerView=listview.findViewById(R.id.list)
        return MedicineViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        val medicine=medicine_list[position]
        holder.medicine_name.text=medicine.medicine_name
        holder.medicine_time.text=medicine.medicine_hour+":"+medicine.medicine_minute


        // Pass the current item's position to the ViewHolder
        holder.myButton.setOnClickListener {
            // Handle button click event
            val currentItem = medicine_list[holder.adapterPosition]
            Toast.makeText(context,currentItem.medicine_name,Toast.LENGTH_SHORT).show()
            sqLiteHelper.deleteRecord(currentItem.id)


            /*val mdcList = sqLiteHelper.getAllMedicine()
            val medicineAdapter= MedicineAdapter(mdcList,context)
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter=medicineAdapter
            medicineList=ArrayList()
            recyclerView.adapter=medicineAdapter*/





        }


    }



    override fun getItemCount(): Int {
       return medicine_list.size
    }
}