package com.ascending.training.club.jdbc;

import com.ascending.training.club.model.ClubJDBC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class ClubJDBCDao {
    //need to set enviroment variable in vm options
    private Logger logger= LoggerFactory.getLogger(getClass());

    static final String DB_URL="jdbc:postgresql://localhost:5431/db_club";
    static final String USER="admin";
    static final String PASS="password";

    public List<ClubJDBC> getClubs(){


        List<ClubJDBC> clubs=new ArrayList();
        Connection conn=null;
        Statement stmt=null;
        ResultSet rs=null;


        try {
            //2:OPEN A CONNECTION
            System.out.println("connecting to database");
            conn= DriverManager.getConnection(DB_URL,USER,PASS);

            //3:EXECUTE QUERY
            //System.out.println("Create statement...");
            logger.debug("Create statement...");
            stmt=conn.createStatement();
            String sql="SELECT * FROM clubs";
            rs=stmt.executeQuery(sql);

            //4:Extract data from result set
            while (rs.next()){
                Long id=rs.getLong("id");
                String name=rs.getString("name");
                String description=rs.getString("description");
                String location=rs.getString("location");
                String  startDate=rs.getString("start_date");
                //Fill the object
                ClubJDBC club=new ClubJDBC();
                club.setId(id);
                club.setName(name);
                club.setDescription(description);
                club.setLocation(location);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate=LocalDate.parse(startDate,formatter);
                club.setStartDate(localDate);
                clubs.add(club);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                if(rs!=null) rs.close();
                if(stmt!=null) stmt.close();
                if(conn!=null) conn.close();
            }catch (SQLException se){
                se.printStackTrace();
            }
        }
        return clubs;
    }

    public static void main(String[] args) {
        ClubJDBCDao c=new ClubJDBCDao();
        System.out.println(c.getClubs().size());
    }
}
