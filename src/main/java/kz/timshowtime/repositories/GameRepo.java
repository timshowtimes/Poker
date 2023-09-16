package kz.timshowtime.repositories;

import kz.timshowtime.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepo extends JpaRepository<Game, Integer> {
}
