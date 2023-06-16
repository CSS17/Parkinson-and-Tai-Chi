package com.example.parkinsonvethaichi

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlinx.android.synthetic.main.activity_statistic.*
import java.util.*
import kotlin.collections.ArrayList

class Statistic : AppCompatActivity() {
    private lateinit var sqLiteHelper : SQLiteHelper
    val calendar = Calendar.getInstance()
    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    private lateinit var fulltime:ArrayList<StatisticsModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistic)
        sqLiteHelper = SQLiteHelper(this)
        fulltime=sqLiteHelper.getAllStats()
        dayselect()
        barChart.axisLeft.setDrawGridLines(false)
        barChart.axisRight.setDrawGridLines(false)
        barChart.setBackgroundColor(Color.WHITE)



        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(1f, fulltime.get(0).spend_time/60.toFloat()))
        entries.add(BarEntry(2f, fulltime.get(1).spend_time/60.toFloat()))
        entries.add(BarEntry(3f, fulltime.get(2).spend_time/60.toFloat()))
        entries.add(BarEntry(4f, fulltime.get(3).spend_time/60.toFloat()))
        entries.add(BarEntry(5f, fulltime.get(4).spend_time/60.toFloat()))
        entries.add(BarEntry(6f, fulltime.get(5).spend_time/60.toFloat()))
        entries.add(BarEntry(7f, fulltime.get(6).spend_time/60.toFloat()))

        val barDataSet = BarDataSet(entries, "Veriler")
        barDataSet.colors = listOf(
            Color.rgb(	126	,106,	176),
            Color.rgb(190	,124,	180),
            Color.rgb(246	,121,	110),
            Color.rgb(246	,167,	115),
            Color.rgb(	254	,240,	3),
            Color.rgb(174	,211,	139),
            Color.rgb(93	,136,	198)
        )
        barDataSet.valueTextColor = Color.WHITE

        val barData = BarData(barDataSet)

        barChart.data = barData
        barChart.setFitBars(true)
        barChart.animateY(2000)
        barChart.description.isEnabled = false

        // Her bir barın üzerine değeri yazmak için
        barData.setValueTextSize(16f)
        barData.setValueTextColor(Color.BLACK) // Yazı rengini beyaz olarak ayarla
        barData.setValueFormatter(object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return value.toInt().toString()
            }
        })


        barChart.xAxis.setDrawGridLines(false)
        barChart.xAxis.setDrawAxisLine(false)
        barChart.xAxis.setDrawLabels(false)
        // Ölçekleri kapat
        barChart.axisLeft.isEnabled = false
        barChart.axisRight.isEnabled = false
        barChart.legend.isEnabled = false
    }
    fun dayselect() {
        val layoutParams = arrow.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
        arrow.layoutParams = layoutParams
        arrow.requestLayout()

        when (dayOfWeek) {

            Calendar.MONDAY -> {
                println("Bugün Pazartesi")
                gunluk_egzersiz.text = (fulltime.get(0).spend_time / 60).toString()+" dk"
                Log.d("ALİHANKE", fulltime.get(0).spend_time.toString())
                val density = resources.displayMetrics.density
                val marginInDp = 55
                val marginPixels = (marginInDp * density + 0.5f).toInt()
                layoutParams.leftMargin = marginPixels
            }
            Calendar.TUESDAY -> {
                println("Bugün Salı")
                gunluk_egzersiz.text = (fulltime.get(1).spend_time / 60).toString()+" dk"
                val density = resources.displayMetrics.density
                val marginInDp = 103
                val marginPixels = (marginInDp * density + 0.5f).toInt()
                layoutParams.leftMargin = marginPixels
            }
            Calendar.WEDNESDAY -> {
                println("Bugün Çarşamba")
                gunluk_egzersiz.text = (fulltime.get(2).spend_time / 60).toString()+" dk"
                val density = resources.displayMetrics.density
                val marginInDp = 151
                val marginPixels = (marginInDp * density + 0.5f).toInt()
                layoutParams.leftMargin = marginPixels
            }
            Calendar.THURSDAY -> {
                println("Bugün Perşembe")
                gunluk_egzersiz.text = (fulltime.get(3).spend_time / 60).toString()+" dk"
                val density = resources.displayMetrics.density
                val marginInDp = 199
                val marginPixels = (marginInDp * density + 0.5f).toInt()
                layoutParams.leftMargin = marginPixels
            }
            Calendar.FRIDAY -> {
                println("Bugün Cuma")
                gunluk_egzersiz.text = (fulltime.get(4).spend_time / 60).toString()+" dk"
                val density = resources.displayMetrics.density
                val marginInDp = 247
                val marginPixels = (marginInDp * density + 0.5f).toInt()
                layoutParams.leftMargin = marginPixels
            }
            Calendar.SATURDAY -> {
                println("Bugün Cumartesi")
                gunluk_egzersiz.text = (fulltime.get(5).spend_time / 60).toString()+" dk"
                val density = resources.displayMetrics.density
                val marginInDp = 295
                val marginPixels = (marginInDp * density + 0.5f).toInt()
                layoutParams.leftMargin = marginPixels
            }
            Calendar.SUNDAY -> {
                println("Bugün Pazar")
                gunluk_egzersiz.text = (fulltime.get(6).spend_time / 60).toString()+" dk"
                val density = resources.displayMetrics.density
                val marginInDp = 343
                val marginPixels = (marginInDp * density + 0.5f).toInt()
                layoutParams.leftMargin = marginPixels
            }

        }

        val formattedNumber = (((fulltime.get(0).spend_time.toFloat()/60) +(fulltime.get(1).spend_time.toFloat()/60)+(fulltime.get(2).spend_time.toFloat()/60)+(fulltime.get(3).spend_time.toFloat()/60)+(fulltime.get(4).spend_time.toFloat()/60)+(fulltime.get(5).spend_time.toFloat()/60)+(fulltime.get(6).spend_time.toFloat()/60))/7)
        haftalık_egzersiz.text=String.format("%.2f", formattedNumber)+" dk"
    }
}