package com.ascending.training.club.respository;

import com.ascending.training.club.model.Player;

import java.util.List;

public interface PlayerDao {
    Player save(Player player, String clubName);
    Player updatePlayerEmail(Player player);

    List<Player> getPlayersAndClub();
    Player getPlayerById(Long Id);
    Player getPlayerByName(String playerName);

    boolean delete(Long id);
}
