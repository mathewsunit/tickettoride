package com.ooad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ooad.entity.Maps;
/**
 * 
 * @author smathew
 *
 */
@Repository
public interface MapRepository extends JpaRepository<Maps, Integer>  {

	Maps findByMapCountry(String mapCountry);
}
