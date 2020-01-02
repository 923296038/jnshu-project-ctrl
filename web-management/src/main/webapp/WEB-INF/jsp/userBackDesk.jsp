<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" %>
<% String path=request.getContextPath();
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<head>
    <title>职业列表</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 引入 Bootstrap -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>

<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    技能树  |  IT修真院
                </h1>
            </div>
        </div>
    </div>

    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    <small>后台管理系统</small>
                </h1>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-4 column">
            <a class="btn btn-primary" href="<%=basePath%>/user/add">新增</a>
            <a class="btn btn-primary" href="<%=basePath%>/a/u/b/profession/id=">查询</a>
        </div>
    </div>
    <div class="row clearfix">
        <div class="col-md-12 column">
            <table class="table table-hover table-striped">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>姓名</th>
                    <th>头像</th>
                    <th>职位</th>
                    <th>介绍</th>
                    <th>是否工作</th>
                    <th>是否为优秀师兄</th>
                    <th>创建时间</th>
                    <th>更新时间</th>
                    <th>创建人</th>
                    <th>修改人</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="student" varStatus="status">
                    <tr>
                        <td>${status.index+1}</td>
                        <td>${student.name}</td>
                        <td>${student.img}</td>
                        <td>${student.position}</td>
                        <td>${student.introduce}</td>
                        <td>${student.working}</td>
                        <td>${student.excellent}</td>
                        <td>${student.create_at}</td>
                        <td>${student.update_at}</td>
                        <td>${student.create_by}</td>
                        <td>${student.update_by}</td>
                        <td>
                            <a href="<%=basePath%>/a/u/b/profession/update/id={id}">编辑</a>
                            <a href="<%=path%>/a/u/b/profession/del/id={id}" methods="post">删除</a>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <br>
            <div>
                <a href="?start=1">[首  页]</a>
                <a href="?start=${page.pageNum-1}">[上一页]</a>
                <a href="?start=${page.pageNum+1}">[下一页]</a>
                <a href="?start=${page.pages}">[末  页]</a>
            </div>
            <br>
        </div>
    </div>
</div>
</div>

