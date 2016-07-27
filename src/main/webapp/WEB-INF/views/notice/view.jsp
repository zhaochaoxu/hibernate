<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

        <section class="content-header">
            <h1>　　</h1>
            <ol class="breadcrumb">
                <li><a href="/notice"><i class="fa fa-list"></i> 公告列表</a></li>
                <li class="active">${notice.title}</li>



            </ol>
        </section>
        <!-- Main content -->
        <section class="content">
            <div class="box box-primary">

                <h3 class="box-title">${notice.title}<small> ${notice.realname} <fmt:formatDate value="${notice.createtime}" pattern="y-M-d H:m"/></small></h3>

                <div class="box-header with-border">
                </div>
                <div class="box-body">
                    ${notice.context}
                </div>
                <div class="box-footer">

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
<script src="/static/plugins/datatables/js/jquery.dataTables.min.js"></script>
<script src="/static/plugins/datatables/js/dataTables.bootstrap.min.js"></script>
<script>

    /*$(function () {

        var _event = null;
        $calendar.fullCalendar({
            lang: 'zh-CN',
            buttonText: {
                today: '今天'
            },
            events: "/task/load",
            dayClick: function (date, jsEvent, view) {
                $("#newForm")[0].reset();
                $("#start_time").val(date.format());
                $("#end_time").val(date.format());
                $("#newModal").modal({
                    show: true,
                    backdrop: 'static'
                });
            },
            eventClick: function (calEvent, jsEvent, view) {
                //alert(calEvent.id + " : " + calEvent.title);
                _event = calEvent;
                $("#event_id").val(calEvent.id);
                $("#event_title").text(calEvent.title);
                $("#event_start").text(moment(calEvent.start).format("YYYY-MM-DD"));
                $("#event_end").text(moment(calEvent.end).format("YYYY-MM-DD"));
                if (calEvent.remindertime) {
                    $("#event_remindtime").text(calEvent.remindertime);
                } else {
                    $("#event_remindtime").text('无');
                }
                $("#eventModal").modal({
                    show: true,
                    backdrop: 'static'
                });
            }
        });
        //新增
        $("#color").colorpicker({
            color: '#61a5e8'
        });
        $("#start_time,#end_time").datepicker({
            format: 'yyyy-mm-dd',
            autoclose: true,
            language: 'zh-CN',
            todayHighlight: true
        });
        $("#saveBtn").click(function () {
            if (!$("#task_title").val()) {
                $("#task_title").focus();
                return;
            }
            if (moment($("#start_time").val()).isAfter(moment($("#end_time").val()))) {
                alert("结束时间必须大于开始时间");
                return;
            }
            $.post("/task/new", $("#newForm").serialize()).done(function (result) {
                if (result.state == "success") {
                    //将返回的日程，渲染到日历控件上
                    $calendar.fullCalendar('renderEvent', result.data);
                    $("#newModal").modal('hide');
                }
            }).fail(function () {
                alert("服务器异常");
            });
        });
        //删除日程
        $("#delBtn").click(function () {
            var id = $("#event_id").val();
            if (confirm("确定要删除吗")) {
                $.get("/task/del/" + id).done(function (data) {
                    if ("success" == data) {
                        $calendar.fullCalendar('removeEvents', id);
                        $("#eventModal").modal('hide');
                    }
                }).fail(function () {
                    alert("服务器异常");
                });
            }
        });
        //将日程标记为已完成
        $("#doneBtn").click(function () {
            var id = $("#event_id").val();
            $.post("/task/" + id + "/done").done(function (result) {
                if (result.state == "success") {
                    _event.color = "#cccccc";
                    $calendar.fullCalendar('updateEvent', _event);
                    $("#eventModal").modal('hide');
                }
            }).fail(function () {
                alert("服务器异常");
            });
        });


    });*/
</script>
</body>
</html>
