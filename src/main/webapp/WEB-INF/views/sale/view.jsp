<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>红太阳CRM | 销售机会 | ${sale.name}</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="/static/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="/static/plugins/fontawesome/css/font-awesome.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="/static/dist/css/AdminLTE.min.css">
    <link rel="stylesheet" href="/static/dist/css/skins/skin-blue.min.css">
    <link rel="stylesheet" href="/static/plugins/webupload/webuploader.custom.min.js">
    <link rel="stylesheet" href="/static/plugins/simditor/styles/simditor.css">

</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@include file="../include/head.jsp" %>
    <jsp:include page="../include/left.jsp">
        <jsp:param name="menu" value="sale"/>
    </jsp:include>
    <div class="content-wrapper">
        <section class="content-header">
            <h1>　</h1>
            <ol class="breadcrumb">
                <li><a href="/sale"><i class="fa fa-dashboard"></i> 销售机会列表</a></li>
                <li class="active">${sale.name}</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">

            <div class="box box-primary">
                <div class="box-header">
                    <h3 class="box-title">
                        ${sale.name}
                    </h3>
                    <shiro:hasRole name="经理">
                        <div class="box-tools">
                            <span class="delSale btn btn-danger" rel="${sale.id}">删除</span>
                        </div>
                    </shiro:hasRole>
                </div>
                <div class="box-body">
                    <table class="table">
                        <tr>
                            <td style="width: 80px">关联客户：</td>
                            <td style="width: 80px"><a href="/customer/${sale.custid}">${sale.custname}</a></td>
                            <td style="width: 80px">效益价值：</td>
                            <td style="width: 80px">${sale.price}</td>
                        </tr>
                        <tr>
                            <td>当前进度:</td>
                            <td>${sale.progress} <a href="javascript:;" id="editProgress">修改</a></td>
                            <td>最后跟时间：</td>
                            <td colspan="3">
                                ${empty sale.lasttime ? '暂无数据' : sale.lasttime}
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <%--customer box end--%>
            <div class="row">


                <div class="col-md-8">
                    <div class="box box-default">
                        <div class="box-header with-border">
                            <h3 class="box-title"><i class="fa fa-list"></i> 跟进记录</h3>
                            <span class="btn btn-xs btn-success pull-right" id="newSaleLog"><i class="fa fa-plus"></i>新增记录</span>
                        </div>
                        <div class="box-body">

                            <ul class="timeline">
                                <c:if test="${empty logList}">
                                    <h3>暂无跟进记录</h3>
                                </c:if>
                                <c:forEach items="${logList}" var="saleLog">

                                    <li>
                                        <c:choose>

                                            <c:when test="${saleLog.type=='auto'}">
                                                <i class="fa fa-history bg-yellow"></i>
                                            </c:when>

                                            <c:otherwise>
                                                <i class="fa fa-commenting bg-aqua"></i>
                                            </c:otherwise>
                                        </c:choose>
                                        <div class="timeline-item">
                                            <span class="time"><i class=" fa fa-clock-o"></i><span class="timeago"
                                                                                                   title="${saleLog.createtime}"></span></span>
                                            <h4 class="timeline-item no-border">${saleLog.context}</h4>
                                        </div>
                                    </li>

                                </c:forEach>
                                <li class="fa-clock-o bg-gray"></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <%--销售机会的相关资料--%>
                <div class="col-md-4">
                    <div class="box box-default">
                        <div class="box-header with-border">
                            <h3 class="box-title"><i class="fa fa-file"></i>相关资料</h3>
                            <div class="box-tools">
                                <span class=" btn btn-default btn-xs pull-right" id="picker"><i class="fa fa-upload">上传文件</i></span>
                            </div>
                            <div class="box-body">

                                <ul class="list-unstyled files">
                                    <c:if test="${empty saleFileList}">
                                        暂时没有数据
                                    </c:if>
                                    <c:forEach items="${saleFileList}" var="file">
                                        <p><a href="#">${file.name}</a></p>
                                    </c:forEach>
                                </ul>

                            </div>
                        </div>

                    </div>

                    <div class="box box-default">
                        <div class="box-header with-border">
                            <h3 class="box-title"><i class="fa fa-calendar-check-o"></i> 代办任务</h3>
                            <button id="newTask" class=" btn btn-success btn-xs pull-right"><i class="fa fa-plus "></i>
                            </button>
                        </div>
                        <div class="box-body">

                            <h5>暂无代办任务</h5>
                        </div>
                    </div>
                </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
</div>
<%--添加待办事项--%>
<div class="modal fade" id="newTaskModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">新增待办事项</h4>
            </div>
            <div class="modal-body">

                <form id="newTaskForm">

                    <input type="hidden" name="salesid" value="${sale.id}">
                    <input type="hidden" name="csutid" value="${sale.custid}">
                    <div class="form-group">
                        <label>代办内容</label>
                        <input type="text" class="form-control" name="title" id="taskTitle">
                    </div>

                    <div class="form-group">
                        <label>开始时间</label>
                        <input type="text" class="form-control" name="start" id="startTime">
                    </div>
                    <div class="form-group">
                        <label>结束时间</label>
                        <input type="text" class="form-control" name="end" id="endTime">
                    </div>
                    <div class="form-group">
                        <label>提醒时间</label>
                        <select name="hour" style="width: 100px">
                            <option value=""></option>
                            <option value="0">0</option>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option value="6">6</option>
                            <option value="7">7</option>
                            <option value="8">8</option>
                            <option value="9">9</option>
                            <option value="10">10</option>
                            <option value="11">11</option>
                            <option value="12">12</option>
                            <option value="13">13</option>
                            <option value="14">14</option>
                            <option value="15">15</option>
                            <option value="16">16</option>
                            <option value="17">17</option>
                            <option value="18">18</option>
                            <option value="19">19</option>
                            <option value="20">20</option>
                            <option value="21">21</option>
                            <option value="22">22</option>
                            <option value="23">23</option>
                        </select>
                        :
                        <select name="min" style="width: 100px">
                            <option value=""></option>
                            <option value="0">0</option>
                            <option value="5">5</option>
                            <option value="10">10</option>
                            <option value="15">15</option>
                            <option value="20">20</option>
                            <option value="25">25</option>
                            <option value="30">30</option>
                            <option value="35">35</option>
                            <option value="40">40</option>
                            <option value="45">45</option>
                            <option value="50">50</option>
                            <option value="55">55</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>显示颜色</label>
                        <input type="text" class="form-control" name="color" id="taskColor">
                    </div>
                </form>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" id="saveBtn">保存</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div>
