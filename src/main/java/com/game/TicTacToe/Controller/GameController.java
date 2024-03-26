package com.game.TicTacToe.Controller;

import com.game.TicTacToe.Model.Team;
import com.game.TicTacToe.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/new")
    public ResponseEntity<String> createGame(@RequestHeader("Team1-API-Key") String team1ApiKey,
                                             @RequestHeader("Team2-API-Key") String team2ApiKey) {
        if (!gameService.isValidApiKey(team1ApiKey) || !gameService.isValidApiKey(team2ApiKey)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid API key(s)");
        }
        String gameId = gameService.createNewGame(team1ApiKey, team2ApiKey);
        return ResponseEntity.ok("Game created with ID: " + gameId);
    }

    @PostMapping("/{gameId}/move")
    public ResponseEntity<String> makeMove(@PathVariable String gameId,
                                           @RequestParam int player,
                                           @RequestParam int x,
                                           @RequestParam int y,
                                           @RequestHeader("API-Key") String apiKey) {
        if (!gameService.isValidApiKey(apiKey)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid API key");
        }
        String moveResult = gameService.makeMove(gameId, player, x, y);
        return moveResult.startsWith("Invalid") ?
                ResponseEntity.badRequest().body(moveResult) :
                ResponseEntity.ok(moveResult);
    }

    @GetMapping("/{gameId}/state")
    public ResponseEntity<String> getGameState(@PathVariable String gameId) {
        String state = gameService.getGameState(gameId);
        return ResponseEntity.ok("Game state: " + state);
    }

    @GetMapping("/stats/my")
    public ResponseEntity<?> getMyTeamStats(@RequestHeader("API-Key") String apiKey) {
        if (!gameService.isValidApiKey(apiKey)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid API key");
        }
        Team teamStats = gameService.getTeamStats(apiKey);
        return teamStats == null ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(teamStats);
    }

    @GetMapping("/stats/all")
    public ResponseEntity<List<Team>> getAllTeamStats() {
        List<Team> allTeamStats = gameService.getAllTeamStats();
        return ResponseEntity.ok(allTeamStats);
    }
    @GetMapping("/{gameId}/board")
    public ResponseEntity<String> getBoardState(@PathVariable String gameId) {
        String boardPrintout = gameService.printBoard(gameId);
        if (boardPrintout.equals("Game does not exist.")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(boardPrintout);
        }
        return ResponseEntity.ok(boardPrintout);
    }

}
