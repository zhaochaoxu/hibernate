<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>红太阳CRM 公告</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="/static/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="/static/plugins/fontawesome/css/font-awesome.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="/static/dist/css/AdminLTE.min.css">
    <link rel="stylesheet" href="/static/plugins/datatables/css/dataTables.bootstrap.min.css">
    <link rel="stylesheet" href="/static/dist/css/skins/skin-blue.min.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@include file="../include/head.jsp" %>
    <jsp:include page="../include/left.jsp">
        <jsp:param name="menu" value="notice"></jsp:param>
    </jsp:include>


    <div class="content-wrapper">

        <section class="content">
            <div class="box box-primary with-border">
                <div class="box-header">
                    <div class="box-title"><h1>公告列表</h1></div>
                </div>
                <shiro:hasRole name="经理">
                    <div class="box-body">
                        <a href="/notice/new" class="btn btn-success pull-right"><i class="fa fa-plus">添加公告</i></a>
                    </div>
                </shiro:hasRole>
            </div>

            <div class="box-body">

                <table class="table" id="table">
                    <thead>
                    <tr>
                        <th>标题</th>
                        <th>发表时间</th>
                        <th>发表人</th>
                    </tr>
                    </thead>

                    <tbody></tbody>

                </table>
            </div>

        </section>

    </div>
    <!-- /.content-wrapper -->
</div>

<script src="/static/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="/static/bootstrap/js/bootstrap.min.js"></script>
<!-- AdminLTE App -->
<script src="/static/dist/js/app.min.js"></script>
<script src="/static/plugins/datatables/js/jquery.dataTables.min.js"></script>
<script src="/static/plugins/datatables/js/dataTables.bootstrap.min.js"></script>
<script src="/static/plugins/moment/moment.min.js"></script>
<script>

    $(function () {
        $("#table").DataTable({

            searching: false,
            serverSide: true,
            ordering: false,
            "autoWidth": false,
            ajax: "/notice/list",

            columns: [

                {"data": function(row){
                    return "<a href='/notice/"+row.id+"'>"+row.title+"</a>"
                }},
                {
                    "data": function (row) {
                        var time = row.createtime;
                        var day = moment(time);
                        return day.format("YYYY-MM-DD HH:mm");

                    }
                },
                {"data": "realname"},
            ],

            "language": { //定义中文
                "search": "请输入员工姓名或文章标题:",
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
