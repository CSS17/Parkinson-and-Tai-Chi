package com.example.parkinsonvethaichi

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class RefreshDataBase(val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams)
{
    private lateinit var sqLiteHelper : SQLiteHelper
    override fun doWork(): Result {
        refreshDatabase()
        return Result.success()
    }

    private fun refreshDatabase(){
        sqLiteHelper = SQLiteHelper(context)
        sqLiteHelper.resetStats()
    }
}