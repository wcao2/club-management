package com.ascending.training.club.respository;

import com.ascending.training.club.model.Role;
import com.ascending.training.club.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao{
    private Logger logger= LoggerFactory.getLogger(getClass());

    @Override
    public Role save(Role role) {
        Transaction transaction=null;
        Session session= HibernateUtil.getSessionFactory().openSession();
        try {
            transaction=session.beginTransaction();
            session.save(role);
            transaction.commit();
            session.close();
            return role;
        }catch (Exception e){
            if(transaction!=null) transaction.rollback();
            logger.error("failure to insert record");
            session.close();
            return null;
        }
    }

    @Override
    public List<Role> getRoles() {
        List<Role> roles=new ArrayList<>();
        String hql="FROM Role";
        Session session=HibernateUtil.getSessionFactory().openSession();
        try{
            Query<Role> query=session.createQuery(hql);
            roles=query.list();
            session.close();
            return roles;
        }catch (Exception e){
            logger.error("failure to retrieve data record",e);
            session.close();;
            return roles;
        }
    }

    @Override
    public Role getById(Long id) {
        String hql="FROM Role r where r.id=:Id";
        Session session=HibernateUtil.getSessionFactory().openSession();
        try{
            Query<Role> query=session.createQuery(hql);
            query.setParameter("Id",id);
            Role result=query.uniqueResult();
            session.close();
            return result;
        }catch (HibernateException e){
            logger.error("Failure to retrieve data",e);
            session.close();
            return null;
        }
    }

    @Override
    public int deleteById(Long id) {
        String hql="DELETE Role as r where r.id=:Id";
        int deletedCount=0;
        Transaction transaction=null;
        Session session=HibernateUtil.getSessionFactory().openSession();
        try{
            transaction=session.beginTransaction();
            Query<Role> query=session.createQuery(hql);
            query.setParameter("Id",id);
            deletedCount=query.executeUpdate();
            transaction.commit();
            session.close();
            return deletedCount;
        }catch (HibernateException e){
            if(transaction!=null) transaction.rollback();
            session.close();
            logger.error("unable to delete record",e);
            return deletedCount;
        }
    }

    @Override
    public int update(Role role) {
        Transaction transaction=null;
        boolean isSuccess=true;
        Session session=HibernateUtil.getSessionFactory().openSession();
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(role);
            transaction.commit();
            session.close();
            return 1;
        }catch (Exception e){
            isSuccess=false;
            if(transaction!=null) transaction.rollback();
            session.close();
            logger.error("Failure to update record",e.getMessage());
            return 0;
        }
    }
}
