/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Category;
import models.Company;
import models.Item;
import models.Role;
import models.User;
import services.AccountService;
import services.InventoryService;

/**
 *
 * @author cocog
 */
public class AdminServlet extends HttpServlet 
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if(user == null)
        {
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
        
        AccountService as = new AccountService();
        InventoryService is = new InventoryService();
        
        if (request.getParameter("action") != null && request.getParameter("action").equals("logout")) 
        {
            session.invalidate();
            session = request.getSession();
            request.setAttribute("message", "User logged out.");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
        
        else if(request.getParameter("action") != null && request.getParameter("action").equals("delete"))
        {
            char ch = '+';
            String email = request.getParameter("email");
            email = email.replace(' ', ch);
            String sessionEmail = (String) session.getAttribute("email");
            
            if(email.equals(sessionEmail))
            {
                request.setAttribute("message", "Admin users not allowed to delete selves");
            }
            else
            {
                try 
                {
                    as.delete(email);
                    request.setAttribute("message", "Account deleted.");
                } 
                catch (Exception ex) 
                {
                    Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
        
        else if(request.getParameter("action") != null && request.getParameter("action").equals("edit"))
        {
            String email = request.getParameter("email");
            char ch = '+';
            email = email.replace(' ', ch);
            
            try 
            {
                User editUser = as.getUser(email);
                request.setAttribute("editUser", editUser);
            } 
            catch (Exception ex) 
            {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(request.getParameter("action") != null && request.getParameter("action").equals("activate"))
        {
            try 
            {
                
                String email = request.getParameter("email");
                char ch = '+';
                email = email.replace(' ', ch);
                
                as.updateActive(email, true);
            } 
            catch (Exception ex) 
            {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        else if(request.getParameter("action") != null && request.getParameter("action").equals("deactivate"))
        {
            try 
            {
                String email = request.getParameter("email");
                char ch = '+';
                email = email.replace(' ', ch);
                String sessionEmail = (String) session.getAttribute("email");
                
                 if(email.equals(sessionEmail))
                {
                    request.setAttribute("message", "Admin users not allowed to deactivate selves");
                }
                else
                {
                    as.updateActive(email, false);
                }
            }
            catch (Exception ex) 
            {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        else if(request.getParameter("action") != null && request.getParameter("action").equals("editCategory"))
        {
            try 
            {
                int categoryId = Integer.parseInt(request.getParameter("categoryId"));
                Category category = is.getCategory(categoryId);
                session.setAttribute("editCategory", category);
            } 
            catch (Exception ex) 
            {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
               
        }
        try
        {
            List<User> users = as.getAllUsers();
            request.setAttribute("users", users);
            
            List<Company> companies = is.getallCompanies();
            request.setAttribute("companies", companies);
            
            List<Role> roles = as.getAllRoles();
            request.setAttribute("roles", roles);
            
            List<Category> categories = is.getAllCategories();
            request.setAttribute("categories", categories);
        }
        catch(Exception e)
        {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, e);
        }
        
       getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        HttpSession session = request.getSession();
        AccountService as = new AccountService();
        InventoryService is = new InventoryService();
        
        String action = request.getParameter("action");
        
        switch(action)
        {
             case "search":
                String searchedItem = request.getParameter("itemSearch");
                try
                {
                    List<User> users = as.getAllUsers();
                    List<Item> items = new ArrayList<Item>();
                    List<Item> usersItems;
                    for(User user: users)
                    {
                        usersItems = user.getItemList();
                        for(Item item: usersItems)
                        {
                            items.add(item);
                        }
                    }
                    List<Item> foundItems = new ArrayList<Item>();
                    for(Item item: items)
                    {
                        if(item.getItemName().contains(searchedItem))
                        {
                            foundItems.add(item);
                        }
                    }
                    request.setAttribute("foundItems", foundItems);
                }
                catch(Exception ex)
                {
                    Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
                
            case "add":
                String email = request.getParameter("emailField");
                String fName = request.getParameter("fName");
                String lName = request.getParameter("lName");
                String password = request.getParameter("password");
                String companyString = request.getParameter("company");
                String roleString = request.getParameter("role");
                
            try 
            {
                if(as.getUser(email) != null)
                {
                    request.setAttribute("message", "Account already exists with that email");
                }
                else if(email.length() > 0 && fName.length() > 0 && lName.length() > 0 && password.length() > 0 &&
                        companyString.length() > 0 && roleString.length() > 0)
                {
                    int companyId = Integer.parseInt(companyString);
                    Company company = is.getCompany(companyId);
                    int roleId = Integer.parseInt(roleString);
                    Role role = as.getRole(roleId);
                    as.insert(email, true, fName, lName, password, company, role);
                }
                else
                {
                    request.setAttribute("editFName", fName);
                    request.setAttribute("editLName", lName);
                    request.setAttribute("editPassword", password);
                    request.setAttribute("editCompany", Integer.parseInt(companyString));
                    request.setAttribute("editRole", Integer.parseInt(roleString));

                    request.setAttribute("message", "Please fill in all fields");
                }
            }
            catch (Exception ex) 
            {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;
            
            case "save":
                String editEmail = request.getParameter("editEmailField");
                String editFName = request.getParameter("editFName");
                String editLName = request.getParameter("editLName");
                String editPassword = request.getParameter("editPassword");
                String editCompany = request.getParameter("editCompany");
                String editRole = request.getParameter("editRole");
         
            if(editEmail.length() > 0 && editFName.length() > 0 && editLName.length() > 0 && editPassword.length() > 0 &&
                    editCompany.length() > 0 && editRole.length() > 0)
            {
                try 
                {
                    int companyIndex = Integer.parseInt(editCompany);
                    Company company = is.getCompany(companyIndex);
                    int roleIndex = Integer.parseInt(editRole);
                    Role role = as.getRole(roleIndex);
                    
                    as.update(editEmail, true, editFName, editLName, editPassword, company, role);
                    request.setAttribute("editUser", null);
                    request.setAttribute("message", "Account edited.");
                } 
                catch (Exception ex) 
                {
                    Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
            {
                request.setAttribute("editFName", editFName);
                request.setAttribute("editLName", editLName);
                request.setAttribute("editPassword", editPassword);
                request.setAttribute("editCompany", Integer.parseInt(editCompany));
                request.setAttribute("editRole", Integer.parseInt(editRole));
                
                request.setAttribute("message", "Please fill in all fields");
            }
            break;
            
            case "addCategory":
                String categoryName = request.getParameter("categoryName");
                if(categoryName.length() > 0)
                {
                    try 
                    {
                        Category category = new Category(categoryName);
                        is.insertCategory(category);
                        request.setAttribute("categoriesMessage", "Category added.");
                        
                    } 
                    catch (Exception ex) 
                    {
                        Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                
                case "saveCategory":
                String editCategoryName = request.getParameter("editCategoryName");
                if(editCategoryName.length() > 0)
                {
                    try 
                    {
                        Category category = (Category) session.getAttribute("editCategory");
                        is.updateCategory(category.getCategoryId(), editCategoryName);
                        
                        session.setAttribute("editCategory", null);
                        request.setAttribute("categoriesMessage", "Category Edited");
                        
                    } 
                    catch (Exception ex) 
                    {
                        Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else
                {
                    request.setAttribute("categoriesMessage", "Please fill in category.");
                }
                break;
        }
        
        try
        {
            List<User> users = as.getAllUsers();
            request.setAttribute("users", users);
            
            List<Company> companies = is.getallCompanies();
            request.setAttribute("companies", companies);
            
            List<Role> roles = as.getAllRoles();
            request.setAttribute("roles", roles);
            
            List<Category> categories = is.getAllCategories();
            request.setAttribute("categories", categories);
        }
        catch(Exception e)
        {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, e);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
    }
}
