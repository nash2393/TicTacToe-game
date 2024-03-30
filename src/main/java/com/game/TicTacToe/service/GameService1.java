package com.game.TicTacToe.service;

import com.game.TicTacToe.Model.Game;
import com.game.TicTacToe.Model.Team;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService1 {
    private final Map<String, Game> games = new HashMap<>();
    private final Map<String, Team> teams = new HashMap<>();

    // Registers a team and its API key, or adds an API key to an existing team
    public void registerTeamApiKey(String teamId, String apiKey) {
        Team team = teams.computeIfAbsent(teamId, k -> new Team(teamId));
        team.addApiKey(apiKey);
    }

    // Validates if the API key belongs to the given team
    public boolean isValidApiKey(String teamId, String apiKey) {
        Team team = teams.get(teamId);
        return team != null && team.isValidApiKey(apiKey);
    }

    // Creates a new game with two teams
    public String createNewGame(String team1Id, String team1ApiKey, String team2Id, String team2ApiKey, int boardSize) {

        if (!isValidApiKey(team1Id, team1ApiKey) || !isValidApiKey(team2Id, team2ApiKey)) {
            return "Invalid API key(s)";
        }
        String gameId = generateGameId();
        Game game = new Game(gameId, boardSize); // Pass boardSize to the Game constructor
        Game.TeamDetails team1 = new Game.TeamDetails(team1Id);
        Game.TeamDetails team2 = new Game.TeamDetails(team2Id);
        game.setTeams(team1, team2);
        games.put(gameId, game);
        return gameId;
    }
    private static String generateGameId() {
        Random random = new Random();
        int gameIdInt = 10000 + random.nextInt(90000); // Generate a random 5-digit number
        return String.valueOf(gameIdInt);
    }
    // Processes a move in a game
    // Processes a move in a game and updates game results accordingly
    public String makeMove(String gameId, int player, int x, int y) {
        Game game = games.get(gameId);
        if (game == null) {
            return "Game not found.";
        }

        if (x < 0 || x >= game.getBoard().length || y < 0 || y >= game.getBoard()[0].length) {
            return "Invalid move. Position out of bounds.";
        }
        if (game.getBoard()[x][y] != 0) {
            return "Invalid move. Cell is already occupied.";
        }
        if(!game.getStatus().equals("IN_PROGRESS")){
            //String gameState = checkWinOrDraw(game);
             return "Game over: " + game.getStatus() + ". Winning team: " + getWinningTeamId(gameId) + "\n" + printBoard(game.getBoard());
        }
        if (game.getCurrentPlayer() != player) {
            return "Invalid move. It's the other player's turn.";
        }

        // Check if the player made consecutive moves
        if (game.getLastPlayer() == player) {
            return "Invalid move. Cannot make consecutive moves. It's the other player's turn.";
        }

        game.getBoard()[x][y] = player;
        game.getMoves().add(new Game.Move(player, x, y)); // Recording the move

        String gameState = checkWinOrDraw(game);
        game.setStatus(gameState);
        game.setLastPlayer(player);


        if (!gameState.equals("IN_PROGRESS")) {
            // Update the game results if the game is concluded
            updateGameResults(gameId, gameState);
            return "Game over: " + gameState + ". Winning team: " + getWinningTeamId(gameId) + "\n" + printBoard(game.getBoard());
        }
        game.setCurrentPlayer(player == 1 ? 2 : 1);

        return "Move accepted. Game continues.\n" + printBoard(game.getBoard());
    }

    // Method to get the winning team ID
    private String getWinningTeamId(String gameId) {
        Game game = games.get(gameId);
        if (game == null || "IN_PROGRESS".equals(game.getStatus())) {
            return "No winner";
        }
        String gameState = game.getStatus();
        if ("DRAW".equals(gameState)) {
            return "DRAW";
        } else {
            int lastPlayer = game.getMoves().get(game.getMoves().size() - 1).getPlayer();
            return lastPlayer == 1 ? game.getTeam1().getTeamId() : game.getTeam2().getTeamId();
        }
    }



    // Returns the state of a game


    // Retrieves stats for a specific team

    private void updateGameResults(String gameId, String gameState) {
        Game game = games.get(gameId);
        if (game == null) return;

        // Identify which team won or if the game was a draw.
        boolean isDraw = "DRAW".equals(gameState);
        String winningTeamId = null, losingTeamId = null;

        if (!isDraw) {
            // Determine the winner based on the last move.
            int lastPlayer = game.getMoves().get(game.getMoves().size() - 1).getPlayer();
            winningTeamId = lastPlayer == 1 ? game.getTeam1().getTeamId() : game.getTeam2().getTeamId();
            losingTeamId = lastPlayer == 1 ? game.getTeam2().getTeamId() : game.getTeam1().getTeamId();
        }

        // Update game results for teams involved.
        if (isDraw) {
            teams.get(game.getTeam1().getTeamId()).updateGameResult(gameId, "DRAW", game.getMoves());
            teams.get(game.getTeam2().getTeamId()).updateGameResult(gameId, "DRAW", game.getMoves());
        } else {
            teams.get(winningTeamId).updateGameResult(gameId, "WON", game.getMoves());
            teams.get(losingTeamId).updateGameResult(gameId, "LOST", game.getMoves());
        }
    }


    private boolean checkWin(int[][] board) {
        // Check rows and columns for a win
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] != 0 && board[i][0] == board[i][1] && board[i][1] == board[i][2]) ||
                    (board[0][i] != 0 && board[0][i] == board[1][i] && board[1][i] == board[2][i])) {
                return true;
            }
        }

        // Check diagonals for a win
        if ((board[0][0] != 0 && board[0][0] == board[1][1] && board[1][1] == board[2][2]) ||
                (board[0][2] != 0 && board[0][2] == board[1][1] && board[1][1] == board[2][0])) {
            return true;
        }

        return false;
    }

    private boolean isBoardFull(int[][] board) {
        for (int[] row : board) {
            for (int cell : row) {
                if (cell == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private String checkWinOrDraw(Game game) {
        if (checkWin(game.getBoard())) {
            // Assuming player 1 is always the winner if a win is detected.
            // This is a simplification; you should implement logic to determine the actual winner.
            return game.getMoves().get(game.getMoves().size() - 1).getPlayer() == 1 ? "TEAM1_WON" : "TEAM2_WON";
        } else if (isBoardFull(game.getBoard())) {
            return "DRAW";
        } else {
            return "IN_PROGRESS";
        }
    }
    private String printBoard(int[][] board) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                char symbol;
                switch (board[i][j]) {
                    case 1: symbol = 'X'; break;
                    case 2: symbol = 'O'; break;
                    default: symbol = '.'; // Empty cell
                }
                sb.append(symbol).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public String printBoard(String gameId) {
        Game game = games.get(gameId);
        if (game == null) {
            return "Game not found.";
        }
        StringBuilder sb = new StringBuilder();
        int[][] board = game.getBoard();
        for (int[] row : board) {
            for (int cell : row) {
                sb.append(cell == 0 ? "." : (cell == 1 ? "X" : "O")).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    public String getGameState(String gameId) {
        Game game = games.get(gameId);
        if (game == null) {
            return "Game does not exist.";
        }
        return "Game state: " + game.getStatus();
    }
    public Team getTeamStats(String teamId) {
        for (Team team : teams.values()) {
            if (team.getTeamId().equals(teamId)) {
                return team;
            }
        }
        return null; // Team not found
    }
    public List<Team> getAllTeamStats() {
        return new ArrayList<>(teams.values());
    }




}
