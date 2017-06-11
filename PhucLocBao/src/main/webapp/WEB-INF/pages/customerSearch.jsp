<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="includes/jstl.jsp"%>
<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main phuclocbao">			
	<div class="row">
		<ol class="breadcrumb">
			<li><a href="#"><svg class="glyph stroked home"><use xlink:href="#stroked-home"></use></svg></a></li>
			<li class="active">Tìm khách hàng</li>
		</ol>
	</div><!--/.row-->
	<c:if test="${not empty msg}">
		<div id="customerAlert" class="alert alert-${css} alert-dismissible fade in" role="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong>${msg}</strong>
		</div>
	</c:if>
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading text-center"><span class="glyphicon glyphicon-user" aria-hidden="true"></span>Tìm khách hàng</div>
					<div class="panel-body">
						<div class="col-md-8">
							<form:form role="form" id="searchCustomerForm" method="post" action="searchCustomer" modelAttribute="csBean">
									<spring:bind path="name">
										<div class="form-group ${status.error ? 'has-error' : ''} pull-left noprint" >
											<form:input id="customerName" class="form-control" cssStyle="width: 240px;"
												placeholder="Họ tên hoặc CMND khách hàng" name="customerName"
												path="name" />
											<form:errors path="name" cssClass="error" />
										</div>
									</spring:bind>
									<button id="btnSearch" type="submit" class="btn btn-primary pull-left stay-left noprint" name="search">
										<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
										Tìm
									</button>
									<spring:bind path="isSearchAllCompany">
										<div class="form-group ${status.error ? 'has-error' : ''} pull-left stay-left noprint" >
											<form:checkbox  path="isSearchAllCompany" cssClass="search-all" label="Tìm tất cả công ty?"></form:checkbox>
											<form:errors path="name" cssClass="error" />
										</div>
									</spring:bind>
							</form:form>
						</div>
						<div class="col-md-4">
							<div style="height:50px">
								<a href="javascript:window.print();" class="noprint pull-right">
									<span class="glyphicon glyphicon-print" aria-hidden="true"></span>
									In trang
								</a>
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
					<label>Khách hàng tại công ty này:</label>
						<table data-toggle="table" >
						    <thead>
						    <tr>
						        <th data-field="name" data-sortable="true">Tên khách hàng</th>
						        <th data-field="phone" data-sortable="true">ĐT</th>
						        <th data-field="idNo" data-sortable="true">CMND</th>
						        <th data-field="address" data-sortable="true">Địa chỉ</th>
						        <th data-field="provinceInString" data-sortable="true">Thành phố</th>
						        <th data-field="contract">Hợp đồng</th>
						    </tr>
						    </thead>
						    <tbody>
						    	<c:forEach var="customer" items="${csBean.customers}">
							    	<tr>
							    		<td>
							    			${customer.name}&nbsp;${customer.birthYear}
							    		</td>
							    		<td class="text-right">
							    			${customer.phone}
							    		</td>
							    		<td>
							    			${customer.idNo}
							    		</td>
							    		<td>
							    			${customer.address}
							    		</td>
							    		<td>
							    			${customer.provinceInString}
							    		</td>
							    		<td>
							    			<div id="plbCustomerContractPanel" class="list-group customer-search">
												<c:if test="${customer.allContracts != null}">
													<c:forEach var="contract"
														items="${customer.allContracts}"
														varStatus="loop">
														<div class="group width-100p">
															<c:if test="${contract.state == 'IN_PROGRESS'}">
																<div class="list-group-item list-group-item-warning">HĐ
																	Mới</div>
															</c:if>
															<c:if test="${contract.state == 'FINISH'}">
																<div class="list-group-item list-group-item-success">HĐ
																	Cũ</div>
															</c:if>
															<c:if test="${contract.state == 'BAD'}">
																<div class="list-group-item list-group-item-bad">HĐ Xấu</div>
															</c:if>
															<div class="list-group-item-label">
																<a class="list-group-link"
																	onclick="openContractDetail(${contract.id})"
																	data-id='${contract.id}' href="#">Ngày
																	${contract.startDate}</a><br>
															</div>
														</div>
													</c:forEach>
												</c:if>
											</div>
							    		</td>
							    	</tr>
						    	</c:forEach>
						    </tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<c:if test="${csBean.isSearchAllCompany}">
			<!-- Table -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-body">
						<label>Khách hàng tại các công ty khác:</label>
							<table data-toggle="table" >
							    <thead>
							    <tr>
							        <th data-field="name" data-sortable="true">Tên khách hàng</th>
							        <th data-field="phone" data-sortable="true">ĐT</th>
							        <th data-field="idNo" data-sortable="true">CMND</th>
							        <th data-field="address" data-sortable="true">Địa chỉ</th>
							        <th data-field="provinceInString" data-sortable="true">Thành phố</th>
							        <th data-field="companyName" data-sortable="true">Thuộc chi nhánh</th>
							        <th data-field="contract">Hợp đồng</th>
							    </tr>
							    </thead>
							    <tbody>
							    	<c:forEach var="customer" items="${csBean.customersInOtherCompany}">
								    	<tr>
								    		<td>
								    			${customer.name}&nbsp;${customer.birthYear}
								    		</td>
								    		<td class="text-right">
								    			${customer.phone}
								    		</td>
								    		<td>
								    			${customer.idNo}
								    		</td>
								    		<td>
								    			${customer.address}
								    		</td>
								    		<td>
								    			${customer.provinceInString}
								    		</td>
								    		<td>
								    			${customer.contract.company.name}
								    		</td>
								    		<td style="width: 20%">
								    			<div id="plbCustomerContractPanel" class="list-group customer-search">
													<c:if test="${customer.allContracts != null}">
														<c:forEach var="contract"
															items="${customer.allContracts}"
															varStatus="loop">
															<div class="group width-100p">
																<c:if test="${contract.state == 'IN_PROGRESS'}">
																	<div class="list-group-item list-group-item-warning">HĐ
																		Mới</div>
																</c:if>
																<c:if test="${contract.state == 'FINISH'}">
																	<div class="list-group-item list-group-item-success">HĐ
																		Cũ</div>
																</c:if>
																<c:if test="${contract.state == 'BAD'}">
																	<div class="list-group-item list-group-item-bad">HĐ Xấu</div>
																</c:if>
																<div class="list-group-item-label">
																	<a class="list-group-link"
																		onclick="openContractDetail(${contract.id})"
																		data-id='${contract.id}' href="#">Hợp đồng ngày
																		${contract.startDate}</a><br>
																</div>
															</div>
														</c:forEach>
													</c:if>
												</div>
								    		</td>
								    	</tr>
							    	</c:forEach>
							    </tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</c:if>
		<jsp:include page="includes/contractDetailDialog.jsp"></jsp:include>
</div>
<script type="text/javascript">
$( document ).ready(function() {
	hideAlert("customerAlert");
});
</script>