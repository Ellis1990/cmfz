package com.ellis.cmfz.filter;


import com.ellis.cmfz.entity.Admin;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res =(HttpServletResponse) servletResponse;
        Admin login = (Admin)req.getSession().getAttribute("login");
        if(login instanceof Admin){
            filterChain.doFilter(req,res);
        }else{
            res.sendRedirect("/cmfz/login/login.jsp");
        }
    }

    @Override
    public void destroy() {

    }
}
