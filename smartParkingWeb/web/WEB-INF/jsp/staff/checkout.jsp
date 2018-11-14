<%-- 
    Document   : checkout
    Created on : Nov 13, 2018, 10:58:45 PM
    Author     : linkpp
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width initial-scale=1">
        <title>Checkout Vehicle</title>

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
                   
                        <div class="row">

                            <div class="col-sm-6 col-md-4 col-lg-4">

                                <div class="table-bordered">
                                    <img src="${pageContext.request.contextPath}/${vehicle.getImg()}" class="img-display table-bordered img-responsive">
                                    
                                </div>
                                <span class="labelImgCheckOut">Registered Image</span>
                            </div>
                            <div class="col-sm-6 col-md-4 col-lg-4">
                                
                                <div class="table-bordered">
                                    <img src="${pageContext.request.contextPath}/resources/images/${checkoutImg}" class="img-display table-bordered img-responsive">
                                </div>
                                <span class="labelImgCheckOut">Checkout Image</span>
                            </div>
                            <div class="col-sm-6 col-md-4 col-lg-4">
                                <form role="form" method="POST" action="checkoutVehicle">
                                <div class="table-bordered well">
                                    <div class="input-group">
                                        <span class="input-group-addon label-vehicle" > Status</span>
                                        <input class="form-control"  disabled value="working">
                                    </div>
                                    <div class="input-group">
                                        <span class="input-group-addon label-vehicle" > Checkin</span>
                                        <input class="form-control"  disabled value="${ticket.getCheckinTime()}">
                                    </div>
                                    <div class="input-group">
                                        <span class="input-group-addon label-vehicle" > Ticket</span>
                                        <input class="form-control"  disabled value="${ticket.getTicketCode()}">
                                    </div>
                                    <div class="input-group">
                                        <span class="input-group-addon label-vehicle" > Model</span>
                                        <input class="form-control"  disabled value="${vehicle.getModel()}">
                                    </div>
                                   
                                    <div class="input-group">
                                        <span class="input-group-addon label-vehicle" > Plate</span>
                                        <input type="text" class="form-control" name="plate" value="${vehicle.getPlate()}" required="required">
                                    </div>
                                    <br>
                                    <input type="hidden" name="ticketid" value="${ticket.getTicketid()}" required="required">
                                    <button type="submit" class="btn btn-info">Update</button>
                                </div>
                                </form>
                            </div>
                        </div>
                    
                    <hr>
                <!-- ./row -->

            </div>  

        </div>
    </body>

</html>