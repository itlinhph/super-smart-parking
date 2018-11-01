<%-- 
    Document   : header
    Created on : Oct 30, 2018, 8:57:58 PM
    Author     : linhph
--%>
<%@page import="model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
        User user = null;
        
        if (session.getAttribute("user") != null) {
            user = (User) session.getAttribute("user");
        }
 %>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        
        <img src="resources/images/logoB.png" alt="logo" class="logo img-responsive" width="30px" height="45px">
        <div class="navbar-header">
            
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navCollapse" >
                
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            
            <a class="navbar-brand" href="index.html">My Parking</a>
        </div>
        <ul class="nav navbar-nav navbar-collapse collapse navCollapse">
            <li><a href="#">About</a></li>
            <li><a href="#">Help & Contact</a></li>
        </ul>
        <form class="navbar-form navbar-left navbar-collapse collapse navCollapse">
            <div class="input-group">
                <input type="text" class="form-control" id="searchText" placeholder="Search">
                <div class="input-group-btn" onclick="return searchText()">
                    <button class="btn btn-default">
                      <i class="glyphicon glyphicon-search"></i>
                    </button>
                </div>
            </div>
        </form>

        <!-- Nav right -->
            <ul class="nav navbar-nav navbar-right navbar-collapse collapse navCollapse">
                <% if(user ==null) { %>
                <li><a href="login.html?action=signup"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
                <li><a href="login.html"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
                
                <% } else { %>
                <li><a href="#">Hi: ${user.getFullname()}</a></li>
                <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#"> My Account<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a class="glyphicon glyphicon-open" href="#"> ManageTicket</a></li>
                        <li><a class="glyphicon glyphicon-tag" href="#"> ManageVehicle</a></li>
                        <li><a class="glyphicon glyphicon-cog" href="#"> PersonalSetting</a></li>
                        <li><a class="glyphicon glyphicon-shopping-cart" href="#"> ShowParking</a></li>
                        <li id="logout1"><a class="glyphicon glyphicon-log-out" href="${pageContext.request.contextPath}/login/logout"> LogOut</a></li>
                    </ul>
                </li>
                
                <% } %>
            </ul>

        <!-- End nav right -->

    </div>
</nav>