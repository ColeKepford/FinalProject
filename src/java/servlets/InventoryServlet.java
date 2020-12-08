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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Category;
import models.Item;
import models.User;
import services.AccountService;
import services.InventoryService;
import utilities.CookieUtil;

/**
 *
 * @author cocog
 */
public class InventoryServlet extends HttpServlet 
{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String email = (String) session.getAttribute("email");
        
        
        
        InventoryService is = new InventoryService();
        AccountService as = new AccountService();
        
        Cookie[] cookies = request.getCookies();
        String itemName = CookieUtil.getCookieValue(cookies, "itemName");
        request.setAttribute("itemNameField", itemName);
        String price = CookieUtil.getCookieValue(cookies, "price");
        request.setAttribute("priceField", price);
        
        String action = request.getParameter("action");
        
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
            email = email.replace(' ', ch);
                try
            {
                int itemId = Integer.parseInt(request.getParameter("itemId"));
                is.delete(itemId, email);
                User newUser = as.getUser(email);
                session.setAttribute("user", newUser);
                request.setAttribute("saveMessage", "Item deleted.");
            }
            catch(Exception e)
            {
                Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, e);
            }
            
        }
        
        else if(request.getParameter("action") != null && request.getParameter("action").equals("edit"))
        {
            try 
            {
                int itemId = Integer.parseInt(request.getParameter("itemId"));
                Item item = is.getItem(itemId);
                if(user.equals(item.getOwner()))
                {
                    session.setAttribute("editItem", item);
                }
            } 
            catch (Exception ex) 
            {
                Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(user == null)
        {
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
     
        try 
        {
            List<Category> categories = is.getAllCategories();
            request.setAttribute("categories", categories);
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String email = (String) session.getAttribute("email");
        InventoryService is = new InventoryService();
        
        String action = request.getParameter("action");
        
        try 
        {
            List<Category> categories = is.getAllCategories();
            request.setAttribute("categories", categories);
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        switch(action)
        {
            case "add":
                try 
                {
                    String categoryId = request.getParameter("category");
                    String itemName = request.getParameter("itemName");
                    String price = request.getParameter("price");

                    if(categoryId.length() > 0 && itemName.length() > 0 && price.length() > 0)
                    {
                        Category category = is.getCategory(Integer.parseInt(categoryId));
                        Item item = new Item(itemName, Double.parseDouble(price), category, user);
                        is.insert(item);
                       
                        
                        request.setAttribute("saveMessage", "Item added.");
                    }
                    else
                    {
                        Cookie itemNameCookie = new Cookie("itemName", itemName);
                        itemNameCookie.setMaxAge(60 * 5);
                        response.addCookie(itemNameCookie);
                        Cookie priceCookie = new Cookie("price", price);
                        priceCookie.setMaxAge(60 * 5);
                        response.addCookie(priceCookie);
                        
                        request.setAttribute("saveMessage", "Fill in all fields.");
                    }
                } 
                catch (Exception ex) 
                {
                    Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            
            case "save":
                
                Item editItem = (Item) session.getAttribute("editItem");
                String editCategoryId = request.getParameter("editCategory");
                String editItemName = request.getParameter("editItemName");
                String editPrice = request.getParameter("editPrice");
                if(editCategoryId.length() > 0 && editItemName.length() > 0 && editPrice.length() > 0)
                {
                    try
                    {
                        int categoryId = Integer.parseInt(editCategoryId);
                        double price = Double.parseDouble(editPrice);
                        is.update(editItem.getItemId(), editItemName, price, categoryId, email);
                        session.setAttribute("editItem", null);
                        
                        request.setAttribute("saveMessage", "Item updated");
                    }
                    catch(Exception ex)
                    {
                        Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else
                    {
                        request.setAttribute("saveMessage", "Fill in all fields.");
                    }
                break;
        }
        try
        {
            AccountService as = new AccountService();
            User newUser = as.getUser(email);
            session.setAttribute("user", newUser);
        }
        catch(Exception ex)
        {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
    }
}
