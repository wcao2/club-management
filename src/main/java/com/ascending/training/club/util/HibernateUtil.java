package com.ascending.training.club.util;


import com.github.fluent.hibernate.cfg.scanner.EntityScanner;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    private static Logger logger= LoggerFactory.getLogger(HibernateUtil.class);

    public static SessionFactory getSessionFactory(){
        if(sessionFactory==null){
            try {
                String[] modelPackages={"com.ascending.training.club.model"};
                String dbDriver = System.getProperty("database.driver");
                String dbDialect = System.getProperty("database.dialect");
                String dbUrl = System.getProperty("database.url");
                String dbUser = System.getProperty("database.user");
                String dbPassword = System.getProperty("database.password");

                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, dbDriver);
                settings.put(Environment.DIALECT, dbDialect);
                settings.put(Environment.URL, dbUrl);
                settings.put(Environment.USER, dbUser);
                settings.put(Environment.PASS, dbPassword);
                settings.put(Environment.SHOW_SQL, "true");
                //to verify mapping correct or not (validate schema)
                settings.put(Environment.HBM2DDL_AUTO, "validate");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                configuration.setProperties(settings);

                //scan object with @Entity
                EntityScanner.scanPackages(modelPackages).addTo(configuration);
                StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
                ServiceRegistry serviceRegistry = registryBuilder.applySettings(configuration.getProperties()).build();
                //construct session Factory
                sessionFactory=configuration.buildSessionFactory(serviceRegistry);
            }
            catch (Exception e){
                logger.error("fail to generate hibernate sessionfactory",e);
            }
        }
        return sessionFactory;
    }

    public static void main(String[] args) {
        //For app, SessionFactory is singleton
        //if there are two data source, then I have to SessionFactory, for every ds, sf is singleton, for app, it not
        //configuration: include all info relates to data source(encapsulation)
        SessionFactory sf=HibernateUtil.getSessionFactory();
        //System.out.println("success update sf"+sf.hashCode());
        logger.info("success update sf"+sf.hashCode());
        //But I could create diff session
        //for session, SessionFactory is a (factory) design pattern;
        //Session s=sf.openSession();
    }
}
