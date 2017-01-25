<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="includes/jstl.jsp"%>
<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">			
	<div class="row">
		<ol class="breadcrumb">
			<li><a href="#"><svg class="glyph stroked home"><use xlink:href="#stroked-home"></use></svg></a></li>
			<li class="active">Nhật ký hoạt động</li>
		</ol>
	</div><!--/.row-->
	<%-- <c:if test="${not empty msg}">
		<div id="historyAlert" class="alert alert-${css} alert-dismissible fade in" role="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong>${msg}</strong>
		</div>
	</c:if>
	<form:form role="form" id="historyForm" method="post" action="filterHistory" modelAttribute="historyView">
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading text-center"><span class="glyphicon glyphicon-bullhorn" aria-hidden="true"></span>Thông báo</div>
					<div class="panel-body">
						<div class="col-md-6">
							<div class="row">
								<div class="col-md-12 text-left">
									<div class="pull-left">
										<strong style="line-height: 32px;">Từ ngày:</strong>
									</div>
									<spring:bind path="startDate">
										<div class="form-group ${status.error ? 'has-error' : ''} pull-left stay-left" >
											<form:input id="startDateHistory" class="form-control readonlyDate"
												readonly="true" placeholder="Ngày/Tháng/Năm" name="startDateHistory"
												path="startDate" />
											<form:errors path="startDate" cssClass="error" />
										</div>
									</spring:bind>
									<div class="pull-left stay-left">
									<strong style="line-height: 32px;">Đến ngày:</strong>
									</div>
										<spring:bind path="endDate">
											<div class="form-group ${status.error ? 'has-error' : ''} pull-left stay-left" >
												<form:input id="endDateHistory" class="form-control readonlyDate"
													readonly="true" placeholder="Ngày/Tháng/Năm" name="endDateHistory"
													path="endDate" />
												<form:errors path="endDate" cssClass="error" />
											</div>
										</spring:bind>
									<button id="btnSearch" type="submit" class="btn btn-primary pull-left stay-left" name="search">
										<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
										Tìm
									</button>
								</div>
							</div>
						</div>
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
						        <th data-field="historyType" data-sortable="true">Loại chi phí</th>
						        <th data-field="fee" data-sortable="true">Số tiền</th>
						        <th data-field="rentingAmount" data-sortable="true">Cho thuê</th>
						        <th data-field="payoff" data-sortable="true">Thanh lý</th>
						        <th data-field="logDate" data-sortable="true">Ngày tháng</th>
						        <th data-field="detail" data-sortable="true">Thông tin thêm</th>
						        <th data-sortable="true">Tham chiếu>
						    </tr>
						    </thead>
						    <tbody>
						    	<c:forEach var="history" items="${historyView.paymentHistories}">
							    	<tr>
							    		<td>
							    			${history.historyName}
							    		</td>
										<td class="text-right">
											<fmt:formatNumber  currencySymbol=" "  value="${history.fee}" type="currency" maxFractionDigits="0" var="fee"/>
												${fn:replace(fee, ",", ".")}
												VNĐ
										</td>
										<td class="text-right">
											<fmt:formatNumber  currencySymbol=" "  value="${history.rentingAmount}" type="currency" maxFractionDigits="0" var="rentingAmount"/>
												${fn:replace(rentingAmount, ",", ".")}
												VNĐ
										</td>
										<td class="text-right">
											<fmt:formatNumber  currencySymbol=" "  value="${history.payoff}" type="currency" maxFractionDigits="0" var="payoff"/>
											${fn:replace(payoff, ",", ".")}
											VNĐ
										</td>
										<td class="text-center">
											<fmt:formatDate pattern="dd/MM/yyyy" value="${history.logDate}" />
										</td>
										<td class="text-left">
											${history.detail}
										</td>
										<td>
											<a href="#" onclick="openContractDetail(${history.contractId})">Hợp đồng số HDTC<span>${history.contractId}</span></a>
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
	hideAlert("historyAlert");
	ph_initPage();
});
</script> --%>
	