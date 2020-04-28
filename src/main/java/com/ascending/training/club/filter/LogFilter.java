package com.ascending.training.club.filter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

//Filter logFilter=new LogFilter();
@WebFilter(filterName = "logFilter",urlPatterns = {"/*"},dispatcherTypes = {DispatcherType.REQUEST})
public class LogFilter implements Filter {
    private Logger logger= LoggerFactory.getLogger(getClass());
    private final List<String> excludedWords = Arrays.asList("newPasswd", "confirmPasswd", "passwd", "password");
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,SSS");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            //pre processing
            long startTime=System.currentTimeMillis();
            HttpServletRequest req=(HttpServletRequest)request;
            String logInfo=logInfo(req);
            chain.doFilter(request,response);
            logger.info(logInfo.replace("responseTime",String.valueOf(System.currentTimeMillis()-startTime)));
            //post processing
            /*logger.info("i am in logger filter");
            long endTime=System.currentTimeMillis();
            logger.info("i am in logger filter after process");*/
    }

    @Override
    public void destroy() {

    }

    private boolean isIgnoredWord(String word, List<String> excludedWords) {
        for (String excludedWord : excludedWords) {
            if (word.toLowerCase().contains(excludedWord)) return true;
        }
        return false;
    }
    // DESC: Organize all requests to get the input and output format I want
    private String logInfo(HttpServletRequest req) {
        String formData = null;
        String httpMethod = req.getMethod();
        LocalDateTime startDateTime = LocalDateTime.now();
        String requestURL = req.getRequestURI();
        String userIP = req.getRemoteHost();
        String sessionID = req.getSession().getId();
        Enumeration<String> parameterNames = req.getParameterNames();
        List<String> parameters = new ArrayList();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            if (isIgnoredWord(paramName, excludedWords)) continue;
            String paramValues = Arrays.asList(req.getParameterValues(paramName)).toString();
            parameters.add(paramName + "=" + paramValues);
        }
        if (!parameters.isEmpty()) {
            formData = parameters.toString().replaceAll("^.|.$", "");
        }
        return  new StringBuilder("| ")
                .append(formatter.format(startDateTime)).append(" | ")
                .append(userIP).append(" | ")
                .append(httpMethod).append(" | ")
                .append(requestURL).append(" | ")
                .append(sessionID).append(" | ")
                .append("responseTime ms").append(" | ")
                .append(formData).toString();
    }
}
