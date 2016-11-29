package com.ooad.DTO;

import com.ooad.entity.DestinationCard;
import com.ooad.entity.Game;
import com.ooad.entity.Route;
import com.ooad.entity.TrainCard;
import com.ooad.enums.Colors;

import java.util.Date;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

/**
 * Created by smathew on 27.05.16.
 */

public class PlayerDTO {

	private Colors playerColor;

	private int playerId;

	private String playerName;

	public Colors getPlayerColor() {
		return playerColor;
	}

	public int getPlayerId() {
		return playerId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerColor(Colors playerColor) {
		this.playerColor = playerColor;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

}