package com.ascending.training.club.respository;

import com.ascending.training.club.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao{
    @Autowired
    private UserDao userDao;
    private Logger logger= LoggerFactory.getLogger(getClass());

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User save(User user) {
        Transaction transaction=null;
        Session session=sessionFactory.openSession();
        try{
            transaction=session.beginTransaction();
            session.save(user);
            transaction.commit();
            session.close();
            return user;
        }catch (HibernateException e){
            if(transaction!=null) transaction.rollback();;
            session.close();
            return null;
        }
    }

    @Override
    public User getUserById(Long id) {
        Session session= sessionFactory.openSession();
        String hql="From User as u left join fetch u.roles where u.id=:id";
        try{
            Query<User> query=session.createQuery(hql);
            query.setParameter("id",id);
            return query.uniqueResult();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public User getUserByCredentials(String email, String password) throws Exception{
        Session session= sessionFactory.openSession();
        String hql="From User as u left join fetch u.roles where(lower(u.email)=:email or lower(u.name)=:email) and u.password=:password";
        try{
            Query<User> query=session.createQuery(hql);
            query.setParameter("email",email.toLowerCase().trim());
            query.setParameter("password",password);
            return query.uniqueResult();
        }catch (Exception e){
            throw new Exception("can not find user record or session");
        }
    }

    @Override
    public boolean delete(Long id) {
        String hql="DELETE User as u where u.id=:id";
        int deletedCount;
        Transaction transaction=null;
        Session session=sessionFactory.openSession();
        try {
            transaction=session.beginTransaction();
            Query<User> query=session.createQuery(hql);
            query.setParameter("id",id);
            deletedCount=query.executeUpdate();
            transaction.commit();
            session.close();
            return deletedCount>=1?true:false;
        }catch (Exception e){
            if(transaction!=null) transaction.rollback();
            session.close();;
            logger.error("unable to delete record",e);
            return false;
        }
    }
}
