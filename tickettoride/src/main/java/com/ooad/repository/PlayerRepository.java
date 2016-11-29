package com.ooad.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ooad.entity.Game;
import com.ooad.entity.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer>{

//	@Query("select * from player where p.playerId=playerId")
//	Player findByPlayerId(int playerId);
	
	List<Player> findByGame(Game game);
}


