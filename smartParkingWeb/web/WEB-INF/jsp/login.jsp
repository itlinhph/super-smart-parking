<%-- 
    Document   : login
    Created on : Oct 30, 2018, 8:47:49 PM
    Author     : linhph
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width initial-scale=1">
        <title>SignUp/Login</title>

        <link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css">
        <script src="${pageContext.request.contextPath}/resources/js/jquery-3.1.1.min.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/resources/js/script.js" type="text/javascript"></script>

    </head>

    <body>
        <!-- navbar -->
        <jsp:include page="partition/navbar.jsp"></jsp:include>
        <!-- End navbar -->
        <jsp:include page="partition/message.jsp"></jsp:include>
        <!-- Sign Up/Login Tab -->
        <div id="signupLoginTab" class="well">
            <ul class="nav nav-tabs nav-pills nav-justified">
                <li class="signupTab"><a data-toggle="tab" href="#signup">Sign Up</a></li>
                <li class="loginTab active" ><a data-toggle="tab" href="#login">Login</a></li>
            </ul>
            <div class="tab-content">
                <div id="signup" class="signupTab tab-pane fade">
                    <form action="" id="registerUserForm">

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
                    
                    <form action="${pageContext.request.contextPath}/login/loginForm" method="POST" role="form">
                        <br>
                        <div class="radioBtn row col-md-12">

                            <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
                                <label class="radio-inline"><input type="radio" name="accountType" checked="checked" value="user">Login As User</label>
                            </div>
                            <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
                                <label class="radio-inline"><input type="radio" name="accountType" value="staff">Login As Staff</label>
                            </div>

                        </div>
                        <br><br>
                        <div class="form-group">
                            <label for="">Username</label>
                            <input type="text" class="form-control" id="lo-username"  placeholder="Your username" required="required" name="username">
                        </div>
                        <div class="form-group">
                            <label for="">Password</label>
                            <input type="password" class="form-control" id="lo-password" placeholder="Password" required="required" name="password">
                        </div>
                        <br>
                        <button id="loginBtn" type="submit" class="btn btn-success">Log In</button>
                    </form>
                <br>
                </div>
                
            </div>
        </div>
        <!-- End Tab -->
    </body>


    <script>
        var isLoginPage = ${isLogin};
        if(!isLoginPage) {
            $('#signup').addClass("in");
            $('#login').removeClass("in");
            $('.signupTab').addClass("active");
            $('.loginTab').removeClass("active");
        }
        
        $("#registerUserForm").submit(function(e){
        e.preventDefault();    
        var message = "No change!";
            
            var dataSend = $("#registerUserForm").serialize();
            console.log(dataSend);
            
            $.ajax({
                method: "POST",
                url: "login/registerUser",
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
        ${script}
        $("#adminLogin").click(function(){
            
        });
    </script>


</html>