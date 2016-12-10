package aw.bowl.main.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by dmitry on 26.11.16.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Player {
    @JsonProperty
    private Integer playerId = null;
    @JsonProperty
    private String  name   = null;
    @JsonProperty
    private Integer gameId = null;
    @JsonProperty
    private List<Integer> throwz = null;

    public Player() {}

    public Player(String name) {
        this.name = name;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public String getName() {
        return name;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public void setThrowz(List<Integer> throwz) {
        this.throwz = throwz;
    }

    public List<Integer> getThrowz() {
        return throwz;
    }
}
