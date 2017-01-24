<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="includes/jstl.jsp"%>
<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main phuclocbao">			
	<div class="row">
		<ol class="breadcrumb">
			<li><a href="#"><svg class="glyph stroked home"><use xlink:href="#stroked-home"></use></svg></a></li>
			<li class="active">Thông báo</li>
		</ol>
	</div><!--/.row-->
	<c:if test="${not empty msg}">
		<div id="ncAlert" class="alert alert-${css} alert-dismissible fade in" role="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong>${msg}</strong>
		</div>
	</c:if>
	<form:form role="form" id="notificationForm" method="post" action="notification" modelAttribute="notificationPage">
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading text-center"><span class="glyphicon glyphicon-briefcase" aria-hidden="true"></span>Thông báo</div>
					<div class="panel-body">
						<div class="col-md-6">
							<div class="row">
								<div class="col-md-12 text-left">
									<div class="pull-left">
									<strong style="line-height: 32px;">Đến ngày:</strong>
									</div>
										<spring:bind path="selectedDate">
											<div class="form-group ${status.error ? 'has-error' : ''} pull-left stay-left" >
												<form:input id="selectedDate" class="form-control readonlyDate"
													readonly="true" placeholder="Ngày/Tháng/Năm" name="selectedDate"
													path="selectedDate" />
												<form:errors path="selectedDate" cssClass="error" />
											</div>
										</spring:bind>
									<button id="btnSearch" type="submit" class="btn btn-primary pull-left stay-left" name="search">
										<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
										Tìm
									</button>
								</div>
								<!-- <div class="col-md-4 text-left">
									
								</div> -->
							</div>
						</div>
						<%-- <div class="col-md-6">
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
						</div> --%>
					</div>
				</div>
			</div>
		</div>
		</form:form>
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
						        <th data-field="customerPhone" data-sortable="true">Số ĐT khách hàng</th>
						        <th data-field="totalAmount" data-sortable="true">Giá trị HĐ/Phí</th>
						        <th data-field="startDate" data-sortable="true">Thông báo</th>
						        <th data-field="expireDate" data-sortable="true">Ngày</th>
						        <th data-field="action" >Thao tác</th>	 		
						    </tr>
						    </thead>
						    <tbody>
						    	<c:forEach var="ncBean" items="${notificationPage.contracts}">
							    	<tr>
							    		<td>
							    			<a href="#" onclick="openContractDetail(${ncBean.contract.id})">${ncBean.contract.customer.name}&nbsp;${ncBean.contract.customer.birthYear}</a></td>
										<td>Cho thuê xe</td>
										<td class="text-right">
											${ncBean.contract.customer.phone}
										</td>
										<td class="text-right">
											<fmt:formatNumber  currencySymbol=" "  value="${ncBean.contract.totalAmount}" type="currency" maxFractionDigits="0" var="totalAmount"/>
											${fn:replace(totalAmount, ",", ".")}
											VNĐ
										</td>
										<td class="text-center">
											<c:if test="${ncBean.stage=='paid' }">
												<span class="as-block">Trả phí</span>
												<span class="as-block"> (trước ${ncBean.contract.periodOfPayment} ngày)</span>
											</c:if>
											<c:if test="${ncBean.stage=='payoff' }">
												Thanh lý
											</c:if>
										</td>
										
										<td class="text-center">
											<span class="${ncBean.severity}">
											<span class="as-block">
												<fmt:formatDate pattern="dd/MM/yyyy" value="${ncBean.paidDate}" />
											</span>
											<span class="as-block">
												(${ncBean.amountDays} Ngày)
											</span>
											</span>
										</td>
										<td>
											<c:if test="${ncBean.stage=='paid' }">
												<spring:url value="/contract/${ncBean.contract.id}/paid" var="paidUrl" /> 
												<button class="btn btn-primary btn-xs" onclick="location.href='${paidUrl}'">Trả phí</button>
											</c:if>
											<c:if test="${ncBean.stage=='payoff' }">
												<spring:url value="/contract/${ncBean.contract.id}/payoff" var="payOffUrl" />
												<button class="btn btn-danger btn-xs" onclick="location.href='${payOffUrl}'">Thanh lý</button>
											</c:if>
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
	
</div>
<script type="text/javascript">
$( document ).ready(function() {
	hideAlert("ncAlert");
	nc_initNotificationPage();
});
</script>