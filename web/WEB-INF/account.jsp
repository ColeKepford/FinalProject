<%-- 
    Document   : account
    Created on : 6-Dec-2020, 4:03:04 PM
    Author     : cocog
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inventory Account</title>
    </head>
    <body>
        <h1>Inventory Account</h1>
        <h2>Menu</h2>
        
        <ul>
            <li><a href="Inventory">Inventory</a></li>
            <li><a href="Account">Account</a></li>
            <li><a href="Admin">Admin</a></li>
            <li><a href="CompanyAdmin">Company Admin</a></li>
            <li><a href="?action=logout">Logout</a></li>
        </ul>
        
        <h3>Account Information for ${user.firstName} ${user.lastName}</h3>
        <form action="Account" method="post">
            Email:<input type="text" name="email" value="${user.email}" readonly><br>
            First Name:<input type="text" name="firstName" value="${user.firstName}"><br>
            Last Name:<input type="text" name="lastName" value="${user.lastName}"><br>
            Password:<input type="password" name="password1" value="${user.password}"><br>
            Confirm Password:<input type="password" name="password2" value="${user.password}"><br>
            <input type="submit" value="Save"><br>
        </form>
        <h2>Deactivate</h2>
        <a href="Account?action=deactivate"><input type="button" value="Deactivate"></a>
        ${message}
    </body>
</html>
