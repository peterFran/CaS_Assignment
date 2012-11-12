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
        <title>Welcome</title>
    </head>
    <body>
        <div class="navbar">
		  <div class="navbar-inner tabbable">
		    <a class="brand" href="index.jsp">CaS Assignment</a>
		    <ul class="nav nav-tabs">
		      <li><a href="ListClients" data-toggle="tab">Users</a></li>
		      <li><a href="Shopping" data-toggle="tab">Shopping</a></li>
		      <li><a href="GetOrders" data-toggle="tab">Orders</a></li>
		      <li><a href="ListItems" data-toggle="tab">Items</a></li>
		    </ul>
		    
		  </div>
		</div>
		<div class="container">
		  <div class="hero-unit">
			<form action="AddClient" method="post">
			  <fieldset>
			    <legend>Add customer</legend>
				    <div class="well">
				    <span>First Name
				    <input type="text" name="first" placeholder="First name"></span><br/>
				    <span>Last Name
				    <input type="text" name="last" placeholder="Last name"></span><br/>
			    </div>
			    <button type="submit" class="btn">Submit</button>
			  </fieldset>
			</form>
		  </div>
		</div>
    </body>
</html>
