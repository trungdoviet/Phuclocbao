<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="includes/jstl.jsp"%>
<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main notification phuclocbao">			
	<div class="row noprint">
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
					<div class="panel-heading text-center"><span class="glyphicon glyphicon-bullhorn" aria-hidden="true"></span>Thông báo</div>
					<div class="panel-body noprint">
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
									<button id="btnSearch" type="submit" class="btn btn-primary pull-left stay-left noprint" name="search">
										<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
										Tìm
									</button>
								</div>
								
							</div>
						</div>
						 <div class="col-md-6">
								<a href="javascript:window.print();" class="noprint pull-right">
								<span class="glyphicon glyphicon-print" aria-hidden="true"></span>
								In trang</a>
						</div> 
					</div>
				</div>
			</div>
		</div>
		</form:form>
		<!-- Table -->
		<div class="row notification-table">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-body">
						<table data-toggle="table" data-pagination="true" data-page-list="[30, 60, 120, 240]" data-page-size="30">
						    <thead>
						    <tr>
						        <th data-field="customerName" data-sortable="true" class="text-print-wrapper">Tên khách hàng</th>
						        <th data-field="contractType" data-sortable="true" class="noprint text-print-wrapper" >Loại hình thuê</th>
						        <th data-field="customerPhone" data-sortable="true" class="customer-phone text-print-wrapper">Số ĐT khách hàng</th>
						        <th data-field="totalAmount" data-sortable="true" class="text-print-wrapper">Giá trị HĐ/Phí</th>
						        <th data-field="startDate" data-sortable="true" class="text-print-wrapper">Thông báo</th>
						        <th data-field="expireDate" data-sortable="true" class="text-print-wrapper">Ngày</th>
						        <th data-field="action" class="noprint text-print-wrapper" >Thao tác</th>
						        <th class="text-print-wrapper screen-hide" >Xác nhận</th>	 		
						    </tr>
						    </thead>
						    <tbody>
						    	<c:forEach var="ncBean" items="${notificationPage.contracts}">
							    	<tr>
							    		<td>
							    			<span class="text-print-wrapper">
							    				<a href="#" onclick="openContractDetail(${ncBean.contract.id})">${ncBean.contract.customer.name}&nbsp;${ncBean.contract.customer.birthYear}</a>
							    			</span>
							    		</td>
										<td  class="noprint">Cho thuê xe</td>
										<td class="text-right customer-phone">
											<span class="text-print-wrapper">
												${ncBean.contract.customer.phone}
											</span>
										</td>
										<td class="text-right">
											<span class="text-print-wrapper">
												<c:if test="${ncBean.stage=='paid' }">
													<fmt:formatNumber  currencySymbol=" "  value="${ncBean.contract.totalFeeOnePeriod}" type="currency" maxFractionDigits="0" var="totalFeeOnePeriod"/>
													${fn:replace(totalFeeOnePeriod, ",", ".")}
													VNĐ
												</c:if>
												<c:if test="${ncBean.stage=='payoff' }">
													<fmt:formatNumber  currencySymbol=" "  value="${ncBean.contract.totalAmount}" type="currency" maxFractionDigits="0" var="totalAmount"/>
													${fn:replace(totalAmount, ",", ".")}
													VNĐ
												</c:if>
											</span>
										</td>
										<td class="text-center">
											<span class="text-print-wrapper">
												<c:if test="${ncBean.stage=='paid' }">
													<span class="as-block">Trả phí</span>
													<span class="as-block"> (trước ${ncBean.contract.periodOfPayment} ngày)</span>
												</c:if>
												<c:if test="${ncBean.stage=='payoff' }">
													Thanh lý
													<c:if test="${ncBean.contract.lastPaidDate != null}">
														<span class="as-block">
															(nợ phí từ <fmt:formatDate pattern="dd/MM/yyyy" value="${ncBean.contract.lastPaidDate}" />)
														</span>
													</c:if>
													<c:if test="${ncBean.contract.lastPaidDate == null}">
														(chưa đóng phí)
													</c:if>
												</c:if>
											</span>
										</td>
										
										<td class="text-center">
											<span class="text-print-wrapper">
												<span class="${ncBean.severity}">
												<span class="as-block">
													<fmt:formatDate pattern="dd/MM/yyyy" value="${ncBean.paidDate}" />
												</span>
												<span class="as-block">
													(${ncBean.amountDays} Ngày)
												</span>
												</span>
											</span>
										</td>
										<td class="noprint">
											<c:if test="${ncBean.stage=='paid' }">
												<spring:url value="/contract/${ncBean.contract.id}/paid" var="paidUrl" /> 
												<button class="btn btn-primary btn-xs noprint" onclick="location.href='${paidUrl}'">Trả phí</button>
											</c:if>
											<c:if test="${ncBean.stage=='payoff' }">
												<spring:url value="/contract/${ncBean.contract.id}/payoff" var="payOffUrl" />
												<button class="btn btn-danger btn-xs noprint" onclick="location.href='${payOffUrl}'">Thanh lý</button>
											</c:if>
										</td>
										<td class="screen-hide">
											<div class="signature"> </div>
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