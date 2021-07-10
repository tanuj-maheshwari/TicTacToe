import java.util.Random;

public class Computer {

    // Utility function that calculates the "hypothetical" score of the board
    // Score is +10 if X (i.e. the player) has won, -10 if O (i.e. the computer) has
    // won
    private int gameScore(int[][] grid) {
        int[][] victory_lines = { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 },
                { 0, 4, 8 }, { 2, 4, 6 } };
        int score = 0;

        for (int[] victory_line : victory_lines) {
            int cell_value_sum = 0;
            boolean full_line = true;
            for (int cell_id : victory_line) {
                if (grid[cell_id][0] != 0) {
                    cell_value_sum += grid[cell_id][1];
                } else {
                    full_line = false;
                    break;
                }
            }
            if (full_line) {
                if (cell_value_sum == 3) {
                    score = 10;
                    break;
                } else if (cell_value_sum == -3) {
                    score = -10;
                    break;
                }
            }
        }

        return score;
    }

    // Checks if the given grid (in 2D Array format) is full
    private boolean isGridFull(int[][] grid) {
        boolean is_grid_full = true;
        for (int i = 0; i < grid.length; i++) {
            if (grid[i][0] == 0) {
                is_grid_full = false;
                break;
            }
        }
        return is_grid_full;
    }

    // Optimised MINIMAX function with alpha-beta pruning(used for Impossible
    // difficulty)
    // The basic idea is to generate all possible end states of the game, assuming
    // that both the players play optimally. The best optimal score will be returned
    // for each player.
    // In our case, the player is the Maximiser, while the computer is the
    // minimiser.
    private int minimax(int[][] grid, int turn, int turn_no, int alpha, int beta) {
        int score = gameScore(grid);

        // turn_no is subtracted/added to the score so that the fastest draw/victory
        // happens
        if (score == 10) {
            return score - turn_no;
        } else if (score == -10) {
            return score + turn_no;
        } else if (isGridFull(grid)) {
            return 0;
        }

        // Maximiser's turn
        if (turn == 1) {
            int max_score = -100;

            // for each possible move, determine the move which maximises the board score
            for (int i = 0; i < grid.length; i++) {
                if (grid[i][0] == 0) {
                    grid[i][0] = 1;
                    grid[i][1] = 1;
                    max_score = Math.max(max_score, minimax(grid, turn + 1, turn_no + 1, alpha, beta));
                    alpha = Math.max(max_score, alpha);
                    grid[i][0] = grid[i][1] = 0;
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return max_score;
        }

        // Minimiser's turn
        else {
            int min_score = 100;

            // for each possible move, determine the move which minimises the board score
            for (int i = 0; i < grid.length; i++) {
                if (grid[i][0] == 0) {
                    grid[i][0] = 1;
                    grid[i][1] = -1;
                    min_score = Math.min(min_score, minimax(grid, turn - 1, turn_no + 1, alpha, beta));
                    beta = Math.min(min_score, beta);
                    grid[i][0] = grid[i][1] = 0;
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return min_score;
        }
    }

    // Function which makes the move for the computer (based on the difficulty)
    public int makeMove(int difficulty, Game game) {

        int[][] grid = game.gridStatus(); // grid
        int move;

        // EASY (i.e. random moves)
        if (difficulty == 1) {
            Random rand = new Random();

            // Keep making random moves untill an empty box is found
            do {
                move = rand.nextInt(9) + 1;
            } while (grid[move - 1][0] != 0);

            return move;
        }

        // MEDIUM (i.e. tries to prevent your victory and tries to win)
        // The idea is to take a look at all the rows/columns/diagonals of the board. If
        // the computer or player is going to win in the next move (i.e. 2 of the 3
        // cells are filled with the same symbol), the computer moves to that empty spot
        // in the line. This prevents player's victory unless the player has made 2 such
        // spots.
        else if (difficulty == 2) {
            move = -1;
            int temp_move = -1;
            int[][] victory_lines = { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 },
                    { 0, 4, 8 }, { 2, 4, 6 } };

            for (int[] victory_line : victory_lines) {
                int num_filled_cells = 0, cell_value_sum = 0, empty_cell_id = -1;
                for (int cell_id : victory_line) {
                    if (grid[cell_id][0] == 0) {
                        empty_cell_id = cell_id;
                    } else {
                        num_filled_cells++;
                        cell_value_sum += grid[cell_id][1];
                    }
                }

                // If number of filled cells in any line is 2, and both are the same symbol,
                // make the move in that empty spot.
                // This strategy is benificial for both the situations (two Xs or two Os in a
                // line). If there are two Xs, the computer prevents player's victory. If there
                // are two Os, the computer wins.
                // The computer prefers winning over stopping the player from winning.
                if (num_filled_cells == 2) {
                    if (cell_value_sum == 2) {
                        temp_move = empty_cell_id + 1;
                        continue;
                    } else if (cell_value_sum == -2) {
                        move = empty_cell_id + 1;
                        break;
                    }
                }
            }

            // If no such spot exists, the computer makes a random move.
            if (move == -1) {
                if (temp_move == -1) {
                    Random rand = new Random();

                    do {
                        move = rand.nextInt(9) + 1;
                    } while (grid[move - 1][0] != 0);
                } else {
                    move = temp_move;
                }
            }
            return move;
        }

        // IMPOSSIBLE (i.e. you can't win against this mode)
        // The issue with Medium stratedgy is that though it works fine if the player
        // doesn't manage to create two spots that determine his/her victory, it fails
        // to prevent the user to make such cases.
        // With this strategy, the player is forced so that he/she can't even create
        // such a situation. This works by looking ahead to all possible end states of
        // the board, and then backtracks to determine which one is the best for the
        // computer.
        else {
            int[][] temp_grid = game.gridStatus().clone();
            int best_score = 100;
            move = -1;

            // Compute score for all possible next moves, and move at the position with the
            // least score (because the computer is a minimiser)
            for (int i = 0; i < temp_grid.length; i++) {
                if (temp_grid[i][0] == 0) {
                    temp_grid[i][0] = 1;
                    temp_grid[i][1] = -1;
                    int cell_score = minimax(temp_grid, 1, 0, -100, 100);
                    if (cell_score < best_score) {
                        best_score = cell_score;
                        move = i + 1;
                    }
                    temp_grid[i][0] = temp_grid[i][1] = 0;
                }
            }

            return move;
        }
    }
}
