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
	<script src="<c:url value="/resources/js/jquery-3.1.1.js" />"><!-- comment --></script>
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
		<%-- <script src="<c:url value='/resources/js/jquery-1.11.1.min.js' />"><!-- comment --></script> --%>
		
		<script src="<c:url value='/resources/js/bootstrap.min.js' />"><!-- comment --></script>
	 	<script src="<c:url value='/resources/js/inputmask/inputmask.js' />"><!-- comment --></script>
		<script src="<c:url value='/resources/js/inputmask/inputmask.numeric.extensions.js' />"><!-- comment --></script>
		<script src="<c:url value='/resources/js/inputmask/inputmask.regex.extensions.js' />"><!-- comment --></script> --%>
		<script src="<c:url value='/resources/js/inputmask/jquery.inputmask.js' />"><!-- comment --></script>
		<script src="<c:url value='/resources/js/bootstrap-datepicker.js' />"><!-- comment --></script>
		<script src="<c:url value='/resources/js/autoNumeric.js' />"><!-- comment --></script>
		<script>
			/*$('#calendar').datepicker({
				}); */
				!function ($) {
					$( document ).ready(function() {
						//$("#totalAmount").inputmask('999999999999', {alias:"decimal", numericInput: true, rightAlign: true, placeholder:" " ,groupSeparator :"."});
						//$("#feeADay").inputmask('999999999', { numericInput: true, rightAlign: true, placeholder:" ",groupSeparator :"." });
						 $("#totalAmount").autoNumeric("init", {
					        aSep: '.',
					        aDec: ',', 
					        pSign: 's',
					        aSign: ' VNĐ',
					        vMin: 0, 
					        vMax: 9999999999
					    });
						 $("#feeADay").autoNumeric("init", {
						        aSep: '.',
						        aDec: ',', 
						        pSign: 's',
						        aSign: ' VNĐ',
						        vMin: 0, 
						        vMax: 999999999
						    });
						$("#periodOfPayment").inputmask('Regex', { regex: "^[1-2][0-9]?$|^30$", rightAlign: true, "oncomplete": function(){ console.log('inputmask complete'); }})
						$( "#startDate" ).datepicker({
						    format: 'dd/mm/yyyy',
						    todayHighlight: true,
						    autoclose:true
						});
						$( "#expireDate" ).datepicker({
							format: 'dd/mm/yyyy',
						    autoclose:true
						});
						$("#startDate").inputmask("99/99/9999",{ "oncomplete": function(){ console.log('inputmask complete'); } });
						
					});
				}(window.jQuery);
				
			$(window).on('resize', function () {
			  if ($(window).width() > 768) $('#sidebar-collapse').collapse('show')
			})
			$(window).on('resize', function () {
			  if ($(window).width() <= 767) $('#sidebar-collapse').collapse('hide')
			})
		</script>	
</body>
</html>