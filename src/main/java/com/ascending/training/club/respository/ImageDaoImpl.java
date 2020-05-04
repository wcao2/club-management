package com.ascending.training.club.respository;

import com.ascending.training.club.model.Image;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ImageDaoImpl implements ImageDao {
    @Autowired
    private SessionFactory sessionFactory;

    Logger logger=(Logger) LoggerFactory.getLogger(this.getClass());
    @Override
    public Image save(Image image) {
        Session session= sessionFactory.openSession();
        Transaction transaction=null;
        try {
            transaction=session.beginTransaction();
            session.save(image);
            transaction.commit();;
            session.close();;
            return image;
        }catch (Exception e){
            session.close();;
            logger.error("fail to save this file",e);
            return null;
        }
    }

    @Override
    public int delByUserId(Long id) {
        String hql="delete from Image where user.id=:id";
        Session session=sessionFactory.openSession();
        Transaction transaction=null;
        try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(hql);
            query.setParameter("id",id);
            int num=query.executeUpdate();
            transaction.commit();
            session.close();
            return num;
        }catch (Exception e){
            session.close();
            logger.error("Failed to delete this record",e);
            return -1;
        }
    }

    @Override
    public List<Image> getByUserId(Long id) {
        Session session=sessionFactory.openSession();
        String hql="from Image as i where user.id=:id";
        Query query=session.createQuery(hql);
        query.setParameter("id",id);
        List<Image> images=query.list();
        session.close();
        return images;
    }
}
