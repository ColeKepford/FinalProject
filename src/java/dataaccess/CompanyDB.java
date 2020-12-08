/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import models.Company;

/**
 *
 * @author cocog
 */
public class CompanyDB 
{
    public List<Company> getAll() throws Exception 
    {
        EntityManager em = DBUtil .getEmFactory().createEntityManager();
        
        try 
        {
            List<Company> companies = em.createNamedQuery("Company.findAll", Company.class).getResultList();
            return companies;

        } 
        finally 
        {
            em.close();
        }
    }
    
    public Company get(int index) throws Exception
    {
        EntityManager em = DBUtil .getEmFactory().createEntityManager();
        
        try 
        {
            Company company = em.find(Company.class, index);
            return company;
           
        } 
        finally 
        {
            em.close();
        }
    }
}
