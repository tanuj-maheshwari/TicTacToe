# Tic-Tac-Toe

## Introduction

A TicTacToe game with both Player v/s Player and Player v/s Computer functionality.

## How to run?

Java must be installed on the system, which can be installed from [the official site](https://www.java.com/en/download/help/download_options.html "Download Java").

After installation, open Terminal / Command Prompt in the directory, and run

```
javac Computer.java
javac Game.java
javac Player.java
javac TicTacToe.java
java TicTacToe
```

This will open the main game (which runs in the terminal itself).

## Greeting Screen and Rulebook

The game starts with a greeting screen which welcomes the user(s) and gives a brief introduction about the game.

There is also an option to **read all the rules** in case the player(s) is/are unfamiliar with the game.

## Player v/s Player

In Player v/s Player mode, the game asks for both player's names, and assigns X to Player 1 (who goes first) and O to Player 2.

The game then begins and consecutively asks each player their choice of cell (1 to 9). The updated grid will be shown after every move for available valid moves.

The game ends when either one of the player wins or the board fills up and results in a tie.

At the end of each game, A scoreboard is shown displaying the total wins/losses of both the players, **with an option to have a rematch**.

## Player v/s Computer

Instead of having a scoreboard for wins/loses, this mode features a **point system**.

When playing against the computer, the user is presented with 3 choices for level of difficulty, each of which have a different point system which will be displayed on the screen.

The 3 difficulties are as follows :-

1. **Easy** :- The computer moves randomly in empty spaces.
2. **Medium** :- The computer tries to prevent your victory and simultaneously tries to win for itself. If no such cells are available, it will move randomly.
3. **Impossible** :- The game in this difficulty can either result in a tie or a loss of the user. \*This version is implemented using a **depth-optimised version** of the standard minimax algorithm with **alpha-beta pruning\***.

The computer moves after each of the player's moves (who goes first). The updated grid will be shown after every move for available valid moves.

At the end of each game, the score is displayed and **an option to have a rematch is shown**. You can _change the difficulty level_ after every game (the previous level is shown while making the choice).

## Other small functionalities

1. The score/scoreboard keeps on updating as long as the player doesn't answer no to the rematch question.

2. If any of the inputs given by the user is invalid, the user is asked again and again till he/she doesn't answer correctly.

## Closing the game

If `y` or `Y` is not chosen when asked for rematch, the game ends with a _goodbye message_.

To force stop the game during the match (or any particular time), press `Ctrl` + `C`.
