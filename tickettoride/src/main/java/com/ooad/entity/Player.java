package com.ooad.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.ooad.enums.Colors;

@Entity
public class Player {

	private Date createdDate;

	
	@OneToMany(targetEntity=DestinationCard.class)
	private List<DestinationCard> destCard;
	
	@ManyToOne(targetEntity = Game.class)
	private Game game;

	private boolean isPlaying;
	
	private Colors playerColor;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	private Integer playerId;

	private String playerName;

//	@JoinColumn(name="hs_GameID") 
//	private GameStats hsGameID;

	@OneToMany(targetEntity = Route.class)
	private List<Route> routes;

	private int score;

	@OneToMany(targetEntity=TrainCard.class)
	private List<TrainCard> trainCards;

	private int trainCars;

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @return the destCard
	 */
	public List<DestinationCard> getDestCard() {
		return destCard;
	}

	public Game getGame() {
		return game;
	}

	/**
	 * @return the playerColor
	 */
	public Colors getPlayerColor() {
		return playerColor;
	}
	
	/**
	 * @return the playerId
	 */
	public Integer getPlayerId() {
		return playerId;
	}
	
	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}

	public List<Route> getRoutes() {
		return routes;
	}

	public int getScore() {
		return score;
	}

	/**
	 * @return the trainCards
	 */
	public List<TrainCard> getTrainCards() {
		return trainCards;
	}

	public int getTrainCars() {
		return trainCars;
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the hsGameID
	 */
//	public GameStats getHsGameID() {
//		return hsGameID;
//	}
//
//	/**
//	 * @param hsGameID
//	 *            the hsGameID to set
//	 */
//	public void setHsGameID(GameStats hsGameID) {
//		this.hsGameID = hsGameID;
//	}

	/**
	 * @param destCard
	 *            the destCard to set
	 */
	public void setDestCard(List<DestinationCard> destCard) {
		this.destCard = destCard;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	/**
	 * @return the handCards
	 */


	/**
	 * @param playerColor
	 *            the playerColor to set
	 */
	public void setPlayerColor(Colors playerColor) {
		this.playerColor = playerColor;
	}

	/**
	 * @param playerId
	 *            the playerId to set
	 */
	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}

	/**
	 * @param playerName
	 *            the playerName to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}

	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}

	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * @param trainCards
	 *            the trainCards to set
	 */
	public void setTrainCards(List<TrainCard> trainCards) {
		this.trainCards = trainCards;
	}
	
	public void setSingleTrainCard(TrainCard trainCard) {
		this.trainCards.add(trainCard);
	}

	public void setTrainCars(int trainCars) {
		this.trainCars = trainCars;
	}

}
