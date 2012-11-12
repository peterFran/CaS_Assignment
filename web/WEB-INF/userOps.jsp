<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--
<%-- 
    Document   : index
    Created on : Oct 22, 2012, 8:48:06 PM
    Author     : petermeckiffe
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
-->

<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.css" />
        <script type="text/javascript" src="bootstrap/js/bootstrap.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Users</title>
    </head>
    <body>
        <div class="navbar">
            <div class="navbar-inner tabbable">
                <a class="brand" href="index.jsp">CaS Assignment</a>
                <ul class="nav nav-tabs">
                    <li class="active"><a href="ListClients" data-toggle="tab">Users</a></li>
                    <li><a href="Shopping" data-toggle="tab">Shopping</a></li>
                    <li><a href="GetOrders" data-toggle="tab">Orders</a></li>
                    <li><a href="ListItems" data-toggle="tab">Items</a></li>
                </ul>
            </div>
        </div>
        <div class="container">
            <div class="hero-unit">
            	
                <legend>Users</legend>
                
                <c:forEach var="user" items="${users}">
                    <div class="well">
                        <label>Name: ${user.getFirstName()} ${user.getLastName()}</label>
                        <label>ID: ${user.getID()}</label>
                        <a href="GetOrders?id=${user.getID()}"><button type="button" class="btn">Show Customer Orders</button></a>
                    </div>
                </c:forEach>
            </div>
        </div>
    </body>
</html>