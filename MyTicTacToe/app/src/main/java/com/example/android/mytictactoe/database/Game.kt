package com.example.android.mytictactoe.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games_table")
data class Game(

    @PrimaryKey(autoGenerate = true)
    var gameId: Long = 0L,

    @ColumnInfo(name = "game_duration")
    var gameDuration: Long = 0L,

    @ColumnInfo(name = "game_won")
    var gameWon: String = "",

    @ColumnInfo(name = "game_type")
    var gameType: Boolean = false // false - for PVP; true - for PVM
)