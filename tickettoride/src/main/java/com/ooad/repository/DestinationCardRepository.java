package com.ooad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ooad.entity.DestinationCard;
import com.ooad.entity.Player;

@Repository
public interface DestinationCardRepository extends JpaRepository<DestinationCard, Integer> {

//	@Query("select * from destinationCard where p.cardId=cardId")
//	DestinationCard findByDestinationCardId(int cardId);

}
