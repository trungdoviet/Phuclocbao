<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>

<head>
	<meta charset="utf-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title><tiles:getAsString name="title" /></title>
	<link rel="icon" type="image/x-icon" href="<c:url value="/resources/img/favicon.ico"/>" />
	<link href="<c:url value='/resources/css/bootstrap.css' />"  rel="stylesheet"></link>
	<link href="<c:url value='/resources/css/jquery-ui.css' />"  rel="stylesheet"></link>
	<link href="<c:url value='/resources/css/mainPage.css' />" rel="stylesheet"></link>
	<link href="<c:url value='/resources/css/bootstrap-table.css' />" rel="stylesheet"></link>
	<link href="<c:url value='/resources/css/styles.css'/> " rel="stylesheet"></link>
	<script src="<c:url value='/resources/js/lumino.glyphs.js' />"><!-- comment --></script>
	<script src="<c:url value="/resources/js/jquery-3.1.1.js" />"><!-- comment --></script>
	<!--[if lt IE 9]>
	<script src="<c:url value='/resources/js/html5shiv.js' />"></script>
	<script src="<c:url value='/resources/js/respond.min.js' />"></script>
	<![endif]-->
</head>
 
<body>
		<header id="header" class="noprint">
			<tiles:insertAttribute name="header"  />
		</header>
	
		<section id="sidemenu" class="noprint">
			<tiles:insertAttribute name="menu" />
		</section>
			
		<section id="site-content">
			<tiles:insertAttribute name="body" />
		</section>
		
		<footer id="footer" class="noprint">
			<tiles:insertAttribute name="footer" />
		</footer>
		
		<script src="<c:url value='/resources/js/bootstrap.min.js' />"><!-- comment --></script>
	 	<script src="<c:url value='/resources/js/inputmask/inputmask.js' />"><!-- comment --></script>
		<script src="<c:url value='/resources/js/inputmask/inputmask.numeric.extensions.js' />"><!-- comment --></script>
		<script src="<c:url value='/resources/js/inputmask/inputmask.regex.extensions.js' />"><!-- comment --></script>
		<script src="<c:url value='/resources/js/inputmask/jquery.inputmask.js' />"><!-- comment --></script>
		<script src="<c:url value='/resources/js/bootstrap-datepicker.js' />"><!-- comment --></script>
		<script src="<c:url value='/resources/js/date.js' />"><!-- comment --></script>
		<script src="<c:url value='/resources/js/autoNumeric.js' />"><!-- comment --></script>
		<script src="<c:url value='/resources/js/jquery-ui.min.js' />"><!-- comment --></script>
		<script src="<c:url value='/resources/js/bootstrap-table.js' />"><!-- comment --></script>
		<script src="<c:url value='/resources/js/phuclocbao.js' />"><!-- comment --></script>
		<script>
				!function ($) {
					$( document ).ready(function() {
						initDateLocally();
					});
				}(window.jQuery);
				
			$(window).on('resize', function () {
			  if ($(window).width() > 768) $('#sidebar-collapse').collapse('show')
			})
			$(window).on('resize', function () {
			  if ($(window).width() <= 767) $('#sidebar-collapse').collapse('hide')
			})
			$( document ).ready(function() {
				general_formatTotalFunding();
			});
			$(document).ready(function() {
				initDigitalClock();
			});
		</script>	
</body>
</html>