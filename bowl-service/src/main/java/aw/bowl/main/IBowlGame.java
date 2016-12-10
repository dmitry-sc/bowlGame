package aw.bowl.main;

import aw.bowl.main.model.Game;
import aw.bowl.main.model.Player;

import java.util.List;
import java.util.Map;

/**
 * Amm, service just a wrapper, all logic should be on appropriate services
 * this one just makes actions!
 *
 * Game-service  -- manipulates with games/players
 * Score-service -- manipulates with scoring for Player
 *
 * Created by dmitry on 26.11.16.
 */
public interface IBowlGame {

    //game related actions
    //
    Game startGame();

    boolean isGameActive(Integer gameId);

    void finishGame(Integer gameId);

    Player addPlayer(String name, Integer gameId);

    List<Player> getPlayers(Integer gameId);


    //scoring sub-subsystem
    //

    void roll(Integer gameId, Integer playerId, Integer pins); //player rolls and gets count of shot pins and flag if he can throw again

    Integer getRollScores(Integer playerId, Integer gameType);

    Integer getTotalScores(Integer playerId, Integer gameType);
}
