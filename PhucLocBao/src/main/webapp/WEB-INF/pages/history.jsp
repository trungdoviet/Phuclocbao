<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="includes/jstl.jsp"%>
<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main phuclocbao">			
	<div class="row">
		<ol class="breadcrumb">
			<li><a href="#"><svg class="glyph stroked home"><use xlink:href="#stroked-home"></use></svg></a></li>
			<li class="active">Nhật ký hoạt động</li>
		</ol>
	</div><!--/.row-->
	<c:if test="${not empty msg}">
		<div id="uhAlert" class="alert alert-${css} alert-dismissible fade in" role="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong>${msg}</strong>
		</div>
	</c:if>
	<form:form role="form" id="userHistoryForm" method="post" action="filterUserHistory" modelAttribute="historyView">
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading text-center"><span class="glyphicon glyphicon-bullhorn" aria-hidden="true"></span>Nhật ký hoạt động</div>
					<div class="panel-body">
						<div class="col-md-12">
							<div class="row">
								<div class="col-md-12 text-left">
									<div class="pull-left">
										<strong style="line-height: 32px;">Từ ngày:</strong>
									</div>
									<spring:bind path="startDate">
										<div class="form-group ${status.error ? 'has-error' : ''} pull-left stay-left" >
											<form:input id="startDateUserHistory" class="form-control readonlyDate"
												readonly="true" placeholder="Ngày/Tháng/Năm" name="startDateUserHistory"
												path="startDate" />
											<form:errors path="startDate" cssClass="error" />
										</div>
									</spring:bind>
									<div class="pull-left stay-left">
									<strong style="line-height: 32px;">Đến ngày:</strong>
									</div>
										<spring:bind path="endDate">
											<div class="form-group ${status.error ? 'has-error' : ''} pull-left stay-left" >
												<form:input id="endDateUserHistory" class="form-control readonlyDate"
													readonly="true" placeholder="Ngày/Tháng/Năm" name="endDateUserHistory"
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
						        <th data-field="historyType" data-sortable="true">Hoạt động</th>
						        <th data-field="fee" data-sortable="true">Tài khoản</th>
						        <th data-field="rentingAmount" data-sortable="true">Công ty</th>
						        <th data-field="payoff" data-sortable="true">Thông tin hoạt động</th>
						        <th data-field="logDate" data-sortable="true">Thời gian</th>
						    </tr>
						    </thead>
						    <tbody>
						    	<c:forEach var="history" items="${historyView.userHistories}">
							    	<tr>
							    		<td >
							    			${history.historyName}
							    		</td>
										<td>
											${history.accountName}
										</td>
										
										<td>
											${history.companyName}
										</td>
	
										<td >
										<div class="detail-message-table-medium">
											${history.detail}
											</div>
										</td>
										<td class="text-center">
											<fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${history.happenTime}" />
										</td>
							    	</tr>
						    	</c:forEach>
						    </tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
</div>
<script type="text/javascript">
$( document ).ready(function() {
	hideAlert("uhAlert");
	uh_initPage();
});
</script>