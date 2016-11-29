package com.ooad.DTO;

import java.util.List;

import com.fasterxml.jackson.databind.type.MapType;
import com.ooad.entity.Player;
import com.ooad.enums.MapEnumType;

public class GameDTO {
	
	private String mapType;
	private int numPlayers;
	
    public int getNumPlayers() {
		return numPlayers;
	}
	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}
	
	public String getMapType() {
		return mapType;
	}
	public void setMapType(String mapType) {
		this.mapType = mapType;
	}
    
}
