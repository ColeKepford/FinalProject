/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.sun.istack.internal.logging.Logger;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.AccountService;

/**
 *
 * @author cocog
 */
public class LoginServlet extends HttpServlet 
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (request.getParameter("action") != null && request.getParameter("action").equals("logout")) 
        {
            session.invalidate();
            session = request.getSession();
            request.setAttribute("message", "User logged out.");
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);  
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        HttpSession session = request.getSession();
        AccountService as = new AccountService();
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        request.setAttribute("email", email);
       
        
        if(email.equals("") || email == null || password.equals("") || password == null)
        {
            request.setAttribute("message", "Missing field. Please fill in both fields");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
       
        User user = as.login(email, password);
        
        if(user == null)
        {
            request.setAttribute("message", "Invalid Credentials");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
        
        session.setAttribute("email", email);
        session.setAttribute("user", user);
        
        if(user.getRole().getRoleId() == 1)
        {
            response.sendRedirect("Admin");
        }
        else if (user.getRole().getRoleId() == 3)
        {
            response.sendRedirect("CompanyAdmin");
        }
        else
        {
            response.sendRedirect("Inventory");
        }
    }
}
