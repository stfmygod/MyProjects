package com.example.android.mytictactoe.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface GameDatabaseDao {

    @Insert
    suspend fun insert(game: Game)

    @Update
    suspend fun update(game: Game)

    @Query("SELECT * FROM games_table WHERE gameId = :key")
    suspend fun get(key: Long) : Game?

    @Query("DELETE FROM games_table")
    suspend fun clear()

    @Query("SELECT * FROM games_table WHERE game_type = :type")
    suspend fun getByType(type: Boolean) : List<Game>

    @Query("SELECT * FROM games_table")
    suspend fun getAllGames(): List<Game>
}