<%-- 
    Document   : userTiket
    Created on : Nov 7, 2018, 8:37:31 PM
    Author     : linhph
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                <div class="col-md-9">
                    <table class="table table-bordered table-hover table-striped">
                        <thead>
                            <tr>
                                <th>#No</th>
                                <th>Plate</th>
                                <th>Park</th>
                                <th>Ticket Code</th>
                                <th>Status</th>
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
                                    <td>${ticket.getTicketCode()}</td>
                                    <td>${ticket.getStatus()}</td>
                                    <td>${ticket.getCheckinTime()}</td>
                                    <td>${ticket.getCheckoutTime()}</td>
                                </tr>
                            </c:forEach> 
                           
                        </tbody>
                    </table>
                </div>

            </div>
        </div> 
        <!-- End container -->


    </body>
</html>
