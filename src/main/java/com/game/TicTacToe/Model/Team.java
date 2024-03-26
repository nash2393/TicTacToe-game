package com.game.TicTacToe.Model;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String teamId;
    private String apiKey;
    private List<GameResult> gameResults = new ArrayList<>();

    // Constructors, getters, and setters as before

    public void addGameResult(String gameId, String status) {
        gameResults.add(new GameResult(gameId, status));
    }

    public Team(String teamId, String apiKey) {
        this.teamId = teamId;
        this.apiKey = apiKey;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public List<GameResult> getGameResults() {
        return gameResults;
    }

    public void setGameResults(List<GameResult> gameResults) {
        this.gameResults = gameResults;
    }

    // This method updates or adds the game result for a specific game
    public void updateGameResult(String gameId, String status) {
        for (GameResult gr : gameResults) {
            if (gr.getGameId().equals(gameId)) {
                gr.setStatus(status);
                return;
            }
        }
        gameResults.add(new GameResult(gameId, status));
    }

    // Inner class GameResult - add a setStatus method
    public static class GameResult {
        private String gameId;
        private String status; // WON, LOSS, DRAW, INPROGRESS

        public GameResult(String gameId, String status) {
            this.gameId = gameId;
            this.status = status;
        }

        // Existing getters and a new setter for status
        public String getGameId() { return gameId; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; } // New setter
    }
}
