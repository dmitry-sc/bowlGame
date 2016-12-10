package aw.bowl.game.controller;

import aw.bowl.game.model.Game;
import aw.bowl.game.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * We can use default CRUD instead, but this is simpler for testing
 *
 * Created by dmitry on 26.11.16.
 */

@RestController
@RequestMapping("/game")
public class GameController {
    @Autowired
    GameRepository gameRepository;

    @RequestMapping(method = RequestMethod.PUT)
    public Game put() {
        Game newGame = gameRepository.save(new Game());
        System.out.println(newGame);
        return newGame;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Game get(@PathVariable("id") Integer id) {
        return gameRepository.findOne(id);
    }
    @RequestMapping(method = RequestMethod.GET)
    public List<Game> getAll() {
        return gameRepository.findAll();
    }
}
