<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title><tiles:getAsString name="title" /></title>
	<link href="<c:url value='/resources/css/bootstrap.css' />"  rel="stylesheet"></link>
	<link href="<c:url value='/resources/css/mainPage.css' />" rel="stylesheet"></link>
	<link href="<c:url value='/resources/css/styles.css'/> " rel="stylesheet"></link>
	<script src="<c:url value='/resources/js/lumino.glyphs.js' />"><!-- comment --></script>
	<!--[if lt IE 9]>
	<script src="<c:url value='/resources/js/html5shiv.js' />"></script>
	<script src="<c:url value='/resources/js/respond.min.js' />"></script>
	<![endif]-->
</head>
 
<body>
		<header id="header">
			<tiles:insertAttribute name="header" />
		</header>
	
		<section id="sidemenu">
			<tiles:insertAttribute name="menu" />
		</section>
			
		<section id="site-content">
			<tiles:insertAttribute name="body" />
		</section>
		
		<footer id="footer">
			<tiles:insertAttribute name="footer" />
		</footer>
		<script src="<c:url value='/resources/js/jquery-1.11.1.min.js' />"><!-- comment --></script>
		<script src="<c:url value='/resources/js/bootstrap.min.js' />"><!-- comment --></script>
		<%-- <script src="<c:url value='/resources/js/chart.min.js' />"><!-- comment --></script>
		<script src="<c:url value='/resources/js/chart-data.js' />"><!-- comment --></script>
		<script src="<c:url value='/resources/js/easypiechart.js' />"><!-- comment --></script>
		<script src="<c:url value='/resources/js/easypiechart-data.js' />"><!-- comment --></script> --%>
		<script src="<c:url value='/resources/js/bootstrap-datepicker.js' />"><!-- comment --></script>
</body>
</html>