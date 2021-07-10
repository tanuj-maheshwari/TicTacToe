public class Game {
    // grid[i][0] represents if i + 1th cell is filled (1) or not (0)
    // grid[i][1] represents who filled the cell (if grid[i][0] == 1) [+1 for X, -1
    // for O]
    private int[][] grid;

    // Constructor that sets the grid to 0
    public Game() {
        grid = new int[9][2];

        for (int i = 0; i < grid.length; i++) {
            grid[i][0] = grid[i][1] = 0;
        }
    }

    // Function to print the current status of the grid
    private void printGrid() {
        System.out.println();

        for (int i = 0; i < grid.length; i++) {
            if (i % 3 == 0) {
                System.out.println();
                if (i == 0) {
                    System.out.println("\t _______ _______ _______");
                } else {
                    System.out.println("\t|_______|_______|_______|");
                }
                System.out.println("\t|       |       |       |");
                System.out.print("\t|   ");
            }
            if (grid[i][0] == 0) {
                System.out.print("    |   ");
            } else {
                if (grid[i][1] == 1) {
                    System.out.print("X   |   ");
                } else {
                    System.out.print("O   |   ");
                }
            }
        }
        System.out.println();
        System.out.println("\t|_______|_______|_______|");
        System.out.println();
    }

    // Function that returns the grid (used by computer to make a move)
    public int[][] gridStatus() {
        return grid;
    }

    // Function which returns if the grid is full or not
    public boolean isGridFull() {
        boolean is_grid_full = true;
        for (int i = 0; i < grid.length; i++) {
            if (grid[i][0] == 0) {
                is_grid_full = false;
                break;
            }
        }
        return is_grid_full;
    }

    private boolean checkValidity(int move) {
        if (move >= 10 || move <= 0) {
            return false;
        }

        // If the position is already filled
        else if (grid[move - 1][0] != 0) {
            return false;
        }

        else {
            return true;
        }

    }

    // Function that checks the validity of the move, and then updates and displays
    // the grid (returns if move is valid)
    public boolean updateGrid(int move, int player_no) {
        // If the move isn't valid
        if (!checkValidity(move)) {
            return false;
        }

        else {
            grid[move - 1][0] = 1;

            // Grid is updated according to which player has moved
            if (player_no == 1) {
                grid[move - 1][1] = 1;
            } else {
                grid[move - 1][1] = -1;
            }

            printGrid(); // Print the grid after updating it

            return true;
        }
    }

    // Function that determines is game ends after each move
    public int determineVictory() {
        boolean victory = false;

        // The indicies of all rows/columns/diagonals (where victory is possible)
        int[][] victory_lines = { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 },
                { 0, 4, 8 }, { 2, 4, 6 } };

        // Checking for each victory line
        for (int[] victory_line : victory_lines) {
            int cell_value_sum = 0; // sum of grid[i][1], where i is index in a victory line
            boolean full_line = true; // if victory line is full or not

            for (int cell_id : victory_line) {
                if (grid[cell_id][0] != 0) {
                    cell_value_sum += grid[cell_id][1];
                } else {
                    full_line = false;
                    break;
                }
            }

            // If line is full, and all symbols in the line are same
            if (full_line && ((cell_value_sum == 3) || (cell_value_sum == -3))) {
                victory = true;
            }
        }

        if (victory) {
            return 1; // victory
        }

        else {
            boolean draw = isGridFull(); // If grid is full but no one won, then it's a draw

            if (!draw) {
                return 0; // continue the game
            } else {
                return -1; // draw
            }
        }
    }

    // Function that resets the grid
    public void resetGame() {
        for (int i = 0; i < grid.length; i++) {
            grid[i][0] = grid[i][1] = 0;
        }
    }
}
