package aw.bowl.game.controller;

import aw.bowl.game.model.Game;
import aw.bowl.game.model.Player;
import aw.bowl.game.repository.GameRepository;
import aw.bowl.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * We can use default CRUD instead, but this is simplier for testing
 * Created by dmitry on 26.11.16.
 */

@RestController
@RequestMapping("/players")
public class PlayerController {
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    GameRepository   gameRepository;

    @RequestMapping(value = "/{name}/{gameId}", method = RequestMethod.PUT)
    public Player put(HttpServletResponse   response,
            @PathVariable("name")   String  name,
            @PathVariable("gameId") Integer gameId) throws IOException {

        Game game = gameRepository.findOne(gameId);
        Player newPlayer;
        if (game == null || playerRepository.findOneByName(name) != null) {
            response.sendError(HttpServletResponse.SC_NO_CONTENT);
            return null;
        }
        newPlayer = playerRepository.save(new Player(name, game));
        System.out.println(newPlayer);
        return newPlayer;
    }

    @RequestMapping(value = "/byId/{playerId}", method = RequestMethod.GET)
    public Player get(@PathVariable("playerId") Integer playerId) {
        return playerRepository.findOne(playerId);
    }

    @RequestMapping(value = "/byGame/{gameId}", method = RequestMethod.GET)
    public List<Player> getAllByGame(@PathVariable("gameId") Integer gameId) {
        return playerRepository.findAllByGameId(gameId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        playerRepository.delete(id);
    }
}
