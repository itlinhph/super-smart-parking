<%-- 
    Document   : manageUser
    Created on : Nov 23, 2018, 8:01:10 PM
    Author     : linhph
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width initial-scale=1">
        <title>Manage User</title>

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
                        All user information:
                    </div>
                    <table class="table table-bordered table-hover table-striped">
                        <thead>
                            <tr>
                                <th>#No</th>
                                <th>Username</th>
                                <th>Fullname</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Status</th>
                                <th>Created</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="user" items="${listUser}">
                                <tr>
                                    <td>${user.getUserId()}</td>
                                    <td>${user.getUsername()}</td>
                                    <td>${user.getFullname()}</td>
                                    <td>${user.getEmail()}</td>
                                    <td>${user.getPhone()}</td>
                                    <td>${user.getStatus()}</td>
                                    <td>${user.getCreated()}</td>
                                    <td> <a href="#deactive" data-toggle="modal" class="btn btn-danger btnSetstatus ${user.getStatus()=="deactive"? "hide":""}">Deactive</a>
                                        <a href="#active" data-toggle="modal" class="btn btn-primary btnSetstatus ${user.getStatus()=="deactive"? "":"hide"}">Active</a> 
                                        <input name="idUser" class="hide" value="${user.getUserId()}"></td>
                                </tr>
                            </c:forEach> 
                        </tbody>
                    </table>
                  <hr>
            </div>
            <hr>
        </div> 
            </div>
        <!-- End container -->
        <!-- modal-delete-->
    <div class="modal fade" id="deactive">
        <form action="adSetStatusUser" method="POST" role="form">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <h4 class="modal-title">Are you sure want to remove this user?</h4>

                    </div>

                    <div class="modal-footer">

                        <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
                        <input type="hidden" name="idUser" value="" class="idUser">
                        <input type="hidden" name="status" value="deactive" >
                        <button type="submit" class="btn btn-danger" >YES</button>
                    </div>
                </div>
            </div>
        </form>
    </div> <!-- End modal -->
    <!-- modal-active-->
    <div class="modal fade" id="active">
        <form action="adSetStatusUser" method="POST" role="form">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <h4 class="modal-title">Are you sure want to active this user?</h4>

                    </div>

                    <div class="modal-footer">

                        <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
                        <input type="hidden" name="idUser" value="" class="idUser">
                        <input type="hidden" name="status" value="working" >
                        <button type="submit" class="btn btn-primary" >YES</button>
                    </div>
                </div>
            </div>
        </form>
    </div> <!-- End modal -->


</body>
<script>
    
    $(".btnSetstatus").click(function(){
            var x= $(this).parent().find("input").val();
            console.log(x);
            $(".idUser").val(x);
            
    });

</script>
</html>




