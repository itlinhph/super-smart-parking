<%-- 
    Document   : login
    Created on : Oct 27, 2018, 10:21:49 PM
    Author     : linhph
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width initial-scale=1">
        <title>Home Page</title>

        <link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css">
        <script src="${pageContext.request.contextPath}/resources/js/jquery-3.1.1.min.js" type="text/javascript"> </script>
        <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js" type="text/javascript"> </script>
        <script src="${pageContext.request.contextPath}/resources/js/script.js" type="text/javascript"></script>
    
    </head>
<body>
<!-- navbar -->
<jsp:include page="partition/navbar.jsp"></jsp:include>
<!-- End navbar -->
<jsp:include page="partition/message.jsp"></jsp:include>
    <!-- Content -->
    <div class="content container">


        <!-- Banner -->
        <div id="carousel-id" class="carousel slide" data-ride="carousel">
            <ol class="carousel-indicators">
             <li data-target="#carousel-id" data-slide-to="0" class="active"></li>
             <li data-target="#carousel-id" data-slide-to="1"></li>
             <li data-target="#carousel-id" data-slide-to="2"></li>
            </ol>
  
            <div class="carousel-inner carousel-slide">
              <div class="item active">
                <img src="${pageContext.request.contextPath}/resources/images/slide1.jpg" width="100%" class="center-block">
              </div>
              <div class="item">
                <img src="${pageContext.request.contextPath}/resources/images/slide22.png" width="100%" class="center-block">
              </div>
              <div class="item">
                <img src="${pageContext.request.contextPath}/resources/images/slide33.png" width="100%" class="center-block">
              </div>
            </div>
            <a class="left carousel-control" href="#carousel-id" data-slide="prev"><span class="glyphicon glyphicon-chevron-left "></span></a>
            <a class="right carousel-control" href="#carousel-id" data-slide="next"><span class="glyphicon glyphicon-chevron-right"></span></a>
          </div>

        <!-- End banner -->

        <hr>
        <div class="row">
            
            <h1 style="text-align: center"> <b> Bãi gửi xe thông minh Smart Parking </b> </h1>
            <br> <br>
            <h3> I. Hệ thống của chúng tôi có gì? </h3>
            <hr>
            <div class="row">
                <div class="col-md-3">
                    <h5 class="titleH5">Thuật tiện cho người gửi xe</h5>
                    <hr>
                    <p>Người gửi xe không cần cầm bất cứ vé xe nào trên tay.</p>
                    <p>Hệ thống sinh vé xe tự động và gửi về tài khoản người dùng.</p>
                </div>
                <div class="col-md-3">
                    <h5 class="titleH5">Hệ thống đọc biển số xe tự động</h5>
                    <hr>
                    <p>Hệ thống đọc biển số xe tự động, nhanh chóng tạo vé xe thông qua biển số.</p>
                </div>
                <div class="col-md-3">
                    <h5 class="titleH5">Quản lý nhà xe thuận tiện</h5>
                    <hr>
                    <p>Nhân viên tương tác với bãi gửi xe nhanh chóng, thuận tiện.</p>
                    <p>Quản trị viên có kênh liên lạc với người gửi.</p>
                </div>
                <div class="col-md-3">
                    <h5 class="titleH5">Chống gian lận</h5>
                    <hr>
                    <p>Hệ thống tính tiền và trả tiền gửi xe tự động.</p>
                </div>
            </div>
            <hr>
            
        <h3> II. Quy trình đăng ký gửi xe </h3>
        <br>
        <div> Người gửi xe phải đăng ký tài khoản xe của mình trên hệ thống website. Sau đó đem giấy tờ đến quản trị viên xác minh thông tin.</div>
        <div>Quy trình đăng ký gửi xe như sau:</div> <br>
        <img src="${pageContext.request.contextPath}/resources/images/quytrinhDk.png">
        <hr>
        <h3> III. Cách thức hoạt động </h3>
        <br>
        <div>Sau khi đăng ký và xác minh thành công, người dùng có thể sử dụng chiếc xe đó ra vào bãi gửi xe theo quy trình:</div>
        <br>
        <img src="${pageContext.request.contextPath}/resources/images/quytrinhguixe.png">
        
        </div> <!-- End row content -->

        
    </div>
    
    <!-- End content -->
    <br>
    <!-- Footer -->
    <jsp:include page="partition/footer.jsp" ></jsp:include>
    <!-- End footer -->
</body>

</html>