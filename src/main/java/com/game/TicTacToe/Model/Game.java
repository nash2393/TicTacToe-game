package com.game.TicTacToe.Model;


public class Game {
    private String gameId;
    private int[][] board; // Assuming a 2D array for the tic-tac-toe board.
    private String team1ApiKey;
    private String team2ApiKey;
    private String status; // e.g., "IN_PROGRESS", "TEAM1_WON", "TEAM2_WON", "DRAW"

    public String getGameId() {
        return gameId;
    }

    public Game(String gameId, int[][] board, String team1ApiKey, String team2ApiKey, String status) {
        this.gameId = gameId;
        this.board = board;
        this.team1ApiKey = team1ApiKey;
        this.team2ApiKey = team2ApiKey;
        this.status = status;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public String getTeam1ApiKey() {
        return team1ApiKey;
    }

    public void setTeam1ApiKey(String team1ApiKey) {
        this.team1ApiKey = team1ApiKey;
    }

    public String getTeam2ApiKey() {
        return team2ApiKey;
    }

    public void setTeam2ApiKey(String team2ApiKey) {
        this.team2ApiKey = team2ApiKey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
