<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>红太阳CRM 客户管理</title>
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
    <jsp:include page="../include/left.jsp">
        <jsp:param name="menu" value="customer"></jsp:param>
    </jsp:include>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <div class="box box-primary">
                <div class="box-header with-border">
                    <h3 class="box-title">客户管理</h3>
                    <button class="btn btn-success btn-xs pull-right" id="newCus"><i class="fa fa-user-plus"></i>添加用户
                    </button>
                </div>
                <div class="box-body">

                    <table class="table" id="dataTable">
                        <thead>
                        <tr>
                            <th></th>
                            <th>用户名</th>
                            <th>电话</th>
                            <th>微信</th>
                            <th>email</th>
                            <th>地址</th>
                            <th>等级</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${empty companyList}">
                            <tr>
                                <td colspan="5">暂时没有任何数据</td>
                            </tr>
                        </c:if>
                        <%--<c:forEach items="${customerList}" var="cus">

                            <tr>
                                <c:choose>

                                    <c:when test="${cus.type=='person'}">
                                        <td><i class="fa fa-user-o"></i></td>
                                        <td><a href="#">}</a>${cus.name}</td>
                                    </c:when>
                                    <c:otherwise>
                                        <td><i class="fa fa-home-o"></i></td>
                                        <td><a href="#"></a>${cus.name}</td>
                                    </c:otherwise>

                                </c:choose>

                                <td>${cus.tel}</td>
                                <td>${cus.weixin}</td>
                                <td>${cus.email}</td>
                                <td>${cus.address}</td>
                                <td>${cus.level}</td>

                            </tr>
                        </c:forEach>--%>
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
<%--添加新客户--%>
<div class="modal fade" id="newModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加新用户</h4>
            </div>
            <div class="modal-body">
                <form action="/customer/new" method="post" id="saveCusForm">
                    <div class="form-group">
                        <label>类型</label>
                        <div>
                            <label class="radio-inline">
                                <input type="radio" name="type" value="person" checked id="ridperson">个人
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="type" value="company" id="ridcompany">公司
                            </label>
                        </div>
                    </div>
                    <%--<input type="hidden" name="fid" value="${id}">--%>
                    <div class="form-group">
                        <label>客户名</label>
                        <input type="text" class="form-control" placeholder="请输入客户名" name="name">
                    </div>
                    <div class="form-group">
                        <label>电话号码</label>
                        <input type="text" class="form-control" placeholder="请输入电话号码" name="tel">
                    </div>
                    <div class="form-group">
                        <label>客户微信</label>
                        <input type="text" class="form-control" placeholder="请输入客户微信" name="weixin">
                    </div>
                    <div class="form-group">
                        <label>客户地址</label>
                        <input type="text" class="form-control" placeholder="请输入客户地址" name="address">
                    </div>
                    <div class="form-group">
                        <label>请输入邮箱</label>
                        <input type="email" class="form-control" placeholder="请输入email" name="emai">
                    </div>
                    <div class="form-group">
                        <label>客户等级</label>
                        <select class="form-control" name="level">
                            <option value=""></option>
                            <option value="★">★</option>
                            <option value="★★">★★</option>
                            <option value="★★★">★★★</option>
                            <option value="★★★★">★★★★</option>
                            <option value="★★★★★">★★★★★</option>
                        </select>
                    </div>
                    <div class="form-group" id="DivComp">
                        <label>所属公司</label>
                        <select class="form-control " name="companyid">

                        </select>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="saveCus">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<%--修改客户--%>
<div class="modal fade" id="editModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">修改客户</h4>
            </div>
            <div class="modal-body">
                <form action="/customer/new" method="post" id="editForm">
                    <%--<input type="hidden" name="fid" value="${id}">--%>
                    <input type="hidden" name="userid" id="editUserid">
                    <input type="hidden" name="id" id="editId">
                    <input type="hidden" name="type" id="editType">
                    <div class="form-group">
                        <label>客户名</label>
                        <input type="text" class="form-control" name="name" id="editName">
                    </div>
                    <div class="form-group">
                        <label>电话号码</label>
                        <input type="text" class="form-control" name="tel" id="editTel">
                    </div>
                    <div class="form-group">
                        <label>客户微信</label>
                        <input type="text" class="form-control" name="weixin" id="editWeinxin">
                    </div>
                    <div class="form-group">
                        <label>客户地址</label>
                        <input type="text" class="form-control" name="address" id="editAddress">
                    </div>
                    <div class="form-group">
                        <label>请输入邮箱</label>
                        <input type="email" class="form-control" name="emai" id="editEamil">
                    </div>
                    <div class="form-group">
                        <label>客户等级</label>
                        <select class="form-control" name="level" id="editLevel">
                            <option value=""></option>
                            <option value="★">★</option>
                            <option value="★★">★★</option>
                            <option value="★★★">★★★</option>
                            <option value="★★★★">★★★★</option>
                            <option value="★★★★★">★★★★★</option>
                        </select>
                    </div>
                    <div class="form-group" id="editCusList">
                        <label>所属公司</label>
                        <select class="form-control " name="companyid" id="editCompanyid">

                        </select>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="editBtn">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script src="/static/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<script src="/static/bootstrap/js/bootstrap.min.js"></script>
<script src="/static/dist/js/app.min.js"></script>
<script src="/static/plugins/datatables/js/jquery.dataTables.min.js"></script>
<script src="/static/plugins/datatables/js/dataTables.bootstrap.min.js"></script>
<script src="/static/plugins/validate/jquery.validate.min.js"></script>

