package aw.bowl.score.controller;

import aw.bowl.score.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by dmitry on 26.11.16.
 */

@RestController
public class GameScoreController {

    @Autowired
    private ScoreService scoreService;

    @RequestMapping(value = "/roll/{gameId}/{gameType}/{playerId}/{pins}", method = RequestMethod.PUT)
    public Boolean roll(
            @PathVariable("gameId")   Integer gameId,
            @PathVariable("gameType") Integer gameType,
            @PathVariable("playerId") Integer playerId,
            @PathVariable("pins")     Integer pins
    ) {
        return scoreService.roll(gameId, gameType, playerId, pins);
    }

    @RequestMapping(value = "/score/{gameId}/{playerId}", method = RequestMethod.GET)
    public Integer getPlayerScore(@PathVariable("gameId") Integer gameId, @PathVariable("playerId")Integer playerId) {
        return scoreService.getPlayerScore(gameId, playerId);
    }

    @RequestMapping(value = "/throws/{gameId}/{playerId}", method = RequestMethod.GET)
    public List<Integer> getPlayerThrows(@PathVariable("gameId") Integer gameId, @PathVariable("playerId")Integer playerId) {
        return scoreService.getPlayerThrows(gameId, playerId);
    }

    @RequestMapping(value = "/score/winner/{gameId}", method = RequestMethod.GET)
    public Integer getWinnerPlayer(@PathVariable("gameId") Integer gameId) {
        return scoreService.getWinnerPlayer(gameId);
    }

    @RequestMapping(value = "/status/{gameId}", method = RequestMethod.GET)
    public Boolean isGameEnded(@PathVariable("gameId") Integer gameId) {
        return scoreService.isGameEnded(gameId);
    }
}
