package aw.bowl.game.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Persistent account entity with JPA markup. Players are stored in an H2
 * relational database.
 *
 * Created by dmitry on 24.11.16.
 */

@Entity
@JsonRootName(value = "player")
public class Player implements Serializable {
    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue
    @JsonProperty
    private int playerId;

    @JsonProperty
    @Column(unique = true, length = 15)
    private String  name;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "game_id", referencedColumnName = "gameId")
    private Game game;

    public Player(String name, Game game) {
        this.name = name;
        this.game = game;
    }

    public Player() {}
}
