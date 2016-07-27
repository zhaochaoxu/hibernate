<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>红太阳CRM 文档管理</title>
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
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@include file="../include/head.jsp" %>
    <jsp:include page="../include/left.jsp">
        <jsp:param name="menu" value="document"></jsp:param>
    </jsp:include>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <div class="box box-primary">
                <div class="box-header with-border">
                    <h3 class="box-title">文档列表-${fid}</h3>
                    <span class=" btn btn-facebook btn-xs pull-right" id="picker"><i class="fa fa-upload"></i>上传文件</span>
                    <button class="btn btn-success btn-xs pull-right" id="newDir"><i class="fa fa-folder"></i>新建文件夹
                    </button>
                </div>
                <div class="box-body">

                    <table class="table">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>文件名</th>
                            <th>文件大小</th>
                            <th>创建人</th>
                            <th>创建时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${empty documentlist}">
                            <tr>
                                <td colspan="5">暂时没有任何数据</td>
                            </tr>
                        </c:if>

                        <c:forEach items="${documentlist}" var="doc">

                            <tr>
                                <c:choose>

                                    <c:when test="${doc.type=='dir'}">
                                        <td><i class="fa fa-folder-o"></i></td>
                                        <td><a href="/doc?fid=${doc.id}">${doc.name}</a></td>
                                    </c:when>
                                    <c:otherwise>
                                        <td><i class="fa fa-file-o"></i></td>
                                        <td><a href="/doc/download/${doc.id}">${doc.name}</a></td>
                                    </c:otherwise>

                                </c:choose>
                                <td>${doc.size}</td>
                                <td>${doc.createuser}</td>
                                <td><fmt:formatDate value="${doc.createtime}" pattern="y-M-d H:mm"/> </td>

                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
</div>
<!-- ./wrapper -->

<div class="modal fade" id="dirModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">新建文件夹</h4>
            </div>
            <div class="modal-body">
                <form action="/doc/dir/new" method="post" id="saveDirForm">
                    <input type="hidden" name="fid" value="${fid}">
                    <div class="form-group">
                        <label>文件夹名称</label>
                        <input type="text" class="form-control" name="name" id="dirname">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="saveDirBtn">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script src="/static/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<script src="/static/bootstrap/js/bootstrap.min.js"></script>
<script src="/static/dist/js/app.min.js"></script>
<script src="/static/plugins/webupload/webuploader.custom.min.js"></script>

<script>

    $(function () {

        //上传文件

        var upload = WebUploader.create({
            swf:"/static/plugins/webupload/Uploader.swf",
            server:"doc/file/upload",
            pick:"#picker",
            fileVaiL:"file",
            formData:{"fid":"${fid}"},
            auto:true,//自动上传文件


        });

        upload.on("startUpload",function(){
            $("#picker").html("<i class='fa fa-upload fa-spin'></i>文件上传中...").attr("disabled","disable");
        });

        upload.on("uploadSuccess",function(file,data){
            if(data._raw=='success'){
                window.history.go(0);
            }
        });

        upload.on("uploadError",function(file){
            alert("文件上传失败")

        });
        upload.on("uploadComplete",function(file){
            $("#picker").html("<i class='fa fa-upload'></i>上传文件").removeAttr("disabled");

        })



        //新建文件夹
        $("#newDir").click(function () {
            $("#dirModal").modal({
                show: true,
                backdrop: 'static',
                keyboard: false
            });
        });

        $("#saveDirBtn").click(function () {

            if (!$("#dirname").val()) {
                $("#dirname").focus();
                return;
            }
            $("#saveDirForm").submit();
        });

    });
</script>
</body>
</html>
