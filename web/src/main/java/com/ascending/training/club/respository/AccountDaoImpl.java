package com.ascending.training.club.respository;

import com.ascending.training.club.model.Account;
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
import sun.security.ssl.HandshakeInStream;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountDaoImpl implements AccountDao {
    private Logger logger= LoggerFactory.getLogger(getClass());

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Account> getAll() {
        Session session= sessionFactory.openSession();
//        String hql="FROM Account as a where a.id=:id";
        String hql="FROM Account as a LEFT JOIN FETCH a.player";
        List<Account> accounts=new ArrayList<>();
        try {
            Query<Account> query=session.createQuery(hql);
            accounts=query.list();
            session.close();
            return accounts;
        }catch (Exception e){
            logger.error("failure to retrieve all Account");
            session.close();
            return null;
        }
    }

    @Override
    public Account save(Account account) {
        Transaction transaction=null;
        Session session=sessionFactory.openSession();
        try {
            transaction=session.beginTransaction();
            session.save(account);
            transaction.commit();
            session.close();
            return account;
        }catch (Exception exception){
            if(transaction!=null) transaction.rollback();
            logger.error("failure to insert record into Account Table");
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        String hql="DELETE Account as a where a.id=:Id";
        int deleteedCount=0;
        Transaction transaction=null;
        Session session=sessionFactory.openSession();
        try {
            transaction=session.beginTransaction();
            Query<Account> query=session.createQuery(hql);
            query.setParameter("Id",id);
            deleteedCount=query.executeUpdate();
            transaction.commit();
            session.close();
            return  deleteedCount>=1?true:false;
        }catch (HibernateException exception){
            if(transaction!=null) transaction.rollback();
            session.close();
            logger.error("unable to delete record",exception);
            return false;
        }
    }

    @Override
    public Account getAccountById(Long Id) {
        Session session= sessionFactory.openSession();
//        String hql="FROM Account as a where a.id=:id";
        String hql="FROM Account as a LEFT JOIN FETCH a.player where a.id=:id";
        try {
            Query<Account> query=session.createQuery(hql);
            query.setParameter("id",Id);
            Account result=query.uniqueResult();
            session.close();
            return result;
        }catch (Exception e){
            logger.error("failure to retrieve Account details");
            session.close();
            return null;
        }
    }
}
















