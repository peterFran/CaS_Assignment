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
                    <%@include file="/WEB-INF/jspf/menuBar.jspf"%>
                </ul>
            </div>
        </div>
        <div class="container">
            <div class="container">
                <div class="row-fluid">
                    <div class="span4">
                        <div class="well sidebar-nav">
                            <li class="nav-header">Add Client</li>
                            <form action="AddClient" method="post">
                                <fieldset>

                                    <span>First Name
                                        <input type="text" name="first" placeholder="First name"></span><br/>
                                    <span>Last Name
                                        <input type="text" name="last" placeholder="Last name"></span><br/>
                                    <button type="submit" class="btn">Submit</button>
                                </fieldset>
                            </form>
                        </div>

                    </div>

                    <div class="span8">
                        <div class="hero-unit">

                            <legend>Users</legend>

                            <c:forEach var="user" items="${users}">
                                <div class="well">
                                    <label>Name: ${user.getFirstName()} ${user.getLastName()}</label>
                                    <label>ID: ${user.getID()}</label>
                                    <a href="GetOrders?client=${user.getID()}"><button type="button" class="btn">Show Customer Orders</button></a><br/>
                                    <form method="post" action="RemoveUser">
                                        <input name="clientId" hidden="true" value="${user.getID()}">
                                        <button type="submit" class="btn">Remove Client</button>
                                    </form>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>