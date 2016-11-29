package com.ooad.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Town {
	
	@Id
	private int townId;
	
	private String townName;
	
	@ManyToMany(targetEntity = Town.class)
	private List<Town> Neighbours;

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}
	
	public boolean addNeighbour(Town neighbour)
	{
		this.Neighbours.add(neighbour);
		return true;
	}

}
