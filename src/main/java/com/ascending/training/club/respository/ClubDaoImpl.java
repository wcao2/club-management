package com.ascending.training.club.respository;

import com.ascending.training.club.model.Club;
import com.ascending.training.club.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//annotates classes at persistence layer, which will act as a database repository
@Repository
public class ClubDaoImpl implements ClubDao {
    private Logger logger= LoggerFactory.getLogger(getClass());

    @Override
    public Club save(Club club) {
        Transaction transaction=null;
        Session session=HibernateUtil.getSessionFactory().openSession();
        try{
            transaction=session.beginTransaction();
            session.save(club);
            transaction.commit();
            session.close();
            return club;
        }catch (Exception e){
            if(transaction!=null) transaction.rollback();
            logger.error("fail to insert new club");
            return null;
        }
    }

    @Override
    public Club getClubById(Long id) {
        String hql="FROM Club c LEFT JOIN FETCH c.player where c.id=:Id";
        Session session=HibernateUtil.getSessionFactory().openSession();
        try{
            Query<Club> query=session.createQuery(hql);
            query.setParameter("Id",id);
            Club result=query.uniqueResult();
            session.close();
            return result;
        }catch (HibernateException e){
            logger.error("failure to retrieve data record from club",e);
            return null;
        }
    }

    @Override
    public Club getClubByName(String clubName) {
        Transaction transaction=null;
        if(clubName==null) return null;
        String hql="FROM Club as c LEFT JOIN FETCH c.player where lower(c.name)=:name";
        Session session=HibernateUtil.getSessionFactory().openSession();
        transaction=session.beginTransaction();
        Query<Club> query=session.createQuery(hql);
        query.setParameter("name",clubName.toLowerCase());
        Club result=query.uniqueResult();
        transaction.commit();
        session.close();
        return result;
    }

    /*@Override
    public List<Club> getClubsLazy() { }*/

    @Override
    public List<Club> getClubsEager() {
        Transaction transaction=null;
        String hql="FROM Club c left join fetch c.player";
        List<Club> clubs=new ArrayList<>();
        Session session=HibernateUtil.getSessionFactory().openSession();
        try{
            Query<Club> query=session.createQuery(hql);
            clubs=query.list();
            session.close();
            return clubs;
        }catch (Exception e){
            logger.error("failure to retrieve data",e);
            session.close();
            return null;
        }
    }

    @Override
    public boolean delete(Long id) {
        String hql="DELETE Club as c where c.id=:Id";
        Transaction transaction=null;
        int deletedCount=0;
        Session session=HibernateUtil.getSessionFactory().openSession();
        try {
            transaction=session.beginTransaction();
            Query<Club> query=session.createQuery(hql);
            query.setParameter("Id",id);
            deletedCount=query.executeUpdate();
            transaction.commit();
            session.close();
            return deletedCount>=1?true:false;
        }catch(HibernateException e){
            if(transaction!=null) transaction.rollback();
            session.close();
            logger.error("unabe to delete record",e);
        }
        return false;
    }

    @Override
    public boolean updateDesc(Club club) {
        Transaction transaction=null;
        boolean isSuccess=true;
        Session session=HibernateUtil.getSessionFactory().openSession();
        try {
            transaction=session.beginTransaction();
            session.update(club);
            transaction.commit();
            session.close();
        }catch (Exception e){
            isSuccess=false;
            if(transaction!=null) transaction.rollback();
            session.close();
            logger.error("Fail to update record",e.getMessage());
        }
        return isSuccess;
    }
}
















