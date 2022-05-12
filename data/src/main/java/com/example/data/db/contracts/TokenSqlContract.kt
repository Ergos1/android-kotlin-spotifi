package com.example.data.db.contracts

class TokenSqlContract {
    companion object {
        const val fetch = "SELECT * FROM ${RoomContract.tableToken} WHERE id = 1"
    }
}