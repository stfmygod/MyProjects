package com.example.android.mytictactoe.game.pvp

import android.app.Application
import androidx.lifecycle.*
import com.example.android.mytictactoe.database.Game
import com.example.android.mytictactoe.database.GameDatabaseDao
import com.example.android.mytictactoe.game.evaluate
import kotlinx.coroutines.launch

class PvpViewModel(
    val database: GameDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    // Stores the length of the game in milliseconds
    private var startTimeMilli: Long = System.currentTimeMillis()

    // End of game flag
    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    // Keeps track of the player turn
    private var player = 1
    // Keeps track of the number of rounds
    private var rounds = 0

    // Stores the game itself
    private var table: Array<Array<String>> =
        arrayOf(arrayOf("", "", ""), arrayOf("", "", ""), arrayOf("", "", ""))


    // Will handle the player moves
    fun game(xPos: Int, yPos: Int): String {
        if (_eventGameFinish.value == false || _eventGameFinish.value == null) {
            // X's turn
            if (player == 1 && table[xPos][yPos] == "") {
                table[xPos][yPos] = "X"
                checkMatrix()
                player = 2
                rounds++
                if (rounds == 9) onGameOver()
                return "X"

                // O's turn
            } else if (player == 2 && table[xPos][yPos] == "") {
                table[xPos][yPos] = "O"
                checkMatrix()
                player = 1
                rounds++
                if (rounds == 9) onGameOver()
                return "O"
            }
        }

        return "err"
    }


    // Will check if a player won and end the game
    private fun checkMatrix() {
        for (i in (0..2)) {
            if (table[i][0] == table[i][1] && table[i][2] == table[i][0] && table[i][0] != "") {
                onGameOver()
            }

            if (table[0][i] == table[1][i] && table[2][i] == table[0][i] && table[0][i] != "") {
                onGameOver()
            }
        }
        if (table[0][0] == table[1][1] && table[0][0] == table[2][2] && table[0][0] != "" || table[0][2] == table[1][1] && table[0][2] == table[2][0] && table[0][2] != "")
            onGameOver()
    }


    // Will send the End of Game flag and store the game data in the database
    private fun onGameOver() {
        _eventGameFinish.value = true

        // Add to database
        viewModelScope.launch {
            val game = Game(gameDuration = System.currentTimeMillis() - startTimeMilli, gameWon = evaluate(table).toString(), gameType = false)
            database.insert(game)
        }
    }

    // Resets the game
    fun onGameOverComplete() {
        _eventGameFinish.value = false

        player = 1
        rounds = 0
        startTimeMilli = System.currentTimeMillis()
        table = arrayOf(arrayOf("", "", ""), arrayOf("", "", ""), arrayOf("", "", ""))
    }
}