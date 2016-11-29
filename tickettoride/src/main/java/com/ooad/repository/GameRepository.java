package com.ooad.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ooad.entity.Game;
import com.ooad.entity.Maps;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

	Game findByCreatedTime(Date time);

	Game findByGameId(int gameId);

}
