package aw.bowl.score;

/**
 * Should count score for each Player,
 * the PlayerGame entity is not important - should be mapped by Bowl-service
 *
 * Created by dmitry on 26.11.16.
 */
public interface IGameScoring {

    Boolean roll(Integer gameId, Integer gameType, Integer player, Integer pins);

/*    Integer getRoundScore(Integer player);

    Integer getTotalScore();*/

    Boolean isGameEnded(Integer gameId);

    Integer getPlayerScore(Integer gameId, Integer playerId);

    Integer getWinnerPlayer(Integer gameId);
}
