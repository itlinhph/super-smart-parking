<%-- 
    Document   : userVehicle
    Created on : Nov 4, 2018, 12:35:02 PM
    Author     : linkpp
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
        <!-- Content -->
        <div class="content container">
            <div class="row">
                <!-- NAV LEFT -->
                <jsp:include page="../partition/navLeft.jsp"></jsp:include>
                <!-- END NAV LEFT -->
                    <div class="col-md-9 contentRight">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-md-9">
                                        <h3>Selling Items</h3>
                                    </div>
                                    <div class="col-md-3">
                                        <a href="/sellNewProduct" class="btn btn-info btn-lg">Sell New Product</a>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <p>You have
                                    2 plate are register in My Parking:</p>
                                <div class="row">
                                    <!-- For each here -->
                                    
                                        <div class="col-sm-6 col-md-4 col-lg-4">
                                            <div class="table-bordered productPreview">
                                                <img src="http://i.ebayimg.com/images/g/EowAAOSwSclXMEe4/s-l1600.jpg" alt="" class="img-display table-bordered img-responsive">
                                                <div class="caption">
                                                    <a href="#">
                                                        <h4 class="textPreview">
                                                           29S2-12345
                                                        </h4>
                                                    </a>
                                                    <div class="bidTimePreview">
                                                        <h4> Price:
                                                            100$ </h4>
                                                        <div id="StartTime"> Start Time:
                                                            20-11-2018
                                                        </div>
                                                        <div id="EndTime"> End Time:
                                                            21-11-2018
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-6">
                                                            <a href="#" data-toggle="modal" class="btn btn-danger btnEdit">Delete</a>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <a href="#" class="btn btn-primary btnEdit">Edit Info</a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        
                                </div>
                                <!-- ./row -->


                               
                                    <div class="modal fade" id="deletemodal">
                                        <form role="form" method="POST" action="/api/product/deleteProduct">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h4>Delete
                                                            xxx
                                                        </h4>
                                                    </div>
                                                    <div class="modal-body">
                                                        <p>This action cannot redo. Are you sure delete item:
                                                            <b>
                                                                xxx
                                                            </b>?</p>
                                                    </div>
                                                    <input type="hidden" name="idProduct" value="100">
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                                                        <button type="submit" class="btn btn-danger">Delete</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </form>

                                    </div>
                                   
                            </div>
                        </div>
                        <!-- ./panel -->


                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-md-9">
                                        <h3>Expired items</h3>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <p>You have
                                    10 products being sold and was expired.</p>
                                <div class="row">
                                    <!-- For each here -->
                                    
                                        <div class="col-sm-6 col-md-4 col-lg-4">
                                            <div class="table-bordered productPreview">
                                                <img src="http://i.ebayimg.com/images/g/EowAAOSwSclXMEe4/s-l1600.jpg" alt="" class="img-display table-bordered img-responsive">
                                                <div class="caption">
                                                    <a href="/detail?id=111">
                                                        <h4 class="textPreview">
                                                            xxx
                                                        </h4>
                                                    </a>
                                                    <div class="bidTimePreview">
                                                        <h4> Price:
                                                            xxx </h4>
                                                        <div id="StartTime"> Start Time:
                                                            xxx
                                                        </div>
                                                        <div id="EndTime"> End Time:
                                                            xx
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <a href="#deletemodal" data-toggle="modal" class="btn btn-danger btnEdit">Delete</a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                       
                                </div>
                                <!-- ./row -->

                                
                                    <div class="modal fade" id="delete-xxx">
                                        <form role="form" method="POST" action="/api/product/deleteProduct">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h4>Delete
                                                            xxx
                                                        </h4>
                                                    </div>
                                                    <div class="modal-body">
                                                        <p>This action cannot redo. Are you sure delete item:
                                                            <b>
                                                               xxx
                                                            </b>?</p>
                                                    </div>
                                                    <input type="hidden" name="idProduct" value="111">
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                                                        <button type="submit" class="btn btn-danger">Delete</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </form>

                                    </div>
                                    
                            </div>
                        </div>
                        <!-- ./panel -->

                    </div>
            </div>
        </div>
    </body>
</html>
