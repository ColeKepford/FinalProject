<%-- 
    Document   : admin
    Created on : 22-Nov-2020, 1:56:57 PM
    Author     : cocog
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Inventory Admin</title>
    </head>
    <body>
        <h1>Home Inventory</h1>
        
        <h3>Menu</h3>
        
        <ul>
            <li><a href="Inventory">Inventory</a></li>
            <li><a href="Account">Account</a></li>
            <li><a href="Admin">Admin</a></li>
            <li><a href="CompanyAdmin">Company Admin</a></li>
            <li><a href="?action=logout">Logout</a></li>
        </ul>
        
        <h2>Manage System for ${user.firstName} ${user.lastName}</h2>
        
         
        <table border = "1" width = "50%">
            <tr>
                <th>Email</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Company</th>
                <th>Role</th>
                <th>Delete</th>
                <th>Edit</th>
                <th>Activate/Deactivate</th>
            </tr>
            <c:forEach items="${users}" var="listUser">
                <tr>
                    <td>${listUser.email}</td>
                    <td>${listUser.firstName}</td>
                    <td>${listUser.lastName}</td>
                    <td>${listUser.company.companyName}</td>
                    <td>${listUser.role.roleName}</td>
                    <td>
                        <a href="Admin?action=delete&amp;email=${listUser.email}"><input type="button" value="Delete"></a>
                    </td>
                    <td>
                        <a href="Admin?action=edit&amp;email=${listUser.email}"><input type="button" value="Edit"></a>
                    </td>
                    <td>
                        <c:if test="${listUser.active == false}">
                            <a href="Admin?action=activate&amp;email=${listUser.email}"><input type="button" value="Activate"></a>
                        </c:if>
                        
                        <c:if test="${listUser.active == true}">
                        <a href="Admin?action=deactivate&amp;email=${listUser.email}"><input type="button" value="Deactivate"></a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    
    <c:if test="${editUser == null}">
        <h3>Add User</h3>

        <form action="Admin" method="post">
            Email: <input type="text" name="emailField"><br>
            First Name: <input type="text" name="fName"><br>
            Last Name: <input type="text" name="lName"><br>
            Company: <select name="company">
                <c:forEach items="${companies}" var="company">
                    <option value="${company.companyId}">${company.companyName}</option>
                </c:forEach>
            </select><br>
            Role: <select name="role">
                <c:forEach items="${roles}" var="role">
                    <option value="${role.roleId}">${role.roleName}</option>
                </c:forEach>
            </select><br>
            Password: <input type="text" name="password"><br>
            <input type="hidden" name="action" value="add">
            <input class="add" type="submit" value="Add">
        </form>
    </c:if>
    
    <c:if test="${editUser != null}">
        <h3>Edit User: ${editUser.firstName} ${editUser.lastName}</h3>

        <form action="Admin" method="post">
            Email: <input type="text" name="editEmailField" value="${editUser.email}" readonly><br>
            First Name: <input type="text" name="editFName" value="${editUser.firstName}"><br>
            Last Name: <input type="text" name="editLName" value="${editUser.lastName}"><br>
            Company: <select name="editCompany">
                <c:forEach items="${companies}" var="company">
                    <c:if test="${company == user.company}">
                        <option value="${company.companyId}" selected>${company.companyName}</option>
                    </c:if>
                    <c:if test="${company != user.company}">
                        <option value="${company.companyId}">${company.companyName}</option>
                    </c:if>
                </c:forEach>
            </select><br>
            Role: <select name="editRole">
               <c:forEach items="${roles}" var="role">
                    <c:if test="${role == user.role}">
                        <option value="${role.roleId}" selected>${role.roleName}</option>
                    </c:if>
                    <c:if test="${role != user.role}">
                        <option value="${role.roleId}">${role.roleName}</option>
                    </c:if>
                </c:forEach>
            </select><br>
            Password: <input type="text" name="editPassword" value="${editUser.password}"><br>
            <input type="hidden" name="action" value="save">
            <input class="save" type="submit" value="Save">
        </form>
    </c:if>
        ${message}
        <h3>Categories</h3>
        <table border = "1" width = "15%">
            <tr>
                <th>Category</th>
                <th>Edit</th>
            </tr>
            <c:forEach items="${categories}" var="category">
                <tr>
                    <td>${category.categoryName}</td>
                    <td><a href="Admin?action=editCategory&amp;categoryId=${category.categoryId}"><input type="button" value="Edit"></a></td>
                </tr>
            </c:forEach>
        </table>
        
        <c:if test="${editCategory == null}">
            <h3>Add Category</h3>

            <form action="Admin" method="post">
                Category: <input type="text" name="categoryName"><br>
                <input type="hidden" name="action" value="addCategory">
                <input class="addCategory" type="submit" value="Add">
            </form>
        </c:if>
            
        <c:if test="${editCategory != null}">
           <h3>Edit Category: ${editCategory.categoryName}</h3>

           <form action="Admin" method="post">
               Category: <input type="text" name="editCategoryName" value="${editCategory.categoryName}"><br>
               <input type="hidden" name="action" value="saveCategory">
               <input class="saveCategory" type="submit" value="Save">
           </form>
       </c:if>
        
    ${categoriesMessage}
    </body>
</html>
