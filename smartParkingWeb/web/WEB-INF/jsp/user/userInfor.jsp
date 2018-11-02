<%-- 
    Document   : userInfor
    Created on : Nov 1, 2018, 8:37:42 PM
    Author     : linhph
--%>

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

            <div class="content container">

                <div class="row">
                <jsp:include page="../partition/navLeft.jsp"></jsp:include>

                    <div class="col-xs-7 col-sm-7 col-md-8 col-lg-8 contentRight">
                        <h2>Public Profile</h2>
                        <hr>
                        <div class="row">
                            <div class="col-md-7">

                            <form role="form" id="editUserInfor">
                                    <div class="form-group">
                                        <label for="">Username <span class="glyphicon glyphicon-lock"></span></label>
                                        <span class="form-control disabled">${user.getUsername()}</span>
                                </div>
                                <div class="form-group">
                                    <label for="">Status <span class="glyphicon glyphicon-lock"></span></label>
                                    <span class="form-control disabled">${user.getStatus()}</span>
                                </div>

                                <div class="form-group">
                                    <label for="">Full Name</label>
                                    <input type="text" name='fullname' class="form-control" required="required" value="${user.getFullname()}">
                                </div>

                                <div class="form-group">
                                    <label for="">Email</label>
                                    <input type="email" name='email' class="form-control" required="required" value="${user.getEmail()}">
                                </div>

                                <div class="form-group">
                                    <label for="">Phone </label>
                                    <input type="number" name='phone' class="form-control" required="required" value="${user.getPhone()}">
                                </div>
                                
                                <label for="">Coin Remain <span class="glyphicon glyphicon-lock"></span></label>
                                <div class="input-group">
                                    <span class="input-group-addon">$</span>
                                    <span type="number" class="form-control disabled" >${user.getCoin_remain()}</span>
                                    
                                </div>
                                <br>
                                <div class="form-group">
                                    <label for="">Created <span class="glyphicon glyphicon-lock"></span></label>
                                    <span class="form-control disabled">${user.getCreated()}</span>
                                </div>

                                <button type="submit" class="btn btn-lg btn-primary">Update Profile</button>

                            </form>
                            <hr><hr>
                            <div>
                                <h3> Change Your Password:</h3>

                                <form action="" method="POST" role="form">

                                    <div class="form-group">
                                        <label for="">Old Password</label>
                                        <input type="password" class="form-control" id="" name='oldPassword' required="required" placeholder="Old password">
                                    </div>
                                    <div class="form-group">
                                        <label for="">New Password</label>
                                        <input type="password" class="form-control" id="" name='newPassword' required="required" placeholder="New password">
                                    </div>
                                    <div class="form-group">
                                        <label for="">Confirm Password</label>
                                        <input type="password" class="form-control" id="" name='confirmPassword' required="required" placeholder="Confirm password">
                                    </div>

                                    <button type="submit" class="btn btn-danger">Change Password</button>
                                </form>

                            </div>
                        </div>

                    </div>
                </div>
            </div>


        </div>
        <!-- End container -->
    </body>
    <script>
         $("#editUserInfor").submit(function(e){
        e.preventDefault();    
        var message = "No change!";
            
            var dataSend = $("#editUserInfor").serialize();
            console.log(dataSend);
            
            $.ajax({
                method: "POST",
                url: "editProfile",
//                async: false,
                data: dataSend,
                success: function (data) { 
                    message = data; 
                    console.log(message);
                    $("#modalMessage").text(message);
                    showMessage();
                }
//           
            });

        });
    </script>
</html>
