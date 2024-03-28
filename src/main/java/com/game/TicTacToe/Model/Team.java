package com.game.TicTacToe.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Team {
    private String teamId;
    private Set<String> apiKeys = new HashSet<>(); // Supports multiple API keys per team
    private List<GameResult> gameResults = new ArrayList<>();

    // Constructor
    public Team(String teamId) {
        this.teamId = teamId;
    }

    // Adds an API key to this team
    public void addApiKey(String apiKey) {
        apiKeys.add(apiKey);
    }

    // Checks if the provided API key is valid for this team
    public boolean isValidApiKey(String apiKey) {
        return apiKeys.contains(apiKey);
    }

    // Updates or adds a game result for this team
    public void updateGameResult(String gameId, String status, List<Game.Move> moves) {
        GameResult existingResult = gameResults.stream()
                .filter(result -> result.getGameId().equals(gameId))
                .findFirst()
                .orElse(null);

        if (existingResult != null) {
            existingResult.setStatus(status);
            existingResult.setMoves(moves);
        } else {
            gameResults.add(new GameResult(gameId, status, moves));
        }
    }

    // Getters and Setters
    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public Set<String> getApiKeys() {
        return apiKeys;
    }

    public void setApiKeys(Set<String> apiKeys) {
        this.apiKeys = apiKeys;
    }

    public List<GameResult> getGameResults() {
        return gameResults;
    }

    public void setGameResults(List<GameResult> gameResults) {
        this.gameResults = gameResults;
    }

    // Nested class to represent game results, including the list of moves
    public static class GameResult {
        private String gameId;
        private String status; // e.g., "WON", "LOST", "DRAW"
        private List<Game.Move> moves;

        public GameResult(String gameId, String status, List<Game.Move> moves) {
            this.gameId = gameId;
            this.status = status;
            this.moves = new ArrayList<>(moves); // Make a copy to ensure immutability
        }

        // Getters and Setters for GameResult
        public String getGameId() {
            return gameId;
        }

        public void setGameId(String gameId) {
            this.gameId = gameId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<Game.Move> getMoves() {
            return moves;
        }

        public void setMoves(List<Game.Move> moves) {
            this.moves = new ArrayList<>(moves); // Copy for immutability
        }
    }
}
