<%-- 
    Document   : report
    Created on : Nov 27, 2018, 8:02:20 PM
    Author     : linhph
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width initial-scale=1">
        <title>Report to admin</title>

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

                    <div class="col-md-9 contentRight">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3><span class="glyphicon glyphicon glyphicon-bullhorn"></span> Report to admin </h3>

                            </div>
                            <div class="panel-body">

                                <form role="form" method="POST" action="userReportAction">
                                    <div class="row">

                                     
                                        <div class="col-sm-6 col-md-6 col-lg-6 ">
                                            <div class="table-bordered vehicleDescription well">


                                                <div class="input-group">
                                                    <span class="input-group-addon label-vehicle" >Status <span class="glyphicon glyphicon-lock"></span></span>
                                                    <input class="form-control"  disabled value="${report.getStatus()==null?"Not Submit":report.getStatus()}">
                                                </div>
                                                <div class="input-group">
                                                    <span class="input-group-addon label-vehicle" >Type</span>
                                                    <select id="selectType" class="form-control" name="type" required ${report.getStatus()=="processed"?"disabled":""}>
                                                    
                                                        <option value="staff" >About Staff</option>
                                                        <option value="ticket" >About a ticket</option>
                                                        <option value="vehicle" >Lost Vehicle</option>
                                                        <option value="other" >Other</option>
                                                    
                                                </select>
                                                </div>
                                                
                                                <br>
                                                <div class="form-group">
                                                    <label>Detail</label>
                                                    <br>
                                                    <textarea class="descriptionReport" name="description" ${report.getStatus()=="processed"?"disabled":""} placeholder="Description" required="required">${report.getDescription()}</textarea>
                                                </div>
                                                
                                                <br>
                                                <div class="row">
                                                    <div class="col-md-6 ">
                                                        <button type="submit" class="btn btn-info ${report.getStatus()=="processed"?"hide":""}">Submit</button>
                                                    </div>
                                                    
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-sm-6 col-md-6 col-lg-6 ">
                                            <div class="form-group">
                                                
                                                <br><br>
                                                <div class=" ${report.getStatus()=="processed"?"":"hide"}" name="adminNote" placeholder="">ADMIN: ${report.getAdminNote()}</div>
                                            </div>
                                        </div>
                                    </div>
                                    <input type="hidden" name="ticketId" value="${ticketId}">
                                </form>

                            <!-- ./row -->
                        </div>
                    </div>
                    <!-- ./panel -->

                </div>
            </div>
                                        
        </div>
    </body>
    <script>
        $("#selectType").val("${report.getType()}");
    </script>


</html>
