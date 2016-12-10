package aw.bowl.game.repository;

import aw.bowl.game.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

/**
 * Created by dmitry on 24.11.16.
 *
 * Basic functionality to work with Game entities
 * All is above is under spring data
 */
@Repository
@RepositoryRestResource
public interface GameRepository extends JpaRepository<Game, Integer> {

}
