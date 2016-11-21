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
    <title>Prescribe Medicines</title>
    <%@include file="../../common/common_header.jsp" %>
    <!-- customized CSS and JavaScript -->
    <link href="static/vendor/boostrap-select/css/bootstrap-select.min.css" rel="stylesheet"/>
    <link href="static/css/domain/doctor/prescribe-medicines.css" rel="stylesheet"/>
    <script src="static/vendor/boostrap-select/js/bootstrap-select.min.js" type="application/javascript"></script>
    <script src="static/js/domain/doctor/prescribe-medicines.js" type="application/javascript"></script>
</head>
<body page="prescribe_medicines">
<%@include file="../../common/navigation.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="row">
                <h1>Prescibe Medicines</h1>
                <hr>
                <div class="container">
                    <div class="row">
                        <div class="form-group">
                            <label for="patient" class="col-md-12"><h3>Patient</h3></label>
                            <div class="col-md-6">
                                <select id="patient" name="patient" class="selectpicker" data-live-search="true"
                                        data-width="auto">
                                </select>
                            </div>
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <div class="form-group">
                            <label class="col-md-12"><h3>Medicines</h3></label>
                            <div class="col-md-8">
                                <!-- drugs start -->
                                <div class="control-group" id="fields">
                                    <div class="controls">
                                        <form role="form" autocomplete="off">
                                            <div class="voca row">
                                                <div class="col-md-3">
                                                    <select name="drug-name" class="selectpicker"
                                                            data-live-search="true" data-width="auto">

                                                    </select>
                                                </div>
                                                <div class="col-md-5">
                                                    <input class="form-control" placeholder="Drug description"
                                                           name="drug-desc" type="text"
                                                           value="">
                                                </div>
                                                <div class="col-md-2">
                                                    <input class="form-control" placeholder="1"
                                                           name="dose" type="number" min="1"
                                                           value="1">
                                                </div>
                                                <button type="button" class="btn btn-success btn-add">
                                                            <span class="glyphicon glyphicon-plus-sign"
                                                                  aria-hidden="true"></span> Add more
                                                </button>
                                            </div>

                                        </form>
                                        <br>

                                    </div>
                                </div>
                                <!-- drugs end -->
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group">
                            <label for="prescription-desc" class="col-md-12"><h3>Description</h3>
                            </label>
                            <div class="col-md-8">
                                    <textarea id="prescription-desc" class="form-control" rows="3"
                                              placeholder="Prescription description content"></textarea>

                            </div>
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label for="repeat"><h3>Repeat times</h3>
                                </label>
                                <input class="form-control col-md-4" placeholder="1" id="repeat"
                                       name="repeat" type="number" min="1"
                                       value="1">
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label for="duration"><h3>Duration</h3>
                                </label>
                                <input class="form-control col-md-4" placeholder="1" id="duration"
                                       name="duration" type="number" min="1"
                                       value="1">
                            </div>
                        </div>
                    </div>
                    <br>
                    <hr>
                    <div class="row">
                        <div class="form-group">
                            <div class="col-md-4">
                                    <span style="font-size: 18px"><b>Total price:</b>&nbsp;&nbsp; $<span
                                            id="total-price" class="form-control-static">0</span></span>
                            </div>
                            <div class="col-xs-12 col-md-2">
                                <a id="prescribe-button" href="javascript:void(0)"
                                   class="btn btn-primary btn-block btn-lg">Prescribe</a>
                            </div>
                            <div class="col-xs-12 col-md-2"><a href="javascript:location.reload()"
                                                               class="btn btn-warning btn-block btn-lg">Cancel</a>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="alert fade prescribe-tips col-md-8" id="prescribe-tips"></div>
                    </div>
                    <br>
                    <br>
                    <br>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
