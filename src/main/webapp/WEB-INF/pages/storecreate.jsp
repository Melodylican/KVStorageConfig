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
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/select2.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/font-awesome/css/font-awesome.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/uniform.css" />
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
			<li class="active"><a href="<%=request.getContextPath()%>/index"> <i class="icon icon-home"></i> <span>已配配置信息</span></a></li>
			<li ><a href="<%=request.getContextPath()%>/user/user"> <i class="icon icon-user"></i> <span>用户信息</span> </a></li>
			<li><a href="<%=request.getContextPath()%>/game/game"> <i class="icon icon-tint"></i> <span>游戏管理</span> </a></li>
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
							<!-- 表单信息 -->
							<div class="widget-box">
								<div class="widget-title">
									<span class="icon"> <i class="icon-align-justify"></i>
									</span>
									<h5>新建 - KV - 存储配置</h5>
								</div>
								<div class="widget-content nopadding">
									<form action="<%=request.getContextPath()%>/store/savecreate" method="post" class="form-horizontal" name="basic_validate" id="basic_validate">
										<div class="control-group">
											<label class="control-label">游戏名</label>
											<div class="controls">
												<select class="span5" name="gameName">
													<c:forEach items="${gameList}" var="nameList">
									                         <option <c:if test="${storeBean.gameName==nameList.gameName }">selected="true"</c:if>  value="${nameList.gameName}">${nameList.gameName}</option>
									                </c:forEach>
												</select>
											</div> 
										</div>
									    <div class="control-group">
											<label class="control-label">主数据库配置</label>
											<div class="controls">
												<textarea name="master" rows="10" placeholder="输入json格式主的配置" class="span5"></textarea>
												<textarea rows="10" disabled="" color=red class="span5">请按照如下格式进行配置:
												 [{"port":3317,"ip":"192.168.2.44","userName":"root","password":"123456"},{"port":3317,"ip":"192.168.2.44","userName":"root","password":"123456"}]</textarea>
											</div>
										</div>
							
										<div class="control-group">
											<label class="control-label">从数据库配置</label>
											<div class="controls">
												<textarea name="slave" rows="10" placeholder="输入ip:port 以逗号分隔" class="span5"></textarea>
												<textarea  rows="10" disabled="" color=red class="span5">请按照如下格式进行配置:
												 [{"port":3317,"ip":"192.168.2.44","userName":"root","password":"123456","weight":80},{"port":3317,"ip":"192.168.2.44","userName":"root","password":"123456","weight":20}]</textarea>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">数据库名</label>
											<div class="controls">
												<input type="text" name="dbName" value="${storeBean.dbName }" placeholder="输入存放数据库名" class="span5">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">表名</label>
											<div class="controls">
												<input type="text" name="tbName" value="${storeBean.tbName }" placeholder="输入存放表名" class="span5">
											</div>
										</div>
										<div class="form-actions">
											<button type="submit" class="btn btn-success">Save</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<button class="btn btn-success" onClick="javascript :history.back(-1);" >返回上一步</button>
										</div>
									</form>
								  </div>
								</div>
							</div>
							<!-- 表单内容结束 -->
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
	<script src="<%=request.getContextPath()%>/resources/js/jquery.ui.custom.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/matrix.tables.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/jquery.dataTables.min.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/matrix.form_common.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/matrix.js"></script> 
	<script src="<%=request.getContextPath()%>/resources/js/jquery.toggle.buttons.html"></script> 
	<script src="<%=request.getContextPath()%>/resources/js/jquery.peity.min.js"></script> 
	<script src="<%=request.getContextPath()%>/resources/js/select2.min.js"></script> 
	<script src="<%=request.getContextPath()%>/resources/js/masked.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/jquery.uniform.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/wysihtml5-0.3.0.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/jquery.validate.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		// Form Validation
	    $("#basic_validate").validate({
	       rules: {
	                master: {
	                    required: true,
	                },
	                slave: {
	                    required: true,
	                },
	                tbName: {
	                    required: true,
	                },
	                dbName: {
	                    required: true,
	                }
	            },
	       messages: {
	                master: {
	                    required: '请参照格式输入主数据库的配置',
	                },
	                slave: {
	                    required: '请参照格式输入从数据库的配置',
	                },
	                tbName: {
	                    required: '请输入数据库的名字',
	                },
	                dbName: {
	                    required: '请输入表的名字',
	                }
	        },
			errorClass: "help-inline",
			errorElement: "span",
			highlight:function(element, errorClass, validClass) {
				$(element).parents('.control-group').addClass('error');
			},
			unhighlight: function(element, errorClass, validClass) {
				$(element).parents('.control-group').removeClass('error');
				$(element).parents('.control-group').addClass('success');
			},
            submitHandler: function (form) {
                console.log($(form).serialize());
                form.submit();
            }
		});
   });
		
	</script>
</body>
</html>
