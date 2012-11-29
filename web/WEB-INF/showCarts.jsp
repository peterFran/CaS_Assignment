
<%-- 
    Document   : index
    Created on : Oct 22, 2012, 8:48:06 PM
    Author     : petermeckiffe
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
    <head>

        <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.css" />
        <script type="text/javascript" src="bootstrap/js/bootstrap.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <title>Items</title>
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
        <div class="hero-unit">

            <legend>Shopping Carts</legend>
                <c:forEach var="cart" items="${shoppingCarts.values()}">
                    <div class="well">
                        <label><h4>${cart.getUserName()}</h4></label>
                        <label>ID: ${cart.getUserID()}</label>
                        <label>Cart Cost: $${cart.getTotalCost()}</label>
                        <c:forEach var="item" items="${cart.getItemList()}">
                            <div class ="well">
                                <label>  Item: ${item.getName()}</label>
                                <label>  Cost: $${item.getPrice()}</label>
                                <label>  Qty: ${item.getQuantity()}</label>
                            </div>
                        </c:forEach>
                        <form method="post" action="PlaceOrder">
                            <input name="clientId" hidden="true" value="${cart.getUserID()}">
                            <button type="submit" class="btn">Place Order</button>
                        </form>
                    </div>
                </c:forEach>
            <form method="post" action="PlaceOrders">
                <button type="submit" class="btn">Place all Orders</button>
            </form>
        </div>
    </body>
</html>