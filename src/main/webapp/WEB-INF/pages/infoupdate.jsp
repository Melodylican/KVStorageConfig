<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/bootstrap-wysihtml5.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery.datetimepicker.css" />

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
			<li class="active"><a href="<%=request.getContextPath()%>/info"><i class="icon icon-flag"></i> <span>已存储信息</span></a></li>
		<c:if test="${pageContext.request.isUserInRole('ROLE_SUPER_ADMIN')}">
			<li><a href="<%=request.getContextPath()%>/index"> <i class="icon icon-home"></i> <span>已配配置信息</span></a></li>
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
							<!-- 用户表单信息 -->
							<div class="widget-box">
								<div class="widget-title">
									<span class="icon"> <i class="icon-align-justify"></i>
									</span>
									<h5>修改 - 已存储信息
									     <c:if test="${not empty updateMsg}"><font color="red ">${updateMsg}</font></c:if>
									</h5>
								</div>
								<div class="widget-content nopadding">
									<form action="<%=request.getContextPath()%>/info/saveupdate" method="post" class="form-horizontal" name="basic_validate" id="basic_validate">
									   <div class="control-group">
											<label class="control-label">游戏名</label>
											<div class="controls">
												<input type="text" disabled="" value="${infoBean.gameName}" class="span5">
												<input type="hidden" name="gameName"value="${infoBean.gameName}">
												<input type="hidden" name="id" value="${infoBean.id }">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">游戏id</label>
											<div class="controls">
												<input type="text" disabled="" value="${infoBean.gameId }"  class="span5">
												<input type="hidden" name="gameId" value="${infoBean.gameId }">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">用户Id</label>
											<div class="controls">
												<input type="text" name="playerId" value="${infoBean.playerId }" placeholder="输入用户Id" class="span5">
											</div>
										</div>										
										<div class="control-group">
											<label class="control-label">存储键</label>
											<div class="controls">
												<input type="text" name="key" value="${infoBean.key }" placeholder="输入键值" class="span5">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">存储值</label>
											<div class="controls">
												<c:choose>
													<c:when test="${not empty infoBean.value}">
														<textarea name="value" rows="10" class="span5" >${infoBean.value }</textarea>
													</c:when>
													<c:otherwise>
														<textarea name="value" rows="10" class="span5" placeholder="请输入存储值..."></textarea>
													</c:otherwise>
												</c:choose>	
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">创建时间</label>
											<div class="controls">
												<input type="text" id="datetimepicker_dark1" value="${infoBean.createAt }" name="createAt" placeholder="点击选择日期时间" class="span5" />
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">存储过期时间</label>
											<div class="controls">
												<input type="text" id="datetimepicker_dark2"  name="life" value="${infoBean.life }" name="createAt" placeholder="点击选择日期时间" class="span5" />
											</div>
										</div>
										<div class="form-actions">
											<button type="submit" class="btn btn-success">Save</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<button class="btn btn-success"  onClick="javascript :history.back(-1);" >返回上一步</button>
										</div>
									</form>
								</div>
							</div>
							<!-- 表格内容结束 -->
							<!-- 用于显示其它信息 -->
							<c:if test="${not empty msg }">
							<div class="alert alert-info alert-block">
								<a class="close" data-dismiss="alert" href="#">×</a>
								<h4 class="alert-heading">Congratulations!</h4>
								<p>
									<center>${msg}</center>
								</p>
							</div>
							</c:if>
							<!-- 其它提示结束 -->							
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
	<script src="<%=request.getContextPath()%>/resources/js/jquery.uniform.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/select2.min.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/jquery.validate.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/matrix.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/masked.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/matrix.form_common.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/jquery.peity.min.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/jquery.datetimepicker.full.js" type="text/javascript"></script>
	
	<script type="text/javascript">
	$('#datetimepicker_dark1').datetimepicker({theme:'dark'})
	$('#datetimepicker_dark2').datetimepicker({theme:'dark'})
	$(document).ready(function(){
		// Form Validation
	    $("#basic_validate").validate({
	       rules: {
	                key: {
	                    required: true,
	                    minlength: 1,
	                    maxlength: 150
	                },
	                value: {
	                    required: true,
	                    minlength: 1,
	                    maxlength: 5000
	                }
	            },
	       messages: {
	                key: {
	                    required: '请输入key',
	                    minlength: '用户名不能小于1个字符',
	                    maxlength: '用户名不能超过150个字符'
	                },
	                value: {
	                    required: '请输入value',
	                    minlength: '用户不能小于1个字符',
	                    maxlength: '用户不能超过5000个字符'
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
