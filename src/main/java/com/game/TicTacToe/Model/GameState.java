package com.game.TicTacToe.Model;public class GameState {
    private String status; // "ONGOING", "WON", "DRAW"
    private int winner; // 0 if no winner, 1 or 2 depending on the player

    // Constructors, getters, and setters
    public GameState(String status, int winner) {
        this.status = status;
        this.winner = winner;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }
}
