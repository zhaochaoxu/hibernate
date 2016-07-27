<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>红太阳集团CRM</title>
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

    <%@include file="../include/head.jsp" %>
    <jsp:include page="../include/left.jsp">
        <jsp:param name="menu" value="chart"></jsp:param>
    </jsp:include>


    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content">

            <div class="box box-primary">
                <div class="box-header with-border">
                    <h2 class="box-title">统计图</h2>
                </div>

            </div>
            <div class="row">
                <div class="col-md-4">
                    <div class="info-box">

                        <span class="info-box-icon bg-aqua"><i class="fa fa-plus"></i></span>
                        <div class="info-box-content">
                            <span class="info-box-text">本月新增客户数量</span>
                            <span class="info-box-number">${newCustomerCount}</span>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="info-box">
                        <
                        <span class="info-box-icon bg-green"><i class="fa fa-flag"></i></span>
                        <div class="info-box-content">
                            <span class="info-box-text">本月完成交易次数</span>
                            <span class="info-box-number">${saleCount}</span>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="info-box">

                        <span class="info-box-icon bg-red"><i class="fa fa-money"></i></span>
                        <div class="info-box-content">
                            <span class="info-box-text">本月交易额</span>
                            <span class="info-box-number">￥<fmt:formatNumber value="${saleMoney}"/> </span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="box box-primary">

                <div class="box-header with-border">
                    <div class="box-title">饼状图</div>
                </div>


                <div class="box-body">
                    <div id="pieChart" style="width: 600px;height: 400px;"></div>
                </div>


            </div>

            <div class="box box-primary">

                <div class="box-header with-border">

                    <div class="box-title">柱状图</div>
                </div>

                <div class="box-body">
                    <div id="barChart"  style="width: 600px;height: 400px"></div>
                </div>
            </div>
        </section>

    </div>
    <!-- /.content-wrapper -->
</div>

<script src="/static/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="/static/bootstrap/js/bootstrap.min.js"></script>
<%--统计插件--%>
<script src="/static/chart/echarts.simple.min.js"></script>


<script>


    $(function () {

        var pieChart = echarts.init($("#pieChart")[0]);
        var pieOption = {
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            series: [
                {
                    name: '访问来源',
                    type: 'pie',
                    data: [],
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };
        pieChart.setOption(pieOption);
        $.get("/chart/progress/load", function (data) {
            pieChart.setOption({
                series: [{
                    data: data
                }]
            })
        })

        //柱状图
        var barChart = echarts.init($("#barChart")[0]);
         var barOption = {
            xAxis: {
                data: []
            },
            yAxis: {},
            series: [{
                name: '业绩',
                type: 'bar',
                data: []
            }]
        };
        barChart.setOption(barOption);
        $.get("/chart/user/money", function (data) {
            console.log(data)
            barChart.setOption({
                xAxis: {
                    data: data.names
                },
                series: [{
                    data: data.values
                }]
            });
        });
    });


</script>
</body>
</html>
