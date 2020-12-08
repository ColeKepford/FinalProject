<%-- 
    Document   : companyadmin
    Created on : 7-Dec-2020, 4:56:23 PM
    Author     : cocog
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Inventory ${user.company.companyName} Admin</title>
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
         
         <h2>Manage Users for ${user.firstName} ${user.lastName} of ${company.companyName}</h2>
         
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
                        <a href="CompanyAdmin?action=delete&amp;email=${listUser.email}"><input type="button" value="Delete"></a>
                    </td>
                    <td>
                        <a href="CompanyAdmin?action=edit&amp;email=${listUser.email}"><input type="button" value="Edit"></a>
                    </td>
                    <td>
                        <c:if test="${listUser.active == false}">
                            <a href="CompanyAdmin?action=activate&amp;email=${listUser.email}"><input type="button" value="Activate"></a>
                        </c:if>
                        
                        <c:if test="${listUser.active == true}">
                        <a href="CompanyAdmin?action=deactivate&amp;email=${listUser.email}"><input type="button" value="Deactivate"></a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
                        
        <c:if test="${editUser == null}">
        <h3>Add User</h3>

        <form action="CompanyAdmin" method="post">
            Email: <input type="text" name="emailField"><br>
            First Name: <input type="text" name="fName"><br>
            Last Name: <input type="text" name="lName"><br>
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

        <form action="CompanyAdmin" method="post">
            Email: <input type="text" name="editEmailField" value="${editUser.email}" readonly><br>
            First Name: <input type="text" name="editFName" value="${editUser.firstName}"><br>
            Last Name: <input type="text" name="editLName" value="${editUser.lastName}"><br>
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
    </body>
</html>
