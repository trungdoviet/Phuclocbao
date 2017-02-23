<%@include file="../includes/springTag.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><tiles:getAsString name="title" /></title>
		<link rel="icon" type="image/x-icon" href="<c:url value="/resources/img/favicon.ico"/>" />
		 <script src="<c:url value="/resources/js/jquery-3.1.1.js" />"><!-- comment --></script>
   		 <script src="<c:url value="/resources/js/prefixfree.min.js" />"><!-- comment --></script>
   		 <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/loginTheme.css"/>">
   		 
	</head>
	<body>
		<div id="site-content">
			<tiles:insertAttribute name="body" />
		</div>
	</body>
</html>