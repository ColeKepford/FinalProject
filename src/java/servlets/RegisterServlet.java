/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Company;
import models.Role;
import models.User;
import services.AccountService;
import services.InventoryService;

/**
 *
 * @author cocog
 */
public class RegisterServlet extends HttpServlet 
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        String uuid = request.getParameter("uuid");
        if(uuid == null)
        {
            try 
            {
                InventoryService is = new InventoryService();
                List<Company> companies = is.getallCompanies();
                request.setAttribute("companies", companies); 
            } 
            catch (Exception ex) 
            {
                Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        }
        else
        {
            AccountService as = new AccountService();
            boolean activationStatus = as.activateUser(uuid);
            
            String activationMessage = activationStatus ? "Succesfully activated account" : "Account not activated";
            request.setAttribute("message", activationMessage);
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response); 
        }
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        InventoryService is = new InventoryService();
        try
        {
            List<Company> companies = is.getallCompanies();
            request.setAttribute("companies", companies);
        }
        catch(Exception ex)
        {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
        HttpSession session = request.getSession();
        
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String companyString = request.getParameter("company");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        
        
        if(email.length() > 0 && firstName.length() > 0 && lastName.length() > 0 &&
                companyString.length() > 0 && password1.length() > 0 && password2.length() > 0
                && password1.equals(password2))
        {
            try 
            {
                int companyId = Integer.parseInt(companyString);
                Company company = is.getCompany(companyId);
                AccountService as = new AccountService();
                User user = as.getUser(email);
                if(user != null)
                {
                    request.setAttribute("firstName", firstName);
                    request.setAttribute("lastName", lastName);
                    request.setAttribute("password1", password1);
                    request.setAttribute("password2", password2);
                    
                    request.setAttribute("message", "Account with that email already exists");
                    getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
                    return;
                }
                else
                {
                    Role role = as.getRole(2);
                    as.insert(email, false, firstName, lastName, password2, company, role);
                    
                    String url = request.getRequestURL().toString();
                    String path = getServletContext().getRealPath("/WEB-INF");
                    
                    as.accountActivation(email, path, url);
                    
                    request.setAttribute("message", "Activation email sent. Click link to login to account");

                    getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                } 
            } 
            catch (Exception ex) 
            {
                Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            request.setAttribute("email", email);
            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName", lastName);
            request.setAttribute("password1", password1);
            request.setAttribute("password2", password2);
            
            request.setAttribute("message", "Please fill in all fields and have matching passwords");
            getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
            return;
        }
    }
}
