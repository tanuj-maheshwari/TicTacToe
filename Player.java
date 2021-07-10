import java.util.Scanner;

public class Player {
    private String name;
    private char game_symbol; // X or O

    private static final Scanner sc = new Scanner(System.in);

    // Constructor
    public Player(int player_no, boolean with_computer) {
        // Assign Symbol
        if (player_no == 1) {
            this.game_symbol = 'X';
        } else {
            this.game_symbol = 'O';
        }

        // Print name entering message according to the game mode
        if (with_computer) {
            System.out.print("Enter your name :- ");
        } else {
            System.out.print("Player no. " + player_no + " (" + game_symbol + "), enter your name :- ");
        }

        String new_name = sc.nextLine();
        this.name = new_name;
    }

    public String getName() {
        return name;
    }

    public char getSymbol(int player_no) {
        return game_symbol;
    }

    // Function which scans the next move from the players
    public int makeMove(boolean invalid_move) {
        int move;

        if (!invalid_move) {
            System.out.print("\n\n" + name + " (" + game_symbol + "), it's your turn, make your move (1-9) :- ");
        }
        // If the move is invalid, ask for the move again (with a different message)
        else {
            System.out.print(name + " (" + game_symbol + "), that was an invalid move, make your move again (1-9) :- ");
        }

        move = sc.nextInt();
        return move;
    }
}
