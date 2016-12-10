package aw.bowl.score.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dmitry on 02.12.16.
 */


public class PlayerGame implements Serializable {
    @JsonProperty
    private Integer gameId;
    @JsonProperty
    private Integer gameType;
    @JsonProperty
    private Integer playerId;
    @JsonIgnore
    private List<Frame> frames = new LinkedList<>();

    public PlayerGame(Integer gameId, Integer playerId, Integer gameType) {
        this.gameId   = gameId;
        this.playerId = playerId;
        this.gameType = gameType;
    }

    public Integer getGameId() {
        return gameId;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public List<Frame> getFrames() {
        return frames;
    }

    public Integer getGameType() {
        return gameType;
    }

    public Integer getGameScore() {
        Integer score = 0;
        for (Integer frameIndex = 0; frameIndex < frames.size(); frameIndex++) {
            Frame frame = frames.get(frameIndex);
            score += frame.getScore();
            if (frame.isSplit()) {
                score += applyBonus(1, frameIndex + 1);
            }
            if (frame.isStrike()) {
                score += applyBonus(2, frameIndex + 1);
            }
        }
        return score;
    }

    /**
     * <b>Strike:</b> When all ten pins are knocked down with the first ball
     * (called a strike and typically rendered as an "X" on a scoresheet),
     * a player is awarded ten points, plus a bonus of whatever is scored with the next two balls
     *
     * <b>Spare:</b> A "spare" is awarded when no pins are left standing after the second ball of a frame;
     * i.e., a player uses both balls of a frame to clear all ten pins.
     * A player achieving a spare is awarded ten points, plus a bonus of whatever is scored with the next ball (only the first ball is counted).
     * It is typically rendered as a slash on scoresheets in place of the second pin count for a frame.
     *
     * @param rolls
     * @param start
     * @return
     */
    private Integer applyBonus(Integer rolls, Integer start) {
        Integer bonus = 0;
        if (start >= 0 && start <= gameType - 1 && start < frames.size()) {
            List<Integer> frame  = frames.get(start).getPins();
            for (Integer frameRoll : frame) {
                bonus += frameRoll;
                rolls--;
                if (rolls == 0) {
                    break;
                }
            }
            if (rolls > 0) {
                bonus += applyBonus(rolls, start + 1);
            }
        }
        return bonus;
    }
}
