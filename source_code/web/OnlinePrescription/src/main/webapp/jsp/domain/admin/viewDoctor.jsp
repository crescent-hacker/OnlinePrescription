<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Doctor Request</title>
    <%@include file="../../common/common_header.jsp" %>
    <script src="static/js/domain/admin/viewDoctor.js"></script>
    <link href="static/css/domain/admin/custom.css" rel="stylesheet" type="text/css"/>

</head>
<body page="doctor_request">
<%@include file="../../common/navigation.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="row">
                <div class="col-md-12">
                    <h1>Doctor Details</h1>
                    <hr/>
                    <div class="form-width">
                        <form>
                            <div class="form-group">
                                <label for="title">Title</label>
                                <input type="text" class="form-control" id="title" placeholder="Title"
                                       value="${doctorBo.title}">
                            </div>
                            <div class="form-group">
                                <label for="firstName">First Name</label>
                                <input type="text" class="form-control" id="firstName" placeholder="First Name"
                                       value="${doctorBo.firstName}">
                            </div>
                            <div class="form-group">
                                <label for="lastName">Last Name</label>
                                <input type="text" class="form-control" id="lastName" placeholder="Last Name"
                                       value="${doctorBo.lastName}">
                            </div>
                            <div class="form-group">
                                <label for="dob">DOB</label>
                                <input type="text" class="form-control" id="dob" placeholder="DOB"
                                       value="${doctorBo.dob}">
                            </div>
                            <div class="form-group">
                                <label for="phone">Phone</label>
                                <input type="text" class="form-control" id="phone" placeholder="Phone"
                                       value="${doctorBo.phone}">
                            </div>
                            <div class="form-group">
                                <label for="hospital">Hospital</label>
                                <input type="text" class="form-control" id="hospital" placeholder="Hospital"
                                       value="${doctorBo.hospital}">
                            </div>
                            <div class="form-group">
                                <label for="department">Department</label>
                                <input type="text" class="form-control" id="department" placeholder="Department"
                                       value="${doctorBo.department}">
                            </div>
                            <div class="form-group">
                                <label for="registrationDate">Registration Date</label>
                                <input type="text" class="form-control" id="registrationDate"
                                       placeholder="Registration Date" value="${doctorBo.registrationDate}">
                            </div>
                            <input type="hidden" id="doctorId" value="${doctorBo.docId}">

                        </form>

                        <div align="center">
                            <c:if test="${actionRequired eq 'yes' }">
                                <button type="button" id='approveBtn' class="btn btn-primary"
                                        onclick="doctorAction('approved')">Approve
                                </button>
                                &nbsp;&nbsp;
                                <button type="button" id="rejectBtn" class="btn btn-default"
                                        onclick="doctorAction('rejected')">Reject
                                </button>
                            </c:if>
                            <c:if test="${actionRequired ne 'yes' }">
                                <button type="button" id='backBtn' class="btn btn-info"
                                        onclick="doctorAction('back')">Back
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