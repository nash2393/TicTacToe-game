package com.game.TicTacToe.Model;
public class GameBoard {
    private int[][] board;
    private final int size;

    public GameBoard(int size) {
        this.size = size;
        this.board = new int[size][size];
    }

    // Getters and setters
    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int getSize() {
        return size;
    }
}

