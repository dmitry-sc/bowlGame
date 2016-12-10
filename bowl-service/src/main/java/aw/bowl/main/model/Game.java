package aw.bowl.main.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by dmitry on 26.11.16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Game {
    private Integer gameId;
    private Integer type;
    private boolean isActive;

    public Integer getGameId() {
        return gameId;
    }

    public Integer getType() {
        return type;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
