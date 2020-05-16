package com.ascending.training.club.respository;

import com.ascending.training.club.model.User;

public interface UserDao {
   /*User getUserByEmail(String email);*/
    User save(User user);
    User getUserById(Long id);
    User getUserByCredentials(String email,String password) throws Exception;
    boolean delete(Long id);
}
