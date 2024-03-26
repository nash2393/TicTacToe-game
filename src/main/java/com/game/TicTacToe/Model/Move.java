package com.game.TicTacToe.Model;public class Move {
    private int player;
    private int x;
    private int y;

    public Move(int player, int x, int y) {
        this.player = player;
        this.x = x;
        this.y = y;
    }

    // Getters and setters
    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
