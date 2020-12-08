/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Role;
import models.User;
import services.AccountService;
import utilities.CookieUtil;

/**
 *
 * @author cocog
 */
public class AccountServlet extends HttpServlet 
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        String action = request.getParameter("action");
        if (request.getParameter("action") != null && request.getParameter("action").equals("logout")) 
        {
            session.invalidate();
            session = request.getSession();
            request.setAttribute("message", "User logged out.");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
     
        if (request.getParameter("action") != null && request.getParameter("action").equals("deactivate"))
        {
            try 
            {
                AccountService as = new AccountService();
                if(user.getRole().getRoleId() == 1 || user.getRole().getRoleId() == 3)
                {
                    request.setAttribute("message", "Admins cannot deactivate selves");
                }
                else
                {
                    as.updateActive(user.getEmail(), false);
                    session.invalidate();
                    session = request.getSession();
                    request.setAttribute("message", "Account Deactivated");
                    getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                }
            } 
            catch (Exception ex) 
            {
                Logger.getLogger(AccountServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        request.setAttribute("email", user.getEmail());
        request.setAttribute("firstName", user.getFirstName());
        request.setAttribute("lastName", user.getLastName());
        request.setAttribute("password1", user.getPassword());
        request.setAttribute("password2", user.getPassword());
        
       getServletContext().getRequestDispatcher("/WEB-INF/account.jsp").forward(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
       
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        
        
        if(firstName.length() > 0 && lastName.length() > 0 
                && password1.length() > 0 && password2.length() > 0&& password1.equals(password2))
        {
            try 
            {
                AccountService as = new AccountService();
                as.update(email, true, firstName, lastName, password1, user.getCompany(), user.getRole());
                User newUser = as.getUser(email);
                session.setAttribute("user", newUser);
                request.setAttribute("message", "Information updated.");
                
                getServletContext().getRequestDispatcher("/WEB-INF/account.jsp").forward(request, response);
            } 
            catch (Exception ex) 
            {
                Logger.getLogger(AccountServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            request.setAttribute("message", "Please fill in all fields and have matching passwords");
            getServletContext().getRequestDispatcher("/WEB-INF/account.jsp").forward(request, response);
            return;
        }
    }
}
