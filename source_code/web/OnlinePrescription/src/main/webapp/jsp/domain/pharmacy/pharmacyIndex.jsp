<%--
  Created by IntelliJ IDEA.
  User: Fang
  Date: 2016/8/29
  Time: 20:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="../../common/common_header.jsp" %>
    <title>Pharmacy Board</title>
    <!-- Custom styles for this template -->
    <link href="static/css/domain/pharmacy/dashboard.css" rel="stylesheet">

    <link rel="stylesheet" type="text/css"
          href="https://cdn.datatables.net/v/bs/jq-2.2.3/dt-1.10.12/datatables.min.css"/>

    <script type="text/javascript" src="https://cdn.datatables.net/v/bs/jq-2.2.3/dt-1.10.12/datatables.min.js"></script>

    <script>
        $(function () {
            //alert("Ready!");
            ajaxDatatable();
            ajaxDatatable1();
        });

        function ajaxDatatable() {
            waitingDialog.show();

            var url = "getAllCurrentOrdersJsonData.ajax";
            $('#table1').dataTable({

                "bJQueryUI": true,
                "bProcessing": true,
                "bServerSide": true,
                "bDestroy": true,
                "bAutoWidth": true,
                "bLengthChange": false,
                "iDisplayLength": 5,
                //"bSort": false,
                "sDom": '<"top"i>rt<"bottom"lp><"clear">',
                //"iDisplayStart": 5,
                //"sScrollX": "940px",
                //"sScrollY": "250px",
                "bScrollCollapse": true,


                "aoColumns": [
                    {"width": "5%", "sType": "numeric"},
                    {
                        "width": "55%",
                        "data": function (data, type, row, meta) {
                            return '<a href="./Order.page?orderId=' + data[1] + '">' + data[1] + '</a>';
                        }
                    },
                    {"width": "30%"},
                    {
                        "width": "10%",
                        "data": function (data, type, row, meta) {
                            switch (data[3]) {
                                case "0":
                                    return 'Received';
                                case "1":
                                    return 'Processing';
                                case "2":
                                    return 'Available';
                                case "3":
                                    return 'Finish';
                                case "4":
                                    return 'Hold';
                                case "5":
                                    return 'Cancel';
                            }
                        }
                    },
                ],
                "aaSorting": [[0, "asc"]],
                "sAjaxSource": url,
                "fnServerData": function (sSource, aoData, fnCallback) {
                    // Add some extra data to the sender
                    aoData.push();
                    waitingDialog.hide();

                    $.ajax({
                        "dataType": 'json',
                        "type": "POST",
                        "url": sSource,
                        "data": aoData,
                        "success": fnCallback
                    });
                }
            });
        }
        function ajaxDatatable1() {
            var url = "getAllStocksJsonData.ajax";
            $('#table2').dataTable({

                "bJQueryUI": true,
                "bProcessing": true,
                "bServerSide": true,
                "bDestroy": true,
                "bAutoWidth": true,
                "bLengthChange": false,
                //"sScrollX": "940px",
                //"sScrollY": "250px",
                "bScrollCollapse": true,
                "bSort": false,
                "sDom": '<"top"i>rt<"bottom"lp><"clear">',

                "aoColumns": [
                    {"width": "5%"},
                    {"width": "45%"},
                    {"width": "40%"},
                    {"width": "10%", "sType": "numeric"},
                ],
                "aaSorting": [[3, "asc"]],
                "sAjaxSource": url,
                "fnServerData": function (sSource, aoData, fnCallback) {
                    // Add some extra data to the sender
                    aoData.push();
                    $.ajax({
                        "dataType": 'json',
                        "type": "POST",
                        "url": sSource,
                        "data": aoData,
                        "success": fnCallback
                    });
                }
            });
        }
    </script>
</head>
<body>
<%@include file="../../common/navigation.jsp" %>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <ul class="nav nav-sidebar">
                <li class="active"><a href="./pharmacyIndex.page">Overview <span class="sr-only">(current)</span></a>
                </li>
                <li><a href="./checkOrders.page">Orders</a></li>
                <li><a href="./checkHistory.page">History</a></li>

            </ul>
            <ul class="nav nav-sidebar">
                <li><a href="./checkStocks.page">Stocks</a></li>
                <li><a href="./checkList.page">Medicine List</a></li>
            </ul>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="row">
                <div class="alert alert-success alert-dismissible col-md-8" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <strong>There are ${neworder} new received orders!</strong>
                </div>
            </div>
            <div class="row">
                <h1 class="page-header">Order</h1>
                <table id="table1" class="display" cellspacing="0">
                    <thead>
                    <tr>
                        <th align="center" width="200px">ID</th>
                        <th align="center" width="200px">Order Number</th>
                        <th align="center" width="200px">Order Time</th>
                        <th align="center" width="200px">Status</th>
                    </tr>
                    </thead>
                    <tbody align="center">
                    </tbody>
                </table>
            </div>
            <div class="row">
                <h1 class="page-header">Stocks</h1>
                <table id="table2" class="display" cellspacing="0">
                    <thead>
                    <tr>
                        <th align="center" width="200px">ID</th>
                        <th align="center" width="200px">Drug Name</th>
                        <th align="center" width="200px">Drug Producer</th>
                        <th align="center" width="200px">Stock</th>
                    </tr>
                    </thead>
                    <tbody align="center">
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    // For demo to fit into DataTables site builder...
    $('#table1')
            .removeClass('display')
            .addClass('table table-striped table-bordered');
</script>
<script type="text/javascript">
    // For demo to fit into DataTables site builder...
    $('#table2')
            .removeClass('display')
            .addClass('table table-striped table-bordered');
</script>
</body>
</html>