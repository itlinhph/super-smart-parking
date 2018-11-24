<%-- 
    Document   : manageStaff
    Created on : Nov 22, 2018, 7:06:19 PM
    Author     : linhph
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width initial-scale=1">
        <title>Manage Staff</title>

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
                    <div class="col-md-12">
                        <hr>
                        <div class="titleParking">
                            All staff information: 
                            <button class="btn btn-success" style="float: right" id="addStaffBtn">Add New Staff</button>


                        </div>
                        <br>

                        <div class="col-sm-6 col-md-6 col-lg-6 hide" id="addStaffDiv">
                            <button class="btn btn-default hide" style="float: right;" id="hideStaffBtn">X</button>
                            <form action="addStaff" method="POST">
                                <div class="table-bordered well">
                                    <div style="font-size: 30px; font-weight: bold; color: red">Add new Staff:</div> 
                                    <br>

                                    <div class="input-group">
                                        <span class="input-group-addon label-vehicle" >Staff Code</span>
                                        <input type="text" class="form-control" name="scode" placeholder="Ex: Slinhph" required="required">
                                    </div>
                                    <div class="input-group">
                                        <span class="input-group-addon label-vehicle" >Full Name</span>
                                        <input type="text" class="form-control" name="fullname" placeholder="Ex: Phan Văn A" required="required">
                                    </div>
                                    <div class="input-group">
                                        <span class="input-group-addon label-vehicle" >Park</span>
                                        <select id="selectPark" class="form-control" name="parkid" required>
                                        <c:forEach var="park" items="${listPark}">
                                            <option value="${park.getId()}" >${park.getParkCode()}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <br>
                                <button class="btn btn-success" type="submit">Submit</button>
                            </div>

                        </form>
                    </div>

                    <table class="table table-bordered table-hover table-striped">
                        <thead>
                            <tr>
                                <th>#No</th>
                                <th>Staff Code</th>
                                <th>Full Name</th>
                                <th>Park Code</th>
                                <th>Status</th>
                                <th>Created</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="staff" items="${listStaff}">
                                <tr>
                                    <td>${staff.getId()}</td>
                                    <td>${staff.getStaffCode()}</td>
                                    <td>${staff.getStaffName()}</td>
                                    <td>${staff.getParkCode()}</td>
                                    <td>${staff.getStatus()}</td>
                                    <td>${staff.getCreated()}</td>
                                    <td>  <a href=""><button class="btn btn-danger">Deactive</button></a> </td>
                                </tr>
                            </c:forEach> 
                        </tbody>
                    </table>
                    <hr>
                </div>
                <hr>
            </div>


        </div>
    </div>
    <!-- End container -->



</body>
<script>
    $("#addStaffBtn").click(function () {
        $("#addStaffDiv").removeClass("hide");
        $("#hideStaffBtn").removeClass("hide");
        $("#addStaffBtn").addClass("hide");
    });

    $("#hideStaffBtn").click(function () {
        $("#addStaffDiv").addClass("hide");
        $("#hideStaffBtn").addClass("hide");
        $("#addStaffBtn").removeClass("hide");
    });

</script>
</html>



