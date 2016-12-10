package aw.bowl.game.repository;

import aw.bowl.game.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dmitry on 24.11.16.
 *
 * Basic functionality to work with Player entities
 * All is above is under spring data
 */
@Repository
@RepositoryRestResource
public interface PlayerRepository extends JpaRepository<Player, Integer> {

    Player findByNameIgnoreCase(String name);

    @Query("select p from Player p where p.game.id = :gameId")
    List<Player> findAllByGameId(@Param("gameId") Integer gameId);

    Player findOneByName(String name);
}
