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
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/matrix-style.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/matrix-media.css" />
<link
	href="<%=request.getContextPath()%>/resources/font-awesome/css/font-awesome.css"
	rel="stylesheet" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/reveal.css" />
<link
	href='http://fonts.googleapis.com/css?family=Open+Sans:400,700,800'
	rel='stylesheet' type='text/css'>
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
	<div id="user-nav" class="navbar navbar-inverse pull-right">
		<ul class="nav pull-right">
			<li class="dropdown" id="profile-messages">
			 <a title="" href="#" data-toggle="dropdown" data-target="#profile-messages" class="dropdown-toggle"><i class="icon icon-user"></i>
			      <span class="text">Welcome <font color="yellow">${pageContext.request.userPrincipal.name}</font></span><b class="caret"></b></a>
				<ul class="dropdown-menu ">
					<li><a href="#"><i class="icon-user"></i> My Profile</a></li>
					<li class="divider"></li>
					<li><a href="#"><i class="icon-check"></i> My Tasks</a></li>
					<li class="divider"></li>
					<li><a href="<%=request.getContextPath()%>/logout"><i
							class="icon-key"></i> Log Out</a></li>
				</ul>
			</li>

			<li class=""><a title="" href="<%=request.getContextPath()%>/logout">
			     <i class="icon icon-share-alt"></i> <span class="text">Logout</span></a>
		   </li>
		</ul>
		
	</div>
	<!--close-top-Header-menu-->


	<!--sidebar-menu-->
	<div id="sidebar">

		<ul>
		<li><a href="<%=request.getContextPath()%>/info"><i
					class="icon icon-flag"></i> <span>已存储信息</span></a></li>
			</li>
		<c:if test="${pageContext.request.isUserInRole('ROLE_SUPER_ADMIN')}">
			<li class="active"><a href="<%=request.getContextPath()%>/index"><i class="icon icon-home"></i> <span>已配配置信息</span></a></li>
			<li class="submenu"><a href="<%=request.getContextPath()%>/user/user"><i class="icon icon-user"></i> <span>用户信息</span> </a></li>
			<li class="submenu"><a href="<%=request.getContextPath()%>/game/game"><i class="icon icon-tint"></i> <span>游戏管理</span> <!--  <span class="label label-important">4</span> --></a></li>
		</c:if>
		</ul>
	</div>
	<!--sidebar-menu-->

	<!--main-container-part-->
	<div id="content">
		<!--breadcrumbs-->
		<div id="content-header">
			<div id="breadcrumb">
				<a href="<%=request.getContextPath()%>/index" title="Go to Home" class="tip-bottom"><i
					class="icon-home"></i> Home</a>
			</div>
		</div>
		<!--End-breadcrumbs-->


		<!--Chart-box-->
		<div class="row-fluid">
			<div class="widget-box">
				<div class="widget-content">
					<div class="row-fluid">
						<div class="span12">
						<c:if test="${not empty list }">
							<!-- 表格内容开始 -->
							<div class="widget-box">
								<div class="widget-title">
									<span class="icon"><i class="icon-th"></i></span>
									<h5>已配配置列表</h5>
								</div>
								<div class="widget-content nopadding" >
									<table id="example" class="table table-bordered data-table" >
										<thead>
        									<tr>
        										 <th rowspan="2">游戏名</th>
         										 <th colspan="4">Master</th>
                                                 <th colspan="5">Slave</th>
                                                 <th rowspan="2">数据库名</th>
                                                 <th rowspan="2">表名</th>
                                                 <th rowspan="2">操作</th>
                                            </tr>
                                            <tr>
        										 <th>ip</th>
         										 <th>port</th>
                                                 <th>用户名</th>
                                                 <th>密码</th>
        										 <th>ip</th>
         										 <th>port</th>
                                                 <th>用户名</th>
                                                 <th>密码</th>                                                 
                                                 <th>权重</th>
                                            </tr>
										</thead>
										<tbody>
										<!-- 
										<tr>
										   <td >苍穹变</td>
										   <td >127.0.0.1</td>
										   <td >3306</td>
										   <td>chris</td>
										   <td>12345</td>
										   
										   <td >127.0.0.1<br>192.168.2.145</td>
										   <td >3306<br>3317</td>
										   <td>chris<br>chris</td>
										   <td>12345<br>12345</td>
										   <td>20<br>80</td>
										   <td>dbname</td>
										   <td>tbname</td>
										   <td>
										      <a href="#" class="btn btn-primary btn-mini"><i class="icon-plus icon-white">新建</i></a>
										      <a href="#" class="btn btn-primary btn-mini"><i class="icon-plus icon-white">新建</i></a>
										      <a href="#" class="btn btn-primary btn-mini"><i class="icon-plus icon-white">新建</i></a>
										   </td>
										</tr>
										 -->
										<c:forEach items="${list}" var="list">
											<tr class="gradeX">
												<td >${list.gameName }</td>
												<td >${list.mIp}</td>
												<td >${list.mPort}</td>
												<td >${list.mUserName}</td>
												<td >${list.mPassword}</td>
												
												<td >${list.sIp}</td>
												<td >${list.sPort}</td>
												<td >${list.sUserName}</td>
												<td >${list.sPassword}</td>
												<td >${list.sWeight}</td>
												<td>${list.dbName }</td>
												<td>${list.tbName }</td>												
												<td><a href="<%=request.getContextPath()%>/store/create" class="btn btn-primary btn-mini"><i class="icon-plus icon-white">新建</i></a>
													<a href="<%=request.getContextPath()%>/store/update?id=${list.id }" class="btn btn-success btn-mini"><i class="icon-wrench icon-white">修改</i></a> 
													<a href="javascript:void(0);" id="delete" item-id="${list.id}" data-reveal-id="myModal" data-animation="fade" class="btn btn-inverse btn-mini"><i class="icon-minus icon-white"> 删除</i></a>

												</td>												
											</tr>

											</c:forEach>
										</tbody>  
									</table>
								</div>
								</c:if>
								<!-- 如果没有记录给出新建按钮 -->
								<c:if test="${empty list }">
								<div class="alert alert-info alert-block">
									<a class="close" data-dismiss="alert" href="#">×</a>
									<h4 class="alert-heading">Oops!</h4>
									<p>
										您好,当前还没有配置信息,快点击“新建”创建配置吧！<a
											href="<%=request.getContextPath()%>/store/create"
											class="btn btn-success btn-mini">新建</a>
									</p>
								</div>
								</c:if>
								<!-- 新建提示结束 -->
								
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
			<h3 >您确定要删除这个配置信息吗？</h3><br>
			<a class="close-reveal-modal">&#215;</a>
			<form action="<%=request.getContextPath()%>/store/delete" method="post">
			<input type="hidden" name="id" value="1" />
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
	<script
		src="<%=request.getContextPath()%>/resources/js/jquery.ui.custom.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/matrix.tables.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/jquery.dataTables.min.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/jquery.reveal.js"></script>
	<script type="text/javascript">
	    $(document).ready( function() {
		       $('#example').dataTable( {
		         "oLanguage": {
		           "sLengthMenu": 'Display <select>'+
		             '<option value="10">10</option>'+
		            '<option value="20">20</option>'+
		             '<option value="30">30</option>'+
		            '<option value="40">40</option>'+
		            '<option value="50">50</option>'+
		             '<option value="-1">All</option>'+
		             '</select> records'
		         },
		         "oPaginate": {
		        	 "sFirst": "首页",
		        	 "sLast": "尾页",
		        	 "sNext": "下一页",
		        	 "sPrevious": "上一页"
		         },
		         "sEmptyTable": "没有当前数据的配置项",
		         "sSearch": "查询:",
		       } );
		     } );
	    
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
