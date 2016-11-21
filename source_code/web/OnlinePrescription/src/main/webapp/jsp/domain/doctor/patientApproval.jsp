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
    <title>Patient Approval</title>
    <%@include file="../../common/common_header.jsp" %>
    <%-- TO-DO CSS and JavaScript --%>
    <script src="static/js/domain/doctor/patient-approval.js" type="application/javascript"></script>
</head>
<body page="patient_approval">
<%@include file="../../common/navigation.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="row">
                <div class="col-md-12">
                    <h1>Patient Approval</h1>
                    <hr>
                    <br>
                    <table id="patientTable" class="table table-bordered">

                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
