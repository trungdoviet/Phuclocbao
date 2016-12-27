<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">			
	<div class="row">
		<ol class="breadcrumb">
			<li><a href="#"><svg class="glyph stroked home"><use xlink:href="#stroked-home"></use></svg></a></li>
			<li class="active">Tạo hợp đồng mới</li>
		</ol>
	</div><!--/.row-->
	
	<!-- <div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">Tạo hợp đồng mới</h1>
		</div>
	</div> --><!--/.row-->
<form:form role="form" id="newContractForm" method="post" action="newContractAction" modelAttribute="contractBean">
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading"><span class="glyphicon glyphicon-user" aria-hidden="true"></span>Thông tin khách hàng</div>
					<div class="panel-body">
						<div class="col-md-6">
							<spring:bind path="contractDto.customer.idNo">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Số CMND/Số GPLX:</label>
									<form:input class="form-control" placeholder="vd: BA-012345678" name="customerIdNo" path="contractDto.customer.idNo"/>
									<form:errors path="contractDto.customer.idNo" cssClass="error" />
								</div>
							</spring:bind>
							<spring:bind path="contractDto.customer.name">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Tên khách hàng:</label>
									<form:input class="form-control" placeholder="vd: Nguyễn Văn A" name="customerName" path="contractDto.customer.name"/>
									<form:errors path="contractDto.customer.name" cssClass="error" />
								</div>
							</spring:bind>
							<spring:bind path="contractDto.customer.phone">		
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Số điện thoại:</label>
									<form:input class="form-control" placeholder="vd: 0909 123456" name="customerPhone" path="contractDto.customer.phone"/>
									<form:errors path="contractDto.customer.phone" cssClass="error" />
								</div>		
							</spring:bind>						
						</div>
						<div class="col-md-6">
							<spring:bind path="contractDto.customer.address">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Địa chỉ:</label>
									<form:textarea class="form-control" placeholder="vd: B64 Bạch Đằng" rows="6" name="customerAddress" path="contractDto.customer.address"/>
									<form:errors path="contractDto.customer.address"  cssClass="error" />
								</div>
							</spring:bind>
							<spring:bind path="contractDto.customer.province">		
								<div class="form-group ${status.error ? 'has-error' : ''}" style="margin-top: -6px;">
									<label>Tỉnh/Thành phố:</label>
									<form:select class="form-control" name="customerProvince" path="contractDto.customer.province">
										<form:option value="" label="--Chọn Tỉnh/TP--" />
										<form:options items="${contractBean.cities}" itemValue="code" itemLabel="name"  />
									</form:select>
									<form:errors path="contractDto.customer.province"  cssClass="error" />
								</div>
							</spring:bind>
						</div>
					</div>
				</div>
		</div><!-- /.col-->
	</div><!-- /.row -->
	
	<!-- Contract Info -->
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Thông tin hợp đồng</div>
					<div class="panel-body">
						<div class="col-md-6">
							<spring:bind path="contractDto.contractType">		
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Loại hình:</label>
									<form:select disabled="true" class="form-control" name="customerProvince" path="contractDto.contractType">
										<option value="RMB">Cho thuê xe</option>
										<!-- <option value="HN"></option> -->
									</form:select>
									<form:errors path="contractDto.contractType"  cssClass="error" />
								</div>
							</spring:bind>
							
							<spring:bind path="contractDto.company.id">		
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Công ty:</label>
									<form:select disabled="true" class="form-control" name="customerProvince" path="contractDto.company.id">
										<option value="${contractBean.currentCompany.id}">${contractBean.currentCompany.name}</option>
										<!-- <option value="HN"></option> -->
									</form:select>
									<form:errors path="contractDto.company.id"  cssClass="error" />
								</div>
							</spring:bind>
							
							 <spring:bind path="contractDto.totalAmount">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Số tiền:</label>
									<form:input id="totalAmount" class="form-control" placeholder="VNĐ" name="totalAmount" path="contractDto.totalAmount"/>
									<form:errors path="contractDto.totalAmount" cssClass="error" />
								</div>
							</spring:bind>
							 <spring:bind path="contractDto.feeADay">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Phí thuê/1 ngày:</label>
									<form:input id="feeADay" class="form-control" placeholder="VNĐ" name="feeADay" path="contractDto.feeADay"/>
									<form:errors path="contractDto.feeADay" cssClass="error" />
								</div>
							</spring:bind>
							<spring:bind path="contractDto.note">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Ghi chú:</label>
									<form:textarea class="form-control" placeholder="thông tin thêm" rows="6" name="contractNote" path="contractDto.note"/>
									<form:errors path="contractDto.note"  cssClass="error" />
								</div>
							</spring:bind>			
						</div>
						<div class="col-md-6">
							<spring:bind path="contractDto.startDate">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Ngày thuê:</label>
									<form:input id="startDate" class="form-control readonlyDate" readonly="true" placeholder="Ngày/Tháng/Năm" name="startDate" path="contractDto.startDate"/>
									<form:errors path="contractDto.startDate" cssClass="error" />
								</div>
							</spring:bind>
							<spring:bind path="contractDto.expireDate">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Ngày hết hạn:</label>
									<form:input id="expireDate" class="form-control readonlyDate" readonly="true" placeholder="Ngày/Tháng/Năm" name="expireDate" path="contractDto.expireDate"/>
									<form:errors path="contractDto.expireDate" cssClass="error" />
								</div>
							</spring:bind>
							<spring:bind path="contractDto.periodOfPayment">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Khoảng ngày thanh toán:</label><cite>(Tối đa 30 ngày)</cite>
									<form:input id="periodOfPayment" class="form-control" placeholder="Ngày" name="periodOfPayment" path="contractDto.periodOfPayment"/>
									<form:errors path="contractDto.periodOfPayment" cssClass="error" />
								</div>
							</spring:bind>
							
							<%-- 
							<spring:bind path="contractDto.customer.province">		
								<div class="form-group ${status.error ? 'has-error' : ''}" style="margin-top: -6px;">
									<label>Tỉnh/Thành phố:</label>
									<form:select class="form-control" name="customerProvince" path="contractDto.customer.province">
										<option value="">--Chọn Tỉnh/TP--</option>
										<option value="HCM">TP.Hồ Chí Minh</option>
										<option value="HN">Hà Nội</option>
										<option value="CB">Cao Bằng</option>
										<option value="LC">Lai Châu</option>
									</form:select>
									<form:errors path="contractDto.customer.province"  cssClass="error" />
								</div>
							</spring:bind> --%>
						</div>
					</div>
				</div>
		</div><!-- /.col-->
	</div><!-- /.row -->
	<div class="row">
		<div class="col-lg-12">
			<div class="pull-right">
				<spring:url value="/newContractAction/cancel" var="cancelUrl" />
				<button type="submit" class="btn btn-primary" name="save">
					<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
					Tạo hợp đồng
				</button>
				<button type="button" class="btn btn-slave" name="cancel" onclick="location.href='${cancelUrl}'">
					<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					Đóng
				</button>
			</div>
		</div>
	</div>
</form:form>
</div>
