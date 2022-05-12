package com.example.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.db.contracts.RoomContract

@Entity(tableName = RoomContract.tableToken)
data class TokenEntity(@PrimaryKey val id: Int,  val accessToken: String) {
}