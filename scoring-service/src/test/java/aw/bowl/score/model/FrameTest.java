package aw.bowl.score.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by dmitry on 25.11.16.
 */

public class FrameTest extends Assert {
    private Frame   throu;
    private Integer gameType = 10;

    @Before
    public void setUp() throws Exception {
        throu = new Frame(gameType);
    }

    @Test
    public void testSplit() throws Exception {
        assertTrue(throu.addRoll(gameType - 1));
        assertFalse(throu.addRoll(1));
        assertTrue(throu.isSplit());
    }

    @Test
    public void testStrike() throws Exception {
        assertFalse(throu.addRoll(gameType));
        assertTrue(throu.isStrike());
    }

    @Test
    public void testNotSplitNotStrike() throws Exception {
        assertTrue(throu.addRoll(3));
        assertFalse(throu.addRoll(3));
        assertFalse(throu.isSplit());
        assertFalse(throu.isStrike());
    }

    @Test
    public void testScore() throws Exception {
        Integer firstPin  = 3;
        Integer secondPin = 7;

        throu.addRoll(firstPin);
        throu.addRoll(secondPin);

        assertEquals(Integer.valueOf(firstPin + secondPin), throu.getScore());
    }

    @Test
    public void testOverflowIsReset() throws Exception {
        Integer firstPin  = 3;
        Integer secondPin = gameType;

        throu.addRoll(firstPin);
        throu.addRoll(secondPin);

        assertTrue(throu.isSplit());
        assertEquals(gameType, throu.getScore());
    }
}