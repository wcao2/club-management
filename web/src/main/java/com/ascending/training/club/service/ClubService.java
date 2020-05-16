package com.ascending.training.club.service;

import com.ascending.training.club.model.Club;
import com.ascending.training.club.respository.ClubDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//annotates classes at the service layer
@Service
public class ClubService {
    @Autowired
    private ClubDao clubDao;

    public Club save(Club club){return clubDao.save(club);}
    public Club getClubById(Long id){return clubDao.getClubById(id);}
    public Club getClubByName(String clubName){return clubDao.getClubByName(clubName);}
    public List<Club> getClubsEager(){return clubDao.getClubsEager();}
    public boolean deleteDepartment(Long id){return clubDao.delete(id);}
    public boolean updateDesc(Club club){return clubDao.updateDesc(club);}
}

