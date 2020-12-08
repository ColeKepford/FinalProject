/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;

/**
 *
 * @author cocog
 */
public class RegisterFilter implements Filter 
{
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException 
    {

            // code that is executed before the servlet
            HttpServletRequest httpRequest = (HttpServletRequest)request;
            HttpSession session = httpRequest.getSession();
            User user = (User) session.getAttribute("user");
            
            if (user != null) 
            {
                if(user.getRole().getRoleId() != 1 && user.getRole().getRoleId() != 3)
                {
                    HttpServletResponse httpResponse = (HttpServletResponse)response;
                    httpResponse.sendRedirect("Inventory");
                    return;
                }
                else
                {
                    HttpServletResponse httpResponse = (HttpServletResponse)response;
                    httpResponse.sendRedirect("Admin");
                    return;
                }
            }
            
            chain.doFilter(request, response); // execute the servlet
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException 
    {
        
    }

    @Override
    public void destroy() 
    {
       
    }
}
