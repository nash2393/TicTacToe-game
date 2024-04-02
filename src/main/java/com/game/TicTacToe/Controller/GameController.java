package com.game.TicTacToe.Controller;

import com.game.TicTacToe.Model.Team;
import com.game.TicTacToe.service.GameService1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService1 gameService;


    @Autowired
    public GameController(GameService1 gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/new")
    public ResponseEntity<String> createGame(@RequestParam("team1Id") String team1Id,
                                             @RequestParam("team1ApiKey") String team1ApiKey,
                                             @RequestParam("team2Id") String team2Id,
                                             @RequestParam("team2ApiKey") String team2ApiKey,
                                             @RequestParam("boardSize") int boardSize,
                                             @RequestParam(value = "target", required = false, defaultValue = "0") int target) {
        gameService.registerTeamApiKey(team1Id, team1ApiKey);
        gameService.registerTeamApiKey(team2Id, team2ApiKey);
        if (!gameService.isValidApiKey(team1Id, team1ApiKey) || !gameService.isValidApiKey(team2Id, team2ApiKey)) {
            return new ResponseEntity<>("Invalid API key(s)", HttpStatus.UNAUTHORIZED);
        }
        String gameId = gameService.createNewGame(team1Id, team1ApiKey, team2Id, team2ApiKey, boardSize, target);
        return ResponseEntity.ok("Game created with ID: " + gameId);
    }

    @PostMapping("/{gameId}/move")
    public ResponseEntity<String> makeMove(@PathVariable String gameId,
                                           @RequestParam int player,
                                           @RequestParam int x,
                                           @RequestParam int y,
                                           @RequestHeader("API-Key") String apiKey) {

        String moveResult = gameService.makeMove(gameId, player, x, y);
        return ResponseEntity.ok(moveResult);
    }

    @GetMapping("/{gameId}/state")
    public ResponseEntity<String> getGameState(@PathVariable String gameId) {
        String state = gameService.getGameState(gameId);
        return ResponseEntity.ok("Game state: " + state);
    }

    @GetMapping("/stats/my")
    public ResponseEntity<?> getMyTeamStats(@RequestParam("teamId") String teamId) {
        Team teamStats = gameService.getTeamStats(teamId);
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
        return ResponseEntity.ok(boardPrintout);
    }
}
