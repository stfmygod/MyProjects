This is my first project in Android done wihout any help from tutorials or videos.
I wanted to check my knowledge in Kotlin and Android after I finished the Android Kotlin Fundamentals course.

In this project I used a Room database, coroutines, live data, navigation and many more.

Was it necessary to use these methods? No.
Do the methods used have any benefit in this application? Some of them do, others don't.
I just wanted to prove to myself that I can use them in a practical example.

This Android application implements the Tic-Tac-Toe game. It has 4 screens:

1. Start page. - A navigation for the application. The user can advance from here to the other 3 screens.

2. PVP Game. - This is the 2 player implementation of the game. The user can play the game with a friend. 

3. PVM Game. - This is the Player vs AI implementation of the game. The user can play the game versus an pseudo AI. In this game mode, the player can never win.
The AI is written to always win or tie. The algorithm used for the AI is the miniMax algorithm to determine the "score" for a given move by simulating all the next possible moves.
The algorithm will use the move with the highest score. Based on the simplicity of the game, this algorithm will help the AI to always win or tie.

In the code there is an "easy mode" for the AI, that can easily be beaten. The algorithm will use a random possible move. This was implemented only to check if the AI works.

4. Statistics. - This page will show some statistics of the past games, stored in a database. 
