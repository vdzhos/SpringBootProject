package com.sbproject.schedule.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class UserActivityLoggerFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(UserActivityLoggerFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Object[] roles = authentication.getAuthorities().toArray();
        String role = ((GrantedAuthority)roles[0]).getAuthority();

        filterChain.doFilter(servletRequest,servletResponse);
        String uri = ((HttpServletRequest)servletRequest).getRequestURI();
        logger.info(Markers.USER_ACTIVITY,username + " ("+role+") " + " --- " + uri);
    }

}
