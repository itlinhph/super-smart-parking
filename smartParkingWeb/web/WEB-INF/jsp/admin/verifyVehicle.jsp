<%-- 
    Document   : verifyVehicle
    Created on : Nov 22, 2018, 7:07:06 PM
    Author     : linhph
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
        
        <jsp:include page="adNavBar.jsp"></jsp:include>
        <jsp:include page="../partition/message.jsp"></jsp:include>
            <!-- Content -->
            <div class="content container">
                <div class="row">
                    <!-- END NAV LEFT -->
                    <div class="col-md-9 contentRight">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3>Verify Vehicle</h3>

                            </div>
                            <div class="panel-body">
                                <p >You have ${listVehicle.size()} plate are waiting to verify:</p>

                            <c:forEach var="vehicle" items="${listVehicle}">
                                <form role="form" method="POST" action="verifyVehicleAction" id="vehicle_${vehicle.getId()}">
                                    <div class="row">

                                        <div class="col-sm-6 col-md-4 col-lg-4">

                                            <div class="table-bordered productPreview">
                                                <img src="${pageContext.request.contextPath}/resources/images/plate/${vehicle.img}"  alt="" class="img-display table-bordered img-responsive">
                                                <div class="caption">
                                                    <h4 class="textPreview">${vehicle.getPlate()}</h4>

                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-sm-6 col-md-7 col-lg-7 ">
                                            <div class="table-bordered well">


                                                <div class="input-group">
                                                    <span class="input-group-addon label-vehicle" >Status <span class="glyphicon glyphicon-lock"></span></span>
                                                    <input class="form-control"  disabled value="${vehicle.getStatus()}">
                                                </div>
                                                <div class="input-group">
                                                    <span class="input-group-addon label-vehicle" >Plate</span>
                                                    <input type="text" class="form-control" name="plate" value="${vehicle.getPlate()}" required="required">
                                                </div>
                                                <div class="input-group">
                                                    <span class="input-group-addon label-vehicle" >Model</span>
                                                    <input type="text" class="form-control" name="model" value="${vehicle.getModel()}" required="required">
                                                </div>

                                                <div class="form-group">
                                                    <label>Description</label>
                                                    <br>
                                                    <textarea class="descriptionVehicle" name="description" placeholder="Description" required="required">${vehicle.getDescription()}</textarea>
                                                </div>
                                                
                                                <br>
                                                <div class="row">
                                                    <div class="col-md-6 ">
                                                        <button type="submit" class="btn btn-info">Verify</button>
                                                    </div>
                                                    <div class="col-md-6" align="right">
                                                        <a href="#delete_1" data-toggle="modal" class="btn btn-danger deactiveBtn">Reject</a>
                                                        <input name="idvehicle" class="hide" value="${vehicle.getId()}">
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
            <!-- modal-delete-->
            <div class="modal fade" id="delete_1">
                <form action="rejectVehicle" method="POST" role="form">
                  <div class="modal-dialog">
                    <div class="modal-content">
                      <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <h4 class="modal-title">Are you sure want to deactive this vehicle?</h4>
                            
                      </div>
                        
                      <div class="modal-footer">

                        <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
                        <input type="hidden" name="idReject" value="" id="idVehicleDeactive">
                        <button type="submit" class="btn btn-danger" >YES</button>
                      </div>
                    </div>
                  </div>
                </form>
            </div> <!-- End modal -->
                                        
        </div>
    </body>

    <script>
        
        $(".deactiveBtn").click(function(){
            var x= $(this).next().val();
            $("#idVehicleDeactive").val(x);
            
        });
    </script>
</html>
