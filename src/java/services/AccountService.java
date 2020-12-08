/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dataaccess.CategoriesDB;
import dataaccess.ItemsDB;
import dataaccess.RoleDB;
import dataaccess.UserDB;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import models.Category;
import models.Company;
import models.Item;
import models.Role;
import models.User;

/**
 *
 * @author cocog
 */
public class AccountService 
{
     public User login(String email, String password) 
     {
        UserDB userDB = new UserDB();
        
        try 
        {
            User user = userDB.get(email);
            if (password.equals(user.getPassword()) && user.getActive() == true) 
            {
                return user;
            }
        } 
        catch (Exception e) 
        {
            
        }
        
        return null;
    }
     
     public boolean resetPassword(String email, String path, String url){
        String uuid = UUID.randomUUID().toString();
        String link = url + "?uuid=" + uuid;
        UserDB userDB = new UserDB();
        
        try{
            User user = userDB.get(email);
            user.setResetPasswordUuid(uuid);
            userDB.update(user);
            
            String to = email;
            String subject = "Password reset for " + user.getEmail();
            String template = path + "/emailtemplates/resetpassword.html";
            
            HashMap<String, String> tags = new HashMap<>();
            tags.put("firstname", user.getFirstName());
            tags.put("lastname", user.getLastName());
            tags.put("link", link);
            
            GmailService.sendMail(to, subject, template, tags);
            return true;
        } catch (Exception e){
            return false;
        }
    }
    
    public boolean changePassword(String uuid, String password) {
        UserDB userDB = new UserDB();
        try {
            User user = userDB.getByUUID(uuid);
            user.setPassword(password);
            user.setResetPasswordUuid(null);
            userDB.update(user);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
     
    public List<User> getAllUsers() throws Exception
     {
        UserDB userDB = new UserDB();
        List<User> users = userDB.getAll();
        return users;
     }
         
    public User getUser(String email) throws Exception
    {
       UserDB userDB = new UserDB();
       User user = userDB.get(email);
       return user;
    }

    public List<Role> getAllRoles() throws Exception
    {
        RoleDB roleDB = new RoleDB();
        List<Role> roles = roleDB.getAll();
        return roles;
    }
    
    public Role getRole(int index) throws Exception
    {
        RoleDB roleDB = new RoleDB();
        Role role = roleDB.get(index);
        return role;
    }
     
     public void insert(String email, boolean active, String firstName, String lastName, String password, Company company, Role role) throws Exception 
    {
        User user = new User(email, active, firstName, lastName, password, role, company);
        
        UserDB userDB = new UserDB();
        userDB.insert(user);
    }
     
     public void update(String email, boolean active, String firstName, String lastName, String password, Company company, Role role) throws Exception
     {
         UserDB userDB = new UserDB();
         User user = userDB.get(email);
         
         user.setActive(active);
         user.setFirstName(firstName);
         user.setLastName(lastName);
         user.setPassword(password);
         user.setCompany(company);
         user.setRole(role);
         
         userDB.update(user);
     }
     
     public void updateActive(String email, boolean active) throws Exception
     {
         UserDB userDB = new UserDB();
         User user = userDB.get(email);
         
         user.setActive(active);
         userDB.update(user);
     }
     
     public void delete(String email) throws Exception
     {
         UserDB userDB = new UserDB();
         User user = userDB.get(email);
         ItemsDB itemDB = new ItemsDB();
         List<Item> items = user.getItemList();
         for(Item item: items)
         {
             itemDB.delete(item);
         }
         
         if(user.getRole().getRoleId() != 1 || user.getRole().getRoleId() != 3)
         {
             userDB.delete(user);
         }
     }
}
