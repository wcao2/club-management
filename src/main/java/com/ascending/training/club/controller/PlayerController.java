package com.ascending.training.club.controller;

import com.ascending.training.club.model.Club;
import com.ascending.training.club.model.Player;
import com.ascending.training.club.service.ClubService;
import com.ascending.training.club.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value={"/players","play"})
public class PlayerController {
//    public Player save(Player player, String clubName){return playerDao.save(player,clubName);}
//    public Player updateEmployeeEmail(Player player){return playerDao.updatePlayerEmail(player);}
//    public List<Player> getPlayersAndClub(){return playerDao.getPlayersAndClub();}
//    public Player getPlayerByName(String playerName){return playerDao.getPlayerByName(playerName);}
//    public boolean deletePlayer(Long id){return playerDao.delete(id);}
//    public Player getPlayerById(Long id){return playerDao.getPlayerById(id);}
    @Autowired
    private PlayerService playerService;
    @Autowired
    private ClubService clubService;

    //{prefix}/players POST
    @RequestMapping(value = "",method = RequestMethod.POST)
    public Player createPlayer(@RequestParam("clubName") String clubName, @RequestBody Player player){
        String msg;
        Club club= clubService.getClubByName(clubName);
        Player p=playerService.save(player, club.getName());
        return p;
    }

    //{prefix}/players/id PATCH
    @RequestMapping(value = "/{id}",method = RequestMethod.PATCH)
    public Player updatePlayerEmail(@PathVariable("id") Long id, @RequestParam("email") String email){
        Player p=playerService.getPlayerById(id);
        p.setEmail(email);
        return playerService.updateEmployeeEmail(p);
    }

    //{prefix}/players/list GET
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Player> getPlayersAndClub(){return playerService.getPlayersAndClub();}

    //{prefix}/players GET
    @RequestMapping(value = "",method = RequestMethod.GET)
    public Player getPlayerByName(@RequestParam("playerName") String playerName){
        Player player=playerService.getPlayerByName(playerName);
        return player;
    }

    //{prefix}/players/id DELETE
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public String deletePlayer(@PathVariable("id") Long id){
        String msg=null;
        boolean isSuccess=playerService.deletePlayer(id);
        if(isSuccess){
            msg="the player is successfully deleted";
        }else{
            msg="failure to delete player";
        }
        return msg;
    }

    //{prefix}/players/id GET
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Player getPlayerById(@PathVariable("id") Long Id){
        Player player=playerService.getPlayerById(Id);
        return player;
    }

}
