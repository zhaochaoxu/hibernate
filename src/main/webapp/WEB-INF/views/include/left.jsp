<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2016/7/7
  Time: 17:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!-- Left side column. contains the logo and sidebar -->
<aside class="main-sidebar">

    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">

        <ul class="sidebar-menu">
            <li class="header">目录</li>
            <!-- Optionally, you can add icons to the links -->
            <shiro:hasAnyRoles name="经理,员工">
                <li class="${param.menu=='home' ? 'active':''}"><a href="/home"><i class="fa fa-link"></i> <span>首页</span></a></li>
                <li class="${param.menu=='notice'? 'active':''}"><a href="/notice"><i class="fa fa-bullhorn"></i>
                    <span>公告</span></a></li>
                <li class="${param.menu=='sale'? 'active':''}"><a href="/sale"><i class="fa fa-building-o"></i> <span>销售机会</span></a></li>
                <li class="${param.menu=='customer'? 'active':''}"><a href="/customer"><i class="fa fa-users"></i> <span>客户管理</span></a></li>
                <li class="${param.menu=='chart'?'actie':''}"><a href="/chart"><i class="fa fa-bar-chart"></i> <span>统计</span></a></li>
                <li class="${param.menu=='task'? 'active':''}"><a href="/task"><i class="fa fa-calendar-check-o"></i> <span>待办事项</span></a></li>
                <li class="${param.menu=='document' ? 'active':''}"><a href="/doc"><i class="fa fa-folder"></i> <span>文档管理</span></a></li>
            </shiro:hasAnyRoles>

            <shiro:hasRole name="管理员">
                <li class="treeview">
                    <a href=""><i class="fa fa-cogs"></i> <span>系统管理</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
                    </a>
                    <ul class="treeview-menu">
                        <li class="${param.menu=='users' ? 'actice':''}"><a href="/admin/list">员工管理</a></li>
                        <li><a href="#">系统设置</a></li>
                    </ul>
                </li>
            </shiro:hasRole>
        </ul>
        <!-- /.sidebar-menu -->
    </section>
    <!-- /.sidebar -->
</aside>