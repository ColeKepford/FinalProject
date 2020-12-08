<%-- 
    Document   : login
    Created on : 22-Nov-2020, 1:57:19 PM
    Author     : cocog
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Inventory</title>
    </head>
    <body>
        <h1>Home Inventory</h1>
        <h2>Login</h2>
        
        <form action="Login" method="post">
            Email: <input type="text" name="email" value="${email}"><br>
            Password: <input type="password" name="password"><br>
            <input type="submit" value="Sign in">
        </form><br>
        <a href="Register"><input type="button" value="Register"></a>
        ${message}
    </body>
</html>
