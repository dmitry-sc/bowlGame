package aw.bowl.score.service;

import aw.bowl.score.IGameScoring;
import aw.bowl.score.model.Frame;
import aw.bowl.score.model.PlayerGame;
import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dmitry on 27.11.16.
 */

@EnableHystrix
@Component
public class ScoreService implements IGameScoring {
    private List<PlayerGame> playerGameList;

    @PostConstruct
    public void init() {
        playerGameList = new LinkedList<>();
    }

    public Boolean roll(Integer gameId, Integer gameType, Integer playerId, Integer pins) {
        if (isGameEnded(gameId)) {
            return false;
        }

        PlayerGame playerGame = playerGameList.stream()
                .filter((element) -> element.getPlayerId().equals(playerId) && element.getGameId().equals(gameId))
                .findFirst()
                .orElse(new PlayerGame(gameId, playerId, gameType));

        Frame aFrame = playerGame.getFrames().stream()
                .filter(Frame::isAwaitsThrowAgain)
                .findFirst()
                .orElse(new Frame(playerGame.getGameType()));

        if (playerGame.getFrames().lastIndexOf(aFrame) < 0) {
            playerGame.getFrames().add(0, aFrame);
        }

        if (playerGameList.lastIndexOf(playerGame) < 0) {
            playerGameList.add(0, playerGame);
        }

        return aFrame.addRoll(pins);
    }

    @Override
    public Integer getPlayerScore(Integer gameId, Integer playerId) {
        PlayerGame playerGame = playerGameList.stream()
                .filter((element) -> element.getPlayerId().equals(playerId) && element.getGameId().equals(gameId))
                .findFirst()
                .orElse(null);

        if (playerGame != null) {
            return playerGame.getGameScore();
        }
        return 0;
    }

    @Override
    public Boolean isGameEnded(Integer gameId) {
        Boolean maxRoundsReached = (playerGameList.stream()
                .filter((element) -> element.getGameId().equals(gameId)).count() > 0L)
                &&
                (playerGameList.stream()
                        .filter((element) -> element.getGameId().equals(gameId))
                        .allMatch((element) -> element.getFrames().size() >= element.getGameType()));

        Boolean allThrowsFinished = playerGameList.stream()
                .filter((element) -> element.getGameId().equals(gameId))
                .allMatch((element) -> element.getFrames().stream().noneMatch(Frame::isAwaitsThrowAgain));

        return maxRoundsReached && allThrowsFinished;
    }

    public List<Integer> getPlayerThrows(Integer gameId, Integer playerId) {
        PlayerGame playerGame = playerGameList.stream()
                .filter((element) -> element.getGameId().equals(gameId) && element.getPlayerId().equals(playerId))
                .findFirst()
                .orElse(null);

        if (playerGame == null) {
            return new LinkedList<>();
        }
        //Lists.reverse applied because we iterating from end to beginning
        List<Integer> throwsList = playerGame.getFrames()
                .stream()
                .flatMap(e -> Lists.reverse(e.getPins()).stream())
                .collect(Collectors.toList());
        return Lists.reverse(throwsList);
    }

    @Override
    @HystrixCommand(fallbackMethod = "defaultWinner")
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public Integer getWinnerPlayer(Integer gameId) {
        return playerGameList.stream()
                .filter((element) -> element.getGameId().equals(gameId))
                .max(Comparator.comparingInt(PlayerGame::getGameScore))
                .get()
                .getPlayerId();
    }

    public Integer defaultWinner(Integer gameId) {
        return -1;
    }

    @PreDestroy
    public void serializeData() {
        System.out.println("TODO: On shutdown event. Serializing data...");
    }
}
