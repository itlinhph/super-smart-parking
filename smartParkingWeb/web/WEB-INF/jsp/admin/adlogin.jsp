<%-- 
    Document   : login
    Created on : Nov 22, 2018, 7:05:14 PM
    Author     : linhph
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width initial-scale=1">
        <title>Admin login</title>

        <link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css">
        <script src="${pageContext.request.contextPath}/resources/js/jquery-3.1.1.min.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/resources/js/script.js" type="text/javascript"></script>

    </head>

    <body>
        <!-- navbar -->
        <jsp:include page="adNavBar.jsp"></jsp:include>
        <!-- End navbar -->
        <jsp:include page="../partition/message.jsp"></jsp:include>
        <!-- Sign Up/Login Tab -->
        <div id="signupLoginTab" class="well">
            
            <div class="tab-content">

                <div id="login">
                    
                    <form action="${pageContext.request.contextPath}/login/adminLogin" method="POST" role="form">
                        <br>
                        <div class="form-group">
                            <label for="">Admin</label>
                            <input type="text" class="form-control" id="username"  placeholder="Username" required="required" name="username">
                        </div>
                        <div class="form-group">
                            <label for="">Password</label>
                            <input type="password" class="form-control" id="password" placeholder="Password" required="required" name="password">
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


</html>