package com.example.parkinsonvethaichi

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.effect.Effect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import java.io.*


class TaiChiParkinson : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tai_chi_parkinson)
        var actionBar = supportActionBar
        actionBar?.title = "Tai Chi ve Parkinson"

        var parkinsoncount=0
        var parkinsonreasonscount=0
        var symptomscount=0
        var effectscount=0

        val filename = "ParkinsonHastaligi_nedir.txt" // replace with the name of your file
        val startRow = 1 // replace with the row number where you want to start saving
        val endRow = 13 // replace with the row number where you want to stop saving
        val ParkinsonInfo= findViewById<TextView>(R.id.ParkinsonInfo)
        val ParkinsonReasonsInfo = findViewById<TextView>(R.id.ParkinsonReasonsInfo)
        val SymptomsInfo = findViewById<TextView>(R.id.SymptomsInfo)
        val EffectsInfo= findViewById<TextView>(R.id.EffectsInfo)

        try {
            val inputStream: InputStream = openFileInput(filename)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val outputStream: FileOutputStream = openFileOutput("output.txt", Context.MODE_PRIVATE)
            val writer = BufferedWriter(OutputStreamWriter(outputStream))
            var line: String?
            var count = 0
            while (reader.readLine().also { line = it } != null && count < endRow) {
                // Read the file until the specified end row
                count++
                if (count <= 4) {
                    // Write the line to the output file if it is between the start and end rows

                    val existingString = ParkinsonInfo.text.toString()
                    ParkinsonInfo.text = "$existingString$line"
                }
                else if(count>4 && count<=5){

                    val existingString = ParkinsonReasonsInfo.text.toString()
                    ParkinsonReasonsInfo.text = "$existingString$line"
                }
                else if(count>5 && count<=7){

                    val existingString = SymptomsInfo.text.toString()
                    SymptomsInfo.text = "$existingString$line"
                }
                else if(count>8 && count<=13){

                    val existingString = EffectsInfo.text.toString()
                    EffectsInfo.text = "$existingString$line"
                }

            }
            reader.close()
            writer.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val ParkinsonTitle = findViewById<TextView>(R.id.ParkinsonTitle)

        ParkinsonTitle.setOnClickListener {
            // Handle the click event here

            if(parkinsoncount%2==0){
                ParkinsonInfo.visibility=View.VISIBLE
                ParkinsonTitle.setBackgroundColor(255)
                parkinsoncount+=1
            }
            else{
                ParkinsonTitle.setBackgroundResource(R.drawable.frame)
                ParkinsonInfo.visibility=View.GONE
                parkinsoncount+=1
            }


        }

        val ParkisonReasonsTitle= findViewById<TextView>(R.id.ParkinsonReasonsTitle)

        ParkisonReasonsTitle.setOnClickListener {

            if (parkinsonreasonscount % 2 == 0) {
                ParkisonReasonsTitle.setBackgroundColor(255)
                ParkinsonReasonsInfo.visibility = View.VISIBLE
                parkinsonreasonscount += 1
            } else {
                ParkisonReasonsTitle.setBackgroundResource(R.drawable.frame)
                ParkinsonReasonsInfo.visibility = View.GONE
                parkinsonreasonscount += 1


            }
        }
            val SymptomsTitle = findViewById<TextView>(R.id.SymptomsTitle)
            SymptomsTitle.setOnClickListener {

                if (symptomscount % 2 == 0) {
                    SymptomsTitle.setBackgroundColor(255)
                    SymptomsInfo.visibility = View.VISIBLE
                    symptomscount += 1
                } else {
                    SymptomsTitle.setBackgroundResource(R.drawable.frame)
                    SymptomsInfo.visibility = View.GONE
                    symptomscount += 1


                }

            }

        val EffectsTitle = findViewById<TextView>(R.id.EffectsTitle)

        EffectsTitle.setOnClickListener {

            if (effectscount % 2 == 0) {
                EffectsTitle.setBackgroundColor(255)
                EffectsInfo.visibility = View.VISIBLE
                effectscount += 1
            } else {
                EffectsTitle.setBackgroundResource(R.drawable.frame)
                EffectsInfo.visibility = View.GONE
                effectscount += 1


            }

        }





        }
        /*val filename = "ParkinsonHastaligi_nedir.txt"
        val file = File(getFilesDir(), filename)
        val br = BufferedReader(FileReader(file))
        val text = StringBuilder()

        var line: String? = null
        while ({ line = br.readLine(); line }() != null) {
            text.append(line)
            text.append('\n')
        }

        br.close()
        val fileContents = text.toString()
        Log.d("MARS",fileContents)

        val scrollView = findViewById<ScrollView>(R.id.scrollView)
        val textView = findViewById<TextView>(R.id.ParkinsonInfo)
        textView.text = fileContents*/



    }
