<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<meta content="text/html;charset=utf-8" http-equiv="Content-Type">
<title>kv存储系统-配置管理系统</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/matrix-style.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/matrix-media.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/reveal.css" />
<link href="<%=request.getContextPath()%>/resources/font-awesome/css/font-awesome.css" rel="stylesheet" />

<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700,800' rel='stylesheet' type='text/css'>
<style type="text/css">
.table th,.table td {
	text-align: center;
	vertical-align: middle !important;
}
</style>
</head>
<body>

	<!--Header-part-->
	<div id="header">
		<h3>
			<img src="<%=request.getContextPath()%>/resources/img/logo.png"
				alt="Logo" />
		</h3>
	</div>
	<!--close-Header-part-->


	<!--top-Header-menu-->
	<div id="user-nav" class="navbar navbar-inverse">
		<ul class="nav">
			<li class="dropdown" id="profile-messages"><a title="" href="#"
				data-toggle="dropdown" data-target="#profile-messages"
				class="dropdown-toggle"><i class="icon icon-user"></i> <span
					class="text">Welcome <font color="yellow">${pageContext.request.userPrincipal.name}</font></span><b
					class="caret"></b></a>
				<ul class="dropdown-menu ">
					<li><a href="#"><i class="icon-user"></i> My Profile</a></li>
					<li class="divider"></li>
					<li><a href="#"><i class="icon-check"></i> My Tasks</a></li>
					<li class="divider"></li>
					<li><a href="<%=request.getContextPath()%>/logout"><i
							class="icon-key"></i> Log Out</a></li>
				</ul></li>

			<li class=""><a title=""
				href="<%=request.getContextPath()%>/logout"><i
					class="icon icon-share-alt"></i> <span class="text">Logout</span></a></li>
		</ul>
	</div>
	<!--close-top-Header-menu-->


	<!--sidebar-menu-->
	<div id="sidebar">

		<ul>
			<li><a href="<%=request.getContextPath()%>/info"><i class="icon icon-flag"></i> <span>已存储信息</span></a></li>
		<c:if test="${pageContext.request.isUserInRole('ROLE_SUPER_ADMIN')}">
			<li><a href="<%=request.getContextPath()%>/index"> <i class="icon icon-home"></i> <span>已配配置信息</span></a></li>
			<li class="active"><a href="<%=request.getContextPath()%>/user/user"> <i class="icon icon-user"></i> <span>用户信息</span> </a></li>
			<li ><a href="<%=request.getContextPath()%>/game/game"> <i class="icon icon-tint"></i> <span>游戏管理</span> </a></li>
		</c:if>
		</ul>
	</div>
	<!--sidebar-menu-->

	<!--main-container-part-->
	<div id="content">
		<!--breadcrumbs-->
		<div id="content-header">
			<div id="breadcrumb">
				<a href="<%=request.getContextPath()%>/index" title="Go to Home"
					class="tip-bottom"><i class="icon-home"></i> Home</a>
			</div>
		</div>
		<!--End-breadcrumbs-->

		<!--Chart-box-->
		<div class="row-fluid">
			<div class="widget-box">
				<div class="widget-content">
					<div class="row-fluid">
						<div class="span12">
							<!-- 表格内容开始 -->
							<c:if test="${not empty list }">
								<div class="widget-box">
									<div class="widget-title">
										<span class="icon"><i class="icon-th"></i></span>
										<h5>用户信息列表</h5>
									</div>
									<div class="widget-content nopadding">
										<table id="example" class="table table-bordered data-table">
											<thead>
												<tr>
													<th>用户名</th>
													<th>密码</th>
													<th>真实姓名</th>
													<th>拥有权限</th>
													<th>注册时间</th>
													<th>简述</th>
													<th>操作</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${empty list }">
												</c:if>
												<c:forEach items="${list}" var="list">
													<tr class="odd">
														<td>${list.userName}</td>
														<td>${list.password}</td>
														<td>${list.userRealName}</td>
														<td><c:if test="${list.role == 'USER' }">测试用户</c:if>
															<c:if test="${list.role == 'ADMIN' }">普通用户</c:if> <c:if
																test="${list.role == 'SUPER_ADMIN' }">超级管理员</c:if></td>
														<td>${list.registerTime}</td>
														<td>
															<p>${list.description}</p>
														</td>
														<td><a href="<%=request.getContextPath()%>/user/update?userBean={userName:'${list.userName }',password:'${list.password}',userRealName:'${list.userRealName}',role:'${list.role}',registerTime:'${list.registerTime }',description:'${list.description }',gameType:'${list.gameType }'}" class="btn btn-success btn-mini"><i class="icon-wrench icon-white">修改</i></a> 
														    <a href="<%=request.getContextPath()%>/user/create" class="btn btn-primary btn-mini"><i class="icon-plus icon-white">新建</i></a> 
														    <a href="javascript:void(0);" id="delete" item-id="${list.userName}" data-reveal-id="myModal" data-animation="fade" class="btn btn-inverse btn-mini"><i class="icon-minus icon-white"> 删除</i></a>
														    <c:choose>
																<c:when test="${list.state == 0 }">
																	<a href="<%=request.getContextPath()%>/user/state?userName=${list.userName }&state=${list.state }" title="点击启用" class="btn btn-warning btn-mini" style="background-color:red">禁用/启用</a>
																</c:when>
																<c:otherwise>
																	<a href="<%=request.getContextPath()%>/user/state?userName=${list.userName }&state=${list.state }" title="点击暂停使用" class="btn btn-warning btn-mini">禁用/启用</a>
																</c:otherwise>
															</c:choose></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
							</c:if>
							<c:if test="${empty list }">
								<div class="alert alert-info alert-block">
									<a class="close" data-dismiss="alert" href="#">×</a>
									<h4 class="alert-heading">Oops!</h4>
									<p>
										您好,当前还没有其它用户,快点击“新建”创建用户吧！<a
											href="<%=request.getContextPath()%>/user/create"
											class="btn btn-success btn-mini">新建</a>
									</p>
								</div>
								</c:if>

						</div>
						<!-- 表格内容结束 -->
					</div>

				</div>
			</div>
		</div>
	</div>
	<hr />

	<!--end-main-container-part-->
	<!--Footer-part-->
	</div>
	<!-- 弹出框测试 -->
	<center><div id="myModal" class="reveal-modal">
	        <h1><font color="red"><i class="icon-umbrella"></i></font></h1>
			<h3 >您确定要删除这个账号吗？</h3><br>
			<a class="close-reveal-modal">&#215;</a>
			<form action="<%=request.getContextPath()%>/user/delete" method="post">
			<input type="hidden" name="userName" value="1" />
			<input type="submit" class="btn btn-inverse" value="确定"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="btn btn-inverse" close-reveal-id="myModal" >取消</a>
			</form>
			</div>
	</center>
	<!-- 弹出框测试结束 -->
	<div class="row-fluid">
		<div id="footer" class="span12">
			2016 &copy; Idreamsky.<a href="http://www.idreamsky.com/">创梦天地</a>
		</div>
	</div>

	<!--end-Footer-part-->
	<script src="<%=request.getContextPath()%>/resources/js/jquery.min.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/jquery.ui.custom.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/matrix.tables.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/jquery.dataTables.min.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/jquery.reveal.js"></script>
	<script type="text/javascript">

		$('a[data-reveal-id]').live('click', function () {
			var id = $(this).attr("item-id");
	        //e.preventDefault();
	        $("#myModal").find("input:hidden").val(id);
	        var modalLocation = $(this).attr('data-reveal-id');
	        $('#' + modalLocation).reveal($(this).data());
	    });
	   
	   	$('a[close-reveal-id]').live('click', function () {
         jQuery("#myModal").trigger('reveal:close');
	        var modalLocation = $(this).attr('close-reveal-id').trigger('reveal:close');
	        $('#' + modalLocation).reveal($(this).data());
	    });
	</script>
</body>
</html>
