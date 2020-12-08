package com.example.android.mytictactoe.game


// Will return 10 if O won, -10 if X won and 0 for a tie/still going
fun evaluate(new_table: Array<Array<String>>): Int {
    // Check rows
    for (i in (0..2)) {
        if (new_table[i][0] == new_table[i][1] && new_table[i][1] == new_table[i][2]) {
            if (new_table[i][0] == "O")
                return 10
            else if (new_table[i][0] == "X")
                return -10
        }
    }

    // Check columns
    for (i in (0..2)) {
        if (new_table[0][i] == new_table[1][i] && new_table[1][i] == new_table[2][i]) {
            if (new_table[0][i] == "O")
                return 10
            else if (new_table[0][i] == "X")
                return -10
        }
    }

    // Check Diagonals
    if (new_table[0][0] == new_table[1][1] && new_table[1][1] == new_table[2][2]) {
        if (new_table[0][0] == "O")
            return 10
        else if (new_table[0][0] == "X")
            return -10
    }

    if (new_table[0][2] == new_table[1][1] && new_table[1][1] == new_table[2][0]) {
        if (new_table[0][2] == "O")
            return 10
        else if (new_table[0][2] == "X")
            return -10
    }

    // If nobody won yet
    return 0
}