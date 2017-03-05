<%@include file="includes/springTag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false);
function hideURLbar(){ window.scrollTo(0,1); } </script>

<!--header-->
<div class="agileheader">
	<h1>Phúc Lộc Bảo</h1>
</div>
<!--//header-->

<!--main-->
<div class="main-w3l">
<div class="w3layouts-main">
	<h2>Xin chào !</h2>
		<form:form id="loginForm" method="post" action="login" modelAttribute="loginBean" autocomplete="off">
			<form:input type="text" placeholder="Tài khoản" name="username" path="username"/><br>
			<form:errors path="username" cssClass="error" />
			<form:input type="password" placeholder="Mật khẩu" name="password" path="password" autocomplete="new-password"/><br>
			<form:errors path="password" cssClass="error" />
			<span class="error">${message}</span>
			<h6><a href="#">Quên mật khẩu?</a></h6>
			<div class="clear"></div>
			<input type="submit" value="Đăng nhập">
		</form:form>
		
</div>
</div>
<!--//main-->

<%-- <div class="body"></div>
<div class="grad"></div>
<div class="wrapper">
	<div class="header">
		<div>Cty&nbsp;<span class="entityName">Phúc Lộc Bảo</span></div>
	</div>
	<br>
	<div class="login">
			<form:form id="loginForm" method="post" action="login" modelAttribute="loginBean" autocomplete="off">
				<form:input type="text" placeholder="username" name="username" path="username"/><br>
				<form:errors path="username" cssClass="error" />
				<form:input type="password" placeholder="password" name="password" path="password" autocomplete="new-password"/><br>
				<form:errors path="password" cssClass="error" />
				<font class="error">${message}</font>
				<input type="submit" value="Login">
			</form:form>
	</div>
</div> --%>
