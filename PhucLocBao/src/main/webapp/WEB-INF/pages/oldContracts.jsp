<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="includes/jstl.jsp"%>
<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main phuclocbao">			
	<div class="row">
		<ol class="breadcrumb">
			<li><a href="#"><svg class="glyph stroked home"><use xlink:href="#stroked-home"></use></svg></a></li>
			<li class="active">Quản lý hợp đồng cũ</li>
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
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading text-center"><span class="glyphicon glyphicon-briefcase" aria-hidden="true"></span>Quản lý thông tin hợp đồng cũ</div>
				<div class="panel-body">
					<div class="col-md-6">
						<form:form role="form" id="finishContractForm" method="post" action="filterFinishContract" modelAttribute="csBean">
								<spring:bind path="name">
									<div class="form-group ${status.error ? 'has-error' : ''} pull-left" >
										<form:input id="customerName" class="form-control"
											placeholder="Họ tên khách hàng" name="customerName"
											path="name" />
										<form:errors path="name" cssClass="error" />
									</div>
								</spring:bind>
								<button id="btnSearch" type="submit" class="btn btn-primary pull-left stay-left" name="search">
									<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
									Tìm
								</button>
						</form:form>
					</div>
					<div class="col-md-6">
						<div style="height:50px"></div>
					</div>
					<div class="col-md-6">
						<div class="row">
							<div class="col-md-8 text-right"><strong class="bottom-line">Tổng số hợp đồng đã thanh lý:</strong></div>
							<div id="ocFinishContract" class="col-md-4 text-left">${oldContract.finishContract}</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="row">
							<div class="col-md-8 text-right"><strong class="bottom-line">Tổng số tiền hợp đồng đã thanh lý:</strong></div>
							<div id="ocTotalUnpaidCost" class="col-md-4 text-right"><strong>
								<span id="mcTotalUnpaidCostSpan">
									<fmt:formatNumber  currencySymbol=" "  value="${oldContract.totalAlreadyPayoffAmmount}" type="currency" maxFractionDigits="0" var="ocTotalPayOffCost"/>
										${fn:replace(ocTotalPayOffCost, ",", ".")}
										VNĐ
								</span></strong>
							</div>
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
					        <th data-field="expireDate" data-sortable="true">Ngày thanh lý</th>
					        <th data-field="totalContractDays" data-sortable="true">Số ngày</th>		
					        <th data-field="action" >Thao tác</th>	 		
					    </tr>
					    </thead>
					    <tbody>
					    	<c:forEach var="contract" items="${oldContract.contracts}">
						    	<tr>
						    		<td>
						    			<a href="#" onclick="openContractDetail(${contract.id})">${contract.customer.name}&nbsp;${contract.customer.birthYear}</a></td>
									<td>Cho thuê xe</td>
									<td class="text-right">
										<fmt:formatNumber  currencySymbol=" "  value="${contract.totalAmount}" type="currency" maxFractionDigits="0" var="totalAmount"/>
										${fn:replace(totalAmount, ",", ".")}
										VNĐ
									</td>
									
									<td class="text-right">
										<fmt:formatNumber currencySymbol=" "  value="${contract.feeADay}" type="currency" maxFractionDigits="0" var="feeADay"/>
										${fn:replace(feeADay, ",", ".")}
										VNĐ
									</td>
									<td class="text-center"><fmt:formatDate pattern="dd/MM/yyyy" value="${contract.startDate}" /></td>
									<td class="text-center"><fmt:formatDate pattern="dd/MM/yyyy" value="${contract.payoffDate}" /></td>
									<td class="text-center">${contract.totalContractDays}</td>
									<td>
										<spring:url value="/contract/${contract.id}/update" var="updateUrl" /> 
										<button class="btn btn-primary btn-xs" onclick="location.href='${updateUrl}'">Cập nhật</button>
									</td>
						    	</tr>
					    	</c:forEach>
					    </tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="includes/contractDetailDialog.jsp"></jsp:include>
<script type="text/javascript">
$( document ).ready(function() {
	hideAlert("mngAlert");
});
</script>
</div>