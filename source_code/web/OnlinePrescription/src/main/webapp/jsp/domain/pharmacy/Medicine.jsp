<%--
  Created by IntelliJ IDEA.
  User: Fang
  Date: 5/10/2016
  Time: 11:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="../../common/common_header.jsp" %>
    <title>Pharmacy </title>
    <!-- Custom styles for this template -->
    <link href="static/css/domain/pharmacy/dashboard.css" rel="stylesheet">

    <link rel="stylesheet" type="text/css" href="static/css/domain/pharmacy/datatables.min.css"/>

    <script type="text/javascript" src="https://cdn.datatables.net/v/bs/jq-2.2.3/dt-1.10.12/datatables.min.js"></script>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.11.2/css/bootstrap-select.min.css">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.11.2/js/bootstrap-select.min.js"></script>

    <!-- (Optional) Latest compiled and minified JavaScript translation files -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.11.2/js/i18n/defaults-*.min.js"></script>

</head>
<body>
<%@include file="../../common/navigation.jsp" %>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <ul class="nav nav-sidebar">
                <li><a href="./pharmacyIndex.page">Overview <span class="sr-only">(current)</span></a></li>
                <li><a href="./checkOrders.page">Orders</a></li>
                <li><a href="./checkHistory.page">History</a></li>

            </ul>
            <ul class="nav nav-sidebar">
                <li><a href="./checkStocks.page">Stocks</a></li>
                <li><a href="./checkList.page">Medicine List</a></li>
            </ul>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="page-header">
                <h1 name="onum">Medicine: ${mname}</h1>
            </div>
            <div class="row">
                <div class="col-md-8 col-md-offset-1 ">
                    <div class="alert alert-danger ${hidden1}" role="alert">
                        <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                        <span class="sr-only">Error:</span>
                        Recruit unsuccessful!
                    </div>
                    <div class="alert alert-success ${hidden2}" role="alert">
                        <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                        <span class="sr-only">Well done!</span>
                        Recruit successful!
                    </div>
                </div>
                <div class="col-md-8 col-md-offset-1 ">
                    <div class="panel panel-warning">
                        <div class="panel-heading">
                            <h3 class="panel-title">Medicine Detail</h3>
                        </div>
                        <div class="panel-body">
                            Medicine:           ${dname}<br>
                            Serial num:          ${dserial}<br>
                            Producer:    ${dproducer}<br>
                            Package:    ${dpackage}<br>
                            Price:    ${dprice}<br>
                            Stock:    ${dvalue1}<br>
                        </div>
                    </div>
                </div><!-- /.col-sm-4 -->
                <form:form action="Medicine.ajax?drug_id=${drug_id}" method="post" modelAttribute="drugid" >
                    <div class="row col-md-3 col-md-offset-1 form-group">
                        <label for="usr">Stock:</label>
                        <form:input path="value"  type="text" class="form-control" id="usr" value="${dvalue2}"/>
                    </div>
                    <div class="col-md-8 col-md-offset-1 top-buffer" >

                        <p>
                            <input type="submit" name="action" class="btn btn-lg btn-primary" value="Recruit" />
                            <input type="submit" name="action" class="btn btn-lg btn-default" value="Cancel" />
                        </p>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
