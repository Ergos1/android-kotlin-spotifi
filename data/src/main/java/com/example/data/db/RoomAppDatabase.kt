package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.db.contracts.RoomContract
import com.example.data.db.dao.TokenDao
import com.example.data.db.models.TokenEntity

@Database(entities = [TokenEntity::class], version=2)
abstract class RoomAppDatabase: RoomDatabase() {
    abstract fun tokenDao(): TokenDao

    companion object {
        fun buildDataSource(context: Context): RoomAppDatabase = Room.databaseBuilder(
            context, RoomAppDatabase::class.java, RoomContract.databaseApp).fallbackToDestructiveMigration()
            .build()
    }
}