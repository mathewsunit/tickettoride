package com.ooad.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.ooad.enums.Colors;
/**
 * Entity to create individual routes for the map.
 * @author smathew
 *
 */
@Entity
public class Route {
	
	@Id
	private int routeId;

	private Colors routeColor;

	private int routeLength;

	@ManyToOne(targetEntity = Town.class)
	private Town source;

	@ManyToOne(targetEntity = Town.class)
	private Town destination;

	public int getRouteId() {
		return routeId;
	}

	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}

	public Colors getRouteColor() {
		return routeColor;
	}

	public void setRouteColor(Colors routeColor) {
		this.routeColor = routeColor;
	}

	public int getRouteLength() {
		return routeLength;
	}

	public void setRouteLength(int routeLength) {
		this.routeLength = routeLength;
	}

	public Town getSource() {
		return source;
	}

	public void setSource(Town source) {
		this.source = source;
	}

	public Town getDestination() {
		return destination;
	}

	public void setDestination(Town destination) {
		this.destination = destination;
	}

}

