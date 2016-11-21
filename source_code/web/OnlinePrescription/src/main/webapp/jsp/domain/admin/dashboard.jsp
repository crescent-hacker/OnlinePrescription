<!DOCTYPE html>
<html lang="en">
<head>
    <title>Dashboard</title>
    <%@include file="../../common/common_header.jsp" %>
    <!-- Chart JS -->
    <script src="static/vendor/canvasjs/canvasjs.min.js"></script>
    <script type="text/javascript">
        $(function () {
            doctorChart();
            pharmChart();
        });

        //Chart Function for Doctor
        function doctorChart() {
            var chart = new CanvasJS.Chart("chartContainer", {
                title: {
                    text: "Doctor Requests"
                },
                animationEnabled: true,
                theme: "theme2",
                data: [
                    {
                        type: "doughnut",
                        indexLabelFontFamily: "Garamond",
                        indexLabelFontSize: 20,
                        startAngle: 0,
                        indexLabelFontColor: "dimgrey",
                        indexLabelLineColor: "darkgrey",
                        toolTipContent: "{y} %",

                        dataPoints: [
                            {y: '${docPen}', indexLabel: "Pending {y}%"},
                            {y: '${docApp}', indexLabel: "Approved {y}%"},
                            {y: '${docRej}', indexLabel: "Rejected {y}%"}
                        ]
                    }
                ]
            });

            chart.render();
        }
        //Chart for Pharmacy
        function pharmChart() {
            var chart = new CanvasJS.Chart("chartContainer2", {
                title: {
                    text: "Pharmacy Requests"
                },
                animationEnabled: true,
                theme: "theme2",
                data: [
                    {
                        type: "doughnut",
                        indexLabelFontFamily: "Garamond",
                        indexLabelFontSize: 20,
                        startAngle: 0,
                        indexLabelFontColor: "dimgrey",
                        indexLabelLineColor: "darkgrey",
                        toolTipContent: "{y} %",

                        dataPoints: [
                            {y: '${pharmPen}', indexLabel: "Pending {y}%"},
                            {y: '${pharmApp}', indexLabel: "Approved {y}%"},
                            {y: '${pharmRej}', indexLabel: "Rejected {y}%"}
                        ]
                    }
                ]
            });

            chart.render();
        }
    </script>

</head>
<body page="dashboard">
<%@include file="../../common/navigation.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="row">
                <div class="col-md-12">
                    <h1>Dashboard</h1>
                    <hr/>
                    <div class="col-md-6">
                        <div id="chartContainer" style="height: 400px; " class="docChartPosition">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="chartContainer2" style="height: 400px; " class="pharmacyChartPosition">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>