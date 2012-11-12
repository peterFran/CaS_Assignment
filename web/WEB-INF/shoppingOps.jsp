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
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <title>Shopping</title>
    </head>
    <body>
        <div class="navbar">
            <div class="navbar-inner tabbable">
                <a class="brand" href="index.jsp">CaS Assignment</a>
                <ul class="nav nav-tabs">
                    <li><a href="ListClients" data-toggle="tab">Users</a></li>
                    <li class="active"><a href="Shopping" data-toggle="tab">Shopping</a></li>
                    <li><a href="GetOrders" data-toggle="tab">Orders</a></li>
                    <li><a href="ListItems" data-toggle="tab">Items</a></li>
                </ul>
                <form class="navbar-form pull-right" method="post" action="Shopping">
                    <select id="cartSelect" name="client">
                        <c:forEach items="${clients}" var="cl">
                            <option value="${cl.getID()}">${cl.getFirstName()} ${cl.getLastName()}</option>
                        </c:forEach>
                    </select>
                    <button type="submit" class="btn">Get cart</button>
                </form>

            </div>
        </div>
        <c:choose>
            <c:when test="${currentClient==null}">
                <div class="hero-unit">
                    <h1>Please choose a client from the menu bar.</h1>
                </div>
            </c:when>
            <c:otherwise>
                <div class="container">
                    <div class="row-fluid">
                        <div class="span3">
                            <div class="well sidebar-nav">
                                <ul class="nav nav-list">
                                    <li class="nav-header">Cart</li>
                                    <c:forEach items="${cart.getItemList()}" var="item">
                                        <label>ID: ${item.getID()}</label>
                                        <label>Name: ${item.getName()}</label>
                                        <label>Quantity: ${item.getQuantity()}</label>
                                        <label>Price: £${item.getPrice()}</label>
                                        <br/>
                                    </c:forEach>
                                    <label>Subtotal: £${cart.getTotalCost()}</label><br/>

                                </ul>
                            </div><!--/.well -->
                        </div><!--/span-->
                        <div class="span9">
                            <div class="hero-unit">
                                <fieldset>
                                    <legend>${currentClient.getFirstName()} ${currentClient.getLastName()}'s cart</legend>
                                    <c:forEach items="${items}" var="item">
                                        <form action="AddToCart" method="post">
                                            <div class="well">
                                                <label><h4>${item.getName()}</h4></label>
                                                <label>ID: ${item.getID()}</label>
                                                <label>Price: £${item.getPrice()}</label>
                                                <input hidden="true" name="itemId" value="${item.getID()}">
                                                <input hidden="true" name="userId" value="${currentClient.getID()}">
                                                <input type="number" name="quantity" placeholder="Quantity" min="1"><br/>
                                                <button type="submit" class="btn">Update</button>
                                            </div>
                                        </form>
                                    </c:forEach>
                                </fieldset>

                                <form method="post" action="PlaceOrder">
                                    <button type="submit" class="btn">Check Out Order</button>
                                </form>
                            </div>

                        </div><!--/span-->
                    </div><!--/row-->
                </div>
            </c:otherwise>
        </c:choose>

    </body>
</html>