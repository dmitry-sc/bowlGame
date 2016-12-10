package aw.bowl.main.services;

import aw.bowl.main.model.Game;
import aw.bowl.main.model.Player;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by dmitry on 08.12.16.
 */
@Component
@EnableHystrix
public class GameHelper {
    private final BowlTransportService bowlTransportService;
    private List<Player> players;
    private final String names[] = {
            "Dima",   "Youra",    "Alexey",
            "Irina",  "Oleg",     "Ivaylo",
            "Thomas", "Lucas",    "Niels",
            "Ivan",   "Robrecht", "Merlijn",
            "Ivaylo", "Alexander","Dancho",
    };

    @Autowired
    public GameHelper(BowlTransportService bowlTransportService) {
        this.bowlTransportService = bowlTransportService;
        this.players = new LinkedList<>();
    }

    public String[] getNames() {
        return names;
    }

    public List<Player> getPlayers() {
        return players;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @HystrixCommand(fallbackMethod = "unknownWinner")
    public Player getWinnerPlayer(Integer gameId) {
        Integer winnerId = bowlTransportService.getWinnerPlayer(gameId);
        return getPlayerThrows(gameId, winnerId);
    }

    private Player unknownWinner(Integer gameId) {
        return new Player("Fallback method goes off, devs warned, player - unknown");
    }

    /**
     * Creates players
     * checks if name already exists and re-creates
     * TODO: no overflow checks are here - can be infinite loop
     *
     * @param playersCount
     * @param game
     */
    public List<Player> initiatePlayers(Integer playersCount, Game game) {
        for (int i = 0; i < playersCount; i++) {
            Player player;
            do {
                player = bowlTransportService.addPlayer(names[getRandomNumberInRange(0, names.length)], game.getGameId());
            } while (player == null);
            players.add(player);
        }
        return players;
    }

    @HystrixCommand(fallbackMethod = "getPlayerThrowsDefault")
    public Player getPlayerThrows(Integer gameId, Integer playerId) {
        Player player = players.stream().filter((e) -> e.getPlayerId().equals(playerId) && e.getGameId().equals(gameId)).findFirst().get();
        player.setThrowz(bowlTransportService.getPlayerThrows(gameId, playerId));
        return player;
    }

    public Player getPlayerThrowsDefault(Integer gameId, Integer playerId) {
        return unknownWinner(playerId);
    }

    private int getRandomNumberInRange(int min, int max) {
        Random r = new Random();
        return r.ints(min, max).findFirst().getAsInt();
    }

    public int getPins(int max) {
        return getRandomNumberInRange(0, max);
    }
}
