package com.ascending.training.club.filter;

import com.ascending.training.club.model.User;
import com.ascending.training.club.service.JWTService;
import com.ascending.training.club.service.UserService;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*
*  The security filter is usde for user authorization.It checks if there is valid token in every request from the clients,
*  if the token is valid, then allow the request passing through, otherwise, it will reject the req,and send "the request is denied" message to client
* */
@WebFilter(filterName = "securityFilter",urlPatterns = {"/*"},dispatcherTypes = {DispatcherType.REQUEST})
public class SecurityFilter implements Filter {
    private static final Set<String> IGNORED_PATHS=new HashSet<>(Arrays.asList("/auth"));
    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserService userService;

    private Logger logger= LoggerFactory.getLogger(getClass());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            HttpServletRequest req=(HttpServletRequest)servletRequest;
            int statusCode=authorization(req);
            if(statusCode==HttpServletResponse.SC_ACCEPTED) filterChain.doFilter(servletRequest,servletResponse);
            else((HttpServletResponse)servletResponse).sendError(statusCode);
    }

    private int authorization(HttpServletRequest req){
        int statucCode= HttpServletResponse.SC_UNAUTHORIZED;
        String uri=req.getRequestURI();
        if(IGNORED_PATHS.contains(uri)) return HttpServletResponse.SC_ACCEPTED;

        try{
            String token=req.getHeader("Authorization").replaceAll("Bearer: ","");
            if(token==null||token.isEmpty()) return statucCode;
            Claims claims=jwtService.decpytToken(token);
            if(claims.getId()!=null){
                User u=userService.getUserById(Long.valueOf(claims.getId()));
                if(u!=null) statucCode=HttpServletResponse.SC_ACCEPTED;
            }
            return statucCode;
        }catch (Exception e){
            logger.error("can not verify token",e);
        }
        return statucCode;
    }
}
