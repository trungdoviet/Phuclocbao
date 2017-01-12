<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="includes/jstl.jsp"%>
<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">			
	<div class="row">
		<ol class="breadcrumb">
			<li><a href="#"><svg class="glyph stroked home"><use xlink:href="#stroked-home"></use></svg></a></li>
			<li class="active">Quản lý hợp đồng</li>
		</ol>
	</div><!--/.row-->
	<c:if test="${not empty msg}">
		<div id="mngAlert" class="alert alert-${css} alert-dismissible fade in" role="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong>${msg}</strong>
		</div>
	</c:if>
	<form:form role="form" id="mngContractForm" method="post" action="availableContracts" modelAttribute="contractBean">
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading text-center"><span class="glyphicon glyphicon-briefcase" aria-hidden="true"></span>Quản lý thông tin hợp đồng</div>
					<div class="panel-body">
						<div class="col-md-6">
							<div class="row">
								<div class="col-md-8 text-right"><strong class="bottom-line">Hợp đồng cho thuê xe:</strong></div>
								<div id="mcTotalContract" class="col-md-4 text-left">${mngContract.totalContract}</div>
							</div>
							<div class="row">
								<div class="col-md-8 text-right"><strong class="bottom-line">Tổng số hợp đồng chưa thanh lý:</strong></div>
								<div id="mcInProgressContract" class="col-md-4 text-left">${mngContract.inProgressContract}</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="row">
								<div class="col-md-8 text-right"><strong class="bottom-line">Tổng phí hợp đồng/ngày:</strong></div>
								<div id="mcTotalFeeADay" class="col-md-4 text-right"><strong><span id="mcTotalFeeADaySpan">${mngContract.totalFeeADay}</span></strong></div>
							</div>
							<div class="row">
								<div class="col-md-8 text-right"><strong class="bottom-line">Tổng số tiền hợp đồng chưa thanh lý:</strong></div>
								<div id="mcTotalUnpaidCost" class="col-md-4 text-right"><strong><span id="mcTotalUnpaidCostSpan">${mngContract.totalPayoffAmmount}</span></strong></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<!-- Table -->
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-body">
						<table data-toggle="table" data-pagination="true" >
						    <thead>
						    <tr>
						        <th data-field="customerName" data-sortable="true">Tên khách hàng</th>
						        <th data-field="contractType" data-sortable="true">Loại hình thuê</th>
						        <th data-field="totalAmount" data-sortable="true">Số tiền</th>
						        <th data-field="feeAday" data-sortable="true">Phí/ngày</th>
						        <th data-field="startDate" data-sortable="true">Ngày thuê</th>
						        <th data-field="expireDate" data-sortable="true">Ngày hết hạn</th>	
						        <th data-field="action" >Thao tác</th>	 		
						    </tr>
						    </thead>
						    <tbody>
						    	<c:forEach var="contract" items="${mngContract.contracts}">
							    	<tr>
							    		<td><a href="#">${contract.customer.name}&nbsp;${contract.customer.birthYear}</a></td>
										<td>Cho thuê xe</td>
										<td class="text-right"><fmt:formatNumber currencySymbol=" "  value="${contract.totalAmount}" type="currency" maxFractionDigits="0"/>&nbsp;VNĐ</td>
										<td class="text-right"><fmt:formatNumber currencySymbol=" "  value="${contract.feeADay}" type="currency" maxFractionDigits="0"/>&nbsp;VNĐ</td>
										<td class="text-center"><fmt:formatDate pattern="dd/MM/yyyy" value="${contract.startDate}" /></td>
										<td class="text-center"><fmt:formatDate pattern="dd/MM/yyyy" value="${contract.expireDate}" /></td>
										<td>Col 7</td>
							    	</tr>
						    	</c:forEach>
						    </tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</form:form>
<script type="text/javascript">
$( document ).ready(function() {
	hideAlert("mngAlert");
	mc_formatNumber();
});
</script>
</div>