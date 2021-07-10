import java.util.Scanner;

public class TicTacToe {
    private static final Scanner sc = new Scanner(System.in);

    // Function that displays the intro and welcome screen
    private static void displayIntro() {
        System.out.println("\n\n\t\t\t\t\tWELCOME TO TIC-TAC-TOE!!\n");
        System.out.println("This is a two player game. You can choose to play with your friend or with the computer.");
        System.out.println("The game consists of a 3X3 grid, which looks like this :-\n");
        System.out.println("\t _______ _______ _______");
        System.out.println("\t|       |       |       |");
        System.out.println("\t|   1   |   2   |   3   |");
        System.out.println("\t|_______|_______|_______|");
        System.out.println("\t|       |       |       |");
        System.out.println("\t|   4   |   5   |   6   |");
        System.out.println("\t|_______|_______|_______|");
        System.out.println("\t|       |       |       |");
        System.out.println("\t|   7   |   8   |   9   |");
        System.out.println("\t|_______|_______|_______|\n\n");
        System.out.println("The goal is to match either a row, column or diagonal.");
        System.out.println("The one who matches first wins the game!!\n\n");
    }

    // FUnction to print all the game rules
    private static void displayRules() {
        System.out.println("\n\n\t1. The game is played on a grid that's 3 squares by 3 squares, as shown above.");
        System.out.println("\t2. Player 1 is X, Player 2 (or the computer) is O. Player 1 always goes first.");
        System.out.println(
                "\t3. Players take turns putting their marks in empty squares. Players can't overwrite already filled up cells.");
        System.out.println(
                "\t4. The first player to get 3 of their marks in a row (up, down, across, or diagonally) is the winner.");
        System.out.println(
                "\t5. When all 9 squares are full, the game is over. If no player has 3 marks in a row, the game ends in a tie.\n\n");
    }

