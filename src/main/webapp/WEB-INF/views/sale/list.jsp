<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>红太阳CRM 销售机会</title>
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
    <link rel="stylesheet" href="/static/plugins/moment/momentjs.jsp">
    <link href="/static/plugins/daterangepicker/daterangepicker.css" rel="stylesheet">
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@include file="../include/head.jsp" %>
    <jsp:include page="../include/left.jsp">
        <jsp:param name="menu" value="sale"></jsp:param>
    </jsp:include>


    <div class="content-wrapper">

        <section class="content">
            <%--搜索框--%>
            <div class="box box-primary">
                <div class="box-header with-border">
                    <h2 class="box-title">搜索</h2>
                    <div class="box-tools">
                        <button class="btn btn-box-tool pull-right" data-widget="collapse" data-toggle="tooltip"><i
                                class="fa fa-plus"></i></button>
                    </div>
                </div>
                <div class="box-body">
                    <form>
                        <input type="text" placeholder="请输入机会名称" name="name" id="searchName">
                        <select name="progress" id="searchProgress">
                            <option value="">当前进度</option>
                            <option value="初次接触">初次接触</option>
                            <option value="确定意向">确定意向</option>
                            <option value="递交合同">递交合同</option>
                            <option value="交易完成">交易完成</option>
                            <option value="合同搁置">合同搁置</option>
                        </select>
                        <input type="hidden" id="startTime" name="startDate">
                        <input type="hidden" id="endtTime" name="endDate">
                        <input type="text" placeholder="请输入时间段" id="rangepicker">
                        <button type="button" id="searchBtn"><i class="fa fa-search"></i>搜索</button>

                    </form>
                </div>
            </div>

            <div class="box box-primary">
                <div class="box-header with-border">
                    <h3 class="box-title">机会列表</h3>
                    <button class="btn btn-success btn-xs pull-right" id="newSale"><i class="fa fa-plus"></i>添加商品
                    </button>
                </div>
                <div class="box-body">

                    <table class="table" id="dataTable">
                        <thead>
                        <tr>
                            <th>机会名称</th>
                            <th>关联客户</th>
                            <th>效益价值</th>
                            <th>当前进度</th>
                            <th>最后跟进时间</th>
                            <th>所属员工</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${empty saleList}">
                            <tr>
                                <td colspan="5">暂时没有任何数据</td>
                            </tr>
                        </c:if>

                        </tbody>
                    </table>
                </div>
            </div>
        </section>
    </div>
</div>
<%--添加机会--%>
<div class="modal fade" id="newModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加新机会</h4>
            </div>
            <div class="modal-body">
                <form method="post" id="saveForm">

                    <div class="form-group">
                        <label>机会名称</label>
                        <input type="text" class="form-control" placeholder="请输入商品名" name="name">
                    </div>
                    <div class="form-group">
                        <label>效益价值</label>
                        <input type="text" class="form-control" placeholder="请输入效益价值" name="price">
                    </div>
                    <div class="form-group" id="customerList">
                        <label>关联客户</label>
                        <select class="form-control" name="custid">

                        </select>
                    </div>
                    <div class="form-group">
                        <label>当前进度</label>
                        <select class="form-control" name="progress">
                            <option value="初次接触">初次接触</option>
                            <option value="确定意向">确定意向</option>
                            <option value="递交合同">递交合同</option>
                            <option value="交易完成">交易完成</option>
                            <option value="合同搁置">合同搁置</option>
                        </select>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="saveBtn">保存</button>
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
<script src="/static/plugins/moment/moment.min.js"></script>
<script src="/static/plugins/daterangepicker/daterangepicker.js"></script>

