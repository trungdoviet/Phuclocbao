<%@include file="includes/springTag.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Phuc Loc Bao - login</title>
		 <script src="<c:url value="/resources/js/jquery-3.1.1.js" />"><!-- comment --></script>
   		 <script src="<c:url value="/resources/js/prefixfree.min.js" />"><!-- comment --></script>
   		 <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/loginTheme.css"/>">
   		 
	</head>
	<body>
		<%-- <font color="red">${message}</font>
		<form:form id="loginForm" method="post" action="login" modelAttribute="loginBean">

			<form:label path="username">Enter your user-name</form:label>
			<form:input id="username" name="username" path="" /><br>
			<form:label path="username">Please enter your password</form:label>
			<form:password id="password" name="password" path="" /><br>
			<input type="submit" value="Submit" /> --%>
			
		<div class="body"></div>
		<div class="grad"></div>
		<div class="wrapper">
			<div class="header">
				<div>Cty&nbsp;<span>Phuc Loc Bao</span></div>
			</div>
			<br>
			<div class="login">
					<font color="red">${message}</font>
					<form:form id="loginForm" method="post" action="login" modelAttribute="loginBean" autocomplete="off">
						<form:input type="text" placeholder="username" name="username" path=""/><br>
						<form:input type="password" placeholder="password" name="password" path="" autocomplete="new-password"/><br>
						<input type="submit" value="Login">
					</form:form>
			</div>
		</div>
		
		<%-- </form:form> --%>
	</body>
</html>