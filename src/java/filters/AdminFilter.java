package filters;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
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
 * @author 821052
 */
public class AdminFilter implements Filter 
{
        
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException 
    {
        
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpSession session = httpRequest.getSession();
            
            
            User user = (User) session.getAttribute("user");
            
           
            if(user.getRole().getRoleId() != 1 && user.getRole().getRoleId() != 3) 
            {
                
                HttpServletResponse httpResponse = (HttpServletResponse)response;
                
                httpResponse.sendRedirect("Inventory");
                
                return;
            }
            else if(user.getRole().getRoleId() == 3)
            {
                 HttpServletResponse httpResponse = (HttpServletResponse)response;
                
                httpResponse.sendRedirect("CompanyAdmin");
                return;
            }
            
            chain.doFilter(request, response);

    }

    public void destroy() 
    {        
    }


    public void init(FilterConfig filterConfig) 
    {        
    }
}

