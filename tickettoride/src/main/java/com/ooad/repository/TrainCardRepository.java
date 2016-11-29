package com.ooad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ooad.entity.TrainCard;

@Repository
public interface TrainCardRepository extends JpaRepository<TrainCard, Integer> {

}
