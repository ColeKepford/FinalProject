<%-- 
    Document   : register
    Created on : 6-Dec-2020, 2:52:25 PM
    Author     : cocog
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Inventory Register</title>
    </head>
    <body>
        <h1>Home Inventory Register</h1>
        <h2>Register Here</h2>
        
        <form action="Register" method="post">
            Email:<input type="text" name="email"><br>
            First Name:<input type="text" name="firstName"><br>
            Last Name:<input type="text" name="lastName"><br>
            Company: <select name="company">
                <c:forEach items="${companies}" var="company">
                    <option value="${company.companyId}">${company.companyName}</option>
                </c:forEach>
            </select><br>
            Password:<input type="password" name="password1"><br>
            Confirm Password:<input type="password" name="password2"><br>
            <input type="submit" value="Register"><br>
        </form>
        ${message}
    </body>
</html>
