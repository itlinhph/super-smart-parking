<%-- 
    Document   : checkinout
    Created on : Nov 19, 2018, 11:12:29 PM
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
                    <hr>
                    <div class="col-md-6 col-lg-6 col-sm-6 checkinout">
                        <form action="checkin" method="POST">
                            <div class="titleParking">Select an image to checkin:</div>
                            <div class="row well">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <input type="file" required="required" name="imgCheckin" accept="image/x-png,image/gif,image/jpeg"> 

                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <button type="submit" class="btn btn-primary">Checkin</button>
                                </div>

                            </div>
                        </form>    
                    </div>
                    <div class="col-md-6 col-lg-6 col-sm-6 checkinout">
                        <form action="checkout" method="POST">
                            <div class="titleParking">Select an image to checkout:</div>
                            <div class="row well">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <input type="file" required="required" name="imgFile" accept="image/x-png,image/gif,image/jpeg"> 

                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <button type="submit" class="btn btn-danger">Checkout</button>
                                </div>

                            </div>
                        </form>    
                    </div>
                    

            </div>  

        </div>
    </body>

</html>
