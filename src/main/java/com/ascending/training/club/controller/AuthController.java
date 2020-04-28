package com.ascending.training.club.controller;

import com.ascending.training.club.model.Player;
import com.ascending.training.club.model.Role;
import com.ascending.training.club.model.User;
import com.ascending.training.club.respository.RoleDao;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    @Autowired private RoleDao roleDao;

    @RequestMapping(value = "", method = RequestMethod.POST)
    //RequestEntity reteurn both jwt and status(customized http status code)
    public ResponseEntity userLogin(@RequestBody User user){//
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

    @RequestMapping(value = "/signup",method=RequestMethod.POST)
    public ResponseEntity userSignUP(@RequestBody User user){
        User u=null;
        if(user.getEmail()==null||user.getName()==null||user.getPassword()==null){
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("please type necessary information");
        }else{
            List<Role> roles=new ArrayList<>();
            Role r=roleDao.getById(2L);
            roles.add(r);
            user.setRoles(roles);

            u=userService.saveUser(user);
            if(u==null) return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).build();
            return ResponseEntity.status(HttpServletResponse.SC_OK).body(u);
        }

    }
}
