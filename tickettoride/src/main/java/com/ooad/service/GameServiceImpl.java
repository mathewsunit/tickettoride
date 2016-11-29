package com.ooad.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.ooad.DTO.GameDTO;
import com.ooad.DTO.PlayerDTO;
import com.ooad.entity.DestinationCard;
import com.ooad.entity.Game;
import com.ooad.entity.Maps;
import com.ooad.entity.Player;
import com.ooad.entity.Route;
import com.ooad.entity.TrainCard;
import com.ooad.enums.CardOwner;
import com.ooad.enums.Colors;
import com.ooad.enums.TrainCards;
import com.ooad.repository.GameRepository;
import com.ooad.repository.MapRepository;
import com.ooad.repository.PlayerRepository;
import com.ooad.repository.RouteRepository;
import com.ooad.repository.TownRepository;
import com.ooad.repository.TrainCardRepository;

@Service
public class GameServiceImpl implements GameService {

	@Autowired
	private GameRepository gameRepo;

	@Autowired
	private MapRepository mapRepo;

	@Autowired
	private PlayerRepository playerRepo;

	private Random randomGenerator;

	@Autowired
	private RouteRepository routeRepo;

	@Autowired
	private TownRepository townRepo;;

	@Autowired
	private TrainCardRepository trainCardRepo;

	@Override
	public Game beginGame(String gameId, List<PlayerDTO> playerDTOList) {
		// TODO Auto-generated method stub
		//Find game
		Game game = gameRepo.getOne(Integer.parseInt(gameId));

		//Assign 4 train Cards to each player and Save Player Info
		for(PlayerDTO player:playerDTOList)
		{
			//add code for exception
			Player playerFound = playerRepo.findOne(player.getPlayerId());
			playerFound.setPlayerName(player.getPlayerName());
			playerFound.setPlayerColor(player.getPlayerColor());
			for(int i =0;i<4;i++)
			{
				playerFound.setSingleTrainCard(pickCardFromDeck(game));
			}
			playerFound.setPlaying(true);
			playerRepo.save(playerFound);
		}

		//Assign 4 train Cards to face Up
		for(int i =0;i<4;i++)
		{
			game.setSingleTrainCard(pickCardFromDeck(game));
		}
		gameRepo.save(game);

		return game;
	}


	public Route BuildRoute() {

		{
			/*- check for eligibility
			{
				-Check route-length train cars
				-check train cards of route color
				-check if route free
			}
			//should we make these also one call
			Post turn logic:
			{	
				- Subtract train cars from player(CheckForEndGame)
				- Subtract train cards from user
				-  updateRouteOwner
				- calculate points
			}*/
		}
		return null;
	}


	private boolean checkEndGame() {

		return false;
	}

	@Override
	public Game createNewGame(Player loggedPlayer, GameDTO gameDTO) {
		// TODO Auto-generated method stub
		Game game = new Game();
		Date now = new Date();
		game.setCreatedTime(now);

		//find maps user selected and associate to game instance
		Maps map = mapRepo.findByMapCountry(gameDTO.getMapType());
		game.setMap(map);

		//build town connections based on routes in map
		List<Route> gameRoutes = map.getMap_routes();
		for(Route route:gameRoutes)
		{
			//Create neighbours for towns
			createTownNeighBours(route);
		}

		//create the trainCard Deck
		List<TrainCard> trainCards = createTrainCards(CardOwner.GAME);
		game.setTrainCardDeck(trainCards);

		//		game.setCreatedTime(createdTime);

		gameRepo.save(game);
		int numPlayers = gameDTO.getNumPlayers();
		List<Player> playerList = new ArrayList<>();
		for(int num=0;num<numPlayers;num++)
		{
			Player player = new Player();
			player.setCreatedDate(now);
			//			player.setPlayerId((int) (now.getTime()/1000)+num);
			player.setGame(game);
			player.setTrainCars(48);
			playerList.add(player);
			playerRepo.save(player);
		}

//		game = gameRepo.findByCreatedTime(now);
		return game;
	}

