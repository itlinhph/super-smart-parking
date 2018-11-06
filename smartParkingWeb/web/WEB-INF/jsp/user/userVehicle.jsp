<%-- 
    Document   : userVehicle
    Created on : Nov 4, 2018, 12:35:02 PM
    Author     : linkpp
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width initial-scale=1">
        <title>User Information</title>

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
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3>Registered Vehicle</h3>
                                
                            </div>
                            <div class="panel-body">
                                <p class="inline">You have ${user.getListVehicle().size()} plate are registered in My Parking:</p>
                            
                                <button class="btn btn-primary inline btn-AddVehicle">Add vehicle</button>
                            <!-- For each here -->

                            <c:forEach var="vehicle" items="${user.getListVehicle()}">
                                <form role="form" action="/user/editVehicle" id="vehicle_${vehicle.getId()}">
                                <div class="row">

                                    <div class="col-sm-6 col-md-4 col-lg-4">
                                        
                                        <div class="statusVehicle ${vehicle.getStatus()=="pendding"?"show":"hide"}">Pendding</div>
                                        <div class="table-bordered productPreview">
                                            <img src="${pageContext.request.contextPath}/${vehicle.img}"  alt="" class="img-display table-bordered img-responsive">
                                            <div class="caption">
                                                <h4 class="textPreview">${vehicle.getPlate()}</h4>


                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <div class="form-group">
                                                            <input type="file" name="Img" accept="image/x-png,image/gif,image/jpeg"> 
                                                        </div>
                                                    </div>
                                                    
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-6 col-md-7 col-lg-7 ">
                                        <div class="table-bordered vehicleDescription well">
                                        

                                            <div class="input-group">
                                              <span class="input-group-addon label-vehicle" >Status <span class="glyphicon glyphicon-lock"></span></span>
                                              <input class="form-control"  disabled value="${vehicle.getStatus()}">
                                            </div>
                                            <div class="input-group">
                                              <span class="input-group-addon label-vehicle" >Plate</span>
                                              <input type="text" class="form-control" value="${vehicle.getPlate()}" required="required">
                                            </div>
                                            <div class="input-group">
                                              <span class="input-group-addon label-vehicle" >Model</span>
                                              <input type="text" class="form-control" value="${vehicle.getModel()}" required="required">
                                            </div>
                                            <div class="form-group">
                                                <label>Description</label>
                                                <br>
                                                <textarea class="descriptionVehicle" name="description" placeholder="Description" required="required">${vehicle.getDescription()}</textarea>
                                            </div>
                                            <br>
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <button type="submit" class="btn btn-info">Update Profile</button>
                                                </div>
                                                <div class="col-md-6" align="right">
                                                    <a href="#" data-toggle="modal" class="btn btn-danger">Deactive</a>
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
                    <!-- ./panel -->

                </div>
            </div>
        </div>
    </body>
    
    <script>
        
    </script>
</html>
