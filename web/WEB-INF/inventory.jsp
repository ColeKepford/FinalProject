<%-- 
    Document   : inventory
    Created on : 22-Nov-2020, 1:57:13 PM
    Author     : cocog
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Inventory</title>
    </head>
    <body>
        <h1>Home Inventory for </h1>
        <h3>Menu</h3>
        
        <ul>
            <li><a href="Inventory">Inventory</a></li>
            <li><a href="Account">Account</a></li>
            <li><a href="Admin">Admin</a></li>
            <li><a href="CompanyAdmin">Company Admin</a></li>
            <li><a href="?action=logout">Logout</a></li>
        </ul>
        
        <h2>Inventory for ${user.firstName} ${user.lastName}</h2>
        
        <table border = "1" width = "40%">
            <tr>
                <th>Category</th>
                <th>Name</th>
                <th>Price</th>
                <th></th>
            </tr>
            <c:forEach items="${user.itemList}" var="item">
                <tr>
                    <td>${item.category.categoryName}</td>
                    <td>${item.itemName}</td>
                    <td>${item.price}</td>
                    <td>
                        <a href="Inventory?action=delete&amp;itemId=${item.itemId}"><input type="button" value="Delete"></a>
                        <a href="Inventory?action=edit&amp;itemId=${item.itemId}"><input type="button" value="Edit"></a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <c:if test="${editItem == null}">
            <h3>Add Item</h3>
    
            <form action="Inventory" method="post">
                Category: <select name="category">
                    <c:forEach items="${categories}" var="category">
                        <option value="${category.categoryId}">${category.categoryName}</option>
                    </c:forEach>
                </select><br>
                Name: <input type="text" name="itemName" value="${itemNameField}"><br>
                Price: <input type="number" name="price" value="${priceField}"><br>
                <input type="hidden" name="action" value="add">
                <input class="add" type="submit" value="Add">
            </form>
        </c:if>
            
            <c:if test="${editItem != null}">
            <h3>Edit Item</h3>
    
            <form action="Inventory" method="post">
                Category: <select name="editCategory">
                    <c:forEach items="${categories}" var="category">
                        <c:if test="${category == editItem.category}">
                            <option value="${category.categoryId}" selected>${category.categoryName}</option>
                        </c:if>
                        <c:if test="${category != editItem.category}">
                            <option value="${category.categoryId}">${category.categoryName}</option>
                        </c:if>
                    </c:forEach>
                </select><br>
                Name: <input type="text" name="editItemName" value="${editItem.itemName}"><br>
                Price: <input type="number" name="editPrice" value="${editItem.price}"><br>
                <input type="hidden" name="action" value="save">
                <input class="save" type="submit" value="Save">
            </form>
        </c:if>
    
    ${saveMessage}
        
    </body>
</html>
