<%-- 
    Document   : message
    Created on : Oct 31, 2018, 5:09:04 PM
    Author     : linhph
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Message modal -->

    <div class="modal fade" id="modal-message">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4>Thông báo từ My Parking:</h4>
                </div>
                <div class="modal-body">
                    <p id="modalMessage">
                        ${message}
                    </p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
    <!-- End Message modal -->

    <script>
        var message = $("#modalMessage").val() ;
        if(message.length > 0) {
            $('#modal-message').modal('show');
            setTimeout(function () {
                $('#modal-message').modal('hide');
            }, 3000);
        }
        console.log(message, message.length);
        
    </script>
