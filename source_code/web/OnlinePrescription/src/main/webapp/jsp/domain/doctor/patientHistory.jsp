<%--
  Created by IntelliJ IDEA.
  User: Hrudai-PC
  Date: 15/10/2016
  Time: 1:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Patient History</title>
    <%@include file="../../common/common_header.jsp" %>
    <!-- TO-DO CSS and JavaScript -->
    <link href="static/vendor/boostrap-table/bootstrap-table.min.css" rel="stylesheet">
    <script src="static/vendor/boostrap-table/bootstrap-table.min.js"></script>
    <script src="static/js/domain/doctor/patient-history.js"></script>

</head>
<body page="patient_history">
<%@include file="../../common/navigation.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="row">
                <div class="col-md-12">
                    <h1>Patient history</h1>
                    <hr>
                    <br>
                    <table id="historyTable" data-height="488" data-pagination="true" sidePagination="client" data-page-number="1" data-page-size="10" data-smart-display="true" data-sortable="true">

                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