</div>
<!-- 修改当前进度 -->
<div class="modal fade" id="editProgressModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">修改当前进度</h4>
            </div>
            <div class="modal-body">
                <form id="editForm" action="/sale/progress/edit" method="post">
                    <input type="hidden" value="${sale.id}" name="id">
                    <select class="form-group" name="progress">
                        <option value="">请选择当前进度</option>
                        <option value="初次接触">初次接触</option>
                        <option value="确定意向">确定意向</option>
                        <option value="递交合同">递交合同</option>
                        <option value="交易完成">交易完成</option>
                        <option value="合同搁置">合同搁置</option>
                    </select>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="editBtn">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<%--添加跟进记录--%>
<div class="modal fade" id="newSaleLogModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加跟进记录</h4>
            </div>
            <div class="modal-body">
                <form id="newSaleLogForm" action="/sale/saveSaleLog" method="post">
                    <input type="hidden" name="salesid" value="${sale.id}">
                    <div class="form-group">
                        <label>跟进记录</label>
                        <textarea name="context" id="context" rows="10" class="form-control"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="saveSaleLogBtn">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- jQuery 2.2.0 -->
<script src="/static/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="/static/bootstrap/js/bootstrap.min.js"></script>
<!-- AdminLTE App -->
<script src="/static/dist/js/app.min.js"></script>
<script src="/static/plugins/webupload/webuploader.custom.min.js"></script>
<script src="/static/plugins/simditor/scripts/module.min.js"></script>
<script src="/static/plugins/simditor/scripts/hotkeys.min.js"></script>
<script src="/static/plugins/simditor/scripts/uploader.min.js"></script>
<script src="/static/plugins/simditor/scripts/simditor.min.js"></script>
<script src="/static/plugins/moment/moment.min.js"></script>
<script src="/static/plugins/timeago/timeago.js"></script>
<script src="/static/plugins/timeago/timeago_zh_cn.js"></script>
<script src="/static/plugins/fullcalendar/fullcalendar.min.js"></script>
<script src="/static/plugins/datepicker/bootstrap-datepicker.js"></script>

<script>
    $(function () {

        //相对时间
        $(".timeago").timeago();
        //上传文件
        var upload = WebUploader.create({
            swf: "/static/plugins/webupload/Uploader.swf",
            server: "/sale/file/upload",
            pick: "#picker",
            fileVail: "file",
            formData: {"salesid": "${sale.id}"},
            auto: true,
        });

        //文件上传成功
        upload.on("startUpload", function () {
            $("#picker").html("<i class='fa fa-upload fa-spin'></i>文件上传中...").attr("disabled", "disabled");
        });
        upload.on("uploadSuccess", function (file, data) {

            if (data._raw == 'success') {
                window.history.go(0);
            }

        });
        upload.on("uploadError", function (file) {
            alert("文件上传失败！")
        });
        upload.on("uploadComplete", function (file) {
            $("#picker").html("<i class='fa fa-upload'></i>上传文件").removeAttr("disabled");
        });

        //修改进度

        $("#editProgress").click(function () {
            $("#editProgressModal").modal({
                show: true,
                backdrop: 'static',
                keyboard: false
            });
        });

        $("#editBtn").click(function () {
            $("#editForm").submit();
        });
        //删除销售机会
        <shiro:hasRole name="经理">
        $(document).delegate(".delSale", "click", function (data) {
            var id = $(this).attr('rel');
            if (confirm("确定要删除此条记录么?")) {
                window.location.href = "/sale/del" + id;
            }
        });
        </shiro:hasRole>

        //在线编译器
        var context = new Simditor({
            textarea: ("#context"),
            placeholder: "请输入跟进内容",
            toolbar: false//不显示在线调试格式 为了编写的格式统一
        });

        //新增记录
        $("#newSaleLog").click(function () {
            $("#newSaleLogModal").modal({
                show: true,
                backdrop: 'static',
                keyboard: false,
            });
        });
        //保存跟进记录
        $("#saveSaleLogBtn").click(function () {
            if (context.getValue()) {
                $("#newSaleLogForm").submit();
                $("#newSaleLogForm")[0].reset();
            } else {
                $("#context").focus();
            }
        });

        //添加任务
        $("#newTask").click(function () {
            $("#newTaskModal").modal({
                show: true,
                backdrop: 'static',
                keyboard: false
            });
        });
        $("#startTime,#endTime").datepicker({
            format: 'yyyy-mm-dd',
            autoclose: true,
            language: 'zh-CN',
            todayHighlight: true
        });

        $("#newTaskForm").validate({
            rules:{
                title:{
                    required:true
                }
            },
            messages:{
                title:{
                    required:'请添加任务内容'
                }
            }

        })

    });
</script>
</body>
</html>