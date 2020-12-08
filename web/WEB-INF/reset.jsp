<%-- 
    Document   : reset
    Created on : 30-Nov-2020, 12:33:00 AM
    Author     : cocog
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Notes Reset Password</title>
    </head>
     <h1>Reset Password</h1>
        <p>Enter your email address to reset your password</p>
        <form method="post" action="Reset">
            Email Address <input type="text" name="resetEmail" value="${resetEmail}">
            <br>
            <input type="submit" value="submit">
        </form>
            <a href="Login">Login</a>
    </body>
</html>