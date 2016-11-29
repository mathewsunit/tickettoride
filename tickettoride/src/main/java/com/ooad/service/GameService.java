package com.ooad.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ooad.DTO.GameDTO;
import com.ooad.DTO.PlayerDTO;
import com.ooad.entity.DestinationCard;
import com.ooad.entity.Game;
import com.ooad.entity.Player;
import com.ooad.entity.Route;
import com.ooad.entity.TrainCard;
import com.ooad.enums.Colors;

public interface GameService {
		
	void playerTurn(int playerId);
	
	Route BuildRoute();
	
	void GameEnds();

	Game createNewGame(Player loggedPlayer, GameDTO gameDTO);

	List<Game> getGamesToJoin(Player loggedPlayer);

	Game joinGame(String gameId, List<PlayerDTO> playerDTOList);

	List<Game> getPlayerGames(Player loggedPlayer);

	Game getGame(Long id);

	List<Player> getPlayersList(String gameId);

	List<Colors> getColorList(String gameId);

	Game beginGame(String gameId, List<PlayerDTO> playerDTOList);

	List<TrainCard> getFaceUpCards(String gameId);

	List<DestinationCard> getDestCards(String gameId);

	List<TrainCard> pickFirstTrainCard(String gameId, TrainCard trainCard);

	Player getPlayersInfo(String gameId);

	List<TrainCard> pickSecondTrainCard(String gameId, TrainCard trainCard);
	

}
