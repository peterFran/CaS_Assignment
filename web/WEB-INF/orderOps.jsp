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
    	<link rel="stylesheet" type="text/css" href="datepicker/css/datepicker.css" />
    	<script type="text/javascript" src="datepicker/js/datepicker.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Orders</title>
    </head>
    <body>
        <div class="navbar">
		  <div class="navbar-inner tabbable">
		    <a class="brand" href="index.jsp">CaS Assignment</a>
		    <ul class="nav nav-tabs">
		      <li><a href="ListClients" data-toggle="tab">Users</a></li>
		      <li><a href="Shopping" data-toggle="tab">Shopping</a></li>
		      <li class="active"><a href="GetOrders" data-toggle="tab">Orders</a></li>
		      <li><a href="ListItems" data-toggle="tab">Items</a></li>
		    </ul>

		  </div>
		</div>
		<div class="container">
		  <div class="hero-unit">
			<form>
			  <fieldset>
			    <legend>Find orders by user</legend>
			    <div class="well">
			    	<select id="cartSelect">
                                    <c:forEach items="${clients}" var="cl">
                                        <option value="${cl.getID()}">${cl.getFirstName()} ${cl.getLastName()}</option>
                                    </c:forEach>
		            </select>
			    </div>
			    <button type="submit" class="btn">Submit</button>
			  </fieldset>
			</form>
		  </div>
		</div>
    </body>
</html>