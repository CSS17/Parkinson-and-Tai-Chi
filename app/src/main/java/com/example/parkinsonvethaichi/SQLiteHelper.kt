package com.example.parkinsonvethaichi

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class SQLiteHelper(context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION){


    companion object{
        private const val DATABASE_VERSION=1
        private const val DATABASE_NAME="medicine.db"
        private const val MEDICINE_NAME="medicine_name"
        private const val TBL_MEDICINE="tbl_medicine"
        private const val ID= "id"
        private const val ID2="id2"
        private const val MEDICINE_HOUR="medicine_hour"
        private const val MEDICINE_MINUTE="medicine_minute"
        private const val TBL_STATS="tbl_stats"
        private const val DAY_OF_WEEK="day_of_week"
        private const val SPEND_TIME="spend_time"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblMedicine = ("CREATE TABLE " + TBL_MEDICINE + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + MEDICINE_NAME + " TEXT,"
                + MEDICINE_HOUR + " TEXT," + MEDICINE_MINUTE + " TEXT" + ")")
        db?.execSQL(createTblMedicine)

        val createStatisticTable = ("CREATE TABLE "+ TBL_STATS + "("
                + ID2+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DAY_OF_WEEK+" TEXT,"
                + SPEND_TIME+" INTEGER"
                + ")")
        db?.execSQL(createStatisticTable)

       /* for (day in listOf("Pazartesi", "Salı", "Çarşamba", "Perşembe", "Cuma", "Cumartesi", "Pazar")) {
            val contentValues = ContentValues()
            contentValues.put("day_of_week", day)
            contentValues.put("spend_time", 0)
            db?.insert(TBL_STATS, null, contentValues)
        }*/


    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_MEDICINE")
        onCreate(db)
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_STATS")
        onCreate(db)
    }

    fun instertMedicine(mdc:MedicineModel) : Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(MEDICINE_NAME,mdc.medicine_name)
        contentValues.put(MEDICINE_HOUR,mdc.medicine_hour)
        contentValues.put(MEDICINE_MINUTE,mdc.medicine_minute)
        val success = db.insert(TBL_MEDICINE,null,contentValues)
        db.close()
        return success
    }

    fun instertTime(stats:StatisticsModel){
        val db = this.writableDatabase
        db.execSQL("UPDATE $TBL_STATS SET $SPEND_TIME = ${stats.spend_time} WHERE $DAY_OF_WEEK = '${stats.day_of_week}'")
        db.close()

    }

    @SuppressLint("Range")
    fun getAllMedicine(): ArrayList<MedicineModel>{
        val mdcList:ArrayList<MedicineModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_MEDICINE"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery,null)

        }catch (e:Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id:Int
        var medicine_name: String
        var medicine_hour: String
        var medicine_minute: String

        if(cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndex("id"))
                medicine_name= cursor.getString(cursor.getColumnIndex("medicine_name"))
                medicine_hour = cursor.getString(cursor.getColumnIndex("medicine_hour"))
                medicine_minute = cursor.getString(cursor.getColumnIndex("medicine_minute"))

                val mdc = MedicineModel(
                    id=id,
                    medicine_name =medicine_name, medicine_hour =medicine_hour,
                    medicine_minute =medicine_minute )
                mdcList.add(mdc)
            } while(cursor.moveToNext())
        }
        return mdcList
    }

    @SuppressLint("Range")
    fun getAllStats(): ArrayList<StatisticsModel> {
        val dataList: ArrayList<StatisticsModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_STATS"
        val db = this.readableDatabase
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery,null)

        }catch (e:Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }


        var day_of_week: String
        var spend_time: Int

        if (cursor.moveToFirst()) {
            do {
                day_of_week = cursor.getString(cursor.getColumnIndex("day_of_week"))
                spend_time = cursor.getInt(cursor.getColumnIndex("spend_time"))

                val data = StatisticsModel(day_of_week=day_of_week, spend_time=spend_time)
                dataList.add(data)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return dataList
    }


    fun deleteRecord(medicineId:Int){
        Log.d("SATURN:  ",medicineId.toString())
        val db = this.writableDatabase
        val tableName = "TBL_MEDICINE"
        val whereClause = "id = ?"
        val whereArgs = arrayOf("$medicineId")
        db.delete(tableName, whereClause, whereArgs)
        db.close()
    }

    fun resetStats(){
        val db = this.writableDatabase
        db.execSQL("UPDATE tbl_stats SET spend_time = 0");
    }

}