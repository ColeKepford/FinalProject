package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import models.Role;
/*
Only contains the getAll() method
*/
public class RoleDB 
{
    
    public List<Role> getAll() throws Exception 
    {
        EntityManager em = DBUtil .getEmFactory().createEntityManager();
        
        try 
        {
            List<Role> roles = em.createNamedQuery("Role.findAll", Role.class).getResultList();
            return roles;

        } 
        finally 
        {
            em.close();
        }
    }
    
    public Role get(int index) throws Exception
    {
         EntityManager em = DBUtil .getEmFactory().createEntityManager();
        
        try 
        {
            Role role = em.find(Role.class, index);
            return role;
           
        } 
        finally 
        {
            em.close();
        }
    }
}
