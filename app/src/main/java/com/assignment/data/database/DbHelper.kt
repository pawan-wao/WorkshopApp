package com.assignment.data.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.assignment.data.model.Workshop

class DbHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION)  {
    companion object{
        private val DB_NAME = "WORKSHOP_DETAILS_v1"
        private val DB_VERSION = 2
        private val TABLE_NAME = "WORKSHOP_TABLE_v1"
        private val ID_COLUMN = "id"
        private val NAME_COLUMN = "WORKSHOP_NAME"
        private val IMAGE_URL = "WORKSHOP_IMAGE"
        private val DATE_COLUMN = "WORKSHOP_DATE"
        private val APPLIED = "WORKSHOP_APPLIED"
    }

    override fun onCreate(database: SQLiteDatabase?) {
        val query = ("CREATE TABLE " + TABLE_NAME + " (" +
                ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME_COLUMN + " TEXT," +
                IMAGE_URL + " TEXT, " +
                DATE_COLUMN + " TEXT," +
                APPLIED + " INTEGER" + ")")

        database?.execSQL(query)
    }

    override fun onUpgrade(database: SQLiteDatabase?, p1: Int, p2: Int) {
        database?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(database)
    }

    fun addWorkshopDetails(workshop: List<Workshop>) {
        val db = this.writableDatabase
        workshop.forEach {
            it.apply {
                val values = ContentValues()
                values.put(NAME_COLUMN, name)
                values.put(IMAGE_URL, imageLink)
                values.put(DATE_COLUMN, date)
                values.put(APPLIED, applied)
                db.insert(TABLE_NAME, null, values)
            }
        }
        db.close()
    }
    @SuppressLint("Range")
    fun getAllWorkShop(): ArrayList<Workshop>  {
        val db = this.readableDatabase
        val data =  db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val count = data.count
        val list = ArrayList<Workshop>()
        if (data.moveToFirst()) {
            do {
                list.add(
                    Workshop(
                        data.getInt(data.getColumnIndex(ID_COLUMN)),
                        data.getString(data.getColumnIndex(NAME_COLUMN)),
                        data.getString(data.getColumnIndex(IMAGE_URL)),
                        data.getString(data.getColumnIndex(DATE_COLUMN)),
                        data.getInt(data.getColumnIndex(APPLIED))
                    )
                )
            } while (data.moveToNext())
        }
        Log.i("VIBHUTI", "getAllWorkShop: $list")
        data.close();
        return list;
    }

    fun applied(id: Int) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(APPLIED, 1)

        db.update(TABLE_NAME, values, "id=?", arrayOf(id.toString()))
        db.close()
    }

    @SuppressLint("Range")
    fun getAppliedWorkshops(): ArrayList<Workshop>  {
        val db = this.readableDatabase
        val data =  db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $APPLIED = 1", null)

        val list = ArrayList<Workshop>()
        if (data.moveToFirst()) {
            do {
                list.add(
                    Workshop(
                        data.getInt(data.getColumnIndex(ID_COLUMN)),
                        data.getString(data.getColumnIndex(NAME_COLUMN)),
                        data.getString(data.getColumnIndex(IMAGE_URL)),
                        data.getString(data.getColumnIndex(DATE_COLUMN)),
                        data.getInt(data.getColumnIndex(APPLIED))
                    )
                )
            } while (data.moveToNext())
        }

        data.close();
        Log.i("VIBHUTI", "getAppliedWorkshops: $list")
        return list;
    }

    fun deleteAppliedWorkshops() {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(APPLIED, 0)
        db.update(TABLE_NAME, values, null, null)
        db.close()
    }

}