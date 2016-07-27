<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>AdminLTE 2 | Starter</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="/static/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="/static/plugins/fontawesome/css/font-awesome.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="/static/dist/css/AdminLTE.min.css">
    <link rel="stylesheet" href="/static/dist/css/skins/skin-blue.min.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@include file="../include/head.jsp"%>
    <%@include file="../include/left.jsp"%>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">

        <section class="content">
        <div class="box box-primary with-border">

            <div class="box-header">
                <div class="box-title">修改用户</div>
            </div>

            <div class="form-group">
                <label>账号</label>
                <input type="text" class="form-control" disabled name="username">
            </div>
            <div class="form-group">
                <label>真实名字</label>
                <input type="text" class="form-control"  name="realname">
            </div>
            <div class="form-group">
                <label>微信</label>
                <input type="text" class="form-control" name="weixin">
            </div>
            <div class="form-group">
                <label>角色</label>
                <select class="form-control" name="roleid">
                    <c:forEach items="${rolelist}" var="role">
                        <option value="${role.id}">${role.rolename}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label>状态</label>
                <select class="form-control" name="enable">
                    <option value="ture">正常</option>
                    <option value="false">正常</option>
                </select>
            </div>
        </div>

        </section>

    </div>
</div>

<script src="/static/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="/static/bootstrap/js/bootstrap.min.js"></script>
<!-- AdminLTE App -->
<script src="/static/dist/js/app.min.js"></script>

</body>
</html>
