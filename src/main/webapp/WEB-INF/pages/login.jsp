<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
        <title>kv存储系统-配置管理系统</title><meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/bootstrap-responsive.min.css" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/matrix-login.css" />
        <link href="<%=request.getContextPath()%>/resources/font-awesome/css/font-awesome.css" rel="stylesheet" />
    </head>
    <body>
        <div id="loginbox">            
            <form id="loginform" class="form-vertical" method="POST" action="${pageContext.request.contextPath}/j_spring_security_check">
				 <div class="control-group normal_text"> <h3><img src="<%=request.getContextPath()%>/resources/img/logo.png" alt="Logo" />  <font color="red">KV</font>-存储系统配置管理系统</h3> </div>
                <div class="control-group">
                    <div class="controls">
                        <div class="main_input_box">
                            <c:if test="${not empty param.error}">
           						<center> &nbsp;&nbsp;&nbsp;&nbsp;<font color="red">Oops</font>:用户名或密码错误！<!--  ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}  --></center>
        					</c:if>
                            <span class="add-on bg_lg"><i class="icon-user"></i></span><input type="text" placeholder="Username" name="username"/>
                        </div>
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <div class="main_input_box">
                            <span class="add-on bg_ly"><i class="icon-lock"></i></span><input type="password" name="password" placeholder="Password" />
                        </div>
                    </div>
                </div>
                <div class="form-actions">
                    <span class="pull-left"><a href="#" class="flip-link btn btn-info" id="to-recover">Lost password?</a></span>
                    <span class="pull-right"><input type="submit" class="btn btn-success" value="login"/></span>
                </div>
            </form>
            <form id="recoverform" action="#" class="form-vertical">
				<p class="normal_text">请邮件联系chris.li@idreamsky.com或者RTX联系 chris.li找回密码吧</p>
                    <div class="controls">
                        <div class="main_input_box">
                            <span class="add-on bg_lo"><i class="icon-envelope"></i></span><input type="text" disabled="" placeholder="E-mail address send to chris.li@idreamsky.com" />
                        </div>
                    </div>
               
                <div class="form-actions">
                    <span class="pull-left"><a href="#" class="flip-link btn btn-success" id="to-login">&laquo; Back to login</a></span>
                    <!-- <span class="pull-right"><a class="btn btn-info"/>Reecover</a></span> -->
                </div>
            </form>
        </div>
        
        <script src="<%=request.getContextPath()%>/resources/js/jquery.min.js"></script>  
        <script src="<%=request.getContextPath()%>/resources/js/matrix.login.js"></script> 
    </body>

</html>
