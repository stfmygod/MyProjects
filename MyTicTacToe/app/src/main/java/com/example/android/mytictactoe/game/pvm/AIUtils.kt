package com.example.android.mytictactoe.game.pvm

import com.example.android.mytictactoe.game.evaluate
import kotlin.math.max
import kotlin.math.min

// WIll check if there are any more possible moves
private fun checkLeftMoves(new_table: Array<Array<String>>): Boolean {
    for (i in (0..2))
        for (j in (0..2))
            if (new_table[i][j] == "")
                return true

    return false
}

// Algorithm to determine the "score" of a move by simulating all the next possible moves
private fun miniMax(new_table: Array<Array<String>>, isAI: Boolean): Int {
    val score = evaluate(new_table)

    // If the AI wins
    if (score == 10)
        return score
    // If the player wins
    if (score == -10)
        return score


    // If nobody won and there are no more moves left, it's a tie
    if (!checkLeftMoves(new_table))
        return 0

    if (isAI) {
        var bestScore = -1000

        for (i in (0..2))
            for (j in (0..2))
                if (new_table[i][j] == "") {
                    new_table[i][j] = "O"

                    bestScore = max(bestScore, miniMax(new_table, !isAI))

                    new_table[i][j] = ""
                }

        return bestScore
    } else {
        var bestScore = 1000

        for (i in (0..2))
            for (j in (0..2))
                if (new_table[i][j] == "") {
                    new_table[i][j] = "X"

                    bestScore = min(bestScore, miniMax(new_table, !isAI))

                    new_table[i][j] = ""
                }

        return bestScore
    }
}

// Will find the best move for the AI
fun findBestMove(new_table: Array<Array<String>>): Array<Int> {
    var bestValue = -1000

    var bestMove = arrayOf(-1, -1)

    for (i in (0..2))
        for (j in (0..2))
            if (new_table[i][j] == "") {
                new_table[i][j] = "O"

                val moveValue = miniMax(new_table, false)

                new_table[i][j] = ""

                if (moveValue > bestValue) {
                    bestMove[0] = i
                    bestMove[1] = j
                    bestValue = moveValue
                }
            }

    return bestMove
}