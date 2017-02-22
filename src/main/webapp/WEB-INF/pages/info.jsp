<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resources/css/jquery.datetimepicker.css" />
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
	<div id="user-nav" class="navbar navbar-inverse">
		<ul class="nav">
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
		          <li class="active"><a href="<%=request.getContextPath()%>/info"><i class="icon icon-flag"></i> <span>已存储信息</span></a></li>
			<c:if test="${pageContext.request.isUserInRole('ROLE_SUPER_ADMIN')}">
			      <li><a href="<%=request.getContextPath()%>/index"><i class="icon icon-home"></i> <span>已配配置信息</span></a></li>
			      <li class="submenu"><a href="<%=request.getContextPath()%>/user/user"><i class="icon icon-user"></i> <span>用户信息</span></a></li>
			      <li class="submenu"><a href="<%=request.getContextPath()%>/game/game"><i class="icon icon-tint"></i> <span>游戏管理</span></a></li>
			</c:if>	
		</ul>	
	</div>
	<!--sidebar-menu-->

	<!--main-container-part-->
	<div id="content">
		<!--breadcrumbs-->
		<div id="content-header">
			<div id="breadcrumb">
				<a href="<%=request.getContextPath()%>/info" title="Go to Home" class="tip-bottom"><i
					class="icon icon-flag"></i> Home</a>
			</div>
		</div>
		<!--End-breadcrumbs-->


		<!--Chart-box-->
		<div class="row-fluid">
			<div class="widget-box">
				<div class="widget-content">
					<div class="row-fluid">
						<div class="span12">
						<!-- -->
							<div class="widget-box">
								    <div class="widget-title"> <span class="icon"> <i class="icon-th"></i> </span>
                                        <h5>已存游戏信息</h5>
                                    </div>
                                    <div class="widget-content nopadding">
                                       <br>
										<div class="control-group ">
											<div class="controls">
											<left>
											<form action="<%=request.getContextPath()%>/info/search" method="post" class="form-horizontal" name="basic_validate" id="basic_validate">
											        &nbsp;&nbsp;&nbsp;&nbsp;游戏名:
												<select class="span2" name="searchGameName">
													<c:forEach items="${gameNameList}" var="nameList">
									                         <option <c:if test="${searchGameName == nameList }">selected="true"</c:if>value="${nameList}">${nameList}</option>
									                </c:forEach>
												</select>
												用户Id：<input type="text" name="userId" <c:if test="${not empty searchUserId }">value="${searchUserId }</c:if> "class="span2"/>
												键值：<input type="text" name="key" value="${searchKey }"class="span2"/>
												开始时间：
												<input type="text" id="datetimepicker_dark1" value="${searchBeginTime }" placeholder="点击选择日期时间" class="span2" name="beginTime" />
												结束时间：
												<input type="text" id="datetimepicker_dark2" value="${searchEndTime }"placeholder="点击选择日期时间" class="span2" name="endTime" />
												<input type="submit" class="btn btn-info"value="查询">
											</form>
											</left>
											</div>
										</div>
                                       <table class="table table-bordered table-striped">
                                            <thead>
                                            <tr>
                                              <th>游戏名</th>
                                              <th>游戏Id</th>
                                              <th>用户Id</th>
                                              <th>Key</th>
                                              <th colspan=2>Value</th>
                                              <th>创建时间</th>
                                              <th>存储过期时间</th>
                                              <th>操作</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${list}" var="list">
                                            <tr class="odd gradeX">
                                               <td>${searchGameName}</td>
                                               <td>${list.gameId }</td>
                                               <td>${list.playerId }</td>
                                               <td>${list.key }</td>
                                               <td>
                                                    <c:if test="${fn:length(list.value)>'20'}">  
									                    ${fn:substring(list.value,0,20)}...  
									                </c:if>  
									                <c:if test="${fn:length(list.value)<='20'}">  
									                    ${list.value}  
									                </c:if>
									            </td>
									            <td>
									                <a href="javascript:void(0);" id="detail" item-id="${list.value}" data-reveal-id1="myModal1" data-animation="fade" class="btn btn-info btn-mini" style="background:DarkSlateBlue"><i class="icon-globe icon-white"> 详细值</i></a>
									            </td>
                                               </td>
                                               <td>${list.createAt }</td>
                                               <td>${list.life}</td>
												<td>
													<a href="<%=request.getContextPath()%>/info/update?infoBean={id:'${list.id }',gameName:'${searchGameName}',gameId:'${list.gameId }',playerId:'${list.playerId }',
													key:'${list.key }',value:'${list.value }',createAt:'${list.createAt }',life:'${list.life }'}" class="btn btn-success btn-mini"><i class="icon-wrench icon-white">修改</i></a> 
													<a href="javascript:void(0);" id="delete" item-id="${list.id}" data-reveal-id="myModal" data-animation="fade" class="btn btn-inverse btn-mini"><i class="icon-minus icon-white"> 删除</i></a>
												</td>
                                            </tr>
                                            </c:forEach>
                                           </tbody>
                                    </table>

                                </div>
                                       <!-- 分页开始 -->
		                               <div class="pagination alternate">
                                        <center>
		              					 <ul>
          					 			<c:if test="${pages>1 && page>1 }">
				                             <li><a href="<%=request.getContextPath()%>/info/search?searchGameName=${searchGameName }<c:if test="${not empty searchUserId}">&userId=${searchUserId }</c:if><c:if test="${not empty searchKey}">&key=${searchKey }</c:if>
				                             <c:if test="${not empty searchBeginTime}">&beginTime=${searchBeginTime }</c:if><c:if test="${not empty searchEndTime}">&endTime=${searchEndTime }</c:if>&page=${page-1}&pageSize=${pageSize}">上一页</a></li>
				                   		 </c:if>
														     
									    <c:forEach  var="index" begin="1" end="${pages}" step="1">
									       <c:choose>
									           <c:when test="${((index-page< 0 ? page-index:index-page)< 4) or index == 1 }">
									               <c:choose>  
					                                  <c:when test="${index==page }">
					                                        <li class="active"><a href="<%=request.getContextPath()%>/info/search?searchGameName=${searchGameName }<c:if test="${not empty searchUserId}">&userId=${searchUserId }</c:if><c:if test="${not empty searchKey}">&key=${searchKey }</c:if>
				                             <c:if test="${not empty searchBeginTime}">&beginTime=${searchBeginTime }</c:if><c:if test="${not empty searchEndTime}">&endTime=${searchEndTime }</c:if>&page=${index}&pageSize=${pageSize}" >${index}</a></li>
					                                  </c:when>  
					                                  <c:otherwise>
					                                        <li><a href="<%=request.getContextPath()%>/info/search?searchGameName=${searchGameName }<c:if test="${not empty searchUserId}">&userId=${searchUserId }</c:if><c:if test="${not empty searchKey}">&key=${searchKey }</c:if>
				                             <c:if test="${not empty searchBeginTime}">&beginTime=${searchBeginTime }</c:if><c:if test="${not empty searchEndTime}">&endTime=${searchEndTime }</c:if>&page=${index}&pageSize=${pageSize}" >${index}</a></li>
					                                  </c:otherwise>  
					                               </c:choose>
									           </c:when>
									           <c:otherwise>
									                <c:choose>
									                <c:when test="${(index-page< 0 ? page-index:index-page)== 4}">
									                   <li class="disabled"><a href="#">...</a></li> 
									                </c:when>
									                <c:otherwise>
									                    <c:if test="${index==pages }"> 
									                     <li><a href="<%=request.getContextPath()%>/info/search?searchGameName=${searchGameName }<c:if test="${not empty searchUserId}">&userId=${searchUserId }</c:if><c:if test="${not empty searchKey}">&key=${searchKey }</c:if>
				                             <c:if test="${not empty searchBeginTime}">&beginTime=${searchBeginTime }</c:if><c:if test="${not empty searchEndTime}">&endTime=${searchEndTime }</c:if>&page=${index}&pageSize=${pageSize}"  <c:if test="${page ==pages  }" >class="cur"</c:if>>${pages}</a></li>
									                    </c:if>   
									                </c:otherwise>
									                </c:choose>
									           </c:otherwise>
									       </c:choose>
					                    </c:forEach>
					                    <c:if test="${pages>1 && page < pages }">
					                             <li><a href="<%=request.getContextPath()%>/info/search?searchGameName=${searchGameName }<c:if test="${not empty searchUserId}">&userId=${searchUserId }</c:if><c:if test="${not empty searchKey}">&key=${searchKey }</c:if>
				                             <c:if test="${not empty searchBeginTime}">&beginTime=${searchBeginTime }</c:if><c:if test="${not empty searchEndTime}">&endTime=${searchEndTime }</c:if>&page=${page+1}&pageSize=${pageSize}">下一页</a></li>
					                    </c:if>
										 </ul>
										</center>
		                 			  </div>
		                 			  <!-- 分页结束 -->
								<!-- 用于显示其它信息 -->
								<c:if test="${not empty msg }">
								<div class="alert alert-info alert-block">
									<a class="close" data-dismiss="alert" href="#">×</a>
									<h4 class="alert-heading">Congratulations!</h4>
									<p>
										<center>${msg }</center>
									</p>
								</div>
								</c:if>
								<!-- 其它提示结束 -->
								<!-- 用于显示其它信息 -->
								<c:if test="${empty list }">
								<div class="alert alert-info alert-block">
									<a class="close" data-dismiss="alert" href="#">×</a>
									<h4 class="alert-heading">Oops!</h4>
									<p>
										<center>当前游戏还没有存储信息(⊙o⊙)哦，换个游戏查询一下吧！</center>
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
		<!-- 删除按钮弹出框 -->
	<center><div id="myModal" class="reveal-modal">
	        <h1><font color="red"><i class="icon-umbrella"></i></font></h1>
			<h3 >您确定要删除这条存储信息吗？</h3><br>
			<a class="close-reveal-modal">&#215;</a>
			<form action="<%=request.getContextPath()%>/info/delete" method="post">
				<input type="hidden" id="delid" name="id" value="1" />
				<input type="hidden" name="searchGameName" value="${searchGameName}" />
				<input type="submit" class="btn btn-inverse" value="确定"/>&nbsp;&nbsp;&nbsp;&nbsp;
				<a class="btn btn-inverse" close-reveal-id="myModal" >取消</a>
			</form>
			</div>
	</center>
	<!-- 删除弹出框结束 -->
	<!-- 值详情弹出框 -->
	<center><div id="myModal1" class="reveal-modal">
	        <h1><font color="OrangeRed"><i class="icon-qrcode"></i></font></h1>
			<h4 >详细存储值如下</h4>
			<a class="close-reveal-modal">&#215;</a>
			<form action="#" method="post">
				<textarea  id="detailinfo" rows="10" class="span5" disabled></textarea><br>
				<a class="btn btn-inverse" close-reveal-id1="myModal1" >关闭</a>
			</form>
			</div>
	</center>	
	<!-- 值详情弹出框结束   -->
	<div class="row-fluid">
		<div id="footer" class="span12">
			2016 &copy; Idreamsky.<a href="http://www.idreamsky.com/">创梦天地</a>
		</div>
	</div>

	<!--end-Footer-part-->

	<script src="<%=request.getContextPath()%>/resources/js/jquery.min.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/jquery.ui.custom.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/jquery.datetimepicker.full.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/resources/js/matrix.tables.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/jquery.dataTables.min.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/jquery.reveal.js"></script>
	<script type="text/javascript">
	$('#datetimepicker_dark1').datetimepicker({theme:'dark'})
	$('#datetimepicker_dark2').datetimepicker({theme:'dark'})
	    //处理删除弹出框
		$('a[data-reveal-id]').live('click', function () {
			var id = $(this).attr("item-id");
	        //e.preventDefault();
	        $("#delid").val(id);
	        var modalLocation = $(this).attr('data-reveal-id');
	        $('#' + modalLocation).reveal($(this).data());
	    });
	   
	   	$('a[close-reveal-id]').live('click', function () {
         jQuery("#myModal").trigger('reveal:close');
	        var modalLocation = $(this).attr('close-reveal-id').trigger('reveal:close');
	        $('#' + modalLocation).reveal($(this).data());
	    });
	   	//处理详情弹出框
		$('a[data-reveal-id1]').live('click', function () {
			var info = $(this).attr("item-id");
	        //e.preventDefault();
	        $("#detailinfo").val(info);
	        var modalLocation = $(this).attr('data-reveal-id1');
	        $('#' + modalLocation).reveal($(this).data());
	    });
	   
	   	$('a[close-reveal-id1]').live('click', function () {
         jQuery("#myModal1").trigger('reveal:close');
	        var modalLocation = $(this).attr('close-reveal-id1').trigger('reveal:close');
	        $('#' + modalLocation).reveal($(this).data());
	    });
	
	</script>
</body>
</html>