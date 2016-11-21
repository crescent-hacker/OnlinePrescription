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
    <title>Refer Patient</title>
    <%@include file="../../common/common_header.jsp" %>
    <%-- TO-DO CSS and JavaScript --%>
</head>
<body page="refer_patient">
<%@include file="../../common/navigation.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="row">
                <div class="col-md-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Refer Patient
                        </div>
                        <div class="panel-body" style="height: calc(100vh - 185px)">
                            <form>
                                <div class="form-group col-lg-6" >
                                    <label for="refer">Details</label>
                                    <input class="form-control input-lg" style="height: calc(90vh - 205px)" id="refer" type="text">
                                    <br>
                                    <span class="input-group-btn">
                                        <button class="btn btn-default" type="button">Print</button> <%-- Print functionality needs to be implemented for this--%>
                                     </span>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
