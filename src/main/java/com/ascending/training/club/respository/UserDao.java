package com.ascending.training.club.respository;

import com.ascending.training.club.model.User;

public interface UserDao {
   /* boolean save(User user);
    User getUserByEmail(String email);
    User getUserById(Long id);*/
    User getUserByCredentials(String email,String password) throws Exception;
}
