<%@ page import="org.apache.shiro.SecurityUtils" %>
<%@ page import="domainModel.system.User" %>
<%@ page import="domainModel.system.SessionAttrKey" %>
<%@ page import="domainModel.system.UserType" %>
<%
    User user = null;
    try {
        user = (User) SecurityUtils.getSubject().getSession().getAttribute(SessionAttrKey.USER);
    } catch (Exception e) {
        response.sendRedirect("/");
    }
    //process subsystem name
    String subSystemName = UserType.getSystemName(user.getUserType());
    //subsystem home page
    String subSystemHome = UserType.getRedirectLink(user.getUserType());
%>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand brand" href="<%=subSystemHome%>">
               &nbsp;&nbsp;<%=subSystemName%>
            </a>

        </div>


        <!-- Admin Navigation Header -->
        <div class="collapse navbar-collapse col-md-offset-4 col-md-5" style="float:right" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                       aria-haspopup="true" aria-expanded="false">Welcome, <%=user.getUserName()%><span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="javascript:void(0)" data-toggle="modal" data-target="#exampleModal" data-whatever="@mdo"><span class="glyphicon glyphicon-cog" aria-hidden="true"></span>&nbsp;View Profile</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="logout.ajax"><span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>&nbsp;Logout</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div><!-- /.container-fluid -->
</nav>
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="exampleModalLabel">User profile</h4>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label for="recipient-name" class="control-label">Recipient:</label>
                        <input type="text" class="form-control" id="recipient-name">
                    </div>
                    <div class="form-group">
                        <label for="message-text" class="control-label">Message:</label>
                        <textarea class="form-control" id="message-text"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" id="Edit" class="btn btn-warning">Edit</button>
                <button type="button" id="Submit" class="btn btn-primary">Submit</button>
            </div>
        </div>
    </div>
</div>
