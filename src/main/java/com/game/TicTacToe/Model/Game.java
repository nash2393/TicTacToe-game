package com.game.TicTacToe.Model;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private String gameId;
    private int[][] board; // Dynamically sized board
    private String status; // Game status (e.g., "IN_PROGRESS", "WON", "DRAW")
    private TeamDetails team1;
    private TeamDetails team2;
    private List<Move> moves = new ArrayList<>();
    private int boardSize;

    public void setTarget(int target) {
        this.target = target;
    }

    public int getTarget() {
        return target;
    }

    private int target;
    private int currentPlayer;
    private int lastPlayer;

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getLastPlayer() {
        return lastPlayer;
    }

    public void setLastPlayer(int lastPlayer) {
        this.lastPlayer = lastPlayer;
    }

    // Constructor that takes gameId and boardSize
    public Game(String gameId, int boardSize,int target) {
        this.gameId = gameId;
        this.currentPlayer=1;
        this.lastPlayer=0;
        this.boardSize = boardSize;
        this.board = new int[boardSize][boardSize]; // Initialize the board with the specified size
        this.status = "IN_PROGRESS"; // Default status
        this.target = target;
    }

    // Setter method for setting teams
    public void setTeams(TeamDetails team1, TeamDetails team2) {
        this.team1 = team1;
        this.team2 = team2;
    }

    // Getters and setters for all the fields
    public String getGameId() { return gameId; }
    public void setGameId(String gameId) { this.gameId = gameId; }
    public int[][] getBoard() { return board; }
    public void setBoard(int[][] board) { this.board = board; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public TeamDetails getTeam1() { return team1; }
    public void setTeam1(TeamDetails team1) { this.team1 = team1; }
    public TeamDetails getTeam2() { return team2; }
    public void setTeam2(TeamDetails team2) { this.team2 = team2; }
    public List<Move> getMoves() { return moves; }
    public void setMoves(List<Move> moves) { this.moves = moves; }
    public int getBoardSize() { return boardSize; }
    public void setBoardSize(int boardSize) { this.boardSize = boardSize; }

    // Nested TeamDetails class
    public static class TeamDetails {
        private String teamId;

        public TeamDetails(String teamId) {
            this.teamId = teamId;
        }

        // Getters and setters for TeamDetails fields
        public String getTeamId() { return teamId; }
        public void setTeamId(String teamId) { this.teamId = teamId; }
    }

    // Nested Move class
    public static class Move {
        private int player; // 1 or 2 to represent each player/team
        private int x; // x-coordinate of the move on the board
        private int y; // y-coordinate of the move on the board

        public Move(int player, int x, int y) {
            this.player = player;
            this.x = x;
            this.y = y;
        }

        // Getters and setters for Move fields
        public int getPlayer() { return player; }
        public void setPlayer(int player) { this.player = player; }
        public int getX() { return x; }
        public void setX(int x) { this.x = x; }
        public int getY() { return y; }
        public void setY(int y) { this.y = y; }
    }
}
