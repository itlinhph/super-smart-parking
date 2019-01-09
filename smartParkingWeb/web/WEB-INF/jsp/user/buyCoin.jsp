<%-- 
    Document   : buyCoin
    Created on : Jan 1, 2019, 3:20:31 PM
    Author     : linhph
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width initial-scale=1">
    <title>Buy Coin</title>

    <link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-3.1.1.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/script.js" type="text/javascript"></script>

</head>

<body>

    <body>
        <!-- navbar -->
        <jsp:include page="../partition/navbar.jsp"></jsp:include>
        <!-- End message -->
        <jsp:include page="../partition/message.jsp"></jsp:include>
        <!-- Content -->
        <div class="content container">
            <div class="row">
                <!-- NAV LEFT -->
                <jsp:include page="../partition/navLeft.jsp"></jsp:include>
                <div class="col-md-9 contentRight">

                    <form action="buyCoinAction" method="POST" role="form" id="buyCoinForm">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                    <h3 class="modal-title">Buy more coin</h3>
                                </div>
                                <div class="modal-body">

                                    <div class="form-group">
                                        <label for="">Enter Amount *</label>
                                        <input type="number" class="form-control" placeholder="Enter amount" required="required"
                                            name="coin">
                                    </div>

                                    <div>
                                        <label>Payments</label>
                                        <select name="payOption">
                                            <option value="zaloPay">Zalo Pay</option>
                                            <option value="momo">MoMo Wallet</option>
                                            <option value="phoneCard">Phone Card</option>
                                        </select>
                                    </div>

                                </div>

                                <div class="modal-footer">
                                    <input type="submit" class="btn btn-primary" value="Thanh toán" name="payment">
                                </div>
                            </div>
                        </div>
                    </form>

                </div>
            </div>
        </div>

    </body>
</body>
<script>

    function formSubmit(dataSend, url) {
        $.ajax({
            method: "POST",
            url: url,
            //                async: false,
            data: dataSend,
            success: function (data) {
                var message = data;
                console.log(message);
                $("#modalMessage").text(message);
                showMessage();
            }
            //           
        });
    }
    $("#buyCoinForm").submit(function (e) {
        e.preventDefault();
        var dataSend = $("#buyCoinForm").serialize();
        formSubmit(dataSend, "buyCoinAction");
    });
</script>

</html>