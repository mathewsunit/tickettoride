package com.ooad.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ooad.entity.Game;
import com.ooad.entity.Maps;
import com.ooad.entity.Town;

@Repository
public interface TownRepository extends JpaRepository<Town, Integer> {

}
