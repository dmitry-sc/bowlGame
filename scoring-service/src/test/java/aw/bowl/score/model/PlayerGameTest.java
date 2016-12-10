package aw.bowl.score.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by dmitry on 02.12.16.
 */

public class PlayerGameTest extends Assert {
    private PlayerGame playerGame;
    private Integer    gameId   = 1;
    private Integer    gameType = 10;
    private Integer    playerId = 1;

    @Test
    public void testGetGameScore() throws Exception {
        playerGame = new PlayerGame(gameId, playerId, gameType);

        //1.
        Frame frame = new Frame(gameType);
        frame.addRoll(2);
        frame.addRoll(2);
        //score = 2 + 2
        playerGame.getFrames().add(frame);
        assertEquals(Integer.valueOf(4), playerGame.getGameScore());
        //2
        frame = new Frame(gameType);
        rollStrike(frame);
        //score = 4 + 10
        playerGame.getFrames().add(frame);
        assertEquals(Integer.valueOf(14), playerGame.getGameScore());
        //3
        frame = new Frame(gameType);
        rollSpare(frame);
        //score = 14 + (5 + 5 + bonus(5x2))
        playerGame.getFrames().add(frame);
        assertEquals(Integer.valueOf(34), playerGame.getGameScore());
        //4
        frame = new Frame(gameType);
        frame.addRoll(3);
        frame.addRoll(4);
        //score = 34 + (3x2 + 4)
        playerGame.getFrames().add(frame);
        assertEquals(Integer.valueOf(44), playerGame.getGameScore());
        //5
        frame = new Frame(gameType);
        rollSpare(frame);
        //score = 44 + 10
        playerGame.getFrames().add(frame);
        assertEquals(Integer.valueOf(54), playerGame.getGameScore());
        //6
        frame = new Frame(gameType);
        rollSpare(frame);
        //score = 54 + bonus(5)+ 10
        playerGame.getFrames().add(frame);
        assertEquals(Integer.valueOf(69), playerGame.getGameScore());
        //7
        frame = new Frame(gameType);
        rollSpare(frame);
        //score = 69 + bonus(5) + 10
        playerGame.getFrames().add(frame);
        assertEquals(Integer.valueOf(84), playerGame.getGameScore());
        //8
        frame = new Frame(gameType);
        rollStrike(frame);
        //score = 84 + bonus(10) + 10
        playerGame.getFrames().add(frame);
        assertEquals(Integer.valueOf(104), playerGame.getGameScore());
        //9
        frame = new Frame(gameType);
        rollStrike(frame);
        //score = 104 + bonus(10) + 10
        playerGame.getFrames().add(frame);
        assertEquals(Integer.valueOf(124), playerGame.getGameScore());
        //10
        frame = new Frame(gameType);
        rollStrike(frame);
        //score = 124 + bonus(10) + bonus(10) + 10
        playerGame.getFrames().add(frame);

        assertEquals(gameType, Integer.valueOf(playerGame.getFrames().size()));
        assertEquals(Integer.valueOf(154), playerGame.getGameScore());
    }

    private void rollSpare(Frame frame) {
        frame.addRoll(5);
        frame.addRoll(5);
    }

    private void rollStrike(Frame frame) {
        frame.addRoll(gameType);
    }
}