<script>

    $(function () {


        var dataTable = $("#dataTable").DataTable({
            searching: false,

            serverSide: true,
            ajax: {
                url: "/sale/load",
                data:function(dataSouce){
                    dataSouce.name = $("#searchName").val();
                    dataSouce.progress =$("#searchProgress").val();
                    dataSouce.startDate =$("#startTime").val();
                    dataSouce.endDate =$("#endtTime").val();
                }
            },
            ordering: false,
            "autoWidth": false,
            columns: [
                {
                    "data": function (row) {
                        return "<a href='/sale/" + row.id + "'>" + row.name + "</a>";
                    }
                },
                {
                    "data": function (row) {
                        return "<a href='/customer/" + row.custid + "'>" + row.custname + "</a>"
                    }
                },
                {
                    "data": function (row) {
                        return "￥" + row.price;
                    }
                },
                {"data": function(row){
                    if(row.progress =='交易完成'){
                        return "<span class='label label-success'>"+row.progress+"</span>"
                    }
                    if(row.progress =='合同搁置'){
                        return "<span class='label label-danger'>"+row.progress+"</span>"
                    }
                    return row.progress;
                }},
                {
                    "data": function (row) {
                        var lastTime = row.lasttime;
                        var day = moment(lastTime);
                        return day.format("YYYY-MM-DD");
                    }
                },
                {"data": "username"},

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

        $("#saveForm").validate({
            errorClass: "text-danger",
            errorElement: "span",
            rules: {
                name: {
                    required: true,
                },
                price: {
                    required: true,
                    number: true
                }
            },
            messages: {
                name: {
                    required: "请输入机会"
                },
                price: {
                    required: "请输入效益价值",
                    number: "请输入纯数字"
                }
            },
            submitHandler: function (form) {
                $.post("/sale/new", $(form).serialize()).done(function (data) {
                            if (data == "success") {
                                $("#newModal").modal('hide');
                                dataTable.ajax.reload();
                            }
                        })
                        .fail(function () {
                            alert("添加机会失败！")
                        });
            }

        });

        //新增机会
        $("#newSale").click(function () {
            //重置表单
            $("#saveForm")[0].reset(),

                    $.get("/sale/cust.json", function (data) {
                        var $select = $("#customerList select");
                        $select.html("");
                        var option = "<option></option>"
                        if (data && data.length) {
                            for (var i = 0; i < data.length; i++) {
                                var customer = data[i];
                                var option = "<option value='" + customer.id + "'>" + customer.name + "</option>"
                                $select.append(option)
                            }
                        }
                    })


            $("#newModal").modal({
                show: true,
                backdrop: 'static',
                keyboard: false
            });
        });

        $("#saveBtn").click(function () {
            $("#saveForm").submit();
        });

        $("#rangepicker").daterangepicker({
            format: "YYYY-MM-DD",
            separator: "~",
            locale: {
                "applyLabel": "选择",
                cancelLabel: "取消",
                fromLabel: "从",
                toLabel: "到",
                customRangeLabel: "自定义",
                weekLabel: "周",
                daysofweek: [
                    "一",
                    "二",
                    "三",
                    "四",
                    "五",
                    "六",
                    "日",
                ],
                monthNames: [
                    "一月",
                    "二月",
                    "三月",
                    "四月",
                    "五月",
                    "六月",
                    "七月",
                    "八月",
                    "九月",
                    "十月",
                    "十一月",
                    "十二月",
                ],
                firday: 1

            },
            ranges: {
                "今天": [moment(), moment()],
                "昨天": [moment().subtract(1, "days"), moment().subtract(1, "days")],
                "最近7天": [moment().subtract(6, "days")],
                "最近一月": [moment().subtract(29, "days")],
                "本月": [moment().subtract(), moment()],
                "上月": [moment().subtract(1, "month").startOf("month"), moment().subtract(1, "month").endOf("month")],
            },
        });
        $("#rangepicker").on('apply.daterangepicker', function (ev, picker) {
            $("#startTime").val(picker.startDate.format("YYYY-MM-DD"));
            $("#endtTime").val(picker.startDate.format("YYYY-MM-DD"));

        });
        //搜索
        $("#searchBtn").click(function () {
            dataTable.ajax.reload();
        });


    });
</script>
</body>
</html>