    // Main function
    public static void main(String[] args) {
        // display the welcome screen
        displayIntro();

        Game tictactoe = new Game(); // Create a new Game() instance

        // Ask to display rulebook
        System.out.print("Would you like to view all the rules? (y for YES, any other character for NO) :- ");
        char view_rulebook = sc.next().charAt(0);
        if (view_rulebook == 'y' || view_rulebook == 'Y') {
            displayRules();
            System.out.println("Now, you are ready to play!!");
        }

        // Selection of game mode
        System.out.print("\n\nChoose to play with either a friend or a computer (1 for friend, 2 for computer) :- ");
        int mode = sc.nextInt();
        System.out.println();
        if (mode != 1 && mode != 2) {
            while (mode != 1 && mode != 2) {
                System.out.print("Entered choice is wrong. Choose again (1 for friend, 2 for computer) :- ");
                mode = sc.nextInt();
            }
        }

        // Play with friend
        if (mode == 1) {
            Player[] player = new Player[2]; // create two players
            player[0] = new Player(1, false);
            player[1] = new Player(2, false);

            char rematch = 'n'; // for rematch
            int[] victory_count = { 0, 0 }; // for scoreboard
            int[] loss_count = { 0, 0 }; // for scoreboard

            do {
                tictactoe.resetGame(); // reset the board

                int result = 0;
                int current_turn_id = 0; // ID (index) of the current turn player
                int next_turn_id = 1; // ID (index) of the next turn player
                int move;
                boolean move_validity;

                do {
                    int player_no = current_turn_id + 1;

                    // player makes the move (again and again if the move entered isn't valid)
                    move = player[current_turn_id].makeMove(false);
                    move_validity = tictactoe.updateGrid(move, player_no);

                    if (!move_validity) {
                        while (!move_validity) {
                            move = player[current_turn_id].makeMove(true);
                            move_validity = tictactoe.updateGrid(move, player_no);
                        }
                    }

                    // Victory determination
                    result = tictactoe.determineVictory();

                    // Current Player wins
                    if (result == 1) {
                        System.out.print("\n\nCongratulations " + player[current_turn_id].getName() + ", you won!! ");
                        System.out.println(player[next_turn_id].getName() + ", better luck next time!!\n");

                        victory_count[current_turn_id]++; // update scoreboard
                        loss_count[next_turn_id]++; // update scoreboard
                    }
                    // Tie
                    else if (result == -1) {
                        System.out.println("\n\nIt's a tie!!\n");
                    }
                    // Game continues
                    else {
                        // Make next player as currenyt player and update next player ID
                        current_turn_id = next_turn_id;
                        next_turn_id = (next_turn_id + 1) % 2;
                    }

                    // If game has ended
                    if (result == 1 || result == -1) {
                        // Print scoreboard
                        System.out.println("Scoreboard :-\n");
                        System.out.println("\t" + player[0].getName() + " at " + victory_count[0] + " win(s) & "
                                + loss_count[0] + " loss(es).");
                        System.out.println("\t" + player[1].getName() + " at " + victory_count[1] + " win(s) & "
                                + loss_count[1] + " loss(es).");

                        // Ask for rematch
                        System.out.print("\n\nWant a rematch?? (y for YES, any other character for NO) :- ");
                        rematch = sc.next().charAt(0);
                    }

                } while (result != 1 && result != -1); // repeat until game doesn't finish

            } while (rematch == 'y' || rematch == 'Y'); // repeat until player(s) don't want a rematch
        }

        // Play against computer
        else {
            // create a player and computer instance
            Player player = new Player(1, true);
            Computer computer = new Computer();

            // Display point system and ask for difficulty level
            System.out.println("\n\nHello, " + player.getName() + ".");
            System.out.println(
                    "The computer mode offers 3 difficulties. Each difficulty has a different point system, as mentioned below :-\n");
            System.out.println("   DIFFICULTY         WIN      TIE      LOSS");
            System.out.println("1. Easy               +5       -2       -4    ");
            System.out.println("2. Medium             +12      +5       -2    ");
            System.out.println("3. Impossible         (X)      +15      -1    \n");
            System.out.print("Choose your preferred difficulty level (1-3):- ");
            int difficulty = sc.nextInt();
            if (difficulty != 1 && difficulty != 2 && difficulty != 3) {
                while (difficulty != 1 && difficulty != 2 && difficulty != 3) {
                    System.out.print("Entered choice is wrong. Choose again (1, 2 or 3) :- ");
                    difficulty = sc.nextInt();
                }
            }

            char rematch = 'y';
            int points = 0;

            do {
                tictactoe.resetGame(); // reset game

                int result = 0;
                int move;
                boolean move_validity;

                do {
                    // player makes the move (again and again if the move entered isn't valid)
                    move = player.makeMove(false);
                    move_validity = tictactoe.updateGrid(move, 1);

                    if (!move_validity) {
                        while (!move_validity) {
                            move = player.makeMove(true);
                            move_validity = tictactoe.updateGrid(move, 1);
                        }
                    }

                    // Determine if player has won
                    result = tictactoe.determineVictory();

                    // Player won
                    if (result == 1) {
                        System.out.println("\n\nCongratulations " + player.getName()
                                + ", you won!! Try again with harder difficulty!\n");

                        // Update score
                        if (difficulty == 1) {
                            points += 5;
                        } else if (difficulty == 2) {
                            points += 12;
                        }
                    }
                    // Tie
                    else if (result == -1) {
                        System.out.println("\n\nIt's a tie!!\n");

                        // Update Score
                        if (difficulty == 1) {
                            points -= 2;
                        } else if (difficulty == 2) {
                            points += 5;
                        } else {
                            points += 15;
                        }
                    }

                    // Game isn't over. Computer move.
                    else {
                        // Computer moves based on difficulty
                        move = computer.makeMove(difficulty, tictactoe);
                        System.out.println("\n\nComputer (O) moves at position " + move);
                        tictactoe.updateGrid(move, 2);

                        // Victory determination
                        result = tictactoe.determineVictory();

                        // Computer Won
                        if (result == 1) {
                            System.out.println("\n\nComputer Won!! " + player.getName()
                                    + ", better luck next time!! Try again with a lower difficulty\n");

                            // Update score
                            if (difficulty == 1) {
                                points -= 4;
                            } else if (difficulty == 2) {
                                points -= 2;
                            } else {
                                points -= 1;
                            }
                        }
                        // Tie
                        else if (result == -1) {
                            System.out.println("\n\nIt's a tie!!\n");
                            if (difficulty == 1) {
                                points -= 2;
                            } else if (difficulty == 2) {
                                points += 5;
                            } else {
                                points += 15;
                            }
                        }
                    }

                    // If game has ended
                    if (result == 1 || result == -1) {
                        // Display score
                        System.out.println("\tScore : " + points);

                        // Ask for rematch and difficulty
                        System.out.print("\n\nWant a rematch?? (y for YES, any other character for NO) :- ");
                        rematch = sc.next().charAt(0);
                        if (rematch == 'y' || rematch == 'Y') {
                            System.out.println();
                            System.out.println("   DIFFICULTY         WIN      TIE      LOSS");
                            System.out.println("1. Easy               +5       -2       -4    ");
                            System.out.println("2. Medium             +12      +5       -2    ");
                            System.out.println("3. Impossible         (X)      +15      -1    \n");
                            System.out.print("Choose Difficulty (last round's difficulty was " + difficulty + ") :- ");
                            difficulty = sc.nextInt();
                            if (difficulty != 1 && difficulty != 2 && difficulty != 3) {
                                while (difficulty != 1 && difficulty != 2 && difficulty != 3) {
                                    System.out.print("Entered choice is wrong. Choose again (1, 2 or 3) :- ");
                                    difficulty = sc.nextInt();
                                }
                            }
                        }
                    }

                } while (result != 1 && result != -1);// repeat until game doesn't finish

            } while (rematch == 'y' || rematch == 'Y'); // repeat until player(s) don't want a rematch
        }

        // Display GoodBye message
        System.out.println("\n\nThank You for playing TicTacToe!! Hope you enjoyed the game!!\n\n");
    }
}