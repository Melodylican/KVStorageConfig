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
 						<h3 style="color:red;">${message}</h3>
					</div>

				</div>
			</div>
		</div>
	</div>
		<hr />

		<!--end-main-container-part-->
		<!--Footer-part-->
	</div>

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

		
	</script>
</body>
</html>
