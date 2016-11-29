package com.ooad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ooad.DTO.PlayerDTO;
import com.ooad.entity.Player;
import com.ooad.entity.Player;
import com.ooad.repository.PlayerRepository;

import java.util.List;

/**
 * Created by pdybka on 16.05.16.
 */
@Service
@Transactional
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player createNewPlayer(PlayerDTO playerDTO) {
        Player newPlayer = new Player();
        newPlayer.setPlayerName(playerDTO.getPlayerName());
        newPlayer.setPlayerId(1);
        //To be filled
        //newPlayer.setCreatedDate();
        playerRepository.save(newPlayer);
        return newPlayer;
    }

    public Player getLoggedPlayer() {
        //To be filled
    	//ContextPlayer principal = (ContextPlayer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //return playerRepository.findOneByPlayerName(principal.getPlayer().getPlayerName());
    	return null;
    }

    public List<Player> listPlayers() {
        List<Player> players = (List<Player>) playerRepository.findAll();
        return players;
    }


}
