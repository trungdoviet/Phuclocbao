<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="includes/jstl.jsp"%>
<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">			
	<div class="row">
		<ol class="breadcrumb">
			<li><a href="#"><svg class="glyph stroked home"><use xlink:href="#stroked-home"></use></svg></a></li>
			<li class="active">Tạo hợp đồng mới</li>
		</ol>
	</div><!--/.row-->
	
<form:form role="form" id="newContractForm" method="post" action="newContractAction" modelAttribute="contractBean">
	<jsp:include page="includes/contractContentPage.jsp"></jsp:include>
	<div class="row">
		<div class="col-lg-12">
			<div class="pull-right">
				<spring:url value="/newContractAction/cancel" var="cancelUrl" />
				<button id="btnNewContract" type="submit" class="btn btn-primary" name="save">
					<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
					Tạo hợp đồng
				</button>
				<button type="button" class="btn btn-slave" name="cancel" onclick="location.href='${cancelUrl}'">
					<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					Thoát
				</button>
			</div>
		</div>
	</div>
</form:form>

<jsp:include page="includes/paymentDialog.jsp"></jsp:include>
<jsp:include page="includes/contractDetailDialog.jsp"></jsp:include>

<script type="text/javascript">
$( document ).ready(function() {
	initNewContractPage();
	showhideAvailableContractPanel();
});
</script>
</div>
