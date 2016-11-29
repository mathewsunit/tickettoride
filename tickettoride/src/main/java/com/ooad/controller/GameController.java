package com.ooad.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ooad.DTO.GameDTO;
import com.ooad.DTO.PlayerDTO;
import com.ooad.entity.DestinationCard;
import com.ooad.entity.Game;
import com.ooad.entity.Maps;
import com.ooad.entity.Player;
import com.ooad.entity.TrainCard;
import com.ooad.enums.Colors;
import com.ooad.repository.DestinationCardRepository;
import com.ooad.repository.MapRepository;
import com.ooad.repository.PlayerRepository;
import com.ooad.repository.RouteRepository;
import com.ooad.service.GameService;
import com.ooad.service.PlayerService;
import com.sun.net.httpserver.Authenticator.Success;

/**
 * 
 * @author smathew
 *
 */
@RestController
@RequestMapping("game")
public class GameController {

    @Autowired
    GameService gameService;
    
    @Autowired
    PlayerService playerService;

    @Autowired
    HttpSession httpSession;
    
    @RequestMapping(value ="/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public Game createNewGame(@RequestBody GameDTO gameDTO) {

    	System.out.println("GameDTO: " + gameDTO.getNumPlayers() );
    	System.out.println("GameDTO: " + gameDTO.getMapType());
    	
        Game game = gameService.createNewGame(playerService.getLoggedPlayer(), gameDTO);
        httpSession.setAttribute("gameId", game.getGameId());

        System.out.println("new game id: " + httpSession.getAttribute("gameId")+ " stored in session" );

        return game;
    }

    @RequestMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Game> getGamesToJoin() {
        return gameService.getGamesToJoin(playerService.getLoggedPlayer());
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public Game joinGame(@RequestBody List<PlayerDTO> playerDTOList) {
        Game game = gameService.joinGame(httpSession.getAttribute("gameId").toString(), playerDTOList);
        return game;
    }
    
    @RequestMapping(value = "/pickfirsttraincard", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TrainCard> pickFirstTrainCard(@RequestBody TrainCard trainCard) {
    	List<TrainCard> trainCardsList = gameService.pickFirstTrainCard(httpSession.getAttribute("gameId").toString(),trainCard);
    	return trainCardsList;
    }
    
    @RequestMapping(value = "/picksecondtraincard", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TrainCard> pickSecondTrainCard(@RequestBody TrainCard trainCard) {
    	List<TrainCard> trainCardsList = gameService.pickSecondTrainCard(httpSession.getAttribute("gameId").toString(),trainCard);
    	return trainCardsList;
    }
    
    @RequestMapping(value = "/begin", method = RequestMethod.POST)
    public Game beginGame(@RequestBody List<PlayerDTO> playerDTOList) {
        Game game = gameService.beginGame(httpSession.getAttribute("gameId").toString(), playerDTOList);
        return game;
    }
    
    @RequestMapping(value = "/players/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Player> getPlayerGames() {
    	List<Player> playerList = gameService.getPlayersList(httpSession.getAttribute("gameId").toString());
    	return playerList;
    }
    
    @RequestMapping(value = "/playerInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public Player getPlayerInfo() {
    	Player player = gameService.getPlayersInfo(httpSession.getAttribute("gameId").toString());
    	return player;
    }
    
    @RequestMapping(value = "/faceUp/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TrainCard> getFaceUpCards() {
    	List<TrainCard> trainCardsList = gameService.getFaceUpCards(httpSession.getAttribute("gameId").toString());
    	System.out.println("trainCardsList "+trainCardsList.size());
    	return trainCardsList;
    }
    
    @RequestMapping(value = "/destCards/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DestinationCard> getDestCards() {
    	List<DestinationCard> destCardsList = gameService.getDestCards(httpSession.getAttribute("gameId").toString());
    	return destCardsList;
    }
    
    @RequestMapping(value = "/colors/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Colors> getPlayerColors() {
    	List<Colors> colors = gameService.getColorList(httpSession.getAttribute("gameId").toString());
    	return colors;
    }
    
    @RequestMapping(value = "/player/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPlayerList() {
    	return "KILL THEM ALL";
    }
    
    @RequestMapping(value = "/{id}")
    public Game getGameProperties(@PathVariable Long id) {

        httpSession.setAttribute("gameId", id);

        return gameService.getGame(id);
    }

}