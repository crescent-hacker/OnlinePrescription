<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Data-table Demo</title>
    <%@include file="../../common/common_header.jsp" %>


    <!--Datatable required js  -->
    <%-- <script src="${pageContext.request.contextPath}/static/vendor/jquery/jquery-1.12.4.min.js"></script> --%>
    <script src="static/vendor/dataTables/js/jquery-ui-1.8.23.custom.min.js"></script>
    <script src="static/vendor/dataTables/js/jquery.dataTables.js"></script>
    <script src="static/vendor/dataTables/js/jquery.validate.js"></script>
    <script src="static/vendor/dataTables/js/jquery.metadata.js"></script>
    <script src="static/vendor/dataTables/js/additional-methods.js"></script>
    <script src="static/vendor/dataTables/js/custom.js"></script>
    <script src="static/js/domain/demo/datatable-demo.js"></script>

    <!--Datatable required css  -->
    <link href="static/vendor/dataTables/css/jquery-ui-1.8.15.custom.css" rel="stylesheet" type="text/css"/>
    <link href="static/css/common/vendor-compatible/jquery-ui-fix.css" rel="stylesheet" type="text/css"/>
    <link href="static/vendor/dataTables/css/demo_page.css" rel="stylesheet" type="text/css"/>
    <link href="static/vendor/dataTables/css/demo_table.css" rel="stylesheet" type="text/css"/>
    <link href="static/vendor/dataTables/css/demo_table_jui.css" rel="stylesheet" type="text/css"/>
    <link href="static/vendor/dataTables/css/dropdown.css" rel="stylesheet" type="text/css"/>
</head>
<body>

<%@include file="../../common/navigation.jsp" %>
<h2 align="center">${message}</h2>
    <table id="table1">
        <thead>
        <tr>
            <th align="center" width="200px">First Name</th>
            <th align="center" width="200px">Last Name</th>
            <th align="center" width="200px">Street</th>
            <th align="center" width="200px">City</th>
            <th align="center" width="200px">State</th>
            <th align="center" width="200px">Country</th>
        </tr>
        </thead>
        <tbody align="center">
        </tbody>
    </table>
    <a href="${pageContext.request.contextPath}/downloadExcelDemo">Download Report</a>
</body>
</html>