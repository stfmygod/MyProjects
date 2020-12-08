package com.example.android.mytictactoe.game.pvm

import android.app.Application
import androidx.lifecycle.*
import com.example.android.mytictactoe.database.Game
import com.example.android.mytictactoe.database.GameDatabaseDao
import com.example.android.mytictactoe.game.evaluate
import kotlinx.coroutines.launch

class PvmViewModel(val database: GameDatabaseDao,
                    application: Application) : AndroidViewModel(application) {

    // For the timer that will be placed in the database
    private var startTimeMillis : Long = System.currentTimeMillis()

    // To inform the fragment that the game is finished
    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    // Keep track of who's turn it is
    private var player = 1

    // Keeps track of the number of rounds
    private var rounds = 0

    // Will store the game data
    private var table: Array<Array<String>> =
        arrayOf(arrayOf("", "", ""), arrayOf("", "", ""), arrayOf("", "", ""))


    // The game itself. Handles turns and player moves
    fun game(xPos: Int, yPos: Int){
        if (_eventGameFinish.value == false || _eventGameFinish.value == null) {
            if (player == 1 && table[xPos][yPos] == "") {
                // Player turn
                table[xPos][yPos] = "X"
                checkMatrix()
                player = 2
                rounds++
                if (rounds == 9) {
                    onGameOver()
                    return
                }

                // AI turn
                botMove()
                checkMatrix()
                rounds++
                player = 1
                if (rounds == 9)
                {
                    onGameOver()
                    return
                }

            }
        }
    }

    private fun botMove() {

//        For random AI. EASY MODE

//        var x = Random.nextInt(until = 3)
//        var y = Random.nextInt(until = 3)
//        while ((xVal == x && yVal == y || table[x][y] != "") && rounds != 9) {
//            x = Random.nextInt(until = 3)
//            y = Random.nextInt(until = 3)
//        }
//        if (rounds != 9 && _eventGameFinish.value != true)
//            table[x][y] = "O"


//        For minimax AI. IMPOSSIBLE MODE

        if (rounds != 9 && _eventGameFinish.value != true) {
            var newTable = table.copyOf()
            val newMove = findBestMove(newTable)
            table[newMove[0]][newMove[1]] = "O"
        }
    }


    // Checks if any player won and end the game if so
    private fun checkMatrix() {
        for (i in (0..2)) {
            if (table[i][0] == table[i][1] && table[i][2] == table[i][0] && table[i][0] != "" && _eventGameFinish.value != true) /*Check line*/{
                onGameOver()
            } else if (table[0][i] == table[1][i] && table[2][i] == table[0][i] && table[0][i] != "" && _eventGameFinish.value != true) /*Check column*/{
                onGameOver()
            }
        }
        if (table[0][0] == table[1][1] && table[0][0] == table[2][2] && table[0][0] != "" ||
            table[0][2] == table[1][1] && table[0][2] == table[2][0] && table[0][2] != "" &&
            _eventGameFinish.value != true
        )  /*Check diagonals*/
            onGameOver()
    }

    // To be able to read the matrix in Fragment
    fun getMatrix(): Array<Array<String>> {
        return table
    }

    // Will send the End Of Game signal and store data in the database
    private fun onGameOver() {
        _eventGameFinish.value = true

        // Add to database. Uses Coroutines so that it doesn't block the UI thread
        viewModelScope.launch {
            val game = Game(gameDuration = System.currentTimeMillis() - startTimeMillis, gameWon = evaluate(table).toString(), gameType = true)
            database.insert(game)
        }
    }


    // Resets the game
    fun onGameRestart() {
        _eventGameFinish.value = false
        player = 1
        rounds = 0
        startTimeMillis = System.currentTimeMillis()
        table = arrayOf(arrayOf("", "", ""), arrayOf("", "", ""), arrayOf("", "", ""))
    }




}