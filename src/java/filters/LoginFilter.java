/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Role;
import models.User;
import services.AccountService;
import servlets.AdminServlet;

/**
 *
 * @author cocog
 */
public class LoginFilter implements Filter 
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
            AccountService as = new AccountService();
            
            try
            {
                Role sysAdmin = as.getRole(1);
                Role genUser = as.getRole(2);
                Role compAdmin = as.getRole(3);
            
                if (user != null) 
                {
                    if(!user.getRole().equals(sysAdmin) && !user.getRole().equals(compAdmin))
                    {
                        HttpServletResponse httpResponse = (HttpServletResponse)response;
                        httpResponse.sendRedirect("Inventory");
                        return;
                    }
                    else if(user.getRole().equals(compAdmin))
                    {
                        HttpServletResponse httpResponse = (HttpServletResponse)response;
                        httpResponse.sendRedirect("CompanyAdmin");
                        return;
                    }
                    else
                    {
                        HttpServletResponse httpResponse = (HttpServletResponse)response;
                        httpResponse.sendRedirect("Admin");
                        return;
                    }
                }
            }
            catch(Exception ex)
            {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
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
