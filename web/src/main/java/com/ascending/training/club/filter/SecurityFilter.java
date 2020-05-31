package com.ascending.training.club.filter;

import com.ascending.training.club.model.User;
import com.ascending.training.club.service.JWTService;
import com.ascending.training.club.service.UserService;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    private static final Set<String> IGNORED_PATHS=new HashSet<>(Arrays.asList("/auth","/auth/signup"));
    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserService userService;

    private Logger logger= LoggerFactory.getLogger(getClass());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            //how to add DI in filter when deploy at tomcat
            if(userService==null){
                //injection every dependency
                SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,servletRequest.getServletContext());
            }
            HttpServletRequest req=(HttpServletRequest)servletRequest; //based on Http status
            int statusCode=authorization(req);
            if(statusCode==HttpServletResponse.SC_ACCEPTED) filterChain.doFilter(servletRequest,servletResponse);
            else((HttpServletResponse)servletResponse).sendError(statusCode);
    }

    private int authorization(HttpServletRequest req){
        int statucCode= HttpServletResponse.SC_UNAUTHORIZED;
        String uri=req.getRequestURI();
        //servlet filter manipulate session attribute
        HttpSession session=req.getSession();

        //if it is login or signUp api, just accept
        if(IGNORED_PATHS.contains(uri)) return HttpServletResponse.SC_ACCEPTED;
        String verb=req.getMethod();
        try{
            String token=req.getHeader("Authorization").replaceAll("Bearer: ","");//TODO REGEX
            if(token==null||token.isEmpty()) return statucCode;
            Claims claims=jwtService.decpytToken(token);
            User user=userService.getUserById(Long.valueOf(claims.getId()));
            if(user==null){
                return statucCode;
            }else{
                session.setAttribute("UserId",user.getId());
            }

            //improvement: u.getRoles instead of using token to getRole, so the token is not too long
            String allowedResources="/";
            switch (verb){
                case "GET" : allowedResources=(String) claims.get("allowedReadResources");break;
                case "POST" : allowedResources=(String) claims.get("allowedCreateResources");break;
                case "PUT" : allowedResources=(String) claims.get("allowedUpdateResources");break;
                case "DELETE" : allowedResources=(String) claims.get("allowedDeleteResources");break;
            }
            for(String s:allowedResources.split(",")){
                if(s.trim().isEmpty()){
                     break;
                }else {
                    if(uri.trim().toLowerCase().startsWith(s.trim().toLowerCase())){
                        statucCode=HttpServletResponse.SC_ACCEPTED;
                        break;
                    }
                }
            }
        }catch (Exception e){
            logger.error("can not verify token",e);
        }
        return statucCode;
    }
}
