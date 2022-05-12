package com.example.spotifi.di

import android.app.Application
import com.example.data.db.RoomAppDatabase

class App : Application() {
    companion object {
        lateinit var roomAppDatabase: RoomAppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        roomAppDatabase = RoomAppDatabase.buildDataSource(context = applicationContext)
    }
}