<%-- 
    Document   : staffNav
    Created on : Nov 9, 2018, 12:02:11 AM
    Author     : linkpp
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        
        <img src="${pageContext.request.contextPath}/resources/images/logoParking.jpg" alt="logo" class="logo img-responsive" width="50px" height="45px">
        <div class="navbar-header">
            
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navCollapse" >
                
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            
            <a class="navbar-brand" href="${pageContext.request.contextPath}/staff/parking">My Parking</a>
        </div>
        <ul class="nav navbar-nav navbar-collapse collapse navCollapse">
            <li><a href="${pageContext.request.contextPath}/staff/parking">Parking Information</a></li>
            <li><a href="${pageContext.request.contextPath}/staff/fixplate">Fix wrong plate</a></li>
            <li><a href="${pageContext.request.contextPath}/staff/checkout">Checkout Vehicle</a></li>
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
                
                <li><a href="#">Hi: ${staff.getStaffName()}</a></li>
                <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#"> My Account<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a class="glyphicon glyphicon-tag" href="${pageContext.request.contextPath}/staff/parking"> ParkingInfor</a></li>
                        <li><a class="glyphicon glyphicon glyphicon-bed" href="${pageContext.request.contextPath}/staff/fixplate"> WrongPlate</a></li>
                        <li><a class="glyphicon glyphicon-cog" href="${pageContext.request.contextPath}/staff/checkout"> CheckoutVehicle</a></li>
                        <li id="logout1"><a class="glyphicon glyphicon-log-out" href="${pageContext.request.contextPath}/login/logout"> LogOut</a></li>
                    </ul>
                </li>
                
            </ul>

        <!-- End nav right -->

    </div>
</nav>