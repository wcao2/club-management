package com.ascending.training.club.service;

import com.ascending.training.club.model.User;
import com.ascending.training.club.respository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public User getUserByCredentials(String email, String password) throws Exception{return userDao.getUserByCredentials(email,password);}

    public User getUserById(Long id){return userDao.getUserById(id);}

    public User saveUser(User u){return userDao.save(u);}

    public boolean deleteUser(Long id){return userDao.delete(id);}
}
