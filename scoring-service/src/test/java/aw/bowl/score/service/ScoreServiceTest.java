package aw.bowl.score.service;

import aw.bowl.score.ScoringServiceApplicationTests;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by dmitry on 06.12.16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ScoringServiceApplicationTests.class,
        loader=AnnotationConfigContextLoader.class)
@EnableCircuitBreaker
public class ScoreServiceTest extends ScoreService {
    private Integer    gameId   = 1;
    private Integer    gameType = 4;
    private Integer    playerId = 1;

    @Test
    public void testRollAndScoreAndGameFinished() {
        roll(gameId, gameType, playerId, 1);
        roll(gameId, gameType, playerId, 2);
        assertEquals(Integer.valueOf(3), getPlayerScore(gameId, playerId));

        roll(gameId, gameType, playerId, 0);
        roll(gameId, gameType, playerId, 1);

        roll(gameId, gameType, playerId, 1);
        roll(gameId, gameType, playerId, 1);

        roll(gameId, gameType, playerId, 0);
        roll(gameId, gameType, playerId, 1);
        assertEquals(Integer.valueOf(7), getPlayerScore(gameId, playerId));
        assertTrue(isGameEnded(gameId));
    }
}