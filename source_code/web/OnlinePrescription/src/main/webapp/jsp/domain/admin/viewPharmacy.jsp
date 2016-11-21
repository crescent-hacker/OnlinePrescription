<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Doctor Request</title>
    <%@include file="../../common/common_header.jsp" %>
    <script src="static/js/domain/admin/viewPharmacy.js"></script>
    <link href="static/css/domain/admin/custom.css" rel="stylesheet" type="text/css"/>

</head>
<body page="pharmacy_request">
<%@include file="../../common/navigation.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="row">
                <div class="col-md-12">
                    <h1>Pharmacy Details</h1>
                    <hr/>
                    <div class="form-width">
                        <form>
                            <div class="form-group">
                                <label for="name">Name</label>
                                <input type="text" class="form-control" id="name" placeholder="Title"
                                       value="${pharm.name}">
                            </div>
                            <div class="form-group">
                                <label for="street">Street</label>
                                <input type="text" class="form-control" id="street" placeholder="Street"
                                       value="${pharm.street}">
                            </div>
                            <div class="form-group">
                                <label for="postcode">Post Code</label>
                                <input type="text" class="form-control" id="postcode" placeholder="Post Code"
                                       value="${pharm.postcode}">
                            </div>
                            <div class="form-group">
                                <label for="suburb">Suburb</label>
                                <input type="text" class="form-control" id="suburb" placeholder="Suburb"
                                       value="${pharm.suburb}">
                            </div>
                            <div class="form-group">
                                <label for="phone">Phone</label>
                                <input type="text" class="form-control" id="phone" placeholder="Phone"
                                       value="${pharm.phone}">
                            </div>

                            <div class="form-group">
                                <label for="registrationDate">Registration Date</label>
                                <input type="text" class="form-control" id="registrationDate"
                                       placeholder="Registration Date" value="${pharm.registrationDate}">
                            </div>
                            <input type="hidden" id="pharmId" value="${pharm.id}">

                        </form>

                        <div align="center">
                            <c:if test="${actionRequired eq 'yes' }">
                                <button type="button" id='approveBtn' class="btn btn-primary"
                                        onclick="pharmacyAction('approved')">Approve
                                </button>
                                &nbsp;&nbsp;
                                <button type="button" id="rejectBtn" class="btn btn-default"
                                        onclick="pharmacyAction('rejected')">Reject
                                </button>
                            </c:if>
                            <c:if test="${actionRequired ne 'yes' }">
                                <button type="button" id='backBtn' class="btn btn-info"
                                        onclick="pharmacyAction('back')">Back
                                </button>
                            </c:if>
                        </div>

                    </div>
                    <div id="loading" style="display:none" class="loaderPosition"><img alt="Processing"
                                                                                       src="static/images/loading.gif">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>