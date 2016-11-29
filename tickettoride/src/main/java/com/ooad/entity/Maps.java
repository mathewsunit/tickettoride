package com.ooad.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Entity for creating a list of maps and its corresponding routes.
 * 
 * @author smathew
 *
 */
@Entity
public class Maps {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer mapId;

	private String mapType;

	private String mapCountry;

	@OneToMany(targetEntity = Route.class)
	List<Route> mapRoutes;

	@OneToMany(targetEntity = DestinationCard.class)
	List<DestinationCard> mapDestinationCards;

	public List<DestinationCard> getMap_destinationCards() {
		return mapDestinationCards;
	}

	public void setMap_destinationCards(List<DestinationCard> map_destinationCards) {
		this.mapDestinationCards = map_destinationCards;
	}

	/**
	 * @return the map_id
	 */
	public Integer getMap_id() {
		return mapId;
	}

	/**
	 * @param map_id
	 *            the map_id to set
	 */
	public void setMap_id(Integer map_id) {
		this.mapId = map_id;
	}

	/**
	 * @return the map_type
	 */
	public String getMap_type() {
		return mapType;
	}

	/**
	 * @param map_type
	 *            the map_type to set
	 */
	public void setMap_type(String map_type) {
		this.mapType = map_type;
	}

	/**
	 * @return the map_country
	 */
	public String getMap_country() {
		return mapCountry;
	}

	/**
	 * @param map_country
	 *            the map_country to set
	 */
	public void setMap_country(String map_country) {
		this.mapCountry = map_country;
	}

	/**
	 * @return the map_routes
	 */
	public List<Route> getMap_routes() {
		return mapRoutes;
	}

	/**
	 * @param map_routes
	 *            the map_routes to set
	 */
	public void setMap_routes(List<Route> map_routes) {
		this.mapRoutes = map_routes;
	}

}
