package com.game.TicTacToe.service;

import com.game.TicTacToe.Model.Game;
import com.game.TicTacToe.Model.Team;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {
    private final Map<String, Game> games = new HashMap<>();
    private final Map<String, Team> teams = new HashMap<>();

    public GameService() {
        // Initialize 10 teams with their API keys
        for (int i = 1; i <= 10; i++) {
            Team newTeam = new Team("team" + i, "apiKey" + i);
            teams.put("apiKey" + i, newTeam);
        }
    }

    public boolean isValidApiKey(String apiKey) {
        return teams.containsKey(apiKey);
    }
    public void updateGameResult(String gameId, String apiKey, String result) {
        Team team = teams.get(apiKey);
        if (team != null) {
            team.addGameResult(gameId, result);
        }
    }

    public Team getTeamStats(String apiKey) {
        return teams.get(apiKey);
    }

    public List<Team> getAllTeamStats() {
        return new ArrayList<>(teams.values());
    }

    public String createNewGame(String team1ApiKey, String team2ApiKey) {
        String gameId = UUID.randomUUID().toString();
        Game game = new Game(gameId, new int[3][3], team1ApiKey, team2ApiKey, "IN_PROGRESS");
        games.put(gameId, game);
        // Optionally, initialize game results or other tracking as needed.
        return gameId;
    }

//    public String makeMove(String gameId, int player, int x, int y) {
//        Game game = games.get(gameId);
//        if (game == null || game.getBoard()[x][y] != 0) {
//            return "Invalid move or game does not exist.";
//        }
//        // Update the board and check for a win or draw...
//        // Assume checkWinOrDraw returns "TEAM1_WON", "TEAM2_WON", "DRAW", or "IN_PROGRESS"
//        String result = checkWinOrDraw(game.getBoard());
//        game.setStatus(result);
//        updateGameResults(gameId, result, game.getTeam1ApiKey(), game.getTeam2ApiKey());
//        return result;
//    }
public String makeMove(String gameId, int player, int x, int y) {
    Game game = games.get(gameId);
    if (game == null || game.getBoard()[x][y] != 0) {
        return "Invalid move or game does not exist.";
    }
    game.getBoard()[x][y] = player; // Mark the move on the board.

    // Check the game state after making a move using checkWinOrDraw
    String result = checkWinOrDraw(game.getBoard());
    game.setStatus(result); // Update the game status based on the result.

    // Based on the result, determine if we need to update the team's game results.
    // This logic would typically consider who the winner is and update both participating teams accordingly.
    if (!"IN_PROGRESS".equals(result)) {
        updateGameResults(gameId, result, game.getTeam1ApiKey(), game.getTeam2ApiKey());
    }

    return result + "\n" + printBoard(game.getBoard()); // Return the game state after the move.
}
    private void updateGameResults(String gameId, String result, String team1ApiKey, String team2ApiKey) {
        Team team1 = teams.get(team1ApiKey);
        Team team2 = teams.get(team2ApiKey);
        if (team1 != null && team2 != null) {
            if ("TEAM1_WON".equals(result)) {
                team1.addGameResult(gameId, "WON");
                team2.addGameResult(gameId, "LOST");
            } else if ("TEAM2_WON".equals(result)) {
                team1.addGameResult(gameId, "LOST");
                team2.addGameResult(gameId, "WON");
            } else if ("DRAW".equals(result)) {
                team1.addGameResult(gameId, "DRAW");
                team2.addGameResult(gameId, "DRAW");
            }
        }
    }

    // Method implementations for hasWinningLine, checkRow, checkColumn, checkDiagonals, getGameState, and printBoard
    public String printBoard(String gameId) {
        Game game = games.get(gameId);
        if (game == null) {
            return "Game does not exist.";
        }
        return printBoard(game.getBoard());
    }
    private String printBoard(int[][] board) {
        StringBuilder sb = new StringBuilder();
        for (int[] row : board) {
            for (int cell : row) {
                sb.append(cell == 0 ? "." : cell).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    private final int boardSize = 3; // Default size, can be adjusted as needed

//    public String createNewGame(int size) {
//        String gameId = UUID.randomUUID().toString();
//        int[][] gameBoard = new int[size][size]; // Initialize game board with zeros
//        games.put(gameId, gameBoard);
//        return gameId;
//    }

//    public String makeMove(String gameId, int player, int x, int y) {
//        int[][] board = games.get(gameId);
//        if (board == null || x < 0 || x >= board.length || y < 0 || y >= board.length || board[x][y] != 0) {
//            return "Invalid move";
//        }
//        board[x][y] = player;
//
//        // Check the game state after making a move
//        String gameState = getGameState(gameId);
//        if ("WON".equals(gameState)) {
//            return "Player " + player + " wins!\n" + printBoard(board);
//        } else if ("DRAW".equals(gameState)) {
//            return "The game is a draw.\n" + printBoard(board);
//        }
//
//        return "Move made successfully.\n" + printBoard(board);
//    }

    private String checkWinOrDraw(int[][] board) {
        int size = board.length; // Assuming a square board
        // Check rows and columns
        for (int i = 0; i < size; i++) {
            if (isWinningLine(board[i][0], board[i][1], board[i][2])) return "WIN";
            if (isWinningLine(board[0][i], board[1][i], board[2][i])) return "WIN";
        }

        // Check diagonals
        if (isWinningLine(board[0][0], board[1][1], board[2][2])) return "WIN";
        if (isWinningLine(board[0][2], board[1][1], board[2][0])) return "WIN";

        // Check for draw
        if (isBoardFull(board)) return "DRAW";

        // Game continues
        return "IN_PROGRESS";
    }

    private boolean isWinningLine(int cell1, int cell2, int cell3) {
        return (cell1 != 0) && (cell1 == cell2) && (cell2 == cell3);
    }

    private boolean isBoardFull(int[][] board) {
        for (int[] row : board) {
            for (int cell : row) {
                if (cell == 0) {
                    return false; // At least one cell is not filled, so the board is not full
                }
            }
        }
        return true; // All cells are filled
    }
    private boolean checkRow(int[][] board, int row) {
        int first = board[row][0];
        for (int col = 1; col < board.length; col++) {
            if (board[row][col] != first) return false;
        }
        return first != 0;
    }

    private boolean checkColumn(int[][] board, int col) {
        int first = board[0][col];
        for (int row = 1; row < board.length; row++) {
            if (board[row][col] != first) return false;
        }
        return first != 0;
    }

    private boolean checkDiagonals(int[][] board) {
        int size = board.length;
        int firstDiagonal = board[0][0], secondDiagonal = board[0][size - 1];
        boolean firstDiagWin = firstDiagonal != 0, secondDiagWin = secondDiagonal != 0;

        for (int i = 1; i < size; i++) {
            if (board[i][i] != firstDiagonal) firstDiagWin = false;
            if (board[i][size - i - 1] != secondDiagonal) secondDiagWin = false;
        }

        return firstDiagWin || secondDiagWin;
    }
    public String getGameState(String gameId) {
        Game game = games.get(gameId);
        if (game == null) {
            return "Game does not exist.";
        }
        // Retrieve the current board state from the game object.
        int[][] board = game.getBoard();
        // Use the checkWinOrDraw method to determine the current game state based on the board.
        String gameState = checkWinOrDraw(board);

        // Based on the returned state, update the game's status if necessary (e.g., from IN_PROGRESS to WON or DRAW).
        // Note: This might not be necessary if you're already updating the game state elsewhere after each move.
        game.setStatus(gameState);

        return gameState;
    }




//    public String getGameState(String gameId) {
//        if (hasWinningLine(gameId)) {
//            return "WON";
//        }
//        // Check for a draw (no zero cells left)
//        int[][] board = games.get(gameId);
//        for (int[] row : board) {
//            for (int cell : row) {
//                if (cell == 0) {
//                    return "ONGOING";
//                }
//            }
//        }
//        return "DRAW";
//    }

//    private String printBoard(int[][] board) {
//        StringBuilder sb = new StringBuilder();
//        for (int[] row : board) {
//            for (int cell : row) {
//                sb.append(cell == 0 ? "." : cell).append(" ");
//            }
//            sb.append("\n");
//        }
//        return sb.toString();
//    }
}
