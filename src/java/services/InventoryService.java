/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dataaccess.CategoriesDB;
import dataaccess.CompanyDB;
import dataaccess.ItemsDB;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Category;
import models.Company;
import models.Item;

/**
 *
 * @author cocog
 */
public class InventoryService 
{
    public Item getItem(int id) throws Exception 
    {
        ItemsDB itemsDB = new ItemsDB();
        Item item = itemsDB.get(id);
        return item;
    }
    
    public List<Item> getAllItems(String email) throws Exception 
    {
        ItemsDB itemsDB = new ItemsDB();
        List<Item> items = itemsDB.getAll(email);
        return items;
    }
    
    public List<Category> getAllCategories() throws Exception
    {
        CategoriesDB categoriesDB = new CategoriesDB();
        List<Category> categories = categoriesDB.getAll();
        return categories;
    }
    
    public Category getCategory(int id) throws Exception
    {
        CategoriesDB categoriesDB = new CategoriesDB();
        Category category = categoriesDB.get(id);
        return category;
    }
    
    public List<Company> getallCompanies() throws Exception
    {
        CompanyDB companyDB = new CompanyDB();
        List<Company> companies = companyDB.getAll();
        return companies;
    }
    
    public Company getCompany(int index) throws Exception
    {
         CompanyDB companyDB = new CompanyDB();
         Company company = companyDB.get(index);
         return company;
    }
    
    public void insert(Item item) throws Exception 
    {
        ItemsDB itemsDB = new ItemsDB();
        itemsDB.insert(item);
    }
    
    public void insertCategory(Category category) throws Exception
    {
        CategoriesDB catDB = new CategoriesDB();
        catDB.insert(category);
    }
    
    public void update(int itemId, String itemName, double price, int categoryId, String email) throws Exception 
    {
        ItemsDB itemsDB = new ItemsDB();
        Item item = itemsDB.get(itemId);
        if(item.getOwner().getEmail().equals(email))
        {
            CategoriesDB categoriesDB = new CategoriesDB();
            Category category = categoriesDB.get(categoryId);

            item.setItemName(itemName);
            item.setPrice(price);
            item.setCategory(category);

            itemsDB.update(item);
        }
    }
    
    public void updateCategory(int categoryId, String categoryName)
    {
        try 
        {
            CategoriesDB catDB = new CategoriesDB();
            Category category = catDB.get(categoryId);
            
            category.setCategoryName(categoryName);
            
            catDB.update(category);
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(InventoryService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(int itemId, String email) throws Exception 
    {
        ItemsDB itemsDB = new ItemsDB();
        Item item = itemsDB.get(itemId);
        
       if(item.getOwner().getEmail().equals(email))
       {
           itemsDB.delete(item);
       }
    }
}
