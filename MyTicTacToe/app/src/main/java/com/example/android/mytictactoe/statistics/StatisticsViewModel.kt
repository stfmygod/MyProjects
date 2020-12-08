package com.example.android.mytictactoe.statistics

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.mytictactoe.database.Game
import com.example.android.mytictactoe.database.GameDatabaseDao
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StatisticsViewModel(
    val database: GameDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private lateinit var pvpGames: List<Game>
    private lateinit var pvmGames: List<Game>

    private var _databaseIsDone = MutableLiveData<Boolean>()
    val databaseIsDone: LiveData<Boolean>
        get() = _databaseIsDone

    private val isPvp = false
    private val isPvm = true

    init {
        // Will get all the games from the database
        viewModelScope.launch {
            pvpGames = database.getByType(isPvp)
            pvmGames = database.getByType(isPvm)
            _databaseIsDone.value = true
        }

    }

    fun getAllPVPGamesWonByX(): Int {
        return pvpGames.count { game -> game.gameWon == "-10" }
    }

    fun getAllPVPGamesTie(): Int {
        return pvpGames.count { game -> game.gameWon == "0" }
    }

    fun getAllPVPGames(): Int {

        return pvpGames.size
    }

    fun getAllPVMGamesWonByX(): Int {
        return pvmGames.count { game -> game.gameWon == "-10" }
    }

    fun getAllPVMGamesTie(): Int {
        return pvmGames.count { game -> (game.gameWon == "0") }
    }

    fun getAllPVMGames(): Int {
        return pvmGames.size
    }
}