package com.ooad.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Synchronize;

import com.ooad.enums.GameStatusType;

@Entity
public class Game {
	private Date createdTime;
	
	private Date finishTime;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int gameId;

	private GameStatusType gameState;

	@OneToOne(targetEntity = Maps.class)
	private Maps map;

	private HashMap<Integer, Route> UsedRoutes;

	@OneToMany(targetEntity = TrainCard.class)
	private List<TrainCard> trainCardDeck;
	
	@OneToMany(targetEntity = TrainCard.class)
	private List<TrainCard> trainCardFaceUp;
	

	public List<TrainCard> getTrainCardFaceUp() {
		return trainCardFaceUp;
	}

	public void setTrainCardFaceUp(List<TrainCard> trainCardFaceUp) {
		this.trainCardFaceUp = trainCardFaceUp;
	}

	public List<TrainCard> getTrainCardDeck() {
		return trainCardDeck;
	}

	public void setTrainCardDeck(List<TrainCard> trainCardDeck) {
		this.trainCardDeck = trainCardDeck;
	}

	/**
	 * @return the createdTime
	 */
	public Date getCreatedTime() {
		return createdTime;
	}

	/**
	 * @return the finishTime
	 */
	public Date getFinishTime() {
		return finishTime;
	}

	/**
	 * @return the gameId
	 */
	public int getGameId() {
		return gameId;
	}

	public GameStatusType getGameState() {
		return gameState;
	}

	public Maps getMap() {
		return map;
	}

	public HashMap<Integer, Route> getUsedRoutes() {
		return UsedRoutes;
	}

	/**
	 * @param createdTime
	 *            the createdTime to set
	 */
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	/**
	 * @param finishTime
	 *            the finishTime to set
	 */
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	/**
	 * @param gameId
	 *            the gameId to set
	 */
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public void setGameState(GameStatusType gameState) {
		this.gameState = gameState;
	}

	public void setMap(Maps map) {
		this.map = map;
	}

	public void setUsedRoutes(HashMap<Integer, Route> usedRoutes) {
		UsedRoutes = usedRoutes;
	}

	public void setSingleTrainCard(TrainCard pickCardFromDeck) {
		// TODO Auto-generated method stub
		this.trainCardFaceUp.add(pickCardFromDeck);
	}

	public TrainCard removeSingleTrainCard(TrainCard pickCardFromDeck) {
		// TODO Auto-generated method stub
		TrainCard cardFound = new TrainCard();
		for(TrainCard tCard:this.trainCardFaceUp)
		{
			if(tCard.getCardId()==pickCardFromDeck.getCardId())
			{
				cardFound = tCard;
				break;
			}
		}
		this.trainCardFaceUp.remove(cardFound);
		return cardFound;
	}
}
