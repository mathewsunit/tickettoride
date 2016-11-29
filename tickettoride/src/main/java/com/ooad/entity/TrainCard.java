package com.ooad.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.ooad.enums.CardOwner;
import com.ooad.enums.TrainCards;
@Entity
public class TrainCard {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cardId;
	
	public int getCardId() {
		return cardId;
	}
	public void setCardId(int cardId) {
		this.cardId = cardId;
	}

	private TrainCards cardType;
	/**
	 * @return the cardId
	 */
	
	private CardOwner cardOwner;
	
	public CardOwner getCardOwner() {
		return cardOwner;
	}
	public void setCardOwner(CardOwner cardOwner) {
		this.cardOwner = cardOwner;
	}
	public TrainCards getCardType() {
		return cardType;
	}
	public void setCardType(TrainCards cardType) {
		this.cardType = cardType;
	}
	
	public List<TrainCard> makeCopy(int num)
	{
		List<TrainCard> trainCardCopies = new ArrayList<>();
		while(num != 0)
		{
			num--;
			TrainCard copy = new TrainCard();
			copy.setCardOwner(this.cardOwner);
			copy.setCardType(this.cardType);
			trainCardCopies.add(copy);
		}
		
		return trainCardCopies;
	}
}