	private void createTownNeighBours(Route route) {
		// TODO Auto-generated method stub
		route.getSource().addNeighbour(route.getDestination());
		route.getDestination().addNeighbour(route.getSource());
		townRepo.save(route.getDestination());
		townRepo.save(route.getSource());
	}

	//	private List<TrainCard> createFaceCardList(Game game) {
	//		// TODO Auto-generated method stub
	//		int index;
	//		//List<TrainCard> tCards = new ArrayList<TrainCard>();
	//		for(int i=0;i<5;i++)
	//		{
	//			index = randomGenerator.nextInt(game.getDeckTrainCards().size());
	//			TrainCard tCard = game.getDeckTrainCards().get(index);
	//			game.getDeckTrainCards().remove(index);
	//			game.getFaceUpCards().add(tCard);
	//		}
	//		return game.getFaceUpCards();
	//	}
	private List<TrainCard> createTrainCards(CardOwner cardOwners) {
		// TODO Auto-generated method stub
		List<TrainCard> tCards = new ArrayList<TrainCard>();
		TrainCard tcard = new TrainCard();

		if(cardOwners == CardOwner.GAME)
		{//use enum instead of array
			for(TrainCards trainCards:TrainCards.values())
			{
				tcard = new TrainCard();
				if(!trainCards.toString().contains("JOKER"))
				{  
					//12 Train cards for each colour
					tcard.setCardType(trainCards);
					//System.out.println(trainCards.toString());
					tcard.setCardOwner(CardOwner.GAME);
					tCards.addAll(tcard.makeCopy(12));
				}
				else
				{
					tcard.setCardType(trainCards);
					//System.out.println(trainCards.toString());
					tcard.setCardOwner(CardOwner.GAME);
					tCards.addAll(tcard.makeCopy(14));
				}
			}
		}
		trainCardRepo.save(tCards);
		return tCards;
	}


	public void GameEnds() {


	}

	@Override
	public List<Colors> getColorList(String gameId) {
		// TODO Auto-generated method stub
		return Arrays.asList(Colors.values());
	}

	@Override
	public List<DestinationCard> getDestCards(String gameId) {
		// TODO Auto-generated method stub
		//Find players and return all the destination cards in map not used by players
		Game game = gameRepo.getOne(Integer.parseInt(gameId));
		//System.out.println("Game Id Int :"+Integer.parseInt(gameId));
		List<DestinationCard> destCardList = new ArrayList<>();

		//Get list of all players
		List<Player> playerList = playerRepo.findByGame(game);

		//get all the cards in the current map
		for(DestinationCard dCard:game.getMap().getMap_destinationCards())
		{
			destCardList.add(dCard);
		}

		//remove all the dcards that are assigned to players
		for(Player player:playerList)
		{
			destCardList.removeAll(player.getDestCard());
		}

		System.out.println("destCardList :"+destCardList.size());
		return destCardList;
	}



