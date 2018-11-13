<%-- 
    Document   : fixWrongPlate
    Created on : Nov 9, 2018, 8:35:57 PM
    Author     : linhph
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width initial-scale=1">
        <title>Fix wrong plate</title>

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
                <!-- For each here -->
                <c:forEach var="wrongPlate" items="${listWrongPlate}">
                    <form role="form" method="POST" action="editWrongPlate" id="vehicle_${vehicle.getId()}">
                        <div class="row">

                            <div class="col-sm-6 col-md-4 col-lg-4">

                                <div class="table-bordered">
                                    <img src="${pageContext.request.contextPath}/${wrongPlate.getImg()}"  alt="" class="img-display table-bordered img-responsive">
                                    
                                </div>
                            </div>
                            <div class="col-sm-6 col-md-7 col-lg-7 ">
                                <div class="table-bordered well">
                                    <div class="input-group">
                                        <span class="input-group-addon label-vehicle" >Status <span class="glyphicon glyphicon-lock"></span></span>
                                        <input class="form-control"  disabled value="${wrongPlate.getStatus()}">
                                    </div>
                                    <div class="input-group">
                                        <span class="input-group-addon label-vehicle" >Checkin</span>
                                        <input class="form-control"  disabled value="${wrongPlate.getCheckinTime()}">
                                    </div>
                                    <div class="input-group">
                                        <span class="input-group-addon label-vehicle" >Plate</span>
                                        <input type="text" class="form-control" name="plate" value="${wrongPlate.getDetectedPlate()}" required="required">
                                    </div>
                                    <br>
                                    <input type="text" class="hide" name="plate_id" value="${wrongPlate.getId()}" required="required">
                                    <div class="row">
                                        <div class="col-md-6" style="height: 90px;">
                                            <button type="submit" class="btn btn-info">Update</button>
                                        </div>
                                        <div class="col-md-6" align="right">
                                            
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <hr>
                </c:forEach>   
                <!-- ./row -->

            </div>  

        </div>
    </body>

    <script>
        
        ${script}
            
        $("#btnAddVehicle").click(function () {
            $("#addVehicle").removeClass("hide");
            $("#btnHide").removeClass("hide");
            $("#btnAddVehicle").addClass("hide");

        });
        $("#btnHide").click(function () {
            $("#addVehicle").addClass("hide");
            $("#btnHide").addClass("hide");
            $("#btnAddVehicle").removeClass("hide");

        });
        
        $(".deactiveBtn").click(function(){
            var x= $(this).next().val();
            $("#idVehicleDeactive").val(x);
            
        });
    </script>
</html>