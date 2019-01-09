<%-- 
    Document   : userTiket
    Created on : Nov 7, 2018, 8:37:31 PM
    Author     : linhph
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width initial-scale=1">
        <title>Ticket</title>

        <link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css">
        <script src="${pageContext.request.contextPath}/resources/js/jquery-3.1.1.min.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/resources/js/script.js" type="text/javascript"></script>

    </head>
    <body>
        <!-- navbar -->
        <jsp:include page="../partition/navbar.jsp"></jsp:include>
            <!-- End message -->
        <jsp:include page="../partition/message.jsp"></jsp:include>
            <!-- Content -->
            <div class="content container">

            <div class="row">
                <!-- NAV LEFT -->
                <jsp:include page="../partition/navLeft.jsp"></jsp:include>
                    <!-- END NAV LEFT -->
                <div class="col-md-9 contentRight">
                    <br>
                    <h3>Your ticket history over all time:</h3>
                    <hr>
                    <table class="table table-bordered table-hover table-striped">
                        <thead>
                            <tr>
                                <th>#No</th>
                                <th>Plate</th>
                                <th>Park</th>
                                <th>Ticket Code</th>
                                <th>Status</th>
                                <th>Fee</th>
                                <th>Checkin time</th>
                                <th>Checkout time</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            
                            <c:forEach var="ticket" items="${listTicket}">
                                <tr>
                                    <td>${ticket.getTicketid()}</td>
                                    <td>${ticket.getPlate()}</td>
                                    <td>${ticket.getPark()}</td>
                                    <td>${ticket.getTicketCode()}</td>
                                    <td class="statusTicket">${ticket.getStatus()}</td>
                                    <td>${ticket.getFee()}</td>
                                    <td>${ticket.getCheckinTime()}</td>
                                    <td>${ticket.getCheckoutTime()}</td>
                                    <td><a href="../report/userReport?ticket=${ticket.getTicketid()}" data-toggle="modal" class="btn btn-warning btnSetstatus ${ticket.getStatus()=="working"? "hide":""}">Report</a></td>
                                </tr>
                            </c:forEach> 
                           
                        </tbody>
                    </table>
                </div>

            </div>
        </div> 
        <!-- End container -->
        <script>
            $(".statusTicket:contains('working')").parent().css("color", "red");
        </script>

    </body>
</html>
