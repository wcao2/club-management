package com.ascending.training.club.respository;

import com.ascending.training.club.model.Club;

import java.util.List;

public interface ClubDao {
    Club save(Club club);

    //1: get club by using Id
    Club getClubById(Long id);
    //2: get club by Using clubName
    Club getClubByName(String clubName);
    //3: lazy join
    //List<Club> getClubsLazy();
    //4: eager join
    List<Club> getClubsEager();

    boolean delete(Long id);
    boolean updateDesc(Club club);
}
