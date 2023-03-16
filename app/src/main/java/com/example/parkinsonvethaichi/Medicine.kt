package com.example.parkinsonvethaichi

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class Medicine : AppCompatActivity() {
    private lateinit var sqLiteHelper : SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private  var medicineAdapter: MedicineAdapter?=null
    private lateinit var hourspinner: Spinner
    private lateinit var minutespinner: Spinner
    private lateinit var medicinename: EditText
    var mdc:MedicineModel?=null
    val hours = arrayOf("00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23")
    val minutes= arrayOf("00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59")
    var medicine_hour = ""
    var medicine_minute = ""
    var medicine_name = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicine)
        var actionBar=supportActionBar
        actionBar?.title="İlaç Saatleri Ayarla"
        initView()
        initRecyclerView()
        sqLiteHelper = SQLiteHelper(this)
        getMedicines()
        hourselect()
        minuteselect()
        medicineAdapter?.setOnClickItem {
            mdc=it
            Log.d("LANN",it.id.toString())
            Toast.makeText(this,it.medicine_name,Toast.LENGTH_SHORT).show()
        }
        medicineAdapter?.setOnClickDeleteItem  {
            deleteMedicine(it.id)
        }

    }

    fun hourselect(){
        val hourarrayAdapter= ArrayAdapter<String>(this,R.layout.spinner_item,hours)
        hourspinner.adapter = hourarrayAdapter
        hourspinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                medicine_hour = hours[p2]
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }
    fun minuteselect(){
        val minutearrayAdapter= ArrayAdapter<String>(this,R.layout.spinner_item,minutes)
        minutespinner.adapter = minutearrayAdapter
        minutespinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                medicine_minute = minutes[p2]
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun getMedicines(){
        val mdcList = sqLiteHelper.getAllMedicine()
        Log.d("SİZE BEBEK",mdcList.size.toString())
        medicineAdapter?.addItems(mdcList)
    }

    fun addmedicine(view: View) {
        medicine_name = medicinename.text.toString()
        if(medicine_name.isEmpty()||medicine_hour.isEmpty() || medicine_minute.isEmpty())
        {
            Toast.makeText(this,"Lütfen boş alan bırakmayınız",Toast.LENGTH_SHORT).show()
        }
        else{
            val mdc = MedicineModel(medicine_name = medicine_name, medicine_hour = medicine_hour, medicine_minute = medicine_minute)
            val status = sqLiteHelper.instertMedicine(mdc)
            if(status > -1){
                Toast.makeText(this,"İlaç Eklendi...",Toast.LENGTH_SHORT).show()
                medicinename.setText(" ")
                minutespinner.setSelection(0)
                hourspinner.setSelection(0)
                getMedicines()
            }
            else{
                Toast.makeText(this,"İlaç Eklenemedi",Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun write_medicine(view: View) {
        medicinename.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                medicinename.setHint("")
            } else {
                medicinename.setHint("Your hint")
            }
        })

    }

    private fun deleteMedicine(id:Int){

       val builder = AlertDialog.Builder(this)
        builder.setMessage("İlaç silinsin mi?")
        builder.setCancelable(true)
        builder.setPositiveButton("Evet") { dialog, _ ->
            sqLiteHelper.deleteRecord(id)
            getMedicines()
            dialog.dismiss()
            Toast.makeText(this,"İlaç Silindi",Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("Hayır"){ dialog, _ ->
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }



    private fun initView(){
        hourspinner = findViewById(R.id.saatsec)
        minutespinner = findViewById(R.id.dakikasec)
        medicinename= findViewById(R.id.MedicineName)
        recyclerView=findViewById(R.id.list)

    }

    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        medicineAdapter = MedicineAdapter()
        recyclerView.adapter = medicineAdapter

    }




}