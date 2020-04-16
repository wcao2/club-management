package com.ascending.training.club.controller;

import com.ascending.training.club.model.Club;
import com.ascending.training.club.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value={"/clubs"})
public class ClubController {
    @Autowired
    private ClubService clubService;
//    public Club save(Club club){return clubDao.save(club);}
//    public Club getClubById(Long id){return clubDao.getClubById(id);}
//    public Club getClubByNae(String clubName){return clubDao.getClubByName(clubName);}
//    public List<Club> getClubsEager(){return clubDao.getClubsEager();}
//    public boolean deleteDepartment(Long id){return clubDao.delete(id);}
//    public boolean updateDesc(Club club){return clubDao.updateDesc(club);}

    //{prefix}/clubs POST
    @RequestMapping(value="",method = RequestMethod.POST)
    public Club createClub(@RequestBody Club club){
        String msg;
        Club c=clubService.save(club);
        if(c==null) msg="The club was not created";
        return c;
    }

    //{prefix}/clubs/id GET
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Club getClubById(@PathVariable("id") Long id){
        return clubService.getClubById(id);
    }

    //{prefix}/clubs GET
    @RequestMapping(value = "",method = RequestMethod.GET)
    public Club getClubByName(@RequestParam("clubName") String clubName){
        Club club=clubService.getClubByName(clubName);
        return club;
    }

    //{prefix}/clubs GET getClubsEager
    @RequestMapping(value = "/list",method=RequestMethod.GET)
    public List<Club> getClubsEager(){
        List<Club> clubs=clubService.getClubsEager();
        return clubs;
    }

    //{prefix}/clubs DELETE
    @RequestMapping(value = "/{id}",method=RequestMethod.DELETE)
    public String deleteClub(@PathVariable("id") Long id){
        String msg=null;
        boolean isSuccess=clubService.deleteDepartment(id);
        if(isSuccess){
            msg="The club is successfully deleted";
        }else {
            msg="failure to delete club";
        }
        return msg;
    }

    //{prefix}/clubs PATCH
    @RequestMapping(value = "",method = RequestMethod.PATCH)
    public String updateClubDesc(@RequestParam("clubName") String clubName,@RequestParam("description") String description){
        Club c=clubService.getClubByName(clubName);
        c.setDescription(description);
        boolean isSuccess=clubService.updateDesc(c);
        String msg;
        if(isSuccess){
            msg="the club is updated";
        }else{
            msg="the club is failure to updated";
        }
        return msg;
    }
}