<script>

    $(function () {


        var dataTable = $("#dataTable").DataTable({
            serverSide: true,
            ajax: "/customer/load",
            ordering: false,
            "autoWidth": false,
            columns: [

                {
                    "data": function (row) {
                        if (row.type == 'company') {
                            return "<i class='fa fa-bank'></i>"
                        }
                        return "<i class='fa fa-user'></i>"
                    }
                },
                {
                    "data": function (row) {
                        if (row.companyname) {
                            return '<a href="/customer/' + row.id + '">' + row.name + '</a>' + " - " + '<a href="/customer/' + row.companyid + '">' + row.companyname + '</a>';
                        }
                        return "<a href='/customer/" + row.id + "'>" + row.name + "</a>";
                    }
                },
                {"data": "tel"},
                {"data": "weixin"},
                {"data": "email"},
                {"data": "address"},
                {"data": "level"},
                {
                    "data": function (row) {
                        return "<a href='javascript:;' rel='" + row.id + "' class='editLink'>编辑</a>  "<shiro:hasRole name="经理"> + "<a href='javascript:;' rel='" + row.id + "' class='delLink'>删除</a>　"</shiro:hasRole>;
                    }
                },
            ],
            "language": {
                "search": "客户名称或电话:",
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


        $("#saveCusForm").validate({
            errorClass: "text-danger",
            errorElement: "span",
            rules: {
                name: {
                    required: true,
                },
                tel: {
                    required: true,

                }
            },
            messages: {
                name: {
                    required: "请输入客户名",
                },
                tel: {
                    required: "请输入客户电话",
                }
            },
            submitHandler: function (form) {
                $.post("/customer/new", $(form).serialize())
                        .done(function (data) {
                            if (data == "success") {
                                $("#newModal").modal("hide");
                                dataTable.ajax.reload();
                            }
                        })
                        .fail(function () {
                            alert("客户保存失败");
                        });
            }
        });


        //新增客户
        $("#newCus").click(function () {
            //重置表单
            $("#saveCusForm")[0].reset(),

                //使用ajax 加载公司列表

                    $.get("/customer/company.json", function (data) {
                        var $select = $("#DivComp select");
                        $select.html("");
                        $select.append("<option></option>")

                        if (data && data.length) {
                            for (var i = 0; i < data.length; i++) {
                                var company = data[i];
                                var option = "<option value='" + company.id + "'>" + company.name + "</option>";
                                $select.append(option);
                            }
                        }
                    })
            $("#newModal").modal({
                show: true,
                backdrop: 'static',
                keyboard: false
            });
        });

        $("#saveCus").click(function () {
            $("#saveCusForm").submit();
        });

        $("#ridperson").click(function () {
            if ($(this)[0].checked) {
                $("#DivComp").show();
            }
        });
        $("#ridcompany").click(function () {
            if ($(this)[0].checked) {
                $("#DivComp").hide()
            }
        });

        //删除用户
        <shiro:hasRole name="经理">
        $(document).delegate(".delLink", "click", function () {
            var id = $(this).attr("rel");
            if (confirm("确定要删除客户么？")) {
                $.get("/customer/del/" + id).done(function (data) {
                    if (data == 'success') {
                        dataTable.ajax.reload();
                    }
                }).fail(function () {
                    alert("数据删除失败！")
                })
            }
        });
        </shiro:hasRole>
        //修改客户

        $("#editForm").validate({
            errorClass: "text-danger",
            errorElement: "span",
            rules: {
                name: {
                    required: true
                },
                tel: {
                    required: true
                }
            },
            messages: {
                name: {
                    required: "请输入客户名称"
                },
                tel: {
                    required: "请输入联系电话"
                }
            },

            submitHandler: function (form) {
                $.post("/customer/edit", $(form).serialize()).done(function (data) {
                    if (data == 'success') {
                        $("#editModal").modal('hide');
                        dataTable.ajax.reload();
                    }
                }).fail(function () {
                    alert("客户修改失败！")
                })
            }

        });

        $(document).delegate(".editLink", "click", function () {

            var id = $(this).attr("rel");
            var $select = $("#editCusList select");
            $select.html("");
            $select.append("<option></option>");

            //ajax请求服务端获取id对应的customer对象和公司列表
            $.get("/customer/edit/" + id + ".json").done(function (data) {

                if (data.state == 'success') {

                    if (data.companyList && data.companyList.length) {
//
                        for (var i = 0; i < data.companyList.length; i++) {
                            var company = data.companyList[i];
                            var option = "<option value='" + company.id + "'>" + company.name + "</option>"
                            $select.append(option);
                        }
                    }
                    var cus = data.customer;

                    if (cus.type == "company") {
                        $("#editCusList").hide();
                    } else {
                        $("#editCusList").show();
                    }

                    $("#editId").val(cus.id);
                    $("#editType").val(cus.type);
                    $("#editName").val(cus.name);
                    $("#editTel").val(cus.tel);
                    $("#editWeinxin").val(cus.weixin);
                    $("#editAddress").val(cus.address);
                    $("#editEamil").val(cus.email);
                    $("#editUserid").val(cus.userid);
                    $("#editLevel").val(cus.level);
                    $select.val(cus.companyid);
                }else{
                    alert(data.messages)
                }

                $("#editModal").modal({
                    show: true,
                    backdrop: false,
                    keyboard: false
                });

            }).fail(function () {
                alert("服务器异常")
            });
        });

        $("#editBtn").click(function () {
            $("#editForm").submit();
            $("#editForm")[0].reset();
        });


    });
</script>
</body>
</html>
