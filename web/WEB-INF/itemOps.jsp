
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
        <div class="container-fluid">
            <div class="row-fluid">
                <div class ="span3">
                <form method="post" action="AddItem">
                    <fieldset>
                        <legend>Add Item</legend>
                        <label>Item Name</label>
                        <input name="name" type="text" placeholder="Item name">
                        <label>Price</label>
                        <span>$ <input name="price" type="number" step="0.01" placeholder="Price"></span>
                        <button type="submit" class="btn">Submit</button>
                    </fieldset>
                </form>
                </div>
                <div class="span9">
                <legend>Existing Items</legend>
                <c:forEach var="item" items="${items}">
                    <div class="well">
                        <label><h4>${item.getName()}</h4></label>
                        <label>ID: ${item.getID()}</label>
                        <label>Price: $${item.getPrice()}</label>
                        <form method="post" action="ChangeItemPrice">
	                        <fieldset>
	                        	<input hidden="true" name="itemId" value="${item.getID()}">
		                        <span>Change price: $<input name="price" type="number" step="0.01" placeholder="Price"></span>
		                        <button type="submit" class="btn">Submit</button>
	                        </fieldset>
                        </form>
                                        <form method="post" action="DeleteItem">
	                        <fieldset>
	                        	<input hidden="true" name="itemId" value="${item.getID()}">
		                        <button type="submit" class="btn">Delete Item</button>
	                        </fieldset>
                        </form>
                    </div>
                </c:forEach>
                </div>
            </div>
        </div>
    </body>
</html>