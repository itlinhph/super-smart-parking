<%-- 
    Document   : navLeft
    Created on : Oct 31, 2018, 11:11:00 AM
    Author     : linhph
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- NAV LEFT -->
<div class="col-xs-3 col-sm-2 col-md-3 col-lg-3 well navLeft">
    <h3> My Account</h3>
    <hr>
    <ul class="nav nav-pills nav-stacked selectCategoryMenu">
        <li id="ticketmenu" class="selectCategory"> <a href="${pageContext.request.contextPath}/user/tiket" class="glyphicon glyphicon-tag cName"> Ticket History</a></li>
        <li id="vehiclemenu" class="selectCategory"> <a href="${pageContext.request.contextPath}/user/vehicle" class="glyphicon glyphicon glyphicon-bed cName"> Manage Vehicle</a></li>
        <li id="userInformenu" class="selectCategory"> <a href="${pageContext.request.contextPath}/user/userInfor" class="glyphicon glyphicon-cog cName"> Personal Setting</a></li>
        <li id="parkmenu" class="selectCategory"> <a href="#" class="glyphicon glyphicon glyphicon-info-sign cName"> Parking Information</a></li>
        <li id="reportmenu" class="selectCategory"> <a href="#" class="glyphicon glyphicon glyphicon-bullhorn cName"> Report </a></li>

    </ul>
</div>
<!-- END NAV LEFT -->

<script>
    
    $('#${menuitem}').addClass('active');
    
</script>