package com.example.parkinsonvethaichi

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class SQLiteHelper(context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION){

    val mdcList:ArrayList<MedicineModel> = ArrayList()
    companion object{
        private const val DATABASE_VERSION=1
        private const val DATABASE_NAME="medicine.db"
        private const val MEDICINE_NAME="medicine_name"
        private const val TBL_MEDICINE="tbl_medicine"
        private const val ID= "id"
        private const val MEDICINE_HOUR="medicine_hour"
        private const val MEDICINE_MINUTE="medicine_minute"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblMedicine = ("CREATE TABLE " + TBL_MEDICINE + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + MEDICINE_NAME + " TEXT,"
                + MEDICINE_HOUR + " TEXT," + MEDICINE_MINUTE + " TEXT" + ")")
        db?.execSQL(createTblMedicine)




    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_MEDICINE")
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

    @SuppressLint("Range")
    fun getAllMedicine(): ArrayList<MedicineModel>{

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

                val mdc = MedicineModel(id=id,medicine_name=medicine_name, medicine_hour=medicine_hour,medicine_minute=medicine_minute )
                mdcList.add(mdc)
            } while(cursor.moveToNext())
        }
        return mdcList
    }


    fun deleteRecord(medicineId:Int){
        val db = this.writableDatabase
        val tableName = "TBL_MEDICINE"
        val whereClause = "id = ?"
        val whereArgs = arrayOf("$medicineId")
        db.delete(tableName, whereClause, whereArgs)
        db.close()
    }

}