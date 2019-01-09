<%-- 
    Document   : staffPark
    Created on : Nov 8, 2018, 8:49:23 PM
    Author     : linhph
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width initial-scale=1">
    <title>Parking information</title>

    <link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-3.1.1.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/script.js" type="text/javascript"></script>

</head>

<body>
    <!-- navbar -->
    <jsp:include page="staffNav.jsp"></jsp:include>
    <!-- End message -->
    <jsp:include page="../partition/message.jsp"></jsp:include>
    <!-- Content -->
    <div class="content container">
        <div class="row">
            <div class="col-md-12">
                <hr>
                <div class="titleParking">
                    ${park.getParkCode()} has only ${park.getTotalSlot()- park.getUsingSlot()} remain slot!
                </div>
                <table class="table table-bordered table-hover table-striped">
                    <thead>
                        <tr>
                            <th>#No</th>
                            <th>Park Code</th>
                            <th>Park Name</th>
                            <th>Total Slot</th>
                            <th>Using Slot</th>
                            <th>Description</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>${park.getId()}</td>
                            <td>${park.getParkCode()}</td>
                            <td>${park.getParkName()}</td>
                            <td class="totalSlot">${park.getTotalSlot()}</td>
                            <td>${park.getUsingSlot()}</td>
                            <td>${park.getDescription()}</td>
                        </tr>
                    </tbody>
                </table>
                <hr>
                <div id="tiketToday">
                    <div class="titleParking">
                        Ticket in Your Parking today:
                    </div>
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
                            </tr>
                        </thead>
                        <tbody>

                            <c:forEach var="ticket" items="${listTicket}">
                                <tr>
                                    <td>${ticket.getTicketid()}</td>
                                    <td>${ticket.getPlate()}</td>
                                    <td>${ticket.getPark()}</td>
                                    <td>****</td>
                                    <td class="statusTicket">${ticket.getStatus()}</td>
                                    <td>${ticket.getFee()}</td>
                                    <td>${ticket.getCheckinTime()}</td>
                                    <td>${ticket.getCheckoutTime()}</td>
                                </tr>
                            </c:forEach>

                        </tbody>
                    </table>
                </div>
            </div>
            <hr>
        </div>
    </div>
    <!-- End container -->
    <script>

        $(".totalSlot").each(function () {
            var totalSlot = $(this).html();
            var usingSlot = $(this).next().html();
            var remainSlot = totalSlot - usingSlot;
            if (remainSlot < 10) {
                $(this).parent().css("color", "red").css("font-weight", "bold");
                $(this).next().css("font-size", "120%");

            }
            else
                $(this).parent().css("color", "blue");
        });

        $(".statusTicket:contains('working')").parent().css("color", "red");

    </script>


</body>

</html>