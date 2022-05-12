package com.example.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.db.contracts.RoomContract
import com.example.data.db.contracts.TokenSqlContract
import com.example.data.db.models.TokenEntity

@Dao
interface TokenDao {

    @Query("SELECT * FROM ${RoomContract.tableToken}")
    fun fetchToken(): TokenEntity

    @Insert(entity = TokenEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertToken(tokenEntity: TokenEntity)

}