<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="includes/jstl.jsp"%>
<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">			
	<div class="row">
		<ol class="breadcrumb">
			<li><a href="#"><svg class="glyph stroked home"><use xlink:href="#stroked-home"></use></svg></a></li>
			<li class="active">Thông tin hợp đồng</li>
		</ol>
	</div><!--/.row-->
<c:if test="${not empty msg}">
	<div id="contractAlert" class="alert alert-${css} alert-dismissible fade in" role="alert">
		<button type="button" class="close" data-dismiss="alert" aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<strong>${msg}</strong>
	</div>
</c:if>
<form:form role="form" id="contractForm" method="post" action="saveContract" modelAttribute="contractBean">
	<jsp:include page="includes/contractContentPage.jsp"></jsp:include>
	<div class="row">
		<div class="col-lg-12">
			<div class="pull-right">
				<spring:url value="/mngContract/cancel" var="cancelUrl" />
				<c:if test="${contractBean.processStaging == 'paid' }">
					<button  id="btnSaveContract" type="submit" class="btn btn-primary" name="save">
						<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
						Lưu hợp đồng
					</button>
				</c:if>
				
				<c:if test="${contractBean.processStaging == 'payoff' }">
					<button  id="btnPayoffContractOk" type="button" class="btn btn-danger" name="payoff">
						<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
						Thanh lý hợp đồng
					</button>
					<button  id="btnPayoffContract" type="submit" class="btn btn-danger" name="payoff" style="display:none">
						<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
						Thanh lý hợp đồng
					</button>
					<jsp:include page="includes/payoffPopup.jsp"></jsp:include>
					<button  id="btnSaveAsDraftContract" type="submit" class="btn btn-primary" name="savedraft">
						<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
						Lưu hợp đồng
					</button>
					
				</c:if>
				<c:if test="${contractBean.processStaging == 'update' }">
				<spring:url value="/oldContract/cancel" var="cancelUrl" />
					<button  id="btnSaveOldContract" type="submit" class="btn btn-primary" name="update">
						<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
						Lưu hợp đồng
					</button>
				</c:if>
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
	hideAlert("contractAlert");
	initContractPageButtons();
	ctr_calTotalRefunding();
});
</script>
</div>
