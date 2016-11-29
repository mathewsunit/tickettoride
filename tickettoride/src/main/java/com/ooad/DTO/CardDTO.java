package com.ooad.DTO;

import java.util.List;

import com.fasterxml.jackson.databind.type.MapType;
import com.ooad.entity.Player;
import com.ooad.enums.CardOwner;
import com.ooad.enums.MapEnumType;
import com.ooad.enums.TrainCards;

public class CardDTO {

	private int cardId;

	private TrainCards cardType;

	private CardOwner cardOwner;

	public int getCardId() {
		return cardId;
	}

	public void setCardId(int cardId) {
		this.cardId = cardId;
	}

	public TrainCards getCardType() {
		return cardType;
	}

	public void setCardType(TrainCards cardType) {
		this.cardType = cardType;
	}

	public CardOwner getCardOwner() {
		return cardOwner;
	}

	public void setCardOwner(CardOwner cardOwner) {
		this.cardOwner = cardOwner;
	}

	
}
