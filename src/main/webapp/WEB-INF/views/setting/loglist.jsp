<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>红太阳CRM</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="/static/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="/static/plugins/fontawesome/css/font-awesome.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="/static/dist/css/AdminLTE.min.css">
    <link rel="stylesheet" href="/static/dist/css/skins/skin-blue.min.css">

    <link rel="stylesheet" href="/static/plugins/datatables/css/dataTables.bootstrap.min.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@include file="../include/head.jsp" %>
    <%@include file="../include/left.jsp" %>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">

        <section class="content">

            <div class="box box-primary">

                <div class="box-header with-border">
                    <div class="box-title" style=" font-size: 20px ;color: black">
                        当前用户的登录日志
                    </div>
                </div>
                <div class="box-body">

                    <table class="table" id="table">

                        <thead>
                            <tr>
                                <th>登录时间</th>
                                <th>登录IP</th>
                            </tr>
                        </thead>

                        <tbody>

                        </tbody>
                    </table>

                </div>
            </div>
        </section>

    </div>
    <!-- /.content-wrapper -->
</div>
<!-- ./wrapper -->

<!-- REQUIRED JS SCRIPTS -->

<!-- jQuery 2.2.0 -->
<script src="/static/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="/static/bootstrap/js/bootstrap.min.js"></script>
<!-- AdminLTE App -->
<script src="/static/dist/js/app.min.js"></script>
<script src="/static/plugins/datatables/js/jquery.dataTables.min.js"></script>
<script src="/static/plugins/datatables/js/dataTables.bootstrap.min.js"></script>
<script>
    $(function () {

        var dateTable = $("#table").DataTable({
            searching: false,
            serverSide: true,
            ajax: "/user/log/load",
            ordering: false,
            autowidth:false,
            columns: [
                {"data": "logintime"},
                {"data": "loginip"}
            ],
            "language": { //定义中文
                "search": "请输入书籍名称:",
                "zeroRecords": "没有匹配的数据",
                "lengthMenu": "显示 _MENU_ 条数据",
                "info": "显示从 _START_ 到 _END_ 条数据 共 _TOTAL_ 条数据",
                "infoFiltered": "(从 _MAX_ 条数据中过滤得来)",
                "loadingRecords": "加载中...",
                "processing": "处理中...",
                "paginate": {
                    "first": "首页",
                    "last": "末页",
                    "next": "下一页",
                    "previous": "上一页"
                }
            }
        });


    });
</script>
</body>
</html>
