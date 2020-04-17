package com.ascending.training.club.respository;

import com.ascending.training.club.model.User;
import com.ascending.training.club.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao{
    @Autowired
    private UserDao userDao;

    @Override
    public User save(User user) {
        Transaction transaction=null;
        Session session=HibernateUtil.getSessionFactory().openSession();
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
        Session session= HibernateUtil.getSessionFactory().openSession();
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
        Session session= HibernateUtil.getSessionFactory().openSession();
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
}
