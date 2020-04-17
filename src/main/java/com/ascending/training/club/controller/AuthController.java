package com.ascending.training.club.controller;

import com.ascending.training.club.model.User;
import com.ascending.training.club.service.JWTService;
import com.ascending.training.club.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/*
*  this controller receive user name and password to authenticate the user, if the user is authenticated, return the token to client
*  otherwise return "wrong name or password" message to client
* */
@RestController
@RequestMapping(value = {"/auth"})
public class AuthController {
    private Logger logger= LoggerFactory.getLogger(getClass());
    @Autowired private UserService userService;
    @Autowired private JWTService jwtService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    //RequestEntity reteurn both jwt and status
    public ResponseEntity userLogin(@RequestBody User user){
        try{
            User u=null;
            if(user.getEmail()!=null){
                //if cannot find user, return 401
                u=userService.getUserByCredentials(user.getEmail(),user.getPassword());
            }else if(user.getName()!=null){
                u=userService.getUserByCredentials(user.getName(),user.getPassword());
            }
            if(u==null)  return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("please input email or username");
            Map<String,String> map=new HashMap<>();
            map.put("token",jwtService.generateToken(u));
            return ResponseEntity.ok().body(map);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }
}
