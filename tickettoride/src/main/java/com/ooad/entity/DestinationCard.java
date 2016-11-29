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

@Entity
public class DestinationCard{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cardId;
	
	@ManyToOne(targetEntity = Town.class)
	private Town source;
	
	@ManyToOne(targetEntity = Town.class)
	private Town destination;
	
	private int point;

	public int getCardId() {
		return cardId;
	}

	public void setCardId(int cardId) {
		this.cardId = cardId;
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

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

}
