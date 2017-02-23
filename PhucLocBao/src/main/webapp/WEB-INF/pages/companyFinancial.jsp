<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="includes/jstl.jsp"%>
<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">			
	<div class="row">
		<ol class="breadcrumb">
			<li><a href="#"><svg class="glyph stroked home"><use xlink:href="#stroked-home"></use></svg></a></li>
			<li class="active">Quản lý tài chính</li>
		</ol>
	</div><!--/.row-->
	<c:if test="${not empty msg}">
		<div id="generalAlert" class="alert alert-${css} alert-dismissible fade in" role="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong>${msg}</strong>
		</div>
	</c:if>
	<form:form role="form" id="companyFinancialForm" method="post" action="updateCompanyFinancial" modelAttribute="cfBean">
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<span class="glyphicon glyphicon-globe" aria-hidden="true"></span>Thông tin công ty
					</div>
					<div class="panel-body">
						<div class="col-md-6">
							<spring:bind path="company.name">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Tên Công ty:</label>
									<form:input id="companyName" class="form-control"
										placeholder="vd: Phúc Lộc Bảo SG" name="companyName"
										path="company.name" />
									<form:errors path="company.name" cssClass="error" />
								</div>
							</spring:bind>
							
							<spring:bind path="company.phoneNumber">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Số điện thoại:</label>
									<form:input id="companyPhone" class="form-control"
										placeholder="vd: 0909 123456" name="companyPhone"
										path="company.phoneNumber" />
									<form:errors path="company.phoneNumber" cssClass="error" />
								</div>
							</spring:bind>
							
							<spring:bind path="company.fax">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Số Fax</label>
									<form:input id="companyFax" 
										class="form-control" placeholder="vd: 08 12345678"
										name="companyFax" path="company.fax" />
									<form:errors path="company.fax" cssClass="error" />
								</div>
							</spring:bind>
							
						</div>
						<div class="col-md-6">
							<spring:bind path="company.address">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Địa chỉ:</label>
									<form:textarea id="companyAddress" class="form-control"
										placeholder="vd: B64 Bạch Đằng" rows="6" name="companyAddress"
										path="company.address" />
									<form:errors path="company.address" cssClass="error" />
								</div>
							</spring:bind>
							<spring:bind path="company.city">
								<div class="form-group ${status.error ? 'has-error' : ''}"
									style="margin-top: -6px;">
									<label>Tỉnh/Thành phố:</label>
									<form:select class="form-control" id="companyCity"  
										name="companyCity" path="company.city">
										<form:option value="" label="--Chọn Tỉnh/TP--" />
										<form:options items="${cfBean.cities}" itemValue="code"
											itemLabel="name" />
									</form:select>
									<form:errors path="company.city" cssClass="error" />
								</div>
							</spring:bind>
						</div>
					</div>
				</div>
			</div>
			<!-- /.col-->
		</div>
		<!-- /.row -->
		
		<!-- Financial Info -->
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<span class="glyphicon glyphicon-piggy-bank" aria-hidden="true"></span>Thông tin tài chính
					</div>
					<div class="panel-body">
						<div class="col-md-6">
							<spring:bind path="company.originalFund">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Vốn ban đầu:</label>
									<form:input id="originalFund" class="form-control text-right"
										 placeholder="VNĐ" name="originalFund" 
										path="company.originalFund" />
									<form:errors path="company.originalFund" cssClass="error" />
								</div>
							</spring:bind>
							<spring:bind path="company.totalFund">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Vốn hiện tại:</label>
									<form:input id="totalFund" class="form-control text-right"
										 placeholder="VNĐ" name="totalFund" 
										path="company.totalFund" />
									<form:errors path="company.totalFund" cssClass="error" />
								</div>
							</spring:bind>
							<spring:bind path="company.startDate">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Ngày áp dụng:</label>
									<form:input id="companyStartDate" class="form-control readonlyDate"
										readonly="true" placeholder="Ngày/Tháng/Năm" name="companyStartDate"
										path="company.startDate" />
									<form:errors path="company.startDate" cssClass="error" />
								</div>
							</spring:bind>
						</div>
						<div class="col-md-6">
							<spring:bind path="company.revenueBeforeStartDate">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Doanh thu (trước ngày áp dụng):</label>
									<form:input id="revenueBeforeStartDate" class="form-control text-right"
										 placeholder="VNĐ" name="revenueBeforeStartDate" 
										path="company.revenueBeforeStartDate" />
									<form:errors path="company.revenueBeforeStartDate" cssClass="error" />
								</div>
							</spring:bind>
							<spring:bind path="company.costBeforeStartDate">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Tổng chi phí (trước ngày áp dụng):</label>
									<form:input id="costBeforeStartDate" class="form-control text-right"
										 placeholder="VNĐ" name="costBeforeStartDate" 
										path="company.costBeforeStartDate" />
									<form:errors path="company.costBeforeStartDate" cssClass="error" />
								</div>
							</spring:bind>
							<spring:bind path="company.investBeforeStartDate">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Tổng đầu tư (trước ngày áp dụng):</label>
									<form:input id="investBeforeStartDate" class="form-control text-right"
										 placeholder="VNĐ" name="investBeforeStartDate" 
										path="company.investBeforeStartDate" />
									<form:errors path="company.investBeforeStartDate" cssClass="error" />
								</div>
							</spring:bind>
							<spring:bind path="company.description">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Ghi chú:</label>
									<form:textarea class="form-control" placeholder="thông tin thêm"
										rows="6" name="description" path="company.description" />
									<form:errors path="company.description" cssClass="error" />
								</div>
							</spring:bind>
						</div>
					</div>
				</div>
			</div>
			<!-- /.col-->
		</div>
		<!-- /.row -->
		<div class="row">
			<div class="col-lg-12">
				<div class="pull-right">
					<button id="btnSaveCompanyFinancial" type="submit" class="btn btn-primary" name="save">
						<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
						Lưu thông tin
					</button>
				</div>
			</div>
		</div>
	</form:form>
	
	<script type="text/javascript">
		$( document ).ready(function() {
			hideAlert("generalAlert");
			cf_init();
		});
	</script>
</div>
	