<%-- 
    Document   : login
    Created on : Oct 30, 2018, 8:47:49 PM
    Author     : linhph
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>

<html>
    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width initial-scale=1">
        <title>SignUp/Login</title>

        <link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="resources/css/style.css" rel="stylesheet" type="text/css">
        <script src="resources/js/jquery-3.1.1.min.js" type="text/javascript"></script>
        <script src="resources/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="resources/js/script.js" type="text/javascript"></script>

    </head>
    <body>
        <!-- Header -->
        <jsp:include page="partition/header.jsp"></jsp:include>
        <!-- End header -->
        <!-- Sign Up/Login Tab -->
        <div id="signupLoginTab" class="well">
            <ul class="nav nav-tabs nav-pills nav-justified">
                <li class="signupTab"><a data-toggle="tab" href="#signup">Sign Up</a></li>
                <li class="loginTab active" ><a data-toggle="tab" href="#login">Login</a></li>
            </ul>
            <div class="tab-content">
                <div id="signup" class="signupTab tab-pane fade">
                    <form action="/login/registerUser" method="POST" role="form">

                        <br> <br>
                        <div class="form-group">
                            <label>Username</label>
                            <input type="text" class="form-control" id="su-username" placeholder="Username" required="required" name="username">
                        </div>
                        <div class="form-group">
                            <label >Email</label>
                            <input type="email" class="form-control" id="su-email" placeholder="Email" required="required" name="email">
                        </div>
                        <div class="form-group">
                            <label >Password</label>
                            <input type="password" class="form-control" id="su-password" placeholder="Password" required="required" name="password">
                        </div>
                        <div class="form-group">
                            <label >Full Name</label>
                            <input type="text" class="form-control" id="su-fullname" placeholder="Fullname" required="required" name="fullname">
                        </div>
                        <div class="form-group">
                            <label >Phone number</label>
                            <input type="number" min='0' class="form-control" id="su-phone" placeholder="Phone number" required="required" name="phone">
                        </div>
                        <button id="registerBtn" type="submit" class="btn btn-success" >Register</button>
                    </form>
                </div> 

                <div id="login" class=" loginTab tab-pane fade in active">
                    <br> <br>
                    <form action="/login/loginForm" method="POST" role="form">

                        <div class="form-group">
                            <label for="">Username</label>
                            <input type="text" class="form-control" id="" placeholder="Your username" required="required" name="username">
                        </div>
                        <div class="form-group">
                            <label for="">Password</label>
                            <input type="password" class="form-control" id="" placeholder="Password" required="required" name="password">
                        </div>
                        <br>
                        <button id="loginBtn" type="submit" class="btn btn-success">Log In</button>
                    </form>
                </div>
            </div>
        </div>
        <!-- End Tab -->
    </body>


    <script>
        var isLogin = ${isLogin}
        if(!isLogin) {
            $('#signup').addClass("in");
            $('#login').removeClass("in");
            $('.signupTab').addClass("active");
            $('.loginTab').removeClass("active");
        }
        
    </script>


</html>