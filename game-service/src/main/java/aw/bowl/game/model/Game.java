package aw.bowl.game.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Persistent account entity with JPA markup. Games are stored in an H2
 * relational database.
 *
 * Created by dmitry on 24.11.16.
 */

@Entity
@JsonRootName(value = "game")
public class Game implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @JsonProperty
    private int gameId;

    @JsonProperty
    private int type = 10;

/*    @JsonIgnore
    private boolean isActive = true;*/

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "game", orphanRemoval = true, targetEntity = Player.class)
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty
    @ElementCollection
    private List<Player> players;

    public Game() {}
}
