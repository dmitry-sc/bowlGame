package aw.bowl.main.controller;

import aw.bowl.main.model.Game;
import aw.bowl.main.model.Player;
import aw.bowl.main.services.BowlTransportService;
import aw.bowl.main.services.GameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by dmitry on 26.11.16.
 */

@RestController
@RequestMapping("/bowl")
@EnableHystrix
public class BowlController  {
    private final BowlTransportService bowlTransportService;
    private final GameHelper gameHelper;

    @Autowired
    public BowlController(BowlTransportService bowlTransportService, GameHelper gameHelper) {
        this.bowlTransportService = bowlTransportService;
        this.gameHelper = gameHelper;
    }

    @GetMapping("/start")
    public Game startGame() {
        return bowlTransportService.startGame();
    }

    @GetMapping("/players/{name}/{gameId}")
    public Player addPlayer(String name, @PathVariable Integer gameId) {
        return bowlTransportService.addPlayer(name, gameId);
    }

    @GetMapping("/players")
    public List<Player> getPlayers() {
        return gameHelper.getPlayers();
    }

    @GetMapping("/throws/{gameId}/{playerId}")
    public Player getPlayerInfo(@PathVariable Integer gameId, @PathVariable Integer playerId) {
        return gameHelper.getPlayerThrows(gameId, playerId);
    }

    @GetMapping("/winner/{gameId}")
    public Player getWinner(@PathVariable Integer gameId) {
        return gameHelper.getWinnerPlayer(gameId);
    }


    @GetMapping("/roll/{gameId}/{gameType}/{playerId}/{pins}")
    public Boolean roll(
            @PathVariable Integer gameId,
            @PathVariable Integer gameType,
            @PathVariable Integer playerId,
            @PathVariable Integer pins
            ) {

        return bowlTransportService.roll(gameId, gameType, playerId, pins);
    }
}
