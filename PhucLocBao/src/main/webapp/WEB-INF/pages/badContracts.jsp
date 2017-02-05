<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="includes/jstl.jsp"%>
<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main phuclocbao">			
	<div class="row">
		<ol class="breadcrumb">
			<li><a href="#"><svg class="glyph stroked home"><use xlink:href="#stroked-home"></use></svg></a></li>
			<li class="active">Hợp đồng xấu</li>
		</ol>
	</div><!--/.row-->
	<c:if test="${not empty msg}">
		<div id="bcAlert" class="alert alert-${css} alert-dismissible fade in" role="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong>${msg}</strong>
		</div>
	</c:if>
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading text-center"><span class="glyphicon glyphicon-briefcase" aria-hidden="true"></span>Quản lý hợp đồng xấu</div>
				<div class="panel-body">
					<div class="col-md-6">
						<div class="row">
							<div class="col-md-8 text-right"><strong class="bottom-line">Tổng HĐ khách hàng xấu:</strong></div>
							<div id="ocFinishContract" class="col-md-4 text-left">${badContract.badContract}</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="row">
							<div class="col-md-8 text-right"><strong class="bottom-line">Tổng giá trị HĐ xấu:</strong></div>
							<div id="ocTotalUnpaidCost" class="col-md-4 text-right"><strong>
								<span id="mcTotalUnpaidCostSpan">
									<fmt:formatNumber  currencySymbol=" "  value="${badContract.totalAmountBadContract}" type="currency" maxFractionDigits="0" var="bcTotalAmmount"/>
										${fn:replace(bcTotalAmmount, ",", ".")}
										VNĐ
								</span></strong>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="row">
							<div></div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="row">
							<div class="col-md-8 text-right"><strong class="bottom-line">Tổng nợ phí:</strong></div>
							<div id="ocTotalUnpaidCost" class="col-md-4 text-right"><strong>
								<span id="mcTotalUnpaidCostSpan">
									<fmt:formatNumber  currencySymbol=" "  value="${badContract.totalAmountFeeOfBadContract}" type="currency" maxFractionDigits="0" var="bcTotalUnpaid"/>
										${fn:replace(bcTotalUnpaid, ",", ".")}
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
					        <th data-field="totalAmount" data-sortable="true">Giá trị HĐ</th>
					        <th data-field="feeAday" data-sortable="true">Phí/ngày</th>
					        <th data-field="lastPaidDate" data-sortable="true">Đã trả phí hết ngày</th>
					        <th data-field="totalLateDays" data-sortable="true">Số ngày chậm</th>
					        <th data-field="totalUnpaidFee" data-sortable="true">Tổng tiền phí</th>		
					         <th>Thao tác</th>
					    </tr>
					    </thead>
					    <tbody>
					    	<c:forEach var="contract" items="${badContract.contracts}">
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
									<td class="text-center">
										<c:if test="${contract.lastPaidDate != null}">
											<fmt:formatDate pattern="dd/MM/yyyy" value="${contract.lastPaidDate}" />
										</c:if>
										<c:if test="${contract.lastPaidDate == null}">
											Không có
									</c:if>
									</td>
									<td class="text-center" ><span class="label-alert"> ${contract.totalLateDays} Ngày</span></td>
									<td class="text-center">
										<fmt:formatNumber currencySymbol=" "  value="${contract.totalUnpaidFee}" type="currency" maxFractionDigits="0" var="totalUnpaidFee"/>
										${fn:replace(totalUnpaidFee, ",", ".")}
										VNĐ
									</td>
									<td>
											<spring:url value="/contract/${contract.id}/payoff" var="payOffUrl" />
											<button class="btn btn-danger btn-xs" onclick="location.href='${payOffUrl}'">Thanh lý</button>
										
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
	hideAlert("bcAlert");
});
</script>
</div>