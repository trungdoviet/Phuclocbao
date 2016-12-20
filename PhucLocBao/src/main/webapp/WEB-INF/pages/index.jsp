<%@include file="includes/springTag.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div class="body"></div>
<div class="grad"></div>
<div class="wrapper">
	<div class="header">
		<div>Cty&nbsp;<span>Phuc Loc Bao</span></div>
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
</div>
