package com.bol.kalaha.repository;

import java.util.UUID;

import com.bol.kalaha.model.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<Game, UUID> {
}
