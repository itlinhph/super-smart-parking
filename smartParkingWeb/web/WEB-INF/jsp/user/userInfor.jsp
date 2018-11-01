<%-- 
    Document   : userInfor
    Created on : Nov 1, 2018, 8:37:42 PM
    Author     : linhph
--%>

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
<jsp:include page="partition/navbar.jsp"></jsp:include>
<!-- End message -->
<jsp:include page="partition/message.jsp"></jsp:include>

        
        <div class="content container">

            <div class="row">
                 <!-- Nav left  -->
                 <div class="col-xs-4 col-sm-2 col-md-3 col-lg-3 well navLeft">
                    <h3> My Account</h3>
                    <hr>
                    <ul class="nav nav-pills nav-stacked selectCategoryMenu">
                        <!-- FrontEnd said : add class .active to active menu you choose -->
                        <li id="sellNewProduct" class="selectCategory"> <a href="/sellNewProduct" class="glyphicon glyphicon-open cName"> Sell New Product</a></li>
                        <li id="userInfo" class="selectCategory"> <a href="/userInfo" class="glyphicon glyphicon-cog cName"> Personal Setting</a></li>
                        <li id="manageSales" class="selectCategory"> <a href="manageSales" class="glyphicon glyphicon-tag cName"> Manage Sales</a></li>
                        <li id="managePurchases" class="selectCategory"> <a href="/managePurchases" class="glyphicon glyphicon-shopping-cart cName"> Manage Purchases</a></li>
                        <li class="selectCategory"> <a href="#" class="glyphicon glyphicon-envelope cName"> Contact Admin </a></li>
                
                    </ul>
                </div>
                <!-- END NAV LEFT -->
                

                <div class="col-xs-7 col-sm-7 col-md-8 col-lg-8 contentRight">
                    <h2>Public Profile</h2>
                    <hr>
                    <div class="row">
                        <div class="col-md-7">
                            
                            <form  action="/api/user/updateProfile" method="POST" role="form">
                                <div class="form-group">
                                    <label for="">Username <span class="glyphicon glyphicon-lock"></span></label>
                                    <span class="form-control disabled">username</span>
                                </div>
                                <div class="form-group">
                                    <label for="">Status <span class="glyphicon glyphicon-lock"></span></label>
                                    <span class="form-control disabled">Active</span>
                                </div>
                                
                                <div class="form-group">
                                    <label for="">Full Name</label>
                                    <input type="text" name='name' class="form-control" required="required" value="name">
                                </div>

                                <div class="form-group">
                                    <label for="">Email</label>
                                    <input type="email" name='email' class="form-control" required="required" value="email">
                                </div>

                                <div class="form-group">
                                    <label for="">Phone </label>
                                    <input type="number" name='phone' class="form-control" required="required" value="phone">
                                </div>
                                
                                <div class="form-group">
                                    <label for="">Coin Remain <span class="glyphicon glyphicon-lock"></span></label>
                                    <span class="form-control disabled">100</span>
                                </div>
                                <div class="form-group">
                                    <label for="">Created <span class="glyphicon glyphicon-lock"></span></label>
                                    <span class="form-control disabled">2018-11-01 10:21:00</span>
                                </div>

                            

                                <button type="submit" class="btn btn-lg btn-primary">Update Profile</button>
                               
                            </form>
                            <hr><hr>
                            <div>
                                <h3> Change Your Password:</h3>
                                
                                <form action="/api/user/changePassword" method="POST" role="form">
                                
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
</html>
