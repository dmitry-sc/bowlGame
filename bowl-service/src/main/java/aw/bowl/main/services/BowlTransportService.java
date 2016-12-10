package aw.bowl.main.services;

import aw.bowl.main.model.Game;
import aw.bowl.main.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by dmitry on 26.11.16.
 */

@Service
public class BowlTransportService {
    @Autowired
    private RestTemplate restTemplate;

    public Game startGame() {
        Game game = restTemplate.exchange("http://game-service/game", HttpMethod.PUT, null, Game.class).getBody();
        System.out.println(">>> Game #" + game.getGameId() + " started!");
        game.setActive(true);
        return game;
    }

    public List<Game> getGames() {
        ParameterizedTypeReference<List<Game>> typeReference = new ParameterizedTypeReference<List<Game>>() {};
        return restTemplate.exchange("http://game-service/gameslist", HttpMethod.GET, null, typeReference).getBody();
    }

    public Player addPlayer(String name, Integer gameId) {
        Player player = restTemplate.exchange("http://game-service/players/" + name + "/" + gameId, HttpMethod.PUT, null, Player.class).getBody();
        if (player == null) {
            return null;
        }
        player.setGameId(gameId);
        return player;
    }

    public List<Player> getPlayers(Integer gameId) {
        ParameterizedTypeReference<List<Player>> typeReference = new ParameterizedTypeReference<List<Player>>() {};
        return restTemplate.exchange("http://game-service/players/byGame/" + gameId, HttpMethod.GET, null, typeReference).getBody();
    }

    public Boolean roll(Integer gameId, Integer gameType, Integer playerId, Integer pins) {
        return restTemplate.exchange("http://scoring-service/roll/" +
                    gameId   + "/" +
                    gameType + "/" +
                    playerId + "/" +
                    pins,
                    HttpMethod.PUT, null, Boolean.class).getBody();
    }

    public Boolean isGameEnded(Integer gameId) {
        return restTemplate.exchange("http://scoring-service/status/" + gameId, HttpMethod.GET, null, Boolean.class).getBody();
    }

    public Integer getWinnerPlayer(Integer gameId) {
        return restTemplate.exchange("http://scoring-service/score/winner/" + gameId, HttpMethod.GET, null, Integer.class).getBody();
    }

    public Integer getPlayerScore(Integer gameId, Integer playerId) {
        return restTemplate.exchange("http://scoring-service/score/" + gameId + "/" + playerId, HttpMethod.GET, null, Integer.class).getBody();
    }

    public List<Integer> getPlayerThrows(Integer gameId, Integer playerId) {
        ParameterizedTypeReference<List<Integer>> typeReference = new ParameterizedTypeReference<List<Integer>>(){};
        return restTemplate.exchange("http://scoring-service/throws/" + gameId + "/" + playerId, HttpMethod.GET, null, typeReference).getBody();
    }
}