	@Override
	public List<TrainCard> getFaceUpCards(String gameId) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Game game = gameRepo.getOne(Integer.parseInt(gameId));
		//System.out.println("Game Id Int :"+Integer.parseInt(gameId));
		List<TrainCard> trainCardList = game.getTrainCardFaceUp();
		//System.out.println("trainCardList :"+trainCardList.size());
		return trainCardList;
	}

	@Override
	public Game getGame(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Game> getGamesToJoin(Player loggedPlayer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Game> getPlayerGames(Player loggedPlayer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player getPlayersInfo(String gameId) {
		// TODO Auto-generated method stub
		Game game = gameRepo.getOne(Integer.parseInt(gameId));
		//System.out.println("Game Id Int :"+Integer.parseInt(gameId));
		List<Player> playerList = playerRepo.findByGame(game);
		//System.out.println("Player List :"+playerList.size());
		Player playerFound = null;
		for(Player p:playerList)
		{
			if(p.isPlaying())
			{
				playerFound = p;
			}
		}
		return playerFound;
	}

	@Override
	public List<Player> getPlayersList(String gameId) {
		// TODO Auto-generated method stub
		Game game = gameRepo.getOne(Integer.parseInt(gameId));
		//System.out.println("Game Id Int :"+Integer.parseInt(gameId));
		List<Player> playerList = playerRepo.findByGame(game);
		//System.out.println("Player List :"+playerList.size());
		return playerList;
	}

	public void incrementTurn(List<Player> playerList)
	{
		int playerFoundIndex = 0;
		for(Player player:playerList)
		{
			if(player.isPlaying())
			{
				player.setPlaying(false);
				playerFoundIndex = playerList.indexOf(player);
			}
		}

		if(playerFoundIndex==playerList.size()-1)
			playerList.get(0).setPlaying(true);
		else
			playerList.get(playerFoundIndex+1).setPlaying(true);
	}

	private List<DestinationCard> initialDestCards(Game game) {
		// TODO Auto-generated method stub
		int index;
		List<DestinationCard> dCards = new ArrayList<DestinationCard>();
		for(int i=0;i<3;i++)
		{
			//			index = randomGenerator.nextInt(game.getDeckDestinationCards().size());
			//			DestinationCard dCard = game.getDeckDestinationCards().get(index);
			//			game.getDeckDestinationCards().remove(index);
			//			dCards.add(dCard);
		}
		return dCards;
	}

	private List<TrainCard> initialTrainCards(Game game) {
		// TODO Auto-generated method stub
		int index;
		List<TrainCard> tCards = new ArrayList<TrainCard>();
		for(int i=0;i<5;i++)
		{
			//			index = randomGenerator.nextInt(game.getDeckTrainCards().size());
			//			TrainCard tCard = game.getDeckTrainCards().get(index);
			//			game.getDeckTrainCards().remove(index);
			//			tCards.add(tCard);
		}
		return tCards;
	}

	@Override
	public Game joinGame(String gameId, List<PlayerDTO> playerDTOList) {
		// TODO Auto-generated method stub
		return null;
	}

	private void keepCard() {

	}

	public TrainCard pickCardFromDeck(Game game) {
		List<TrainCard> trainCards = game.getTrainCardDeck();
		List<TrainCard> freeDeck = new ArrayList<>();

		for(TrainCard trainCard:trainCards)
		{
			if(trainCard.getCardOwner()==CardOwner.GAME)
			{
				freeDeck.add(trainCard);
			}
		}

		//if there are no more cards in the game, convert all the heap cards to game
		if(freeDeck.size()==0)
		{
			for(TrainCard trainCard:trainCards)
			{
				if(trainCard.getCardOwner()==CardOwner.HEAP)
				{
					trainCard.setCardOwner(CardOwner.GAME);
					freeDeck.add(trainCard);
				}
			}

		}

		//System.out.println(freeDeck.size());

		TrainCard pickedTrainCard = freeDeck.get((int )(Math.random() * freeDeck.size()));
		pickedTrainCard.setCardOwner(CardOwner.PLAYER);
		return pickedTrainCard;

	}

	public void pickCardFromFaceUp() {

	}


	//One of These three methods are going to flow out users whole one turn..
	//UI will send u 3 IDs
	public void pickDestinationCard() {
		// TODO Auto-generated method stub
		int index;
		List<DestinationCard> dCards = new ArrayList<DestinationCard>();
		for(int i=0;i<3;i++)
		{
			//index = randomGenerator.nextInt(game.getDeckDestinationCards().size());
			//DestinationCard dCard = game.getDeckDestinationCards().get(index);
			//dCards.add(dCard);
		}
		//show these cards to user on some interface and make him select two...
		//suppose he selects cards with these two Ids
		int id1,id2;
		//remove cards with these Ids from Destination Deck and add to Players Dest deck.
	}


	@Override
	public List<TrainCard> pickFirstTrainCard(String gameId, TrainCard trainCard) {
		// TODO Auto-generated method stub
		Game game = gameRepo.getOne(Integer.parseInt(gameId));
		System.out.println("Game Id Int :"+Integer.parseInt(gameId));

		List<Player> playerList = playerRepo.findByGame(game);
		Player currentPlayer = new Player();
		for(Player player:playerList)
		{
			if(player.isPlaying())
			{
				currentPlayer = player;
			}
		}

		TrainCard tCard = game.removeSingleTrainCard(trainCard);;
		currentPlayer.setSingleTrainCard(tCard);
		tCard.setCardOwner(CardOwner.PLAYER);
		trainCardRepo.save(trainCard);
		playerRepo.save(currentPlayer);

		game.setSingleTrainCard(pickCardFromDeck(game));

		gameRepo.save(game);

		List<TrainCard> trainCardList = game.getTrainCardFaceUp();
		System.out.println("trainCardList :"+trainCardList.size());
		return trainCardList;
	}


	@Override
	public List<TrainCard> pickSecondTrainCard(String gameId, TrainCard trainCard) {
		// TODO Auto-generated method stub
		Game game = gameRepo.getOne(Integer.parseInt(gameId));
		System.out.println("Game Id Int :"+Integer.parseInt(gameId));

		List<Player> playerList = playerRepo.findByGame(game);
		Player currentPlayer = new Player();
		for(Player player:playerList)
		{
			if(player.isPlaying())
			{
				currentPlayer = player;
			}
		}

		TrainCard tCard = game.removeSingleTrainCard(trainCard);;
		currentPlayer.setSingleTrainCard(tCard);
		tCard.setCardOwner(CardOwner.PLAYER);
		trainCardRepo.save(trainCard);
		playerRepo.save(currentPlayer);

		game.setSingleTrainCard(pickCardFromDeck(game));

		gameRepo.save(game);

		List<TrainCard> trainCardList = game.getTrainCardFaceUp();
		System.out.println("trainCardList :"+trainCardList.size());
		return trainCardList;
	}


	public void pickTrainCard(){

	}

	@Override
	public void playerTurn(int playerId) {
		// TODO Auto-generated method stub

	}

	public void playerTurn(int playerId, int type,Game game) {

		if(type ==  1){
			//Destination cards
			//Pick three destination card...
			int index;
			List<DestinationCard> dCards = new ArrayList<DestinationCard>();
			for(int i=0;i<3;i++)
			{
				//				index = randomGenerator.nextInt(game.getDeckDestinationCards().size());
				//				DestinationCard dCard = game.getDeckDestinationCards().get(index);
				//				game.getDeckDestinationCards().remove(index);
				//				dCards.add(dCard);

			}


		}

		//		newGame.setNewGame(false);
		//		newGame.setPlaying(true);
		//		
		//		pickDestinationCard();
		//		
		//		int turn =2;
		//		
		//		while(turn>0){
		//			try {
		//				wait();
		//			} catch (InterruptedException e) {
		//				// TODO Auto-generated catch block
		//				e.printStackTrace();
		//			}	
		//		}
		//		
		//		if(checkEndGame()){
		//			newGame.setPlaying(false);
		//			newGame.setEnded(true);
		//			GameEnds();
		//			
		//		}
		//		
		//	
		//		/*pickDestCard();
		//		- Pick destination card{
		//			KeepCard
		//			ReturnCard
		//		}
		//
		//	- Pick train card
		//		- Pick train card from deck{
		//				turn =2;
		//		while(turn){
		//			case1:pick	Card	from	Deck
		//			if(joker){
		//				turn 0;
		//			}
		//			else {
		//				turn- - 
		//			}
		//			case2: pick Card from face
		//			if(joker){
		//				turn 0;
		//			}
		//			else {
		//				turn- - 
		//			}
		//		}*/
		//
		//		
		//		
		//		BuildRoute();

	}

}
