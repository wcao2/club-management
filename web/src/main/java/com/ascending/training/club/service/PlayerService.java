package com.ascending.training.club.service;

import com.ascending.training.club.model.Player;
import com.ascending.training.club.respository.PlayerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {
    @Autowired
    private PlayerDao playerDao;

    public Player save(Player player,String clubName){return playerDao.save(player,clubName);}
    public Player updateEmployeeEmail(Player player){return playerDao.updatePlayerEmail(player);}
    public List<Player> getPlayersAndClub(){return playerDao.getPlayersAndClub();}
    public Player getPlayerByName(String playerName){return playerDao.getPlayerByName(playerName);}
    public boolean deletePlayer(Long id){return playerDao.delete(id);}
    public Player getPlayerById(Long id){return playerDao.getPlayerById(id);}
}
