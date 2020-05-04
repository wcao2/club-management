package com.ascending.training.club.respository;

import com.ascending.training.club.model.Club;
import com.ascending.training.club.model.Player;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PlayerDaoImpl implements PlayerDao {

    @Autowired
    private ClubDao clubDao;

    @Autowired
    private SessionFactory sessionFactory;

    private Logger logger= LoggerFactory.getLogger(getClass());


    @Override
    public Player save(Player player, String clubName) {
        Transaction transaction=null;
        Session session=sessionFactory.openSession();
        try {
            Club club=clubDao.getClubByName(clubName);
            if(club!=null){
                transaction=session.beginTransaction();
                player.setClub(club);
                session.save(player);
                transaction.commit();
                session.close();
                return player;
            }else {
                logger.error("the club name does not exist");
                return null;
            }
        }catch (Exception exception){
            if(transaction!=null) transaction.rollback();
            logger.error(exception.getMessage());
            session.close();
            return null;
        }
    }

    @Override
    public Player updatePlayerEmail(Player player) {
        Transaction transaction=null;
        Session session=sessionFactory.openSession();
        try {
            transaction=session.beginTransaction();
            session.update(player);
            transaction.commit();
            session.close();
            return player;
        }catch (Exception exception){
            if(transaction!=null) transaction.rollback();
            logger.error(exception.getMessage());
            session.close();
            return null;
        }
    }

    @Override
    public List<Player> getPlayersAndClub() {
        List<Player> players=new ArrayList<>();
        String hql="FROM Player as p LEFT JOIN FETCH p.club";
        Session session=sessionFactory.openSession();
        try {
            Query<Player> query=session.createQuery(hql);
            players=query.list();
            session.close();;
            return players;
        }catch (Exception e){
            logger.error("failure to retrieve data",e);
            return null;
        }
    }

    @Override
    public Player getPlayerById(Long Id) {
        if(Id==null) return null;
        String hql="FROM Player as p left join fetch p.club where p.id=:id";
        Session session=sessionFactory.openSession();
        Query<Player> query=session.createQuery(hql);
        query.setParameter("id",Id);
        Player player=query.uniqueResult();
        session.close();
        return player;
    }

    @Override
    public Player getPlayerByName(String playerName) {
        if(playerName==null) return null;
        String hql="FROM Player as p left join fetch p.club where lower(p.name)=:name";
        Session session=sessionFactory.openSession();
        Query<Player> query=session.createQuery(hql);
        query.setParameter("name",playerName.toLowerCase());
        Player player=query.uniqueResult();
        session.close();
        return player;
    }

    @Override
    public boolean delete(Long id) {
        String hql="DELETE Player as p where p.id=:Id";
        int deletedCount;
        Transaction transaction=null;
        Session session=sessionFactory.openSession();
        try {
            transaction=session.beginTransaction();
            Query<Player> query=session.createQuery(hql);
            query.setParameter("Id",id);
            deletedCount=query.executeUpdate();
            transaction.commit();
            session.close();
            return deletedCount>=1?true:false;
        }catch (HibernateException exception){
            if(transaction!=null) transaction.rollback();
            session.close();
            logger.error("unable to delete record",exception);
            return false;
        }
    }
}
