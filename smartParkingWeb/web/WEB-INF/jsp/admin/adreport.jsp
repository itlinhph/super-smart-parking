<%-- 
    Document   : report
    Created on : Nov 24, 2018, 12:40:40 AM
    Author     : linkpp
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width initial-scale=1">
    <title>Manage Report</title>

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

            <div class="col-md-9 contentRight">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3><span class="glyphicon glyphicon glyphicon-bullhorn"></span> Manage report </h3>

                    </div>
                    <div class="panel-body">
                        <h4>You have ${listReport.size()} report from user are waiting to reply:</h4>
                        <br>
                        <c:forEach var="report" items="${listReport}">
                            <div class="row">
                                <div class="col-sm-6 col-md-6 col-lg-6 ">
                                    <div class="table-bordered  well">

                                        <div class="input-group">
                                            <span class="input-group-addon label-vehicle">Status </span>
                                            <input class="form-control" disabled value="${report.getStatus()}">
                                        </div>
                                        <div class="input-group">
                                            <span class="input-group-addon label-vehicle">Email </span>
                                            <input class="form-control" disabled value="${report.getEmailUser()}">
                                        </div>
                                        <div class="input-group">
                                            <span class="input-group-addon label-vehicle">Type </span>
                                            <input class="form-control" disabled value="${report.getType()}">
                                        </div>
                                        <br>
                                        <div class="form-group">
                                            <label>Detail</label>
                                            <br>
                                            <textarea class="descriptionReport" name="description" disabled placeholder="Description"
                                                required="required">${report.getDescription()}</textarea>
                                        </div>
                                        <br>
                                    </div>
                                </div>
                                <div class="col-sm-6 col-md-6 col-lg-6 ">
                                    <form action="adReplyReport" method="POST">
                                        <textarea class="descriptionReportAdmin" name="adminReply" placeholder="Admin reply here..."
                                            required="required">${report.getAdminNote()}</textarea>
                                        <input type="hidden" name="reportId" value="${report.getId()}">
                                        <br><br>
                                        <button type="submit" class="btn btn-primary">Submit</button>
                                    </form>
                                </div>
                            </div>
                            <hr>
                        </c:forEach>
                        <!-- ./row -->

                    </div>
                </div>
                <!-- ./panel -->

            </div>
        </div>

    </div>
</body>

</html>