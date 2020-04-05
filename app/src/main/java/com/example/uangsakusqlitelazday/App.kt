package com.example.uangsakusqlitelazday

import android.app.Application
import android.database.sqlite.SQLiteDatabase
import com.example.uangsakusqlitelazday.database.DatabaseHelper

class App : Application() {

    companion object{
        var db : DatabaseHelper? = null
    }

    override fun onCreate() {
        super.onCreate()
        db = DatabaseHelper(this)
    }

}