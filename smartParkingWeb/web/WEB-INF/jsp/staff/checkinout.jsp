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
                    <div class="col-md-6 col-lg-6 col-sm-6 checkinout" id="checkinForm">
                        <form action="checkin" method="POST" >
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
                    <div class="col-md-6 col-lg-6 col-sm-6 checkinout hide" id="image_plate">
                        <div class="table-bordered">
                            <img src="${pageContext.request.contextPath}/resources/images/plate/${image_file}"  alt="" class="img-display table-bordered img-responsive">
                        </div>
                    </div>
                    <div class="col-md-6 col-lg-6 col-sm-6" id="checkoutForm">
                        <div class="titleParking ">Select an image to checkout:</div>
                        <form action="checkout" method="POST" class="well" >
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <input type="file" required="required" name="imgCheckout" accept="image/x-png,image/gif,image/jpeg"> 

                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <button type="submit" class="btn btn-danger">Checkout</button>
                                </div>

                            </div>
                            <div class="input-group hide" id="fixplateinput">
                                <input type="text" class="form-control" name="plate" value="${detect_plate}">
                            </div>
                        </form>
                        
                    </div>
                    

            </div>  

        </div>
    </body>
    
    <script>
//        var message = $("#modalMessage").text() ;
        var plate = $("input[name=plate]").val();
        if(plate != "") {
            $("#fixplateinput").removeClass("hide");
            $("#image_plate").removeClass("hide");
            $("#checkinForm").addClass("hide");
            $("input[name=imgCheckout]").removeAttr("required");
            $("input[name=plate]").addAttr("required");
        }
        
    </script>

</html>
