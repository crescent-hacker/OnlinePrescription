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
    <title>Dashboard Template for Bootstrap</title>
    <!-- Custom styles for this template -->
    <link href="static/css/domain/pharmacy/dashboard.css" rel="stylesheet">

    <link rel="stylesheet" type="text/css"
          href="https://cdn.datatables.net/v/bs/jq-2.2.3/dt-1.10.12/datatables.min.css"/>

    <script type="text/javascript" src="https://cdn.datatables.net/v/bs/jq-2.2.3/dt-1.10.12/datatables.min.js"></script>

    <script>
        $(function () {
            //alert("Ready!");
            ajaxDatatable();
        });

        function ajaxDatatable() {
            waitingDialog.show();

            var url = "getAllStocksJsonData.ajax";
            $('#table1').dataTable({

                "bJQueryUI": true,
                "bProcessing": true,
                "bServerSide": true,
                "bDestroy": true,
                "bAutoWidth": true,
                //"sScrollX": "940px",
                //"sScrollY": "250px",
                "bScrollCollapse": true,
                "aaSorting": [[0, "asc"]],
                "aoColumns": [
                    {"width": "5%"},
                    {
                        "width": "45%",
                        "data": function (data, type, row, meta) {
                            return '<a href="./Medicine.page?drug_id=' + data[0] + '">' + data[1] + '</a>';
                        }
                    },
                    {"width": "40%"},
                    {"width": "10%"},
                ],
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

    </script>
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
                <li class="active"><a href="./checkStocks.page">Stocks</a></li>
                <li><a href="./checkList.page">Medicine List</a></li>
            </ul>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <h1 class="page-header">Stocks</h1>


            <table id="table1" class="display" cellspacing="0">
                <thead>
                <tr>
                    <th align="center" width="200px">Drug ID</th>
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

<script type="text/javascript">
    // For demo to fit into DataTables site builder...
    $('#table1')
            .removeClass('display')
            .addClass('table table-striped table-bordered');
</script>
</body>
</html>