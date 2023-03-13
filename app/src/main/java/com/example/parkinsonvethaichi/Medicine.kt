package com.example.parkinsonvethaichi

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class Medicine : AppCompatActivity() {
    lateinit var mdcList:ArrayList<MedicineModel>
    private lateinit var sqLiteHelper : SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var medicineList: ArrayList<MedicineList>
    private lateinit var medicineAdapter: MedicineAdapter
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
        sqLiteHelper = SQLiteHelper(this)
        mdcList = sqLiteHelper.getAllMedicine()
        recyclerView=findViewById(R.id.list)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager=LinearLayoutManager(this)

        medicineList=ArrayList()


        medicineAdapter= MedicineAdapter(mdcList)
        recyclerView.adapter=medicineAdapter


        hourselect()
        minuteselect()


    }

    fun hourselect(){
        val hourspinner = findViewById<Spinner>(R.id.saatsec)

        val hourarrayAdapter= ArrayAdapter<String>(this,R.layout.spinner_item,hours)
        hourspinner.adapter = hourarrayAdapter

        hourspinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(applicationContext,"Seçilen Saat: "+ hours[p2],Toast.LENGTH_SHORT).show()
                medicine_hour = hours[p2]
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }
    fun minuteselect(){
        val minutespinner = findViewById<Spinner>(R.id.dakikasec)

        val minutearrayAdapter= ArrayAdapter<String>(this,R.layout.spinner_item,minutes)
        minutespinner.adapter = minutearrayAdapter

        minutespinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(applicationContext,"Seçilen Dakika: "+ minutes[p2],Toast.LENGTH_SHORT).show()
                medicine_minute = minutes[p2]
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    fun addmedicine(view: View) {
        val medicinename= findViewById<EditText>(R.id.MedicineName)
        val minutespinner = findViewById<Spinner>(R.id.dakikasec)
        val hourspinner = findViewById<Spinner>(R.id.saatsec)
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
                mdcList = sqLiteHelper.getAllMedicine()
                medicineList=ArrayList()
                medicineAdapter= MedicineAdapter(mdcList)
                recyclerView.adapter=medicineAdapter
            }
            else{
                Toast.makeText(this,"İlaç Eklenemedi",Toast.LENGTH_SHORT).show()
            }
        }





    }

    fun write_medicine(view: View) {
        val medicinename = findViewById<EditText>(R.id.MedicineName)
        medicinename.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                medicinename.setHint("")
            } else {
                medicinename.setHint("Your hint")
            }
        })

    }






}