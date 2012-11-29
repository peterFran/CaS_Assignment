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
                    <%@include file="/WEB-INF/jspf/menuBar.jspf"%>
                </ul>
                <form class="navbar-form pull-right" method="post" action="Shopping">
                    <select id="cartSelect" name="clientId">
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
                                    <li class="nav-header"><h4>Cart</h4></li>
                                    <c:forEach items="${cart.getItemList()}" var="item">
                                        <div class="well">
                                            <label>Item: ${item.getName()}</label>
                                            <label>Quantity: ${item.getQuantity()}</label>
                                            <label>Price: $${item.getPrice()}</label>
                                        </div>
                                        
                                    </c:forEach>
                                    <label>Subtotal: $${cart.getTotalCost()}</label><br/>

                                </ul>
                                <form method="post" action="PlaceOrder">
                                    <input hidden="true" name="clientId" value="${currentClient.getID()}">
                                    <button type="submit" class="btn">Check Out Order</button>
                                </form>
                                <form method="post" action="ClearCart">
                                    <input hidden="true" name="clientId" value="${currentClient.getID()}">
                                    <button type="submit" class="btn">Clear Cart</button>
                                </form>
                            </div><!--/.well -->
                        </div><!--/span-->
                        <div class="span9">
                            <div class="hero-unit">
                                <fieldset>
                                    <legend>Add Items : ${currentClient.getFirstName()} ${currentClient.getLastName()}</legend>
                                    <c:forEach items="${items}" var="item">
                                        <form action="AddToCart" method="post">
                                            <div class="well">
                                                <label><h4>${item.getName()}</h4></label>
                                                <label>ID: ${item.getID()}</label>
                                                <label>Price: $${item.getPrice()}</label>
                                                <input hidden="true" name="itemId" value="${item.getID()}">
                                                <input hidden="true" name="userId" value="${currentClient.getID()}">
                                                <input type="number" name="quantity" placeholder="Quantity" min="1"><br/>
                                                <input type="submit" class="btn" name="inc" value="+1">
                                                <input type="submit" class="btn" name="dec" value="-1">
                                                <button type="submit" class="btn">Update</button>
                                            </div>
                                        </form>
                                    </c:forEach>
                                </fieldset>


                            </div>

                        </div><!--/span-->
                    </div><!--/row-->
                </div>
            </c:otherwise>
        </c:choose>

    </body>
</html>